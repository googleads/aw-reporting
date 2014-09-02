// Copyright 2012 Google Inc. All Rights Reserved.
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

import com.google.api.client.util.Lists;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Util class for File objects.
 *
 * @author jtoledo@google.com (Julian Toledo)
 */
public final class FileUtil {

  /**
   * Private constructor.
   */
  private FileUtil() {}

  /**
   * GUnzips a source File to a destination File.
   *
   * @param srcFile the source file.
   * @param destFile the destination file.
   * @throws IOException error handling the files.
   */
  public static void gUnzip(File srcFile, File destFile) throws IOException {

    FileOutputStream fos = null;
    GZIPInputStream zIn = null;
    InputStream fis = null;

    try {
      fos = new FileOutputStream(destFile);
      fis = new FileInputStream(srcFile);
      zIn = new GZIPInputStream(fis);

      copy(zIn, fos);

    } finally {
      if (fis != null) {
        fis.close();
      }
      if (fos != null) {
        fos.close();
      }
      if (zIn != null) {
        zIn.close();
      }
    }
  }

  /**
   * GZips a source File to a destination File.
   *
   * @param srcFile the source file.
   * @param destFile the destination file.
   * @throws IOException error handling the files.
   */
  public static void gZip(File srcFile, File destFile) throws IOException {

    FileOutputStream fos = null;
    GZIPOutputStream zos = null;
    InputStream fis = null;

    try {
      fos = new FileOutputStream(destFile);
      fis = new FileInputStream(srcFile);
      zos = new GZIPOutputStream(fos);

      copy(fis, zos);

    } finally {
      if (fis != null) {
        fis.close();
      }
      if (zos != null) {
        zos.close();
      }
      if (fos != null) {
        fos.close();
      }
    }
  }

  /**
   * Reads the lines of a file and create a list of Strings containing each line.
   *
   * @param file the file
   * @return the list of String with the lines
   * @throws FileNotFoundException the file was not found
   */
  public static List<String> readFileLinesAsStrings(File file) throws FileNotFoundException {

    List<String> lines = Lists.newArrayList();
    Scanner fileScanner = new Scanner(file);

    while (fileScanner.hasNext()) {
      lines.add(fileScanner.nextLine());
    }
    
    fileScanner.close();
    return lines;
  }

  /**
   * Copies from the {@code InputStream} into the {@code OutputStream}.
   *
   * @param inputStream the {@code GZIPInputStream}.
   * @param outputStream the {@code OutputStream}.
   * @throws IOException error handling files.
   */
  public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {

    byte[] buffer = new byte[8 * 1024];
    int count = 0;
    do {
      outputStream.write(buffer, 0, count);
      count = inputStream.read(buffer, 0, buffer.length);
    } while (count != -1);
  }
}
