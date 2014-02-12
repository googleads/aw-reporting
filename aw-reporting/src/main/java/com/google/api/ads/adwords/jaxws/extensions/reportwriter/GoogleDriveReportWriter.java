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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * A {@link ReportWriter} that writes reports to Google Drive.
 *
 * @author joeltoby@google.com (Joel Toby)
 */

public class GoogleDriveReportWriter extends ReportWriter {
  
  private final long accountId;
  private final String dateStart;
  private final String dateEnd;
  private final ReportFileType reportFileType;
  
  private GoogleDriveReportWriter(GoogleDriveReportWriterBuilder builder) throws IOException {
    this.accountId = builder.accountId;
    this.dateStart = builder.dateStart;
    this.dateEnd = builder.dateEnd;
    this.reportFileType = builder.reportType;
    
    String reportFileName = "Report_" + accountId + "_" + dateStart + "_" 
        + dateEnd + "." + reportFileType.toString().toLowerCase();
  }
  
  public static class GoogleDriveReportWriterBuilder {
    private final long accountId;
    private final String dateStart;
    private final String dateEnd;
    private final ReportFileType reportType;
    
    public GoogleDriveReportWriterBuilder(long accountId,
        String dateStart, String dateEnd, ReportFileType reportType) {
      this.accountId = accountId;
      this.dateStart = dateStart;
      this.dateEnd = dateEnd;
      this.reportType = reportType;
    }
    
    public GoogleDriveReportWriter build() throws IOException {
      return new GoogleDriveReportWriter(this);
    }
  }

  @Override
  public void close() throws IOException {
 // TODO Auto-generated method stub
    
  }

  @Override
  public void flush() throws IOException {
 // TODO Auto-generated method stub
    
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
 // TODO Auto-generated method stub
    
  }

  @Override
  public void write(InputStream is) throws FileNotFoundException, IOException {
    // TODO Auto-generated method stub
    
  }

}
