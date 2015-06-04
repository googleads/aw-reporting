package com.google.api.ads.adwords.awreporting.alerting.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.google.api.ads.adwords.awreporting.alerting.csv.ReportMappingStrategy;

public class ReportData {
  // Field -> index mapping
  private Map<String, Integer> mapping;
  
  // use List instead of native array[], since report data will be enriched
  private List<String> header;
  private List<List<String>> entries;

  //private List<List<String>> entries = Collections.synchronizedList(new ArrayList<List<String>>());
  // needs synchronized(entries) on iterate, not on add/delete
  
  public ReportData(String[] headerArray, List<String[]> entriesArray, ReportMappingStrategy fieldsMapping) {
    int columns = headerArray.length;
    header = new ArrayList<String>(columns);
    mapping = new HashMap<String, Integer>(columns);
    
    for (int i = 0; i < columns; ++i) {
      String fieldName = fieldsMapping.getFieldName(headerArray[i]);
      header.add(fieldName);
      this.mapping.put(fieldName, Integer.valueOf(i));
    }
    
    int rows = entriesArray.size();
    entries = new ArrayList<List<String>>(rows);
    for (int i = 0; i < rows; ++i) {
      // need to create a new ArrayList object which is extendible
      entries.add(new ArrayList<String>(Arrays.asList(entriesArray.get(i))));
    }
  }
  
  public List<String> getHeader() {
    return header;
  }
  
  public List<List<String>> getEntries() {
    return entries;
  }
  
  public List<String> getEntry(int index) {
    return entries.get(index);
  }
  
  public Map<String, Integer> getMapping() {
    return mapping;
  }
  
  public int getFieldIndex(String columnName) {
    return mapping.get(columnName).intValue();
  }
  
  public void addNewField(String fieldName) {
    assert(mapping.size() == header.size());
    
    if (!mapping.containsKey(fieldName)) {
      int newIndex = header.size();
      header.add(fieldName);
      mapping.put(fieldName, Integer.valueOf(newIndex));
    }
  }
  
  public void print() {
    System.out.println("Header:");
    System.out.println(StringUtils.arrayToCommaDelimitedString(header.toArray()));
    System.out.println("Data:");
    for (List<String> entry : entries) {
      System.out.println(StringUtils.arrayToCommaDelimitedString(entry.toArray()));
    }
  }
}
