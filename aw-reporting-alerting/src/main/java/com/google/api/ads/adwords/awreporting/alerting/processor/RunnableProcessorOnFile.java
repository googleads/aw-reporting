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

package com.google.api.ads.adwords.awreporting.alerting.processor;

import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;

import com.google.api.ads.adwords.awreporting.alerting.report.ReportData;
import com.google.api.ads.adwords.awreporting.alerting.util.AdWordsSessionBuilderSynchronizer;
import com.google.api.ads.adwords.jaxws.v201502.cm.ReportDefinitionReportType;
import com.google.gson.JsonObject;

/**
 * This {@link Runnable} implements the core logic to download the report file
 * from the AdWords API.
 *
 * The {@link Collection}s passed to this runner are considered to be synchronized and thread safe.
 * This class has no blocking logic when adding elements to the collections.
 *
 * Also the {@link AdWordsSessionBuilderSynchronizer} is kept by the client class, and should
 * handle all the concurrent threads.
 *
 * Parse the rows in the CSV file for the report type, and persists the beans into the data base.
 *
 * @author gustavomoreira@google.com (Gustavo Moreira)
 * @author jtoledo@google.com (Julian Toledo)
 * 
 * @param <R> type of sub Report.
 */
public class RunnableProcessorOnFile implements Runnable {

  private static final Logger LOGGER = Logger.getLogger(RunnableProcessorOnFile.class);

  private CountDownLatch latch;

  private File file;
  private JsonObject ruleConfig;
  private Map<String, String> mapping;
  private String alertName;
  private ReportDefinitionReportType reportType;
  private List<ReportData> outputReports;

  private Exception error = null;

  /**
   * C'tor.
   *
   * @param file the CSV file.
   * @param csvToBean the {@code CsvToBean}
   * @param mappingStrategy
   */
  public RunnableProcessorOnFile(File file,
      JsonObject ruleConfig,
      Map<String, String> mapping,
      String alertName,
      ReportDefinitionReportType reportType,
      List<ReportData> outputReports) {
    this.file = file;
    this.ruleConfig = ruleConfig;
    this.mapping = mapping;
    this.alertName = alertName;
    this.reportType = reportType;
    this.outputReports = outputReports;
  }

  /**
   * Executes the API call to download the report that was given when this {@code Runnable} was
   * created.
   *
   *  The download blocks this thread until it is finished, and also does the file copying.
   *
   *  There is also a retry logic implemented by this method, where the times retried depends on the
   * value given in the constructor.
   *
   * @see java.lang.Runnable#run()
   */
  @Override
  public void run() {

    try {
      LOGGER.debug("Creating CsvReader for file: " + file.getAbsolutePath());
      CSVReader csvReader = new CSVReader(new FileReader(file));

      LOGGER.debug("Starting parse of report rows...");
      ReportData report = new ReportData(csvReader.readNext(), csvReader.readAll(), mapping, alertName, reportType);
      
      AlertRuleProcessor ruleProcessor = new AlertRuleProcessor(ruleConfig);
      ruleProcessor.processReport(report);
      outputReports.add(report);
      
      LOGGER.debug("... success.");
      csvReader.close();

    } catch (Exception e) {
      String errorMessage = "Error processing file: " + file.getAbsolutePath();
      error = new Exception(errorMessage, e);
      LOGGER.error(errorMessage);
      e.printStackTrace();
    } finally {
      if (this.latch != null) {
        this.latch.countDown();
      }
    }
  }

  /**
   * @param latch the latch to set
   */
  public void setLatch(CountDownLatch latch) {
    this.latch = latch;
  }

  public Exception getError() {
    return error;
  }
}
