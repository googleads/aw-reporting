package com.google.api.ads.adwords.awreporting.alerting.report;

import com.google.api.ads.adwords.awreporting.alerting.util.ConfigTags;
import com.google.gson.JsonObject;

public class ReportQuery {  
  private String reportType;
  private String fields;
  private String predicate;  // optional
  private String timeRange;  // optional
  
  public ReportQuery(JsonObject config) {
    reportType = config.get(ConfigTags.ReportQuery.REPORT_TYPE).getAsString();
    fields = config.get(ConfigTags.ReportQuery.FIELDS).getAsString();
    
    if (config.has(ConfigTags.ReportQuery.PREDICATE)) {
      predicate = config.get(ConfigTags.ReportQuery.PREDICATE).getAsString();
    }
    
    if (config.has(ConfigTags.ReportQuery.TIME_RANGE)) {
      timeRange = config.get(ConfigTags.ReportQuery.TIME_RANGE).getAsString();
    }
  }
  
  public String getReportType() {
    return reportType;
  }
  
  public String generateAWQL() {
    String query = "SELECT " + fields + " FROM " + reportType;
    if (predicate != null) {
      query += " WHERE " + predicate;
    }
    if (timeRange != null) {
      query += " DURING " + timeRange;
    }
    
    return query;
  }
}
