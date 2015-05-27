//Copyright 2014 Google Inc. All Rights Reserved.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.

package com.google.api.ads.adwords.awreporting.server.appengine.processors;

import com.google.api.ads.adwords.awreporting.model.entities.Report;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinition;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionDateRangeType;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionReportType;
import com.google.appengine.api.taskqueue.DeferredTask;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import java.util.Collection;
import java.util.logging.Logger;

/**
 * This class creates tasks to download each report 
 *
 * @author jtoledo@google.com (Julian Toledo)
 */
public class ReportTaskCreator<R extends Report> implements DeferredTask {

  private static final long serialVersionUID = 1L;
  private static final Logger LOGGER = Logger.getLogger(ReportTaskCreator.class.getName());
  
  private String userId;
  private Collection<Long> acountIdList;
  private ReportDefinition reportDefinition;
  private String dateStart;
  private String dateEnd;
  private String mccAccountId;
  private int reportRowsSetSize;
  private ReportDefinitionReportType reportType;
  private ReportDefinitionDateRangeType dateRangeType;
  private Class<R> reportBeanClass;
  
  public ReportTaskCreator(
    String userId,
    String mccAccountId,
    Collection<Long> acountIdList,
    ReportDefinition reportDefinition,
    String dateStart,
    String dateEnd,
    int reportRowsSetSize,
    ReportDefinitionReportType reportType,
    ReportDefinitionDateRangeType dateRangeType,
    Class<R> reportBeanClass) {

    this.userId = userId;
    this.acountIdList = acountIdList;
    this.reportDefinition = reportDefinition;
    this.dateStart = dateStart;
    this.dateEnd = dateEnd;
    this.mccAccountId = mccAccountId;
    this.reportRowsSetSize = reportRowsSetSize;
    this.reportType = reportType;
    this.dateRangeType = dateRangeType;
    this.reportBeanClass = reportBeanClass;
  }

  @Override
  public void run() {

    // Processing Report Local Files
    LOGGER.info(" Procesing reports using Tasks...");

    for (Long accountId : acountIdList) {
      try {
        LOGGER.info("Task for account: " + accountId + reportType.name());

        QueueFactory.getQueue("reports").add(TaskOptions.Builder.withPayload(
            new TaskProcessorOnMemory<R>(userId, accountId, reportDefinition, dateRangeType,
                dateStart, dateEnd, mccAccountId, reportRowsSetSize, reportBeanClass)));

      } catch (Exception e) {
        LOGGER.severe("Ignoring account (Error when processing): " + accountId);
        e.printStackTrace();
      }
    } 
  }
}
