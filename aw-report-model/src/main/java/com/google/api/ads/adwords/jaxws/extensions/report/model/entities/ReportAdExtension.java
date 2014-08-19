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

package com.google.api.ads.adwords.jaxws.extensions.report.model.entities;

import com.google.api.ads.adwords.jaxws.extensions.report.model.csv.annotation.CsvField;
import com.google.api.ads.adwords.jaxws.extensions.report.model.csv.annotation.CsvReport;
import com.google.api.ads.adwords.lib.jaxb.v201406.ReportDefinitionReportType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Specific report class for ReportAccount
 *
 * @author jtoledo@google.com (Julian Toledo)
 * @author gustavomoreira@google.com (Gustavo Moreira)
 */
@Entity
@com.googlecode.objectify.annotation.Entity
@Table(name = "AW_ReportAdExtension")
@CsvReport(value = ReportDefinitionReportType.AD_EXTENSIONS_PERFORMANCE_REPORT,
    reportExclusions = {"ExternalCustomerId", "AccountDescriptiveName", "AccountCurrencyCode",
    "AccountTimeZoneId", "CustomerDescriptiveName", "PrimaryCompanyName"})
public class ReportAdExtension extends ReportBase {

  @Column(name = "CAMPAIGN_ID")
  @CsvField(value = "Campaign ID", reportField = "CampaignId")
  private Long campaignId;

  @Column(name = "AD_EXTENSION_ID")
  @CsvField(value = "Ad Extension ID", reportField = "AdExtensionId")
  private Long adExtensionId;

  @Column(name = "AD_EXTENSION_TYPE", length = 64)
  @CsvField(value = "Ad Extension Type", reportField = "AdExtensionType")
  private String adExtensionType;

  @Column(name = "AD_EXTENSION_STATUS", length = 32)
  @CsvField(value = "State", reportField = "Status")
  private String status;

  @Column(name = "AD_EXTENSION_APPROVAL_STATUS", length = 32)
  @CsvField(value = "Status", reportField = "ApprovalStatus")
  private String approvalStatus;

  /**
   * Hibernate needs an empty constructor
   */
  public ReportAdExtension() {
  }

  public ReportAdExtension(Long topAccountId, Long accountId) {
    this.topAccountId = topAccountId;
    this.accountId = accountId;
  }

  @Override
  public void setId() {
    // Generating unique id after having accountId, campaignId, adGroupId and date
    if (this.getCampaignId() != null && this.getAdExtensionId() != null) {
      this.id = this.getCampaignId() + "-" + this.getAdExtensionId();
    }

    this.id += setIdDates();

    if (this.getAdExtensionType() != null && this.getAdExtensionType().length() > 0) {
      this.id += "-" + this.getAdExtensionType();
    }

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

  // campaignId
  public Long getCampaignId() {
    return campaignId;
  }

  public void setCampaignId(Long campaignId) {
    this.campaignId = campaignId;
  }

  // status
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  // adExtensionId
  public Long getAdExtensionId() {
    return adExtensionId;
  }

  public void setAdExtensionId(Long adExtensionId) {
    this.adExtensionId = adExtensionId;
  }

  // adExtensionType
  public String getAdExtensionType() {
    return adExtensionType;
  }

  public void setAdExtensionType(String adExtensionType) {
    this.adExtensionType = adExtensionType;
  }

  // approvalStatus
  public String getApprovalStatus() {
    return approvalStatus;
  }

  public void setApprovalStatus(String approvalStatus) {
    this.approvalStatus = approvalStatus;
  }
}
