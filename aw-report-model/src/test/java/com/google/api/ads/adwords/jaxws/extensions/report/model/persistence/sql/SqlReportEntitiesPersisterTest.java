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

package com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.sql;

import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.AuthMcc;
import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.Report;
import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.ReportAccount;
import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.ReportBase;
import com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister;
import com.google.api.ads.adwords.jaxws.extensions.report.model.util.DateUtil;
import com.google.common.collect.Lists;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Test case for the {@code SqlReportEntitiesPersister} class.
 *
 * @author jtoledo@google.com (Julian Toledo)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:aw-report-model-test-beans.xml")
public class SqlReportEntitiesPersisterTest {

  @Autowired
  private EntityPersister reportEntitiesPersister;

  @Before
  @After
  public void cleanDB() {

    List<ReportAccount> reports = this.reportEntitiesPersister.listReports(ReportAccount.class);
    this.reportEntitiesPersister.remove(reports);
    
    List<AuthMcc> authMccs = this.reportEntitiesPersister.get(AuthMcc.class);
    this.reportEntitiesPersister.remove(authMccs);
  }

  /**
   * Tests the persistence and retrieval of Report Entities.
   */
  @Test
  public void testTokenPersistence() {
    ReportBase report = new ReportAccount(123L, 456L);
    report.setAccountDescriptiveName("testAccount");
    report.setId();
    List<Report> reportList = Lists.newArrayList();
    reportList.add(report);
    reportEntitiesPersister.persistReportEntities(reportList);

    List<ReportAccount> reportAccountList =
        reportEntitiesPersister.listReports(ReportAccount.class);
    Assert.assertNotNull(reportAccountList);
    Assert.assertTrue(reportAccountList.size() == 1);
    Assert.assertTrue(reportAccountList.get(0).getTopAccountId().equals(123L));
    Assert.assertTrue(reportAccountList.get(0).getAccountId().equals(456L));
    Assert.assertTrue(reportAccountList.get(0).getAccountDescriptiveName().equals("testAccount"));

    report = new ReportAccount(789L, 456L);
    report.setAccountDescriptiveName("updatedTestAccount");
    reportList = Lists.newArrayList();
    reportList.add(report);
    reportEntitiesPersister.persistReportEntities(reportList);

    reportAccountList = reportEntitiesPersister.listReports(ReportAccount.class);
    Assert.assertNotNull(reportAccountList);
    Assert.assertTrue(reportAccountList.size() == 1);
    Assert.assertTrue(reportAccountList.get(0).getTopAccountId().equals(789L));
    Assert.assertTrue(reportAccountList.get(0).getAccountId().equals(456L));
    Assert.assertTrue(
        reportAccountList.get(0).getAccountDescriptiveName().equals("updatedTestAccount"));

    this.reportEntitiesPersister.remove(reportAccountList);
  }

  /**
   * Tests the gets methods.
   */
  @Test
  public void testGet() {

    ReportBase report1 = new ReportAccount(123L, 456L);
    report1.setAccountDescriptiveName("testAccount1");
    report1.setAdNetwork("test2");
    report1.setId();
    ReportBase report2 = new ReportAccount(124L, 4567L);
    report2.setAccountDescriptiveName("testAccount2");
    report2.setAdNetwork("test2");
    report2.setId();
    List<Report> reportList = Lists.newArrayList();
    reportList.add(report1);
    reportList.add(report2);
    reportEntitiesPersister.persistReportEntities(reportList);

    List<ReportAccount> list = this.reportEntitiesPersister.get(ReportAccount.class, 0, 1);
    Assert.assertEquals(1, list.size());
    Assert.assertTrue(list.contains(report1) || list.contains(report2));

    list = this.reportEntitiesPersister.get(ReportAccount.class);
    Assert.assertEquals(2, list.size());

    Assert.assertTrue(list.contains(report1));
    Assert.assertTrue(list.contains(report2));

    this.reportEntitiesPersister.remove(list);
  }

  /**
   * Tests the gets methods with values for properties.
   */
  @Test
  public void testGetWithValue() {

    ReportBase report1 = new ReportAccount(123L, 456L);
    report1.setAccountDescriptiveName("testAccount1");
    report1.setAdNetwork("test2");
    report1.setId();
    ReportBase report2 = new ReportAccount(124L, 4567L);
    report2.setAccountDescriptiveName("testAccount2");
    report2.setAdNetwork("test2");
    report2.setId();
    List<Report> reportList = Lists.newArrayList();
    reportList.add(report1);
    reportList.add(report2);
    reportEntitiesPersister.persistReportEntities(reportList);

    List<ReportAccount> list =
        this.reportEntitiesPersister.get(ReportAccount.class, "adNetwork", "test2", 0, 1);
    Assert.assertEquals(1, list.size());
    Assert.assertTrue(list.contains(report1) || list.contains(report2));

    list = this.reportEntitiesPersister.get(ReportAccount.class, "adNetwork", "test2", 0, 3);
    Assert.assertEquals(2, list.size());
    Assert.assertTrue(list.contains(report1));
    Assert.assertTrue(list.contains(report2));

    list = this.reportEntitiesPersister.get(ReportAccount.class, "accountDescriptiveName",
        "testAccount1");
    Assert.assertEquals(1, list.size());
    Assert.assertTrue(list.contains(report1));

    list = this.reportEntitiesPersister.get(ReportAccount.class, "accountDescriptiveName",
        "testAccount2");
    Assert.assertEquals(1, list.size());
    Assert.assertTrue(list.contains(report2));

    list = this.reportEntitiesPersister.get(ReportAccount.class, "adNetwork", "test2");
    Assert.assertEquals(2, list.size());
    Assert.assertTrue(list.contains(report1));
    Assert.assertTrue(list.contains(report2));

    this.reportEntitiesPersister.remove(list);
  }

  /**
   * Tests the gets methods with values for dates.
   */
  @Test
  public void testGetWithDate() {

    DateTime start = new DateTime(2013, 3, 9, 10, 10);
    DateTime end = new DateTime(2013, 3, 14, 10, 10);

    ReportBase report1 = new ReportAccount(123L, 456L);
    report1.setAccountDescriptiveName("testAccount1");
    report1.setAdNetwork("test2");
    report1.setDay(new DateTime(2013, 3, 10, 10, 10));
    report1.setId();
    ReportBase report2 = new ReportAccount(124L, 4567L);
    report2.setAccountDescriptiveName("testAccount2");
    report2.setAdNetwork("test2");
    report2.setDay(new DateTime(2013, 3, 13, 10, 10));
    report2.setId();
    List<Report> reportList = Lists.newArrayList();
    reportList.add(report1);
    reportList.add(report2);
    reportEntitiesPersister.persistReportEntities(reportList);

    List<ReportAccount> list = this.reportEntitiesPersister.get(ReportAccount.class,
        "adNetwork",
        "test2",
        "day",
        start.toDate(),
        end.toDate());
    Assert.assertEquals(2, list.size());
    Assert.assertTrue(list.contains(report1));
    Assert.assertTrue(list.contains(report2));

    list = this.reportEntitiesPersister.get(ReportAccount.class,
        "adNetwork",
        "test2",
        "day",
        start.plusDays(1).toDate(),
        end.minusDays(1).toDate(),
        0,
        1);
    Assert.assertEquals(1, list.size());
    Assert.assertTrue(list.contains(report1) || list.contains(report2));

    list = this.reportEntitiesPersister.get(ReportAccount.class,
        "adNetwork",
        "test2",
        "day",
        start.plusDays(1).toDate(),
        end.minusDays(2).toDate(),
        0,
        1);
    Assert.assertEquals(1, list.size());
    Assert.assertTrue(list.contains(report1));

    list = this.reportEntitiesPersister.get(ReportAccount.class,
        "adNetwork",
        "test2",
        "day",
        start.plusDays(2).toDate(),
        end.minusDays(1).toDate(),
        0,
        1);
    Assert.assertEquals(1, list.size());
    Assert.assertTrue(list.contains(report2));

    list = this.reportEntitiesPersister.get(ReportAccount.class,
        "adNetwork",
        "test2",
        "day",
        start.plusDays(2).toDate(),
        end.minusDays(2).toDate(),
        0,
        1);
    Assert.assertEquals(0, list.size());

    list = this.reportEntitiesPersister.get(ReportAccount.class,
        "adNetwork",
        "test2",
        "day",
        start.plusDays(1).toDate(),
        end.minusDays(1).toDate());
    Assert.assertEquals(2, list.size());
    Assert.assertTrue(list.contains(report1));
    Assert.assertTrue(list.contains(report2));

    this.reportEntitiesPersister.remove(list);
  }

  /**
   * Tests the gets methods with a map of values for properties.
   */
  @Test
  public void testGetWithValueMap() {

    ReportBase report1 = new ReportAccount(123L, 456L);
    report1.setAccountDescriptiveName("testAccount1");
    report1.setAdNetwork("test2");
    report1.setId();
    ReportBase report2 = new ReportAccount(124L, 4567L);
    report2.setAccountDescriptiveName("testAccount2");
    report2.setAdNetwork("test2");
    report2.setId();
    List<Report> reportList = Lists.newArrayList();
    reportList.add(report1);
    reportList.add(report2);
    reportEntitiesPersister.persistReportEntities(reportList);

    Map<String, String> valueMap = new HashMap<String, String>();
    valueMap.put("adNetwork", "test2");

    List<ReportAccount> list =
        this.reportEntitiesPersister.get(ReportAccount.class, valueMap, 0, 1);
    Assert.assertEquals(1, list.size());
    Assert.assertTrue(list.contains(report1) || list.contains(report2));

    valueMap = new HashMap<String, String>();
    valueMap.put("adNetwork", "test2");
    valueMap.put("accountDescriptiveName", "testAccount1");
    list = this.reportEntitiesPersister.get(ReportAccount.class, valueMap, 0, 3);
    Assert.assertEquals(1, list.size());
    Assert.assertTrue(list.contains(report1));

    valueMap = new HashMap<String, String>();
    valueMap.put("adNetwork", "test2");
    valueMap.put("accountDescriptiveName", "testAccount2");
    list = this.reportEntitiesPersister.get(ReportAccount.class, valueMap);
    Assert.assertEquals(1, list.size());
    Assert.assertTrue(list.contains(report2));

    valueMap = new HashMap<String, String>();
    valueMap.put("accountDescriptiveName", "testAccount1");
    list = this.reportEntitiesPersister.get(ReportAccount.class, valueMap);
    Assert.assertEquals(1, list.size());
    Assert.assertTrue(list.contains(report1));

    valueMap = new HashMap<String, String>();
    valueMap.put("adNetwork", "test2");
    list = this.reportEntitiesPersister.get(ReportAccount.class, valueMap);
    Assert.assertEquals(2, list.size());
    Assert.assertTrue(list.contains(report1));
    Assert.assertTrue(list.contains(report2));

    this.reportEntitiesPersister.remove(list);
  }

  /**
   * Tests the list of reports grouped by month
   */
  @Test
  public void testListMonthReports() {

    DateTime start = new DateTime(2013, 5, 4, 0, 0, 0, 0);
    DateTime end = new DateTime(2013, 7, 4, 0, 0, 0, 0);

    ReportBase report1 = new ReportAccount(123L, 456L);
    report1.setAccountDescriptiveName("testAccount1");
    report1.setAdNetwork("test1");
    report1.setMonth(start);
    report1.setDateStart(DateUtil.formatYearMonthDay(start));
    report1.setDateEnd(DateUtil.formatYearMonthDay(start));
    report1.setId();

    ReportBase report2 = new ReportAccount(124L, 456L);
    report2.setAccountDescriptiveName("testAccount2");
    report2.setAdNetwork("test2");
    report2.setMonth(start.plusMonths(1));
    report2.setDateStart(DateUtil.formatYearMonthDay(start.plusMonths(1)));
    report2.setDateEnd(DateUtil.formatYearMonthDay(start.plusMonths(1)));
    report2.setId();

    ReportBase report3 = new ReportAccount(125L, 456L);
    report3.setAccountDescriptiveName("testAccount3");
    report3.setAdNetwork("test3");
    report3.setMonth(start.plusMonths(10));
    report3.setDateStart(DateUtil.formatYearMonthDay(start.plusMonths(10)));
    report3.setDateEnd(DateUtil.formatYearMonthDay(start.plusMonths(10)));
    report3.setId();

    List<Report> reportList = Lists.newArrayList();
    reportList.add(report1);
    reportList.add(report2);
    reportList.add(report3);

    reportEntitiesPersister.persistReportEntities(reportList);

    List<? extends Report> reports =
        this.reportEntitiesPersister.listMonthReports(ReportAccount.class, 456L, start, end, 0, 20);

    Assert.assertEquals(2, reports.size());
    Assert.assertEquals(report1, reports.get(0));
    Assert.assertEquals(report2, reports.get(1));

    this.reportEntitiesPersister.remove(reports);
  }
  
  @Test
  public void testGetAndRemoveWithListValues() {
    List<AuthMcc> authMccs = Lists.newArrayList();
    authMccs.add(new AuthMcc("1", "Name1", "Token", "OAuth"));
    authMccs.add(new AuthMcc("2", "Name2", "Token", "OAuth"));
    authMccs.add(new AuthMcc("3", "Name3", "Token", "OAuth"));

    reportEntitiesPersister.save(authMccs);

    List<AuthMcc> authMccsResult = reportEntitiesPersister.get(
        AuthMcc.class, AuthMcc.TOP_ACCOUNT_ID, Lists.newArrayList("1", "2", "3"));

    Assert.assertEquals(authMccsResult.size(), 3);
    Assert.assertEquals(authMccsResult.get(0).getTopAccountName(), "Name1");
    Assert.assertEquals(authMccsResult.get(1).getTopAccountName(), "Name2");
    Assert.assertEquals(authMccsResult.get(2).getTopAccountName(), "Name3");
    
    reportEntitiesPersister.remove(AuthMcc.class, AuthMcc.TOP_ACCOUNT_ID, Lists.newArrayList("1", "2", "3"));
    
    Assert.assertEquals(reportEntitiesPersister.get(AuthMcc.class).size(), 0);
  }
}
