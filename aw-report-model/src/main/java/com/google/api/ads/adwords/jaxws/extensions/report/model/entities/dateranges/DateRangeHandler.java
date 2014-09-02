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

package com.google.api.ads.adwords.jaxws.extensions.report.model.entities.dateranges;

import org.joda.time.DateTime;

/**
 * Interface to handle the different types of date range.
 * 
 * @author gustavomoreira
 */
public interface DateRangeHandler {

  /**
   * Retrieves the starting date for the type of date range
   * 
   * @param date
   *            the date to convert
   * @return the starting date
   */
  DateTime retrieveDateStart(DateTime date);

  /**
   * Retrieves the ending date for the type of date range
   * 
   * @param date
   *            the date to convert
   * @return the ending date
   */
  DateTime retrieveDateEnd(DateTime date);

  /**
   * Retrieves the month (if applicable) for the date range type
   * 
   * @param date
   *            the date to convert
   * @return the month
   */
  DateTime retrieveMonth(DateTime date);

}
