package com.google.api.ads.adwords.awreporting.alerting.csv;

import java.util.HashMap;
import java.util.Map;
import com.google.api.ads.adwords.jaxws.v201502.cm.ReportDefinitionReportType;

public class ReportMappingStrategy {
  private ReportDefinitionReportType reportType;
  private Map<String, String> mapper;  // display field name -> field name
  
  public ReportMappingStrategy(ReportDefinitionReportType reportType) {
    this.reportType = reportType;
    mapper = new HashMap<String, String>();
  }
  
  public void addMapping(String displayFieldName, String fieldName) {
    mapper.put(displayFieldName, fieldName);
  }
  
  public String getFieldName(String displayFieldName) {
    return mapper.get(displayFieldName);
  }
}
