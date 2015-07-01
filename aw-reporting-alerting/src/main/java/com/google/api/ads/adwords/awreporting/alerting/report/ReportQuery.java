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

package com.google.api.ads.adwords.awreporting.alerting.report;

import com.google.api.ads.adwords.awreporting.alerting.util.ConfigTags;
import com.google.gson.JsonObject;

/**
 * Generator of AWQL report query.
 * Refer to AWQL guide for more info: https://developers.google.com/adwords/api/docs/guides/awql
 * 
 * @author zhuoc@google.com (Zhuo Chen)
 */
public class ReportQuery {  
  private String reportType;
  private String fields;
  private String conditions;  // optional
  private String timeRange;   // optional
  
  /**
   * Constructor
   * 
   * @param config the JSON configuration of the report query
   */
  public ReportQuery(JsonObject config) {
    reportType = config.get(ConfigTags.ReportQuery.REPORT_TYPE).getAsString();
    fields = config.get(ConfigTags.ReportQuery.FIELDS).getAsString();
    
    if (config.has(ConfigTags.ReportQuery.CONDITIONS)) {
      conditions = config.get(ConfigTags.ReportQuery.CONDITIONS).getAsString();
    }
    
    if (config.has(ConfigTags.ReportQuery.TIME_RANGE)) {
      timeRange = config.get(ConfigTags.ReportQuery.TIME_RANGE).getAsString();
    }
  }
  
  public String getReportType() {
    return reportType;
  }
  
  /**
   * Generates AWQL report query.
   * 
   * @return AWQL report query string.
   */
  public String generateAWQL() {
    String query = "SELECT " + fields + " FROM " + reportType;
    if (conditions != null) {
      query += " WHERE " + conditions;
    }
    if (timeRange != null) {
      query += " DURING " + timeRange;
    }
    
    return query;
  }
}
