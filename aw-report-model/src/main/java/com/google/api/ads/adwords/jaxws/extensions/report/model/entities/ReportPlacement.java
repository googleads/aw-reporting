// Copyright 2014 Google Inc. All Rights Reserved.
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.api.ads.adwords.jaxws.extensions.report.model.csv.annotation.CsvField;
import com.google.api.ads.adwords.jaxws.extensions.report.model.csv.annotation.CsvReport;
import com.google.api.ads.adwords.lib.jaxb.v201406.ReportDefinitionReportType;

/**
 * Specific Report class for GeoPerformanceReports
 *
 * @author markbowyer@google.com (Mark R. Bowyer)
 */
@Entity
@com.googlecode.objectify.annotation.Entity
@Table(name = "AW_ReportPlacementPerformance")
@CsvReport(value = ReportDefinitionReportType.PLACEMENT_PERFORMANCE_REPORT)
public class ReportPlacement extends ReportBase {

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
  private String bidModifier;
  
  @Column(name = "CAMPAIGN_ID")
  @CsvField(value = "Campaign ID", reportField = "CampaignId")
  private Long campaignId;

  @Column(name = "CAMPAIGN_NAME", length = 255)
  @CsvField(value = "Campaign", reportField = "CampaignName")
  private String campaignName;

  @Column(name = "CAMPAIGN_STATUS", length = 32)
  @CsvField(value = "Campaign state", reportField = "CampaignStatus")
  private String campaignStatus;

  @Column(name = "CPC_BID_SOURCE")
  @CsvField(value = "Max CPC source", reportField = "CpcBidSource")
  private String cpcBidSource;

  @Column(name = "CPM_BID_SOURCE")
  @CsvField(value = "Max CPM source", reportField = "CpmBidSource")
  private String cpmBidSource;

  @Column(name = "CRITERIA_DESTINATION_URL")
  @CsvField(value = "Destination URL", reportField = "CriteriaDestinationUrl")
  private String criteriaDestinationUrl;

  @Column(name = "DESTINATION_URL")
  @CsvField(value = "Destination URL", reportField = "DestinationUrl")
  private String destinationUrl;

  @Column(name = "DISPLAY_NAME", length = 2048)
  @CsvField(value = "Criteria Display Name", reportField = "DisplayName")
  private String displayName;

  @Column(name = "Id")
  @CsvField(value = "Criterion ID", reportField = "Id")
  private String criterionId;

  @Column(name = "IS_NEGATIVE")
  @CsvField(value = "Is negative", reportField = "IsNegative")
  private String isNegative;

  @Column(name = "MAX_CPC")
  @CsvField(value = "Max. CPC", reportField = "MaxCpc")
  private String maxCpc;

  @Column(name = "MAX_CPM")
  @CsvField(value = "Max. CPM", reportField = "MaxCpm")
  private String maxCpm;  

  @Column(name = "PLACEMENT_URL")
  @CsvField(value = "Placement", reportField = "PlacementUrl")
  private String placementUrl; 

  @Column(name = "TARGETING_SETTING")
  @CsvField(value = "Targeting Setting", reportField = "TargetingSetting")
  private String targetingSetting;

  @Column(name = "TOTAL_CONV_VALUE")
  @CsvField(value = "Total conv. value", reportField = "TotalConvValue")
  private Long totalConvValue;

  /**
   * Hibernate needs an empty constructor
   */
  public ReportPlacement() {}

  @Override
  public void setId() {

    // Generating unique id after having date and accountId
    if (this.getAccountId() != null) {
      this.id = this.getAccountId().toString();
    } else {
      this.id = "-";
    }
    if (this.getCampaignId() != null) {
      this.id += "-" + this.getCampaignId().toString();
    } else {
      this.id += "-";
    }
    if (this.getAdGroupId() != null) {
      this.id += "-" + this.getAdGroupId().toString();
    } else {
      this.id += "-";
    }
    if (this.getCriterionId() != null) {
      this.id += "-" + this.getCriterionId().toString();
    } else {
      this.id += "-";
    }

    this.id += setIdDates();

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
    if (this.getPlacementUrl() != null && this.getPlacementUrl().length() > 0) {
      this.id += "-" + this.getPlacementUrl();
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

  public String getCampaignStatus() {
    return campaignStatus;
  }

  public void setCampaignStatus(String campaignStatus) {
    this.campaignStatus = campaignStatus;
  }

  public String getCampaignName() {
    return campaignName;
  }

  public void setCampaignName(String campaignName) {
    this.campaignName = campaignName;
  }

  public Long getCampaignId() {
    return campaignId;
  }

  public void setCampaignId(Long campaignId) {
    this.campaignId = campaignId;
  }

  public String getCpcBidSource() {
    return cpcBidSource;
  }

  public void setCpcBidSource(String cpcBidSource) {
    this.cpcBidSource = cpcBidSource;
  }

  public String getCpmBidSource() {
    return cpmBidSource;
  }

  public void setCpmBidSource(String cpmBidSource) {
    this.cpmBidSource = cpmBidSource;
  }

  public String getCriteriaDestinationUrl() {
    return criteriaDestinationUrl;
  }

  public void setCriteriaDestinationUrl(String criteriaDestinationUrl) {
    this.criteriaDestinationUrl = criteriaDestinationUrl;
  }

  public String getDestinationUrl() {
    return destinationUrl;
  }

  public void setDestinationUrl(String destinationUrl) {
    this.destinationUrl = destinationUrl;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getCriterionId() {
    return criterionId;
  }

  public void setCriterionId(String criterionId) {
    this.criterionId = criterionId;
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

  public String getPlacementUrl() {
    return placementUrl;
  }

  public void setPlacementUrl(String placementUrl) {
    this.placementUrl = placementUrl;
  }

  public String getTargetingSetting() {
    return targetingSetting;
  }

  public void setTargetingSetting(String targetingSetting) {
    this.targetingSetting = targetingSetting;
  }

  public Long getTotalConvValue() {
    return totalConvValue;
  }

  public void setTotalConvValue(Long totalConvValue) {
    this.totalConvValue = totalConvValue;
  }
}
