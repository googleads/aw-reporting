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

package com.google.api.ads.adwords.jaxws.extensions.kratu.restserver;

import com.google.api.ads.adwords.jaxws.extensions.exporter.HTMLExporter;
import com.google.api.ads.adwords.jaxws.extensions.exporter.reportwriter.MemoryReportWriter;

import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.InputRepresentation;
import org.restlet.representation.Representation;
import org.w3c.dom.Document;
import org.xhtmlrenderer.resource.XMLResource;

import java.io.InputStream;
import java.io.StringReader;

/**
 * HtmlToPdfRest
 * 
 * @author jtoledo@google.com (Julian Toledo)
 */
public class HtmlToPdfRest extends AbstractServerResource {

  public Representation getHandler() {
    String result = null;
    return createJsonResult(result);
  }

  public void deleteHandler() {
    this.setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
  }

  public Representation postPutHandler(String html) {
    Representation result = null;
    try {
      getParameters();
      
      StringReader stringReader = new StringReader(html);

      MemoryReportWriter memoryReportWriter = MemoryReportWriter.newMemoryReportWriter();      
      Document document = XMLResource.load(stringReader).getDocument();
      HTMLExporter.exportHtmlToPdf(document, memoryReportWriter, null);

      result = createPdfResult(memoryReportWriter.getAsSource());

    } catch (Exception exception) {
      return handleException(exception);
    }
    addHeaders();    
    return result;
  }
  
  protected InputRepresentation createPdfResult(InputStream inputStream) {
    InputRepresentation inputRepresentation = new InputRepresentation(inputStream);
    inputRepresentation.setMediaType(MediaType.APPLICATION_PDF);
    return inputRepresentation;
  }
}
