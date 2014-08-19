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

package com.google.api.ads.adwords.jaxws.extensions.processors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

import com.google.api.ads.adwords.jaxws.extensions.authentication.Authenticator;
import com.google.api.ads.adwords.jaxws.extensions.report.model.csv.CsvReportEntitiesMapping;
import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.Report;
import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.ReportAccount;
import com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister;
import com.google.api.ads.adwords.jaxws.extensions.util.DynamicPropertyPlaceholderConfigurer;
import com.google.api.ads.adwords.lib.jaxb.v201406.DownloadFormat;
import com.google.api.ads.adwords.lib.jaxb.v201406.ReportDefinition;
import com.google.api.ads.adwords.lib.jaxb.v201406.ReportDefinitionDateRangeType;
import com.google.api.ads.adwords.lib.jaxb.v201406.ReportDefinitionReportType;
import com.google.api.ads.adwords.lib.jaxb.v201406.Selector;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Test case for the {@link ReportProcessor} class.
 *
 * @author jtoledo@google.com (Julian Toledo)
 */
public class ReportProcessorTest {

  @Mock
  private ReportProcessor reportProcessor;
  
  @Mock
  private EntityPersister mockedEntitiesPersister;

  @Mock
  private Authenticator authenticator;
  
  private Properties properties;
  
  private ApplicationContext appCtx;
  
  private static final Set<Long> accountIds = ImmutableSet.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);
  
  private final ReportAccount reportAccount = new ReportAccount();
  private final List<ReportAccount> listAccounts = ImmutableList.of(reportAccount);
  
  private static final String dateStart = "20140101";
  private static final String dateEnd = "20140131";

  @SuppressWarnings("unchecked")
  @Before
  public <R extends Report> void setUp() throws Exception {
    Resource resource = new FileSystemResource("src/test/resources/aw-report-sample.properties");
    DynamicPropertyPlaceholderConfigurer.setDynamicResource(resource);
    properties = PropertiesLoaderUtils.loadProperties(resource);
    appCtx = new ClassPathXmlApplicationContext("classpath:aw-report-test-beans.xml");

    MockitoAnnotations.initMocks(this);
    doCallRealMethod().when(reportProcessor).setCsvReportEntitiesMapping(
        any(CsvReportEntitiesMapping.class));

    doCallRealMethod().when(reportProcessor).setPersister(
        any(EntityPersister.class));

    doCallRealMethod().when(reportProcessor).getReportDefinition(
        any(ReportDefinitionReportType.class), any(ReportDefinitionDateRangeType.class),
        anyString(), anyString(), any(Properties.class));
    
    doCallRealMethod().when(reportProcessor).instantiateReportDefinition(
        any(ReportDefinitionReportType.class), any(ReportDefinitionDateRangeType.class), any(Selector.class));

    reportProcessor.setCsvReportEntitiesMapping(appCtx.getBean(CsvReportEntitiesMapping.class));
    reportProcessor.setPersister(mockedEntitiesPersister);
    reportProcessor.setAuthentication(authenticator);

    reportAccount.setAccountDescriptiveName("TestAccount");
    reportAccount.setAccountId(123L);
    reportAccount.setMonth("20140101");
    reportAccount.setCost(new BigDecimal(456L));
    reportAccount.setImpressions(99L);
    reportAccount.setClicks(999L);
    reportAccount.setAvgCpc(new BigDecimal(12L));
    reportAccount.setAvgCpm(new BigDecimal(4L));

    when(mockedEntitiesPersister.listMonthReports(
        (Class<ReportAccount>) anyObject(),
        anyLong(), any(DateTime.class), any(DateTime.class))).thenReturn(listAccounts);

    when(reportProcessor.retrieveAccountIds(anyString(), anyString())).thenReturn(accountIds);
  }

  @Test
  public void testGetReportDefinition() throws Exception { 
    ReportDefinition reportDefinition = reportProcessor.getReportDefinition(
        ReportDefinitionReportType.ACCOUNT_PERFORMANCE_REPORT,
        ReportDefinitionDateRangeType.CUSTOM_DATE, dateStart, dateEnd, properties);

    assertEquals(reportDefinition.getReportName().split(" ")[0],
        ReportProcessor.REPORT_PREFIX + ReportDefinitionReportType.ACCOUNT_PERFORMANCE_REPORT);
    assertEquals(reportDefinition.getDateRangeType(), ReportDefinitionDateRangeType.CUSTOM_DATE);
    assertEquals(reportDefinition.getDownloadFormat(), DownloadFormat.GZIPPED_CSV);
    assertEquals(reportDefinition.isIncludeZeroImpressions(), false);
  }
}
