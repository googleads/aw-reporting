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

package com.google.api.ads.adwords.awreporting.processors.stream;

import com.google.api.ads.adwords.awreporting.ReportProcessingException;
import com.google.api.ads.adwords.awreporting.authentication.Authenticator;
import com.google.api.ads.adwords.awreporting.downloader.DownloadSetting;
import com.google.api.ads.adwords.awreporting.model.csv.ModifiedCsvToBean;
import com.google.api.ads.adwords.awreporting.model.csv.ReportEntityMappingStrategy;
import com.google.api.ads.adwords.awreporting.model.entities.DateRangeAndType;
import com.google.api.ads.adwords.awreporting.model.entities.Report;
import com.google.api.ads.adwords.awreporting.processors.ReportProcessor;
import com.google.api.ads.adwords.awreporting.util.AdWordsSessionUtil;
import com.google.api.ads.adwords.lib.client.AdWordsSession.ImmutableAdWordsSession;
import com.google.api.ads.adwords.lib.jaxb.v201708.ReportDefinition;
import com.google.api.ads.adwords.lib.jaxb.v201708.ReportDefinitionReportType;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
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
@Qualifier("streamingReportProcessor")
public class StreamingReportProcessor extends ReportProcessor {

  private static final Logger logger = LoggerFactory.getLogger(StreamingReportProcessor.class);
  
  private DownloadSetting downloadSetting;

  /**
   * Constructor.
   *
   * @param reportRowsSetSize the size of the set parsed before persisting to the database.
   * @param numberOfReportProcessors the number of threads used to process reports.
   * @throws ReportProcessingException
   */
  @Autowired
  public StreamingReportProcessor(
      @Value(value = "${aw.report.processor.rows.size}") Integer reportRowsSetSize,
      @Value(value = "${aw.report.processor.threads}") Integer numberOfReportProcessors,
      Authenticator authenticator)
      throws ReportProcessingException {
    super(reportRowsSetSize, numberOfReportProcessors, authenticator);
  }

  /**
   * Downloads all the files from the API and process all the rows, saving the data to the
   * configured database.
   *
   * @param topCustomerId the top customer account id.
   * @param reportType the report type.
   * @param dateRangeAndType the date range and type.
   * @param accountIdList the account IDs.
   * @param properties the properties resource.
   * @throws ReportProcessingException
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
    logger.info(" Processing reports...");

    ReportDefinition reportDefinition =
        getReportDefinition(reportType, dateRangeAndType, reportDefinitionKey, properties);

    Class<? extends Report> reportBeanClass =
        this.csvReportEntitiesMapping.getReportBeanClass(reportType);

    downloadAndProcessReports(
        topCustomerId, reportBeanClass, accountIdList, reportDefinition, dateRangeAndType);
  }

  /**
   * For each account, download report stream, map to a {@code Report}, and then persist to
   * configured database.
   *
   * @param topCustomerId the top customer account id.
   * @param reportBeanClass report to download.
   * @param accountIdList the account IDs.
   * @param reportDefinition the report definition.
   * @param dateRangeAndType the date range and type.
   * @throws ReportProcessingException
   */
  private <R extends Report> void downloadAndProcessReports(
      String topCustomerId,
      Class<R> reportBeanClass,
      Set<Long> accountIdList,
      ReportDefinition reportDefinition,
      DateRangeAndType dateRangeAndType)
      throws ReportProcessingException {
    logger.info("Processing report streams...");
    Stopwatch stopwatch = Stopwatch.createStarted();
    
    ExecutorService executorService = Executors.newFixedThreadPool(numberOfReportProcessors);
    List<Callable<Object>> taskJobs = Lists.newArrayList();

    for (Long accountId : accountIdList) {
      try {
        logger.debug("Processing account: " + accountId);

        // We need to create a csvToBean and mappingStrategy for each thread
        ModifiedCsvToBean<R> csvToBean = new ModifiedCsvToBean<R>();
        ReportEntityMappingStrategy<R> mappingStrategy =
            new ReportEntityMappingStrategy<R>(reportBeanClass);

        ImmutableAdWordsSession accountSession =
            AdWordsSessionUtil.buildImmutableSessionForCid(sessionBuilder, accountId);

        StreamingRunnableProcessor<R> runnableProcessor =
            new StreamingRunnableProcessor<R>(
                topCustomerId,
                accountSession,
                reportDefinition,
                dateRangeAndType,
                csvToBean,
                mappingStrategy,
                persister,
                reportRowsSetSize);
        taskJobs.add(Executors.callable(runnableProcessor));
      } catch (ValidationException e) {
        logger.warn("Ignoring account (Error when processing): " + accountId, e);
      }
    }

    try {
      executorService.invokeAll(taskJobs);
    } catch (InterruptedException e) {
      throw new ReportProcessingException(
          "StreamingReportProcessor encounters InterruptedException.", e);
    }
    
    executorService.shutdown();
    stopwatch.stop();
    logger.info(
        "*** Finished processing all reports in "
            + stopwatch.elapsed(TimeUnit.SECONDS)
            + " seconds ***\n");
  }

  @Autowired
  public void setDownloaderSetting(DownloadSetting downloadSetting) {
    this.downloadSetting = downloadSetting;
    this.downloadSetting.applyToRateLimiter();
  }
}
