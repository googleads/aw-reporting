// Copyright 2012 Google Inc. All Rights Reserved.
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

import com.google.api.ads.adwords.awreporting.authentication.Authenticator;
import com.google.api.ads.adwords.awreporting.exporter.ReportExporterLocal;
import com.google.api.ads.adwords.awreporting.processors.ReportProcessor;
import com.google.api.ads.adwords.awreporting.processors.onfile.ReportProcessorOnFile;
import com.google.api.ads.adwords.awreporting.proxy.JaxWsProxySelector;
import com.google.api.ads.adwords.awreporting.util.DataBaseType;
import com.google.api.ads.adwords.awreporting.util.DynamicPropertyPlaceholderConfigurer;
import com.google.api.ads.adwords.awreporting.util.FileUtil;
import com.google.api.ads.adwords.awreporting.util.ProcessorType;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionDateRangeType;
import com.google.api.client.util.Lists;
import com.google.api.client.util.Sets;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ProxySelector;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

/**
 * Main class that executes the report processing logic delegating to the {@link ReportProcessor}.
 *
 *  This class holds a Spring application context that manages the creation of all the beans needed.
 * No configuration is done in this class.
 *
 *  Credentials and properties are pulled from the ~/aw-report-sample.properties.properties file or
 * -file <file> provided.
 *
 * See README for more info.
 *
 * @author jtoledo@google.com (Julian Toledo)
 * @author gustavomoreira@google.com (Gustavo Moreira)
 */
public class AwReporting {

  private static final Logger LOGGER = Logger.getLogger(AwReporting.class);

  /**
   * The DB type key specified in the properties file.
   */
  private static final String AW_REPORT_MODEL_DB_TYPE = "aw.report.model.db.type";

  /**
   * The Processor type key specified in the properties file.
   */
  private static final String AW_REPORT_PROCESSOR_TYPE = "aw.report.processor.type";

  /**
   * The Spring application context used to get all the beans.
   */
  private static ApplicationContext appCtx;

  /**
   * Main method.
   *
   * @param args the command line arguments.
   */
  public static void main(String args[]) {

    // Proxy
    JaxWsProxySelector ps = new JaxWsProxySelector(ProxySelector.getDefault());
    ProxySelector.setDefault(ps);

    Options options = createCommandLineOptions();

    boolean errors = false;
    String propertiesPath = null;

    try {
      CommandLineParser parser = new BasicParser();
      CommandLine cmdLine = parser.parse(options, args);

      // Print full help and quit
      if (cmdLine.hasOption("help")) {
        printHelpMessage(options);
        printSamplePropertiesFile();
        System.exit(0);
      }

      setLogLevel(cmdLine);

      if (cmdLine.hasOption("file")) {
        propertiesPath = cmdLine.getOptionValue("file");
      } else {
        LOGGER.error("Missing required option: 'file'");
        System.exit(0);
      }
      LOGGER.info("Using properties file: " + propertiesPath);

      Set<Long> accountIdsSet = Sets.newHashSet();
      if (cmdLine.hasOption("accountIdsFile")) {
        String accountsFileName = cmdLine.getOptionValue("accountIdsFile");
        addAccountsFromFile(accountIdsSet, accountsFileName);
      }

      boolean forceOnFileProcessor = false;
      if (cmdLine.hasOption("onFileReport")) {
        if (!cmdLine.hasOption("csvReportFile") || !cmdLine.hasOption("startDate")
            || !cmdLine.hasOption("endDate")) {
          LOGGER.error("Missing one or more of the required options: "
              + "'csvReportFile', 'startDate' or 'endDate'");
          System.exit(0);
        }
        forceOnFileProcessor = true;
      }
      Properties properties =
          initApplicationContextAndProperties(propertiesPath, forceOnFileProcessor);

      LOGGER.debug("Creating ReportProcessor bean...");
      ReportProcessor processor = createReportProcessor();
      LOGGER.debug("... success.");

      String mccAccountId = properties.getProperty("mccAccountId").replaceAll("-", "");

      if (cmdLine.hasOption("generatePdf")) {

        LOGGER.debug("GeneratePDF option detected.");

        // Get HTML template and output directory
        String[] pdfFiles = cmdLine.getOptionValues("generatePdf");
        File htmlTemplateFile = new File(pdfFiles[0]);
        File outputDirectory = new File(pdfFiles[1]);
        boolean sumAdExtensions = false;

        if (cmdLine.hasOption("sumAdExtensions")) {
          LOGGER.debug("sumAdExtensions option detected.");
          sumAdExtensions = true;
        }

        LOGGER.debug("Html template file to be used: " + htmlTemplateFile);
        LOGGER.debug("Output directory for PDF: " + outputDirectory);

        // Export Reports
        ReportExporterLocal reportExporter = createReportExporter();
        reportExporter.exportReports(createAuthenticator().getOAuth2Credential(null, mccAccountId,
            false),
            mccAccountId,
            cmdLine.getOptionValue("startDate"),
            cmdLine.getOptionValue("endDate"),
            processor.retrieveAccountIds(null, mccAccountId),
            properties,
            htmlTemplateFile,
            outputDirectory,
            sumAdExtensions);

      } else if (cmdLine.hasOption("startDate") && cmdLine.hasOption("endDate")) {
        // Generate Reports

        String dateStart = cmdLine.getOptionValue("startDate");
        String dateEnd = cmdLine.getOptionValue("endDate");

        if (cmdLine.hasOption("onFileReport")) {

          String reportTypeName = cmdLine.getOptionValue("onFileReport");
          String csvReportFile = cmdLine.getOptionValue("csvReportFile");

          File csvFile = new File(csvReportFile);
          if (!csvFile.exists()) {
            LOGGER.error("Could not find CSV file: " + csvReportFile);
            System.exit(0);
          }

          ReportProcessorOnFile onFileProcessor = (ReportProcessorOnFile) processor;
          List<File> localFiles = new ArrayList<File>();
          localFiles.add(csvFile);

          LOGGER.info("Starting report processing for dateStart: " + dateStart + " and dateEnd: "
              + dateEnd);
          onFileProcessor.processLocalFiles(mccAccountId,
              reportTypeName,
              localFiles,
              dateStart,
              dateEnd,
              ReportDefinitionDateRangeType.CUSTOM_DATE);

        } else {
          LOGGER.info(
              "Starting report download for dateStart: " + dateStart + " and dateEnd: " + dateEnd);

          processor.generateReportsForMCC(null,
              mccAccountId,
              ReportDefinitionDateRangeType.CUSTOM_DATE,
              dateStart,
              dateEnd,
              accountIdsSet,
              properties,
              null,
              null);
        }
      } else if (cmdLine.hasOption("dateRange")) {

        ReportDefinitionDateRangeType dateRangeType =
            ReportDefinitionDateRangeType.fromValue(cmdLine.getOptionValue("dateRange"));

        LOGGER.info("Starting report download for dateRange: " + dateRangeType.name());

        processor.generateReportsForMCC(null,
            mccAccountId,
            dateRangeType,
            null,
            null,
            accountIdsSet,
            properties,
            null,
            null);

      } else {
        errors = true;
        LOGGER.error("Configuration incomplete. Missing options for command line.");
      }

    } catch (IOException e) {
      errors = true;

      if (e.getMessage().contains("Insufficient Permission")) {
        LOGGER.error("Insufficient Permission error accessing the API" + e.getMessage());
      } else {
        LOGGER.error("File not found: " + e.getMessage());
      }

    } catch (ParseException e) {
      errors = true;
      System.err.println(
          "Error parsing the values for the command line options: " + e.getMessage());
    } catch (Exception e) {
      errors = true;
      LOGGER.error("Unexpected error accessing the API: " + e.getMessage());
      e.printStackTrace();
    }

    if (errors) {
      System.exit(1);
    } else {
      System.exit(0);
    }
  }

  /**
   * Reads the account ids from the file, and adds them to the given set.
   *
   * @param accountIdsSet the set to add the accounts
   * @param accountsFileName the file to be read
   * @throws FileNotFoundException file not found
   */
  protected static void addAccountsFromFile(Set<Long> accountIdsSet, String accountsFileName)
      throws FileNotFoundException {

    LOGGER.info("Using accounts file: " + accountsFileName);

    List<String> linesAsStrings = FileUtil.readFileLinesAsStrings(new File(accountsFileName));

    LOGGER.debug("Acount IDs to be queried:");
    for (String line : linesAsStrings) {

      String accountIdAsString = line.replaceAll("-", "");
      long accountId = Long.parseLong(accountIdAsString);
      accountIdsSet.add(accountId);

      LOGGER.debug("Acount ID: " + accountId);
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
   * Creates the {@link ReportExporterLocal} autowiring all the dependencies.
   *
   * @return the {@code ReportExporter} with all the dependencies properly injected.
   */
  private static ReportExporterLocal createReportExporter() {

    return appCtx.getBean(ReportExporterLocal.class);
  }

  /**
   * Creates the {@link Authenticator} autowiring all the dependencies.
   *
   * @return the {@code Authenticator} with all the dependencies properly injected.
   */
  private static Authenticator createAuthenticator() {

    return appCtx.getBean(Authenticator.class);
  }

  /**
   * Creates the command line options.
   *
   * @return the {@link Options}.
   */
  private static Options createCommandLineOptions() {

    Options options = new Options();
    Option help = new Option("help", "print this message");
    options.addOption(help);

    OptionBuilder.withArgName("file");
    OptionBuilder.hasArg(true);
    OptionBuilder.withDescription("aw-report-sample.properties file.");
    OptionBuilder.isRequired(false);
    options.addOption(OptionBuilder.create("file"));

    OptionBuilder.withArgName("YYYYMMDD");
    OptionBuilder.hasArg(true);
    OptionBuilder.withDescription("Start date for CUSTOM_DATE Reports (YYYYMMDD)");
    OptionBuilder.isRequired(false);
    options.addOption(OptionBuilder.create("startDate"));

    OptionBuilder.withArgName("YYYMMDD");
    OptionBuilder.hasArg(true);
    OptionBuilder.withDescription("End date for CUSTOM_DATE Reports (YYYYMMDD)");
    OptionBuilder.isRequired(false);
    options.addOption(OptionBuilder.create("endDate"));

    OptionBuilder.withArgName("DateRangeType");
    OptionBuilder.hasArg(true);
    OptionBuilder.withDescription("ReportDefinitionDateRangeType");
    OptionBuilder.isRequired(false);
    options.addOption(OptionBuilder.create("dateRange"));

    OptionBuilder.withArgName("htmlTemplateFile> <outputDirectory");
    OptionBuilder.withValueSeparator(' ');
    OptionBuilder.hasArgs(2);
    OptionBuilder.withDescription("Generate Monthly Account Reports for all Accounts in PDF\n"
        + "NOTE: For PDF use aw-report-sample-for-pdf.properties instead, "
        + "the fields need to be different.");
    options.addOption(OptionBuilder.create("generatePdf"));

    OptionBuilder.withArgName("accountIdsFile");
    OptionBuilder.hasArg(true);
    OptionBuilder.withDescription(
        "Consider ONLY the account IDs specified on the file to run the report");
    OptionBuilder.isRequired(false);
    options.addOption(OptionBuilder.create("accountIdsFile"));

    OptionBuilder.withArgName("verbose");
    OptionBuilder.hasArg(false);
    OptionBuilder.withDescription("The application will print all the tracing on the console");
    OptionBuilder.isRequired(false);
    options.addOption(OptionBuilder.create("verbose"));

    OptionBuilder.withArgName("sumAdExtensions");
    OptionBuilder.hasArg(false);
    OptionBuilder.withDescription("The application will include calculated sums"
        + "for AdExtension reporting in HTML/PDF reports.");
    OptionBuilder.isRequired(false);
    options.addOption(OptionBuilder.create("sumAdExtensions"));

    OptionBuilder.withArgName("debug");
    OptionBuilder.hasArg(false);
    OptionBuilder.withDescription("Will display all the debug information. "
        + "If the option 'verbose' is activated, "
        + "all the information will be displayed on the console as well");
    OptionBuilder.isRequired(false);
    options.addOption(OptionBuilder.create("debug"));

    OptionBuilder.withArgName("onFileReport");
    OptionBuilder.hasArg(true);
    OptionBuilder.withDescription("This is an experimental argument, where you can specify "
        + "the report type, and the processor will read the data directly from the CSV file "
        + "passed on the 'csvReportFile' argument. It's mandatory to pass the 'csvReportFile' "
        + "argument if you pass this argument.");
    OptionBuilder.isRequired(false);
    options.addOption(OptionBuilder.create("onFileReport"));

    OptionBuilder.withArgName("csvReportFile");
    OptionBuilder.hasArg(true);
    OptionBuilder.withDescription("This is the path to the CSV report file that will be used to "
        + "import data directly into AwReporting.");
    OptionBuilder.isRequired(false);
    options.addOption(OptionBuilder.create("csvReportFile"));

    return options;
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
    formatter.printHelp(" java -Xmx1G -jar aw-reporting.jar -startDate YYYYMMDD -endDate YYYYMMDD "
        + "-file <file>\n java -Xmx1G -jar aw-reporting.jar "
        + "-generatePdf <htmlTemplateFile> <outputDirectory> -sumAdExtensions "
        + "-startDate YYYYMMDD -endDate YYYYMMDD -file <file>", "\nArguments:", options, "");
    System.out.println();
  }

  /**
   * Prints the sample properties file on the default output.
   */
  private static void printSamplePropertiesFile() {

    System.out.println("\n  File: aw-report-sample.properties example");

    ClassPathResource sampleFile = new ClassPathResource("aw-report-sample.properties");
    Scanner fileScanner = null;
    try {
      fileScanner = new Scanner(sampleFile.getInputStream());
      while (fileScanner.hasNext()) {
        System.out.println(fileScanner.nextLine());
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (fileScanner != null) {
        fileScanner.close();
      }
    }
  }

  /**
   * Sets the Log level based on the command line arguments
   *
   * @param commandLine the command line
   */
  private static void setLogLevel(CommandLine commandLine) {

    Level logLevel = Level.INFO;

    if (commandLine.hasOption("debug")) {
      logLevel = Level.DEBUG;
    }

    ConsoleAppender console = new ConsoleAppender(); // create appender
    String pattern = "%d [%p|%c|%C{1}] %m%n";
    console.setLayout(new PatternLayout(pattern));
    console.activateOptions();
    if (commandLine.hasOption("verbose")) {
      console.setThreshold(logLevel);
    } else {
      console.setThreshold(Level.ERROR);
    }
    Logger.getLogger("com.google.api.ads.adwords.awreporting").addAppender(console);

    FileAppender fa = new FileAppender();
    fa.setName("FileLogger");
    fa.setFile("aw-reporting.log");
    fa.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
    fa.setThreshold(logLevel);
    fa.setAppend(true);
    fa.activateOptions();
    Logger.getLogger("com.google.api.ads.adwords.awreporting").addAppender(fa);

  }

  /**
   * Initialize the application context, adding the properties configuration file depending on the
   * specified path.
   *
   * @param propertiesPath the path to the file.
   * @param forceOnFileProcessor true if the processor will be created to run "on file"
   * @return the resource loaded from the properties file.
   * @throws IOException error opening the properties file.
   */
  private static Properties initApplicationContextAndProperties(String propertiesPath,
      boolean forceOnFileProcessor) throws IOException {

    Resource resource = new ClassPathResource(propertiesPath);
    if (!resource.exists()) {
      resource = new FileSystemResource(propertiesPath);
    }
    LOGGER.trace("Innitializing Spring application context.");
    DynamicPropertyPlaceholderConfigurer.setDynamicResource(resource);
    Properties properties = PropertiesLoaderUtils.loadProperties(resource);

    // Selecting the XMLs to choose the Spring Beans to load.
    List<String> listOfClassPathXml = Lists.newArrayList();

    // Choose the DB type to use based properties file
    String dbType = (String) properties.get(AW_REPORT_MODEL_DB_TYPE);
    if (dbType != null && dbType.equals(DataBaseType.MONGODB.name())) {
      LOGGER.info("Using MONGO DB configuration properties.");
      listOfClassPathXml.add("classpath:aw-report-mongodb-beans.xml");
    } else {
      LOGGER.info("Using SQL DB configuration properties.");
      LOGGER.warn("Updating database schema, this could take a few minutes ...");
      listOfClassPathXml.add("classpath:aw-report-sql-beans.xml");
      LOGGER.warn("Done.");
    }

    // Choose the Processor type to use based properties file
    String processorType = (String) properties.get(AW_REPORT_PROCESSOR_TYPE);
    if (processorType != null && processorType.equals(ProcessorType.ONMEMORY.name())
        && !forceOnFileProcessor) {
      LOGGER.info("Using ONMEMORY Processor.");
      listOfClassPathXml.add("classpath:aw-report-processor-beans-onmemory.xml");
    } else {
      LOGGER.info("Using ONFILE Processor.");
      listOfClassPathXml.add("classpath:aw-report-processor-beans-onfile.xml");
    }

    appCtx = new ClassPathXmlApplicationContext(
        listOfClassPathXml.toArray(new String[listOfClassPathXml.size()]));

    return properties;
  }
}
