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
        format = new DecimalFormat("##.#");

      } else if (indexOfComma > indexOfDot) {
        nonSpacedString = nonSpacedString.replaceAll("[.]", "");
        
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator(',');
        format = new DecimalFormat("##,#", otherSymbols);

      } else {
        format = new DecimalFormat();
      }

      try {
        return new BigDecimal(format.parse(nonSpacedString).doubleValue(),
            new MathContext(12));

      } catch (ParseException e) {
        // unrecognized number format
        return null;
      }
    }
    return null;
  }


  /**
   * Formats the given {@code BigDecimal} to a readable String.
   *
   * @param number the {@code BigDecimal} to be formatted
   * @return the formatted number with precision two
   */
  public static String formatAsReadable(BigDecimal number) {
    return humanReadableFormat.format(number);
  }

}
