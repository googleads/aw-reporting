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

import com.google.api.ads.adwords.jaxws.extensions.appengine.RestServer;
import com.google.api.ads.adwords.jaxws.extensions.processors.ReportProcessor;
import com.google.api.ads.adwords.jaxws.extensions.processors.ReportProcessorOnMemory;
import com.google.api.ads.adwords.jaxws.extensions.report.rest.AbstractServerResource;
import com.google.api.ads.adwords.lib.jaxb.v201309.ReportDefinitionDateRangeType;

import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;

import java.util.Properties;

/**
 * 
 * @author jtoledo
 * 
 */
public class GenerateReportsRest extends AbstractServerResource {
  
  public JsonRepresentation getHandler() {
    String result = null;
    try {
      getParameters();

      if (topAccountId != null) { // Generate Report Task for MCC
        log.info(" Generate Report Task for MCC for " + topAccountId);
        System.out.println(" Generate Report Task for MCC for " + topAccountId);

        String dateStart = "20131201";
        String dateEnd = "20131231";    
        Properties properties = RestServer.properties;

        ReportProcessorOnMemory processor = createReportProcessor();
        try {
          processor.generateReportsForMCC(ReportDefinitionDateRangeType.CUSTOM_DATE, dateStart,
              dateEnd, null, properties);
        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        
        result = "OK";
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
  
  /**
   * Creates the {@link ReportProcessor} autowiring all the dependencies.
   *
   * @return the {@code ReportProcessor} with all the dependencies properly injected.
   */
  private static ReportProcessorOnMemory createReportProcessor() {

    return RestServer.appCtx.getBean(ReportProcessorOnMemory.class);
  }
}