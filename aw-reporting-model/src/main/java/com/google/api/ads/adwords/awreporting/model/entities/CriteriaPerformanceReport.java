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
   * Specific report class for CriteriaPerformanceReport.
 *
*/
@Entity
@Table(name = "AW_CriteriaPerformanceReport")
@CsvReport(value = ReportDefinitionReportType.CRITERIA_PERFORMANCE_REPORT)
public class CriteriaPerformanceReport extends DateReport {

  @Column(name = "Parameter")
  @CsvField(value = "Dynamic ad target", reportField = "Parameter")
  private String parameter;

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

  @Column(name = "AveragePosition")
  @CsvField(value = "Avg. position", reportField = "AveragePosition")
  private BigDecimal averagePosition;

  @Column(name = "BaseAdGroupId")
  @CsvField(value = "Base Ad group ID", reportField = "BaseAdGroupId")
  private Long baseAdGroupId;

  @Column(name = "BaseCampaignId")
  @CsvField(value = "Base Campaign ID", reportField = "BaseCampaignId")
  private Long baseCampaignId;

  @Column(name = "BidModifier")
  @CsvField(value = "Bid adj.", reportField = "BidModifier")
  private BigDecimal bidModifier;

  @Column(name = "CampaignId")
  @CsvField(value = "Campaign ID", reportField = "CampaignId")
  private Long campaignId;

  @Column(name = "CampaignName")
  @CsvField(value = "Campaign", reportField = "CampaignName")
  private String campaignName;

  @Column(name = "CampaignStatus")
  @CsvField(value = "Campaign state", reportField = "CampaignStatus")
  private String campaignStatus;

  @Column(name = "Clicks")
  @CsvField(value = "Clicks", reportField = "Clicks")
  private Long clicks;

  @Column(name = "ClickType")
  @CsvField(value = "Click type", reportField = "ClickType")
  private String clickType;

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

  @Column(name = "CpvBid")
  @CsvField(value = "Max. CPV", reportField = "CpvBid")
  @MoneyField
  private BigDecimal cpvBid;

  @Column(name = "CpvBidSource")
  @CsvField(value = "Max CPV source", reportField = "CpvBidSource")
  private String cpvBidSource;

  @Column(name = "CreativeQualityScore")
  @CsvField(value = "Ad relevance", reportField = "CreativeQualityScore")
  private String creativeQualityScore;

  @Column(name = "Criteria")
  @CsvField(value = "Keyword / Placement", reportField = "Criteria")
  private String criteria;

  @Column(name = "CriteriaDestinationUrl", length = 2048)
  @CsvField(value = "Keyword/Placement destination URL", reportField = "CriteriaDestinationUrl")
  private String criteriaDestinationUrl;

  @Column(name = "CriteriaType")
  @CsvField(value = "Criteria Type", reportField = "CriteriaType")
  private String criteriaType;

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

  @Column(name = "DisplayName")
  @CsvField(value = "Criteria Display Name", reportField = "DisplayName")
  private String displayName;

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

  @Column(name = "FinalAppUrls", length = 2048)
  @CsvField(value = "App final URL", reportField = "FinalAppUrls")
  private String finalAppUrls;

  @Column(name = "FinalMobileUrls", length = 2048)
  @CsvField(value = "Mobile final URL", reportField = "FinalMobileUrls")
  private String finalMobileUrls;

  @Column(name = "FinalUrls", length = 2048)
  @CsvField(value = "Final URL", reportField = "FinalUrls")
  private String finalUrls;

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

  @Column(name = "Id")
  @CsvField(value = "Keyword ID", reportField = "Id")
  private Long id;

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

  @Column(name = "LabelIds")
  @CsvField(value = "Label IDs", reportField = "LabelIds")
  private String labelIds;

  @Column(name = "Labels")
  @CsvField(value = "Labels", reportField = "Labels")
  private String labels;

  @Column(name = "PostClickQualityScore")
  @CsvField(value = "Landing page experience", reportField = "PostClickQualityScore")
  private String postClickQualityScore;

  @Column(name = "QualityScore")
  @CsvField(value = "Quality score", reportField = "QualityScore")
  private Integer qualityScore;

  @Column(name = "SearchPredictedCtr")
  @CsvField(value = "Expected clickthrough rate", reportField = "SearchPredictedCtr")
  private String searchPredictedCtr;

  @Column(name = "Slot")
  @CsvField(value = "Top vs. Other", reportField = "Slot")
  private String slot;

  @Column(name = "Status")
  @CsvField(value = "Keyword/Placement state", reportField = "Status")
  private String status;

  @Column(name = "SystemServingStatus")
  @CsvField(value = "Criterion serving status", reportField = "SystemServingStatus")
  private String systemServingStatus;

  @Column(name = "TopOfPageCpc")
  @CsvField(value = "Top of page CPC", reportField = "TopOfPageCpc")
  private String topOfPageCpc;

  @Column(name = "TrackingUrlTemplate", length = 2048)
  @CsvField(value = "Tracking template", reportField = "TrackingUrlTemplate")
  private String trackingUrlTemplate;

  @Column(name = "UrlCustomParameters", length = 2048)
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
  public CriteriaPerformanceReport() {
  }

  public CriteriaPerformanceReport(Long topAccountId, Long accountId){
    super(topAccountId, accountId);
  }

  public String getParameter() {
    return parameter;
  }

  public void setParameter(String parameter) {
    this.parameter = parameter;
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

  public String getAveragePosition() {
    return BigDecimalUtil.formatAsReadable(averagePosition);
  }

  public BigDecimal getAveragePositionBigDecimal() {
    return averagePosition;
  }

  public void setAveragePosition(String averagePosition) {
    this.averagePosition = BigDecimalUtil.parseFromNumberString(averagePosition);
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

  public String getBidModifier() {
    return BigDecimalUtil.formatAsReadable(bidModifier);
  }

  public BigDecimal getBidModifierBigDecimal() {
    return bidModifier;
  }

  public void setBidModifier(String bidModifier) {
    this.bidModifier = BigDecimalUtil.parseFromNumberString(bidModifier);
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

  public BigDecimal getCpvBid() {
    return cpvBid;
  }

  public void setCpvBid(BigDecimal cpvBid) {
    this.cpvBid = cpvBid;
  }

  public String getCpvBidSource() {
    return cpvBidSource;
  }

  public void setCpvBidSource(String cpvBidSource) {
    this.cpvBidSource = cpvBidSource;
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

  public String getCriteriaType() {
    return criteriaType;
  }

  public void setCriteriaType(String criteriaType) {
    this.criteriaType = criteriaType;
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

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public String getSearchPredictedCtr() {
    return searchPredictedCtr;
  }

  public void setSearchPredictedCtr(String searchPredictedCtr) {
    this.searchPredictedCtr = searchPredictedCtr;
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
    CriteriaPerformanceReport other = (CriteriaPerformanceReport) obj;
    return new EqualsBuilder()
      .appendSuper(super.equals(obj))
      .append(parameter, other.parameter)
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
      .append(averagePosition, other.averagePosition)
      .append(baseAdGroupId, other.baseAdGroupId)
      .append(baseCampaignId, other.baseCampaignId)
      .append(bidModifier, other.bidModifier)
      .append(campaignId, other.campaignId)
      .append(campaignName, other.campaignName)
      .append(campaignStatus, other.campaignStatus)
      .append(clicks, other.clicks)
      .append(clickType, other.clickType)
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
      .append(cpvBid, other.cpvBid)
      .append(cpvBidSource, other.cpvBidSource)
      .append(creativeQualityScore, other.creativeQualityScore)
      .append(criteria, other.criteria)
      .append(criteriaDestinationUrl, other.criteriaDestinationUrl)
      .append(criteriaType, other.criteriaType)
      .append(crossDeviceConversions, other.crossDeviceConversions)
      .append(ctr, other.ctr)
      .append(currentModelAttributedConversions, other.currentModelAttributedConversions)
      .append(currentModelAttributedConversionValue, other.currentModelAttributedConversionValue)
      .append(customerDescriptiveName, other.customerDescriptiveName)
      .append(device, other.device)
      .append(displayName, other.displayName)
      .append(engagementRate, other.engagementRate)
      .append(engagements, other.engagements)
      .append(enhancedCpcEnabled, other.enhancedCpcEnabled)
      .append(estimatedAddClicksAtFirstPositionCpc, other.estimatedAddClicksAtFirstPositionCpc)
      .append(estimatedAddCostAtFirstPositionCpc, other.estimatedAddCostAtFirstPositionCpc)
      .append(externalConversionSource, other.externalConversionSource)
      .append(finalAppUrls, other.finalAppUrls)
      .append(finalMobileUrls, other.finalMobileUrls)
      .append(finalUrls, other.finalUrls)
      .append(firstPageCpc, other.firstPageCpc)
      .append(firstPositionCpc, other.firstPositionCpc)
      .append(gmailForwards, other.gmailForwards)
      .append(gmailSaves, other.gmailSaves)
      .append(gmailSecondaryClicks, other.gmailSecondaryClicks)
      .append(hasQualityScore, other.hasQualityScore)
      .append(id, other.id)
      .append(impressions, other.impressions)
      .append(interactionRate, other.interactionRate)
      .append(interactions, other.interactions)
      .append(interactionTypes, other.interactionTypes)
      .append(isNegative, other.isNegative)
      .append(labelIds, other.labelIds)
      .append(labels, other.labels)
      .append(postClickQualityScore, other.postClickQualityScore)
      .append(qualityScore, other.qualityScore)
      .append(searchPredictedCtr, other.searchPredictedCtr)
      .append(slot, other.slot)
      .append(status, other.status)
      .append(systemServingStatus, other.systemServingStatus)
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
      .append(parameter)
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
      .append(averagePosition)
      .append(baseAdGroupId)
      .append(baseCampaignId)
      .append(bidModifier)
      .append(campaignId)
      .append(campaignName)
      .append(campaignStatus)
      .append(clicks)
      .append(clickType)
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
      .append(cpvBid)
      .append(cpvBidSource)
      .append(creativeQualityScore)
      .append(criteria)
      .append(criteriaDestinationUrl)
      .append(criteriaType)
      .append(crossDeviceConversions)
      .append(ctr)
      .append(currentModelAttributedConversions)
      .append(currentModelAttributedConversionValue)
      .append(customerDescriptiveName)
      .append(device)
      .append(displayName)
      .append(engagementRate)
      .append(engagements)
      .append(enhancedCpcEnabled)
      .append(estimatedAddClicksAtFirstPositionCpc)
      .append(estimatedAddCostAtFirstPositionCpc)
      .append(externalConversionSource)
      .append(finalAppUrls)
      .append(finalMobileUrls)
      .append(finalUrls)
      .append(firstPageCpc)
      .append(firstPositionCpc)
      .append(gmailForwards)
      .append(gmailSaves)
      .append(gmailSecondaryClicks)
      .append(hasQualityScore)
      .append(id)
      .append(impressions)
      .append(interactionRate)
      .append(interactions)
      .append(interactionTypes)
      .append(isNegative)
      .append(labelIds)
      .append(labels)
      .append(postClickQualityScore)
      .append(qualityScore)
      .append(searchPredictedCtr)
      .append(slot)
      .append(status)
      .append(systemServingStatus)
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
