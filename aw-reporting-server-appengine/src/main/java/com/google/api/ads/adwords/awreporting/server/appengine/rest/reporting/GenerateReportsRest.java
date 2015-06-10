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

package com.google.api.ads.adwords.awreporting.server.appengine.rest.reporting;

import com.google.api.ads.adwords.awreporting.model.util.DateUtil;
import com.google.api.ads.adwords.awreporting.processors.ReportProcessor;
import com.google.api.ads.adwords.awreporting.server.appengine.RestServer;
import com.google.api.ads.adwords.awreporting.server.appengine.processors.ReportProcessorAppEngine;
import com.google.api.ads.adwords.awreporting.server.rest.AbstractBaseResource;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionDateRangeType;

import org.restlet.representation.Representation;

import java.util.Date;
import java.util.Properties;

/**
 * Rest entry point to launch Generate Reports data tasks
 * 
 * @author jtoledo@google.com (Julian Toledo)
 */
public class GenerateReportsRest extends AbstractBaseResource {

  public Representation getHandler() {
    String result = null;
    
    try {
      RestServer.getWebAuthenticator().checkAuthentication();
      
      Long topAccountId = getParameterAsLong("topAccountId");
      Date dateStart = getParameterAsDate("dateStart");
      Date dateEnd = getParameterAsDate("dateEnd");

      if (topAccountId != null && dateStart != null && dateEnd != null) { // Generate Report Task for MCC

        LOGGER.info(" Generate Report Task for MCC for " + topAccountId);

        Properties properties = RestServer.getProperties();

        ReportProcessorAppEngine reportProcessorAppEngine = createReportProcessor();

        reportProcessorAppEngine.generateReportsForMCC(String.valueOf(topAccountId),
            ReportDefinitionDateRangeType.CUSTOM_DATE, DateUtil.formatYearMonthDayNoDash(dateStart),
            DateUtil.formatYearMonthDayNoDash(dateEnd), null, properties, null, null);

        result = "OK";
      } else {
        throw new IllegalArgumentException("Missing topAccountId, dateStart or dateEnd parameters for GenerateReports");
      }

    } catch (Exception exception) {
      return handleException(exception);
    }
    addReadOnlyHeaders();
    return createJsonResult(result);
  }

  public Representation postPutHandler(String json) {
    String result = null;
    
    try {
      RestServer.getWebAuthenticator().checkAuthentication();
      
      /*
      HtmlTemplate template = new Gson().fromJson(json, HtmlTemplate.class);

      // Set the userId on the template and save it
      template.setUserId(userId);
      LOGGER.info("Persisting template...");
      HtmlTemplate savedTemplate = RestServer.getPersister().save(template);

      result = gson.toJson(savedTemplate);
      */
    } catch (Exception exception) {
      return handleException(exception);
    }
    addHeaders();    
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
