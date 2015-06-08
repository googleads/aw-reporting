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

package com.google.api.ads.adwords.awreporting.alerting.util;

/**
 * Helper class that defines the tags in JSON alerts configuration.
 * 
 * @author zhuoc@google.com (Zhuo Chen)
 *
 */
public final class ConfigTags {
  public static final String ALERTS          = "Alerts";
  public static final String ALERT_NAME      = "Name";
  public static final String REPORT_QUERY    = "ReportQuery";
  public static final String RULES           = "Rules";
  public static final String ALERT_MESSAGE   = "AlertMessage";
  public static final String ACTIONS         = "Actions";
  
  // For "ReportQuery" segment.
  public static final class ReportQuery {
    public static final String REPORT_TYPE   = "ReportType";
    public static final String FIELDS        = "Fields";
    public static final String PREDICATE     = "Predicate";
    public static final String TIME_RANGE    = "TimeRange";
  }
  
  //For "Rules" segment.
  public static final class Rule {
    public static final String RULE_CLASS    = "RuleClass";
  }
  
  //For "Actions" segment.
  public static final class Actions {
    public static final String ACTION_CLASS  = "ActionClass";
  }
}
