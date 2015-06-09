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

package com.google.api.ads.adwords.awreporting.processors.onfile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.google.api.ads.adwords.awreporting.model.csv.AnnotationBasedMappingStrategy;
import com.google.api.ads.adwords.awreporting.model.entities.Report;
import com.google.api.ads.adwords.awreporting.model.entities.ReportAccount;
import com.google.api.ads.adwords.awreporting.model.persistence.EntityPersister;
import com.google.api.ads.adwords.awreporting.model.util.ModifiedCsvToBean;
import com.google.api.ads.adwords.awreporting.processors.onfile.RunnableProcessorOnFile;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionDateRangeType;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import au.com.bytecode.opencsv.bean.MappingStrategy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Test case for the {@link RunnableProcessorOnFile} class.
 *
 * @author jtoledo@google.com (Julian Toledo)
 */
public class RunnableProcessorOnFileTest {

  private static final String CSV_FILE_PATH =
      "src/test/resources/csv/reportDownload-ACCOUNT_PERFORMANCE_REPORT-2602198216-1370030134500.report";
  
  private File file = new File(CSV_FILE_PATH);
  private File newFile = new File(CSV_FILE_PATH + ".gunzip");

  @Mock
  private EntityPersister mockedEntitiesPersister;

  @Spy
  private RunnableProcessorOnFile<ReportAccount> runnableProcessorOnFile;
  
  @Captor
  ArgumentCaptor<List<? extends Report>> reportEntitiesCaptor;

  @Before
  public void setUp() throws IOException {

    ModifiedCsvToBean<ReportAccount> csvToBean = new ModifiedCsvToBean<ReportAccount>();
    MappingStrategy<ReportAccount> mappingStrategy =
        new AnnotationBasedMappingStrategy<ReportAccount>(ReportAccount.class);

    FileUtils.copyFile(file, newFile);

    runnableProcessorOnFile = new RunnableProcessorOnFile<ReportAccount>(file,
        csvToBean, mappingStrategy, ReportDefinitionDateRangeType.CUSTOM_DATE,
        "20140101", "20140131", "123", mockedEntitiesPersister, 5);

    MockitoAnnotations.initMocks(this);

    runnableProcessorOnFile.setPersister(mockedEntitiesPersister);
  }

  @Test
  public void testRun() {
    runnableProcessorOnFile.run();
    
    verify(runnableProcessorOnFile, times(1)).run();
    
    verify(mockedEntitiesPersister, times(2)).persistReportEntities(
        reportEntitiesCaptor.capture());
    newFile.delete();
    
    assertTrue(reportEntitiesCaptor.getValue() instanceof ArrayList);
    assertTrue(reportEntitiesCaptor.getValue().get(0) instanceof ReportAccount);
    assertEquals(reportEntitiesCaptor.getValue().get(0).getAccountId(), new Long(1232198123));
  }
}
