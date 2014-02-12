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

package com.google.api.ads.adwords.jaxws.extensions.report.model.entities;

import com.google.api.ads.adwords.jaxws.extensions.report.model.csv.annotation.CsvField;
import com.google.api.ads.adwords.jaxws.extensions.report.model.csv.annotation.CsvReport;
import com.google.api.ads.adwords.jaxws.extensions.report.model.util.BigDecimalUtil;
import com.google.api.ads.adwords.lib.jaxb.v201309.ReportDefinitionReportType;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Specific report class for ReportKeyword
 * 
 * @author jtoledo@google.com (Julian Toledo)
 * @author joeltoby@gmail.com (Joel Toby)
 */
@Entity
@com.googlecode.objectify.annotation.Entity
@Table(name = "AW_ReportCriteria")
@CsvReport(value = ReportDefinitionReportType.CRITERIA_PERFORMANCE_REPORT)
public class ReportCriteriaPerformance extends ReportBase {

  @Column(name = "ACCOUNT_TIME_ZONE_ID")
  @CsvField(value = "Time zone", reportField = "AccountTimeZoneId")
  private String accountTimeZoneId;

  @Column(name = "AD_GROUP_ID")
  @CsvField(value = "Ad group ID", reportField = "AdGroupId")
  private Long adGroupId;

  @Column(name = "AD_GROUP_NAME")
  @CsvField(value = "Ad group", reportField = "AdGroupName")
  private String adGroupName;

  @Column(name = "AD_GROUP_STATUS")
  @CsvField(value = "Ad group state", reportField = "AdGroupStatus")
  private String adGroupStatus;

  @Column(name = "ADVERTISER_EXPERIMENT_SEGMENTATION_BIN")
  @CsvField(value = "ACE split", reportField = "AdvertiserExperimentSegmentationBin")
  private String advertiserExperimentSegmentationBin;

  @Column(name = "APPROVAL_STATUS")
  @CsvField(value = "Approval Status", reportField = "ApprovalStatus")
  private String approvalStatus;

  @Column(name = "BID_MODIFIER")
  @CsvField(value = "Bid adj.", reportField = "BidModifier")
  private BigDecimal bidModifier;

  @Column(name = "CAMPAIGN_ID")
  @CsvField(value = "Campaign ID", reportField = "CampaignId")
  private Long campaignId;

  @Column(name = "CAMPAIGN_NAME")
  @CsvField(value = "Campaign", reportField = "CampaignName")
  private String campaignName;

  @Column(name = "CAMPAIGN_STATUS")
  @CsvField(value = "Campaign state", reportField = "CampaignStatus")
  private String campaignStatus;

  @Column(name = "CLICK_SIGNIFICANCE")
  @CsvField(value = "Clicks ACE indicator", reportField = "ClickSignificance")
  private String clickSignificance;

  @Column(name = "CONVERSION_CATEGORY_NAME")
  @CsvField(value = "Conversion tracking purpose", reportField = "ConversionCategoryName")
  private String conversionCategoryName;

  @Column(name = "CONVERSION_MANY_PER_CLICK_SIGNIFICANCE")
  @CsvField(value = "Conv. (many-per-click) ACE indicator",
  reportField = "ConversionManyPerClickSignificance")
  private String conversionManyPerClickSignificance;

  @Column(name = "CONVERSION_RATE")
  @CsvField(value = "Conv. rate (1-per-click)", reportField = "ConversionRate")
  private BigDecimal conversionRate;

  @Column(name = "CONVERSION_RATE_MANY_PER_CLICK")
  @CsvField(value = "Conv. rate (many-per-click)", reportField = "ConversionRateManyPerClick")
  private BigDecimal conversionRateManyPerClick;

  @Column(name = "CONVERSION_RATE_MANY_PER_CLICK_SIGNIFICANCE")
  @CsvField(value = "Conv. rate (many-per-click) ACE indicator",
  reportField = "ConversionRateManyPerClickSignificance")
  private String conversionRateManyPerClickSignificance;

  @Column(name = "CONVERSION_RATE_SIGNIFICANCE")
  @CsvField(value = "Conv. rate (1-per-click) ACE indicator",
  reportField = "ConversionRateSignificance")
  private String conversionRateSignificance;

  @Column(name = "CONVERSION_SIGNIFICANCE")
  @CsvField(value = "Conv. (1-per-click) ACE indicator", reportField = "ConversionSignificance")
  private String conversionSignificance;

  @Column(name = "CONVERSIONS_MANY_PER_CLICK")
  @CsvField(value = "Conv. (many-per-click)", reportField = "ConversionsManyPerClick")
  private Long conversionsManyPerClick;

  @Column(name = "CONVERSION_TYPE_NAME")
  @CsvField(value = "Conversion action name", reportField = "ConversionTypeName")
  private String conversionTypeName;

  @Column(name = "CONVERSION_VALUE")
  @CsvField(value = "Total conv. value", reportField = "ConversionValue")
  private Long conversionValue;

  @Column(name = "COST_PER_CONVERSION")
  @CsvField(value = "Cost / conv. (1-per-click)", reportField = "CostPerConversion")
  private BigDecimal costPerConversion;

  @Column(name = "COST_PER_CONVERSION_MANY_PER_CLICK")
  @CsvField(value = "Cost / conv. (many-per-click)",
  reportField = "CostPerConversionManyPerClick")
  private BigDecimal costPerConversionManyPerClick;

  @Column(name = "COST_PER_CONVERSION_MANY_PER_CLICK_SIGNIFICANCE")
  @CsvField(value = "Cost/conv. (many-per-click) ACE indicator",
  reportField = "CostPerConversionManyPerClickSignificance")
  private String costPerConversionManyPerClickSignificance;

  @Column(name = "COST_PER_CONVERSION_SIGNIFICANCE")
  @CsvField(value = "Cost/conv. (1-per-click) ACE indicator",
  reportField = "CostPerConversionSignificance")
  private String costPerConversionSignificance;

  @Column(name = "COST_SIGNIFICANCE")
  @CsvField(value = "Cost ACE indicator", reportField = "CostSignificance")
  private String costSignificance;

  @Column(name = "CPC_BID_SOURCE")
  @CsvField(value = "Max CPC source", reportField = "CpcBidSource")
  private String cpcBidSource;

  @Column(name = "CPC_SIGNIFICANCE")
  @CsvField(value = "CPC ACE indicator", reportField = "CpcSignificance")
  private String cpcSignificance;

  @Column(name = "CPM_SIGNIFICANCE")
  @CsvField(value = "CPM ACE indicator", reportField = "CpmSignificance")
  private String cpmSignificance;

  @Column(name = "CRITERIA")
  @CsvField(value = "Keyword / Placement", reportField = "Criteria")
  private String criteria;

  @Column(name = "CRITERIA_DESTINATION_URL", length = 2048)
  @CsvField(value = "Keyword/Placement destination URL", reportField = "CriteriaDestinationUrl")
  private String criteriaDestinationUrl;

  @Column(name = "CRITERIA_TYPE")
  @CsvField(value = "Criteria Type", reportField = "CriteriaType")
  private String criteriaType;

  @Column(name = "CTR_SIGNIFICANCE")
  @CsvField(value = "CTR ACE indicator", reportField = "CtrSignificance")
  private String ctrSignificance;

  @Column(name = "CUSTOMER_DESCRIPTIVE_NAME")
  @CsvField(value = "Client name", reportField = "CustomerDescriptiveName")
  private String customerDescriptiveName;

  @Column(name = "DAY_OF_WEEK")
  @CsvField(value = "Day of week", reportField = "DayOfWeek")
  private String dayOfWeek;

  @Column(name = "DISPLAY_NAME")
  @CsvField(value = "Criteria Display Name", reportField = "DisplayName")
  private String displayName;

  @Column(name = "FIRST_PAGE_CPC")
  @CsvField(value = "First page CPC", reportField = "FirstPageCpc")
  private String firstPageCpc;

  @Column(name = "KEYWORD_ID")
  @CsvField(value = "Keyword ID", reportField = "Id")
  private String keywordId;

  @Column(name = "IMPRESSION_SIGNIFICANCE")
  @CsvField(value = "Impressions ACE indicator", reportField = "ImpressionSignificance")
  private String impressionSignificance;

  @Column(name = "IS_NEGATIVE")
  @CsvField(value = "Is negative", reportField = "IsNegative")
  private String isNegative;

  @Column(name = "MAX_CPC")
  @CsvField(value = "Max. CPC", reportField = "MaxCpc")
  private String maxCpc;

  @Column(name = "MAX_CPM")
  @CsvField(value = "Max. CPM", reportField = "MaxCpm")
  private String maxCpm;

  @Column(name = "MONTH_OF_YEAR")
  @CsvField(value = "Month of Year", reportField = "MonthOfYear")
  private String monthOfYear;

  @Column(name = "PARAMETER")
  @CsvField(value = "Dynamic ad target", reportField = "Parameter")
  private String parameter;

  @Column(name = "PERCENT_CPA")
  @CsvField(value = "Max. CPA%", reportField = "PercentCpa")
  private String percentCpa;

  @Column(name = "POSITION_SIGNIFICANCE")
  @CsvField(value = "Position ACE indicator", reportField = "PositionSignificance")
  private String positionSignificance;

  @Column(name = "PRIMARY_COMPANY_NAME")
  @CsvField(value = "Company name", reportField = "PrimaryCompanyName")
  private String primaryCompanyName;

  @Column(name = "PRIMARY_USER_LOGIN")
  @CsvField(value = "Login email", reportField = "PrimaryUserLogin")
  private String primaryUserLogin;

  @Column(name = "QUALITY_SCORE")
  @CsvField(value = "Quality score", reportField = "QualityScore")
  private Long qualityScore;

  @Column(name = "QUARTER")
  @CsvField(value = "Quarter", reportField = "Quarter")
  private String quarter;

  @Column(name = "SLOT")
  @CsvField(value = "Top vs. side", reportField = "Slot")
  private String slot;

  @Column(name = "STATUS")
  @CsvField(value = "Keyword/Placement state", reportField = "Status")
  private String status;

  @Column(name = "TOP_OF_PAGE_CPC")
  @CsvField(value = "Top of page CPC", reportField = "TopOfPageCpc")
  private String topOfPageCpc;

  @Column(name = "TOTAL_CONV_VALUE")
  @CsvField(value = "Total conv. value", reportField = "TotalConvValue")
  private String totalConvValue;

  @Column(name = "VALUE_PER_CONV")
  @CsvField(value = "Value / conv. (1-per-click)", reportField = "ValuePerConv")
  private BigDecimal valuePerConv;

  @Column(name = "VALUE_PER_CONVERSION")
  @CsvField(value = "Value / conv. (1-per-click)", reportField = "ValuePerConversion")
  private BigDecimal valuePerConversion;

  @Column(name = "VALUE_PER_CONVERSION_MANY_PER_CLICK")
  @CsvField(value = "Value / conv. (many-per-click)",
  reportField = "ValuePerConversionManyPerClick")
  private BigDecimal valuePerConversionManyPerClick;

  @Column(name = "VALUEPERCONVMANYPERCLICK")
  @CsvField(value = "Value / conv. (many-per-click)", reportField = "ValuePerConvManyPerClick")
  private BigDecimal valuePerConvManyPerClick;

  @Column(name = "VIEW_THROUGH_CONVERSIONS")
  @CsvField(value = "View-through conv.", reportField = "ViewThroughConversions")
  private String viewThroughConversions;

  @Column(name = "VIEW_THROUGH_CONVERSIONS_SIGNIFICANCE")
  @CsvField(value = "View-through conv. ACE indicator",
  reportField = "ViewThroughConversionsSignificance")
  private String viewThroughConversionsSignificance;

  @Column(name = "WEEK")
  @CsvField(value = "Week", reportField = "Week")
  private String week;

  @Column(name = "YEAR")
  @CsvField(value = "Year", reportField = "Year")
  private Long year;

  /**
   * Hibernate needs an empty constructor
   */
  public ReportCriteriaPerformance() {
  }

  public ReportCriteriaPerformance(Long topAccountId, Long accountId) {
    this.topAccountId = topAccountId;
    this.accountId = accountId;
  }

  @Override
  public void setId() {
    // Generating unique id after having accountId, campaignId, adGroupId
    // and date
    this.id = this.getAccountId() + "-" + this.getCampaignId() + "-"
        + this.getAdGroupId() + "-" + this.getKeywordId();

    this.id += this.setIdDates();

    // Adding extra fields for unique ID
    if (this.getAdNetwork() != null && this.getAdNetwork().length() > 0) {
      this.id += "-" + this.getAdNetwork();
    }
    if (this.getAdNetworkPartners() != null && this.getAdNetworkPartners().length() > 0) {
      this.id += "-" + this.getAdNetworkPartners();
    }
    if (this.getDevice() != null && this.getDevice().length() > 0) {
      this.id += "-" + this.getDevice();
    }
    if (this.getClickType() != null && this.getClickType().length() > 0) {
      this.id += "-" + this.getClickType();
    }
  }

  public String getAccountTimeZoneId() {
    return accountTimeZoneId;
  }

  public void setAccountTimeZoneId(String accountTimeZoneId) {
    this.accountTimeZoneId = accountTimeZoneId;
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

  public String getAdvertiserExperimentSegmentationBin() {
    return advertiserExperimentSegmentationBin;
  }

  public void setAdvertiserExperimentSegmentationBin(
      String advertiserExperimentSegmentationBin) {
    this.advertiserExperimentSegmentationBin = advertiserExperimentSegmentationBin;
  }

  public String getApprovalStatus() {
    return approvalStatus;
  }

  public void setApprovalStatus(String approvalStatus) {
    this.approvalStatus = approvalStatus;
  }

  public String getBidModifier() {
    return BigDecimalUtil.formatAsReadable(bidModifier);
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

  public String getClickSignificance() {
    return clickSignificance;
  }

  public void setClickSignificance(String clickSignificance) {
    this.clickSignificance = clickSignificance;
  }

  public String getConversionCategoryName() {
    return conversionCategoryName;
  }

  public void setConversionCategoryName(String conversionCategoryName) {
    this.conversionCategoryName = conversionCategoryName;
  }

  public String getConversionManyPerClickSignificance() {
    return conversionManyPerClickSignificance;
  }

  public void setConversionManyPerClickSignificance(
      String conversionManyPerClickSignificance) {
    this.conversionManyPerClickSignificance = conversionManyPerClickSignificance;
  }

  public String getConversionRate() {
    return BigDecimalUtil.formatAsReadable(conversionRate);
  }

  public void setConversionRate(BigDecimal conversionRate) {
    this.conversionRate = conversionRate;
  }

  public String getConversionRateManyPerClick() {
    return BigDecimalUtil.formatAsReadable(conversionRateManyPerClick);
  }

  public void setConversionRateManyPerClick(
      BigDecimal conversionRateManyPerClick) {
    this.conversionRateManyPerClick = conversionRateManyPerClick;
  }

  public String getConversionRateManyPerClickSignificance() {
    return conversionRateManyPerClickSignificance;
  }

  public void setConversionRateManyPerClickSignificance(
      String conversionRateManyPerClickSignificance) {
    this.conversionRateManyPerClickSignificance = conversionRateManyPerClickSignificance;
  }

  public String getConversionRateSignificance() {
    return conversionRateSignificance;
  }

  public void setConversionRateSignificance(String conversionRateSignificance) {
    this.conversionRateSignificance = conversionRateSignificance;
  }

  public String getConversionSignificance() {
    return conversionSignificance;
  }

  public void setConversionSignificance(String conversionSignificance) {
    this.conversionSignificance = conversionSignificance;
  }

  public Long getConversionsManyPerClick() {
    return conversionsManyPerClick;
  }

  public void setConversionsManyPerClick(Long conversionsManyPerClick) {
    this.conversionsManyPerClick = conversionsManyPerClick;
  }

  public String getConversionTypeName() {
    return conversionTypeName;
  }

  public void setConversionTypeName(String conversionTypeName) {
    this.conversionTypeName = conversionTypeName;
  }

  public Long getConversionValue() {
    return conversionValue;
  }

  public void setConversionValue(Long conversionValue) {
    this.conversionValue = conversionValue;
  }

  public BigDecimal getCostPerConversion() {
    return costPerConversion;
  }

  public void setCostPerConversion(String costPerConversion) {
    this.costPerConversion = BigDecimalUtil.parseFromNumberString(costPerConversion);
  }

  public String getCostPerConversionManyPerClick() {
    return BigDecimalUtil.formatAsReadable(costPerConversionManyPerClick);
  }

  public void setCostPerConversionManyPerClick(
      String costPerConversionManyPerClick) {
    this.costPerConversionManyPerClick = BigDecimalUtil
        .parseFromNumberString(costPerConversionManyPerClick);
  }

  public String getCostPerConversionManyPerClickSignificance() {
    return costPerConversionManyPerClickSignificance;
  }

  public void setCostPerConversionManyPerClickSignificance(
      String costPerConversionManyPerClickSignificance) {
    this.costPerConversionManyPerClickSignificance = costPerConversionManyPerClickSignificance;
  }

  public String getCostPerConversionSignificance() {
    return costPerConversionSignificance;
  }

  public void setCostPerConversionSignificance(
      String costPerConversionSignificance) {
    this.costPerConversionSignificance = costPerConversionSignificance;
  }

  public String getCostSignificance() {
    return costSignificance;
  }

  public void setCostSignificance(String costSignificance) {
    this.costSignificance = costSignificance;
  }

  public String getCpcBidSource() {
    return cpcBidSource;
  }

  public void setCpcBidSource(String cpcBidSource) {
    this.cpcBidSource = cpcBidSource;
  }

  public String getCpcSignificance() {
    return cpcSignificance;
  }

  public void setCpcSignificance(String cpcSignificance) {
    this.cpcSignificance = cpcSignificance;
  }

  public String getCpmSignificance() {
    return cpmSignificance;
  }

  public void setCpmSignificance(String cpmSignificance) {
    this.cpmSignificance = cpmSignificance;
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

  public String getCtrSignificance() {
    return ctrSignificance;
  }

  public void setCtrSignificance(String ctrSignificance) {
    this.ctrSignificance = ctrSignificance;
  }

  public String getCustomerDescriptiveName() {
    return customerDescriptiveName;
  }

  public void setCustomerDescriptiveName(String customerDescriptiveName) {
    this.customerDescriptiveName = customerDescriptiveName;
  }

  public String getDayOfWeek() {
    return dayOfWeek;
  }

  public void setDayOfWeek(String dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getFirstPageCpc() {
    return firstPageCpc;
  }

  public void setFirstPageCpc(String firstPageCpc) {
    this.firstPageCpc = firstPageCpc;
  }

  public String getKeywordId() {
    return keywordId;
  }

  public void setKeywordId(String keywordId) {
    this.keywordId = keywordId;
  }

  public String getImpressionSignificance() {
    return impressionSignificance;
  }

  public void setImpressionSignificance(String impressionSignificance) {
    this.impressionSignificance = impressionSignificance;
  }

  public String getIsNegative() {
    return isNegative;
  }

  public void setIsNegative(String isNegative) {
    this.isNegative = isNegative;
  }

  public String getMaxCpc() {
    return maxCpc;
  }

  public void setMaxCpc(String maxCpc) {
    this.maxCpc = maxCpc;
  }

  public String getMaxCpm() {
    return maxCpm;
  }

  public void setMaxCpm(String maxCpm) {
    this.maxCpm = maxCpm;
  }

  public String getMonthOfYear() {
    return monthOfYear;
  }

  public void setMonthOfYear(String monthOfYear) {
    this.monthOfYear = monthOfYear;
  }

  public String getParameter() {
    return parameter;
  }

  public void setParameter(String parameter) {
    this.parameter = parameter;
  }

  public String getPercentCpa() {
    return percentCpa;
  }

  public void setPercentCpa(String percentCpa) {
    this.percentCpa = percentCpa;
  }

  public String getPositionSignificance() {
    return positionSignificance;
  }

  public void setPositionSignificance(String positionSignificance) {
    this.positionSignificance = positionSignificance;
  }

  public String getPrimaryCompanyName() {
    return primaryCompanyName;
  }

  public void setPrimaryCompanyName(String primaryCompanyName) {
    this.primaryCompanyName = primaryCompanyName;
  }

  public String getPrimaryUserLogin() {
    return primaryUserLogin;
  }

  public void setPrimaryUserLogin(String primaryUserLogin) {
    this.primaryUserLogin = primaryUserLogin;
  }

  public Long getQualityScore() {
    return qualityScore;
  }

  public void setQualityScore(Long qualityScore) {
    this.qualityScore = qualityScore;
  }

  public String getQuarter() {
    return quarter;
  }

  public void setQuarter(String quarter) {
    this.quarter = quarter;
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

  public String getTopOfPageCpc() {
    return topOfPageCpc;
  }

  public void setTopOfPageCpc(String topOfPageCpc) {
    this.topOfPageCpc = topOfPageCpc;
  }

  public String getTotalConvValue() {
    return totalConvValue;
  }

  public void setTotalConvValue(String totalConvValue) {
    this.totalConvValue = totalConvValue;
  }

  public String getValuePerConv() {
    return BigDecimalUtil.formatAsReadable(valuePerConv);
  }

  public void setValuePerConv(BigDecimal valuePerConv) {
    this.valuePerConv = valuePerConv;
  }

  public String getValuePerConversion() {
    return BigDecimalUtil.formatAsReadable(valuePerConversion);
  }

  public void setValuePerConversion(BigDecimal valuePerConversion) {
    this.valuePerConversion = valuePerConversion;
  }

  public String getValuePerConversionManyPerClick() {
    return BigDecimalUtil.formatAsReadable(valuePerConversionManyPerClick);
  }

  public void setValuePerConversionManyPerClick(
      BigDecimal valuePerConversionManyPerClick) {
    this.valuePerConversionManyPerClick = valuePerConversionManyPerClick;
  }

  public String getValuePerConvManyPerClick() {
    return BigDecimalUtil.formatAsReadable(valuePerConvManyPerClick);
  }

  public void setValuePerConvManyPerClick(BigDecimal valuePerConvManyPerClick) {
    this.valuePerConvManyPerClick = valuePerConvManyPerClick;
  }

  public String getViewThroughConversions() {
    return viewThroughConversions;
  }

  public void setViewThroughConversions(String viewThroughConversions) {
    this.viewThroughConversions = viewThroughConversions;
  }

  public String getViewThroughConversionsSignificance() {
    return viewThroughConversionsSignificance;
  }

  public void setViewThroughConversionsSignificance(
      String viewThroughConversionsSignificance) {
    this.viewThroughConversionsSignificance = viewThroughConversionsSignificance;
  }

  public String getWeek() {
    return week;
  }

  public void setWeek(String week) {
    this.week = week;
  }

  public Long getYear() {
    return year;
  }

  public void setYear(Long year) {
    this.year = year;
  }
}
