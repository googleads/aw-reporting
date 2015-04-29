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

package com.google.api.ads.adwords.awreporting.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 * Test case for the {@link TemplateStringsUtil} class.
 * 
 * @author joeltoby
 */
public class TemplateStringsUtilTest {

  private Calendar calendar = Calendar.getInstance();
  private SimpleDateFormat format = new SimpleDateFormat("MMMMM yyyy");
  private SimpleDateFormat abrieviatedFromat = new SimpleDateFormat("MMM yyyy");

  /**
   * Tests formatting date ranges e.g. 'March 2014 - April 2014'
   */
  @Test
  public void testFormatFullMonthDateRange() {
    calendar.set(2014, Calendar.MARCH, 1);
    Date startDate = calendar.getTime();
    calendar.set(2014, Calendar.APRIL, 30);
    Date endDate = calendar.getTime();
    String dateRange = TemplateStringsUtil.formatFullMonthDateRange(startDate,
        endDate);
    Assert.assertEquals(format.format(startDate) + " - " + format.format(endDate), dateRange);

    calendar.set(2014, Calendar.MARCH, 10);
    endDate = calendar.getTime();
    dateRange = TemplateStringsUtil
        .formatFullMonthDateRange(startDate, endDate);
    Assert.assertEquals(format.format(endDate), dateRange);

    DateTime startDateTime = new DateTime(2014, 3, 1, 0, 0);
    DateTime endDateTime = new DateTime(2014, 4, 30, 0, 0);
    dateRange = TemplateStringsUtil.formatFullMonthDateRange(startDateTime,
        endDateTime);
    Assert.assertEquals(format.format(startDateTime.toDate()) + " - " 
        + format.format(endDateTime.toDate()), dateRange);

    endDateTime = new DateTime(2014, 3, 10, 0, 0);
    dateRange = TemplateStringsUtil.formatFullMonthDateRange(startDateTime,
        endDateTime);
    Assert.assertEquals(format.format(startDateTime.toDate()), dateRange);
  }

  /**
   * Tests formatting date ranges to 'MMMM yyyy' e.g. 'March 2014'
   */
  @Test
  public void testFormatDateFullMonthYear() {
    calendar.set(2014, Calendar.MARCH, 8);
    Date date = calendar.getTime();
    String formatted = TemplateStringsUtil.formatDateFullMonthYear(date);
    Assert.assertEquals(format.format(date), formatted);

    DateTime dateTime = new DateTime(2014, 3, 8, 0, 0);
    formatted = TemplateStringsUtil.formatDateFullMonthYear(dateTime);
  }

  /**
   * Tests formatting date ranges to 'MMM yyyy' e.g. 'Sep 2015'
   */
  @Test
  public void testFormatDateAbrieviatedMonthYear() {
    calendar.set(2015, Calendar.SEPTEMBER, 21);
    Date date = calendar.getTime();
    String formatted = TemplateStringsUtil.formatDateAbrieviatedMonthYear(date);
    Assert.assertEquals(abrieviatedFromat.format(date), formatted);

    DateTime dateTime = new DateTime(2015, 9, 21, 0, 0);
    formatted = TemplateStringsUtil.formatDateAbrieviatedMonthYear(dateTime);
    Assert.assertEquals(abrieviatedFromat.format(dateTime.toDate()), formatted);
  }
}
