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

package com.google.api.ads.adwords.awreporting.server.rest;

import com.google.api.ads.adwords.awreporting.model.util.DateUtil;
import com.google.api.ads.adwords.awreporting.model.util.GsonUtil;
import com.google.api.ads.adwords.jaxws.v201409.mcm.ApiException;
import com.google.api.client.util.Maps;
import com.google.gson.Gson;

import org.apache.log4j.Logger;
import org.restlet.Message;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.ByteArrayRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Options;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Defines commmon methods for all Rest entry points
 * 
 * @author jtoledo@google.com (Julian Toledo)
 */
public abstract class AbstractBaseResource extends ServerResource {

  protected static final Logger LOGGER = Logger.getLogger(AbstractBaseResource.class.getName());

  protected static final String HEADERS_KEY = "org.restlet.http.headers";

  protected static final Gson gson = GsonUtil.getGsonBuilder().create();

  @Get
  abstract public Representation getHandler();

  // Not allowed by default, subClass most implement if needed.
  @Delete
  public Representation deleteHandler() {
    this.setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
    return handleException(
        new ResourceException(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED, "Delete not allowed"));
  }

  // Not allowed by default, subClass most implement if needed.
  @Post
  @Put
  public Representation postPutHandler(String json) {
    this.setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
    return handleException(
        new ResourceException(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED, "Post/Put not allowed"));
  }

  @Options
  public void optionsHandler() {
    addHeaders();
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
<<<<<<< HEAD
  
  /**
   * Adds the 'Content-Disposition' header which suggests a file name for downloads
   * to browsers. <br>
   * This should be invoked for any file responses to be saved such as PDFs.
   * 
   * @param fileName the suggested name of the file to be returned
   * @param forceFileDownload whether or not to foce the browser to download the file and not to try displaying it
   */
  protected void addFileNameHeader(String fileName, boolean forceFileDownload) {
	String contentDisposition;
	if( forceFileDownload ) {
		contentDisposition = "attachment; filename=\"" + fileName + "\"";
	} else {
		contentDisposition = "inline; filename=\"" + fileName + "\"";
	}
	getMessageHeaders(getResponse()).add("Content-Disposition", contentDisposition);
  }
=======
>>>>>>> 2f2a7486c98ac121ad93d5d5bb44c6f875fcbc1a

  protected Representation createJsonResult(String result) {
    this.setAutoCommitting(true);

    // Return a Json Error with the Not Found Exception
    if (result == null) {
      return handleException(
          new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Nothing found, check your URL/Parameters"));
    }

    JsonRepresentation jsonRepresentation = new JsonRepresentation(result);
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

  protected ByteArrayRepresentation createPdfResult(byte[] byteArray) {
    if (byteArray == null) {
      this.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
      return null;
    }
    ByteArrayRepresentation byteArrayRepresentation = new ByteArrayRepresentation(byteArray, MediaType.APPLICATION_PDF);

    return byteArrayRepresentation;
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
      result.put("message", "Check your request parameters");

    } else if (exception instanceof com.mongodb.MongoException) {
      this.setStatus(Status.SERVER_ERROR_SERVICE_UNAVAILABLE);
      result.put("error", "mongo_db_is_down");
      result.put("message", "Check your MongoDB server and configuration");

    } else if(exception instanceof ParseException) {
      this.setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
      result.put("error", "invalid_text_params");
      result.put("message", "Check your REST Parameters. Likely an invalid date format.");

    } else if(exception instanceof ResourceException) {
      this.setStatus(((ResourceException) exception).getStatus());
      result.put("error", ((ResourceException) exception).getStatus().getDescription());
      result.put("message", "Check that this methods is allows by the Rest Class");

    } else if(exception instanceof ApiException) {
      this.setStatus(Status.SERVER_ERROR_INTERNAL);
      result.put("error", "Check your AdWords API access for this Account/MCC");

    } else {
      this.setStatus(Status.SERVER_ERROR_INTERNAL);
      result.put("error", "internal_error");
      result.put("message", "There was an error processing the request, see the server logs for more info");
    }

    if (exception.getMessage() != null && exception.getMessage().length() > 0) {      
      result.put("exception_message", exception.getMessage());
    }

    return createJsonResult(gson.toJson(result));
  }

  @SuppressWarnings("unchecked")
  protected Series<Header> getMessageHeaders(Message message) {
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

  protected String getParameter(String name) {
    try {
      // Get from Request Attibutes
      String tempString = (String) getRequestAttributes().get(name);
      if (tempString == null) {
        // Get from Query
        tempString = getReference().getQueryAsForm().getFirstValue(name);
      }
      return tempString;
    } catch(Exception exception) {
      throw new IllegalArgumentException(exception);
    }
  }

  protected Long getParameterAsLong(String name) {
    try {
      // Get from Request Attibutes
      String tempString = (String) getRequestAttributes().get(name);
      if (tempString == null) {
        // Get from Query
        tempString = getReference().getQueryAsForm().getFirstValue(name);
      }

      if (tempString != null) {
        tempString = tempString.replaceAll("[^\\d.]", "");
      }

      return tempString == null || tempString.length() == 0 ? null : Long.parseLong(tempString);
    } catch(Exception exception) {
      throw new IllegalArgumentException(exception);
    }
  }
  
  protected Integer getParameterAsInteger(String name) {
    try {
      // Get from Request Attibutes
      String tempString = (String) getRequestAttributes().get(name);
      if (tempString == null) {
        // Get from Query
        tempString = getReference().getQueryAsForm().getFirstValue(name);
      }

      if (tempString != null) {
        tempString = tempString.replaceAll("[^\\d.]", "");
      }

      return tempString == null || tempString.length() == 0 ? null : Integer.parseInt(tempString);
    } catch(Exception exception) {
      throw new IllegalArgumentException(exception);
    }
  }

  protected boolean getParameterAsBoolean(String name) {
    try {
      // Get from Request Attibutes
      String tempString = (String) getRequestAttributes().get(name);
      if (tempString == null) {
        // Get from Query
        tempString = getReference().getQueryAsForm().getFirstValue(name);
      }
      return (tempString != null && tempString.equals("true"));
    } catch(Exception exception) {
      throw new IllegalArgumentException(exception);
    }
  }

  protected Date getParameterAsDate(String name) {
    try {
      // Get from Request Attibutes
      String tempString = (String) getRequestAttributes().get(name);
      if (tempString == null) {
        // Get from Query
        tempString = getReference().getQueryAsForm().getFirstValue(name);
      }
      return tempString == null ? null : DateUtil.parseDateTime(tempString).toDate();
    } catch(Exception exception) {
      throw new IllegalArgumentException(exception);
    }
  }
}
