// Copyright 2014 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.awreporting.server;

import com.google.api.ads.adwords.awreporting.model.util.DateUtil;
import com.google.api.ads.adwords.awreporting.processors.ReportProcessor;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionDateRangeType;

import org.apache.log4j.Logger;

import java.util.Date;
import java.util.Properties;

/**
 * RunnableReport
 * 
 * @author jtoledo@google.com (Julian Toledo)
 */
public class RunnableReport implements Runnable {

  private static final Logger LOGGER = Logger.getLogger(RunnableReport.class);

  private Long topAccountId;
  private ReportProcessor processor;
  private Properties properties;
  private Date dateStart;
  private Date dateEnd;

  public RunnableReport(Long topAccountId, ReportProcessor processor, Properties properties, Date dateStart, Date dateEnd) {
    super();
    this.topAccountId = topAccountId;
    this.processor = processor;
    this.properties = properties;
    this.dateStart = dateStart;
    this.dateEnd = dateEnd;
  }

  public void run() {
    try {
      
      LOGGER.debug("Creating ReportProcessor bean...");

      processor.generateReportsForMCC(null, String.valueOf(topAccountId), ReportDefinitionDateRangeType.CUSTOM_DATE,
          DateUtil.formatYearMonthDayNoDash(dateStart), DateUtil.formatYearMonthDayNoDash(dateEnd), null, properties, null, null);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
