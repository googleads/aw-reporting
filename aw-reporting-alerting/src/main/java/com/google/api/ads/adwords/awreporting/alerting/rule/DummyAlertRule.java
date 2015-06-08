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

import java.util.List;

import com.google.api.ads.adwords.awreporting.alerting.report.ReportEntry;
import com.google.gson.JsonObject;

/**
 * A dummy alert rule implementation that doesn't modify the report.
 * Note that it must provide a constructor that takes a JsonObject parameter, and must be stateless.
 *
 * The JSON config should look like:
 * {
 *   "RuleClass": "DummyAlertRule"
 * }
 * 
 * @author zhuoc@google.com (Zhuo Chen)
 */
public class DummyAlertRule implements AlertRule {

  /**
   * Constructor
   * 
   * @param config the JsonObject for the alert rule configuration.
   */
  public DummyAlertRule(JsonObject config) {
  }

  /**
   * Do not extend columns in the report.
   */
  @Override
  public List<String> newReportHeaderFields() {
    return null;
  }

  /**
   * Do not append new field values into report entry.
   */
  @Override
  public void appendReportEntryFields(ReportEntry entry) {
  }

  /**
   * Do not remove any report entry from result alerts.
   */
  @Override
  public boolean shouldRemoveReportEntry(ReportEntry entry) {
    return false;
  }  
}
