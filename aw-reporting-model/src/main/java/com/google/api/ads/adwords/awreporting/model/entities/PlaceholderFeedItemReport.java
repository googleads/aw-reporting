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
   * Specific report class for PlaceholderFeedItemReport.
 *
*/
@Entity
@Table(name = "AW_PlaceholderFeedItemReport")
@CsvReport(value = ReportDefinitionReportType.PLACEHOLDER_FEED_ITEM_REPORT)
public class PlaceholderFeedItemReport extends DateReport {

  @Column(name = "Scheduling")
  @CsvField(value = "Scheduling", reportField = "Scheduling")
  private String scheduling;

  @Column(name = "UrlCustomParameters")
  @Lob
  @CsvField(value = "Custom parameter", reportField = "UrlCustomParameters")
  private String urlCustomParameters;

  @Column(name = "ValidationDetails")
  @CsvField(value = "Approval Status", reportField = "ValidationDetails")
  private String validationDetails;

  @Column(name = "AccountCurrencyCode")
  @CsvField(value = "Currency", reportField = "AccountCurrencyCode")
  private String accountCurrencyCode;

  @Column(name = "AccountDescriptiveName")
  @CsvField(value = "Account", reportField = "AccountDescriptiveName")
  private String accountDescriptiveName;

  @Column(name = "AccountTimeZone")
  @CsvField(value = "Time zone", reportField = "AccountTimeZone")
  private String accountTimeZone;

  @Column(name = "AdGroupId")
  @CsvField(value = "Ad group ID", reportField = "AdGroupId")
  private Long adGroupId;

  @Column(name = "AdGroupName")
  @CsvField(value = "Ad group", reportField = "AdGroupName")
  private String adGroupName;

  @Column(name = "AdGroupStatus")
  @CsvField(value = "Ad group state", reportField = "AdGroupStatus")
  private String adGroupStatus;

  @Column(name = "AdId")
  @CsvField(value = "Ad ID", reportField = "AdId")
  private Long adId;

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

  @Column(name = "AttributeValues")
  @CsvField(value = "Attribute Values", reportField = "AttributeValues")
  private String attributeValues;

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

  @Column(name = "ConversionCategoryName")
  @CsvField(value = "Conversion category", reportField = "ConversionCategoryName")
  private String conversionCategoryName;

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

  @Column(name = "Criteria")
  @CsvField(value = "Target keyword text", reportField = "Criteria")
  private String criteria;

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

  @Column(name = "DevicePreference")
  @CsvField(value = "Device preference", reportField = "DevicePreference")
  private String devicePreference;

  @Column(name = "DisapprovalShortNames")
  @CsvField(value = "Disapproval reasons", reportField = "DisapprovalShortNames")
  private String disapprovalShortNames;

  @Column(name = "EndTime")
  @CsvField(value = "End date", reportField = "EndTime")
  private String endTime;

  @Column(name = "EngagementRate")
  @CsvField(value = "Engagement rate", reportField = "EngagementRate")
  private BigDecimal engagementRate;

  @Column(name = "Engagements")
  @CsvField(value = "Engagements", reportField = "Engagements")
  private Long engagements;

  @Column(name = "ExternalConversionSource")
  @CsvField(value = "Conversion source", reportField = "ExternalConversionSource")
  private String externalConversionSource;

  @Column(name = "FeedId")
  @CsvField(value = "Feed ID", reportField = "FeedId")
  private Long feedId;

  @Column(name = "FeedItemId")
  @CsvField(value = "Item ID", reportField = "FeedItemId")
  private Long feedItemId;

  @Column(name = "GeoTargetingCriterionId")
  @CsvField(value = "Target location", reportField = "GeoTargetingCriterionId")
  private Long geoTargetingCriterionId;

  @Column(name = "GeoTargetingRestriction")
  @CsvField(value = "Target location restriction", reportField = "GeoTargetingRestriction")
  private String geoTargetingRestriction;

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

  @Column(name = "IsSelfAction")
  @CsvField(value = "This extension vs. Other", reportField = "IsSelfAction")
  private String isSelfAction;

  @Column(name = "KeywordMatchType")
  @CsvField(value = "Target keyword match type", reportField = "KeywordMatchType")
  private String keywordMatchType;

  @Column(name = "KeywordTargetingId")
  @CsvField(value = "Keyword ID", reportField = "KeywordTargetingId")
  private Long keywordTargetingId;

  @Column(name = "KeywordTargetingMatchType")
  @CsvField(value = "Target keyword match type", reportField = "KeywordTargetingMatchType")
  private String keywordTargetingMatchType;

  @Column(name = "KeywordTargetingText")
  @CsvField(value = "Target keyword text", reportField = "KeywordTargetingText")
  private String keywordTargetingText;

  @Column(name = "PlaceholderType")
  @CsvField(value = "Feed placeholder type", reportField = "PlaceholderType")
  private Integer placeholderType;

  @Column(name = "Slot")
  @CsvField(value = "Top vs. Other", reportField = "Slot")
  private String slot;

  @Column(name = "StartTime")
  @CsvField(value = "Start date", reportField = "StartTime")
  private String startTime;

  @Column(name = "Status")
  @CsvField(value = "Item state", reportField = "Status")
  private String status;

  @Column(name = "TargetingAdGroupId")
  @CsvField(value = "Target ad group ID", reportField = "TargetingAdGroupId")
  private Long targetingAdGroupId;

  @Column(name = "TargetingCampaignId")
  @CsvField(value = "Target campaign ID", reportField = "TargetingCampaignId")
  private Long targetingCampaignId;

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

  /**
   * Hibernate needs an empty constructor
   */
  public PlaceholderFeedItemReport() {
  }

  public PlaceholderFeedItemReport(Long topAccountId, Long accountId){
    super(topAccountId, accountId);
  }

  public String getScheduling() {
    return scheduling;
  }

  public void setScheduling(String scheduling) {
    this.scheduling = scheduling;
  }

  public String getUrlCustomParameters() {
    return urlCustomParameters;
  }

  public void setUrlCustomParameters(String urlCustomParameters) {
    this.urlCustomParameters = urlCustomParameters;
  }

  public String getValidationDetails() {
    return validationDetails;
  }

  public void setValidationDetails(String validationDetails) {
    this.validationDetails = validationDetails;
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

  public Long getAdId() {
    return adId;
  }

  public void setAdId(Long adId) {
    this.adId = adId;
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

  public String getAttributeValues() {
    return attributeValues;
  }

  public void setAttributeValues(String attributeValues) {
    this.attributeValues = attributeValues;
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

  public String getConversionCategoryName() {
    return conversionCategoryName;
  }

  public void setConversionCategoryName(String conversionCategoryName) {
    this.conversionCategoryName = conversionCategoryName;
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

  public String getCriteria() {
    return criteria;
  }

  public void setCriteria(String criteria) {
    this.criteria = criteria;
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

  public String getDevicePreference() {
    return devicePreference;
  }

  public void setDevicePreference(String devicePreference) {
    this.devicePreference = devicePreference;
  }

  public String getDisapprovalShortNames() {
    return disapprovalShortNames;
  }

  public void setDisapprovalShortNames(String disapprovalShortNames) {
    this.disapprovalShortNames = disapprovalShortNames;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
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

  public Long getFeedId() {
    return feedId;
  }

  public void setFeedId(Long feedId) {
    this.feedId = feedId;
  }

  public Long getFeedItemId() {
    return feedItemId;
  }

  public void setFeedItemId(Long feedItemId) {
    this.feedItemId = feedItemId;
  }

  public Long getGeoTargetingCriterionId() {
    return geoTargetingCriterionId;
  }

  public void setGeoTargetingCriterionId(Long geoTargetingCriterionId) {
    this.geoTargetingCriterionId = geoTargetingCriterionId;
  }

  public String getGeoTargetingRestriction() {
    return geoTargetingRestriction;
  }

  public void setGeoTargetingRestriction(String geoTargetingRestriction) {
    this.geoTargetingRestriction = geoTargetingRestriction;
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

  public String getIsSelfAction() {
    return isSelfAction;
  }

  public void setIsSelfAction(String isSelfAction) {
    this.isSelfAction = isSelfAction;
  }

  public String getKeywordMatchType() {
    return keywordMatchType;
  }

  public void setKeywordMatchType(String keywordMatchType) {
    this.keywordMatchType = keywordMatchType;
  }

  public Long getKeywordTargetingId() {
    return keywordTargetingId;
  }

  public void setKeywordTargetingId(Long keywordTargetingId) {
    this.keywordTargetingId = keywordTargetingId;
  }

  public String getKeywordTargetingMatchType() {
    return keywordTargetingMatchType;
  }

  public void setKeywordTargetingMatchType(String keywordTargetingMatchType) {
    this.keywordTargetingMatchType = keywordTargetingMatchType;
  }

  public String getKeywordTargetingText() {
    return keywordTargetingText;
  }

  public void setKeywordTargetingText(String keywordTargetingText) {
    this.keywordTargetingText = keywordTargetingText;
  }

  public Integer getPlaceholderType() {
    return placeholderType;
  }

  public void setPlaceholderType(Integer placeholderType) {
    this.placeholderType = placeholderType;
  }

  public String getSlot() {
    return slot;
  }

  public void setSlot(String slot) {
    this.slot = slot;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Long getTargetingAdGroupId() {
    return targetingAdGroupId;
  }

  public void setTargetingAdGroupId(Long targetingAdGroupId) {
    this.targetingAdGroupId = targetingAdGroupId;
  }

  public Long getTargetingCampaignId() {
    return targetingCampaignId;
  }

  public void setTargetingCampaignId(Long targetingCampaignId) {
    this.targetingCampaignId = targetingCampaignId;
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
    if (adId != null) {
      idBuilder.append("-").append(adId);
    }
    if (feedId != null) {
      idBuilder.append("-").append(feedId);
    }
    if (feedItemId != null) {
      idBuilder.append("-").append(feedItemId);
    }
    idBuilder.append("-").append(getDateLabel());

    // Include all segmentation fields (if set).
    if (!StringUtils.isEmpty(adGroupName)) {
      idBuilder.append("-").append(adGroupName);
    }
    if (!StringUtils.isEmpty(adGroupStatus)) {
      idBuilder.append("-").append(adGroupStatus);
    }
    if (!StringUtils.isEmpty(adNetworkType1)) {
      idBuilder.append("-").append(adNetworkType1);
    }
    if (!StringUtils.isEmpty(adNetworkType2)) {
      idBuilder.append("-").append(adNetworkType2);
    }
    if (!StringUtils.isEmpty(campaignName)) {
      idBuilder.append("-").append(campaignName);
    }
    if (!StringUtils.isEmpty(campaignStatus)) {
      idBuilder.append("-").append(campaignStatus);
    }
    if (!StringUtils.isEmpty(clickType)) {
      idBuilder.append("-").append(clickType);
    }
    if (!StringUtils.isEmpty(conversionCategoryName)) {
      idBuilder.append("-").append(conversionCategoryName);
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
    PlaceholderFeedItemReport other = (PlaceholderFeedItemReport) obj;
    return new EqualsBuilder()
      .appendSuper(super.equals(obj))
      .append(scheduling, other.scheduling)
      .append(urlCustomParameters, other.urlCustomParameters)
      .append(validationDetails, other.validationDetails)
      .append(accountCurrencyCode, other.accountCurrencyCode)
      .append(accountDescriptiveName, other.accountDescriptiveName)
      .append(accountTimeZone, other.accountTimeZone)
      .append(adGroupId, other.adGroupId)
      .append(adGroupName, other.adGroupName)
      .append(adGroupStatus, other.adGroupStatus)
      .append(adId, other.adId)
      .append(adNetworkType1, other.adNetworkType1)
      .append(adNetworkType2, other.adNetworkType2)
      .append(allConversionRate, other.allConversionRate)
      .append(allConversions, other.allConversions)
      .append(allConversionValue, other.allConversionValue)
      .append(attributeValues, other.attributeValues)
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
      .append(conversionCategoryName, other.conversionCategoryName)
      .append(conversionRate, other.conversionRate)
      .append(conversions, other.conversions)
      .append(conversionTrackerId, other.conversionTrackerId)
      .append(conversionTypeName, other.conversionTypeName)
      .append(conversionValue, other.conversionValue)
      .append(cost, other.cost)
      .append(costPerAllConversion, other.costPerAllConversion)
      .append(costPerConversion, other.costPerConversion)
      .append(criteria, other.criteria)
      .append(crossDeviceConversions, other.crossDeviceConversions)
      .append(ctr, other.ctr)
      .append(customerDescriptiveName, other.customerDescriptiveName)
      .append(device, other.device)
      .append(devicePreference, other.devicePreference)
      .append(disapprovalShortNames, other.disapprovalShortNames)
      .append(endTime, other.endTime)
      .append(engagementRate, other.engagementRate)
      .append(engagements, other.engagements)
      .append(externalConversionSource, other.externalConversionSource)
      .append(feedId, other.feedId)
      .append(feedItemId, other.feedItemId)
      .append(geoTargetingCriterionId, other.geoTargetingCriterionId)
      .append(geoTargetingRestriction, other.geoTargetingRestriction)
      .append(impressions, other.impressions)
      .append(interactionRate, other.interactionRate)
      .append(interactions, other.interactions)
      .append(interactionTypes, other.interactionTypes)
      .append(isSelfAction, other.isSelfAction)
      .append(keywordMatchType, other.keywordMatchType)
      .append(keywordTargetingId, other.keywordTargetingId)
      .append(keywordTargetingMatchType, other.keywordTargetingMatchType)
      .append(keywordTargetingText, other.keywordTargetingText)
      .append(placeholderType, other.placeholderType)
      .append(slot, other.slot)
      .append(startTime, other.startTime)
      .append(status, other.status)
      .append(targetingAdGroupId, other.targetingAdGroupId)
      .append(targetingCampaignId, other.targetingCampaignId)
      .append(valuePerAllConversion, other.valuePerAllConversion)
      .append(valuePerConversion, other.valuePerConversion)
      .append(videoViewRate, other.videoViewRate)
      .append(videoViews, other.videoViews)
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .appendSuper(super.hashCode())
      .append(scheduling)
      .append(urlCustomParameters)
      .append(validationDetails)
      .append(accountCurrencyCode)
      .append(accountDescriptiveName)
      .append(accountTimeZone)
      .append(adGroupId)
      .append(adGroupName)
      .append(adGroupStatus)
      .append(adId)
      .append(adNetworkType1)
      .append(adNetworkType2)
      .append(allConversionRate)
      .append(allConversions)
      .append(allConversionValue)
      .append(attributeValues)
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
      .append(conversionCategoryName)
      .append(conversionRate)
      .append(conversions)
      .append(conversionTrackerId)
      .append(conversionTypeName)
      .append(conversionValue)
      .append(cost)
      .append(costPerAllConversion)
      .append(costPerConversion)
      .append(criteria)
      .append(crossDeviceConversions)
      .append(ctr)
      .append(customerDescriptiveName)
      .append(device)
      .append(devicePreference)
      .append(disapprovalShortNames)
      .append(endTime)
      .append(engagementRate)
      .append(engagements)
      .append(externalConversionSource)
      .append(feedId)
      .append(feedItemId)
      .append(geoTargetingCriterionId)
      .append(geoTargetingRestriction)
      .append(impressions)
      .append(interactionRate)
      .append(interactions)
      .append(interactionTypes)
      .append(isSelfAction)
      .append(keywordMatchType)
      .append(keywordTargetingId)
      .append(keywordTargetingMatchType)
      .append(keywordTargetingText)
      .append(placeholderType)
      .append(slot)
      .append(startTime)
      .append(status)
      .append(targetingAdGroupId)
      .append(targetingCampaignId)
      .append(valuePerAllConversion)
      .append(valuePerConversion)
      .append(videoViewRate)
      .append(videoViews)
      .toHashCode();
  }

}
