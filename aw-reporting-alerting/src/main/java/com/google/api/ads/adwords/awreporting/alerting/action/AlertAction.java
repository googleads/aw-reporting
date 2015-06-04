package com.google.api.ads.adwords.awreporting.alerting.action;

import com.google.api.ads.adwords.awreporting.alerting.report.ReportEntry;

public interface AlertAction {  
  // All implementations must have a constructor with a JsonObject parameter
  
  // Process each report entry, it could perform the action here; or record some info
  // and perform some aggregated action in finalizeAction().
  public void initializeAction();
  public void processReportEntry(ReportEntry entry);
  public void finalizeAction();
}