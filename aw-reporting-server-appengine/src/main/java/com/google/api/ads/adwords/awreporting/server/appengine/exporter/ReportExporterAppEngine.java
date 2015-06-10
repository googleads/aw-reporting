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

package com.google.api.ads.adwords.awreporting.server.appengine.exporter;

import com.google.api.ads.adwords.awreporting.exporter.HTMLExporter;
import com.google.api.ads.adwords.awreporting.exporter.reportwriter.FileSystemReportWriter;
import com.google.api.ads.adwords.awreporting.exporter.reportwriter.GoogleDriveReportWriter;
import com.google.api.ads.adwords.awreporting.exporter.reportwriter.GoogleDriveService;
import com.google.api.ads.adwords.awreporting.exporter.reportwriter.MemoryReportWriter;
import com.google.api.ads.adwords.awreporting.exporter.reportwriter.ReportWriter;
import com.google.api.ads.adwords.awreporting.exporter.reportwriter.ReportWriter.ReportFileType;
import com.google.api.ads.adwords.awreporting.exporter.reportwriter.ReportWriterType;
import com.google.api.ads.adwords.awreporting.server.appengine.RestServer;
import com.google.api.ads.adwords.awreporting.server.appengine.util.MccTaskCounter;
import com.google.api.ads.adwords.awreporting.server.exporter.ServerReportExporter;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.client.auth.oauth2.Credential;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import com.lowagie.text.DocumentException;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Exports DOC reports to Google Drive from an AppEngine instance of AW Reports to DB.
 *
 * @author joeltoby@google.com (Joel Toby)
 */
@Component
public class ReportExporterAppEngine extends ServerReportExporter implements Serializable {

  private static final long serialVersionUID = 1L;
  private static final Logger LOGGER = Logger.getLogger(ReportExporterAppEngine.class);

  private static final String HTML_2_PDF_SERVER_URL = "http://23.251.134.132:8081/html2pdf";

  /**
   * Creates the tasks that write export the Drive Doc reports.
   * @return the URL of the top level drive folder that will contain the reports
   */
  public void exportReports(final String mccAccountId,
      final String dateStart, final String dateEnd, final Set<Long> accountIds, final Properties properties,
      final Long htmlTemplateId, final File outputDirectory, final Boolean sumAdExtensions) 
          throws IOException, OAuthException, DocumentException {

    // Create the folder before creating the threads if it does not exist.
    Credential credential = RestServer.getAuthenticator().getOAuth2Credential(mccAccountId, false);

    GoogleDriveService.getGoogleDriveService(credential).getReportsFolder(mccAccountId).getWebContentLink();

    MccTaskCounter.increasePendingExportTasks(Long.valueOf(mccAccountId), accountIds.size());    

    LOGGER.info("Generating PDF exporting tasks for " + accountIds.size() + " accounts");
    // Create a task for each 200 accounts that will create sub-tasks
    for (List<Long> partition : Iterables.partition(accountIds, 200)) {
      // Queues will wait 10 seconds to ensure that all creation tasks get queued.
      // Partition needs to be serializable
      QueueFactory.getDefaultQueue().add(TaskOptions.Builder.withPayload(
          new ExportTaskCreator(
              mccAccountId,
              Lists.newArrayList(partition),
              dateStart,
              dateEnd,
              properties, 
              htmlTemplateId,
              outputDirectory,
              sumAdExtensions)).countdownMillis(10*1000l));
    }
  }

  /**
   * Export reports to PDF/HTML for one account.
   *
   * @param dateStart the start date for the reports
   * @param dateEnd the end date for the reports
   * @param accountId the account CID to generate PDF for
   * @param properties the properties file containing all the configuration
   * @param template the template file to generete the report
   * @param outputDirectory where to output the files
   * @param sumAdExtensions to add up all the extensions
   * @throws IOException 
   * @throws OAuthException 
   * @throws DocumentException 
   * @throws Exception error creating PDF
   */
  public void exportReport(Credential credential, String mccAccountId, String dateStart, String dateEnd, Long accountId, Properties properties,
      InputStreamReader template, String templateName, File outputDirectory, Boolean sumAdExtensions)
          throws IOException, OAuthException, DocumentException {

    LOGGER.info("Starting Report Export for account " + accountId);

    Map<String, Object> reportDataMap = createReportDataMap(dateStart, dateEnd, accountId, properties, sumAdExtensions);

    if (reportDataMap.size() > 0) {

      String propertyReportWriterType = properties.getProperty("aw.report.exporter.reportwritertype");

      Boolean writeHtml = true;
      if (properties.getProperty("aw.report.exporter.writeHtml") != null) {
        writeHtml = Boolean.valueOf(properties.getProperty("aw.report.exporter.writeHtml"));
      }

      Boolean writePdf = true;
      if (properties.getProperty("aw.report.exporter.writePdf") != null) {
        writePdf = Boolean.valueOf(properties.getProperty("aw.report.exporter.writePdf"));
      }
      
      Boolean writeDriveDoc = false;
      if (properties.getProperty("aw.report.exporter.writeDriveDoc") != null) {
        writeDriveDoc = Boolean.valueOf(properties.getProperty("aw.report.exporter.writeDriveDoc"));
      }

      // Get the Fonts for the PDF from the properties file
      String propertyReportFonts = properties.getProperty("aw.report.exporter.reportfonts");
      List<String> fontPaths = Lists.newArrayList();
      if (propertyReportFonts != null) {
        fontPaths = Arrays.asList(propertyReportFonts.split(","));
      }

      LOGGER.debug("Generating in Memory HTML for account: " + accountId);
      // Writing HTML to Memory
      MemoryReportWriter mrwHtml = MemoryReportWriter.newMemoryReportWriter();
      HTMLExporter.exportHtml(reportDataMap, template, mrwHtml);

      if (propertyReportWriterType != null && 
          propertyReportWriterType.equals(ReportWriterType.GoogleDriveWriter.name())) {
        
        // One folder per account
        Boolean perAccountFolder = false;
        if (properties.getProperty("aw.report.exporter.reportwritertype.drive.peraccountfolder") != null) {
          perAccountFolder = Boolean.valueOf(properties.getProperty("aw.report.exporter.writeDriveDoc"));
        }

        // Writing HTML to GoogleDrive
        if (writeHtml) {
          LOGGER.debug("Writing (to GoogleDrive) HTML for account: " + accountId);
          GoogleDriveReportWriter gdrwHtml = new GoogleDriveReportWriter.GoogleDriveReportWriterBuilder(
              accountId, dateStart, dateEnd, mccAccountId, credential, ReportFileType.HTML,
              templateName)
            .withFolderPerAccount(perAccountFolder)
            .build();
          gdrwHtml.write(mrwHtml.getAsSource());
        }

        // Writing PDF to GoogleDrive
        if (writePdf) {
          LOGGER.debug("Writing (to GoogleDrive) PDF for account: " + accountId);

          GoogleDriveReportWriter gdrwPdf = new GoogleDriveReportWriter.GoogleDriveReportWriterBuilder(
              accountId, dateStart, dateEnd, mccAccountId, credential, ReportFileType.PDF,
              templateName)
            .withFolderPerAccount(perAccountFolder)
            .build();
          
          html2PdfOverNet(mrwHtml.getAsSource(), gdrwPdf);
          
        }
        
        // Writing Drive Doc to GoogleDrive
        if (writeDriveDoc) {
          LOGGER.debug("Writing GoogleDrive Doc for account: " + accountId);
          GoogleDriveReportWriter gdrwDriveDoc = new GoogleDriveReportWriter.GoogleDriveReportWriterBuilder(
              accountId, dateStart, dateEnd, mccAccountId, credential, ReportFileType.DRIVE_DOC,
              templateName)
            .withFolderPerAccount(perAccountFolder)
            .build();
          gdrwDriveDoc.write(mrwHtml.getAsSource());
        }

      } else {

        // Writing HTML to Disk
        if (writeHtml) {
          LOGGER.debug("Writing (to FileSystem) HTML for account: " + accountId);
          FileSystemReportWriter fsrwHtml = FileSystemReportWriter.newFileSystemReportWriter(
              templateName, dateStart, dateEnd, accountId, outputDirectory, ReportFileType.HTML);
          fsrwHtml.write(mrwHtml.getAsSource());
        }

        // Writing PDF to Disk
        if (writePdf) {
          LOGGER.debug("Writing (to FileSystem) PDF for account: " + accountId);
          FileSystemReportWriter fsrwPdf = FileSystemReportWriter.newFileSystemReportWriter(
              templateName, dateStart, dateEnd, accountId, outputDirectory, ReportFileType.PDF);
          HTMLExporter.exportHtmlToPdf(mrwHtml.getAsSource(), fsrwPdf, fontPaths);
        }
      }
    }
  }

  public static void html2PdfOverNet(InputStream htmlSource, ReportWriter reportWriter) {
    try {
      URL url = new URL(HTML_2_PDF_SERVER_URL); 
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();           
      connection.setDoOutput(true);
      connection.setDoInput(true);
      connection.setInstanceFollowRedirects(false); 
      connection.setRequestMethod("POST"); 
      connection.setRequestProperty("Content-Type", "text/html"); 
      connection.setRequestProperty("charset", "utf-8");
      connection.setUseCaches (false);

      DataOutputStream send = new DataOutputStream(connection.getOutputStream());
      send.write(IOUtils.toByteArray(htmlSource));
      send.flush();
      
      // Read from connection
      reportWriter.write(connection.getInputStream());

      send.close();
      
      connection.disconnect();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
