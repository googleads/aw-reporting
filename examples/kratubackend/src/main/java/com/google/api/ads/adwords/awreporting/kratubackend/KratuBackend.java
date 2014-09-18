// Copyright 2013 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.awreporting.kratubackend;

import com.google.api.ads.adwords.awreporting.AwReporting;
import com.google.api.ads.adwords.awreporting.kratubackend.restserver.KratuRestServer;
import com.google.api.ads.adwords.awreporting.processors.ReportProcessor;
import com.google.api.ads.adwords.awreporting.proxy.JaxWsProxySelector;
import com.google.api.ads.adwords.awreporting.util.FileUtil;
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
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ProxySelector;
import java.util.List;
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
 */
public class KratuBackend {

  private static final Logger logger = Logger.getLogger(KratuBackend.class);

  /**
   * Default properties file name.
   */
  private static final String CLASSPATH_AW_REPORT_MODEL_PROPERTIES_LOCATION =
      "kratubackend-sample.properties";

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
    String propertiesPath = CLASSPATH_AW_REPORT_MODEL_PROPERTIES_LOCATION;

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
      }
      System.out.println("Using properties from: " + propertiesPath);

      KratuRestServer kratuRestServer = new KratuRestServer();
      kratuRestServer.addClassPathXml("classpath:kratu-processor-beans.xml");
      kratuRestServer.initApplicationContextAndProperties(propertiesPath);

      String mccAccountId = KratuRestServer.getProperties().getProperty("mccAccountId").replaceAll("-", "");

      if (cmdLine.hasOption("startServer")) {

        // Start the Rest Server
        System.out.println("Starting Rest Server...");
        kratuRestServer.startServer();

      } else {
        if (cmdLine.hasOption("startDate") && cmdLine.hasOption("endDate")) {

          if (cmdLine.hasOption("processKratus")) {
            // Process Kratus, this process runs for the whole MCC
            // within the given dates and creates a daily Kratu per account.
            System.out.println("Starting Process Kratus...");

            // Process only the accounts at accountIdsFile
            Set<Long> accountIdsSet = Sets.newHashSet();
            if (cmdLine.hasOption("accountIdsFile")) {
              String accountsFileName = cmdLine.getOptionValue("accountIdsFile");
              System.out.println("Using accounts file: " + accountsFileName);              
              addAccountsFromFile(accountIdsSet, accountsFileName);
            }

            KratuProcessor kratuProcessor = KratuRestServer.getApplicationContext().getBean(KratuProcessor.class);
            kratuProcessor.processKratus(Long.valueOf(mccAccountId), accountIdsSet,
                cmdLine.getOptionValue("startDate"), cmdLine.getOptionValue("endDate"));
            System.exit(0);

          } else {
            // Download Reports,
            // this porcess downloads 7 report types for each account under the MCC
            System.out.println("Starting Download Reports porcess...");
            AwReporting.main(args);
            System.exit(0);

          }
        } else {
          errors = true;
          System.out.println("Configuration incomplete. Missing options for command line.");
        }
      }
    } catch (IOException e) {
      errors = true;
      System.out.println("Properties file (" + propertiesPath + ") not found: " + e.getMessage());
    } catch (ParseException e) {
      errors = true;
      System.out.println(
          "Error parsing the values for the command line options: " + e.getMessage());
    } catch (Exception e) {
      errors = true;
      System.out.println("Unexpected error: " + e.getMessage());
    }

    if (errors) {
      printHelpMessage(options);
      System.exit(1);      
    }
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

    OptionBuilder.withArgName("startServer");
    OptionBuilder.hasArg(false);
    OptionBuilder.withDescription(
        "Starts the Rest Server. No dates required");
    OptionBuilder.isRequired(false);
    options.addOption(OptionBuilder.create("startServer"));

    OptionBuilder.withArgName("processKratus");
    OptionBuilder.hasArg(false);
    OptionBuilder.withDescription(
        "Process Kratus processes the 7 reports peraccount and creates a daily Kratu");
    OptionBuilder.isRequired(false);
    options.addOption(OptionBuilder.create("processKratus"));

    OptionBuilder.withArgName("file");
    OptionBuilder.hasArg(true);
    OptionBuilder.withDescription(
        "kratubackend-sample.properties file " + 
        " (./kratubackend-sample.properties by default if not provided)");
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

    formatter.printHelp("java -Xmx4G -jar kratubackend.jar -startDate YYYYMMDD -endDate YYYYMMDD -file <file>\n"
        + "-Xmx4G -jar kratubackend.jar -processKratus -startDate YYYYMMDD -endDate YYYYMMDD -file <file>\n"
        + "-Xmx4G -jar kratubackend.jar -startServer -file <file>\n", options);

    printSamplePropertiesFile();
    System.out.println();
  }

  /**
   * Prints the sample properties file on the default output.
   */
  private static void printSamplePropertiesFile() {

    System.out.println("\n  File: kratubackend-sample.properties example");

    ClassPathResource sampleFile = new ClassPathResource(CLASSPATH_AW_REPORT_MODEL_PROPERTIES_LOCATION);
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
   * Reads the account ids from the file, and adds them to the given set.
   *
   * @param accountIdsSet the set to add the accounts
   * @param accountsFileName the file to be read
   * @throws FileNotFoundException file not found
   */
  protected static void addAccountsFromFile(Set<Long> accountIdsSet, String accountsFileName)
      throws FileNotFoundException {

    logger.info("Using accounts file: " + accountsFileName);

    List<String> linesAsStrings = FileUtil.readFileLinesAsStrings(new File(accountsFileName));

    logger.debug("Acount IDs to be queried:");
    for (String line : linesAsStrings) {

      String accountIdAsString = line.replaceAll("-", "");
      long accountId = Long.parseLong(accountIdAsString);
      accountIdsSet.add(accountId);

      logger.debug("Acount ID: " + accountId);
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
}
