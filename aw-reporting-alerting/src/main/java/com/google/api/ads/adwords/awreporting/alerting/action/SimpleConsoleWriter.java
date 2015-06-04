package com.google.api.ads.adwords.awreporting.alerting.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.api.ads.adwords.awreporting.alerting.report.ReportEntry;
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
    System.out.println(entry.getFieldValue(AlertAction.ALERT_MESSAGE_TAG));
  }

  @Override
  public void finalizeAction() {
    System.out.println();
    System.out.println();
  }
}
