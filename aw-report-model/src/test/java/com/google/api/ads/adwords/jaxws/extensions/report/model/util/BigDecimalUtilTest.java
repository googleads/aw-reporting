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

import org.junit.Test;

import java.math.BigDecimal;

/**
 * Test for the {@link BigDecimalUtil} class.
 */
public class BigDecimalUtilTest {

  /**
   * Tests the American number format parsing. (##,###,###.00)
   */
  @Test
  public void testAmericanNumberFormat() {

    String sparseString = "1 200 300.10";
    BigDecimal parsed = BigDecimalUtil.parseFromNumberString(sparseString);

    Assert.assertEquals(
        "The parsed value is not the expected.", 1200300.10, parsed.doubleValue(), 0.0000001);

    sparseString = "1,200,300.10";
    parsed = BigDecimalUtil.parseFromNumberString(sparseString);

    Assert.assertEquals(
        "The parsed value is not the expected.", 1200300.10, parsed.doubleValue(), 0.0000001);

  }

  /**
   * Tests the Brazilian number format parsing (##.###.###,00).
   */
  @Test
  public void testBrazilianNumberFormat() {

    String sparseString = "1 200 300,10";
    BigDecimal parsed = BigDecimalUtil.parseFromNumberString(sparseString);

    Assert.assertEquals(
        "The parsed value is not the expected.", 1200300.10, parsed.doubleValue(), 0.0000001);

    sparseString = "1.200.300,10";
    parsed = BigDecimalUtil.parseFromNumberString(sparseString);

    Assert.assertEquals(
        "The parsed value is not the expected.", 1200300.10, parsed.doubleValue(), 0.0000001);

  }

  /**
   * Tests Comma rounding
   */
  @Test
  public void testRoundingComma() {
    
    String sparseString = "0,39";
    BigDecimal parsed = BigDecimalUtil.parseFromNumberString(sparseString);
    Assert.assertEquals(
        "The parsed value is not the expected.", 0.39, parsed.doubleValue());

    sparseString = "33,33";
    parsed = BigDecimalUtil.parseFromNumberString(sparseString);
    Assert.assertEquals(
        "The parsed value is not the expected.", 33.33, parsed.doubleValue());
  }

  /**
   * Tests Dot rounding
   */
  @Test
  public void testRoundingDot() {

    String sparseString = "0.39";
    BigDecimal parsed = BigDecimalUtil.parseFromNumberString(sparseString);
    Assert.assertEquals(
        "The parsed value is not the expected.", 0.39, parsed.doubleValue());

    sparseString = "33.33";
    parsed = BigDecimalUtil.parseFromNumberString(sparseString);
    Assert.assertEquals(
        "The parsed value is not the expected.", 33.33, parsed.doubleValue());
  }
}
