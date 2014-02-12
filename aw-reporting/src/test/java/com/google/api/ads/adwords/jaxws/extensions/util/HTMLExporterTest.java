// Copyright 2013 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.jaxws.extensions.util;

import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.Report;
import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.ReportAccount;
import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.ReportPlaceholderFeedItem;
import com.google.api.ads.adwords.lib.jaxb.v201309.ReportDefinitionReportType;
import com.google.api.client.util.Maps;

import com.lowagie.text.DocumentException;

import org.joda.time.DateTime;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author markbowyer@google.com (Mark R. Bowyer)
 *
 *         Test the HTML Exporter classes
 */
public class HTMLExporterTest {

  @Test
  public void testPlaceHolderFeedItemReport() throws IOException, DocumentException {

    List<Report> list = new ArrayList<Report>();

    ReportPlaceholderFeedItem reportPHFI = createFeedItemReportRow(123456789L);
    list.add(reportPHFI);
    reportPHFI = createFeedItemReportRow(987654321L);
    list.add(reportPHFI);
    reportPHFI = createFeedItemReportRow(135792468L);
    list.add(reportPHFI);
    reportPHFI = createFeedItemReportRow(246813579L);
    list.add(reportPHFI);

    File htmlFile = new File("target/testReport.html");
    File pdfFile = new File("target/testReport.pdf");
    
    final File templateFile =
        new File("src/main/resources/templates/PLACEHOLDER_FEED_ITEM_REPORT.tmpl");
    
    Map<String, Object> reportMap = Maps.newHashMap();
    reportMap.put(ReportDefinitionReportType.PLACEHOLDER_FEED_ITEM_REPORT.name(), list);

    HTMLExporter.exportHTML(reportMap, templateFile, htmlFile);

    HTMLExporter.convertHTMLtoPDF(htmlFile, pdfFile);
  }

  /**
   * @param idNumber to use
   * @return a filled in ReportPlaceholderFeedItem
   */
  private ReportPlaceholderFeedItem createFeedItemReportRow(Long idNumber) {

    ReportPlaceholderFeedItem reportPHFI = new ReportPlaceholderFeedItem();
    reportPHFI.setMonth(DateTime.now());
    reportPHFI.setAccountDescriptiveName("Test Account Name");
    reportPHFI.setAccountId(idNumber);
    reportPHFI.setCampaignId(idNumber);
    reportPHFI.setCampaignName("Test Campaign Name");
    reportPHFI.setFeedId(idNumber);
    reportPHFI.setFeedItemId(idNumber);
    reportPHFI.setAdGroupId(idNumber);
    reportPHFI.setAdGroupName("Test AdGroup Name");
    reportPHFI.setAdId(idNumber);
    reportPHFI.setAvgCpc(new BigDecimal(2.00));
    reportPHFI.setAvgCpm(new BigDecimal(1.00));
    reportPHFI.setAvgPosition(new BigDecimal(2.2));
    reportPHFI.setClickType("SiteLink");
    reportPHFI.setClicks((long) 33);
    reportPHFI.setImpressions((long)333);
    reportPHFI.setStatus("ACTIVE");
    reportPHFI.setConversionRate1(new BigDecimal(1.5));
    reportPHFI.setConversionRateMany(new BigDecimal(3.3));
    return reportPHFI;
  }

  @Test
  public void testAccountPerformanceReport() throws IOException, DocumentException {

    List<Report> list = new ArrayList<Report>();

    ReportAccount reportAccount = createAccountReportRow(123456789L);
    list.add(reportAccount);
    reportAccount = createAccountReportRow(987654321L);
    list.add(reportAccount);
    reportAccount = createAccountReportRow(135792468L);
    list.add(reportAccount);
    reportAccount = createAccountReportRow(246813579L);
    list.add(reportAccount);

    File htmlFile = new File("target/testReport.html");
    File pdfFile = new File("target/testReport.pdf");
    
    final File templateFile =
        new File("src/main/resources/templates/ACCOUNT_PERFORMANCE_REPORT.tmpl");

    Map<String, Object> reportMap = Maps.newHashMap();
    reportMap.put(ReportDefinitionReportType.ACCOUNT_PERFORMANCE_REPORT.name(), list);
    
    HTMLExporter.exportHTML(reportMap, templateFile, htmlFile);

    HTMLExporter.convertHTMLtoPDF(htmlFile, pdfFile);
  }

  /**
   * @param idNumber to use
   * @return a filled in ReportPlaceholderFeedItem
   */
  private ReportAccount createAccountReportRow(Long idNumber) {

    ReportAccount reportAccount = new ReportAccount();
    reportAccount.setMonth(DateTime.now());
    reportAccount.setAccountDescriptiveName("Test Account Name");
    reportAccount.setAccountId(idNumber);
    reportAccount.setImpressions(99L);
    reportAccount.setCost(new BigDecimal(99.99));
    reportAccount.setAvgCpc(new BigDecimal(2.00));
    reportAccount.setAvgCpm(new BigDecimal(1.00));
    reportAccount.setAvgPosition(new BigDecimal(2.2));
    reportAccount.setClicks((long) 33);
    return reportAccount;
  }
}
