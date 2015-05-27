//Copyright 2014 Google Inc. All Rights Reserved.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.

package com.google.api.ads.adwords.awreporting.server.appengine.processors;

import com.google.api.ads.adwords.awreporting.server.appengine.RestServer;
import com.google.api.ads.adwords.awreporting.server.appengine.exporter.ExportTaskCreator;
import com.google.api.ads.adwords.awreporting.server.appengine.util.MccTaskCounter;
import com.google.api.ads.adwords.awreporting.server.entities.Account;
import com.google.api.ads.adwords.jaxws.v201502.mcm.ManagedCustomer;
import com.google.appengine.api.taskqueue.DeferredTask;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import java.util.List;
import java.util.logging.Logger;

/**
 * Tasks to Refresh the Account List for an MCC.
 * 
 * @author jtoledo@google.com (Julian Toledo)
 */
public class RefreshAccountsTask implements DeferredTask {

  private static final long serialVersionUID = 1L;
  private static final Logger LOGGER = Logger.getLogger(ExportTaskCreator.class.getName());

  private String userId;
  private String mccAccountId;

  public RefreshAccountsTask(final String userId, final String mccAccountId) {
    this.userId = userId;
    this.mccAccountId = mccAccountId;
  }

  @Override
  public void run() {

    LOGGER.info("Starting refresh accounts list task for mcc: " + this.mccAccountId);

    try {

      MccTaskCounter.decreasePendingProcessAccountsTasks(Long.valueOf(mccAccountId));
      
      ReportProcessorAppEngine reportProcessor = RestServer.getApplicationContext().getBean(ReportProcessorAppEngine.class);
      
      List<ManagedCustomer> listAccountsApi =
          reportProcessor.getAccounts(userId, mccAccountId);

      List<Account> accounts = Account.fromManagedCustomerList(listAccountsApi, Long.valueOf(mccAccountId));      

      RestServer.getPersister().save(accounts);

    } catch (Exception e) {      
      LOGGER.severe("Error refreshing accounts list for mcc: " + this.mccAccountId + " " + e.getMessage());
      e.printStackTrace(); 
    }
  }

  public static void createRefreshAccountsTask(String userId, String mccAccountId) {

    MccTaskCounter.increasePendingProcessAccountsTasks(Long.valueOf(mccAccountId));

    QueueFactory.getQueue("default").add(TaskOptions.Builder.withPayload(
        new RefreshAccountsTask(userId, mccAccountId)));
  }
}

