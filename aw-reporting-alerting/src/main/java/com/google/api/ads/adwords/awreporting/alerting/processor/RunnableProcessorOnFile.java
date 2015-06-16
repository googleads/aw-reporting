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

package com.google.api.ads.adwords.awreporting.alerting.processor;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;

import com.google.api.ads.adwords.awreporting.alerting.report.ReportData;
import com.google.api.ads.adwords.jaxws.v201502.cm.ReportDefinitionReportType;

/**
 * This {@link Runnable} implements the core logic to download the report file
 * from AdWords API (using AWQL), and apply alert rules on them.
 *
 * The {@link List} passed to this runner is considered to be synchronized and thread safe.
 * This class has no blocking logic when adding elements to the list.
 *
 * Parse the CSV file for the report type into ReportData, and apply alert rules on it.
 *
 * @author zhuoc@google.com (Zhuo Chen)
 */
public class RunnableProcessorOnFile implements Runnable {

  private static final Logger LOGGER = Logger.getLogger(RunnableProcessorOnFile.class);

  private CountDownLatch latch;

  private File file;
  private Map<String, String> filedsMapping;
  private String alertName;
  private ReportDefinitionReportType reportType;
  private AlertRulesProcessor rulesProcessor;
  private String alertMessage;
  private List<ReportData> outputReports;

  private Exception error = null;

  /**
   * C'tor.
   *
   * @param file the CSV file of the report type.
   * @param filedsMapping the fields mapping for this report type.
   * @param alertName the name of current alert.
   * @param reportType the type of current report.
   * @param rulesProcessor the processor of current alert rules.
   * @param alertMessage the current alert message template.
   * @param outputReports the thread-safe list of generated ReportData
   */
  public RunnableProcessorOnFile(File file,
      Map<String, String> filedsMapping,
      String alertName,
      ReportDefinitionReportType reportType,
      AlertRulesProcessor rulesProcessor,
      String alertMessage,
      List<ReportData> outputReports) {
    this.file = file;
    this.filedsMapping = filedsMapping;
    this.alertName = alertName;
    this.reportType = reportType;
    this.rulesProcessor = rulesProcessor;
    this.alertMessage = alertMessage;
    this.outputReports = outputReports;
  }

  /**
   * Executes the API call to process the report that was given when this {@code Runnable} was
   * created, and apply alert rules on it.
   *
   * The processing blocks this thread until it is finished, and also does the alert rules execution.
   *
   * @see java.lang.Runnable#run()
   */
  @Override
  public void run() {

    try {
      LOGGER.debug("Creating CsvReader for file: " + file.getAbsolutePath());
      CSVReader csvReader = new CSVReader(new FileReader(file));

      // Parse the CSV file into report
      LOGGER.debug("Starting processing rules of report...");
      ReportData report = new ReportData(csvReader.readNext(), csvReader.readAll(), filedsMapping, alertName, reportType);
      
      // Apply alert rules on the report
      if (null != rulesProcessor) {
        rulesProcessor.processReport(report);
      }
      
      // Apply alert message template on each report entry
      AlertMessageProcessor messageProcessor = new AlertMessageProcessor(alertMessage);
      messageProcessor.processReport(report);
      
      // Add the report into result list (thread-safe)
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

  /**
   * @return error during processing this report
   */
  public Exception getError() {
    return error;
  }
}
