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

package com.google.api.ads.adwords.jaxws.extensions.util;

import com.google.api.ads.adwords.jaxws.extensions.reportwriter.ReportWriter;
import com.lowagie.text.DocumentException;
import com.samskivert.mustache.Mustache;

import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.resource.XMLResource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Class to export reports to HTML using JMoustache, and convert HTML to PDF using Flying Saucer
 *
 * @author markbowyer@google.com (Mark R. Bowyer)
 */
public class HTMLExporter {

  public HTMLExporter() {}

  /**
   * Exports an HTML file of the given report
   *
   * @param map the data from the Report
   * @param templateFile where to read out the HTML template
   * @param reportWriter the ReportWriter to which HTML should be written
   * @throws IOException error writing HTML file
   * @throws FileNotFoundException error reading template file
   */
  public static void exportHTML(final Map<String, Object> map,
      File templateFile, ReportWriter reportWriter) throws IOException {
    FileReader templateReader = new FileReader(templateFile);
//    FileWriter fileWriter = new FileWriter(outputFile);
    Mustache.compiler().compile(templateReader).execute(map, reportWriter);

    reportWriter.flush();
    reportWriter.close();
    templateReader.close();
  }

  /**
   * Convert a given HTML file to a PDF file
   *
   * @param inFile The HTML file
   * @param reportWriter the ReportWriter to which HTML should be written
   * @throws DocumentException error creating PDF file
   * @throws IOException error closing file
   */
  public static void convertHTMLtoPDF(File inFile, ReportWriter reportWriter)
      throws DocumentException, IOException {

    FileReader htmlReader = new FileReader(inFile);

    Document document = XMLResource.load(htmlReader).getDocument();
    ITextRenderer renderer = new ITextRenderer();
    renderer.getSharedContext().setReplacedElementFactory(
        new MediaReplacedElementFactory(renderer.getSharedContext().getReplacedElementFactory()));
    renderer.setDocument(document, null);

    renderer.layout();
    
//    FileOutputStream fos = new FileOutputStream(outFile);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    renderer.createPDF(outputStream, true);
    ByteArrayInputStream is = new ByteArrayInputStream(outputStream.toByteArray());
    reportWriter.write(is);

    outputStream.flush();
    outputStream.close();
  }
}
