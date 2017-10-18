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
import com.google.api.ads.adwords.awreporting.model.util.DateUtil;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.LocalDate;

/**
 * The base abstract class for all Reports segmented by date. For a full list of reports
 * and their fields see:
 * https://developers.google.com/adwords/api/docs/appendix/reports
 *
 * <p>For a full list of date ranges see:
 * https://developers.google.com/adwords/api/docs/guides/reporting#date-ranges
 *
 * <p>All fields are stored as String, except {@code hourOfDay} and {@code year} which are
 * {@code Long}. The String values represent dates as yyyy-MM-dd or enum values.
 */
@MappedSuperclass
public abstract class DateReport extends Report {
  // Date Segments
  // Date column definition set as DATETIME for database, returned as string for ROW_ID
  @Column(name = "Date", columnDefinition="DATETIME")
  @CsvField(value = "Day", reportField = "Date")
  protected String date;

  @Column(name = "DayOfWeek")
  @CsvField(value = "Day of week", reportField = "DayOfWeek")
  protected String dayOfWeek;

  @Column(name = "HourOfDay")
  @CsvField(value = "Hour of day", reportField = "HourOfDay")
  protected Long hourOfDay;

  @Column(name = "Week")
  @CsvField(value = "Week", reportField = "Week")
  protected String week;

  @Column(name = "Month")
  @CsvField(value = "Month", reportField = "Month")
  protected String month;

  @Column(name = "MonthOfYear")
  @CsvField(value = "Month of Year", reportField = "MonthOfYear")
  protected String monthOfYear;

  @Column(name = "Quarter")
  @CsvField(value = "Quarter", reportField = "Quarter")
  protected String quarter;

  @Column(name = "Year")
  @CsvField(value = "Year", reportField = "Year")
  protected Long year;

  /**
   * Constructor to satisfy Hibernate.
   */
  DateReport() {}

  /**
   * Constructor for DateReport class.
   *
   * @param topAccountId the top level manager account ID
   * @param accountId the account ID
   */
  public DateReport(Long topAccountId, Long accountId){
    super(topAccountId, accountId);
  }

  /**
   * Returns the date as a String for use in the rowId.
   */
  public String getDateLabel() {
    // Time Segments
    if (getDate() != null) {
      return getDate();
    }
    if (getDayOfWeek() != null) {
      return getDayOfWeek();
    }
    if (getHourOfDay() != null) {
      return String.valueOf(getHourOfDay());
    }
    // Week may partially overlap with Month / Quarter / Year.
    List<String> labelComponents = Lists.newLinkedList();
    if (getWeek() != null) {
      labelComponents.add(getWeek());
    }
    if (getMonthOfYear() != null) {
      labelComponents.add(getMonthOfYear());
    } else if (getMonth() != null) {
      labelComponents.add(getMonth());
    } else if (getQuarter() != null) {
      labelComponents.add(getQuarter());
    } else if (getYear() != null) {
      labelComponents.add(getYear().toString());
    }
    if (!labelComponents.isEmpty()) {
      return Joiner.on("-").join(labelComponents);
    }

    Preconditions.checkState(startDate != null, "startDate should not be null.");
    Preconditions.checkState(endDate != null, "endDate should not be null.");
    return startDate + "-" + endDate;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    LocalDate parsedLocalDate = DateUtil.parseLocalDate(date);
    if (parsedLocalDate != null) {
      this.date = DateUtil.formatYearMonthDay(parsedLocalDate);
    }
  }

  public String getDayOfWeek() {
    return dayOfWeek;
  }

  public void setDayOfWeek(String dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }

  public Long getHourOfDay() {
    return hourOfDay;
  }

  public void setHourOfDay(Long hourOfDay) {
    this.hourOfDay = hourOfDay;
  }

  public String getWeek() {
    return week;
  }

  public void setWeek(String week) {
    this.week = week;
  }

  public String getMonth() {
    return month;
  }

  public void setMonth(String month) {
    LocalDate parseDateTime = DateUtil.parseLocalDate(month);
    if (parseDateTime != null) {
      this.month = DateUtil.formatYearMonthDay(parseDateTime);
    }
  }

  public String getMonthOfYear() {
    return monthOfYear;
  }

  public void setMonthOfYear(String monthOfYear) {
    this.monthOfYear = monthOfYear;
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

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (this == obj) {
      return true;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }
    
    DateReport other = (DateReport) obj;
    return new EqualsBuilder()
        .appendSuper(super.equals(obj))
        .append(date, other.date)
        .append(dayOfWeek, other.dayOfWeek)
        .append(hourOfDay, other.hourOfDay)
        .append(week, other.week)
        .append(month, other.month)
        .append(monthOfYear, other.monthOfYear)
        .append(quarter, other.quarter)
        .append(year, other.year)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .appendSuper(super.hashCode())
        .append(date)
        .append(dayOfWeek)
        .append(hourOfDay)
        .append(week)
        .append(month)
        .append(monthOfYear)
        .append(quarter)
        .append(year)
        .hashCode();
  }
}
