package com.google.api.ads.adwords.awreporting.alerting.rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.google.api.ads.adwords.awreporting.alerting.report.ReportEntry;
import com.google.gson.JsonObject;

/*
Config:
 {
   "RuleClass": "AddAccountManagerRule"
 }
*/

public class AddAccountManagerRule implements AlertRule {
  
  class AccountManager {
    public String name;
    public String email;
    
    AccountManager(String name, String email) {
      this.name = name;
      this.email = email;
    }
  }
  
  private List<AccountManager> accountManagers;

  public AddAccountManagerRule(JsonObject config) {
    // add sample account managers
    accountManagers = new ArrayList<AccountManager>();
    accountManagers.add(new AccountManager("Josh G.", "josh@example.com"));
    accountManagers.add(new AccountManager("Michael F.", "michael@example.com"));
  }
  
  private AccountManager getAccountManager(String accountId) {
    // Look up accountId and returns the corresponding account manager
    // This is for demo only, so it just randomly assigns account to an AM.
    assert(accountManagers.size() > 0);
    
    int index = new Random().nextInt(accountManagers.size());
    return accountManagers.get(index);
  }
  
  @Override
  public List<String> newReportHeaderFields() {
    return Arrays.asList("AccountManagerName", "AccountManagerEmail");
  }

  @Override
  public void appendReportEntryFields(ReportEntry entry) {
    String accountId = entry.getFieldValue("ExternalCustomerId");
    AccountManager am = getAccountManager(accountId);
    entry.addFieldValues(Arrays.asList(am.name, am.email));
  }

  @Override
  public boolean shouldRemoveReportEntry(ReportEntry entry) {
    return false;
  }

}
