//Copyright 2015 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.awreporting.alerting.report;

import static org.junit.Assert.assertEquals;

import com.google.api.ads.adwords.awreporting.alerting.util.TestEntitiesGenerator;
import com.google.gson.JsonObject;

import org.junit.Test;

/**
* Test case for the {@link ReportQuery} class.
* 
 * @author zhuoc@google.com (Zhuo Chen)
*/
public class ReportQueryTest {

  /**
  * Test the AWQL query generation from JSON config
  */
  @Test
  public void testReportQueryGeneration() {
    // Test full AWQL query
    JsonObject jsonConfig = TestEntitiesGenerator.getTestReportQueryConfig();
    ReportQuery reportQuery1 = new ReportQuery(jsonConfig);
    
    String expectedAwqlStr1 = "SELECT ExternalCustomerId,AccountDescriptiveName,Id,KeywordText,Impressions,Ctr " +
                              "FROM KEYWORDS_PERFORMANCE_REPORT " +
                              "WHERE Impressions > 100 AND Ctr < 0.05 " +
                              "DURING YESTERDAY";
    assertEquals(reportQuery1.getReportType(), "KEYWORDS_PERFORMANCE_REPORT");
    assertEquals(reportQuery1.generateAWQL(), expectedAwqlStr1);
                          
    // Test AWQL query without "WHERE" clause
    jsonConfig.remove("Conditions");
    ReportQuery reportQuery2 = new ReportQuery(jsonConfig);
    
    String expectedAwqlStr2 = "SELECT ExternalCustomerId,AccountDescriptiveName,Id,KeywordText,Impressions,Ctr " +
                              "FROM KEYWORDS_PERFORMANCE_REPORT " +
                              "DURING YESTERDAY";
    assertEquals(reportQuery2.getReportType(), "KEYWORDS_PERFORMANCE_REPORT");
    assertEquals(reportQuery2.generateAWQL(), expectedAwqlStr2);
    
    // Test AWQL query without "DURING" clause
    jsonConfig.remove("TimeRange");
    ReportQuery reportQuery3 = new ReportQuery(jsonConfig);
    
    String expectedAwqlStr3 = "SELECT ExternalCustomerId,AccountDescriptiveName,Id,KeywordText,Impressions,Ctr " +
                              "FROM KEYWORDS_PERFORMANCE_REPORT";
    assertEquals(reportQuery3.getReportType(), "KEYWORDS_PERFORMANCE_REPORT");
    assertEquals(reportQuery3.generateAWQL(), expectedAwqlStr3);
  }
}
