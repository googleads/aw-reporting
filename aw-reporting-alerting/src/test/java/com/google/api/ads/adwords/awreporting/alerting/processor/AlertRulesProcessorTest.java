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

import java.io.IOException;
import java.util.Arrays;

import com.google.api.ads.adwords.awreporting.alerting.report.ReportData;
import com.google.api.ads.adwords.awreporting.alerting.report.ReportEntry;
import com.google.api.ads.adwords.awreporting.alerting.rule.AlertRule;
import com.google.api.ads.adwords.awreporting.alerting.rule.DummyAlertRule;
import com.google.api.ads.adwords.awreporting.alerting.util.ConfigTags;
import com.google.api.ads.adwords.awreporting.alerting.util.TestEntitiesGenerator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

/**
* Test case for the {@link AlertRulesProcessor} class.
* 
 * @author zhuoc@google.com (Zhuo Chen)
*/
public class AlertRulesProcessorTest {
  
  @Mock
  private DummyAlertRule mockedDummyAlertRule1, mockedDummyAlertRule2;
  
  @Spy
  private AlertRulesProcessor alertRulesProcessor;
  
  @Before
  public void setUp() {
    JsonArray configs = new JsonArray();
    JsonObject alertRuleConfig = new JsonObject();
    alertRuleConfig.addProperty(ConfigTags.Rule.RULE_CLASS, "DummyAlertRule");
    configs.add(alertRuleConfig);
    configs.add(alertRuleConfig);
    alertRulesProcessor = new AlertRulesProcessor(configs);

    MockitoAnnotations.initMocks(this);
  }
  
  @Test
  public void testConstruction() {
    assertEquals(alertRulesProcessor.getRulesCount(), 2);
  }
  
  @Test
  public void testProcessReport() throws IOException {
    ReportData reportData = TestEntitiesGenerator.getTestReportData();
    AlertRule[] dummyAlertRules = {mockedDummyAlertRule1, mockedDummyAlertRule2};
    alertRulesProcessor.setAlertRules(Arrays.asList(dummyAlertRules));
    alertRulesProcessor.processReport(reportData);
    
    verify(alertRulesProcessor, times(1)).processReport(Mockito.<ReportData>anyObject());
    verify(alertRulesProcessor, times(2)).extendReportData(Mockito.<AlertRule>anyObject(), Mockito.<ReportData>anyObject());
    verify(alertRulesProcessor, times(2)).filterReportData(Mockito.<AlertRule>anyObject(), Mockito.<ReportData>anyObject());
    
    // Test report data has 7 entries
    verify(mockedDummyAlertRule1, times(1)).newReportHeaderFields();
    verify(mockedDummyAlertRule1, times(7)).appendReportEntryFields(Mockito.<ReportEntry>anyObject());
    verify(mockedDummyAlertRule1, times(7)).shouldRemoveReportEntry(Mockito.<ReportEntry>anyObject());
    
    verify(mockedDummyAlertRule2, times(1)).newReportHeaderFields();
    verify(mockedDummyAlertRule2, times(7)).appendReportEntryFields(Mockito.<ReportEntry>anyObject());
    verify(mockedDummyAlertRule2, times(7)).shouldRemoveReportEntry(Mockito.<ReportEntry>anyObject());
  }
}
