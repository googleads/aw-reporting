// Copyright 2015 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.awreporting.alerting.processor;

import com.google.api.ads.adwords.awreporting.authentication.Authenticator;
import com.google.api.ads.adwords.awreporting.util.ManagedCustomerDelegate;
import com.google.api.ads.adwords.jaxws.v201502.mcm.ApiException;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.api.client.util.Sets;
import com.google.gson.JsonObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Set;

/**
 * Reporting processor, responsible for downloading report files (one thread for each account),
 * parsing them into ReportData objects and applying alert rules (one thread for each file / account),
 * and running alert actions (one thread of each alert action).
 * 
 * @author zhuoc@google.com (Zhuo Chen)
 */
public abstract class ReportProcessor {

  private static final Logger LOGGER = Logger.getLogger(ReportProcessor.class);
  
  private static final int NUMBER_OF_REPORT_PROCESSORS = 20;

  protected int numberOfReportProcessors = NUMBER_OF_REPORT_PROCESSORS;
  
  protected Authenticator authenticator;

  abstract protected void cacheAccounts(Set<Long> accountIdsSet);
  
  abstract public void generateAlertsForMCC(String mccAccountId,
      Set<Long>accountIds,
      JsonObject alertsConfig) throws Exception;

  /**
   * Uses the API to retrieve the managed accounts, and extract their IDs.
   *
   * @return the account IDs for all the managed accounts.
   * @throws IOException
   * @throws ValidationException
   * @throws OAuthException
   * @throws ApiException
   * @throws Exception error reading the API.
   */
  public Set<Long> retrieveAccountIds(String mccAccountId) throws OAuthException,
      ValidationException, IOException, ApiException {

    Set<Long> accountIdsSet = Sets.newHashSet();
    try {

      LOGGER.info("Account IDs being recovered from the API. This may take a while...");
      accountIdsSet = new ManagedCustomerDelegate(
          authenticator.authenticate(null, mccAccountId, false).build()).getAccountIds();

    } catch (ApiException e) {
      if (e.getMessage().contains("AuthenticationError")) {

        // retries Auth once for expired Tokens
        LOGGER.info("AuthenticationError, Getting a new Token...");
        LOGGER.info("Account IDs being recovered from the API. This may take a while...");
        accountIdsSet = new ManagedCustomerDelegate(
            authenticator.authenticate(null, mccAccountId, true).build()).getAccountIds();

      } else {
        LOGGER.error("API error: " + e.getMessage());
        e.printStackTrace();
        throw e;
      }
    }

    this.cacheAccounts(accountIdsSet);

    return accountIdsSet;
  }

  /**
   * @param authenticator the helper class for Auth
   */
  @Autowired
  public void setAuthentication(Authenticator authenticator) {
    this.authenticator = authenticator;
  }
}

