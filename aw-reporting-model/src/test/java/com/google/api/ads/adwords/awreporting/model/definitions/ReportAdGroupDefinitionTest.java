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

import com.google.api.ads.adwords.awreporting.model.entities.ReportAdGroup;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionReportType;

import junit.framework.Assert;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests the Ad Group Performance report definition.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:aw-report-model-test-beans.xml")
public class ReportAdGroupDefinitionTest extends AbstractReportDefinitionTest<ReportAdGroup> {

  /**
   * C'tor
   */
  public ReportAdGroupDefinitionTest() {
    super(ReportAdGroup.class, ReportDefinitionReportType.ADGROUP_PERFORMANCE_REPORT,
        "src/test/resources/csv/ad-group.csv");
  }

  /**
   * @see com.google.api.ads.adwords.awreporting.model.definitions.
   * AbstractReportDefinitionTest#testFirstEntry(
   * com.google.api.ads.adwords.awreporting.model.entities.Report)
   */
  @Override
  protected void testFirstEntry(ReportAdGroup first) {

    Assert.assertEquals(1230945123L, first.getAccountId().longValue());
    Assert.assertEquals("2013-05-07", first.getDay());
    Assert.assertEquals(2.72, first.getCost().doubleValue());
    Assert.assertEquals(6L, first.getClicks().longValue());
    Assert.assertEquals(16L, first.getImpressions().longValue());
    Assert.assertEquals(0L, first.getConvertedClicks().longValue());
    Assert.assertEquals(37.50, first.getCtrBigDecimal().doubleValue());
    Assert.assertEquals(170.00, first.getAvgCpm().doubleValue());
    Assert.assertEquals(0.45, first.getAvgCpc().doubleValue());
    Assert.assertEquals(2.69, first.getAvgPositionBigDecimal().doubleValue());
    Assert.assertEquals("EUR", first.getCurrencyCode());
    Assert.assertEquals(129807304L, first.getCampaignId().longValue());
    Assert.assertEquals(7253055064L, first.getAdGroupId().longValue());
    Assert.assertEquals("enabled", first.getAdGroupStatus());

  }

  /**
   * @see com.google.api.ads.adwords.awreporting.model.definitions.
   * AbstractReportDefinitionTest#testLastEntry(
   * com.google.api.ads.adwords.awreporting.model.entities.Report)
   */
  @Override
  protected void testLastEntry(ReportAdGroup last) {

    Assert.assertEquals(1230945123L, last.getAccountId().longValue());
    Assert.assertEquals("2013-05-10", last.getDay());
    Assert.assertEquals(0.60, last.getCost().doubleValue());
    Assert.assertEquals(1L, last.getClicks().longValue());
    Assert.assertEquals(72L, last.getImpressions().longValue());
    Assert.assertEquals(0L, last.getConvertedClicks().longValue());
    Assert.assertEquals(1.39, last.getCtrBigDecimal().doubleValue());
    Assert.assertEquals(8.33, last.getAvgCpm().doubleValue());
    Assert.assertEquals(0.60, last.getAvgCpc().doubleValue());
    Assert.assertEquals(2.58, last.getAvgPositionBigDecimal().doubleValue());
    Assert.assertEquals("EUR", last.getCurrencyCode());
    Assert.assertEquals(129807304L, last.getCampaignId().longValue());
    Assert.assertEquals(7253055064L, last.getAdGroupId().longValue());
    Assert.assertEquals("enabled", last.getAdGroupStatus());

  }

  /**
   * @see com.google.api.ads.adwords.awreporting.model.definitions.
   * AbstractReportDefinitionTest#retrieveCsvEntries()
   */
  @Override
  protected int retrieveCsvEntries() {
    return 4;
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
        // Specific to AdGroup Performance Report
        "CampaignId",
        "AdGroupId",
        "AdGroupName",
        "AdGroupStatus",
        "TargetCpa",
        "SearchImpressionShare",
        "SearchRankLostImpressionShare",
        "ContentImpressionShare",
        "ContentRankLostImpressionShare",
        "SearchExactMatchImpressionShare",
        "Labels",
        "ActiveViewCpm",
        "ActiveViewImpressions",
        "ConversionTrackerId",
        "TrackingUrlTemplate",
        "UrlCustomParameters",
        // Analytics Fieds        
        "AveragePageviews",
        "AverageTimeOnSite",
        "BounceRate",
        "PercentNewVisitors",
    };
  }
}
