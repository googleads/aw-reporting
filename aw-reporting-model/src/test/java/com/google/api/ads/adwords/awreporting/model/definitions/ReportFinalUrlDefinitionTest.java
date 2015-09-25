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

import com.google.api.ads.adwords.awreporting.model.entities.ReportFinalUrl;
import com.google.api.ads.adwords.lib.jaxb.v201506.ReportDefinitionReportType;
import com.google.common.collect.Lists;

import junit.framework.Assert;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Tests the Final Url Report definition.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:aw-report-model-test-beans.xml")
public class ReportFinalUrlDefinitionTest extends AbstractReportDefinitionTest<ReportFinalUrl> {

  /**
   * C'tor
   */
  public ReportFinalUrlDefinitionTest() {
    super(ReportFinalUrl.class, ReportDefinitionReportType.FINAL_URL_REPORT,
        "src/test/resources/csv/final-url.csv");
  }

  @Override
  protected void testFirstEntry(ReportFinalUrl row) {

    Assert.assertEquals(123456789L, row.getCampaignId().longValue());
    Assert.assertEquals("Campaign1", row.getCampaignName());
    Assert.assertEquals("enabled", row.getCampaignStatus());
    Assert.assertEquals(100000000L, row.getAdGroupId().longValue());
    Assert.assertEquals("AdGroup1" , row.getAdGroupName());
    Assert.assertEquals("enabled", row.getAdGroupStatus());
    
    Assert.assertEquals(0.00, row.getActiveViewCpm().doubleValue());
    Assert.assertEquals(25L, row.getActiveViewImpressions().longValue());
    Assert.assertEquals("Display Network", row.getAdNetwork());
    Assert.assertEquals("Display Network", row.getAdNetworkPartners());
    
    Assert.assertEquals(0.00, row.getAvgCpc().doubleValue());
    Assert.assertEquals(0.00, row.getAvgCpm().doubleValue());
    Assert.assertEquals(1.9, row.getAvgPositionBigDecimal().doubleValue());
    
    Assert.assertEquals(0L, row.getClicks().longValue());
    Assert.assertEquals("Headline", row.getClickType());
    
    Assert.assertEquals(0.00, row.getConversionRateManyPerClickBigDecimal().doubleValue());
    Assert.assertEquals(0L, row.getConversionsManyPerClick().longValue());
    Assert.assertEquals(0.00, row.getConversionValueBigDecimal().doubleValue());
    
    Assert.assertEquals(0.00, row.getCost().doubleValue());
    Assert.assertEquals(0.00, row.getCostPerConversionManyPerClick().doubleValue());
    Assert.assertEquals("Content", row.getCriteriaParameters());
    Assert.assertEquals("Broad", row.getCriteriaTypeName());
    
    Assert.assertEquals("2015-08-25", row.getDay());
    Assert.assertEquals("Tuesday", row.getDayOfWeek());
    Assert.assertEquals("Mobile devices with full browsers", row.getDevice());
    Assert.assertEquals("https://example.com/index/type/P/", row.getEffectiveFinalUrl());
    
    Assert.assertEquals(66L, row.getImpressions().longValue());
    Assert.assertEquals("2015-08-01", row.getMonth());
    Assert.assertEquals("August", row.getMonthOfYear());
    Assert.assertEquals("2015-07-01", row.getQuarter());
    
    Assert.assertEquals("Google Display Network", row.getSlot());
    Assert.assertEquals(0.00, row.getValuePerConversionManyPerClickBigDecimal().doubleValue());
    Assert.assertEquals("2015-08-24", row.getWeek());
    Assert.assertEquals(2015L, row.getYear().longValue());
    
  }

  @Override
  protected void testLastEntry(ReportFinalUrl row) {
    Assert.assertEquals(123456789L, row.getCampaignId().longValue());
    Assert.assertEquals("Campaign1", row.getCampaignName());
    Assert.assertEquals("enabled", row.getCampaignStatus());
    Assert.assertEquals(500000000L, row.getAdGroupId().longValue());
    Assert.assertEquals("AdGroup5" , row.getAdGroupName());
    Assert.assertEquals("enabled", row.getAdGroupStatus());
    
    Assert.assertEquals(80.00, row.getActiveViewCpm().doubleValue());
    Assert.assertEquals(3L, row.getActiveViewImpressions().longValue());
    Assert.assertEquals("Display Network", row.getAdNetwork());
    Assert.assertEquals("Display Network", row.getAdNetworkPartners());
    
    Assert.assertEquals(0.24, row.getAvgCpc().doubleValue());
    Assert.assertEquals(18.461538, row.getAvgCpm().doubleValue(), 0.01);
    Assert.assertEquals(2.3, row.getAvgPositionBigDecimal().doubleValue());
    
    Assert.assertEquals(1L, row.getClicks().longValue());
    Assert.assertEquals("Headline", row.getClickType());
    
    Assert.assertEquals(0.00, row.getConversionRateManyPerClickBigDecimal().doubleValue());
    Assert.assertEquals(0L, row.getConversionsManyPerClick().longValue());
    Assert.assertEquals(0.00, row.getConversionValueBigDecimal().doubleValue());
    
    Assert.assertEquals(0.24, row.getCost().doubleValue());
    Assert.assertEquals(0.00, row.getCostPerConversionManyPerClick().doubleValue());
    Assert.assertEquals("Content", row.getCriteriaParameters());
    Assert.assertEquals("Broad", row.getCriteriaTypeName());
    
    Assert.assertEquals("2015-08-31", row.getDay());
    Assert.assertEquals("Monday", row.getDayOfWeek());
    Assert.assertEquals("Mobile devices with full browsers", row.getDevice());
    Assert.assertEquals("https://example.com/index/type/T/", row.getEffectiveFinalUrl());
    
    Assert.assertEquals(13L, row.getImpressions().longValue());
    Assert.assertEquals("2015-08-01", row.getMonth());
    Assert.assertEquals("August", row.getMonthOfYear());
    Assert.assertEquals("2015-07-01", row.getQuarter());
    
    Assert.assertEquals("Google Display Network", row.getSlot());
    Assert.assertEquals(0.00, row.getValuePerConversionManyPerClickBigDecimal().doubleValue());
    Assert.assertEquals("2015-08-31", row.getWeek());
    Assert.assertEquals(2015L, row.getYear().longValue());
  }

  @Override
  protected int retrieveCsvEntries() {
    return 72;
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
        // Specific to Final URL Performance Report
        "ActiveViewCpm",
        "ActiveViewImpressions",
        "AdGroupId",
        "AdGroupName",
        "AdGroupStatus",
        "CampaignId",
        "CampaignName",
        "CampaignStatus",
        "ConversionTrackerId",
        "CriteriaParameters",
        "CriteriaTypeName",
        "EffectiveFinalUrl",
        "Slot"
    };
  }

  @Override
  protected List<String> retrieveSegmentedProperties() {
    return Lists.newArrayList(
        "AdNetworkType1",
        "AdNetworkType2",
        "ClickType",
        "ConversionCategoryName",
        "ConversionTrackerId",
        "Device"
    );
  }
}
