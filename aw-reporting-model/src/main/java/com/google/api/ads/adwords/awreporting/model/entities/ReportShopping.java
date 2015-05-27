// Copyright 2014 Google Inc. All Rights Reserved.
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.api.ads.adwords.awreporting.model.csv.annotation.CsvField;
import com.google.api.ads.adwords.awreporting.model.csv.annotation.CsvReport;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionReportType;

/**
 * Specific Report class for ShoppingPerformanceReports
 *
 * @author zhuoc@google.com (Zhuo Chen)
 */
@Entity
@com.googlecode.objectify.annotation.Entity
@Table(name = "AW_ReportShoppingPerformance")
@CsvReport(value = ReportDefinitionReportType.SHOPPING_PERFORMANCE_REPORT)
public class ReportShopping extends ReportBase {

  @Column(name = "ADGROUP_ID")
  @CsvField(value = "Ad group ID", reportField = "AdGroupId")
  private Long adGroupId;

  @Column(name = "ADGROUP_NAME", length = 255)
  @CsvField(value = "Ad group", reportField = "AdGroupName")
  private String adGroupName;

  @Column(name = "AGGREGATOR_ID")
  @CsvField(value = "MCA Id", reportField = "AggregatorId")
  private Long aggregatorId;

  @Column(name = "BRAND", length = 255)
  @CsvField(value = "Brand", reportField = "Brand")
  private String brand;
  
  @Column(name = "CAMPAIGN_ID")
  @CsvField(value = "Campaign ID", reportField = "CampaignId")
  private Long campaignId;

  @Column(name = "CAMPAIGN_NAME", length = 255)
  @CsvField(value = "Campaign", reportField = "CampaignName")
  private String campaignName;

  @Column(name = "CATEGORY_L1", length = 255)
  @CsvField(value = "Category (1st level)", reportField = "CategoryL1")
  private String categoryL1;

  @Column(name = "CATEGORY_L2", length = 255)
  @CsvField(value = "Category (2nd level)", reportField = "CategoryL2")
  private String categoryL2;

  @Column(name = "CATEGORY_L3", length = 255)
  @CsvField(value = "Category (3rd level)", reportField = "CategoryL3")
  private String categoryL3;

  @Column(name = "CATEGORY_L4", length = 255)
  @CsvField(value = "Category (4th level)", reportField = "CategoryL4")
  private String categoryL4;

  @Column(name = "CATEGORY_L5", length = 255)
  @CsvField(value = "Category (5th level)", reportField = "CategoryL5")
  private String categoryL5;

  @Column(name = "COUNTRY_CRITERIA_ID")
  @CsvField(value = "Country/Territory", reportField = "CountryCriteriaId")
  private Long countryCriteriaId;

  @Column(name = "CUSTOM_ATTRIBUTE_0", length = 255)
  @CsvField(value = "Custom label 0", reportField = "CustomAttribute0")
  private String customAttribute0;

  @Column(name = "CUSTOM_ATTRIBUTE_1", length = 255)
  @CsvField(value = "Custom label 1", reportField = "CustomAttribute1")
  private String customAttribute1;

  @Column(name = "CUSTOM_ATTRIBUTE_2", length = 255)
  @CsvField(value = "Custom label 2", reportField = "CustomAttribute2")
  private String customAttribute2;

  @Column(name = "CUSTOM_ATTRIBUTE_3", length = 255)
  @CsvField(value = "Custom label 3", reportField = "CustomAttribute3")
  private String customAttribute3;

  @Column(name = "CUSTOM_ATTRIBUTE_4", length = 255)
  @CsvField(value = "Custom label 4", reportField = "CustomAttribute4")
  private String customAttribute4;

  @Column(name = "LANGUAGE_CRITERIA_ID")
  @CsvField(value = "Language", reportField = "LanguageCriteriaId")
  private Long languageCriteriaId;
  
  @Column(name = "MERCHANT_ID")
  @CsvField(value = "MC Id", reportField = "MerchantId")
  protected Long merchantId;

  @Column(name = "OFFER_ID", length = 64)
  @CsvField(value = "Item Id", reportField = "OfferId")
  private String offerId;

  @Column(name = "PRODUCT_CONDITION", length = 32)
  @CsvField(value = "Condition", reportField = "ProductCondition")
  private String productCondition;

  @Column(name = "PRODUCT_TYPE_L1", length = 255)
  @CsvField(value = "Product type (1st level)", reportField = "ProductTypeL1")
  private String productTypeL1;

  @Column(name = "PRODUCT_TYPE_L2", length = 255)
  @CsvField(value = "Product type (2nd level)", reportField = "ProductTypeL2")
  private String productTypeL2;

  @Column(name = "PRODUCT_TYPE_L3", length = 255)
  @CsvField(value = "Product type (3rd level)", reportField = "ProductTypeL3")
  private String productTypeL3;

  @Column(name = "PRODUCT_TYPE_L4", length = 255)
  @CsvField(value = "Product type (4th level)", reportField = "ProductTypeL4")
  private String productTypeL4;

  @Column(name = "PRODUCT_TYPE_L5", length = 255)
  @CsvField(value = "Product type (5th level)", reportField = "ProductTypeL5")
  private String productTypeL5;
  
  @Column(name = "STORE_ID")
  @CsvField(value = "Store Id", reportField = "StoreId")
  private Long storeId;
  
  @Column(name = "CHANNEL")
  @CsvField(value = "Channel", reportField = "Channel")
  private String channel;
  
  @Column(name = "CHANNEL_EXCLUSIVITY")
  @CsvField(value = "Channel Exclusivity", reportField = "ChannelExclusivity")
  private String channelExclusivity;

  /**
   * Hibernate needs an empty constructor
   */
  public ReportShopping() {}

  @Override
  public void setId() {

    // Generating unique id after having date and accountId
    if (this.getAccountId() != null) {
      this.id = this.getAccountId().toString();
    } else {
      this.id = "-";
    }
    if (this.getCampaignId() != null) {
      this.id += "-" + this.getCampaignId().toString();
    } else {
      this.id += "-";
    }
    if (this.getAdGroupId() != null) {
      this.id += "-" + this.getAdGroupId().toString();
    } else {
      this.id += "-";
    }

    this.id += setIdDates();

    // Adding extra fields for unique ID
    if (this.getMerchantId() != null) {
      this.id += "-" + this.getMerchantId().toString();
    }
    if (this.getCountryCriteriaId() != null) {
      this.id += "-" + this.getCountryCriteriaId().toString();
    }
    if (this.getCategoryL1() != null && this.getCategoryL1().length() > 0) {
      this.id += "-" + this.getCategoryL1();
    }
    if (this.getClickType() != null && this.getClickType().length() > 0) {
      this.id += "-" + this.getClickType();
    }
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
  
  public Long getAggregatorId() {
    return aggregatorId;
  }
  
  public void setAggregatorId(Long aggregatorId) {
	  this.aggregatorId = aggregatorId;
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
  
  public Long getCountryCriteriaId() {
    return countryCriteriaId;
  }
  
  public void setCountryCriteriaId(Long countryCriteriaId) {
    this.countryCriteriaId = countryCriteriaId;
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
  
  public Long getLanguageCriteriaId() {
    return languageCriteriaId;
  }
  
  public void setLanguageCriteriaId(Long languageCriteriaId)
  {
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
    this.productTypeL1 = productTypeL5;
  }
  
  public Long getStoreId() {
    return storeId;
  }
  
  public void setStoreId(Long storeId) {
    this.storeId = storeId;
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
}
