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

import com.google.api.ads.adwords.awreporting.alerting.downloader.MultipleClientReportDownloader;
import com.google.api.ads.adwords.awreporting.alerting.downloader.ReportDefinitionDownloader;
import com.google.api.ads.adwords.awreporting.alerting.processor.ReportProcessor;
import com.google.api.ads.adwords.awreporting.alerting.report.ReportData;
import com.google.api.ads.adwords.awreporting.alerting.report.ReportQuery;
import com.google.api.ads.adwords.awreporting.alerting.util.ConfigTags;
import com.google.api.ads.adwords.awreporting.util.AdWordsSessionBuilderSynchronizer;
import com.google.api.ads.adwords.jaxws.v201502.cm.ApiException_Exception;
import com.google.api.ads.adwords.jaxws.v201502.cm.ReportDefinitionReportType;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.client.reporting.ReportingConfiguration;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Main reporting processor responsible for downloading report files to the file system, parsing them
 * into ReportData objects, applying alert rules and running alert actions.
 *
 * @author zhuoc@google.com (Zhuo Chen)
 */
@Component
@Qualifier("reportProcessorOnFile")
public class ReportProcessorOnFile extends ReportProcessor {

  private static final Logger LOGGER = Logger.getLogger(ReportProcessorOnFile.class);

  private static final DateFormat TIMESTAMPFORMAT = new SimpleDateFormat("yyyy-MM-dd-HH_mm");

  private MultipleClientReportDownloader multipleClientReportDownloader;
  
  private ReportDefinitionDownloader reportDefinitionDownloader;

  /**
   * Constructor.
   *
   * @param numberOfReportProcessors the number of numberOfReportProcessors threads to be run
   */
  @Autowired
  public ReportProcessorOnFile(
      @Value(value = "${aw.report.processor.threads:}") Integer numberOfReportProcessors) {

    if (numberOfReportProcessors != null && numberOfReportProcessors > 0) {
      this.numberOfReportProcessors = numberOfReportProcessors;
    }
  }

  /**
   * Caches the accounts into a temporary file.
   *
   * @param accountIdsSet the set with all the accounts
   */
  @Override
  protected void cacheAccounts(Set<Long> accountIdsSet) {

    DateTime now = new DateTime();
    String nowFormat = TIMESTAMPFORMAT.format(now.toDate());

    try {
      File tempFile = File.createTempFile(nowFormat + "-accounts-ids", ".txt");
      LOGGER.info("Cache file created for accounts: " + tempFile.getAbsolutePath());

      FileWriter writer = new FileWriter(tempFile);
      for (Long accountId : accountIdsSet) {
        writer.write(Long.toString(accountId) + "\n");
      }
      writer.close();
      LOGGER.info("All account IDs added to cache file.");

    } catch (IOException e) {
      LOGGER.error("Could not create temporary file with the accounts. Accounts won't be cached.");
      e.printStackTrace();
    }
  }
  
  /**
   * Generate all the alerts for the given account IDs under the MCC.
   *
   * @param mccAccountId the MCC account ID.
   * @param accountIdsSet the account IDs.
   * @param alertsConfig the JSON config of the alerts.
   * @throws OAuthException 
   * @throws ApiException_Exception 
   * @throws ValidationException 
   */
  @Override
  public void generateAlertsForMCC(String mccAccountId,
      Set<Long>accountIds,
      JsonObject alertsConfig) throws OAuthException, ValidationException, ApiException_Exception {
    
    Stopwatch stopwatch = Stopwatch.createStarted();

    // For easy processing, skip report header and summary (but keep column names).
    ReportingConfiguration reportingConfig = new ReportingConfiguration.Builder()
        .skipReportHeader(true)
        .skipReportSummary(true)
        .build();
    AdWordsSession.Builder builder = authenticator.authenticate(mccAccountId, false)
        .withReportingConfiguration(reportingConfig);
    AdWordsSessionBuilderSynchronizer sessionBuilder = new AdWordsSessionBuilderSynchronizer(builder);
    
    // Initialize reportDefinitionDownloader
    initReportDefinitionDownloader(builder);
    
    int count = 0;
    for (JsonElement alertConfig : alertsConfig.getAsJsonArray(ConfigTags.ALERTS)) {
      count++;
      this.processAlertForMCC(mccAccountId, accountIds, sessionBuilder, alertConfig.getAsJsonObject(), count);
    }
    
    this.multipleClientReportDownloader.finalizeExecutorService();

    stopwatch.stop();
    LOGGER.info("*** Finished all processing in "
        + (stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000) + " seconds ***\n");
  }
  
  /**
   * Process one alert for the given account IDs under the MCC.
   *
   * @param mccAccountId the MCC account ID.
   * @param accountIdsSet the account IDs.
   * @param sessionBuilder the session builder to build AdWords session for each account.
   * @param alertsConfig the JSON config of one alert.
   * @param count the sequence number of current alert.
   * @throws ApiException_Exception 
   */
  protected void processAlertForMCC(String mccAccountId,
      Set<Long> accountIds,
      AdWordsSessionBuilderSynchronizer sessionBuilder,
      JsonObject alertConfig,
      int count) throws ApiException_Exception  {
    String alertName = alertConfig.get(ConfigTags.ALERT_NAME).getAsString();
    LOGGER.info("*** Generating alert #" + count + "(name: \"" + alertName + "\") for " + accountIds.size() + " accounts ***");

    JsonObject reportQueryConfig = alertConfig.getAsJsonObject(ConfigTags.REPORT_QUERY);
    JsonArray rulesConfig = alertConfig.getAsJsonArray(ConfigTags.RULES);  // optional
    JsonArray actionsConfig = alertConfig.getAsJsonArray(ConfigTags.ACTIONS);
    String alertMessage = alertConfig.get(ConfigTags.ALERT_MESSAGE).getAsString();
   
    // Get the fields mapping of this report type
    ReportQuery reportQuery = new ReportQuery(reportQueryConfig);
    ReportDefinitionReportType reportType = ReportDefinitionReportType.valueOf(reportQuery.getReportType());
    Map<String, String> fieldsMapping = reportDefinitionDownloader.getFieldsMapping(reportType);
    
    // Generate AWQL report query and download reports for all accounts under MCC.
    Collection<File> files = this.downloadReports(mccAccountId, accountIds, sessionBuilder, reportQuery);

    // Construct a shared AlertRulesProcessor to process rules in multiple download threads
    // Note that alert rules are optional!
    AlertRulesProcessor rulesProcessor = null;
    if (null != rulesConfig) {
      rulesProcessor = new AlertRulesProcessor(rulesConfig);
    }
    
    // Process the downloaded report files.
    processFiles(files, fieldsMapping, alertName, reportType, rulesProcessor, alertMessage, actionsConfig);
    
    // Delete the temporary report files.
    this.deleteTemporaryFiles(files, reportType);
  }
  
  /**
   * Download report files for the given account IDs under the MCC.
   *
   * @param mccAccountId the MCC account ID.
   * @param accountIdsSet the account IDs.
   * @param sessionBuilder the session builder to build AdWords session for each account.
   * @param reportQuery the AWQL report query.
   */
  private Collection<File> downloadReports(String mccAccountId,
      Set<Long> accountIds,
      AdWordsSessionBuilderSynchronizer sessionBuilder,
      ReportQuery reportQuery) {
    
    // Download Reports to local files
    LOGGER.info(" Downloading " + reportQuery.getReportType() + "reports...");
    Collection<File> localFiles = Lists.newArrayList();
    try {
      localFiles = this.multipleClientReportDownloader.downloadReports(sessionBuilder, reportQuery, accountIds);
    } catch (InterruptedException e) {
      LOGGER.error(e.getMessage());
      e.printStackTrace();
    } catch (ValidationException e) {
      LOGGER.error(e.getMessage());
      e.printStackTrace();
    }
    
    return localFiles;
  }
  
  /**
   * Process report files for the given account IDs under the MCC.
   *
   * @param files the downloaded report files.
   * @param mapping the fields mapping for this report type.
   * @param alertName the name of current alert.
   * @param reportType the type of current report.
   * @param rulesProcessor the processor of current alert rules.
   * @param alertMessage the current alert message template.
   * @param actionsConfig the JSON config of current alert actions.
   */

  protected void processFiles(Collection<File> files,
      Map<String, String> fieldsMapping,
      String alertName,
      ReportDefinitionReportType reportType,
      AlertRulesProcessor rulesProcessor,
      String alertMessage,
      JsonArray actionsConfig) {

    // Processing Report Local Files
    LOGGER.info(" Processing reports...");

    Stopwatch stopwatch = Stopwatch.createStarted();

    final CountDownLatch latch = new CountDownLatch(files.size());
    ExecutorService executorService = Executors.newFixedThreadPool(numberOfReportProcessors);
    
    // Process report files in multiple threads
    final List<ReportData> reports = Collections.synchronizedList(new ArrayList<ReportData>());
    for (File file : files) {
      try {
        RunnableReportProcessorOnFile runnableProcessor = new RunnableReportProcessorOnFile(file,
            fieldsMapping,
            alertName,
            reportType,
            rulesProcessor,
            reports);
        
        runnableProcessor.setLatch(latch);
        executorService.execute(runnableProcessor);
      } catch (Exception e) {
        LOGGER.error("Ignoring file (Error when processing): " + file.getAbsolutePath());
        e.printStackTrace();
      }
    }

    try {
      latch.await();
    } catch (InterruptedException e) {
      LOGGER.error(e.getMessage());
      e.printStackTrace();
    }
    executorService.shutdown();
    
    // Apply alert message template on each report
    if (!reports.isEmpty()) {
      AlertMessageProcessor messageProcessor = new AlertMessageProcessor(alertMessage);
      LOGGER.debug("*** Reports after processing alert rules and messages:");
      int seq = 1;
      for (ReportData report : reports) {
        messageProcessor.processReport(report);
        LOGGER.debug("===== Report #" + seq++ + " =====");
        LOGGER.debug(report.toString());
      }
    }

    // Run alert actions (one action per thread) on report data, and make sure not to modify them
    AlertActionsProcessor actionsProcessor = new AlertActionsProcessor(actionsConfig);
    actionsProcessor.processReports(Collections.unmodifiableList(reports));
    
    stopwatch.stop();
    LOGGER.info("*** Finished processing all reports in "
        + (stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000) + " seconds ***\n");
  }
  
  /**
   * Deletes the local files used as temporary containers.
   *
   * @param localFiles the list of local files.
   * @param reportType the report type.
   */
  private void deleteTemporaryFiles(Collection<File> localFiles,
      ReportDefinitionReportType reportType) {

    // Delete temporary report files
    LOGGER.info("\n Deleting temporary report files after Parsing...");
    for (File file : localFiles) {
      file.delete();
    }
    LOGGER.info("** Deleted temporary reoprt files of : " + reportType.name() + " **");
  }

  /**
   * @param multipleClientReportDownloader the multipleClientReportDownloader to set
   */
  @Autowired
  public void setMultipleClientReportDownloader(
      MultipleClientReportDownloader multipleClientReportDownloader) {
    this.multipleClientReportDownloader = multipleClientReportDownloader;
  }
  
  /**
   * @param reportDefinitionDownloader the reportDefinitionDownloader to set
   */
  @Autowired
  public void setReportDefinitionDownloader(
      ReportDefinitionDownloader reportDefinitionDownloader) {
    this.reportDefinitionDownloader = reportDefinitionDownloader;
  }
  
  /**
   * Initialize reportDefinitionDownloader. Separating this method for Mockito test 
   */
  protected void initReportDefinitionDownloader(AdWordsSession.Builder builder) throws ValidationException {
    reportDefinitionDownloader.initialize(builder.build());
  }
}
