//Copyright 2014 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.api.ads.adwords.awreporting.kratubackend;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.google.api.ads.adwords.awreporting.kratubackend.entities.Kratu;
import com.google.api.ads.adwords.awreporting.kratubackend.util.KratuStorageHelper;
import com.google.api.ads.adwords.awreporting.model.entities.Report;
import com.google.api.ads.adwords.awreporting.model.entities.ReportAccount;
import com.google.api.ads.adwords.awreporting.model.entities.ReportAd;
import com.google.api.ads.adwords.awreporting.model.entities.ReportAdExtension;
import com.google.api.ads.adwords.awreporting.model.entities.ReportAdGroup;
import com.google.api.ads.adwords.awreporting.model.entities.ReportCampaign;
import com.google.api.ads.adwords.awreporting.model.entities.ReportKeyword;
import com.google.api.ads.adwords.awreporting.model.util.DateUtil;
import com.google.api.ads.adwords.awreporting.server.entities.Account;
import com.google.api.client.util.Lists;
import com.google.common.collect.ImmutableList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

/**
 * Test case for the {@code KratuCompute} class.
 *
 * @author jtoledo@google.com (Julian Toledo)
 */
public class KratuComputeTest {

  private static final RoundingMode ROUNDING = RoundingMode.HALF_UP;
  private static final int SCALE = 2;

  private final List<Kratu> dailyKratus = Lists.newArrayList();

  private final Date day1 = DateUtil.parseDateTime("20140601").toDate();
  private final Date day2 = DateUtil.parseDateTime("20140602").toDate();

  private Kratu kratu1;
  private Kratu kratu2;
  
  private Account account;
  
  @Mock
  private KratuStorageHelper storageHelper;
  
  @Captor
  ArgumentCaptor<Class<? extends Report>> classReportCaptor;

  @Before
  public <R extends Report> void setUp() {

    account = new Account();
    account.setCurrencyCode("EUR");
    account.setDateTimeZone("Europe/Paris");
    account.setId("777");
    account.setName("Account1");

    kratu1 = new Kratu(123L, account, day1);
    kratu2 = new Kratu(123L, account, day2);
    kratu1.setAccountActive("Yes");
    kratu1.setAccountSuspended(false);
    kratu1.setAverageCpcDisplay(new BigDecimal(1L));
    kratu1.setAverageCpcSearch(new BigDecimal(2L));
    kratu1.setAverageCpmDisplay(new BigDecimal(3L));
    kratu1.setAverageCpmSearch(new BigDecimal(4L));
    kratu1.setAveragePositionDisplay(new BigDecimal(5L));
    kratu1.setAveragePositionSearch(new BigDecimal(6L));
    kratu1.setConversions(7L);
    kratu1.setCtrDisplay(new BigDecimal(8L));
    kratu1.setCtrSearch(new BigDecimal(9L));
    kratu1.setElegibleImpressionsDisplay(new BigDecimal(10L));
    kratu1.setElegibleImpressionsSearch(new BigDecimal(11L));
    kratu1.setImpressionsDisplay(12L);
    kratu1.setImpressionsSearch(13L);
    kratu1.setLostImpressionsDueToBidAdRankDisplay(new BigDecimal(14L));
    kratu1.setLostImpressionsDueToBidAdRankSearch(new BigDecimal(15L));
    kratu1.setLostImpressionsDueToBudgetDisplay(new BigDecimal(16L));
    kratu1.setLostImpressionsDueToBudgetSearch(new BigDecimal(17L));
    kratu1.setNumberOfActiveAdGroups(new BigDecimal(18L));
    kratu1.setNumberOfActiveAds(new BigDecimal(19L));
    kratu1.setNumberOfActiveAverageQualityScoreKeywords(new BigDecimal(20L));
    kratu1.setNumberOfActiveBroadMatchingKeywords(new BigDecimal(21L));
    kratu1.setNumberOfActiveCampaigns(new BigDecimal(22L));
    kratu1.setNumberOfActiveExactMatchingKeywords(new BigDecimal(23L));
    kratu1.setNumberOfActiveGoodQualityScoreKeywords(new BigDecimal(24L));
    kratu1.setNumberOfActivePhraseMatchingKeywords(new BigDecimal(25L));
    kratu1.setNumberOfActivePoorQualityScoreKeywords(new BigDecimal(26L));
    kratu1.setNumberOfAdGroupNegativeActiveKeywords(new BigDecimal(27L));
    kratu1.setNumberOfAdgroupsWithoneActiveAd(new BigDecimal(28L));
    kratu1.setNumberOfAdgroupsWithTwoActiveAds(new BigDecimal(29L));
    kratu1.setNumberOfCampaignNegativeActiveKeywords(new BigDecimal(30L));
    kratu1.setNumberOfCampaignsWithCallExtensionEnabled(new BigDecimal(31L));
    kratu1.setNumberOfCampaignsWithLocationExtensionEnabled(new BigDecimal(32L));
    kratu1.setNumberOfCampaignsWithSiteLinksEnabled(new BigDecimal(33L));
    kratu1.setNumberOfDisapprovedAds(new BigDecimal(34L));
    kratu1.setNumberOfNegativeActiveKeywords(new BigDecimal(35L));
    kratu1.setNumberOfPositiveActiveKeywords(new BigDecimal(36L));
    kratu1.setSpend(new BigDecimal(37L));
    kratu1.setSumBudget(new BigDecimal(38L));
    kratu1.setTotalClicksDisplay(39L);
    kratu1.setTotalClicksSearch(40L);
    kratu1.setWeightedAverageKeywordPosition(new BigDecimal(41L));
    kratu1.setWeightedAverageQualityScore(new BigDecimal(42L));

    kratu2.setAccountActive("Yes");
    kratu2.setAccountSuspended(false);
    kratu2.setAverageCpcDisplay(new BigDecimal(99.99));
    kratu2.setAverageCpcSearch(new BigDecimal(199.99));
    kratu2.setAverageCpmDisplay(new BigDecimal(299.99));
    kratu2.setAverageCpmSearch(new BigDecimal(399.99));    
    kratu2.setAveragePositionDisplay(new BigDecimal(99.99));
    kratu2.setAveragePositionSearch(new BigDecimal(99.99));
    kratu2.setConversions(33L);
    kratu2.setCtrDisplay(new BigDecimal(99.99));
    kratu2.setCtrSearch(new BigDecimal(99.99));
    kratu2.setElegibleImpressionsDisplay(new BigDecimal(99.99));
    kratu2.setElegibleImpressionsSearch(new BigDecimal(99.99));
    kratu2.setImpressionsDisplay(3000L);
    kratu2.setImpressionsSearch(5000L);
    kratu2.setLostImpressionsDueToBidAdRankDisplay(new BigDecimal(99.99));
    kratu2.setLostImpressionsDueToBidAdRankSearch(new BigDecimal(99.99));
    kratu2.setLostImpressionsDueToBudgetDisplay(new BigDecimal(99.99));
    kratu2.setLostImpressionsDueToBudgetSearch(new BigDecimal(99.99));
    kratu2.setNumberOfActiveAdGroups(new BigDecimal(99.99));
    kratu2.setNumberOfActiveAds(new BigDecimal(99.99));
    kratu2.setNumberOfActiveAverageQualityScoreKeywords(new BigDecimal(99.99));
    kratu2.setNumberOfActiveBroadMatchingKeywords(new BigDecimal(99.99));
    kratu2.setNumberOfActiveCampaigns(new BigDecimal(99.99));
    kratu2.setNumberOfActiveExactMatchingKeywords(new BigDecimal(99.99));
    kratu2.setNumberOfActiveGoodQualityScoreKeywords(new BigDecimal(99.99));
    kratu2.setNumberOfActivePhraseMatchingKeywords(new BigDecimal(99.99));
    kratu2.setNumberOfActivePoorQualityScoreKeywords(new BigDecimal(99.99));
    kratu2.setNumberOfAdGroupNegativeActiveKeywords(new BigDecimal(99.99));
    kratu2.setNumberOfAdgroupsWithoneActiveAd(new BigDecimal(99.99));
    kratu2.setNumberOfAdgroupsWithTwoActiveAds(new BigDecimal(99.99));
    kratu2.setNumberOfCampaignNegativeActiveKeywords(new BigDecimal(99.99));
    kratu2.setNumberOfCampaignsWithCallExtensionEnabled(new BigDecimal(99.99));
    kratu2.setNumberOfCampaignsWithLocationExtensionEnabled(new BigDecimal(99.99));
    kratu2.setNumberOfCampaignsWithSiteLinksEnabled(new BigDecimal(99.99));
    kratu2.setNumberOfDisapprovedAds(new BigDecimal(99.99));
    kratu2.setNumberOfNegativeActiveKeywords(new BigDecimal(99.99));
    kratu2.setNumberOfPositiveActiveKeywords(new BigDecimal(99.99));
    kratu2.setSpend(new BigDecimal(99.99));
    kratu2.setSumBudget(new BigDecimal(99.99));
    kratu2.setTotalClicksDisplay(60L);
    kratu2.setTotalClicksSearch(888L);
    kratu2.setWeightedAverageKeywordPosition(new BigDecimal(99.99));
    kratu2.setWeightedAverageQualityScore(new BigDecimal(99.99));

    dailyKratus.add(kratu1);
    dailyKratus.add(kratu2);

    MockitoAnnotations.initMocks(this);
    
    Mockito.doAnswer(new Answer<List<? extends Report>>() {
      @Override
      public List<? extends Report> answer(InvocationOnMock invocation) throws Throwable {

        if (invocation.getArguments()[0].equals(ReportAccount.class)) {
          ReportAccount reportAccount =  new ReportAccount();
          reportAccount.setCost(new BigDecimal(9.99));
          reportAccount.setConversions(100L);
          return ImmutableList.of(reportAccount);
        } else {
          return Lists.newArrayList();
        }

      }
    }).when(storageHelper)
        .getReportByAccountId(Mockito.<Class<? extends Report>>anyObject(), Mockito.any(Long.class),Mockito.any(Date.class), Mockito.any(Date.class));

  }

  /*
   * Test for createKratuSummary
   */
  @Test
  public void test_createKratuSummary() {    

    Kratu kratuSummarized = KratuCompute.createKratuSummary(dailyKratus, day1, day2);

    // From Account
    assertEquals(kratuSummarized.getTopAccountId(), new Long(123));
    assertEquals(kratuSummarized.getAccountName(), "Account1");
    assertEquals(kratuSummarized.getExternalCustomerId(), new Long(777));
    assertEquals(kratuSummarized.getCurrencyCode(), "EUR");
    assertEquals(kratuSummarized.getDateTimeZone(), "Europe/Paris");
    assertEquals(kratuSummarized.getAccountSuspended(), false);

    // General
    assertEquals(kratuSummarized.getSpend(), kratu1.getSpend().add(kratu2.getSpend()).setScale(SCALE, ROUNDING));
    assertEquals(kratuSummarized.getSumBudget(), kratu1.getSumBudget().add(kratu2.getSumBudget()));
    assertEquals(kratuSummarized.getConversions(), new Long(kratu1.getConversions() + kratu2.getConversions()));
    assertEquals(kratuSummarized.getAccountActive(), "Yes");

    // Search Info
    assertEquals(kratuSummarized.getTotalClicksSearch(),
        new Long(kratu1.getTotalClicksSearch() + kratu2.getTotalClicksSearch()));

    assertEquals(kratuSummarized.getImpressionsSearch(),
        new Long(kratu1.getImpressionsSearch() + kratu2.getImpressionsSearch()));

    assertEquals(kratuSummarized.getElegibleImpressionsSearch(),
        kratu1.getElegibleImpressionsSearch().add(kratu2.getElegibleImpressionsSearch()));

    assertEquals(kratuSummarized.getLostImpressionsDueToBudgetSearch(),
        kratu1.getLostImpressionsDueToBudgetSearch().add(kratu2.getLostImpressionsDueToBudgetSearch()));

    assertEquals(kratuSummarized.getLostImpressionsDueToBidAdRankSearch(),
        kratu1.getLostImpressionsDueToBidAdRankSearch().add(kratu2.getLostImpressionsDueToBidAdRankSearch()));

    assertEquals(kratuSummarized.getCtrSearch(),
        dailyAverage(kratu1.getCtrSearch(), kratu2.getCtrSearch()));

    assertEquals(kratuSummarized.getAverageCpcSearch(),
        dailyAverage(kratu1.getAverageCpcSearch(), kratu2.getAverageCpcSearch()));

    assertEquals(kratuSummarized.getAverageCpmSearch(),
        dailyAverage(kratu1.getAverageCpmSearch(), kratu2.getAverageCpmSearch()));

    assertEquals(kratuSummarized.getAveragePositionSearch(),
        dailyAverage(kratu1.getAveragePositionSearch(), kratu2.getAveragePositionSearch()));

    // Display Info
    assertEquals(kratuSummarized.getTotalClicksDisplay(),
        new Long(kratu1.getTotalClicksDisplay() + kratu2.getTotalClicksDisplay()));

    assertEquals(kratuSummarized.getImpressionsDisplay(),
        new Long(kratu1.getImpressionsDisplay() + kratu2.getImpressionsDisplay()));

    assertEquals(kratuSummarized.getElegibleImpressionsDisplay(),
        kratu1.getElegibleImpressionsDisplay().add(kratu2.getElegibleImpressionsDisplay()));

    assertEquals(kratuSummarized.getLostImpressionsDueToBudgetDisplay(),
        kratu1.getLostImpressionsDueToBudgetDisplay().add(kratu2.getLostImpressionsDueToBudgetDisplay()));

    assertEquals(kratuSummarized.getLostImpressionsDueToBidAdRankDisplay(),
        kratu1.getLostImpressionsDueToBidAdRankDisplay().add(kratu2.getLostImpressionsDueToBidAdRankDisplay()));

    assertEquals(kratuSummarized.getCtrDisplay(),
        dailyAverage(kratu1.getCtrDisplay(), kratu2.getCtrDisplay()));

    assertEquals(kratuSummarized.getAverageCpcDisplay(),
        dailyAverage(kratu1.getAverageCpcDisplay(), kratu2.getAverageCpcDisplay()));

    assertEquals(kratuSummarized.getAverageCpmDisplay(),
        dailyAverage(kratu1.getAverageCpmDisplay(), kratu2.getAverageCpmDisplay()));

    assertEquals(kratuSummarized.getAveragePositionDisplay(),
        dailyAverage(kratu1.getAveragePositionDisplay(), kratu2.getAveragePositionDisplay()));

    // Structural Info
    assertEquals(kratuSummarized.getNumberOfActiveCampaigns(),
        dailyAverage(kratu1.getNumberOfActiveCampaigns(), kratu2.getNumberOfActiveCampaigns()));
    
    assertEquals(kratuSummarized.getNumberOfActiveAdGroups(),
        dailyAverage(kratu1.getNumberOfActiveAdGroups(), kratu2.getNumberOfActiveAdGroups()));
    
    assertEquals(kratuSummarized.getNumberOfActiveAds(),
        dailyAverage(kratu1.getNumberOfActiveAds(), kratu2.getNumberOfActiveAds()));
    
    assertEquals(kratuSummarized.getNumberOfPositiveActiveKeywords(),
        dailyAverage(kratu1.getNumberOfPositiveActiveKeywords(), kratu2.getNumberOfPositiveActiveKeywords()));


    assertEquals(kratuSummarized.getNumberOfActiveBroadMatchingKeywords(),
        dailyAverage(kratu1.getNumberOfActiveBroadMatchingKeywords(), kratu2.getNumberOfActiveBroadMatchingKeywords()));
    
    assertEquals(kratuSummarized.getNumberOfActivePhraseMatchingKeywords(),
        dailyAverage(kratu1.getNumberOfActivePhraseMatchingKeywords(), kratu2.getNumberOfActivePhraseMatchingKeywords()));
    
    assertEquals(kratuSummarized.getNumberOfAdGroupNegativeActiveKeywords(),
        dailyAverage(kratu1.getNumberOfAdGroupNegativeActiveKeywords(), kratu2.getNumberOfAdGroupNegativeActiveKeywords()));

    assertEquals(kratuSummarized.getNumberOfActiveGoodQualityScoreKeywords(),
        dailyAverage(kratu1.getNumberOfActiveGoodQualityScoreKeywords(), kratu2.getNumberOfActiveGoodQualityScoreKeywords()));
    
    assertEquals(kratuSummarized.getNumberOfActiveAverageQualityScoreKeywords(),
        dailyAverage(kratu1.getNumberOfActiveAverageQualityScoreKeywords(), kratu2.getNumberOfActiveAverageQualityScoreKeywords()));
    
    assertEquals(kratuSummarized.getNumberOfActivePoorQualityScoreKeywords(),
        dailyAverage(kratu1.getNumberOfActivePoorQualityScoreKeywords(), kratu2.getNumberOfActivePoorQualityScoreKeywords()));


    assertEquals(kratuSummarized.getNumberOfCampaignsWithCallExtensionEnabled(),
        dailyAverage(kratu1.getNumberOfCampaignsWithCallExtensionEnabled(), kratu2.getNumberOfCampaignsWithCallExtensionEnabled()));
    
    assertEquals(kratuSummarized.getNumberOfCampaignsWithLocationExtensionEnabled(),
        dailyAverage(kratu1.getNumberOfCampaignsWithLocationExtensionEnabled(), kratu2.getNumberOfCampaignsWithLocationExtensionEnabled()));
    
    assertEquals(kratuSummarized.getNumberOfCampaignsWithSiteLinksEnabled(),
        dailyAverage(kratu1.getNumberOfCampaignsWithSiteLinksEnabled(), kratu2.getNumberOfCampaignsWithSiteLinksEnabled()));


    assertEquals(kratuSummarized.getNumberOfAdgroupsWithoneActiveAd(),
        dailyAverage(kratu1.getNumberOfAdgroupsWithoneActiveAd(), kratu2.getNumberOfAdgroupsWithoneActiveAd()));

    assertEquals(kratuSummarized.getNumberOfAdgroupsWithTwoActiveAds(),
        dailyAverage(kratu1.getNumberOfAdgroupsWithTwoActiveAds(), kratu2.getNumberOfAdgroupsWithTwoActiveAds()));
    
    assertEquals(kratuSummarized.getNumberOfDisapprovedAds(),
        dailyAverage(kratu1.getNumberOfDisapprovedAds(), kratu2.getNumberOfDisapprovedAds()));

    
    assertEquals(kratuSummarized.getWeightedAverageKeywordPosition(),
        dailyAverage(kratu1.getWeightedAverageKeywordPosition(), kratu2.getWeightedAverageKeywordPosition()));
    
    assertEquals(kratuSummarized.getWeightedAverageQualityScore(),
        dailyAverage(kratu1.getWeightedAverageQualityScore(), kratu2.getWeightedAverageQualityScore())); 
  }

  /*
   * Test for createDailyKratuFromDB
   */
  @Test
  public void test_createDailyKratuFromDB() {

    KratuCompute.createDailyKratuFromDB(storageHelper, 1L, account, day1);

    ArgumentCaptor<Long> accountIdCaptor = ArgumentCaptor.forClass(Long.class);
    ArgumentCaptor<Date> date1Captor = ArgumentCaptor.forClass(Date.class);
    ArgumentCaptor<Date> date2Captor = ArgumentCaptor.forClass(Date.class);

    verify(storageHelper, times(6)).getReportByAccountId(
        classReportCaptor.capture(), accountIdCaptor.capture(), date1Captor.capture(), date2Captor.capture());

    List<Class<? extends Report>> classes = classReportCaptor.getAllValues();
    assertEquals(classes.get(0), ReportAccount.class);
    assertEquals(classes.get(1), ReportCampaign.class);
    assertEquals(classes.get(2), ReportAdGroup.class);
    assertEquals(classes.get(3), ReportAd.class);
    assertEquals(classes.get(4), ReportKeyword.class);
    assertEquals(classes.get(5), ReportAdExtension.class);
    
    List<Long> accountIds = accountIdCaptor.getAllValues();
    assertEquals(accountIds.get(0), new Long(777));
    assertEquals(accountIds.get(1), new Long(777));
    assertEquals(accountIds.get(2), new Long(777));
    assertEquals(accountIds.get(3), new Long(777));
    assertEquals(accountIds.get(4), new Long(777));
    assertEquals(accountIds.get(5), new Long(777));
    
    List<Date> date1Captors = date1Captor.getAllValues();
    assertEquals(date1Captors.get(0), day1);
    assertEquals(date1Captors.get(1), day1);
    assertEquals(date1Captors.get(2), day1);
    assertEquals(date1Captors.get(3), day1);
    assertEquals(date1Captors.get(4), day1);
    assertEquals(date1Captors.get(5), day1);
    
    List<Date> date2Captors = date2Captor.getAllValues();
    assertEquals(date1Captors.get(0), day1);
    assertEquals(date2Captors.get(1), day1);
    assertEquals(date2Captors.get(2), day1);
    assertEquals(date2Captors.get(3), day1);
    assertEquals(date2Captors.get(4), day1);
    assertEquals(date2Captors.get(5), day1);
  }

  private BigDecimal dailyAverage(BigDecimal bigDecimal1, BigDecimal bigDecimal2) {
    BigDecimal daysInRange = new BigDecimal(2);
    return bigDecimal1.divide(daysInRange, SCALE, ROUNDING).add(bigDecimal2.divide(daysInRange, SCALE, ROUNDING));
  }
}
