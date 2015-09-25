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

package com.google.api.ads.adwords.awreporting.util;

import com.google.api.ads.adwords.jaxws.factory.AdWordsServices;
import com.google.api.ads.adwords.jaxws.v201506.cm.ApiException_Exception;
import com.google.api.ads.adwords.jaxws.v201506.cm.ReportDefinitionField;
import com.google.api.ads.adwords.jaxws.v201506.cm.ReportDefinitionReportType;
import com.google.api.ads.adwords.jaxws.v201506.cm.ReportDefinitionServiceInterface;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.common.collect.Lists;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Set;

/**
 * {@code ReportDefinitionDelegate} encapsulates the {@code ReportDefinitionServiceInterface}.
 *
 * This service is used to get all the report fields what are segmented.
 *
 */
public class ReportDefinitionDelegate {

  private static final Logger LOGGER = Logger.getLogger(ReportDefinitionDelegate.class);
  
  private static final String SEGMENT = "SEGMENT";

  private ReportDefinitionServiceInterface ReportDefinitionService;

  /**
   * Default Constructor.
   *
   * @param adWordsSession the {@code adWordsSession} to use with the delegate/service
   */
  public ReportDefinitionDelegate(AdWordsSession adWordsSession) {
    this.ReportDefinitionService =
        new AdWordsServices().get(adWordsSession, ReportDefinitionServiceInterface.class);
  }

  /**
   * Constructor with a ReportDefinitionServiceInterface.
   *
   * @param ReportDefinitionService the report definition service available for the session
   */
  public ReportDefinitionDelegate(ReportDefinitionServiceInterface ReportDefinitionService) {
    this.ReportDefinitionService = ReportDefinitionService;
  }

  /**
   * Gets fields that are explicitly segmented {@link AdWordsSession}.
   *
   * The report fields are read in complete.  Their field behavior is
   * extracted and field name is returned in a {@code Set} if the
   * behavior is "SEGMENT".
   *
   * @return the {@link Set} with field name of the segmented report fields
   * @throws Exception error from the API when retrieving the fields
   */
  public List<String> getSegmentedReportFields(String reportType) 
      throws Exception {
    List<String> segmentedReportFieldsList = Lists.newArrayList();
    ReportDefinitionReportType reportDefinitionType = ReportDefinitionReportType.valueOf(reportType);
    List<ReportDefinitionField> reportFieldsList = this.retrieveReportFields(reportDefinitionType);

    if (reportFieldsList != null) {
      for (ReportDefinitionField reportField : reportFieldsList) {
        if (SEGMENT.equalsIgnoreCase(reportField.getFieldBehavior())) {
          segmentedReportFieldsList.add(reportField.getFieldName());
        }
      }
    }
    return segmentedReportFieldsList;
  }

  /**
   * Retrieve all the report fields
   *
   * @param reportType to retrieve ReportDefinitionFields
   * @throws Exception error from the API when retrieving the fields
   */
  private List<ReportDefinitionField> retrieveReportFields(ReportDefinitionReportType reportType)
      throws Exception {
    List<ReportDefinitionField> reportFieldsList = Lists.newArrayList();

    try {
      reportFieldsList = ReportDefinitionService.getReportFields(reportType);
    } catch (ApiException_Exception e) {
      // Retry Once
      reportFieldsList = ReportDefinitionService.getReportFields(reportType);
    }
    
    LOGGER.info(reportFieldsList.size() + " report fields retrieved.");
    return reportFieldsList;
  }

}
