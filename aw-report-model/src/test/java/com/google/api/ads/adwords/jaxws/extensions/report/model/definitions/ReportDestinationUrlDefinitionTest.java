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

package com.google.api.ads.adwords.jaxws.extensions.report.model.definitions;

import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.ReportDestinationUrl;
import com.google.api.ads.adwords.lib.jaxb.v201406.ReportDefinitionReportType;

import junit.framework.Assert;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests the Ad Performance report definition.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:aw-report-model-test-beans.xml")
public class ReportDestinationUrlDefinitionTest
extends AbstractReportDefinitionTest<ReportDestinationUrl> {

  /**
   * C'tor
   */
  public ReportDestinationUrlDefinitionTest() {
    super(ReportDestinationUrl.class, ReportDefinitionReportType.DESTINATION_URL_REPORT,
        "src/test/resources/csv/destination-url.csv");
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.definitions.
   * AbstractReportDefinitionTest#testFirstEntry(
   * com.google.api.ads.adwords.jaxws.extensions.report.model.entities.Report)
   */
  @Override
  protected void testFirstEntry(ReportDestinationUrl row) {

    Assert.assertEquals("(GMT+01:00) Paris", row.getAccountTimeZoneId());
    Assert.assertEquals(3448007340L, row.getAdGroupId().longValue());
    Assert.assertEquals("Ad Group #1", row.getAdGroupName());
    Assert.assertEquals("enabled", row.getAdGroupStatus());
    Assert.assertEquals(84058260L , row.getCampaignId().longValue());
    Assert.assertEquals("CAMPAGNE", row.getCampaignName());
    Assert.assertEquals("active", row.getCampaignStatus());
    
    //Assert.assertEquals("0.00", row.getConversionRate());
    //Assert.assertEquals("0.00", row.getConversionRateManyPerClick());
    Assert.assertEquals(0L, row.getConversionsManyPerClick().longValue());
    //Assert.assertEquals(null, row.getConversionValue());
    //Assert.assertEquals("0.00", row.getCostPerConversion());
    //Assert.assertEquals("0.00", row.getCostPerConversionManyPerClick());
    
    Assert.assertEquals("" , row.getCriteriaDestinationUrl());
    Assert.assertEquals("+immo +benodet", row.getCriteriaParameters());
    Assert.assertEquals("enabled", row.getCriteriaStatus());
    Assert.assertEquals("Broad", row.getCriteriaTypeName());
    Assert.assertEquals("Era Rouxelimmo - Plomelin", row.getCustomerDescriptiveName());
    Assert.assertEquals("Sunday", row.getDayOfWeek());
    Assert.assertEquals("http://www.era-immobilier-plomelin.fr", row.getEffectiveDestinationUrl());
    Assert.assertEquals("false" , row.getIsNegative());
    Assert.assertEquals("September" , row.getMonthOfYear());
    Assert.assertEquals("", row.getPrimaryCompanyName());
    Assert.assertEquals("2013-07-01", row.getQuarter());

  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.definitions.
   * AbstractReportDefinitionTest#testLastEntry(
   * com.google.api.ads.adwords.jaxws.extensions.report.model.entities.Report)
   */
  @Override
  protected void testLastEntry(ReportDestinationUrl row) {
    Assert.assertEquals("(GMT+01:00) Paris", row.getAccountTimeZoneId());
    Assert.assertEquals(5827855980L, row.getAdGroupId().longValue());
    Assert.assertEquals("Ad Group #1", row.getAdGroupName());
    Assert.assertEquals("enabled", row.getAdGroupStatus());
    Assert.assertEquals(118203540L , row.getCampaignId().longValue());
    Assert.assertEquals("DISPLAY", row.getCampaignName());
    Assert.assertEquals("active", row.getCampaignStatus());
    
    //Assert.assertEquals("0.00", row.getConversionRate());
    //Assert.assertEquals("0.00", row.getConversionRateManyPerClick());
    //Assert.assertEquals(0L, row.getConversionsManyPerClick().longValue());
    //Assert.assertEquals(null, row.getConversionValue());
    //Assert.assertEquals("0.00", row.getCostPerConversion());
    //Assert.assertEquals("0.00", row.getCostPerConversionManyPerClick());
    
    Assert.assertEquals("" , row.getCriteriaDestinationUrl());
    Assert.assertEquals("Content", row.getCriteriaParameters());
    Assert.assertEquals("enabled", row.getCriteriaStatus());
    Assert.assertEquals("Broad", row.getCriteriaTypeName());
    Assert.assertEquals("Era Rouxelimmo - Plomelin", row.getCustomerDescriptiveName());
    Assert.assertEquals("Sunday", row.getDayOfWeek());
    Assert.assertEquals("http://www.era-immobilier-plomelin.fr",
        row.getEffectiveDestinationUrl());
    Assert.assertEquals("false" , row.getIsNegative());
    Assert.assertEquals("September" , row.getMonthOfYear());
    Assert.assertEquals("", row.getPrimaryCompanyName());
    Assert.assertEquals("2013-07-01", row.getQuarter());

    Assert.assertEquals(true, true);
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.definitions.
   * AbstractReportDefinitionTest#retrieveCsvEntries()
   */
  @Override
  protected int retrieveCsvEntries() {
    return 96;
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.definitions.
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
        "ValuePerConvManyPerClick",
        "ValuePerConversionManyPerClick",
        "Conversions",
        "ConversionRate",
        "CostPerConversion",
        "ValuePerConv",
        "ValuePerConversion",
        "ConversionCategoryName",
        "ConversionTypeName",
        "ConversionValue",
        "ViewThroughConversions",
        // Specific to Destination URL Performance Report
        "AdGroupId",
        "AdGroupName",
        "AdGroupStatus",
        "CampaignId",
        "CampaignName",
        "CampaignStatus",
        "CriteriaDestinationUrl",
        "CriteriaParameters",
        "CriteriaStatus",
        "CriteriaTypeName",
        "EffectiveDestinationUrl",
        "IsNegative"
        };
  }
}