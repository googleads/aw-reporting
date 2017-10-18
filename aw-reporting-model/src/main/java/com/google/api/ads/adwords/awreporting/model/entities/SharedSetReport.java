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
import com.google.api.ads.adwords.lib.jaxb.v201710.ReportDefinitionReportType;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
   * Specific report class for SharedSetReport.
 *
*/
@Entity
@Table(name = "AW_SharedSetReport")
@CsvReport(value = ReportDefinitionReportType.SHARED_SET_REPORT)
public class SharedSetReport extends Report {

  @Column(name = "AccountDescriptiveName")
  @CsvField(value = "Account", reportField = "AccountDescriptiveName")
  private String accountDescriptiveName;

  @Column(name = "MemberCount")
  @CsvField(value = "Member Count", reportField = "MemberCount")
  private String memberCount;

  @Column(name = "Name")
  @CsvField(value = "Shared Set Name", reportField = "Name")
  private String name;

  @Column(name = "ReferenceCount")
  @CsvField(value = "Reference Count", reportField = "ReferenceCount")
  private String referenceCount;

  @Column(name = "SharedSetId")
  @CsvField(value = "Shared Set ID", reportField = "SharedSetId")
  private Long sharedSetId;

  @Column(name = "Status")
  @CsvField(value = "State", reportField = "Status")
  private String status;

  @Column(name = "Type")
  @CsvField(value = "Shared Set Type", reportField = "Type")
  private String type;

  /**
   * Hibernate needs an empty constructor
   */
  public SharedSetReport() {
  }

  public SharedSetReport(Long topAccountId, Long accountId){
    super(topAccountId, accountId);
  }

  public String getAccountDescriptiveName() {
    return accountDescriptiveName;
  }

  public void setAccountDescriptiveName(String accountDescriptiveName) {
    this.accountDescriptiveName = accountDescriptiveName;
  }

  public String getMemberCount() {
    return memberCount;
  }

  public void setMemberCount(String memberCount) {
    this.memberCount = memberCount;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getReferenceCount() {
    return referenceCount;
  }

  public void setReferenceCount(String referenceCount) {
    this.referenceCount = referenceCount;
  }

  public Long getSharedSetId() {
    return sharedSetId;
  }

  public void setSharedSetId(Long sharedSetId) {
    this.sharedSetId = sharedSetId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public void setRowId() {
    // General fields for generating unique id.
    StringBuilder idBuilder = new StringBuilder(getCustomerId().toString());
    if (sharedSetId != null) {
      idBuilder.append("-").append(sharedSetId);
    }

    // Include all segmentation fields (if set).
    this.rowId = idBuilder.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) { return false; }
    if (obj == this) { return true; }
    if (obj.getClass() != getClass()) { return false; }
    SharedSetReport other = (SharedSetReport) obj;
    return new EqualsBuilder()
      .appendSuper(super.equals(obj))
      .append(accountDescriptiveName, other.accountDescriptiveName)
      .append(memberCount, other.memberCount)
      .append(name, other.name)
      .append(referenceCount, other.referenceCount)
      .append(sharedSetId, other.sharedSetId)
      .append(status, other.status)
      .append(type, other.type)
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .appendSuper(super.hashCode())
      .append(accountDescriptiveName)
      .append(memberCount)
      .append(name)
      .append(referenceCount)
      .append(sharedSetId)
      .append(status)
      .append(type)
      .toHashCode();
  }

}
