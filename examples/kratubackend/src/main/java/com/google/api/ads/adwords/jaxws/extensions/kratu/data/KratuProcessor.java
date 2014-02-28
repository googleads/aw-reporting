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

import com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister;
import com.google.api.ads.adwords.jaxws.extensions.report.model.util.DateUtil;
import com.google.common.base.Stopwatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
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

  private String mccAccountId = null;
  private StorageHelper storageHelper;

  @Autowired
  public KratuProcessor(@Value("${mccAccountId}") String mccAccountId) {
    this.mccAccountId = mccAccountId.replaceAll("-", "");
  }

  public void processKratus(String dateStart, String dateEnd) throws InterruptedException {
    processKratus(DateUtil.parseDateTime(dateStart).toDate(), DateUtil.parseDateTime(dateEnd).toDate());
  }

  public RunnableKratu createRunnableKratu(StorageHelper storageHelper, Date dateStart, Date dateEnd) {
    return new RunnableKratu(Long.valueOf(mccAccountId), storageHelper, dateStart, dateEnd);
  }
  
  public void processKratus(Date dateStart, Date dateEnd) throws InterruptedException {
    System.out.println("Processing Kratus for" + mccAccountId);

    // We use a Latch so the main thread knows when all the worker threads are complete.
    final CountDownLatch latch = new CountDownLatch(1);
    Stopwatch stopwatch = Stopwatch.createStarted();

    RunnableKratu runnableKratu = createRunnableKratu(storageHelper, dateStart, dateEnd);

    ExecutorService executorService = Executors.newFixedThreadPool(1);
    runnableKratu.setLatch(latch);
    executorService.execute(runnableKratu);

    latch.await();
    stopwatch.stop();    
  }

  /**
   * @param persister the persister to set
   */
  @Autowired
  public void setPersister(EntityPersister persister) {
    storageHelper = new StorageHelper(persister);
  }
}