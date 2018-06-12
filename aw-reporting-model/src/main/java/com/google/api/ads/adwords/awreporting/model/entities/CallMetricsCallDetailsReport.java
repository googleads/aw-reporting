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
import com.google.api.ads.adwords.lib.jaxb.v201806.ReportDefinitionReportType;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
   * Specific report class for CallMetricsCallDetailsReport.
 *
*/
@Entity
@Table(name = "AW_CallMetricsCallDetailsReport")
@CsvReport(value = ReportDefinitionReportType.CALL_METRICS_CALL_DETAILS_REPORT)
public class CallMetricsCallDetailsReport extends DateReport {

  @Column(name = "AccountCurrencyCode")
  @CsvField(value = "Currency", reportField = "AccountCurrencyCode")
  private String accountCurrencyCode;

  @Column(name = "AccountDescriptiveName")
  @CsvField(value = "Account", reportField = "AccountDescriptiveName")
  private String accountDescriptiveName;

  @Column(name = "AccountTimeZone")
  @CsvField(value = "Time zone", reportField = "AccountTimeZone")
  private String accountTimeZone;

  @Column(name = "AdGroupId")
  @CsvField(value = "Ad group ID", reportField = "AdGroupId")
  private Long adGroupId;

  @Column(name = "AdGroupName")
  @CsvField(value = "Ad group", reportField = "AdGroupName")
  private String adGroupName;

  @Column(name = "AdGroupStatus")
  @CsvField(value = "Ad group state", reportField = "AdGroupStatus")
  private String adGroupStatus;

  @Column(name = "CallDuration")
  @CsvField(value = "Duration (seconds)", reportField = "CallDuration")
  private Long callDuration;

  @Column(name = "CallEndTime")
  @CsvField(value = "End time", reportField = "CallEndTime")
  private Long callEndTime;

  @Column(name = "CallerCountryCallingCode")
  @CsvField(value = "Caller country code", reportField = "CallerCountryCallingCode")
  private String callerCountryCallingCode;

  @Column(name = "CallerNationalDesignatedCode")
  @CsvField(value = "Caller area code", reportField = "CallerNationalDesignatedCode")
  private String callerNationalDesignatedCode;

  @Column(name = "CallStartTime")
  @CsvField(value = "Start time", reportField = "CallStartTime")
  private Long callStartTime;

  @Column(name = "CallStatus")
  @CsvField(value = "Status", reportField = "CallStatus")
  private String callStatus;

  @Column(name = "CallTrackingDisplayLocation")
  @CsvField(value = "Call source", reportField = "CallTrackingDisplayLocation")
  private String callTrackingDisplayLocation;

  @Column(name = "CallType")
  @CsvField(value = "Call type", reportField = "CallType")
  private String callType;

  @Column(name = "CampaignId")
  @CsvField(value = "Campaign ID", reportField = "CampaignId")
  private Long campaignId;

  @Column(name = "CampaignName")
  @CsvField(value = "Campaign", reportField = "CampaignName")
  private String campaignName;

  @Column(name = "CampaignStatus")
  @CsvField(value = "Campaign state", reportField = "CampaignStatus")
  private String campaignStatus;

  @Column(name = "CustomerDescriptiveName")
  @CsvField(value = "Client name", reportField = "CustomerDescriptiveName")
  private String customerDescriptiveName;

  /**
   * Hibernate needs an empty constructor
   */
  public CallMetricsCallDetailsReport() {
  }

  public CallMetricsCallDetailsReport(Long topAccountId, Long accountId){
    super(topAccountId, accountId);
  }

  public String getAccountCurrencyCode() {
    return accountCurrencyCode;
  }

  public void setAccountCurrencyCode(String accountCurrencyCode) {
    this.accountCurrencyCode = accountCurrencyCode;
  }

  public String getAccountDescriptiveName() {
    return accountDescriptiveName;
  }

  public void setAccountDescriptiveName(String accountDescriptiveName) {
    this.accountDescriptiveName = accountDescriptiveName;
  }

  public String getAccountTimeZone() {
    return accountTimeZone;
  }

  public void setAccountTimeZone(String accountTimeZone) {
    this.accountTimeZone = accountTimeZone;
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

  public Long getCallDuration() {
    return callDuration;
  }

  public void setCallDuration(Long callDuration) {
    this.callDuration = callDuration;
  }

  public Long getCallEndTime() {
    return callEndTime;
  }

  public void setCallEndTime(Long callEndTime) {
    this.callEndTime = callEndTime;
  }

  public String getCallerCountryCallingCode() {
    return callerCountryCallingCode;
  }

  public void setCallerCountryCallingCode(String callerCountryCallingCode) {
    this.callerCountryCallingCode = callerCountryCallingCode;
  }

  public String getCallerNationalDesignatedCode() {
    return callerNationalDesignatedCode;
  }

  public void setCallerNationalDesignatedCode(String callerNationalDesignatedCode) {
    this.callerNationalDesignatedCode = callerNationalDesignatedCode;
  }

  public Long getCallStartTime() {
    return callStartTime;
  }

  public void setCallStartTime(Long callStartTime) {
    this.callStartTime = callStartTime;
  }

  public String getCallStatus() {
    return callStatus;
  }

  public void setCallStatus(String callStatus) {
    this.callStatus = callStatus;
  }

  public String getCallTrackingDisplayLocation() {
    return callTrackingDisplayLocation;
  }

  public void setCallTrackingDisplayLocation(String callTrackingDisplayLocation) {
    this.callTrackingDisplayLocation = callTrackingDisplayLocation;
  }

  public String getCallType() {
    return callType;
  }

  public void setCallType(String callType) {
    this.callType = callType;
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

  public String getCustomerDescriptiveName() {
    return customerDescriptiveName;
  }

  public void setCustomerDescriptiveName(String customerDescriptiveName) {
    this.customerDescriptiveName = customerDescriptiveName;
  }

  @Override
  public void setRowId() {
    // General fields for generating unique id.
    StringBuilder idBuilder = new StringBuilder(getCustomerId().toString());
    if (campaignId != null) {
      idBuilder.append("-").append(campaignId);
    }
    if (adGroupId != null) {
      idBuilder.append("-").append(adGroupId);
    }
    if (callStartTime != null) {
      idBuilder.append("-").append(callStartTime);
    }
    if (callEndTime != null) {
      idBuilder.append("-").append(callEndTime);
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
    CallMetricsCallDetailsReport other = (CallMetricsCallDetailsReport) obj;
    return new EqualsBuilder()
      .appendSuper(super.equals(obj))
      .append(accountCurrencyCode, other.accountCurrencyCode)
      .append(accountDescriptiveName, other.accountDescriptiveName)
      .append(accountTimeZone, other.accountTimeZone)
      .append(adGroupId, other.adGroupId)
      .append(adGroupName, other.adGroupName)
      .append(adGroupStatus, other.adGroupStatus)
      .append(callDuration, other.callDuration)
      .append(callEndTime, other.callEndTime)
      .append(callerCountryCallingCode, other.callerCountryCallingCode)
      .append(callerNationalDesignatedCode, other.callerNationalDesignatedCode)
      .append(callStartTime, other.callStartTime)
      .append(callStatus, other.callStatus)
      .append(callTrackingDisplayLocation, other.callTrackingDisplayLocation)
      .append(callType, other.callType)
      .append(campaignId, other.campaignId)
      .append(campaignName, other.campaignName)
      .append(campaignStatus, other.campaignStatus)
      .append(customerDescriptiveName, other.customerDescriptiveName)
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .appendSuper(super.hashCode())
      .append(accountCurrencyCode)
      .append(accountDescriptiveName)
      .append(accountTimeZone)
      .append(adGroupId)
      .append(adGroupName)
      .append(adGroupStatus)
      .append(callDuration)
      .append(callEndTime)
      .append(callerCountryCallingCode)
      .append(callerNationalDesignatedCode)
      .append(callStartTime)
      .append(callStatus)
      .append(callTrackingDisplayLocation)
      .append(callType)
      .append(campaignId)
      .append(campaignName)
      .append(campaignStatus)
      .append(customerDescriptiveName)
      .toHashCode();
  }

}
