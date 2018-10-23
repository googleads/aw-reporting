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

import com.google.api.ads.adwords.jaxws.utils.v201809.SelectorBuilder;
import com.google.api.ads.adwords.jaxws.v201809.cm.Selector;
import com.google.api.ads.adwords.jaxws.v201809.mcm.ApiException;
import com.google.api.ads.adwords.jaxws.v201809.mcm.ManagedCustomer;
import com.google.api.ads.adwords.jaxws.v201809.mcm.ManagedCustomerPage;
import com.google.api.ads.adwords.jaxws.v201809.mcm.ManagedCustomerServiceInterface;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.selectorfields.v201809.cm.ManagedCustomerField;
import com.google.common.annotations.VisibleForTesting;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@code ManagedCustomerDelegate} encapsulates the {@code ManagedCustomerServiceInterface}.
 *
 * <p>This service is used to get all the child accounts for the manager account.
 */
public class ManagedCustomerDelegate {
  private static final Logger LOGGER = LoggerFactory.getLogger(ManagedCustomerDelegate.class);

  /**
   * The number of results paginated when retrieving the next page of results.
   */
  private static final int NUMBER_OF_RESULTS = 1000;

  private final ManagedCustomerServiceInterface managedCustomerService;
  private final boolean excludeHiddenAccounts;

  public ManagedCustomerDelegate(AdWordsSession adWordsSession, boolean excludeHiddenAccounts) {
    this.managedCustomerService =
        getManagedCustomerServiceInterface(adWordsSession);
    this.excludeHiddenAccounts = excludeHiddenAccounts;
  }

  /**
   * Returns a ManagedCustomerServiceInterface with the given adWordsSession.
   */
  @VisibleForTesting
  ManagedCustomerServiceInterface getManagedCustomerServiceInterface(
      AdWordsSession adWordsSession){
    return AdWordsServicesUtil.getService(adWordsSession, ManagedCustomerServiceInterface.class);
  }

  /**
   * Gets all the client customer IDs for the {@link AdWordsSession}.
   *
   * <p>The accounts are read in complete, and after all accounts have been retrieved, their IDs
   * are extracted and returned in a {@code Set}.
   *
   * @return the {@link Set} with the IDs of the found accounts
   * @throws ApiException error from the API when retrieving the accounts
   */
  public Set<Long> getClientCustomerIds() throws ApiException {
    Set<Long> clientCustomerIdsSet = new LinkedHashSet<Long>();
    ManagedCustomerPage managedCustomerPage;
    int offset = 0;

    SelectorBuilder builder = new SelectorBuilder();
    Selector selector =
        builder.fields(ManagedCustomerField.CustomerId)
            .offset(offset)
            .limit(NUMBER_OF_RESULTS)
            .equals(ManagedCustomerField.CanManageClients, String.valueOf(false))
            .equals("ExcludeHiddenAccounts", String.valueOf(excludeHiddenAccounts))
            .build();

    do {
      LOGGER.info("Retrieving next {} accounts.", NUMBER_OF_RESULTS);

      managedCustomerPage = managedCustomerService.get(selector);
      addClientCustomerIds(managedCustomerPage, clientCustomerIdsSet);

      LOGGER.info("{} accounts retrieved.", clientCustomerIdsSet.size());
      offset += NUMBER_OF_RESULTS;
      selector = builder.increaseOffsetBy(NUMBER_OF_RESULTS).build();
    } while (managedCustomerPage.getTotalNumEntries() > offset);

    return clientCustomerIdsSet;
  }


  /**
   * Add client customer IDs into the result set.
   *
   * @param managedCustomerPage the page of managed customers
   * @param clientCustomerIdsSet the result set of client customer IDs
   */
  private static void addClientCustomerIds(
      @Nullable ManagedCustomerPage managedCustomerPage, Set<Long> clientCustomerIdsSet) {
    if (managedCustomerPage != null) {
      List<ManagedCustomer> managedCustomers = managedCustomerPage.getEntries();

      // ManagedCustomerPage.getEntries() could return null.
      if (managedCustomers != null) {
        for (ManagedCustomer managedCustomer : managedCustomers) {
          clientCustomerIdsSet.add(managedCustomer.getCustomerId());
        }
      }
    }
  }
}
