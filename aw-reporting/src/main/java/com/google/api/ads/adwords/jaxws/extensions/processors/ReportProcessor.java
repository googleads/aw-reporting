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

import com.google.api.ads.adwords.jaxws.extensions.ManagedCustomerDelegate;
import com.google.api.ads.adwords.jaxws.extensions.downloader.MultipleClientReportDownloader;
import com.google.api.ads.adwords.jaxws.extensions.report.model.csv.AnnotationBasedMappingStrategy;
import com.google.api.ads.adwords.jaxws.extensions.report.model.csv.CsvReportEntitiesMapping;
import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.AuthMcc;
import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.Report;
import com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.AuthTokenPersister;
import com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister;
import com.google.api.ads.adwords.jaxws.extensions.report.model.util.DateUtil;
import com.google.api.ads.adwords.jaxws.extensions.report.model.util.ModifiedCsvToBean;
import com.google.api.ads.adwords.jaxws.extensions.util.GetRefreshToken;
import com.google.api.ads.adwords.jaxws.extensions.util.HTMLExporter;
import com.google.api.ads.adwords.jaxws.v201309.mcm.ApiException;
import com.google.api.ads.adwords.jaxws.v201309.mcm.ManagedCustomer;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.jaxb.v201309.DateRange;
import com.google.api.ads.adwords.lib.jaxb.v201309.DownloadFormat;
import com.google.api.ads.adwords.lib.jaxb.v201309.ReportDefinition;
import com.google.api.ads.adwords.lib.jaxb.v201309.ReportDefinitionDateRangeType;
import com.google.api.ads.adwords.lib.jaxb.v201309.ReportDefinitionReportType;
import com.google.api.ads.adwords.lib.jaxb.v201309.Selector;
import com.google.api.ads.common.lib.auth.OfflineCredentials;
import com.google.api.ads.common.lib.auth.OfflineCredentials.Api;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.util.Maps;
import com.google.api.client.util.Sets;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;

import au.com.bytecode.opencsv.bean.MappingStrategy;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Main reporting processor responsible for downloading and saving the files to
 * the file system. The persistence of the parsed beans is delegated to the
 * configured persister.
 * 
 * @author jtoledo@google.com (Julian Toledo)
 * @author gustavomoreira@google.com (Gustavo Moreira)
 */
@Component
public class ReportProcessor {

  public static final int REPORT_BUFFER_DB = 1000;

  public static final int NUMBER_OF_REPORT_PROCESSORS = 4;

  private static final Logger LOGGER = Logger
      .getLogger(ReportProcessor.class);

  private static final DateFormat TIMESTAMPFORMAT = new SimpleDateFormat(
      "yyyy-MM-dd-HH_mm");

  private static final String USER_AGENT = "AwReporting";

  private static final String REPORT_PREFIX = "AwReporting-";

  private CsvReportEntitiesMapping csvReportEntitiesMapping;

  private MultipleClientReportDownloader multipleClientReportDownloader;

  private EntityPersister persister;

  private AuthTokenPersister authTokenPersister;

  // AdWords Authentication Properties
  private String clientId = null;
  private String clientSecret = null;
  private String developerToken = null;
  private String mccAccountId = null;
  private String companyName = null;

  private int reportRowsSetSize = REPORT_BUFFER_DB;
  private int numberOfReportProcessors = NUMBER_OF_REPORT_PROCESSORS;

  /**
   * Constructor.
   * 
   * @param mccAccountId
   *            the MCC account ID
   * @param developerToken
   *            the developer token
   * @param companyName
   *            the company name (optional)
   * @param clientId
   *            the OAuth2 authentication clientId
   * @param clientSecret
   *            the OAuth2 authentication clientSecret
   * @param reportRowsSetSize
   *            the size of the set parsed before send to the DB
   */
  @Autowired
  public ReportProcessor(
      @Value("${mccAccountId}") String mccAccountId,
      @Value("${developerToken}") String developerToken,
      @Value(value = "${companyName:}") String companyName,
      @Value(value = "${clientId}") String clientId,
      @Value(value = "${clientSecret}") String clientSecret,
      @Value(value = "${aw.report.processor.rows.size:}") Integer reportRowsSetSize,
      @Value(value = "${aw.report.processor.threads:}") Integer numberOfReportProcessors) {

    this.mccAccountId = mccAccountId;
    this.developerToken = developerToken;
    this.companyName = companyName;
    this.clientId = clientId;
    this.clientSecret = clientSecret;

    if (reportRowsSetSize != null && reportRowsSetSize > 0) {
      this.reportRowsSetSize = reportRowsSetSize;
    }
    if (numberOfReportProcessors != null && numberOfReportProcessors > 0) {
      this.numberOfReportProcessors = numberOfReportProcessors;
    }
  }

  private <R extends Report> void processFiles(Class<R> reportBeanClass,
      Collection<File> localFiles,
      ReportDefinitionDateRangeType dateRangeType, String dateStart,
      String dateEnd) {

    final CountDownLatch latch = new CountDownLatch(localFiles.size());
    ExecutorService executorService = Executors
        .newFixedThreadPool(numberOfReportProcessors);

    // Processing Report Local Files
    LOGGER.info(" Procesing reports...");

    Stopwatch stopwatch = new Stopwatch();
    stopwatch.start();

    for (File file : localFiles) {
      LOGGER.trace(".");
      try {

        ModifiedCsvToBean<R> csvToBean = new ModifiedCsvToBean<R>();
        MappingStrategy<R> mappingStrategy = new AnnotationBasedMappingStrategy<R>(
            reportBeanClass);

        LOGGER.debug("Parsing file: " + file.getAbsolutePath());
        RunnableProcessor<R> runnableProcesor = new RunnableProcessor<R>(
            file, csvToBean, mappingStrategy, dateRangeType,
            dateStart, dateEnd, mccAccountId, persister,
            reportRowsSetSize);
        runnableProcesor.setLatch(latch);
        executorService.execute(runnableProcesor);

      } catch (Exception e) {
        LOGGER.error("Ignoring file (Error when processing): "
            + file.getAbsolutePath());
        e.printStackTrace();
      }
    }

    try {
      latch.await();
    } catch (InterruptedException e) {
      LOGGER.error(e.getMessage());
      e.printStackTrace();
    }
    executorService.shutdown();
    stopwatch.stop();
    LOGGER.info("*** Finished processing all reports in "
        + (stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000)
        + " seconds ***\n");
  }

  /**
   * The implementation must persist the token to be retrieved later.
   * 
   * @param mccAccountId
   *            the MCC account ID.
   * @param authToken
   *            the authentication token.
   */
  private void saveAuthTokenToStorage(String mccAccountId, String authToken) {

    LOGGER.debug("Persisting refresh token...");
    AuthMcc authMcc = new AuthMcc(mccAccountId, authToken);
    this.authTokenPersister.persistAuthToken(authMcc);
    LOGGER.debug("... success.");
  }

  /**
   * The implementation should retrieve the authentication token previously
   * persisted.
   * 
   * @param mccAccountId
   *            the MCC account ID.
   * @return the authentication token.
   */
  private String getAuthTokenFromStorage(String mccAccountId) {

    AuthMcc authToken = this.authTokenPersister.getAuthToken(mccAccountId);
    if (authToken != null) {
      return authToken.getAuthToken();
    }
    return null;
  }

  /**
   * Authenticates the user against the API.
   * 
   * @param force
   *            true if the authentication token must be renewed.
   * @return the session builder after the authentication.
   * @throws IOException
   *             error connecting to authentication server
   * @throws OAuthException
   *             error on the OAuth process
   */
  protected AdWordsSession.Builder authenticate(boolean force)
      throws OAuthException, IOException {

    String authToken = this.retrieveAuthToken(force);
    String localUserAgent = this.buildLocalUserAgent();

    Credential oAuth2Credential = null;
    try {
      oAuth2Credential = this.buildOAuth2Credentials(authToken);

    } catch (OAuthException e) {
      if (e.getMessage().contains("Credential could not be refreshed")) {
        System.out
        .println("**ERROR** Credential could not be refreshed,"
            + " we need a new OAuth2 Token");

        authToken = GetRefreshToken.get(this.clientId,
            this.clientSecret);
        oAuth2Credential = this.buildOAuth2Credentials(authToken);

        System.out.println("Saving Refresh Token to DB...\n");
        this.saveAuthTokenToStorage(this.mccAccountId, authToken);

      } else {
        e.printStackTrace();
        throw e;
      }
    }

    return new AdWordsSession.Builder()
    .withOAuth2Credential(oAuth2Credential)
    .withUserAgent(localUserAgent)
    .withClientCustomerId(this.mccAccountId)
    .withDeveloperToken(this.developerToken);
  }

  /**
   * Builds the OAuth 2.0 credential for the user
   * 
   * @param authToken
   *            the last authentication token
   * @return the new {@link Credential}
   * @throws OAuthException
   *             error creating the credentials
   */
  private Credential buildOAuth2Credentials(String authToken)
      throws OAuthException {

    try {
      return new OfflineCredentials.Builder().forApi(Api.ADWORDS)
          .withRefreshToken(authToken)
          .withClientSecrets(this.clientId, this.clientSecret)
          .build().generateCredential();
    } catch (ValidationException e) {

      e.printStackTrace();
      throw new IllegalStateException("Builder not set properly. "
          + "This might mean a bug with the authentication, "
          + "or the credential values are incorrect.", e);
    }
  }

  /**
   * Retrieves the authentication token by refreshing it if necessary, and
   * persisting the updated token into the DB.
   * 
   * @param force
   *            if the actual token should be refreshed
   * @return the valid token for the moment
   * @throws IOException
   *             error connecting to authentication server
   * @throws OAuthException
   *             error on the OAuth process
   */
  private String retrieveAuthToken(boolean force) throws IOException,
  OAuthException {

    LOGGER.debug("Retrieving auth token from DB.");
    String authToken = this.getAuthTokenFromStorage(this.mccAccountId);

    // Generate a new Auth token if necessary
    if ((authToken == null || force)) {
      try {
        if (force) {
          LOGGER.debug("Token refresh FORCED. Getting a new one.");
        } else {
          LOGGER.debug("Token not found. Getting one.");
        }
        authToken = GetRefreshToken.get(this.clientId,
            this.clientSecret);

      } catch (IOException e) {
        if (e.getMessage().contains("Connection reset")) {
          LOGGER.info("Connection reset when getting authToken, retrying...");
          this.authenticate(true);
        } else {
          LOGGER.error("Error authenticating: " + e.getMessage());
          e.printStackTrace();
          throw e;
        }
      }
      LOGGER.info("Saving Refresh Token to DB...");
      this.saveAuthTokenToStorage(this.mccAccountId, authToken);
    }
    return authToken;
  }

  /**
   * @return the local user agent built from the global user agent and the
   *         company name
   */
  private String buildLocalUserAgent() {

    String localUserAgent = USER_AGENT;
    if (this.companyName != null && this.companyName.length() > 0) {
      localUserAgent += "-" + this.companyName;
    }
    return localUserAgent;
  }

  /**
   * Uses the API to retrieve the managed accounts, and extract their IDs.
   * 
   * @return the account IDs for all the managed accounts.
   * @throws Exception
   *             error reading the API.
   */
  public Set<Long> retrieveAccountIds() throws Exception {

    Set<Long> accountIdsSet = Sets.newHashSet();
    try {

      LOGGER.info("Account IDs being recovered from the API. This may take a while...");
      accountIdsSet = new ManagedCustomerDelegate(this
          .authenticate(false).build()).getAccountIds();

    } catch (ApiException e) {
      if (e.getMessage().contains("AuthenticationError")) {

        // retries Auth once for expired Tokens
        LOGGER.info("AuthenticationError, Getting a new Token...");
        LOGGER.info("Account IDs being recovered from the API. This may take a while...");
        accountIdsSet = new ManagedCustomerDelegate(this.authenticate(
            true).build()).getAccountIds();

      } else {
        LOGGER.error("API error: " + e.getMessage());
        e.printStackTrace();
        throw e;
      }
    }

    this.cacheAccountsToFile(accountIdsSet);

    return accountIdsSet;
  }

  /**
   * Caches the accounts into a temporary file.
   * 
   * @param accountIdsSet
   *            the set with all the accounts
   */
  private void cacheAccountsToFile(Set<Long> accountIdsSet) {

    DateTime now = new DateTime();
    String nowFormat = TIMESTAMPFORMAT.format(now.toDate());

    try {
      File tempFile = File.createTempFile(nowFormat + "-accounts-ids",
          ".txt");
      LOGGER.info("Cache file created for accounts: "
          + tempFile.getAbsolutePath());

      FileWriter writer = new FileWriter(tempFile);
      for (Long accountId : accountIdsSet) {
        writer.write(Long.toString(accountId) + "\n");
      }
      writer.close();
      LOGGER.info("All account IDs added to cache file.");

    } catch (IOException e) {
      LOGGER.error("Could not create temporary file with the accounts. Accounts won't be cached.");
      e.printStackTrace();
    }
  }

  /**
   * Uses the API to retrieve the managed accounts, and extract their IDs.
   * 
   * @return the account IDs for all the managed accounts.
   * @throws Exception
   *             error reading the API.
   */
  public List<ManagedCustomer> getAccounts() throws Exception {

    List<ManagedCustomer> accounts = Lists.newArrayList();
    try {
      accounts = new ManagedCustomerDelegate(this.authenticate(false)
          .build()).getAccounts();
    } catch (ApiException e) {
      if (e.getMessage().contains("AuthenticationError")) {
        // retries Auth once for expired Tokens
        LOGGER.info("AuthenticationError, Getting a new Token...");
        accounts = new ManagedCustomerDelegate(this.authenticate(true)
            .build()).getAccounts();
      } else {
        LOGGER.error("API error: " + e.getMessage());
        e.printStackTrace();
        throw e;
      }
    }
    return accounts;
  }

  /**
   * Generate all the mapped reports to the given account IDs.
   * 
   * @param dateRangeType
   *            the date range type.
   * @param dateStart
   *            the starting date.
   * @param dateEnd
   *            the ending date.
   * @param accountIdsSet
   *            the account IDs.
   * @param properties
   *            the properties file
   * @throws Exception
   *             error reaching the API.
   */
  public void generateReportsForMCC(
      ReportDefinitionDateRangeType dateRangeType, String dateStart,
      String dateEnd, Set<Long> accountIdsSet, Properties properties)
          throws Exception {

    LOGGER.info("*** Retrieving account IDs ***");

    if (accountIdsSet == null || accountIdsSet.size() == 0) {
      accountIdsSet = this.retrieveAccountIds();
    } else {
      LOGGER.info("Accounts loaded from file.");
    }

    AdWordsSession.Builder builder = this.authenticate(false);

    LOGGER.info("*** Generating Reports for " + accountIdsSet.size()
        + " accounts ***");

    Stopwatch stopwatch = new Stopwatch();
    stopwatch.start();

    Set<ReportDefinitionReportType> reports = this.csvReportEntitiesMapping
        .getDefinedReports();

    // reports
    for (ReportDefinitionReportType reportType : reports) {

      if (properties.containsKey(reportType.name())) {
        this.downloadAndProcess(builder, reportType, dateRangeType,
            dateStart, dateEnd, accountIdsSet, properties);
      }
    }

    this.multipleClientReportDownloader.finalizeExecutorService();

    stopwatch.stop();
    LOGGER.info("*** Finished processing all reports in "
        + (stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000)
        + " seconds ***\n");
  }

  /**
   * Downloads all the files from the API and process all the rows, saving the
   * data to the configured data base.
   * 
   * @param builder
   *            the session builder.
   * @param reportType
   *            the report type.
   * @param dateRangeType
   *            the date range type.
   * @param dateStart
   *            the start date.
   * @param dateEnd
   *            the ending date.
   * @param acountIdList
   *            the account IDs.
   * @param properties
   *            the properties resource.
   */
  private <R extends Report> void downloadAndProcess(
      AdWordsSession.Builder builder,
      ReportDefinitionReportType reportType,
      ReportDefinitionDateRangeType dateRangeType, String dateStart,
      String dateEnd, Set<Long> acountIdList, Properties properties) {

    // Download Reports to local files and Generate Report objects
    LOGGER.info("\n\n ** Generating: " + reportType.name() + " **");
    LOGGER.info(" Downloading reports...");
    Collection<File> localFiles = Lists.newArrayList();
    try {

      ReportDefinition reportDefinition = getReportDefinition(reportType,
          dateRangeType, dateStart, dateEnd, properties);

      localFiles = this.multipleClientReportDownloader.downloadReports(
          builder, reportDefinition, acountIdList);

    } catch (InterruptedException e) {
      LOGGER.error(e.getMessage());
      e.printStackTrace();
      return;
    }

    this.processLocalFiles(reportType, localFiles, dateStart, dateEnd,
        dateRangeType);

    this.deleteTemporaryFiles(localFiles, reportType);
  }

  /**
   * Process the local files delegating the call to the concrete
   * implementation.
   * 
   * @param reportType
   *            the report type.
   * @param localFiles
   *            the local files.
   * @param dateStart
   *            the start date.
   * @param dateEnd
   *            the end date.
   * @param dateRangeType
   *            the date range type.
   */
  private <R extends Report> void processLocalFiles(
      ReportDefinitionReportType reportType, Collection<File> localFiles,
      String dateStart, String dateEnd,
      ReportDefinitionDateRangeType dateRangeType) {

    Stopwatch stopwatch = new Stopwatch();
    stopwatch.start();

    @SuppressWarnings("unchecked")
    Class<R> reportBeanClass = (Class<R>) this.csvReportEntitiesMapping
    .getReportBeanClass(reportType);
    this.processFiles(reportBeanClass, localFiles, dateRangeType,
        dateStart, dateEnd);

    stopwatch.stop();
    LOGGER.info("\n* DB Process finished in "
        + (stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000)
        + " seconds ***");
  }

  /**
   * Deletes the local files used as temporary containers.
   * 
   * @param localFiles
   *            the list of local files.
   * @param reportType
   *            the report type.
   */
  private void deleteTemporaryFiles(Collection<File> localFiles,
      ReportDefinitionReportType reportType) {

    // Delete temporary report files
    LOGGER.info("\n Deleting temporary report files after Parsing...");
    for (File file : localFiles) {
      File gUnzipFile = new File(file.getAbsolutePath() + ".gunzip");
      gUnzipFile.delete();
      file.delete();
      LOGGER.trace(".");
    }
    LOGGER.info("\n ** Finished: " + reportType.name() + " **");
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
  private ReportDefinition getReportDefinition(
      ReportDefinitionReportType reportDefinitionReportType,
      ReportDefinitionDateRangeType dateRangeType, String dateStart,
      String dateEnd, Properties properties) {

    // Create the Selector with all the fields defined in the Mapping
    Selector selector = new Selector();

    List<String> reportFields = this.csvReportEntitiesMapping
        .retrievePropertiesToSelect(reportDefinitionReportType);

    // Add the inclusions from the properties file
    List<String> reportInclusions = this.getReportInclusions(
        reportDefinitionReportType, properties);
    for (String reportField : reportFields) {
      if (reportInclusions.contains(reportField)) {
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
  private void adjustDateRange(
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
  private ReportDefinition instantiateReportDefinition(
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
  private List<String> getReportInclusions(
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
   * Generates the PDF files from the report data
   *
   * @param dateStart the start date for the reports
   * @param dateEnd the end date for the reports
   * @param properties the properties file containing all the configuration
   * @throws Exception error creating PDF
   */
  public void generatePdf(String dateStart, String dateEnd, Properties properties,
      File htmlTemplateFile, File outputDirectory, boolean sumAdExtensions) throws Exception {

    LOGGER.info("Starting PDF Generation");
    Map<String, Object> reportMap = Maps.newHashMap();

    for (Long accountId : retrieveAccountIds()) {
      LOGGER.debug("Retrieving monthly reports for account: " + accountId);

      Set<ReportDefinitionReportType> reports = this.csvReportEntitiesMapping.getDefinedReports();
      for (ReportDefinitionReportType reportType : reports) {
        if (properties.containsKey(reportType.name())) {
          // Adding each report type rows from DB to the accounts montlyeports list.

          List<Report> monthlyReports = Lists.newArrayList(persister.listMonthReports(
              csvReportEntitiesMapping.getReportBeanClass(reportType), accountId,
              DateUtil.parseDateTime(dateStart), DateUtil.parseDateTime(dateEnd)));
          
          if (sumAdExtensions && reportType.name() == "PLACEHOLDER_FEED_ITEM_REPORT") {
            Map<String, Integer> adExtensions = new HashMap<String, Integer>();
            for (Report report : monthlyReports) {
              System.out.println(report);
            }
          }

          reportMap.put(reportType.name(), monthlyReports);
        }
      }


      if (reportMap != null && reportMap.size() > 0) {

        File htmlFile = new File(outputDirectory,
            "Report_" + accountId + "_" + dateStart + "_" + dateEnd + ".html");
        File pdfFile = new File(outputDirectory,
            "Report_" + accountId + "_" + dateStart +  "_" + dateEnd + ".pdf");

        LOGGER.debug("Exporting monthly reports to HTML for account: " + accountId);
        HTMLExporter.exportHTML(reportMap, htmlTemplateFile, htmlFile);

        LOGGER.debug("Converting HTML to PDF for account: " + accountId);
        HTMLExporter.convertHTMLtoPDF(htmlFile, pdfFile);
      }
    }
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
   * @param multipleClientReportDownloader
   *            the multipleClientReportDownloader to set
   */
  @Autowired
  public void setMultipleClientReportDownloader(
      MultipleClientReportDownloader multipleClientReportDownloader) {
    this.multipleClientReportDownloader = multipleClientReportDownloader;
  }

  /**
   * @param persister
   *            the persister to set
   */
  @Autowired
  public void setPersister(EntityPersister persister) {
    this.persister = persister;
  }

  /**
   * @param authTokenPersister
   *            the authTokenPersister to set
   */
  @Autowired
  public void setAuthTokenPersister(AuthTokenPersister authTokenPersister) {
    this.authTokenPersister = authTokenPersister;
  }
}
