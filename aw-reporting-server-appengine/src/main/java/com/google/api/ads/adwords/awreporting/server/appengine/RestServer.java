//Copyright 2012 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.api.ads.adwords.awreporting.server.appengine;

import com.google.api.ads.adwords.awreporting.model.entities.AuthMcc;
import com.google.api.ads.adwords.awreporting.model.entities.ReportAccount;
import com.google.api.ads.adwords.awreporting.model.entities.ReportAd;
import com.google.api.ads.adwords.awreporting.model.entities.ReportAdGroup;
import com.google.api.ads.adwords.awreporting.model.entities.ReportBudget;
import com.google.api.ads.adwords.awreporting.model.entities.ReportCampaign;
import com.google.api.ads.adwords.awreporting.model.entities.ReportCampaignNegativeKeyword;
import com.google.api.ads.adwords.awreporting.model.entities.ReportCriteriaPerformance;
import com.google.api.ads.adwords.awreporting.model.entities.ReportDestinationUrl;
import com.google.api.ads.adwords.awreporting.model.entities.ReportKeyword;
import com.google.api.ads.adwords.awreporting.model.entities.ReportPlaceholderFeedItem;
import com.google.api.ads.adwords.awreporting.model.entities.ReportUrl;
import com.google.api.ads.adwords.awreporting.model.persistence.EntityPersister;
import com.google.api.ads.adwords.awreporting.server.appengine.authentication.AppEngineOAuth2Authenticator;
import com.google.api.ads.adwords.awreporting.server.appengine.model.UserToken;
import com.google.api.ads.adwords.awreporting.server.appengine.rest.MccRest;
import com.google.api.ads.adwords.awreporting.server.appengine.rest.OAuthRest;
import com.google.api.ads.adwords.awreporting.server.appengine.rest.UserRest;
import com.google.api.ads.adwords.awreporting.server.appengine.rest.cron.GenerateReportsCronRest;
import com.google.api.ads.adwords.awreporting.server.appengine.rest.reporting.DataAvailable;
import com.google.api.ads.adwords.awreporting.server.appengine.rest.reporting.ExportReportsRest;
import com.google.api.ads.adwords.awreporting.server.appengine.rest.reporting.GenerateReportsRest;
import com.google.api.ads.adwords.awreporting.server.appengine.rest.reporting.PreviewReportRest;
import com.google.api.ads.adwords.awreporting.server.authentication.WebAuthenticator;
import com.google.api.ads.adwords.awreporting.server.entities.Account;
import com.google.api.ads.adwords.awreporting.server.entities.HtmlTemplate;
import com.google.api.ads.adwords.awreporting.server.rest.AccountRest;
import com.google.api.ads.adwords.awreporting.server.rest.HtmlTemplateRest;
import com.google.api.ads.adwords.awreporting.server.rest.reports.ReportAccountRest;
import com.google.api.ads.adwords.awreporting.server.rest.reports.ReportAdGroupRest;
import com.google.api.ads.adwords.awreporting.server.rest.reports.ReportCampaignRest;
import com.google.api.ads.adwords.awreporting.server.rest.reports.ReportPlaceholderFeedItemRest;
import com.google.api.ads.adwords.awreporting.util.AdWordsSessionBuilderSynchronizer;
import com.google.api.ads.adwords.awreporting.util.DynamicPropertyPlaceholderConfigurer;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.client.util.Maps;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.impl.translate.opt.BigDecimalLongTranslatorFactory;

import org.restlet.routing.Router;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Main class for the Server, it routes request to the Rest entry points. 
 *
 * @author jtoledo@google.com (Julian Toledo)
 * @author joeltoby@google.com (Joel Toby)
 */
public class RestServer extends com.google.api.ads.adwords.awreporting.server.rest.RestServer {
  
  public RestServer() throws IOException {
    super();
  }

  private static final Logger LOGGER = Logger.getLogger(RestServer.class.getName());
  
  private static final String PROPERTIES_FILE = "aw-reporting-appengine-sample.properties";

  private static AppEngineOAuth2Authenticator authenticator;
  
  public static ApplicationContext getApplicationContext() {
    if (appCtx == null) {
      synchronized (RestServer.class) {
        if (appCtx == null) {
          initApplicationContextAndProperties();
        }
      }
    }
    return appCtx;
  }

  public static Properties getProperties() {
    if (properties == null || appCtx == null) {
      synchronized (RestServer.class) {
        if (properties == null || appCtx == null) {
          initApplicationContextAndProperties();
        }
      }
    }
    return properties;
  }

  public static EntityPersister getPersister() {
    if (persister == null || appCtx == null) {
      synchronized (RestServer.class) {
        if (persister == null || appCtx == null) {
          initApplicationContextAndProperties();
        }
      }
    }
    return persister;
  }

  public static AppEngineOAuth2Authenticator getAuthenticator() {
    if (authenticator == null || appCtx == null) {
      synchronized (RestServer.class) {
        if (authenticator == null || appCtx == null) {
          initApplicationContextAndProperties();
        }
      }
    }
    return authenticator;
  }

  public static WebAuthenticator getWebAuthenticator() {
    if (webAuthenticator == null || appCtx == null) {
      synchronized (RestServer.class) {
        if (webAuthenticator == null || appCtx == null) {
          initApplicationContextAndProperties();
        }
      }
    }
    return webAuthenticator;
  }

  private static final HashMap<AdWordsSessionBuilderSynchronizer, AdWordsSessionBuilderSynchronizer>
    adWordsSessionBuilderSynchronizerMap = Maps.newHashMap();

  public static synchronized AdWordsSessionBuilderSynchronizer getAdWordsSessionBuilderSynchronizer(String userId, String mccAccountId) throws OAuthException, IOException {
    AdWordsSessionBuilderSynchronizer adWordsSessionBuilderSynchronizer = adWordsSessionBuilderSynchronizerMap.get(mccAccountId);
    if (adWordsSessionBuilderSynchronizer == null) {
      synchronized (RestServer.class) {
        if (adWordsSessionBuilderSynchronizer == null) {
          adWordsSessionBuilderSynchronizer = new AdWordsSessionBuilderSynchronizer(getAuthenticator().authenticate(userId, mccAccountId, false));
        }
      }
    }
    return adWordsSessionBuilderSynchronizer;
  }

  /**
   * Creates a root Restlet that will receive all incoming calls.
   */
  @Override
  public synchronized Router createInboundRoot() {

    initApplicationContextAndProperties();

    Router router = new Router(getContext());

    // *** MCCs ***

    /* ## HTTP method: GET
     *   Provides an array of 'UserToken' objects which includes info on the user (including user ID) 
     *   and the MCC.  Only includes tokens owned by the user.
     * 
     *   @return array of 'UserToken' objects
     * 
     * ## HTTP method: PUT/POST
     *   Not implemented
     * 
     * ## HTTP method: DELETE
     *   Not implemented
     */
    router.attach("/mcc", MccRest.class);

    /* ## HTTP method: GET
     *   Provides a single 'UserToken' object for the MCC CID provided which includes info
     *   on the user (including user ID) and the MCC.  MCC must be owned by the user.
     * 
     *   @param (url) topAccountId The MCC CID [REQUIRED]
     *   @return 'UserToken' object
     * 
     * ## HTTP method: PUT/POST
     *   Not implemented
     * 
     * ## HTTP method: DELETE
     *   Deletes the MCC CID provided (for that user)
     *   @param (url) topAccountId The MCC CID [REQUIRED]
     *   @return "OK"
     */
    router.attach("/mcc/{topAccountId}", MccRest.class);


    // *** Accounts ***

    /* ## HTTP method: GET
     *   Provides an array of accounts managed by the MCC specified
     * 
     *   @param (url) topAccountId The MCC CID [REQUIRED]
     *   @return Array of accounts
     * 
     * ## HTTP method: PUT/POST
     *   Not implemented
     * 
     * ## HTTP method: DELETE
     *   Not implemented
     */
    router.attach("/mcc/{topAccountId}/accounts", AccountRest.class);

    /* ## HTTP method: GET
     *   Provides info on a single account
     * 
     *   @param (url) accountId MCC CID [REQUIRED]
     *   @return An account object
     * 
     * ## HTTP method: PUT/POST
     *   Not implemented
     * 
     * ## HTTP method: DELETE
     *   Not implemented
     */
    router.attach("/account/{accountId}", AccountRest.class);


    //*** Authentication ***

    /* ## HTTP method: GET
     *   Authenticate an MCC for that user
     * 
     *   @param (url) topAccountId MCC to authenticate [REQUIRED]
     *   @return "" (empty string, but redirects)
     * 
     * ## HTTP method: PUT/POST
     *   Same as GET except topAccountId is sent in BODY: { topAccountId: "123-123-1234" }
     * 
     * ## HTTP method: DELETE
     *   Not implemented
     */
    router.attach("/oauth/{topAccountId}", OAuthRest.class);
    router.attach("/oauth2callback", OAuthRest.class);
    
    
    //*** Users ***

    /* ## HTTP method: GET
     *   Get info on current user ("Who am I")
     * 
     *   @return User info
     * 
     * ## HTTP method: PUT/POST
     *   Not implemented
     * 
     * ## HTTP method: DELETE
     *   Not implemented
     */
    router.attach("/user", UserRest.class);
    
    /* ## HTTP method: GET
     *   User login or logout
     *   
     *   @param (url) other acceptable values are simply 'login' or logout.  i.e. /user/login
     *   @return "" (empty string)
     * 
     * ## HTTP method: PUT/POST
     *   Not implemented
     * 
     * ## HTTP method: DELETE
     *   Not implemented
     */
    router.attach("/user/{other}", UserRest.class);


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


    // *** Reporting ***

    /* ## HTTP method: GET
     *   Generates a report task for an MCC
     *   
     *   @param (url) topAccountId the MCC CID [REQUIRED]
     *   @param (url) dateStart formated yyyyMMdd [REQUIRED]
     *   @param (url) dateEnd formated yyyyMMdd [REQUIRED]
     *   @return "OK"
     * 
     * ## HTTP method: PUT/POST
     *   NOT IMPLEMENTED
     * 
     * ## HTTP method: DELETE
     *   NOT IMPLEMENTED
     */
    router.attach("/mcc/{topAccountId}/generatereports", GenerateReportsRest.class);
    
    /* ## HTTP method: GET
     *   Generates an export report task for an MCC (all accounts within MCC)
     *   
     *   @param (url) topAccountId the MCC CID [REQUIRED]
     *   @param (query) templateId ID of template to use for report doc [REQUIRED]
     *   @param (query) monthStart formated yyyyMM [OPTIONAL - defaults to last month if both dates not provided]
     *   @param (query) monthEnd formated yyyyMM [OPTIONAL - defaults to last month if both dates not provided]
     *   @return "OK" (Needs to be changed to URL of Drive folder containing reports)
     * 
     * ## HTTP method: PUT/POST
     *   NOT IMPLEMENTED
     * 
     * ## HTTP method: DELETE
     *   NOT IMPLEMENTED
     */
    router.attach("/mcc/{topAccountId}/exportreports", ExportReportsRest.class);
        
    /* ## HTTP method: GET
     *   Generates an export report task for a single account for complete monthly periods.
     *   
     *   @param (url) topAccountId the MCC CID [REQUIRED]
     *   @param (url) accountId the MCC CID [REQUIRED]
     *   @param (query) templateId ID of template to use for report doc [REQUIRED]
     *   @param (query) monthStart formated yyyyMM [OPTIONAL - defaults to last month if both dates not provided]
     *   @param (query) monthEnd formated yyyyMM [OPTIONAL - defaults to last month if both dates not provided]
     *   @return "OK" (Needs to be changed to URL of Drive folder containing reports)
     * 
     * ## HTTP method: PUT/POST
     *   NOT IMPLEMENTED
     * 
     * ## HTTP method: DELETE
     *   NOT IMPLEMENTED
     */
    router.attach("/mcc/{topAccountId}/exportreports/account/{accountId}", ExportReportsRest.class);

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

    /* ## HTTP method: GET
     *   Provides a list map with the minimal and maximal dates available for each Report Type
     *   for a given topAccountId. Returns an error message if a usertoken does not exist for the account.
     * 
     *   @param (url) topAccountId The MCC CID [REQUIRED]
     *   @return Array of accounts
     * 
     * ## HTTP method: PUT/POST
     *   Not implemented
     * 
     * ## HTTP method: DELETE
     *   Not implemented
     */
    router.attach("/mcc/{topAccountId}/dataavailable", DataAvailable.class);

    // LIST Top Account level
    router.attach("/mcc/{topAccountId}/reportaccount", ReportAccountRest.class);
    // LIST Account level
    router.attach("/mcc/{topAccountId}/account/{accountId}/reportaccount", ReportAccountRest.class);

    // LIST Top Account level
    router.attach("/mcc/{topAccountId}/reportcampaign", ReportCampaignRest.class);
    // LIST Account level
    router.attach("/mcc/{topAccountId}/account/{accountId}/reportcampaign", ReportCampaignRest.class);

    // LIST Top Account level
    router.attach("/mcc/{topAccountId}/reportadgroup", ReportAdGroupRest.class);
    // LIST Account level
    router.attach("/mcc/{topAccountId}/account/{accountId}/reportadgroup", ReportAdGroupRest.class);

    // LIST Top Account level
    router.attach("/mcc/{topAccountId}/reportplaceholderfeeditem", ReportPlaceholderFeedItemRest.class);
    // LIST Account level
    router.attach("/mcc/{topAccountId}/account/{accountId}/reportplaceholderfeeditem", ReportPlaceholderFeedItemRest.class);


    // *** Generate Reports Cron Job ***
    router.attach("/generatereportscron", GenerateReportsCronRest.class);
    
    return router;
  }

  /**
   * Initialize the application context, adding the properties configuration file depending on the
   * specified path.
   */
  protected synchronized static void initApplicationContextAndProperties() {

    // Resister all Model Objects in the ObjectifyService
    ObjectifyService.factory().getTranslators().add(new BigDecimalLongTranslatorFactory(1000000000));

    ObjectifyService.register(UserToken.class);

    // AwReporting Server model objects
    ObjectifyService.register(Account.class);
    ObjectifyService.register(HtmlTemplate.class);

    // AwReporting model objects
    ObjectifyService.register(AuthMcc.class);
    ObjectifyService.register(ReportAccount.class);
    ObjectifyService.register(ReportAd.class);
    ObjectifyService.register(ReportAdGroup.class);
    ObjectifyService.register(ReportBudget.class);
    ObjectifyService.register(ReportCampaign.class);
    ObjectifyService.register(ReportCampaignNegativeKeyword.class);
    ObjectifyService.register(ReportCriteriaPerformance.class);
    ObjectifyService.register(ReportDestinationUrl.class);
    ObjectifyService.register(ReportKeyword.class);
    ObjectifyService.register(ReportPlaceholderFeedItem.class);
    ObjectifyService.register(ReportUrl.class);

    try {
      Resource resource = new ClassPathResource(PROPERTIES_FILE);
      if (!resource.exists()) {
        resource = new FileSystemResource(PROPERTIES_FILE);
      }
      DynamicPropertyPlaceholderConfigurer.setDynamicResource(resource);

      properties = PropertiesLoaderUtils.loadProperties(resource);
      
    } catch (IOException e) {
      LOGGER.severe("Error reading properties file " + e.getMessage());
    }

    // Loading AppEngine DB type by default (ignoring DB type from properties file)
    appCtx = new ClassPathXmlApplicationContext("classpath:aw-reporting-appengine-beans.xml");
    persister = appCtx.getBean(EntityPersister.class);
    authenticator = appCtx.getBean(AppEngineOAuth2Authenticator.class);
    webAuthenticator = appCtx.getBean(WebAuthenticator.class);
  }
}
