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

import com.google.api.ads.adwords.awreporting.model.entities.ReportDisplayKeyword;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionReportType;

import junit.framework.Assert;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests the Keyword Performance report definition.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:aw-report-model-test-beans.xml")
public class ReportDisplayKeywordDefinitionTest extends AbstractReportDefinitionTest<ReportDisplayKeyword> {

  /**
   * C'tor
   */
  public ReportDisplayKeywordDefinitionTest() {

    super(ReportDisplayKeyword.class, ReportDefinitionReportType.DISPLAY_KEYWORD_PERFORMANCE_REPORT,
        "src/test/resources/csv/display-keyword-performance.csv");
  }

  /**
   * @see com.google.api.ads.adwords.awreporting.model.definitions.
   * AbstractReportDefinitionTest#testFirstEntry(
   * com.google.api.ads.adwords.awreporting.model.entities.Report)
   */
  @Override
  protected void testFirstEntry(ReportDisplayKeyword first) {
    Assert.assertEquals(1056270861L, first.getAccountId().longValue());
    Assert.assertEquals("2014-06-09", first.getDay());
    Assert.assertEquals(0.00, first.getCost().doubleValue());
    Assert.assertEquals(0L, first.getClicks().longValue());
    Assert.assertEquals(59L, first.getImpressions().longValue());
    Assert.assertEquals(0L, first.getConvertedClicks().longValue());
    Assert.assertEquals(0.00, first.getCtrBigDecimal().doubleValue());
    Assert.assertEquals(0.00, first.getAvgCpm().doubleValue());
    Assert.assertEquals(0.00, first.getAvgCpc().doubleValue());

    Assert.assertEquals(208108586L, first.getCampaignId().longValue());
    Assert.assertEquals("Search IP", first.getCampaignName());
    Assert.assertEquals(11390393906L, first.getAdGroupId().longValue());
    Assert.assertEquals("Gen", first.getAdGroupName());
    Assert.assertEquals("", first.getCriteriaDestinationUrl());
    Assert.assertEquals(10024300L, first.getKeywordId().longValue());
    Assert.assertEquals("restaurant", first.getKeywordText());
    Assert.assertFalse(first.isNegative());
  }

  /**
   * @see com.google.api.ads.adwords.awreporting.model.definitions.
   * AbstractReportDefinitionTest#testLastEntry(
   * com.google.api.ads.adwords.awreporting.model.entities.Report)
   */
  @Override
  protected void testLastEntry(ReportDisplayKeyword last) {
    Assert.assertEquals(1056270861L, last.getAccountId().longValue());
    Assert.assertEquals("2014-06-09", last.getDay());
    Assert.assertEquals(0.30, last.getCost().doubleValue());
    Assert.assertEquals(2L, last.getClicks().longValue());
    Assert.assertEquals(1853L, last.getImpressions().longValue());
    Assert.assertEquals(0L, last.getConvertedClicks().longValue());
    Assert.assertEquals(0.11, last.getCtrBigDecimal().doubleValue());
    Assert.assertEquals(0.16, last.getAvgCpm().doubleValue());
    Assert.assertEquals(0.15, last.getAvgCpc().doubleValue());

    Assert.assertEquals(209867786L, last.getCampaignId().longValue());
    Assert.assertEquals("Display", last.getCampaignName());
    Assert.assertEquals(11553542546L, last.getAdGroupId().longValue());
    Assert.assertEquals("Display", last.getAdGroupName());
    Assert.assertEquals("", last.getCriteriaDestinationUrl());
    Assert.assertEquals(32912034877L, last.getKeywordId().longValue());
    Assert.assertEquals("restaurant italien 34", last.getKeywordText());
    Assert.assertFalse(last.isNegative());
  }

  /**
   * @see com.google.api.ads.adwords.awreporting.model.definitions.
   * AbstractReportDefinitionTest#retrieveCsvEntries()
   */
  @Override
  protected int retrieveCsvEntries() {
    
    return 9;
  }

  /**
   * @see com.google.api.ads.adwords.awreporting.model.definitions.
   * AbstractReportDefinitionTest#retrievePropertiesToBeSelected()
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
        // Specific to Display Keyword Performance Report
        "AdGroupId",
        "AdGroupName",
        "AdGroupStatus",
        "CampaignId",
        "CampaignName",
        "CampaignStatus",
        "CpcBidSource",
        "CpmBidSource",
        "CriteriaDestinationUrl",
        "Id",
        "IsNegative",
        "IsRestrict",
        "KeywordText",
        "CpcBid",
        "CpmBid",
        "TargetingSetting",
        "ActiveViewCpm",
        "ActiveViewImpressions",
        "ConversionTrackerId",
        "FinalAppUrls",
        "FinalMobileUrls",
        "FinalUrls",
        "TrackingUrlTemplate",
        "UrlCustomParameters"
    };
  }
}
