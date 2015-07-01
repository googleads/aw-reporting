//Copyright 2013 Google Inc. All Rights Reserved.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.

package com.google.api.ads.adwords.awreporting.alerting.processor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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

import com.google.api.ads.adwords.awreporting.alerting.downloader.MultipleClientReportDownloader;
import com.google.api.ads.adwords.awreporting.alerting.downloader.ReportDefinitionDownloader;
import com.google.api.ads.adwords.awreporting.alerting.report.ReportQuery;
import com.google.api.ads.adwords.awreporting.alerting.util.TestEntitiesGenerator;
import com.google.api.ads.adwords.awreporting.authentication.Authenticator;
import com.google.api.ads.adwords.awreporting.authentication.InstalledOAuth2Authenticator;
import com.google.api.ads.adwords.awreporting.exporter.reportwriter.ReportWriterType;
import com.google.api.ads.adwords.awreporting.util.AdWordsSessionBuilderSynchronizer;
import com.google.api.ads.adwords.jaxws.v201502.cm.ApiException_Exception;
import com.google.api.ads.adwords.jaxws.v201502.cm.ReportDefinitionReportType;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
* Test case for the {@link ReportProcessorOnFile} class.
* 
 * @author zhuoc@google.com (Zhuo Chen)
*
*/
public class ReportProcessorOnFileTest {
  
  private static final int NUMBER_OF_ACCOUNTS = 100;
  private static final int NUMBER_OF_THREADS = 50;

  private static final Set<Long> CIDS = Sets.newHashSet();
  
  @Mock
  private MultipleClientReportDownloader mockedMultipleClientReportDownloader;
  
  @Mock
  private ReportDefinitionDownloader reportDefinitionDownloader;
  
  @Mock
  private Authenticator authenticator;
  
  @Spy
  private ReportProcessorOnFile reportProcessorOnFile;
  
  @Captor
  ArgumentCaptor<Collection<File>> filesCaptor;
  
  @Before
  public void setUp() throws InterruptedException, IOException, OAuthException, ValidationException, ApiException_Exception {
    
    for (int i = 1; i <= NUMBER_OF_ACCOUNTS; i++) {
      CIDS.add(Long.valueOf(i));
    }
   
    reportProcessorOnFile = new ReportProcessorOnFile(NUMBER_OF_THREADS);
    authenticator = new InstalledOAuth2Authenticator("DevToken", "ClientId", "ClientSecret",
                                                     ReportWriterType.FileSystemWriter);
    
    MockitoAnnotations.initMocks(this);
    
    mockMultipleClinetReportDownloader();
    mockReportDefinitionDownloader();
    mockAuthenticator();
    
    reportProcessorOnFile.setAuthentication(authenticator);
    reportProcessorOnFile.setMultipleClientReportDownloader(mockedMultipleClientReportDownloader);
    reportProcessorOnFile.setReportDefinitionDownloader(reportDefinitionDownloader);
    
    // Mock reportProcessorOnFile.initReportDefinitionDownloader() to do nothing
    Mockito.doNothing().when(reportProcessorOnFile)
        .initReportDefinitionDownloader(Mockito.<AdWordsSession.Builder>anyObject());
  }
  
  @Test
  public void testGenerateReportsForMCC() throws Exception {
    String alertsConfigFilePath = TestEntitiesGenerator.getTestAlertsConfigFilePath();
    JsonObject alertsConfig = new JsonParser().parse(new FileReader(alertsConfigFilePath)).getAsJsonObject();
    final int NUMBER_OF_ALERTS = 2;
    
    reportProcessorOnFile.generateAlertsForMCC("123", CIDS, alertsConfig);
    
    verify(reportProcessorOnFile, times(2)).processAlertForMCC(
        Mockito.anyString(),
        Mockito.<Set<Long>>anyObject(),
        Mockito.<AdWordsSessionBuilderSynchronizer>anyObject(),
        Mockito.<JsonObject>anyObject(),
        Mockito.anyInt());
    
    // Invoke getFieldsMapping() NUMBER_OF_ALERTS times (each for one alert's report query)
    verify(reportDefinitionDownloader, times(NUMBER_OF_ALERTS)).getFieldsMapping(
        Mockito.<ReportDefinitionReportType>anyObject());
    
    // Invoke downloadReports() NUMBER_OF_ALERTS times (each with NUMBER_OF_ACCOUNTS accounts)
    verify(mockedMultipleClientReportDownloader, times(NUMBER_OF_ALERTS)).downloadReports(
        Mockito.<AdWordsSessionBuilderSynchronizer>anyObject(),
        Mockito.<ReportQuery>anyObject(),
        Mockito.<Set<Long>>anyObject());
    
    // Invoke processFiles() NUMBER_OF_ALERTS times (each with NUMBER_OF_ACCOUNTS downloaded files)
    verify(reportProcessorOnFile, times(NUMBER_OF_ALERTS)).processFiles(
        filesCaptor.capture(),
        Mockito.<Map<String,String>>anyObject(),
        Mockito.anyString(),
        Mockito.<ReportDefinitionReportType>anyObject(),
        Mockito.<AlertRulesProcessor>anyObject(),
        Mockito.anyString(),
        Mockito.<JsonArray>anyObject());
    
    List<Collection<File>> capturedFiles = filesCaptor.getAllValues();
    assertEquals(capturedFiles.size(), NUMBER_OF_ALERTS);
    assertEquals(capturedFiles.get(0).size(), NUMBER_OF_ACCOUNTS);
    assertEquals(capturedFiles.get(1).size(), NUMBER_OF_ACCOUNTS);
  }
  
  private void mockMultipleClinetReportDownloader() throws InterruptedException, ValidationException {
    Mockito.doAnswer(new Answer<Collection<File>>() {
      @Override
      public Collection<File> answer(InvocationOnMock invocation) throws Throwable {
       
        String reportTypeStr = ((ReportQuery) invocation.getArguments()[1]).getReportType();
        if (reportTypeStr.equals(ReportDefinitionReportType.ACCOUNT_PERFORMANCE_REPORT.value())) {
          return getReportFiles(TestEntitiesGenerator.getTestReportFilePath(), NUMBER_OF_ACCOUNTS);
        }
        // Undefined report type on this test
        throw (new Exception("Undefined report type on Tests: " + reportTypeStr));
      }
    }).when(mockedMultipleClientReportDownloader).downloadReports(
        Mockito.<AdWordsSessionBuilderSynchronizer>anyObject(),
        Mockito.<ReportQuery>anyObject(),
        Mockito.<Set<Long>>anyObject());
  }
  
  private Collection<File> getReportFiles(String filePath, int numberOfFiles) throws IOException {
    final Collection<File> files = Lists.newArrayList();
    for (int i = 1; i <= numberOfFiles; i++) {
      File newFile = new File(filePath + i + ".gunzip");
      FileUtils.copyFile(new File(filePath), newFile);
      files.add(newFile);
    }
    return files;
  }
  
  private void mockReportDefinitionDownloader() throws ApiException_Exception  {
    Mockito.doAnswer(new Answer<Map<String, String>>() {
      @Override
      public Map<String, String> answer(InvocationOnMock invocation) throws Throwable {
       
        ReportDefinitionReportType reportType = (ReportDefinitionReportType) invocation.getArguments()[0];
        if (reportType.equals(ReportDefinitionReportType.ACCOUNT_PERFORMANCE_REPORT)) {
          return TestEntitiesGenerator.getTestFiledsMapping();
        }
        // Undefined report type on this test
        throw (new Exception("Undefined report type on Tests: " + reportType.value()));
      }
    }).when(reportDefinitionDownloader).getFieldsMapping(
        Mockito.<ReportDefinitionReportType>anyObject());
  }
  
  private void mockAuthenticator() throws OAuthException {
    // Mocking the Authentication because in OAuth2 we are force to call buildOAuth2Credentials
    AdWordsSession.Builder builder = new AdWordsSession.Builder().withClientCustomerId("1");
    Mockito.doReturn(builder).when(authenticator)
        .authenticate(Mockito.anyString(), Mockito.anyBoolean());
  }
}
