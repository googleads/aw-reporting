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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

/**
 * A {@link ReportWriter} that writes reports to Memory.
 *
 * @author jtoledo@google.com (Julian Toledo)
 */
public class MemoryReportWriter extends OutputStreamWriter implements ReportWriter {

  private final ByteArrayOutputStream byteArrayOutputStream;

  private MemoryReportWriter(ByteArrayOutputStream byteArrayOutputStream) {
    super(byteArrayOutputStream);
    this.byteArrayOutputStream = byteArrayOutputStream;
  }

  public static MemoryReportWriter newMemoryReportWriter() {
    return new MemoryReportWriter(new ByteArrayOutputStream());
  }

  /**
   * Returns the InputStream source to be used on other writers.
   *
   * @return InputStream
   */
  public InputStream getAsSource() {
    return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
  }

  /**
   * Writes the inputStream to Memory.
   *
   * @param inputStream the file content to be written 
   * @throws FileNotFoundException
   * @throws IOException 
   */
  public void write(InputStream inputStream) throws FileNotFoundException, IOException {
    int read = 0;
    byte[] bytes = new byte[1024];

    while ((read = inputStream.read(bytes)) != -1) {
      byteArrayOutputStream.write(bytes, 0, read);
    }

    inputStream.close();
    byteArrayOutputStream.flush();
    byteArrayOutputStream.close();
  }
}
