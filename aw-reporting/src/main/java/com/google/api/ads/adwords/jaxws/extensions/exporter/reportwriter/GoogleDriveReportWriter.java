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

package com.google.api.ads.adwords.jaxws.extensions.exporter.reportwriter;

import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.ParentReference;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * A {@link ReportWriter} that writes reports to Google Drive.
 *
 * @author joeltoby@google.com (Joel Toby)
 * @author jtoledo@google.com (Julian Toledo)
 */
public class GoogleDriveReportWriter implements ReportWriter {

  private static final Logger LOGGER = Logger.getLogger(GoogleDriveReportWriter.class);

  private final String PDF_MIME_TYPE = "application/pdf";
  private final String HTML_MIME_TYPE = "text/html";
  private final String DOC_MIME_TYPE = "application/vnd.google-apps.document";

  private final long accountId;
  private final String dateStart;
  private final String dateEnd;
  private final boolean folderPerAccount;
  private final String mccAccountId;
  private GoogleDriveService googleDriveService;
  private Credential credential;
  private ReportFileType reportFileType;
  private final String templateName;

  private GoogleDriveReportWriter(GoogleDriveReportWriterBuilder builder) throws IOException, OAuthException {
    this.accountId = builder.accountId;
    this.dateStart = builder.dateStart;
    this.dateEnd = builder.dateEnd;
    this.folderPerAccount = builder.folderPerAccount;
    this.mccAccountId = builder.mccAccountId;
    this.credential = builder.credential;
    this.reportFileType = builder.reportFileType;
    this.templateName = builder.templateName;

    // Replace this when GoogleDriveService properly extends Drive.
    LOGGER.debug("Getting GoogleDrive service.");
    googleDriveService =  GoogleDriveService.getGoogleDriveService(credential);
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
    return reportFileType;
  }

  /**
   * @return true if a sub-folder is created per account
   */
  public boolean isFolderPerAccount() {
    return folderPerAccount;
  }

  /**
   * @return the topAccountCid
   */
  public String getMccAccountId() {
    return mccAccountId;
  }

  /**
   * Builder for the {@link GoogleDriveReportWriter}.
   */
  public static class GoogleDriveReportWriterBuilder {
    private final long accountId;
    private final String dateStart;
    private final String dateEnd;
    private final String mccAccountId;
    private boolean folderPerAccount = false;
    private final Credential credential;
    private final ReportFileType reportFileType;
    private final String templateName;

    public GoogleDriveReportWriterBuilder(long accountId, String dateStart, 
        String dateEnd, String mccAccountId,
        Credential credential, ReportFileType reportFileType, String templateName) {
      this.accountId = accountId;
      this.dateStart = dateStart;
      this.dateEnd = dateEnd;
      this.mccAccountId = mccAccountId;
      this.credential = credential;
      this.reportFileType = reportFileType;
      this.templateName = templateName;
    }

    /**
     * If set to true, a sub folder will be created on Google Drive for each account.
     * @param folderPerAccount
     * @return
     */
    public GoogleDriveReportWriterBuilder withFolderPerAccount(boolean folderPerAccount) {
      this.folderPerAccount = folderPerAccount;
      return this;
    }

    public GoogleDriveReportWriter build() throws IOException, OAuthException {
      return new GoogleDriveReportWriter(this);
    }
  }

  @Override
  public void write(InputStream inputStream) throws IOException {
    LOGGER.info("Getting AW Reports Drive output folder");
    File outputFolder;
    // Get or create an AW Reports folder
    File reportsFolder = googleDriveService.getReportsFolder(mccAccountId);
    outputFolder = reportsFolder;
    
    if( folderPerAccount ) {
      File accountFolder = googleDriveService.getAccountFolder(reportsFolder, String.valueOf(accountId));
      outputFolder = accountFolder;
    }

    // Create a Google Drive PDF file
    File reportFile = new File();
    reportFile.setFileExtension(reportFileType.name());

    String fileNameWithOutExt = FilenameUtils.removeExtension((templateName));
    String reportFileName = fileNameWithOutExt + "_" + accountId + "_" + dateStart + "_" 
        + dateEnd + "." + reportFileType.toString().toLowerCase();
    
    reportFile.setDescription("AdWords Report " + fileNameWithOutExt + " for account "
        + accountId + "for dates between" + dateStart + " and " + dateEnd);

    reportFile.setTitle(reportFileName);

    // Place the file in the correct Drive folder
    reportFile.setParents(Arrays.asList(new ParentReference().setId(outputFolder.getId())));

    // Write the PDF file to Drive.
    if (reportFileType.equals(ReportFileType.PDF)) {
      reportFile.setMimeType(PDF_MIME_TYPE);
      AbstractInputStreamContent aisc = new InputStreamContent(PDF_MIME_TYPE, inputStream);
      googleDriveService.getDriveService().files().insert(reportFile, aisc).execute();
    }

    // Write the HTML file to Drive.
    if (reportFileType.equals(ReportFileType.HTML)) {
      reportFile.setMimeType(HTML_MIME_TYPE);
      AbstractInputStreamContent aisc = new InputStreamContent(HTML_MIME_TYPE, inputStream);
      googleDriveService.getDriveService().files().insert(reportFile, aisc).execute();
    }
    
    // Convert the HTML file to a Drive Doc and write to Drive.
    if (reportFileType.equals(ReportFileType.DRIVE_DOC)) {
      reportFile.setMimeType(DOC_MIME_TYPE);
      AbstractInputStreamContent aisc = new InputStreamContent(HTML_MIME_TYPE, inputStream);
      googleDriveService.getDriveService().files().insert(reportFile, aisc).setConvert(true).execute();
    }
    inputStream.close(); 
  }
}
