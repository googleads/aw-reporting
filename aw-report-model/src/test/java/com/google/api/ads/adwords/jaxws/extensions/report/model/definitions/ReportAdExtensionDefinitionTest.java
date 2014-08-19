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

import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.ReportAdExtension;
import com.google.api.ads.adwords.lib.jaxb.v201406.ReportDefinitionReportType;

import junit.framework.Assert;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests the Ad Extensions Performance report definition.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:aw-report-model-test-beans.xml")
public class ReportAdExtensionDefinitionTest
    extends AbstractReportDefinitionTest<ReportAdExtension> {

  /**
   * C'tor
   */
  public ReportAdExtensionDefinitionTest() {
    super(ReportAdExtension.class, ReportDefinitionReportType.AD_EXTENSIONS_PERFORMANCE_REPORT,
        "src/test/resources/csv/ad-extensions.csv");
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.definitions.
   * AbstractReportDefinitionTest#testFirstEntry(
   * com.google.api.ads.adwords.jaxws.extensions.report.model.entities.Report)
   */
  @Override
  protected void testFirstEntry(ReportAdExtension first) {

    Assert.assertEquals(319387457L, first.getAdExtensionId().longValue());
    Assert.assertEquals(119807057L, first.getCampaignId().longValue());
    Assert.assertEquals("active", first.getStatus());
    Assert.assertEquals("eligible", first.getApprovalStatus());
    Assert.assertEquals("location extension", first.getAdExtensionType());

    Assert.assertEquals("Computers", first.getDevice());
    Assert.assertEquals("Headline", first.getClickType());

    Assert.assertEquals("2013-05-01", first.getDay());
    Assert.assertEquals(0L, first.getClicks().longValue());
    Assert.assertEquals(47L, first.getImpressions().longValue());
    Assert.assertEquals(0L, first.getConversions().longValue());
    Assert.assertEquals(0.00, first.getCost().doubleValue());
    Assert.assertEquals(0.00, first.getCtrBigDecimal().doubleValue());
    Assert.assertEquals(0.00, first.getAvgCpc().doubleValue());
    Assert.assertEquals(0.00, first.getAvgCpm().doubleValue());
    Assert.assertEquals(4.51, first.getAvgPositionBigDecimal().doubleValue());

  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.definitions.
   * AbstractReportDefinitionTest#testLastEntry(
   * com.google.api.ads.adwords.jaxws.extensions.report.model.entities.Report)
   */
  @Override
  protected void testLastEntry(ReportAdExtension last) {

    Assert.assertEquals(327723137L, last.getAdExtensionId().longValue());
    Assert.assertEquals(122782217L, last.getCampaignId().longValue());
    Assert.assertEquals("active", last.getStatus());
    Assert.assertEquals("eligible", last.getApprovalStatus());
    Assert.assertEquals("site links extension", last.getAdExtensionType());

    Assert.assertEquals("Computers", last.getDevice());
    Assert.assertEquals("Sitelink", last.getClickType());

    Assert.assertEquals("2013-05-05", last.getDay());
    Assert.assertEquals(0L, last.getClicks().longValue());
    Assert.assertEquals(1L, last.getImpressions().longValue());
    Assert.assertEquals(0L, last.getConversions().longValue());
    Assert.assertEquals(0.00, last.getCost().doubleValue());
    Assert.assertEquals(0.00, last.getCtrBigDecimal().doubleValue());
    Assert.assertEquals(0.00, last.getAvgCpc().doubleValue());
    Assert.assertEquals(0.00, last.getAvgCpm().doubleValue());
    Assert.assertEquals(3.00, last.getAvgPositionBigDecimal().doubleValue());

  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.definitions.
   * AbstractReportDefinitionTest#retrieveCsvEntries()
   */
  @Override
  protected int retrieveCsvEntries() {
    return 23;
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.definitions.
   * AbstractReportDefinitionTest#retrievePropertiesToBeSelected()
   */
  @Override
  protected String[] retrievePropertiesToBeSelected() {

    return new String[] {
        // ReportBase (with Exclusions)
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
        // Specific to AdExtenion Performance
        "CampaignId",
        "AdExtensionId",
        "AdExtensionType",
        "Status",
        "ApprovalStatus"
        };
  }
}
