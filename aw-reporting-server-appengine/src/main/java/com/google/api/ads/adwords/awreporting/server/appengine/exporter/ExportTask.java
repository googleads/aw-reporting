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

import com.google.api.ads.adwords.awreporting.exporter.ReportExporter;
import com.google.api.ads.adwords.awreporting.server.appengine.RestServer;
import com.google.api.ads.adwords.awreporting.server.appengine.util.MccTaskCounter;
import com.google.api.ads.adwords.awreporting.server.entities.HtmlTemplate;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.client.auth.oauth2.Credential;
import com.google.appengine.api.taskqueue.DeferredTask;

import com.lowagie.text.DocumentException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Tasks to Export Reports.
 * 
 * @author jtoledo@google.com (Julian Toledo)
 * @author joeltoby@google.com (Joel Toby)
 */
public class ExportTask implements DeferredTask {

  private static final long serialVersionUID = 1L;
  private static final Logger LOGGER = Logger.getLogger(ExportTaskCreator.class.getName());

  private String mccAccountId;
  private String dateStart;
  private String dateEnd;
  private Long accountId;
  private Properties properties;
  private Long htmlTemplateId;
  private File outputDirectory;
  private boolean sumAdExtensions;

  public ExportTask(final String mccAccountId, final String dateStart,
      final String dateEnd, final Long accountId, final Properties properties,
      final Long htmlTemplateId, final File outputDirectory, final Boolean sumAdExtensions) {
    this.mccAccountId = mccAccountId;
    this.dateStart = dateStart;
    this.dateEnd = dateEnd;
    this.accountId = accountId;
    this.properties = properties;
    this.htmlTemplateId = htmlTemplateId;
    this.outputDirectory = outputDirectory;
    this.sumAdExtensions = sumAdExtensions;
  }

  @Override
  public void run() {
    ReportExporter reportExporter = RestServer.getApplicationContext().getBean(ReportExporter.class);

    LOGGER.info("Starting export report... for account " + this.accountId);

    try {

      Credential credential = RestServer.getAuthenticator().getOAuth2Credential(mccAccountId, false);
      
      List<HtmlTemplate> templatesList = RestServer.getPersister().get(HtmlTemplate.class, "id", htmlTemplateId);
      
      if( templatesList != null && ! templatesList.isEmpty()) {
        
        HtmlTemplate template = templatesList.get(0);

        reportExporter.exportReport(credential, mccAccountId, dateStart, dateEnd, accountId, properties, 
            template.getTemplateHtmlAsInputStream(), template.getTemplateName(), outputDirectory, sumAdExtensions);

      }
      
      int tasks = MccTaskCounter.decreasePendingExportTasks(Long.valueOf(mccAccountId));
      LOGGER.info("Pending processing tasks: " + tasks);

    } catch (OAuthException e) {
      LOGGER.severe("Error exporting report for account: " + this.accountId + " " + e.getMessage());
      e.printStackTrace();
    } catch (IOException e) {
      LOGGER.severe("Error exporting report for account: " + this.accountId + " " + e.getMessage());
      e.printStackTrace();
    } catch (DocumentException e) {
      LOGGER.severe("Error exporting report for account: " + this.accountId + " " + e.getMessage());
      e.printStackTrace();
    }
  }
}
