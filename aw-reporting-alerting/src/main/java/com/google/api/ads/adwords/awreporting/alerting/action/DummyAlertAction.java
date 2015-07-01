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

import com.google.api.ads.adwords.awreporting.alerting.report.ReportEntry;
import com.google.gson.JsonObject;

/**
 * A dummy alert action implementation that doesn't fire any action.
 * Note that it must provide a constructor that takes a JsonObject parameter, and must not modify report data.
 * 
 * The JSON config should look like:
 * {
 *   "RuleClass": "DummyAlertAction"
 * }
 * @author zhuoc@google.com (Zhuo Chen)
 */
public class DummyAlertAction implements AlertAction {
  
  /**
   * Constructor
   * 
   * @param config the JsonObject for the alert action configuration.
   */
  public DummyAlertAction(JsonObject config) {
  }
  
  /**
   * Do nothing on initialization.
   */
  @Override
  public void initializeAction() {
  }
  
  /**
   * Do nothing on processing report entries.
   */
  @Override
  public void processReportEntry(ReportEntry entry) {
  }
  
  /**
   * Do nothing on finalization.
   */
  @Override
  public void finalizeAction() {
  }
}