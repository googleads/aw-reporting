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

import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.ReportUrl;
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
public class ReportUrlDefinitionTest extends AbstractReportDefinitionTest<ReportUrl> {

  /**
   * C'tor
   */
  public ReportUrlDefinitionTest() {
    super(ReportUrl.class, ReportDefinitionReportType.URL_PERFORMANCE_REPORT,
        "src/test/resources/csv/managed-placement-url.csv");
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.definitions.
   * AbstractReportDefinitionTest#testFirstEntry(
   * com.google.api.ads.adwords.jaxws.extensions.report.model.entities.Report)
   */
  @Override
  protected void testFirstEntry(ReportUrl first) {

    Assert.assertEquals(6421165252L, first.getAdGroupId().longValue());
    Assert.assertEquals(9572882832L, first.getAccountId().longValue());

    // Assert.assertEquals("2013-05-01", first.getDay());
    // Assert.assertEquals("9.39", first.getCost());
    // Assert.assertEquals(32L, first.getClicks().longValue());
    // Assert.assertEquals(1258L, first.getImpressions().longValue());
    // Assert.assertEquals(0L, first.getConversions().longValue());
    // Assert.assertEquals("2.54", first.getCtr());
    // Assert.assertEquals("7.46", first.getAvgCpm());
    // Assert.assertEquals("0.29", first.getAvgCpc());
    // Assert.assertEquals("2.12", first.getAvgPosition());
    // Assert.assertEquals("EUR", first.getCurrencyCode());
    //
    // Assert.assertEquals(132958027L, first.getCampaignId().longValue());
    // Assert.assertEquals(6113972227L, first.getAdGroupId().longValue());

  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.definitions.
   * AbstractReportDefinitionTest#testLastEntry(
   * com.google.api.ads.adwords.jaxws.extensions.report.model.entities.Report)
   */
  @Override
  protected void testLastEntry(ReportUrl last) {

    Assert.assertEquals(9572882832L, last.getAccountId().longValue());
    // Assert.assertEquals("2013-05-10", last.getDay());
    // Assert.assertEquals("1.46", last.getCost());
    // Assert.assertEquals(2L, last.getClicks().longValue());
    // Assert.assertEquals(58L, last.getImpressions().longValue());
    // Assert.assertEquals(0L, last.getConversions().longValue());
    // Assert.assertEquals("3.45", last.getCtr());
    // Assert.assertEquals("25.17", last.getAvgCpm());
    // Assert.assertEquals("0.73", last.getAvgCpc());
    // Assert.assertEquals("3.29", last.getAvgPosition());
    // Assert.assertEquals("EUR", last.getCurrencyCode());
    //
    // Assert.assertEquals(132958027L, last.getCampaignId().longValue());
    // Assert.assertEquals(6114146707L, last.getAdGroupId().longValue());

  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.definitions.
   * AbstractReportDefinitionTest#retrieveCsvEntries()
   */
  @Override
  protected int retrieveCsvEntries() {
    return 600;
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
        // ReportBase (without Exclusions)
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
        // Specific to URL Performance Report
        "AdFormat",
        "AdGroupCriterionStatus",
        "AdGroupId",
        "AdGroupName",
        "AdGroupStatus",
        "CampaignId",
        "CampaignName",
        "CampaignStatus",
        "CriteriaParameters",
        "DisplayName",
        "Domain",
        "IsAutoOptimized",
        "IsBidOnPath",
        "IsPathExcluded",
        "Url"
        };
  }
}
