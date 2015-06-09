package com.google.api.ads.adwords.awreporting.model.entities;

import com.google.api.ads.adwords.awreporting.model.csv.annotation.CsvField;
import com.google.api.ads.adwords.awreporting.model.csv.annotation.CsvReport;
import com.google.api.ads.adwords.awreporting.model.util.BigDecimalUtil;
import com.google.api.ads.adwords.awreporting.model.util.DateUtil;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionReportType;

import com.googlecode.objectify.annotation.Index;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * This is an experimental report for video campaign reports downloaded directly from the interface.
 *
 * @author Gustavo Moreira
 */
@Entity
@com.googlecode.objectify.annotation.Entity
@Table(name = "AW_ReportVideoCampaign")
@CsvReport(value = ReportDefinitionReportType.UNKNOWN, fileOnlyReportType = "VIDEO_CAMPAIGN_REPORT")
public class ReportVideoCampaign extends Report {

  // Date Segments
  @Index
  @Column(name = "DAY")
  @CsvField(value = "Day", reportField = "")
  private Date day;

  @Column(name = "DAYOFWEEK")
  @CsvField(value = "Day of week", reportField = "")
  private String dayOfWeek;

  @Column(name = "WEEK")
  @CsvField(value = "Week", reportField = "")
  private String week;

  @Index
  @Column(name = "MONTH")
  @CsvField(value = "Month", reportField = "")
  private Date month;

  @Column(name = "MONTH_OF_YEAR")
  @CsvField(value = "Month of Year", reportField = "")
  private String monthOfYear;

  @Column(name = "QUARTER")
  @CsvField(value = "Quarter", reportField = "")
  private String quarter;

  @Column(name = "YEAR")
  @CsvField(value = "Year", reportField = "")
  private Long year;

  // Main Metrics
  @Column(name = "COST")
  @CsvField(value = "Cost", reportField = "")
  private BigDecimal cost;

  @Column(name = "CTR")
  @CsvField(value = "CTR", reportField = "")
  private BigDecimal ctr;

  // Main Segments
  @Column(name = "DEVICE", length = 64)
  @CsvField(value = "Device", reportField = "")
  private String device;

  @Column(name = "NETWORK", length = 32)
  @CsvField(value = "Network", reportField = "")
  private String adNetwork;

  @Column(name = "NETWORK_PARTNERS", length = 32)
  @CsvField(value = "Network (with search partners)", reportField = "")
  private String adNetworkPartners;

  // Attributes
  @Column(name = "CAMPAIGN_NAME", length = 255)
  @CsvField(value = "Campaign", reportField = "")
  private String campaignName;

  @Column(name = "ACCOUNT", length = 255)
  @CsvField(value = "Account", reportField = "")
  private String account;

  @Column(name = "STATUS", length = 32)
  @CsvField(value = "Campaign state", reportField = "")
  private String status;

  @Column(name = "BUDGET")
  @CsvField(value = "Budget", reportField = "")
  private Long budget;

  @Column(name = "IMPRESSIONS")
  @CsvField(value = "Impressions", reportField = "")
  private Long impressions;

  @Column(name = "THUMBNAIL_IMPRESSIONS")
  @CsvField(value = "Thumbnail Impressions", reportField = "")
  private Long thumbnailImpressions;

  @Column(name = "VIDEO_IMPRESSIONS")
  @CsvField(value = "Video Impressions", reportField = "")
  private Long videoImpressions;

  @Column(name = "VIEW_RATE")
  @CsvField(value = "View rate", reportField = "")
  private BigDecimal viewRate;

  @Column(name = "VIEW_PLAYED_25")
  @CsvField(value = "Video played to 25%", reportField = "")
  private BigDecimal viewPlayed25;

  @Column(name = "VIEW_PLAYED_50")
  @CsvField(value = "Video played to 50%", reportField = "")
  private BigDecimal viewPlayed50;

  @Column(name = "VIEW_PLAYED_75")
  @CsvField(value = "Video played to 75%", reportField = "")
  private BigDecimal viewPlayed75;

  @Column(name = "VIEW_PLAYED_100")
  @CsvField(value = "Video played to 100%", reportField = "")
  private BigDecimal viewPlayed100;

  @Column(name = "VIEWS")
  @CsvField(value = "Views", reportField = "")
  private Long views;

  @Column(name = "AVERAGE_CPV")
  @CsvField(value = "Avg. CPV", reportField = "")
  private BigDecimal avgCpv;

  @Column(name = "WEBSITE_CLICKS")
  @CsvField(value = "Website clicks", reportField = "")
  private Long websiteClicks;

  @Override
  public void setId() {
    // Generating unique id after having accountId, campaignId and date
    if (this.getAccountId() != null) {
      this.id = "" + this.getAccountId();
    }
    if (this.getCampaignName() != null) {
      this.id += "-" + this.getCampaignName();
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
  }

  public String getDay() {
    if (day != null) {
      return DateUtil.formatYearMonthDay(day);
    } else {
      return null;
    }
  }

  public void setDay(DateTime day) {
    this.day = new DateTime(day).toDate();
  }

  public void setDay(String day) {
    try {
      DateTime parseDateTime = DateUtil.parseDateTime(day);
      if (parseDateTime != null) {
        this.day = parseDateTime.toDate();
      }
    } catch (IllegalArgumentException e) {
      this.day = null;
    }
  }

  public String getMonth() {
    if (month != null) {
      return DateUtil.formatYearMonthDay(month);
    } else {
      return null;
    }
  }

  public DateTime getMonthDateTime() {
    return new DateTime(month);
  }

  public void setMonth(DateTime month) {
    this.month = new DateTime(month).toDate();
  }

  public void setMonth(String month) {
    try {
      DateTime parseDateTime = DateUtil.parseDateTime(month);
      if (parseDateTime != null) {
        this.month = parseDateTime.toDate();
      }
    } catch (IllegalArgumentException e) {
      this.month = null;
    }
  }

  public String getMonthOfYear() {
    return monthOfYear;
  }

  public void setMonthOfYear(String monthOfYear) {
    this.monthOfYear = monthOfYear;
  }

  public String getDayOfWeek() {
    return dayOfWeek;
  }

  public void setDayOfWeek(String dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }

  public String getWeek() {
    return week;
  }

  public void setWeek(String week) {
    this.week = week;
  }

  public String getQuarter() {
    return quarter;
  }

  public void setQuarter(String quarter) {
    this.quarter = quarter;
  }

  public Long getYear() {
    return year;
  }

  public void setYear(Long year) {
    this.year = year;
  }

  public String getCost() {
    return BigDecimalUtil.formatAsReadable(cost);
  }

  public BigDecimal getCostBigDecimal() {
    return cost;
  }

  public void setCost(String cost) {
    this.cost = BigDecimalUtil.parseFromNumberString(cost);
  }

  public String getCtr() {
    return BigDecimalUtil.formatAsReadable(ctr);
  }

  public BigDecimal getCtrBigDecimal() {
    return ctr;
  }

  public void setCtr(String ctr) {
    this.ctr = BigDecimalUtil.parseFromNumberString(ctr);
  }

  public String getDevice() {
    return device;
  }

  public void setDevice(String device) {
    this.device = device;
  }

  public String getAdNetwork() {
    return adNetwork;
  }

  public void setAdNetwork(String adNetwork) {
    this.adNetwork = adNetwork;
  }

  public String getAdNetworkPartners() {
    return adNetworkPartners;
  }

  public void setAdNetworkPartners(String adNetworkPartners) {
    this.adNetworkPartners = adNetworkPartners;
  }

  public String getCampaignName() {
    return campaignName;
  }

  public void setCampaignName(String campaignName) {
    this.campaignName = campaignName;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Long getBudget() {
    return budget;
  }

  public void setBudget(Long budget) {
    this.budget = budget;
  }

  public Long getImpressions() {
    return impressions;
  }

  public void setImpressions(Long impressions) {
    this.impressions = impressions;
  }

  public Long getThumbnailImpressions() {
    return thumbnailImpressions;
  }

  public void setThumbnailImpressions(Long thumbnailImpressions) {
    this.thumbnailImpressions = thumbnailImpressions;
  }

  public Long getVideoImpressions() {
    return videoImpressions;
  }

  public void setVideoImpressions(Long videoImpressions) {
    this.videoImpressions = videoImpressions;
  }

  public String getViewRate() {
    return BigDecimalUtil.formatAsReadable(viewRate);
  }

  public BigDecimal getViewRateBigDecimal() {
    return viewRate;
  }

  public void setViewRate(String viewRate) {
    this.viewRate = BigDecimalUtil.parseFromNumberStringPercentage(viewRate);
  }

  public String getViewPlayed25() {
    return BigDecimalUtil.formatAsReadable(viewPlayed25);
  }

  public BigDecimal getViewPlayed25BigDecimal() {
    return viewPlayed25;
  }

  public void setViewPlayed25(String viewPlayed25) {
    this.viewPlayed25 = BigDecimalUtil.parseFromNumberStringPercentage(viewPlayed25);
  }

  public String getViewPlayed50() {
    return BigDecimalUtil.formatAsReadable(viewPlayed50);
  }

  public BigDecimal getViewPlayed50BigDecimal() {
    return viewPlayed50;
  }

  public void setViewPlayed50(String viewPlayed50) {
    this.viewPlayed50 = BigDecimalUtil.parseFromNumberStringPercentage(viewPlayed50);
  }

  public String getViewPlayed75() {
    return BigDecimalUtil.formatAsReadable(viewPlayed75);
  }

  public BigDecimal getViewPlayed75BigDecimal() {
    return viewPlayed75;
  }

  public void setViewPlayed75(String viewPlayed75) {
    this.viewPlayed75 = BigDecimalUtil.parseFromNumberStringPercentage(viewPlayed75);
  }

  public String getViewPlayed100() {
    return BigDecimalUtil.formatAsReadable(viewPlayed100);
  }

  public BigDecimal getViewPlayed100BigDecimal() {
    return viewPlayed100;
  }

  public void setViewPlayed100(String viewPlayed100) {
    this.viewPlayed100 = BigDecimalUtil.parseFromNumberStringPercentage(viewPlayed100);
  }

  public Long getViews() {
    return views;
  }

  public void setViews(Long views) {
    this.views = views;
  }

  public String getAvgCpv() {
    return BigDecimalUtil.formatAsReadable(avgCpv);
  }

  public BigDecimal getAvgCpvBigDecimal() {
    return avgCpv;
  }

  public void setAvgCpv(String avgCpv) {
    this.avgCpv = BigDecimalUtil.parseFromNumberString(avgCpv);
  }

  public Long getWebsiteClicks() {
    return websiteClicks;
  }

  public void setWebsiteClicks(Long websiteClicks) {
    this.websiteClicks = websiteClicks;
  }
}
