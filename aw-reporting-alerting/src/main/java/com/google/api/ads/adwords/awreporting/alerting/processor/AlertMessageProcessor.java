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

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.api.ads.adwords.awreporting.alerting.report.ReportData;
import com.google.api.ads.adwords.awreporting.alerting.util.ConfigTags;

/**
 * Alert message processor is responsible for reading the alert message template from alert config,
 * and replace the placeholders with corresponding field values for each report entry.
 * 
 * Note this class is NOT stateless, so one instance instance cannot be shared among multiple threads.
 *
 * @author zhuoc@google.com (Zhuo Chen)
 */
public class AlertMessageProcessor {
  private final static String MESSAGE_REGEX = "\\{\\w+\\}";
  private final static Pattern messagePattern = Pattern.compile(MESSAGE_REGEX);
  
  private Matcher messageMatcher;
  
  /**
   * Constructor.
   *
   * @param alertMessageTemplate the alert message template from the alert configuration.
   */
  public AlertMessageProcessor(String alertMessageTemplate) {
    messageMatcher = messagePattern.matcher(alertMessageTemplate);
  }
  
  /**
   * Process the report to add the alert message column
   *
   * @param report the ReportData to process (for each entry, add alert message column).
   */
  public void processReport(ReportData report) {
    report.appendNewField(ConfigTags.ALERT_MESSAGE);

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
        if (!report.getIndiceMapping().containsKey(fieldName)) {
          throw new IllegalArgumentException("Placeholder value is not available in downloaded report " + curMatch);
        }
        
        int index = report.getFieldIndex(fieldName);
        messageMatcher.appendReplacement(sb, entry.get(index));
      }
      messageMatcher.appendTail(sb);
      entry.add(sb.toString());
    }
  }
}
