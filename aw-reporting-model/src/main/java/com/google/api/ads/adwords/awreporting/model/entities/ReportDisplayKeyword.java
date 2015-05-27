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
import com.google.api.ads.adwords.awreporting.model.csv.annotation.MoneyField;
import com.google.api.ads.adwords.awreporting.model.util.BigDecimalUtil;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionReportType;
import com.google.common.collect.Lists;

import java.math.BigDecimal;

/**
 * Specific Report class for DisplayKeywordPerformanceReports
 *
 * @author zhuoc@google.com (Zhuo Chen)
 */
@Entity
@com.googlecode.objectify.annotation.Entity
@Table(name = "AW_ReportDisplayKeywordPerformance")
@CsvReport(value = ReportDefinitionReportType.DISPLAY_KEYWORD_PERFORMANCE_REPORT)
public class ReportDisplayKeyword extends ReportBase {

  @Column(name = "ADGROUP_ID")
  @CsvField(value = "Ad group ID", reportField = "AdGroupId")
  private Long adGroupId;

  @Column(name = "ADGROUP_NAME", length = 255)
  @CsvField(value = "Ad group", reportField = "AdGroupName")
  private String adGroupName;

  @Column(name = "ADGROUP_STATUS", length = 32)
  @CsvField(value = "Ad group state", reportField = "AdGroupStatus")
  private String adGroupStatus;
  
  @Column(name = "CAMPAIGN_ID")
  @CsvField(value = "Campaign ID", reportField = "CampaignId")
  private Long campaignId;

  @Column(name = "CAMPAIGN_NAME", length = 255)
  @CsvField(value = "Campaign", reportField = "CampaignName")
  private String campaignName;

  @Column(name = "CAMPAIGN_STATUS", length = 32)
  @CsvField(value = "Campaign state", reportField = "CampaignStatus")
  private String campaignStatus;

  @Column(name = "CPC_BID_SOURCE")
  @CsvField(value = "Max CPC source", reportField = "CpcBidSource")
  private String cpcBidSource;

  @Column(name = "CPM_BID_SOURCE")
  @CsvField(value = "Max CPM source", reportField = "CpmBidSource")
  private String cpmBidSource;

  @Column(name = "CRITERIA_DESTINATION_URL")
  @CsvField(value = "Destination URL", reportField = "CriteriaDestinationUrl")
  private String criteriaDestinationUrl;

  @Column(name = "Id")
  @CsvField(value = "Keyword ID", reportField = "Id")
  private Long keywordId;

  @Column(name = "IS_NEGATIVE")
  @CsvField(value = "Is negative", reportField = "IsNegative")
  private boolean negative;

  @Column(name = "IS_RESTRICT")
  @CsvField(value = "Is restricting", reportField = "IsRestrict")
  private boolean restrict;

  @Column(name = "KEYWORD_TEXT", length = 255)
  @CsvField(value = "Keyword", reportField = "KeywordText")
  private String keywordText;
  
  @Column(name = "MAX_CPC")
  @CsvField(value = "Max. CPC", reportField = "CpcBid")
  @MoneyField
  private String maxCpc;

  @Column(name = "MAX_CPM")
  @CsvField(value = "Max. CPM", reportField = "CpmBid")
  @MoneyField
  private String maxCpm;

  @Column(name = "TARGETING_SETTING")
  @CsvField(value = "Targeting Setting", reportField = "TargetingSetting")
  private boolean targetingSetting;
  
  @Column(name = "ACTIVE_VIEW_CPM")
  @CsvField(value = "Active View avg. CPM", reportField = "ActiveViewCpm")
  @MoneyField
  private BigDecimal activeViewCpm;
  
  @Column(name = "ACTIVE_VIEW_IMPRESSIONS")
  @CsvField(value = "Active View avg. CPM", reportField = "ActiveViewImpressions")
  private Long activeViewImpressions;
  
  @Column(name = "CONVERSION_TRACKER_ID")
  @CsvField(value = "Conversion Tracker Id", reportField = "ConversionTrackerId")
  private Long conversionTrackerId;
  
  @Column(name = "FINAL_APP_URLS", length=2048)
  @CsvField(value="App final URL", reportField = "FinalAppUrls")
  private String finalAppUrls;
  
  @Column(name = "FINAL_MOBILE_URLS", length=2048)
  @CsvField(value="Mobile final URL", reportField = "FinalMobileUrls")
  private String finalMobileUrls;
  
  @Column(name = "FINAL_URLS", length=2048)
  @CsvField(value="Final URL", reportField = "FinalUrls")
  private String finalUrls;
  
  @Column(name = "TRACKING_URL_TEMPLATE", length=2048)
  @CsvField(value = "Tracking template", reportField = "TrackingUrlTemplate")
  private String trackingUrlTemplate;
  
  @Column(name = "URL_CUSTOM_PARAMETERS", length=2048)
  @CsvField(value = "Custom parameter", reportField = "UrlCustomParameters")
  private String urlCustomParameters;

  /**
   * Hibernate needs an empty constructor
   */
  public ReportDisplayKeyword() {}

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
    if (this.getKeywordId() != null) {
      this.id += "-" + this.getKeywordId().toString();
    } else {
      this.id += "-";
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

  public String getCampaignStatus() {
    return campaignStatus;
  }

  public void setCampaignStatus(String campaignStatus) {
    this.campaignStatus = campaignStatus;
  }

  public String getCampaignName() {
    return campaignName;
  }

  public void setCampaignName(String campaignName) {
    this.campaignName = campaignName;
  }

  public Long getCampaignId() {
    return campaignId;
  }

  public void setCampaignId(Long campaignId) {
    this.campaignId = campaignId;
  }

  public String getCpcBidSource() {
    return cpcBidSource;
  }

  public void setCpcBidSource(String cpcBidSource) {
    this.cpcBidSource = cpcBidSource;
  }

  public String getCpmBidSource() {
    return cpmBidSource;
  }

  public void setCpmBidSource(String cpmBidSource) {
    this.cpmBidSource = cpmBidSource;
  }

  public String getCriteriaDestinationUrl() {
    return criteriaDestinationUrl;
  }

  public void setCriteriaDestinationUrl(String criteriaDestinationUrl) {
    this.criteriaDestinationUrl = criteriaDestinationUrl;
  }

  public Long getKeywordId() {
    return keywordId;
  }

  public void setKeywordId(Long keywordId) {
    this.keywordId = keywordId;
  }

  public boolean isNegative() {
    return negative;
  }

  public void setNegative(String negative) {
	  this.negative = Boolean.parseBoolean(negative);
  }

  public void setNegative(boolean negative) {
    this.negative = negative;
  }
  
  public boolean isRestrict() {
    return restrict;
  }
  
  public void setRestrict(String restrict) {
    this.restrict = Boolean.parseBoolean(restrict);
  }
  
  public void setRestrict(boolean restrict) {
    this.restrict = restrict;
  }
  
  public String getKeywordText() {
	  return keywordText;
  }
  
  public void setKeywordText(String keywordText) {
	  this.keywordText = keywordText;
  }

  public String getMaxCpc() {
    return maxCpc;
  }

  public void setMaxCpc(String maxCpc) {
    this.maxCpc = maxCpc;
  }

  public String getMaxCpm() {
    return maxCpm;
  }

  public void setMaxCpm(String maxCpm) {
    this.maxCpm = maxCpm;
  }

  public boolean getTargetingSetting() {
    return targetingSetting;
  }

  public void setTargetingSetting(String targetingSetting) {
    this.targetingSetting = Boolean.parseBoolean(targetingSetting);
  }
  
  public void setTargetingSetting(boolean targetingSetting) {
	  this.targetingSetting = targetingSetting;
  }
  
  public String getActiveViewCpm() {
    return BigDecimalUtil.formatAsReadable(activeViewCpm);
  }

  public BigDecimal getActiveViewCpmBigDecimal() {
    return activeViewCpm;
  }

  public void setActiveViewCpm(String activeViewCpm) {
    this.activeViewCpm = BigDecimalUtil.parseFromNumberStringPercentage(activeViewCpm);
  }
  
  public Long getActiveViewImpressions() {
    return activeViewImpressions;
  }
  
  public void setActiveViewImpressions(Long activeViewImpressions) {
    this.activeViewImpressions = activeViewImpressions;
  }
  
  public Long getConversionTrackerId() {
    return conversionTrackerId;
  }
  
  public void setConversionTrackerId(Long conversionTrackerId) {
    this.conversionTrackerId = conversionTrackerId;
  }
  
  public String getFinalAppUrls() {
    return finalAppUrls;
  }
  
  public boolean hasFinalAppUrl(String finalAppUrl) {
    if (finalAppUrls != null && finalAppUrls.length() > 0) {
      return Lists.newArrayList(finalAppUrls.split(";")).contains(finalAppUrl);
    } else {
      return false;
    }
  }
  
  public void setFinalAppUrls(String finalAppUrls) {
    this.finalAppUrls = finalAppUrls;
  }
  
  public String getFinalMobileUrls() {
    return finalMobileUrls;
  }
  
  public boolean hasFinalMobileUrl(String finalMobileUrl) {
    if (finalMobileUrls != null && finalMobileUrls.length() > 0) {
      return Lists.newArrayList(finalMobileUrls.split(";")).contains(finalMobileUrl);
    } else {
      return false;
    }
  }
  
  public void setFinalMobileUrls(String finalMobileUrls) {
    this.finalMobileUrls = finalMobileUrls;
  }
  
  public String getFinalUrls() {
    return finalUrls;
  }
  
  public boolean hasFinalUrl(String finalUrl) {
    if (finalUrls != null && finalUrls.length() > 0) {
      return Lists.newArrayList(finalUrls.split(";")).contains(finalUrl);
    } else {
      return false;
    }
  }
  
  public void setFinalUrls(String finalUrls) {
    this.finalUrls = finalUrls;
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
}
