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
import com.google.api.ads.adwords.lib.jaxb.v201809.ReportDefinitionReportType;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
   * Specific report class for PaidOrganicQueryReport.
 *
*/
@Entity
@Table(name = "AW_PaidOrganicQueryReport")
@CsvReport(value = ReportDefinitionReportType.PAID_ORGANIC_QUERY_REPORT)
public class PaidOrganicQueryReport extends DateReport {

  @Column(name = "KeywordId")
  @CsvField(value = "Keyword ID", reportField = "KeywordId")
  private String keywordId;

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

  @Column(name = "AverageCpc")
  @CsvField(value = "Ad Avg. CPC", reportField = "AverageCpc")
  @MoneyField
  private BigDecimal averageCpc;

  @Column(name = "AveragePosition")
  @CsvField(value = "Ad Avg. position", reportField = "AveragePosition")
  private BigDecimal averagePosition;

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
  @CsvField(value = "Ad Clicks", reportField = "Clicks")
  private Long clicks;

  @Column(name = "CombinedAdsOrganicClicks")
  @CsvField(value = "Combined Clicks", reportField = "CombinedAdsOrganicClicks")
  private Long combinedAdsOrganicClicks;

  @Column(name = "CombinedAdsOrganicClicksPerQuery")
  @CsvField(value = "Combined Clicks/Query", reportField = "CombinedAdsOrganicClicksPerQuery")
  private BigDecimal combinedAdsOrganicClicksPerQuery;

  @Column(name = "CombinedAdsOrganicQueries")
  @CsvField(value = "Combined Queries", reportField = "CombinedAdsOrganicQueries")
  private Long combinedAdsOrganicQueries;

  @Column(name = "Ctr")
  @CsvField(value = "Ad CTR", reportField = "Ctr")
  private BigDecimal ctr;

  @Column(name = "CustomerDescriptiveName")
  @CsvField(value = "Client name", reportField = "CustomerDescriptiveName")
  private String customerDescriptiveName;

  @Column(name = "Impressions")
  @CsvField(value = "Ad Impressions", reportField = "Impressions")
  private Long impressions;

  @Column(name = "KeywordTextMatchingQuery")
  @CsvField(value = "Keyword", reportField = "KeywordTextMatchingQuery")
  private String keywordTextMatchingQuery;

  @Column(name = "OrganicAveragePosition")
  @CsvField(value = "Organic Average Position", reportField = "OrganicAveragePosition")
  private BigDecimal organicAveragePosition;

  @Column(name = "OrganicClicks")
  @CsvField(value = "Organic Clicks", reportField = "OrganicClicks")
  private Long organicClicks;

  @Column(name = "OrganicClicksPerQuery")
  @CsvField(value = "Organic Clicks/Query", reportField = "OrganicClicksPerQuery")
  private BigDecimal organicClicksPerQuery;

  @Column(name = "OrganicImpressions")
  @CsvField(value = "Organic Listings", reportField = "OrganicImpressions")
  private Long organicImpressions;

  @Column(name = "OrganicImpressionsPerQuery")
  @CsvField(value = "Organic Listings/Query", reportField = "OrganicImpressionsPerQuery")
  private BigDecimal organicImpressionsPerQuery;

  @Column(name = "OrganicQueries")
  @CsvField(value = "Organic Queries", reportField = "OrganicQueries")
  private Long organicQueries;

  @Column(name = "QueryMatchType")
  @CsvField(value = "Match type", reportField = "QueryMatchType")
  private String queryMatchType;

  @Column(name = "SearchQuery")
  @CsvField(value = "Query", reportField = "SearchQuery")
  private String searchQuery;

  @Column(name = "SerpType")
  @CsvField(value = "Search Result Type", reportField = "SerpType")
  private String serpType;

  /**
   * Hibernate needs an empty constructor
   */
  public PaidOrganicQueryReport() {
  }

  public PaidOrganicQueryReport(Long topAccountId, Long accountId){
    super(topAccountId, accountId);
  }

  public String getKeywordId() {
    return keywordId;
  }

  public void setKeywordId(String keywordId) {
    this.keywordId = keywordId;
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

  public BigDecimal getAverageCpc() {
    return averageCpc;
  }

  public void setAverageCpc(BigDecimal averageCpc) {
    this.averageCpc = averageCpc;
  }

  public String getAveragePosition() {
    return BigDecimalUtil.formatAsReadable(averagePosition);
  }

  public BigDecimal getAveragePositionBigDecimal() {
    return averagePosition;
  }

  public void setAveragePosition(String averagePosition) {
    this.averagePosition = BigDecimalUtil.parseFromNumberString(averagePosition);
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

  public Long getCombinedAdsOrganicClicks() {
    return combinedAdsOrganicClicks;
  }

  public void setCombinedAdsOrganicClicks(Long combinedAdsOrganicClicks) {
    this.combinedAdsOrganicClicks = combinedAdsOrganicClicks;
  }

  public String getCombinedAdsOrganicClicksPerQuery() {
    return BigDecimalUtil.formatAsReadable(combinedAdsOrganicClicksPerQuery);
  }

  public BigDecimal getCombinedAdsOrganicClicksPerQueryBigDecimal() {
    return combinedAdsOrganicClicksPerQuery;
  }

  public void setCombinedAdsOrganicClicksPerQuery(String combinedAdsOrganicClicksPerQuery) {
    this.combinedAdsOrganicClicksPerQuery = BigDecimalUtil.parseFromNumberString(combinedAdsOrganicClicksPerQuery);
  }

  public Long getCombinedAdsOrganicQueries() {
    return combinedAdsOrganicQueries;
  }

  public void setCombinedAdsOrganicQueries(Long combinedAdsOrganicQueries) {
    this.combinedAdsOrganicQueries = combinedAdsOrganicQueries;
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

  public Long getImpressions() {
    return impressions;
  }

  public void setImpressions(Long impressions) {
    this.impressions = impressions;
  }

  public String getKeywordTextMatchingQuery() {
    return keywordTextMatchingQuery;
  }

  public void setKeywordTextMatchingQuery(String keywordTextMatchingQuery) {
    this.keywordTextMatchingQuery = keywordTextMatchingQuery;
  }

  public String getOrganicAveragePosition() {
    return BigDecimalUtil.formatAsReadable(organicAveragePosition);
  }

  public BigDecimal getOrganicAveragePositionBigDecimal() {
    return organicAveragePosition;
  }

  public void setOrganicAveragePosition(String organicAveragePosition) {
    this.organicAveragePosition = BigDecimalUtil.parseFromNumberString(organicAveragePosition);
  }

  public Long getOrganicClicks() {
    return organicClicks;
  }

  public void setOrganicClicks(Long organicClicks) {
    this.organicClicks = organicClicks;
  }

  public String getOrganicClicksPerQuery() {
    return BigDecimalUtil.formatAsReadable(organicClicksPerQuery);
  }

  public BigDecimal getOrganicClicksPerQueryBigDecimal() {
    return organicClicksPerQuery;
  }

  public void setOrganicClicksPerQuery(String organicClicksPerQuery) {
    this.organicClicksPerQuery = BigDecimalUtil.parseFromNumberString(organicClicksPerQuery);
  }

  public Long getOrganicImpressions() {
    return organicImpressions;
  }

  public void setOrganicImpressions(Long organicImpressions) {
    this.organicImpressions = organicImpressions;
  }

  public String getOrganicImpressionsPerQuery() {
    return BigDecimalUtil.formatAsReadable(organicImpressionsPerQuery);
  }

  public BigDecimal getOrganicImpressionsPerQueryBigDecimal() {
    return organicImpressionsPerQuery;
  }

  public void setOrganicImpressionsPerQuery(String organicImpressionsPerQuery) {
    this.organicImpressionsPerQuery = BigDecimalUtil.parseFromNumberString(organicImpressionsPerQuery);
  }

  public Long getOrganicQueries() {
    return organicQueries;
  }

  public void setOrganicQueries(Long organicQueries) {
    this.organicQueries = organicQueries;
  }

  public String getQueryMatchType() {
    return queryMatchType;
  }

  public void setQueryMatchType(String queryMatchType) {
    this.queryMatchType = queryMatchType;
  }

  public String getSearchQuery() {
    return searchQuery;
  }

  public void setSearchQuery(String searchQuery) {
    this.searchQuery = searchQuery;
  }

  public String getSerpType() {
    return serpType;
  }

  public void setSerpType(String serpType) {
    this.serpType = serpType;
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
    if (keywordId != null) {
      idBuilder.append("-").append(keywordId);
    }
    idBuilder.append("-").append(getDateLabel());

    // Include all segmentation fields (if set).
    if (!StringUtils.isEmpty(queryMatchType)) {
      idBuilder.append("-").append(queryMatchType);
    }
    if (!StringUtils.isEmpty(serpType)) {
      idBuilder.append("-").append(serpType);
    }
    this.rowId = idBuilder.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) { return false; }
    if (obj == this) { return true; }
    if (obj.getClass() != getClass()) { return false; }
    PaidOrganicQueryReport other = (PaidOrganicQueryReport) obj;
    return new EqualsBuilder()
      .appendSuper(super.equals(obj))
      .append(keywordId, other.keywordId)
      .append(accountCurrencyCode, other.accountCurrencyCode)
      .append(accountDescriptiveName, other.accountDescriptiveName)
      .append(accountTimeZone, other.accountTimeZone)
      .append(adGroupId, other.adGroupId)
      .append(adGroupName, other.adGroupName)
      .append(adGroupStatus, other.adGroupStatus)
      .append(averageCpc, other.averageCpc)
      .append(averagePosition, other.averagePosition)
      .append(campaignId, other.campaignId)
      .append(campaignName, other.campaignName)
      .append(campaignStatus, other.campaignStatus)
      .append(clicks, other.clicks)
      .append(combinedAdsOrganicClicks, other.combinedAdsOrganicClicks)
      .append(combinedAdsOrganicClicksPerQuery, other.combinedAdsOrganicClicksPerQuery)
      .append(combinedAdsOrganicQueries, other.combinedAdsOrganicQueries)
      .append(ctr, other.ctr)
      .append(customerDescriptiveName, other.customerDescriptiveName)
      .append(impressions, other.impressions)
      .append(keywordTextMatchingQuery, other.keywordTextMatchingQuery)
      .append(organicAveragePosition, other.organicAveragePosition)
      .append(organicClicks, other.organicClicks)
      .append(organicClicksPerQuery, other.organicClicksPerQuery)
      .append(organicImpressions, other.organicImpressions)
      .append(organicImpressionsPerQuery, other.organicImpressionsPerQuery)
      .append(organicQueries, other.organicQueries)
      .append(queryMatchType, other.queryMatchType)
      .append(searchQuery, other.searchQuery)
      .append(serpType, other.serpType)
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
      .appendSuper(super.hashCode())
      .append(keywordId)
      .append(accountCurrencyCode)
      .append(accountDescriptiveName)
      .append(accountTimeZone)
      .append(adGroupId)
      .append(adGroupName)
      .append(adGroupStatus)
      .append(averageCpc)
      .append(averagePosition)
      .append(campaignId)
      .append(campaignName)
      .append(campaignStatus)
      .append(clicks)
      .append(combinedAdsOrganicClicks)
      .append(combinedAdsOrganicClicksPerQuery)
      .append(combinedAdsOrganicQueries)
      .append(ctr)
      .append(customerDescriptiveName)
      .append(impressions)
      .append(keywordTextMatchingQuery)
      .append(organicAveragePosition)
      .append(organicClicks)
      .append(organicClicksPerQuery)
      .append(organicImpressions)
      .append(organicImpressionsPerQuery)
      .append(organicQueries)
      .append(queryMatchType)
      .append(searchQuery)
      .append(serpType)
      .toHashCode();
  }

}
