//Copyright 2014 Google Inc. All Rights Reserved.
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

import com.google.api.ads.adwords.awreporting.alerting.report.ReportData;
import com.google.api.ads.adwords.awreporting.alerting.util.TestEntitiesGenerator;
import com.google.api.ads.adwords.jaxws.v201502.cm.ReportDefinitionReportType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
* Test case for the {@link RunnableReportProcessorOnFile} class.
*/
public class RunnableReportProcessorOnFileTest {
  private File file = new File(TestEntitiesGenerator.getTestReportFilePath());
  
  private List<ReportData> outputReports = new ArrayList<ReportData>();
  
  private RunnableReportProcessorOnFile runnableReportProcessorOnFile;

  @Mock
  private AlertRulesProcessor mockedAlertRulesProcessor;
  
  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    runnableReportProcessorOnFile = Mockito.spy(
        new RunnableReportProcessorOnFile(file,
            TestEntitiesGenerator.getTestFiledsMapping(),
            "Test alert",
            ReportDefinitionReportType.ACCOUNT_PERFORMANCE_REPORT,
            mockedAlertRulesProcessor,
            outputReports));
  }
  
  @Test
  public void testRun() {
    runnableReportProcessorOnFile.run();
    verify(runnableReportProcessorOnFile, times(1)).run();
    
    // AlertRulesProcessor should process report once
    verify(mockedAlertRulesProcessor, times(1)).processReport(Mockito.<ReportData>anyObject());
    
    assertEquals(outputReports.size(), 1);
  }
}
