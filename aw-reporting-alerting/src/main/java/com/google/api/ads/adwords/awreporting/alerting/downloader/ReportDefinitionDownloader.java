// Copyright 2015 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.awreporting.alerting.downloader;

import com.google.api.ads.adwords.jaxws.factory.AdWordsServices;
import com.google.api.ads.adwords.jaxws.v201502.cm.ApiException_Exception;
import com.google.api.ads.adwords.jaxws.v201502.cm.ReportDefinitionField;
import com.google.api.ads.adwords.jaxws.v201502.cm.ReportDefinitionReportType;
import com.google.api.ads.adwords.jaxws.v201502.cm.ReportDefinitionServiceInterface;
import com.google.api.ads.adwords.lib.client.AdWordsSession;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to download report definition and generate (displayFiledName -> filedName) mapping
 * for each required report type.
 *
 * @author zhuoc@google.com (Zhuo Chen)
 */
public class ReportDefinitionDownloader {

  private static final Logger LOGGER = Logger.getLogger(ReportDefinitionDownloader.class);

  private static final int RETRIES_COUNT = 5;
  private static final int BACKOFF_INTERVAL = 1000 * 5;

  private int retriesCount = RETRIES_COUNT;
  private int backoffInterval = BACKOFF_INTERVAL;
  
  private ReportDefinitionServiceInterface reportDefinitionService;
  
  private final Map<ReportDefinitionReportType, Map<String, String>> reportFieldsMappings = 
      new HashMap<ReportDefinitionReportType, Map<String, String>>();
  
  
  /**
   * Initialize this downloader with specified adwords session
   * @param session the adwords session
   */
  public void initialize(AdWordsSession session) {
    reportDefinitionService = new AdWordsServices().get(session, ReportDefinitionServiceInterface.class);
  }
  
  /**
   * Get (displayFiledName -> filedName) mapping for the specified report type.
   * @param reportDefinitionService the report definition service interface
   * @param reportType the specified report type
   * @throws ApiException_Exception 
   */
  public Map<String, String> getFieldsMapping(ReportDefinitionReportType reportType)
      throws ApiException_Exception {
    Map<String, String> fieldsMapping = reportFieldsMappings.get(reportType);
    if (null == fieldsMapping) {
      fieldsMapping = generateFieldsMapping(reportType);
      reportFieldsMappings.put(reportType, fieldsMapping);
    }
    
    return fieldsMapping;
  }
  
  /**
   * For the specified report type, Download fields from ReportDefinitionService and
   * generate (displayFiledName -> filedName) mapping.
   * @param reportDefinitionService the report definition service interface
   * @param reportType the specified report type
   * @throws ApiException_Exception 
   */
  public Map<String, String> generateFieldsMapping(ReportDefinitionReportType reportType)
      throws ApiException_Exception {
    List<ReportDefinitionField> reportDefinitionFields = null;
    for (int i = 1; i <= this.retriesCount; ++i) {
      try {
        reportDefinitionFields = reportDefinitionService.getReportFields(reportType);
        break;
      }
      catch (ApiException_Exception e) {
        System.out.println("(Error getting report definition: " + e.getMessage() + " " + e.getCause()
            + " Retry# " + i + "/" + retriesCount + ")");
        
        if (i == this.retriesCount) {
          // failed the last retry, just rethrow
          throw e;
        }
      }
      
      // Slow down the rate of requests increasingly to avoid running into rate limits.
      try {
        Thread.sleep(this.backoffInterval * (i + 1));
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        break;
      }
    }
    
    LOGGER.info("Successfully downloaded report definition for " + reportType.value());
    Map<String, String>fieldsMapping = new HashMap<String, String>(reportDefinitionFields.size());
    for (ReportDefinitionField field : reportDefinitionFields) {
      fieldsMapping.put(field.getDisplayFieldName(), field.getFieldName());
    }
    return fieldsMapping;
  }
  
  /**
   * @param retriesCount the retriesCount to set. Default value = 5
   */
  public void setRetriesCount(int retriesCount) {
    this.retriesCount = retriesCount;
  }
  
  /**
   * @param backoffInterval the backoffInterval to set. Default value = 1000 * 5
   */
  public void setBackoffInterval(int backoffInterval) {
    this.backoffInterval = backoffInterval;
  }
}
