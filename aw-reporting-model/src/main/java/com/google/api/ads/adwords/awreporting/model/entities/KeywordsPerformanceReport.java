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
import javax.persistence.Lob;
import javax.persistence.Table;

/**
   * Specific report class for KeywordsPerformanceReport.
 *
*/
@Entity
@Table(name = "AW_KeywordsPerformanceReport")
@CsvReport(value = ReportDefinitionReportType.KEYWORDS_PERFORMANCE_REPORT)
public class KeywordsPerformanceReport extends DateReport {

  @Column(name = "AbsoluteTopImpressionPercentage")
  @CsvField(value = "Impr. (Abs. Top) %", reportField = "AbsoluteTopImpressionPercentage")
  private BigDecimal absoluteTopImpressionPercentage;

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

  @Column(name = "AdGroupId")
  @CsvField(value = "Ad group ID", reportField = "AdGroupId")
  private Long adGroupId;

  @Column(name = "AdGroupName")
  @CsvField(value = "Ad group", reportField = "AdGroupName")
  private String adGroupName;

  @Column(name = "AdGroupStatus")
  @CsvField(value = "Ad group state", reportField = "AdGroupStatus")
  private String adGroupStatus;

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

  @Column(name = "ApprovalStatus")
  @CsvField(value = "Approval Status", reportField = "ApprovalStatus")
  private String approvalStatus;

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

  @Column(name = "AveragePageviews")
  @CsvField(value = "Pages / session", reportField = "AveragePageviews")
  private BigDecimal averagePageviews;

  @Column(name = "AveragePosition")
  @CsvField(value = "Avg. position", reportField = "AveragePosition")
  private BigDecimal averagePosition;

  @Column(name = "AverageTimeOnSite")
  @CsvField(value = "Avg. session duration (seconds)", reportField = "AverageTimeOnSite")
  private BigDecimal averageTimeOnSite;

  @Column(name = "BaseAdGroupId")
  @CsvField(value = "Base Ad group ID", reportField = "BaseAdGroupId")
  private Long baseAdGroupId;

  @Column(name = "BaseCampaignId")
  @CsvField(value = "Base Campaign ID", reportField = "BaseCampaignId")
  private Long baseCampaignId;

  @Column(name = "BiddingStrategyId")
  @CsvField(value = "Bid Strategy ID", reportField = "BiddingStrategyId")
  private Long biddingStrategyId;

  @Column(name = "BiddingStrategyName")
  @CsvField(value = "Bid Strategy Name", reportField = "BiddingStrategyName")
  private String biddingStrategyName;

  @Column(name = "BiddingStrategySource")
  @CsvField(value = "Bidding Strategy Source", reportField = "BiddingStrategySource")
  private String biddingStrategySource;

  @Column(name = "BiddingStrategyType")
  @CsvField(value = "Bid Strategy Type", reportField = "BiddingStrategyType")
  private String biddingStrategyType;

  @Column(name = "BounceRate")
  @CsvField(value = "Bounce rate", reportField = "BounceRate")
  private BigDecimal bounceRate;

  @Column(name = "CampaignId")
  @CsvField(value = "Campaign ID", reportField = "CampaignId")
  private Long campaignId;

  @Column(name = "CampaignName")
  @CsvField(value = "Campaign", reportField = "CampaignName")
  private String campaignName;

  @Column(name = "CampaignStatus")
  @CsvField(value = "Campaign state", reportField = "CampaignStatus")
  private String campaignStatus;

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

  @Column(name = "ConversionAdjustment")
  @CsvField(value = "Conversion adjustment", reportField = "ConversionAdjustment")
  private String conversionAdjustment;

  @Column(name = "ConversionAdjustmentLagBucket")
  @CsvField(value = "Days to conversion or adjustment", reportField = "ConversionAdjustmentLagBucket")
  private String conversionAdjustmentLagBucket;

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

  @Column(name = "CpcBid")
  @CsvField(value = "Max. CPC", reportField = "CpcBid")
  @MoneyField
  private BigDecimal cpcBid;

  @Column(name = "CpcBidSource")
  @CsvField(value = "Max CPC source", reportField = "CpcBidSource")
  private String cpcBidSource;

  @Column(name = "CpmBid")
  @CsvField(value = "Max. CPM", reportField = "CpmBid")
  @MoneyField
  private BigDecimal cpmBid;

  @Column(name = "CreativeQualityScore")
  @CsvField(value = "Ad relevance", reportField = "CreativeQualityScore")
  private String creativeQualityScore;

  @Column(name = "Criteria")
  @CsvField(value = "Keyword", reportField = "Criteria")
  private String criteria;

  @Column(name = "CriteriaDestinationUrl")
  @Lob
  @CsvField(value = "Destination URL", reportField = "CriteriaDestinationUrl")
  private String criteriaDestinationUrl;

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

  @Column(name = "EstimatedAddClicksAtFirstPositionCpc")
  @CsvField(value = "Est. add. clicks/wk (first position bid)", reportField = "EstimatedAddClicksAtFirstPositionCpc")
  private Long estimatedAddClicksAtFirstPositionCpc;

  @Column(name = "EstimatedAddCostAtFirstPositionCpc")
  @CsvField(value = "Est. add. cost/wk (first position bid)", reportField = "EstimatedAddCostAtFirstPositionCpc")
  @MoneyField
  private BigDecimal estimatedAddCostAtFirstPositionCpc;

  @Column(name = "ExternalConversionSource")
  @CsvField(value = "Conversion source", reportField = "ExternalConversionSource")
  private String externalConversionSource;

  @Column(name = "FinalAppUrls")
  @Lob
  @CsvField(value = "App final URL", reportField = "FinalAppUrls")
  private String finalAppUrls;

  @Column(name = "FinalMobileUrls")
  @Lob
  @CsvField(value = "Mobile final URL", reportField = "FinalMobileUrls")
  private String finalMobileUrls;

  @Column(name = "FinalUrls")
  @Lob
  @CsvField(value = "Final URL", reportField = "FinalUrls")
  private String finalUrls;

  @Column(name = "FinalUrlSuffix")
  @CsvField(value = "Final URL suffix", reportField = "FinalUrlSuffix")
  private String finalUrlSuffix;

  @Column(name = "FirstPageCpc")
  @CsvField(value = "First page CPC", reportField = "FirstPageCpc")
  private String firstPageCpc;

  @Column(name = "FirstPositionCpc")
  @CsvField(value = "First position CPC", reportField = "FirstPositionCpc")
  private String firstPositionCpc;

  @Column(name = "GmailForwards")
  @CsvField(value = "Gmail forwards", reportField = "GmailForwards")
  private Long gmailForwards;

  @Column(name = "GmailSaves")
  @CsvField(value = "Gmail saves", reportField = "GmailSaves")
  private Long gmailSaves;

  @Column(name = "GmailSecondaryClicks")
  @CsvField(value = "Gmail clicks to website", reportField = "GmailSecondaryClicks")
  private Long gmailSecondaryClicks;

  @Column(name = "HasQualityScore")
  @CsvField(value = "Has Quality Score", reportField = "HasQualityScore")
  private String hasQualityScore;

  @Column(name = "HistoricalCreativeQualityScore")
  @CsvField(value = "Ad relevance (hist.)", reportField = "HistoricalCreativeQualityScore")
  private String historicalCreativeQualityScore;

  @Column(name = "HistoricalLandingPageQualityScore")
  @CsvField(value = "Landing page experience (hist.)", reportField = "HistoricalLandingPageQualityScore")
  private String historicalLandingPageQualityScore;

  @Column(name = "HistoricalQualityScore")
  @CsvField(value = "Qual. score (hist.)", reportField = "HistoricalQualityScore")
  private Integer historicalQualityScore;

  @Column(name = "HistoricalSearchPredictedCtr")
  @CsvField(value = "Expected clickthrough rate (hist.)", reportField = "HistoricalSearchPredictedCtr")
  private String historicalSearchPredictedCtr;

  @Column(name = "Id")
  @CsvField(value = "Keyword ID", reportField = "Id")
  private Long id;

  @Column(name = "ImpressionAssistedConversions")
  @CsvField(value = "Impr. Assisted Conv.", reportField = "ImpressionAssistedConversions")
  private Long impressionAssistedConversions;

  @Column(name = "ImpressionAssistedConversionsOverLastClickConversions")
  @CsvField(value = "Impr. Assisted Conv. / Last Click Conv.", reportField = "ImpressionAssistedConversionsOverLastClickConversions")
  private BigDecimal impressionAssistedConversionsOverLastClickConversions;

  @Column(name = "ImpressionAssistedConversionValue")
  @CsvField(value = "Impr. Assisted Conv. Value", reportField = "ImpressionAssistedConversionValue")
  private BigDecimal impressionAssistedConversionValue;

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

  @Column(name = "IsNegative")
  @CsvField(value = "Is negative", reportField = "IsNegative")
  private String isNegative;

  @Column(name = "KeywordMatchType")
  @CsvField(value = "Match type", reportField = "KeywordMatchType")
  private String keywordMatchType;

  @Column(name = "LabelIds")
  @CsvField(value = "Label IDs", reportField = "LabelIds")
  private String labelIds;

  @Column(name = "Labels")
  @CsvField(value = "Labels", reportField = "Labels")
  private String labels;

  @Column(name = "PercentNewVisitors")
  @CsvField(value = "% new sessions", reportField = "PercentNewVisitors")
  private BigDecimal percentNewVisitors;

  @Column(name = "PostClickQualityScore")
  @CsvField(value = "Landing page experience", reportField = "PostClickQualityScore")
  private String postClickQualityScore;

  @Column(name = "QualityScore")
  @CsvField(value = "Quality score", reportField = "QualityScore")
  private Integer qualityScore;

  @Column(name = "SearchAbsoluteTopImpressionShare")
  @CsvField(value = "Search abs. top IS", reportField = "SearchAbsoluteTopImpressionShare")
  private BigDecimal searchAbsoluteTopImpressionShare;

  @Column(name = "SearchBudgetLostAbsoluteTopImpressionShare")
  @CsvField(value = "Search lost abs. top IS (budget)", reportField = "SearchBudgetLostAbsoluteTopImpressionShare")
  private BigDecimal searchBudgetLostAbsoluteTopImpressionShare;

  @Column(name = "SearchBudgetLostTopImpressionShare")
  @CsvField(value = "Search lost top IS (budget)", reportField = "SearchBudgetLostTopImpressionShare")
  private BigDecimal searchBudgetLostTopImpressionShare;

  @Column(name = "SearchExactMatchImpressionShare")
  @CsvField(value = "Search Exact match IS", reportField = "SearchExactMatchImpressionShare")
  private BigDecimal searchExactMatchImpressionShare;

  @Column(name = "SearchImpressionShare")
  @CsvField(value = "Search Impr. share", reportField = "SearchImpressionShare")
  private BigDecimal searchImpressionShare;

  @Column(name = "SearchPredictedCtr")
  @CsvField(value = "Expected clickthrough rate", reportField = "SearchPredictedCtr")
  private String searchPredictedCtr;

  @Column(name = "SearchRankLostAbsoluteTopImpressionShare")
  @CsvField(value = "Search lost abs. top IS (rank)", reportField = "SearchRankLostAbsoluteTopImpressionShare")
  private BigDecimal searchRankLostAbsoluteTopImpressionShare;

  @Column(name = "SearchRankLostImpressionShare")
  @CsvField(value = "Search Lost IS (rank)", reportField = "SearchRankLostImpressionShare")
  private BigDecimal searchRankLostImpressionShare;

  @Column(name = "SearchRankLostTopImpressionShare")
  @CsvField(value = "Search lost top IS (rank)", reportField = "SearchRankLostTopImpressionShare")
  private BigDecimal searchRankLostTopImpressionShare;

  @Column(name = "SearchTopImpressionShare")
  @CsvField(value = "Search top IS", reportField = "SearchTopImpressionShare")
  private BigDecimal searchTopImpressionShare;

  @Column(name = "Slot")
  @CsvField(value = "Top vs. Other", reportField = "Slot")
  private String slot;

  @Column(name = "Status")
  @CsvField(value = "Keyword state", reportField = "Status")
  private String status;

  @Column(name = "SystemServingStatus")
  @CsvField(value = "Criterion serving status", reportField = "SystemServingStatus")
  private String systemServingStatus;

  @Column(name = "TopImpressionPercentage")
  @CsvField(value = "Impr. (Top) %", reportField = "TopImpressionPercentage")
  private BigDecimal topImpressionPercentage;

  @Column(name = "TopOfPageCpc")
  @CsvField(value = "Top of page CPC", reportField = "TopOfPageCpc")
  private String topOfPageCpc;

  @Column(name = "TrackingUrlTemplate")
  @Lob
  @CsvField(value = "Tracking template", reportField = "TrackingUrlTemplate")
  private String trackingUrlTemplate;

  @Column(name = "UrlCustomParameters")
  @Lob
  @CsvField(value = "Custom parameter", reportField = "UrlCustomParameters")
  private String urlCustomParameters;

  @Column(name = "ValuePerAllConversion")
  @CsvField(value = "Value / all conv.", reportField = "ValuePerAllConversion")
  private BigDecimal valuePerAllConversion;

  @Column(name = "ValuePerConversion")
  @CsvField(value = "Value / conv.", reportField = "ValuePerConversion")
  private BigDecimal valuePerConversion;

  @Column(name = "ValuePerCurrentModelAttributedConversion")
  @CsvField(value = "Value / conv. (current model)", reportField = "ValuePerCurrentModelAttributedConversion")
  private BigDecimal valuePerCurrentModelAttributedConversion;

  @Column(name = "VerticalId")
  @CsvField(value = "Vertical ID", reportField = "VerticalId")
  private Long verticalId;

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
  public KeywordsPerformanceReport() {
  }

  public KeywordsPerformanceReport(Long topAccountId, Long accountId){
    super(topAccountId, accountId);
  }

  public String getAbsoluteTopImpressionPercentage() {
    return BigDecimalUtil.formatAsReadable(absoluteTopImpressionPercentage);
  }

  public BigDecimal getAbsoluteTopImpressionPercentageBigDecimal() {
    return absoluteTopImpressionPercentage;
  }

  public void setAbsoluteTopImpressionPercentage(String absoluteTopImpressionPercentage) {
    this.absoluteTopImpressionPercentage = BigDecimalUtil.parseFromNumberString(absoluteTopImpressionPercentage);
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

  public Long getAdGroupId() {
    return adGroupId;
  }

  public void setAdGroupId(Long adGroupId) {
    this.adGroupId = adGroupId;
  }

  public String getAdGroupName() {
    return adGroupName;
  }

  public void setAdGroupName(String adGroupName) {
    this.adGroupName = adGroupName;
  }

  public String getAdGroupStatus() {
    return adGroupStatus;
  }

  public void setAdGroupStatus(String adGroupStatus) {
    this.adGroupStatus = adGroupStatus;
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

  public String getApprovalStatus() {
    return approvalStatus;
  }

  public void setApprovalStatus(String approvalStatus) {
    this.approvalStatus = approvalStatus;
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

  public Long getBaseAdGroupId() {
    return baseAdGroupId;
  }

  public void setBaseAdGroupId(Long baseAdGroupId) {
    this.baseAdGroupId = baseAdGroupId;
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

  public String getBiddingStrategySource() {
    return biddingStrategySource;
  }

  public void setBiddingStrategySource(String biddingStrategySource) {
    this.biddingStrategySource = biddingStrategySource;
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

  public Long getCampaignId() {
    return campaignId;
  }

  public void setCampaignId(Long campaignId) {
    this.campaignId = campaignId;
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

  public BigDecimal getCpcBid() {
    return cpcBid;
  }

  public void setCpcBid(BigDecimal cpcBid) {
    this.cpcBid = cpcBid;
  }

  public String getCpcBidSource() {
    return cpcBidSource;
  }

  public void setCpcBidSource(String cpcBidSource) {
    this.cpcBidSource = cpcBidSource;
  }

  public BigDecimal getCpmBid() {
    return cpmBid;
  }

  public void setCpmBid(BigDecimal cpmBid) {
    this.cpmBid = cpmBid;
  }

  public String getCreativeQualityScore() {
    return creativeQualityScore;
  }

  public void setCreativeQualityScore(String creativeQualityScore) {
    this.creativeQualityScore = creativeQualityScore;
  }

  public String getCriteria() {
    return criteria;
  }

  public void setCriteria(String criteria) {
    this.criteria = criteria;
  }

  public String getCriteriaDestinationUrl() {
    return criteriaDestinationUrl;
  }

  public void setCriteriaDestinationUrl(String criteriaDestinationUrl) {
    this.criteriaDestinationUrl = criteriaDestinationUrl;
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

  public Long getEstimatedAddClicksAtFirstPositionCpc() {
    return estimatedAddClicksAtFirstPositionCpc;
  }

  public void setEstimatedAddClicksAtFirstPositionCpc(Long estimatedAddClicksAtFirstPositionCpc) {
    this.estimatedAddClicksAtFirstPositionCpc = estimatedAddClicksAtFirstPositionCpc;
  }

  public BigDecimal getEstimatedAddCostAtFirstPositionCpc() {
    return estimatedAddCostAtFirstPositionCpc;
  }

  public void setEstimatedAddCostAtFirstPositionCpc(BigDecimal estimatedAddCostAtFirstPositionCpc) {
    this.estimatedAddCostAtFirstPositionCpc = estimatedAddCostAtFirstPositionCpc;
  }

  public String getExternalConversionSource() {
    return externalConversionSource;
  }

  public void setExternalConversionSource(String externalConversionSource) {
    this.externalConversionSource = externalConversionSource;
  }

  public String getFinalAppUrls() {
    return finalAppUrls;
  }

  public void setFinalAppUrls(String finalAppUrls) {
    this.finalAppUrls = finalAppUrls;
  }

  public String getFinalMobileUrls() {
    return finalMobileUrls;
  }

  public void setFinalMobileUrls(String finalMobileUrls) {
    this.finalMobileUrls = finalMobileUrls;
  }

  public String getFinalUrls() {
    return finalUrls;
  }

  public void setFinalUrls(String finalUrls) {
    this.finalUrls = finalUrls;
  }

  public String getFinalUrlSuffix() {
    return finalUrlSuffix;
  }

  public void setFinalUrlSuffix(String finalUrlSuffix) {
    this.finalUrlSuffix = finalUrlSuffix;
  }

  public String getFirstPageCpc() {
    return firstPageCpc;
  }

  public void setFirstPageCpc(String firstPageCpc) {
    this.firstPageCpc = firstPageCpc;
  }

  public String getFirstPositionCpc() {
    return firstPositionCpc;
  }

  public void setFirstPositionCpc(String firstPositionCpc) {
    this.firstPositionCpc = firstPositionCpc;
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

  public String getHasQualityScore() {
    return hasQualityScore;
  }

  public void setHasQualityScore(String hasQualityScore) {
    this.hasQualityScore = hasQualityScore;
  }

  public String getHistoricalCreativeQualityScore() {
    return historicalCreativeQualityScore;
  }

  public void setHistoricalCreativeQualityScore(String historicalCreativeQualityScore) {
    this.historicalCreativeQualityScore = historicalCreativeQualityScore;
  }

  public String getHistoricalLandingPageQualityScore() {
    return historicalLandingPageQualityScore;
  }

  public void setHistoricalLandingPageQualityScore(String historicalLandingPageQualityScore) {
    this.historicalLandingPageQualityScore = historicalLandingPageQualityScore;
  }

  public Integer getHistoricalQualityScore() {
    return historicalQualityScore;
  }

  public void setHistoricalQualityScore(Integer historicalQualityScore) {
    this.historicalQualityScore = historicalQualityScore;
  }

  public String getHistoricalSearchPredictedCtr() {
    return historicalSearchPredictedCtr;
  }

  public void setHistoricalSearchPredictedCtr(String historicalSearchPredictedCtr) {
    this.historicalSearchPredictedCtr = historicalSearchPredictedCtr;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public String getIsNegative() {
    return isNegative;
  }

  public void setIsNegative(String isNegative) {
    this.isNegative = isNegative;
  }

  public String getKeywordMatchType() {
    return keywordMatchType;
  }

  public void setKeywordMatchType(String keywordMatchType) {
    this.keywordMatchType = keywordMatchType;
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

  public String getPercentNewVisitors() {
    return BigDecimalUtil.formatAsReadable(percentNewVisitors);
  }

  public BigDecimal getPercentNewVisitorsBigDecimal() {
    return percentNewVisitors;
  }

  public void setPercentNewVisitors(String percentNewVisitors) {
    this.percentNewVisitors = BigDecimalUtil.parseFromNumberString(percentNewVisitors);
  }

  public String getPostClickQualityScore() {
    return postClickQualityScore;
  }

  public void setPostClickQualityScore(String postClickQualityScore) {
    this.postClickQualityScore = postClickQualityScore;
  }

  public Integer getQualityScore() {
    return qualityScore;
  }

  public void setQualityScore(Integer qualityScore) {
    this.qualityScore = qualityScore;
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

  public String getSearchBudgetLostAbsoluteTopImpressionShare() {
    return BigDecimalUtil.formatAsReadable(searchBudgetLostAbsoluteTopImpressionShare);
  }

  public BigDecimal getSearchBudgetLostAbsoluteTopImpressionShareBigDecimal() {
    return searchBudgetLostAbsoluteTopImpressionShare;
  }

  public void setSearchBudgetLostAbsoluteTopImpressionShare(String searchBudgetLostAbsoluteTopImpressionShare) {
    this.searchBudgetLostAbsoluteTopImpressionShare = BigDecimalUtil.parseFromNumberString(searchBudgetLostAbsoluteTopImpressionShare);
  }

  public String getSearchBudgetLostTopImpressionShare() {
    return BigDecimalUtil.formatAsReadable(searchBudgetLostTopImpressionShare);
  }

  public BigDecimal getSearchBudgetLostTopImpressionShareBigDecimal() {
    return searchBudgetLostTopImpressionShare;
  }

  public void setSearchBudgetLostTopImpressionShare(String searchBudgetLostTopImpressionShare) {
    this.searchBudgetLostTopImpressionShare = BigDecimalUtil.parseFromNumberString(searchBudgetLostTopImpressionShare);
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

  public String getSearchPredictedCtr() {
    return searchPredictedCtr;
  }

  public void setSearchPredictedCtr(String searchPredictedCtr) {
    this.searchPredictedCtr = searchPredictedCtr;
  }

  public String getSearchRankLostAbsoluteTopImpressionShare() {
    return BigDecimalUtil.formatAsReadable(searchRankLostAbsoluteTopImpressionShare);
  }

  public BigDecimal getSearchRankLostAbsoluteTopImpressionShareBigDecimal() {
    return searchRankLostAbsoluteTopImpressionShare;
  }

  public void setSearchRankLostAbsoluteTopImpressionShare(String searchRankLostAbsoluteTopImpressionShare) {
    this.searchRankLostAbsoluteTopImpressionShare = BigDecimalUtil.parseFromNumberString(searchRankLostAbsoluteTopImpressionShare);
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

  public String getSearchRankLostTopImpressionShare() {
    return BigDecimalUtil.formatAsReadable(searchRankLostTopImpressionShare);
  }

  public BigDecimal getSearchRankLostTopImpressionShareBigDecimal() {
    return searchRankLostTopImpressionShare;
  }

  public void setSearchRankLostTopImpressionShare(String searchRankLostTopImpressionShare) {
    this.searchRankLostTopImpressionShare = BigDecimalUtil.parseFromNumberString(searchRankLostTopImpressionShare);
  }

  public String getSearchTopImpressionShare() {
    return BigDecimalUtil.formatAsReadable(searchTopImpressionShare);
  }

  public BigDecimal getSearchTopImpressionShareBigDecimal() {
    return searchTopImpressionShare;
  }

  public void setSearchTopImpressionShare(String searchTopImpressionShare) {
    this.searchTopImpressionShare = BigDecimalUtil.parseFromNumberString(searchTopImpressionShare);
  }

  public String getSlot() {
    return slot;
  }

  public void setSlot(String slot) {
    this.slot = slot;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getSystemServingStatus() {
    return systemServingStatus;
  }

  public void setSystemServingStatus(String systemServingStatus) {
    this.systemServingStatus = systemServingStatus;
  }

  public String getTopImpressionPercentage() {
    return BigDecimalUtil.formatAsReadable(topImpressionPercentage);
  }

  public BigDecimal getTopImpressionPercentageBigDecimal() {
    return topImpressionPercentage;
  }

  public void setTopImpressionPercentage(String topImpressionPercentage) {
    this.topImpressionPercentage = BigDecimalUtil.parseFromNumberString(topImpressionPercentage);
  }

  public String getTopOfPageCpc() {
    return topOfPageCpc;
  }

  public void setTopOfPageCpc(String topOfPageCpc) {
    this.topOfPageCpc = topOfPageCpc;
  }

  public String getTrackingUrlTemplate() {
    return trackingUrlTemplate;
  }

  public void setTrackingUrlTemplate(String trackingUrlTemplate) {
    this.trackingUrlTemplate = trackingUrlTemplate;
  }

  public String getUrlCustomParameters() {
    return urlCustomParameters;
  }

  public void setUrlCustomParameters(String urlCustomParameters) {
    this.urlCustomParameters = urlCustomParameters;
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

  public Long getVerticalId() {
    return verticalId;
  }

  public void setVerticalId(Long verticalId) {
    this.verticalId = verticalId;
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
    if (adGroupId != null) {
      idBuilder.append("-").append(adGroupId);
    }
    if (id != null) {
      idBuilder.append("-").append(id);
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
    KeywordsPerformanceReport other = (KeywordsPerformanceReport) obj;
    return new EqualsBuilder()
      .appendSuper(super.equals(obj))
      .append(absoluteTopImpressionPercentage, other.absoluteTopImpressionPercentage)
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
      .append(adGroupId, other.adGroupId)
      .append(adGroupName, other.adGroupName)
      .append(adGroupStatus, other.adGroupStatus)
      .append(adNetworkType1, other.adNetworkType1)
      .append(adNetworkType2, other.adNetworkType2)
      .append(allConversionRate, other.allConversionRate)
      .append(allConversions, other.allConversions)
      .append(allConversionValue, other.allConversionValue)
      .append(approvalStatus, other.approvalStatus)
      .append(averageCost, other.averageCost)
      .append(averageCpc, other.averageCpc)
      .append(averageCpe, other.averageCpe)
      .append(averageCpm, other.averageCpm)
      .append(averageCpv, other.averageCpv)
      .append(averagePageviews, other.averagePageviews)
      .append(averagePosition, other.averagePosition)
      .append(averageTimeOnSite, other.averageTimeOnSite)
      .append(baseAdGroupId, other.baseAdGroupId)
      .append(baseCampaignId, other.baseCampaignId)
      .append(biddingStrategyId, other.biddingStrategyId)
      .append(biddingStrategyName, other.biddingStrategyName)
      .append(biddingStrategySource, other.biddingStrategySource)
      .append(biddingStrategyType, other.biddingStrategyType)
      .append(bounceRate, other.bounceRate)
      .append(campaignId, other.campaignId)
      .append(campaignName, other.campaignName)
      .append(campaignStatus, other.campaignStatus)
      .append(clickAssistedConversions, other.clickAssistedConversions)
      .append(clickAssistedConversionsOverLastClickConversions, other.clickAssistedConversionsOverLastClickConversions)
      .append(clickAssistedConversionValue, other.clickAssistedConversionValue)
      .append(clicks, other.clicks)
      .append(clickType, other.clickType)
      .append(conversionAdjustment, other.conversionAdjustment)
      .append(conversionAdjustmentLagBucket, other.conversionAdjustmentLagBucket)
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
      .append(cpcBid, other.cpcBid)
      .append(cpcBidSource, other.cpcBidSource)
      .append(cpmBid, other.cpmBid)
      .append(creativeQualityScore, other.creativeQualityScore)
      .append(criteria, other.criteria)
      .append(criteriaDestinationUrl, other.criteriaDestinationUrl)
      .append(crossDeviceConversions, other.crossDeviceConversions)
      .append(ctr, other.ctr)
      .append(currentModelAttributedConversions, other.currentModelAttributedConversions)
      .append(currentModelAttributedConversionValue, other.currentModelAttributedConversionValue)
      .append(customerDescriptiveName, other.customerDescriptiveName)
      .append(device, other.device)
      .append(engagementRate, other.engagementRate)
      .append(engagements, other.engagements)
      .append(enhancedCpcEnabled, other.enhancedCpcEnabled)
      .append(estimatedAddClicksAtFirstPositionCpc, other.estimatedAddClicksAtFirstPositionCpc)
      .append(estimatedAddCostAtFirstPositionCpc, other.estimatedAddCostAtFirstPositionCpc)
      .append(externalConversionSource, other.externalConversionSource)
      .append(finalAppUrls, other.finalAppUrls)
      .append(finalMobileUrls, other.finalMobileUrls)
      .append(finalUrls, other.finalUrls)
      .append(finalUrlSuffix, other.finalUrlSuffix)
      .append(firstPageCpc, other.firstPageCpc)
      .append(firstPositionCpc, other.firstPositionCpc)
      .append(gmailForwards, other.gmailForwards)
      .append(gmailSaves, other.gmailSaves)
      .append(gmailSecondaryClicks, other.gmailSecondaryClicks)
      .append(hasQualityScore, other.hasQualityScore)
      .append(historicalCreativeQualityScore, other.historicalCreativeQualityScore)
      .append(historicalLandingPageQualityScore, other.historicalLandingPageQualityScore)
      .append(historicalQualityScore, other.historicalQualityScore)
      .append(historicalSearchPredictedCtr, other.historicalSearchPredictedCtr)
      .append(id, other.id)
      .append(impressionAssistedConversions, other.impressionAssistedConversions)
      .append(impressionAssistedConversionsOverLastClickConversions, other.impressionAssistedConversionsOverLastClickConversions)
      .append(impressionAssistedConversionValue, other.impressionAssistedConversionValue)
      .append(impressions, other.impressions)
      .append(interactionRate, other.interactionRate)
      .append(interactions, other.interactions)
      .append(interactionTypes, other.interactionTypes)
      .append(isNegative, other.isNegative)
      .append(keywordMatchType, other.keywordMatchType)
      .append(labelIds, other.labelIds)
      .append(labels, other.labels)
      .append(percentNewVisitors, other.percentNewVisitors)
      .append(postClickQualityScore, other.postClickQualityScore)
      .append(qualityScore, other.qualityScore)
      .append(searchAbsoluteTopImpressionShare, other.searchAbsoluteTopImpressionShare)
      .append(searchBudgetLostAbsoluteTopImpressionShare, other.searchBudgetLostAbsoluteTopImpressionShare)
      .append(searchBudgetLostTopImpressionShare, other.searchBudgetLostTopImpressionShare)
      .append(searchExactMatchImpressionShare, other.searchExactMatchImpressionShare)
      .append(searchImpressionShare, other.searchImpressionShare)
      .append(searchPredictedCtr, other.searchPredictedCtr)
      .append(searchRankLostAbsoluteTopImpressionShare, other.searchRankLostAbsoluteTopImpressionShare)
      .append(searchRankLostImpressionShare, other.searchRankLostImpressionShare)
      .append(searchRankLostTopImpressionShare, other.searchRankLostTopImpressionShare)
      .append(searchTopImpressionShare, other.searchTopImpressionShare)
      .append(slot, other.slot)
      .append(status, other.status)
      .append(systemServingStatus, other.systemServingStatus)
      .append(topImpressionPercentage, other.topImpressionPercentage)
      .append(topOfPageCpc, other.topOfPageCpc)
      .append(trackingUrlTemplate, other.trackingUrlTemplate)
      .append(urlCustomParameters, other.urlCustomParameters)
      .append(valuePerAllConversion, other.valuePerAllConversion)
      .append(valuePerConversion, other.valuePerConversion)
      .append(valuePerCurrentModelAttributedConversion, other.valuePerCurrentModelAttributedConversion)
      .append(verticalId, other.verticalId)
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
      .append(absoluteTopImpressionPercentage)
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
      .append(adGroupId)
      .append(adGroupName)
      .append(adGroupStatus)
      .append(adNetworkType1)
      .append(adNetworkType2)
      .append(allConversionRate)
      .append(allConversions)
      .append(allConversionValue)
      .append(approvalStatus)
      .append(averageCost)
      .append(averageCpc)
      .append(averageCpe)
      .append(averageCpm)
      .append(averageCpv)
      .append(averagePageviews)
      .append(averagePosition)
      .append(averageTimeOnSite)
      .append(baseAdGroupId)
      .append(baseCampaignId)
      .append(biddingStrategyId)
      .append(biddingStrategyName)
      .append(biddingStrategySource)
      .append(biddingStrategyType)
      .append(bounceRate)
      .append(campaignId)
      .append(campaignName)
      .append(campaignStatus)
      .append(clickAssistedConversions)
      .append(clickAssistedConversionsOverLastClickConversions)
      .append(clickAssistedConversionValue)
      .append(clicks)
      .append(clickType)
      .append(conversionAdjustment)
      .append(conversionAdjustmentLagBucket)
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
      .append(cpcBid)
      .append(cpcBidSource)
      .append(cpmBid)
      .append(creativeQualityScore)
      .append(criteria)
      .append(criteriaDestinationUrl)
      .append(crossDeviceConversions)
      .append(ctr)
      .append(currentModelAttributedConversions)
      .append(currentModelAttributedConversionValue)
      .append(customerDescriptiveName)
      .append(device)
      .append(engagementRate)
      .append(engagements)
      .append(enhancedCpcEnabled)
      .append(estimatedAddClicksAtFirstPositionCpc)
      .append(estimatedAddCostAtFirstPositionCpc)
      .append(externalConversionSource)
      .append(finalAppUrls)
      .append(finalMobileUrls)
      .append(finalUrls)
      .append(finalUrlSuffix)
      .append(firstPageCpc)
      .append(firstPositionCpc)
      .append(gmailForwards)
      .append(gmailSaves)
      .append(gmailSecondaryClicks)
      .append(hasQualityScore)
      .append(historicalCreativeQualityScore)
      .append(historicalLandingPageQualityScore)
      .append(historicalQualityScore)
      .append(historicalSearchPredictedCtr)
      .append(id)
      .append(impressionAssistedConversions)
      .append(impressionAssistedConversionsOverLastClickConversions)
      .append(impressionAssistedConversionValue)
      .append(impressions)
      .append(interactionRate)
      .append(interactions)
      .append(interactionTypes)
      .append(isNegative)
      .append(keywordMatchType)
      .append(labelIds)
      .append(labels)
      .append(percentNewVisitors)
      .append(postClickQualityScore)
      .append(qualityScore)
      .append(searchAbsoluteTopImpressionShare)
      .append(searchBudgetLostAbsoluteTopImpressionShare)
      .append(searchBudgetLostTopImpressionShare)
      .append(searchExactMatchImpressionShare)
      .append(searchImpressionShare)
      .append(searchPredictedCtr)
      .append(searchRankLostAbsoluteTopImpressionShare)
      .append(searchRankLostImpressionShare)
      .append(searchRankLostTopImpressionShare)
      .append(searchTopImpressionShare)
      .append(slot)
      .append(status)
      .append(systemServingStatus)
      .append(topImpressionPercentage)
      .append(topOfPageCpc)
      .append(trackingUrlTemplate)
      .append(urlCustomParameters)
      .append(valuePerAllConversion)
      .append(valuePerConversion)
      .append(valuePerCurrentModelAttributedConversion)
      .append(verticalId)
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
