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

package com.google.api.ads.adwords.jaxws.extensions.report.model.definitions;

import com.google.api.ads.adwords.jaxws.extensions.report.model.csv.AnnotationBasedMappingStrategy;
import com.google.api.ads.adwords.jaxws.extensions.report.model.csv.AwReportCsvReader;
import com.google.api.ads.adwords.jaxws.extensions.report.model.csv.CsvReportEntitiesMapping;
import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.Report;
import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.ReportBase;
import com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister;
import com.google.api.ads.adwords.jaxws.extensions.report.model.util.ModifiedCsvToBean;
import com.google.api.ads.adwords.lib.jaxb.v201406.ReportDefinitionReportType;

import au.com.bytecode.opencsv.CSVReader;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * Abstraction for the report definition tests.
 *
 * @param <T> type of sub Report.
 */
public abstract class AbstractReportDefinitionTest<T extends Report> {

  @Autowired
  private CsvReportEntitiesMapping csvReportEntitiesMapping;

  @Autowired
  @Qualifier("sqlReportEntitiesPersister")
  private EntityPersister persister;

  private Class<T> reportBeanClass;

  private ReportDefinitionReportType reportType;

  private String csvFileLocation;

  /**
   * C'tor
   *
   * @param reportBeanClass the report bean class to be tested.
   * @param reportType the report type that should mapped.
   * @param csvFileLocation the csv file location to test the mapping
   */
  public AbstractReportDefinitionTest(Class<T> reportBeanClass,
      ReportDefinitionReportType reportType, String csvFileLocation) {
    this.reportBeanClass = reportBeanClass;
    this.reportType = reportType;
    this.csvFileLocation = csvFileLocation;
  }

  /**
   * Tests the report mapping definition.
   */
  @Test
  public void testReportTypeDefinition() {

    Set<ReportDefinitionReportType> reports = this.csvReportEntitiesMapping.getDefinedReports();

    Assert.assertTrue(reports.contains(this.reportType));

    Class<? extends Report> mappedBeanClass =
        this.csvReportEntitiesMapping.getReportBeanClass(this.reportType);
    Assert.assertNotNull(mappedBeanClass);
    Assert.assertEquals(this.reportBeanClass, mappedBeanClass);
  }

  /**
   * Tests the properties that will be selected for the report.
   */
  @Test
  public void testReportProperties() {

    List<String> propertiesToSelect =
        this.csvReportEntitiesMapping.retrievePropertiesToSelect(this.reportType);

    Assert.assertNotNull(propertiesToSelect);

    String[] reportProperties = this.retrievePropertiesToBeSelected();

    Assert.assertEquals(
        "The number of properties mapped is different from the properties specified on the test.\n"
        + "expected: " + Arrays.toString(reportProperties) + "\nfound: "
        + propertiesToSelect.toString(), reportProperties.length, propertiesToSelect.size());

    for (int i = 0; i < reportProperties.length; i++) {
      Assert.assertTrue(propertiesToSelect.contains(reportProperties[i]));
    }
  }

  /**
   * Tests the mapping of the bean with the class.
   *
   * @throws FileNotFoundException the test should fail
   * @throws UnsupportedEncodingException the should fail
   */
  @Test
  public void testCSVMapping() throws UnsupportedEncodingException, FileNotFoundException {

    CSVReader csvReader = new AwReportCsvReader(
        new InputStreamReader(new FileInputStream(this.csvFileLocation), "UTF-8"), ',', '\"', 1);

    AnnotationBasedMappingStrategy<T> mappingStrategy =
        new AnnotationBasedMappingStrategy<T>(this.reportBeanClass);

    ModifiedCsvToBean<T> csvToBean = new ModifiedCsvToBean<T>();
    List<T> parsedBeans = csvToBean.parse(mappingStrategy, csvReader);

    Assert.assertEquals(this.retrieveCsvEntries(), parsedBeans.size());

    Assert.assertTrue(parsedBeans.size() > 1);

    T first = parsedBeans.get(0);
    T last = parsedBeans.get(parsedBeans.size() - 1);

    this.testFirstEntry(first);

    this.testLastEntry(last);
  }

  /**
   * Tests the persistence mapping of the bean against the SQL persister.
   *
   * @throws FileNotFoundException the test should fail
   * @throws UnsupportedEncodingException the test should fail
   */
  @Test
  public void testSQLPersistence() throws UnsupportedEncodingException, FileNotFoundException {

    CSVReader csvReader = new AwReportCsvReader(
        new InputStreamReader(new FileInputStream(this.csvFileLocation), "UTF-8"), ',', '\"', 1);

    AnnotationBasedMappingStrategy<T> mappingStrategy =
        new AnnotationBasedMappingStrategy<T>(this.reportBeanClass);

    ModifiedCsvToBean<T> csvToBean = new ModifiedCsvToBean<T>();
    List<T> parsedBeans = csvToBean.parse(mappingStrategy, csvReader);

    Assert.assertEquals("Wrong number of parsed entities from CSV.", this.retrieveCsvEntries(),
        parsedBeans.size());

    Assert.assertTrue(parsedBeans.size() > 1);

    this.persister.persistReportEntities(parsedBeans);

    List<T> listedReports = this.persister.listReports(this.reportBeanClass);
    int entries = this.retrieveCsvEntries();
    Assert.assertEquals("Different number of entities in DB than expected.", entries,
        listedReports.size());

    Collections.sort(listedReports, new ReportBeanDateComparator());

    this.testFirstEntry(listedReports.get(0));

    this.testLastEntry(listedReports.get(entries - 1));

    this.persister.removeReportEntities(listedReports);
  }

  /**
   * The implementation should test the values in the parsed bean.
   *
   * @param first the first entry in the CSV file.
   */
  protected abstract void testFirstEntry(T first);

  /**
   * The implementation should test the values in the parsed bean.
   *
   * @param last the last entry in the CSV file.
   */
  protected abstract void testLastEntry(T last);

  /**
   * @return the number of entries in the CSV file.
   */
  protected abstract int retrieveCsvEntries();

  /**
   * @return the properties that must be selected by the report definition.
   */
  protected abstract String[] retrievePropertiesToBeSelected();

  /**
   * Comparator to organize the retrieved data from DB.
   */
  private static final class ReportBeanDateComparator implements Comparator<Report> {

    /**
     * Compares the two reports days.
     */
    @Override
    public int compare(Report report1, Report report2) {

      if (report1 instanceof ReportBase && report2 instanceof ReportBase) {

        ReportBase report1base = ReportBase.class.cast(report1);
        ReportBase report2base = ReportBase.class.cast(report2);

        if (report1base.getDay() == null && report2base.getDay() == null) {
          return 0;
        }
        if (report1base.getDay() == null && report2base.getDay() != null) {
          return -1;
        }
        if (report1base.getDay() != null && report2base.getDay() == null) {
          return 1;
        }
        return report1base.getDay().compareTo(report2base.getDay());

      } else {

        if (report1.getDateStart() == null || report2.getDateStart() == null
            || report1.getDateEnd() == null || report2.getDateEnd() == null) {
          return 0;
        }

        // If DateStart are equal compare DateEnd
        if (report1.getDateStart().compareTo(report2.getDateStart()) == 0) {
          return report1.getDateEnd().compareTo(report2.getDateEnd());
        } else {
          return report1.getDateStart().compareTo(report2.getDateStart());
        }
      }
    }
  }
}
