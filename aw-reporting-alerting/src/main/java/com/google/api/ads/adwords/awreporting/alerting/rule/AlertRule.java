package com.google.api.ads.adwords.awreporting.alerting.rule;

import java.util.List;
import java.util.Map;

import com.google.api.ads.adwords.awreporting.alerting.report.ReportEntry;
import com.google.gson.JsonObject;

public interface AlertRule {
  // All implementations must have a constructor with a JsonObject parameter
  
  // Names of new fields to extend
  public List<String> newReportHeaderFields();
  
  // Append new fields in the report entry
  public void appendReportEntryFields(ReportEntry entry);
  
  // Whether a report entry should be filtered from final resulting alerts
  public boolean shouldRemoveReportEntry(ReportEntry entry);
}
