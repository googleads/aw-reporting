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

package com.google.api.ads.adwords.jaxws.extensions.appengine;

import com.google.api.ads.adwords.jaxws.extensions.appengine.model.Account;
import com.google.api.ads.adwords.jaxws.extensions.appengine.model.UserToken;
import com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister;
import com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.appengine.objectify.ObjectifyEntityPersister;
import com.google.api.ads.adwords.jaxws.extensions.report.rest.AccountRest;
import com.google.api.ads.adwords.jaxws.extensions.report.rest.MyMCCs;
import com.google.api.ads.adwords.jaxws.extensions.report.rest.OAuthRest;
import com.google.api.ads.adwords.jaxws.extensions.report.rest.reporting.GenerateReportsRest;
import com.google.api.ads.adwords.jaxws.extensions.report.rest.reporting.TaskReportsRest;
import com.google.api.ads.adwords.jaxws.extensions.util.DataBaseType;
import com.google.api.ads.adwords.jaxws.extensions.util.DynamicPropertyPlaceholderConfigurer;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.googlecode.objectify.ObjectifyService;

import java.io.IOException;
import java.util.Properties;

public class RestServer extends Application {

  private static final String AW_REPORT_MODEL_DB_TYPE = "aw.report.model.db.type";

  public static ApplicationContext appCtx;
  
  public static Properties properties;
  
  public static EntityPersister persister;

  /**
   * Creates a root Restlet that will receive all incoming calls.
   */
  @Override
  public Restlet createInboundRoot() {
    
    
    ObjectifyService.register(Account.class);
    ObjectifyService.register(UserToken.class);
    
    try {
      properties = initApplicationContextAndProperties("aw-report-sample-appengine.properties");
      
      UserToken userToken = new UserToken("id Test", 123L, "email", "token");
      persister.save(userToken);
      
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    Router router = new Router(getContext());

    // *** Reporting ***
    // Generate Report Task for MCC
    // ?month=yyyy-MM OR empty for last month
    router.attach("/generatereports/{topAccountId}", GenerateReportsRest.class);

    // *** Reporting Tasks ***
    // Process Report Task MCC level
    router.attach("/taskreport/{topAccountId}", TaskReportsRest.class);
    // Process Report Task Account level
    router.attach("/taskreport/{topAccountId}/{accountId}", TaskReportsRest.class);

    router.attach("/accounts/{topAccountId}", AccountRest.class);
    router.attach("/accounts/account/{accountId}", AccountRest.class);
    
    // Authenticate a new MCC using OAuth
    router.attach("/oauth/{topAccountId}", OAuthRest.class);
    router.attach("/oauth2callback", OAuthRest.class);
    router.attach("/oauth/user/{other}", OAuthRest.class);
    
    router.attach("/mymccs", MyMCCs.class);

    return router;
  }
  
  /**
   * Initialize the application context, adding the properties configuration file depending on the
   * specified path.
   *
   * @param propertiesPath the path to the file.
   * @return the resource loaded from the properties file.
   * @throws IOException error opening the properties file.
   */
  protected static Properties initApplicationContextAndProperties(String propertiesPath)
      throws IOException {

    Resource resource = new ClassPathResource(propertiesPath);
    if (!resource.exists()) {
      resource = new FileSystemResource(propertiesPath);
    }
    
    DynamicPropertyPlaceholderConfigurer.setDynamicResource(resource);

    Properties properties = PropertiesLoaderUtils.loadProperties(resource);
    String dbType = (String) properties.get(AW_REPORT_MODEL_DB_TYPE);
    if (dbType != null && dbType.equals(DataBaseType.MONGODB.name())) {

      appCtx = new ClassPathXmlApplicationContext("classpath:aw-reporting-mongodb-beans.xml");
    } else {
    
      appCtx = new ClassPathXmlApplicationContext("classpath:aw-reporting-sql-beans.xml");
    }
    
    persister = appCtx.getBean(ObjectifyEntityPersister.class);

    return properties;
  }
}