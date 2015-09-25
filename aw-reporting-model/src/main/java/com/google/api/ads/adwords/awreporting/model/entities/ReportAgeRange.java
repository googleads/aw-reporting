// Copyright 2011 Google Inc. All Rights Reserved.
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
import com.google.api.ads.adwords.lib.jaxb.v201506.ReportDefinitionReportType;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Specific report class for ReportAgeRange
 */
@Entity
@com.googlecode.objectify.annotation.Entity
@Table(name = "AW_ReportAgeRange")
@CsvReport(value = ReportDefinitionReportType.AGE_RANGE_PERFORMANCE_REPORT)
public class ReportAgeRange extends ReportBase {

  @Column(name = "ACTIVE_VIEW_CPM")
  @CsvField(value = "Active View avg. CPM", reportField = "ActiveViewCpm")
  @MoneyField
  private BigDecimal activeViewCpm;

  @Column(name = "ACTIVE_VIEW_IMPRESSIONS")
  @CsvField(value = "Active View avg. CPM", reportField = "ActiveViewImpressions")
  private Long activeViewImpressions;
  
  @Column(name = "ADGROUP_ID")
  @CsvField(value = "Ad group ID", reportField = "AdGroupId")
  private Long adGroupId;

  @Column(name = "ADGROUP_NAME", length = 255)
  @CsvField(value = "Ad group", reportField = "AdGroupName")
  private String adGroupName;

  @Column(name = "ADGROUP_STATUS", length = 32)
  @CsvField(value = "Ad group state", reportField = "AdGroupStatus")
  private String adGroupStatus;
  
  @Column(name = "BID_MODIFIER")
  @CsvField(value = "Bid adj.", reportField = "BidModifier")
  private BigDecimal bidModifier;
  
  @Column(name = "BID_TYPE", length = 32)
  @CsvField(value = "Conversion optimizer bid type", reportField = "BidType")
  private String bidType;
  
  @Column(name = "CAMPAIGN_ID")
  @CsvField(value = "Campaign ID", reportField = "CampaignId")
  private Long campaignId;

  @Column(name = "CAMPAIGN_NAME", length = 255)
  @CsvField(value = "Campaign", reportField = "CampaignName")
  private String campaignName;

  @Column(name = "CAMPAIGN_STATUS", length = 32)
  @CsvField(value = "Campaign state", reportField = "CampaignStatus")
  private String campaignStatus;
  
  @Column(name = "CONVERSION_TRACKER_ID")
  @CsvField(value = "Conversion Tracker Id", reportField = "ConversionTrackerId")
  private Long conversionTrackerId;
  
  @Column(name = "MAX_CPC")
  @CsvField(value = "Max. CPC", reportField = "CpcBid")
  @MoneyField
  private BigDecimal maxCpc;
  
  @Column(name = "CPC_BID_SOURCE")
  @CsvField(value = "Max CPC source", reportField = "CpcBidSource")
  private String cpcBidSource;
  
  @Column(name = "MAX_CPM")
  @CsvField(value = "Max. CPM", reportField = "CpmBid")
  @MoneyField
  private BigDecimal maxCpm;
  
  @Column(name = "CPM_BID_SOURCE")
  @CsvField(value = "Max CPM source", reportField = "CpmBidSource")
  private String cpmBidSource;
  
  @Column(name = "CRITERIA")
  @CsvField(value = "Age Range", reportField = "Criteria")
  private String criteria;

  @Column(name = "CRITERIA_DESTINATION_URL", length = 2048)
  @CsvField(value = "Destination URL", reportField = "CriteriaDestinationUrl")
  private String criteriaDestinationUrl;

  @Column(name = "FINAL_APP_URLS", length=2048)
  @CsvField(value="App final URL", reportField = "FinalAppUrls")
  private String finalAppUrls;
  
  @Column(name = "FINAL_MOBILE_URLS", length=2048)
  @CsvField(value="Mobile final URL", reportField = "FinalMobileUrls")
  private String finalMobileUrls;
  
  @Column(name = "FINAL_URLS", length=2048)
  @CsvField(value="Final URL", reportField = "FinalUrls")
  private String finalUrls;
  
  @Column(name = "GMAIL_FORWARDS")
  @CsvField(value = "Gmail forwards", reportField = "GmailForwards")
  private Long gmailForwards;

  @Column(name = "GMAIL_SAVES")
  @CsvField(value = "Gmail saves", reportField = "GmailSaves")
  private Long gmailSaves;

  @Column(name = "GMAIL_SECONDARY_CLICKS")
  @CsvField(value = "Gmail clicks to website", reportField = "GmailSecondaryClicks")
  private Long gmailSecondaryClicks;
  
  @Column(name = "CRITERION_ID")
  @CsvField(value = "Criterion ID", reportField = "Id")
  private Long criterionId;
  
  @Column(name = "IS_NEGATIVE")
  @CsvField(value = "Is negative", reportField = "IsNegative")
  private boolean negative;
    
  @Column(name = "IS_RESTRICT")
  @CsvField(value = "Is restricting", reportField = "IsRestrict")
  private boolean restrict;
 
  @Column(name = "STATUS", length = 32)
  @CsvField(value = "Age Range state", reportField = "Status")
  private String status;

  @Column(name = "TRACKING_URL_TEMPLATE", length=2048)
  @CsvField(value = "Tracking template", reportField = "TrackingUrlTemplate")
  private String trackingUrlTemplate;
  
  @Column(name = "URL_CUSTOM_PARAMETERS", length=2048)
  @CsvField(value = "Custom parameter", reportField = "UrlCustomParameters")
  private String urlCustomParameters;
  
  /**
   * Hibernate needs an empty constructor
   */
  public ReportAgeRange() {}

  public ReportAgeRange(Long topAccountId, Long accountId) {
    this.topAccountId = topAccountId;
    this.accountId = accountId;
  }

  @Override
  public void setId() {
    // Generating unique id after having accountId, campaignId, adGroupId
    // and date
    this.id = this.getAccountId() + "-" + this.getCampaignId() + "-"
        + this.getAdGroupId() + "-" + this.getCriteria();

    this.id += this.setIdDates();

    // Adding extra fields for unique ID
    this.id += getSegmentedId();
  }
  
  public String getActiveViewCpm() {
    return BigDecimalUtil.formatAsReadable(activeViewCpm);
  }

  public BigDecimal getActiveViewCpmBigDecimal() {
    return activeViewCpm;
  }

  public void setActiveViewCpm(String activeViewCpm) {
    this.activeViewCpm = BigDecimalUtil.parseFromNumberStringPercentage(activeViewCpm);
  }
  
  public Long getActiveViewImpressions() {
    return activeViewImpressions;
  }
  
  public void setActiveViewImpressions(Long activeViewImpressions) {
    this.activeViewImpressions = activeViewImpressions;
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

  public String getBidModifier() {
    return BigDecimalUtil.formatAsReadable(bidModifier);
  }
  
  public BigDecimal getBidModifierBigDecimal() {
    return bidModifier;
  }

  public void setBidModifier(String bidModifier) {
    this.bidModifier = BigDecimalUtil.parseFromNumberString(bidModifier);
  }

  public String getBidType() {
    return bidType;
  }

  public void setBidType(String bidType) {
    this.bidType = bidType;
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

  public Long getConversionTrackerId() {
    return conversionTrackerId;
  }

  public void setConversionTrackerId(Long conversionTrackerId) {
    this.conversionTrackerId = conversionTrackerId;
  }
  
  public BigDecimal getMaxCpc() {
    return maxCpc;
  }

  public void setMaxCpc(BigDecimal maxCpc) {
    this.maxCpc = maxCpc;
  }

  public String getCpcBidSource() {
    return cpcBidSource;
  }

  public void setCpcBidSource(String cpcBidSource) {
    this.cpcBidSource = cpcBidSource;
  }

  public BigDecimal getMaxCpm() {
    return maxCpm;
  }

  public void setMaxCpm(BigDecimal maxCpm) {
    this.maxCpm = maxCpm;
  }

  public String getCpmBidSource() {
    return cpmBidSource;
  }

  public void setCpmBidSource(String cpmBidSource) {
    this.cpmBidSource = cpmBidSource;
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

  public Long getCriterionId() {
    return criterionId;
  }

  public void setCriterionId(Long criterionId) {
    this.criterionId = criterionId;
  }

  public boolean isNegative() {
    return negative;
  }

  public void setNegative(boolean negative) {
    this.negative = negative;
  }

  public boolean isRestrict() {
    return restrict;
  }

  public void setRestrict(boolean restrict) {
    this.restrict = restrict;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
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

}
