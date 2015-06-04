package com.google.api.ads.adwords.awreporting.alerting.report;

import java.util.List;
import java.util.Map;

public class ReportEntry {
  private List<String> entry;
  private Map<String, Integer> mapping;
  
  public ReportEntry(List<String> entry, Map<String, Integer> mapping) {
    this.entry = entry;
    this.mapping = mapping;
  }
  
  public String getFieldValue(String fieldName) {
    if (mapping.containsKey(fieldName)) {
      return entry.get(mapping.get(fieldName).intValue());
    }
    return null;
  }
  
  public void addFieldValue(String fieldValue) {
    entry.add(fieldValue);
  }
  
  public void addFieldValues(List<String> fieldValues) {
    entry.addAll(fieldValues);
  }
}
