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
import java.util.Date;

import com.google.api.ads.adwords.awreporting.alerting.report.ReportEntry;
import com.google.api.ads.adwords.awreporting.alerting.util.ConfigTags;
import com.google.gson.JsonObject;

/**
 * An alert action implementation that writes alert messages in console.
 * Note that it must provide a constructor that takes a JsonObject parameter, and must not modify report entries.
 * 
 * The JSON config should look like:
 * {
 *   "RuleClass": "SimpleConsoleWriter"
 * }
 * 
 * @author zhuoc@google.com (Zhuo Chen)
 */
public class SimpleConsoleWriter implements AlertAction {

  /**
   * Constructor
   * @param config the JsonObject for the alert action configuration.
   */
  public SimpleConsoleWriter(JsonObject config) {
  }

  /**
   * Initialization action: print some header lines.
   */
  @Override
  public void initializeAction() {
    Date now = new Date();
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    System.out.println("Alerts generated at " + dateFormat.format(now) + ":");
  }
  
  /**
   * Process a report entry, and write its alert message in console.
   * 
   * @param entry the report entry to process.
   */
  @Override
  public void processReportEntry(ReportEntry entry) {
    System.out.println(entry.getFieldValue(ConfigTags.ALERT_MESSAGE));
  }

  /**
   * Finalization action: print some foot lines.
   */
  @Override
  public void finalizeAction() {
    System.out.println();
    System.out.println();
  }
}
