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

package com.google.api.ads.adwords.awreporting.server.rest;

import com.google.api.ads.adwords.awreporting.model.persistence.EntityPersister;
import com.google.api.ads.adwords.awreporting.server.authentication.WebAuthenticator;
import com.google.api.ads.adwords.awreporting.server.rest.kratu.GenerateKratusRest;
import com.google.api.ads.adwords.awreporting.server.rest.kratu.KratuRest;
import com.google.api.ads.adwords.awreporting.server.rest.reports.DataAvailable;
import com.google.api.ads.adwords.awreporting.server.rest.reports.GenerateReportsRest;
import com.google.api.ads.adwords.awreporting.server.rest.reports.PreviewReportRest;
import com.google.api.ads.adwords.awreporting.server.rest.reports.ReportAccountRest;
import com.google.api.ads.adwords.awreporting.server.rest.reports.ReportAdExtensionRest;
import com.google.api.ads.adwords.awreporting.server.rest.reports.ReportAdGroupRest;
import com.google.api.ads.adwords.awreporting.server.rest.reports.ReportAdRest;
import com.google.api.ads.adwords.awreporting.server.rest.reports.ReportCampaignNegativeKeywordRest;
import com.google.api.ads.adwords.awreporting.server.rest.reports.ReportCampaignRest;
import com.google.api.ads.adwords.awreporting.server.rest.reports.ReportKeywordRest;
import com.google.api.ads.adwords.awreporting.server.rest.reports.ReportPlaceholderFeedItemRest;
import com.google.api.ads.adwords.awreporting.server.util.StorageHelper;
import com.google.api.ads.adwords.awreporting.util.DataBaseType;
import com.google.api.ads.adwords.awreporting.util.DynamicPropertyPlaceholderConfigurer;
import com.google.api.ads.adwords.awreporting.util.ProcessorType;
import com.google.api.client.util.Lists;

import org.apache.log4j.Logger;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.data.Protocol;
import org.restlet.resource.Directory;
import org.restlet.routing.Redirector;
import org.restlet.routing.Router;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * RestServer, add the Routing for the REST entry points and
 * initiates the ApplicationContext with the Spring beans
 * 
 * @author jtoledo@google.com (Julian Toledo)
 */
public class RestServer extends Application {

  private static final Logger logger = Logger.getLogger(RestServer.class);
  
  private static int serverPort = 8081;

  protected static ApplicationContext appCtx;

  protected static EntityPersister persister;

  protected static StorageHelper storageHelper;
  
  protected static WebAuthenticator webAuthenticator;
  
  protected static Properties properties;
  
  private List<String> listOfClassPathXml = Lists.newArrayList();

  /**
   * The DB type key specified in the properties file.
   */
  private static final String AW_REPORT_MODEL_DB_TYPE = "aw.report.model.db.type";

  /**
   * The Processor type key specified in the properties file.
   */
  private static final String AW_REPORT_PROCESSOR_TYPE = "aw.report.processor.type";

  public static ApplicationContext getApplicationContext() {
    return appCtx;
  }

  public static EntityPersister getPersister() {
    return persister;
  }

  public static WebAuthenticator getWebAuthenticator() {
    return webAuthenticator;
  }

  public static StorageHelper getStorageHelper() {
    return storageHelper;
  }

  public static Properties getProperties() {
    return properties;
  }

  public void addClassPathXml(String path) {
    listOfClassPathXml.add(path);
  }

  public RestServer() throws IOException {
  }

  public void startServer() throws Exception {
    startServer(this);
  }

  protected void startServer(RestServer restServer) throws Exception {
    Component component = new Component();
    Context context = component.getContext().createChildContext();
    component.getServers().add(Protocol.HTTP, serverPort);
    component.getClients().add(Protocol.FILE);
    context.getParameters().add("useForwardedForHeader", "true");
    setContext(context);
    component.getDefaultHost().attach(restServer);
    component.start();
  }

  @Override
  public synchronized Router createInboundRoot() {

    Router router = new Router(getContext());


    // *** MCCs ***
    /* ## HTTP method: GET
     *   Provides the list of 
     *   @return Array of accounts
     * 
     * ## HTTP method: PUT/POST
     *   Add/Modify an MCC Authenticated
     * 
     * ## HTTP method: DELETE
     *   Deletes AuthMccRest by topAccountId
     *   @param (url) topAccountId The MCC CID [REQUIRED]
     */
    router.attach("/mcc", AuthMccRest.class); //LIST All


    // *** Accounts ***
    /* ## HTTP method: GET
     *   Provides an array of accounts managed by the MCC specified
     * 
     *   @param (url) topAccountId The MCC CID [REQUIRED]
     *   @offset number of results to skip [OPTIONAL]
     *   @limit number of results to return[OPTIONAL]
     *   @return Array of accounts
     * 
     * ## HTTP method: PUT/POST
     *   Not implemented
     * 
     * ## HTTP method: DELETE
     *   Not implemented
     */
    router.attach("/mcc/{topAccountId}/accounts", AccountRest.class); //LIST All


    // *** Reporting ***
    // Generate
    // ?dateStart=yyyyMMdd&dateEnd=yyyyMMdd
    router.attach("/mcc/{topAccountId}/generatereports", GenerateReportsRest.class);

    // Accounts
    // ?dateStart=yyyyMMdd&dateEnd=yyyyMMdd
    // dateRangeType=DAY or dateRangeType=MONTH
    // @offset number of results to skip [OPTIONAL]
    // @limit number of results to return[OPTIONAL]
    router.attach("/mcc/{topAccountId}/reportaccount", ReportAccountRest.class); //LIST All
    router.attach("/mcc/{topAccountId}/reportaccount/{accountId}", ReportAccountRest.class); //LIST Account level

    // Campaigns
    // ?dateStart=yyyyMMdd&dateEnd=yyyyMMdd
    // dateRangeType=DAY or dateRangeType=MONTH
    // @offset number of results to skip [OPTIONAL]
    // @limit number of results to return[OPTIONAL]
    router.attach("/mcc/{topAccountId}/reportcampaign", ReportCampaignRest.class); //LIST All
    router.attach("/mcc/{topAccountId}/reportcampaign/{accountId}", ReportCampaignRest.class); //LIST Account level
    router.attach("/mcc/{topAccountId}/reportcampaign/campaign/{campaignId}", ReportCampaignRest.class); //LIST Campaign level

    // AdGroups
    // ?dateStart=yyyyMMdd&dateEnd=yyyyMMdd
    // dateRangeType=DAY or dateRangeType=MONTH
    // @offset number of results to skip [OPTIONAL]
    // @limit number of results to return[OPTIONAL]
    router.attach("/mcc/{topAccountId}/reportadgroup", ReportAdGroupRest.class); //LIST All
    router.attach("/mcc/{topAccountId}/reportadgroup/{accountId}", ReportAdGroupRest.class); //LIST Account level
    router.attach("/mcc/{topAccountId}/reportadgroup/campaign/{campaignId}", ReportAdGroupRest.class); //LIST Campaign level
    router.attach("/mcc/{topAccountId}/reportadgroup/adgroup/{adGroupId}", ReportAdGroupRest.class); //LIST AdGroup level

    // Ads
    // ?dateStart=yyyyMMdd&dateEnd=yyyyMMdd
    // dateRangeType=DAY or dateRangeType=MONTH
    // @offset number of results to skip [OPTIONAL]
    // @limit number of results to return[OPTIONAL]
    router.attach("/mcc/{topAccountId}/reportad", ReportAdRest.class); //LIST All
    router.attach("/mcc/{topAccountId}/reportad/{accountId}", ReportAdRest.class); //LIST Account level
    router.attach("/mcc/{topAccountId}/reportad/campaign/{campaignId}", ReportAdRest.class); //LIST Campaign level
    router.attach("/mcc/{topAccountId}/reportad/adgroup/{adGroupId}", ReportAdRest.class); //LIST AdGroup level
    router.attach("/mcc/{topAccountId}/reportad/ad/{adId}", ReportAdRest.class); //LIST Ad level

    // Keywords
    // ?dateStart=yyyyMMdd&dateEnd=yyyyMMdd
    // dateRangeType=DAY or dateRangeType=MONTH
    // @offset number of results to skip [OPTIONAL]
    // @limit number of results to return[OPTIONAL]
    router.attach("/mcc/{topAccountId}/reportkeyword", ReportKeywordRest.class); //LIST All
    router.attach("/mcc/{topAccountId}/reportkeyword/{accountId}", ReportKeywordRest.class); //LIST Account level
    router.attach("/mcc/{topAccountId}/reportkeyword/campaign/{campaignId}", ReportKeywordRest.class); //LIST Campaign level
    router.attach("/mcc/{topAccountId}/reportkeyword/adgroup/{adGroupId}", ReportKeywordRest.class); //LIST AdGroup level
    router.attach("/mcc/{topAccountId}/reportkeyword/keyword/{criterionId}", ReportKeywordRest.class); //LIST Keyword level

    // ReportCampaignNegativeKeyword
    // This one does not support dateStart and dateEnd
    router.attach("/mcc/{topAccountId}/reportcampaignnegativekeyword", ReportCampaignNegativeKeywordRest.class); //LIST All
    router.attach("/mcc/{topAccountId}/reportcampaignnegativekeyword/{accountId}", ReportCampaignNegativeKeywordRest.class); //LIST Account level
    router.attach("/mcc/{topAccountId}/reportcampaignnegativekeyword/campaign/{campaignId}", ReportCampaignNegativeKeywordRest.class); //LIST Campaign level
    router.attach("/mcc/{topAccountId}/reportcampaignnegativekeyword/keyword/{criterionId}", ReportCampaignNegativeKeywordRest.class); //LIST Keyword level

    // ReportAdExtension
    // ?dateStart=yyyyMMdd&dateEnd=yyyyMMdd
    // dateRangeType=DAY or dateRangeType=MONTH
    // @offset number of results to skip [OPTIONAL]
    // @limit number of results to return[OPTIONAL]
    router.attach("/mcc/{topAccountId}/reportadextension", ReportAdExtensionRest.class); //LIST All
    router.attach("/mcc/{topAccountId}/reportadextension/{accountId}", ReportAdExtensionRest.class); //LIST Account level
    router.attach("/mcc/{topAccountId}/reportadextension/campaign/{campaignId}", ReportAdExtensionRest.class); //LIST Campaign level
    router.attach("/mcc/{topAccountId}/reportadextension/adextension/{adExtensionId}", ReportAdExtensionRest.class); //LIST AdExtension level

    // ReportPlaceholderFeedItem
    // ?dateStart=yyyyMMdd&dateEnd=yyyyMMdd
    // dateRangeType=DAY or dateRangeType=MONTH
    // @offset number of results to skip [OPTIONAL]
    // @limit number of results to return[OPTIONAL]
    router.attach("/mcc/{topAccountId}/reportplaceholderfeeditem", ReportPlaceholderFeedItemRest.class); //LIST All
    router.attach("/mcc/{topAccountId}/reportplaceholderfeeditem/{accountId}", ReportPlaceholderFeedItemRest.class); //LIST Account level
    router.attach("/mcc/{topAccountId}/reportplaceholderfeeditem/campaign/{campaignId}", ReportPlaceholderFeedItemRest.class); //LIST Campaign level
    router.attach("/mcc/{topAccountId}/reportplaceholderfeeditem/adgroup/{adGroupId}", ReportPlaceholderFeedItemRest.class); //LIST AdGroup level
    router.attach("/mcc/{topAccountId}/reportplaceholderfeeditem/ad/{adId}", ReportPlaceholderFeedItemRest.class); //LIST Ad level
    router.attach("/mcc/{topAccountId}/reportplaceholderfeeditem/feed/{feedId}", ReportPlaceholderFeedItemRest.class); //LIST Feed level
    router.attach("/mcc/{topAccountId}/reportplaceholderfeeditem/feeditem/{feedItemId}", ReportPlaceholderFeedItemRest.class); //LIST FeedItem level

    /* ## HTTP method: GET
     *   Provides a list map with the minimal and maximal dates available for each Report Type
     *   for a given topAccountId. Returns an error message if a usertoken does not exist for the account.
     * 
     *   @param (url) topAccountId The MCC CID [REQUIRED]
     *   @param (query) dateRangeType Type of available data to check ('day' or 'month') [OPTIONAL - defaults to 'month' if not provided]
     *   @return Array of available data objects. If data range is not provided, there is no data.
     * 
     * ## HTTP method: PUT/POST
     *   Not implemented
     * 
     * ## HTTP method: DELETE
     *   Not implemented
     */
    router.attach("/mcc/{topAccountId}/dataavailable", DataAvailable.class);


    // *** HTML to PDF conversion & Report Preview***
    router.attach("/html2pdf", HtmlToPdfRest.class);


    /* ## HTTP method: GET
     *   Returns the report in PDF or HTML
     *   
     *   @param (url) topAccountId the MCC CID [REQUIRED]
     *   @param (url) accountId the MCC CID [REQUIRED]
     *   @param (query) templateId ID of template to use for report doc [REQUIRED]
     *   @param (query) monthStart formated yyyyMM [OPTIONAL - defaults to last month if both dates not provided]
     *   @param (query) monthEnd formated yyyyMM [OPTIONAL - defaults to last month if both dates not provided]
     *   @param (query) reporttype "pdf" or "html" [OPTIONAL - defaults to html]
     *   @return "OK" (Needs to be changed to URL of Drive folder containing reports)
     * 
     * ## HTTP method: PUT/POST
     *   NOT IMPLEMENTED
     * 
     * ## HTTP method: DELETE
     *   NOT IMPLEMENTED
     */
    router.attach("/mcc/{topAccountId}/previewreports/account/{accountId}", PreviewReportRest.class);


    // *** HTML template management ***
    /* ## HTTP method: GET
     *   Gets either templates owned by a user or 'public' templates
     *   
     *   @param (url) isPublic true to get public templates [OPTIONAL: defaults to false if not set]
     *   @return Array of templates
     * 
     * ## HTTP method: PUT/POST
     *   Add (upload) a new template or update an existing template.
     *   Existing templates can be uploaded by ensuring that the existing templateId is set.
     *   
     *   @param (body) templateName [Optional, but should be changed to required]
     *   @param (body) templateDescription  [Optional, but should be changed to required]
     *   @param (body) isPublic  [Optional, defaults to false]
     *   @param (body) templateHtml (encoded)  [Optional, but should be changed to required]
     *   @return The persisted template object including the templateId
     * 
     * ## HTTP method: DELETE
     *   NOT IMPLEMENTED
     */
    router.attach("/template", HtmlTemplateRest.class);

    /* ## HTTP method: GET
     *   Gets a template owned by a user using it's ID
     *   
     *   @param (url) templateId template to get
     *   @return template object
     * 
     * ## HTTP method: PUT/POST
     *   NOT IMPLEMENTED
     * 
     * ## HTTP method: DELETE
     *   Deletes a template
     *   @param (query) templateId the template to delete [REQUIRED]
     */
    router.attach("/template/{templateId}", HtmlTemplateRest.class);


    // *** Kratu ***
    // ?includeZeroImpressions=false by default
    router.attach("/mcc/{topAccountId}/kratu", KratuRest.class); // List All
    router.attach("/mcc/{topAccountId}/kratu/{accountId}", KratuRest.class); // LIST Account level

    // Genereate Kratus MCC level
    // ?dateStart=yyyyMMdd&dateEnd=yyyyMMdd
    router.attach("/mcc/{topAccountId}/generatekratus", GenerateKratusRest.class);


    // *** Static files *** 
<<<<<<< HEAD
    String target = "awrc/index.html";
=======
    String target = "index.html";
>>>>>>> 2f2a7486c98ac121ad93d5d5bb44c6f875fcbc1a
    Redirector redirector = new Redirector(getContext(), target, Redirector.MODE_CLIENT_FOUND);
    router.attach("/", redirector);
    File currentPath = new File(RestServer.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    String htmlPath = "file:///" + currentPath.getParent() + "/html/";
    router.attach("/", redirector);
    router.attach("", new Directory(getContext(), htmlPath));

    return router;
  }

  /**
   * Initialize the application context, adding the properties configuration file depending on the
   * specified path.
   * @throws IOException 
   */
  public void initApplicationContextAndProperties(String propertiesFilePath) throws IOException {

    Resource resource = new ClassPathResource(propertiesFilePath);
    if (!resource.exists()) {
      resource = new FileSystemResource(propertiesFilePath);
    }
    DynamicPropertyPlaceholderConfigurer.setDynamicResource(resource);
    properties = PropertiesLoaderUtils.loadProperties(resource);

    // Set the server port from the properties file or use 8081 as default. 
    String strServerPort = properties.getProperty("serverport");
    if (strServerPort != null && strServerPort.length() > 0) {
      serverPort = Integer.valueOf(strServerPort);
    }

    listOfClassPathXml.add("classpath:storage-helper-beans.xml");

    listOfClassPathXml.add("classpath:kratu-processor-beans.xml");

    listOfClassPathXml.add("aw-reporting-server-webauthenticator.xml");
    
    listOfClassPathXml.add("aw-pdf-exporter-beans.xml");

    // Choose the DB type to use based properties file
    String dbType = (String) properties.get(AW_REPORT_MODEL_DB_TYPE);
    if (dbType != null && dbType.equals(DataBaseType.MONGODB.name())) {
      logger.info("Using MONGO DB configuration properties.");
      listOfClassPathXml.add("classpath:aw-report-mongodb-beans.xml");
    } else {
      logger.info("Using SQL DB configuration properties.");
      listOfClassPathXml.add("classpath:aw-report-sql-beans.xml");
    }

    // Choose the Processor type to use based properties file
    String processorType = (String) properties.get(AW_REPORT_PROCESSOR_TYPE);
    if (processorType != null && processorType.equals(ProcessorType.ONMEMORY.name())) {
      logger.info("Using ONMEMORY Processor.");
      listOfClassPathXml.add("classpath:aw-report-processor-beans-onmemory.xml");
    } else {
      logger.info("Using ONFILE Processor.");
      listOfClassPathXml.add("classpath:aw-report-processor-beans-onfile.xml");
    }

    appCtx = new ClassPathXmlApplicationContext(listOfClassPathXml.toArray(new String[listOfClassPathXml.size()]));    
    persister = appCtx.getBean(EntityPersister.class);
    storageHelper = appCtx.getBean(StorageHelper.class);
    webAuthenticator = appCtx.getBean(WebAuthenticator.class);
  }
}
