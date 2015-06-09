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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.zip.GZIPOutputStream;

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
import com.google.api.ads.adwords.awreporting.exporter.reportwriter.ReportWriterType;
import com.google.api.ads.adwords.awreporting.model.csv.CsvReportEntitiesMapping;
import com.google.api.ads.adwords.awreporting.model.entities.Report;
import com.google.api.ads.adwords.awreporting.model.persistence.EntityPersister;
import com.google.api.ads.adwords.awreporting.processors.onmemory.ReportProcessorOnMemory;
import com.google.api.ads.adwords.awreporting.processors.onmemory.RunnableProcessorOnMemory;
import com.google.api.ads.adwords.awreporting.util.DynamicPropertyPlaceholderConfigurer;
import com.google.api.ads.adwords.awreporting.util.FileUtil;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionDateRangeType;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionReportType;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.util.Lists;
import com.google.api.client.util.Maps;
import com.google.common.collect.Sets;

/**
 * Test case for the {@code ReportProcessorOnMemory} class.
 *
 * @author jtoledo@google.com (Julian Toledo)
 */
public class ReportProcessorOnMemoryTest {

  private static final String PROPERTIES_FILE = "src/test/resources/aw-report-sample.properties";

  private static final int NUMBER_OF_ACCOUNTS = 50;
  
  private static final int NUMBER_OF_THREADS = 50;
  
  private static final int CALLS_TO_PERSIST_ENTITIES = 13500;

  private Properties properties;

  private static final Set<Long> CIDS = Sets.newHashSet();

  private ApplicationContext appCtx;

  @Mock
  private EntityPersister mockedEntitiesPersister;

  @Spy
  private ReportProcessorOnMemory reportProcessorOnMemory;
 
  // We will Spy each RunnableProcessorOnMemory
  private List<RunnableProcessorOnMemory<Report>> runnableProcessorOnMemoryList = Lists.newArrayList();

  @Mock
  private Authenticator authenticator;

  @Captor
  private ArgumentCaptor<List<? extends Report>> reportEntitiesCaptor;
  
  private Map<ReportDefinitionReportType, byte[]> reportDataMap = Maps.newHashMap();

  @SuppressWarnings("unchecked")
  @Before
  public void setUp() throws IOException, OAuthException {

    for (int i = 1; i <= NUMBER_OF_ACCOUNTS; i++) {
      CIDS.add(Long.valueOf(i));
    }

    Resource resource = new FileSystemResource(PROPERTIES_FILE);
    DynamicPropertyPlaceholderConfigurer.setDynamicResource(resource);
    properties = PropertiesLoaderUtils.loadProperties(resource);
    appCtx = new ClassPathXmlApplicationContext("classpath:aw-report-test-beans.xml");

    reportProcessorOnMemory = new ReportProcessorOnMemory(2, NUMBER_OF_THREADS);

    authenticator = new InstalledOAuth2Authenticator("DevToken", "ClientId", "ClientSecret",
        ReportWriterType.FileSystemWriter);

    MockitoAnnotations.initMocks(this);

    doAnswer(new Answer<Void>() {
      @Override
      public Void answer(InvocationOnMock invocation) throws Throwable {
        return null;
      }
    }).when(mockedEntitiesPersister)
        .persistReportEntities(Mockito.<List<? extends Report>>anyObject());

    reportProcessorOnMemory.setCsvReportEntitiesMapping(
        appCtx.getBean(CsvReportEntitiesMapping.class));
    reportProcessorOnMemory.setAuthentication(authenticator);
    reportProcessorOnMemory.setPersister(mockedEntitiesPersister);

    // Mocking the Authentication because in OAuth2 we are force to call buildOAuth2Credentials
    AdWordsSession.Builder builder =
        new AdWordsSession.Builder().withEndpoint("http://www.google.com")
            .withDeveloperToken("DeveloperToken")
            .withClientCustomerId("123")
            .withUserAgent("UserAgent")
            .withOAuth2Credential( new GoogleCredential.Builder().build());

    doReturn(builder).when(authenticator)
        .authenticate(Mockito.anyString(), Mockito.anyBoolean());

    // Modifying ReportProcessorOnMemory to use the Mocked objects and avoid calling the real API 
    doAnswer(new Answer<RunnableProcessorOnMemory<Report>>() {
      @Override
      public RunnableProcessorOnMemory<Report> answer(InvocationOnMock invocation) throws Throwable {
        Object[] args = invocation.getArguments();

        RunnableProcessorOnMemory<Report> runnableProcessorOnMemory = Mockito.spy((RunnableProcessorOnMemory<Report>)args[0]);
        
        doAnswer(new Answer<ByteArrayInputStream>() {
          @Override
          public ByteArrayInputStream answer(InvocationOnMock invocation) throws Throwable {
            
            RunnableProcessorOnMemory<Report> rr = (RunnableProcessorOnMemory<Report>) invocation.getMock();
            
            byte[] reportData = getReporDatafromCsv(rr.getReportDefinition().getReportType());

            return new ByteArrayInputStream(reportData);
          }
        }).when(runnableProcessorOnMemory).getReportInputStream();

        runnableProcessorOnMemoryList.add(runnableProcessorOnMemory);
        return runnableProcessorOnMemory;
      }
    }).when(reportProcessorOnMemory).getRunnableProcessorOnMemory(Mockito.any(RunnableProcessorOnMemory.class));
  }

  @Test
  public void testGenerateReportsForMCC() throws Exception {

    reportProcessorOnMemory.generateReportsForMCC("123",
        ReportDefinitionDateRangeType.CUSTOM_DATE, "20130101", "20130131", CIDS, properties, null, null);

    verify(mockedEntitiesPersister, times(CALLS_TO_PERSIST_ENTITIES)).persistReportEntities(
        reportEntitiesCaptor.capture());

    assertEquals(CALLS_TO_PERSIST_ENTITIES, reportEntitiesCaptor.getAllValues().size());

    // Check that all RunnableProcessorOnMemory executed without errors
    for(RunnableProcessorOnMemory<Report> runnable : runnableProcessorOnMemoryList) {
      Exception error = runnable.getError();
      if (error != null) {
        fail("There where errors in a RunnableProcessorOnMemory: " + error.getMessage());
      }
    }
  }

  private String getReportDataFileName(ReportDefinitionReportType reportType) throws Exception {
    // returns the appropriate file depending on the report type
    if (reportType.equals(ReportDefinitionReportType.ACCOUNT_PERFORMANCE_REPORT)) {
      return "src/test/resources/csv/reportDownload-ACCOUNT_PERFORMANCE_REPORT-2602198216-1370030134500.report";
    }
    if (reportType.equals(ReportDefinitionReportType.ADGROUP_PERFORMANCE_REPORT)) {
      return "src/test/resources/csv/reportDownload-ADGROUP_PERFORMANCE_REPORT-2450945640-1370030054471.report";
    }
    if (reportType.equals(ReportDefinitionReportType.AD_PERFORMANCE_REPORT)) {
      return "src/test/resources/csv/reportDownload-AD_PERFORMANCE_REPORT-1001270004-1369848354724.report";
    }
    if (reportType.equals(
        ReportDefinitionReportType.CAMPAIGN_NEGATIVE_KEYWORDS_PERFORMANCE_REPORT)) {
      return "src/test/resources/csv/reportDownload-CAMPAIGN_NEGATIVE_KEYWORDS_PERFORMANCE_REPORT-2602198216-1370029913872.report";
    }
    if (reportType.equals(ReportDefinitionReportType.CAMPAIGN_PERFORMANCE_REPORT)) {
      return "src/test/resources/csv/reportDownload-CAMPAIGN_PERFORMANCE_REPORT-1252422563-1370029981335.report";
    }
    if (reportType.equals(ReportDefinitionReportType.KEYWORDS_PERFORMANCE_REPORT)) {
      return "src/test/resources/csv/reportDownload-KEYWORDS_PERFORMANCE_REPORT-8661954824-1370029730794.report";
    }
    if (reportType.equals(ReportDefinitionReportType.PLACEHOLDER_FEED_ITEM_REPORT)) {
      return "src/test/resources/csv/reportDownload-PLACEHOLDER_FEED_ITEM_REPORT-128401167-1378740342878.report";
    }
    if (reportType.equals(ReportDefinitionReportType.CRITERIA_PERFORMANCE_REPORT)) {
      return "src/test/resources/csv/reportDownload-CRITERIA_PERFORMANCE_REPORT-2752283680-1378903912127.report";
    }
    if (reportType.equals(ReportDefinitionReportType.SEARCH_QUERY_PERFORMANCE_REPORT)) {
      return "src/test/resources/csv/reportDownload-SEARCH_QUERY_PERFORMANCE_REPORT-2084918008-570857839140990020.report";
    }
    if (reportType.equals(ReportDefinitionReportType.CAMPAIGN_LOCATION_TARGET_REPORT)) {
      return "src/test/resources/csv/reportDownload-CAMPAIGN_LOCATION_TARGET_REPORT-9250931436-3311462434933679712.report";
    }
    if (reportType.equals(ReportDefinitionReportType.GEO_PERFORMANCE_REPORT)) {
      return "src/test/resources/csv/reportDownload-GEO_PERFORMANCE_REPORT-2084918008-570857839140990020.report10";
    }
    if (reportType.equals(ReportDefinitionReportType.PLACEMENT_PERFORMANCE_REPORT)) {
      return "src/test/resources/csv/reportDownload-PLACEMENT_PERFORMANCE_REPORT-501111125-37111114339129.report10";
    }
    if (reportType.equals(ReportDefinitionReportType.DISPLAY_KEYWORD_PERFORMANCE_REPORT)) {
      return "src/test/resources/csv/reportDownload-DISPLAY_KEYWORD_PERFORMANCE_REPORT-1056270861-4656938936183294408.report";
    }
    if (reportType.equals(ReportDefinitionReportType.SHOPPING_PERFORMANCE_REPORT)) {
      return "src/test/resources/csv/reportDownload-SHOPPING_PERFORMANCE_REPORT-4159595773-1835647307310030649.report";
    }
    // Undefined report type on this test
    throw (new Exception("Undefined report type on Tests: " + reportType.value()));
  }

  private byte[] getReporDatafromCsv(ReportDefinitionReportType reportType) throws Exception {
    byte[] reportData = reportDataMap.get(reportType);
    if (reportData == null) {
      FileInputStream fis = new FileInputStream(getReportDataFileName(reportType));
      ByteArrayOutputStream baos = new ByteArrayOutputStream(2048);
      GZIPOutputStream gzipOut = new GZIPOutputStream(baos);
      FileUtil.copy(fis, gzipOut);
      gzipOut.flush();
      gzipOut.close();
      reportData = baos.toByteArray();
      reportDataMap.put(reportType, reportData);
      baos.flush();
      baos.close();
    }
    return reportData;
  }
}
