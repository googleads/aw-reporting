package com.google.api.ads.adwords.awreporting.alerting.report;

import com.google.gson.JsonObject;

public class ReportQuery {
  private static String REPORT_TYPE_TAG = "ReportType";
  private static String FIELDS_TAG = "Fields";
  private static String PREDICATE_TAG = "Predicate";
  private static String TIME_RANGE_TAG = "TimeRange";
  
  private String reportType;
  private String fields;
  private String predicate;  // optional
  private String timeRange;  // optional
  
  public ReportQuery(JsonObject config) {
    reportType = config.get(REPORT_TYPE_TAG).getAsString();
    fields = config.get(FIELDS_TAG).getAsString();
    if (config.has(PREDICATE_TAG)) {
      predicate = config.get(PREDICATE_TAG).getAsString();
    }
    if (config.has(TIME_RANGE_TAG)) {
      timeRange = config.get(TIME_RANGE_TAG).getAsString();
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
