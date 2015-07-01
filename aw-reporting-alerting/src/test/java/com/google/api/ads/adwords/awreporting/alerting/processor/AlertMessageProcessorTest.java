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

import com.google.api.ads.adwords.awreporting.alerting.report.ReportData;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

/**
* Test case for the {@link AlertMessageProcessor} class.
* 
 * @author zhuoc@google.com (Zhuo Chen)
*/
public class AlertMessageProcessorTest {
  
  @Mock
  private ReportData mockedReportData;
    
  @Spy
  private AlertMessageProcessor alertMessageProcessor;
  
  @Before
  public void setUp() {
    final String alertMessageTemplate = 
        "Account \"{AccountDescriptiveName}\" (ID \"{ExternalCustomerId}\") has " +
        "{Impressions} impressions and {Clicks} clicks.";
    alertMessageProcessor = new AlertMessageProcessor(alertMessageTemplate);
    
    MockitoAnnotations.initMocks(this);
  }
  
  @Test
  public void testRun() {
    alertMessageProcessor.processReport(mockedReportData);;
   
    verify(alertMessageProcessor, times(1)).processReport(Mockito.<ReportData>anyObject());
    verify(mockedReportData, times(1)).appendNewField(Mockito.anyString());
  }
}
