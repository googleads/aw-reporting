//Copyright 2015 Google Inc. All Rights Reserved.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.

package com.google.api.ads.adwords.awreporting.alerting.processor;

import com.google.api.ads.adwords.awreporting.alerting.action.AlertAction;
import com.google.api.ads.adwords.awreporting.alerting.report.ReportData;
import com.google.api.ads.adwords.awreporting.alerting.report.ReportEntry;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
* This {@link Runnable} implements the core logic to run alert actions on reports.
*
* The {@link List} passed to this runner is considered to be thread safe and won't be modified.
*
* @author zhuoc@google.com (Zhuo Chen)
*/
public class RunnableAlertActionProcessor implements Runnable {

  private static final Logger LOGGER = Logger.getLogger(RunnableAlertActionProcessor.class);
  
  private CountDownLatch latch;
  
  private final AlertAction action;
  private final List<ReportData> reports;
  
  /**
  * C'tor.
  *
  * @param action the AlertAction to use
  * @param reports the list of ReportData to apply the action
  */
  public RunnableAlertActionProcessor(AlertAction action, List<ReportData> reports) {
    this.action = action;
    this.reports = reports;
  }
 
  
  /**
   * Executes the API call to run alert actions on the report that was given when this {@code Runnable}
   * was created.
   *
   * The processing blocks this thread until it is finished, and also does the file copying.
   *
   * @see java.lang.Runnable#run()
   */
  @Override
  public void run() {
  
    try {
      LOGGER.debug("Start running AlertAction \"" + action.getClass().getSimpleName() + "\" for " + reports.size() + " reports");
      
      // Run alert action on each report
      action.initializeAction();
      for (ReportData report : reports) {
        final Map<String, Integer> mapping = report.getIndiceMapping();
        for (List<String> entry : report.getEntries()) {
          final ReportEntry curEntry = new ReportEntry(entry, mapping);
          action.processReportEntry(curEntry);
        }
      }
      action.finalizeAction();
      
      LOGGER.debug("... success.");  
    } catch (Exception e) {
      String errorMessage = "Error running AlertAction \"" + action.getClass().getSimpleName() + "\": " + e.getMessage();
      LOGGER.error(errorMessage);
      e.printStackTrace();
    } finally {
      if (null != this.latch) {
        this.latch.countDown();
      }
    }
  }
  
  /**
   * @param latch the latch to set
   */
  public void setLatch(CountDownLatch latch) {
    this.latch = latch;
  }
}