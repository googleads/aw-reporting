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
   * Specific report class for LandingPageReport.
 *
*/
@Entity
@Table(name = "AW_LandingPageReport")
@CsvReport(value = ReportDefinitionReportType.LANDING_PAGE_REPORT)
public class LandingPageReport extends DateReport {

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

  @Column(name = "AdvertisingChannelType")
  @CsvField(value = "Advertising Channel", reportField = "AdvertisingChannelType")
  private String advertisingChannelType;

  @Column(name = "AllConversions")
  @CsvField(value = "All conv.", reportField = "AllConversions")
  private BigDecimal allConversions;

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

  @Column(name = "ConversionRate")
  @CsvField(value = "Conv. rate", reportField = "ConversionRate")
  private BigDecimal conversionRate;

  @Column(name = "Conversions")
  @CsvField(value = "Conversions", reportField = "Conversions")
  private BigDecimal conversions;

  @Column(name = "ConversionValue")
  @CsvField(value = "Total conv. value", reportField = "ConversionValue")
  private BigDecimal conversionValue;

  @Column(name = "Cost")
  @CsvField(value = "Cost", reportField = "Cost")
  @MoneyField
  private BigDecimal cost;

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

  @Column(name = "Device")
  @CsvField(value = "Device", reportField = "Device")
  private String device;

  @Column(name = "EngagementRate")
  @CsvField(value = "Engagement rate", reportField = "EngagementRate")
  private BigDecimal engagementRate;

  @Column(name = "Engagements")
  @CsvField(value = "Engagements", reportField = "Engagements")
  private Long engagements;

  @Column(name = "ExpandedFinalUrlString")
  @CsvField(value = "Expanded landing page", reportField = "ExpandedFinalUrlString")
  private String expandedFinalUrlString;

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

  @Column(name = "PercentageMobileFriendlyClicks")
  @CsvField(value = "Mobile-friendly click rate", reportField = "PercentageMobileFriendlyClicks")
  private BigDecimal percentageMobileFriendlyClicks;

  @Column(name = "PercentageValidAcceleratedMobilePagesClicks")
  @CsvField(value = "Valid AMP click rate", reportField = "PercentageValidAcceleratedMobilePagesClicks")
  private BigDecimal percentageValidAcceleratedMobilePagesClicks;

  @Column(name = "Slot")
  @CsvField(value = "Top vs. Other", reportField = "Slot")
  private String slot;

  @Column(name = "SpeedScore")
  @CsvField(value = "Mobile speed score", reportField = "SpeedScore")
  private Long speedScore;

  @Column(name = "UnexpandedFinalUrlString")
  @CsvField(value = "Landing page", reportField = "UnexpandedFinalUrlString")
  private String unexpandedFinalUrlString;

  @Column(name = "ValuePerConversion")
  @CsvField(value = "Value / conv.", reportField = "ValuePerConversion")
  private BigDecimal valuePerConversion;

  @Column(name = "VideoViewRate")
  @CsvField(value = "View rate", reportField = "VideoViewRate")
  private BigDecimal videoViewRate;

  @Column(name = "VideoViews")
  @CsvField(value = "Views", reportField = "VideoViews")
  private Long videoViews;

  /**
   * Hibernate needs an empty constructor
   */
  public LandingPageReport() {
  }

  public LandingPageReport(Long topAccountId, Long accountId){
    super(topAccountId, accountId);
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

  public String getAdvertisingChannelType() {
    return advertisingChannelType;
  }

  public void setAdvertisingChannelType(String advertisingChannelType) {
    this.advertisingChannelType = advertisingChannelType;
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

  public String getExpandedFinalUrlString() {
    return expandedFinalUrlString;
  }

  public void setExpandedFinalUrlString(String expandedFinalUrlString) {
    this.expandedFinalUrlString = expandedFinalUrlString;
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

  public String getPercentageMobileFriendlyClicks() {
    return BigDecimalUtil.formatAsReadable(percentageMobileFriendlyClicks);
  }

  public BigDecimal getPercentageMobileFriendlyClicksBigDecimal() {
    return percentageMobileFriendlyClicks;
  }

  public void setPercentageMobileFriendlyClicks(String percentageMobileFriendlyClicks) {
    this.percentageMobileFriendlyClicks = BigDecimalUtil.parseFromNumberString(percentageMobileFriendlyClicks);
  }

  public String getPercentageValidAcceleratedMobilePagesClicks() {
    return BigDecimalUtil.formatAsReadable(percentageValidAcceleratedMobilePagesClicks);
  }

  public BigDecimal getPercentageValidAcceleratedMobilePagesClicksBigDecimal() {
    return percentageValidAcceleratedMobilePagesClicks;
  }

  public void setPercentageValidAcceleratedMobilePagesClicks(String percentageValidAcceleratedMobilePagesClicks) {
    this.percentageValidAcceleratedMobilePagesClicks = BigDecimalUtil.parseFromNumberString(percentageValidAcceleratedMobilePagesClicks);
  }

  public String getSlot() {
    return slot;
  }

  public void setSlot(String slot) {
    this.slot = slot;
  }

  public Long getSpeedScore() {
    return speedScore;
  }

  public void setSpeedScore(Long speedScore) {
    this.speedScore = speedScore;
  }

  public String getUnexpandedFinalUrlString() {
    return unexpandedFinalUrlString;
  }

  public void setUnexpandedFinalUrlString(String unexpandedFinalUrlString) {
    this.unexpandedFinalUrlString = unexpandedFinalUrlString;
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
    if (!StringUtils.isEmpty(device)) {
      idBuilder.append("-").append(device);
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
    LandingPageReport other = (LandingPageReport) obj;
    return new EqualsBuilder()
      .appendSuper(super.equals(obj))
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
      .append(advertisingChannelType, other.advertisingChannelType)
      .append(allConversions, other.allConversions)
      .append(averageCost, other.averageCost)
      .append(averageCpc, other.averageCpc)
      .append(averageCpe, other.averageCpe)
      .append(averageCpm, other.averageCpm)
      .append(averageCpv, other.averageCpv)
      .append(averagePosition, other.averagePosition)
      .append(campaignId, other.campaignId)
      .append(campaignName, other.campaignName)
      .append(campaignStatus, other.campaignStatus)
      .append(clicks, other.clicks)
      .append(clickType, other.clickType)
      .append(conversionRate, other.conversionRate)
      .append(conversions, other.conversions)
      .append(conversionValue, other.conversionValue)
      .append(cost, other.cost)
      .append(costPerConversion, other.costPerConversion)
      .append(crossDeviceConversions, other.crossDeviceConversions)
      .append(ctr, other.ctr)
      .append(device, other.device)
      .append(engagementRate, other.engagementRate)
      .append(engagements, other.engagements)
      .append(expandedFinalUrlString, other.expandedFinalUrlString)
      .append(impressions, other.impressions)
      .append(interactionRate, other.interactionRate)
      .append(interactions, other.interactions)
      .append(interactionTypes, other.interactionTypes)
      .append(percentageMobileFriendlyClicks, other.percentageMobileFriendlyClicks)
      .append(percentageValidAcceleratedMobilePagesClicks, other.percentageValidAcceleratedMobilePagesClicks)
      .append(slot, other.slot)
      .append(speedScore, other.speedScore)
      .append(unexpandedFinalUrlString, other.unexpandedFinalUrlString)
      .append(valuePerConversion, other.valuePerConversion)
      .append(videoViewRate, other.videoViewRate)
      .append(videoViews, other.videoViews)
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .appendSuper(super.hashCode())
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
      .append(advertisingChannelType)
      .append(allConversions)
      .append(averageCost)
      .append(averageCpc)
      .append(averageCpe)
      .append(averageCpm)
      .append(averageCpv)
      .append(averagePosition)
      .append(campaignId)
      .append(campaignName)
      .append(campaignStatus)
      .append(clicks)
      .append(clickType)
      .append(conversionRate)
      .append(conversions)
      .append(conversionValue)
      .append(cost)
      .append(costPerConversion)
      .append(crossDeviceConversions)
      .append(ctr)
      .append(device)
      .append(engagementRate)
      .append(engagements)
      .append(expandedFinalUrlString)
      .append(impressions)
      .append(interactionRate)
      .append(interactions)
      .append(interactionTypes)
      .append(percentageMobileFriendlyClicks)
      .append(percentageValidAcceleratedMobilePagesClicks)
      .append(slot)
      .append(speedScore)
      .append(unexpandedFinalUrlString)
      .append(valuePerConversion)
      .append(videoViewRate)
      .append(videoViews)
      .toHashCode();
  }

}
