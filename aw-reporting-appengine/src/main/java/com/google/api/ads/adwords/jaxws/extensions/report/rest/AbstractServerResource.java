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
import com.google.api.ads.adwords.jaxws.extensions.appengine.model.Account;
import com.google.api.ads.adwords.jaxws.extensions.appengine.model.UserToken;
import com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.common.collect.Maps;
import com.google.gson.Gson;

import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.engine.header.HeaderConstants;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.CharacterRepresentation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Options;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public abstract class AbstractServerResource extends ServerResource {

  protected static final Logger log = Logger.getLogger(AbstractServerResource.class.getName());

  protected static final String DATE_FORMAT_REPORT = "yyyyMMdd";

  public static final DateFormat dfReport = new SimpleDateFormat(DATE_FORMAT_REPORT);

  protected static final Gson gson = new Gson();

  // HTTP Parameters
  protected boolean live = false;
  
  protected boolean task = false;

  protected Long partnerId = null;

  protected Long topAccountId = null;

  protected Long accountId = null;

  protected Long campaignId = null;

  protected Long adGroupId = null;

  protected Long adId = null;

  protected Long criterionId = null;

  protected String campaignIdcriterionIdString = null;

  protected String dateStart = null;

  protected String dateEnd = null;

  protected String dateRangeType = null;
  
  protected String month = null;

  protected String authToken = null;

  protected String developerToken = null;

  protected String reportDefinition = null;

  protected String reportClassName = null;

  protected String state = null;

  protected String code = null;

  protected String userId = null;
  
  protected String other = null;
  
  protected boolean includeZeroImpressions = false;

  @Get
  abstract public CharacterRepresentation getHandler();

  @Delete
  abstract public void deleteHandler();

  @Post
  @Put
  abstract public CharacterRepresentation postPutHandler(String json);

  @Options
  public void optionsHandler() {
    addHeaders();
  }

  protected void getParameters() {
    String partnerIdString = (String) getRequestAttributes().get("partnerId");
    String topAccountIdString = (String) getRequestAttributes().get("topAccountId");
    String accountIdString = (String) getRequestAttributes().get("accountId");
    String campaignIdString = (String) getRequestAttributes().get("campaignId");
    String adGroupIdString = (String) getRequestAttributes().get("adGroupId");
    String adIdString = (String) getRequestAttributes().get("adId");
    String criterionIdString = (String) getRequestAttributes().get("criterionId");
    campaignIdcriterionIdString = (String) getRequestAttributes().get("campaignId-criterionId");
    other = (String) getRequestAttributes().get("other");

    String liveString = this.getReference().getQueryAsForm().getFirstValue("live");
    String taskString = this.getReference().getQueryAsForm().getFirstValue("task");
    String includeZeroImpressionsString = this.getReference().getQueryAsForm().getFirstValue("includeZeroImpressions");
    dateStart = this.getReference().getQueryAsForm().getFirstValue("dateStart");
    dateEnd = this.getReference().getQueryAsForm().getFirstValue("dateEnd");
    dateRangeType = this.getReference().getQueryAsForm().getFirstValue("dateRangeType");
    month = this.getReference().getQueryAsForm().getFirstValue("month");
    authToken = this.getReference().getQueryAsForm().getFirstValue("authToken");
    developerToken = this.getReference().getQueryAsForm().getFirstValue("developerToken");
    reportDefinition = this.getReference().getQueryAsForm().getFirstValue("reportDefinition");
    reportClassName = this.getReference().getQueryAsForm().getFirstValue("reportClassName");

    live = (liveString != null && liveString.equals("true"));
    task = (taskString != null && taskString.equals("true"));
    includeZeroImpressions = (includeZeroImpressionsString != null && includeZeroImpressionsString.equals("true"));
    partnerId = partnerIdString == null ? null : Long.parseLong(partnerIdString);
    topAccountId = topAccountIdString == null ? null : Long.parseLong(topAccountIdString.replaceAll("-", ""));
    accountId = accountIdString == null ? null : Long.parseLong(accountIdString);
    campaignId = campaignIdString == null ? null : Long.parseLong(campaignIdString);
    adGroupId = adGroupIdString == null ? null : Long.parseLong(adGroupIdString);
    adId = adIdString == null ? null : Long.parseLong(adIdString);
    criterionId = criterionIdString == null ? null : Long.parseLong(criterionIdString);

    state = this.getReference().getQueryAsForm().getFirstValue("state");
    code = this.getReference().getQueryAsForm().getFirstValue("code");

    currentUser();
  }
  
  protected String currentUser() {
    userId = this.getReference().getQueryAsForm().getFirstValue("userId");
    // Get user id from UserService if it is not included as parameter
    if (userId == null || userId.length() == 0) {
      UserService userService = UserServiceFactory.getUserService();
      if (userService != null && userService.getCurrentUser() != null)
        userId = userService.getCurrentUser().getUserId();
    }
    return userId;
  }

  @SuppressWarnings("unchecked")
  private Series<Header> getHeaders() {
    Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get(HeaderConstants.ATTRIBUTE_HEADERS);
    if (responseHeaders == null) {
      responseHeaders = new Series<Header>(Header.class);
      getResponse().getAttributes().put(HeaderConstants.ATTRIBUTE_HEADERS, responseHeaders); 
    }
    return responseHeaders;
  }

  protected void addHeaders() {
    Series<Header> responseHeaders = getHeaders();
    responseHeaders.add("Access-Control-Allow-Origin", "*");
    responseHeaders.add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
  }

  protected void addReadOnlyHeaders() {
    Series<Header> responseHeaders = getHeaders();
    responseHeaders.add("Access-Control-Allow-Origin", "*");
    responseHeaders.add("Access-Control-Allow-Methods", "GET");
  }

  protected JsonRepresentation createJsonResult(String result) {
    if (result == null) {
      this.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
      result = Status.CLIENT_ERROR_NOT_FOUND.getDescription();
    }
    JsonRepresentation jsonRepresentation = new org.restlet.ext.json.JsonRepresentation(result);
    jsonRepresentation.setMediaType(MediaType.APPLICATION_JSON);
    return jsonRepresentation;
  }

  protected StringRepresentation createHtmlResult(String result) {
    if (result == null) {
      this.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
      result = Status.CLIENT_ERROR_NOT_FOUND.getDescription();
    }
    StringRepresentation stringRepresentation = new StringRepresentation(result);
    stringRepresentation.setMediaType(MediaType.TEXT_HTML);

    return stringRepresentation;
  }

  protected String stackTraceToString(Throwable e) {
    StringBuilder sb = new StringBuilder();
    for (StackTraceElement element : e.getStackTrace()) {
      sb.append(element.toString());
      sb.append("\n");
    }
    return sb.toString();
  }
  
  protected String handleException(Exception exception) {
    String result;
    
    log.severe(stackTraceToString(exception));
    if (exception.getMessage() != null && exception.getMessage().length() > 0)
      result = exception.getMessage() + "\n\nStackTrace: " + stackTraceToString(exception);
    else
      result = stackTraceToString(exception);
    exception.printStackTrace();
    this.setStatus(Status.SERVER_ERROR_INTERNAL);
    
    return result;
  }

  protected boolean checkAuthentication() throws Exception {
    
    EntityPersister persister = RestServer.appCtx.getBean(EntityPersister.class);
    
    if (topAccountId != null) {

      Map<String,Object> map = Maps.newHashMap();
      map.put("userId", userId);
      map.put("topAccountId", topAccountId);
      List<UserToken> userTokenList = persister.get(UserToken.class, map);
      if ( userTokenList.size() > 0 )
        return true;
    } else if (accountId != null) {
      
      List<Account> accounts = persister.get(Account.class, "accountId", accountId);
      
      if (accounts.size() == 1) {
        Account account = accounts.get(0);
        if ( account != null && account.getTopAccountId() != null) {
          
          Map<String,Object> map = Maps.newHashMap();
          map.put("userId", currentUser());
          map.put("topAccountId", account.getTopAccountId());
          List<UserToken> userTokenList = persister.get(UserToken.class, map);
          
          if ( userTokenList.size() > 0 )
            return true;
        }
      }
    }
    throw new Exception("FORBIDDEN");
  }
}