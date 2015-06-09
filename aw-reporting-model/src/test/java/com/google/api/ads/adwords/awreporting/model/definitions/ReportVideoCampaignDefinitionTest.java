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

import com.google.api.ads.adwords.awreporting.model.entities.Report;
import com.google.api.ads.adwords.awreporting.model.entities.ReportVideoCampaign;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionReportType;

import junit.framework.Assert;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests the Campaign Performance report definition.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:aw-report-model-test-beans.xml")
public class ReportVideoCampaignDefinitionTest extends
    AbstractReportDefinitionTest<ReportVideoCampaign> {

  /**
   * C'tor
   */
  public ReportVideoCampaignDefinitionTest() {

    super(ReportVideoCampaign.class, ReportDefinitionReportType.UNKNOWN,
        "src/test/resources/csv/video-campaign-performance.csv");
  }

  /**
   * @see com.google.api.ads.adwords.awreporting.model.definitions.
   *      AbstractReportDefinitionTest#testFirstEntry(com.google.api.ads.adwords.awreporting.model.entities.Report)
   */
  @Override
  protected void testFirstEntry(ReportVideoCampaign first) {

    Assert.assertEquals(123L, first.getAccountId().longValue());
    Assert.assertEquals("a", first.getAccount());
    Assert.assertEquals("a", first.getCampaignName());
    Assert.assertEquals("enabled", first.getStatus());
    Assert.assertEquals(179512L, first.getImpressions().longValue());
    Assert.assertEquals(2295L, first.getViews().longValue());
    Assert.assertEquals(73.16, first.getViewPlayed25BigDecimal().doubleValue());
    Assert.assertEquals(53.38, first.getViewPlayed50BigDecimal().doubleValue());
    Assert.assertEquals(42.15, first.getViewPlayed75BigDecimal().doubleValue());
    Assert.assertEquals(41.82, first.getViewPlayed100BigDecimal().doubleValue());
    Assert.assertEquals(0.00, first.getCtrBigDecimal().doubleValue());
    Assert.assertEquals(1300L, first.getBudget().longValue());
    Assert.assertEquals(0L, first.getWebsiteClicks().longValue());
  }

  /**
   * @see com.google.api.ads.adwords.awreporting.model.definitions.
   *      AbstractReportDefinitionTest#testLastEntry(com.google.api.ads.adwords.awreporting.model.entities.Report)
   */
  @Override
  protected void testLastEntry(ReportVideoCampaign last) {

    Assert.assertEquals(125L, last.getAccountId().longValue());
    Assert.assertEquals("c", last.getAccount());
    Assert.assertEquals("l", last.getCampaignName());
    Assert.assertEquals("enabled", last.getStatus());
    Assert.assertEquals(10223L, last.getImpressions().longValue());
    Assert.assertEquals(2374L, last.getViews().longValue());
    Assert.assertEquals(91.38, last.getViewPlayed25BigDecimal().doubleValue());
    Assert.assertEquals(39.55, last.getViewPlayed50BigDecimal().doubleValue());
    Assert.assertEquals(27.93, last.getViewPlayed75BigDecimal().doubleValue());
    Assert.assertEquals(23.42, last.getViewPlayed100BigDecimal().doubleValue());
    Assert.assertEquals(0.01, last.getCtrBigDecimal().doubleValue());
    Assert.assertEquals(1200L, last.getBudget().longValue());
    Assert.assertEquals(75L, last.getWebsiteClicks().longValue());
  }

  /**
   * Overridden to test the experimental report definition
   */
  @Override
  public void testReportTypeDefinition() {

    Class<? extends Report> beanClass =
        this.getCsvReportEntitiesMapping().getExperimentalReportBeanClass("VIDEO_CAMPAIGN_REPORT");
    Assert.assertNotNull(beanClass);

    Assert.assertEquals(ReportVideoCampaign.class, beanClass);
  }

  /**
   * @see com.google.api.ads.adwords.awreporting.model.definitions.
   *      AbstractReportDefinitionTest#retrieveCsvEntries()
   */
  @Override
  protected int retrieveCsvEntries() {

    return 12;
  }

  /**
   * Overridden because this class doesn't follow the API pattern. This is a file only report.
   */
  @Override
  public void testReportProperties() {
    // Does nothing
    Assert.assertTrue(true);
  }

  /**
   * @see com.google.api.ads.adwords.awreporting.model.definitions.
   *      AbstractReportDefinitionTest#retrievePropertiesToBeSelected()
   */
  @Override
  protected String[] retrievePropertiesToBeSelected() {
    return new String[] {
    // No need to return any fields
    };
  }
}
