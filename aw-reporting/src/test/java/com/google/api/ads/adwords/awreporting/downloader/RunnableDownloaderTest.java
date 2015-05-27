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

package com.google.api.ads.adwords.awreporting.downloader;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.google.api.ads.adwords.awreporting.downloader.RunnableDownloader;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.jaxb.v201502.DownloadFormat;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinition;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionDateRangeType;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionReportType;
import com.google.api.ads.adwords.lib.jaxb.v201502.Selector;
import com.google.api.ads.adwords.lib.utils.ReportDownloadResponseException;
import com.google.api.ads.adwords.lib.utils.ReportException;
import com.google.api.ads.adwords.lib.utils.v201502.DetailedReportDownloadResponseException;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

/**
 * Test case for the {@code RunnableDownloader} class.
 *
 * @author jtoledo@google.com (Julian Toledo)
 */
public class RunnableDownloaderTest {

  @Spy
  private RunnableDownloader mockedRunnableDownloader;

  @Before
  public void setUp() throws ValidationException {

    AdWordsSession adWordsSession =
        new AdWordsSession.Builder().withEndpoint("http://www.google.com")
            .withDeveloperToken("DeveloperToken")
            .withClientCustomerId("123")
            .withUserAgent("UserAgent")
            .withOAuth2Credential( new GoogleCredential.Builder().build())
            .build();

    ReportDefinition reportDefinition = new ReportDefinition();
    reportDefinition.setReportName("");
    reportDefinition.setDateRangeType(ReportDefinitionDateRangeType.CUSTOM_DATE);
    reportDefinition.setReportType(ReportDefinitionReportType.ACCOUNT_PERFORMANCE_REPORT);
    reportDefinition.setDownloadFormat(DownloadFormat.GZIPPED_CSV);
    reportDefinition.setIncludeZeroImpressions(false);
    reportDefinition.setSelector(new Selector());

    Collection<File> results = Lists.newArrayList();

    mockedRunnableDownloader =
        new RunnableDownloader(5, 0, 10, 1L, reportDefinition, adWordsSession, results);

    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testRun()
      throws FileNotFoundException,
      ValidationException,
      ReportException,
      ReportDownloadResponseException,
      IOException {

    doReturn(new File("")).when(mockedRunnableDownloader).downloadFileToFileSystem();

    mockedRunnableDownloader.run();
    verify(mockedRunnableDownloader, times(1)).downloadFileToFileSystem();
    verify(mockedRunnableDownloader, times(1)).run();
  }

  @Test
  public void testRun_retries()
      throws FileNotFoundException,
      ValidationException,
      ReportException,
      ReportDownloadResponseException,
      IOException {
    ReportException ex =
        new ReportException("ReportException", new Exception("UnitTest Retryable Server Error"));
    doThrow(ex).when(mockedRunnableDownloader).downloadFileToFileSystem();

    mockedRunnableDownloader.run();
    verify(mockedRunnableDownloader, times(5)).downloadFileToFileSystem();
    verify(mockedRunnableDownloader, times(1)).run();
  }

  /**
   * Test for DetailedReportDownloadResponseExceptions
   * 
   * a DetailedReportDownloadResponseException breaks the retries,
   * tipically is an invalid field in the definition.
   */
  @Test
  public void testRun_retriesStop()
      throws FileNotFoundException,
      ValidationException,
      ReportException,
      ReportDownloadResponseException,
      IOException {

    DetailedReportDownloadResponseException ex = new DetailedReportDownloadResponseException(
        404, "Testing");
    ex.setType("DetailedReportDownloadResponseException");
    ex.setTrigger("UnitTest non-Retryable Server Error");
    doThrow(ex).when(mockedRunnableDownloader).downloadFileToFileSystem();

    mockedRunnableDownloader.run();
    verify(mockedRunnableDownloader, times(1)).downloadFileToFileSystem();
    verify(mockedRunnableDownloader, times(1)).run();
  }
}
