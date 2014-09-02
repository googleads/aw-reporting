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

package com.google.api.ads.adwords.jaxws.extensions.processors;

import com.google.api.ads.adwords.jaxws.extensions.authentication.Authenticator;
import com.google.api.ads.adwords.jaxws.extensions.report.model.csv.CsvReportEntitiesMapping;
import com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister;
import com.google.api.ads.adwords.jaxws.extensions.util.CustomerDelegate;
import com.google.api.ads.adwords.jaxws.extensions.util.ManagedCustomerDelegate;
import com.google.api.ads.adwords.jaxws.v201406.mcm.ApiException;
import com.google.api.ads.adwords.jaxws.v201406.mcm.Customer;
import com.google.api.ads.adwords.jaxws.v201406.mcm.ManagedCustomer;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.jaxb.v201406.DateRange;
import com.google.api.ads.adwords.lib.jaxb.v201406.DownloadFormat;
import com.google.api.ads.adwords.lib.jaxb.v201406.ReportDefinition;
import com.google.api.ads.adwords.lib.jaxb.v201406.ReportDefinitionDateRangeType;
import com.google.api.ads.adwords.lib.jaxb.v201406.ReportDefinitionReportType;
import com.google.api.ads.adwords.lib.jaxb.v201406.Selector;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.api.client.util.Sets;
import com.google.common.collect.Lists;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Reporting processor, responsible for downloading and saving the files to
 * the file system. The persistence of the parsed beans is delegated to the
 * configured persister.
 * 
 * @author jtoledo@google.com (Julian Toledo)
 * @author gustavomoreira@google.com (Gustavo Moreira)
 * @author joeltoby@google.com (Joel Toby)
 */

public abstract class ReportProcessor {

  private static final Logger LOGGER = Logger.getLogger(ReportProcessor.class);

  private static final int REPORT_BUFFER_DB = 1000;
  private static final int NUMBER_OF_REPORT_PROCESSORS = 20;

  protected static final String REPORT_PREFIX = "AwReporting-";

  protected CsvReportEntitiesMapping csvReportEntitiesMapping;

  protected EntityPersister persister;

  protected Authenticator authenticator;

  protected int reportRowsSetSize = REPORT_BUFFER_DB;

  protected int numberOfReportProcessors = NUMBER_OF_REPORT_PROCESSORS;

  abstract protected void cacheAccounts(Set<Long> accountIdsSet);

  abstract public void generateReportsForMCC(
      String userId, String mccAccountId,
      ReportDefinitionDateRangeType dateRangeType, String dateStart,
      String dateEnd, Set<Long> accountIdsSet, Properties properties,
      ReportDefinitionReportType reportType, List<String> reportFieldsToInclude)
          throws Exception;

  /**
   * Uses the API to retrieve the managed accounts, and extract their IDs.
   * 
   * @return the account IDs for all the managed accounts.
   * @throws IOException 
   * @throws ValidationException 
   * @throws OAuthException 
   * @throws ApiException 
   * @throws Exception
   *             error reading the API.
   */
  public Set<Long> retrieveAccountIds(String userId, String mccAccountId)
      throws OAuthException, ValidationException, IOException, ApiException {

    Set<Long> accountIdsSet = Sets.newHashSet();
    try {

      LOGGER.info("Account IDs being recovered from the API. This may take a while...");
      accountIdsSet = new ManagedCustomerDelegate(
          authenticator.authenticate(userId, mccAccountId, false).build()).getAccountIds();

    } catch (ApiException e) {
      if (e.getMessage().contains("AuthenticationError")) {

        // retries Auth once for expired Tokens
        LOGGER.info("AuthenticationError, Getting a new Token...");
        LOGGER.info("Account IDs being recovered from the API. This may take a while...");
        accountIdsSet = new ManagedCustomerDelegate(
            authenticator.authenticate(userId, mccAccountId, true).build()).getAccountIds();

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
   * Uses the API to retrieve the managed accounts, and extract their IDs.
   * 
   * @return the account IDs for all the managed accounts.
   * @throws Exception
   *             error reading the API.
   */
  public List<ManagedCustomer> getAccounts(String userId, String mccAccountId) throws Exception {

    List<ManagedCustomer> accounts = Lists.newArrayList();
    try {
      accounts = new ManagedCustomerDelegate(
          authenticator.authenticate(userId, mccAccountId, false).build()).getAccounts();
    } catch (ApiException e) {
      if (e.getMessage().contains("AuthenticationError")) {
        // retries Auth once for expired Tokens
        LOGGER.info("AuthenticationError, Getting a new Token...");
        accounts = new ManagedCustomerDelegate(
            authenticator.authenticate(userId, mccAccountId, true).build()).getAccounts();
      } else {
        LOGGER.error("API error: " + e.getMessage());
        e.printStackTrace();
        throw e;
      }
    }
    return accounts;
  }

  public List<Customer> getAccountsInfo(String userId, String mccAccountId, Set<Long> accountIds) throws OAuthException, ValidationException, IOException {
    List<Customer> accounts = Lists.newArrayList();
    AdWordsSession adWordsSession = authenticator.authenticate(userId, mccAccountId, false).build();

    CustomerDelegate customerDelegate = new CustomerDelegate(adWordsSession);
    for (Long accountId : accountIds) {
      adWordsSession.setClientCustomerId(String.valueOf(accountId));
      try {
        accounts.add(customerDelegate.getCustomer());
      } catch (ApiException e) {
        if (e.getMessage().contains("AuthenticationError")) {
          // retries Auth once for expired Tokens
          LOGGER.info("AuthenticationError, Getting a new Token...");
          adWordsSession = authenticator.authenticate(userId, mccAccountId, false).build();
          customerDelegate = new CustomerDelegate(adWordsSession);
          try {
            accounts.add(customerDelegate.getCustomer());
          } catch (ApiException e2) {
            LOGGER.error("Skipping Account " + accountId + " error while getting it's information: " + e2.getMessage());          }
        } else {
          LOGGER.error("Skipping Account " + accountId + " error while getting it's information: " + e.getMessage());
        } 
      }
    }
    return accounts;
  }

  /**
   * Creates the complete report definition with all the dates and types
   * properly set.
   * 
   * @param reportDefinitionReportType
   *            the report type.
   * @param dateRangeType
   *            the date range type.
   * @param dateStart
   *            the initial date.
   * @param dateEnd
   *            the ending date.
   * @param properties
   *            the properties resource.
   * @return the {@code ReportDefinition} instance.
   */
  protected ReportDefinition getReportDefinition(
      ReportDefinitionReportType reportDefinitionReportType,
      ReportDefinitionDateRangeType dateRangeType, String dateStart,
      String dateEnd, Properties properties) {

    // Create the Selector with all the fields defined in the Mapping
    Selector selector = new Selector();

    List<String> reportFields = this.csvReportEntitiesMapping
        .retrievePropertiesToSelect(reportDefinitionReportType);

    // Add the inclusions from the properties file
    List<String> reportFieldsToInclude = this.getReportInclusions(
        reportDefinitionReportType, properties);
    for (String reportField : reportFields) {
      if (reportFieldsToInclude.contains(reportField)) {
        selector.getFields().add(reportField);
      }
    }
    this.adjustDateRange(reportDefinitionReportType, dateRangeType,
        dateStart, dateEnd, selector);

    return this.instantiateReportDefinition(reportDefinitionReportType,
        dateRangeType, selector);
  }
  
  protected ReportDefinition getReportDefinition(
      ReportDefinitionReportType reportDefinitionReportType,
      ReportDefinitionDateRangeType dateRangeType, String dateStart,
      String dateEnd, List<String> reportFieldsToInclude) {

    // Create the Selector with all the fields defined in the Mapping
    Selector selector = new Selector();

    List<String> reportFields = this.csvReportEntitiesMapping
        .retrievePropertiesToSelect(reportDefinitionReportType);

    for (String reportField : reportFields) {
      if (reportFieldsToInclude.contains(reportField)) {
        selector.getFields().add(reportField);
      }
    }
    this.adjustDateRange(reportDefinitionReportType, dateRangeType,
        dateStart, dateEnd, selector);

    return this.instantiateReportDefinition(reportDefinitionReportType,
        dateRangeType, selector);
  }

  /**
   * Adjusts the date range in case of a custom date type. The adjustment do
   * not apply for the {@code CAMPAIGN_NEGATIVE_KEYWORDS_PERFORMANCE_REPORT}.
   * 
   * @param reportDefinitionReportType
   *            the report type.
   * @param dateRangeType
   *            the date range type.
   * @param dateStart
   *            the start.
   * @param dateEnd
   *            the end.
   * @param selector
   *            the selector with the properties.
   */
  protected void adjustDateRange(
      ReportDefinitionReportType reportDefinitionReportType,
      ReportDefinitionDateRangeType dateRangeType, String dateStart,
      String dateEnd, Selector selector) {

    if (dateRangeType.equals(ReportDefinitionDateRangeType.CUSTOM_DATE)
        && !reportDefinitionReportType
        .equals(ReportDefinitionReportType.CAMPAIGN_NEGATIVE_KEYWORDS_PERFORMANCE_REPORT)) {
      DateRange dateRange = new DateRange();
      dateRange.setMin(dateStart);
      dateRange.setMax(dateEnd);
      selector.setDateRange(dateRange);
    }
  }

  /**
   * Instantiates the report definition, setting all the correct formats.
   * 
   * @param reportDefinitionReportType
   *            the report definition type.
   * @param dateRangeType
   *            the date range type.
   * @param selector
   *            the selector containing the report properties.
   * @return the {@code ReportDefinition}
   */
  protected ReportDefinition instantiateReportDefinition(
      ReportDefinitionReportType reportDefinitionReportType,
      ReportDefinitionDateRangeType dateRangeType, Selector selector) {

    // Create the Report Definition
    ReportDefinition reportDefinition = new ReportDefinition();
    reportDefinition.setReportName(REPORT_PREFIX
        + reportDefinitionReportType.value() + " "
        + System.currentTimeMillis());
    reportDefinition.setDateRangeType(dateRangeType);
    reportDefinition.setReportType(reportDefinitionReportType);
    reportDefinition.setDownloadFormat(DownloadFormat.GZIPPED_CSV);
    reportDefinition.setIncludeZeroImpressions(false);
    reportDefinition.setSelector(selector);
    return reportDefinition;
  }

  /**
   * Look for properties to include in the reports.
   * 
   * @param reportType
   *            the report type.
   * @param properties
   *            the resource properties.
   * @return the list of properties that should be included in the report.
   */
  protected List<String> getReportInclusions(
      ReportDefinitionReportType reportType, Properties properties) {

    String propertyInclusions = properties.getProperty(reportType.name());

    if (propertyInclusions != null && propertyInclusions.length() > 0) {
      String[] inclusions = propertyInclusions.split(",");
      List<String> inclusionsList = Lists
          .newArrayListWithCapacity(inclusions.length);
      for (int i = 0; i < inclusions.length; i++) {
        inclusionsList.add(inclusions[i].trim());
      }
      return inclusionsList;
    }
    return Lists.newArrayListWithCapacity(0);
  }

  /**
   * @param csvReportEntitiesMapping
   *            the csvReportEntitiesMapping to set
   */
  @Autowired
  public void setCsvReportEntitiesMapping(
      CsvReportEntitiesMapping csvReportEntitiesMapping) {
    this.csvReportEntitiesMapping = csvReportEntitiesMapping;
  }

  /**
   * @param persister the persister to set
   */
  @Autowired
  public void setPersister(EntityPersister persister) {
    this.persister = persister;
  }

  /**
   * @param authentication the helper class for Auth
   */
  @Autowired
  public void setAuthentication(Authenticator authenticator) {
    this.authenticator = authenticator;
  }
}
