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

/*
 Config:
  {
    "ActionClass": "LogFileWriter",
    "LogFilePathname": "/tmp/xyz.log",
    "AppendMode": "true"
  }
 */

public class LogFileWriter implements AlertAction {
  private static String LOG_FILE_PATHNAME_TAG = "LogFilePathname";
  private static String APPEND_MODE_TAG       = "AppendMode";
  private static int LINES_TO_FLUSH = 100;  // Flush buffer after this number of lines
  
  private BufferedWriter writer;
  private int nonflushedLines;

  public LogFileWriter(JsonObject config) {
    String filePathname = config.get(LOG_FILE_PATHNAME_TAG).getAsString();
    boolean appendMode = config.get(APPEND_MODE_TAG).getAsBoolean();
    
    try {
      writer = new BufferedWriter(new FileWriter(filePathname, appendMode));
    } catch (IOException e) {
      e.printStackTrace();
    }
    nonflushedLines = 0;
  }

  @Override
  public void initializeAction() {
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

  @Override
  public void processReportEntry(ReportEntry entry) {
    try {
      writer.write(entry.getFieldValue(ConfigTags.Rule.ALERT_MESSAGE));
      writer.newLine();
      if (nonflushedLines++ >= LINES_TO_FLUSH) {
        writer.flush();
        nonflushedLines = 0;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

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
  }
}
