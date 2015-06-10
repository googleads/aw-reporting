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

package com.google.api.ads.adwords.awreporting.server.appengine.processors;

import com.google.api.ads.adwords.awreporting.server.appengine.RestServer;
import com.google.api.ads.adwords.awreporting.server.appengine.util.MccTaskCounter;
import com.google.api.ads.adwords.awreporting.util.AdWordsSessionBuilderSynchronizer;
import com.google.api.ads.adwords.awreporting.model.csv.AnnotationBasedMappingStrategy;
import com.google.api.ads.adwords.awreporting.model.csv.AwReportCsvReader;
import com.google.api.ads.adwords.awreporting.model.entities.Report;
import com.google.api.ads.adwords.awreporting.model.persistence.EntityPersister;
import com.google.api.ads.adwords.awreporting.model.util.CsvParserIterator;
import com.google.api.ads.adwords.awreporting.model.util.ModifiedCsvToBean;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinition;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionDateRangeType;
import com.google.api.ads.adwords.lib.utils.v201502.ReportDownloader;
import com.google.api.ads.adwords.lib.utils.ReportDownloadResponse;
import com.google.api.ads.adwords.lib.utils.ReportDownloadResponseException;
import com.google.api.ads.adwords.lib.utils.ReportException;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.appengine.api.taskqueue.DeferredTask;
import com.google.common.collect.Lists;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.MappingStrategy;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

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

public class TaskProcessorOnMemory<R extends Report> implements DeferredTask {

  private static final long serialVersionUID = 1L;
  private static final Logger LOGGER = Logger.getLogger(TaskProcessorOnMemory.class.getName());

  private ReportDefinitionDateRangeType dateRangeType;
  private String dateStart;
  private String dateEnd;
  private String mccAccountId;
  private int reportRowsSetSize;

  private Long accountId;
  private ReportDefinition reportDefinition;
  private Class<R> reportBeanClass;
  
  private EntityPersister entityPersister = RestServer.getPersister();

  /**
   * C'tor.
   *
   * @param file the CSV file.
   * @param csvToBean the {@code CsvToBean}
   * @param mappingStrategy
   * @throws IOException 
   * @throws OAuthException 
   * @throws ValidationException 
   */
  public TaskProcessorOnMemory(Long accountId, 
      ReportDefinition reportDefinition, ReportDefinitionDateRangeType dateRangeType,
      String dateStart, String dateEnd, String mccAccountId, Integer reportRowsSetSize,
      Class<R> reportBeanClass) throws OAuthException, IOException, ValidationException {
    this.accountId = accountId;
    this.reportDefinition = reportDefinition;
    this.dateRangeType = dateRangeType;
    this.dateStart = dateStart;
    this.dateEnd = dateEnd;
    this.mccAccountId = mccAccountId;
    this.reportRowsSetSize = reportRowsSetSize;
    this.reportBeanClass = reportBeanClass;
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

      ModifiedCsvToBean<R> csvToBean = new ModifiedCsvToBean<R>();
      MappingStrategy<R> mappingStrategy = new AnnotationBasedMappingStrategy<R>(
          reportBeanClass);

      LOGGER.info("Starting parse of report rows... for account " + this.accountId);
      CsvParserIterator<R> reportRowsList = csvToBean.lazyParse(mappingStrategy, csvReader);

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
          entityPersister.persistReportEntities(reportBuffer);
          reportBuffer.clear();
        }
      }
      if (reportBuffer.size() > 0) {
        entityPersister.persistReportEntities(reportBuffer);
      }
      LOGGER.info("... success.");
      csvReader.close();

    } catch (IOException e) {
      LOGGER.severe("Error on report for account: " + this.accountId + " " + e.getMessage());
      e.printStackTrace();
    } catch (ValidationException e) {
      LOGGER.severe("Error on report for account: " + this.accountId + " " + e.getMessage());
      e.printStackTrace();
    } catch (ReportException e) {
      LOGGER.severe("Error on report for account: " + this.accountId + " " + e.getMessage());
      e.printStackTrace();
    } catch (ReportDownloadResponseException e) {
      LOGGER.severe("Error on report for account: " + this.accountId + " " + e.getMessage());
      e.printStackTrace();
    } catch (OAuthException e) {
      LOGGER.severe("Error on report for account: " + this.accountId + " " + e.getMessage());
      e.printStackTrace();
    } finally {

      int tasks = MccTaskCounter.decreasePendingProcessTasks(Long.valueOf(mccAccountId));
      LOGGER.info("Pending processing tasks: " + tasks);

    }
  }

  /**
   * Creates the proper {@link CSVReader} to parse the AW reports.
   *
   * @param file the CSV file.
   * @return the {@code CSVReader}
   * @throws UnsupportedEncodingException should not happen.
   */
  private CSVReader createCsvReader(InputStream inputStream) throws UnsupportedEncodingException {
    return new AwReportCsvReader(
        new InputStreamReader(inputStream, "UTF-8"), ',', '\"', 1);
  }

  /**
   * Downloads the file from the API into an InputStream.
   *
   * @return the InputStream from the online report
   * @throws ValidationException API validation error
   * @throws ReportException error in the report
   * @throws ReportDownloadResponseException error in the response format
   * @throws IOException 
   * @throws OAuthException 
   */
  protected InputStream getReportInputStream() throws ValidationException, ReportException,
  ReportDownloadResponseException, IOException, OAuthException {

    InputStream inputStream = null;

    ReportDownloader reportDownloader = new ReportDownloader(
        RestServer.getAdWordsSessionBuilderSynchronizer(mccAccountId).getAdWordsSessionCopy(accountId));

    ReportDownloadResponse reportDownloadResponse = reportDownloader.downloadReport(reportDefinition);

    if (reportDownloadResponse.getHttpStatus() == HttpURLConnection.HTTP_OK) {
      inputStream = reportDownloadResponse.getInputStream();
    } else {
      System.out.println("getHttpStatus():" + reportDownloadResponse.getHttpStatus());
      System.out.println("getAsString():" + reportDownloadResponse.getAsString());
    }
    return inputStream;
  }
  
  public void setPersister(EntityPersister entityPersister) {
    this.entityPersister = entityPersister;
  }
}
