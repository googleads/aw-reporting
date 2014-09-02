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

package com.google.api.ads.adwords.jaxws.extensions.kratu.data;

import com.google.api.ads.adwords.jaxws.extensions.processors.ReportProcessor;
import com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister;
import com.google.api.ads.adwords.jaxws.extensions.report.model.util.DateUtil;
import com.google.api.client.util.Lists;
import com.google.common.base.Stopwatch;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * KratuProcessor
 * 
 * @author jtoledo@google.com (Julian Toledo)
 */
@Component
public class KratuProcessor {
  
  private static final Logger LOGGER = Logger.getLogger(KratuProcessor.class);

  private StorageHelper storageHelper;
  private ReportProcessor reportProcessor;

  public KratuProcessor() {
  }

  /*
   * Creates the daily Kratus for each account on the list between the date range
   */
  public void processKratus(Long topAccountId,  Set<Long> accountIdsSet, String dateStart, String dateEnd) throws InterruptedException {
    processKratus(topAccountId, accountIdsSet, DateUtil.parseDateTime(dateStart).toDate(), DateUtil.parseDateTime(dateEnd).toDate());
  }

  /*
   * Creates the daily Kratus for each account on the list between the date range
   */
  public void processKratus(Long topAccountId, Set<Long> accountIdsSet, Date dateStart, Date dateEnd) throws InterruptedException {
    System.out.println("Processing Kratus for " + topAccountId);

    // We use a Latch so the main thread knows when all the worker threads are complete.
    final CountDownLatch latch = new CountDownLatch(1);
    Stopwatch stopwatch = Stopwatch.createStarted();

    RunnableKratu runnableKratu = createRunnableKratu(topAccountId, accountIdsSet, storageHelper, dateStart, dateEnd);

    ExecutorService executorService = Executors.newFixedThreadPool(1);
    runnableKratu.setLatch(latch);
    executorService.execute(runnableKratu);

    latch.await();
    stopwatch.stop();    
  }

  public RunnableKratu createRunnableKratu(Long topAccountId, Set<Long> accountIdsSet, StorageHelper storageHelper, Date dateStart, Date dateEnd) {

    List<Account> accounts = Lists.newArrayList();

    // Update accounts if not accounts were provided at hte accountIdsFile
    if (accountIdsSet == null || accountIdsSet.size() == 0) {
      accounts = updateAccounts(topAccountId);
    } else {
      try {

        LOGGER.info("Fetching data for accounts on the accountIdsFile from the API");
        accounts = Account.fromCustomerList(reportProcessor.getAccountsInfo(null, String.valueOf(topAccountId), accountIdsSet), topAccountId);
        storageHelper.getEntityPersister().save(accounts);
        
        System.out.println("Updating DB indexes... (may take long)");
        storageHelper.createReportIndexes();

      } catch (Exception e) {
        LOGGER.error( "Error Updating Accounts: " + e.getMessage() );
        e.printStackTrace();
      }
    }

    return new RunnableKratu(topAccountId,  accounts, storageHelper, dateStart, dateEnd);
  }

  /**
   * Refreshes the Accounts by downloading the whole list using the API
   * and refreshes the report indexes before heavily reading reports.
   */
  private List<Account> updateAccounts(Long topAccountId) {
    List<Account> accounts = Lists.newArrayList();
    // Refresh Account List and refresh indexes
    System.out.println("Updating Accounts from server... (may take long)");
    try {

      accounts = Account.fromManagedCustomerList(reportProcessor.getAccounts(null, String.valueOf(topAccountId)), topAccountId);
      storageHelper.getEntityPersister().save(accounts);
      
      System.out.println("Updating DB indexes... (may take long)");
      storageHelper.createReportIndexes();

    } catch (Exception e) {
      LOGGER.error( "Error Updating Accounts: " + e.getMessage() );
    }
    return accounts;
  }

  /**
   * @param persister the persister to set
   */
  @Autowired
  public void setPersister(EntityPersister persister) {
    storageHelper = new StorageHelper(persister);
  }

  /**
   * @param reportProcessor the reportProcessor to set
   */
  @Autowired
  public void setReportProcessor(ReportProcessor reportProcessor) {
    this.reportProcessor = reportProcessor;
  }
}
