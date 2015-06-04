package com.google.api.ads.adwords.awreporting.alerting.dataextender;

import com.google.api.ads.adwords.awreporting.alerting.report.ReportData;
import com.google.gson.JsonObject;

public abstract class DataExtender {
  
  public abstract void loadConfig(JsonObject config);
  
  // append data to report entires (could integrate from external source)
  public abstract void addData(ReportData report);
}
