// Copyright 2015 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.api.ads.adwords.awreporting.alerting.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.google.api.ads.adwords.jaxws.v201502.cm.ReportDefinitionReportType;

public class ReportData {  
  // use List instead of native array[], since report data will be enriched
  private List<String> header;
  private List<List<String>> entries;
  // Field -> index mapping
  private Map<String, Integer> mapping;
  private String alertName;
  private ReportDefinitionReportType reportType;
  
  public ReportData(String[] headerArray,
      List<String[]> entriesArray,
      Map<String, String> fieldsMapping,
      String alertName,
      ReportDefinitionReportType reportType) {
    final int columns = headerArray.length;
    header = new ArrayList<String>(columns);
    
    final int rows = entriesArray.size();
    entries = new ArrayList<List<String>>(rows);
    for (int i = 0; i < rows; ++i) {
      // need to create a new ArrayList object which is extendible
      entries.add(new ArrayList<String>(Arrays.asList(entriesArray.get(i))));
    }
    
    mapping = new HashMap<String, Integer>(columns);
    for (int i = 0; i < columns; ++i) {
      String fieldName = fieldsMapping.get(headerArray[i]);
      assert (null != fieldName);
      header.add(fieldName);
      this.mapping.put(fieldName, Integer.valueOf(i));
    }
    
    this.alertName = alertName;
    this.reportType = reportType;
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
  
  public String getAlertName() {
    return alertName;
  }
  
  public ReportDefinitionReportType getReportType() {
    return reportType;
  }
  
  public void print() {
    System.out.println(reportType.value() + " for alert \"" + alertName + "\":");
    System.out.println("Header:");
    System.out.println(StringUtils.arrayToCommaDelimitedString(header.toArray()));
    System.out.println("Data:");
    for (List<String> entry : entries) {
      System.out.println(StringUtils.arrayToCommaDelimitedString(entry.toArray()));
    }
    System.out.println();
  }
}
