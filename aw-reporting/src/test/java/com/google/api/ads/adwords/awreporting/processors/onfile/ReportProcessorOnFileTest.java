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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.google.api.ads.adwords.awreporting.authentication.Authenticator;
import com.google.api.ads.adwords.awreporting.authentication.InstalledOAuth2Authenticator;
import com.google.api.ads.adwords.awreporting.downloader.MultipleClientReportDownloader;
import com.google.api.ads.adwords.awreporting.exporter.reportwriter.ReportWriterType;
import com.google.api.ads.adwords.awreporting.model.csv.CsvReportEntitiesMapping;
import com.google.api.ads.adwords.awreporting.model.entities.AuthMcc;
import com.google.api.ads.adwords.awreporting.model.entities.Report;
import com.google.api.ads.adwords.awreporting.model.persistence.AuthTokenPersister;
import com.google.api.ads.adwords.awreporting.model.persistence.EntityPersister;
import com.google.api.ads.adwords.awreporting.processors.onfile.ReportProcessorOnFile;
import com.google.api.ads.adwords.awreporting.util.AdWordsSessionBuilderSynchronizer;
import com.google.api.ads.adwords.awreporting.util.DynamicPropertyPlaceholderConfigurer;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinition;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionDateRangeType;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionReportType;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Test case for the {@code ReportProcessorOnFile} class.
 *
 * @author jtoledo@google.com (Julian Toledo)
 */
public class ReportProcessorOnFileTest {

  private static final int NUMBER_OF_ACCOUNTS = 100;
  
  private static final int NUMBER_OF_THREADS = 50;
  
  private static final int REPORT_TYPES_SIZE = 14;
  
  private static final int CALLS_TO_PERSIST_ENTITIES = 5700;
  
  private static final Set<Long> CIDS = Sets.newHashSet();
  
  @Mock
  private AuthTokenPersister mockedAuthTokenPersister;

  @Mock
  private EntityPersister mockedEntitiesPersister;

  @Mock
  private MultipleClientReportDownloader mockedMultipleClientReportDownloader;

  @Spy
  private ReportProcessorOnFile reportProcessorOnFile;

  @Mock
  private Authenticator authenticator;

  private Properties properties;

  private ApplicationContext appCtx;


  @Captor
  ArgumentCaptor<List<? extends Report>> reportEntitiesCaptor;

  @Before
  public void setUp() throws InterruptedException, IOException, OAuthException, ValidationException {

    for (int i = 1; i <= NUMBER_OF_ACCOUNTS; i++) {
      CIDS.add(Long.valueOf(i));
    }

    Resource resource = new FileSystemResource("src/test/resources/aw-report-sample.properties");
    DynamicPropertyPlaceholderConfigurer.setDynamicResource(resource);
    properties = PropertiesLoaderUtils.loadProperties(resource);
    appCtx = new ClassPathXmlApplicationContext("classpath:aw-report-test-beans.xml");

    reportProcessorOnFile = new ReportProcessorOnFile(10, NUMBER_OF_THREADS);
    authenticator = new InstalledOAuth2Authenticator("DevToken", "ClientId", "ClientSecret",
        ReportWriterType.FileSystemWriter);

    MockitoAnnotations.initMocks(this);

    when(mockedAuthTokenPersister.getAuthToken(Mockito.anyString())).thenReturn(
        new AuthMcc("1", "AccountName", "TOKEN", "scope"));

    Mockito.doAnswer(new Answer<Void>() {
      @Override
      public Void answer(InvocationOnMock invocation) throws Throwable {
        return null;
      }
    }).when(mockedEntitiesPersister)
        .persistReportEntities(Mockito.<List<? extends Report>>anyObject());

    mockDownloadReports(CIDS.size());

    reportProcessorOnFile.setMultipleClientReportDownloader(mockedMultipleClientReportDownloader);
    reportProcessorOnFile.setPersister(mockedEntitiesPersister);
    reportProcessorOnFile.setCsvReportEntitiesMapping(
        appCtx.getBean(CsvReportEntitiesMapping.class));
    reportProcessorOnFile.setAuthentication(authenticator);

    // Mocking the Authentication because in OAuth2 we are force to call buildOAuth2Credentials
    AdWordsSession.Builder builder = new AdWordsSession.Builder().withClientCustomerId("1");
    Mockito.doReturn(builder).when(authenticator)
        .authenticate(Mockito.anyString(), Mockito.anyBoolean());
  }

  @Test
  public void testGenerateReportsForMCC() throws Exception {

    reportProcessorOnFile.generateReportsForMCC("123",
        ReportDefinitionDateRangeType.CUSTOM_DATE,
        "20130101",
        "20130131",
        CIDS,
        properties,
        null,
        null);

    verify(mockedMultipleClientReportDownloader, times(REPORT_TYPES_SIZE)).downloadReports(
        Mockito.<AdWordsSessionBuilderSynchronizer>anyObject(), Mockito.<ReportDefinition>anyObject(),
        Mockito.<Set<Long>>anyObject());

    verify(mockedEntitiesPersister, times(CALLS_TO_PERSIST_ENTITIES)).persistReportEntities(
        reportEntitiesCaptor.capture());

    assertEquals(CALLS_TO_PERSIST_ENTITIES, reportEntitiesCaptor.getAllValues().size());
    
    for (List<? extends Report> reportEntities : reportEntitiesCaptor.getAllValues()) {
      for (Report report : reportEntities) {
        assertNotNull(report.getId());
        assertNotNull(report.getAccountId());
      }
    }
  }

  private void mockDownloadReports(final int numberOfFiles) throws InterruptedException, ValidationException {
    Mockito.doAnswer(new Answer<Collection<File>>() {
      @Override
      public Collection<File> answer(InvocationOnMock invocation) throws Throwable {
        ReportDefinitionReportType reportType =
            ((ReportDefinition) invocation.getArguments()[1]).getReportType();
        // returns the appropriate gZip files depending on the report type
        if (reportType.equals(ReportDefinitionReportType.ACCOUNT_PERFORMANCE_REPORT)) {
          return getReportFiles(
              "reportDownload-ACCOUNT_PERFORMANCE_REPORT-2602198216-1370030134500.report",
              numberOfFiles);
        }
        if (reportType.equals(ReportDefinitionReportType.ADGROUP_PERFORMANCE_REPORT)) {
          return getReportFiles(
              "reportDownload-ADGROUP_PERFORMANCE_REPORT-2450945640-1370030054471.report",
              numberOfFiles);
        }
        if (reportType.equals(ReportDefinitionReportType.AD_PERFORMANCE_REPORT)) {
          return getReportFiles(
              "reportDownload-AD_PERFORMANCE_REPORT-1001270004-1369848354724.report",
              numberOfFiles);
        }
        if (reportType.equals(
            ReportDefinitionReportType.CAMPAIGN_NEGATIVE_KEYWORDS_PERFORMANCE_REPORT)) {
          return getReportFiles("reportDownload-CAMPAIGN_NEGATIVE_KEYWORDS_PERFORMANCE_REPORT"
              + "-2602198216-1370029913872.report", numberOfFiles);
        }
        if (reportType.equals(ReportDefinitionReportType.CAMPAIGN_PERFORMANCE_REPORT)) {
          return getReportFiles(
              "reportDownload-CAMPAIGN_PERFORMANCE_REPORT-1252422563-1370029981335.report",
              numberOfFiles);
        }
        if (reportType.equals(ReportDefinitionReportType.KEYWORDS_PERFORMANCE_REPORT)) {
          return getReportFiles(
              "reportDownload-KEYWORDS_PERFORMANCE_REPORT-8661954824-1370029730794.report",
              numberOfFiles);
        }
        if (reportType.equals(ReportDefinitionReportType.PLACEHOLDER_FEED_ITEM_REPORT)) {
          return getReportFiles(
              "reportDownload-PLACEHOLDER_FEED_ITEM_REPORT-128401167-1378740342878.report",
              numberOfFiles);
        }
        if (reportType.equals(ReportDefinitionReportType.CRITERIA_PERFORMANCE_REPORT)) {
          return getReportFiles(
              "reportDownload-CRITERIA_PERFORMANCE_REPORT-2752283680-1378903912127.report",
              numberOfFiles);
        }
        if (reportType.equals(ReportDefinitionReportType.SEARCH_QUERY_PERFORMANCE_REPORT)) {
          return getReportFiles(
              "reportDownload-SEARCH_QUERY_PERFORMANCE_REPORT-2084918008-570857839140990020.report",
              numberOfFiles);
        }
        if (reportType.equals(ReportDefinitionReportType.CAMPAIGN_LOCATION_TARGET_REPORT)) {
          return getReportFiles(
              "reportDownload-CAMPAIGN_LOCATION_TARGET_REPORT-9250931436-3311462434933679712.report",
              numberOfFiles);
        }
        if (reportType.equals(ReportDefinitionReportType.GEO_PERFORMANCE_REPORT)) {
          return getReportFiles(
              "reportDownload-GEO_PERFORMANCE_REPORT-2084918008-570857839140990020.report10",
              numberOfFiles);
        }
        if (reportType.equals(ReportDefinitionReportType.PLACEMENT_PERFORMANCE_REPORT)) {
          return getReportFiles(
              "reportDownload-PLACEMENT_PERFORMANCE_REPORT-501111125-37111114339129.report10",
              numberOfFiles);
        }
        if (reportType.equals(ReportDefinitionReportType.DISPLAY_KEYWORD_PERFORMANCE_REPORT)) {
        	return getReportFiles(
              "reportDownload-DISPLAY_KEYWORD_PERFORMANCE_REPORT-1056270861-4656938936183294408.report",
              numberOfFiles);
        }
        if (reportType.equals(ReportDefinitionReportType.SHOPPING_PERFORMANCE_REPORT)) {
          return getReportFiles(
              "reportDownload-SHOPPING_PERFORMANCE_REPORT-4159595773-1835647307310030649.report",
              numberOfFiles);
        }
        // Undefined report type on this test
        throw (new Exception("Undefined report type on Tests: " + reportType.value()));
      }
    }).when(mockedMultipleClientReportDownloader).downloadReports(
        Mockito.<AdWordsSessionBuilderSynchronizer>anyObject(), Mockito.<ReportDefinition>anyObject(),
        Mockito.<Set<Long>>anyObject());
  }

  private Collection<File> getReportFiles(String fileName, int numberOfFiles) throws IOException {
    final Collection<File> files = Lists.newArrayList();
    for (int i = 1; i <= numberOfFiles; i++) {

      File newFile = new File("src/test/resources/csv/" + fileName + i);
      File newFile2 = new File("src/test/resources/csv/" + fileName + i + ".gunzip");

      FileUtils.copyFile(new File("src/test/resources/csv/" + fileName), newFile);
      FileUtils.copyFile(new File("src/test/resources/csv/" + fileName), newFile2);

      files.add(newFile);
    }
    return files;
  }
}
