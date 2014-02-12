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
import com.google.api.ads.adwords.jaxws.extensions.report.model.util.BigDecimalUtil;
import com.google.api.ads.adwords.lib.jaxb.v201309.ReportDefinitionReportType;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Specific report class for ReportDestinationUrl
 *
 * @author marcwan@google.com (Marc Wandschneider)
 */
@Entity
@com.googlecode.objectify.annotation.Entity
@Table(name = "AW_ReportDestinationUrl")
@CsvReport(value = ReportDefinitionReportType.DESTINATION_URL_REPORT)
public class ReportDestinationUrl extends ReportBase {

  @Column(name = "ACCOUNTTIMEZONEID")
  @CsvField(value = "Time zone", reportField = "AccountTimeZoneId")
  private String accountTimeZoneId;

  @Column(name = "ADGROUPID")
  @CsvField(value = "Ad group ID", reportField = "AdGroupId")
  private Long adGroupId;

  @Column(name = "ADGROUPNAME")
  @CsvField(value = "Ad group", reportField = "AdGroupName")
  private String adGroupName;

  @Column(name = "ADGROUPSTATUS")
  @CsvField(value = "Ad group state", reportField = "AdGroupStatus")
  private String adGroupStatus;

  @Column(name = "CAMPAIGNID")
  @CsvField(value = "Campaign ID", reportField = "CampaignId")
  private Long campaignId;

  @Column(name = "CAMPAIGNNAME")
  @CsvField(value = "Campaign", reportField = "CampaignName")
  private String campaignName;

  @Column(name = "CAMPAIGNSTATUS")
  @CsvField(value = "Campaign state", reportField = "CampaignStatus")
  private String campaignStatus;

  @Column(name = "CONVERSIONRATE")
  @CsvField(value = "Conv. rate (1-per-click)", reportField = "ConversionRate")
  private BigDecimal conversionRate;

  @Column(name = "CONVERSIONRATEMANYPERCLICK")
  @CsvField(value = "Conv. rate (many-per-click)", reportField = "ConversionRateManyPerClick")
  private BigDecimal conversionRateManyPerClick;

  @Column(name = "CONVERSIONSMANYPERCLICK")
  @CsvField(value = "Conv. (many-per-click)", reportField = "ConversionsManyPerClick")
  private Long conversionsManyPerClick;

  @Column(name = "CONVERSIONVALUE")
  @CsvField(value = "Total conv. value", reportField = "ConversionValue")
  private Long conversionValue;

  @Column(name = "COSTPERCONVERSION")
  @CsvField(value = "Cost / conv. (1-per-click)", reportField = "CostPerConversion")
  private BigDecimal costPerConversion;

  @Column(name = "COSTPERCONVERSIONMANYPERCLICK")
  @CsvField(value = "Cost / conv. (many-per-click)", reportField = "CostPerConversionManyPerClick")
  private BigDecimal costPerConversionManyPerClick;

  @Column(name = "CRITERIADESTINATIONURL")
  @CsvField(value = "Keyword/Placement destination URL", reportField = "CriteriaDestinationUrl")
  private String criteriaDestinationUrl;

  @Column(name = "CRITERIAPARAMETERS")
  @CsvField(value = "Keyword / Placement", reportField = "CriteriaParameters")
  private String criteriaParameters;

  @Column(name = "CRITERIASTATUS")
  @CsvField(value = "Keyword/Placement state", reportField = "CriteriaStatus")
  private String criteriaStatus;

  @Column(name = "CRITERIATYPENAME")
  @CsvField(value = "Match type", reportField = "CriteriaTypeName")
  private String criteriaTypeName;

  @Column(name = "CUSTOMERDESCRIPTIVENAME")
  @CsvField(value = "Client name", reportField = "CustomerDescriptiveName")
  private String customerDescriptiveName;

  @Column(name = "DAYOFWEEK")
  @CsvField(value = "Day of week", reportField = "DayOfWeek")
  private String dayOfWeek;

  @Column(name = "EFFECTIVEDESTINATIONURL")
  @CsvField(value = "Destination URL", reportField = "EffectiveDestinationUrl")
  private String effectiveDestinationUrl;

  @Column(name = "ISNEGATIVE")
  @CsvField(value = "Is negative", reportField = "IsNegative")
  private String isNegative;

  @Column(name = "MONTHOFYEAR")
  @CsvField(value = "Month of Year", reportField = "MonthOfYear")
  private String monthOfYear;

  @Column(name = "PRIMARYCOMPANYNAME")
  @CsvField(value = "Company name", reportField = "PrimaryCompanyName")
  private String primaryCompanyName;

  @Column(name = "PRIMARYUSERLOGIN")
  @CsvField(value = "Login email", reportField = "PrimaryUserLogin")
  private String primaryUserLogin;

  @Column(name = "QUARTER")
  @CsvField(value = "Quarter", reportField = "Quarter")
  private String quarter;

  @Column(name = "TOTALCONVVALUE")
  @CsvField(value = "Total conv. value", reportField = "TotalConvValue")
  private BigDecimal totalConvValue;

  @Column(name = "VALUEPERCONV")
  @CsvField(value = "Value / conv. (1-per-click)", reportField = "ValuePerConv")
  private BigDecimal valuePerConv;

  @Column(name = "VALUEPERCONVERSION")
  @CsvField(value = "Value / conv. (1-per-click)", reportField = "ValuePerConversion")
  private BigDecimal valuePerConversion;

  @Column(name = "VALUEPERCONVERSIONMANYPERCLICK")
  @CsvField(value = "Value / conv. (many-per-click)",
  reportField = "ValuePerConversionManyPerClick")
  private BigDecimal valuePerConversionManyPerClick;

  @Column(name = "VALUEPERCONVMANYPERCLICK")
  @CsvField(value = "Value / conv. (many-per-click)", reportField = "ValuePerConvManyPerClick")
  private BigDecimal valuePerConvManyPerClick;

  @Column(name = "VIEWTHROUGHCONVERSIONS")
  @CsvField(value = "View-through conv.", reportField = "ViewThroughConversions")
  private Long viewThroughConversions;

  @Column(name = "WEEK")
  @CsvField(value = "Week", reportField = "Week")
  private String week;

  @Column(name = "YEAR")
  @CsvField(value = "Year", reportField = "Year")
  private Long year;

  /**
   * Hibernate needs an empty constructor
   */
  public ReportDestinationUrl() {
  }

  public ReportDestinationUrl(Long topAccountId, Long accountId) {
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
    if (this.getAdNetwork() != null) {
      this.id += this.getAdNetwork() + "-";
    }
    if (this.getAdNetworkPartners() != null) {
      this.id += this.getAdNetworkPartners() + "-";
    }
    if (this.getClickType() != null) {
      this.id += this.getClickType() + "-";
    }
    if (this.getCriteriaParameters() != null) { 
      this.id += this.getCriteriaParameters() + "-";
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
  }

  public String getAccountTimeZoneId() {
    return accountTimeZoneId;
  }

  public void setAccountTimeZoneId(String accountTimeZoneId) {
    this.accountTimeZoneId = accountTimeZoneId;
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

  public String getConversionRate() {
    return BigDecimalUtil.formatAsReadable(conversionRate);
  }

  public void setConversionRate(String conversionRate) {
    this.conversionRate = BigDecimalUtil.parseFromNumberString(conversionRate);
  }

  public void setConversionRate(BigDecimal conversionRate) {
    this.conversionRate = conversionRate;
  }

  public String getConversionRateManyPerClick() {
    return BigDecimalUtil.formatAsReadable(conversionRateManyPerClick);
  }

  public void setConversionRateManyPerClick(String conversionRateManyPerClick) {
    this.conversionRateManyPerClick =
        BigDecimalUtil.parseFromNumberString(conversionRateManyPerClick);
  }

  public void setConversionRateManyPerClick(BigDecimal conversionRateManyPerClick) {
    this.conversionRateManyPerClick = conversionRateManyPerClick;
  }

  public Long getConversionsManyPerClick() {
    return conversionsManyPerClick;
  }

  public void setConversionsManyPerClick(Long conversionsManyPerClick) {
    this.conversionsManyPerClick = conversionsManyPerClick;
  }

  public Long getConversionValue() {
    return conversionValue;
  }

  public void setConversionValue(Long conversionValue) {
    this.conversionValue = conversionValue;
  }

  public String getCostPerConversion() {
    return BigDecimalUtil.formatAsReadable(costPerConversion);
  }

  public void setCostPerConversion(String costPerConversion) {
    this.costPerConversion = BigDecimalUtil.parseFromNumberString(costPerConversion);
  }

  public void setCostPerConversion(BigDecimal costPerConversion) {
    this.costPerConversion = costPerConversion;
  }

  public String getCostPerConversionManyPerClick() {
    return BigDecimalUtil.formatAsReadable(costPerConversionManyPerClick);
  }

  public void setCostPerConversionManyPerClick(String costPerConversionManyPerClick) {
    this.costPerConversionManyPerClick =
        BigDecimalUtil.parseFromNumberString(costPerConversionManyPerClick);
  }

  public void setCostPerConversionManyPerClick(BigDecimal costPerConversionManyPerClick) {
    this.costPerConversionManyPerClick = costPerConversionManyPerClick;
  }

  public String getCriteriaDestinationUrl() {
    return criteriaDestinationUrl;
  }

  public void setCriteriaDestinationUrl(String criteriaDestinationUrl) {
    this.criteriaDestinationUrl = criteriaDestinationUrl;
  }

  public String getCriteriaParameters() {
    return criteriaParameters;
  }

  public void setCriteriaParameters(String criteriaParameters) {
    this.criteriaParameters = criteriaParameters;
  }

  public String getCriteriaStatus() {
    return criteriaStatus;
  }

  public void setCriteriaStatus(String criteriaStatus) {
    this.criteriaStatus = criteriaStatus;
  }

  public String getCriteriaTypeName() {
    return criteriaTypeName;
  }

  public void setCriteriaTypeName(String criteriaTypeName) {
    this.criteriaTypeName = criteriaTypeName;
  }

  public String getCustomerDescriptiveName() {
    return customerDescriptiveName;
  }

  public void setCustomerDescriptiveName(String customerDescriptiveName) {
    this.customerDescriptiveName = customerDescriptiveName;
  }

  public String getDayOfWeek() {
    return dayOfWeek;
  }

  public void setDayOfWeek(String dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }

  public String getEffectiveDestinationUrl() {
    return effectiveDestinationUrl;
  }

  public void setEffectiveDestinationUrl(String effectiveDestinationUrl) {
    this.effectiveDestinationUrl = effectiveDestinationUrl;
  }

  public String getIsNegative() {
    return isNegative;
  }

  public void setIsNegative(String isNegative) {
    this.isNegative = isNegative;
  }

  public String getMonthOfYear() {
    return monthOfYear;
  }

  public void setMonthOfYear(String monthOfYear) {
    this.monthOfYear = monthOfYear;
  }

  public String getPrimaryCompanyName() {
    return primaryCompanyName;
  }

  public void setPrimaryCompanyName(String primaryCompanyName) {
    this.primaryCompanyName = primaryCompanyName;
  }

  public String getPrimaryUserLogin() {
    return primaryUserLogin;
  }

  public void setPrimaryUserLogin(String primaryUserLogin) {
    this.primaryUserLogin = primaryUserLogin;
  }

  public String getQuarter() {
    return quarter;
  }

  public void setQuarter(String quarter) {
    this.quarter = quarter;
  }

  public String getTotalConvValue() {
    return BigDecimalUtil.formatAsReadable(totalConvValue);
  }

  public void setTotalConvValue(String totalConvValue) {
    this.totalConvValue = BigDecimalUtil.parseFromNumberString(totalConvValue);
  }

  public void setTotalConvValue(BigDecimal totalConvValue) {
    this.totalConvValue = totalConvValue;
  }

  public String getValuePerConv() {
    return BigDecimalUtil.formatAsReadable(valuePerConv);
  }

  public void setValuePerConv(String valuePerConv) {
    this.valuePerConv = BigDecimalUtil.parseFromNumberString(valuePerConv);
  }

  public void setValuePerConv(BigDecimal valuePerConv) {
    this.valuePerConv = valuePerConv;
  }

  public String getValuePerConversion() {
    return BigDecimalUtil.formatAsReadable(valuePerConversion);
  }

  public void setValuePerConversion(String valuePerConversion) {
    this.valuePerConversion = BigDecimalUtil.parseFromNumberString(valuePerConversion);
  }

  public void setValuePerConversion(BigDecimal valuePerConversion) {
    this.valuePerConversion = valuePerConversion;
  }

  public String getValuePerConversionManyPerClick() {
    return BigDecimalUtil.formatAsReadable(valuePerConversionManyPerClick);
  }

  public void setValuePerConversionManyPerClick(String valuePerConversionManyPerClick) {
    this.valuePerConversionManyPerClick =
        BigDecimalUtil.parseFromNumberString(valuePerConversionManyPerClick);
  }

  public void setValuePerConversionManyPerClick(BigDecimal valuePerConversionManyPerClick) {
    this.valuePerConversionManyPerClick = valuePerConversionManyPerClick;
  }

  public String getValuePerConvManyPerClick() {
    return BigDecimalUtil.formatAsReadable(valuePerConvManyPerClick);
  }

  public void setValuePerConvManyPerClick(String valuePerConvManyPerClick) {
    this.valuePerConvManyPerClick = BigDecimalUtil.parseFromNumberString(valuePerConvManyPerClick);
  }

  public void setValuePerConvManyPerClick(BigDecimal valuePerConvManyPerClick) {
    this.valuePerConvManyPerClick = valuePerConvManyPerClick;
  }


  public Long getViewThroughConversions() {
    return viewThroughConversions;
  }

  public void setViewThroughConversions(Long viewThroughConversions) {
    this.viewThroughConversions = viewThroughConversions;
  }

  public String getWeek() {
    return week;
  }

  public void setWeek(String week) {
    this.week = week;
  }

  public Long getYear() {
    return year;
  }

  public void setYear(Long year) {
    this.year = year;
  }
}