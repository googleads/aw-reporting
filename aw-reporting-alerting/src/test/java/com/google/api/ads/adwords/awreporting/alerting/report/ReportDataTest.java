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
import static org.junit.Assert.fail;

import com.google.api.ads.adwords.awreporting.alerting.util.TestEntitiesGenerator;
import com.google.api.ads.adwords.jaxws.v201502.cm.ReportDefinitionReportType;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
* Test case for the {@link ReportData} class.
* 
 * @author zhuoc@google.com (Zhuo Chen)
*/
public class ReportDataTest {

  /**
  * Test the CSV file loading into ReportData object
  */
  @Test
  public void testLoadReportData()  {
    try {
      ReportData report = TestEntitiesGenerator.getTestReportData();
      
      assertEquals(report.getAlertName(), "Test alert");
      assertEquals(report.getReportType(), ReportDefinitionReportType.ACCOUNT_PERFORMANCE_REPORT);
      
      // Check indice mappings
      int iExternalCustomerId     = report.getFieldIndex("ExternalCustomerId");
      int iDate                   = report.getFieldIndex("Date");
      int iAccountDescriptiveName = report.getFieldIndex("AccountDescriptiveName");
      int iCost                   = report.getFieldIndex("Cost");
      int iClicks                 = report.getFieldIndex("Clicks");
      int iImpressions            = report.getFieldIndex("Impressions");
      int iConvertedClicks        = report.getFieldIndex("ConvertedClicks");
      int iCtr                    = report.getFieldIndex("Ctr");
      
      assertEquals(iExternalCustomerId,     0);
      assertEquals(iDate,                   1);
      assertEquals(iAccountDescriptiveName, 2);
      assertEquals(iCost,                   3);
      assertEquals(iClicks,                 4);
      assertEquals(iImpressions,            5);
      assertEquals(iConvertedClicks,        6);
      assertEquals(iCtr,                    7);
      
      int entryCount = report.getEntries().size();
      assertEquals(entryCount, 7);
      
      // Check first entry
      List<String> firstEntry = report.getEntry(0);
      assertEquals(firstEntry.get(iExternalCustomerId),     "1232198123");
      assertEquals(firstEntry.get(iDate),                   "2013-05-01");
      assertEquals(firstEntry.get(iAccountDescriptiveName), "Le Test");
      assertEquals(firstEntry.get(iCost),                   "1420000");
      assertEquals(firstEntry.get(iClicks),                 "10");
      assertEquals(firstEntry.get(iImpressions),            "1978");
      assertEquals(firstEntry.get(iConvertedClicks),        "0");
      assertEquals(firstEntry.get(iCtr),                    "0.51%");
      
      // Check last entry
      List<String> lastEntry = report.getEntry(entryCount - 1);
      assertEquals(lastEntry.get(iExternalCustomerId),     "1232198123");
      assertEquals(lastEntry.get(iDate),                   "2013-05-10");
      assertEquals(lastEntry.get(iAccountDescriptiveName), "Le Test");
      assertEquals(lastEntry.get(iCost),                   "750000");
      assertEquals(lastEntry.get(iClicks),                 "4");
      assertEquals(lastEntry.get(iImpressions),            "2793");
      assertEquals(lastEntry.get(iConvertedClicks),        "0");
      assertEquals(lastEntry.get(iCtr),                    "0.14%");
    }
    catch (IOException e) {
      e.printStackTrace();
      fail("Unexpected exception thrown");
    }
  }
}
