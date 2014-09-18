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

package com.google.api.ads.adwords.awreporting.kratubackend.util;

import com.google.api.ads.adwords.awreporting.kratubackend.KratuCompute;
import com.google.api.ads.adwords.awreporting.kratubackend.entities.Kratu;
import com.google.api.ads.adwords.awreporting.model.persistence.EntityPersister;
import com.google.api.ads.adwords.awreporting.server.entities.Account;
import com.google.api.ads.adwords.awreporting.server.util.StorageHelper;
import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * KratuStorageHelper
 * 
 * @author jtoledo@google.com (Julian Toledo)
 */
@Component
@Qualifier("kratuStorageHelper")
public class KratuStorageHelper extends StorageHelper {

  public KratuStorageHelper() {
    super();
  }

  public KratuStorageHelper(EntityPersister entityPersister) {
    super(entityPersister);
  }

  // Kratu
  public Kratu saveKratu(Kratu kratu) {
    return getEntityPersister().save(kratu);
  }

  public List<Kratu> getKratus(Long accountId) {
    return getEntityPersister().get(Kratu.class, Kratu.EXTERNAL_CUSTOMER_ID, accountId);
  }

  public List<Kratu> getKratus(Long topAccountId, Date startDate, Date endDate) {
    List<Kratu> kratusSummary = Lists.newArrayList();
    List<Account> listAccounts = getEntityPersister().get(Account.class, Account.TOP_ACCOUNT_ID, topAccountId);

    System.out.println("\n ** Summary Kratus (for: " + listAccounts.size() + ") **");
    long start = System.currentTimeMillis();

    // Get all the (not-MCC) Accounts under TopAccount
    int i = 0;
    for (Account account : listAccounts) {
      System.out.println();
      System.out.print(i++ + " ");
      List<Kratu> accountDailyKratus = getEntityPersister().get(Kratu.class, Kratu.EXTERNAL_CUSTOMER_ID,
          account.getExternalCustomerId(), Kratu.DAY, startDate, endDate);
      if (accountDailyKratus != null && accountDailyKratus.size() > 0) {
        kratusSummary.add(KratuCompute.createKratuSummary(accountDailyKratus, startDate, endDate));
      }
    }

    System.out.println("\n*** Finished Summary Kratus in "
        + ((System.currentTimeMillis() - start) / 1000) + " seconds ***");
    System.out.println();
    
    return kratusSummary;
  }

  public void createReportIndexes() {
    // Create AwReportingServer Indexes
    super.createReportIndexes();

    // Create Kratu Indexes
    List<String> kratuIndexes = Lists.newArrayList();
    kratuIndexes.add("externalCustomerId");
    kratuIndexes.add("day");
    getEntityPersister().createIndex(Kratu.class, kratuIndexes);
  }
}
