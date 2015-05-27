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

package com.google.api.ads.adwords.awreporting.server.kratu;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.api.ads.adwords.awreporting.model.persistence.EntityPersister;
import com.google.api.ads.adwords.awreporting.model.util.DateUtil;
import com.google.api.ads.adwords.awreporting.processors.ReportProcessor;
import com.google.api.ads.adwords.awreporting.server.entities.Account;
import com.google.api.ads.adwords.awreporting.server.util.StorageHelper;
import com.google.api.ads.adwords.jaxws.v201502.mcm.Customer;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Test case for the {@code RunnableKratu} class.
 * 
 * @author jtoledo@google.com (Julian Toledo)
 */
public class KratuProcessorTest {

  @Spy
  private KratuProcessor kratuProcessor;

  @Mock
  private EntityPersister mockedEntitiesPersister;

  @Mock
  private ReportProcessor mockedReportProcessor;

  @Spy
  private StorageHelper  storageHelper;

  private final Date dateStart = DateUtil.parseDateTime("20140101").toDate();
  private final Date dateEnd = DateUtil.parseDateTime("20140131").toDate();

  @Before
  public void setUp() throws Exception {
    storageHelper = new StorageHelper(mockedEntitiesPersister);

    kratuProcessor = new KratuProcessor();

    MockitoAnnotations.initMocks(this);

    kratuProcessor.setPersister(mockedEntitiesPersister);
    kratuProcessor.setReportProcessor(mockedReportProcessor);
    storageHelper.setPersister(mockedEntitiesPersister);

    Customer customer1 = new Customer();
    customer1.setCustomerId(1L);
    Customer customer2 = new Customer();
    customer2.setCustomerId(2L);
    List<Customer> customers = ImmutableList.of(customer1, customer2);
    when(mockedReportProcessor.getAccountsInfo(
        Mockito.anyString(), Mockito.anyString(), Mockito.anySetOf(Long.class))).thenReturn(customers);
  }

  @Test
  public void testCreateRunnableKratu() {
    Set<Long> accountIds = ImmutableSet.of(1L, 2L);

    RunnableKratu runnableKratu = kratuProcessor.createRunnableKratu(1L, accountIds, storageHelper, dateStart, dateEnd);
    assertNotNull(runnableKratu);

    verify(kratuProcessor, times(1)).createRunnableKratu(Mockito.anyLong(),
        Mockito.anySetOf(Long.class), Mockito.any(StorageHelper.class), Mockito.any(Date.class), Mockito.any(Date.class));

    verify(mockedEntitiesPersister, times(1)).save(Mockito.anyListOf(Account.class));
  }
}
