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

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * {@code BigDecimalUtil} is a utility class that helps the parsing and handling the different types
 * of number formats in the reports.
 *
 * @author gustavomoreira@google.com (Gustavo Moreira)
 */
public class BigDecimalUtil {

  private static final DecimalFormat humanReadableFormat = new DecimalFormat("#0.00");

  /**
   * Private constructor.
   */
  private BigDecimalUtil() {}

  /**
   * Finds out the number format in {@code String} format, and parse the number to
   * {@code BigDecimal} format.
   *
   *  The passed number can contain white spaces of any sort, and can have the default separators
   * such as ',' and '.'.
   *
   * @param numberString the number in {@code String} format
   * @return the {@code BigDecimal} that was parsed from the {@code String}. If the number format is
   *         not recognized, than {@code null} is returned.
   */
  public static BigDecimal parseFromNumberString(String numberString) {

    if (numberString != null) {

      String nonSpacedString =
          numberString.replaceAll("[ \\t\\n\\x0B\\f\\r]", "").replaceAll("%", "");

      int indexOfComma = nonSpacedString.indexOf(',');
      int indexOfDot = nonSpacedString.indexOf('.');
      NumberFormat format = null;

      if (indexOfComma < indexOfDot) {
        nonSpacedString = nonSpacedString.replaceAll("[,]", "");


        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator('.');
        format = new DecimalFormat("##.#", otherSymbols);

      } else if (indexOfComma > indexOfDot) {
        nonSpacedString = nonSpacedString.replaceAll("[.]", "");

        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator(',');
        format = new DecimalFormat("##,#", otherSymbols);

      } else {
        format = new DecimalFormat();
      }

      try {
        return new BigDecimal(format.parse(nonSpacedString).doubleValue(), new MathContext(12));

      } catch (ParseException e) {
        // unrecognized number format
        return null;
      }
    }
    return null;
  }

  /**
   * Finds out the number format in {@code String} format, and parse the number to
   * {@code BigDecimal} format.
   *
   * Mainly use for the ugly "< 10%", "> 90%" or "--" numbers from the API.
   *
   * @param numberString the number in {@code String} format
   * @return the {@code BigDecimal} that was parsed from the {@code String}. If the number format is
   *         not recognized, than {@code null} is returned.
   */
  public static BigDecimal parseFromNumberStringPercentage(String numberString) {
    if (numberString != null) {
      return new BigDecimal(numberString.replaceAll("--", "0").replaceAll("\\s|%|>|<", ""));
    }
    return null;
  }

  /**
   * Formats the given {@code BigDecimal} to a readable String.
   *
   * @param number the {@code BigDecimal} to be formatted
   * @return the formatted number with precision two. Null in case of null object
   */
  public static String formatAsReadable(BigDecimal number) {
    if (number == null) {
      return null;
    }
    return humanReadableFormat.format(number);
  }

}
