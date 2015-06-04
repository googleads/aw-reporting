package com.google.api.ads.adwords.awreporting.alerting.util;

public final class ConfigTags {
  public static final String ALERTS          = "Alerts";
  public static final String ALERT_NAME      = "Name";
  public static final String REPORT_QUERY    = "ReportQuery";
  public static final String RULE            = "Rule";
  public static final String ACTIONS         = "Actions";
  
  public static final class ReportQuery {
    public static final String REPORT_TYPE   = "ReportType";
    public static final String FIELDS        = "Fields";
    public static final String PREDICATE     = "Predicate";
    public static final String TIME_RANGE    = "TimeRange";
  }
  
  public static final class Rule {
    public static final String RULE_CLASS    = "RuleClass";
    public static final String ALERT_MESSAGE = "AlertMessage";
  }
  
  public static final class Actions {
    public static final String ACTION_CLASS  = "ActionClass";
  }
}
