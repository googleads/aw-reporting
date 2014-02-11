//Copyright 2012 Google Inc. All Rights Reserved.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.

package com.google.api.ads.adwords.jaxws.extensions.report.rest.reporting;


import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;

import com.google.api.ads.adwords.jaxws.extensions.report.rest.AbstractServerResource;

/**
 * 
 * @author jtoledo
 * 
 */
public class TaskReportsRest extends AbstractServerResource {

  public JsonRepresentation getHandler() {
    String result = null;
    try {
      getParameters();

      // Process Report Task MCC level
      if (topAccountId != null && accountId == null) {
        log.info(" Reports for " + topAccountId);
        System.out.println(" Reports for " + topAccountId);

        //Reporting reporting = new Reporting(String.valueOf(topAccountId), userId);
        //reporting.generateReportsForMCC(month, null);

        result = "";
      }

      // Process Report Task Account level
      if (topAccountId != null && accountId != null) {
        
        /*
        AdWordsSession.Builder builder = Authentication.getAdWordsSessionBuilder(
            String.valueOf(topAccountId), String.valueOf(accountId), userId);
        @SuppressWarnings("unchecked")
        Class<? extends Report> reportClass = (Class<? extends Report>) Class
        .forName(reportClassName);
        Report report = reportClass.newInstance();
        log.info(" Downloading report for " + accountId);
        ClientReportDownloader.runReportForAccount(topAccountId, builder, reportDefinition,
            accountId, month, report);
            */
        result = "";
      }

    } catch (Exception exception) {
      result = handleException(exception);
    }
    addReadOnlyHeaders();
    return createJsonResult(result);
  }

  public void deleteHandler() {
    this.setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
  }

  public JsonRepresentation postPutHandler(String json) {
    this.setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
    return createJsonResult(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED.getDescription());
  }
}