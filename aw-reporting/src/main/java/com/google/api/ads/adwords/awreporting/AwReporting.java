// Copyright 2016 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.api.ads.adwords.awreporting;

import com.google.api.ads.adwords.awreporting.model.entities.DateRangeAndType;
import com.google.api.ads.adwords.awreporting.model.util.DateUtil;
import com.google.api.ads.adwords.awreporting.processors.ReportProcessor;
import com.google.api.ads.adwords.awreporting.processors.file.FileReportProcessor;
import com.google.api.ads.adwords.awreporting.proxy.JaxWsProxySelector;
import com.google.api.ads.adwords.awreporting.util.DatabaseType;
import com.google.api.ads.adwords.awreporting.util.ProcessorType;
import com.google.api.ads.adwords.lib.jaxb.v201802.ReportDefinitionDateRangeType;
import com.google.api.client.util.Lists;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.net.ProxySelector;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * Main class that executes the report processing logic delegating to the {@link ReportProcessor}.
 *
 * <p>This class holds a Spring application context that manages the creation of all the beans
 * needed. No configuration is done in this class.
 *
 * <p>Credentials and properties are pulled from the {@code
 * ~/aw-report-sample.properties.properties} file or {@code -file} argument provided.
 *
 * <p>See README for more info.
 */
public class AwReporting {

  private static final Logger logger = LoggerFactory.getLogger(AwReporting.class);

  /** The DB type key specified in the properties file. */
  private static final String AW_REPORT_MODEL_DB_TYPE = "aw.report.model.db.type";

  /** The Processor type key specified in the properties file. */
  private static final String AW_REPORT_PROCESSOR_TYPE = "aw.report.processor.type";

  /** The Spring application context used to get all the beans. */
  private static ClassPathXmlApplicationContext appCtx;

  /** The Spring beans file for processing reports using streaming. */
  private static final String STREAM_PROCESSING_BEANS_FILE = "aw-report-processor-beans-stream.xml";

  /** The Spring beans file for processing reports using files. */
   private static final String FILE_PROCESSING_BEANS_FILE = "aw-report-processor-beans-file.xml";

  /** The Spring beans file for using a SQL database. */
  private static final String SQL_BEANS_FILE = "aw-report-sql-beans.xml";

  /** The Spring beans file for using MongoDB. */
  private static final String MONGODB_BEANS_FILE = "aw-report-mongodb-beans.xml";

  /**
   * Main method.
   *
   * @param args the command line arguments.
   */
  public static void main(String[] args) {
    // Proxy
    JaxWsProxySelector ps = new JaxWsProxySelector(ProxySelector.getDefault());
    ProxySelector.setDefault(ps);

    try {
      Options options = AwReportingOption.createCommandLineOptions();
      CommandLine cmdLine = parseCommandLine(options, args);

      if (cmdLine.hasOption("help")) {
        printHelpMessage(options);
        printSamplePropertiesFile();
        System.exit(0);
      }

      String propertiesPath = getPropertiesPath(cmdLine);

      Set<Long> accountIdsSet = Sets.newHashSet();
      String accountIdsFile = AwReportingOption.ACCOUNT_IDS_FILE.getOptionValue(cmdLine);
      if (accountIdsFile != null) {
        addAccountsFromFile(accountIdsSet, accountIdsFile);
        if (accountIdsSet.isEmpty()) {
          logger.error(
              "Account IDs file does not contain any AdWords CIDs (" + accountIdsFile + ")");
          System.exit(1);
        }
      }

      ReportDefinitionDateRangeType dateRangeType = ReportDefinitionDateRangeType.CUSTOM_DATE;
      String dateRangeStr = AwReportingOption.DATE_RANGE.getOptionValue(cmdLine);
      if (dateRangeStr != null) {
        dateRangeType = ReportDefinitionDateRangeType.fromValue(dateRangeStr);
      }

      LocalDate startDate =
          DateUtil.parseLocalDate(AwReportingOption.START_DATE.getOptionValue(cmdLine));
      LocalDate endDate =
          DateUtil.parseLocalDate(AwReportingOption.END_DATE.getOptionValue(cmdLine));

      String reportFileTypeName = AwReportingOption.REPORT_FILE_TYPE.getOptionValue(cmdLine);
      String csvReportFile = AwReportingOption.CSV_FILE_INPUT.getOptionValue(cmdLine);

      boolean forceFileProcessor = !Strings.isNullOrEmpty(reportFileTypeName);
      Properties properties = init(propertiesPath, forceFileProcessor);

      logger.debug("Creating ReportProcessor bean...");
      ReportProcessor processor = createReportProcessor();
      logger.debug("... success.");

      String managerAccountId = properties.getProperty("managerAccountId").replaceAll("-", "");
      DateRangeAndType dateRangeAndType =
          DateRangeAndType.fromValues(startDate, endDate, dateRangeType);

      if (forceFileProcessor) {
        validateFileReportArguments(startDate, endDate, csvReportFile);
        List<File> localFiles = getCsvFiles(csvReportFile);
        ((FileReportProcessor) processor)
            .processInputFiles(managerAccountId, reportFileTypeName, localFiles, dateRangeAndType);
      } else {
        processor.generateReportsForManagerAccount(
            managerAccountId,
            dateRangeAndType,
            accountIdsSet,
            properties);
      }
    } catch (ReportConfigLoadException e) {
      logger.error("Failed to load configuration.", e);
      System.exit(1);
    } catch (ReportProcessingException e) {
      logger.error("Failed to process reports.", e);
      System.exit(1);
    }
  }

  private static CommandLine parseCommandLine(Options options, String[] args)
      throws ReportConfigLoadException {
    CommandLineParser parser = new DefaultParser();
    try {
      return parser.parse(options, args);
    } catch (ParseException e) {
      throw new ReportConfigLoadException("Unable to parse command line arguments", e);
    }
  }

  private static String getPropertiesPath(CommandLine cmdLine) throws ReportConfigLoadException {
    String propertiesPath = AwReportingOption.FILE.getOptionValue(cmdLine);
    if (propertiesPath == null) {
      throw new ReportConfigLoadException(
          "Missing required option: '" + AwReportingOption.FILE.getArgName() + "'");
    }

    logger.info("Using properties file: " + propertiesPath);
    return propertiesPath;
  }

  /**
   * Validate that user entered all configs for reading reports from a local file like start date,
   * end date, and the file to read.
   *
   * @param startDate YYYYMMdd date to start.
   * @param endDate YYYYMMdd date to end.
   * @param csvReportFile csv file where data is stored.
   */
  private static void validateFileReportArguments(
      LocalDate startDate, LocalDate endDate, String csvReportFile)
      throws ReportConfigLoadException {
    if (startDate == null || endDate == null || Strings.isNullOrEmpty(csvReportFile)) {
      throw new ReportConfigLoadException(
          String.format(
              "Inputing a report file requires: '%s', '%s', and '%s'",
              AwReportingOption.START_DATE.getArgName(),
              AwReportingOption.END_DATE.getArgName(),
              AwReportingOption.CSV_FILE_INPUT.getArgName()));
    }

    logger.info(
        String.format(
            "Starting report processing for startDate: %s and endDate: %s on csvReportFile: %s",
            startDate, endDate, csvReportFile));
  }

  /**
   * Checks if file exists and then creates a {@code List} of {@code File}s.
   *
   * @param csvReportFile csv file where data is stored.
   * @return list of csv files for process.
   */
  private static List<File> getCsvFiles(String csvReportFile) throws ReportConfigLoadException {
    File csvFile = new File(csvReportFile);
    if (!csvFile.exists()) {
      throw new ReportConfigLoadException("Could not find CSV file: " + csvReportFile);
    }

    return Arrays.asList(csvFile);
  }

  /**
   * Reads the account ids from the file, and adds them to the given set.
   *
   * @param accountIdsSet the set to add the accounts
   * @param accountsFileName the file to be read
   */
  protected static void addAccountsFromFile(Set<Long> accountIdsSet, String accountsFileName)
      throws ReportConfigLoadException {
    logger.info("Using accounts file: " + accountsFileName);
    List<String> lines = null;

    try {
      lines = Files.asCharSource(new File(accountsFileName), StandardCharsets.UTF_8).readLines();
    } catch (IOException e) {
      throw new ReportConfigLoadException("Unable to read accounts from file.", e);
    }

    logger.debug("Account IDs to be queried:");
    for (String line : lines) {
      line = line.trim();
      if (!line.startsWith("#") && !line.isEmpty()) {
        String accountIdAsString = line.replaceAll("-", "");
        long accountId = Long.parseLong(accountIdAsString);
        accountIdsSet.add(accountId);

        logger.debug("Account ID: " + accountId);
      }
    }
  }

  /**
   * Creates the {@link ReportProcessor} autowiring all the dependencies.
   *
   * @return the {@code ReportProcessor} with all the dependencies properly injected.
   */
  private static ReportProcessor createReportProcessor() {
    return appCtx.getBean(ReportProcessor.class);
  }

  /**
   * Prints the help message.
   *
   * @param options the options available for the user.
   */
  private static void printHelpMessage(Options options) {
    // automatically generate the help statement
    System.out.println();
    HelpFormatter formatter = new HelpFormatter();
    formatter.setWidth(120);
    formatter.printHelp(
        " java -jar aw-reporting.jar -startDate YYYYMMDD -endDate YYYYMMDD " + "-file <file>\n",
        "Arguments:",
        options,
        "");
    System.out.println();
  }

  /** Prints the sample properties file on the default output. */
  private static void printSamplePropertiesFile() throws ReportConfigLoadException {
    System.out.println("\n  File: aw-report-sample.properties example");
    ClassPathResource sampleFile = new ClassPathResource("aw-report-sample.properties");

    try {
      List<String> lines =
          Files.asCharSource(sampleFile.getFile(), StandardCharsets.UTF_8).readLines();
      for (String line : lines) {
        System.out.println(line);
      }
    } catch (IOException e) {
      throw new ReportConfigLoadException("Unable to print sample properties file", e);
    }
  }

  /**
   * Initialize the application context, adding the properties configuration file depending on the
   * specified path.
   *
   * @param propertiesPath the path to the file.
   * @param forceFileProcessor true if the processor will be created to run "on file"
   * @return the properties instance loaded from the properties file.
   * @throws ReportConfigLoadException error opening the properties file.
   */
  private static Properties init(String propertiesPath, boolean forceFileProcessor)
      throws ReportConfigLoadException {
    logger.trace("Initializing Spring application context.");

    Resource resource = new ClassPathResource(propertiesPath);
    if (!resource.exists()) {
      resource = new FileSystemResource(propertiesPath);
    }
    PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
    configurer.setLocation(resource);

    Properties properties = null;
    try {
      properties = PropertiesLoaderUtils.loadProperties(resource);
    } catch (IOException e) {
      throw new ReportConfigLoadException("Failed to load properties file.", e);
    }

    // Selecting the XMLs to choose the Spring Beans to load.
    List<String> listOfClassPathXml = Lists.newArrayList();

    // Choose the DB type to use based properties file, default to MYSQL
    String dbType = (String) properties.get(AW_REPORT_MODEL_DB_TYPE);
    DatabaseType sqldbType = null;
    if (DatabaseType.MONGODB.name().equals(dbType)) {
      logger.info("Using MONGO DB configuration properties.");
      listOfClassPathXml.add("classpath:" + MONGODB_BEANS_FILE);
    } else if (DatabaseType.MSSQL.name().equals(dbType)) {
      sqldbType = DatabaseType.MSSQL;
      logger.info("Using MSSQL DB configuration properties.");
      listOfClassPathXml.add("classpath:" + SQL_BEANS_FILE);
    } else if (DatabaseType.MYSQL.name().equals(dbType)) {
      sqldbType = DatabaseType.MYSQL;
      logger.info("Using MYSQL DB configuration properties.");
      listOfClassPathXml.add("classpath:" + SQL_BEANS_FILE);
    } else {
      throw new ReportConfigLoadException("Unknown database type: " + dbType);
    }

    // Choose the Processor type to use based properties file
    String processorType = (String) properties.get(AW_REPORT_PROCESSOR_TYPE);
    if (forceFileProcessor) {
      processorType = ProcessorType.FILE.name();
    }

    if (ProcessorType.STREAM.name().equals(processorType)) {
      logger.info("Using MEMORY Processor.");
      listOfClassPathXml.add("classpath:" + STREAM_PROCESSING_BEANS_FILE);
    } else if (ProcessorType.FILE.name().equals(processorType)) {
      logger.info("Using FILE Processor.");
      listOfClassPathXml.add("classpath:" + FILE_PROCESSING_BEANS_FILE);
    } else {
      throw new ReportConfigLoadException("Unknown processor type: " + processorType);
    }

    appCtx = new ClassPathXmlApplicationContext();
    if (sqldbType != null) {
      appCtx.getEnvironment().setActiveProfiles(sqldbType.name());
    }

    appCtx.setConfigLocations(listOfClassPathXml.toArray(new String[listOfClassPathXml.size()]));
    appCtx.addBeanFactoryPostProcessor(configurer);
    logger.warn("Updating database schema, this could take a few minutes ...");
    appCtx.refresh();
    logger.warn("Done.");

    return properties;
  }
}
