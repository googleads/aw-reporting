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

import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.ReportShopping;
import com.google.api.ads.adwords.lib.jaxb.v201406.ReportDefinitionReportType;

import junit.framework.Assert;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests the Keyword Performance report definition.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:aw-report-model-test-beans.xml")
public class ReportShoppingDefinitionTest extends AbstractReportDefinitionTest<ReportShopping> {

  /**
   * C'tor
   */
  public ReportShoppingDefinitionTest() {

    super(ReportShopping.class, ReportDefinitionReportType.SHOPPING_PERFORMANCE_REPORT,
        "src/test/resources/csv/shopping-performance.csv");
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.definitions.
   * AbstractReportDefinitionTest#testFirstEntry(
   * com.google.api.ads.adwords.jaxws.extensions.report.model.entities.Report)
   */
  @Override
  protected void testFirstEntry(ReportShopping first) {
    Assert.assertEquals("2014-06-20", first.getDay());
    Assert.assertEquals(0.53, first.getCost().doubleValue());
    Assert.assertEquals(2L, first.getClicks().longValue());
    Assert.assertEquals(99L, first.getImpressions().longValue());
    Assert.assertEquals(0L, first.getConversions().longValue());
    Assert.assertEquals(2.02, first.getCtrBigDecimal().doubleValue());
    Assert.assertEquals(0.26, first.getAvgCpc().doubleValue());
    Assert.assertEquals(100514323L, first.getMerchantId().longValue());
    Assert.assertEquals("France", first.getCountryCriteriaId());
    Assert.assertEquals("Maison et jardin", first.getCategoryL1());

    Assert.assertEquals(175572164L, first.getCampaignId().longValue());
    Assert.assertEquals("Shopping", first.getCampaignName());
    Assert.assertEquals(12165252524L, first.getAdGroupId().longValue());
    Assert.assertEquals("Ad Group #1", first.getAdGroupName());
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.definitions.
   * AbstractReportDefinitionTest#testLastEntry(
   * com.google.api.ads.adwords.jaxws.extensions.report.model.entities.Report)
   */
  @Override
  protected void testLastEntry(ReportShopping last) {
    Assert.assertEquals("2014-06-21", last.getDay());
    Assert.assertEquals(0.00, last.getCost().doubleValue());
    Assert.assertEquals(0L, last.getClicks().longValue());
    Assert.assertEquals(19L, last.getImpressions().longValue());
    Assert.assertEquals(0L, last.getConversions().longValue());
    Assert.assertEquals(0.00, last.getCtrBigDecimal().doubleValue());
    Assert.assertEquals(0.00, last.getAvgCpc().doubleValue());
    Assert.assertEquals(100514323L, last.getMerchantId().longValue());
    Assert.assertEquals("France", last.getCountryCriteriaId());
    Assert.assertEquals("Meubles", last.getCategoryL1());

    Assert.assertEquals(175572164L, last.getCampaignId().longValue());
    Assert.assertEquals("Shopping", last.getCampaignName());
    Assert.assertEquals(12165252524L, last.getAdGroupId().longValue());
    Assert.assertEquals("Ad Group #1", last.getAdGroupName());
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.definitions.
   * AbstractReportDefinitionTest#retrieveCsvEntries()
   */
  @Override
  protected int retrieveCsvEntries() {

    return 4;
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
        // Specific to Shopping Performance Report
        "AdGroupId",
        "AdGroupName",
        "AggregatorId",
        "Brand",
        "CampaignId",
        "CampaignName",
        "CategoryL1",
        "CategoryL2",
        "CategoryL3",
        "CategoryL4",
        "CategoryL5",
        "CountryCriteriaId",
        "CustomAttribute0",
        "CustomAttribute1",
        "CustomAttribute2",
        "CustomAttribute3",
        "CustomAttribute4",
        "LanguageCriteriaId",
        "MerchantId",
        "OfferId",
        "ProductCondition",
        "ProductTypeL1",
        "ProductTypeL2",
        "ProductTypeL3",
        "ProductTypeL4",
        "ProductTypeL5",
        "StoreId",
        "TotalConvValue"
    };
  }
}
