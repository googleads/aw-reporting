// Copyright 2013 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.jaxws.extensions.downloader;

import com.google.api.ads.adwords.jaxws.extensions.util.AdWordsSessionBuilderSynchronizer;
import com.google.api.ads.adwords.jaxws.extensions.util.FileUtil;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.jaxb.v201406.ReportDefinition;
import com.google.api.ads.adwords.lib.utils.ReportDownloadResponse;
import com.google.api.ads.adwords.lib.utils.ReportDownloadResponseException;
import com.google.api.ads.adwords.lib.utils.ReportException;
import com.google.api.ads.adwords.lib.utils.v201406.DetailedReportDownloadResponseException;
import com.google.api.ads.adwords.lib.utils.v201406.ReportDownloader;
import com.google.api.ads.common.lib.exception.ValidationException;

import org.apache.log4j.Logger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;

/**
 * This {@link Runnable} implements the core logic to download the report file from the AdWords API.
 *
 * The {@link Collection}s passed to this runner are considered to be synchronized and thread safe.
 * This class has no blocking logic when adding elements to the collections.
 *
 * Also the {@link AdWordsSessionBuilderSynchronizer} is kept by the client class, and should handle
 * all the concurrent threads.
 *
 * @author gustavomoreira@google.com (Gustavo Moreira)
 * @author jtoledo@google.com (Julian Toledo)
 */
public class RunnableDownloader implements Runnable {

  private static final Logger LOGGER = Logger.getLogger(RunnableDownloader.class);
  
  private final AdWordsSession adWordsSession;

  private final int retriesCount;
  private final int backoffInterval;
  private final int bufferSize;

  private final Long cid;
  private final ReportDefinition reportDefinition;

  private final Collection<File> results;

  private Collection<Long> failed;
  private CountDownLatch latch;

  /**
   * C'tor.
   *
   * @param retriesCount the number of retries if an error occur.
   * @param backoffInterval the time to backoff if an error occur to prevent QPS limits.
   * @param bufferSize the size of the buffer used to flush files to the FS.
   * @param cid the costumer ID.
   * @param reportDefinition the report to be downloaded.
   * @param sessionBuilder the builder for the session.
   * @param results the list of results.
   */
  public RunnableDownloader(int retriesCount,
      int backoffInterval,
      int bufferSize,
      Long cid,
      ReportDefinition reportDefinition,
      AdWordsSession adWordsSession,
      Collection<File> results) {
    super();
    this.retriesCount = retriesCount;
    this.backoffInterval = backoffInterval;
    this.bufferSize = bufferSize;
    this.cid = cid;
    this.reportDefinition = reportDefinition;
    this.adWordsSession = adWordsSession;
    this.adWordsSession.setClientCustomerId(String.valueOf(cid));
    this.results = results;
  }

  /**
   * Executes the API call to download the report that was given when this {@code Runnable} was
   * created.
   *
   *  The download blocks this thread until it is finished, and also does the file copying.
   *
   *  There is also a retry logic implemented by this method, where the times retried depends on the
   * value given in the constructor.
   *
   * @see java.lang.Runnable#run()
   */
  @Override
  public void run() {

    try {
      File reportFile = null;
      for (int i = 1; i <= this.retriesCount; i++) {

        try {
          reportFile = this.downloadFileToFileSystem();
          if (reportFile != null) {
            this.handleReportFileResult(reportFile);
            LOGGER.trace(".");
            break;
          }

        } catch (IOException e) {
          System.out.println("\n(Error: " + e.getMessage() + " " + e.getCause() + " Retry# " + i
              + "/" + retriesCount + ")");
        } catch (ValidationException e) {
          System.out.println("\n(Error: " + e.getMessage() + " " + e.getCause() + " Retry# " + i
              + "/" + retriesCount + ")");
        } catch (ReportException e) {
          System.out.println("\n(Error: " + e.getMessage() + " " + e.getCause() + " Retry# " + i
              + "/" + retriesCount + ")");
        } catch (ReportDownloadResponseException e) {
          if (e instanceof DetailedReportDownloadResponseException) {
            DetailedReportDownloadResponseException detailedException =
                (DetailedReportDownloadResponseException) e;
            System.out.println("\n(Error: " + detailedException.getType() + " Trigger:"
                + detailedException.getTrigger() + ")");
            // Not retrying DetailedReportDownloadResponseException errors.
            break;
          } else {
            System.out.println("\n(Error: " + e.getMessage() + " " + e.getCause() + " Retry# " + i
                + "/" + retriesCount + ")");
          }
        }

        // If we haven't succeeded, slow down the rate of requests
        // increasingly to avoid running into rate limits.
        try {
          Thread.sleep(this.backoffInterval * (i + 1));
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          break;
        }
      }

    } finally {
      if (this.latch != null) {
        this.latch.countDown();
      }
    }
  }

  /**
   * Downloads the file from the API, and copies it to the file system.
   *
   * @return the report file downloaded and saved to the FS.
   * @throws ValidationException API validation error
   * @throws ReportException error in the report
   * @throws ReportDownloadResponseException error in the response format
   * @throws IOException error saving file
   * @throws FileNotFoundException concurrent modification to the temporary file
   */
  protected File downloadFileToFileSystem()
      throws ValidationException,
      ReportException,
      ReportDownloadResponseException,
      IOException,
      FileNotFoundException {

    File reportFile = null;

    ReportDownloader reportDownloader = new ReportDownloader(adWordsSession);
    ReportDownloadResponse reportDownloadResponse =
        reportDownloader.downloadReport(this.reportDefinition);

    if (reportDownloadResponse.getHttpStatus() == HttpURLConnection.HTTP_OK) {
      File tempFile = this.createTempFile(this.cid, this.reportDefinition.getReportType().value());
      BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(tempFile));
      copy(reportDownloadResponse.getInputStream(), output);
      output.close();
      reportFile = tempFile;

    } else {
      System.out.println("getHttpStatus():" + reportDownloadResponse.getHttpStatus());
      System.out.println(
          "getHttpResponseMessage():" + reportDownloadResponse.getHttpResponseMessage());
    }
    return reportFile;
  }

  /**
   * @param reportFile the report file.
   */
  private void handleReportFileResult(File reportFile) {

    if (reportFile == null && this.failed != null) {
      this.failed.add(this.cid);
    } else {
      File gUnzipFile = new File(reportFile.getAbsolutePath() + ".gunzip");
      try {
        // gUnzips downloeded file
        FileUtil.gUnzip(reportFile, gUnzipFile);
        this.results.add(reportFile);
      } catch (IOException e) {
        LOGGER.info("Ignoring file (Error when UnZipping): " + reportFile.getAbsolutePath());
      }
    }
  }

  /**
   * Creates a temporary file for storing the results of the report (assumes CSV).
   *
   * @return {@link File} Object referencing the written file.
   * @throws IOException
   */
  private File createTempFile(Long cid, String name) throws IOException {
    return File.createTempFile("reportDownload-" + name + "-" + cid + "-", ".report");
  }

  /**
   * Helper method that copies bytes from the {@code InputStream} to the {@code OutputStream}.
   *
   * @param from Stream to copy from.
   * @param to Stream to copy to.
   * @throws IOException error handling file.
   */
  private void copy(InputStream from, OutputStream to) throws IOException {

    int r;
    byte[] buf = new byte[this.bufferSize];
    while ((r = from.read(buf)) != -1) {
      to.write(buf, 0, r);
    }
  }

  /**
   * @param failed the failed to set
   */
  public void setFailed(Collection<Long> failed) {
    this.failed = failed;
  }

  /**
   * @param latch the latch to set
   */
  public void setLatch(CountDownLatch latch) {
    this.latch = latch;
  }
}
