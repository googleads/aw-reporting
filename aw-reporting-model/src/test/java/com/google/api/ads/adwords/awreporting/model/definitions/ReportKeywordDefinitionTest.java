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

import com.google.api.ads.adwords.awreporting.model.entities.ReportKeyword;
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
public class ReportKeywordDefinitionTest extends AbstractReportDefinitionTest<ReportKeyword> {

  /**
   * C'tor
   */
  public ReportKeywordDefinitionTest() {

    super(ReportKeyword.class, ReportDefinitionReportType.KEYWORDS_PERFORMANCE_REPORT,
        "src/test/resources/csv/keyword.csv");
  }

  /**
   * @see com.google.api.ads.adwords.awreporting.model.definitions.
   * AbstractReportDefinitionTest#testFirstEntry(
   * com.google.api.ads.adwords.awreporting.model.entities.Report)
   */
  @Override
  protected void testFirstEntry(ReportKeyword first) {

    Assert.assertEquals(8661954824L, first.getAccountId().longValue());
    Assert.assertEquals("2013-05-01", first.getDay());
    Assert.assertEquals(0.00, first.getCost().doubleValue());
    Assert.assertEquals(0L, first.getClicks().longValue());
    Assert.assertEquals(20L, first.getImpressions().longValue());
    Assert.assertEquals(0L, first.getConvertedClicks().longValue());
    Assert.assertEquals(0.00, first.getCtrBigDecimal().doubleValue());
    Assert.assertEquals(0.00, first.getAvgCpm().doubleValue());
    Assert.assertEquals(0.00, first.getAvgCpc().doubleValue());
    Assert.assertEquals(4.50, first.getAvgPositionBigDecimal().doubleValue());
    Assert.assertEquals("EUR", first.getCurrencyCode());

    Assert.assertEquals(86352677L, first.getCampaignId().longValue());
    Assert.assertEquals(3398915357L, first.getAdGroupId().longValue());
    Assert.assertEquals(41933620L, first.getKeywordId().longValue());
    Assert.assertEquals("enabled", first.getStatus());
    Assert.assertEquals(10.00, first.getQualityScoreAsBigDecimal().doubleValue());
    Assert.assertEquals("Broad", first.getKeywordMatchType());
    Assert.assertEquals("achat forêt", first.getKeywordText());
    Assert.assertEquals("", first.getCriteriaDestinationUrl());
    Assert.assertFalse(first.isNegative());

  }

  /**
   * @see com.google.api.ads.adwords.awreporting.model.definitions.
   * AbstractReportDefinitionTest#testLastEntry(
   * com.google.api.ads.adwords.awreporting.model.entities.Report)
   */
  @Override
  protected void testLastEntry(ReportKeyword last) {

    Assert.assertEquals(8661954824L, last.getAccountId().longValue());
    Assert.assertEquals("2013-05-10", last.getDay());
    Assert.assertEquals(0.00, last.getCost().doubleValue());
    Assert.assertEquals(0L, last.getClicks().longValue());
    Assert.assertEquals(1L, last.getImpressions().longValue());
    Assert.assertEquals(0L, last.getConvertedClicks().longValue());
    Assert.assertEquals(0.00, last.getCtrBigDecimal().doubleValue());
    Assert.assertEquals(0.00, last.getAvgCpm().doubleValue());
    Assert.assertEquals(0.00, last.getAvgCpc().doubleValue());
    Assert.assertEquals(6.00, last.getAvgPositionBigDecimal().doubleValue());
    Assert.assertEquals("EUR", last.getCurrencyCode());

    Assert.assertEquals(86352677L, last.getCampaignId().longValue());
    Assert.assertEquals(3398915357L, last.getAdGroupId().longValue());
    Assert.assertEquals(44877775648L, last.getKeywordId().longValue());
    Assert.assertEquals("enabled", last.getStatus());
    Assert.assertEquals(10.00, last.getQualityScoreAsBigDecimal().doubleValue());
    Assert.assertEquals("Broad", last.getKeywordMatchType());
    Assert.assertEquals("propriete sologne a vendre", last.getKeywordText());
    Assert.assertEquals("", last.getCriteriaDestinationUrl());
    Assert.assertFalse(last.isNegative());

  }

  /**
   * @see com.google.api.ads.adwords.awreporting.model.definitions.
   * AbstractReportDefinitionTest#retrieveCsvEntries()
   */
  @Override
  protected int retrieveCsvEntries() {

    return 149;
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
        // Specific to Keyword Performance Report
        "CampaignId",
        "AdGroupId",
        "AdGroupName",
        "Id",
        "Status",
        "QualityScore",
        "KeywordMatchType",
        "KeywordText",
        "CriteriaDestinationUrl",
        "IsNegative",
        "CampaignName",
        "AdGroupName",
        "CpcBid",
        "CpmBid",
        "SearchImpressionShare",
        "SearchRankLostImpressionShare",
        "SearchExactMatchImpressionShare",
        "Labels",
        "FirstPageCpc",
        "TopOfPageCpc",
        "ActiveViewCpm",
        "ActiveViewImpressions",
        "ConversionTrackerId",
        "FinalAppUrls",
        "FinalMobileUrls",
        "FinalUrls",
        "TrackingUrlTemplate",
        "UrlCustomParameters",
        // Analytics Fieds        
        "AveragePageviews",
        "AverageTimeOnSite",
        "BounceRate",
        "PercentNewVisitors"
    };
  }
}
