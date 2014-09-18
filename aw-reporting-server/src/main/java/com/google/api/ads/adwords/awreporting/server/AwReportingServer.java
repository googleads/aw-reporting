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

package com.google.api.ads.adwords.awreporting.server;

import com.google.api.ads.adwords.awreporting.processors.ReportProcessor;
import com.google.api.ads.adwords.awreporting.proxy.JaxWsProxySelector;
import com.google.api.ads.adwords.awreporting.server.rest.RestServer;

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

import java.net.ProxySelector;

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
public class AwReportingServer {

  private static final int DEFAULT_SERVER_PORT = 8081; 

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
    String propertiesPath;

    try {
      CommandLineParser parser = new BasicParser();
      CommandLine cmdLine = parser.parse(options, args);

      // Print full help and quit
      if (cmdLine.hasOption("help")) {
        printHelpMessage(options);
        System.exit(0);
      }

      setLogLevel(cmdLine);
      
      if (cmdLine.hasOption("file")) {
        propertiesPath = cmdLine.getOptionValue("file");
        System.out.println("Using properties from: " + propertiesPath);

        // Start the Rest Server
        System.out.println("Starting Rest Server at Port: " + DEFAULT_SERVER_PORT);

        RestServer restServer = new RestServer();
        restServer.initApplicationContextAndProperties(propertiesPath);
        restServer.startServer();

        System.out.println("Updating DB indexes... (may take long)");
        RestServer.getStorageHelper().createReportIndexes();
        System.out.println("DB indexes Updated.");

      } else {
        errors = true;
        System.out.println("Configuration incomplete. Missing options for command line.");
      }

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

    OptionBuilder.withArgName("file");
    OptionBuilder.hasArg(true);
    OptionBuilder.withDescription(
        "kratubackend-sample.properties file " + 
        " (./kratubackend-sample.properties by default if not provided)");
    OptionBuilder.isRequired(false);
    options.addOption(OptionBuilder.create("file"));

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
    formatter.printHelp("java -Xmx4G -jar aw-reporting-server.jar \n", options);
    System.out.println();
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
