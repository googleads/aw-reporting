// Copyright 2014 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.awreporting.server.exporter;

import com.google.api.ads.adwords.awreporting.exporter.HTMLExporter;
import com.google.api.ads.adwords.awreporting.exporter.ReportExporter;
import com.google.api.ads.adwords.awreporting.exporter.reportwriter.MemoryReportWriter;
import com.google.api.ads.adwords.awreporting.server.entities.HtmlTemplate;
import com.google.api.ads.adwords.awreporting.server.rest.RestServer;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.client.auth.oauth2.Credential;
import com.google.common.collect.Lists;

import com.lowagie.text.DocumentException;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Exports DOC reports to Google Drive from an AppEngine instance of AW Reports to DB.
 *
 * @author joeltoby@google.com (Joel Toby)
 */
@Component
public class ServerReportExporter extends ReportExporter implements Serializable {

  private static final long serialVersionUID = 1L;
  private static final Logger LOGGER = Logger.getLogger(ServerReportExporter.class);

  /* (non-Javadoc)
   * @see com.google.api.ads.adwords.awreporting.exporter.ReportExporter#exportReports(java.lang.String, java.lang.String, java.util.Set, java.util.Properties, java.io.File, java.io.File, boolean)
   */
  public void exportReports(Credential credential, String mccAccountId, String dateStart, String dateEnd, Set<Long> accountIds,
      Properties properties, File htmlTemplateFile, File outputDirectory, Boolean sumAdExtensions)
          throws IOException, OAuthException, DocumentException {

    LOGGER.info("Starting PDF Generation for all Accounts");
    for (Long accountId : accountIds) {
      exportReport(credential, mccAccountId, dateStart, dateEnd, accountId, properties, 
          htmlTemplateFile, htmlTemplateFile.getName(), outputDirectory, sumAdExtensions);
    }
  }

<<<<<<< HEAD
  public String getReportHtml(Long accountId, Properties properties, String templateId,
=======
  public String getReportHtml(Long accountId, Properties properties, Long templateId,
>>>>>>> 2f2a7486c98ac121ad93d5d5bb44c6f875fcbc1a
      String dateStart, String dateEnd) throws IOException {

    String result = null;

    Map<String, Object> reportDataMap = createReportDataMap(dateStart, dateEnd,  accountId, properties, false);

    List<HtmlTemplate> templatesList = RestServer.getPersister().get(HtmlTemplate.class, HtmlTemplate.ID, templateId);
    if( templatesList != null && ! templatesList.isEmpty()) {
      HtmlTemplate template = templatesList.get(0);

      // Writing HTML to Memory
      MemoryReportWriter mrwHtml = MemoryReportWriter.newMemoryReportWriter();
      HTMLExporter.exportHtml(reportDataMap, template.getTemplateHtmlAsInputStream(), mrwHtml);

      result = IOUtils.toString(mrwHtml.getAsSource(), "UTF-8");
    }
    return result;
  }

<<<<<<< HEAD
  public byte[] getReportPdf(Long accountId, Properties properties, String templateId,
=======
  public byte[] getReportPdf(Long accountId, Properties properties, Long templateId,
>>>>>>> 2f2a7486c98ac121ad93d5d5bb44c6f875fcbc1a
      String dateStart, String dateEnd) throws IOException, DocumentException {

    byte[] result = null;

    // Get the Fonts for the PDF from the properties file
    String propertyReportFonts = properties.getProperty("aw.report.exporter.reportfonts");
    List<String> fontPaths = Lists.newArrayList();
    if (propertyReportFonts != null) {
      fontPaths = Arrays.asList(propertyReportFonts.split(","));
    }

    Map<String, Object> reportDataMap = createReportDataMap(dateStart, dateEnd,  accountId, properties, false);

    List<HtmlTemplate> templatesList = RestServer.getPersister().get(HtmlTemplate.class, HtmlTemplate.ID, templateId);
    if( templatesList != null && ! templatesList.isEmpty()) {
      HtmlTemplate template = templatesList.get(0);

      // Writing HTML to Memory
      MemoryReportWriter mrwHtml = MemoryReportWriter.newMemoryReportWriter();
      HTMLExporter.exportHtml(reportDataMap, template.getTemplateHtmlAsInputStream(), mrwHtml);
      
      // Writing PDF to memory
      MemoryReportWriter mrwPdf = MemoryReportWriter.newMemoryReportWriter();
      HTMLExporter.exportHtmlToPdf(mrwHtml.getAsSource(), mrwPdf, fontPaths);

      result = IOUtils.toByteArray(mrwPdf.getAsSource());
    }
    return result;
  }
}
