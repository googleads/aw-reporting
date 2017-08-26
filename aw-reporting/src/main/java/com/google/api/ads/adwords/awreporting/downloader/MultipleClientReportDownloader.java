// Copyright 2016 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.awreporting.downloader;

import com.google.api.ads.adwords.awreporting.ReportProcessingException;
import com.google.api.ads.adwords.awreporting.util.AdWordsSessionUtil;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.client.AdWordsSession.ImmutableAdWordsSession;
import com.google.api.ads.adwords.lib.jaxb.v201708.ReportDefinition;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that concurrently downloads report via the reporting API.
 *
 * An {@link ExecutorService} is created in order to handle all the threads. Each client report
 * download is handled in {@link CallableReportDownloader}. Upon completion of all downloads
 * a {@link Collection} of {@link File} is returned.
 */
public class MultipleClientReportDownloader {
  private static final Logger logger =
      LoggerFactory.getLogger(MultipleClientReportDownloader.class);
  private static final String SEPARATOR = System.getProperty("line.separator");

  private final int numThreads;

  public MultipleClientReportDownloader(int numThreads) {
    this.numThreads = numThreads;
  }

  /**
   * Downloads the specified report for all specified CIDs.
   *
   * @param sessionBuilder the adwords session builder used for downloading reports
   * @param reportDefinition the definition of the report to download
   * @param clientCustomerIds the client customer IDs to download the report for
   * @return Collection of File objects reports have been downloaded to
   */
  public Collection<File> downloadReports(
      AdWordsSession.Builder sessionBuilder,
      ReportDefinition reportDefinition,
      Set<Long> clientCustomerIds)
      throws ReportProcessingException {
    ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
    Stopwatch stopwatch = Stopwatch.createStarted();
    logger.info("Downloading {" + reportDefinition.getReportType() + "} reports...");

    List<Long> taskIds = Lists.newArrayList();
    List<CallableReportDownloader> taskJobs = Lists.newArrayList();
    Map<Long, String> failures = Maps.newHashMap();
    ImmutableAdWordsSession session = null;
    for (Long clientCustomerId : clientCustomerIds) {
      try {
        session = AdWordsSessionUtil.buildImmutableSessionForCid(sessionBuilder, clientCustomerId);
      } catch (ValidationException e) {
        logger.error("Failed to create valid adwords session for CID " + clientCustomerId
            + ", skipping it.");
        failures.put(clientCustomerId, e.getMessage());
        continue;
      }

      taskIds.add(clientCustomerId);
      taskJobs.add(genCallableReportDownloader(session, reportDefinition));
    }

    List<Future<File>> taskResults;
    try {
      //Note that invokeAll() returns results in the same sequence as input tasks.
      taskResults = executorService.invokeAll(taskJobs);
    } catch (InterruptedException e) {
      throw new ReportProcessingException(
        "MultipleClientReportDownloader encounters InterruptedException.", e);
    }

    List<File> results = Lists.newArrayList();
    for (int i = 0; i < taskResults.size(); i++) {
      try {
        results.add(taskResults.get(i).get());
      } catch (ExecutionException e) {
        failures.put(taskIds.get(i), e.getMessage());
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new ReportProcessingException(
            "MultiClientReportDownloader encounters InterruptedException.", e);
      }
    }

    executorService.shutdown();
    stopwatch.stop();

    logger.info("Downloaded reports for " + clientCustomerIds.size() + " accounts in "
        + stopwatch.elapsed(TimeUnit.SECONDS) + " seconds.");
    logger.info("Result: " +  results.size() + " successes, " + failures.size() + " failures.");
    if (!failures.isEmpty()) {
      StringBuilder sb = new StringBuilder("*** Account IDs of download failures ***");
      sb.append(SEPARATOR);
      sb.append(Joiner.on(SEPARATOR).withKeyValueSeparator(": ").join(failures));
      logger.error(sb.toString());
    }

    return results;
  }

  /**
   * Generates a CallableReportDownloader for downloading report in a service thread.
   */
  protected CallableReportDownloader genCallableReportDownloader(
      ImmutableAdWordsSession session, ReportDefinition reportDefinition) {
    return new CallableReportDownloader(session, reportDefinition);
  }
}
