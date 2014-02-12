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
import java.io.Writer;

/**
 * Writes HTML or PDF reports. Should be extended by any classes that
 * write report files.
 *
 * @author joeltoby@google.com (Joel Toby)
 */
public abstract class ReportWriter extends Writer {
  
  /**
   * Represents the type of report file to be written for a given report
   */
  public enum ReportFileType { HTML, PDF };
  
  /**
   * Writes the InputStream to to the file
   * @param is
   * @throws FileNotFoundException 
   * @throws IOException 
   */
  public abstract void write(InputStream is) throws FileNotFoundException, IOException;

}
