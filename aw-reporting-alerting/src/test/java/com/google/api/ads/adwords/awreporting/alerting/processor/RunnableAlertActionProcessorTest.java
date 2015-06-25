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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.google.api.ads.adwords.awreporting.alerting.action.AlertAction;
import com.google.api.ads.adwords.awreporting.alerting.report.ReportData;
import com.google.api.ads.adwords.awreporting.alerting.report.ReportEntry;
import com.google.api.ads.adwords.awreporting.alerting.util.TestEntitiesGenerator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
* Test case for the {@link RunnableAlertActionProcessor} class.
* 
 * @author zhuoc@google.com (Zhuo Chen)
*/
public class RunnableAlertActionProcessorTest {
  
  private RunnableAlertActionProcessor runnableAlertActionProcessor;
  
  @Mock
  private AlertAction mockedAlertAction;
  
  @Before
  public void setUp() throws IOException{
    MockitoAnnotations.initMocks(this);
  
    // add 2 test reports, each having 7 entries
    List<ReportData> reports = new ArrayList<ReportData>();
    reports.add(TestEntitiesGenerator.getTestReportData());
    reports.add(TestEntitiesGenerator.getTestReportData());
    
    runnableAlertActionProcessor = Mockito.spy(new RunnableAlertActionProcessor(mockedAlertAction, reports));
  }
  
  @Test
  public void testRun() {
    runnableAlertActionProcessor.run();
    verify(runnableAlertActionProcessor, times(1)).run();
    
    // Should process 2 reports, 2 * 7 = 14 entries
    verify(mockedAlertAction, times(1)).initializeAction();
    verify(mockedAlertAction, times(14)).processReportEntry(Mockito.<ReportEntry>anyObject());
    verify(mockedAlertAction, times(1)).finalizeAction();
  }
}
