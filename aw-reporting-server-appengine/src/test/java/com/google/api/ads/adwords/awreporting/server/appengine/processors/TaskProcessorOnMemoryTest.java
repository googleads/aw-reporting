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

package com.google.api.ads.adwords.awreporting.server.appengine.processors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.google.api.ads.adwords.awreporting.model.entities.Report;
import com.google.api.ads.adwords.awreporting.model.entities.ReportAccount;
import com.google.api.ads.adwords.awreporting.model.persistence.EntityPersister;
import com.google.api.ads.adwords.awreporting.server.appengine.processors.TaskProcessorOnMemory;
import com.google.api.ads.adwords.awreporting.util.FileUtil;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionDateRangeType;
import com.google.api.ads.adwords.lib.utils.ReportDownloadResponseException;
import com.google.api.ads.adwords.lib.utils.ReportException;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.ads.common.lib.exception.ValidationException;

import org.junit.Before;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * Test case for the {@link TaskProcessorOnMemory} class.
 *
 * @author jtoledo@google.com (Julian Toledo)
 */
public class TaskProcessorOnMemoryTest {

  private static final String CSV_FILE_PATH = "src/test/resources/account.csv";
  
  @Mock
  private EntityPersister mockedEntitiesPersister;

  @Spy
  private TaskProcessorOnMemory<ReportAccount> taskProcessorOnMemory;
  
  @Captor
  ArgumentCaptor<List<? extends Report>> reportEntitiesCaptor;

  @Before
  public void setUp() throws OAuthException, IOException, ValidationException,
  ReportException, ReportDownloadResponseException {

    taskProcessorOnMemory = new TaskProcessorOnMemory<ReportAccount>("888", 2602198216L, null, ReportDefinitionDateRangeType.CUSTOM_DATE,
        "20140101", "20140131", "123", 5, ReportAccount.class);

    MockitoAnnotations.initMocks(this);

    taskProcessorOnMemory.setPersister(mockedEntitiesPersister);

    // Load CSV into memory and compress it
    FileInputStream fis = new FileInputStream(CSV_FILE_PATH);
    ByteArrayOutputStream baos = new ByteArrayOutputStream(2048);
    GZIPOutputStream gzipOut = new GZIPOutputStream(baos);
    FileUtil.copy(fis, gzipOut);
    gzipOut.flush();
    gzipOut.close();
    doReturn(new ByteArrayInputStream(baos.toByteArray())).when(taskProcessorOnMemory).getReportInputStream();
  }

  //@Test
  public void test_run() {
    taskProcessorOnMemory.run();
    verify(taskProcessorOnMemory, times(1)).run();
    verify(mockedEntitiesPersister, times(2)).persistReportEntities(reportEntitiesCaptor.capture());

    List<? extends Report> persistedReports = reportEntitiesCaptor.getValue();
    assertEquals(2, persistedReports.size());
    assertEquals(ReportAccount.class, persistedReports.get(0).getClass());
    assertTrue(123L == persistedReports.get(0).getTopAccountId());
    assertTrue(2602198216L == persistedReports.get(0).getAccountId());
    assertTrue(ReportDefinitionDateRangeType.CUSTOM_DATE.value() == persistedReports.get(0).getDateRangeType());
    assertTrue("20140101" == persistedReports.get(0).getDateStart());
    assertTrue("20140131" == persistedReports.get(0).getDateEnd());
  }
}
