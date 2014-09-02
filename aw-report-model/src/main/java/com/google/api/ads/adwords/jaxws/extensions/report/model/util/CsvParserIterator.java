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

import com.google.common.collect.AbstractIterator;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.MappingStrategy;

/**
 * Iterator to read one line at a time from the CSV file.
 * 
 * @param <T> type of class to map.
 */
public class CsvParserIterator<T> extends AbstractIterator<T> {

  private final MappingStrategy<T> mapper;
  private final CSVReader csv;
  private final ModifiedCsvToBean<T> csvToBean;

  /**
   * @param mapper the mapping strategy for the bean
   * @param csv the CSV file reader
   * @param csvToBean the modified implementation of the {@code CsvToBean} to access the parsing
   *        methods
   */
  public CsvParserIterator(
      MappingStrategy<T> mapper, CSVReader csv, ModifiedCsvToBean<T> csvToBean) {
    this.mapper = mapper;
    this.csv = csv;
    this.csvToBean = csvToBean;
  }

  /**
   * @see com.google.common.collect.AbstractIterator#computeNext()
   */
  @Override
  protected T computeNext() {

    try {
      String[] line = this.csv.readNext();
      if (line != null) {
        return this.csvToBean.processLine(this.mapper, line);
      } else {
        this.endOfData();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return null;
  }

}
