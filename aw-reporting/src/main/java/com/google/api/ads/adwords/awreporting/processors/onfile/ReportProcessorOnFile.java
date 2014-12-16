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

package com.google.api.ads.adwords.awreporting.processors.onfile;

import com.google.api.ads.adwords.awreporting.downloader.MultipleClientReportDownloader;
import com.google.api.ads.adwords.awreporting.model.csv.AnnotationBasedMappingStrategy;
import com.google.api.ads.adwords.awreporting.model.entities.Report;
import com.google.api.ads.adwords.awreporting.model.util.ModifiedCsvToBean;
import com.google.api.ads.adwords.awreporting.processors.ReportProcessor;
import com.google.api.ads.adwords.awreporting.util.AdWordsSessionBuilderSynchronizer;
import com.google.api.ads.adwords.lib.jaxb.v201409.ReportDefinition;
import com.google.api.ads.adwords.lib.jaxb.v201409.ReportDefinitionDateRangeType;
import com.google.api.ads.adwords.lib.jaxb.v201409.ReportDefinitionReportType;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import au.com.bytecode.opencsv.bean.MappingStrategy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Main reporting processor responsible for downloading and saving the files to the file system. The
 * persistence of the parsed beans is delegated to the configured persister.
 *
 * @author jtoledo@google.com (Julian Toledo)
 * @author gustavomoreira@google.com (Gustavo Moreira)
 */
@Component
@Qualifier("reportProcessorOnFile")
public class ReportProcessorOnFile extends ReportProcessor {

  private static final Logger LOGGER = Logger.getLogger(ReportProcessorOnFile.class);

  private static final DateFormat TIMESTAMPFORMAT = new SimpleDateFormat("yyyy-MM-dd-HH_mm");

  private MultipleClientReportDownloader multipleClientReportDownloader;

  /**
   * Constructor.
   *
   * @param reportRowsSetSize the size of the set parsed before send to the DB
   * @param numberOfReportProcessors the number of numberOfReportProcessors threads to be run
   */
  @Autowired
  public ReportProcessorOnFile(
      @Value(value = "${aw.report.processor.rows.size:}") Integer reportRowsSetSize,
      @Value(value = "${aw.report.processor.threads:}") Integer numberOfReportProcessors) {

    if (reportRowsSetSize != null && reportRowsSetSize > 0) {
      this.reportRowsSetSize = reportRowsSetSize;
    }
    if (numberOfReportProcessors != null && numberOfReportProcessors > 0) {
      this.numberOfReportProcessors = numberOfReportProcessors;
    }
  }

<<<<<<< HEAD
  private <R extends Report> void processFiles(String userId,
      String mccAccountId,
=======
  private <R extends Report> void processFiles(String mccAccountId,
>>>>>>> 2f2a7486c98ac121ad93d5d5bb44c6f875fcbc1a
      Class<R> reportBeanClass,
      Collection<File> localFiles,
      ReportDefinitionDateRangeType dateRangeType,
      String dateStart,
      String dateEnd) {

    final CountDownLatch latch = new CountDownLatch(localFiles.size());
    ExecutorService executorService = Executors.newFixedThreadPool(numberOfReportProcessors);

    // Processing Report Local Files
    LOGGER.info(" Processing reports...");

    Stopwatch stopwatch = Stopwatch.createStarted();

    for (File file : localFiles) {
      LOGGER.trace(".");
      try {
<<<<<<< HEAD
        
=======

>>>>>>> 2f2a7486c98ac121ad93d5d5bb44c6f875fcbc1a
        // We need to create a csvToBean and mappingStrategy for each thread
        ModifiedCsvToBean<R> csvToBean = new ModifiedCsvToBean<R>();
        MappingStrategy<R> mappingStrategy = new AnnotationBasedMappingStrategy<R>(reportBeanClass);

        LOGGER.debug("Parsing file: " + file.getAbsolutePath());
        RunnableProcessorOnFile<R> runnableProcesor = new RunnableProcessorOnFile<R>(file,
            csvToBean,
            mappingStrategy,
            dateRangeType,
            dateStart,
            dateEnd,
            mccAccountId,
            persister,
            reportRowsSetSize);
        runnableProcesor.setLatch(latch);
        executorService.execute(runnableProcesor);

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
    stopwatch.stop();
    LOGGER.info("*** Finished processing all reports in "
        + (stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000) + " seconds ***\n");
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
   * Generate all the mapped reports to the given account IDs.
   *
   * @param dateRangeType the date range type.
   * @param dateStart the starting date.
   * @param dateEnd the ending date.
   * @param accountIdsSet the account IDs.
   * @param properties the properties file
   * @throws Exception error reaching the API.
   */
  @Override
  public void generateReportsForMCC(String userId,
      String mccAccountId,
      ReportDefinitionDateRangeType dateRangeType,
      String dateStart,
      String dateEnd,
      Set<Long> accountIdsSet,
      Properties properties,
      ReportDefinitionReportType onDemandReportType,
      List<String> reportFieldsToInclude) throws Exception {

    LOGGER.info("*** Retrieving account IDs ***");

    if (accountIdsSet == null || accountIdsSet.size() == 0) {
      accountIdsSet = this.retrieveAccountIds(userId, mccAccountId);
    } else {
      LOGGER.info("Accounts loaded from file.");
    }
<<<<<<< HEAD
    
    AdWordsSessionBuilderSynchronizer sessionBuilder =
        new AdWordsSessionBuilderSynchronizer(authenticator.authenticate(userId, mccAccountId, false));
=======

    AdWordsSessionBuilderSynchronizer sessionBuilder = new AdWordsSessionBuilderSynchronizer(
        authenticator.authenticate(userId, mccAccountId, false));
>>>>>>> 2f2a7486c98ac121ad93d5d5bb44c6f875fcbc1a

    LOGGER.info("*** Generating Reports for " + accountIdsSet.size() + " accounts ***");

    Stopwatch stopwatch = Stopwatch.createStarted();

    Set<ReportDefinitionReportType> reports = this.csvReportEntitiesMapping.getDefinedReports();

    // reports
<<<<<<< HEAD
    for (ReportDefinitionReportType reportType : reports) {
      if (properties.containsKey(reportType.name())) {
        this.downloadAndProcess(userId,
            mccAccountId,
=======
    Set<Object> propertiesKeys = properties.keySet();
    for (Object key : propertiesKeys) {

      String reportDefinitionKey = key.toString();
      ReportDefinitionReportType reportType = this.extractReportTypeFromKey(reportDefinitionKey);
      if (reportType != null && reports.contains(reportType)) {
        this.downloadAndProcess(mccAccountId,
>>>>>>> 2f2a7486c98ac121ad93d5d5bb44c6f875fcbc1a
            sessionBuilder,
            reportType,
            dateRangeType,
            dateStart,
            dateEnd,
            accountIdsSet,
<<<<<<< HEAD
=======
            reportDefinitionKey,
>>>>>>> 2f2a7486c98ac121ad93d5d5bb44c6f875fcbc1a
            properties);
      }
    }

    this.multipleClientReportDownloader.finalizeExecutorService();

    stopwatch.stop();
    LOGGER.info("*** Finished processing all reports in "
        + (stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000) + " seconds ***\n");
  }

  /**
   * Downloads all the files from the API and process all the rows, saving the data to the
   * configured data base.
   *
<<<<<<< HEAD
   * @param builder the session builder.
=======
   * @param sessionBuilder the session builder.
>>>>>>> 2f2a7486c98ac121ad93d5d5bb44c6f875fcbc1a
   * @param reportType the report type.
   * @param dateRangeType the date range type.
   * @param dateStart the start date.
   * @param dateEnd the ending date.
   * @param acountIdList the account IDs.
   * @param properties the properties resource.
   */
<<<<<<< HEAD
  private <R extends Report> void downloadAndProcess(String userId,
      String mccAccountId,
=======
  private <R extends Report> void downloadAndProcess(String mccAccountId,
>>>>>>> 2f2a7486c98ac121ad93d5d5bb44c6f875fcbc1a
      AdWordsSessionBuilderSynchronizer sessionBuilder,
      ReportDefinitionReportType reportType,
      ReportDefinitionDateRangeType dateRangeType,
      String dateStart,
      String dateEnd,
      Set<Long> acountIdList,
<<<<<<< HEAD
=======
      String reportDefinitionKey,
>>>>>>> 2f2a7486c98ac121ad93d5d5bb44c6f875fcbc1a
      Properties properties) {

    // Download Reports to local files and Generate Report objects
    LOGGER.info("\n\n ** Generating: " + reportType.name() + " **");
    LOGGER.info(" Downloading reports...");
    Collection<File> localFiles = Lists.newArrayList();
    try {

<<<<<<< HEAD
      ReportDefinition reportDefinition =
          getReportDefinition(reportType, dateRangeType, dateStart, dateEnd, properties);

      localFiles = this.multipleClientReportDownloader.downloadReports(sessionBuilder, reportDefinition,
          acountIdList);
=======
      ReportDefinition reportDefinition = getReportDefinition(reportType,
          dateRangeType,
          dateStart,
          dateEnd,
          reportDefinitionKey,
          properties);

      localFiles = this.multipleClientReportDownloader.downloadReports(sessionBuilder,
          reportDefinition, acountIdList);
>>>>>>> 2f2a7486c98ac121ad93d5d5bb44c6f875fcbc1a

    } catch (InterruptedException e) {
      LOGGER.error(e.getMessage());
      e.printStackTrace();
      return;
    } catch (ValidationException e) {
      LOGGER.error(e.getMessage());
      e.printStackTrace();
      return;
    }

<<<<<<< HEAD
    this.processLocalFiles(userId,
        mccAccountId,
        reportType,
        localFiles,
        dateStart,
        dateEnd,
        dateRangeType);
=======
    this.processLocalFiles(mccAccountId, reportType, localFiles, dateStart, dateEnd, dateRangeType);
>>>>>>> 2f2a7486c98ac121ad93d5d5bb44c6f875fcbc1a

    this.deleteTemporaryFiles(localFiles, reportType);
  }

  /**
   * Process the local files delegating the call to the concrete implementation.
   *
   * @param reportType the report type.
   * @param localFiles the local files.
   * @param dateStart the start date.
   * @param dateEnd the end date.
   * @param dateRangeType the date range type.
   */
<<<<<<< HEAD
  private <R extends Report> void processLocalFiles(String userId,
      String mccAccountId,
=======
  private <R extends Report> void processLocalFiles(String mccAccountId,
>>>>>>> 2f2a7486c98ac121ad93d5d5bb44c6f875fcbc1a
      ReportDefinitionReportType reportType,
      Collection<File> localFiles,
      String dateStart,
      String dateEnd,
      ReportDefinitionDateRangeType dateRangeType) {

    Stopwatch stopwatch = Stopwatch.createStarted();

    @SuppressWarnings("unchecked")
    Class<R> reportBeanClass =
        (Class<R>) this.csvReportEntitiesMapping.getReportBeanClass(reportType);
<<<<<<< HEAD
    this.processFiles(userId,
        mccAccountId,
        reportBeanClass,
        localFiles,
        dateRangeType,
        dateStart,
        dateEnd);
=======
    this.processFiles(mccAccountId, reportBeanClass, localFiles, dateRangeType, dateStart, dateEnd);
>>>>>>> 2f2a7486c98ac121ad93d5d5bb44c6f875fcbc1a

    stopwatch.stop();
    LOGGER.info("\n* DB Process finished in " + (stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000)
        + " seconds ***");
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
      File gUnzipFile = new File(file.getAbsolutePath() + ".gunzip");
      gUnzipFile.delete();
      file.delete();
      LOGGER.trace(".");
    }
    LOGGER.info("\n ** Finished: " + reportType.name() + " **");
  }

  /**
   * @param multipleClientReportDownloader the multipleClientReportDownloader to set
   */
  @Autowired
  public void setMultipleClientReportDownloader(
      MultipleClientReportDownloader multipleClientReportDownloader) {
    this.multipleClientReportDownloader = multipleClientReportDownloader;
  }
}
