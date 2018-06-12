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
import com.google.api.ads.adwords.lib.jaxb.v201806.ReportDefinitionReportType;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
   * Specific report class for ShoppingPerformanceReport.
 *
*/
@Entity
@Table(name = "AW_ShoppingPerformanceReport")
@CsvReport(value = ReportDefinitionReportType.SHOPPING_PERFORMANCE_REPORT)
public class ShoppingPerformanceReport extends DateReport {

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

  @Column(name = "AggregatorId")
  @CsvField(value = "MCA Id", reportField = "AggregatorId")
  private Long aggregatorId;

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

  @Column(name = "Brand")
  @CsvField(value = "Brand", reportField = "Brand")
  private String brand;

  @Column(name = "CampaignId")
  @CsvField(value = "Campaign ID", reportField = "CampaignId")
  private Long campaignId;

  @Column(name = "CampaignName")
  @CsvField(value = "Campaign", reportField = "CampaignName")
  private String campaignName;

  @Column(name = "CampaignStatus")
  @CsvField(value = "Campaign state", reportField = "CampaignStatus")
  private String campaignStatus;

  @Column(name = "CategoryL1")
  @CsvField(value = "Category (1st level)", reportField = "CategoryL1")
  private String categoryL1;

  @Column(name = "CategoryL2")
  @CsvField(value = "Category (2nd level)", reportField = "CategoryL2")
  private String categoryL2;

  @Column(name = "CategoryL3")
  @CsvField(value = "Category (3rd level)", reportField = "CategoryL3")
  private String categoryL3;

  @Column(name = "CategoryL4")
  @CsvField(value = "Category (4th level)", reportField = "CategoryL4")
  private String categoryL4;

  @Column(name = "CategoryL5")
  @CsvField(value = "Category (5th level)", reportField = "CategoryL5")
  private String categoryL5;

  @Column(name = "Channel")
  @CsvField(value = "Channel", reportField = "Channel")
  private String channel;

  @Column(name = "ChannelExclusivity")
  @CsvField(value = "Channel Exclusivity", reportField = "ChannelExclusivity")
  private String channelExclusivity;

  @Column(name = "Clicks")
  @CsvField(value = "Clicks", reportField = "Clicks")
  private Long clicks;

  @Column(name = "ClickType")
  @CsvField(value = "Click type", reportField = "ClickType")
  private String clickType;

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

  @Column(name = "CountryCriteriaId")
  @CsvField(value = "Country/Territory", reportField = "CountryCriteriaId")
  private Integer countryCriteriaId;

  @Column(name = "CrossDeviceConversions")
  @CsvField(value = "Cross-device conv.", reportField = "CrossDeviceConversions")
  private BigDecimal crossDeviceConversions;

  @Column(name = "Ctr")
  @CsvField(value = "CTR", reportField = "Ctr")
  private BigDecimal ctr;

  @Column(name = "CustomAttribute0")
  @CsvField(value = "Custom label 0", reportField = "CustomAttribute0")
  private String customAttribute0;

  @Column(name = "CustomAttribute1")
  @CsvField(value = "Custom label 1", reportField = "CustomAttribute1")
  private String customAttribute1;

  @Column(name = "CustomAttribute2")
  @CsvField(value = "Custom label 2", reportField = "CustomAttribute2")
  private String customAttribute2;

  @Column(name = "CustomAttribute3")
  @CsvField(value = "Custom label 3", reportField = "CustomAttribute3")
  private String customAttribute3;

  @Column(name = "CustomAttribute4")
  @CsvField(value = "Custom label 4", reportField = "CustomAttribute4")
  private String customAttribute4;

  @Column(name = "Device")
  @CsvField(value = "Device", reportField = "Device")
  private String device;

  @Column(name = "ExternalConversionSource")
  @CsvField(value = "Conversion source", reportField = "ExternalConversionSource")
  private String externalConversionSource;

  @Column(name = "Impressions")
  @CsvField(value = "Impressions", reportField = "Impressions")
  private Long impressions;

  @Column(name = "LanguageCriteriaId")
  @CsvField(value = "Language", reportField = "LanguageCriteriaId")
  private Integer languageCriteriaId;

  @Column(name = "MerchantId")
  @CsvField(value = "MC Id", reportField = "MerchantId")
  private Long merchantId;

  @Column(name = "OfferId")
  @CsvField(value = "Item Id", reportField = "OfferId")
  private String offerId;

  @Column(name = "ProductCondition")
  @CsvField(value = "Condition", reportField = "ProductCondition")
  private String productCondition;

  @Column(name = "ProductTitle")
  @CsvField(value = "Product Title", reportField = "ProductTitle")
  private String productTitle;

  @Column(name = "ProductTypeL1")
  @CsvField(value = "Product type (1st level)", reportField = "ProductTypeL1")
  private String productTypeL1;

  @Column(name = "ProductTypeL2")
  @CsvField(value = "Product type (2nd level)", reportField = "ProductTypeL2")
  private String productTypeL2;

  @Column(name = "ProductTypeL3")
  @CsvField(value = "Product type (3rd level)", reportField = "ProductTypeL3")
  private String productTypeL3;

  @Column(name = "ProductTypeL4")
  @CsvField(value = "Product type (4th level)", reportField = "ProductTypeL4")
  private String productTypeL4;

  @Column(name = "ProductTypeL5")
  @CsvField(value = "Product type (5th level)", reportField = "ProductTypeL5")
  private String productTypeL5;

  @Column(name = "SearchAbsoluteTopImpressionShare")
  @CsvField(value = "Search abs. top IS", reportField = "SearchAbsoluteTopImpressionShare")
  private BigDecimal searchAbsoluteTopImpressionShare;

  @Column(name = "SearchClickShare")
  @CsvField(value = "Click share", reportField = "SearchClickShare")
  private BigDecimal searchClickShare;

  @Column(name = "SearchImpressionShare")
  @CsvField(value = "Search Impr. share", reportField = "SearchImpressionShare")
  private BigDecimal searchImpressionShare;

  @Column(name = "StoreId")
  @CsvField(value = "Store Id", reportField = "StoreId")
  private String storeId;

  @Column(name = "ValuePerAllConversion")
  @CsvField(value = "Value / all conv.", reportField = "ValuePerAllConversion")
  private BigDecimal valuePerAllConversion;

  @Column(name = "ValuePerConversion")
  @CsvField(value = "Value / conv.", reportField = "ValuePerConversion")
  private BigDecimal valuePerConversion;

  @Column(name = "ViewThroughConversions")
  @CsvField(value = "View-through conv.", reportField = "ViewThroughConversions")
  private Long viewThroughConversions;

  /**
   * Hibernate needs an empty constructor
   */
  public ShoppingPerformanceReport() {
  }

  public ShoppingPerformanceReport(Long topAccountId, Long accountId){
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

  public Long getAggregatorId() {
    return aggregatorId;
  }

  public void setAggregatorId(Long aggregatorId) {
    this.aggregatorId = aggregatorId;
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

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
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

  public String getCategoryL1() {
    return categoryL1;
  }

  public void setCategoryL1(String categoryL1) {
    this.categoryL1 = categoryL1;
  }

  public String getCategoryL2() {
    return categoryL2;
  }

  public void setCategoryL2(String categoryL2) {
    this.categoryL2 = categoryL2;
  }

  public String getCategoryL3() {
    return categoryL3;
  }

  public void setCategoryL3(String categoryL3) {
    this.categoryL3 = categoryL3;
  }

  public String getCategoryL4() {
    return categoryL4;
  }

  public void setCategoryL4(String categoryL4) {
    this.categoryL4 = categoryL4;
  }

  public String getCategoryL5() {
    return categoryL5;
  }

  public void setCategoryL5(String categoryL5) {
    this.categoryL5 = categoryL5;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public String getChannelExclusivity() {
    return channelExclusivity;
  }

  public void setChannelExclusivity(String channelExclusivity) {
    this.channelExclusivity = channelExclusivity;
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

  public Integer getCountryCriteriaId() {
    return countryCriteriaId;
  }

  public void setCountryCriteriaId(Integer countryCriteriaId) {
    this.countryCriteriaId = countryCriteriaId;
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

  public String getCustomAttribute0() {
    return customAttribute0;
  }

  public void setCustomAttribute0(String customAttribute0) {
    this.customAttribute0 = customAttribute0;
  }

  public String getCustomAttribute1() {
    return customAttribute1;
  }

  public void setCustomAttribute1(String customAttribute1) {
    this.customAttribute1 = customAttribute1;
  }

  public String getCustomAttribute2() {
    return customAttribute2;
  }

  public void setCustomAttribute2(String customAttribute2) {
    this.customAttribute2 = customAttribute2;
  }

  public String getCustomAttribute3() {
    return customAttribute3;
  }

  public void setCustomAttribute3(String customAttribute3) {
    this.customAttribute3 = customAttribute3;
  }

  public String getCustomAttribute4() {
    return customAttribute4;
  }

  public void setCustomAttribute4(String customAttribute4) {
    this.customAttribute4 = customAttribute4;
  }

  public String getDevice() {
    return device;
  }

  public void setDevice(String device) {
    this.device = device;
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

  public Integer getLanguageCriteriaId() {
    return languageCriteriaId;
  }

  public void setLanguageCriteriaId(Integer languageCriteriaId) {
    this.languageCriteriaId = languageCriteriaId;
  }

  public Long getMerchantId() {
    return merchantId;
  }

  public void setMerchantId(Long merchantId) {
    this.merchantId = merchantId;
  }

  public String getOfferId() {
    return offerId;
  }

  public void setOfferId(String offerId) {
    this.offerId = offerId;
  }

  public String getProductCondition() {
    return productCondition;
  }

  public void setProductCondition(String productCondition) {
    this.productCondition = productCondition;
  }

  public String getProductTitle() {
    return productTitle;
  }

  public void setProductTitle(String productTitle) {
    this.productTitle = productTitle;
  }

  public String getProductTypeL1() {
    return productTypeL1;
  }

  public void setProductTypeL1(String productTypeL1) {
    this.productTypeL1 = productTypeL1;
  }

  public String getProductTypeL2() {
    return productTypeL2;
  }

  public void setProductTypeL2(String productTypeL2) {
    this.productTypeL2 = productTypeL2;
  }

  public String getProductTypeL3() {
    return productTypeL3;
  }

  public void setProductTypeL3(String productTypeL3) {
    this.productTypeL3 = productTypeL3;
  }

  public String getProductTypeL4() {
    return productTypeL4;
  }

  public void setProductTypeL4(String productTypeL4) {
    this.productTypeL4 = productTypeL4;
  }

  public String getProductTypeL5() {
    return productTypeL5;
  }

  public void setProductTypeL5(String productTypeL5) {
    this.productTypeL5 = productTypeL5;
  }

  public String getSearchAbsoluteTopImpressionShare() {
    return BigDecimalUtil.formatAsReadable(searchAbsoluteTopImpressionShare);
  }

  public BigDecimal getSearchAbsoluteTopImpressionShareBigDecimal() {
    return searchAbsoluteTopImpressionShare;
  }

  public void setSearchAbsoluteTopImpressionShare(String searchAbsoluteTopImpressionShare) {
    this.searchAbsoluteTopImpressionShare = BigDecimalUtil.parseFromNumberString(searchAbsoluteTopImpressionShare);
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

  public String getStoreId() {
    return storeId;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
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

  public Long getViewThroughConversions() {
    return viewThroughConversions;
  }

  public void setViewThroughConversions(Long viewThroughConversions) {
    this.viewThroughConversions = viewThroughConversions;
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
    if (!StringUtils.isEmpty(offerId)) {
      idBuilder.append("-").append(offerId);
    }
    idBuilder.append("-").append(getDateLabel());

    // Include all segmentation fields (if set).
    if (!StringUtils.isEmpty(adGroupStatus)) {
      idBuilder.append("-").append(adGroupStatus);
    }
    if (!StringUtils.isEmpty(adNetworkType1)) {
      idBuilder.append("-").append(adNetworkType1);
    }
    if (!StringUtils.isEmpty(adNetworkType2)) {
      idBuilder.append("-").append(adNetworkType2);
    }
    if (!StringUtils.isEmpty(campaignStatus)) {
      idBuilder.append("-").append(campaignStatus);
    }
    if (!StringUtils.isEmpty(channel)) {
      idBuilder.append("-").append(channel);
    }
    if (!StringUtils.isEmpty(channelExclusivity)) {
      idBuilder.append("-").append(channelExclusivity);
    }
    if (!StringUtils.isEmpty(clickType)) {
      idBuilder.append("-").append(clickType);
    }
    if (!StringUtils.isEmpty(conversionCategoryName)) {
      idBuilder.append("-").append(conversionCategoryName);
    }
    if (conversionTrackerId != null) {
      idBuilder.append("-").append(conversionTrackerId);
    }
    if (!StringUtils.isEmpty(conversionTypeName)) {
      idBuilder.append("-").append(conversionTypeName);
    }
    if (!StringUtils.isEmpty(device)) {
      idBuilder.append("-").append(device);
    }
    if (!StringUtils.isEmpty(externalConversionSource)) {
      idBuilder.append("-").append(externalConversionSource);
    }
    if (!StringUtils.isEmpty(productCondition)) {
      idBuilder.append("-").append(productCondition);
    }
    this.rowId = idBuilder.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) { return false; }
    if (obj == this) { return true; }
    if (obj.getClass() != getClass()) { return false; }
    ShoppingPerformanceReport other = (ShoppingPerformanceReport) obj;
    return new EqualsBuilder()
      .appendSuper(super.equals(obj))
      .append(accountDescriptiveName, other.accountDescriptiveName)
      .append(adGroupId, other.adGroupId)
      .append(adGroupName, other.adGroupName)
      .append(adGroupStatus, other.adGroupStatus)
      .append(adNetworkType1, other.adNetworkType1)
      .append(adNetworkType2, other.adNetworkType2)
      .append(aggregatorId, other.aggregatorId)
      .append(allConversionRate, other.allConversionRate)
      .append(allConversions, other.allConversions)
      .append(allConversionValue, other.allConversionValue)
      .append(averageCpc, other.averageCpc)
      .append(brand, other.brand)
      .append(campaignId, other.campaignId)
      .append(campaignName, other.campaignName)
      .append(campaignStatus, other.campaignStatus)
      .append(categoryL1, other.categoryL1)
      .append(categoryL2, other.categoryL2)
      .append(categoryL3, other.categoryL3)
      .append(categoryL4, other.categoryL4)
      .append(categoryL5, other.categoryL5)
      .append(channel, other.channel)
      .append(channelExclusivity, other.channelExclusivity)
      .append(clicks, other.clicks)
      .append(clickType, other.clickType)
      .append(conversionCategoryName, other.conversionCategoryName)
      .append(conversionRate, other.conversionRate)
      .append(conversions, other.conversions)
      .append(conversionTrackerId, other.conversionTrackerId)
      .append(conversionTypeName, other.conversionTypeName)
      .append(conversionValue, other.conversionValue)
      .append(cost, other.cost)
      .append(costPerAllConversion, other.costPerAllConversion)
      .append(costPerConversion, other.costPerConversion)
      .append(countryCriteriaId, other.countryCriteriaId)
      .append(crossDeviceConversions, other.crossDeviceConversions)
      .append(ctr, other.ctr)
      .append(customAttribute0, other.customAttribute0)
      .append(customAttribute1, other.customAttribute1)
      .append(customAttribute2, other.customAttribute2)
      .append(customAttribute3, other.customAttribute3)
      .append(customAttribute4, other.customAttribute4)
      .append(device, other.device)
      .append(externalConversionSource, other.externalConversionSource)
      .append(impressions, other.impressions)
      .append(languageCriteriaId, other.languageCriteriaId)
      .append(merchantId, other.merchantId)
      .append(offerId, other.offerId)
      .append(productCondition, other.productCondition)
      .append(productTitle, other.productTitle)
      .append(productTypeL1, other.productTypeL1)
      .append(productTypeL2, other.productTypeL2)
      .append(productTypeL3, other.productTypeL3)
      .append(productTypeL4, other.productTypeL4)
      .append(productTypeL5, other.productTypeL5)
      .append(searchAbsoluteTopImpressionShare, other.searchAbsoluteTopImpressionShare)
      .append(searchClickShare, other.searchClickShare)
      .append(searchImpressionShare, other.searchImpressionShare)
      .append(storeId, other.storeId)
      .append(valuePerAllConversion, other.valuePerAllConversion)
      .append(valuePerConversion, other.valuePerConversion)
      .append(viewThroughConversions, other.viewThroughConversions)
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
      .append(aggregatorId)
      .append(allConversionRate)
      .append(allConversions)
      .append(allConversionValue)
      .append(averageCpc)
      .append(brand)
      .append(campaignId)
      .append(campaignName)
      .append(campaignStatus)
      .append(categoryL1)
      .append(categoryL2)
      .append(categoryL3)
      .append(categoryL4)
      .append(categoryL5)
      .append(channel)
      .append(channelExclusivity)
      .append(clicks)
      .append(clickType)
      .append(conversionCategoryName)
      .append(conversionRate)
      .append(conversions)
      .append(conversionTrackerId)
      .append(conversionTypeName)
      .append(conversionValue)
      .append(cost)
      .append(costPerAllConversion)
      .append(costPerConversion)
      .append(countryCriteriaId)
      .append(crossDeviceConversions)
      .append(ctr)
      .append(customAttribute0)
      .append(customAttribute1)
      .append(customAttribute2)
      .append(customAttribute3)
      .append(customAttribute4)
      .append(device)
      .append(externalConversionSource)
      .append(impressions)
      .append(languageCriteriaId)
      .append(merchantId)
      .append(offerId)
      .append(productCondition)
      .append(productTitle)
      .append(productTypeL1)
      .append(productTypeL2)
      .append(productTypeL3)
      .append(productTypeL4)
      .append(productTypeL5)
      .append(searchAbsoluteTopImpressionShare)
      .append(searchClickShare)
      .append(searchImpressionShare)
      .append(storeId)
      .append(valuePerAllConversion)
      .append(valuePerConversion)
      .append(viewThroughConversions)
      .toHashCode();
  }

}
