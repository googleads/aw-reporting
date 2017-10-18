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
   * Specific report class for SharedSetCriteriaReport.
 *
*/
@Entity
@Table(name = "AW_SharedSetCriteriaReport")
@CsvReport(value = ReportDefinitionReportType.SHARED_SET_CRITERIA_REPORT)
public class SharedSetCriteriaReport extends Report {

  @Column(name = "AccountDescriptiveName")
  @CsvField(value = "Account", reportField = "AccountDescriptiveName")
  private String accountDescriptiveName;

  @Column(name = "Criteria")
  @CsvField(value = "Negative keyword", reportField = "Criteria")
  private String criteria;

  @Column(name = "Id")
  @CsvField(value = "Keyword ID", reportField = "Id")
  private Long id;

  @Column(name = "KeywordMatchType")
  @CsvField(value = "Match type", reportField = "KeywordMatchType")
  private String keywordMatchType;

  @Column(name = "SharedSetId")
  @CsvField(value = "Shared Set ID", reportField = "SharedSetId")
  private Long sharedSetId;

  /**
   * Hibernate needs an empty constructor
   */
  public SharedSetCriteriaReport() {
  }

  public SharedSetCriteriaReport(Long topAccountId, Long accountId){
    super(topAccountId, accountId);
  }

  public String getAccountDescriptiveName() {
    return accountDescriptiveName;
  }

  public void setAccountDescriptiveName(String accountDescriptiveName) {
    this.accountDescriptiveName = accountDescriptiveName;
  }

  public String getCriteria() {
    return criteria;
  }

  public void setCriteria(String criteria) {
    this.criteria = criteria;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getKeywordMatchType() {
    return keywordMatchType;
  }

  public void setKeywordMatchType(String keywordMatchType) {
    this.keywordMatchType = keywordMatchType;
  }

  public Long getSharedSetId() {
    return sharedSetId;
  }

  public void setSharedSetId(Long sharedSetId) {
    this.sharedSetId = sharedSetId;
  }

  @Override
  public void setRowId() {
    // General fields for generating unique id.
    StringBuilder idBuilder = new StringBuilder(getCustomerId().toString());
    if (id != null) {
      idBuilder.append("-").append(id);
    }
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
    SharedSetCriteriaReport other = (SharedSetCriteriaReport) obj;
    return new EqualsBuilder()
      .appendSuper(super.equals(obj))
      .append(accountDescriptiveName, other.accountDescriptiveName)
      .append(criteria, other.criteria)
      .append(id, other.id)
      .append(keywordMatchType, other.keywordMatchType)
      .append(sharedSetId, other.sharedSetId)
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .appendSuper(super.hashCode())
      .append(accountDescriptiveName)
      .append(criteria)
      .append(id)
      .append(keywordMatchType)
      .append(sharedSetId)
      .toHashCode();
  }

}
