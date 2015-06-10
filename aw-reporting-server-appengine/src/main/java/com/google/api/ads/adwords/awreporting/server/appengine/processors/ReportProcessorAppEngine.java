// Copyright 2012 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.awreporting.server.appengine.processors;

import com.google.api.ads.adwords.awreporting.model.entities.Report;
import com.google.api.ads.adwords.awreporting.processors.ReportProcessor;
import com.google.api.ads.adwords.awreporting.server.appengine.util.MccTaskCounter;
import com.google.api.ads.adwords.awreporting.server.entities.Account;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinition;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionDateRangeType;
import com.google.api.ads.adwords.lib.jaxb.v201502.ReportDefinitionReportType;
import com.google.api.client.util.Sets;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Main reporting processor responsible for downloading and saving the files to
 * the file system. The persistence of the parsed beans is delegated to the
 * configured persister.
 * 
 * @author jtoledo@google.com (Julian Toledo)
 * @author gustavomoreira@google.com (Gustavo Moreira)
 */
@Component
public class ReportProcessorAppEngine extends ReportProcessor {

  private static final Logger LOGGER = Logger
      .getLogger(ReportProcessorAppEngine.class.getName());

  /**
   * Constructor.
   * 
   * @param mccAccountId the MCC account ID
   * @param developerToken the developer token
   * @param companyName the company name (optional)
   * @param clientId the OAuth2 authentication clientId
   * @param clientSecret the OAuth2 authentication clientSecret
   * @param reportRowsSetSize the size of the set parsed before send to the DB
   */
  @Autowired
  public ReportProcessorAppEngine(
      @Value(value = "${aw.report.processor.rows.size:}") Integer reportRowsSetSize,
      @Value(value = "${aw.report.processor.threads:}") Integer numberOfReportProcessors) {

    if (reportRowsSetSize != null && reportRowsSetSize > 0) {
      this.reportRowsSetSize = reportRowsSetSize;
    }
  }

  /**
   * Caches the accounts into a temporary file.
   * 
   * @param accountIdsSet
   *            the set with all the accounts
   */
  protected void cacheAccounts(Set<Long> accountIdsSet) {
    // TODO: Cache accounts on DB insted of File.
  }

  /**
   * Generate all the mapped reports to the given account IDs.
   * 
   * @param dateRangeType the date range type.
   * @param dateStart the starting date in YYYYMMDD format
   * @param dateEnd the ending date in YYYYMMDD format
   * @param accountIdsSet the account IDs.
   * @param properties the properties file
   * 
   * @reportType
   * @reportFieldsToInclude
   * 
   * @throws Exception error reaching the API.
   */
  public void generateReportsForMCC(String mccAccountId,
      ReportDefinitionDateRangeType dateRangeType, String dateStart,
      String dateEnd, Set<Long> accountIdsSet, Properties properties,
      ReportDefinitionReportType onDemandReportType, List<String> reportFieldsToInclude)
          throws Exception {

    LOGGER.info("*** Retrieving account IDs ***");

    if (accountIdsSet == null || accountIdsSet.size() == 0) {
      // Getting accounts Ids from DB
      accountIdsSet = Sets.newHashSet();
      List<Account> accounts = persister.get(Account.class, Account.TOP_ACCOUNT_ID, Long.valueOf(mccAccountId));
      for (Account account : accounts) {
        accountIdsSet.add(Long.valueOf(account.getId()));
      }
    }

    LOGGER.info("*** Generating Reports for " + accountIdsSet.size()
        + " accounts ***");

    Stopwatch stopwatch = Stopwatch.createStarted();

    // We will download/generate the reportType with reportFieldsToInclude fields
    // if null we will use the Properties file report definitions
    if (onDemandReportType != null) {

      // Skip properties file
      this.downloadAndProcess(mccAccountId, onDemandReportType, dateRangeType, dateStart, dateEnd, accountIdsSet, properties);
      
    } else {
      
      Set<ReportDefinitionReportType> allReportTypes = this.csvReportEntitiesMapping.getDefinedReports();

      // Iterate over properties file
      for (ReportDefinitionReportType propertiesReportType : allReportTypes) {
        if (properties.containsKey(propertiesReportType.name())) {
          this.downloadAndProcess(mccAccountId, propertiesReportType, dateRangeType,dateStart, dateEnd, accountIdsSet, properties);
        }
      }
    }

    stopwatch.stop();
    LOGGER.info("*** Finished processing all reports in "
        + (stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000)
        + " seconds ***\n");
  }

  /**
   * Creates the main tasks that will create the sub tasks to
   * download each report, split in 500 reports per main task.
   * 
   * @param builder the session builder.
   * @param reportType the report type.
   * @param dateRangeType the date range type.
   * @param dateStart the start date.
   * @param dateEnd the ending date.
   * @param acountIdList the account IDs.
   * @param properties the properties resource.
   */
  private <R extends Report> void downloadAndProcess(
      String mccAccountId,
      ReportDefinitionReportType reportType,
      ReportDefinitionDateRangeType dateRangeType, String dateStart,
      String dateEnd, Set<Long> acountIdList, Properties properties) {

    // Download Reports to local files and Generate Report objects
    LOGGER.info("\n\n ** Generating Taks for : " + reportType.name() + " **");

    // No multiple reports of the same type
    ReportDefinition reportDefinition = getReportDefinition(reportType,
        dateRangeType, dateStart, dateEnd, reportType.value(), properties);
        
    @SuppressWarnings("unchecked")
    Class<R> reportBeanClass = (Class<R>) this.csvReportEntitiesMapping.getReportBeanClass(reportType);

    MccTaskCounter.increasePendingProcessTasks(Long.valueOf(mccAccountId), acountIdList.size());

    // Create a task for each 500 accounts that will create sub-tasks
    for (List<Long> partition : Iterables.partition(acountIdList, 500)) {

      // We will make the queues wait 10 seconds to make sure all Creation tasks get queued.
      // Partition needs to be serializable
      LOGGER.info(reportType.name() + " " + partition.size()); 
      QueueFactory.getDefaultQueue().add(TaskOptions.Builder.withPayload(    
          new ReportTaskCreator<R>(
              mccAccountId,
              Lists.newArrayList(partition),
              reportDefinition,
              dateStart,
              dateEnd,
              reportRowsSetSize,
              reportType,
              dateRangeType,
              reportBeanClass)).countdownMillis(10*1000l));
    }
  }
}

