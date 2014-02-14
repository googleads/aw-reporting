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

package com.google.api.ads.adwords.jaxws.extensions.reportwriter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.api.ads.adwords.jaxws.extensions.util.GoogleDriveService;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.ParentReference;

/**
 * A {@link ReportWriter} that writes reports to Google Drive.
 *
 * @author joeltoby@google.com (Joel Toby)
 */

public class GoogleDriveReportWriter extends ReportWriter {
  
  private static final Logger LOGGER = Logger
      .getLogger(GoogleDriveReportWriter.class);
  
  private static final ReportFileType REPORT_FILE_TYPE = ReportFileType.PDF;
  
  private final String REPORT_FOLDER_NAME = "AW Reports - AdWords generated PDF Reports";
  
  private final String FOLDER_MIME_TYPE = "application/vnd.google-apps.folder";
  
  private final String PDF_MIME_TYPE = "application/pdf";
  
  private final long accountId;
  private final String dateStart;
  private final String dateEnd;
  private Drive driveService;
  
  
  private GoogleDriveReportWriter(GoogleDriveReportWriterBuilder builder) throws IOException {
//    this.outputDirectory = builder.outputDirectory;
    this.accountId = builder.accountId;
    this.dateStart = builder.dateStart;
    this.dateEnd = builder.dateEnd;
    
    driveService = GoogleDriveService.getInstance().getDriveService();
  }

  /**
   * @return the accountId
   */
  public long getAccountId() {
    return accountId;
  }

  /**
   * @return the dateStart
   */
  public String getDateStart() {
    return dateStart;
  }

  /**
   * @return the dateEnd
   */
  public String getDateEnd() {
    return dateEnd;
  }

  /**
   * @return the reportFileType
   */
  public ReportFileType getReportFileType() {
    return REPORT_FILE_TYPE;
  }

//  /**
//   * @return the outputFile
//   */
//  public File getOutputFile() {
//    return outputFile;
//  }



  public static class GoogleDriveReportWriterBuilder {
//    private final String outputDirectory;
    private final long accountId;
    private final String dateStart;
    private final String dateEnd;
    
    public GoogleDriveReportWriterBuilder(long accountId, String dateStart, 
        String dateEnd, String clientId, String clientSecret) {
      this.accountId = accountId;
      this.dateStart = dateStart;
      this.dateEnd = dateEnd;
    }
    
    public GoogleDriveReportWriter build() throws IOException {
      return new GoogleDriveReportWriter(this);
    }
  }

  @Override
  public void close() throws IOException {
    // TODO
    
  }

  @Override
  public void flush() throws IOException {
 // TODO
    
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    // TODO
  }

  @Override
  public void write(InputStream inputStream) throws IOException {
    LOGGER.info("Getting AW Reports Drive output folder");
    // Get or create an AW Reports folder
    File reportsFolder = getReportsFolder();
    
    File reportPdfFile = new File();
    reportPdfFile.setFileExtension(REPORT_FILE_TYPE.name());
    reportPdfFile.setDescription("AdWords Report for account " + accountId + "for dates between" 
        + dateStart + " and " + dateEnd);
    reportPdfFile.setTitle("Report_" + accountId + "_" + dateStart + "_" + dateEnd);
    reportPdfFile.setMimeType(PDF_MIME_TYPE);
    reportPdfFile.setParents(Arrays.asList(new ParentReference().setId(reportsFolder.getId())));
    
    AbstractInputStreamContent aisc = new InputStreamContent("application/pdf", inputStream);
    driveService.files().insert(reportPdfFile, aisc).execute();
    
    inputStream.close();
    
  }
  
  /**
   * Gets the AW Reports Google Drive folder. If one does not exist, it will be created.
   * @throws IOException
   */
  private File getReportsFolder() throws IOException {
    
    // Check if the folder exists
    List<File> results = new ArrayList<File>();
    LOGGER.info("Building find folder query");
    Files.List request = driveService.files().list()
        .setQ("title= '" + REPORT_FOLDER_NAME + "' and mimeType='" 
         + FOLDER_MIME_TYPE +"' and trashed = false");
    LOGGER.info("Executing find folder query");
    FileList files = request.execute();
    LOGGER.info("Number of results from query: " + files.size());
    results.addAll(files.getItems());
    
    if(! results.isEmpty()) {
      // Found the existing folder
      return results.get(0);
      
    } else {
      // Folder does not exist. Create it.
      LOGGER.info("Creating folder");
      File reportsFolder = new File();
      reportsFolder.setTitle(REPORT_FOLDER_NAME);
      reportsFolder.setMimeType(FOLDER_MIME_TYPE);
      reportsFolder.setDescription("Contains AdWords PDF reports generated by AW Reports to DB");
      
      LOGGER.info("Executing create folder");
      return driveService.files().insert(reportsFolder).execute();
      
    }
  }

}
