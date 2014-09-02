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
 * Handles the date range for the LAST_MONTH type.
 * 
 * @author gustavomoreira
 */
public class LastMonthDateRangeHandler implements DateRangeHandler {

  @Override
  public DateTime retrieveDateStart(DateTime date) {
    DateTime minusMonth = date.minusMonths(1);
    return new DateTime(minusMonth.getYear(), minusMonth.getMonthOfYear(),
        1, 12, 0);
  }

  @Override
  public DateTime retrieveDateEnd(DateTime date) {
    DateTime sameDate = new DateTime(date.getYear(), date.getMonthOfYear(),
        1, 12, 0);
    return sameDate.minusDays(1);
  }

  @Override
  public DateTime retrieveMonth(DateTime date) {
    return date.minusMonths(1);
  }

}
