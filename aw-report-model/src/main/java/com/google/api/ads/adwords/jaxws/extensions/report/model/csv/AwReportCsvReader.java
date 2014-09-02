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

package com.google.api.ads.adwords.jaxws.extensions.report.model.csv;

import au.com.bytecode.opencsv.CSVReader;

import java.io.IOException;
import java.io.Reader;

/**
 * Class to redefine the behavior of the basic {@link CSVReader}.
 *
 *  The CSV parsing should stop when the line with the sum of the columns is reached. In order to
 * accomplish such behavior, the {@code readNext} was overriden to change the stop condition.
 *
 * @author gustavomoreira@google.com (Gustavo Moreira)
 */
public class AwReportCsvReader extends CSVReader {

  /**
   * The 'Total' {@code String} represents the last line of the AW Report CSV file.
   */
  private static final String AW_REPORT_CSV_TOTAL = "total";

  /**
   * Constructs CSVReader with supplied separator, quote char and quote handling behavior.
   *
   * @param reader the reader to an underlying CSV source.
   * @param separator the delimiter to use for separating entries.
   * @param quotechar the character to use for quoted elements.
   * @param strictQuotes sets if characters outside the quotes are ignored.
   */
  public AwReportCsvReader(Reader reader, char separator, char quotechar, boolean strictQuotes) {
    super(reader, separator, quotechar, strictQuotes);
  }

  /**
   * Constructs CSVReader with supplied separator and quote char.
   *
   * @param reader the reader to an underlying CSV source.
   * @param separator the delimiter to use for separating entries.
   * @param quotechar the character to use for quoted elements.
   * @param escape the character to use for escaping a separator or quote.
   * @param line the line number to skip for start reading
   * @param strictQuotes sets if characters outside the quotes are ignored.
   * @param ignoreLeadingWhiteSpace it true, parser should ignore white space before a quote in a
   *        field.
   */
  public AwReportCsvReader(Reader reader,
      char separator,
      char quotechar,
      char escape,
      int line,
      boolean strictQuotes,
      boolean ignoreLeadingWhiteSpace) {
    super(reader, separator, quotechar, escape, line, strictQuotes, ignoreLeadingWhiteSpace);
  }

  /**
   * Constructs CSVReader with supplied separator and quote char.
   *
   * @param reader the reader to an underlying CSV source.
   * @param separator the delimiter to use for separating entries.
   * @param quotechar the character to use for quoted elements.
   * @param escape the character to use for escaping a separator or quote.
   * @param line the line number to skip for start reading.
   * @param strictQuotes sets if characters outside the quotes are ignored.
   */
  public AwReportCsvReader(Reader reader,
      char separator,
      char quotechar,
      char escape,
      int line,
      boolean strictQuotes) {
    super(reader, separator, quotechar, escape, line, strictQuotes);
  }

  /**
   * Constructs CSVReader with supplied separator and quote char.
   *
   * @param reader the reader to an underlying CSV source.
   * @param separator the delimiter to use for separating entries.
   * @param quotechar the character to use for quoted elements.
   * @param escape the character to use for escaping a separator or quote.
   * @param line the line number to skip for start reading.
   */
  public AwReportCsvReader(Reader reader, char separator, char quotechar, char escape, int line) {
    super(reader, separator, quotechar, escape, line);
  }

  /**
   * Constructs CSVReader with supplied separator and quote char.
   *
   * @param reader the reader to an underlying CSV source.
   * @param separator the delimiter to use for separating entries.
   * @param quotechar the character to use for quoted elements.
   * @param escape the character to use for escaping a separator or quote.
   */
  public AwReportCsvReader(Reader reader, char separator, char quotechar, char escape) {
    super(reader, separator, quotechar, escape);
  }

  /**
   * Constructs CSVReader with supplied separator and quote char.
   *
   * @param reader the reader to an underlying CSV source.
   * @param separator the delimiter to use for separating entries.
   * @param quotechar the character to use for quoted elements.
   * @param line the line number to skip for start reading.
   */
  public AwReportCsvReader(Reader reader, char separator, char quotechar, int line) {
    super(reader, separator, quotechar, line);
  }

  /**
   * Constructs CSVReader with supplied separator and quote char.
   *
   * @param reader the reader to an underlying CSV source.
   * @param separator the delimiter to use for separating entries.
   * @param quotechar the character to use for quoted elements.
   */
  public AwReportCsvReader(Reader reader, char separator, char quotechar) {
    super(reader, separator, quotechar);
  }

  /**
   * Constructs CSVReader with supplied separator.
   *
   * @param reader the reader to an underlying CSV source.
   * @param separator the delimiter to use for separating entries.
   */
  public AwReportCsvReader(Reader reader, char separator) {
    super(reader, separator);
  }

  /**
   * C'tor
   *
   * @param reader the reader to be decorated
   */
  public AwReportCsvReader(Reader reader) {
    super(reader);
  }

  /**
   * Returns the next CSV line in the file. If the line starts with the AW last line total String,
   * than returns null.
   *
   * @return the next line in the CSV, or {@code null} in case the file has ended, or the total line
   *         was reached.
   */
  @Override
  public String[] readNext() throws IOException {

    String[] next = super.readNext();
    if (next != null && !next[0].toLowerCase().equals(AW_REPORT_CSV_TOTAL)) {
      return next;
    }
    return null;
  }

}
