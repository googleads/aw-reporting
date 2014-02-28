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

package com.google.api.ads.adwords.jaxws.extensions.util;

import com.google.api.ads.adwords.jaxws.factory.AdWordsServices;
import com.google.api.ads.adwords.jaxws.v201309.cm.Paging;
import com.google.api.ads.adwords.jaxws.v201309.cm.Selector;
import com.google.api.ads.adwords.jaxws.v201309.mcm.ApiException;
import com.google.api.ads.adwords.jaxws.v201309.mcm.ManagedCustomer;
import com.google.api.ads.adwords.jaxws.v201309.mcm.ManagedCustomerPage;
import com.google.api.ads.adwords.jaxws.v201309.mcm.ManagedCustomerServiceInterface;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.common.collect.Lists;

import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * {@code ManagedCustomerDelegate} encapsulates the {@code ManagedCustomerServiceInterface}.
 *
 * This service is used to get all the child accounts for the MCC.
 *
 * @author jtoledo@google.com (Julian Toledo)
 * @author gustavomoreira@google.com (Gustavo Moreira)
 */
public class ManagedCustomerDelegate {

  private static final Logger LOGGER = Logger.getLogger(ManagedCustomerDelegate.class);

  /**
   * The amount of results paginated when retrieving the next page of resuts.
   */
  private static final int NUMBER_OF_RESULTS = 500;

  private ManagedCustomerServiceInterface managedCustomerService;

  /**
   * Default Constructor.
   *
   * @param adWordsSession the {@code adWordsSession} to use with the delegate/service
   */
  public ManagedCustomerDelegate(AdWordsSession adWordsSession) {
    this.managedCustomerService =
        new AdWordsServices().get(adWordsSession, ManagedCustomerServiceInterface.class);
  }

  /**
   * Constructor with a ManagedCustomerServiceInterface.
   *
   * @param managedCustomerService the costumer service available for the session
   */
  public ManagedCustomerDelegate(ManagedCustomerServiceInterface managedCustomerService) {
    this.managedCustomerService = managedCustomerService;
  }

  /**
   * Gets all the accounts IDs for the {@link AdWordsSession}.
   *
   *  The accounts are read in complete, and after all accounts have been retrieved, their IDs are
   * extracted and returned in a {@code Set}.
   *
   * @return the {@link Set} with the IDs of the found accounts
   * @throws ApiException error from the API when retrieving the accounts
   */
  public Set<Long> getAccountIds() throws ApiException {
    Set<Long> accountIdsSet = new HashSet<Long>();
    List<ManagedCustomer> accounts = this.getAccounts();

    if (accounts != null) {
      for (ManagedCustomer account : accounts) {
        if (!account.isCanManageClients()) {
          accountIdsSet.add(account.getCustomerId());
        }
      }
    }
    return accountIdsSet;
  }

  /**
   * Gets all the accounts for the {@code AdWordsSession}.
   *
   * @return the list of accounts.
   * @throws ApiException error retrieving the accounts.
   */
  public List<ManagedCustomer> getAccounts() throws ApiException {
    Selector selector = createAccountSelectorFields();

    // Paging results to avoid RESPONSE_SIZE_LIMIT_EXCEEDED
    List<ManagedCustomer> accountList = Lists.newArrayList();
    try {
      this.retrieveAccounts(selector, accountList);
    } catch (ExceptionInInitializerError e) {
      throw new ApiException(
          "Error on managedCustomerService.get(selector), probably an AuthenticationError",
          new com.google.api.ads.adwords.jaxws.v201309.cm.ApiException());
    }
    return accountList;
  }

  /**
   * Retrieve all the accounts for the selector.
   *
   * @param selector the accounts selector
   * @param accountList the list of accounts to be populated
   * @throws ApiException error retrieving the accounts page
   */
  private void retrieveAccounts(Selector selector, List<ManagedCustomer> accountList)
      throws ApiException {

    int startIndex = 0;
    Paging paging = new Paging();
    selector.setPaging(paging);
    ManagedCustomerPage managedCustomerPage;
    do {
      paging.setStartIndex(startIndex);
      paging.setNumberResults(NUMBER_OF_RESULTS);

      LOGGER.info("Retrieving next " + NUMBER_OF_RESULTS + " accounts.");

      managedCustomerPage = managedCustomerService.get(selector);
      accountList.addAll(managedCustomerPage.getEntries());

      LOGGER.info(accountList.size() + " accounts retrieved.");

      startIndex = startIndex + NUMBER_OF_RESULTS;
    } while (managedCustomerPage.getTotalNumEntries() > startIndex);
  }

  /**
   * Creates a Generic Selector for Managed Customers queries.
   *
   * @return the default {@link Selector} to retrieve the accounts
   */
  private Selector createAccountSelectorFields() {
    Selector selector = new Selector();
    selector.getFields().add("CustomerId");
    selector.getFields().add("CanManageClients");
    selector.getFields().add("Name");
    selector.getFields().add("CompanyName");
    selector.getFields().add("CurrencyCode");
    selector.getFields().add("DateTimeZone");
    return selector;
  }
}
