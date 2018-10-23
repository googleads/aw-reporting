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
import com.google.api.ads.adwords.lib.jaxb.v201809.ReportDefinitionReportType;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
   * Specific report class for CampaignPerformanceReport.
 *
*/
@Entity
@Table(name = "AW_CampaignPerformanceReport")
@CsvReport(value = ReportDefinitionReportType.CAMPAIGN_PERFORMANCE_REPORT)
public class CampaignPerformanceReport extends DateReport {

  @Column(name = "Period")
  @CsvField(value = "Budget period", reportField = "Period")
  private String period;

  @Column(name = "UrlCustomParameters", length = 2048)
  @CsvField(value = "Custom parameter", reportField = "UrlCustomParameters")
  private String urlCustomParameters;

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

  @Column(name = "AdvertisingChannelSubType")
  @CsvField(value = "Advertising Sub Channel", reportField = "AdvertisingChannelSubType")
  private String advertisingChannelSubType;

  @Column(name = "AdvertisingChannelType")
  @CsvField(value = "Advertising Channel", reportField = "AdvertisingChannelType")
  private String advertisingChannelType;

  @Column(name = "AllConversionRate")
  @CsvField(value = "All conv. rate", reportField = "AllConversionRate")
  private BigDecimal allConversionRate;

  @Column(name = "AllConversions")
  @CsvField(value = "All conv.", reportField = "AllConversions")
  private BigDecimal allConversions;

  @Column(name = "AllConversionValue")
  @CsvField(value = "All conv. value", reportField = "AllConversionValue")
  private BigDecimal allConversionValue;

  @Column(name = "Amount")
  @CsvField(value = "Budget", reportField = "Amount")
  @MoneyField
  private BigDecimal amount;

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

  @Column(name = "AverageFrequency")
  @CsvField(value = "Avg. impr. freq. per cookie", reportField = "AverageFrequency")
  private BigDecimal averageFrequency;

  @Column(name = "AveragePageviews")
  @CsvField(value = "Pages / session", reportField = "AveragePageviews")
  private BigDecimal averagePageviews;

  @Column(name = "AveragePosition")
  @CsvField(value = "Avg. position", reportField = "AveragePosition")
  private BigDecimal averagePosition;

  @Column(name = "AverageTimeOnSite")
  @CsvField(value = "Avg. session duration (seconds)", reportField = "AverageTimeOnSite")
  private BigDecimal averageTimeOnSite;

  @Column(name = "BaseCampaignId")
  @CsvField(value = "Base Campaign ID", reportField = "BaseCampaignId")
  private Long baseCampaignId;

  @Column(name = "BiddingStrategyId")
  @CsvField(value = "Bid Strategy ID", reportField = "BiddingStrategyId")
  private Long biddingStrategyId;

  @Column(name = "BiddingStrategyName")
  @CsvField(value = "Bid Strategy Name", reportField = "BiddingStrategyName")
  private String biddingStrategyName;

  @Column(name = "BiddingStrategyType")
  @CsvField(value = "Bid Strategy Type", reportField = "BiddingStrategyType")
  private String biddingStrategyType;

  @Column(name = "BounceRate")
  @CsvField(value = "Bounce rate", reportField = "BounceRate")
  private BigDecimal bounceRate;

  @Column(name = "BudgetId")
  @CsvField(value = "Budget ID", reportField = "BudgetId")
  private Long budgetId;

  @Column(name = "CampaignDesktopBidModifier")
  @CsvField(value = "Desktop bid adj.", reportField = "CampaignDesktopBidModifier")
  private BigDecimal campaignDesktopBidModifier;

  @Column(name = "CampaignGroupId")
  @CsvField(value = "Campaign Group ID", reportField = "CampaignGroupId")
  private Long campaignGroupId;

  @Column(name = "CampaignId")
  @CsvField(value = "Campaign ID", reportField = "CampaignId")
  private Long campaignId;

  @Column(name = "CampaignMobileBidModifier")
  @CsvField(value = "Mobile bid adj.", reportField = "CampaignMobileBidModifier")
  private BigDecimal campaignMobileBidModifier;

  @Column(name = "CampaignName")
  @CsvField(value = "Campaign", reportField = "CampaignName")
  private String campaignName;

  @Column(name = "CampaignStatus")
  @CsvField(value = "Campaign state", reportField = "CampaignStatus")
  private String campaignStatus;

  @Column(name = "CampaignTabletBidModifier")
  @CsvField(value = "Tablet bid adj.", reportField = "CampaignTabletBidModifier")
  private BigDecimal campaignTabletBidModifier;

  @Column(name = "CampaignTrialType")
  @CsvField(value = "Campaign Trial Type", reportField = "CampaignTrialType")
  private String campaignTrialType;

  @Column(name = "ClickAssistedConversions")
  @CsvField(value = "Click Assisted Conv.", reportField = "ClickAssistedConversions")
  private Long clickAssistedConversions;

  @Column(name = "ClickAssistedConversionsOverLastClickConversions")
  @CsvField(value = "Click Assisted Conv. / Last Click Conv.", reportField = "ClickAssistedConversionsOverLastClickConversions")
  private BigDecimal clickAssistedConversionsOverLastClickConversions;

  @Column(name = "ClickAssistedConversionValue")
  @CsvField(value = "Click Assisted Conv. Value", reportField = "ClickAssistedConversionValue")
  private BigDecimal clickAssistedConversionValue;

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

  @Column(name = "ConversionAdjustment")
  @CsvField(value = "Conversion adjustment", reportField = "ConversionAdjustment")
  private String conversionAdjustment;

  @Column(name = "ConversionAdjustmentLagBucket")
  @CsvField(value = "Days to conversion or adjustment", reportField = "ConversionAdjustmentLagBucket")
  private String conversionAdjustmentLagBucket;

  @Column(name = "ConversionAttributionEventType")
  @CsvField(value = "Ad event type", reportField = "ConversionAttributionEventType")
  private String conversionAttributionEventType;

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

  @Column(name = "CostPerCurrentModelAttributedConversion")
  @CsvField(value = "Cost / conv. (current model)", reportField = "CostPerCurrentModelAttributedConversion")
  private BigDecimal costPerCurrentModelAttributedConversion;

  @Column(name = "CrossDeviceConversions")
  @CsvField(value = "Cross-device conv.", reportField = "CrossDeviceConversions")
  private BigDecimal crossDeviceConversions;

  @Column(name = "Ctr")
  @CsvField(value = "CTR", reportField = "Ctr")
  private BigDecimal ctr;

  @Column(name = "CurrentModelAttributedConversions")
  @CsvField(value = "Conversions (current model)", reportField = "CurrentModelAttributedConversions")
  private BigDecimal currentModelAttributedConversions;

  @Column(name = "CurrentModelAttributedConversionValue")
  @CsvField(value = "Conv. value (current model)", reportField = "CurrentModelAttributedConversionValue")
  private BigDecimal currentModelAttributedConversionValue;

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

  @Column(name = "EnhancedCpcEnabled")
  @CsvField(value = "Enhanced CPC enabled", reportField = "EnhancedCpcEnabled")
  private String enhancedCpcEnabled;

  @Column(name = "ExternalConversionSource")
  @CsvField(value = "Conversion source", reportField = "ExternalConversionSource")
  private String externalConversionSource;

  @Column(name = "FinalUrlSuffix")
  @CsvField(value = "Final URL suffix", reportField = "FinalUrlSuffix")
  private String finalUrlSuffix;

  @Column(name = "GmailForwards")
  @CsvField(value = "Gmail forwards", reportField = "GmailForwards")
  private Long gmailForwards;

  @Column(name = "GmailSaves")
  @CsvField(value = "Gmail saves", reportField = "GmailSaves")
  private Long gmailSaves;

  @Column(name = "GmailSecondaryClicks")
  @CsvField(value = "Gmail clicks to website", reportField = "GmailSecondaryClicks")
  private Long gmailSecondaryClicks;

  @Column(name = "HasRecommendedBudget")
  @CsvField(value = "Has recommended Budget", reportField = "HasRecommendedBudget")
  private String hasRecommendedBudget;

  @Column(name = "ImpressionAssistedConversions")
  @CsvField(value = "Impr. Assisted Conv.", reportField = "ImpressionAssistedConversions")
  private Long impressionAssistedConversions;

  @Column(name = "ImpressionAssistedConversionsOverLastClickConversions")
  @CsvField(value = "Impr. Assisted Conv. / Last Click Conv.", reportField = "ImpressionAssistedConversionsOverLastClickConversions")
  private BigDecimal impressionAssistedConversionsOverLastClickConversions;

  @Column(name = "ImpressionAssistedConversionValue")
  @CsvField(value = "Impr. Assisted Conv. Value", reportField = "ImpressionAssistedConversionValue")
  private BigDecimal impressionAssistedConversionValue;

  @Column(name = "ImpressionReach")
  @CsvField(value = "Unique cookies", reportField = "ImpressionReach")
  private Long impressionReach;

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

  @Column(name = "IsBudgetExplicitlyShared")
  @CsvField(value = "Budget explicitly shared", reportField = "IsBudgetExplicitlyShared")
  private String isBudgetExplicitlyShared;

  @Column(name = "LabelIds")
  @CsvField(value = "Label IDs", reportField = "LabelIds")
  private String labelIds;

  @Column(name = "Labels")
  @CsvField(value = "Labels", reportField = "Labels")
  private String labels;

  @Column(name = "MaximizeConversionValueTargetRoas")
  @CsvField(value = "Target ROAS (Maximize Conversion Value)", reportField = "MaximizeConversionValueTargetRoas")
  private BigDecimal maximizeConversionValueTargetRoas;

  @Column(name = "NumOfflineImpressions")
  @CsvField(value = "Phone impressions", reportField = "NumOfflineImpressions")
  private Long numOfflineImpressions;

  @Column(name = "NumOfflineInteractions")
  @CsvField(value = "Phone calls", reportField = "NumOfflineInteractions")
  private Long numOfflineInteractions;

  @Column(name = "OfflineInteractionRate")
  @CsvField(value = "PTR", reportField = "OfflineInteractionRate")
  private BigDecimal offlineInteractionRate;

  @Column(name = "PercentNewVisitors")
  @CsvField(value = "% new sessions", reportField = "PercentNewVisitors")
  private BigDecimal percentNewVisitors;

  @Column(name = "RecommendedBudgetAmount")
  @CsvField(value = "Recommended Budget amount", reportField = "RecommendedBudgetAmount")
  @MoneyField
  private BigDecimal recommendedBudgetAmount;

  @Column(name = "RelativeCtr")
  @CsvField(value = "Relative CTR", reportField = "RelativeCtr")
  private BigDecimal relativeCtr;

  @Column(name = "SearchAbsoluteTopImpressionShare")
  @CsvField(value = "Search abs. top IS", reportField = "SearchAbsoluteTopImpressionShare")
  private BigDecimal searchAbsoluteTopImpressionShare;

  @Column(name = "SearchBudgetLostImpressionShare")
  @CsvField(value = "Search Lost IS (budget)", reportField = "SearchBudgetLostImpressionShare")
  private BigDecimal searchBudgetLostImpressionShare;

  @Column(name = "SearchClickShare")
  @CsvField(value = "Click share", reportField = "SearchClickShare")
  private BigDecimal searchClickShare;

  @Column(name = "SearchExactMatchImpressionShare")
  @CsvField(value = "Search Exact match IS", reportField = "SearchExactMatchImpressionShare")
  private BigDecimal searchExactMatchImpressionShare;

  @Column(name = "SearchImpressionShare")
  @CsvField(value = "Search Impr. share", reportField = "SearchImpressionShare")
  private BigDecimal searchImpressionShare;

  @Column(name = "SearchRankLostImpressionShare")
  @CsvField(value = "Search Lost IS (rank)", reportField = "SearchRankLostImpressionShare")
  private BigDecimal searchRankLostImpressionShare;

  @Column(name = "ServingStatus")
  @CsvField(value = "Campaign serving status", reportField = "ServingStatus")
  private String servingStatus;

  @Column(name = "Slot")
  @CsvField(value = "Top vs. Other", reportField = "Slot")
  private String slot;

  @Column(name = "TotalAmount")
  @CsvField(value = "Total Budget amount", reportField = "TotalAmount")
  @MoneyField
  private BigDecimal totalAmount;

  @Column(name = "TrackingUrlTemplate", length = 2048)
  @CsvField(value = "Tracking template", reportField = "TrackingUrlTemplate")
  private String trackingUrlTemplate;

  @Column(name = "ValuePerAllConversion")
  @CsvField(value = "Value / all conv.", reportField = "ValuePerAllConversion")
  private BigDecimal valuePerAllConversion;

  @Column(name = "ValuePerConversion")
  @CsvField(value = "Value / conv.", reportField = "ValuePerConversion")
  private BigDecimal valuePerConversion;

  @Column(name = "ValuePerCurrentModelAttributedConversion")
  @CsvField(value = "Value / conv. (current model)", reportField = "ValuePerCurrentModelAttributedConversion")
  private BigDecimal valuePerCurrentModelAttributedConversion;

  @Column(name = "VideoQuartile100Rate")
  @CsvField(value = "Video played to 100%", reportField = "VideoQuartile100Rate")
  private BigDecimal videoQuartile100Rate;

  @Column(name = "VideoQuartile25Rate")
  @CsvField(value = "Video played to 25%", reportField = "VideoQuartile25Rate")
  private BigDecimal videoQuartile25Rate;

  @Column(name = "VideoQuartile50Rate")
  @CsvField(value = "Video played to 50%", reportField = "VideoQuartile50Rate")
  private BigDecimal videoQuartile50Rate;

  @Column(name = "VideoQuartile75Rate")
  @CsvField(value = "Video played to 75%", reportField = "VideoQuartile75Rate")
  private BigDecimal videoQuartile75Rate;

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
  public CampaignPerformanceReport() {
  }

  public CampaignPerformanceReport(Long topAccountId, Long accountId){
    super(topAccountId, accountId);
  }

  public String getPeriod() {
    return period;
  }

  public void setPeriod(String period) {
    this.period = period;
  }

  public String getUrlCustomParameters() {
    return urlCustomParameters;
  }

  public void setUrlCustomParameters(String urlCustomParameters) {
    this.urlCustomParameters = urlCustomParameters;
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

  public String getAdvertisingChannelSubType() {
    return advertisingChannelSubType;
  }

  public void setAdvertisingChannelSubType(String advertisingChannelSubType) {
    this.advertisingChannelSubType = advertisingChannelSubType;
  }

  public String getAdvertisingChannelType() {
    return advertisingChannelType;
  }

  public void setAdvertisingChannelType(String advertisingChannelType) {
    this.advertisingChannelType = advertisingChannelType;
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

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
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

  public String getAverageFrequency() {
    return BigDecimalUtil.formatAsReadable(averageFrequency);
  }

  public BigDecimal getAverageFrequencyBigDecimal() {
    return averageFrequency;
  }

  public void setAverageFrequency(String averageFrequency) {
    this.averageFrequency = BigDecimalUtil.parseFromNumberString(averageFrequency);
  }

  public String getAveragePageviews() {
    return BigDecimalUtil.formatAsReadable(averagePageviews);
  }

  public BigDecimal getAveragePageviewsBigDecimal() {
    return averagePageviews;
  }

  public void setAveragePageviews(String averagePageviews) {
    this.averagePageviews = BigDecimalUtil.parseFromNumberString(averagePageviews);
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

  public String getAverageTimeOnSite() {
    return BigDecimalUtil.formatAsReadable(averageTimeOnSite);
  }

  public BigDecimal getAverageTimeOnSiteBigDecimal() {
    return averageTimeOnSite;
  }

  public void setAverageTimeOnSite(String averageTimeOnSite) {
    this.averageTimeOnSite = BigDecimalUtil.parseFromNumberString(averageTimeOnSite);
  }

  public Long getBaseCampaignId() {
    return baseCampaignId;
  }

  public void setBaseCampaignId(Long baseCampaignId) {
    this.baseCampaignId = baseCampaignId;
  }

  public Long getBiddingStrategyId() {
    return biddingStrategyId;
  }

  public void setBiddingStrategyId(Long biddingStrategyId) {
    this.biddingStrategyId = biddingStrategyId;
  }

  public String getBiddingStrategyName() {
    return biddingStrategyName;
  }

  public void setBiddingStrategyName(String biddingStrategyName) {
    this.biddingStrategyName = biddingStrategyName;
  }

  public String getBiddingStrategyType() {
    return biddingStrategyType;
  }

  public void setBiddingStrategyType(String biddingStrategyType) {
    this.biddingStrategyType = biddingStrategyType;
  }

  public String getBounceRate() {
    return BigDecimalUtil.formatAsReadable(bounceRate);
  }

  public BigDecimal getBounceRateBigDecimal() {
    return bounceRate;
  }

  public void setBounceRate(String bounceRate) {
    this.bounceRate = BigDecimalUtil.parseFromNumberString(bounceRate);
  }

  public Long getBudgetId() {
    return budgetId;
  }

  public void setBudgetId(Long budgetId) {
    this.budgetId = budgetId;
  }

  public String getCampaignDesktopBidModifier() {
    return BigDecimalUtil.formatAsReadable(campaignDesktopBidModifier);
  }

  public BigDecimal getCampaignDesktopBidModifierBigDecimal() {
    return campaignDesktopBidModifier;
  }

  public void setCampaignDesktopBidModifier(String campaignDesktopBidModifier) {
    this.campaignDesktopBidModifier = BigDecimalUtil.parseFromNumberString(campaignDesktopBidModifier);
  }

  public Long getCampaignGroupId() {
    return campaignGroupId;
  }

  public void setCampaignGroupId(Long campaignGroupId) {
    this.campaignGroupId = campaignGroupId;
  }

  public Long getCampaignId() {
    return campaignId;
  }

  public void setCampaignId(Long campaignId) {
    this.campaignId = campaignId;
  }

  public String getCampaignMobileBidModifier() {
    return BigDecimalUtil.formatAsReadable(campaignMobileBidModifier);
  }

  public BigDecimal getCampaignMobileBidModifierBigDecimal() {
    return campaignMobileBidModifier;
  }

  public void setCampaignMobileBidModifier(String campaignMobileBidModifier) {
    this.campaignMobileBidModifier = BigDecimalUtil.parseFromNumberString(campaignMobileBidModifier);
  }

  public String getCampaignName() {
    return campaignName;
  }

  public void setCampaignName(String campaignName) {
    this.campaignName = campaignName;
  }

  public String getCampaignStatus() {
    return campaignStatus;
  }

  public void setCampaignStatus(String campaignStatus) {
    this.campaignStatus = campaignStatus;
  }

  public String getCampaignTabletBidModifier() {
    return BigDecimalUtil.formatAsReadable(campaignTabletBidModifier);
  }

  public BigDecimal getCampaignTabletBidModifierBigDecimal() {
    return campaignTabletBidModifier;
  }

  public void setCampaignTabletBidModifier(String campaignTabletBidModifier) {
    this.campaignTabletBidModifier = BigDecimalUtil.parseFromNumberString(campaignTabletBidModifier);
  }

  public String getCampaignTrialType() {
    return campaignTrialType;
  }

  public void setCampaignTrialType(String campaignTrialType) {
    this.campaignTrialType = campaignTrialType;
  }

  public Long getClickAssistedConversions() {
    return clickAssistedConversions;
  }

  public void setClickAssistedConversions(Long clickAssistedConversions) {
    this.clickAssistedConversions = clickAssistedConversions;
  }

  public String getClickAssistedConversionsOverLastClickConversions() {
    return BigDecimalUtil.formatAsReadable(clickAssistedConversionsOverLastClickConversions);
  }

  public BigDecimal getClickAssistedConversionsOverLastClickConversionsBigDecimal() {
    return clickAssistedConversionsOverLastClickConversions;
  }

  public void setClickAssistedConversionsOverLastClickConversions(String clickAssistedConversionsOverLastClickConversions) {
    this.clickAssistedConversionsOverLastClickConversions = BigDecimalUtil.parseFromNumberString(clickAssistedConversionsOverLastClickConversions);
  }

  public String getClickAssistedConversionValue() {
    return BigDecimalUtil.formatAsReadable(clickAssistedConversionValue);
  }

  public BigDecimal getClickAssistedConversionValueBigDecimal() {
    return clickAssistedConversionValue;
  }

  public void setClickAssistedConversionValue(String clickAssistedConversionValue) {
    this.clickAssistedConversionValue = BigDecimalUtil.parseFromNumberString(clickAssistedConversionValue);
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

  public String getConversionAdjustment() {
    return conversionAdjustment;
  }

  public void setConversionAdjustment(String conversionAdjustment) {
    this.conversionAdjustment = conversionAdjustment;
  }

  public String getConversionAdjustmentLagBucket() {
    return conversionAdjustmentLagBucket;
  }

  public void setConversionAdjustmentLagBucket(String conversionAdjustmentLagBucket) {
    this.conversionAdjustmentLagBucket = conversionAdjustmentLagBucket;
  }

  public String getConversionAttributionEventType() {
    return conversionAttributionEventType;
  }

  public void setConversionAttributionEventType(String conversionAttributionEventType) {
    this.conversionAttributionEventType = conversionAttributionEventType;
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

  public String getCostPerCurrentModelAttributedConversion() {
    return BigDecimalUtil.formatAsReadable(costPerCurrentModelAttributedConversion);
  }

  public BigDecimal getCostPerCurrentModelAttributedConversionBigDecimal() {
    return costPerCurrentModelAttributedConversion;
  }

  public void setCostPerCurrentModelAttributedConversion(String costPerCurrentModelAttributedConversion) {
    this.costPerCurrentModelAttributedConversion = BigDecimalUtil.parseFromNumberString(costPerCurrentModelAttributedConversion);
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

  public String getCurrentModelAttributedConversions() {
    return BigDecimalUtil.formatAsReadable(currentModelAttributedConversions);
  }

  public BigDecimal getCurrentModelAttributedConversionsBigDecimal() {
    return currentModelAttributedConversions;
  }

  public void setCurrentModelAttributedConversions(String currentModelAttributedConversions) {
    this.currentModelAttributedConversions = BigDecimalUtil.parseFromNumberString(currentModelAttributedConversions);
  }

  public String getCurrentModelAttributedConversionValue() {
    return BigDecimalUtil.formatAsReadable(currentModelAttributedConversionValue);
  }

  public BigDecimal getCurrentModelAttributedConversionValueBigDecimal() {
    return currentModelAttributedConversionValue;
  }

  public void setCurrentModelAttributedConversionValue(String currentModelAttributedConversionValue) {
    this.currentModelAttributedConversionValue = BigDecimalUtil.parseFromNumberString(currentModelAttributedConversionValue);
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

  public String getEnhancedCpcEnabled() {
    return enhancedCpcEnabled;
  }

  public void setEnhancedCpcEnabled(String enhancedCpcEnabled) {
    this.enhancedCpcEnabled = enhancedCpcEnabled;
  }

  public String getExternalConversionSource() {
    return externalConversionSource;
  }

  public void setExternalConversionSource(String externalConversionSource) {
    this.externalConversionSource = externalConversionSource;
  }

  public String getFinalUrlSuffix() {
    return finalUrlSuffix;
  }

  public void setFinalUrlSuffix(String finalUrlSuffix) {
    this.finalUrlSuffix = finalUrlSuffix;
  }

  public Long getGmailForwards() {
    return gmailForwards;
  }

  public void setGmailForwards(Long gmailForwards) {
    this.gmailForwards = gmailForwards;
  }

  public Long getGmailSaves() {
    return gmailSaves;
  }

  public void setGmailSaves(Long gmailSaves) {
    this.gmailSaves = gmailSaves;
  }

  public Long getGmailSecondaryClicks() {
    return gmailSecondaryClicks;
  }

  public void setGmailSecondaryClicks(Long gmailSecondaryClicks) {
    this.gmailSecondaryClicks = gmailSecondaryClicks;
  }

  public String getHasRecommendedBudget() {
    return hasRecommendedBudget;
  }

  public void setHasRecommendedBudget(String hasRecommendedBudget) {
    this.hasRecommendedBudget = hasRecommendedBudget;
  }

  public Long getImpressionAssistedConversions() {
    return impressionAssistedConversions;
  }

  public void setImpressionAssistedConversions(Long impressionAssistedConversions) {
    this.impressionAssistedConversions = impressionAssistedConversions;
  }

  public String getImpressionAssistedConversionsOverLastClickConversions() {
    return BigDecimalUtil.formatAsReadable(impressionAssistedConversionsOverLastClickConversions);
  }

  public BigDecimal getImpressionAssistedConversionsOverLastClickConversionsBigDecimal() {
    return impressionAssistedConversionsOverLastClickConversions;
  }

  public void setImpressionAssistedConversionsOverLastClickConversions(String impressionAssistedConversionsOverLastClickConversions) {
    this.impressionAssistedConversionsOverLastClickConversions = BigDecimalUtil.parseFromNumberString(impressionAssistedConversionsOverLastClickConversions);
  }

  public String getImpressionAssistedConversionValue() {
    return BigDecimalUtil.formatAsReadable(impressionAssistedConversionValue);
  }

  public BigDecimal getImpressionAssistedConversionValueBigDecimal() {
    return impressionAssistedConversionValue;
  }

  public void setImpressionAssistedConversionValue(String impressionAssistedConversionValue) {
    this.impressionAssistedConversionValue = BigDecimalUtil.parseFromNumberString(impressionAssistedConversionValue);
  }

  public Long getImpressionReach() {
    return impressionReach;
  }

  public void setImpressionReach(Long impressionReach) {
    this.impressionReach = impressionReach;
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

  public String getIsBudgetExplicitlyShared() {
    return isBudgetExplicitlyShared;
  }

  public void setIsBudgetExplicitlyShared(String isBudgetExplicitlyShared) {
    this.isBudgetExplicitlyShared = isBudgetExplicitlyShared;
  }

  public String getLabelIds() {
    return labelIds;
  }

  public void setLabelIds(String labelIds) {
    this.labelIds = labelIds;
  }

  public String getLabels() {
    return labels;
  }

  public void setLabels(String labels) {
    this.labels = labels;
  }

  public String getMaximizeConversionValueTargetRoas() {
    return BigDecimalUtil.formatAsReadable(maximizeConversionValueTargetRoas);
  }

  public BigDecimal getMaximizeConversionValueTargetRoasBigDecimal() {
    return maximizeConversionValueTargetRoas;
  }

  public void setMaximizeConversionValueTargetRoas(String maximizeConversionValueTargetRoas) {
    this.maximizeConversionValueTargetRoas = BigDecimalUtil.parseFromNumberString(maximizeConversionValueTargetRoas);
  }

  public Long getNumOfflineImpressions() {
    return numOfflineImpressions;
  }

  public void setNumOfflineImpressions(Long numOfflineImpressions) {
    this.numOfflineImpressions = numOfflineImpressions;
  }

  public Long getNumOfflineInteractions() {
    return numOfflineInteractions;
  }

  public void setNumOfflineInteractions(Long numOfflineInteractions) {
    this.numOfflineInteractions = numOfflineInteractions;
  }

  public String getOfflineInteractionRate() {
    return BigDecimalUtil.formatAsReadable(offlineInteractionRate);
  }

  public BigDecimal getOfflineInteractionRateBigDecimal() {
    return offlineInteractionRate;
  }

  public void setOfflineInteractionRate(String offlineInteractionRate) {
    this.offlineInteractionRate = BigDecimalUtil.parseFromNumberString(offlineInteractionRate);
  }

  public String getPercentNewVisitors() {
    return BigDecimalUtil.formatAsReadable(percentNewVisitors);
  }

  public BigDecimal getPercentNewVisitorsBigDecimal() {
    return percentNewVisitors;
  }

  public void setPercentNewVisitors(String percentNewVisitors) {
    this.percentNewVisitors = BigDecimalUtil.parseFromNumberString(percentNewVisitors);
  }

  public BigDecimal getRecommendedBudgetAmount() {
    return recommendedBudgetAmount;
  }

  public void setRecommendedBudgetAmount(BigDecimal recommendedBudgetAmount) {
    this.recommendedBudgetAmount = recommendedBudgetAmount;
  }

  public String getRelativeCtr() {
    return BigDecimalUtil.formatAsReadable(relativeCtr);
  }

  public BigDecimal getRelativeCtrBigDecimal() {
    return relativeCtr;
  }

  public void setRelativeCtr(String relativeCtr) {
    this.relativeCtr = BigDecimalUtil.parseFromNumberString(relativeCtr);
  }

  public String getSearchAbsoluteTopImpressionShare() {
    return BigDecimalUtil.formatAsReadable(searchAbsoluteTopImpressionShare);
  }

  public BigDecimal getSearchAbsoluteTopImpressionShareBigDecimal() {
    return searchAbsoluteTopImpressionShare;
  }

  public void setSearchAbsoluteTopImpressionShare(String searchAbsoluteTopImpressionShare) {
    this.searchAbsoluteTopImpressionShare = BigDecimalUtil.parseFromNumberString(searchAbsoluteTopImpressionShare);
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

  public String getSearchClickShare() {
    return BigDecimalUtil.formatAsReadable(searchClickShare);
  }

  public BigDecimal getSearchClickShareBigDecimal() {
    return searchClickShare;
  }

  public void setSearchClickShare(String searchClickShare) {
    this.searchClickShare = BigDecimalUtil.parseFromNumberString(searchClickShare);
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

  public String getServingStatus() {
    return servingStatus;
  }

  public void setServingStatus(String servingStatus) {
    this.servingStatus = servingStatus;
  }

  public String getSlot() {
    return slot;
  }

  public void setSlot(String slot) {
    this.slot = slot;
  }

  public BigDecimal getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
  }

  public String getTrackingUrlTemplate() {
    return trackingUrlTemplate;
  }

  public void setTrackingUrlTemplate(String trackingUrlTemplate) {
    this.trackingUrlTemplate = trackingUrlTemplate;
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

  public String getValuePerCurrentModelAttributedConversion() {
    return BigDecimalUtil.formatAsReadable(valuePerCurrentModelAttributedConversion);
  }

  public BigDecimal getValuePerCurrentModelAttributedConversionBigDecimal() {
    return valuePerCurrentModelAttributedConversion;
  }

  public void setValuePerCurrentModelAttributedConversion(String valuePerCurrentModelAttributedConversion) {
    this.valuePerCurrentModelAttributedConversion = BigDecimalUtil.parseFromNumberString(valuePerCurrentModelAttributedConversion);
  }

  public String getVideoQuartile100Rate() {
    return BigDecimalUtil.formatAsReadable(videoQuartile100Rate);
  }

  public BigDecimal getVideoQuartile100RateBigDecimal() {
    return videoQuartile100Rate;
  }

  public void setVideoQuartile100Rate(String videoQuartile100Rate) {
    this.videoQuartile100Rate = BigDecimalUtil.parseFromNumberString(videoQuartile100Rate);
  }

  public String getVideoQuartile25Rate() {
    return BigDecimalUtil.formatAsReadable(videoQuartile25Rate);
  }

  public BigDecimal getVideoQuartile25RateBigDecimal() {
    return videoQuartile25Rate;
  }

  public void setVideoQuartile25Rate(String videoQuartile25Rate) {
    this.videoQuartile25Rate = BigDecimalUtil.parseFromNumberString(videoQuartile25Rate);
  }

  public String getVideoQuartile50Rate() {
    return BigDecimalUtil.formatAsReadable(videoQuartile50Rate);
  }

  public BigDecimal getVideoQuartile50RateBigDecimal() {
    return videoQuartile50Rate;
  }

  public void setVideoQuartile50Rate(String videoQuartile50Rate) {
    this.videoQuartile50Rate = BigDecimalUtil.parseFromNumberString(videoQuartile50Rate);
  }

  public String getVideoQuartile75Rate() {
    return BigDecimalUtil.formatAsReadable(videoQuartile75Rate);
  }

  public BigDecimal getVideoQuartile75RateBigDecimal() {
    return videoQuartile75Rate;
  }

  public void setVideoQuartile75Rate(String videoQuartile75Rate) {
    this.videoQuartile75Rate = BigDecimalUtil.parseFromNumberString(videoQuartile75Rate);
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
    if (campaignId != null) {
      idBuilder.append("-").append(campaignId);
    }
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
    if (!StringUtils.isEmpty(conversionAdjustmentLagBucket)) {
      idBuilder.append("-").append(conversionAdjustmentLagBucket);
    }
    if (!StringUtils.isEmpty(conversionAttributionEventType)) {
      idBuilder.append("-").append(conversionAttributionEventType);
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
    CampaignPerformanceReport other = (CampaignPerformanceReport) obj;
    return new EqualsBuilder()
      .appendSuper(super.equals(obj))
      .append(period, other.period)
      .append(urlCustomParameters, other.urlCustomParameters)
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
      .append(advertisingChannelSubType, other.advertisingChannelSubType)
      .append(advertisingChannelType, other.advertisingChannelType)
      .append(allConversionRate, other.allConversionRate)
      .append(allConversions, other.allConversions)
      .append(allConversionValue, other.allConversionValue)
      .append(amount, other.amount)
      .append(averageCost, other.averageCost)
      .append(averageCpc, other.averageCpc)
      .append(averageCpe, other.averageCpe)
      .append(averageCpm, other.averageCpm)
      .append(averageCpv, other.averageCpv)
      .append(averageFrequency, other.averageFrequency)
      .append(averagePageviews, other.averagePageviews)
      .append(averagePosition, other.averagePosition)
      .append(averageTimeOnSite, other.averageTimeOnSite)
      .append(baseCampaignId, other.baseCampaignId)
      .append(biddingStrategyId, other.biddingStrategyId)
      .append(biddingStrategyName, other.biddingStrategyName)
      .append(biddingStrategyType, other.biddingStrategyType)
      .append(bounceRate, other.bounceRate)
      .append(budgetId, other.budgetId)
      .append(campaignDesktopBidModifier, other.campaignDesktopBidModifier)
      .append(campaignGroupId, other.campaignGroupId)
      .append(campaignId, other.campaignId)
      .append(campaignMobileBidModifier, other.campaignMobileBidModifier)
      .append(campaignName, other.campaignName)
      .append(campaignStatus, other.campaignStatus)
      .append(campaignTabletBidModifier, other.campaignTabletBidModifier)
      .append(campaignTrialType, other.campaignTrialType)
      .append(clickAssistedConversions, other.clickAssistedConversions)
      .append(clickAssistedConversionsOverLastClickConversions, other.clickAssistedConversionsOverLastClickConversions)
      .append(clickAssistedConversionValue, other.clickAssistedConversionValue)
      .append(clicks, other.clicks)
      .append(clickType, other.clickType)
      .append(contentBudgetLostImpressionShare, other.contentBudgetLostImpressionShare)
      .append(contentImpressionShare, other.contentImpressionShare)
      .append(contentRankLostImpressionShare, other.contentRankLostImpressionShare)
      .append(conversionAdjustment, other.conversionAdjustment)
      .append(conversionAdjustmentLagBucket, other.conversionAdjustmentLagBucket)
      .append(conversionAttributionEventType, other.conversionAttributionEventType)
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
      .append(costPerCurrentModelAttributedConversion, other.costPerCurrentModelAttributedConversion)
      .append(crossDeviceConversions, other.crossDeviceConversions)
      .append(ctr, other.ctr)
      .append(currentModelAttributedConversions, other.currentModelAttributedConversions)
      .append(currentModelAttributedConversionValue, other.currentModelAttributedConversionValue)
      .append(customerDescriptiveName, other.customerDescriptiveName)
      .append(device, other.device)
      .append(engagementRate, other.engagementRate)
      .append(engagements, other.engagements)
      .append(enhancedCpcEnabled, other.enhancedCpcEnabled)
      .append(externalConversionSource, other.externalConversionSource)
      .append(finalUrlSuffix, other.finalUrlSuffix)
      .append(gmailForwards, other.gmailForwards)
      .append(gmailSaves, other.gmailSaves)
      .append(gmailSecondaryClicks, other.gmailSecondaryClicks)
      .append(hasRecommendedBudget, other.hasRecommendedBudget)
      .append(impressionAssistedConversions, other.impressionAssistedConversions)
      .append(impressionAssistedConversionsOverLastClickConversions, other.impressionAssistedConversionsOverLastClickConversions)
      .append(impressionAssistedConversionValue, other.impressionAssistedConversionValue)
      .append(impressionReach, other.impressionReach)
      .append(impressions, other.impressions)
      .append(interactionRate, other.interactionRate)
      .append(interactions, other.interactions)
      .append(interactionTypes, other.interactionTypes)
      .append(invalidClickRate, other.invalidClickRate)
      .append(invalidClicks, other.invalidClicks)
      .append(isBudgetExplicitlyShared, other.isBudgetExplicitlyShared)
      .append(labelIds, other.labelIds)
      .append(labels, other.labels)
      .append(maximizeConversionValueTargetRoas, other.maximizeConversionValueTargetRoas)
      .append(numOfflineImpressions, other.numOfflineImpressions)
      .append(numOfflineInteractions, other.numOfflineInteractions)
      .append(offlineInteractionRate, other.offlineInteractionRate)
      .append(percentNewVisitors, other.percentNewVisitors)
      .append(recommendedBudgetAmount, other.recommendedBudgetAmount)
      .append(relativeCtr, other.relativeCtr)
      .append(searchAbsoluteTopImpressionShare, other.searchAbsoluteTopImpressionShare)
      .append(searchBudgetLostImpressionShare, other.searchBudgetLostImpressionShare)
      .append(searchClickShare, other.searchClickShare)
      .append(searchExactMatchImpressionShare, other.searchExactMatchImpressionShare)
      .append(searchImpressionShare, other.searchImpressionShare)
      .append(searchRankLostImpressionShare, other.searchRankLostImpressionShare)
      .append(servingStatus, other.servingStatus)
      .append(slot, other.slot)
      .append(totalAmount, other.totalAmount)
      .append(trackingUrlTemplate, other.trackingUrlTemplate)
      .append(valuePerAllConversion, other.valuePerAllConversion)
      .append(valuePerConversion, other.valuePerConversion)
      .append(valuePerCurrentModelAttributedConversion, other.valuePerCurrentModelAttributedConversion)
      .append(videoQuartile100Rate, other.videoQuartile100Rate)
      .append(videoQuartile25Rate, other.videoQuartile25Rate)
      .append(videoQuartile50Rate, other.videoQuartile50Rate)
      .append(videoQuartile75Rate, other.videoQuartile75Rate)
      .append(videoViewRate, other.videoViewRate)
      .append(videoViews, other.videoViews)
      .append(viewThroughConversions, other.viewThroughConversions)
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .appendSuper(super.hashCode())
      .append(period)
      .append(urlCustomParameters)
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
      .append(advertisingChannelSubType)
      .append(advertisingChannelType)
      .append(allConversionRate)
      .append(allConversions)
      .append(allConversionValue)
      .append(amount)
      .append(averageCost)
      .append(averageCpc)
      .append(averageCpe)
      .append(averageCpm)
      .append(averageCpv)
      .append(averageFrequency)
      .append(averagePageviews)
      .append(averagePosition)
      .append(averageTimeOnSite)
      .append(baseCampaignId)
      .append(biddingStrategyId)
      .append(biddingStrategyName)
      .append(biddingStrategyType)
      .append(bounceRate)
      .append(budgetId)
      .append(campaignDesktopBidModifier)
      .append(campaignGroupId)
      .append(campaignId)
      .append(campaignMobileBidModifier)
      .append(campaignName)
      .append(campaignStatus)
      .append(campaignTabletBidModifier)
      .append(campaignTrialType)
      .append(clickAssistedConversions)
      .append(clickAssistedConversionsOverLastClickConversions)
      .append(clickAssistedConversionValue)
      .append(clicks)
      .append(clickType)
      .append(contentBudgetLostImpressionShare)
      .append(contentImpressionShare)
      .append(contentRankLostImpressionShare)
      .append(conversionAdjustment)
      .append(conversionAdjustmentLagBucket)
      .append(conversionAttributionEventType)
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
      .append(costPerCurrentModelAttributedConversion)
      .append(crossDeviceConversions)
      .append(ctr)
      .append(currentModelAttributedConversions)
      .append(currentModelAttributedConversionValue)
      .append(customerDescriptiveName)
      .append(device)
      .append(engagementRate)
      .append(engagements)
      .append(enhancedCpcEnabled)
      .append(externalConversionSource)
      .append(finalUrlSuffix)
      .append(gmailForwards)
      .append(gmailSaves)
      .append(gmailSecondaryClicks)
      .append(hasRecommendedBudget)
      .append(impressionAssistedConversions)
      .append(impressionAssistedConversionsOverLastClickConversions)
      .append(impressionAssistedConversionValue)
      .append(impressionReach)
      .append(impressions)
      .append(interactionRate)
      .append(interactions)
      .append(interactionTypes)
      .append(invalidClickRate)
      .append(invalidClicks)
      .append(isBudgetExplicitlyShared)
      .append(labelIds)
      .append(labels)
      .append(maximizeConversionValueTargetRoas)
      .append(numOfflineImpressions)
      .append(numOfflineInteractions)
      .append(offlineInteractionRate)
      .append(percentNewVisitors)
      .append(recommendedBudgetAmount)
      .append(relativeCtr)
      .append(searchAbsoluteTopImpressionShare)
      .append(searchBudgetLostImpressionShare)
      .append(searchClickShare)
      .append(searchExactMatchImpressionShare)
      .append(searchImpressionShare)
      .append(searchRankLostImpressionShare)
      .append(servingStatus)
      .append(slot)
      .append(totalAmount)
      .append(trackingUrlTemplate)
      .append(valuePerAllConversion)
      .append(valuePerConversion)
      .append(valuePerCurrentModelAttributedConversion)
      .append(videoQuartile100Rate)
      .append(videoQuartile25Rate)
      .append(videoQuartile50Rate)
      .append(videoQuartile75Rate)
      .append(videoViewRate)
      .append(videoViews)
      .append(viewThroughConversions)
      .toHashCode();
  }

}
