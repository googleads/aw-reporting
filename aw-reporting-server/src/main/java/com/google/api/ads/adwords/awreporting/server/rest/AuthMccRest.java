// Copyright 2014 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.awreporting.server.rest;

import com.google.api.ads.adwords.awreporting.model.entities.auth.AuthMcc;
import com.google.gson.Gson;

import org.restlet.representation.Representation;

import java.util.List;

/**
 * MccRest
 * 
 * @author jtoledo@google.com (Julian Toledo)
 */
public class AuthMccRest extends AbstractBaseResource {

  public Representation getHandler() {
    String result = null;

    try {
      RestServer.getWebAuthenticator().checkAuthentication();

      List<AuthMcc> listAuthMcc = RestServer.getStorageHelper().getEntityPersister()
          .get(AuthMcc.class);

      result = gson.toJson(listAuthMcc);

    } catch (Exception exception) {
      return handleException(exception);
    }
    addReadOnlyHeaders();
    return createJsonResult(result);
  }

  public Representation deleteHandler() {
    String result = null;

    try {
      RestServer.getWebAuthenticator().checkAuthentication();

      Long topAccountId = getParameterAsLong("topAccountId");
      if (topAccountId != null) {

        // Delete template by ID
        List<AuthMcc> authMccs = 
            RestServer.getPersister().get(AuthMcc.class, AuthMcc.TOP_ACCOUNT_ID, topAccountId);
        RestServer.getPersister().remove(authMccs);

        result = "OK";

      } else {
        throw new IllegalArgumentException("Missing topAccountId or problem with the authenticated user");
      }

    } catch (Exception exception) {
      return handleException(exception);
    }
    addHeaders();
    return createJsonResult(result);
  }

  public Representation postPutHandler(String json) {
    String result = null;
    try {

      RestServer.getWebAuthenticator().checkAuthentication();

      AuthMcc authMcc = new Gson().fromJson(json, AuthMcc.class);
      // Set the userId on the template and save it
      LOGGER.info("Persisting authMcc...");
      AuthMcc savedAuthMcc = RestServer.getPersister().save(authMcc);

      result = gson.toJson(savedAuthMcc);

    } catch (Exception exception) {
      return handleException(exception);
    }
    addHeaders();    
    return createJsonResult(result);
  }
}
