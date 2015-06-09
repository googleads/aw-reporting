// Copyright 2014 Google Inc. All Rights Reserved.
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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.google.api.ads.adwords.awreporting.model.csv.AnnotationBasedMappingStrategy;
import com.google.api.ads.adwords.awreporting.model.entities.Report;
import com.google.api.ads.adwords.awreporting.model.entities.ReportAccount;
import com.google.api.ads.adwords.awreporting.model.persistence.EntityPersister;
import com.google.api.ads.adwords.awreporting.model.util.ModifiedCsvToBean;
import com.google.api.ads.adwords.awreporting.processors.onmemory.RunnableProcessorOnMemory;
import com.google.api.ads.adwords.awreporting.util.FileUtil;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionDateRangeType;
import com.google.api.ads.adwords.lib.utils.ReportDownloadResponseException;
import com.google.api.ads.adwords.lib.utils.ReportException;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import au.com.bytecode.opencsv.bean.MappingStrategy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * Test case for the {@link RunnableProcessorOnMemory} class.
 *
 * @author jtoledo@google.com (Julian Toledo)
 */
public class RunnableProcessorOnMemoryTest {

  private static final String CSV_FILE_PATH =
      "src/test/resources/csv/reportDownload-ACCOUNT_PERFORMANCE_REPORT-2602198216-1370030134500.report";

  @Mock
  private EntityPersister mockedEntitiesPersister;

  @Spy
  private RunnableProcessorOnMemory<ReportAccount> runnableProcessorOnMemory;
  
  @Captor
  ArgumentCaptor<List<? extends Report>> reportEntitiesCaptor;

  @Before
  public void setUp() throws IOException, ValidationException,
  ReportException, ReportDownloadResponseException {

    ModifiedCsvToBean<ReportAccount> csvToBean = new ModifiedCsvToBean<ReportAccount>();
    MappingStrategy<ReportAccount> mappingStrategy =
        new AnnotationBasedMappingStrategy<ReportAccount>(ReportAccount.class);
    
    AdWordsSession adWordsSession =
        new AdWordsSession.Builder().withEndpoint("http://www.google.com")
            .withDeveloperToken("DeveloperToken")
            .withClientCustomerId("123")
            .withUserAgent("UserAgent")
            .withOAuth2Credential( new GoogleCredential.Builder().build())
            .build();

    runnableProcessorOnMemory = new RunnableProcessorOnMemory<ReportAccount>(1232198123L, adWordsSession, null,
        csvToBean, mappingStrategy, ReportDefinitionDateRangeType.CUSTOM_DATE,
        "20140101", "20140131", "123", mockedEntitiesPersister, 5);

    MockitoAnnotations.initMocks(this);

    runnableProcessorOnMemory.setPersister(mockedEntitiesPersister);

    // Load CSV into memory and compress it
    FileInputStream fis = new FileInputStream(CSV_FILE_PATH);
    ByteArrayOutputStream baos = new ByteArrayOutputStream(2048);
    GZIPOutputStream gzipOut = new GZIPOutputStream(baos);
    FileUtil.copy(fis, gzipOut);
    gzipOut.flush();
    gzipOut.close();
    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    baos.flush();
    baos.close();
    doReturn(bais).when(
        runnableProcessorOnMemory).getReportInputStream();
  }

  @Test
  public void testRun() {
    runnableProcessorOnMemory.run();

    verify(runnableProcessorOnMemory, times(1)).run();

    verify(mockedEntitiesPersister, times(2)).persistReportEntities(
        reportEntitiesCaptor.capture());

    assertTrue(reportEntitiesCaptor.getValue() instanceof ArrayList);
    assertTrue(reportEntitiesCaptor.getValue().get(0) instanceof ReportAccount);
    assertEquals(reportEntitiesCaptor.getValue().get(0).getAccountId(), new Long(1232198123));
  }
}
