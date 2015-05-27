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
// limitations under the License.package com.google.api.ads.adwords.awreporting.model.definitions;
package com.google.api.ads.adwords.awreporting.model.definitions;

import com.google.api.ads.adwords.awreporting.model.entities.ReportPlacement;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionReportType;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.Assert;

/**
 * Tests the Placement Performance report definition.
 * 
 *  * @author markbowyer@google.com (Mark R. Bowyer)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:aw-report-model-test-beans.xml")

public class ReportPlacementDefinitionTest extends 
AbstractReportDefinitionTest<ReportPlacement> {
  /**
   * C'tor
   */
  public ReportPlacementDefinitionTest() {

    super(ReportPlacement.class,
        ReportDefinitionReportType.PLACEMENT_PERFORMANCE_REPORT,
        "src/test/resources/csv/placement-performance.csv");
  }

  @Override
  protected void testFirstEntry(ReportPlacement first) {
    Assert.assertEquals(1154812872L, first.getAdGroupId().longValue());
    Assert.assertEquals("Digital-TV", first.getAdGroupName());
    Assert.assertEquals("enabled", first.getAdGroupStatus());
    Assert.assertEquals(42344742L, first.getCampaignId().longValue());
    Assert.assertEquals("[Generic] Private", first.getCampaignName());
    Assert.assertEquals("www.common.se", first.getDisplayName());
    Assert.assertEquals(" --", first.getCriterionId());
    Assert.assertEquals(0l, first.getMaxCpm().longValue());
    Assert.assertEquals("www.common.se", first.getPlacementUrl());
    Assert.assertEquals("2014-03-01", first.getMonth());
    Assert.assertEquals(1.61, first.getCost().doubleValue());
    Assert.assertEquals(1, first.getClicks().longValue());
    Assert.assertEquals(1, first.getImpressions().longValue());
    Assert.assertEquals("100.00", first.getCtr());
    Assert.assertEquals(1610.00, first.getAvgCpm().doubleValue());
    Assert.assertEquals(1.61, first.getAvgCpc().doubleValue());
    Assert.assertEquals("Computers", first.getDevice());
    Assert.assertEquals("Headline", first.getClickType());
    Assert.assertEquals("Search Network", first.getAdNetwork());
    Assert.assertEquals("Search partners", first.getAdNetworkPartners());
  }

  @Override
  protected void testLastEntry(ReportPlacement last) {
    Assert.assertEquals(7645868292L, last.getAdGroupId().longValue());
    Assert.assertEquals("Kittens & Cats", last.getAdGroupName());
    Assert.assertEquals("enabled", last.getAdGroupStatus());
    Assert.assertEquals(145627092L, last.getCampaignId().longValue());
    Assert.assertEquals("[SEARCH][DSA-Extracted]Kittens", last.getCampaignName());
    Assert.assertEquals("raspberry.com", last.getDisplayName());
    Assert.assertEquals(" --", last.getCriterionId());
    Assert.assertEquals(0l, last.getMaxCpm().longValue());
    Assert.assertEquals("raspberry.com", last.getPlacementUrl());
    Assert.assertEquals("2014-03-01", last.getMonth());
    Assert.assertEquals(1.90, last.getCost().doubleValue());
    Assert.assertEquals(1, last.getClicks().longValue());
    Assert.assertEquals(3, last.getImpressions().longValue());
    Assert.assertEquals("33.33", last.getCtr());
    Assert.assertEquals(633.33, last.getAvgCpm().doubleValue());
    Assert.assertEquals(1.90, last.getAvgCpc().doubleValue());
    Assert.assertEquals("Mobile devices with full browsers", last.getDevice());
    Assert.assertEquals("Headline", last.getClickType());
    Assert.assertEquals("Search Network", last.getAdNetwork());
    Assert.assertEquals("Search partners", last.getAdNetworkPartners());
  }

  @Override
  protected int retrieveCsvEntries() {
    return 3;
  }

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
        // Specific to Performance Performance Report
        "AdGroupId",
        "AdGroupName",
        "AdGroupStatus",
        "BidModifier",
        "CampaignId",
        "CampaignName",
        "CampaignStatus",
        "CpcBidSource",
        "CpmBidSource",
        "CriteriaDestinationUrl",
        "DisplayName",
        "Id",
        "IsNegative",
        "IsRestrict",
        "CpcBid",
        "CpmBid",
        "PlacementUrl",
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
