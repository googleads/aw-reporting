// Copyright 2015 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.awreporting.alerting.rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.google.api.ads.adwords.awreporting.alerting.report.ReportEntry;
import com.google.gson.JsonObject;

/**
 * An alert rule implementation that adds account manager information of the account.
 * Note that it must provide a constructor that takes a JsonObject parameter, and must be stateless.
 * 
 * The JSON config should look like:
 * {
 *   "RuleClass": "AddAccountManagerRule"
 * }
 * 
 * @author zhuoc@google.com (Zhuo Chen)
 */
public class AddAccountManagerRule implements AlertRule {
  
  /**
   * Helper inner class for account manager
   */
  class AccountManager {
    public String name;
    public String email;
    
    AccountManager(String name, String email) {
      this.name = name;
      this.email = email;
    }
  }
  
  private List<AccountManager> accountManagers;

  /**
   * Constructor
   * 
   * @param config the JsonObject for the alert rule configuration.
   */
  public AddAccountManagerRule(JsonObject config) {
    // add sample account managers
    accountManagers = new ArrayList<AccountManager>();
    accountManagers.add(new AccountManager("Josh G.", "josh@example.com"));
    accountManagers.add(new AccountManager("Michael F.", "michael@example.com"));
  }
  
  /**
   * Get account manager of the specified account.
   * For demonstration only, it just randomly choose an account manager.
   * 
   * @param accountId the account ID.
   * @return the account manager of the specified account ID.
   */
  private AccountManager getAccountManager(String accountId) {
    // Look up accountId and returns the corresponding account manager
    // This is for demo only, so it just randomly assigns account to an AM.
    assert(accountManagers.size() > 0);
    
    int index = new Random().nextInt(accountManagers.size());
    return accountManagers.get(index);
  }
  
  /**
   * Extend new columns names for account manager in the report.
   */
  @Override
  public List<String> newReportHeaderFields() {
    return Arrays.asList("AccountManagerName", "AccountManagerEmail");
  }

  /**
   * Append new field values for account manager into the report entry.
   * 
   * @param entry the report entry to append new field values.
   */
  @Override
  public void appendReportEntryFields(ReportEntry entry) {
    String accountId = entry.getFieldValue("ExternalCustomerId");
    AccountManager am = getAccountManager(accountId);
    entry.appendFieldValues(Arrays.asList(am.name, am.email));
  }

  /**
   * Do not remove any entry from result alerts.
   */
  @Override
  public boolean shouldRemoveReportEntry(ReportEntry entry) {
    return false;
  }

}
