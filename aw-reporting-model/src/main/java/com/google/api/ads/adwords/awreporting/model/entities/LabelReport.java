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


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
   * Specific report class for LabelReport.
 *
*/
@Entity
@Table(name = "AW_LabelReport")
@CsvReport(value = ReportDefinitionReportType.LABEL_REPORT)
public class LabelReport extends Report {

  @Column(name = "AccountDescriptiveName")
  @CsvField(value = "Account", reportField = "AccountDescriptiveName")
  private String accountDescriptiveName;

  @Column(name = "AdGroupCreativesCount")
  @CsvField(value = "Ad group creatives count", reportField = "AdGroupCreativesCount")
  private Long adGroupCreativesCount;

  @Column(name = "AdGroupCriteriaCount")
  @CsvField(value = "Ad group criteria count", reportField = "AdGroupCriteriaCount")
  private Long adGroupCriteriaCount;

  @Column(name = "AdGroupsCount")
  @CsvField(value = "Ad groups count", reportField = "AdGroupsCount")
  private Long adGroupsCount;

  @Column(name = "CampaignsCount")
  @CsvField(value = "Campaigns count", reportField = "CampaignsCount")
  private Long campaignsCount;

  @Column(name = "LabelId")
  @CsvField(value = "Label ID", reportField = "LabelId")
  private Long labelId;

  @Column(name = "LabelName")
  @CsvField(value = "Label name", reportField = "LabelName")
  private String labelName;

  @Column(name = "UserListsCount")
  @CsvField(value = "User lists count", reportField = "UserListsCount")
  private Long userListsCount;

  /**
   * Hibernate needs an empty constructor
   */
  public LabelReport() {
  }

  public LabelReport(Long topAccountId, Long accountId){
    super(topAccountId, accountId);
  }

  public String getAccountDescriptiveName() {
    return accountDescriptiveName;
  }

  public void setAccountDescriptiveName(String accountDescriptiveName) {
    this.accountDescriptiveName = accountDescriptiveName;
  }

  public Long getAdGroupCreativesCount() {
    return adGroupCreativesCount;
  }

  public void setAdGroupCreativesCount(Long adGroupCreativesCount) {
    this.adGroupCreativesCount = adGroupCreativesCount;
  }

  public Long getAdGroupCriteriaCount() {
    return adGroupCriteriaCount;
  }

  public void setAdGroupCriteriaCount(Long adGroupCriteriaCount) {
    this.adGroupCriteriaCount = adGroupCriteriaCount;
  }

  public Long getAdGroupsCount() {
    return adGroupsCount;
  }

  public void setAdGroupsCount(Long adGroupsCount) {
    this.adGroupsCount = adGroupsCount;
  }

  public Long getCampaignsCount() {
    return campaignsCount;
  }

  public void setCampaignsCount(Long campaignsCount) {
    this.campaignsCount = campaignsCount;
  }

  public Long getLabelId() {
    return labelId;
  }

  public void setLabelId(Long labelId) {
    this.labelId = labelId;
  }

  public String getLabelName() {
    return labelName;
  }

  public void setLabelName(String labelName) {
    this.labelName = labelName;
  }

  public Long getUserListsCount() {
    return userListsCount;
  }

  public void setUserListsCount(Long userListsCount) {
    this.userListsCount = userListsCount;
  }

  @Override
  public void setRowId() {
    // General fields for generating unique id.
    StringBuilder idBuilder = new StringBuilder(getCustomerId().toString());
    if (labelId != null) {
      idBuilder.append("-").append(labelId);
    }

    // Include all segmentation fields (if set).
    this.rowId = idBuilder.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) { return false; }
    if (obj == this) { return true; }
    if (obj.getClass() != getClass()) { return false; }
    LabelReport other = (LabelReport) obj;
    return new EqualsBuilder()
      .appendSuper(super.equals(obj))
      .append(accountDescriptiveName, other.accountDescriptiveName)
      .append(adGroupCreativesCount, other.adGroupCreativesCount)
      .append(adGroupCriteriaCount, other.adGroupCriteriaCount)
      .append(adGroupsCount, other.adGroupsCount)
      .append(campaignsCount, other.campaignsCount)
      .append(labelId, other.labelId)
      .append(labelName, other.labelName)
      .append(userListsCount, other.userListsCount)
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .appendSuper(super.hashCode())
      .append(accountDescriptiveName)
      .append(adGroupCreativesCount)
      .append(adGroupCriteriaCount)
      .append(adGroupsCount)
      .append(campaignsCount)
      .append(labelId)
      .append(labelName)
      .append(userListsCount)
      .toHashCode();
  }

}
