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
import com.google.api.ads.adwords.awreporting.model.util.DateUtil;
import com.google.api.ads.adwords.lib.jaxb.v201506.ReportDefinitionReportType;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.joda.time.DateTime;

/**
 * Specific report class for ReportCampaign
 *
 * @author jtoledo@google.com (Julian Toledo)
 * @author gustavomoreira@google.com (Gustavo Moreira)
 */
@Entity
@com.googlecode.objectify.annotation.Entity
@Table(name = "AW_ReportCampaign")
@CsvReport(value = ReportDefinitionReportType.CAMPAIGN_PERFORMANCE_REPORT)
public class ReportCampaign extends ReportBase {

  @Column(name = "CAMPAIGN_ID")
  @CsvField(value = "Campaign ID", reportField = "CampaignId")
  private Long campaignId;

  @Column(name = "CAMPAIGN_NAME", length = 255)
  @CsvField(value = "Campaign", reportField = "CampaignName")
  private String campaignName;

  @Column(name = "CAMPAIGN_STATUS", length = 32)
  @CsvField(value = "Campaign state", reportField = "CampaignStatus")
  private String campaignStatus;

  @Column(name = "BUDGET")
  @CsvField(value = "Budget", reportField = "Amount")
  @MoneyField
  private BigDecimal budget;
  
  @Column(name = "BUDGET_ID")
  @CsvField(value = "Budget ID", reportField = "BudgetId")
  private Long budgetId;

  @Column(name = "CLICKCONVERSIONRATESIGNIFICANCE")
  @CsvField(value = "Click conversion rate ACE indicator", reportField = "ClickConversionRateSignificance")
  protected BigDecimal clickConversionRateSignificance;

  @Column(name = "CONVERSIONRATEMANYPERCLICKSIGNIFICANCE")
  @CsvField(value = "Conversion rate ACE indicator", reportField = "ConversionRateManyPerClickSignificance")
  protected BigDecimal conversionRateManyPerClickSignificance;
  
  @Column(name = "CONVERSIONMANYPERCLICKSIGNIFICANCE")
  @CsvField(value = "Conversion ACE indicator", reportField = "ConversionManyPerClickSignificance")
  protected BigDecimal conversionManyPerClickSignificance;

  @Column(name = "COSTPERCONVERSIONMANYPERCLICKSIGNIFICANCE")
  @CsvField(value = "Cost/conversion ACE indicator", reportField = "CostPerConversionManyPerClickSignificance")
  protected BigDecimal costPerConversionManyPerClickSignificance;
  
  @Column(name = "CONVERTEDCLICKSSIGNIFICANCE")
  @CsvField(value = "Converted clicks ACE indicator", reportField = "ConvertedClicksSignificance")
  private BigDecimal convertedClicksSignificance;

  @Column(name = "COSTPERCONVERTEDCLICKSIGNIFICANCE")
  @CsvField(value = "Cost/converted click ACE indicator", reportField = "CostPerConvertedClickSignificance")
  private BigDecimal costPerConvertedClickSignificance;

  @Column(name = "AVERAGE_FREQUENCY")
  @CsvField(value = "Avg. impr. freq. per cookie", reportField = "AverageFrequency")
  private BigDecimal averageFrequency;
  
  @Column(name = "AVERAGE_PAGEVIEWS")
  @CsvField(value = "Pages / visit", reportField = "AveragePageviews")
  private BigDecimal averagePageviews;

  @Column(name = "AVERAGE_TIME_ON_SITE")
  @CsvField(value = "Avg. visit duration (seconds)", reportField = "AverageTimeOnSite")
  private BigDecimal averageTimeOnSite;
  
  @Column(name = "BOUNCE_RATE")
  @CsvField(value = "Bounce rate", reportField = "BounceRate")
  private BigDecimal bounceRate;

  @Column(name = "PERCENT_NEW_VISITORS")
  @CsvField(value = "% new visits", reportField = "PercentNewVisitors")
  private BigDecimal percentNewVisitors;

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

  @Lob
  @Column(name = "LABELS", length = 2048)
  @CsvField(value = "Labels", reportField = "Labels")
  private String labels;

  @Column(name = "HOUR_OF_DAY")
  @CsvField(value = "Hour of day", reportField = "HourOfDay")
  private Long hourOfDay;

  @Column(name = "ADVERTISING_CHANNEL_TYPE", length = 32)
  @CsvField(value = "Advertising Channel", reportField = "AdvertisingChannelType")
  protected String advertisingChannelType;

  @Column(name = "ADVERTISING_CHANNEL_SUBTYPE", length = 32)
  @CsvField(value = "Advertising Sub Channel", reportField = "AdvertisingChannelSubType")
  protected String advertisingChannelSubType;
  
  @Column(name = "IMPRESSION_REACH")
  @CsvField(value = "Unique cookies", reportField = "ImpressionReach")
  private Long impressionReach;
  
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
  
  @Column(name = "TRACKING_URL_TEMPLATE", length=2048)
  @CsvField(value = "Tracking template", reportField = "TrackingUrlTemplate")
  private String trackingUrlTemplate;
  
  @Column(name = "URL_CUSTOM_PARAMETERS", length=2048)
  @CsvField(value = "Custom parameter", reportField = "UrlCustomParameters")
  private String urlCustomParameters;
  
  @Column(name = "GMAIL_FORWARDS")
  @CsvField(value = "Gmail forwards", reportField = "GmailForwards")
  private Long gmailForwards;

  @Column(name = "GMAIL_SAVES")
  @CsvField(value = "Gmail saves", reportField = "GmailSaves")
  private Long gmailSaves;

  @Column(name = "GMAIL_SECONDARY_CLICKS")
  @CsvField(value = "Gmail clicks to website", reportField = "GmailSecondaryClicks")
  private Long gmailSecondaryClicks;
  
  @Column(name = "START_DATE")
  @CsvField(value = "Start date", reportField = "StartDate")
  protected Date startDate;

  @Column(name = "END_DATE")
  @CsvField(value = "End date", reportField = "EndDate")
  protected Date endDate;
  
  @Column(name = "SLOT")
  @CsvField(value = "Top vs. Other", reportField = "Slot")
  private String slot;

  /**
   * Hibernate needs an empty constructor
   */
  public ReportCampaign() {}

  public ReportCampaign(Long topAccountId, Long accountId) {
    this.topAccountId = topAccountId;
    this.accountId = accountId;
  }

  @Override
  public void setId() {
    // Generating unique id after having accountId, campaignId and date
    if (this.getAccountId() != null && this.getCampaignId() != null) {
      this.id = this.getAccountId() + "-" + this.getCampaignId();
    }

    this.id += setIdDates();

    // Adding extra fields for unique ID
    this.id += getSegmentedId();
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

  public BigDecimal getBudget() {
    return budget;
  }

  public void setBudget(BigDecimal budget) {
    this.budget = budget;
  }  
  
  public Long getBudgetId() {
    return budgetId;
  }

  public void setBudgetId(Long budgetId) {
    this.budgetId = budgetId;
  }

  public String getClickConversionRateSignificance() {
    return BigDecimalUtil.formatAsReadable(clickConversionRateSignificance);
  }
  
  public BigDecimal getClickConversionRateSignificanceBigDecimal() {
    return clickConversionRateSignificance;
  }

  public void setClickConversionRateSignificance(String clickConversionRateSignificance) {
    this.clickConversionRateSignificance = BigDecimalUtil.parseFromNumberString(clickConversionRateSignificance);
  }

  public String getConversionRateManyPerClickSignificance() {
    return BigDecimalUtil.formatAsReadable(conversionRateManyPerClickSignificance);
  }
  
  public BigDecimal getConversionRateManyPerClickSignificanceBigDecimal() {
    return conversionRateManyPerClickSignificance;
  }

  public void setConversionRateManyPerClickSignificance(
      String conversionRateManyPerClickSignificance) {
    this.conversionRateManyPerClickSignificance = BigDecimalUtil.parseFromNumberString(conversionRateManyPerClickSignificance);
  }
  
  public String getConversionManyPerClickSignificance() {
    return BigDecimalUtil.formatAsReadable(conversionManyPerClickSignificance);
  }
  
  public BigDecimal getConversionManyPerClickSignificanceBigDecimal() {
    return conversionManyPerClickSignificance;
  }

  public void setConversionManyPerClickSignificance(String conversionManyPerClickSignificance) {
    this.conversionManyPerClickSignificance = BigDecimalUtil.parseFromNumberString(conversionManyPerClickSignificance);
  }
  
  public String getCostPerConversionManyPerClickSignificance() {
    return BigDecimalUtil.formatAsReadable(costPerConversionManyPerClickSignificance);
  }
  
  public BigDecimal getCostPerConversionManyPerClickSignificanceBigDecimal() {
    return costPerConversionManyPerClickSignificance;
  }

  public void setCostPerConversionManyPerClickSignificance(
      BigDecimal costPerConversionManyPerClickSignificance) {
    this.costPerConversionManyPerClickSignificance = costPerConversionManyPerClickSignificance;
  }

  public String getConvertedClicksSignificance() {
    return BigDecimalUtil.formatAsReadable(convertedClicksSignificance);
  }
  
  public BigDecimal getConvertedClicksSignificanceBigDecimal() {
    return convertedClicksSignificance;
  }

  public void setConvertedClicksSignificance(String convertedClicksSignificance) {
    this.convertedClicksSignificance = BigDecimalUtil.parseFromNumberString(convertedClicksSignificance);
  }
  
  public String getCostPerConvertedClickSignificance() {
    return BigDecimalUtil.formatAsReadable(costPerConvertedClickSignificance);
  }
  
  public BigDecimal getCostPerConvertedClickSignificanceBigDecimal() {
    return costPerConvertedClickSignificance;
  }

  public void setCostPerConvertedClickSignificance(String costPerConvertedClickSignificance) {
    this.costPerConvertedClickSignificance = BigDecimalUtil.parseFromNumberString(costPerConvertedClickSignificance);
  }
  
  public String getAverageFrequency() {
    return BigDecimalUtil.formatAsReadable(averageFrequency);
  }

  public BigDecimal getAverageFrequencyBigDecimal() {
    return averageFrequency;
  }
  
  public void setAverageFrequency(String averageFrequency) {
    this.averageFrequency =  BigDecimalUtil.parseFromNumberString(averageFrequency);
  }
  
  public String getAveragePageviews() {
    return BigDecimalUtil.formatAsReadable(averagePageviews);
  }

  public BigDecimal getAveragePageviewsBigDecimal() {
    return averagePageviews;
  }
  
  public void setAveragePageviews(String averagePageviews) {
    this.averagePageviews =  BigDecimalUtil.parseFromNumberString(averagePageviews);
  }

  public String getAverageTimeOnSite() {
    return BigDecimalUtil.formatAsReadable(averageTimeOnSite);
  }

  public BigDecimal getAverageTimeOnSiteBigDecimal() {
    return averageTimeOnSite;
  }
  
  public void setAverageTimeOnSite(String averageTimeOnSite) {
    this.averageTimeOnSite =  BigDecimalUtil.parseFromNumberString(averageTimeOnSite);
  }

  public String getBounceRate() {
    return BigDecimalUtil.formatAsReadable(bounceRate);
  }

  public BigDecimal getBounceRateBigDecimal() {
    return bounceRate;
  }
  
  public void setBounceRate(String bounceRate) {
    this.bounceRate =  BigDecimalUtil.parseFromNumberString(bounceRate);
  }

  public String getPercentNewVisitors() {
    return BigDecimalUtil.formatAsReadable(percentNewVisitors);
  }

  public BigDecimal getPercentNewVisitorsBigDecimal() {
    return percentNewVisitors;
  }
  
  public void setPercentNewVisitors(String percentNewVisitors) {
    this.percentNewVisitors =  BigDecimalUtil.parseFromNumberString(percentNewVisitors);
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
  
  public String getLabels() {
    return this.labels;
  }

  public boolean hasLabel(String label) {
    if (labels != null && labels.length() > 0) {
      return Lists.newArrayList(labels.split(";")).contains(label);
    } else {
      return false;
    }
  }

  public void setLabels(String labels) {
    this.labels = labels;
  }

  public Long getHourOfDay() {
    return hourOfDay;
  }
  
  public void setHourOfDay(Long hourOfDay) {
    this.hourOfDay = hourOfDay;
  }
  
  public String getAdvertisingChannelType() {
    return advertisingChannelType;
  }

  public void setAdvertisingChannelType(String advertisingChannelType) {
    this.advertisingChannelType = advertisingChannelType;
  }

  public String getAdvertisingChannelSubType() {
    return advertisingChannelSubType;
  }

  public void setAdvertisingChannelSubType(String advertisingChannelSubType) {
    this.advertisingChannelSubType = advertisingChannelSubType;
  }
  
  public Long getImpressionReach() {
    return impressionReach;
  }
  
  public void setImpressoinReach(Long impressionReach) {
    this.impressionReach = impressionReach;
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
  
  public Long getGmailForwards() {
    return gmailForwards;
  }
  
  public void setGmailForwards(Long gmailForwards) {
    this.gmailForwards = gmailForwards;
  }
  
  public Long getGmailSaves() {
    return gmailSaves;
  }
  
  public void setGmailSaves(Long gmailSaves) {
    this.gmailSaves = gmailSaves;
  }
  
  public Long getGmailSecondaryClicks() {
    return gmailSecondaryClicks;
  }
  
  public void setGmailSecondaryClicks(Long gmailSecondaryClicks) {
    this.gmailSecondaryClicks = gmailSecondaryClicks;
  }
  
  public String getSlot() {
    return slot;
  }

  public void setSlot(String slot) {
    this.slot = slot;
  }
  
  public String getStartDate() {
    if (startDate != null) {
      return DateUtil.formatYearMonthDay(startDate);
    } else {
      return null;
    }
  }

  public void setStartDate(DateTime startDate) {
    this.startDate = new DateTime(startDate).toDate();
  }

  public void setStartDate(String startDate) {
    try {
      DateTime parseDateTime = DateUtil.parseDateTime(startDate);
      if (parseDateTime != null) {
        this.startDate = parseDateTime.toDate();
      }
    } catch (IllegalArgumentException e) {
      this.startDate = null;
    }
  }
  
  public String getEndDate() {
    if (endDate != null) {
      return DateUtil.formatYearMonthDay(endDate);
    } else {
      return null;
    }
  }

  public void setEndDate(DateTime endDate) {
    this.endDate = new DateTime(endDate).toDate();
  }

  public void setEndDate(String endDate) {
    try {
      DateTime parseDateTime = DateUtil.parseDateTime(endDate);
      if (parseDateTime != null) {
        this.endDate = parseDateTime.toDate();
      }
    } catch (IllegalArgumentException e) {
      this.endDate = null;
    }
  }
}
