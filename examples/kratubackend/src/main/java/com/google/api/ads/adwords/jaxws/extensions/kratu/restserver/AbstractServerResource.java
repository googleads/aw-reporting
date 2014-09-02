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

import com.google.api.ads.adwords.jaxws.extensions.kratu.data.StorageHelper;
import com.google.api.ads.adwords.jaxws.extensions.report.model.util.DateUtil;
import com.google.api.ads.adwords.jaxws.extensions.report.model.util.GsonUtil;
import com.google.api.client.util.Maps;
import com.google.gson.Gson;

import org.apache.log4j.Logger;
import org.restlet.Message;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.BufferingRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Options;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;
import org.springframework.context.ApplicationContext;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * AbstractServerResource
 * 
 * @author jtoledo@google.com (Julian Toledo)
 */
public abstract class AbstractServerResource extends ServerResource {

  private static final Logger LOGGER = Logger.getLogger(AbstractServerResource.class.getName());

  private static final String HEADERS_KEY = "org.restlet.http.headers";

  protected static final Gson gson = GsonUtil.getGsonBuilder().create();
  
  protected String file = "";
  
  private ApplicationContext appCtx;
  private StorageHelper storageHelper;
  
  // HTTP Parameters
  protected boolean live = false;
  protected Long partnerId = null;
  protected Long topAccountId = null;
  protected Long accountId = null;
  protected Long campaignId = null;
  protected Long adGroupId = null;
  protected Long adId = null;
  protected Long criterionId = null;
  protected Long adExtensionId = null;
  protected String campaignIdcriterionIdString = null;
  protected Date dateStart = null;
  protected Date dateEnd = null;
  protected String dateRangeType = null;
  protected boolean includeZeroImpressions = false;

  @Get
  abstract public Representation getHandler();

  @Delete
  abstract public void deleteHandler();

  @Post
  @Put
  abstract public Representation postPutHandler(String json);

  @Options
  public void optionsHandler() {
    addHeaders();
  }

  protected void getParameters() {
    
    try {
      file = (String)getContext().getAttributes().get("file");
  
      String liveString = this.getReference().getQueryAsForm().getFirstValue("live");
      String partnerIdString = (String)getRequestAttributes().get("partnerId");
      String topAccountIdString = (String)getRequestAttributes().get("topAccountId");
      String accountIdString = (String)getRequestAttributes().get("accountId");
      String campaignIdString = (String)getRequestAttributes().get("campaignId");
      String adGroupIdString = (String)getRequestAttributes().get("adGroupId");
      String adIdString = (String)getRequestAttributes().get("adId");
      String criterionIdString = (String)getRequestAttributes().get("criterionId");
      String adExtensionIdString = (String)getRequestAttributes().get("adExtensionId");
      campaignIdcriterionIdString = (String)getRequestAttributes().get("campaignId-criterionId");
  
      String dateStartString = this.getReference().getQueryAsForm().getFirstValue("dateStart");
      String dateEndString = this.getReference().getQueryAsForm().getFirstValue("dateEnd");
  
      dateRangeType = this.getReference().getQueryAsForm().getFirstValue("dateRangeType");
      String includeZeroImpressionsString = this.getReference().getQueryAsForm().getFirstValue("includeZeroImpressions");
  
      live = (liveString != null && liveString.equals("true"));
      partnerId = partnerIdString == null ? null : Long.parseLong(partnerIdString);
      topAccountId = topAccountIdString == null ? null : Long.parseLong(topAccountIdString);
      accountId = accountIdString == null ? null : Long.parseLong(accountIdString);
      campaignId = campaignIdString == null ? null : Long.parseLong(campaignIdString);
      adGroupId = adGroupIdString == null ? null : Long.parseLong(adGroupIdString);
      adId = adIdString == null ? null : Long.parseLong(adIdString);
      criterionId = criterionIdString == null ? null : Long.parseLong(criterionIdString);
      adExtensionId = adExtensionIdString == null ? null : Long.parseLong(adExtensionIdString);
      includeZeroImpressions = (includeZeroImpressionsString != null && includeZeroImpressionsString.equals("true"));
      
      if (dateStartString != null && dateEndString != null) {
        dateStart = DateUtil.parseDateTime(dateStartString).toDate();
        dateEnd = DateUtil.parseDateTime(dateEndString).toDate();
      }
  
      criterionId = criterionIdString == null ? null : Long.parseLong(criterionIdString);
    } catch(Exception exception) {
      throw new IllegalArgumentException(exception);
    }
  }

  protected void addHeaders() {
    getMessageHeaders(getResponse()).add("Access-Control-Allow-Origin", "*");
    getMessageHeaders(getResponse()).add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");

    getMessageHeaders(getResponse()).add("Cache-Control", "no-cache, no-store, must-revalidate");
    getMessageHeaders(getResponse()).add("Pragma", "no-cache"); 
    getMessageHeaders(getResponse()).add("Expires", "0");
  }

  protected void addReadOnlyHeaders() {
    getMessageHeaders(getResponse()).add("Access-Control-Allow-Origin", "*");
    getMessageHeaders(getResponse()).add("Access-Control-Allow-Methods", "GET");
    
    getMessageHeaders(getResponse()).add("Cache-Control", "no-cache, no-store, must-revalidate");
    getMessageHeaders(getResponse()).add("Pragma", "no-cache"); 
    getMessageHeaders(getResponse()).add("Expires", "0");
  }

  protected Representation createJsonResult(String result) {
    this.setAutoCommitting(true);

    if (result == null) {
      this.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
      result = Status.CLIENT_ERROR_NOT_FOUND.getDescription();
    }
    JsonRepresentation jsonRepresentation = new JsonRepresentation(result);
    
    // Buffering avoids a Chunked response, Chunked caused last chunk timeouts.
    BufferingRepresentation bufferingRep = new BufferingRepresentation(jsonRepresentation);
    
    return bufferingRep;
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

  protected Representation handleException(Exception exception) {
    HashMap<String,String> result = Maps.newHashMap();
    LOGGER.fatal(stackTraceToString(exception));

    if (exception instanceof IllegalArgumentException) {
      this.setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
      result.put("error", "invalid_params");
      result.put("message", "Check your REST Parameters");
    } else {
      result.put("error", "internal_error");
      result.put("message", "There was an error processing the request, see the server logs for more info");
      this.setStatus(Status.SERVER_ERROR_INTERNAL);
    }

    if (exception instanceof com.mongodb.MongoException) {
      result.put("error", "mongo_db_is_down");
      result.put("message", "Check your MongoDB sercer and configuration");
    }

    if (exception.getMessage() != null && exception.getMessage().length() > 0) {      
      result.put("exception_message", exception.getMessage());
    }

    return createJsonResult(gson.toJson(result));
  }

  @SuppressWarnings("unchecked")
  static Series<Header> getMessageHeaders(Message message) {
    ConcurrentMap<String, Object> attrs = message.getAttributes();
    Series<Header> headers = (Series<Header>) attrs.get(HEADERS_KEY);
    if (headers == null) {
      headers = new Series<Header>(Header.class);
      Series<Header> prev = (Series<Header>) 
          attrs.putIfAbsent(HEADERS_KEY, headers);
      if (prev != null) { headers = prev; }
    }
    return headers;
  }

  protected ApplicationContext getApplicationContext() {
    if (appCtx == null) {
      appCtx = (ApplicationContext) getContext().getAttributes().get("appCtx");
    }
    return appCtx;
  }

  protected StorageHelper getStorageHelper() {
    if (storageHelper == null) {
      storageHelper = getApplicationContext().getBean(StorageHelper.class);
    }
    return storageHelper;
  }
}