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

package com.google.api.ads.adwords.awreporting.model.entities;

import com.google.api.ads.adwords.awreporting.model.csv.annotation.CsvField;
import com.google.api.ads.adwords.awreporting.model.csv.annotation.CsvReport;
import com.google.api.ads.adwords.awreporting.model.csv.annotation.MoneyField;
import com.google.api.ads.adwords.awreporting.model.util.BigDecimalUtil;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionReportType;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Specific report class for ReportAccount
 *
 * @author jtoledo@google.com (Julian Toledo)
 */
@Entity
@com.googlecode.objectify.annotation.Entity
@Table(name = "AW_ReportAccount")
@CsvReport(value = ReportDefinitionReportType.ACCOUNT_PERFORMANCE_REPORT)
public class ReportAccount extends ReportBase {

  @Column(name = "SEARCH_IMPRESSION_SHARE")
  @CsvField(value = "Search Impr. share", reportField = "SearchImpressionShare")
  private BigDecimal searchImpressionShare;

  @Column(name = "SEARCH_LOST_IS_BUDGET")
  @CsvField(value = "Search Lost IS (budget)", reportField = "SearchBudgetLostImpressionShare")
  private BigDecimal searchLostISBudget;

  @Column(name = "SEARCH_LOST_IS_RANK")
  @CsvField(value = "Search Lost IS (rank)", reportField = "SearchRankLostImpressionShare")
  private BigDecimal searchLostISRank;

  @Column(name = "CONTENT_IMPRESSION_SHARE")
  @CsvField(value = "Content Impr. share", reportField = "ContentImpressionShare")
  private BigDecimal contentImpressionShare;

  @Column(name = "CONTENT_LOST_IS_BUDGET")
  @CsvField(value = "Content Lost IS (budget)", reportField = "ContentBudgetLostImpressionShare")
  private BigDecimal contentLostISBudget;

  @Column(name = "CONTENT_LOST_IS_RANK")
  @CsvField(value = "Content Lost IS (rank)", reportField = "ContentRankLostImpressionShare")
  private BigDecimal contentLostISRank;

  @Column(name = "SEARCH_EXACT_MATCH_IMPRESSION_SHARE")
  @CsvField(value = "Search Exact match IS", reportField = "SearchExactMatchImpressionShare")
  private BigDecimal searchExactMatchImpressionShare;

  @Column(name = "HOUR_OF_DAY")
  @CsvField(value = "Hour of day", reportField = "HourOfDay")
  private Long hourOfDay;
  
  @Column(name = "CAN_MANAGE_CLIENTS")
  @CsvField(value = "Can manage clients", reportField = "CanManageClients")
  private String canManageClients;

  @Column(name = "IS_AUTO_TAGGING_ENABLED")
  @CsvField(value = "Auto tagging enabled", reportField = "IsAutoTaggingEnabled")
  private String isAutoTaggingEnabled;
  
  @Column(name = "IS_TEST_ACCOUNT")
  @CsvField(value = "Test account", reportField = "IsTestAccount")
  private String isTestAccount;
  
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
  
  /**
   * Hibernate needs an empty constructor
   */
  public ReportAccount() {
  }

  public ReportAccount(Long topAccountId, Long accountId) {
    this.topAccountId = topAccountId;
    this.accountId = accountId;
  }

  @Override
  public void setId() {
    // Generating unique id after having date and accountId
    this.id = this.getAccountId().toString();

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
    if (this.getHourOfDay() != null) {
      this.id += "-" + this.getHourOfDay();
    }
    if (this.getConversionTypeName() != null) {
      this.id += "-" + this.getConversionTypeName();
    }
  }

  public String getSearchImpressionShare() {
    return BigDecimalUtil.formatAsReadable(this.searchImpressionShare);
  }

  public BigDecimal getSearchImpressionShareBigDecimal() {
    return searchImpressionShare;
  }

  public void setSearchImpressionShare(String searchImpressionShare) {
    this.searchImpressionShare = BigDecimalUtil.parseFromNumberStringPercentage(searchImpressionShare);
  }
  
  public String getSearchLostISBudget() {
    return BigDecimalUtil.formatAsReadable(this.searchLostISBudget);
  }

  public BigDecimal getSearchLostISBudgetBigDecimal() {
    return searchLostISBudget;
  }

  public void setSearchLostISBudget(String lostISBudget) {
    this.searchLostISBudget = BigDecimalUtil.parseFromNumberStringPercentage(lostISBudget);
  }

  public String getSearchLostISRank() {
    return BigDecimalUtil.formatAsReadable(this.searchLostISRank);
  }

  public BigDecimal getSearchLostISRankBigDecimal() {
    return searchLostISRank;
  }

  public void setSearchLostISRank(String lostISRank) {
    this.searchLostISRank = BigDecimalUtil.parseFromNumberStringPercentage(lostISRank);
  }

  public String getContentImpressionShare() {
    return BigDecimalUtil.formatAsReadable(this.contentImpressionShare);
  }

  public BigDecimal getContentImpressionShareBigDecimal() {
    return contentImpressionShare;
  }

  public void setContentImpressionShare(String contentImpressionShare) {
    this.contentImpressionShare = BigDecimalUtil.parseFromNumberStringPercentage(contentImpressionShare);
  }

  public String getContentLostISBudget() {
    return BigDecimalUtil.formatAsReadable(this.contentLostISBudget);
  }

  public BigDecimal getContentLostISBudgetBigDecimal() {
    return contentLostISBudget;
  }

  public void setContentLostISBudget(String lostISBudget) {
    this.contentLostISBudget = BigDecimalUtil.parseFromNumberStringPercentage(lostISBudget);
  }

  public String getContentLostISRank() {
    return BigDecimalUtil.formatAsReadable(this.contentLostISRank);
  }

  public BigDecimal getContentLostISRankBigDecimal() {
    return contentLostISRank;
  }

  public void setContentLostISRank(String lostISRank) {
    this.contentLostISRank = BigDecimalUtil.parseFromNumberStringPercentage(lostISRank);
  }
  
  public String getSearchExactMatchImpressionShare() {
    return BigDecimalUtil.formatAsReadable(this.searchExactMatchImpressionShare);
  }

  public BigDecimal getSearchExactMatchImpressionShareBigDecimal() {
    return searchExactMatchImpressionShare;
  }

  public void setSearchExactMatchImpressionShare(String searchExactMatchImpressionShare) {
    this.searchExactMatchImpressionShare = BigDecimalUtil.parseFromNumberStringPercentage(searchExactMatchImpressionShare);
  }

  public Long getHourOfDay() {
    return hourOfDay;
  }
  
  public void setHourOfDay(Long hourOfDay) {
    this.hourOfDay = hourOfDay;
  }
  
  public String getCanManageClients() {
    return canManageClients;
  }
  
  public void setCanManageClients(String canManageClients) {
    this.canManageClients = canManageClients;
  }
  
  public String getIsAutoTaggingEnabled() {
    return isAutoTaggingEnabled;
  }
  
  public void setIsAutoTaggingEnabled(String isAutoTaggingEnabled) {
    this.isAutoTaggingEnabled = isAutoTaggingEnabled;
  }
  
  public String getIsTestAccount() {
    return isTestAccount;
  }
  
  public void setIsTestAccount(String isTestAccount) {
    this.isTestAccount = isTestAccount;
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
}
