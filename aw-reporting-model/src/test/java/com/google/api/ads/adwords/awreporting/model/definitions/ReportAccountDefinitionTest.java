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

package com.google.api.ads.adwords.awreporting.model.definitions;

import com.google.api.ads.adwords.awreporting.model.entities.ReportAccount;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionReportType;

import junit.framework.Assert;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests the Account Performance report definition.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:aw-report-model-test-beans.xml")
public class ReportAccountDefinitionTest extends AbstractReportDefinitionTest<ReportAccount> {

  /**
   * C'tor
   */
  public ReportAccountDefinitionTest() {
    super(ReportAccount.class, ReportDefinitionReportType.ACCOUNT_PERFORMANCE_REPORT,
        "src/test/resources/csv/account.csv");
  }

  /**
   * @see com.google.api.ads.adwords.awreporting.model.definitions.
   * AbstractReportDefinitionTest#testFirstEntry(
   * com.google.api.ads.adwords.awreporting.model.entities.Report)
   */
  @Override
  protected void testFirstEntry(ReportAccount first) {

    Assert.assertEquals(1232198123L, first.getAccountId().longValue());
    Assert.assertEquals("2013-05-01", first.getDay());
    Assert.assertEquals("Test Le Test", first.getAccountDescriptiveName());
    Assert.assertEquals(1.42, first.getCost().doubleValue());
    Assert.assertEquals(10L, first.getClicks().longValue());
    Assert.assertEquals(1978L, first.getImpressions().longValue());
    Assert.assertEquals(0L, first.getConvertedClicks().longValue());
    Assert.assertEquals(0.51, first.getCtrBigDecimal().doubleValue());
    Assert.assertEquals(0.72, first.getAvgCpm().doubleValue());
    Assert.assertEquals(0.14, first.getAvgCpc().doubleValue());
    Assert.assertEquals(2.97, first.getAvgPositionBigDecimal().doubleValue());
    Assert.assertEquals("EUR", first.getCurrencyCode());
    Assert.assertEquals("Search Network", first.getAdNetwork());
    Assert.assertEquals(17.40, first.getSearchLostISBudgetBigDecimal().doubleValue());
    Assert.assertEquals(42.93, first.getSearchLostISRankBigDecimal().doubleValue());
    Assert.assertEquals(0.0, first.getContentLostISBudgetBigDecimal().doubleValue());
    Assert.assertEquals(0.0, first.getContentLostISRankBigDecimal().doubleValue());
  }


  /**
   * @see com.google.api.ads.adwords.awreporting.model.definitions.
   * AbstractReportDefinitionTest#testLastEntry(
   * com.google.api.ads.adwords.awreporting.model.entities.Report)
   */
  @Override
  protected void testLastEntry(ReportAccount last) {

    Assert.assertEquals(1232198123L, last.getAccountId().longValue());
    Assert.assertEquals("2013-05-10", last.getDay());
    Assert.assertEquals("Test Le Test", last.getAccountDescriptiveName());
    Assert.assertEquals(0.75, last.getCost().doubleValue());
    Assert.assertEquals(4L, last.getClicks().longValue());
    Assert.assertEquals(2793L, last.getImpressions().longValue());
    Assert.assertEquals(0L, last.getConvertedClicks().longValue());
    Assert.assertEquals(0.14, last.getCtrBigDecimal().doubleValue());
    Assert.assertEquals(0.27, last.getAvgCpm().doubleValue());
    Assert.assertEquals(0.19, last.getAvgCpc().doubleValue());
    Assert.assertEquals(3.14, last.getAvgPositionBigDecimal().doubleValue());
    Assert.assertEquals("EUR", last.getCurrencyCode());
    Assert.assertEquals("Search Network", last.getAdNetwork());
    Assert.assertEquals(2.12, last.getSearchLostISBudgetBigDecimal().doubleValue());
    Assert.assertEquals(44.45, last.getSearchLostISRankBigDecimal().doubleValue());
    Assert.assertEquals(0.00, last.getContentLostISBudgetBigDecimal().doubleValue());
    Assert.assertEquals(0.00, last.getContentLostISRankBigDecimal().doubleValue());

  }

  /**
   * @see com.google.api.ads.adwords.awreporting.model.definitions.
   * AbstractReportDefinitionTest#retrieveCsvEntries()
   */
  @Override
  protected int retrieveCsvEntries() {
    return 7;
  }

  /**
   * @return the properties for the Account Performance report.
   */
  @Override
  protected String[] retrievePropertiesToBeSelected() {

    return new String[] {
        // Report
        "ExternalCustomerId",
        // ReportBase
        "AccountDescriptiveName",
        "AccountTimeZoneId",
        "CustomerDescriptiveName",
        "PrimaryCompanyName",
        "AccountCurrencyCode",
        "HourOfDay",
        "Date",
        "DayOfWeek",
        "Week",
        "Month",
        "MonthOfYear",
        "Quarter",
        "Year",
        "Cost",
        "Clicks",
        "Impressions",
        "Ctr",
        "AverageCpm",
        "AverageCpc",
        "AveragePosition",
        "Device",
        "ClickType",
        "AdNetworkType1",
        "AdNetworkType2",
        "ConversionsManyPerClick",
        "ConversionRateManyPerClick",
        "CostPerConversionManyPerClick",
        "ValuePerConversionManyPerClick",
        "ConvertedClicks",
        "ClickConversionRate",
        "CostPerConvertedClick",
        "ValuePerConvertedClick",
        "ConversionCategoryName",
        "ConversionTypeName",
        "ConversionValue",
        "ViewThroughConversions",
        // Specific to Account Performance
        "SearchImpressionShare",
        "SearchBudgetLostImpressionShare",
        "SearchRankLostImpressionShare",
        "ContentImpressionShare",
        "ContentBudgetLostImpressionShare",
        "ContentRankLostImpressionShare",
        "SearchExactMatchImpressionShare",
        "CanManageClients",
        "IsAutoTaggingEnabled",
        "IsTestAccount",
        "ActiveViewCpm",
        "ActiveViewImpressions",
        "ConversionTrackerId"
    };
  }
}
