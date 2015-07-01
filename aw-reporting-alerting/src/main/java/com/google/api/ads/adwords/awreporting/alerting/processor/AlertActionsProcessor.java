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
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.google.api.ads.adwords.awreporting.alerting.action.AlertAction;
import com.google.api.ads.adwords.awreporting.alerting.report.ReportData;
import com.google.api.ads.adwords.awreporting.alerting.util.ConfigTags;
import com.google.common.base.Stopwatch;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Alert actions processor is responsible for processing the list of alert actions on all ReportData objects.
 * It will spawn a thread for each alert action, which runs on all ReportData objects because some times it
 * need to get aggregate stats from all reports.
 * The list of ReportData is shared among multiple threads, so it MUST NOT alter any ReportData object.
 *
 * @author zhuoc@google.com (Zhuo Chen)
 */
public class AlertActionsProcessor {  
  private static final Logger LOGGER = Logger.getLogger(AlertActionsProcessor.class);

  private List<AlertAction> actions;
  
  /**
   * Constructor.
   *
   * @param configs the JSON array of alert actions configurations
   */
  public AlertActionsProcessor(JsonArray configs) {
    actions = new ArrayList<AlertAction>(configs.size());
    for (JsonElement config : configs) {
      AlertAction action = getActionObject(config.getAsJsonObject());
      if (null == action) {
        throw new JsonParseException("Wrong AlertAction config: " + config.toString());
      }
      actions.add(action);
    }
  }
  
  /**
   * Construct the AlertAction object according to the JSON configuration
   *
   * @param config the JSON configuration of the alert action
   */
  protected AlertAction getActionObject(JsonObject config) {
    String className = config.get(ConfigTags.Action.ACTION_CLASS).getAsString();
    if (!className.contains(".")) {
      className = "com.google.api.ads.adwords.awreporting.alerting.action." + className;
    }
    
    AlertAction action = null;
    try {
      Object obj = Class.forName(className).getConstructor(new Class[] {JsonObject.class}).newInstance(config);
      if (obj instanceof AlertAction) {
        action = (AlertAction)obj;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    
    return action;
  }
  
  /**
   * Process the ReportData with the list of alert actions.
   *
   * @param report the ReportData to run each alert action against.
   */
  public void processReports(List<ReportData> reports) {
    // Create one thread for each AlertAction, and process all reports
    Stopwatch stopwatch = Stopwatch.createStarted();

    final int count = actions.size();
    final CountDownLatch latch = new CountDownLatch(count);
    ExecutorService executorService = Executors.newFixedThreadPool(count);

    for (AlertAction action: actions) {
      RunnableAlertActionProcessor actionProcessor = new RunnableAlertActionProcessor(action, reports);
      actionProcessor.setLatch(latch);
      executorService.execute(actionProcessor);
    }

    try {
      latch.await();
    } catch (InterruptedException e) {
      LOGGER.error(e.getMessage());
      e.printStackTrace();
    }
    executorService.shutdown();
    stopwatch.stop();
    
    LOGGER.info("*** Processed " + actions.size() + " actions on " + reports.size() + " reports in "
        + (stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000) + " seconds.");
  }
  
  public int getActionsSize() {
    return actions.size();
  }
}
