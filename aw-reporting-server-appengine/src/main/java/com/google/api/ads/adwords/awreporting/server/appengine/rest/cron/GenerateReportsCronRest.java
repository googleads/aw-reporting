//Copyright 2012 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.awreporting.server.appengine.rest.cron;

import com.google.api.ads.adwords.awreporting.model.util.DateUtil;
import com.google.api.ads.adwords.awreporting.processors.ReportProcessor;
import com.google.api.ads.adwords.awreporting.server.appengine.RestServer;
import com.google.api.ads.adwords.awreporting.server.appengine.model.UserToken;
import com.google.api.ads.adwords.awreporting.server.appengine.processors.RefreshAccountsTask;
import com.google.api.ads.adwords.awreporting.server.appengine.processors.ReportProcessorAppEngine;
import com.google.api.ads.adwords.awreporting.server.rest.AbstractBaseResource;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionDateRangeType;
import com.google.common.collect.Sets;

import org.restlet.representation.Representation;

import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Rest entry point to launch Generate Reports with the Cron Job
 * 
 * @author jtoledo@google.com (Julian Toledo)
 */
public class GenerateReportsCronRest extends AbstractBaseResource {

  public Representation getHandler() {
    String result = null;

    try {

      LOGGER.info(" Generate Report Cron Task");
      System.out.println(" Generate Report Cron Task");

      Properties properties = RestServer.getProperties();
      ReportProcessorAppEngine reportProcessorAppEngine = createReportProcessor();

      // Get all User Token, they may be duplicates and we will only run the first one found.
      List<UserToken> userTokens = RestServer.getPersister().get(UserToken.class);
      Set<Long> topAccountIds = Sets.newHashSet();
      for (UserToken token : userTokens) {

        Long tokenTopAccountId = token.getTopAccountId();

        if (topAccountIds.add(tokenTopAccountId)) {

          LOGGER.info(" Refresh Accounts for MCC for " + tokenTopAccountId);
          RefreshAccountsTask.createRefreshAccountsTask(String.valueOf(tokenTopAccountId));

          LOGGER.info(" Generate Report Task for MCC for " + tokenTopAccountId);
          System.out.println(" Generate Report Task for MCC for " + tokenTopAccountId);

          // Get the dates for first day and last day of the previous month
          String dateStart = DateUtil.formatYearMonthDayNoDash(DateUtil.firstDayPreviousMonth());
          String dateEnd = DateUtil.formatYearMonthDayNoDash(DateUtil.lastDayPreviousMonth());

          reportProcessorAppEngine.generateReportsForMCC(String.valueOf(tokenTopAccountId),
              ReportDefinitionDateRangeType.CUSTOM_DATE, dateStart, dateEnd, null, properties, null, null);
        }
        result = "OK";
      }
    } catch (Exception exception) {
      return handleException(exception);
    }
    addReadOnlyHeaders();
    return createJsonResult(result);
  }

  /**
   * Creates the {@link ReportProcessor} autowiring all the dependencies.
   *
   * @return the {@code ReportProcessor} with all the dependencies properly injected.
   */
  private static ReportProcessorAppEngine createReportProcessor() {
    ReportProcessorAppEngine reportProcessorAppEngine = 
        RestServer.getApplicationContext().getBean(ReportProcessorAppEngine.class);
    return reportProcessorAppEngine;
  }
}
