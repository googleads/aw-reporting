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
import com.google.api.ads.adwords.awreporting.model.util.UrlHashUtil;
import com.google.api.ads.adwords.lib.jaxb.v201506.ReportDefinitionReportType;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Specific report class for ReportFinalUrl
 */
@Entity
@com.googlecode.objectify.annotation.Entity
@Table(name = "AW_ReportFinalUrl")
@CsvReport(value = ReportDefinitionReportType.FINAL_URL_REPORT)
public class ReportFinalUrl extends ReportBase {
  
  @Column(name = "ACTIVE_VIEW_CPM")
  @CsvField(value = "Active View avg. CPM", reportField = "ActiveViewCpm")
  @MoneyField
  private BigDecimal activeViewCpm;

  @Column(name = "ACTIVE_VIEW_IMPRESSIONS")
  @CsvField(value = "Active View viewable impressions", reportField = "ActiveViewImpressions")
  private Long activeViewImpressions;
  
  @Column(name = "AD_GROUP_ID")
  @CsvField(value = "Ad group ID", reportField = "AdGroupId")
  private Long adGroupId;
  
  @Column(name = "AD_GROUP_NAME")
  @CsvField(value = "Ad group", reportField = "AdGroupName")
  private String adGroupName;

  @Column(name = "AD_GROUP_STATUS")
  @CsvField(value = "Ad group state", reportField = "AdGroupStatus")
  private String adGroupStatus;
  
  @Column(name = "CAMPAIGN_ID")
  @CsvField(value = "Campaign ID", reportField = "CampaignId")
  private Long campaignId;
  
  @Column(name = "CAMPAIGN_NAME")
  @CsvField(value = "Campaign", reportField = "CampaignName")
  private String campaignName;

  @Column(name = "CAMPAIGN_STATUS")
  @CsvField(value = "Campaign state", reportField = "CampaignStatus")
  private String campaignStatus;

  @Column(name = "CONVERSION_TRACKER_ID")
  @CsvField(value = "Conversion Tracker Id", reportField = "ConversionTrackerId")
  private Long conversionTrackerId;
  
  @Column(name = "CRITERIA_PARAMETERS")
  @CsvField(value = "Keyword / Placement", reportField = "CriteriaParameters")
  private String criteriaParameters;

  @Column(name = "CRITERIA_TYPE_NAME")
  @CsvField(value = "Match type", reportField = "CriteriaTypeName")
  private String criteriaTypeName;
  
  @Column(name = "EFFECTIVE_FINAL_URL", length = 2048)
  @CsvField(value = "Final URL", reportField = "EffectiveFinalUrl")
  private String effectiveFinalUrl;

  @Column(name = "SLOT")
  @CsvField(value = "Top vs. Other", reportField = "Slot")
  private String slot;
  
  /**
   * Hibernate needs an empty constructor
   */
  public ReportFinalUrl() {
  }

  public ReportFinalUrl(Long topAccountId, Long accountId) {
    this.topAccountId = topAccountId;
    this.accountId = accountId;
  }

  @Override
  public void setId() {
    // Generating unique id after having accountId, campaignId, adGroupId and date
    this.id = "";
    if (this.getAccountId() != null) {
      this.id += this.getAccountId() + "-";
    }
    if (this.getCampaignId() != null) {
      this.id += this.getCampaignId() + "-";
    }
    if (this.getAdGroupId() != null) {
      this.id += this.getAdGroupId() + "-";
    }
    if (this.getCriteriaParameters() != null) { 
      this.id += this.getCriteriaParameters() + "-";
    }
 
    // Generating a SHA-1 Hash of the URLs for ID generation
    if (this.getEffectiveFinalUrl() != null) { 
      this.id += UrlHashUtil.createUrlHash(this.getEffectiveFinalUrl());
    }

    this.id += setIdDates();
    
    this.id += getSegmentedId();

  }
  
  public BigDecimal getActiveViewCpm() {
    return activeViewCpm;
  }
  
  public void setActiveViewCpm(BigDecimal activeViewCpm) {
    this.activeViewCpm = activeViewCpm;
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
  
  public String getCriteriaParameters() {
    return criteriaParameters;
  }

  public void setCriteriaParameters(String criteriaParameters) {
    this.criteriaParameters = criteriaParameters;
  }

  public String getCriteriaTypeName() {
    return criteriaTypeName;
  }

  public void setCriteriaTypeName(String criteriaTypeName) {
    this.criteriaTypeName = criteriaTypeName;
  }

  public String getEffectiveFinalUrl() {
    return effectiveFinalUrl;
  }

  public void setEffectiveFinalUrl(String effectiveFinalUrl) {
    this.effectiveFinalUrl = effectiveFinalUrl;
  }

  public String getSlot() {
    return slot;
  }

  public void setSlot(String slot) {
    this.slot = slot;
  }
  
}
