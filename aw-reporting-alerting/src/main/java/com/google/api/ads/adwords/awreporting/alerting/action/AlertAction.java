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

/**
 * This is the interface that every alert action should implement.
 * 
 * Important notes:
 * 1. All implementations MUST have a constructor with a JsonObject parameter, otherwise it will fail to load.
 * 2. All implementations MUST NOT modify ReportEntry object in processReportEntry().
 *    
 * @author zhuoc@google.com (Zhuo Chen)
 */
public interface AlertAction {
  
  /**
   * Perform initialization action before processing any report entry.
   */
  public void initializeAction();
  
  /**
   * Process a report entry. It could either perform some action here, or record some info
   * and perform some aggregated action in {@link #finalizeAction()} method.
   * 
   * @param entry the report entry to process.
   */
  public void processReportEntry(ReportEntry entry);
  
  /**
   * Perform finalization action after processing all report entries.
   */
  public void finalizeAction();
}