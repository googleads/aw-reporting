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
import com.google.api.ads.adwords.lib.jaxb.v201309.ReportDefinitionDateRangeType;
import com.google.api.client.util.Maps;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * The base abstract class with base report fields
 * 
 * Fields from http://code.google.com/apis/adwords/docs/appendix/reports.html
 * Fields from http://code.google.com/apis/adwords/docs/reportguide.html
 * 
 * @author jtoledo@google.com (Julian Toledo)
 * @author gustavomoreira@google.com (Gustavo Moreira)
 */
@MappedSuperclass
public abstract class ReportBase extends Report {

  @Column(name = "DAY")
  @CsvField(value = "Day", reportField = "Date")
  protected Date day;

  @Column(name = "MONTH")
  @CsvField(value = "Month", reportField = "Month")
  protected Date month;

  @Column(name = "ACCOUNT_DESCRIPTIVE_NAME", length = 255)
  @CsvField(value = "Account", reportField = "AccountDescriptiveName")
  protected String accountDescriptiveName;

  @Column(name = "COST")
  @CsvField(value = "Cost", reportField = "Cost")
  protected BigDecimal cost;

  @Column(name = "CLICKS")
  @CsvField(value = "Clicks", reportField = "Clicks")
  protected Long clicks;

  @Column(name = "IMPRESSIONS")
  @CsvField(value = "Impressions", reportField = "Impressions")
  protected Long impressions;

  @Column(name = "CONVERSIONS")
  @CsvField(value = "Conv. (1-per-click)", reportField = "Conversions")
  protected Long conversions = 0L;

  @Column(name = "CTR")
  @CsvField(value = "CTR", reportField = "Ctr")
  protected BigDecimal ctr;

  @Column(name = "AVERAGE_CPM")
  @CsvField(value = "Avg. CPM", reportField = "AverageCpm")
  protected BigDecimal avgCpm;

  @Column(name = "AVERAGE_CPC")
  @CsvField(value = "Avg. CPC", reportField = "AverageCpc")
  protected BigDecimal avgCpc;

  @Column(name = "AVERAGE_POSITION")
  @CsvField(value = "Avg. position", reportField = "AveragePosition")
  protected BigDecimal avgPosition;

  @Column(name = "CURRENCY_CODE", length = 6)
  @CsvField(value = "Currency", reportField = "AccountCurrencyCode")
  protected String currencyCode;

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

  private static final Map<String, DateRangeHandler> dateRangeHandlers = Maps
      .newHashMap();

  static {
    dateRangeHandlers.put(
        ReportDefinitionDateRangeType.LAST_14_DAYS.name(),
        new Last14DaysDateRangeHandler());
    dateRangeHandlers.put(
        ReportDefinitionDateRangeType.LAST_30_DAYS.name(),
        new Last30DaysDateRangeHandler());
    dateRangeHandlers.put(ReportDefinitionDateRangeType.LAST_7_DAYS.name(),
        new Last7DaysDateRangeHandler());
    dateRangeHandlers.put(ReportDefinitionDateRangeType.LAST_MONTH.name(),
        new LastMonthDateRangeHandler());
    dateRangeHandlers.put(ReportDefinitionDateRangeType.LAST_WEEK.name(),
        new LastWeekDateRangeHandler());
    dateRangeHandlers.put(ReportDefinitionDateRangeType.THIS_MONTH.name(),
        new ThisMonthDateRangeHandler());
    dateRangeHandlers.put(ReportDefinitionDateRangeType.TODAY.name(),
        new TodayDateRangeHandler());
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
    // Day or Month
    if (this.getDay() != null) {
      return "-" + this.getDay();
    }
    if (this.getMonth() != null) {
      return "-" + DateUtil.formatYearMonth(this.getMonthDateTime());
    }
    if (this.getDateRangeType() != null) {

      DateRangeHandler handler = dateRangeHandlers.get(this
          .getDateRangeType());
      if (handler != null) {
        this.setMonth(handler.retrieveMonth(DateTime.now()));
        this.setDateStart(DateUtil.formatYearMonthDay(handler
            .retrieveDateStart(DateTime.now())));
        this.setDateEnd(DateUtil.formatYearMonthDay(handler
            .retrieveDateEnd(DateTime.now())));
      }
    }
    if (this.getDateStart() != null && this.getDateEnd() != null) {
      return "-" + this.getDateStart() + "-" + this.getDateEnd();
    }
    return "";
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public Long getPartnerId() {
    return partnerId;
  }

  @Override
  public void setPartnerId(Long partnerId) {
    this.partnerId = partnerId;
  }

  @Override
  public Long getTopAccountId() {
    return topAccountId;
  }

  @Override
  public void setTopAccountId(Long topAccountId) {
    this.topAccountId = topAccountId;
  }

  @Override
  public Long getAccountId() {
    return accountId;
  }

  @Override
  public void setAccountId(Long accountId) {
    this.accountId = accountId;
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
      this.day = DateUtil.parseDateTime(day).toDate();
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
      this.month = DateUtil.parseDateTime(month).toDate();
    } catch (IllegalArgumentException e) {
      this.month = null;
    }
  }

  @Override
  public String getDateRangeType() {
    return dateRangeType;
  }

  @Override
  public void setDateRangeType(String dateRangeType) {
    this.dateRangeType = dateRangeType;
  }

  public void setCost(BigDecimal cost) {
    this.cost = cost;
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

  public String getCost() {
    return BigDecimalUtil.formatAsReadable(cost);
  }

  public BigDecimal getCostBigDecimal() {
    return cost;
  }

  public void setCost(String cost) {
    this.cost = BigDecimalUtil.parseFromNumberString(cost);
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

  public Long getConversions() {
    return conversions;
  }

  public void setConversions(Long conversions) {
    this.conversions = conversions;
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

  public String getAvgCpm() {
    return BigDecimalUtil.formatAsReadable(avgCpm);
  }

  public BigDecimal getAvgCpmBigDecimal() {
    return avgCpm;
  }

  public void setAvgCpm(String avgCpm) {
    this.avgCpm = BigDecimalUtil.parseFromNumberString(avgCpm);
  }

  public String getAvgCpc() {
    return BigDecimalUtil.formatAsReadable(avgCpc);
  }

  public BigDecimal getAvgCpcBigDecimal() {
    return avgCpc;
  }

  public void setAvgCpc(String avgCpc) {
    this.avgCpc = BigDecimalUtil.parseFromNumberString(avgCpc);
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

  @Override
  public Date getTimestamp() {
    return timestamp;
  }

  @Override
  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
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

  @Override
  public String getDateStart() {
    return dateStart;
  }

  @Override
  public void setDateStart(String dateStart) {
    this.dateStart = dateStart;
  }

  @Override
  public String getDateEnd() {
    return dateEnd;
  }

  @Override
  public void setDateEnd(String dateEnd) {
    this.dateEnd = dateEnd;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime
        * result
        + ((accountDescriptiveName == null) ? 0
            : accountDescriptiveName.hashCode());
    result = prime * result
        + ((accountId == null) ? 0 : accountId.hashCode());
    result = prime * result
        + ((adNetwork == null) ? 0 : adNetwork.hashCode());
    result = prime
        * result
        + ((adNetworkPartners == null) ? 0 : adNetworkPartners
            .hashCode());
    result = prime * result + ((avgCpc == null) ? 0 : avgCpc.hashCode());
    result = prime * result + ((avgCpm == null) ? 0 : avgCpm.hashCode());
    result = prime * result
        + ((avgPosition == null) ? 0 : avgPosition.hashCode());
    result = prime * result
        + ((clickType == null) ? 0 : clickType.hashCode());
    result = prime * result + ((clicks == null) ? 0 : clicks.hashCode());
    result = prime * result
        + ((conversions == null) ? 0 : conversions.hashCode());
    result = prime * result + ((cost == null) ? 0 : cost.hashCode());
    result = prime * result + ((ctr == null) ? 0 : ctr.hashCode());
    result = prime * result
        + ((currencyCode == null) ? 0 : currencyCode.hashCode());
    result = prime * result + ((dateEnd == null) ? 0 : dateEnd.hashCode());
    result = prime * result
        + ((dateRangeType == null) ? 0 : dateRangeType.hashCode());
    result = prime * result
        + ((dateStart == null) ? 0 : dateStart.hashCode());
    result = prime * result + ((day == null) ? 0 : day.hashCode());
    result = prime * result + ((device == null) ? 0 : device.hashCode());
    result = prime * result
        + ((impressions == null) ? 0 : impressions.hashCode());
    result = prime * result + ((month == null) ? 0 : month.hashCode());
    result = prime * result
        + ((partnerId == null) ? 0 : partnerId.hashCode());
    result = prime * result
        + ((timestamp == null) ? 0 : timestamp.hashCode());
    result = prime * result
        + ((topAccountId == null) ? 0 : topAccountId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ReportBase other = (ReportBase) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (accountDescriptiveName == null) {
      if (other.accountDescriptiveName != null) {
        return false;
      }
    } else if (!accountDescriptiveName.equals(other.accountDescriptiveName)) {
      return false;
    }
    if (accountId == null) {
      if (other.accountId != null) {
        return false;
      }
    } else if (!accountId.equals(other.accountId)) {
      return false;
    }
    if (adNetwork == null) {
      if (other.adNetwork != null) {
        return false;
      }
    } else if (!adNetwork.equals(other.adNetwork)) {
      return false;
    }
    if (adNetworkPartners == null) {
      if (other.adNetworkPartners != null) {
        return false;
      }
    } else if (!adNetworkPartners.equals(other.adNetworkPartners)) {
      return false;
    }
    if (avgCpc == null) {
      if (other.avgCpc != null) {
        return false;
      }
    } else if (!avgCpc.equals(other.avgCpc)) {
      return false;
    }
    if (avgCpm == null) {
      if (other.avgCpm != null) {
        return false;
      }
    } else if (!avgCpm.equals(other.avgCpm)) {
      return false;
    }
    if (avgPosition == null) {
      if (other.avgPosition != null) {
        return false;
      }
    } else if (!avgPosition.equals(other.avgPosition)) {
      return false;
    }
    if (clickType == null) {
      if (other.clickType != null) {
        return false;
      }
    } else if (!clickType.equals(other.clickType)) {
      return false;
    }
    if (clicks == null) {
      if (other.clicks != null) {
        return false;
      }
    } else if (!clicks.equals(other.clicks)) {
      return false;
    }
    if (conversions == null) {
      if (other.conversions != null) {
        return false;
      }
    } else if (!conversions.equals(other.conversions)) {
      return false;
    }
    if (cost == null) {
      if (other.cost != null) {
        return false;
      }
    } else if (!cost.equals(other.cost)) {
      return false;
    }
    if (ctr == null) {
      if (other.ctr != null) {
        return false;
      }
    } else if (!ctr.equals(other.ctr)) {
      return false;
    }
    if (currencyCode == null) {
      if (other.currencyCode != null) {
        return false;
      }
    } else if (!currencyCode.equals(other.currencyCode)) {
      return false;
    }
    if (dateEnd == null) {
      if (other.dateEnd != null) {
        return false;
      }
    } else if (!dateEnd.equals(other.dateEnd)) {
      return false;
    }
    if (dateRangeType == null) {
      if (other.dateRangeType != null) {
        return false;
      }
    } else if (!dateRangeType.equals(other.dateRangeType)) {
      return false;
    }
    if (dateStart == null) {
      if (other.dateStart != null) {
        return false;
      }
    } else if (!dateStart.equals(other.dateStart)) {
      return false;
    }
    if (day == null) {
      if (other.day != null) {
        return false;
      }
    } else if (!day.equals(other.day)) {
      return false;
    }
    if (device == null) {
      if (other.device != null) {
        return false;
      }
    } else if (!device.equals(other.device)) {
      return false;
    }
    if (impressions == null) {
      if (other.impressions != null) {
        return false;
      }
    } else if (!impressions.equals(other.impressions)) {
      return false;
    }
    if (month == null) {
      if (other.month != null) {
        return false;
      }
    } else if (!month.equals(other.month)) {
      return false;
    }
    if (partnerId == null) {
      if (other.partnerId != null) {
        return false;
      }
    } else if (!partnerId.equals(other.partnerId)) {
      return false;
    }
    if (timestamp == null) {
      if (other.timestamp != null) {
        return false;
      }
    } else if (!timestamp.equals(other.timestamp)) {
      return false;
    }
    if (topAccountId == null) {
      if (other.topAccountId != null) {
        return false;
      }
    } else if (!topAccountId.equals(other.topAccountId)) {
      return false;
    }
    return true;
  }
}
