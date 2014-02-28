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

package com.google.api.ads.adwords.jaxws.extensions.processors.onmemory;

import com.google.api.ads.adwords.jaxws.extensions.processors.ReportProcessor;
import com.google.api.ads.adwords.jaxws.extensions.report.model.csv.AnnotationBasedMappingStrategy;
import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.Report;
import com.google.api.ads.adwords.jaxws.extensions.report.model.util.ModifiedCsvToBean;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.jaxb.v201309.ReportDefinition;
import com.google.api.ads.adwords.lib.jaxb.v201309.ReportDefinitionDateRangeType;
import com.google.api.ads.adwords.lib.jaxb.v201309.ReportDefinitionReportType;
import com.google.common.base.Stopwatch;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import au.com.bytecode.opencsv.bean.MappingStrategy;

import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Main reporting processor responsible for downloading and saving the files to
 * the file system. The persistence of the parsed beans is delegated to the
 * configured persister.
 * 
 * @author jtoledo@google.com (Julian Toledo)
 * @author gustavomoreira@google.com (Gustavo Moreira)
 */
@Component
@Qualifier("reportProcessorOnMemory")
public class ReportProcessorOnMemory extends ReportProcessor {

  private static final Logger LOGGER = Logger.getLogger(ReportProcessorOnMemory.class);

  /**
   * Constructor.
   * 
   * @param mccAccountId
   *            the MCC account ID
   * @param developerToken
   *            the developer token
   * @param companyName
   *            the company name (optional)
   * @param clientId
   *            the OAuth2 authentication clientId
   * @param clientSecret
   *            the OAuth2 authentication clientSecret
   * @param reportRowsSetSize
   *            the size of the set parsed before send to the DB
   */
  @Autowired
  public ReportProcessorOnMemory(
      @Value("${mccAccountId}") String mccAccountId,
      @Value(value = "${aw.report.processor.rows.size:}") Integer reportRowsSetSize,
      @Value(value = "${aw.report.processor.threads:}") Integer numberOfReportProcessors) {

    this.mccAccountId = mccAccountId;

    if (reportRowsSetSize != null && reportRowsSetSize > 0) {
      this.reportRowsSetSize = reportRowsSetSize;
    }
    if (numberOfReportProcessors != null && numberOfReportProcessors > 0) {
      this.numberOfReportProcessors = numberOfReportProcessors;
    }
  }

  /**
   * Caches the accounts into a temporary file.
   * 
   * @param accountIdsSet
   *            the set with all the accounts
   */
  protected void cacheAccounts(Set<Long> accountIdsSet) {
    // TODO: Cache accounts on DB insted of File.
  }

  /**
   * Generate all the mapped reports to the given account IDs.
   * 
   * @param dateRangeType
   *            the date range type.
   * @param dateStart
   *            the starting date.
   * @param dateEnd
   *            the ending date.
   * @param accountIdsSet
   *            the account IDs.
   * @param properties
   *            the properties file
   * @throws Exception
   *             error reaching the API.
   */
  public void generateReportsForMCC(
      ReportDefinitionDateRangeType dateRangeType, String dateStart,
      String dateEnd, Set<Long> accountIdsSet, Properties properties)
          throws Exception {

    LOGGER.info("*** Retrieving account IDs ***");

    if (accountIdsSet == null || accountIdsSet.size() == 0) {
      accountIdsSet = this.retrieveAccountIds();
    } else {
      LOGGER.info("Accounts loaded from file.");
    }

    AdWordsSession.Builder builder = authenticator.authenticate(mccAccountId, false);

    LOGGER.info("*** Generating Reports for " + accountIdsSet.size()
        + " accounts ***");

    Stopwatch stopwatch = Stopwatch.createStarted();

    Set<ReportDefinitionReportType> reports = this.csvReportEntitiesMapping
        .getDefinedReports();

    // reports
    for (ReportDefinitionReportType reportType : reports) {

      if (properties.containsKey(reportType.name())) {
        this.downloadAndProcess(builder, reportType, dateRangeType,
            dateStart, dateEnd, accountIdsSet, properties);
      }
    }

    stopwatch.stop();
    LOGGER.info("*** Finished processing all reports in "
        + (stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000)
        + " seconds ***\n");
  }

  /**
   * Downloads all the files from the API and process all the rows, saving the
   * data to the configured data base.
   * 
   * @param builder
   *            the session builder.
   * @param reportType
   *            the report type.
   * @param dateRangeType
   *            the date range type.
   * @param dateStart
   *            the start date.
   * @param dateEnd
   *            the ending date.
   * @param acountIdList
   *            the account IDs.
   * @param properties
   *            the properties resource.
   */
  private <R extends Report> void downloadAndProcess(
      AdWordsSession.Builder builder,
      ReportDefinitionReportType reportType,
      ReportDefinitionDateRangeType dateRangeType, String dateStart,
      String dateEnd, Set<Long> acountIdList, Properties properties) {

    // Download Reports to local files and Generate Report objects
    LOGGER.info("\n\n ** Generating: " + reportType.name() + " **");
    LOGGER.info(" Processing reports...");

    ReportDefinition reportDefinition = getReportDefinition(reportType,
        dateRangeType, dateStart, dateEnd, properties);

    @SuppressWarnings("unchecked")
    Class<R> reportBeanClass = (Class<R>) this.csvReportEntitiesMapping
    .getReportBeanClass(reportType);

    final CountDownLatch latch = new CountDownLatch(acountIdList.size());
    ExecutorService executorService = Executors
        .newFixedThreadPool(numberOfReportProcessors);

    Stopwatch stopwatch = Stopwatch.createStarted();

    for (Long accountId : acountIdList) {
      LOGGER.trace(".");
      try {

        ModifiedCsvToBean<R> csvToBean = new ModifiedCsvToBean<R>();
        MappingStrategy<R> mappingStrategy = new AnnotationBasedMappingStrategy<R>(
            reportBeanClass);

        LOGGER.debug("Parsing account: " + accountId); 

        RunnableProcessorOnMemory<R> runnableProcesor = new RunnableProcessorOnMemory<R>(
            accountId, builder, reportDefinition, csvToBean, mappingStrategy, dateRangeType,
            dateStart, dateEnd, mccAccountId, persister,
            reportRowsSetSize);

        runnableProcesor.setLatch(latch);
        executorService.execute(runnableProcesor);

      } catch (Exception e) {
        LOGGER.error("Ignoring account (Error when processing): " + accountId);
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
}
