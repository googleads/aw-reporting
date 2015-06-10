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

package com.google.api.ads.adwords.awreporting.server.appengine.exporter;

import com.google.appengine.api.taskqueue.DeferredTask;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Responsible for creating tasks that write HTML reports to Google Drive. 
 *
 * @author joeltoby@google.com (Joel Toby)
 */
public class ExportTaskCreator implements DeferredTask, Serializable {

  private static final long serialVersionUID = 1L;
  private static final Logger LOGGER = Logger.getLogger(ExportTaskCreator.class.getName());

  private String mccAccountId;
  private Collection<Long> accountIdList;
  private String dateStart;
  private String dateEnd;
  private Properties properties;
  private Long htmlTemplateId;
  private File outputDirectory;
  private Boolean sumAdExtensions;

  public ExportTaskCreator(
      String mccAccountId,
      Collection<Long> accountIdList,
      String dateStart,
      String dateEnd,
      Properties properties,
      Long htmlTemplateId,
      File outputDirectory,
      Boolean sumAdExtensions) {
    this.mccAccountId = mccAccountId;
    this.accountIdList = accountIdList;
    this.dateStart = dateStart;
    this.dateEnd = dateEnd;    
    this.properties = properties;
    this.htmlTemplateId = htmlTemplateId;
    this.outputDirectory = outputDirectory;
    this.sumAdExtensions = sumAdExtensions;
  }

  @Override
  public void run() {
    LOGGER.info(" Exporting report files via tasks...");

    for (final Long accountId : accountIdList) {
      try {
        LOGGER.info("Exporting HTML report for account: " + accountId);
        QueueFactory.getQueue("pdfs").add(TaskOptions.Builder.withPayload(
            new ExportTask(mccAccountId, dateStart, dateEnd, accountId, properties, htmlTemplateId,
                outputDirectory, sumAdExtensions)));
      } catch (Exception e) {
        LOGGER.severe("Ignoring account (Error when processing): " + accountId);
        e.printStackTrace();
      }
    }
  }
}
