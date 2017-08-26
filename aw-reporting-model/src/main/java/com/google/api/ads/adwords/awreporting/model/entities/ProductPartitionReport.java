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
   * Specific report class for ProductPartitionReport.
 *
*/
@Entity
@Table(name = "AW_ProductPartitionReport")
@CsvReport(value = ReportDefinitionReportType.PRODUCT_PARTITION_REPORT)
public class ProductPartitionReport extends DateReport {

  @Column(name = "AccountDescriptiveName")
  @CsvField(value = "Account", reportField = "AccountDescriptiveName")
  private String accountDescriptiveName;

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

  @Column(name = "BenchmarkAverageMaxCpc")
  @CsvField(value = "Benchmark Max. CPC", reportField = "BenchmarkAverageMaxCpc")
  @MoneyField
  private BigDecimal benchmarkAverageMaxCpc;

  @Column(name = "BenchmarkCtr")
  @CsvField(value = "Benchmark CTR", reportField = "BenchmarkCtr")
  private BigDecimal benchmarkCtr;

  @Column(name = "CampaignId")
  @CsvField(value = "Campaign ID", reportField = "CampaignId")
  private Long campaignId;

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

  @Column(name = "ConversionRate")
  @CsvField(value = "Conv. rate", reportField = "ConversionRate")
  private BigDecimal conversionRate;

  @Column(name = "Conversions")
  @CsvField(value = "Conversions", reportField = "Conversions")
  private BigDecimal conversions;

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

  @Column(name = "CpcBid")
  @CsvField(value = "Max. CPC", reportField = "CpcBid")
  @MoneyField
  private BigDecimal cpcBid;

  @Column(name = "CriteriaDestinationUrl", length = 2048)
  @CsvField(value = "Keyword/Placement destination URL", reportField = "CriteriaDestinationUrl")
  private String criteriaDestinationUrl;

  @Column(name = "CrossDeviceConversions")
  @CsvField(value = "Cross-device conv.", reportField = "CrossDeviceConversions")
  private BigDecimal crossDeviceConversions;

  @Column(name = "Ctr")
  @CsvField(value = "CTR", reportField = "Ctr")
  private BigDecimal ctr;

  @Column(name = "Device")
  @CsvField(value = "Device", reportField = "Device")
  private String device;

  @Column(name = "Id")
  @CsvField(value = "Criterion ID", reportField = "Id")
  private Long id;

  @Column(name = "Impressions")
  @CsvField(value = "Impressions", reportField = "Impressions")
  private Long impressions;

  @Column(name = "IsNegative")
  @CsvField(value = "Is negative", reportField = "IsNegative")
  private String isNegative;

  @Column(name = "ParentCriterionId")
  @CsvField(value = "Parent Criterion ID", reportField = "ParentCriterionId")
  private Long parentCriterionId;

  @Column(name = "PartitionType")
  @CsvField(value = "Partition Type", reportField = "PartitionType")
  private String partitionType;

  @Column(name = "ProductGroup")
  @CsvField(value = "Product Group", reportField = "ProductGroup")
  private String productGroup;

  @Column(name = "SearchClickShare")
  @CsvField(value = "Click share", reportField = "SearchClickShare")
  private BigDecimal searchClickShare;

  @Column(name = "SearchImpressionShare")
  @CsvField(value = "Search Impr. share", reportField = "SearchImpressionShare")
  private BigDecimal searchImpressionShare;

  @Column(name = "TrackingUrlTemplate", length = 2048)
  @CsvField(value = "Tracking template", reportField = "TrackingUrlTemplate")
  private String trackingUrlTemplate;

  @Column(name = "UrlCustomParameters", length = 2048)
  @CsvField(value = "Custom parameter", reportField = "UrlCustomParameters")
  private String urlCustomParameters;

  @Column(name = "ValuePerAllConversion")
  @CsvField(value = "Value / all conv.", reportField = "ValuePerAllConversion")
  private BigDecimal valuePerAllConversion;

  @Column(name = "ValuePerConversion")
  @CsvField(value = "Value / conv.", reportField = "ValuePerConversion")
  private BigDecimal valuePerConversion;

  /**
   * Hibernate needs an empty constructor
   */
  public ProductPartitionReport() {
  }

  public ProductPartitionReport(Long topAccountId, Long accountId){
    super(topAccountId, accountId);
  }

  public String getAccountDescriptiveName() {
    return accountDescriptiveName;
  }

  public void setAccountDescriptiveName(String accountDescriptiveName) {
    this.accountDescriptiveName = accountDescriptiveName;
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

  public BigDecimal getBenchmarkAverageMaxCpc() {
    return benchmarkAverageMaxCpc;
  }

  public void setBenchmarkAverageMaxCpc(BigDecimal benchmarkAverageMaxCpc) {
    this.benchmarkAverageMaxCpc = benchmarkAverageMaxCpc;
  }

  public String getBenchmarkCtr() {
    return BigDecimalUtil.formatAsReadable(benchmarkCtr);
  }

  public BigDecimal getBenchmarkCtrBigDecimal() {
    return benchmarkCtr;
  }

  public void setBenchmarkCtr(String benchmarkCtr) {
    this.benchmarkCtr = BigDecimalUtil.parseFromNumberString(benchmarkCtr);
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

  public BigDecimal getCpcBid() {
    return cpcBid;
  }

  public void setCpcBid(BigDecimal cpcBid) {
    this.cpcBid = cpcBid;
  }

  public String getCriteriaDestinationUrl() {
    return criteriaDestinationUrl;
  }

  public void setCriteriaDestinationUrl(String criteriaDestinationUrl) {
    this.criteriaDestinationUrl = criteriaDestinationUrl;
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

  public String getDevice() {
    return device;
  }

  public void setDevice(String device) {
    this.device = device;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getImpressions() {
    return impressions;
  }

  public void setImpressions(Long impressions) {
    this.impressions = impressions;
  }

  public String getIsNegative() {
    return isNegative;
  }

  public void setIsNegative(String isNegative) {
    this.isNegative = isNegative;
  }

  public Long getParentCriterionId() {
    return parentCriterionId;
  }

  public void setParentCriterionId(Long parentCriterionId) {
    this.parentCriterionId = parentCriterionId;
  }

  public String getPartitionType() {
    return partitionType;
  }

  public void setPartitionType(String partitionType) {
    this.partitionType = partitionType;
  }

  public String getProductGroup() {
    return productGroup;
  }

  public void setProductGroup(String productGroup) {
    this.productGroup = productGroup;
  }

  public String getSearchClickShare() {
    return BigDecimalUtil.formatAsReadable(searchClickShare);
  }

  public BigDecimal getSearchClickShareBigDecimal() {
    return searchClickShare;
  }

  public void setSearchClickShare(String searchClickShare) {
    this.searchClickShare = BigDecimalUtil.parseFromNumberString(searchClickShare);
  }

  public String getSearchImpressionShare() {
    return BigDecimalUtil.formatAsReadable(searchImpressionShare);
  }

  public BigDecimal getSearchImpressionShareBigDecimal() {
    return searchImpressionShare;
  }

  public void setSearchImpressionShare(String searchImpressionShare) {
    this.searchImpressionShare = BigDecimalUtil.parseFromNumberStringPercentage(searchImpressionShare);
  }

  public String getTrackingUrlTemplate() {
    return trackingUrlTemplate;
  }

  public void setTrackingUrlTemplate(String trackingUrlTemplate) {
    this.trackingUrlTemplate = trackingUrlTemplate;
  }

  public String getUrlCustomParameters() {
    return urlCustomParameters;
  }

  public void setUrlCustomParameters(String urlCustomParameters) {
    this.urlCustomParameters = urlCustomParameters;
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
    if (id != null) {
      idBuilder.append("-").append(id);
    }
    idBuilder.append("-").append(getDateLabel());

    // Include all segmentation fields (if set).
    if (!StringUtils.isEmpty(adNetworkType1)) {
      idBuilder.append("-").append(adNetworkType1);
    }
    if (!StringUtils.isEmpty(adNetworkType2)) {
      idBuilder.append("-").append(adNetworkType2);
    }
    if (!StringUtils.isEmpty(clickType)) {
      idBuilder.append("-").append(clickType);
    }
    if (!StringUtils.isEmpty(device)) {
      idBuilder.append("-").append(device);
    }
    if (!StringUtils.isEmpty(partitionType)) {
      idBuilder.append("-").append(partitionType);
    }
    this.rowId = idBuilder.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) { return false; }
    if (obj == this) { return true; }
    if (obj.getClass() != getClass()) { return false; }
    ProductPartitionReport other = (ProductPartitionReport) obj;
    return new EqualsBuilder()
      .appendSuper(super.equals(obj))
      .append(accountDescriptiveName, other.accountDescriptiveName)
      .append(adGroupId, other.adGroupId)
      .append(adGroupName, other.adGroupName)
      .append(adGroupStatus, other.adGroupStatus)
      .append(adNetworkType1, other.adNetworkType1)
      .append(adNetworkType2, other.adNetworkType2)
      .append(allConversionRate, other.allConversionRate)
      .append(allConversions, other.allConversions)
      .append(allConversionValue, other.allConversionValue)
      .append(averageCpc, other.averageCpc)
      .append(averageCpm, other.averageCpm)
      .append(benchmarkAverageMaxCpc, other.benchmarkAverageMaxCpc)
      .append(benchmarkCtr, other.benchmarkCtr)
      .append(campaignId, other.campaignId)
      .append(campaignName, other.campaignName)
      .append(campaignStatus, other.campaignStatus)
      .append(clicks, other.clicks)
      .append(clickType, other.clickType)
      .append(conversionRate, other.conversionRate)
      .append(conversions, other.conversions)
      .append(conversionValue, other.conversionValue)
      .append(cost, other.cost)
      .append(costPerAllConversion, other.costPerAllConversion)
      .append(costPerConversion, other.costPerConversion)
      .append(cpcBid, other.cpcBid)
      .append(criteriaDestinationUrl, other.criteriaDestinationUrl)
      .append(crossDeviceConversions, other.crossDeviceConversions)
      .append(ctr, other.ctr)
      .append(device, other.device)
      .append(id, other.id)
      .append(impressions, other.impressions)
      .append(isNegative, other.isNegative)
      .append(parentCriterionId, other.parentCriterionId)
      .append(partitionType, other.partitionType)
      .append(productGroup, other.productGroup)
      .append(searchClickShare, other.searchClickShare)
      .append(searchImpressionShare, other.searchImpressionShare)
      .append(trackingUrlTemplate, other.trackingUrlTemplate)
      .append(urlCustomParameters, other.urlCustomParameters)
      .append(valuePerAllConversion, other.valuePerAllConversion)
      .append(valuePerConversion, other.valuePerConversion)
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .appendSuper(super.hashCode())
      .append(accountDescriptiveName)
      .append(adGroupId)
      .append(adGroupName)
      .append(adGroupStatus)
      .append(adNetworkType1)
      .append(adNetworkType2)
      .append(allConversionRate)
      .append(allConversions)
      .append(allConversionValue)
      .append(averageCpc)
      .append(averageCpm)
      .append(benchmarkAverageMaxCpc)
      .append(benchmarkCtr)
      .append(campaignId)
      .append(campaignName)
      .append(campaignStatus)
      .append(clicks)
      .append(clickType)
      .append(conversionRate)
      .append(conversions)
      .append(conversionValue)
      .append(cost)
      .append(costPerAllConversion)
      .append(costPerConversion)
      .append(cpcBid)
      .append(criteriaDestinationUrl)
      .append(crossDeviceConversions)
      .append(ctr)
      .append(device)
      .append(id)
      .append(impressions)
      .append(isNegative)
      .append(parentCriterionId)
      .append(partitionType)
      .append(productGroup)
      .append(searchClickShare)
      .append(searchImpressionShare)
      .append(trackingUrlTemplate)
      .append(urlCustomParameters)
      .append(valuePerAllConversion)
      .append(valuePerConversion)
      .toHashCode();
  }

}
