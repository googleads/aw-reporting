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

package com.google.api.ads.adwords.awreporting.alerting.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.ads.adwords.awreporting.alerting.report.ReportEntry;
import com.google.gson.JsonObject;

/*
Config:
  {
    "ActionClass": "EmailSender",
    "Subject": "Low impression accounts",
    "CC": "abc@example.com,xyz@example.com"
  }
*/

public class EmailSender implements AlertAction {
  
  class AlertEmail {
    
    public String to;
    public Map<String, List<String>> alertsMap;  // account ID -> alert messages
    
    public AlertEmail(String to) {
      this.to = to;
      this.alertsMap = new HashMap<String, List<String>>();
    }
    
    public void addAlert(String accountId, String alertMessage) {
      List<String> alerts = alertsMap.get(accountId);
      if (null == alerts) {
        alerts = new ArrayList<String>();
        alertsMap.put(accountId, alerts);
      }
      
      alerts.add(alertMessage);
    }
    
    public boolean shouldFireAlert() {
      return !alertsMap.isEmpty();
    }
    
    public String print(String subject, String from, List<String> ccList) {
      StringBuffer sb = new StringBuffer();
      sb.append("===== Alert email starts =====");
      sb.append(NEWLINE);
      sb.append(NEWLINE);
      
      sb.append("From: " + from);
      sb.append(NEWLINE);
      
      sb.append("To: " + to);
      sb.append(NEWLINE);
      
      if (null != ccList && !ccList.isEmpty()) {
        boolean firstCc = true;
        for (String cc : ccList) {
          if (firstCc) {
            sb.append("Cc: ");
            firstCc = false;
          }
          else {
            sb.append("    ");
          }
          sb.append(cc);
          sb.append(NEWLINE);
        }
      }
      
      Date now = new Date();
      DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
      sb.append("Date: " + dateFormat.format(now));
      sb.append(NEWLINE);
      sb.append(NEWLINE);
      
      for (Map.Entry<String, List<String>> entry : alertsMap.entrySet()) {
        String accountId = entry.getKey();
        List<String> alertMessages = entry.getValue();
        sb.append("Account ID: " + accountId);
        sb.append(NEWLINE);
        for (String alertMessage : alertMessages) {
          sb.append("  " + alertMessage);
          sb.append(NEWLINE);
        }
        sb.append(NEWLINE);
      }

      sb.append("===== Alert email ends =====");
      sb.append(NEWLINE);
      return sb.toString();
    }
  }

  private static String NEWLINE = System.getProperty("line.separator");
  private static String FROM        = "aw-report-alerting@example.com";
  
  private static String SUBJECT_TAG = "Subject";
  private static String CC_TAG      = "CC";
  
  // Each email for one receiver (account manager)
  private Map<String, AlertEmail> emailsMap;
  private String subject;
  private List<String> ccList;
  
  public EmailSender(JsonObject config) {
    subject = config.get(SUBJECT_TAG).getAsString();
    ccList = null;
    if (config.has(CC_TAG)) {
      ccList = Arrays.asList(config.get(CC_TAG).getAsString().split(","));
    }
    
    emailsMap = new HashMap<String, AlertEmail>();
  }

  @Override
  public void initializeAction() {
    // Nothing to do
  }

  @Override
  public void processReportEntry(ReportEntry entry) {
    String to = entry.getFieldValue("AccountManagerEmail");
    AlertEmail email = emailsMap.get(to);
    if (null == email) {
      email = new AlertEmail(to);
      emailsMap.put(to, email);
    }

    String accountId = entry.getFieldValue("ExternalCustomerId");
    String alertMessage = entry.getFieldValue("AlertMessage");
    email.addAlert(accountId, alertMessage);
  }

  @Override
  public void finalizeAction() {
    StringBuffer sb = new StringBuffer();
    if (!emailsMap.isEmpty()) {
      for (AlertEmail email : emailsMap.values()) {
        if (email.shouldFireAlert()) {
          sb.append(email.print(subject, FROM, ccList));
        }
      }
    }

    System.out.println(sb);
  }
}
