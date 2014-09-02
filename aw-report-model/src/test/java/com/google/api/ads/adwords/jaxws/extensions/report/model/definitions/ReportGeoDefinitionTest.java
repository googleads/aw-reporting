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
// limitations under the License.

package com.google.api.ads.adwords.jaxws.extensions.report.model.definitions;

import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.ReportGeo;
import com.google.api.ads.adwords.lib.jaxb.v201406.ReportDefinitionReportType;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.Assert;

/**
 * Tests the Geo Performance report definition.
 * 
 *  * @author markbowyer@google.com (Mark R. Bowyer)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:aw-report-model-test-beans.xml")

public class ReportGeoDefinitionTest extends
AbstractReportDefinitionTest<ReportGeo>{

  /**
   * C'tor
   */
  public ReportGeoDefinitionTest() {

    super(ReportGeo.class,
        ReportDefinitionReportType.GEO_PERFORMANCE_REPORT,
        "src/test/resources/csv/geo-performance.csv");
  }

  @Override
  protected void testFirstEntry(ReportGeo first) {
    Assert.assertEquals(6039084783L, first.getAdGroupId().longValue());
    Assert.assertEquals("General", first.getAdGroupName());
    Assert.assertEquals("enabled", first.getAdGroupStatus());
    Assert.assertEquals("Unspecified", first.getCityCriteriaId());
    Assert.assertEquals("United States", first.getCountryTerritory());
    Assert.assertEquals("true", first.getIsTargetable());
    Assert.assertEquals("", first.getMetroArea());
    Assert.assertEquals("United States", first.getMostSpecificLocation());
    Assert.assertEquals("Froody Rudy", first.getAccountDescriptiveName());
    Assert.assertEquals("2014-03-01", first.getMonth());
    Assert.assertEquals(0d, first.getCost().doubleValue());
    Assert.assertEquals(0, first.getClicks().longValue());
    Assert.assertEquals(2, first.getImpressions().longValue());
    Assert.assertEquals("0.00", first.getCtr());
    Assert.assertEquals(0d, first.getAvgCpm().doubleValue());
    Assert.assertEquals(0d, first.getAvgCpc().doubleValue());
    Assert.assertEquals("1.00", first.getAvgPosition());
    Assert.assertEquals("0.00", first.getConversionRateManyPerClick());
    Assert.assertEquals("0.00", first.getConversionRate());
    Assert.assertEquals(6671111111L, first.getAccountId().longValue());
  }

  @Override
  protected void testLastEntry(ReportGeo last) {
    Assert.assertEquals(8649799503L, last.getAdGroupId().longValue());
    Assert.assertEquals("Inventory", last.getAdGroupName());
    Assert.assertEquals("enabled", last.getAdGroupStatus());
    Assert.assertEquals("San Diego", last.getCityCriteriaId());
    Assert.assertEquals("United States", last.getCountryTerritory());
    Assert.assertEquals("true", last.getIsTargetable());
    Assert.assertEquals("San Diego CA", last.getMetroArea());
    Assert.assertEquals("92124", last.getMostSpecificLocation());
    Assert.assertEquals("Froody Rudy", last.getAccountDescriptiveName());
    Assert.assertEquals("2014-03-01", last.getMonth());
    Assert.assertEquals(0d, last.getCost().doubleValue());
    Assert.assertEquals(0, last.getClicks().longValue());
    Assert.assertEquals(1, last.getImpressions().longValue());
    Assert.assertEquals("0.00", last.getCtr());
    Assert.assertEquals(0d, last.getAvgCpm().doubleValue());
    Assert.assertEquals(0d, last.getAvgCpc().doubleValue());
    Assert.assertEquals("1.00", last.getAvgPosition());
    Assert.assertEquals("0.00", last.getConversionRateManyPerClick());
    Assert.assertEquals("0.00", last.getConversionRate());
    Assert.assertEquals(6671111111L, last.getAccountId().longValue());
  }

  @Override
  protected int retrieveCsvEntries() {
    return 5;
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
        // Specific to Geo Performance Report
        "AdFormat",
        "AdGroupId",
        "AdGroupName",
        "AdGroupStatus",
        "CampaignId",
        "CampaignName",
        "CampaignStatus",
        "CityCriteriaId",
        "ConversionTrackerId",
        "CountryCriteriaId",
        "LocationType",
        "MetroCriteriaId",
        "IsTargetingLocation",
        "MostSpecificCriteriaId",
        "RegionCriteriaId"
    };
  }
}