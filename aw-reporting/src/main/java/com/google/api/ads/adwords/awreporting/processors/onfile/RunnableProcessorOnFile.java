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

package com.google.api.ads.adwords.awreporting.processors.onfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.MappingStrategy;

import com.google.api.ads.adwords.awreporting.model.csv.AwReportCsvReader;
import com.google.api.ads.adwords.awreporting.model.entities.Report;
import com.google.api.ads.adwords.awreporting.model.persistence.EntityPersister;
import com.google.api.ads.adwords.awreporting.model.util.CsvParserIterator;
import com.google.api.ads.adwords.awreporting.model.util.ModifiedCsvToBean;
import com.google.api.ads.adwords.awreporting.util.AdWordsSessionBuilderSynchronizer;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionDateRangeType;
import com.google.common.collect.Lists;

/**
 * This {@link Runnable} implements the core logic to download the report file from the AdWords API.
 *
 * The {@link Collection}s passed to this runner are considered to be synchronized and thread safe.
 * This class has no blocking logic when adding elements to the collections.
 *
 * Also the {@link AdWordsSessionBuilderSynchronizer} is kept by the client class, and should handle
 * all the concurrent threads.
 *
 * Parse the rows in the CSV file for the report type, and persists the beans into the data base.
 *
 * @author gustavomoreira@google.com (Gustavo Moreira)
 * @author jtoledo@google.com (Julian Toledo)
 *
 * @param <R> type of sub Report.
 */
public class RunnableProcessorOnFile<R extends Report> implements Runnable {

  private static final Logger LOGGER = Logger.getLogger(RunnableProcessorOnFile.class);

  private CountDownLatch latch;

  private File file;
  private ModifiedCsvToBean<R> csvToBean;
  private MappingStrategy<R> mappingStrategy;
  private ReportDefinitionDateRangeType dateRangeType;
  private String dateStart;
  private String dateEnd;
  private String mccAccountId;
  private EntityPersister entityPersister;
  private int reportRowsSetSize;

  private Exception error = null;

  /**
   * C'tor.
   *
   * @param file the CSV file.
   * @param csvToBean the {@code CsvToBean}
   * @param mappingStrategy
   */
  public RunnableProcessorOnFile(File file,
      ModifiedCsvToBean<R> csvToBean,
      MappingStrategy<R> mappingStrategy,
      ReportDefinitionDateRangeType dateRangeType,
      String dateStart,
      String dateEnd,
      String mccAccountId,
      EntityPersister entityPersister,
      Integer reportRowsSetSize) {
    this.file = file;
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
      CSVReader csvReader = this.createCsvReader(file);

      LOGGER.debug("Starting parse of report rows...");
      CsvParserIterator<R> reportRowsList = csvToBean.lazyParse(mappingStrategy, csvReader);
      LOGGER.debug("... success.");

      LOGGER.debug("Starting report persistence...");
      List<R> reportBuffer = Lists.newArrayList();
      while (reportRowsList.hasNext()) {

        R report = reportRowsList.next();

        // Getting Account Id from File Name for reports that do not have Client Customer Id
        if (report.getAccountId() == null && file.getName().contains("-")
            && file.getName().split("-") != null && file.getName().split("-").length > 2
            && file.getName().split("-")[2].matches("\\d*")) {
          report.setAccountId(Long.parseLong(file.getName().split("-")[2]));
        }

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
      error = new Exception("Error processing file: " + file.getAbsolutePath(), e);
      LOGGER.error("Error processing file: " + file.getAbsolutePath());
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
   * @param file the CSV file.
   * @return the {@code CSVReader}
   * @throws UnsupportedEncodingException should not happen.
   * @throws FileNotFoundException in case the file has been deleted before the reading.
   */
  private CSVReader createCsvReader(File file) throws UnsupportedEncodingException,
      FileNotFoundException {

    String fileAbsolutePath = file.getAbsolutePath();
    if (new File(fileAbsolutePath + ".gunzip").exists()) {
      fileAbsolutePath = fileAbsolutePath + ".gunzip";
    }
    LOGGER.debug("Creating AwReportCsvReader for file: " + fileAbsolutePath);
    return new AwReportCsvReader(
        new InputStreamReader(new FileInputStream(fileAbsolutePath), "UTF-8"), ',', '\"', 1);
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

  /**
   * @param entityPersister the entityPersister to set
   */
  public void setPersister(EntityPersister entityPersister) {
    this.entityPersister = entityPersister;
  }
}
