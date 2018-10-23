// Copyright 2016 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.awreporting.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.api.ads.adwords.jaxws.v201809.cm.Selector;
import com.google.api.ads.adwords.jaxws.v201809.mcm.ApiException;
import com.google.api.ads.adwords.jaxws.v201809.mcm.ManagedCustomer;
import com.google.api.ads.adwords.jaxws.v201809.mcm.ManagedCustomerPage;
import com.google.api.ads.adwords.jaxws.v201809.mcm.ManagedCustomerServiceInterface;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Test case for the {@code ManagedCustomerServiceDelegate} class.
 */
@RunWith(JUnit4.class)
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
    ManagedCustomerPage managedCustomerPage 
      = new ManagedCustomerPage();
    managedCustomerPage.getEntries().add(managedCustomer);
    managedCustomerPage.setTotalNumEntries(1);

    MockitoAnnotations.initMocks(this);

    when(managedCustomerServiceInterfaceMock.get(
        Mockito.<Selector>anyObject())).thenReturn(managedCustomerPage);
        
    managedCustomerDelegate = new ManagedCustomerDelegate(null, false) {
      @Override
      ManagedCustomerServiceInterface getManagedCustomerServiceInterface(
          AdWordsSession adWordsSession){
        return managedCustomerServiceInterfaceMock;
      }
    };
  }

  /**
   * Tests the getClientCustomerIds(AdWordsSession adWordsSession).
   *
   * @throws ApiException, exception class for holding a list of service errors
   */
  @Test
  public void testGetClientCustomerIds() throws ApiException {
    Set<Long> set = managedCustomerDelegate.getClientCustomerIds();

    assertTrue(set.contains(123L));

    ArgumentCaptor<Selector> argument = ArgumentCaptor.forClass(Selector.class);
    verify(managedCustomerServiceInterfaceMock).get(argument.capture());
    assertEquals(1, argument.getValue().getFields().size());
    assertTrue(argument.getValue().getFields().contains("CustomerId"));
  }
}
