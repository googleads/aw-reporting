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

package com.google.api.ads.adwords.awreporting.alerting.processor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.api.ads.adwords.awreporting.alerting.report.ReportData;
import com.google.api.ads.adwords.awreporting.alerting.report.ReportEntry;
import com.google.api.ads.adwords.awreporting.alerting.rule.AlertRule;
import com.google.api.ads.adwords.awreporting.alerting.util.ConfigTags;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Alert rules processor is responsible for processing the list of alert rules on this ReportData.
 * It MUST be stateless, as the instance will be shared among multiple threads.
 * It is optional, it won't be instantiated if there's no "Rules" config for alert.
 *
 * @author zhuoc@google.com (Zhuo Chen)
 */
public class AlertRulesProcessor {
  private List<AlertRule> rules;
  
  /**
   * Constructor.
   *
   * @param configs the JSON array of alert rules configurations
   */
  public AlertRulesProcessor(JsonArray configs) {
    rules = new ArrayList<AlertRule>(configs.size());
    for (JsonElement config : configs) {
      AlertRule rule = getRuleObject(config.getAsJsonObject());
      if (null == rule) {
        throw new JsonParseException("Wrong AlertRule config: " + config.toString());
      }
      rules.add(rule);
    }
  }
  
  /**
   * Construct the AlertRule object according to the JSON configuration
   *
   * @param config the JSON configuration of the alert rule
   */
  protected AlertRule getRuleObject(JsonObject config) {
    String className = config.get(ConfigTags.Rule.RULE_CLASS).getAsString();
    if (!className.contains(".")) {
      className = "com.google.api.ads.adwords.awreporting.alerting.rule." + className;
    }
    
    AlertRule rule = null;
    try {
      Object obj = Class.forName(className).getConstructor(new Class[] {JsonObject.class}).newInstance(config);
      if (obj instanceof AlertRule) {
        rule = (AlertRule)obj;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    
    return rule;
  }
  
  /**
   * Extend ReportData (add more columns) using the specified alert rule
   *
   * @param rule the AlertRule to use
   * @param report the ReportData to extend
   */
  protected void extendReportData(AlertRule rule, ReportData report) {
    List<String> reportHeaderFields = rule.newReportHeaderFields();
    if (null != reportHeaderFields) {
      for (String newHeaderField : reportHeaderFields) {
        report.appendNewField(newHeaderField);
      }
    }
    
    final Map<String, Integer> mapping = report.getIndiceMapping();
    for (List<String> entry : report.getEntries()) {
      ReportEntry curEntry = new ReportEntry(entry, mapping);
      rule.appendReportEntryFields(curEntry);
    }
  }
  
  /**
   * Filter unwanted ReportData entries using the specified alert rule
   *
   * @param rule the AlertRule to use
   * @param report the ReportData to filter
   */
  protected void filterReportData(AlertRule rule, ReportData report) {
    final Map<String, Integer> mapping = report.getIndiceMapping();
    for (Iterator<List<String>> iter = report.getEntries().iterator(); iter.hasNext(); ) {
      List<String> entry = iter.next();
      ReportEntry curEntry = new ReportEntry(entry, mapping);
      if (rule.shouldRemoveReportEntry(curEntry)) {
        iter.remove();
      }
    }
  }
  
  /**
   * Process the ReportData with the list of alert rules
   *
   * @param report the ReportData to process (extend data and filter entries)
   */
  public void processReport(ReportData report) {
    // The execution is in the same thread
    for (AlertRule rule : rules) {
      extendReportData(rule, report);
      filterReportData(rule, report);
    }
  }
  
  public int getRulesCount() {
    return rules.size();
  }
  
  /**
   * For Mockito testing
   */
  protected void setAlertRules(List<AlertRule> rules) {
    this.rules = rules;
  }
}
