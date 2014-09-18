//Copyright 2012 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.awreporting.kratubackend.entities;

import com.google.api.ads.adwords.awreporting.model.persistence.mongodb.MongoEntity;
import com.google.api.ads.adwords.awreporting.model.util.DateUtil;
import com.google.api.ads.adwords.awreporting.server.entities.Account;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Kratu is a colection of signals from different AdWords Reports
 * 
 * @author jtoledo@google.com (Julian Toledo)
 */
@Entity
@Table(name = "AW_Kratu")
public class Kratu implements MongoEntity {

  public static final String TOP_ACCOUNT_ID = "topAccountId";
  public static final String EXTERNAL_CUSTOMER_ID = "externalCustomerId";
  public static final String DAY = "day";

  @Id
  @Column(name = "ID")
  private String id;

  @Column(name = "EXTERNAL_CUSTOMER_ID")
  private Long externalCustomerId;

  @Column(name = "TOP_ACCOUNT_ID")
  private Long topAccountId;

  @Column(name = "ACCOUNT_NAME")
  private String accountName;

  @Column(name = "CURRENCY_CODE")
  private String currencyCode;

  @Column(name = "DATE_TIME_ZONE")
  private String dateTimeZone;

  @Column(name = "ACCOUNT_ACIVE")
  private String accountActive = "No";

  @Column(name = "TOTAL_CLICKS_SEARCH")
  private Long totalClicksSearch = 0l;

  @Column(name = "IMPRESSIONS_SEARCH")
  private Long impressionsSearch = 0l;

  @Column(name = "CTR_SEARCH")
  private BigDecimal ctrSearch = BigDecimal.ZERO;

  @Column(name = "AVERAGE_CPC_SEARCH")
  private BigDecimal averageCpcSearch = BigDecimal.ZERO;

  @Column(name = "AVERAGE_CPM_SEARCH")
  private BigDecimal averageCpmSearch = BigDecimal.ZERO;

  @Column(name = "AVERAGE_POSITION_SEARCH")
  private BigDecimal averagePositionSearch = BigDecimal.ZERO;

  @Column(name = "TOTAL_CLICKS_DISPLAY")
  private Long totalClicksDisplay = 0l;

  @Column(name = "IMPRESSIONS_DISPLAY")
  private Long impressionsDisplay = 0l;

  @Column(name = "CTR_DISPLAY")
  private BigDecimal ctrDisplay = BigDecimal.ZERO;

  @Column(name = "AVERAGE_CPC_DISPLAY")
  private BigDecimal averageCpcDisplay = BigDecimal.ZERO;

  @Column(name = "AVERAGE_CPM_DISPLAY")
  private BigDecimal averageCpmDisplay = BigDecimal.ZERO;

  @Column(name = "AVERAGE_POSITION_DISPLAY")
  private BigDecimal averagePositionDisplay = BigDecimal.ZERO;

  @Column(name = "DAY")
  private Date day;

  @Column(name = "SPEND")
  private BigDecimal spend = BigDecimal.ZERO;

  @Column(name = "CONVERSIONS")
  private Long conversions = 0l;

  @Column(name = "ELEGIBLE_IMPRESSION_SEARCH")
  private BigDecimal elegibleImpressionsSearch = BigDecimal.ZERO;

  @Column(name = "ELEGIBLE_IMPRESSION_DISPLAY")
  private BigDecimal elegibleImpressionsDisplay = BigDecimal.ZERO;

  @Column(name = "LOST_IMPRESSIONS_DUE_TO_BUDGET_SEARCH")
  private BigDecimal lostImpressionsDueToBudgetSearch = BigDecimal.ZERO;

  @Column(name = "LOST_IMPRESSIONS_DUE_TO_BID_RANK_SEARCH")
  private BigDecimal lostImpressionsDueToBidAdRankSearch = BigDecimal.ZERO;

  @Column(name = "LOST_IMPRESSIONS_DUE_TO_BUDGET_DISPLAY")
  private BigDecimal lostImpressionsDueToBudgetDisplay = BigDecimal.ZERO;

  @Column(name = "LOST_IMPRESSIONS_DUE_TO_BID_RANK_DISPLAY")
  private BigDecimal lostImpressionsDueToBidAdRankDisplay = BigDecimal.ZERO;

  @Column(name = "NUMBER_OF_ACTIVE_CAMPAINGS")
  private BigDecimal numberOfActiveCampaigns = BigDecimal.ZERO;

  @Column(name = "NUMBER_OF_ACTIVE_ADGROUPS")
  private BigDecimal numberOfActiveAdGroups = BigDecimal.ZERO;

  @Column(name = "NUMBER_OF_ACTIVE_ADS")
  private BigDecimal numberOfActiveAds = BigDecimal.ZERO;

  @Column(name = "NUMBER_OF_POSITIVE_ACTIVE_KEYWORDS")
  private BigDecimal numberOfPositiveActiveKeywords = BigDecimal.ZERO;

  @Column(name = "NUMBER_OF_ACTIVE_BROAD_MATCHING_KEYWORDS")
  private BigDecimal numberOfActiveBroadMatchingKeywords = BigDecimal.ZERO;

  @Column(name = "NUMBER_OF_ACTIVE_PHRASE_MATCHING_KEYWORDS")
  private BigDecimal numberOfActivePhraseMatchingKeywords = BigDecimal.ZERO;

  @Column(name = "NUMBER_OF_ACTIVE_EXACT_MATCHING_KEYWORDS")
  private BigDecimal numberOfActiveExactMatchingKeywords = BigDecimal.ZERO;

  @Column(name = "NUMBER_OF_NEGATIVE_ACTIVE_KEYWORDS")
  private BigDecimal numberOfNegativeActiveKeywords = BigDecimal.ZERO;

  @Column(name = "NUMBER_OF_CAMPAIGN_NEGATIVE_ACTIVE_KEYWORDS")
  private BigDecimal numberOfCampaignNegativeActiveKeywords = BigDecimal.ZERO;

  @Column(name = "NUMBER_OF_ADGROUP_NEGATIVE_ACTIVE_KEYWORDS")
  private BigDecimal numberOfAdGroupNegativeActiveKeywords = BigDecimal.ZERO;

  @Column(name = "NUMBER_OF_ACTIVE_GOOD_QUALITY_SCORE_KEYWORDS")
  private BigDecimal numberOfActiveGoodQualityScoreKeywords = BigDecimal.ZERO;

  @Column(name = "NUMBER_OF_ACTIVE_AVERAGE_QUALITY_SCORE_KEYWORDS")
  private BigDecimal numberOfActiveAverageQualityScoreKeywords = BigDecimal.ZERO;

  @Column(name = "NUMBER_OF_ACTIVE_POOR_QUALITY_SCORE_KEYWORDS")
  private BigDecimal numberOfActivePoorQualityScoreKeywords = BigDecimal.ZERO;

  @Column(name = "NUMBER_OF_CAMPAIGNS_WITH_CALL_EXTENSIONS_ENABLE")
  private BigDecimal numberOfCampaignsWithCallExtensionEnabled = BigDecimal.ZERO;

  @Column(name = "NUMBER_OF_CAMPAIGNS_WITH_LOCATION_EXTENSIONS_ENABLE")
  private BigDecimal numberOfCampaignsWithLocationExtensionEnabled = BigDecimal.ZERO;

  @Column(name = "NUMBER_OF_CAMPAIGNS_WITH_SITELINKS_ENABLE")
  private BigDecimal numberOfCampaignsWithSiteLinksEnabled = BigDecimal.ZERO;

  @Column(name = "WEIHTED_AVERAGE_KEYWORD_POSITION")
  private BigDecimal weightedAverageKeywordPosition = BigDecimal.ZERO;

  @Column(name = "WEIHTED_AVERAGE_QUALITY_SCORE")
  private BigDecimal weightedAverageQualityScore = BigDecimal.ZERO;

  @Column(name = "SUM_BUDGET")
  private BigDecimal sumBudget = BigDecimal.ZERO;

  @Column(name = "ACCOUNT_SUSPENDED")
  private Boolean accountSuspended = false;

  @Column(name = "NUMBER_OF_ADGROUPS_WITH_ONE_ACTIVE_AD")
  private BigDecimal numberOfAdgroupsWithoneActiveAd = BigDecimal.ZERO;

  @Column(name = "NUMBER_OF_ADGROUPS_WITH_TWO_ACTIVE_AD")
  private BigDecimal numberOfAdgroupsWithTwoActiveAds = BigDecimal.ZERO;

  @Column(name = "NUMBER_OF_DISAPPROVED_ADS")
  private BigDecimal numberOfDisapprovedAds = BigDecimal.ZERO;

  /**
   * C'tor
   */
  public Kratu() {
  }

  /**
   * Creates a new Kratu with the base Account Info
   * 
   * @param topAccountId The top MCC for the account
   * @param account the AdWords Account
   * @param day the Day to create the Kratu for
   */
  public Kratu(Long topAccountId, Account account, Date day) {
    this.setTopAccountId(topAccountId);
    this.setExternalCustomerId(account.getExternalCustomerId());
    this.setAccountName(account.getName());
    this.setCurrencyCode(account.getCurrencyCode());
    this.setDateTimeZone(account.getDateTimeZone());
    this.setDay(day);
    this.setId(account.getExternalCustomerId() + "-" + DateUtil.formatYearMonthDayNoDash(day));
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Long getExternalCustomerId() {
    return externalCustomerId;
  }

  public void setExternalCustomerId(Long externalCustomerId) {
    this.externalCustomerId = externalCustomerId;
  }

  public Long getTopAccountId() {
    return topAccountId;
  }

  public void setTopAccountId(Long topAccountId) {
    this.topAccountId = topAccountId;
  }

  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public String getDateTimeZone() {
    return dateTimeZone;
  }

  public void setDateTimeZone(String dateTimeZone) {
    this.dateTimeZone = dateTimeZone;
  }

  public String getAccountActive() {
    return accountActive;
  }

  public void setAccountActive(String accountActive) {
    this.accountActive = accountActive;
  }

  public Long getTotalClicksSearch() {
    return totalClicksSearch;
  }

  public void setTotalClicksSearch(Long totalClicksSearch) {
    this.totalClicksSearch = totalClicksSearch;
  }

  public Long getImpressionsSearch() {
    return impressionsSearch;
  }

  public void setImpressionsSearch(Long impressionsSearch) {
    this.impressionsSearch = impressionsSearch;
  }

  public BigDecimal getCtrSearch() {
    return ctrSearch;
  }

  public void setCtrSearch(BigDecimal ctrSearch) {
    this.ctrSearch = ctrSearch;
  }

  public BigDecimal getAverageCpcSearch() {
    return averageCpcSearch;
  }

  public void setAverageCpcSearch(BigDecimal averageCpcSearch) {
    this.averageCpcSearch = averageCpcSearch;
  }

  public BigDecimal getAverageCpmSearch() {
    return averageCpmSearch;
  }

  public void setAverageCpmSearch(BigDecimal averageCpmSearch) {
    this.averageCpmSearch = averageCpmSearch;
  }

  public BigDecimal getAveragePositionSearch() {
    return averagePositionSearch;
  }

  public void setAveragePositionSearch(BigDecimal averagePositionSearch) {
    this.averagePositionSearch = averagePositionSearch;
  }

  public Long getTotalClicksDisplay() {
    return totalClicksDisplay;
  }

  public void setTotalClicksDisplay(Long totalClicksDisplay) {
    this.totalClicksDisplay = totalClicksDisplay;
  }

  public Long getImpressionsDisplay() {
    return impressionsDisplay;
  }

  public void setImpressionsDisplay(Long impressionsDisplay) {
    this.impressionsDisplay = impressionsDisplay;
  }

  public BigDecimal getCtrDisplay() {
    return ctrDisplay;
  }

  public void setCtrDisplay(BigDecimal ctrDisplay) {
    this.ctrDisplay = ctrDisplay;
  }

  public BigDecimal getAverageCpcDisplay() {
    return averageCpcDisplay;
  }

  public void setAverageCpcDisplay(BigDecimal averageCpcDisplay) {
    this.averageCpcDisplay = averageCpcDisplay;
  }

  public BigDecimal getAverageCpmDisplay() {
    return averageCpmDisplay;
  }

  public void setAverageCpmDisplay(BigDecimal averageCpmDisplay) {
    this.averageCpmDisplay = averageCpmDisplay;
  }

  public BigDecimal getAveragePositionDisplay() {
    return averagePositionDisplay;
  }

  public void setAveragePositionDisplay(BigDecimal averagePositionDisplay) {
    this.averagePositionDisplay = averagePositionDisplay;
  }

  public Date getDay() {
    return day;
  }

  public void setDay(Date day) {
    this.day = day;
  }

  public BigDecimal getSpend() {
    return spend;
  }

  public void setSpend(BigDecimal spend) {
    this.spend = spend;
  }

  public Long getConversions() {
    return conversions;
  }

  public void setConversions(Long conversions) {
    this.conversions = conversions;
  }

  public BigDecimal getElegibleImpressionsSearch() {
    return elegibleImpressionsSearch;
  }

  public void setElegibleImpressionsSearch(BigDecimal elegibleImpressionsSearch) {
    this.elegibleImpressionsSearch = elegibleImpressionsSearch;
  }

  public BigDecimal getElegibleImpressionsDisplay() {
    return elegibleImpressionsDisplay;
  }

  public void setElegibleImpressionsDisplay(BigDecimal elegibleImpressionsDisplay) {
    this.elegibleImpressionsDisplay = elegibleImpressionsDisplay;
  }

  public BigDecimal getLostImpressionsDueToBudgetSearch() {
    return lostImpressionsDueToBudgetSearch;
  }

  public void setLostImpressionsDueToBudgetSearch(BigDecimal lostImpressionsDueToBudgetSearch) {
    this.lostImpressionsDueToBudgetSearch = lostImpressionsDueToBudgetSearch;
  }

  public BigDecimal getLostImpressionsDueToBidAdRankSearch() {
    return lostImpressionsDueToBidAdRankSearch;
  }

  public void setLostImpressionsDueToBidAdRankSearch(BigDecimal lostImpressionsDueToBidAdRankSearch) {
    this.lostImpressionsDueToBidAdRankSearch = lostImpressionsDueToBidAdRankSearch;
  }

  public BigDecimal getLostImpressionsDueToBudgetDisplay() {
    return lostImpressionsDueToBudgetDisplay;
  }

  public void setLostImpressionsDueToBudgetDisplay(BigDecimal lostImpressionsDueToBudgetDisplay) {
    this.lostImpressionsDueToBudgetDisplay = lostImpressionsDueToBudgetDisplay;
  }

  public BigDecimal getLostImpressionsDueToBidAdRankDisplay() {
    return lostImpressionsDueToBidAdRankDisplay;
  }

  public void setLostImpressionsDueToBidAdRankDisplay(BigDecimal lostImpressionsDueToBidAdRankDisplay) {
    this.lostImpressionsDueToBidAdRankDisplay = lostImpressionsDueToBidAdRankDisplay;
  }

  public BigDecimal getNumberOfActiveCampaigns() {
    return numberOfActiveCampaigns;
  }

  public void setNumberOfActiveCampaigns(BigDecimal numberOfActiveCampaigns) {
    this.numberOfActiveCampaigns = numberOfActiveCampaigns;
  }

  public BigDecimal getNumberOfActiveAdGroups() {
    return numberOfActiveAdGroups;
  }

  public void setNumberOfActiveAdGroups(BigDecimal numberOfActiveAdGroups) {
    this.numberOfActiveAdGroups = numberOfActiveAdGroups;
  }

  public BigDecimal getNumberOfActiveAds() {
    return numberOfActiveAds;
  }

  public void setNumberOfActiveAds(BigDecimal numberOfActiveAds) {
    this.numberOfActiveAds = numberOfActiveAds;
  }

  public BigDecimal getNumberOfPositiveActiveKeywords() {
    return numberOfPositiveActiveKeywords;
  }

  public void setNumberOfPositiveActiveKeywords(BigDecimal numberOfPositiveActiveKeywords) {
    this.numberOfPositiveActiveKeywords = numberOfPositiveActiveKeywords;
  }

  public BigDecimal getNumberOfActiveBroadMatchingKeywords() {
    return numberOfActiveBroadMatchingKeywords;
  }

  public void setNumberOfActiveBroadMatchingKeywords(BigDecimal numberOfActiveBroadMatchingKeywords) {
    this.numberOfActiveBroadMatchingKeywords = numberOfActiveBroadMatchingKeywords;
  }

  public BigDecimal getNumberOfActivePhraseMatchingKeywords() {
    return numberOfActivePhraseMatchingKeywords;
  }

  public void setNumberOfActivePhraseMatchingKeywords(BigDecimal numberOfActivePhraseMatchingKeywords) {
    this.numberOfActivePhraseMatchingKeywords = numberOfActivePhraseMatchingKeywords;
  }

  public BigDecimal getNumberOfActiveExactMatchingKeywords() {
    return numberOfActiveExactMatchingKeywords;
  }

  public void setNumberOfActiveExactMatchingKeywords(BigDecimal numberOfActiveExactMatchingKeywords) {
    this.numberOfActiveExactMatchingKeywords = numberOfActiveExactMatchingKeywords;
  }

  public BigDecimal getNumberOfNegativeActiveKeywords() {
    return numberOfNegativeActiveKeywords;
  }

  public void setNumberOfNegativeActiveKeywords(BigDecimal numberOfNegativeActiveKeywords) {
    this.numberOfNegativeActiveKeywords = numberOfNegativeActiveKeywords;
  }

  public BigDecimal getNumberOfCampaignNegativeActiveKeywords() {
    return numberOfCampaignNegativeActiveKeywords;
  }

  public void setNumberOfCampaignNegativeActiveKeywords(
      BigDecimal numberOfCampaignNegativeActiveKeywords) {
    this.numberOfCampaignNegativeActiveKeywords = numberOfCampaignNegativeActiveKeywords;
  }

  public BigDecimal getNumberOfAdGroupNegativeActiveKeywords() {
    return numberOfAdGroupNegativeActiveKeywords;
  }

  public void setNumberOfAdGroupNegativeActiveKeywords(
      BigDecimal numberOfAdGroupNegativeActiveKeywords) {
    this.numberOfAdGroupNegativeActiveKeywords = numberOfAdGroupNegativeActiveKeywords;
  }

  public BigDecimal getNumberOfActiveGoodQualityScoreKeywords() {
    return numberOfActiveGoodQualityScoreKeywords;
  }

  public void setNumberOfActiveGoodQualityScoreKeywords(
      BigDecimal numberOfActiveGoodQualityScoreKeywords) {
    this.numberOfActiveGoodQualityScoreKeywords = numberOfActiveGoodQualityScoreKeywords;
  }

  public BigDecimal getNumberOfActiveAverageQualityScoreKeywords() {
    return numberOfActiveAverageQualityScoreKeywords;
  }

  public void setNumberOfActiveAverageQualityScoreKeywords(
      BigDecimal numberOfActiveAverageQualityScoreKeywords) {
    this.numberOfActiveAverageQualityScoreKeywords = numberOfActiveAverageQualityScoreKeywords;
  }

  public BigDecimal getNumberOfActivePoorQualityScoreKeywords() {
    return numberOfActivePoorQualityScoreKeywords;
  }

  public void setNumberOfActivePoorQualityScoreKeywords(
      BigDecimal numberOfActivePoorQualityScoreKeywords) {
    this.numberOfActivePoorQualityScoreKeywords = numberOfActivePoorQualityScoreKeywords;
  }

  public BigDecimal getNumberOfCampaignsWithCallExtensionEnabled() {
    return numberOfCampaignsWithCallExtensionEnabled;
  }

  public void setNumberOfCampaignsWithCallExtensionEnabled(
      BigDecimal numberOfCampaignsWithCallExtensionEnabled) {
    this.numberOfCampaignsWithCallExtensionEnabled = numberOfCampaignsWithCallExtensionEnabled;
  }

  public BigDecimal getNumberOfCampaignsWithLocationExtensionEnabled() {
    return numberOfCampaignsWithLocationExtensionEnabled;
  }

  public void setNumberOfCampaignsWithLocationExtensionEnabled(
      BigDecimal numberOfCampaignsWithLocationExtensionEnabled) {
    this.numberOfCampaignsWithLocationExtensionEnabled =
        numberOfCampaignsWithLocationExtensionEnabled;
  }

  public BigDecimal getNumberOfCampaignsWithSiteLinksEnabled() {
    return numberOfCampaignsWithSiteLinksEnabled;
  }

  public void setNumberOfCampaignsWithSiteLinksEnabled(
      BigDecimal numberOfCampaignsWithSiteLinksEnabled) {
    this.numberOfCampaignsWithSiteLinksEnabled = numberOfCampaignsWithSiteLinksEnabled;
  }

  public BigDecimal getWeightedAverageKeywordPosition() {
    return weightedAverageKeywordPosition;
  }

  public void setWeightedAverageKeywordPosition(BigDecimal weightedAverageKeywordPosition) {
    this.weightedAverageKeywordPosition = weightedAverageKeywordPosition;
  }

  public BigDecimal getWeightedAverageQualityScore() {
    return weightedAverageQualityScore;
  }

  public void setWeightedAverageQualityScore(BigDecimal weightedAverageQualityScore) {
    this.weightedAverageQualityScore = weightedAverageQualityScore;
  }

  public BigDecimal getSumBudget() {
    return sumBudget;
  }

  public void setSumBudget(BigDecimal sumBudget) {
    this.sumBudget = sumBudget;
  }

  public Boolean getAccountSuspended() {
    return accountSuspended;
  }

  public void setAccountSuspended(Boolean accountSuspended) {
    this.accountSuspended = accountSuspended;
  }

  public BigDecimal getNumberOfAdgroupsWithoneActiveAd() {
    return numberOfAdgroupsWithoneActiveAd;
  }

  public void setNumberOfAdgroupsWithoneActiveAd(BigDecimal numberOfAdgroupsWithoneActiveAd) {
    this.numberOfAdgroupsWithoneActiveAd = numberOfAdgroupsWithoneActiveAd;
  }

  public BigDecimal getNumberOfAdgroupsWithTwoActiveAds() {
    return numberOfAdgroupsWithTwoActiveAds;
  }

  public void setNumberOfAdgroupsWithTwoActiveAds(BigDecimal numberOfAdgroupsWithTwoActiveAds) {
    this.numberOfAdgroupsWithTwoActiveAds = numberOfAdgroupsWithTwoActiveAds;
  }

  public BigDecimal getNumberOfDisapprovedAds() {
    return numberOfDisapprovedAds;
  }

  public void setNumberOfDisapprovedAds(BigDecimal numberOfDisapprovedAds) {
    this.numberOfDisapprovedAds = numberOfDisapprovedAds;
  }

  // Add methods  

  public void addTotalClicksSearch(Long totalClicksSearch) {
    this.totalClicksSearch += totalClicksSearch;
  }

  public void addImpressionsSearch(Long impressionsSearch) {
    this.impressionsSearch += impressionsSearch;
  }

  public void addTotalClicksDisplay(Long totalClicksDisplay) {
    this.totalClicksDisplay += totalClicksDisplay;
  }

  public void addImpressionsDisplay(Long impressionsDisplay) {
    this.impressionsDisplay += impressionsDisplay;
  }  
  public void addCtrSearch(BigDecimal bigDecimal) {
    ctrSearch = ctrSearch.add(bigDecimal);
  }

  public void addAverageCpcSearch(BigDecimal bigDecimal) {
    averageCpcSearch = averageCpcSearch.add(bigDecimal);
  }

  public void addAverageCpmSearch(BigDecimal bigDecimal) {
    averageCpmSearch = averageCpmSearch.add(bigDecimal);
  }

  public void addAveragePositionSearch(BigDecimal bigDecimal) {
    averagePositionSearch = averagePositionSearch.add(bigDecimal);
  }

  public void addCtrDisplay(BigDecimal bigDecimal) {
    ctrDisplay = ctrDisplay.add(bigDecimal);
  }

  public void addAverageCpcDisplay(BigDecimal bigDecimal) {
    averageCpcDisplay = averageCpcDisplay.add(bigDecimal);
  }

  public void addAverageCpmDisplay(BigDecimal bigDecimal) {
    averageCpmDisplay = averageCpmDisplay.add(bigDecimal);
  }

  public void addAveragePositionDisplay(BigDecimal bigDecimal) {
    averagePositionDisplay = averagePositionDisplay.add(bigDecimal);
  }

  public void addSpend(BigDecimal bigDecimal) {
    spend = spend.add(bigDecimal);
  }
  
  public void addConversions(Long conversions) {
    this.conversions += conversions;
  }

  public void addElegibleImpressionsSearch(BigDecimal bigDecimal) {
    elegibleImpressionsSearch = elegibleImpressionsSearch.add(bigDecimal);
  }

  public void addElegibleImpressionsDisplay(BigDecimal bigDecimal) {
    elegibleImpressionsDisplay = elegibleImpressionsDisplay.add(bigDecimal);
  }

  public void addLostImpressionsDueToBudgetSearch(BigDecimal bigDecimal) {
    lostImpressionsDueToBudgetSearch = lostImpressionsDueToBudgetSearch.add(bigDecimal);
  }

  public void addLostImpressionsDueToBidAdRankSearch(BigDecimal bigDecimal) {
    lostImpressionsDueToBidAdRankSearch = lostImpressionsDueToBidAdRankSearch.add(bigDecimal);
  }

  public void addLostImpressionsDueToBudgetDisplay(BigDecimal bigDecimal) {
    lostImpressionsDueToBudgetDisplay = lostImpressionsDueToBudgetDisplay.add(bigDecimal);
  }

  public void addLostImpressionsDueToBidAdRankDisplay(BigDecimal bigDecimal) {
    lostImpressionsDueToBidAdRankDisplay = lostImpressionsDueToBidAdRankDisplay.add(bigDecimal);
  }
  public void addNumberOfActiveCampaigns(BigDecimal bigDecimal) {
    numberOfActiveCampaigns = numberOfActiveCampaigns.add(bigDecimal);
  }

  public void addNumberOfActiveAdGroups(BigDecimal bigDecimal) {
    numberOfActiveAdGroups = numberOfActiveAdGroups.add(bigDecimal);
  }

  public void addNumberOfActiveAds(BigDecimal bigDecimal) {
    numberOfActiveAds = numberOfActiveAds.add(bigDecimal);
  }

  public void addNumberOfPositiveActiveKeywords(BigDecimal bigDecimal) {
    numberOfPositiveActiveKeywords = numberOfPositiveActiveKeywords.add(bigDecimal);
  }

  public void addNumberOfActiveBroadMatchingKeywords(BigDecimal bigDecimal) {
    numberOfActiveBroadMatchingKeywords = numberOfActiveBroadMatchingKeywords.add(bigDecimal);
  }

  public void addNumberOfActivePhraseMatchingKeywords(BigDecimal bigDecimal) {
    numberOfActivePhraseMatchingKeywords = numberOfActivePhraseMatchingKeywords.add(bigDecimal);
  }

  public void addNumberOfActiveExactMatchingKeywords(BigDecimal bigDecimal) {
    numberOfActiveExactMatchingKeywords = numberOfActiveExactMatchingKeywords.add(bigDecimal);
  }

  public void addNumberOfNegativeActiveKeywords(BigDecimal bigDecimal) {
    numberOfNegativeActiveKeywords = numberOfNegativeActiveKeywords.add(bigDecimal);
  }

  public void addNumberOfCampaignNegativeActiveKeywords(BigDecimal bigDecimal) {
    numberOfCampaignNegativeActiveKeywords = numberOfCampaignNegativeActiveKeywords.add(bigDecimal);
  }

  public void addNumberOfAdGroupNegativeActiveKeywords(BigDecimal bigDecimal) {
    numberOfAdGroupNegativeActiveKeywords = numberOfAdGroupNegativeActiveKeywords.add(bigDecimal);
  }

  public void addNumberOfActiveGoodQualityScoreKeywords(BigDecimal bigDecimal) {
    numberOfActiveGoodQualityScoreKeywords = numberOfActiveGoodQualityScoreKeywords.add(bigDecimal);
  }

  public void addNumberOfActiveAverageQualityScoreKeywords(BigDecimal bigDecimal) {
    numberOfActiveAverageQualityScoreKeywords = numberOfActiveAverageQualityScoreKeywords.add(bigDecimal);
  }

  public void addNumberOfActivePoorQualityScoreKeywords(BigDecimal bigDecimal) {
    numberOfActivePoorQualityScoreKeywords = numberOfActivePoorQualityScoreKeywords.add(bigDecimal);
  }

  public void addNumberOfCampaignsWithCallExtensionEnabled(BigDecimal bigDecimal) {
    numberOfCampaignsWithCallExtensionEnabled = numberOfCampaignsWithCallExtensionEnabled.add(bigDecimal);
  }

  public void addNumberOfCampaignsWithLocationExtensionEnabled(BigDecimal bigDecimal) {
    numberOfCampaignsWithLocationExtensionEnabled = numberOfCampaignsWithLocationExtensionEnabled.add(bigDecimal);
  }

  public void addNumberOfCampaignsWithSiteLinksEnabled(BigDecimal bigDecimal) {
    numberOfCampaignsWithSiteLinksEnabled = numberOfCampaignsWithSiteLinksEnabled.add(bigDecimal);
  }
  public void addWeightedAverageKeywordPosition(BigDecimal bigDecimal) {
    weightedAverageKeywordPosition = weightedAverageKeywordPosition.add(bigDecimal);
  }

  public void addWeightedAverageQualityScore(BigDecimal bigDecimal) {
    weightedAverageQualityScore = weightedAverageQualityScore.add(bigDecimal);
  }

  public void addSumBudget(BigDecimal bigDecimal) {
    sumBudget = sumBudget.add(bigDecimal);
  }

  public void addNumberOfAdgroupsWithoneActiveAd(BigDecimal bigDecimal) {
    numberOfAdgroupsWithoneActiveAd = numberOfAdgroupsWithoneActiveAd.add(bigDecimal);
  }

  public void addNumberOfAdgroupsWithTwoActiveAds(BigDecimal bigDecimal) {
    numberOfAdgroupsWithTwoActiveAds = numberOfAdgroupsWithTwoActiveAds.add(bigDecimal);
  }

  public void addNumberOfDisapprovedAds(BigDecimal bigDecimal) { 
    numberOfDisapprovedAds = numberOfDisapprovedAds.add(bigDecimal);
  }
}
