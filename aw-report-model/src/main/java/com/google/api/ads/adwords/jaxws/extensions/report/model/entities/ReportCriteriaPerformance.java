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
import com.google.api.ads.adwords.lib.jaxb.v201406.ReportDefinitionReportType;

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

  @Column(name = "PARAMETER")
  @CsvField(value = "Dynamic ad target", reportField = "Parameter")
  private String parameter;

  @Column(name = "PERCENT_CPA")
  @CsvField(value = "Max. CPA%", reportField = "PercentCpa")
  private String percentCpa;

  @Column(name = "POSITION_SIGNIFICANCE")
  @CsvField(value = "Position ACE indicator", reportField = "PositionSignificance")
  private String positionSignificance;

  @Column(name = "QUALITY_SCORE")
  @CsvField(value = "Quality score", reportField = "QualityScore")
  private Long qualityScore;

  @Column(name = "SLOT")
  @CsvField(value = "Top vs. side", reportField = "Slot")
  private String slot;

  @Column(name = "STATUS")
  @CsvField(value = "Keyword/Placement state", reportField = "Status")
  private String status;

  @Column(name = "TOP_OF_PAGE_CPC")
  @CsvField(value = "Top of page CPC", reportField = "TopOfPageCpc")
  private String topOfPageCpc;

  @Column(name = "VIEW_THROUGH_CONVERSIONS_SIGNIFICANCE")
  @CsvField(value = "View-through conv. ACE indicator",
  reportField = "ViewThroughConversionsSignificance")
  private String viewThroughConversionsSignificance;

  @Column(name = "CONVERSIONRATESIGNIFICANCE")
  @CsvField(value = "Click conversion rate ACE indicator", reportField = "ConversionRateSignificance")
  protected BigDecimal conversionRateSignificance;

  @Column(name = "CONVERSIONRATEMANYPERCLICKSIGNIFICANCE")
  @CsvField(value = "Conversion rate ACE indicator", reportField = "ConversionRateManyPerClickSignificance")
  protected BigDecimal conversionRateManyPerClickSignificance;
  
  @Column(name = "CONVERSIONMANYPERCLICKSIGNIFICANCE")
  @CsvField(value = "Conversion ACE indicator", reportField = "ConversionManyPerClickSignificance")
  protected BigDecimal conversionManyPerClickSignificance;

  @Column(name = "COSTPERCONVERSIONMANYPERCLICKSIGNIFICANCE")
  @CsvField(value = "Cost/conversion ACE indicator", reportField = "CostPerConversionManyPerClickSignificance")
  protected BigDecimal costPerConversionManyPerClickSignificance;
  
  @Column(name = "CONVERSIONSIGNIFICANCE")
  @CsvField(value = "Converted clicks ACE indicator", reportField = "ConversionSignificance")
  protected BigDecimal conversionSignificance;

  @Column(name = "COSTPERCONVERSIONSIGNIFICANCE")
  @CsvField(value = "Cost/converted click ACE indicator", reportField = "CostPerConversionSignificance")
  protected BigDecimal costPerConversionSignificance;

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

  public Long getQualityScore() {
    return qualityScore;
  }

  public void setQualityScore(Long qualityScore) {
    this.qualityScore = qualityScore;
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

  public String getViewThroughConversionsSignificance() {
    return viewThroughConversionsSignificance;
  }

  public void setViewThroughConversionsSignificance(
      String viewThroughConversionsSignificance) {
    this.viewThroughConversionsSignificance = viewThroughConversionsSignificance;
  }
  
  public String getConversionRateSignificance() {
    return BigDecimalUtil.formatAsReadable(conversionRateSignificance);
  }
  
  public BigDecimal getConversionRateSignificanceBigDecimal() {
    return conversionRateSignificance;
  }

  public void setConversionRateSignificance(String conversionRateSignificance) {
    this.conversionRateSignificance = BigDecimalUtil.parseFromNumberString(conversionRateSignificance);
  }

  public String getConversionRateManyPerClickSignificance() {
    return BigDecimalUtil.formatAsReadable(conversionRateManyPerClickSignificance);
  }
  
  public BigDecimal getConversionRateManyPerClickSignificanceBigDecimal() {
    return conversionRateManyPerClickSignificance;
  }

  public void setConversionRateManyPerClickSignificance(
      String conversionRateManyPerClickSignificance) {
    this.conversionRateManyPerClickSignificance = BigDecimalUtil.parseFromNumberString(conversionRateManyPerClickSignificance);
  }
  
  public String getConversionManyPerClickSignificance() {
    return BigDecimalUtil.formatAsReadable(conversionManyPerClickSignificance);
  }
  
  public BigDecimal getConversionManyPerClickSignificanceBigDecimal() {
    return conversionManyPerClickSignificance;
  }

  public void setConversionManyPerClickSignificance(String conversionManyPerClickSignificance) {
    this.conversionManyPerClickSignificance = BigDecimalUtil.parseFromNumberString(conversionManyPerClickSignificance);
  }
  
  public String getCostPerConversionManyPerClickSignificance() {
    return BigDecimalUtil.formatAsReadable(costPerConversionManyPerClickSignificance);
  }
  
  public BigDecimal getCostPerConversionManyPerClickSignificanceBigDecimal() {
    return costPerConversionManyPerClickSignificance;
  }

  public void setCostPerConversionManyPerClickSignificance(
      BigDecimal costPerConversionManyPerClickSignificance) {
    this.costPerConversionManyPerClickSignificance = costPerConversionManyPerClickSignificance;
  }

  public String getConversionSignificance() {
    return BigDecimalUtil.formatAsReadable(conversionSignificance);
  }
  
  public BigDecimal getConversionSignificanceBigDecimal() {
    return conversionSignificance;
  }

  public void setConversionSignificance(String conversionSignificance) {
    this.conversionSignificance = BigDecimalUtil.parseFromNumberString(conversionSignificance);
  }
  
  public String getCostPerConversionSignificance() {
    return BigDecimalUtil.formatAsReadable(costPerConversionSignificance);
  }
  
  public BigDecimal getCostPerConversionSignificanceBigDecimal() {
    return costPerConversionSignificance;
  }

  public void setCostPerConversionSignificance(String costPerConversionSignificance) {
    this.costPerConversionSignificance = BigDecimalUtil.parseFromNumberString(costPerConversionSignificance);
  }
}
