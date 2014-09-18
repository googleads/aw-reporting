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

package com.google.api.ads.adwords.awreporting.kratubackend.restserver;

import com.google.api.ads.adwords.awreporting.kratubackend.util.KratuStorageHelper;
import com.google.api.ads.adwords.awreporting.server.rest.RestServer;

import org.restlet.resource.Directory;
import org.restlet.routing.Redirector;
import org.restlet.routing.Router;

import java.io.File;
import java.io.IOException;

/**
 * RestServer
 * 
 * @author jtoledo@google.com (Julian Toledo)
 */
public class KratuRestServer extends RestServer {

  private static KratuStorageHelper kratuStorageHelper;

  public static KratuStorageHelper getKratuStorageHelper() {
    if (kratuStorageHelper == null || getApplicationContext() == null) {
      synchronized (RestServer.class) {
        if (kratuStorageHelper == null || getApplicationContext() == null) {
          kratuStorageHelper = getApplicationContext().getBean(KratuStorageHelper.class);
        }
      }
    }
    return kratuStorageHelper;
  }

  public KratuRestServer() throws IOException {
    super();
  }

  public void startServer() throws Exception {
    startServer(this);
  }

  public synchronized Router createInboundRoot() {
    // Get default routes from AwReportingServer's RestServer
    Router router = super.createInboundRoot();

    // *** MCCs ***
    router.attach("/mcc", MccRest.class); //LIST All

    // *** Kratu ***
    // ?includeZeroImpressions=false by default
    router.attach("/mcc/{topAccountId}/kratu", KratuRest.class); // List All
    router.attach("/mcc/{topAccountId}/kratu/{accountId}", KratuRest.class); // LIST Account level

    // Genereate Kratus MCC level
    // ?dateStart=yyyyMMdd&dateEnd=yyyyMMdd
    router.attach("/mcc/{topAccountId}/generatekratus", GenerateKratusRest.class);

    // *** Static files *** 
    // USING FILE
    String target = "index.html";
    Redirector redirector = new Redirector(getContext(), target, Redirector.MODE_CLIENT_FOUND);
    router.attach("/", redirector);
    File currentPath = new File(KratuRestServer.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    String htmlPath = "file:///" + currentPath.getParent() + "/html/";
    router.attach("/", redirector);
    router.attach("", new Directory(getContext(), htmlPath));

    return router;
  }
}
