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
import com.google.api.ads.adwords.lib.jaxb.v201809.ReportDefinitionReportType;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
   * Specific report class for ClickPerformanceReport.
 *
*/
@Entity
@Table(name = "AW_ClickPerformanceReport")
@CsvReport(value = ReportDefinitionReportType.CLICK_PERFORMANCE_REPORT)
public class ClickPerformanceReport extends DateReport {

  @Column(name = "Page")
  @CsvField(value = "Page", reportField = "Page")
  private Integer page;

  @Column(name = "AccountDescriptiveName")
  @CsvField(value = "Account", reportField = "AccountDescriptiveName")
  private String accountDescriptiveName;

  @Column(name = "AdFormat")
  @CsvField(value = "Ad type", reportField = "AdFormat")
  private String adFormat;

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

  @Column(name = "AdVariationControlTrialArmId")
  @CsvField(value = "Ad Variation Control Trial Arm ID", reportField = "AdVariationControlTrialArmId")
  private Long adVariationControlTrialArmId;

  @Column(name = "AdVariationTreatmentTrialArmId")
  @CsvField(value = "Ad Variation Treatment Trial Arm ID", reportField = "AdVariationTreatmentTrialArmId")
  private Long adVariationTreatmentTrialArmId;

  @Column(name = "AdVariationTrialId")
  @CsvField(value = "Ad Variation Trial ID", reportField = "AdVariationTrialId")
  private Long adVariationTrialId;

  @Column(name = "AoiCityCriteriaId")
  @CsvField(value = "City (Location of interest)", reportField = "AoiCityCriteriaId")
  private Integer aoiCityCriteriaId;

  @Column(name = "AoiCountryCriteriaId")
  @CsvField(value = "Country/Territory (Location of interest)", reportField = "AoiCountryCriteriaId")
  private Integer aoiCountryCriteriaId;

  @Column(name = "AoiMetroCriteriaId")
  @CsvField(value = "Metro area (Location of interest)", reportField = "AoiMetroCriteriaId")
  private Integer aoiMetroCriteriaId;

  @Column(name = "AoiMostSpecificTargetId")
  @CsvField(value = "Most specific location target (Location of interest)", reportField = "AoiMostSpecificTargetId")
  private Long aoiMostSpecificTargetId;

  @Column(name = "AoiRegionCriteriaId")
  @CsvField(value = "Region (Location of interest)", reportField = "AoiRegionCriteriaId")
  private Integer aoiRegionCriteriaId;

  @Column(name = "CampaignId")
  @CsvField(value = "Campaign ID", reportField = "CampaignId")
  private Long campaignId;

  @Column(name = "CampaignLocationTargetId")
  @CsvField(value = "Campaign location target", reportField = "CampaignLocationTargetId")
  private Long campaignLocationTargetId;

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

  @Column(name = "CreativeId")
  @CsvField(value = "Ad ID", reportField = "CreativeId")
  private Long creativeId;

  @Column(name = "CriteriaId")
  @CsvField(value = "Keyword ID", reportField = "CriteriaId")
  private Long criteriaId;

  @Column(name = "CriteriaParameters")
  @CsvField(value = "Keyword / Placement", reportField = "CriteriaParameters")
  private String criteriaParameters;

  @Column(name = "Device")
  @CsvField(value = "Device", reportField = "Device")
  private String device;

  @Column(name = "GclId")
  @CsvField(value = "Google Click ID", reportField = "GclId")
  private String gclId;

  @Column(name = "KeywordMatchType")
  @CsvField(value = "Match type", reportField = "KeywordMatchType")
  private String keywordMatchType;

  @Column(name = "LopCityCriteriaId")
  @CsvField(value = "City (Physical location)", reportField = "LopCityCriteriaId")
  private Integer lopCityCriteriaId;

  @Column(name = "LopCountryCriteriaId")
  @CsvField(value = "Country/Territory (Physical location)", reportField = "LopCountryCriteriaId")
  private Integer lopCountryCriteriaId;

  @Column(name = "LopMetroCriteriaId")
  @CsvField(value = "Metro area (Physical location)", reportField = "LopMetroCriteriaId")
  private Integer lopMetroCriteriaId;

  @Column(name = "LopMostSpecificTargetId")
  @CsvField(value = "Most specific location target (Physical location)", reportField = "LopMostSpecificTargetId")
  private Long lopMostSpecificTargetId;

  @Column(name = "LopRegionCriteriaId")
  @CsvField(value = "Region (Physical location)", reportField = "LopRegionCriteriaId")
  private Integer lopRegionCriteriaId;

  @Column(name = "Slot")
  @CsvField(value = "Top vs. Other", reportField = "Slot")
  private String slot;

  @Column(name = "UserListId")
  @CsvField(value = "User List ID", reportField = "UserListId")
  private Long userListId;

  /**
   * Hibernate needs an empty constructor
   */
  public ClickPerformanceReport() {
  }

  public ClickPerformanceReport(Long topAccountId, Long accountId){
    super(topAccountId, accountId);
  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public String getAccountDescriptiveName() {
    return accountDescriptiveName;
  }

  public void setAccountDescriptiveName(String accountDescriptiveName) {
    this.accountDescriptiveName = accountDescriptiveName;
  }

  public String getAdFormat() {
    return adFormat;
  }

  public void setAdFormat(String adFormat) {
    this.adFormat = adFormat;
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

  public Long getAdVariationControlTrialArmId() {
    return adVariationControlTrialArmId;
  }

  public void setAdVariationControlTrialArmId(Long adVariationControlTrialArmId) {
    this.adVariationControlTrialArmId = adVariationControlTrialArmId;
  }

  public Long getAdVariationTreatmentTrialArmId() {
    return adVariationTreatmentTrialArmId;
  }

  public void setAdVariationTreatmentTrialArmId(Long adVariationTreatmentTrialArmId) {
    this.adVariationTreatmentTrialArmId = adVariationTreatmentTrialArmId;
  }

  public Long getAdVariationTrialId() {
    return adVariationTrialId;
  }

  public void setAdVariationTrialId(Long adVariationTrialId) {
    this.adVariationTrialId = adVariationTrialId;
  }

  public Integer getAoiCityCriteriaId() {
    return aoiCityCriteriaId;
  }

  public void setAoiCityCriteriaId(Integer aoiCityCriteriaId) {
    this.aoiCityCriteriaId = aoiCityCriteriaId;
  }

  public Integer getAoiCountryCriteriaId() {
    return aoiCountryCriteriaId;
  }

  public void setAoiCountryCriteriaId(Integer aoiCountryCriteriaId) {
    this.aoiCountryCriteriaId = aoiCountryCriteriaId;
  }

  public Integer getAoiMetroCriteriaId() {
    return aoiMetroCriteriaId;
  }

  public void setAoiMetroCriteriaId(Integer aoiMetroCriteriaId) {
    this.aoiMetroCriteriaId = aoiMetroCriteriaId;
  }

  public Long getAoiMostSpecificTargetId() {
    return aoiMostSpecificTargetId;
  }

  public void setAoiMostSpecificTargetId(Long aoiMostSpecificTargetId) {
    this.aoiMostSpecificTargetId = aoiMostSpecificTargetId;
  }

  public Integer getAoiRegionCriteriaId() {
    return aoiRegionCriteriaId;
  }

  public void setAoiRegionCriteriaId(Integer aoiRegionCriteriaId) {
    this.aoiRegionCriteriaId = aoiRegionCriteriaId;
  }

  public Long getCampaignId() {
    return campaignId;
  }

  public void setCampaignId(Long campaignId) {
    this.campaignId = campaignId;
  }

  public Long getCampaignLocationTargetId() {
    return campaignLocationTargetId;
  }

  public void setCampaignLocationTargetId(Long campaignLocationTargetId) {
    this.campaignLocationTargetId = campaignLocationTargetId;
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

  public Long getCreativeId() {
    return creativeId;
  }

  public void setCreativeId(Long creativeId) {
    this.creativeId = creativeId;
  }

  public Long getCriteriaId() {
    return criteriaId;
  }

  public void setCriteriaId(Long criteriaId) {
    this.criteriaId = criteriaId;
  }

  public String getCriteriaParameters() {
    return criteriaParameters;
  }

  public void setCriteriaParameters(String criteriaParameters) {
    this.criteriaParameters = criteriaParameters;
  }

  public String getDevice() {
    return device;
  }

  public void setDevice(String device) {
    this.device = device;
  }

  public String getGclId() {
    return gclId;
  }

  public void setGclId(String gclId) {
    this.gclId = gclId;
  }

  public String getKeywordMatchType() {
    return keywordMatchType;
  }

  public void setKeywordMatchType(String keywordMatchType) {
    this.keywordMatchType = keywordMatchType;
  }

  public Integer getLopCityCriteriaId() {
    return lopCityCriteriaId;
  }

  public void setLopCityCriteriaId(Integer lopCityCriteriaId) {
    this.lopCityCriteriaId = lopCityCriteriaId;
  }

  public Integer getLopCountryCriteriaId() {
    return lopCountryCriteriaId;
  }

  public void setLopCountryCriteriaId(Integer lopCountryCriteriaId) {
    this.lopCountryCriteriaId = lopCountryCriteriaId;
  }

  public Integer getLopMetroCriteriaId() {
    return lopMetroCriteriaId;
  }

  public void setLopMetroCriteriaId(Integer lopMetroCriteriaId) {
    this.lopMetroCriteriaId = lopMetroCriteriaId;
  }

  public Long getLopMostSpecificTargetId() {
    return lopMostSpecificTargetId;
  }

  public void setLopMostSpecificTargetId(Long lopMostSpecificTargetId) {
    this.lopMostSpecificTargetId = lopMostSpecificTargetId;
  }

  public Integer getLopRegionCriteriaId() {
    return lopRegionCriteriaId;
  }

  public void setLopRegionCriteriaId(Integer lopRegionCriteriaId) {
    this.lopRegionCriteriaId = lopRegionCriteriaId;
  }

  public String getSlot() {
    return slot;
  }

  public void setSlot(String slot) {
    this.slot = slot;
  }

  public Long getUserListId() {
    return userListId;
  }

  public void setUserListId(Long userListId) {
    this.userListId = userListId;
  }

  @Override
  public void setRowId() {
    // General fields for generating unique id.
    StringBuilder idBuilder = new StringBuilder(getCustomerId().toString());
    if (!StringUtils.isEmpty(gclId)) {
      idBuilder.append("-").append(gclId);
    }
    idBuilder.append("-").append(getDateLabel());

    // Include all segmentation fields (if set).
    this.rowId = idBuilder.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) { return false; }
    if (obj == this) { return true; }
    if (obj.getClass() != getClass()) { return false; }
    ClickPerformanceReport other = (ClickPerformanceReport) obj;
    return new EqualsBuilder()
      .appendSuper(super.equals(obj))
      .append(page, other.page)
      .append(accountDescriptiveName, other.accountDescriptiveName)
      .append(adFormat, other.adFormat)
      .append(adGroupId, other.adGroupId)
      .append(adGroupName, other.adGroupName)
      .append(adGroupStatus, other.adGroupStatus)
      .append(adNetworkType1, other.adNetworkType1)
      .append(adNetworkType2, other.adNetworkType2)
      .append(adVariationControlTrialArmId, other.adVariationControlTrialArmId)
      .append(adVariationTreatmentTrialArmId, other.adVariationTreatmentTrialArmId)
      .append(adVariationTrialId, other.adVariationTrialId)
      .append(aoiCityCriteriaId, other.aoiCityCriteriaId)
      .append(aoiCountryCriteriaId, other.aoiCountryCriteriaId)
      .append(aoiMetroCriteriaId, other.aoiMetroCriteriaId)
      .append(aoiMostSpecificTargetId, other.aoiMostSpecificTargetId)
      .append(aoiRegionCriteriaId, other.aoiRegionCriteriaId)
      .append(campaignId, other.campaignId)
      .append(campaignLocationTargetId, other.campaignLocationTargetId)
      .append(campaignName, other.campaignName)
      .append(campaignStatus, other.campaignStatus)
      .append(clicks, other.clicks)
      .append(clickType, other.clickType)
      .append(creativeId, other.creativeId)
      .append(criteriaId, other.criteriaId)
      .append(criteriaParameters, other.criteriaParameters)
      .append(device, other.device)
      .append(gclId, other.gclId)
      .append(keywordMatchType, other.keywordMatchType)
      .append(lopCityCriteriaId, other.lopCityCriteriaId)
      .append(lopCountryCriteriaId, other.lopCountryCriteriaId)
      .append(lopMetroCriteriaId, other.lopMetroCriteriaId)
      .append(lopMostSpecificTargetId, other.lopMostSpecificTargetId)
      .append(lopRegionCriteriaId, other.lopRegionCriteriaId)
      .append(slot, other.slot)
      .append(userListId, other.userListId)
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .appendSuper(super.hashCode())
      .append(page)
      .append(accountDescriptiveName)
      .append(adFormat)
      .append(adGroupId)
      .append(adGroupName)
      .append(adGroupStatus)
      .append(adNetworkType1)
      .append(adNetworkType2)
      .append(adVariationControlTrialArmId)
      .append(adVariationTreatmentTrialArmId)
      .append(adVariationTrialId)
      .append(aoiCityCriteriaId)
      .append(aoiCountryCriteriaId)
      .append(aoiMetroCriteriaId)
      .append(aoiMostSpecificTargetId)
      .append(aoiRegionCriteriaId)
      .append(campaignId)
      .append(campaignLocationTargetId)
      .append(campaignName)
      .append(campaignStatus)
      .append(clicks)
      .append(clickType)
      .append(creativeId)
      .append(criteriaId)
      .append(criteriaParameters)
      .append(device)
      .append(gclId)
      .append(keywordMatchType)
      .append(lopCityCriteriaId)
      .append(lopCountryCriteriaId)
      .append(lopMetroCriteriaId)
      .append(lopMostSpecificTargetId)
      .append(lopRegionCriteriaId)
      .append(slot)
      .append(userListId)
      .toHashCode();
  }

}
