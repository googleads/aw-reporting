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
   * Specific report class for CampaignSharedSetReport.
 *
*/
@Entity
@Table(name = "AW_CampaignSharedSetReport")
@CsvReport(value = ReportDefinitionReportType.CAMPAIGN_SHARED_SET_REPORT)
public class CampaignSharedSetReport extends Report {

  @Column(name = "AccountDescriptiveName")
  @CsvField(value = "Account", reportField = "AccountDescriptiveName")
  private String accountDescriptiveName;

  @Column(name = "CampaignId")
  @CsvField(value = "Campaign ID", reportField = "CampaignId")
  private Long campaignId;

  @Column(name = "CampaignName")
  @CsvField(value = "Campaign", reportField = "CampaignName")
  private String campaignName;

  @Column(name = "CampaignStatus")
  @CsvField(value = "Campaign state", reportField = "CampaignStatus")
  private String campaignStatus;

  @Column(name = "SharedSetId")
  @CsvField(value = "Shared Set ID", reportField = "SharedSetId")
  private Long sharedSetId;

  @Column(name = "SharedSetName")
  @CsvField(value = "Shared Set Name", reportField = "SharedSetName")
  private String sharedSetName;

  @Column(name = "SharedSetType")
  @CsvField(value = "Shared Set Type", reportField = "SharedSetType")
  private String sharedSetType;

  @Column(name = "Status")
  @CsvField(value = "State", reportField = "Status")
  private String status;

  /**
   * Hibernate needs an empty constructor
   */
  public CampaignSharedSetReport() {
  }

  public CampaignSharedSetReport(Long topAccountId, Long accountId){
    super(topAccountId, accountId);
  }

  public String getAccountDescriptiveName() {
    return accountDescriptiveName;
  }

  public void setAccountDescriptiveName(String accountDescriptiveName) {
    this.accountDescriptiveName = accountDescriptiveName;
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

  public Long getSharedSetId() {
    return sharedSetId;
  }

  public void setSharedSetId(Long sharedSetId) {
    this.sharedSetId = sharedSetId;
  }

  public String getSharedSetName() {
    return sharedSetName;
  }

  public void setSharedSetName(String sharedSetName) {
    this.sharedSetName = sharedSetName;
  }

  public String getSharedSetType() {
    return sharedSetType;
  }

  public void setSharedSetType(String sharedSetType) {
    this.sharedSetType = sharedSetType;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public void setRowId() {
    // General fields for generating unique id.
    StringBuilder idBuilder = new StringBuilder(getCustomerId().toString());
    if (campaignId != null) {
      idBuilder.append("-").append(campaignId);
    }
    if (!StringUtils.isEmpty(sharedSetName)) {
      idBuilder.append("-").append(sharedSetName);
    }

    // Include all segmentation fields (if set).
    this.rowId = idBuilder.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) { return false; }
    if (obj == this) { return true; }
    if (obj.getClass() != getClass()) { return false; }
    CampaignSharedSetReport other = (CampaignSharedSetReport) obj;
    return new EqualsBuilder()
      .appendSuper(super.equals(obj))
      .append(accountDescriptiveName, other.accountDescriptiveName)
      .append(campaignId, other.campaignId)
      .append(campaignName, other.campaignName)
      .append(campaignStatus, other.campaignStatus)
      .append(sharedSetId, other.sharedSetId)
      .append(sharedSetName, other.sharedSetName)
      .append(sharedSetType, other.sharedSetType)
      .append(status, other.status)
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .appendSuper(super.hashCode())
      .append(accountDescriptiveName)
      .append(campaignId)
      .append(campaignName)
      .append(campaignStatus)
      .append(sharedSetId)
      .append(sharedSetName)
      .append(sharedSetType)
      .append(status)
      .toHashCode();
  }

}
