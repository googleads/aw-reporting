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

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;

import com.google.api.ads.adwords.awreporting.model.entities.AccountPerformanceReport;
import com.google.api.ads.adwords.awreporting.model.entities.DateRangeAndType;
import com.google.api.ads.adwords.awreporting.model.persistence.EntityPersister;
import java.util.Arrays;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.LockAcquisitionException;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Test case for the {@code SqlReportEntitiesPersister} class.
 *
 * <p>Note: Tests in this class do not use assertThrows as this is not available without updating to
 * JUnit5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    classes = {SqlReportEntitiesPersisterTest.Config.class},
    loader = AnnotationConfigContextLoader.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class SqlReportEntitiesPersisterTest {

  @Autowired
  @Qualifier("sqlEntitiesPersister")
  private EntityPersister sqlEntitiesPersister;

  @Autowired private Session session;

  @Autowired private SessionFactory sessionFactory;

  @Autowired private SqlReportEntitiesPersister.Config config;

  @Mock private Criteria criteria;

  private InOrder sequence;

  @Configuration
  @ComponentScan(
      basePackageClasses = {
        com.google.api.ads.adwords.awreporting.model.persistence.sql.SqlReportEntitiesPersister
            .class
      })
  @EnableRetry
  public static class Config {

    @Bean
    public SessionFactory sessionFactory() {
      return Mockito.mock(SessionFactory.class);
    }

    @Bean
    public Session session() {
      return Mockito.mock(Session.class);
    }

    @Bean
    SqlReportEntitiesPersister.Config config() {
      return new SqlReportEntitiesPersister.Config();
    }
  }

  @Before
  public void setUp() {
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(AccountPerformanceReport.class)).thenReturn(criteria);

    sequence = Mockito.inOrder(session);
  }

  @Test
  public void persistReportEntities_savesSingleInstance() {
    AccountPerformanceReport report = generateReport(1);

    sqlEntitiesPersister.persistReportEntities(Arrays.asList(report));

    sequence.verify(session).saveOrUpdate(report);
  }

  @Test
  public void persistReportEntities_savesInBatches() {
    config.setBatchSize(2);

    List<AccountPerformanceReport> reports =
        Arrays.asList(generateReport(1), generateReport(2), generateReport(3));

    sqlEntitiesPersister.persistReportEntities(reports);

    sequence.verify(session).saveOrUpdate(reports.get(0));
    sequence.verify(session).saveOrUpdate(reports.get(1));
    sequence.verify(session).flush();
    sequence.verify(session).clear();

    sequence.verify(session).saveOrUpdate(reports.get(2));
  }

  @Test
  public void persistReportEntities_abortsOnSqlException() {
    Mockito.doThrow(new HibernateException("")).when(session).saveOrUpdate(any());

    try {
      sqlEntitiesPersister.persistReportEntities(Arrays.asList(generateReport(1)));
      fail();
    } catch (HibernateException ex) {
      // expected
    }
  }

  @Test
  public void persistReportEntities_retriesAfterDeadlockThenFails() {
    AccountPerformanceReport report = generateReport(1);

    Mockito.doThrow(new LockAcquisitionException("", null)).when(session).saveOrUpdate(any());

    try {
      sqlEntitiesPersister.persistReportEntities(Arrays.asList(report));
      fail();
    } catch (LockAcquisitionException ex) {
      // expected
    }

    sequence.verify(session, Mockito.times(20)).saveOrUpdate(report);
  }

  @Test
  public void persistReportEntities_retriesAfterDeadlockThenSucceeds() {
    AccountPerformanceReport report = generateReport(1);

    Mockito.doThrow(new LockAcquisitionException("", null))
        .doNothing()
        .when(session)
        .saveOrUpdate(any());

    sqlEntitiesPersister.persistReportEntities(Arrays.asList(report));

    sequence.verify(session, Mockito.times(2)).saveOrUpdate(report);
  }

  @Test
  public void persistReportEntities_setsManualFlushModeAndResetsIt() {
      AccountPerformanceReport report = generateReport(1);

      Mockito.when(session.getHibernateFlushMode()).thenReturn(FlushMode.AUTO);

      sqlEntitiesPersister.persistReportEntities(Arrays.asList(report));

      sequence.verify(session).getHibernateFlushMode();
      sequence.verify(session).setHibernateFlushMode(FlushMode.MANUAL);
      sequence.verify(session).setHibernateFlushMode(FlushMode.AUTO);
  }

  private AccountPerformanceReport generateReport(long accountId) {
    AccountPerformanceReport report = new AccountPerformanceReport(123L, accountId);
    report.setAccountDescriptiveName("testAccount");

    LocalDate today = LocalDate.now();
    DateRangeAndType dateRange = DateRangeAndType.fromValues(today, today, null);
    report.setDateRangeType(dateRange.getTypeStr());
    report.setStartDate(dateRange.getStartDateStr());
    report.setEndDate(dateRange.getEndDateStr());
    return report;
  }
}
