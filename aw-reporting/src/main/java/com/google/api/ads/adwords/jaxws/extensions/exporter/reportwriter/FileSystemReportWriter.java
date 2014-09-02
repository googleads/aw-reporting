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

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A {@link ReportWriter} that writes reports to the File System.
 *
 * @author joeltoby@google.com (Joel Toby)
 * @author jtoledo@google.com (Julian Toledo)
 */
public class FileSystemReportWriter extends FileWriter implements ReportWriter {

  private final File file;

  public FileSystemReportWriter(File file) throws IOException {
    super(file);
    this.file = file;
  }

  /**
   * Returns the file as source to be used on other writers
   *
   * @return  
   */
  public File getAsSource() {
    return file;
  }

  /**
   * Writes the inputStream to the File System.
   *
   * @param inputStream the file content to be written 
   * @throws FileNotFoundException
   * @throws IOException 
   */
  public void write(InputStream inputStream) throws FileNotFoundException, IOException {
    OutputStream outputStream = new FileOutputStream(file);
    int read = 0;
    byte[] bytes = new byte[1024];

    while ((read = inputStream.read(bytes)) != -1) {
      outputStream.write(bytes, 0, read);
    }

    inputStream.close();
    outputStream.flush();
    outputStream.close();
  }

  /**
   * Generates the report filename, based on template file names
   * dates and accountId
   *
   * @param htmlTemplateFile the template file to generete the report
   * @param dateStart the start date for the reports
   * @param dateEnd the end date for the reports
   * @param accountId the account CID to generate the Report for
   * @param outputDirectory where to output the files
   * @param reportFileType either PDF or HTML
   * @throws IOException 
   */
  public static FileSystemReportWriter newFileSystemReportWriter(String templateName, String dateStart,
      String dateEnd, Long accountId, File outputDirectory, ReportFileType reportFileType)
          throws IOException {

    String fileNameWithOutExt = FilenameUtils.removeExtension((templateName));
    
    String reportFileName = fileNameWithOutExt + "_" + accountId + "_" + dateStart + "_" 
        + dateEnd + "." + reportFileType.toString().toLowerCase();
    
    return new FileSystemReportWriter(new File(outputDirectory, reportFileName));
  }
}
