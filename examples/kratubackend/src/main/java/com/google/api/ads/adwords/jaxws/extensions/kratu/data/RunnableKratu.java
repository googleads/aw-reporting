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

import com.google.api.ads.adwords.jaxws.extensions.kratu.KratuCompute;
import com.google.common.collect.Lists;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * KratuProcessor
 * 
 * @author jtoledo@google.com (Julian Toledo)
 */
public class RunnableKratu implements Runnable {
  
  private CountDownLatch latch;
  
  Long topAccountId;
  Date dateStart;
  Date dateEnd;
  List<Account> accounts;

  StorageHelper storageHelper;

  public RunnableKratu(Long topAccountId, List<Account> accounts, StorageHelper storageHelper, Date dateStart, Date dateEnd) {
    
    super();
    this.topAccountId = topAccountId;
    this.dateStart = dateStart;
    this.dateEnd = dateEnd;
    this.storageHelper = storageHelper;
    this.accounts = accounts;
  }

  public void run() {
    try {

      System.out.println("\n ** Generating Kratus (for: " + accounts.size() + ") **");
      long start = System.currentTimeMillis();

      // Get all the (not-MCC) Accounts under TopAccount
      int i = 0;
      for (Account account : accounts) {
        if (account != null && !account.getIsCanManageClients()) {
          System.out.println();
          System.out.print(i++);
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(dateStart);
          while(calendar.getTime().compareTo(dateEnd) <= 0) {

            Kratu kratu = KratuCompute.createDailyKratuFromDB(
                storageHelper, topAccountId, account, calendar.getTime());
            if (kratu != null){
              storageHelper.saveKratu(kratu);
            }
            calendar.add(Calendar.DATE, 1);
            System.out.print(".");
          }
        }
      }

      List<String> indexes = Lists.newArrayList();
      indexes.add(Kratu._externalCustomerId);
      indexes.add(Kratu._day);
      storageHelper.getEntityPersister().createIndex(Kratu.class, indexes);

      System.out.println("\n*** Finished generating Kratus in "
          + ((System.currentTimeMillis() - start) / 1000) + " seconds ***");
      System.out.println();

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (this.latch != null) {
        this.latch.countDown();
      }
    }
  }

  /**
   * @param latch the latch to set
   */
  public void setLatch(CountDownLatch latch) {
    this.latch = latch;
  }
}