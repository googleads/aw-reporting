// Copyright 2015 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.awreporting.alerting.action;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.api.ads.adwords.awreporting.alerting.report.ReportEntry;
import com.google.api.ads.adwords.awreporting.alerting.util.ConfigTags;
import com.google.gson.JsonObject;

/**
 * An alert action implementation that writes alert messages in the specified log file.
 * Note that it must provide a constructor that takes a JsonObject parameter, and must not modify report entries.
 * 
 * The JSON config should look like:
 * {
 *   "ActionClass": "SimpleLogFileWriter",
 *   "LogFilePathname": "/tmp/xyz.log",
 *   "AppendMode": "true"
 * }
 * 
 * @author zhuoc@google.com (Zhuo Chen)
 */
public class SimpleLogFileWriter implements AlertAction {
  private static String LOG_FILE_PATHNAME_TAG = "LogFilePathname";
  private static String APPEND_MODE_TAG       = "AppendMode";  // optional
  private static int LINES_TO_FLUSH = 100;  // Flush buffer after this number of lines
  
  private String filePathname;
  private BufferedWriter writer;
  private int nonflushedLines;

  /**
   * Constructor
   * @param config the JsonObject for the alert action configuration.
   */
  public SimpleLogFileWriter(JsonObject config) {
    filePathname = config.get(LOG_FILE_PATHNAME_TAG).getAsString();
    boolean appendMode = true;
    if (config.has(APPEND_MODE_TAG)) {
        appendMode = config.get(APPEND_MODE_TAG).getAsBoolean();
    }
    
    try {
      writer = new BufferedWriter(new FileWriter(filePathname, appendMode));
    } catch (IOException e) {
      e.printStackTrace();
    }
    nonflushedLines = 0;
  }

  /**
   * Initialization action: print some header lines.
   */
  @Override
  public void initializeAction() {
    System.out.println("Start generating alerts into log file: " + filePathname);
        
    Date now = new Date();
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    try {
      writer.write("===== Begin of this run =====");
      writer.newLine();
      writer.write("Alerts generated at " + dateFormat.format(now) + ":");
      writer.newLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Process a report entry, and write its alert message in the log file.
   * 
   * @param entry the report entry to process.
   */
  @Override
  public void processReportEntry(ReportEntry entry) {
    try {
      writer.write(entry.getFieldValue(ConfigTags.ALERT_MESSAGE));
      writer.newLine();
      if (nonflushedLines++ >= LINES_TO_FLUSH) {
        writer.flush();
        nonflushedLines = 0;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Finalization action: print some foot lines.
   */
  @Override
  public void finalizeAction() {
    try {
      writer.write("===== End of this run =====");
      writer.newLine();
      writer.newLine();
      writer.newLine();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    System.out.println("Finish generating alerts into log file: " + filePathname);
    System.out.println();
  }
}
