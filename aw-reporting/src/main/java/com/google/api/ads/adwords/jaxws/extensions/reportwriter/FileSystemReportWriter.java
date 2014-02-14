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

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A {@link ReportWriter} that writes reports to the file system.
 *
 * @author joeltoby@google.com (Joel Toby)
 */

public class FileSystemReportWriter extends ReportWriter {
  private FileWriter fileWriter;
  
  private final String outputDirectory;
  private final long accountId;
  private final String dateStart;
  private final String dateEnd;
  private final ReportFileType reportFileType;
  private final File outputFile;
  
  private FileSystemReportWriter(FileSystemReportWriterBuilder builder) throws IOException {
    this.outputDirectory = builder.outputDirectory;
    this.accountId = builder.accountId;
    this.dateStart = builder.dateStart;
    this.dateEnd = builder.dateEnd;
    this.reportFileType = builder.reportType;
    
    String reportFileName = "Report_" + accountId + "_" + dateStart + "_" 
        + dateEnd + "." + reportFileType.toString().toLowerCase();
    
    outputFile = new File(outputDirectory, reportFileName);
    fileWriter = new FileWriter(outputFile);
  }
  
  /**
   * @return the fileWriter
   */
  public FileWriter getFileWriter() {
    return fileWriter;
  }

  /**
   * @return the outputDirectory
   */
  public String getOutputDirectory() {
    return outputDirectory;
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
   * @return the outputFile
   */
  public File getOutputFile() {
    return outputFile;
  }



  public static class FileSystemReportWriterBuilder {
    private final String outputDirectory;
    private final long accountId;
    private final String dateStart;
    private final String dateEnd;
    private final ReportFileType reportType;
    
    public FileSystemReportWriterBuilder(String outputDirectory, long accountId,
        String dateStart, String dateEnd, ReportFileType reportType) {
      this.outputDirectory = outputDirectory;
      this.accountId = accountId;
      this.dateStart = dateStart;
      this.dateEnd = dateEnd;
      this.reportType = reportType;
    }
    
    public FileSystemReportWriter build() throws IOException {
      return new FileSystemReportWriter(this);
    }
  }

  @Override
  public void close() throws IOException {
    fileWriter.close();
    
  }

  @Override
  public void flush() throws IOException {
    fileWriter.flush();
    
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    fileWriter.write(cbuf, off, len);    
  }

  @Override
  public void write(InputStream inputStream) throws IOException {
    OutputStream outputStream = new FileOutputStream(outputFile);
    int read = 0;
    byte[] bytes = new byte[1024];

    while ((read = inputStream.read(bytes)) != -1) {
        outputStream.write(bytes, 0, read);
    }
    
    inputStream.close();
    outputStream.flush();
    outputStream.close();
    
  }

}
