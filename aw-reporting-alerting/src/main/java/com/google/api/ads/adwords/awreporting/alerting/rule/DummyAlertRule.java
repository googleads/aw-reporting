package com.google.api.ads.adwords.awreporting.alerting.rule;

import java.util.List;

import com.google.api.ads.adwords.awreporting.alerting.report.ReportEntry;
import com.google.gson.JsonObject;

public class DummyAlertRule implements AlertRule {

  public DummyAlertRule(JsonObject config) {
  }

  @Override
  public List<String> newReportHeaderFields() {
    return null;
  }

  @Override
  public void appendReportEntryFields(ReportEntry entry) {
  }

  @Override
  public boolean shouldRemoveReportEntry(ReportEntry entry) {
    return false;
  }  
}
