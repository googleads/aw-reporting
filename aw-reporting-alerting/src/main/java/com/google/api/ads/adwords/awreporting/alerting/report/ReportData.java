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

/**
 * Report data corresponding to a downloaded report for one account.
 * 
 * @author zhuoc@google.com (Zhuo Chen)
 */
public class ReportData {  
  // use List instead of native array[], since report data will be enriched
  private List<String> header;
  private List<List<String>> entries;
  
  // Field name -> index mapping
  private Map<String, Integer> indiceMapping;
  
  private String alertName;
  private ReportDefinitionReportType reportType;
  
  /**
   * Constructor.
   *
   * @param headerArray the array of report headers.
   * @param entriesArray the 2-dimensional array of report entries.
   * @param fieldsMapping the "display field name" -> "field name" mapping of this report type.
   * @param alertName the name of the alert used to download this report.
   * @param reportType the type of this report.
   */
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
    
    indiceMapping = new HashMap<String, Integer>(columns);
    for (int i = 0; i < columns; ++i) {
      String fieldName = fieldsMapping.get(headerArray[i]);
      assert (null != fieldName);
      header.add(fieldName);
      this.indiceMapping.put(fieldName, Integer.valueOf(i));
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
  
  /**
   * Get the i-th entry of this report.
   * 
   * @param index the index of the returning entry
   * @return the i-th entry of this report.
   */
  public List<String> getEntry(int index) {
    return entries.get(index);
  }
  
  public Map<String, Integer> getIndiceMapping() {
    return indiceMapping;
  }
  
  /**
   * Get the column index of the specified column name
   * @param columnName the column name
   * @return the index of the specified column
   */
  public int getFieldIndex(String columnName) {
    return indiceMapping.get(columnName).intValue();
  }
  
  /**
   * Append a new column in the report.
   * 
   * @param fieldName the name of the new field.
   */
  public void appendNewField(String fieldName) {
    assert(indiceMapping.size() == header.size());
    
    if (!indiceMapping.containsKey(fieldName)) {
      int newIndex = header.size();
      header.add(fieldName);
      indiceMapping.put(fieldName, Integer.valueOf(newIndex));
    }
  }
  
  public String getAlertName() {
    return alertName;
  }
  
  public ReportDefinitionReportType getReportType() {
    return reportType;
  }
  
  /**
   * Print the content of the report. This is for debugging purpose.
   */
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
