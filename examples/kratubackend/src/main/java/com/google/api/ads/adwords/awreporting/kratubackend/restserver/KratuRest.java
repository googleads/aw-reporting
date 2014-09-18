//Copyright 2011 Google Inc. All Rights Reserved.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.

package com.google.api.ads.adwords.awreporting.kratubackend.restserver;

import com.google.api.ads.adwords.awreporting.server.rest.AbstractServerResource;

import org.restlet.representation.Representation;

/**
 * KratuRest
 * 
 * @author jtoledo@google.com (Julian Toledo)
 */
public class KratuRest extends AbstractServerResource {

  public Representation getHandler() {
    String result = null;
    try {
      getParameters();

      // Retrieve All Kratus for the MCC between the two dates.
      if (topAccountId != null && dateStart != null && dateEnd != null) {
        result = gson.toJson(KratuRestServer.getKratuStorageHelper().getKratus(topAccountId, dateStart, dateEnd));
      }

      // Retrieve All Kratus for a single account.
      if (accountId != null) {
        result = gson.toJson(KratuRestServer.getKratuStorageHelper().getKratus(accountId));
      }

    } catch (Exception exception) {
      return handleException(exception);
    }
    addReadOnlyHeaders();
    return createJsonResult(result);
  }
}
