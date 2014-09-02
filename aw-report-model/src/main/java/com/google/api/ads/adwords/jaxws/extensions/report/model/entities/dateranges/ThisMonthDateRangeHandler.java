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
 * Handles the date range for the THIS_MONTH type.
 * 
 * @author gustavomoreira
 */
public class ThisMonthDateRangeHandler implements DateRangeHandler {

  @Override
  public DateTime retrieveDateStart(DateTime date) {
    return new DateTime(date.getYear(), date.getMonthOfYear(), 1, 12, 0);
  }

  @Override
  public DateTime retrieveDateEnd(DateTime date) {
    DateTime plusMonth = date.plusMonths(1);
    plusMonth = new DateTime(plusMonth.getYear(),
        plusMonth.getMonthOfYear(), 1, 12, 0);
    return plusMonth.minusDays(1);
  }

  @Override
  public DateTime retrieveMonth(DateTime date) {
    return date;
  }

}
