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

package com.google.api.ads.adwords.jaxws.extensions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.api.ads.adwords.jaxws.extensions.util.ManagedCustomerDelegate;
import com.google.api.ads.adwords.jaxws.v201309.cm.Selector;
import com.google.api.ads.adwords.jaxws.v201309.mcm.ApiException;
import com.google.api.ads.adwords.jaxws.v201309.mcm.ManagedCustomer;
import com.google.api.ads.adwords.jaxws.v201309.mcm.ManagedCustomerPage;
import com.google.api.ads.adwords.jaxws.v201309.mcm.ManagedCustomerServiceInterface;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;

/**
 * Test case for the {@code ManagedCustomerServiceDelegate} class.
 * 
 * @author jtoledo@google.com (Julian Toledo)
 */
public class ManagedCustomerServiceDelegateTest {

  private ManagedCustomer managedCustomer;
  @Mock
  private ManagedCustomerServiceInterface managedCustomerServiceInterfaceMock;
  private ManagedCustomerDelegate managedCustomerDelegate;

  @Before
  public void setUp() throws ApiException {
    managedCustomer = new ManagedCustomer();
    managedCustomer.setCustomerId(123L);
    managedCustomer.setCanManageClients(false);
    ManagedCustomerPage managedCustomerPage = new ManagedCustomerPage();
    managedCustomerPage.getEntries().add(managedCustomer);
    managedCustomerPage.setTotalNumEntries(1);

    MockitoAnnotations.initMocks(this);

    when(managedCustomerServiceInterfaceMock.get(
        Mockito.<Selector>anyObject())).thenReturn(managedCustomerPage);
        
    managedCustomerDelegate = new ManagedCustomerDelegate(managedCustomerServiceInterfaceMock);
  }

  /**
   * Tests the getAccountIds(AdWordsSession adWordsSession).
   *
   * @throws ApiException, exception class for holding a list of service errors
   */
  @Test
  public void testGetAccountIds() throws ApiException {
    Set<Long> set = managedCustomerDelegate.getAccountIds();

    assertTrue(set.contains(123L));

    ArgumentCaptor<Selector> argument = ArgumentCaptor.forClass(Selector.class);
    verify(managedCustomerServiceInterfaceMock).get(argument.capture());
    assertEquals(argument.getValue().getFields().size(), 6);
    assertTrue(argument.getValue().getFields().contains("CustomerId"));
    assertTrue(argument.getValue().getFields().contains("CanManageClients"));
    assertTrue(argument.getValue().getFields().contains("Name"));
    assertTrue(argument.getValue().getFields().contains("CompanyName"));
    assertTrue(argument.getValue().getFields().contains("CurrencyCode"));
    assertTrue(argument.getValue().getFields().contains("DateTimeZone"));
  }

  /**
   * Tests the getAccounts(AdWordsSession adWordsSession).
   *
   * @throws ApiException, exception class for holding a list of service errors
   */
  @Test
  public void testGetAccounts() throws ApiException {
    List<ManagedCustomer> list = managedCustomerDelegate.getAccounts();

    assertEquals(list.get(0), managedCustomer);
    assertEquals(list.get(0).getCustomerId(), new Long(123L));

    ArgumentCaptor<Selector> argument = ArgumentCaptor.forClass(Selector.class);
    verify(managedCustomerServiceInterfaceMock).get(argument.capture());
    assertEquals(argument.getValue().getFields().size(), 6);
    assertTrue(argument.getValue().getFields().contains("CustomerId"));
    assertTrue(argument.getValue().getFields().contains("CanManageClients"));
    assertTrue(argument.getValue().getFields().contains("Name"));
    assertTrue(argument.getValue().getFields().contains("CompanyName"));
    assertTrue(argument.getValue().getFields().contains("CurrencyCode"));
    assertTrue(argument.getValue().getFields().contains("DateTimeZone"));
  }
}
