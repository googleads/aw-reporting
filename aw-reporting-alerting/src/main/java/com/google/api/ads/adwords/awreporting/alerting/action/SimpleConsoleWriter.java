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

public class SimpleConsoleWriter implements AlertAction {

  public SimpleConsoleWriter(JsonObject config) {
    // Do nothing
  }

  @Override
  public void initializeAction() {
    Date now = new Date();
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    System.out.println("Alerts generated at " + dateFormat.format(now) + ":");
  }
  
  @Override
  public void processReportEntry(ReportEntry entry) {
    System.out.println(entry.getFieldValue(ConfigTags.ALERT_MESSAGE));
  }

  @Override
  public void finalizeAction() {
    System.out.println();
    System.out.println();
  }
}
