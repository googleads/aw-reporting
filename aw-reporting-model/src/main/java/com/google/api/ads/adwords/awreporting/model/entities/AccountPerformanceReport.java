// Copyright 2016 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.awreporting.model.entities;

import com.google.api.ads.adwords.awreporting.model.csv.annotation.CsvField;
import com.google.api.ads.adwords.awreporting.model.csv.annotation.CsvReport;
import com.google.api.ads.adwords.awreporting.model.csv.annotation.MoneyField;
import com.google.api.ads.adwords.awreporting.model.util.BigDecimalUtil;
import com.google.api.ads.adwords.lib.jaxb.v201802.ReportDefinitionReportType;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
   * Specific report class for AccountPerformanceReport.
 *
*/
@Entity
@Table(name = "AW_AccountPerformanceReport")
@CsvReport(value = ReportDefinitionReportType.ACCOUNT_PERFORMANCE_REPORT)
public class AccountPerformanceReport extends DateReport {

  @Column(name = "AccountCurrencyCode")
  @CsvField(value = "Currency", reportField = "AccountCurrencyCode")
  private String accountCurrencyCode;

  @Column(name = "AccountDescriptiveName")
  @CsvField(value = "Account", reportField = "AccountDescriptiveName")
  private String accountDescriptiveName;

  @Column(name = "AccountTimeZone")
  @CsvField(value = "Time zone", reportField = "AccountTimeZone")
  private String accountTimeZone;

  @Column(name = "ActiveViewCpm")
  @CsvField(value = "Active View avg. CPM", reportField = "ActiveViewCpm")
  @MoneyField
  private BigDecimal activeViewCpm;

  @Column(name = "ActiveViewCtr")
  @CsvField(value = "Active View viewable CTR", reportField = "ActiveViewCtr")
  private BigDecimal activeViewCtr;

  @Column(name = "ActiveViewImpressions")
  @CsvField(value = "Active View viewable impressions", reportField = "ActiveViewImpressions")
  private Long activeViewImpressions;

  @Column(name = "ActiveViewMeasurability")
  @CsvField(value = "Active View measurable impr. / impr.", reportField = "ActiveViewMeasurability")
  private BigDecimal activeViewMeasurability;

  @Column(name = "ActiveViewMeasurableCost")
  @CsvField(value = "Active View measurable cost", reportField = "ActiveViewMeasurableCost")
  @MoneyField
  private BigDecimal activeViewMeasurableCost;

  @Column(name = "ActiveViewMeasurableImpressions")
  @CsvField(value = "Active View measurable impr.", reportField = "ActiveViewMeasurableImpressions")
  private Long activeViewMeasurableImpressions;

  @Column(name = "ActiveViewViewability")
  @CsvField(value = "Active View viewable impr. / measurable impr.", reportField = "ActiveViewViewability")
  private BigDecimal activeViewViewability;

  @Column(name = "AdNetworkType1")
  @CsvField(value = "Network", reportField = "AdNetworkType1")
  private String adNetworkType1;

  @Column(name = "AdNetworkType2")
  @CsvField(value = "Network (with search partners)", reportField = "AdNetworkType2")
  private String adNetworkType2;

  @Column(name = "AllConversionRate")
  @CsvField(value = "All conv. rate", reportField = "AllConversionRate")
  private BigDecimal allConversionRate;

  @Column(name = "AllConversions")
  @CsvField(value = "All conv.", reportField = "AllConversions")
  private BigDecimal allConversions;

  @Column(name = "AllConversionValue")
  @CsvField(value = "All conv. value", reportField = "AllConversionValue")
  private BigDecimal allConversionValue;

  @Column(name = "AverageCost")
  @CsvField(value = "Avg. Cost", reportField = "AverageCost")
  @MoneyField
  private BigDecimal averageCost;

  @Column(name = "AverageCpc")
  @CsvField(value = "Avg. CPC", reportField = "AverageCpc")
  @MoneyField
  private BigDecimal averageCpc;

  @Column(name = "AverageCpe")
  @CsvField(value = "Avg. CPE", reportField = "AverageCpe")
  private BigDecimal averageCpe;

  @Column(name = "AverageCpm")
  @CsvField(value = "Avg. CPM", reportField = "AverageCpm")
  @MoneyField
  private BigDecimal averageCpm;

  @Column(name = "AverageCpv")
  @CsvField(value = "Avg. CPV", reportField = "AverageCpv")
  private BigDecimal averageCpv;

  @Column(name = "AveragePosition")
  @CsvField(value = "Avg. position", reportField = "AveragePosition")
  private BigDecimal averagePosition;

  @Column(name = "CanManageClients")
  @CsvField(value = "Can manage clients", reportField = "CanManageClients")
  private String canManageClients;

  @Column(name = "Clicks")
  @CsvField(value = "Clicks", reportField = "Clicks")
  private Long clicks;

  @Column(name = "ClickType")
  @CsvField(value = "Click type", reportField = "ClickType")
  private String clickType;

  @Column(name = "ContentBudgetLostImpressionShare")
  @CsvField(value = "Content Lost IS (budget)", reportField = "ContentBudgetLostImpressionShare")
  private BigDecimal contentBudgetLostImpressionShare;

  @Column(name = "ContentImpressionShare")
  @CsvField(value = "Content Impr. share", reportField = "ContentImpressionShare")
  private BigDecimal contentImpressionShare;

  @Column(name = "ContentRankLostImpressionShare")
  @CsvField(value = "Content Lost IS (rank)", reportField = "ContentRankLostImpressionShare")
  private BigDecimal contentRankLostImpressionShare;

  @Column(name = "ConversionCategoryName")
  @CsvField(value = "Conversion category", reportField = "ConversionCategoryName")
  private String conversionCategoryName;

  @Column(name = "ConversionLagBucket")
  @CsvField(value = "Days to conversion", reportField = "ConversionLagBucket")
  private String conversionLagBucket;

  @Column(name = "ConversionRate")
  @CsvField(value = "Conv. rate", reportField = "ConversionRate")
  private BigDecimal conversionRate;

  @Column(name = "Conversions")
  @CsvField(value = "Conversions", reportField = "Conversions")
  private BigDecimal conversions;

  @Column(name = "ConversionTrackerId")
  @CsvField(value = "Conversion Tracker Id", reportField = "ConversionTrackerId")
  private Long conversionTrackerId;

  @Column(name = "ConversionTypeName")
  @CsvField(value = "Conversion name", reportField = "ConversionTypeName")
  private String conversionTypeName;

  @Column(name = "ConversionValue")
  @CsvField(value = "Total conv. value", reportField = "ConversionValue")
  private BigDecimal conversionValue;

  @Column(name = "Cost")
  @CsvField(value = "Cost", reportField = "Cost")
  @MoneyField
  private BigDecimal cost;

  @Column(name = "CostPerAllConversion")
  @CsvField(value = "Cost / all conv.", reportField = "CostPerAllConversion")
  @MoneyField
  private BigDecimal costPerAllConversion;

  @Column(name = "CostPerConversion")
  @CsvField(value = "Cost / conv.", reportField = "CostPerConversion")
  @MoneyField
  private BigDecimal costPerConversion;

  @Column(name = "CrossDeviceConversions")
  @CsvField(value = "Cross-device conv.", reportField = "CrossDeviceConversions")
  private BigDecimal crossDeviceConversions;

  @Column(name = "Ctr")
  @CsvField(value = "CTR", reportField = "Ctr")
  private BigDecimal ctr;

  @Column(name = "CustomerDescriptiveName")
  @CsvField(value = "Client name", reportField = "CustomerDescriptiveName")
  private String customerDescriptiveName;

  @Column(name = "Device")
  @CsvField(value = "Device", reportField = "Device")
  private String device;

  @Column(name = "EngagementRate")
  @CsvField(value = "Engagement rate", reportField = "EngagementRate")
  private BigDecimal engagementRate;

  @Column(name = "Engagements")
  @CsvField(value = "Engagements", reportField = "Engagements")
  private Long engagements;

  @Column(name = "ExternalConversionSource")
  @CsvField(value = "Conversion source", reportField = "ExternalConversionSource")
  private String externalConversionSource;

  @Column(name = "Impressions")
  @CsvField(value = "Impressions", reportField = "Impressions")
  private Long impressions;

  @Column(name = "InteractionRate")
  @CsvField(value = "Interaction Rate", reportField = "InteractionRate")
  private BigDecimal interactionRate;

  @Column(name = "Interactions")
  @CsvField(value = "Interactions", reportField = "Interactions")
  private Long interactions;

  @Column(name = "InteractionTypes")
  @CsvField(value = "Interaction Types", reportField = "InteractionTypes")
  private String interactionTypes;

  @Column(name = "InvalidClickRate")
  @CsvField(value = "Invalid click rate", reportField = "InvalidClickRate")
  private BigDecimal invalidClickRate;

  @Column(name = "InvalidClicks")
  @CsvField(value = "Invalid clicks", reportField = "InvalidClicks")
  private Long invalidClicks;

  @Column(name = "IsAutoTaggingEnabled")
  @CsvField(value = "Auto tagging enabled", reportField = "IsAutoTaggingEnabled")
  private String isAutoTaggingEnabled;

  @Column(name = "IsTestAccount")
  @CsvField(value = "Test account", reportField = "IsTestAccount")
  private String isTestAccount;

  @Column(name = "SearchBudgetLostImpressionShare")
  @CsvField(value = "Search Lost IS (budget)", reportField = "SearchBudgetLostImpressionShare")
  private BigDecimal searchBudgetLostImpressionShare;

  @Column(name = "SearchExactMatchImpressionShare")
  @CsvField(value = "Search Exact match IS", reportField = "SearchExactMatchImpressionShare")
  private BigDecimal searchExactMatchImpressionShare;

  @Column(name = "SearchImpressionShare")
  @CsvField(value = "Search Impr. share", reportField = "SearchImpressionShare")
  private BigDecimal searchImpressionShare;

  @Column(name = "SearchRankLostImpressionShare")
  @CsvField(value = "Search Lost IS (rank)", reportField = "SearchRankLostImpressionShare")
  private BigDecimal searchRankLostImpressionShare;

  @Column(name = "Slot")
  @CsvField(value = "Top vs. Other", reportField = "Slot")
  private String slot;

  @Column(name = "ValuePerAllConversion")
  @CsvField(value = "Value / all conv.", reportField = "ValuePerAllConversion")
  private BigDecimal valuePerAllConversion;

  @Column(name = "ValuePerConversion")
  @CsvField(value = "Value / conv.", reportField = "ValuePerConversion")
  private BigDecimal valuePerConversion;

  @Column(name = "VideoViewRate")
  @CsvField(value = "View rate", reportField = "VideoViewRate")
  private BigDecimal videoViewRate;

  @Column(name = "VideoViews")
  @CsvField(value = "Views", reportField = "VideoViews")
  private Long videoViews;

  @Column(name = "ViewThroughConversions")
  @CsvField(value = "View-through conv.", reportField = "ViewThroughConversions")
  private Long viewThroughConversions;

  /**
   * Hibernate needs an empty constructor
   */
  public AccountPerformanceReport() {
  }

  public AccountPerformanceReport(Long topAccountId, Long accountId){
    super(topAccountId, accountId);
  }

  public String getAccountCurrencyCode() {
    return accountCurrencyCode;
  }

  public void setAccountCurrencyCode(String accountCurrencyCode) {
    this.accountCurrencyCode = accountCurrencyCode;
  }

  public String getAccountDescriptiveName() {
    return accountDescriptiveName;
  }

  public void setAccountDescriptiveName(String accountDescriptiveName) {
    this.accountDescriptiveName = accountDescriptiveName;
  }

  public String getAccountTimeZone() {
    return accountTimeZone;
  }

  public void setAccountTimeZone(String accountTimeZone) {
    this.accountTimeZone = accountTimeZone;
  }

  public BigDecimal getActiveViewCpm() {
    return activeViewCpm;
  }

  public void setActiveViewCpm(BigDecimal activeViewCpm) {
    this.activeViewCpm = activeViewCpm;
  }

  public String getActiveViewCtr() {
    return BigDecimalUtil.formatAsReadable(activeViewCtr);
  }

  public BigDecimal getActiveViewCtrBigDecimal() {
    return activeViewCtr;
  }

  public void setActiveViewCtr(String activeViewCtr) {
    this.activeViewCtr = BigDecimalUtil.parseFromNumberString(activeViewCtr);
  }

  public Long getActiveViewImpressions() {
    return activeViewImpressions;
  }

  public void setActiveViewImpressions(Long activeViewImpressions) {
    this.activeViewImpressions = activeViewImpressions;
  }

  public String getActiveViewMeasurability() {
    return BigDecimalUtil.formatAsReadable(activeViewMeasurability);
  }

  public BigDecimal getActiveViewMeasurabilityBigDecimal() {
    return activeViewMeasurability;
  }

  public void setActiveViewMeasurability(String activeViewMeasurability) {
    this.activeViewMeasurability = BigDecimalUtil.parseFromNumberString(activeViewMeasurability);
  }

  public BigDecimal getActiveViewMeasurableCost() {
    return activeViewMeasurableCost;
  }

  public void setActiveViewMeasurableCost(BigDecimal activeViewMeasurableCost) {
    this.activeViewMeasurableCost = activeViewMeasurableCost;
  }

  public Long getActiveViewMeasurableImpressions() {
    return activeViewMeasurableImpressions;
  }

  public void setActiveViewMeasurableImpressions(Long activeViewMeasurableImpressions) {
    this.activeViewMeasurableImpressions = activeViewMeasurableImpressions;
  }

  public String getActiveViewViewability() {
    return BigDecimalUtil.formatAsReadable(activeViewViewability);
  }

  public BigDecimal getActiveViewViewabilityBigDecimal() {
    return activeViewViewability;
  }

  public void setActiveViewViewability(String activeViewViewability) {
    this.activeViewViewability = BigDecimalUtil.parseFromNumberString(activeViewViewability);
  }

  public String getAdNetworkType1() {
    return adNetworkType1;
  }

  public void setAdNetworkType1(String adNetworkType1) {
    this.adNetworkType1 = adNetworkType1;
  }

  public String getAdNetworkType2() {
    return adNetworkType2;
  }

  public void setAdNetworkType2(String adNetworkType2) {
    this.adNetworkType2 = adNetworkType2;
  }

  public String getAllConversionRate() {
    return BigDecimalUtil.formatAsReadable(allConversionRate);
  }

  public BigDecimal getAllConversionRateBigDecimal() {
    return allConversionRate;
  }

  public void setAllConversionRate(String allConversionRate) {
    this.allConversionRate = BigDecimalUtil.parseFromNumberString(allConversionRate);
  }

  public String getAllConversions() {
    return BigDecimalUtil.formatAsReadable(allConversions);
  }

  public BigDecimal getAllConversionsBigDecimal() {
    return allConversions;
  }

  public void setAllConversions(String allConversions) {
    this.allConversions = BigDecimalUtil.parseFromNumberString(allConversions);
  }

  public String getAllConversionValue() {
    return BigDecimalUtil.formatAsReadable(allConversionValue);
  }

  public BigDecimal getAllConversionValueBigDecimal() {
    return allConversionValue;
  }

  public void setAllConversionValue(String allConversionValue) {
    this.allConversionValue = BigDecimalUtil.parseFromNumberString(allConversionValue);
  }

  public BigDecimal getAverageCost() {
    return averageCost;
  }

  public void setAverageCost(BigDecimal averageCost) {
    this.averageCost = averageCost;
  }

  public BigDecimal getAverageCpc() {
    return averageCpc;
  }

  public void setAverageCpc(BigDecimal averageCpc) {
    this.averageCpc = averageCpc;
  }

  public String getAverageCpe() {
    return BigDecimalUtil.formatAsReadable(averageCpe);
  }

  public BigDecimal getAverageCpeBigDecimal() {
    return averageCpe;
  }

  public void setAverageCpe(String averageCpe) {
    this.averageCpe = BigDecimalUtil.parseFromNumberString(averageCpe);
  }

  public BigDecimal getAverageCpm() {
    return averageCpm;
  }

  public void setAverageCpm(BigDecimal averageCpm) {
    this.averageCpm = averageCpm;
  }

  public String getAverageCpv() {
    return BigDecimalUtil.formatAsReadable(averageCpv);
  }

  public BigDecimal getAverageCpvBigDecimal() {
    return averageCpv;
  }

  public void setAverageCpv(String averageCpv) {
    this.averageCpv = BigDecimalUtil.parseFromNumberString(averageCpv);
  }

  public String getAveragePosition() {
    return BigDecimalUtil.formatAsReadable(averagePosition);
  }

  public BigDecimal getAveragePositionBigDecimal() {
    return averagePosition;
  }

  public void setAveragePosition(String averagePosition) {
    this.averagePosition = BigDecimalUtil.parseFromNumberString(averagePosition);
  }

  public String getCanManageClients() {
    return canManageClients;
  }

  public void setCanManageClients(String canManageClients) {
    this.canManageClients = canManageClients;
  }

  public Long getClicks() {
    return clicks;
  }

  public void setClicks(Long clicks) {
    this.clicks = clicks;
  }

  public String getClickType() {
    return clickType;
  }

  public void setClickType(String clickType) {
    this.clickType = clickType;
  }

  public String getContentBudgetLostImpressionShare() {
    return BigDecimalUtil.formatAsReadable(contentBudgetLostImpressionShare);
  }

  public BigDecimal getContentBudgetLostImpressionShareBigDecimal() {
    return contentBudgetLostImpressionShare;
  }

  public void setContentBudgetLostImpressionShare(String contentBudgetLostImpressionShare) {
    this.contentBudgetLostImpressionShare = BigDecimalUtil.parseFromNumberStringPercentage(contentBudgetLostImpressionShare);
  }

  public String getContentImpressionShare() {
    return BigDecimalUtil.formatAsReadable(contentImpressionShare);
  }

  public BigDecimal getContentImpressionShareBigDecimal() {
    return contentImpressionShare;
  }

  public void setContentImpressionShare(String contentImpressionShare) {
    this.contentImpressionShare = BigDecimalUtil.parseFromNumberStringPercentage(contentImpressionShare);
  }

  public String getContentRankLostImpressionShare() {
    return BigDecimalUtil.formatAsReadable(contentRankLostImpressionShare);
  }

  public BigDecimal getContentRankLostImpressionShareBigDecimal() {
    return contentRankLostImpressionShare;
  }

  public void setContentRankLostImpressionShare(String contentRankLostImpressionShare) {
    this.contentRankLostImpressionShare = BigDecimalUtil.parseFromNumberStringPercentage(contentRankLostImpressionShare);
  }

  public String getConversionCategoryName() {
    return conversionCategoryName;
  }

  public void setConversionCategoryName(String conversionCategoryName) {
    this.conversionCategoryName = conversionCategoryName;
  }

  public String getConversionLagBucket() {
    return conversionLagBucket;
  }

  public void setConversionLagBucket(String conversionLagBucket) {
    this.conversionLagBucket = conversionLagBucket;
  }

  public String getConversionRate() {
    return BigDecimalUtil.formatAsReadable(conversionRate);
  }

  public BigDecimal getConversionRateBigDecimal() {
    return conversionRate;
  }

  public void setConversionRate(String conversionRate) {
    this.conversionRate = BigDecimalUtil.parseFromNumberString(conversionRate);
  }

  public String getConversions() {
    return BigDecimalUtil.formatAsReadable(conversions);
  }

  public BigDecimal getConversionsBigDecimal() {
    return conversions;
  }

  public void setConversions(String conversions) {
    this.conversions = BigDecimalUtil.parseFromNumberString(conversions);
  }

  public Long getConversionTrackerId() {
    return conversionTrackerId;
  }

  public void setConversionTrackerId(Long conversionTrackerId) {
    this.conversionTrackerId = conversionTrackerId;
  }

  public String getConversionTypeName() {
    return conversionTypeName;
  }

  public void setConversionTypeName(String conversionTypeName) {
    this.conversionTypeName = conversionTypeName;
  }

  public String getConversionValue() {
    return BigDecimalUtil.formatAsReadable(conversionValue);
  }

  public BigDecimal getConversionValueBigDecimal() {
    return conversionValue;
  }

  public void setConversionValue(String conversionValue) {
    this.conversionValue = BigDecimalUtil.parseFromNumberString(conversionValue);
  }

  public BigDecimal getCost() {
    return cost;
  }

  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }

  public BigDecimal getCostPerAllConversion() {
    return costPerAllConversion;
  }

  public void setCostPerAllConversion(BigDecimal costPerAllConversion) {
    this.costPerAllConversion = costPerAllConversion;
  }

  public BigDecimal getCostPerConversion() {
    return costPerConversion;
  }

  public void setCostPerConversion(BigDecimal costPerConversion) {
    this.costPerConversion = costPerConversion;
  }

  public String getCrossDeviceConversions() {
    return BigDecimalUtil.formatAsReadable(crossDeviceConversions);
  }

  public BigDecimal getCrossDeviceConversionsBigDecimal() {
    return crossDeviceConversions;
  }

  public void setCrossDeviceConversions(String crossDeviceConversions) {
    this.crossDeviceConversions = BigDecimalUtil.parseFromNumberString(crossDeviceConversions);
  }

  public String getCtr() {
    return BigDecimalUtil.formatAsReadable(ctr);
  }

  public BigDecimal getCtrBigDecimal() {
    return ctr;
  }

  public void setCtr(String ctr) {
    this.ctr = (ctr == null ? null : BigDecimalUtil.parseFromNumberString(ctr.replace("%","")));
  }

  public String getCustomerDescriptiveName() {
    return customerDescriptiveName;
  }

  public void setCustomerDescriptiveName(String customerDescriptiveName) {
    this.customerDescriptiveName = customerDescriptiveName;
  }

  public String getDevice() {
    return device;
  }

  public void setDevice(String device) {
    this.device = device;
  }

  public String getEngagementRate() {
    return BigDecimalUtil.formatAsReadable(engagementRate);
  }

  public BigDecimal getEngagementRateBigDecimal() {
    return engagementRate;
  }

  public void setEngagementRate(String engagementRate) {
    this.engagementRate = BigDecimalUtil.parseFromNumberString(engagementRate);
  }

  public Long getEngagements() {
    return engagements;
  }

  public void setEngagements(Long engagements) {
    this.engagements = engagements;
  }

  public String getExternalConversionSource() {
    return externalConversionSource;
  }

  public void setExternalConversionSource(String externalConversionSource) {
    this.externalConversionSource = externalConversionSource;
  }

  public Long getImpressions() {
    return impressions;
  }

  public void setImpressions(Long impressions) {
    this.impressions = impressions;
  }

  public String getInteractionRate() {
    return BigDecimalUtil.formatAsReadable(interactionRate);
  }

  public BigDecimal getInteractionRateBigDecimal() {
    return interactionRate;
  }

  public void setInteractionRate(String interactionRate) {
    this.interactionRate = BigDecimalUtil.parseFromNumberString(interactionRate);
  }

  public Long getInteractions() {
    return interactions;
  }

  public void setInteractions(Long interactions) {
    this.interactions = interactions;
  }

  public String getInteractionTypes() {
    return interactionTypes;
  }

  public void setInteractionTypes(String interactionTypes) {
    this.interactionTypes = interactionTypes;
  }

  public String getInvalidClickRate() {
    return BigDecimalUtil.formatAsReadable(invalidClickRate);
  }

  public BigDecimal getInvalidClickRateBigDecimal() {
    return invalidClickRate;
  }

  public void setInvalidClickRate(String invalidClickRate) {
    this.invalidClickRate = BigDecimalUtil.parseFromNumberString(invalidClickRate);
  }

  public Long getInvalidClicks() {
    return invalidClicks;
  }

  public void setInvalidClicks(Long invalidClicks) {
    this.invalidClicks = invalidClicks;
  }

  public String getIsAutoTaggingEnabled() {
    return isAutoTaggingEnabled;
  }

  public void setIsAutoTaggingEnabled(String isAutoTaggingEnabled) {
    this.isAutoTaggingEnabled = isAutoTaggingEnabled;
  }

  public String getIsTestAccount() {
    return isTestAccount;
  }

  public void setIsTestAccount(String isTestAccount) {
    this.isTestAccount = isTestAccount;
  }

  public String getSearchBudgetLostImpressionShare() {
    return BigDecimalUtil.formatAsReadable(searchBudgetLostImpressionShare);
  }

  public BigDecimal getSearchBudgetLostImpressionShareBigDecimal() {
    return searchBudgetLostImpressionShare;
  }

  public void setSearchBudgetLostImpressionShare(String searchBudgetLostImpressionShare) {
    this.searchBudgetLostImpressionShare = BigDecimalUtil.parseFromNumberStringPercentage(searchBudgetLostImpressionShare);
  }

  public String getSearchExactMatchImpressionShare() {
    return BigDecimalUtil.formatAsReadable(searchExactMatchImpressionShare);
  }

  public BigDecimal getSearchExactMatchImpressionShareBigDecimal() {
    return searchExactMatchImpressionShare;
  }

  public void setSearchExactMatchImpressionShare(String searchExactMatchImpressionShare) {
    this.searchExactMatchImpressionShare = BigDecimalUtil.parseFromNumberStringPercentage(searchExactMatchImpressionShare);
  }

  public String getSearchImpressionShare() {
    return BigDecimalUtil.formatAsReadable(searchImpressionShare);
  }

  public BigDecimal getSearchImpressionShareBigDecimal() {
    return searchImpressionShare;
  }

  public void setSearchImpressionShare(String searchImpressionShare) {
    this.searchImpressionShare = BigDecimalUtil.parseFromNumberStringPercentage(searchImpressionShare);
  }

  public String getSearchRankLostImpressionShare() {
    return BigDecimalUtil.formatAsReadable(searchRankLostImpressionShare);
  }

  public BigDecimal getSearchRankLostImpressionShareBigDecimal() {
    return searchRankLostImpressionShare;
  }

  public void setSearchRankLostImpressionShare(String searchRankLostImpressionShare) {
    this.searchRankLostImpressionShare = BigDecimalUtil.parseFromNumberStringPercentage(searchRankLostImpressionShare);
  }

  public String getSlot() {
    return slot;
  }

  public void setSlot(String slot) {
    this.slot = slot;
  }

  public String getValuePerAllConversion() {
    return BigDecimalUtil.formatAsReadable(valuePerAllConversion);
  }

  public BigDecimal getValuePerAllConversionBigDecimal() {
    return valuePerAllConversion;
  }

  public void setValuePerAllConversion(String valuePerAllConversion) {
    this.valuePerAllConversion = BigDecimalUtil.parseFromNumberString(valuePerAllConversion);
  }

  public String getValuePerConversion() {
    return BigDecimalUtil.formatAsReadable(valuePerConversion);
  }

  public BigDecimal getValuePerConversionBigDecimal() {
    return valuePerConversion;
  }

  public void setValuePerConversion(String valuePerConversion) {
    this.valuePerConversion = BigDecimalUtil.parseFromNumberString(valuePerConversion);
  }

  public String getVideoViewRate() {
    return BigDecimalUtil.formatAsReadable(videoViewRate);
  }

  public BigDecimal getVideoViewRateBigDecimal() {
    return videoViewRate;
  }

  public void setVideoViewRate(String videoViewRate) {
    this.videoViewRate = BigDecimalUtil.parseFromNumberString(videoViewRate);
  }

  public Long getVideoViews() {
    return videoViews;
  }

  public void setVideoViews(Long videoViews) {
    this.videoViews = videoViews;
  }

  public Long getViewThroughConversions() {
    return viewThroughConversions;
  }

  public void setViewThroughConversions(Long viewThroughConversions) {
    this.viewThroughConversions = viewThroughConversions;
  }

  @Override
  public void setRowId() {
    // General fields for generating unique id.
    StringBuilder idBuilder = new StringBuilder(getCustomerId().toString());
    idBuilder.append("-").append(getDateLabel());

    // Include all segmentation fields (if set).
    if (!StringUtils.isEmpty(adNetworkType1)) {
      idBuilder.append("-").append(adNetworkType1);
    }
    if (!StringUtils.isEmpty(adNetworkType2)) {
      idBuilder.append("-").append(adNetworkType2);
    }
    if (!StringUtils.isEmpty(clickType)) {
      idBuilder.append("-").append(clickType);
    }
    if (!StringUtils.isEmpty(conversionCategoryName)) {
      idBuilder.append("-").append(conversionCategoryName);
    }
    if (!StringUtils.isEmpty(conversionLagBucket)) {
      idBuilder.append("-").append(conversionLagBucket);
    }
    if (conversionTrackerId != null) {
      idBuilder.append("-").append(conversionTrackerId);
    }
    if (!StringUtils.isEmpty(conversionTypeName)) {
      idBuilder.append("-").append(conversionTypeName);
    }
    if (!StringUtils.isEmpty(device)) {
      idBuilder.append("-").append(device);
    }
    if (!StringUtils.isEmpty(externalConversionSource)) {
      idBuilder.append("-").append(externalConversionSource);
    }
    if (!StringUtils.isEmpty(slot)) {
      idBuilder.append("-").append(slot);
    }
    this.rowId = idBuilder.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) { return false; }
    if (obj == this) { return true; }
    if (obj.getClass() != getClass()) { return false; }
    AccountPerformanceReport other = (AccountPerformanceReport) obj;
    return new EqualsBuilder()
      .appendSuper(super.equals(obj))
      .append(accountCurrencyCode, other.accountCurrencyCode)
      .append(accountDescriptiveName, other.accountDescriptiveName)
      .append(accountTimeZone, other.accountTimeZone)
      .append(activeViewCpm, other.activeViewCpm)
      .append(activeViewCtr, other.activeViewCtr)
      .append(activeViewImpressions, other.activeViewImpressions)
      .append(activeViewMeasurability, other.activeViewMeasurability)
      .append(activeViewMeasurableCost, other.activeViewMeasurableCost)
      .append(activeViewMeasurableImpressions, other.activeViewMeasurableImpressions)
      .append(activeViewViewability, other.activeViewViewability)
      .append(adNetworkType1, other.adNetworkType1)
      .append(adNetworkType2, other.adNetworkType2)
      .append(allConversionRate, other.allConversionRate)
      .append(allConversions, other.allConversions)
      .append(allConversionValue, other.allConversionValue)
      .append(averageCost, other.averageCost)
      .append(averageCpc, other.averageCpc)
      .append(averageCpe, other.averageCpe)
      .append(averageCpm, other.averageCpm)
      .append(averageCpv, other.averageCpv)
      .append(averagePosition, other.averagePosition)
      .append(canManageClients, other.canManageClients)
      .append(clicks, other.clicks)
      .append(clickType, other.clickType)
      .append(contentBudgetLostImpressionShare, other.contentBudgetLostImpressionShare)
      .append(contentImpressionShare, other.contentImpressionShare)
      .append(contentRankLostImpressionShare, other.contentRankLostImpressionShare)
      .append(conversionCategoryName, other.conversionCategoryName)
      .append(conversionLagBucket, other.conversionLagBucket)
      .append(conversionRate, other.conversionRate)
      .append(conversions, other.conversions)
      .append(conversionTrackerId, other.conversionTrackerId)
      .append(conversionTypeName, other.conversionTypeName)
      .append(conversionValue, other.conversionValue)
      .append(cost, other.cost)
      .append(costPerAllConversion, other.costPerAllConversion)
      .append(costPerConversion, other.costPerConversion)
      .append(crossDeviceConversions, other.crossDeviceConversions)
      .append(ctr, other.ctr)
      .append(customerDescriptiveName, other.customerDescriptiveName)
      .append(device, other.device)
      .append(engagementRate, other.engagementRate)
      .append(engagements, other.engagements)
      .append(externalConversionSource, other.externalConversionSource)
      .append(impressions, other.impressions)
      .append(interactionRate, other.interactionRate)
      .append(interactions, other.interactions)
      .append(interactionTypes, other.interactionTypes)
      .append(invalidClickRate, other.invalidClickRate)
      .append(invalidClicks, other.invalidClicks)
      .append(isAutoTaggingEnabled, other.isAutoTaggingEnabled)
      .append(isTestAccount, other.isTestAccount)
      .append(searchBudgetLostImpressionShare, other.searchBudgetLostImpressionShare)
      .append(searchExactMatchImpressionShare, other.searchExactMatchImpressionShare)
      .append(searchImpressionShare, other.searchImpressionShare)
      .append(searchRankLostImpressionShare, other.searchRankLostImpressionShare)
      .append(slot, other.slot)
      .append(valuePerAllConversion, other.valuePerAllConversion)
      .append(valuePerConversion, other.valuePerConversion)
      .append(videoViewRate, other.videoViewRate)
      .append(videoViews, other.videoViews)
      .append(viewThroughConversions, other.viewThroughConversions)
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .appendSuper(super.hashCode())
      .append(accountCurrencyCode)
      .append(accountDescriptiveName)
      .append(accountTimeZone)
      .append(activeViewCpm)
      .append(activeViewCtr)
      .append(activeViewImpressions)
      .append(activeViewMeasurability)
      .append(activeViewMeasurableCost)
      .append(activeViewMeasurableImpressions)
      .append(activeViewViewability)
      .append(adNetworkType1)
      .append(adNetworkType2)
      .append(allConversionRate)
      .append(allConversions)
      .append(allConversionValue)
      .append(averageCost)
      .append(averageCpc)
      .append(averageCpe)
      .append(averageCpm)
      .append(averageCpv)
      .append(averagePosition)
      .append(canManageClients)
      .append(clicks)
      .append(clickType)
      .append(contentBudgetLostImpressionShare)
      .append(contentImpressionShare)
      .append(contentRankLostImpressionShare)
      .append(conversionCategoryName)
      .append(conversionLagBucket)
      .append(conversionRate)
      .append(conversions)
      .append(conversionTrackerId)
      .append(conversionTypeName)
      .append(conversionValue)
      .append(cost)
      .append(costPerAllConversion)
      .append(costPerConversion)
      .append(crossDeviceConversions)
      .append(ctr)
      .append(customerDescriptiveName)
      .append(device)
      .append(engagementRate)
      .append(engagements)
      .append(externalConversionSource)
      .append(impressions)
      .append(interactionRate)
      .append(interactions)
      .append(interactionTypes)
      .append(invalidClickRate)
      .append(invalidClicks)
      .append(isAutoTaggingEnabled)
      .append(isTestAccount)
      .append(searchBudgetLostImpressionShare)
      .append(searchExactMatchImpressionShare)
      .append(searchImpressionShare)
      .append(searchRankLostImpressionShare)
      .append(slot)
      .append(valuePerAllConversion)
      .append(valuePerConversion)
      .append(videoViewRate)
      .append(videoViews)
      .append(viewThroughConversions)
      .toHashCode();
  }

}
