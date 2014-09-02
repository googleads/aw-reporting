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

import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.ReportPlaceholderFeedItem;
import com.google.api.ads.adwords.jaxws.extensions.report.model.util.BigDecimalUtil;
import com.google.api.ads.adwords.lib.jaxb.v201406.ReportDefinitionReportType;

import junit.framework.Assert;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests the Placeholder Feed Item report definition.
 * 
 *  * @author markbowyer@google.com (Mark R. Bowyer)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:aw-report-model-test-beans.xml")
public class ReportPlaceholderFeedItemTest extends
AbstractReportDefinitionTest<ReportPlaceholderFeedItem> {

  /**
   * C'tor
   */
  public ReportPlaceholderFeedItemTest() {

    super(ReportPlaceholderFeedItem.class,
        ReportDefinitionReportType.PLACEHOLDER_FEED_ITEM_REPORT,
        "src/test/resources/csv/placeholder-feeditem.csv");
  }

  @Override
  protected void testFirstEntry(ReportPlaceholderFeedItem first) {
    Assert.assertEquals(128401167L, first.getCampaignId().longValue());
    Assert.assertEquals(7788826047L, first.getAdGroupId().longValue());
    Assert.assertEquals(421887, first.getFeedId().longValue());
    Assert.assertEquals(1785447, first.getFeedItemId().longValue());
    Assert.assertEquals(1, first.getFeedPlaceholderType());
    Assert.assertEquals("Les Ecuries De Kerballan - Gommenech", first.getAccountDescriptiveName());
    Assert.assertEquals(0.7,first.getCost().doubleValue());
    Assert.assertEquals(9, first.getClicks().intValue());
    Assert.assertEquals(74, first.getImpressions().intValue());
    Assert.assertEquals(0, first.getConversions().intValue());
    Assert.assertEquals(BigDecimalUtil.parseFromNumberString("12.16"),
        BigDecimalUtil.parseFromNumberString(first.getCtr()));
    Assert.assertEquals(9.46, first.getAvgCpm().doubleValue());
    Assert.assertEquals(0.08, first.getAvgCpc().doubleValue());
    Assert.assertEquals(BigDecimalUtil.parseFromNumberString("2.2"),
        BigDecimalUtil.parseFromNumberString(first.getAvgPosition()));
    Assert.assertEquals("Headline", first.getClickType());
    Assert.assertEquals("Search Network", first.getAdNetwork());    
  }

  @Override
  protected void testLastEntry(ReportPlaceholderFeedItem last) {
    Assert.assertEquals(128401167L, last.getCampaignId().longValue());
    Assert.assertEquals(7788826287L, last.getAdGroupId().longValue());
    Assert.assertEquals(421887, last.getFeedId().longValue());
    Assert.assertEquals(1785567, last.getFeedItemId().longValue());
    Assert.assertEquals(1, last.getFeedPlaceholderType());
    Assert.assertEquals("Les Ecuries De Kerballan - Gommenech", last.getAccountDescriptiveName());
    Assert.assertEquals(0d, last.getCost().doubleValue());
    Assert.assertEquals(0, last.getClicks().intValue());
    Assert.assertEquals(130, last.getImpressions().intValue());
    Assert.assertEquals(0, last.getConversions().intValue());
    Assert.assertEquals(BigDecimalUtil.parseFromNumberString("0.0"),
        BigDecimalUtil.parseFromNumberString(last.getCtr()));
    Assert.assertEquals(0d, last.getAvgCpm().doubleValue());
    Assert.assertEquals(0d, last.getAvgCpc().doubleValue());
    Assert.assertEquals(BigDecimalUtil.parseFromNumberString("2.8"),
        BigDecimalUtil.parseFromNumberString(last.getAvgPosition()));
    Assert.assertEquals("Sitelink", last.getClickType());
    Assert.assertEquals("Search Network", last.getAdNetwork());
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
        // Specific to PlaceHolderFeedItem Performance Report
        "CampaignId",
        "CampaignName",
        "Status",
        "AdGroupId",
        "AdGroupName",
        "AdId",
        "FeedId",
        "FeedItemId",
        "PlaceholderType",
        "IsSelfAction"
        };
  }
}
