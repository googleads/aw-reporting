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

import com.google.api.ads.adwords.awreporting.model.csv.AwReportCsvReader;
import com.google.api.ads.adwords.awreporting.model.csv.CsvParserIterator;
import com.google.api.ads.adwords.awreporting.model.csv.CsvReportParsingException;
import com.google.api.ads.adwords.awreporting.model.csv.ModifiedCsvToBean;
import com.google.api.ads.adwords.awreporting.model.csv.ReportEntityMappingStrategy;
import com.google.api.ads.adwords.awreporting.model.entities.DateRangeAndType;
import com.google.api.ads.adwords.awreporting.model.entities.Report;
import com.google.api.ads.adwords.awreporting.model.persistence.EntityPersister;
import com.google.api.ads.adwords.awreporting.model.util.StringsUtil;
import com.google.api.ads.adwords.awreporting.util.AdWordsServicesUtil;
import com.google.api.ads.adwords.lib.client.AdWordsSession.ImmutableAdWordsSession;
import com.google.api.ads.adwords.lib.jaxb.v201809.ReportDefinition;
import com.google.api.ads.adwords.lib.utils.ReportDownloadResponse;
import com.google.api.ads.adwords.lib.utils.ReportDownloadResponseException;
import com.google.api.ads.adwords.lib.utils.ReportException;
import com.google.api.ads.adwords.lib.utils.v201809.ReportDownloaderInterface;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.GZIPInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This {@link Runnable} implements the core logic to parse the rows in the CSV file for the report
 * type, and persists the beans into the database.
 *
 * @param <R> type of sub Report.
 */
public class StreamingRunnableProcessor<R extends Report> implements Runnable {

  private static final Logger logger = LoggerFactory.getLogger(StreamingRunnableProcessor.class);

  private final String topCustomerId;
  private final ImmutableAdWordsSession session;
  private final ReportDefinition reportDefinition;
  private final DateRangeAndType dateRangeAndType;
  private final ModifiedCsvToBean<R> csvToBean;
  private final ReportEntityMappingStrategy<R> mappingStrategy;
  private final EntityPersister entityPersister;
  private final int reportRowsSetSize;

  /**
   * Constructor for {@code Runnable} to download reports from AdWords API.
   *
   * @param topCustomerId the top customer account id.
   * @param session AdWords session used for downloading report stream.
   * @param reportDefinition {@code ReportDefinition} to define report parameters.
   * @param csvToBean the {@code CsvToBean}.
   * @param mappingStrategy the mapping strategy to convert CSV files into Java beans.
   * @param dateRangeAndType the date range and type.
   * @param entityPersister the bean to persist report to database.
   * @param reportRowsSetSize the size of the set parsed that will be persisted to the database.
   */
  public StreamingRunnableProcessor(
      String topCustomerId,
      ImmutableAdWordsSession session,
      ReportDefinition reportDefinition,
      DateRangeAndType dateRangeAndType,
      ModifiedCsvToBean<R> csvToBean,
      ReportEntityMappingStrategy<R> mappingStrategy,
      EntityPersister entityPersister,
      int reportRowsSetSize) {
    this.topCustomerId = Preconditions.checkNotNull(topCustomerId, "topCustomerId cannot be null");
    
    this.session = Preconditions.checkNotNull(session, "session cannot be null.");
    this.reportDefinition =
        Preconditions.checkNotNull(reportDefinition, "reportDefinition cannot be null");
    this.dateRangeAndType =
        Preconditions.checkNotNull(dateRangeAndType, "dateRangeAndType cannot be null");
    
    this.csvToBean = Preconditions.checkNotNull(csvToBean, "csvToBean cannot be null");
    this.mappingStrategy =
        Preconditions.checkNotNull(mappingStrategy, "mappingStrategy cannot be null");
    this.entityPersister =
        Preconditions.checkNotNull(entityPersister, "entityPersister cannot be null");
    this.reportRowsSetSize = reportRowsSetSize;
    Preconditions.checkArgument(reportRowsSetSize > 0, "reportRowsSetSize must be > 0");
  }

  /**
   * Executes the API call to download the report that was given when this {@code Runnable} was
   * created.
   *
   * <p>The download blocks this thread until it is finished, does the file copying and stream
   * parsing.
   *
   * <p>There is also a retry logic implemented by this method, where the times retried depends on
   * the value given in the constructor.
   *
   * @see java.lang.Runnable#run()
   */
  @Override
  public void run() {
    // Report Input Streams comes GZipped
    try (GZIPInputStream gZIPInputStream = new GZIPInputStream(getReportInputStream())) {
      AwReportCsvReader csvReader = createCsvReader(gZIPInputStream);
      parseCsv(csvReader);
    } catch (IOException | CsvReportParsingException e) {
      logger.error("Error processing report for account: " + session.getClientCustomerId(), e);
    } catch (RuntimeException e) {
      logger.error(
          "Runtime error processing report for account: " + session.getClientCustomerId(), e);
    }
  }

  /**
   * Creates the proper {@link AwReportCsvReader} to parse the AW reports.
   *
   * @param inputStream the CSV file.
   * @return the {@code CSVReader}
   * @throws IOException in case the file is failed to parse as CSV.
   */
  private AwReportCsvReader createCsvReader(InputStream inputStream) throws IOException {
    InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    return new AwReportCsvReader(reader, false, false);
  }

  /**
   * Downloads the file from the API into an InputStream.
   *
   * @return the InputStream from the online report, null if httpStatus is not {@code HTTP_OK}.
   */
  private InputStream getReportInputStream() {
    ReportDownloaderInterface reportDownloader =
        AdWordsServicesUtil.getUtility(session, ReportDownloaderInterface.class);
    
    InputStream result = null;
    try {
      ReportDownloadResponse reportDownloadResponse =
          reportDownloader.downloadReport(reportDefinition);
      result = reportDownloadResponse.getInputStream();
    } catch (ReportException | ReportDownloadResponseException e) {
      logger.error(
          "Failed to download report stream for {} with account {}.",
          reportDefinition.getReportType(),
          session.getClientCustomerId(),
          e);
    }

    return result;
  }
  
  /**
   * Parse the CSV content and persist data.
   *
   * @param csvReader the the {@code AwReportCsvReader} object to parse
   * @throws CsvReportParsingException error on parsing the from the csvReader
   */
  private void parseCsv(AwReportCsvReader csvReader) throws CsvReportParsingException {
    logger.debug("Starting parse of report rows...");
    CsvParserIterator<R> reportRowsList = csvToBean.lazyParse(mappingStrategy, csvReader);
    logger.debug("Successfully finished parse of report rows.");

    logger.debug("Starting report persistence...");
    List<R> reportBuffer = Lists.newArrayList();
    while (reportRowsList.hasNext()) {
      R report = reportRowsList.next();
      report.setCustomerId(StringsUtil.parseCustomerId(session.getClientCustomerId()));
      report.setTopCustomerId(StringsUtil.parseCustomerId(topCustomerId));
      report.setDateRangeType(dateRangeAndType.getTypeStr());
      report.setStartDate(dateRangeAndType.getStartDateStr());
      report.setEndDate(dateRangeAndType.getEndDateStr());
      report.setRowId();
      reportBuffer.add(report);

      if (reportBuffer.size() >= reportRowsSetSize) {
        entityPersister.persistReportEntities(reportBuffer);
        reportBuffer.clear();
      }
    }
    if (reportBuffer.size() > 0) {
      entityPersister.persistReportEntities(reportBuffer);
    }
    logger.debug("Successfully finished report persistence.");
  }
}
