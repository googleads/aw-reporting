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

package com.google.api.ads.adwords.awreporting.downloader;

import static org.junit.Assert.assertEquals;

import com.google.api.ads.adwords.awreporting.ReportProcessingException;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.client.AdWordsSession.ImmutableAdWordsSession;
import com.google.api.ads.adwords.lib.jaxb.v201806.ReportDefinition;
import com.google.api.ads.adwords.lib.jaxb.v201806.ReportDefinitionReportType;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.common.collect.ImmutableSet;
import java.io.File;
import java.util.Collection;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.MockitoAnnotations;

/**
 * Test case for the {@code MultipleClientReportDownloader} class.
 */
@RunWith(JUnit4.class)
public class MultipleClientReportDownloaderTest {

  private MultipleClientReportDownloader multipleClientReportDownloader;
  private ReportDefinition reportDefinition;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    // does not matter which report definition type
    reportDefinition = new ReportDefinition();
    reportDefinition.setReportType(ReportDefinitionReportType.PLACEHOLDER_REPORT);
    
    multipleClientReportDownloader =
        new MultipleClientReportDownloader(20) {
          @Override
          protected CallableReportDownloader genCallableReportDownloader(
              ImmutableAdWordsSession session, ReportDefinition reportDefinition) {
            return new CallableReportDownloader(session, reportDefinition) {
              @Override
              public File call() {
                return null;
              }
            };
          }
        };
  }

  /** Tests downloading reports. */
  @Test
  public void testDownloadReports() throws ReportProcessingException {

    AdWordsSession.Builder sessionBuilder =
      new AdWordsSession.Builder().withEndpoint("http://www.google.com")
        .withDeveloperToken("DeveloperToken")
        .withClientCustomerId("123")
        .withUserAgent("UserAgent")
        .withOAuth2Credential(new GoogleCredential.Builder().build());

    Set<Long> cids = ImmutableSet.of(1L, 2L, 3L, 4L, 5L);
    Collection<File> results =
        multipleClientReportDownloader.downloadReports(sessionBuilder, reportDefinition, cids);

    assertEquals(cids.size(), results.size());
  }
}
