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

package com.google.api.ads.adwords.jaxws.extensions.report.rest;

import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;

/**
 * 
 * @author jtoledo
 * 
 */
public class AccountRest extends AbstractServerResource {

  public JsonRepresentation getHandler() {
    String result = null;
    try {
      getParameters();
      checkAuthentication();

      if (topAccountId != null) { // LIST Top Account level
        //result = gson.toJson(Account.getAccounts(topAccountId, userId));
      }
      
      if (accountId != null) { // LIST Account level
        //result = gson.toJson(Account.getAccount(accountId));
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