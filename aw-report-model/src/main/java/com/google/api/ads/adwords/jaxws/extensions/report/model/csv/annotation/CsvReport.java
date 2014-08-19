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

package com.google.api.ads.adwords.jaxws.extensions.report.model.csv.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.google.api.ads.adwords.lib.jaxb.v201406.ReportDefinitionReportType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation to indicate that a java Bean class is the representation of the CSV file data.
 *
 * If a class is annotated with {@code CsvReport}, it means that when the CSV file for the mapped
 * report is processed, the values in the CSV columns will be mapped to the annotated properties of
 * the class.
 *
 * @author gustavomoreira@google.com (Gustavo Moreira)
 *
 */
@Target({TYPE})
@Retention(RUNTIME)
public @interface CsvReport {

  /**
   * The type of the report that the annotated Java bean is associated.
   *
   * @return the report definition.
   */
  ReportDefinitionReportType value();

  /**
   * The name of the <i>REPORT</i> fields that will be excluded from this report. The exclusions are
   * used in case that the report class extends from another mapped report class, but not all the
   * fields apply.
   *
   * @return the array with the names of the report fields to be excluded.
   */
  String[] reportExclusions() default {};

}
