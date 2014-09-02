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

import com.google.api.ads.adwords.jaxws.extensions.kratu.data.Account;
import org.restlet.data.Status;
import org.restlet.representation.Representation;

import java.util.List;

/**
 * AccountRest
 * 
 * @author jtoledo@google.com (Julian Toledo)
 */
public class AccountRest extends AbstractServerResource {
	
  public Representation getHandler() {
    String result = null;
    try {
      getParameters();

      if (topAccountId != null ) {
        List<Account> listAccounts = getStorageHelper().getEntityPersister().get(
            Account.class, "topAccountId", topAccountId);
        result =  gson.toJson(listAccounts);
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
