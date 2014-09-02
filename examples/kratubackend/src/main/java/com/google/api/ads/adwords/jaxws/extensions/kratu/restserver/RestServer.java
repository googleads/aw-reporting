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

package com.google.api.ads.adwords.jaxws.extensions.kratu.restserver;

import com.google.api.ads.adwords.jaxws.extensions.kratu.restserver.kratu.GenerateKratusRest;
import com.google.api.ads.adwords.jaxws.extensions.kratu.restserver.kratu.KratuRest;
import com.google.api.ads.adwords.jaxws.extensions.kratu.restserver.reports.GenerateReportsRest;
import com.google.api.ads.adwords.jaxws.extensions.kratu.restserver.reports.ReportAccountRest;
import com.google.api.ads.adwords.jaxws.extensions.kratu.restserver.reports.ReportAdExtensionRest;
import com.google.api.ads.adwords.jaxws.extensions.kratu.restserver.reports.ReportAdGroupRest;
import com.google.api.ads.adwords.jaxws.extensions.kratu.restserver.reports.ReportAdRest;
import com.google.api.ads.adwords.jaxws.extensions.kratu.restserver.reports.ReportCampaignNegativeKeywordRest;
import com.google.api.ads.adwords.jaxws.extensions.kratu.restserver.reports.ReportCampaignRest;
import com.google.api.ads.adwords.jaxws.extensions.kratu.restserver.reports.ReportKeywordRest;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.resource.Directory;
import org.restlet.routing.Redirector;
import org.restlet.routing.Router;
import org.springframework.context.ApplicationContext;

import java.io.File;

/**
 * RestServer
 * 
 * @author jtoledo@google.com (Julian Toledo)
 */
public class RestServer extends Application {

  public static void createRestServer(ApplicationContext appCtx, String propertiesPath, int port) throws Exception {

    // Create a component
    Component component = new Component();
    component.getServers().add(Protocol.HTTP, port);
    component.getClients().add(Protocol.FILE);

    Context context = component.getContext().createChildContext();
    RestServer application = new RestServer(context);
    
    application.getContext().getParameters().add("useForwardedForHeader", "true");
    
    application.getContext().getAttributes().put("appCtx", appCtx);
    application.getContext().getAttributes().put("file", propertiesPath);

    // Attach the application to the component and start it
    component.getDefaultHost().attach(application);
    component.start();
  }

  private RestServer(Context context) {
    super(context);
  }

  public synchronized Restlet createInboundRoot() {
    Router router = new Router(getContext());

    // *** MCCs ***
    router.attach("/mcc", MccRest.class); //LIST All


    // *** Accounts ***
    router.attach("/mcc/{topAccountId}/accounts", AccountRest.class); //LIST All

    
    // *** Kratu ***
    // ?includeZeroImpressions=false by default
    router.attach("/mcc/{topAccountId}/kratu", KratuRest.class); // List All
    router.attach("/mcc/{topAccountId}/kratu/{accountId}", KratuRest.class); // LIST Account level

    // Genereate Kratus MCC level
    // ?dateStart=yyyyMMdd&dateEnd=yyyyMMdd
    router.attach("/mcc/{topAccountId}/generatekratus", GenerateKratusRest.class);

    
    // *** Reporting ***
    // Generate
    // ?dateStart=yyyyMMdd&dateEnd=yyyyMMdd
    router.attach("/mcc/{topAccountId}/generatereports", GenerateReportsRest.class);

    // Accounts
    // ?dateStart=yyyyMMdd&dateEnd=yyyyMMdd
    router.attach("/mcc/{topAccountId}/reportaccount", ReportAccountRest.class); //LIST All
    router.attach("/mcc/{topAccountId}/reportaccount/{accountId}", ReportAccountRest.class); //LIST Account level
    
    // Campaigns
    // ?dateStart=yyyyMMdd&dateEnd=yyyyMMdd
    router.attach("/mcc/{topAccountId}/reportcampaign", ReportCampaignRest.class); //LIST All
    router.attach("/mcc/{topAccountId}/reportcampaign/{accountId}", ReportCampaignRest.class); //LIST Account level
    router.attach("/mcc/{topAccountId}/reportcampaign/campaign/{campaignId}", ReportCampaignRest.class); //LIST Campaign level

    // AdGroups
    // ?dateStart=yyyyMMdd&dateEnd=yyyyMMdd
    router.attach("/mcc/{topAccountId}/reportadgroup", ReportAdGroupRest.class); //LIST All
    router.attach("/mcc/{topAccountId}/reportadgroup/{accountId}", ReportAdGroupRest.class); //LIST Account level
    router.attach("/mcc/{topAccountId}/reportadgroup/campaign/{campaignId}", ReportAdGroupRest.class); //LIST Campaign level
    router.attach("/mcc/{topAccountId}/reportadgroup/adgroup/{adGroupId}", ReportAdGroupRest.class); //LIST AdGroup level

    // Ads
    // ?dateStart=yyyyMMdd&dateEnd=yyyyMMdd
    router.attach("/mcc/{topAccountId}/reportad", ReportAdRest.class); //LIST All
    router.attach("/mcc/{topAccountId}/reportad/{accountId}", ReportAdRest.class); //LIST Account level
    router.attach("/mcc/{topAccountId}/reportad/campaign/{campaignId}", ReportAdRest.class); //LIST Campaign level
    router.attach("/mcc/{topAccountId}/reportad/adgroup/{adGroupId}", ReportAdRest.class); //LIST AdGroup level
    router.attach("/mcc/{topAccountId}/reportad/ad/{adId}", ReportAdRest.class); //LIST Ad level

    // Keywords
    // ?dateStart=yyyyMMdd&dateEnd=yyyyMMdd
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
    router.attach("/mcc/{topAccountId}/reportadextension", ReportAdExtensionRest.class); //LIST All
    router.attach("/mcc/{topAccountId}/reportadextension/{accountId}", ReportAdExtensionRest.class); //LIST Account level
    router.attach("/mcc/{topAccountId}/reportadextension/campaign/{campaignId}", ReportAdExtensionRest.class); //LIST Campaign level
    router.attach("/mcc/{topAccountId}/reportadextension/adextension/{adExtensionId}", ReportAdExtensionRest.class); //LIST Keyword level


    router.attach("/html2pdf", HtmlToPdfRest.class); 


    // *** Static files *** 
    // USING FILE
    String target = "index.html";
    Redirector redirector = new Redirector(getContext(), target, Redirector.MODE_CLIENT_FOUND);
    router.attach("/", redirector);
    File currentPath = new File(RestServer.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    String htmlPath = "file:///" + currentPath.getParent() + "/html/";
    router.attach("/", redirector);
    router.attach("", new Directory(getContext(), htmlPath));

    return router;
  }
}