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

package com.google.api.ads.adwords.awreporting.model.persistence.sql;

import static org.junit.Assert.assertThrows;
import static org.mockito.Matchers.any;

import com.google.api.ads.adwords.awreporting.model.entities.AccountPerformanceReport;
import com.google.api.ads.adwords.awreporting.model.entities.DateRangeAndType;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.exception.LockAcquisitionException;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Test case for the {@code SqlReportEntitiesPersister} class.
 */
@RunWith(JUnit4.class)
public class SqlReportEntitiesPersisterTest {

  private SqlReportEntitiesPersister reportEntitiesPersister;

  @Mock private Session session;

  @Mock private SessionFactory sessionFactory;

  @Mock private Criteria criteria;

  private InOrder sequence;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(AccountPerformanceReport.class)).thenReturn(criteria);

    sequence = Mockito.inOrder(session);

    reportEntitiesPersister = new SqlReportEntitiesPersister(sessionFactory);
    reportEntitiesPersister.setRetryDelay(Duration.ZERO);
  }

  @Test
  public void SqlReportEntitiesPersister_persistReportEntities_savesSingleInstance() {
    AccountPerformanceReport report = generateReport();

    reportEntitiesPersister.persistReportEntities(Arrays.asList(report));

    sequence.verify(session).saveOrUpdate(report);
  }

  @Test
  public void SqlReportEntitiesPersister_persistReportEntities_savesInBatches() {
    reportEntitiesPersister.setBatchSize(1);

    List<AccountPerformanceReport> reports =
        Arrays.asList(generateReport(), generateReport(), generateReport());

    reportEntitiesPersister.persistReportEntities(reports);

    sequence.verify(session).saveOrUpdate(reports.get(0));
    sequence.verify(session).flush();
    sequence.verify(session).clear();

    sequence.verify(session).saveOrUpdate(reports.get(1));
    sequence.verify(session).flush();
    sequence.verify(session).clear();

    sequence.verify(session).saveOrUpdate(reports.get(2));
    sequence.verify(session).flush();
    sequence.verify(session).clear();
  }

  @Test
  public void SqlReportEntitiesPersister_persistReportEntities_abortsOnSqlException() {
    Mockito.doThrow(new HibernateException("")).when(session).saveOrUpdate(any());

    assertThrows(
        HibernateException.class,
        () -> reportEntitiesPersister.persistReportEntities(Arrays.asList(generateReport())));
  }

  @Test
  public void SqlReportEntitiesPersister_persistReportEntities_retriesAfterDeadlockThenFails() {
    AccountPerformanceReport report = generateReport();

    Mockito.doThrow(new LockAcquisitionException("", null)).when(session).saveOrUpdate(any());

    assertThrows(
        LockAcquisitionException.class,
        () -> reportEntitiesPersister.persistReportEntities(Arrays.asList(report)));

    sequence.verify(session, Mockito.times(5)).saveOrUpdate(report);
  }

  @Test
  public void SqlReportEntitiesPersister_persistReportEntities_retriesAfterDeadlockThenSucceeds() {
    AccountPerformanceReport report = generateReport();

    Mockito.doThrow(new LockAcquisitionException("", null))
        .doNothing()
        .when(session)
        .saveOrUpdate(any());

    reportEntitiesPersister.persistReportEntities(Arrays.asList(report));

    sequence.verify(session, Mockito.times(2)).saveOrUpdate(report);
  }

  private AccountPerformanceReport generateReport() {
    AccountPerformanceReport report = new AccountPerformanceReport(123L, 456L);
    report.setAccountDescriptiveName("testAccount");

    LocalDate today = LocalDate.now();
    DateRangeAndType dateRange = DateRangeAndType.fromValues(today, today, null);
    report.setDateRangeType(dateRange.getTypeStr());
    report.setStartDate(dateRange.getStartDateStr());
    report.setEndDate(dateRange.getEndDateStr());
    return report;
  }
}
