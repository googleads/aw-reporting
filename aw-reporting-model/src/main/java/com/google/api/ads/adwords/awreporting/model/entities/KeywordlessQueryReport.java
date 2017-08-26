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
import com.google.api.ads.adwords.awreporting.model.csv.annotation.MoneyField;
import com.google.api.ads.adwords.awreporting.model.util.BigDecimalUtil;
import com.google.api.ads.adwords.lib.jaxb.v201708.ReportDefinitionReportType;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
   * Specific report class for KeywordlessQueryReport.
 *
*/
@Entity
@Table(name = "AW_KeywordlessQueryReport")
@CsvReport(value = ReportDefinitionReportType.KEYWORDLESS_QUERY_REPORT)
public class KeywordlessQueryReport extends DateReport {

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

  @Column(name = "AllConversionRate")
  @CsvField(value = "All conv. rate", reportField = "AllConversionRate")
  private BigDecimal allConversionRate;

  @Column(name = "AllConversions")
  @CsvField(value = "All conv.", reportField = "AllConversions")
  private BigDecimal allConversions;

  @Column(name = "AllConversionValue")
  @CsvField(value = "All conv. value", reportField = "AllConversionValue")
  private BigDecimal allConversionValue;

  @Column(name = "AverageCpc")
  @CsvField(value = "Avg. CPC", reportField = "AverageCpc")
  @MoneyField
  private BigDecimal averageCpc;

  @Column(name = "AverageCpm")
  @CsvField(value = "Avg. CPM", reportField = "AverageCpm")
  @MoneyField
  private BigDecimal averageCpm;

  @Column(name = "CampaignId")
  @CsvField(value = "Campaign ID", reportField = "CampaignId")
  private Long campaignId;

  @Column(name = "CampaignName")
  @CsvField(value = "Campaign", reportField = "CampaignName")
  private String campaignName;

  @Column(name = "CampaignStatus")
  @CsvField(value = "Campaign state", reportField = "CampaignStatus")
  private String campaignStatus;

  @Column(name = "CategoryPaths")
  @CsvField(value = "Categories", reportField = "CategoryPaths")
  private String categoryPaths;

  @Column(name = "Clicks")
  @CsvField(value = "Clicks", reportField = "Clicks")
  private Long clicks;

  @Column(name = "ConversionCategoryName")
  @CsvField(value = "Conversion category", reportField = "ConversionCategoryName")
  private String conversionCategoryName;

  @Column(name = "ConversionRate")
  @CsvField(value = "Conv. rate", reportField = "ConversionRate")
  private BigDecimal conversionRate;

  @Column(name = "Conversions")
  @CsvField(value = "Conversions", reportField = "Conversions")
  private BigDecimal conversions;

  @Column(name = "ConversionTrackerId")
  @CsvField(value = "Conversion Tracker Id", reportField = "ConversionTrackerId")
  private Long conversionTrackerId;

  @Column(name = "ConversionTypeName")
  @CsvField(value = "Conversion name", reportField = "ConversionTypeName")
  private String conversionTypeName;

  @Column(name = "ConversionValue")
  @CsvField(value = "Total conv. value", reportField = "ConversionValue")
  private BigDecimal conversionValue;

  @Column(name = "Cost")
  @CsvField(value = "Cost", reportField = "Cost")
  @MoneyField
  private BigDecimal cost;

  @Column(name = "CostPerAllConversion")
  @CsvField(value = "Cost / all conv.", reportField = "CostPerAllConversion")
  @MoneyField
  private BigDecimal costPerAllConversion;

  @Column(name = "CostPerConversion")
  @CsvField(value = "Cost / conv.", reportField = "CostPerConversion")
  @MoneyField
  private BigDecimal costPerConversion;

  @Column(name = "CriterionId")
  @CsvField(value = "Keyword ID", reportField = "CriterionId")
  private Long criterionId;

  @Column(name = "CrossDeviceConversions")
  @CsvField(value = "Cross-device conv.", reportField = "CrossDeviceConversions")
  private BigDecimal crossDeviceConversions;

  @Column(name = "Ctr")
  @CsvField(value = "CTR", reportField = "Ctr")
  private BigDecimal ctr;

  @Column(name = "CustomerDescriptiveName")
  @CsvField(value = "Client name", reportField = "CustomerDescriptiveName")
  private String customerDescriptiveName;

  @Column(name = "Domain")
  @CsvField(value = "Domain", reportField = "Domain")
  private String domain;

  @Column(name = "ExternalConversionSource")
  @CsvField(value = "Conversion source", reportField = "ExternalConversionSource")
  private String externalConversionSource;

  @Column(name = "Impressions")
  @CsvField(value = "Impressions", reportField = "Impressions")
  private Long impressions;

  @Column(name = "Line1")
  @CsvField(value = "Dynamically generated Headline", reportField = "Line1")
  private String line1;

  @Column(name = "Query")
  @CsvField(value = "Search term", reportField = "Query")
  private String query;

  @Column(name = "QueryTargetingStatus")
  @CsvField(value = "Added/Excluded", reportField = "QueryTargetingStatus")
  private String queryTargetingStatus;

  @Column(name = "Title")
  @CsvField(value = "Landing Page Title", reportField = "Title")
  private String title;

  @Column(name = "Url", length = 2048)
  @CsvField(value = "URL", reportField = "Url")
  private String url;

  @Column(name = "ValuePerAllConversion")
  @CsvField(value = "Value / all conv.", reportField = "ValuePerAllConversion")
  private BigDecimal valuePerAllConversion;

  @Column(name = "ValuePerConversion")
  @CsvField(value = "Value / conv.", reportField = "ValuePerConversion")
  private BigDecimal valuePerConversion;

  /**
   * Hibernate needs an empty constructor
   */
  public KeywordlessQueryReport() {
  }

  public KeywordlessQueryReport(Long topAccountId, Long accountId){
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

  public String getAllConversionRate() {
    return BigDecimalUtil.formatAsReadable(allConversionRate);
  }

  public BigDecimal getAllConversionRateBigDecimal() {
    return allConversionRate;
  }

  public void setAllConversionRate(String allConversionRate) {
    this.allConversionRate = BigDecimalUtil.parseFromNumberString(allConversionRate);
  }

  public String getAllConversions() {
    return BigDecimalUtil.formatAsReadable(allConversions);
  }

  public BigDecimal getAllConversionsBigDecimal() {
    return allConversions;
  }

  public void setAllConversions(String allConversions) {
    this.allConversions = BigDecimalUtil.parseFromNumberString(allConversions);
  }

  public String getAllConversionValue() {
    return BigDecimalUtil.formatAsReadable(allConversionValue);
  }

  public BigDecimal getAllConversionValueBigDecimal() {
    return allConversionValue;
  }

  public void setAllConversionValue(String allConversionValue) {
    this.allConversionValue = BigDecimalUtil.parseFromNumberString(allConversionValue);
  }

  public BigDecimal getAverageCpc() {
    return averageCpc;
  }

  public void setAverageCpc(BigDecimal averageCpc) {
    this.averageCpc = averageCpc;
  }

  public BigDecimal getAverageCpm() {
    return averageCpm;
  }

  public void setAverageCpm(BigDecimal averageCpm) {
    this.averageCpm = averageCpm;
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

  public String getCategoryPaths() {
    return categoryPaths;
  }

  public void setCategoryPaths(String categoryPaths) {
    this.categoryPaths = categoryPaths;
  }

  public Long getClicks() {
    return clicks;
  }

  public void setClicks(Long clicks) {
    this.clicks = clicks;
  }

  public String getConversionCategoryName() {
    return conversionCategoryName;
  }

  public void setConversionCategoryName(String conversionCategoryName) {
    this.conversionCategoryName = conversionCategoryName;
  }

  public String getConversionRate() {
    return BigDecimalUtil.formatAsReadable(conversionRate);
  }

  public BigDecimal getConversionRateBigDecimal() {
    return conversionRate;
  }

  public void setConversionRate(String conversionRate) {
    this.conversionRate = BigDecimalUtil.parseFromNumberString(conversionRate);
  }

  public String getConversions() {
    return BigDecimalUtil.formatAsReadable(conversions);
  }

  public BigDecimal getConversionsBigDecimal() {
    return conversions;
  }

  public void setConversions(String conversions) {
    this.conversions = BigDecimalUtil.parseFromNumberString(conversions);
  }

  public Long getConversionTrackerId() {
    return conversionTrackerId;
  }

  public void setConversionTrackerId(Long conversionTrackerId) {
    this.conversionTrackerId = conversionTrackerId;
  }

  public String getConversionTypeName() {
    return conversionTypeName;
  }

  public void setConversionTypeName(String conversionTypeName) {
    this.conversionTypeName = conversionTypeName;
  }

  public String getConversionValue() {
    return BigDecimalUtil.formatAsReadable(conversionValue);
  }

  public BigDecimal getConversionValueBigDecimal() {
    return conversionValue;
  }

  public void setConversionValue(String conversionValue) {
    this.conversionValue = BigDecimalUtil.parseFromNumberString(conversionValue);
  }

  public BigDecimal getCost() {
    return cost;
  }

  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }

  public BigDecimal getCostPerAllConversion() {
    return costPerAllConversion;
  }

  public void setCostPerAllConversion(BigDecimal costPerAllConversion) {
    this.costPerAllConversion = costPerAllConversion;
  }

  public BigDecimal getCostPerConversion() {
    return costPerConversion;
  }

  public void setCostPerConversion(BigDecimal costPerConversion) {
    this.costPerConversion = costPerConversion;
  }

  public Long getCriterionId() {
    return criterionId;
  }

  public void setCriterionId(Long criterionId) {
    this.criterionId = criterionId;
  }

  public String getCrossDeviceConversions() {
    return BigDecimalUtil.formatAsReadable(crossDeviceConversions);
  }

  public BigDecimal getCrossDeviceConversionsBigDecimal() {
    return crossDeviceConversions;
  }

  public void setCrossDeviceConversions(String crossDeviceConversions) {
    this.crossDeviceConversions = BigDecimalUtil.parseFromNumberString(crossDeviceConversions);
  }

  public String getCtr() {
    return BigDecimalUtil.formatAsReadable(ctr);
  }

  public BigDecimal getCtrBigDecimal() {
    return ctr;
  }

  public void setCtr(String ctr) {
    this.ctr = (ctr == null ? null : BigDecimalUtil.parseFromNumberString(ctr.replace("%","")));
  }

  public String getCustomerDescriptiveName() {
    return customerDescriptiveName;
  }

  public void setCustomerDescriptiveName(String customerDescriptiveName) {
    this.customerDescriptiveName = customerDescriptiveName;
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public String getExternalConversionSource() {
    return externalConversionSource;
  }

  public void setExternalConversionSource(String externalConversionSource) {
    this.externalConversionSource = externalConversionSource;
  }

  public Long getImpressions() {
    return impressions;
  }

  public void setImpressions(Long impressions) {
    this.impressions = impressions;
  }

  public String getLine1() {
    return line1;
  }

  public void setLine1(String line1) {
    this.line1 = line1;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public String getQueryTargetingStatus() {
    return queryTargetingStatus;
  }

  public void setQueryTargetingStatus(String queryTargetingStatus) {
    this.queryTargetingStatus = queryTargetingStatus;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getValuePerAllConversion() {
    return BigDecimalUtil.formatAsReadable(valuePerAllConversion);
  }

  public BigDecimal getValuePerAllConversionBigDecimal() {
    return valuePerAllConversion;
  }

  public void setValuePerAllConversion(String valuePerAllConversion) {
    this.valuePerAllConversion = BigDecimalUtil.parseFromNumberString(valuePerAllConversion);
  }

  public String getValuePerConversion() {
    return BigDecimalUtil.formatAsReadable(valuePerConversion);
  }

  public BigDecimal getValuePerConversionBigDecimal() {
    return valuePerConversion;
  }

  public void setValuePerConversion(String valuePerConversion) {
    this.valuePerConversion = BigDecimalUtil.parseFromNumberString(valuePerConversion);
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
    if (criterionId != null) {
      idBuilder.append("-").append(criterionId);
    }
    idBuilder.append("-").append(getDateLabel());

    // Include all segmentation fields (if set).
    if (!StringUtils.isEmpty(conversionCategoryName)) {
      idBuilder.append("-").append(conversionCategoryName);
    }
    if (conversionTrackerId != null) {
      idBuilder.append("-").append(conversionTrackerId);
    }
    if (!StringUtils.isEmpty(conversionTypeName)) {
      idBuilder.append("-").append(conversionTypeName);
    }
    if (!StringUtils.isEmpty(externalConversionSource)) {
      idBuilder.append("-").append(externalConversionSource);
    }
    this.rowId = idBuilder.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) { return false; }
    if (obj == this) { return true; }
    if (obj.getClass() != getClass()) { return false; }
    KeywordlessQueryReport other = (KeywordlessQueryReport) obj;
    return new EqualsBuilder()
      .appendSuper(super.equals(obj))
      .append(accountCurrencyCode, other.accountCurrencyCode)
      .append(accountDescriptiveName, other.accountDescriptiveName)
      .append(accountTimeZone, other.accountTimeZone)
      .append(adGroupId, other.adGroupId)
      .append(adGroupName, other.adGroupName)
      .append(adGroupStatus, other.adGroupStatus)
      .append(allConversionRate, other.allConversionRate)
      .append(allConversions, other.allConversions)
      .append(allConversionValue, other.allConversionValue)
      .append(averageCpc, other.averageCpc)
      .append(averageCpm, other.averageCpm)
      .append(campaignId, other.campaignId)
      .append(campaignName, other.campaignName)
      .append(campaignStatus, other.campaignStatus)
      .append(categoryPaths, other.categoryPaths)
      .append(clicks, other.clicks)
      .append(conversionCategoryName, other.conversionCategoryName)
      .append(conversionRate, other.conversionRate)
      .append(conversions, other.conversions)
      .append(conversionTrackerId, other.conversionTrackerId)
      .append(conversionTypeName, other.conversionTypeName)
      .append(conversionValue, other.conversionValue)
      .append(cost, other.cost)
      .append(costPerAllConversion, other.costPerAllConversion)
      .append(costPerConversion, other.costPerConversion)
      .append(criterionId, other.criterionId)
      .append(crossDeviceConversions, other.crossDeviceConversions)
      .append(ctr, other.ctr)
      .append(customerDescriptiveName, other.customerDescriptiveName)
      .append(domain, other.domain)
      .append(externalConversionSource, other.externalConversionSource)
      .append(impressions, other.impressions)
      .append(line1, other.line1)
      .append(query, other.query)
      .append(queryTargetingStatus, other.queryTargetingStatus)
      .append(title, other.title)
      .append(url, other.url)
      .append(valuePerAllConversion, other.valuePerAllConversion)
      .append(valuePerConversion, other.valuePerConversion)
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
      .append(allConversionRate)
      .append(allConversions)
      .append(allConversionValue)
      .append(averageCpc)
      .append(averageCpm)
      .append(campaignId)
      .append(campaignName)
      .append(campaignStatus)
      .append(categoryPaths)
      .append(clicks)
      .append(conversionCategoryName)
      .append(conversionRate)
      .append(conversions)
      .append(conversionTrackerId)
      .append(conversionTypeName)
      .append(conversionValue)
      .append(cost)
      .append(costPerAllConversion)
      .append(costPerConversion)
      .append(criterionId)
      .append(crossDeviceConversions)
      .append(ctr)
      .append(customerDescriptiveName)
      .append(domain)
      .append(externalConversionSource)
      .append(impressions)
      .append(line1)
      .append(query)
      .append(queryTargetingStatus)
      .append(title)
      .append(url)
      .append(valuePerAllConversion)
      .append(valuePerConversion)
      .toHashCode();
  }

}
