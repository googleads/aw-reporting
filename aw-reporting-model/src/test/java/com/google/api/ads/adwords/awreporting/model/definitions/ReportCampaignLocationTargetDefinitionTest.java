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

import com.google.api.ads.adwords.awreporting.model.entities.ReportCampaignLocationTarget;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionReportType;

import junit.framework.Assert;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests the Campaign Location Target report definition.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:aw-report-model-test-beans.xml")
public class ReportCampaignLocationTargetDefinitionTest extends
    AbstractReportDefinitionTest<ReportCampaignLocationTarget> {

  /**
   * C'tor
   */
  public ReportCampaignLocationTargetDefinitionTest() {

    super(ReportCampaignLocationTarget.class,
        ReportDefinitionReportType.CAMPAIGN_LOCATION_TARGET_REPORT,
        "src/test/resources/csv/campaign-location-target.csv");
  }

  /**
   * @see com.google.api.ads.adwords.awreporting.model.definitions.
   *      AbstractReportDefinitionTest#testFirstEntry(com.google.api.ads.adwords.awreporting.model.entities.Report)
   */
  @Override
  protected void testFirstEntry(ReportCampaignLocationTarget first) {

    Assert.assertEquals(10L, first.getLocationId().longValue());
    Assert.assertEquals("2.00", first.getBidModifier());
    Assert.assertEquals(20L, first.getCampaignId().longValue());
    Assert.assertEquals("campaign", first.getCampaignName());
    Assert.assertEquals("enabled", first.getCampaignStatus());
    Assert.assertEquals("true", first.getIsNegative());
    Assert.assertEquals("2013-10-10", first.getDay());
    Assert.assertEquals(100L, first.getClicks().longValue());
    Assert.assertEquals(500L, first.getImpressions().longValue());
    Assert.assertEquals("1.00", first.getCtr());
    Assert.assertEquals(4.00, first.getAvgCpm().doubleValue());
    Assert.assertEquals(5.00, first.getAvgCpc().doubleValue());
    Assert.assertEquals("6.00", first.getAvgPosition());
    Assert.assertEquals(2L, first.getConvertedClicks().longValue());
    Assert.assertEquals("10.00", first.getClickConversionRate());

  }

  /**
   * @see com.google.api.ads.adwords.awreporting.model.definitions.
   *      AbstractReportDefinitionTest#testLastEntry(com.google.api.ads.adwords.awreporting.model.entities.Report)
   */
  @Override
  protected void testLastEntry(ReportCampaignLocationTarget last) {

    Assert.assertEquals(20L, last.getLocationId().longValue());
    Assert.assertEquals("4.00", last.getBidModifier());
    Assert.assertEquals(40L, last.getCampaignId().longValue());
    Assert.assertEquals("campaign", last.getCampaignName());
    Assert.assertEquals("enabled", last.getCampaignStatus());
    Assert.assertEquals("true", last.getIsNegative());
    Assert.assertEquals("2013-10-11", last.getDay());
    Assert.assertEquals(200L, last.getClicks().longValue());
    Assert.assertEquals(1000L, last.getImpressions().longValue());
    Assert.assertEquals("2.00", last.getCtr());
    Assert.assertEquals(8.00, last.getAvgCpm().doubleValue());
    Assert.assertEquals(10.00, last.getAvgCpc().doubleValue());
    Assert.assertEquals("12.00", last.getAvgPosition());
    Assert.assertEquals(4L, last.getConvertedClicks().longValue());
    Assert.assertEquals("20.00", last.getClickConversionRate());

  }

  /**
   * @see com.google.api.ads.adwords.awreporting.model.definitions.
   *      AbstractReportDefinitionTest#retrieveCsvEntries()
   */
  @Override
  protected int retrieveCsvEntries() {

    return 3;
  }

  /**
   * @see com.google.api.ads.adwords.awreporting.model.definitions.
   *      AbstractReportDefinitionTest#retrievePropertiesToBeSelected()
   */
  @Override
  protected String[] retrievePropertiesToBeSelected() {
    return new String[] {
        // Report
        "ExternalCustomerId",
        // ReportBase
        "AccountDescriptiveName", "AccountTimeZoneId", "CustomerDescriptiveName", "PrimaryCompanyName", "AccountCurrencyCode", "Date", "DayOfWeek", "Week", "Month", "MonthOfYear", "Quarter", "Year", "Cost", "Clicks", "Impressions", "Ctr", "AverageCpm", "AverageCpc", "AveragePosition", "Device", "ClickType", "AdNetworkType1", "AdNetworkType2", "ConversionsManyPerClick", "ConversionRateManyPerClick", "CostPerConversionManyPerClick", "ValuePerConversionManyPerClick", "ConvertedClicks", "ClickConversionRate", "CostPerConvertedClick", "ValuePerConvertedClick", "ConversionCategoryName", "ConversionTypeName", "ConversionValue", "ViewThroughConversions",
        // Specific
        "Id", "BidModifier", "CampaignId", "CampaignName", "CampaignStatus", "IsNegative"};
  }
}
