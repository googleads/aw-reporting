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

import com.google.api.ads.adwords.awreporting.model.entities.ReportCampaign;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionReportType;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.Assert;

/**
 * Tests the Campaign Performance report definition.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:aw-report-model-test-beans.xml")
public class ReportCampaignDefinitionTest extends AbstractReportDefinitionTest<ReportCampaign> {

  /**
   * C'tor
   */
  public ReportCampaignDefinitionTest() {

    super(ReportCampaign.class, ReportDefinitionReportType.CAMPAIGN_PERFORMANCE_REPORT,
        "src/test/resources/csv/campaign.csv");
  }

  /**
   * @see com.google.api.ads.adwords.awreporting.model.definitions.
   * AbstractReportDefinitionTest#testFirstEntry(
   * com.google.api.ads.adwords.awreporting.model.entities.Report)
   */
  @Override
  protected void testFirstEntry(ReportCampaign first) {

    Assert.assertEquals(1232422123L, first.getAccountId().longValue());
    Assert.assertEquals("2013-05-01", first.getDay());
    Assert.assertEquals(1.11, first.getCost().doubleValue());
    Assert.assertEquals(5L, first.getClicks().longValue());
    Assert.assertEquals(927L, first.getImpressions().longValue());
    Assert.assertEquals(0L, first.getConvertedClicks().longValue());
    Assert.assertEquals(0.54, first.getCtrBigDecimal().doubleValue());
    Assert.assertEquals(1.20, first.getAvgCpm().doubleValue());
    Assert.assertEquals(0.22, first.getAvgCpc().doubleValue());
    Assert.assertEquals(3.59, first.getAvgPositionBigDecimal().doubleValue());
    Assert.assertEquals("EUR", first.getCurrencyCode());

    Assert.assertEquals(132449648L, first.getCampaignId().longValue());
    Assert.assertEquals("active", first.getCampaignStatus());
    Assert.assertEquals(1.00, first.getBudget().doubleValue());
    Assert.assertEquals(41273L, first.getBudgetId().longValue());
    
  }

  /**
   * @see com.google.api.ads.adwords.awreporting.model.definitions.
   * AbstractReportDefinitionTest#testLastEntry(
   * com.google.api.ads.adwords.awreporting.model.entities.Report)
   */
  @Override
  protected void testLastEntry(ReportCampaign last) {

    Assert.assertEquals(1232422123L, last.getAccountId().longValue());
    Assert.assertEquals("2013-05-10", last.getDay());
    Assert.assertEquals(0.88, last.getCost().doubleValue());
    Assert.assertEquals(6L, last.getClicks().longValue());
    Assert.assertEquals(757L, last.getImpressions().longValue());
    Assert.assertEquals(0L, last.getConvertedClicks().longValue());
    Assert.assertEquals(0.79, last.getCtrBigDecimal().doubleValue());
    Assert.assertEquals(1.16, last.getAvgCpm().doubleValue());
    Assert.assertEquals(0.15, last.getAvgCpc().doubleValue());
    Assert.assertEquals(3.21, last.getAvgPositionBigDecimal().doubleValue());
    Assert.assertEquals("EUR", last.getCurrencyCode());

    Assert.assertEquals(132449648L, last.getCampaignId().longValue());
    Assert.assertEquals("active", last.getCampaignStatus());
    Assert.assertEquals(1.00, last.getBudget().doubleValue());
    Assert.assertEquals(412987L, last.getBudgetId().longValue());

  }

  /**
   * @see com.google.api.ads.adwords.awreporting.model.definitions.
   * AbstractReportDefinitionTest#retrieveCsvEntries()
   */
  @Override
  protected int retrieveCsvEntries() {

    return 10;
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
        "ImpressionReach",
        "Ctr",
        "AverageCpm",
        "AverageCpc",
        "AveragePosition",
        "Device",
        "ClickType",
        "AdNetworkType1",
        "AdNetworkType2",
        "ConversionsManyPerClick",
        "ConversionManyPerClickSignificance",
        "ConversionRateManyPerClickSignificance",
        "ConversionRateManyPerClick",
        "CostPerConversionManyPerClick",
        "CostPerConversionManyPerClickSignificance",
        "ValuePerConversionManyPerClick",
        "ConvertedClicks",
        "ClickConversionRate",
        "ClickConversionRateSignificance",
        "ConvertedClicksSignificance",
        "CostPerConvertedClick",
        "CostPerConvertedClickSignificance",
        "ValuePerConvertedClick",
        "ConversionCategoryName",
        "ConversionTypeName",
        "ConversionValue",
        "ViewThroughConversions",
        // Specific to Campaign Performance Report
        "CampaignId",
        "CampaignName",
        "CampaignStatus",
        "Amount",
        "BudgetId",
        "SearchImpressionShare",
        "SearchBudgetLostImpressionShare",
        "SearchRankLostImpressionShare",
        "ContentImpressionShare",
        "ContentBudgetLostImpressionShare",
        "ContentRankLostImpressionShare",
        "SearchExactMatchImpressionShare",
        "Labels",
        "AdvertisingChannelType",
        "AdvertisingChannelSubType",
        "ActiveViewCpm",
        "ActiveViewImpressions",
        "ConversionTrackerId",
        "TrackingUrlTemplate",
        "UrlCustomParameters",
        // Analytics Fieds      
        "AverageFrequency",  
        "AveragePageviews",
        "AverageTimeOnSite",
        "BounceRate",
        "PercentNewVisitors"
    };
  }
}
