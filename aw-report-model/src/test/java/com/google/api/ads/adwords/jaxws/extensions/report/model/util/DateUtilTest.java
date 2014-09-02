// Copyright 2013 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.jaxws.extensions.report.model.util;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Test case for the {@link DateUtil} class.
 */
public class DateUtilTest {

  private Calendar calendar = Calendar.getInstance();

  /**
   * Tests the date format for the yyyy-MM-dd pattern.
   */
  @Test
  public void testYearMonthDay() {

    calendar.set(2011, Calendar.SEPTEMBER, 15);
    Date date = calendar.getTime();
    String formatted = DateUtil.formatYearMonthDay(date);
    Assert.assertEquals("2011-09-15", formatted);

    calendar.set(2013, Calendar.JULY, 1);
    date = calendar.getTime();
    formatted = DateUtil.formatYearMonthDay(date);
    Assert.assertEquals("2013-07-01", formatted);

    DateTime dateTime = new DateTime(2011, 9, 15, 0, 0);
    formatted = DateUtil.formatYearMonthDay(dateTime);
    Assert.assertEquals("2011-09-15", formatted);

    dateTime = new DateTime(2013, 1, 31, 0, 0);
    formatted = DateUtil.formatYearMonthDay(dateTime);
    Assert.assertEquals("2013-01-31", formatted);

  }

  /**
   * Tests the date format for the yyyyMMdd pattern.
   */
  @Test
  public void testYearMonthDayNoDash() {

    calendar.set(2011, Calendar.SEPTEMBER, 15);
    Date date = calendar.getTime();
    String formatted = DateUtil.formatYearMonthDayNoDash(date);
    Assert.assertEquals("20110915", formatted);

    calendar.set(2013, Calendar.JULY, 1);
    date = calendar.getTime();
    formatted = DateUtil.formatYearMonthDayNoDash(date);
    Assert.assertEquals("20130701", formatted);

    DateTime dateTime = new DateTime(2011, 9, 15, 0, 0);
    formatted = DateUtil.formatYearMonthDayNoDash(dateTime);
    Assert.assertEquals("20110915", formatted);

    dateTime = new DateTime(2013, 1, 31, 0, 0);
    formatted = DateUtil.formatYearMonthDayNoDash(dateTime);
    Assert.assertEquals("20130131", formatted);

  }


  /**
   * Tests the date format for the yyyy-MM pattern.
   */
  @Test
  public void testYearMonth() {

    calendar.set(2011, Calendar.SEPTEMBER, 15);
    Date date = calendar.getTime();
    String formatted = DateUtil.formatYearMonth(date);
    Assert.assertEquals("2011-09", formatted);

    calendar.set(2013, Calendar.JULY, 1);
    date = calendar.getTime();
    formatted = DateUtil.formatYearMonth(date);
    Assert.assertEquals("2013-07", formatted);

    DateTime dateTime = new DateTime(2011, 9, 15, 0, 0);
    formatted = DateUtil.formatYearMonth(dateTime);
    Assert.assertEquals("2011-09", formatted);

    dateTime = new DateTime(2013, 1, 31, 0, 0);
    formatted = DateUtil.formatYearMonth(dateTime);
    Assert.assertEquals("2013-01", formatted);

  }

  /**
   * Tests the parsing of a {@code String} in the format yyyy-MM-dd to a valid {@code DateTime}.
   */
  @Test
  public void testParseYearMonthDay() {

    String toParse = "2010-02-22";
    DateTime dateTime = DateUtil.parseDateTime(toParse);
    Assert.assertNotNull(dateTime);

    Assert.assertEquals(2010, dateTime.getYear());
    Assert.assertEquals(2, dateTime.getMonthOfYear());
    Assert.assertEquals(22, dateTime.getDayOfMonth());

    Assert.assertNull(DateUtil.parseDateTime("invalid date"));

  }

  /**
   * Tests the parsing of a {@code String} in the format yyyy-MM-dd to a valid {@code DateTime}.
   */
  @Test
  public void testParseYearMonth() {

    String toParse = "2000-12";
    DateTime dateTime = DateUtil.parseDateTime(toParse);
    Assert.assertNotNull(dateTime);

    Assert.assertEquals(2000, dateTime.getYear());
    Assert.assertEquals(12, dateTime.getMonthOfYear());
    Assert.assertEquals(1, dateTime.getDayOfMonth());

    toParse = "2010-20";
    Assert.assertNull(DateUtil.parseDateTime(toParse));

  }

  /**
   * Tests the parsing of a {@code String} in the format yyyy-MM-dd to a valid {@code DateTime}.
   */
  @Test
  public void testParseYearMonthDayNoDash() {

    String toParse = "20010101";
    DateTime dateTime = DateUtil.parseDateTime(toParse);
    Assert.assertNotNull(dateTime);

    Assert.assertEquals(2001, dateTime.getYear());
    Assert.assertEquals(1, dateTime.getMonthOfYear());
    Assert.assertEquals(1, dateTime.getDayOfMonth());

    toParse = "20001032";
    Assert.assertNull(DateUtil.parseDateTime(toParse));

  }

}
