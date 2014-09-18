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
import com.google.api.ads.adwords.awreporting.server.rest.reports.ReportAccountRest;
import com.google.api.ads.adwords.awreporting.server.rest.reports.ReportAdExtensionRest;
import com.google.api.ads.adwords.awreporting.server.rest.reports.ReportAdGroupRest;
import com.google.api.ads.adwords.awreporting.server.rest.reports.ReportAdRest;
import com.google.api.ads.adwords.awreporting.server.rest.reports.ReportCampaignNegativeKeywordRest;
import com.google.api.ads.adwords.awreporting.server.rest.reports.ReportCampaignRest;
import com.google.api.ads.adwords.awreporting.server.rest.reports.ReportKeywordRest;
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
import org.restlet.routing.Router;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

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

  private static ApplicationContext appCtx;

  private static EntityPersister persister;

  private static StorageHelper storageHelper;
  
  private static Properties properties;
  
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

    // Accounts
    // ?dateStart=yyyyMMdd&dateEnd=yyyyMMdd
    // dateRangeType=DAY or dateRangeType=MONTH
    router.attach("/mcc/{topAccountId}/reportaccount", ReportAccountRest.class); //LIST All
    router.attach("/mcc/{topAccountId}/reportaccount/{accountId}", ReportAccountRest.class); //LIST Account level
    
    // Campaigns
    // ?dateStart=yyyyMMdd&dateEnd=yyyyMMdd
    // dateRangeType=DAY or dateRangeType=MONTH
    router.attach("/mcc/{topAccountId}/reportcampaign", ReportCampaignRest.class); //LIST All
    router.attach("/mcc/{topAccountId}/reportcampaign/{accountId}", ReportCampaignRest.class); //LIST Account level
    router.attach("/mcc/{topAccountId}/reportcampaign/campaign/{campaignId}", ReportCampaignRest.class); //LIST Campaign level

    // AdGroups
    // ?dateStart=yyyyMMdd&dateEnd=yyyyMMdd
    // dateRangeType=DAY or dateRangeType=MONTH
    router.attach("/mcc/{topAccountId}/reportadgroup", ReportAdGroupRest.class); //LIST All
    router.attach("/mcc/{topAccountId}/reportadgroup/{accountId}", ReportAdGroupRest.class); //LIST Account level
    router.attach("/mcc/{topAccountId}/reportadgroup/campaign/{campaignId}", ReportAdGroupRest.class); //LIST Campaign level
    router.attach("/mcc/{topAccountId}/reportadgroup/adgroup/{adGroupId}", ReportAdGroupRest.class); //LIST AdGroup level

    // Ads
    // ?dateStart=yyyyMMdd&dateEnd=yyyyMMdd
    // dateRangeType=DAY or dateRangeType=MONTH
    router.attach("/mcc/{topAccountId}/reportad", ReportAdRest.class); //LIST All
    router.attach("/mcc/{topAccountId}/reportad/{accountId}", ReportAdRest.class); //LIST Account level
    router.attach("/mcc/{topAccountId}/reportad/campaign/{campaignId}", ReportAdRest.class); //LIST Campaign level
    router.attach("/mcc/{topAccountId}/reportad/adgroup/{adGroupId}", ReportAdRest.class); //LIST AdGroup level
    router.attach("/mcc/{topAccountId}/reportad/ad/{adId}", ReportAdRest.class); //LIST Ad level

    // Keywords
    // ?dateStart=yyyyMMdd&dateEnd=yyyyMMdd
    // dateRangeType=DAY or dateRangeType=MONTH
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
    router.attach("/mcc/{topAccountId}/reportadextension", ReportAdExtensionRest.class); //LIST All
    router.attach("/mcc/{topAccountId}/reportadextension/{accountId}", ReportAdExtensionRest.class); //LIST Account level
    router.attach("/mcc/{topAccountId}/reportadextension/campaign/{campaignId}", ReportAdExtensionRest.class); //LIST Campaign level
    router.attach("/mcc/{topAccountId}/reportadextension/adextension/{adExtensionId}", ReportAdExtensionRest.class); //LIST Keyword level

    // HTML to PDF conversion
    router.attach("/html2pdf", HtmlToPdfRest.class);

    // *** Accounts ***
    router.attach("/mcc/{topAccountId}/accounts", AccountRest.class); //LIST All

    // *** Static files *** 
    // USING FILE
    /*
    String target = "index.html";
    Redirector redirector = new Redirector(getContext(), target, Redirector.MODE_CLIENT_FOUND);
    router.attach("/", redirector);
    File currentPath = new File(RestServer.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    String htmlPath = "file:///" + currentPath.getParent() + "/html/";
    router.attach("/", redirector);
    router.attach("", new Directory(getContext(), htmlPath));
    */

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
    storageHelper = getApplicationContext().getBean(StorageHelper.class);
  }
}
