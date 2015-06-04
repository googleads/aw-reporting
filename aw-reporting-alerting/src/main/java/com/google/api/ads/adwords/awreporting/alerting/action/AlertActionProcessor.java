package com.google.api.ads.adwords.awreporting.alerting.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.api.ads.adwords.awreporting.alerting.report.ReportData;
import com.google.api.ads.adwords.awreporting.alerting.report.ReportEntry;
import com.google.api.ads.adwords.awreporting.alerting.rule.AlertRule;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class AlertActionProcessor {
  private final static String ACTION_CLASS_TAG = "ActionClass";
  
  private List<AlertAction> actions;
  
  public AlertActionProcessor(JsonArray configs) {
    actions = new ArrayList<AlertAction>(configs.size());
    for (JsonElement config : configs) {
      AlertAction action = getActionObject(config.getAsJsonObject());
      if (null == action) {
        throw new JsonParseException("Wrong AlertAction config: " + config.toString());
      }
      actions.add(action);
    }
  }
  
  private AlertAction getActionObject(JsonObject config) {
    String className = config.get(ACTION_CLASS_TAG).getAsString();
    if (!className.contains(".")) {
      className = "com.google.api.ads.adwords.awreporting.alerting.action." + className;
    }
    
    AlertAction action = null;
    try {
      Object obj = Class.forName(className).getDeclaredConstructor(new Class[] {JsonObject.class}).newInstance(config);
      if (obj instanceof AlertAction) {
        action = (AlertAction)obj;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    
    return action;
  }
  
  public void processReports(List<ReportData> reports) {    
    for (AlertAction action : actions) {
      action.initializeAction();
      
      for (ReportData report : reports) {
        final Map<String, Integer> mapping = report.getMapping();
        for (List<String> entry : report.getEntries()) {
          ReportEntry curEntry = new ReportEntry(entry, mapping);
          action.processReportEntry(curEntry);
        }
      }
      
      action.finalizeAction();
    }
  }
}
