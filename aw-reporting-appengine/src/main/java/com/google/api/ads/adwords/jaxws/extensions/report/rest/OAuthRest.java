//Copyright 2012 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.jaxws.extensions.report.rest;

import com.google.api.adwords.awreporting.util.Authentication;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;

/**
 * 
 * @author jtoledo
 * 
 */
public class OAuthRest extends AbstractServerResource {

  public JsonRepresentation getHandler() {
    String result = null;
    try {
      getParameters();

      if (topAccountId != null && state == null) {
        // Redirect the user to OAuth
        String url = Authentication.getOAuth2Credential(topAccountId);
        this.setStatus(Status.REDIRECTION_TEMPORARY);
        this.setLocationRef(url);
        result = "";
      }

      if (code != null && code.length() > 0 && state != null && state.length() > 0) {
        
        String topAccountId = "";
        String returnUrl = "/";
        // State contains TopAccountId and Return URL without any separator
        if (state.indexOf("http") < 0) {
          topAccountId = state;
        } else {
          topAccountId = state.substring(0, state.indexOf("http"));
          returnUrl = state.substring(state.indexOf("http"));
        }
        
        Authentication.processOAuth2Credential(code, topAccountId).getAccessToken();
        this.setStatus(Status.REDIRECTION_FOUND);
        this.setLocationRef(returnUrl);
        result = "";
      }
      
      if (other != null && other.equals("login")) {
        UserService userService = UserServiceFactory.getUserService();
        String url = userService.createLoginURL("/");
        this.setStatus(Status.REDIRECTION_FOUND);
        this.setLocationRef(url);
        result = "";
      }
      
      if (other != null && other.equals("logout")) {
        UserService userService = UserServiceFactory.getUserService();
        String url = userService.createLogoutURL("/");
        this.setStatus(Status.REDIRECTION_FOUND);
        this.setLocationRef(url);
        result = "";
      }
      
      if (other != null && other.equals("user")) {
        UserService userService = UserServiceFactory.getUserService();
        result = gson.toJson(userService.getCurrentUser());
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