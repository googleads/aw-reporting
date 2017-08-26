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

package com.google.api.ads.adwords.awreporting.processors;

import com.google.api.ads.adwords.awreporting.ReportProcessingException;
import com.google.api.ads.adwords.awreporting.authentication.Authenticator;
import com.google.api.ads.adwords.awreporting.model.csv.CsvReportEntitiesMapping;
import com.google.api.ads.adwords.awreporting.model.entities.DateRangeAndType;
import com.google.api.ads.adwords.awreporting.model.persistence.EntityPersister;
import com.google.api.ads.adwords.awreporting.util.AdWordsSessionUtil;
import com.google.api.ads.adwords.awreporting.util.ManagedCustomerDelegate;
import com.google.api.ads.adwords.jaxws.v201708.mcm.ApiException;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.client.AdWordsSession.ImmutableAdWordsSession;
import com.google.api.ads.adwords.lib.client.reporting.ReportingConfiguration;
import com.google.api.ads.adwords.lib.jaxb.v201708.DownloadFormat;
import com.google.api.ads.adwords.lib.jaxb.v201708.ReportDefinition;
import com.google.api.ads.adwords.lib.jaxb.v201708.ReportDefinitionDateRangeType;
import com.google.api.ads.adwords.lib.jaxb.v201708.ReportDefinitionReportType;
import com.google.api.ads.adwords.lib.jaxb.v201708.Selector;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.api.client.util.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Reporting processor, responsible for downloading and saving the files to the file system. The
 * persistence of the parsed beans is delegated to the configured persister.
 */
public abstract class ReportProcessor {
  private static final Logger logger = LoggerFactory.getLogger(ReportProcessor.class);

  private static final char REPORT_KEY_SEPARATOR = '$';
  private static final String REPORT_PREFIX = "AwReporting-";
  
  private static final String PROPERTY_KEY_INCLUDEZEROIMPRESSIONS =
      "aw.report.definition.includeZeroImpressions";
  private static final boolean DEFAULT_VALUE_INCLUDEZEROIMPRESSIONS = false;
  
  private static final String PROPERTY_KEY_USERAWENUMVALUES =
      "aw.report.definition.useRawEnumValues";
  private static final boolean DEFAULT_VALUE_USERAWENUMVALUES = false;
  
  private static final String PROPERTY_KEY_EXCLUDEHIDDENACCOUNTS =
      "aw.report.downloader.excludeHiddenAccounts";
  private static final boolean DEFAULT_VALUE_EXCLUDEHIDDENACCOUNTS = false;

  protected CsvReportEntitiesMapping csvReportEntitiesMapping;

  protected EntityPersister persister;

  protected final int reportRowsSetSize;
  protected final int numberOfReportProcessors;

  private static final DateTimeFormatter TIMESTAMPFORMAT =
      DateTimeFormat.forPattern("yyyy-MM-dd-HH_mm");

  protected final AdWordsSession.Builder sessionBuilder;

  /**
   * Constructor.
   *
   * @param reportRowsSetSize the size of the set parsed before persisting to the database.
   * @param numberOfReportProcessors the number of threads used to process reports.
   * @param authenticator to create the AdWords session.
   * @throws ReportProcessingException
   */
  @Autowired
  public ReportProcessor(
      Integer reportRowsSetSize, Integer numberOfReportProcessors, Authenticator authenticator)
      throws ReportProcessingException {
    Preconditions.checkNotNull(reportRowsSetSize, "ReportRowSetSize cannot be null!");
    Preconditions.checkNotNull(
        numberOfReportProcessors, "NumberOfReportProcessors cannot be null!");
    Preconditions.checkNotNull(authenticator, "Authenticator cannot be null!");
    Preconditions.checkArgument(reportRowsSetSize > 0, "ReportRowSetSize must be > 0");
    Preconditions.checkArgument(
        numberOfReportProcessors > 0, "NumberOfReportProcessors must be > 0");
    
    this.reportRowsSetSize = reportRowsSetSize.intValue();
    this.numberOfReportProcessors = numberOfReportProcessors.intValue();

    try {
      sessionBuilder = authenticator.authenticate();
    } catch (OAuthException e) {
      throw new ReportProcessingException("Failed to authenticate AdWordsSession.", e);
    }
  }

  /**
   * Generate all the mapped reports of (optionally) given account IDs under the specified
   * manager account.
   *
   * @param topCustomerId the top customer account id.
   * @param dateRangeAndType the date range and type.
   * @param accountIdsSet the account IDs, if missing it means all accounts under the topCustomerId
   * @param properties the properties file
   * @throws ReportProcessingException error invoking the API calls.
   */
  public void generateReportsForManagerAccount(
      String topCustomerId,
      DateRangeAndType dateRangeAndType,
      Set<Long> accountIdsSet,
      Properties properties)
      throws ReportProcessingException {
    logger.info("*** Retrieving account IDs ***");

    if (accountIdsSet == null || accountIdsSet.size() == 0) {
      boolean excludeHiddenAccounts = getExcludeHiddenAccounts(properties);
      accountIdsSet = retrieveAccountIds(topCustomerId, excludeHiddenAccounts);
    } else {
      logger.info("Accounts loaded from file.");
    }

    ReportingConfiguration reportingConfig = getReportingConfiguration(properties);
    sessionBuilder.withReportingConfiguration(reportingConfig);

    logger.info("*** Generating Reports for " + accountIdsSet.size() + " accounts ***");
    Stopwatch stopwatch = Stopwatch.createStarted();
    Set<ReportDefinitionReportType> reports = csvReportEntitiesMapping.getDefinedReports();

    // reports
    Set<Object> propertiesKeys = properties.keySet();
    for (Object key : propertiesKeys) {

      String reportDefinitionKey = key.toString();
      ReportDefinitionReportType reportType = extractReportTypeFromKey(reportDefinitionKey);
      if (reportType != null && reports.contains(reportType)) {
        try {
          downloadAndProcess(
              topCustomerId,
              reportType,
              dateRangeAndType,
              accountIdsSet,
              reportDefinitionKey,
              properties);
        } catch (ReportProcessingException e) {
          logger.error("Unable to download and process " + reportType + " for " + topCustomerId, e);
        }
      }
    }

    stopwatch.stop();
    logger.info(
        "*** Finished processing all reports in "
            + stopwatch.elapsed(TimeUnit.SECONDS)
            + " seconds ***\n");
  }

  /**
   * Downloads all the files from the API and process all the rows, saving the data to the
   * configured database.
   *
   * @param topCustomerId the top customer account id.
   * @param reportType the report type.
   * @param dateRangeAndType the date range and type.
   * @param accountIdList the account IDs.
   * @param properties the properties resource.
   * @throws ReportProcessingException
   */
  protected abstract void downloadAndProcess(
      String topCustomerId,
      ReportDefinitionReportType reportType,
      DateRangeAndType dateRangeAndType,
      Set<Long> accountIdList,
      String reportDefinitionKey,
      Properties properties)
      throws ReportProcessingException;

  /**
   * Uses the API to retrieve the managed accounts and extract their IDs.
   *
   * @param topCustomerId the top manager account ID.
   * @param excludeHiddenAccounts whether to exclude hidden accounts.
   * @throws ReportProcessingException error invoking the API calls.
   */
  public Set<Long> retrieveAccountIds(String topCustomerId, boolean excludeHiddenAccounts)
      throws ReportProcessingException {
    logger.info("Client customer IDs being retrieved from the API. This may take a while...");
    logger.info("Property ExcludeHiddenAccounts=" + String.valueOf(excludeHiddenAccounts));
    
    try {
      ImmutableAdWordsSession session =
          AdWordsSessionUtil.buildImmutableSessionForCid(sessionBuilder, topCustomerId);
      Set<Long> accountIdsSet =
          new ManagedCustomerDelegate(session, excludeHiddenAccounts).getClientCustomerIds();

      cacheAccounts(accountIdsSet);
      return accountIdsSet;
    } catch (ValidationException e) {
      throw new ReportProcessingException(
          "Failed to create valid session for account " + topCustomerId, e);
    } catch (ApiException e) {
      throw new ReportProcessingException(
          "Failed to retrieve account IDs under " + topCustomerId, e);
    }
  }

  /**
   * Caches the accounts into a temporary file.
   *
   * @param accountIdsSet the set with all the customer accounts ids.
   */
  private void cacheAccounts(Set<Long> accountIdsSet) {
    String nowFormat = TIMESTAMPFORMAT.print(DateTime.now());
    
    File tempFile = null;
    try {
      tempFile = File.createTempFile(nowFormat + "-accounts-ids", ".txt");
      logger.info("Cache file created for accounts: " + tempFile.getAbsolutePath());
    } catch (IOException e) {
      logger.error(
          "Could not create temporary file with the accounts. Accounts won't be cached.", e);
    }

    try (OutputStreamWriter writer =
        new OutputStreamWriter(new FileOutputStream(tempFile), StandardCharsets.UTF_8)) {
      for (Long accountId : accountIdsSet) {
        writer.write(Long.toString(accountId) + "\n");
      }
      logger.info("All account IDs added to cache file.");
    } catch (IOException e) {
      logger.error("Failed ot cache accounts into temporary file.", e);
    }
  }

  /**
   * Creates the complete report definition with all the dates and types properly set.
   *
   * @param reportDefinitionReportType the report type.
   * @param dateRangeAndType the date range and type.
   * @param reportDefinitionKey the key defining the report in the properties file.
   * @param properties the properties resource.
   * @return the {@code ReportDefinition} instance.
   */
  protected ReportDefinition getReportDefinition(
      ReportDefinitionReportType reportDefinitionReportType,
      DateRangeAndType dateRangeAndType,
      String reportDefinitionKey,
      Properties properties) {

    // Create the Selector with all the fields defined in the Mapping
    Selector selector = new Selector();

    List<String> reportFields =
        csvReportEntitiesMapping.retrievePropertiesToSelect(reportDefinitionReportType);

    // Add the inclusions from the properties file
    List<String> reportFieldsToInclude = getReportInclusions(reportDefinitionKey, properties);
    for (String reportField : reportFields) {
      if (reportFieldsToInclude.contains(reportField)) {
        selector.getFields().add(reportField);
      }
    }
    adjustDateRange(reportDefinitionReportType, dateRangeAndType, selector);

    return instantiateReportDefinition(
        reportDefinitionReportType, dateRangeAndType.getType(), selector);
  }

  /**
   * Extracts the report type name from the given key name.
   *
   * @param keyName the name of the key in the properties file.
   * @return the report type name, without the "$" symbol, so it can be used to download the report
   *         type.
   */
  private ReportDefinitionReportType extractReportTypeFromKey(String keyName) {
    int indexOfDollar = keyName.indexOf(REPORT_KEY_SEPARATOR);
    if (indexOfDollar > 0) {
      keyName = keyName.substring(0, indexOfDollar);
    }
    try {
      return ReportDefinitionReportType.valueOf(keyName);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  /**
   * Adjusts the date range in case of a custom date type. The adjustment do not apply for certain
   * reports, like {@code CAMPAIGN_NEGATIVE_KEYWORDS_PERFORMANCE_REPORT}.
   *
   * @param reportDefinitionReportType the report type.
   * @param dateRangeAndType the date range and type.
   * @param selector the selector with the properties.
   */
  protected void adjustDateRange(
      ReportDefinitionReportType reportDefinitionReportType,
      DateRangeAndType dateRangeAndType,
      Selector selector) {
    if (dateRangeAndType.getType().equals(ReportDefinitionDateRangeType.CUSTOM_DATE)
        && csvReportEntitiesMapping.supportsDateRange(reportDefinitionReportType)) {
      selector.setDateRange(dateRangeAndType.getDateRange());
    }
  }

  /**
   * Instantiates the report definition, setting all the correct formats.
   *
   * @param reportDefinitionReportType the report definition type.
   * @param dateRangeType the date range type.
   * @param selector the selector containing the report properties.
   * @return the {@code ReportDefinition}
   */
  protected ReportDefinition instantiateReportDefinition(
      ReportDefinitionReportType reportDefinitionReportType,
      ReportDefinitionDateRangeType dateRangeType,
      Selector selector) {

    // Create the Report Definition
    ReportDefinition reportDefinition = new ReportDefinition();
    reportDefinition.setReportName(
        REPORT_PREFIX + reportDefinitionReportType.value() + " " + System.currentTimeMillis());
    reportDefinition.setDateRangeType(dateRangeType);
    reportDefinition.setReportType(reportDefinitionReportType);
    reportDefinition.setDownloadFormat(DownloadFormat.GZIPPED_CSV);
    reportDefinition.setSelector(selector);
    return reportDefinition;
  }

  /**
   * Look for properties to include in the reports.
   *
   * @param reportDefinitionKey the report definition key specified in the properties file.
   * @param properties the resource properties.
   * @return the list of properties that should be included in the report.
   */
  private List<String> getReportInclusions(String reportDefinitionKey, Properties properties) {
    String propertyInclusions = properties.getProperty(reportDefinitionKey);
    return Lists.newArrayList(
        Splitter.on(',').omitEmptyStrings().trimResults().split(propertyInclusions));
  }
  
  private boolean getBooleanPropertyValue(Properties properties, String key, boolean defaultValue) {
    boolean result = defaultValue;
    
    String value = properties.getProperty(key);
    if (!Strings.isNullOrEmpty(value)) {
      result = value.equalsIgnoreCase("true");
    }
    
    logger.info("Property " + key + "=" + String.valueOf(result));
    return result;
  }
  
  private boolean getIncludeZeroImpressions(Properties properties) {
    return getBooleanPropertyValue(
        properties, PROPERTY_KEY_INCLUDEZEROIMPRESSIONS, DEFAULT_VALUE_INCLUDEZEROIMPRESSIONS);
  }
  
  private boolean getUseRawEnumValues(Properties properties) {
    return getBooleanPropertyValue(
        properties, PROPERTY_KEY_USERAWENUMVALUES, DEFAULT_VALUE_USERAWENUMVALUES);
  }
  
  private boolean getExcludeHiddenAccounts(Properties properties) {
    return getBooleanPropertyValue(
        properties, PROPERTY_KEY_EXCLUDEHIDDENACCOUNTS, DEFAULT_VALUE_EXCLUDEHIDDENACCOUNTS);
  }

  private ReportingConfiguration getReportingConfiguration(Properties properties) {
    ReportingConfiguration reportingConfig =
        new ReportingConfiguration.Builder()
            .skipReportHeader(true)
            .skipColumnHeader(false)
            .skipReportSummary(true)
            .includeZeroImpressions(getIncludeZeroImpressions(properties))
            .useRawEnumValues(getUseRawEnumValues(properties))
            .build();
    return reportingConfig;
  }

  @Autowired
  public void setCsvReportEntitiesMapping(CsvReportEntitiesMapping csvReportEntitiesMapping) {
    this.csvReportEntitiesMapping = csvReportEntitiesMapping;
  }

  @Autowired
  public void setPersister(EntityPersister persister) {
    this.persister = persister;
  }
}
