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

import static org.junit.Assert.assertEquals;

import com.google.api.ads.adwords.lib.jaxb.v201710.ReportDefinitionDateRangeType;
import org.joda.time.DateTimeUtils;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class DateRangeAndTypeTest {
  private LocalDate date = new LocalDate(2013, 10, 2);

  @Before
  public void setUp() throws Exception {
    DateTimeUtils.setCurrentMillisFixed(date.toDateTimeAtStartOfDay().getMillis());
  }

  @After
  public void tearDown() {
    DateTimeUtils.setCurrentMillisSystem();
  }

  @Test
  public void testLast14DayRange() {
    DateRangeAndType last14Days = DateRangeAndType.fromString("LAST_14_DAYS");
    testDateRange(last14Days, "20130918", "20131001");
  }

  @Test
  public void testLast30DayRange() {
    DateRangeAndType last30Days = DateRangeAndType.fromString("LAST_30_DAYS");
    testDateRange(last30Days, "20130902", "20131001");
  }

  @Test
  public void testLast7DayRange() {
    DateRangeAndType last7Days = DateRangeAndType.fromString("LAST_7_DAYS");
    testDateRange(last7Days, "20130925", "20131001");
  }

  @Test
  public void testLastMonthRange() {
    DateRangeAndType lastMonth = DateRangeAndType.fromString("LAST_MONTH");
    testDateRange(lastMonth, "20130901", "20130930");
  }

  @Test
  public void testLastWeekRange() {
    DateRangeAndType lastWeek = DateRangeAndType.fromString("LAST_WEEK");
    testDateRange(lastWeek, "20130923", "20130929");
  }

  @Test
  public void testThisMonthRange() {
    DateRangeAndType thisMonth = DateRangeAndType.fromString("THIS_MONTH");
    testDateRange(thisMonth, "20131001", "20131031");
  }

  @Test
  public void testTodayRange() {
    DateRangeAndType today = DateRangeAndType.fromString("TODAY");
    testDateRange(today, "20131002", "20131002");
  }

  @Test
  public void testYesterdayRange() {
    DateRangeAndType thisMonth = DateRangeAndType.fromString("YESTERDAY");
    testDateRange(thisMonth, "20131001", "20131001");
  }

  @Test
  public void testCustomRange() {
    DateRangeAndType custom = DateRangeAndType.fromString("20131001,20131005");
    testDateRange(custom, "20131001", "20131005");
  }

  @Test(expected = NullPointerException.class)
  public void testNullRange() {
    DateRangeAndType.fromString(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyRange() {
    DateRangeAndType.fromString("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRange() {
    DateRangeAndType.fromString("INVALID_RANGE");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCustomRangeOneDate() {
    DateRangeAndType.fromString("20131002");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCustomRangeBadFormat() {
    DateRangeAndType.fromString("2013-10-02,2013-10-05");
  }

  @Test
  public void testCustomRangeSameDay() {
    DateRangeAndType custom = DateRangeAndType.fromString("20131001,20131001");
    testDateRange(custom, "20131001", "20131001");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCustomInvalidOrder() {
    DateRangeAndType.fromString("20131005,20131001");
  }

  private void testDateRange(
      DateRangeAndType dateRange, String expectedStartDate, String expectedEndDate) {
    String startDate = dateRange.getStartDateStr();
    String endDate = dateRange.getEndDateStr();

    assertEquals("Start date must be correct", expectedStartDate, startDate);
    assertEquals("End date must be correct", expectedEndDate, endDate);
  }

  @Test
  public void testOverwritingType() {
    DateRangeAndType custom = DateRangeAndType.fromValues(date, date, null);
    assertEquals("Type must be correct", ReportDefinitionDateRangeType.CUSTOM_DATE,
        custom.getType());
  }
  
  @Test
  public void testOverritingDates() {
    DateRangeAndType last7Days = DateRangeAndType.fromValues(null, null,
        ReportDefinitionDateRangeType.LAST_7_DAYS);
    testDateRange(last7Days, "20130925", "20131001");
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidValues1() {
    DateRangeAndType.fromValues(null, null, null);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidValues2() {
    DateRangeAndType.fromValues(null, date, ReportDefinitionDateRangeType.CUSTOM_DATE);
  }
}
