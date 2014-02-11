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

import com.google.api.ads.adwords.jaxws.extensions.appengine.RestServer;
import com.google.api.ads.adwords.jaxws.extensions.appengine.model.UserToken;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.CharacterRepresentation;

import java.util.List;

public class MyMCCs extends AbstractServerResource {

  public CharacterRepresentation getHandler() {
    String result = null;
    try {
      getParameters();

      UserService userService = UserServiceFactory.getUserService();
      if (userService != null && userService.getCurrentUser() != null) {
        
        List<UserToken> userTokenList = RestServer.persister.get(UserToken.class, "userId", userService.getCurrentUser().getUserId());
        
        result = gson.toJson(userTokenList);
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