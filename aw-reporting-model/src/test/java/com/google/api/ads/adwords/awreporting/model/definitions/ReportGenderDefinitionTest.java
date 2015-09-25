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

import com.google.api.ads.adwords.awreporting.model.entities.ReportGender;
import com.google.api.ads.adwords.lib.jaxb.v201506.ReportDefinitionReportType;
import com.google.common.collect.Lists;

import junit.framework.Assert;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Tests the Campaign Performance report definition.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:aw-report-model-test-beans.xml")
public class ReportGenderDefinitionTest extends
AbstractReportDefinitionTest<ReportGender> {

  /**
   * C'tor
   */
  public ReportGenderDefinitionTest() {

    super(ReportGender.class,
        ReportDefinitionReportType.GENDER_PERFORMANCE_REPORT,
        "src/test/resources/csv/gender.csv");
  }

  @Override
  protected void testFirstEntry(ReportGender row) {
    Assert.assertEquals(1234567890L, row.getAccountId().longValue());
    Assert.assertEquals("2013-09-10", row.getDay());
    Assert.assertEquals("My account", row.getAccountDescriptiveName());
    Assert.assertEquals(0.00, row.getCost().doubleValue());
    Assert.assertEquals(0L, row.getClicks().longValue());
    Assert.assertEquals(0.00, row.getAvgCpc().doubleValue());
    Assert.assertEquals(0.00, row.getAvgCpm().doubleValue());
    Assert.assertEquals("USD", row.getCurrencyCode());
    
    Assert.assertEquals("Computers", row.getDevice());
    Assert.assertEquals("Headline", row.getClickType());
    Assert.assertEquals("Search Network", row.getAdNetwork());
    Assert.assertEquals("Search partners", row.getAdNetworkPartners());
    Assert.assertEquals("(GMT-08:00) Pacific Time", row.getAccountTimeZoneId());
    
    Assert.assertEquals(4545192429L, row.getAdGroupId().longValue());
    Assert.assertEquals("AdGroup1" , row.getAdGroupName());
    Assert.assertEquals("enabled", row.getAdGroupStatus());
    
    Assert.assertEquals(12345678L, row.getCampaignId().longValue());
    Assert.assertEquals("Campaign1", row.getCampaignName());
    Assert.assertEquals("enabled", row.getCampaignStatus());
    
    Assert.assertEquals(0.00, row.getConversionValueBigDecimal().doubleValue());
    Assert.assertEquals(0.00, row.getCostPerConversionManyPerClick().doubleValue());
    
    Assert.assertEquals(0.00, row.getMaxCpc().doubleValue());
    Assert.assertEquals("ad group criteria", row.getCpcBidSource());
    Assert.assertEquals("Male", row.getCriteria());
    Assert.assertEquals("http://example.com/index/A", row.getCriteriaDestinationUrl());
    Assert.assertEquals("ClientName1", row.getCustomerDescriptiveName());
    
    Assert.assertEquals("Tuesday", row.getDayOfWeek());
    Assert.assertEquals(41224769349L, row.getCriterionId().longValue());
    Assert.assertEquals(false, row.isNegative());
    Assert.assertEquals(false, row.isRestrict());
    
    Assert.assertEquals("September", row.getMonthOfYear());
    Assert.assertEquals("My company name", row.getPrimaryCompanyName());
  
    Assert.assertEquals("enabled", row.getStatus());
    Assert.assertEquals(0L, row.getViewThroughConversions().longValue());
  }

  @Override
  protected void testLastEntry(ReportGender row) {
    Assert.assertEquals(1234567890L, row.getAccountId().longValue());
    Assert.assertEquals("2013-09-10", row.getDay());
    Assert.assertEquals("My account", row.getAccountDescriptiveName());
    Assert.assertEquals(0.00, row.getCost().doubleValue());
    Assert.assertEquals(0L, row.getClicks().longValue());
    Assert.assertEquals(0.00, row.getAvgCpc().doubleValue());
    Assert.assertEquals(0.00, row.getAvgCpm().doubleValue());
    Assert.assertEquals("USD", row.getCurrencyCode());
    
    Assert.assertEquals("Computers", row.getDevice());
    Assert.assertEquals("Sitelink", row.getClickType());
    Assert.assertEquals("Search Network", row.getAdNetwork());
    Assert.assertEquals("Search partners", row.getAdNetworkPartners());
    Assert.assertEquals("(GMT-08:00) Pacific Time", row.getAccountTimeZoneId());
    
    Assert.assertEquals(4545190149L, row.getAdGroupId().longValue());
    Assert.assertEquals("AdGroup2" , row.getAdGroupName());
    Assert.assertEquals("enabled", row.getAdGroupStatus());
    
    Assert.assertEquals(12345678L, row.getCampaignId().longValue());
    Assert.assertEquals("Campaign1", row.getCampaignName());
    Assert.assertEquals("enabled", row.getCampaignStatus());
    
    Assert.assertEquals(0.00, row.getConversionValueBigDecimal().doubleValue());
    Assert.assertEquals(0.00, row.getCostPerConversionManyPerClick().doubleValue());
    
    Assert.assertEquals(2.50, row.getMaxCpc().doubleValue());
    Assert.assertEquals("ad group criteria", row.getCpcBidSource());
    Assert.assertEquals("Undetermined", row.getCriteria());
    Assert.assertEquals("http://example.com/index/A", row.getCriteriaDestinationUrl());
    Assert.assertEquals("ClientName1", row.getCustomerDescriptiveName());
    
    Assert.assertEquals("Tuesday", row.getDayOfWeek());
    Assert.assertEquals(20115029265L, row.getCriterionId().longValue());
    Assert.assertEquals(false, row.isNegative());
    Assert.assertEquals(false, row.isRestrict());
    
    Assert.assertEquals("September", row.getMonthOfYear());
    Assert.assertEquals("My company name", row.getPrimaryCompanyName());
  
    Assert.assertEquals("enabled", row.getStatus());
    Assert.assertEquals(0L, row.getViewThroughConversions().longValue());
  }

  @Override
  protected int retrieveCsvEntries() {
    return 10;
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
        // Specific to Campaign Performance Report
        "ActiveViewCpm",
        "ActiveViewImpressions",
        "AdGroupId",
        "AdGroupName",
        "AdGroupStatus",
        "BidModifier",
        "BidType",
        "CampaignId",
        "CampaignName",
        "CampaignStatus",
        "ConversionTrackerId",
        "CpcBid",
        "CpcBidSource",
        "CpmBid",
        "CpmBidSource",
        "Criteria",
        "CriteriaDestinationUrl",
        "FinalAppUrls",
        "FinalMobileUrls",
        "FinalUrls",
        "GmailForwards",
        "GmailSaves",
        "GmailSecondaryClicks",
        "Id",
        "IsNegative",
        "IsRestrict",
        "Status",
        "TrackingUrlTemplate",
        "UrlCustomParameters"
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
