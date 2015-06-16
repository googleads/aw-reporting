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

package com.google.api.ads.adwords.awreporting.alerting.rule;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.google.api.ads.adwords.awreporting.alerting.report.ReportEntry;
import com.google.gson.JsonObject;

/**
* An alert rule implementation that adds account monthly budget information of the account.
* Note that it must provide a constructor that takes a JsonObject parameter, and must be stateless.
* 
* The JSON config should look like:
* {
*   "RuleClass": "AddAccountMonthlyBudget"
* }
* 
* @author zhuoc@google.com (Zhuo Chen)
*/
public class AddAccountMonthlyBudget implements AlertRule {

  /**
  * Constructor
  * 
  * @param config the JsonObject for the alert rule configuration.
  */
  public AddAccountMonthlyBudget(JsonObject config) {
  }
  
  /**
  * Extend new columns name for account monthly budget in the report.
  */
  @Override
  public List<String> newReportHeaderFields() {
   return Arrays.asList("AccountMonthlyBudget");
  }
  
  /**
  * Append new field value for account monthly budget into the report entry.
  * 
  * As a demonstration, it just randomly chooses a monthly budget between 0 / 50 /100 dollars,
  * with 0 meaning unlimited.
  * 
  * @param entry the report entry to append new field values.
  */
  @Override
  public void appendReportEntryFields(ReportEntry entry) {
    int multiplier = new Random().nextInt(3);
    long budget = multiplier * 50 * 1000000;  // micro amount
    entry.appendFieldValue(String.valueOf(budget));
  
  }
  
  /**
  * Do not alert for accounts with budgets being well-utilized
  */
  @Override
  public boolean shouldRemoveReportEntry(ReportEntry entry) {
    final long budget = Long.parseLong(entry.getFieldValue("AccountMonthlyBudget"));
    
    // If budget is unlimited, don't alert.
    if (0 == budget) {
      return true;
    }
    
    // If it's in the first 3 days of the month, don't alert
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    int day = cal.get(Calendar.DAY_OF_MONTH);
    if (day <= 3) {
      return true;
    }
    
    // If average daily spend is more than 60% of available budget, don't alert 
    final long cost = Long.parseLong(entry.getFieldValue("Cost"));
    int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    
    // Since cost / budget are in micro amount, don't care much about integer division result
    if (cost / day > 0.6 * budget / daysInMonth) {
      return true;
    }
    
    // All other cases, fire alert
    return false;
  }
}
