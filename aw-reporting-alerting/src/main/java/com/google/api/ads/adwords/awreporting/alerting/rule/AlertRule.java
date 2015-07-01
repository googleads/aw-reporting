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

/**
 * This is the interface that every alert rule should implement.
 * 
 * Important notes:
 * 1. All implementations MUST have a constructor with a JsonObject parameter, otherwise it will fail to load.
 * 2. All implementations MUST be stateless, as the instance will be shared among multiple threads.
 *    That is, the subclasses either have no member fields, or the member fields are immutable.
 *    
 * @author zhuoc@google.com (Zhuo Chen)
 */
public interface AlertRule {
  
  /**
   * Return new column names that the alert rule will extend in the report.
   */
  public List<String> newReportHeaderFields();
  
  /**
   * Append new field values into the report entry.
   * 
   * @param entry the report entry to append new field values.
   */
  public void appendReportEntryFields(ReportEntry entry);
  
  /**
   * Check whether a report entry should be removed from result alerts.
   * 
   * @param entry the report entry to check.
   * @return whether this report entry should be removed from result alerts.
   */
  public boolean shouldRemoveReportEntry(ReportEntry entry);
}
