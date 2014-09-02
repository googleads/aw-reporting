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

package com.google.api.ads.adwords.jaxws.extensions.kratu.data;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.Report;
import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.ReportAccount;
import com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister;
import com.google.api.ads.adwords.jaxws.extensions.report.model.util.DateUtil;
import com.google.api.ads.adwords.lib.utils.ReportDownloadResponseException;
import com.google.api.ads.adwords.lib.utils.ReportException;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.common.collect.ImmutableList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

/**
 * Test case for the {@code RunnableKratu} class.
 * 
 * @author jtoledo@google.com (Julian Toledo)
 */
public class RunnableKratuTest {

  @Spy
  private RunnableKratu mockedRunnableKratu;

  @Mock
  private EntityPersister mockedEntitiesPersister;

  private StorageHelper  storageHelper;

  private ImmutableList<Account> accountList;
  
  private final Date dateStart = DateUtil.parseDateTime("20140101").toDate();
  private final Date dateEnd = DateUtil.parseDateTime("20140131").toDate();

  @Before
  public void setUp() {
    // creating one list wuth 3 nonMCC accounts
    Account account1 = new Account();
    account1.setTopAccountId(456L);
    account1.setIsCanManageClients(false);
    account1.setExternalCustomerId(123L);
    accountList = ImmutableList.of(account1, account1, account1);

    storageHelper = new StorageHelper();

    mockedRunnableKratu = new RunnableKratu(456L, accountList, storageHelper, dateStart, dateEnd);

    MockitoAnnotations.initMocks(this);

    when(mockedEntitiesPersister.get(Account.class, "topAccountId", 456L)).thenReturn(accountList);
    storageHelper.setPersister(mockedEntitiesPersister);
  }

  @Test
  public void testRun()
      throws FileNotFoundException,
      ValidationException,
      ReportException,
      ReportDownloadResponseException,
      IOException {

    mockedRunnableKratu.run();
    verify(mockedRunnableKratu, times(1)).run();

    verify(mockedEntitiesPersister, times(3)).get(
        ReportAccount.class, Report.ACCOUNT_ID, 123L, Report.DAY, dateStart, dateStart);    
  }
}
