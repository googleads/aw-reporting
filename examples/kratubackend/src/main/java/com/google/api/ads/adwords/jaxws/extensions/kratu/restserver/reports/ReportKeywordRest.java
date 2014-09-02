//Copyright 2011 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.jaxws.extensions.kratu.restserver.reports;

import com.google.api.ads.adwords.jaxws.extensions.kratu.restserver.AbstractServerResource;
import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.ReportKeyword;

import org.restlet.data.Status;
import org.restlet.representation.Representation;

import java.util.List;

/**
 * ReportKeywordRest
 * 
 * @author jtoledo@google.com (Julian Toledo)
 */
public class ReportKeywordRest extends AbstractServerResource {

  public Representation getHandler() {
    String result = null;
    try {
      getParameters();
      
      if (criterionId != null) { //LIST AdGroup level
        List<ReportKeyword> listReportAccount = getStorageHelper().getReportKeywordByKeywordId(criterionId, dateStart, dateEnd);
        if (listReportAccount != null)
          result = gson.toJson(listReportAccount);
      } else if (adGroupId != null) { //LIST AdGroup level
        List<ReportKeyword> listReportAccount = getStorageHelper().getReportKeywordByAdGroupId(adGroupId, dateStart, dateEnd);
        if (listReportAccount != null)
          result = gson.toJson(listReportAccount);
      } else if (campaignId != null) { //LIST Campaign level
        List<ReportKeyword> listReportAccount = getStorageHelper().getReportKeywordByCampaignId(campaignId, dateStart, dateEnd);
        if (listReportAccount != null)
          result = gson.toJson(listReportAccount);
      } else if (accountId != null) { //LIST Account level
        List<ReportKeyword> listReportAccount = getStorageHelper().getReportByAccountId(ReportKeyword.class, accountId, dateStart, dateEnd);
        if (listReportAccount != null)
          result = gson.toJson(listReportAccount);
      } else { //LIST All
        List<ReportKeyword> listReportAccount = getStorageHelper().getReportByDates(ReportKeyword.class, dateStart, dateEnd);
        if (listReportAccount != null)
          result = gson.toJson(listReportAccount);
      } 
    } catch (Exception exception) {
      return handleException(exception);
    }
    addReadOnlyHeaders();
    return createJsonResult(result);
  }

  public void deleteHandler() {
    this.setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
  }

  public Representation postPutHandler(String json) {
    this.setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
    return createJsonResult(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED.getDescription());
  }
}