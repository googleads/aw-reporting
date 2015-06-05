// Copyright 2015 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.awreporting.alerting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ProxySelector;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

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

import com.google.api.ads.adwords.awreporting.alerting.processor.ReportProcessor;
import com.google.api.ads.adwords.awreporting.proxy.JaxWsProxySelector;
import com.google.api.ads.adwords.awreporting.util.DynamicPropertyPlaceholderConfigurer;
import com.google.api.ads.adwords.awreporting.util.FileUtil;
import com.google.api.client.util.Lists;
import com.google.api.client.util.Sets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Main class that executes the alerts processing logic delegating to the {@link ReportProcessor}.
 *
 * This class holds a Spring application context that manages the creation of all the beans needed.
 * No configuration is done in this class.
 *
 *  Credentials and properties are pulled from the ~/aw-report-alerting-sample.properties.properties file or
 * -file <file> provided.
 *
 * See README for more info.
 *
 * @author zhuoc@google.com (Zhuo Chen)
 */
public class AwReportingAlerting {
  private static final Logger LOGGER = Logger.getLogger(AwReportingAlerting.class);

  // Only support SQL DB type for now
  // Only support OnFile processor type for now
  
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

      Resource propertiesResource = new ClassPathResource(propertiesPath);
      if (!propertiesResource.exists()) {
        propertiesResource = new FileSystemResource(propertiesPath);
      }
      Properties properties = initApplicationContextAndProperties(propertiesResource);
      
      // Load alerts config from the same folder as properties file
      String alertsConfigFilename = properties.getProperty("aw.report.alerting.alerts");
      String propertiesFolder = propertiesResource.getFile().getParent();
      File alertsConfigFile = new File(propertiesFolder, alertsConfigFilename);
      LOGGER.debug("Loading alerts config file from " + alertsConfigFile.getAbsolutePath());
      JsonParser jsonParser = new JsonParser();
      JsonObject alertsConfig = jsonParser.parse(new FileReader(alertsConfigFile)).getAsJsonObject();
      LOGGER.debug("Done.");

      LOGGER.debug("Creating ReportProcessor bean...");
      ReportProcessor processor = createReportProcessor();
      LOGGER.debug("... success.");

      String mccAccountId = properties.getProperty("mccAccountId").replaceAll("-", "");

      LOGGER.info("*** Retrieving account IDs ***");
      Set<Long> accountIdsSet = Sets.newHashSet();
      if (cmdLine.hasOption("accountIdsFile")) {
        String accountsFileName = cmdLine.getOptionValue("accountIdsFile");
        addAccountsFromFile(accountIdsSet, accountsFileName);
        LOGGER.info("Accounts loaded from file.");
      }
      else {
        accountIdsSet = processor.retrieveAccountIds(mccAccountId);
      }
      
      processor.generateAlertsForMCC(mccAccountId, accountIdsSet, alertsConfig);
    } catch (IOException e) {
      errors = true;

      if (e.getMessage().contains("Insufficient Permission")) {
        LOGGER.error("Insufficient Permission error accessing the API" + e.getMessage());
      } else {
        LOGGER.error("File not found: " + e.getMessage());  
      }

    } catch (ParseException e) {
      errors = true;
      System.err.println("Error parsing the values for the command line options: " + e.getMessage());
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
    OptionBuilder.withDescription("aw-report-alerting-sample.properties file.");
    OptionBuilder.isRequired(false);
    options.addOption(OptionBuilder.create("file"));

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
    
    OptionBuilder.withArgName("debug");
    OptionBuilder.hasArg(false);
    OptionBuilder.withDescription("Will display all the debug information. "
        + "If the option 'verbose' is activated, "
        + "all the information will be displayed on the console as well");
    OptionBuilder.isRequired(false);
    options.addOption(OptionBuilder.create("debug"));

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
    formatter.printHelp(
            " java -Xmx1G -jar aw-reporting-alerting.jar -file <file>",
            "\nArguments:", options, "");
    System.out.println();
  }

  /**
   * Prints the sample properties file on the default output.
   */
  private static void printSamplePropertiesFile() {

    System.out.println("\n  File: aw-report-alerting-sample.properties example");

    ClassPathResource sampleFile = new ClassPathResource("aw-report-alerting-sample.properties");
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
    Logger.getLogger("com.google.api.ads.adwords.awreporting.alerting").addAppender(console);

    FileAppender fa = new FileAppender();
    fa.setName("FileLogger");
    fa.setFile("aw-reporting-alerting.log");
    fa.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
    fa.setThreshold(logLevel);
    fa.setAppend(true);
    fa.activateOptions();
    Logger.getLogger("com.google.api.ads.adwords.awreporting.alerting").addAppender(fa);

  }

  /**
   * Initialize the application context, adding the properties configuration file depending on the
   * specified path.
   *
   * @param propertiesPath the path to the file.
   * @return the resource loaded from the properties file.
   * @throws IOException error opening the properties file.
   */
  private static Properties initApplicationContextAndProperties(Resource propertiesResource)
      throws IOException {

    LOGGER.trace("Innitializing Spring application context.");
    DynamicPropertyPlaceholderConfigurer.setDynamicResource(propertiesResource);
    Properties properties = PropertiesLoaderUtils.loadProperties(propertiesResource);
    
    // Selecting the XMLs to choose the Spring Beans to load.
    List<String> listOfClassPathXml = Lists.newArrayList();
   
    LOGGER.info("Using SQL DB configuration properties.");
    LOGGER.warn("Updating database schema, this could take a few minutes ...");
    listOfClassPathXml.add("classpath:aw-report-sql-beans.xml");
    LOGGER.warn("Done."); 
    
    LOGGER.info("Using ONFILE Processor.");
    listOfClassPathXml.add("classpath:aw-report-alerting-processor-beans-onfile.xml");

    appCtx = new ClassPathXmlApplicationContext(listOfClassPathXml.toArray(new String[listOfClassPathXml.size()]));    
    
    return properties;
  }
}
