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

import com.google.api.ads.adwords.lib.jaxb.v201809.DateRange;
import com.google.api.ads.adwords.lib.jaxb.v201809.ReportDefinitionDateRangeType;
import com.google.api.client.util.Preconditions;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Class to represent start date and end date of user specified date range.
 */
public class DateRangeAndType {
  private static final DateTimeFormatter DATE_FORMMATER = DateTimeFormat.forPattern("yyyyMMdd");

  private final LocalDate startDate;
  private final LocalDate endDate;
  private final ReportDefinitionDateRangeType type;

  /**
   * Make constructor private so it can only be created by factory method.
   */
  private DateRangeAndType(
      LocalDate startDate, LocalDate endDate, ReportDefinitionDateRangeType type) {
    this.startDate = Preconditions.checkNotNull(startDate, "Argument 'startDate' cannot be null.");
    this.endDate = Preconditions.checkNotNull(endDate, "Argument 'endDate' cannot be null.");
    this.type = Preconditions.checkNotNull(type, "Argument 'type' cannot be null.");

    Preconditions.checkArgument(
        !endDate.isBefore(startDate), "Start date must be before or equal to end date.");
  }

  public String getStartDateStr() {
    return startDate.toString(DATE_FORMMATER);
  }

  public String getEndDateStr() {
    return endDate.toString(DATE_FORMMATER);
  }

  public String getTypeStr() {
    return type.value();
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public ReportDefinitionDateRangeType getType() {
    return type;
  }
  
  public DateRange getDateRange() {
    DateRange dateRange = new DateRange();
    dateRange.setMin(getStartDateStr());
    dateRange.setMax(getEndDateStr());
    return dateRange;
  }

  /**
   * Factory method to create a DateRange instance, with overwriting / alteration of values.
   */
  public static DateRangeAndType fromValues(LocalDate startDate, LocalDate endDate,
      ReportDefinitionDateRangeType type) {
    if (startDate != null && endDate != null) {
      // Overwrite type to custom if start date & end date are provided.
      return new DateRangeAndType(startDate, endDate, ReportDefinitionDateRangeType.CUSTOM_DATE);
    } else if (type != null && type != ReportDefinitionDateRangeType.CUSTOM_DATE) {
      // Overwrite the provided start/end date if type is custom.
      return parseEnumFormat(type);
    } else {
      throw new IllegalArgumentException(
          "Must provide either: 'startDate' and 'endData', or 'type'.");
      }
  }

  /**
   * Factory method to create a DateRange instance.
   * @param dateRange the date range string, either in "yyyyMMdd,yyyyMMdd" format, or some
   *                  ReportDefinitionDateRangeType enum value (such as "LAST_7_DAYS")
   * @return the DateRange object
   */
  public static DateRangeAndType fromString(final String dateRange) {
    Preconditions.checkNotNull(dateRange, "DateRange cannot be null.");
    Preconditions.checkArgument(!dateRange.isEmpty(), "DateRange cannot be empty.");

    return dateRange.contains(",") ? parseCustomFormat(dateRange) : parseEnumFormat(dateRange);
  }

  /**
   * Parse DateRange in "yyyyMMdd,yyyyMMdd" format.
   */
  private static DateRangeAndType parseCustomFormat(final String dateRange) {
    String[] dates = dateRange.split(",");
    Preconditions.checkArgument(dates.length == 2, "Unknown DateRange format: '%s.'", dateRange);

    // Just throws exception if argument is not in proper format "yyyyMMdd"
    LocalDate startDate = DATE_FORMMATER.parseLocalDate(dates[0].trim());
    LocalDate endDate = DATE_FORMMATER.parseLocalDate(dates[1].trim());

    return new DateRangeAndType(startDate, endDate, ReportDefinitionDateRangeType.CUSTOM_DATE);
  }

  /**
   * Parse DateRange in ReportDefinitionDateRangeType enum format.
   */
  private static DateRangeAndType parseEnumFormat(ReportDefinitionDateRangeType type) {
    LocalDate today = LocalDate.now();
    LocalDate startDate;
    LocalDate endDate;
    switch (type) {
      case TODAY:
        startDate = endDate = today;
        break;
      case YESTERDAY:
        startDate = endDate = today.minusDays(1);
        break;
      case LAST_7_DAYS:
        startDate = today.minusDays(7);
        endDate = today.minusDays(1);
        break;
      case LAST_WEEK:
        LocalDate.Property lastWeekProp = today.minusWeeks(1).dayOfWeek();
        startDate = lastWeekProp.withMinimumValue();
        endDate = lastWeekProp.withMaximumValue();
        break;
      case THIS_MONTH:
        LocalDate.Property thisMonthProp = today.dayOfMonth();
        startDate = thisMonthProp.withMinimumValue();
        endDate = thisMonthProp.withMaximumValue();
        break;
      case LAST_MONTH:
        LocalDate.Property lastMonthProp = today.minusMonths(1).dayOfMonth();
        startDate = lastMonthProp.withMinimumValue();
        endDate = lastMonthProp.withMaximumValue();
        break;
      case LAST_14_DAYS:
        startDate = today.minusDays(14);
        endDate = today.minusDays(1);
        break;
      case LAST_30_DAYS:
        startDate = today.minusDays(30);
        endDate = today.minusDays(1);
        break;
      case THIS_WEEK_SUN_TODAY:
        // Joda-Time uses the ISO standard Monday to Sunday week.
        startDate = today.minusWeeks(1).dayOfWeek().withMaximumValue();
        endDate = today;
        break;
      case THIS_WEEK_MON_TODAY:
        startDate = today.dayOfWeek().withMinimumValue();
        endDate = today;
        break;
      case LAST_WEEK_SUN_SAT:
        startDate = today.minusWeeks(2).dayOfWeek().withMaximumValue();
        endDate = today.minusWeeks(1).dayOfWeek().withMaximumValue().minusDays(1);
        break;
        // Don't support the following enums
      case LAST_BUSINESS_WEEK:
      case ALL_TIME:
      case CUSTOM_DATE:
      default:
        throw new IllegalArgumentException("Unsupported DateRange type: " + type.value());
    }

    return new DateRangeAndType(startDate, endDate, type);
  }
  
  /**
   * Parse DateRange in ReportDefinitionDateRangeType enum string.
   */
  private static DateRangeAndType parseEnumFormat(final String dateRange) {
    try {
      ReportDefinitionDateRangeType type = ReportDefinitionDateRangeType.valueOf(dateRange);
      return parseEnumFormat(type);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Unknown DateRange type: " + dateRange);
    }
  }
}
