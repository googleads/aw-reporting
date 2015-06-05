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

public interface AlertRule {
  // All implementations must have a constructor with a JsonObject parameter
  
  // All implementations must be stateless, as the instance will be shared among downloading threads.
  // That is, the subclasses either have no member fields, or the member fields are immutable.
  
  // Names of new fields to extend
  public List<String> newReportHeaderFields();
  
  // Append new fields in the report entry
  public void appendReportEntryFields(ReportEntry entry);
  
  // Whether a report entry should be filtered from final resulting alerts
  public boolean shouldRemoveReportEntry(ReportEntry entry);
}
