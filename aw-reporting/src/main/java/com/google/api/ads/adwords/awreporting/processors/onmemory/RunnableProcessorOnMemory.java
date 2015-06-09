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

package com.google.api.ads.adwords.awreporting.processors.onmemory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.zip.GZIPInputStream;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.MappingStrategy;

import com.google.api.ads.adwords.awreporting.model.csv.AwReportCsvReader;
import com.google.api.ads.adwords.awreporting.model.entities.Report;
import com.google.api.ads.adwords.awreporting.model.persistence.EntityPersister;
import com.google.api.ads.adwords.awreporting.model.util.CsvParserIterator;
import com.google.api.ads.adwords.awreporting.model.util.ModifiedCsvToBean;
import com.google.api.ads.adwords.awreporting.util.AdWordsSessionBuilderSynchronizer;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinition;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionDateRangeType;
import com.google.api.ads.adwords.lib.utils.ReportDownloadResponse;
import com.google.api.ads.adwords.lib.utils.ReportDownloadResponseException;
import com.google.api.ads.adwords.lib.utils.ReportException;
import com.google.api.ads.adwords.lib.utils.v201502.ReportDownloader;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.common.collect.Lists;

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
public class RunnableProcessorOnMemory<R extends Report> implements Runnable {

  private static final Logger LOGGER = Logger.getLogger(RunnableProcessorOnMemory.class);

  private final String mccAccountId;
  private final AdWordsSession adWordsSession;
  private final Long accountId;
  private final ReportDefinition reportDefinition;
  private final ModifiedCsvToBean<R> csvToBean;
  private final MappingStrategy<R> mappingStrategy;
  private final ReportDefinitionDateRangeType dateRangeType;
  private final String dateStart;
  private final String dateEnd;
  private EntityPersister entityPersister;
  private final int reportRowsSetSize;

  private CountDownLatch latch;

  private Exception error = null;

  /**
   * C'tor.
   *
   * @param accountId
   * @param adWordsSession
   * @param reportDefinition
   * @param csvToBean the {@code CsvToBean}
   * @param mappingStrategy
   * @param dateRangeType
   * @param dateStart
   * @param dateEnd
   * @param mccAccountId
   * @param entityPersister
   * @param reportRowsSetSize
   */
  public RunnableProcessorOnMemory(final Long accountId, final AdWordsSession adWordsSession, 
      ReportDefinition reportDefinition, ModifiedCsvToBean<R> csvToBean,
      MappingStrategy<R> mappingStrategy, ReportDefinitionDateRangeType dateRangeType,
      String dateStart, String dateEnd, final String mccAccountId, EntityPersister entityPersister,
      Integer reportRowsSetSize) {
    this.accountId = accountId;
    this.adWordsSession = adWordsSession;
    this.adWordsSession.setClientCustomerId(String.valueOf(accountId));
    this.reportDefinition = reportDefinition;
    this.csvToBean = csvToBean;
    this.mappingStrategy = mappingStrategy;
    this.dateRangeType = dateRangeType;
    this.dateStart = dateStart;
    this.dateEnd = dateEnd;
    this.mccAccountId = mccAccountId;
    this.entityPersister = entityPersister;
    this.reportRowsSetSize = reportRowsSetSize;
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
      // Report Input Streams comes GZipped
      GZIPInputStream gZIPInputStream = new GZIPInputStream(getReportInputStream());
      CSVReader csvReader = this.createCsvReader(gZIPInputStream);

      LOGGER.debug("Starting parse of report rows...");
      CsvParserIterator<R> reportRowsList = csvToBean.lazyParse(mappingStrategy, csvReader);
      LOGGER.debug("... success.");

      LOGGER.debug("Starting report persistence...");
      List<R> reportBuffer = Lists.newArrayList();
      while (reportRowsList.hasNext()) {
        R report = reportRowsList.next();
        report.setAccountId(this.accountId);
        report.setTopAccountId(Long.parseLong(this.mccAccountId.replaceAll("-", "")));
        report.setDateRangeType(dateRangeType.value());
        report.setDateStart(dateStart);
        report.setDateEnd(dateEnd);
        report.setId();
        reportBuffer.add(report);

        if (reportBuffer.size() >= this.reportRowsSetSize) {
          this.entityPersister.persistReportEntities(reportBuffer);
          reportBuffer.clear();
        }
      }
      if (reportBuffer.size() > 0) {
        this.entityPersister.persistReportEntities(reportBuffer);
      }
      LOGGER.debug("... success.");
      csvReader.close();
    } catch (Exception e) {
      error = new Exception("Error processing report for account: " + this.accountId, e);
      LOGGER.error("Error processing report for account: " + this.accountId + " " + e.getMessage());
      e.printStackTrace();
    } finally {
      if (this.latch != null) {
        this.latch.countDown();
      }
    }
  }

  /**
   * Creates the proper {@link CSVReader} to parse the AW reports.
   *
   * @param inputStream the CSV file.
   * @return the {@code CSVReader}
   * @throws UnsupportedEncodingException should not happen.
   */
  private CSVReader createCsvReader(InputStream inputStream) throws UnsupportedEncodingException {
    return new AwReportCsvReader(
        new InputStreamReader(inputStream, "UTF-8"), ',', '\"', 1);
  }

  /**
   * @param latch the latch to set
   */
  public void setLatch(CountDownLatch latch) {
    this.latch = latch;
  }

  /**
   * Downloads the file from the API into an InputStream.
   *
   * @return the InputStream from the online report
   * @throws ValidationException API validation error
   * @throws ReportException error in the report
   * @throws ReportDownloadResponseException error in the response format
   */
  protected InputStream getReportInputStream()
      throws ValidationException, ReportException, ReportDownloadResponseException {

    InputStream inputStream = null;

    ReportDownloader reportDownloader = new ReportDownloader(adWordsSession);
    ReportDownloadResponse reportDownloadResponse =
        reportDownloader.downloadReport(this.reportDefinition);

    if (reportDownloadResponse.getHttpStatus() == HttpURLConnection.HTTP_OK) {
      inputStream = reportDownloadResponse.getInputStream();

    } else {
      System.out.println("getHttpStatus():" + reportDownloadResponse.getHttpStatus());
      System.out.println(
          "getHttpResponseMessage():" + reportDownloadResponse.getHttpResponseMessage());
    }
    return inputStream;
  }

  public Exception getError() {
    return error;
  }

  public ReportDefinition getReportDefinition() {
    return reportDefinition;
  }

  /**
   * @param entityPersister
   *            the entityPersister to set
   */
  public void setPersister(EntityPersister entityPersister) {
    this.entityPersister = entityPersister;
  }
}
