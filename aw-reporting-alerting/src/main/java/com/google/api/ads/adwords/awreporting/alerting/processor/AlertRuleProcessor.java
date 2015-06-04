package com.google.api.ads.adwords.awreporting.alerting.processor;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.api.ads.adwords.awreporting.alerting.report.ReportData;
import com.google.api.ads.adwords.awreporting.alerting.report.ReportEntry;
import com.google.api.ads.adwords.awreporting.alerting.rule.AlertRule;
import com.google.api.ads.adwords.awreporting.alerting.util.ConfigTags;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class AlertRuleProcessor {
  private final static String MESSAGE_REGEX = "\\{\\w+\\}";
  private final static Pattern messagePattern = Pattern.compile(MESSAGE_REGEX);
  
  private String messageTemplate;
  private Matcher messageMatcher;
  private AlertRule rule;
  
  public AlertRuleProcessor(JsonObject config) {
    messageTemplate = config.get(ConfigTags.Rule.ALERT_MESSAGE).getAsString();
    messageMatcher = messagePattern.matcher(messageTemplate);
    
    rule = getRuleObject(config);
    if (null == rule) {
      throw new JsonParseException("Wrong AlertRule config: " + config.toString());
    }
  }
  
  private AlertRule getRuleObject(JsonObject config) {
    String className = config.get(ConfigTags.Rule.RULE_CLASS).getAsString();
    if (!className.contains(".")) {
      className = "com.google.api.ads.adwords.awreporting.alerting.rule." + className;
    }
    
    AlertRule rule = null;
    try {
      Object obj = Class.forName(className).getDeclaredConstructor(new Class[] {JsonObject.class}).newInstance(config);
      if (obj instanceof AlertRule) {
        rule = (AlertRule)obj;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    
    return rule;
  }
  
  private void extendReportData(ReportData report) {
    List<String> reportHeaderFields = rule.newReportHeaderFields();
    if (null != reportHeaderFields) {
      for (String newHeaderField : reportHeaderFields) {
        report.addNewField(newHeaderField);
      }
    }
    
    final Map<String, Integer> mapping = report.getMapping();
    for (List<String> entry : report.getEntries()) {
      ReportEntry curEntry = new ReportEntry(entry, mapping);
      rule.appendReportEntryFields(curEntry);
    }
  }
  
  
  private void filterReportData(ReportData report) {
    final Map<String, Integer> mapping = report.getMapping();
    for (Iterator<List<String>> iter = report.getEntries().iterator(); iter.hasNext(); ) {
      List<String> entry = iter.next();
      ReportEntry curEntry = new ReportEntry(entry, mapping);
      if (rule.shouldRemoveReportEntry(curEntry)) {
        iter.remove();
      }
    }
  }
  
  private void generateAlertMessages(ReportData report) {
    report.addNewField(ConfigTags.Rule.ALERT_MESSAGE);

    // replace all {...} placeholders to the values for each entry
    for (List<String> entry : report.getEntries()) {
      messageMatcher.reset();
      StringBuffer sb = new StringBuffer();
      while (messageMatcher.find()) {
        String curMatch = messageMatcher.group();
        int length = curMatch.length();
        if (length <= 2 || curMatch.charAt(0) != '{' || curMatch.charAt(length-1) != '}') {
          throw new IllegalArgumentException("Alert message template contains invalid placeholder: " + curMatch);
        }
        
        String fieldName = curMatch.substring(1, length-1);
        if (!report.getMapping().containsKey(fieldName)) {
          throw new IllegalArgumentException("Placeholder value is not available in downloaded report " + curMatch);
        }
        
        int index = report.getMapping().get(fieldName).intValue();
        messageMatcher.appendReplacement(sb, entry.get(index));
      }
      messageMatcher.appendTail(sb);
      entry.add(sb.toString());
    }
  }
  
  public void processReport(ReportData report) {
    extendReportData(report);
    filterReportData(report);
    generateAlertMessages(report);
  };
}
