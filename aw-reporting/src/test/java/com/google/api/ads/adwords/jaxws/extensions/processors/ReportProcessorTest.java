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

package com.google.api.ads.adwords.jaxws.extensions.processors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.api.ads.adwords.jaxws.extensions.authentication.Authenticator;
import com.google.api.ads.adwords.jaxws.extensions.authentication.InstalledOAuth2Authenticator;
import com.google.api.ads.adwords.jaxws.extensions.downloader.MultipleClientReportDownloader;
import com.google.api.ads.adwords.jaxws.extensions.processors.onfile.ReportProcessorOnFile;
import com.google.api.ads.adwords.jaxws.extensions.report.model.csv.CsvReportEntitiesMapping;
import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.AuthMcc;
import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.Report;
import com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.AuthTokenPersister;
import com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister;
import com.google.api.ads.adwords.jaxws.extensions.reportwriter.ReportWriterType;
import com.google.api.ads.adwords.jaxws.extensions.util.DynamicPropertyPlaceholderConfigurer;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.jaxb.v201309.ReportDefinition;
import com.google.api.ads.adwords.lib.jaxb.v201309.ReportDefinitionDateRangeType;
import com.google.api.ads.adwords.lib.jaxb.v201309.ReportDefinitionReportType;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

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

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Test case for the {@code ReportProcessor} class.
 *
 * @author jtoledo@google.com (Julian Toledo)
 */
public class ReportProcessorTest {

  @Mock
  private AuthTokenPersister mockedAuthTokenPersister;

  @Mock
  private EntityPersister mockedReportEntitiesPersister;

  @Mock
  private MultipleClientReportDownloader mockedMultipleClientReportDownloader;

  @Spy
  private ReportProcessorOnFile reportProcessorOnFile;
  
  @Mock
  private Authenticator authenticator;

  private Properties properties;

  private ApplicationContext appCtx;

  private static final Set<Long> CIDS = ImmutableSet.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);

  private static final int REPORT_TYPES_SIZE = 9;

  private static final int ROW_COUNT_CSV_TOTAL = 260;

  @Captor
  ArgumentCaptor<List<? extends Report>> reportEntitiesCaptor;

  @Before
  public void setUp() throws InterruptedException, IOException, OAuthException {
    Resource resource = new FileSystemResource("src/test/resources/aw-report-sample.properties");
    DynamicPropertyPlaceholderConfigurer.setDynamicResource(resource);
    properties = PropertiesLoaderUtils.loadProperties(resource);
    appCtx = new ClassPathXmlApplicationContext("classpath:aw-report-test-beans.xml");

    reportProcessor =
        new ReportProcessor("1", "token", "companyName", "clientId", "clientSecret", 10, 2, ReportWriterType.FileSystemWriter);

    reportProcessorOnFile = new ReportProcessorOnFile("1", 10, 2);
    authenticator = new InstalledOAuth2Authenticator("DevToken","ClientId", "ClientSecret", ReportWriterType.FileSystemWriter);

    MockitoAnnotations.initMocks(this);

    when(mockedAuthTokenPersister.getAuthToken(Mockito.anyString())).thenReturn(
        new AuthMcc("1", "TOKEN"));

    Mockito.doAnswer(new Answer<Void>() {
      @Override
      public Void answer(InvocationOnMock invocation) throws Throwable {
        return null;
      }
    }).when(mockedReportEntitiesPersister)
    .persistReportEntities(Mockito.<List<? extends Report>>anyObject());

    mockDownloadReports(CIDS.size());

    reportProcessorOnFile.setMultipleClientReportDownloader(mockedMultipleClientReportDownloader);
    reportProcessorOnFile.setPersister(mockedReportEntitiesPersister);
    reportProcessorOnFile.setCsvReportEntitiesMapping(appCtx.getBean(CsvReportEntitiesMapping.class));    
    reportProcessorOnFile.setAuthentication(authenticator);

    // Mocking the Authentication because in OAuth2 we are force to call buildOAuth2Credentials
    AdWordsSession.Builder builder = new AdWordsSession.Builder().withClientCustomerId("1");
    Mockito.doReturn(builder).when(authenticator).authenticate(
        Mockito.anyString(), Mockito.anyBoolean());
  }

  @Test
  public void testGenerateReportsForMCC() throws Exception {

    reportProcessorOnFile.generateReportsForMCC(
        ReportDefinitionDateRangeType.CUSTOM_DATE, "20130101", "20130131", CIDS, properties);

    verify(mockedMultipleClientReportDownloader, times(REPORT_TYPES_SIZE)).downloadReports(
        Mockito.<AdWordsSession.Builder>anyObject(), Mockito.<ReportDefinition>anyObject(),
        Mockito.<Set<Long>>anyObject());

    verify(mockedReportEntitiesPersister, times(ROW_COUNT_CSV_TOTAL))
    .persistReportEntities(reportEntitiesCaptor.capture());

    assertEquals(ROW_COUNT_CSV_TOTAL, reportEntitiesCaptor.getAllValues().size());
    for (List<? extends Report> reportEntities : reportEntitiesCaptor.getAllValues()) {
      for (Report report : reportEntities) {
        assertNotNull(report.getId());
        assertNotNull(report.getAccountId());
      }
    }
  }

  private void mockDownloadReports(final int numberOfFiles) throws InterruptedException {
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
        if (reportType.equals(ReportDefinitionReportType.AD_EXTENSIONS_PERFORMANCE_REPORT)) {
          return getReportFiles(
              "reportDownload-AD_EXTENSIONS_PERFORMANCE_REPORT-2602198216-1370029629538.report",
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
          return getReportFiles(
              "reportDownload-CAMPAIGN_NEGATIVE_KEYWORDS_PERFORMANCE_REPORT" + 
                  "-2602198216-1370029913872.report",
                  numberOfFiles);
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
        // Undefined report type on this test
        throw (new Exception("Undefined report type on Tests: " + reportType.value()));
      }
    }).when(mockedMultipleClientReportDownloader).downloadReports(
        Mockito.<AdWordsSession.Builder>anyObject(), Mockito.<ReportDefinition>anyObject(),
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
