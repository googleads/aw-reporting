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
import com.google.api.ads.adwords.jaxws.extensions.report.model.csv.annotation.MoneyField;
import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.dateranges.DateRangeHandler;
import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.dateranges.Last14DaysDateRangeHandler;
import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.dateranges.Last30DaysDateRangeHandler;
import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.dateranges.Last7DaysDateRangeHandler;
import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.dateranges.LastMonthDateRangeHandler;
import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.dateranges.LastWeekDateRangeHandler;
import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.dateranges.ThisMonthDateRangeHandler;
import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.dateranges.TodayDateRangeHandler;
import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.dateranges.YesterdayDateRangeHandler;
import com.google.api.ads.adwords.jaxws.extensions.report.model.util.BigDecimalUtil;
import com.google.api.ads.adwords.jaxws.extensions.report.model.util.DateUtil;
import com.google.api.ads.adwords.lib.jaxb.v201406.ReportDefinitionDateRangeType;
import com.google.api.client.util.Maps;

import com.googlecode.objectify.annotation.Index;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * The base abstract class with base report fields
 *
 *  Fields from http://code.google.com/apis/adwords/docs/appendix/reports.html Fields from
 * http://code.google.com/apis/adwords/docs/reportguide.html
 *
 * @author jtoledo@google.com (Julian Toledo)
 * @author gustavomoreira@google.com (Gustavo Moreira)
 */
@MappedSuperclass
public abstract class ReportBase extends Report {

  public static final String DAYOFWEEK = "dayOfWeek";
  public static final String WEEK = "week";
  public static final String MONTH = "month";
  public static final String QUARTER = "quarter";
  public static final String YEAR = "year";

  // General
  @Column(name = "ACCOUNT_DESCRIPTIVE_NAME", length = 255)
  @CsvField(value = "Account", reportField = "AccountDescriptiveName")
  protected String accountDescriptiveName;

  @Column(name = "ACCOUNTTIMEZONEID")
  @CsvField(value = "Time zone", reportField = "AccountTimeZoneId")
  protected String accountTimeZoneId;

  @Column(name = "CUSTOMER_DESCRIPTIVE_NAME")
  @CsvField(value = "Client name", reportField = "CustomerDescriptiveName")
  protected String customerDescriptiveName;

  @Column(name = "PRIMARYCOMPANYNAME")
  @CsvField(value = "Company name", reportField = "PrimaryCompanyName")
  protected String primaryCompanyName;

  @Column(name = "CURRENCY_CODE", length = 6)
  @CsvField(value = "Currency", reportField = "AccountCurrencyCode")
  protected String currencyCode;

  // Date Segments
  @Index
  @Column(name = "DAY")
  @CsvField(value = "Day", reportField = "Date")
  protected Date day;

  @Column(name = "DAYOFWEEK")
  @CsvField(value = "Day of week", reportField = "DayOfWeek")
  protected String dayOfWeek;

  @Column(name = "WEEK")
  @CsvField(value = "Week", reportField = "Week")
  protected String week;

  @Index
  @Column(name = "MONTH")
  @CsvField(value = "Month", reportField = "Month")
  protected Date month;

  @Column(name = "MONTH_OF_YEAR")
  @CsvField(value = "Month of Year", reportField = "MonthOfYear")
  protected String monthOfYear;

  @Column(name = "QUARTER")
  @CsvField(value = "Quarter", reportField = "Quarter")
  private String quarter;

  @Column(name = "YEAR")
  @CsvField(value = "Year", reportField = "Year")
  protected Long year;

  // Main Metrics
  @Column(name = "COST")
  @CsvField(value = "Cost", reportField = "Cost")
  @MoneyField
  protected BigDecimal cost;

  @Column(name = "CLICKS")
  @CsvField(value = "Clicks", reportField = "Clicks")
  protected Long clicks;

  @Column(name = "IMPRESSIONS")
  @CsvField(value = "Impressions", reportField = "Impressions")
  protected Long impressions;

  @Column(name = "CTR")
  @CsvField(value = "CTR", reportField = "Ctr")
  protected BigDecimal ctr;

  @Column(name = "AVERAGE_CPM")
  @CsvField(value = "Avg. CPM", reportField = "AverageCpm")
  @MoneyField
  protected BigDecimal avgCpm;

  @Column(name = "AVERAGE_CPC")
  @CsvField(value = "Avg. CPC", reportField = "AverageCpc")
  @MoneyField
  protected BigDecimal avgCpc;

  @Column(name = "AVERAGE_POSITION")
  @CsvField(value = "Avg. position", reportField = "AveragePosition")
  protected BigDecimal avgPosition;

  // Main Segments
  @Column(name = "DEVICE", length = 64)
  @CsvField(value = "Device", reportField = "Device")
  protected String device;

  @Column(name = "CLICK_TYPE", length = 64)
  @CsvField(value = "Click type", reportField = "ClickType")
  protected String clickType;

  @Column(name = "NETWORK", length = 32)
  @CsvField(value = "Network", reportField = "AdNetworkType1")
  protected String adNetwork;

  @Column(name = "NETWORK_PARTNERS", length = 32)
  @CsvField(value = "Network (with search partners)", reportField = "AdNetworkType2")
  protected String adNetworkPartners;

  // Conversion Columns
  // Many Per Click
  @Column(name = "CONVERSIONSMANYPERCLICK")
  @CsvField(value = "Conversions", reportField = "ConversionsManyPerClick")
  protected Long conversionsManyPerClick = 0L;

  @Column(name = "CONVERSIONRATEMANYPERCLICK")
  @CsvField(value = "Conv. rate", reportField = "ConversionRateManyPerClick")
  protected BigDecimal conversionRateManyPerClick;

  @Column(name = "COSTPERCONVERSIONMANYPERCLICK")
  @CsvField(value = "Cost / conv.", reportField = "CostPerConversionManyPerClick")
  @MoneyField
  protected BigDecimal costPerConversionManyPerClick;

  @Column(name = "VALUEPERCONVMANYPERCLICK")
  @CsvField(value = "Value / conv.", reportField = "ValuePerConvManyPerClick")
  protected BigDecimal valuePerConvManyPerClick;

  @Column(name = "VALUEPERCONVERSIONMANYPERCLICK")
  @CsvField(value = "Value / conv", reportField = "ValuePerConversionManyPerClick")
  protected BigDecimal valuePerConversionManyPerClick;

  // One Per Click
  @Column(name = "CONVERSIONS")
  @CsvField(value = "Converted clicks", reportField = "Conversions")
  protected Long conversions = 0L;

  @Column(name = "CONVERSIONRATE")
  @CsvField(value = "Click conversion rate", reportField = "ConversionRate")
  protected BigDecimal conversionRate;

  @Column(name = "COSTPERCONVERSION")
  @CsvField(value = "Cost / converted click", reportField = "CostPerConversion")
  @MoneyField
  protected BigDecimal costPerConversion;

  @Column(name = "VALUEPERCONV")
  @CsvField(value = "Value / converted click", reportField = "ValuePerConv")
  protected BigDecimal valuePerConv;

  @Column(name = "VALUEPERCONVERSION")
  @CsvField(value = "Value / converted click", reportField = "ValuePerConversion")
  protected BigDecimal valuePerConversion;

  // General
  @Column(name = "CONVERSIONCATEGORYNAME")
  @CsvField(value = "Conversion category", reportField = "ConversionCategoryName")
  protected String conversionCategoryName;

  @Column(name = "CONVERSIONTYPENAME")
  @CsvField(value = "Conversion name", reportField = "ConversionTypeName")
  protected String conversionTypeName;

  @Column(name = "CONVERSIONVALUE")
  @CsvField(value = "Total conv. value", reportField = "ConversionValue")
  protected BigDecimal conversionValue;

  @Column(name = "VIEWTHROUGHCONVERSIONS")
  @CsvField(value = "View-through conv.", reportField = "ViewThroughConversions")
  protected Long viewThroughConversions = 0L;

  private static final Map<String, DateRangeHandler> dateRangeHandlers = Maps.newHashMap();

  static {
    dateRangeHandlers.put(ReportDefinitionDateRangeType.LAST_14_DAYS.name(),
        new Last14DaysDateRangeHandler());
    dateRangeHandlers.put(ReportDefinitionDateRangeType.LAST_30_DAYS.name(),
        new Last30DaysDateRangeHandler());
    dateRangeHandlers.put(ReportDefinitionDateRangeType.LAST_7_DAYS.name(),
        new Last7DaysDateRangeHandler());
    dateRangeHandlers.put(ReportDefinitionDateRangeType.LAST_MONTH.name(),
        new LastMonthDateRangeHandler());
    dateRangeHandlers.put(ReportDefinitionDateRangeType.LAST_WEEK.name(),
        new LastWeekDateRangeHandler());
    dateRangeHandlers.put(ReportDefinitionDateRangeType.THIS_MONTH.name(),
        new ThisMonthDateRangeHandler());
    dateRangeHandlers.put(ReportDefinitionDateRangeType.TODAY.name(), new TodayDateRangeHandler());
    dateRangeHandlers.put(ReportDefinitionDateRangeType.YESTERDAY.name(),
        new YesterdayDateRangeHandler());
  }

  /**
   * Hibernate needs an empty constructor
   */
  public ReportBase() {
    timestamp = new DateTime().toDate();
  }

  public ReportBase(Long topAccountId, Long accountId) {
    this.topAccountId = topAccountId;
    this.accountId = accountId;
    timestamp = new DateTime().toDate();
  }

  @Override
  public abstract void setId();

  @Override
  public String setIdDates() {
    // Time Segments
    if (this.getDay() != null) {
      return "-" + this.getDay();
    }
    if (this.getMonth() != null) {
      return "-" + DateUtil.formatYearMonth(this.getMonthDateTime());
    }
    if (this.getDayOfWeek() != null) {
      return "-" + this.getDayOfWeek();
    }
    if (this.getWeek() != null) {
      return "-" + this.getWeek();
    }
    if (this.getQuarter() != null) {
      return "-" + this.getQuarter();
    }
    if (this.getMonthOfYear() != null) {
      return "-" + this.getMonthOfYear();
    }
    if (this.getYear() != null) {
      return "-" + this.getYear();
    }

    if (this.getDateRangeType() != null) {
      DateRangeHandler handler = dateRangeHandlers.get(this.getDateRangeType());
      if (handler != null) {
        this.setMonth(handler.retrieveMonth(DateTime.now()));
        this.setDateStart(DateUtil.formatYearMonthDay(handler.retrieveDateStart(DateTime.now())));
        this.setDateEnd(DateUtil.formatYearMonthDay(handler.retrieveDateEnd(DateTime.now())));
      }
    }
    if (this.getDateStart() != null && this.getDateEnd() != null) {
      return "-" + this.getDateStart() + "-" + this.getDateEnd();
    }
    return "";
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

  public void setCtr(BigDecimal ctr) {
    this.ctr = ctr;
  }

  public void setAvgCpm(BigDecimal avgCpm) {
    this.avgCpm = avgCpm;
  }

  public void setAvgCpc(BigDecimal avgCpc) {
    this.avgCpc = avgCpc;
  }

  public void setAvgPosition(BigDecimal avgPosition) {
    this.avgPosition = avgPosition;
  }

  public String getAccountDescriptiveName() {
    return accountDescriptiveName;
  }

  public void setAccountDescriptiveName(String accountDescriptiveName) {
    this.accountDescriptiveName = accountDescriptiveName;
  }

  public String getAccountTimeZoneId() {
    return accountTimeZoneId;
  }

  public void setAccountTimeZoneId(String accountTimeZoneId) {
    this.accountTimeZoneId = accountTimeZoneId;
  }

  public String getPrimaryCompanyName() {
    return primaryCompanyName;
  }

  public void setPrimaryCompanyName(String primaryCompanyName) {
    this.primaryCompanyName = primaryCompanyName;
  }

  public String getCustomerDescriptiveName() {
    return customerDescriptiveName;
  }

  public void setCustomerDescriptiveName(String customerDescriptiveName) {
    this.customerDescriptiveName = customerDescriptiveName;
  }

  public BigDecimal getCost() {
    return cost;
  }

  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }

  public Long getClicks() {
    return clicks;
  }

  public void setClicks(Long clicks) {
    this.clicks = clicks;
  }

  public Long getImpressions() {
    return impressions;
  }

  public void setImpressions(Long impressions) {
    this.impressions = impressions;
  }

  public String getCtr() {
    return BigDecimalUtil.formatAsReadable(ctr);
  }

  public BigDecimal getCtrBigDecimal() {
    return ctr;
  }

  public void setCtr(String ctr) {
    // removing percentage symbol from the string
    if (ctr != null) {
      String replace = ctr.replace("%", "");
      this.ctr = BigDecimalUtil.parseFromNumberString(replace);
    }
  }

  public BigDecimal getAvgCpm() {
    return avgCpm;
  }

  public BigDecimal getAvgCpc() {
    return avgCpc;
  }

  public String getAvgPosition() {
    return BigDecimalUtil.formatAsReadable(avgPosition);
  }

  public BigDecimal getAvgPositionBigDecimal() {
    return avgPosition;
  }

  public void setAvgPosition(String avgPosition) {
    this.avgPosition = BigDecimalUtil.parseFromNumberString(avgPosition);
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public String getDevice() {
    return device;
  }

  public void setDevice(String device) {
    this.device = device;
  }

  public String getClickType() {
    return clickType;
  }

  public void setClickType(String clickType) {
    this.clickType = clickType;
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

  // Conversion Columns
  public Long getConversions() {
    return conversions;
  }

  public void setConversions(Long conversions) {
    this.conversions = conversions;
  }

  public Long getConversionsManyPerClick() {
    return conversionsManyPerClick;
  }

  public void setConversionsManyPerClick(Long conversionsManyPerClick) {
    this.conversionsManyPerClick = conversionsManyPerClick;
  }

  public String getConversionRateManyPerClick() {
    return BigDecimalUtil.formatAsReadable(conversionRateManyPerClick);
  }

  public BigDecimal getConversionRateManyPerClickBigDecimal() {
    return conversionRateManyPerClick;
  }

  public void setConversionRateManyPerClick(String conversionRateManyPerClick) {
    this.conversionRateManyPerClick =
        BigDecimalUtil.parseFromNumberString(conversionRateManyPerClick);
  }

  public BigDecimal getCostPerConversionManyPerClick() {
    return costPerConversionManyPerClick;
  }

  public void setCostPerConversionManyPerClick(BigDecimal costPerConversionManyPerClick) {
    this.costPerConversionManyPerClick = costPerConversionManyPerClick;
  }

  public BigDecimal getValuePerConvManyPerClickBigDecimal() {
    return valuePerConvManyPerClick;
  }

  public String getValuePerConvManyPerClick() {
    return BigDecimalUtil.formatAsReadable(valuePerConvManyPerClick);
  }

  public void setValuePerConvManyPerClick(String valuePerConvManyPerClick) {
    this.valuePerConvManyPerClick = BigDecimalUtil.parseFromNumberString(valuePerConvManyPerClick);
  }

  public String getValuePerConversionManyPerClick() {
    return BigDecimalUtil.formatAsReadable(valuePerConversionManyPerClick);
  }

  public BigDecimal getValuePerConversionManyPerClickBigDecimal() {
    return valuePerConversionManyPerClick;
  }

  public void setValuePerConversionManyPerClick(String valuePerConversionManyPerClick) {
    this.valuePerConversionManyPerClick =
        BigDecimalUtil.parseFromNumberString(valuePerConversionManyPerClick);
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

  public BigDecimal getCostPerConversion() {
    return costPerConversion;
  }

  public void setCostPerConversion(BigDecimal costPerConversion) {
    this.costPerConversion = costPerConversion;
  }

  public String getValuePerConv() {
    return BigDecimalUtil.formatAsReadable(valuePerConv);
  }

  public BigDecimal getValuePerConvBigDecimal() {
    return valuePerConv;
  }

  public void setValuePerConv(String valuePerConv) {
    this.valuePerConv = BigDecimalUtil.parseFromNumberString(valuePerConv);
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

  public String getConversionCategoryName() {
    return conversionCategoryName;
  }

  public void setConversionCategoryName(String conversionCategoryName) {
    this.conversionCategoryName = conversionCategoryName;
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

  public Long getViewThroughConversions() {
    return viewThroughConversions;
  }

  public void setViewThroughConversions(Long viewThroughConversions) {
    this.viewThroughConversions = viewThroughConversions;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result
        + ((accountDescriptiveName == null) ? 0 : accountDescriptiveName.hashCode());
    result = prime * result + ((accountTimeZoneId == null) ? 0 : accountTimeZoneId.hashCode());
    result = prime * result + ((adNetwork == null) ? 0 : adNetwork.hashCode());
    result = prime * result + ((adNetworkPartners == null) ? 0 : adNetworkPartners.hashCode());
    result = prime * result + ((avgCpc == null) ? 0 : avgCpc.hashCode());
    result = prime * result + ((avgCpm == null) ? 0 : avgCpm.hashCode());
    result = prime * result + ((avgPosition == null) ? 0 : avgPosition.hashCode());
    result = prime * result + ((clickType == null) ? 0 : clickType.hashCode());
    result = prime * result + ((clicks == null) ? 0 : clicks.hashCode());
    result = prime * result
        + ((conversionCategoryName == null) ? 0 : conversionCategoryName.hashCode());
    result = prime * result + ((conversionRate == null) ? 0 : conversionRate.hashCode());
    result = prime * result
        + ((conversionRateManyPerClick == null) ? 0 : conversionRateManyPerClick.hashCode());
    result = prime * result + ((conversionTypeName == null) ? 0 : conversionTypeName.hashCode());
    result = prime * result + ((conversionValue == null) ? 0 : conversionValue.hashCode());
    result = prime * result + ((conversions == null) ? 0 : conversions.hashCode());
    result = prime * result
        + ((conversionsManyPerClick == null) ? 0 : conversionsManyPerClick.hashCode());
    result = prime * result + ((cost == null) ? 0 : cost.hashCode());
    result = prime * result + ((costPerConversion == null) ? 0 : costPerConversion.hashCode());
    result = prime * result
        + ((costPerConversionManyPerClick == null) ? 0 : costPerConversionManyPerClick.hashCode());
    result = prime * result + ((ctr == null) ? 0 : ctr.hashCode());
    result = prime * result + ((currencyCode == null) ? 0 : currencyCode.hashCode());
    result = prime * result
        + ((customerDescriptiveName == null) ? 0 : customerDescriptiveName.hashCode());
    result = prime * result + ((day == null) ? 0 : day.hashCode());
    result = prime * result + ((dayOfWeek == null) ? 0 : dayOfWeek.hashCode());
    result = prime * result + ((device == null) ? 0 : device.hashCode());
    result = prime * result + ((impressions == null) ? 0 : impressions.hashCode());
    result = prime * result + ((month == null) ? 0 : month.hashCode());
    result = prime * result + ((monthOfYear == null) ? 0 : monthOfYear.hashCode());
    result = prime * result + ((primaryCompanyName == null) ? 0 : primaryCompanyName.hashCode());
    result = prime * result + ((quarter == null) ? 0 : quarter.hashCode());
    result = prime * result + ((valuePerConv == null) ? 0 : valuePerConv.hashCode());
    result = prime * result
        + ((valuePerConvManyPerClick == null) ? 0 : valuePerConvManyPerClick.hashCode());
    result = prime * result + ((valuePerConversion == null) ? 0 : valuePerConversion.hashCode());
    result = prime
        * result
        + ((valuePerConversionManyPerClick == null) ? 0 : valuePerConversionManyPerClick.hashCode());
    result = prime * result
        + ((viewThroughConversions == null) ? 0 : viewThroughConversions.hashCode());
    result = prime * result + ((week == null) ? 0 : week.hashCode());
    result = prime * result + ((year == null) ? 0 : year.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    ReportBase other = (ReportBase) obj;
    if (accountDescriptiveName == null) {
      if (other.accountDescriptiveName != null)
        return false;
    } else if (!accountDescriptiveName.equals(other.accountDescriptiveName))
      return false;
    if (accountTimeZoneId == null) {
      if (other.accountTimeZoneId != null)
        return false;
    } else if (!accountTimeZoneId.equals(other.accountTimeZoneId))
      return false;
    if (adNetwork == null) {
      if (other.adNetwork != null)
        return false;
    } else if (!adNetwork.equals(other.adNetwork))
      return false;
    if (adNetworkPartners == null) {
      if (other.adNetworkPartners != null)
        return false;
    } else if (!adNetworkPartners.equals(other.adNetworkPartners))
      return false;
    if (avgCpc == null) {
      if (other.avgCpc != null)
        return false;
    } else if (!avgCpc.equals(other.avgCpc))
      return false;
    if (avgCpm == null) {
      if (other.avgCpm != null)
        return false;
    } else if (!avgCpm.equals(other.avgCpm))
      return false;
    if (avgPosition == null) {
      if (other.avgPosition != null)
        return false;
    } else if (!avgPosition.equals(other.avgPosition))
      return false;
    if (clickType == null) {
      if (other.clickType != null)
        return false;
    } else if (!clickType.equals(other.clickType))
      return false;
    if (clicks == null) {
      if (other.clicks != null)
        return false;
    } else if (!clicks.equals(other.clicks))
      return false;
    if (conversionCategoryName == null) {
      if (other.conversionCategoryName != null)
        return false;
    } else if (!conversionCategoryName.equals(other.conversionCategoryName))
      return false;
    if (conversionRate == null) {
      if (other.conversionRate != null)
        return false;
    } else if (!conversionRate.equals(other.conversionRate))
      return false;
    if (conversionRateManyPerClick == null) {
      if (other.conversionRateManyPerClick != null)
        return false;
    } else if (!conversionRateManyPerClick.equals(other.conversionRateManyPerClick))
      return false;
    if (conversionTypeName == null) {
      if (other.conversionTypeName != null)
        return false;
    } else if (!conversionTypeName.equals(other.conversionTypeName))
      return false;
    if (conversionValue == null) {
      if (other.conversionValue != null)
        return false;
    } else if (!conversionValue.equals(other.conversionValue))
      return false;
    if (conversions == null) {
      if (other.conversions != null)
        return false;
    } else if (!conversions.equals(other.conversions))
      return false;
    if (conversionsManyPerClick == null) {
      if (other.conversionsManyPerClick != null)
        return false;
    } else if (!conversionsManyPerClick.equals(other.conversionsManyPerClick))
      return false;
    if (cost == null) {
      if (other.cost != null)
        return false;
    } else if (!cost.equals(other.cost))
      return false;
    if (costPerConversion == null) {
      if (other.costPerConversion != null)
        return false;
    } else if (!costPerConversion.equals(other.costPerConversion))
      return false;
    if (costPerConversionManyPerClick == null) {
      if (other.costPerConversionManyPerClick != null)
        return false;
    } else if (!costPerConversionManyPerClick.equals(other.costPerConversionManyPerClick))
      return false;
    if (ctr == null) {
      if (other.ctr != null)
        return false;
    } else if (!ctr.equals(other.ctr))
      return false;
    if (currencyCode == null) {
      if (other.currencyCode != null)
        return false;
    } else if (!currencyCode.equals(other.currencyCode))
      return false;
    if (customerDescriptiveName == null) {
      if (other.customerDescriptiveName != null)
        return false;
    } else if (!customerDescriptiveName.equals(other.customerDescriptiveName))
      return false;
    if (day == null) {
      if (other.day != null)
        return false;
    } else if (!day.equals(other.day))
      return false;
    if (dayOfWeek == null) {
      if (other.dayOfWeek != null)
        return false;
    } else if (!dayOfWeek.equals(other.dayOfWeek))
      return false;
    if (device == null) {
      if (other.device != null)
        return false;
    } else if (!device.equals(other.device))
      return false;
    if (impressions == null) {
      if (other.impressions != null)
        return false;
    } else if (!impressions.equals(other.impressions))
      return false;
    if (month == null) {
      if (other.month != null)
        return false;
    } else if (!month.equals(other.month))
      return false;
    if (monthOfYear == null) {
      if (other.monthOfYear != null)
        return false;
    } else if (!monthOfYear.equals(other.monthOfYear))
      return false;
    if (primaryCompanyName == null) {
      if (other.primaryCompanyName != null)
        return false;
    } else if (!primaryCompanyName.equals(other.primaryCompanyName))
      return false;
    if (quarter == null) {
      if (other.quarter != null)
        return false;
    } else if (!quarter.equals(other.quarter))
      return false;
    if (valuePerConv == null) {
      if (other.valuePerConv != null)
        return false;
    } else if (!valuePerConv.equals(other.valuePerConv))
      return false;
    if (valuePerConvManyPerClick == null) {
      if (other.valuePerConvManyPerClick != null)
        return false;
    } else if (!valuePerConvManyPerClick.equals(other.valuePerConvManyPerClick))
      return false;
    if (valuePerConversion == null) {
      if (other.valuePerConversion != null)
        return false;
    } else if (!valuePerConversion.equals(other.valuePerConversion))
      return false;
    if (valuePerConversionManyPerClick == null) {
      if (other.valuePerConversionManyPerClick != null)
        return false;
    } else if (!valuePerConversionManyPerClick.equals(other.valuePerConversionManyPerClick))
      return false;
    if (viewThroughConversions == null) {
      if (other.viewThroughConversions != null)
        return false;
    } else if (!viewThroughConversions.equals(other.viewThroughConversions))
      return false;
    if (week == null) {
      if (other.week != null)
        return false;
    } else if (!week.equals(other.week))
      return false;
    if (year == null) {
      if (other.year != null)
        return false;
    } else if (!year.equals(other.year))
      return false;
    return true;
  }
}
