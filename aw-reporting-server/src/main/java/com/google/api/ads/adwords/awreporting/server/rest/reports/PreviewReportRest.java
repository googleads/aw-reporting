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

package com.google.api.ads.adwords.awreporting.server.rest.reports;

import com.google.api.ads.adwords.awreporting.model.util.DateUtil;
import com.google.api.ads.adwords.awreporting.server.exporter.ServerReportExporter;
import com.google.api.ads.adwords.awreporting.server.rest.AbstractBaseResource;
import com.google.api.ads.adwords.awreporting.server.rest.RestServer;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.restlet.representation.Representation;

import java.util.Properties;

/**
 * Rest entry point to launch Report Export tasks
 * 
 * @author joeltoby@google.com (Joel Toby)
 * @author jtoledo@google.com (Julian Toledo)
 */
public class PreviewReportRest extends AbstractBaseResource {
  
  private static final String DATE_FORMAT_SHORT_WITHOUTDAY = "yyyyMM";
  protected static final DateTimeFormatter dfYearMonthNoDash = DateTimeFormat.forPattern(DATE_FORMAT_SHORT_WITHOUTDAY);

  @Override
  public Representation getHandler() {
    String result = null;

    try {
      RestServer.getWebAuthenticator().checkAuthentication();

      Long topAccountId = getParameterAsLong("topAccountId");
      Long accountId = getParameterAsLong("accountId");
<<<<<<< HEAD
      String templateId = getParameter("templateId");
=======
      Long templateId = getParameterAsLong("templateId");
>>>>>>> 2f2a7486c98ac121ad93d5d5bb44c6f875fcbc1a
      String monthStart = getParameter("monthStart");
      String monthEnd = getParameter("monthEnd");
      String reportType = getParameter("reportType");

      String dateStart;
      String dateEnd;

      if (templateId != null) {
        
        // Export Reports uses YYYYMM month format and converts it to the 
        // YYYYMMDD with the first and last day of the month.
        if(monthStart == null)
          LOGGER.info("MONTH START = NULL");
        if(monthEnd == null)
          LOGGER.info("MONTH END = NULL");
        if(monthStart == null || monthEnd == null) {

          // Set date range to last month if dates are not provided
          LOGGER.info("Report date range not provided.  Defaulting to last month.");

          dateStart = DateUtil.formatYearMonthDayNoDash(DateUtil.firstDayPreviousMonth());
          dateEnd = DateUtil.formatYearMonthDayNoDash(DateUtil.lastDayPreviousMonth());

        } else {
          dateStart = DateUtil.formatYearMonthDayNoDash(
              DateUtil.firstDayMonth(dfYearMonthNoDash.parseDateTime(monthStart)));
          dateEnd = DateUtil.formatYearMonthDayNoDash(
              DateUtil.lastDayMonth(dfYearMonthNoDash.parseDateTime(monthEnd)));
        }

        Properties properties = RestServer.getProperties();
        ServerReportExporter serverReportExporter = RestServer.getApplicationContext().getBean(ServerReportExporter.class);

        // Export One Report in HTML or PDF
        if (topAccountId != null && accountId != null) {
          
          // Check that the user owns that MCC
          RestServer.getWebAuthenticator().checkAuthentication(topAccountId);

          LOGGER.info(" Export Report Task for account for " + accountId +
              " using templateId: #" + templateId + ". Report date range: " +
              dateStart + " - " + dateEnd);

          if (reportType != null && reportType.equals("pdf")) {
            // PDF
            byte[] pdfContent = serverReportExporter.getReportPdf(accountId, properties, templateId, dateStart, dateEnd);
<<<<<<< HEAD
            
            String reportFileName = "performance-report-" + accountId + "_" + dateStart + "_" + dateEnd + ".pdf";
            addFileNameHeader(reportFileName, false);
=======
>>>>>>> 2f2a7486c98ac121ad93d5d5bb44c6f875fcbc1a
            return createPdfResult(pdfContent);

          } else {
            // HTML (default)
            result = serverReportExporter.getReportHtml(accountId, properties, templateId, dateStart, dateEnd);
            return createHtmlResult(result);
          }
        }
      } else {
        throw new IllegalArgumentException("Missing templateId parameter");
      }

    } catch (Exception exception) {
      return handleException(exception);
    }
    addReadOnlyHeaders();
    return createJsonResult(result);
  }
}
