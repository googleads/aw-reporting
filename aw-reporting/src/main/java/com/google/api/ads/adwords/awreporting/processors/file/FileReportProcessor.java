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

package com.google.api.ads.adwords.awreporting.processors.file;

import com.google.api.ads.adwords.awreporting.ReportProcessingException;
import com.google.api.ads.adwords.awreporting.authentication.Authenticator;
import com.google.api.ads.adwords.awreporting.downloader.DownloadSetting;
import com.google.api.ads.adwords.awreporting.downloader.MultipleClientReportDownloader;
import com.google.api.ads.adwords.awreporting.model.csv.ModifiedCsvToBean;
import com.google.api.ads.adwords.awreporting.model.csv.ReportEntityMappingStrategy;
import com.google.api.ads.adwords.awreporting.model.entities.DateRangeAndType;
import com.google.api.ads.adwords.awreporting.model.entities.Report;
import com.google.api.ads.adwords.awreporting.processors.ReportProcessor;
import com.google.api.ads.adwords.lib.jaxb.v201806.ReportDefinition;
import com.google.api.ads.adwords.lib.jaxb.v201806.ReportDefinitionReportType;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Main reporting processor responsible for downloading and saving the files to the file system. The
 * persistence of the parsed beans is delegated to the configured persister.
 */
@Component
@Qualifier("fileReportProcessor")
public class FileReportProcessor extends ReportProcessor {

  private static final Logger logger = LoggerFactory.getLogger(FileReportProcessor.class);

  private DownloadSetting downloadSetting;

  /**
   * Constructor.
   *
   * @param reportRowsSetSize the size of the set parsed before persisting to the database.
   * @param numberOfReportProcessors the number of threads used to process reports.
   * @throws ReportProcessingException
   */
  @Autowired
  public FileReportProcessor(
      @Value(value = "${aw.report.processor.rows.size}") Integer reportRowsSetSize,
      @Value(value = "${aw.report.processor.threads}") Integer numberOfReportProcessors,
      Authenticator authenticator)
      throws ReportProcessingException {
    super(reportRowsSetSize, numberOfReportProcessors, authenticator);
  }

  /**
   * Downloads all the files from the API using {@code MultipleClientReportDownloader} and stores on
   * local file system. The local files are processed using {@code FileRunnableProcessor} to read
   * all the rows in each file and persist the data to the configured database.
   *
   * @param topCustomerId the top customer account id
   * @param reportType the report type
   * @param dateRangeAndType the date range and type
   * @param accountIdList the account IDs
   * @param properties the properties resource
   */
  @Override
  protected void downloadAndProcess(
      String topCustomerId,
      ReportDefinitionReportType reportType,
      DateRangeAndType dateRangeAndType,
      Set<Long> accountIdList,
      String reportDefinitionKey,
      Properties properties)
      throws ReportProcessingException {

    // Download Reports to local files and Generate Report objects
    logger.info("\n\n ** Generating: " + reportType.name() + " **");
    logger.info(" Downloading reports...");
    Collection<File> localFiles = Lists.newArrayList();
    ReportDefinition reportDefinition =
        getReportDefinition(reportType, dateRangeAndType, reportDefinitionKey, properties);
    MultipleClientReportDownloader multipleClientReportDownloader =
        new MultipleClientReportDownloader(downloadSetting.getNumThreads());
    localFiles =
        multipleClientReportDownloader.downloadReports(
            sessionBuilder, reportDefinition, accountIdList);
    processLocalFiles(topCustomerId, reportType, localFiles, dateRangeAndType);
    deleteTemporaryFiles(localFiles, reportType);
  }

  /**
   * Each of the files downloaded is processed using {@code FileRunnableProcessor} to read the CSV,
   * map to a {@code Report}, and then persist to configured database.
   *
   * @param topCustomerId the top customer account id.
   * @param reportBeanClass report to download.
   * @param localFiles local report files.
   * @param filesDownloadedByAPI whether the report files are downloaded by AWAPI (no
   *     header/summary) or provided by user (via csvReportFile option).
   * @param dateRangeAndType the date range and type.
   */
  private <R extends Report> void processFiles(
      String topCustomerId,
      Class<R> reportBeanClass,
      Collection<File> localFiles,
      boolean filesDownloadedByAPI,
      DateRangeAndType dateRangeAndType)
      throws ReportProcessingException {
    logger.info("Processing report files...");
    Stopwatch stopwatch = Stopwatch.createStarted();

    ExecutorService executorService = Executors.newFixedThreadPool(numberOfReportProcessors);
    List<Callable<Object>> taskJobs = Lists.newArrayList();
    
    for (File file : localFiles) {
      logger.trace(".");
      // We need to create a csvToBean and mappingStrategy for each thread
      ModifiedCsvToBean<R> csvToBean = new ModifiedCsvToBean<R>();
        ReportEntityMappingStrategy<R> mappingStrategy =
            new ReportEntityMappingStrategy<R>(reportBeanClass);

        logger.debug("Parsing file: " + file.getAbsolutePath());
        FileRunnableProcessor<R> runnableProcessor =
            new FileRunnableProcessor<R>(
                topCustomerId,
                file,
                filesDownloadedByAPI,
                csvToBean,
                mappingStrategy,
                dateRangeAndType,
                persister,
                reportRowsSetSize);
      taskJobs.add(Executors.callable(runnableProcessor));
    }
    
    try {
      executorService.invokeAll(taskJobs);
    } catch (InterruptedException e) {
      throw new ReportProcessingException(
          "FileReportProcessor encounters InterruptedException.", e);
    }

    executorService.shutdown();
    stopwatch.stop();
    logger.info(
        "*** Finished processing all reports in "
            + stopwatch.elapsed(TimeUnit.SECONDS)
            + " seconds ***\n");
  }

  /**
   * Process the local files delegating the call to the concrete implementation.
   *
   * @param topCustomerId the top customer account id.
   * @param reportType the report type.
   * @param localFiles the local files.
   * @param dateRangeAndType the date range and type.
   * @throws ReportProcessingException
   */
  private void processLocalFiles(
      String topCustomerId,
      ReportDefinitionReportType reportType,
      Collection<File> localFiles,
      DateRangeAndType dateRangeAndType)
      throws ReportProcessingException {
    Class<? extends Report> reportBeanClass =
        csvReportEntitiesMapping.getReportBeanClass(reportType);
    processFiles(topCustomerId, reportBeanClass, localFiles, true, dateRangeAndType);
  }

  /**
   * Process the input files delegating the call to the concrete implementation.
   *
   * @param topCustomerId the top customer account id.
   * @param reportTypeName the report type name as String.
   * @param localFiles the local files.
   * @param dateRangeAndType the date range and type.
   * @throws ReportProcessingException
   */
  public void processInputFiles(
      String topCustomerId,
      String reportTypeName,
      Collection<File> localFiles,
      DateRangeAndType dateRangeAndType)
      throws ReportProcessingException {

    Class<? extends Report> reportBeanClass;
    try {
      ReportDefinitionReportType reportType = ReportDefinitionReportType.valueOf(reportTypeName);
      reportBeanClass = csvReportEntitiesMapping.getReportBeanClass(reportType);
    } catch (IllegalArgumentException e) {
      reportBeanClass = csvReportEntitiesMapping.getExperimentalReportBeanClass(reportTypeName);
    }
    if (reportBeanClass == null) {
      throw new IllegalArgumentException("Report type not found: " + reportTypeName);
    }

    processFiles(topCustomerId, reportBeanClass, localFiles, false, dateRangeAndType);
  }

  /**
   * Deletes the local files used as temporary containers.
   *
   * @param localFiles the list of local unzipped files.
   * @param reportType the report type.
   */
  private void deleteTemporaryFiles(
      Collection<File> localFiles, ReportDefinitionReportType reportType) {

    // Delete temporary report files
    logger.info("\n Deleting temporary report files after Parsing...");
    for (File file : localFiles) {
      file.delete();
      logger.trace(".");
    }
    logger.info("\n ** Finished: " + reportType.name() + " **");
  }
  
  @Autowired
  public void setDownloaderSetting(DownloadSetting downloadSetting) {
    this.downloadSetting = downloadSetting;
    this.downloadSetting.applyToRateLimiter();
  }
}
