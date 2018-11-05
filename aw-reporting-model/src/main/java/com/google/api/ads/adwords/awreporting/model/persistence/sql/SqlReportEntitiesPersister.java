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

import com.google.api.ads.adwords.awreporting.model.entities.Report;
import com.google.api.ads.adwords.awreporting.model.persistence.EntityPersister;
import com.google.common.base.Preconditions;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.LockAcquisitionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is the basic implementation of the persistence layer to communicate with a SQL database. The
 * communication is done using a generic {@link SessionFactory}.
 */
@Component
@Qualifier("sqlEntitiesPersister")
public class SqlReportEntitiesPersister implements EntityPersister {

  private static final Logger logger = LoggerFactory.getLogger(SqlReportEntitiesPersister.class);

  private final SessionFactory sessionFactory;
  private final Config config;

  /**
   * Constructor.
   *
   * @param sessionFactory the session factory to communicate with the DB.
   */
  @Autowired
  public SqlReportEntitiesPersister(SessionFactory sessionFactory, Config config) {
    this.sessionFactory =
        Preconditions.checkNotNull(sessionFactory, "SessionFactory can not be null");
    this.config = Preconditions.checkNotNull(config, "Config can not be null");
  }

  /**
   * Persists all the given entities into the DB configured in the {@code SessionFactory}. Specify
   * the following system properties backoff.delay
   */
  @Override
  @Transactional
  @Retryable(
      value = {LockAcquisitionException.class},
      maxAttemptsExpression = "#{ @systemProperties['retryBackoff'] ?: 20}",
      backoff =
          @Backoff(
              delayExpression = "#{ @systemProperties['retryDelay'] ?: 100}",
              maxDelayExpression = "#{ @systemProperties['retryMaxDelay'] ?: 50000 }",
              multiplierExpression = "#{ @systemProperties['retryMultiplier'] ?: 1.5}"))
  public void persistReportEntities(List<? extends Report> reportEntities) {
    int batchFlush = 0;
    Session session = sessionFactory.getCurrentSession();
    FlushMode previousFlushMode = session.getFlushMode();
    session.setFlushMode(FlushMode.MANUAL);

    try {
      for (Report report : reportEntities) {
        report.setRowId();

        session.saveOrUpdate(report);
        batchFlush++;

        if (batchFlush == config.getBatchSize()) {
          session.flush();
          session.clear();
          batchFlush = 0;
        }
      }

      if (batchFlush > 0) {
        session.flush();
        session.clear();
      }
    } catch (NonUniqueObjectException ex) {
      // Github issue 268 & 280
      //   https://github.com/googleads/aw-reporting/issues/268
      //   https://github.com/googleads/aw-reporting/issues/280
      //
      // Currently we allow specifying report definitions which do not include all primary key
      // fields. This leads to cryptic hibernate errors without providing a reasonable
      // resolution strategy.
      //
      // This fix explains where to find the list of primary key fields, but does not address
      // the underlying issue of allowing non-unique rows to be downloaded in the first place.
      //
      // Ideally we would guarantee uniqueness of rows without the user having to specify the
      // PK fields.
      // However, this would be a substantial migration for the AWReporting user base.
      // Instead, we just log a (hopefully) useful error message.
      // Also note that the error message assumes that reportEntities was not empty, because
      // otherwise the exception would not have been thrown.
      logger.error(
          "Duplicate row detected. This is most likely because your report definition does not "
              + "include the primary key fields defined in {}.setRowId(). "
              + "Please add the missing fields and try again.",
          reportEntities.get(0).getClass().getName());
      throw ex;
    } finally {
      session.setFlushMode(previousFlushMode);
    }
  }

  @Override
  @Transactional
  public <T> T save(T t) {
    Session session = sessionFactory.getCurrentSession();
    session.saveOrUpdate(t);
    return t;
  }

  private <T> Criteria createCriteria(Class<T> classT) {
    Session session = sessionFactory.getCurrentSession();
    return session.createCriteria(classT);
  }

  @SuppressWarnings("unchecked")
  @Override
  @Transactional(readOnly = true)
  public <T, V> List<T> get(Class<T> classT, String key, V value) {
    Criteria criteria = createCriteria(classT);
    criteria.add(Restrictions.eq(key, value));
    return criteria.list();
  }

  /** This class is used to specify parameters to the {@link SqlReportEntitiesPersister}. */
  public static class Config {
    private static final int DEFAULT_BATCH_SIZE = 50;

    private int batchSize;

    public Config() {
      this(DEFAULT_BATCH_SIZE);
    }

    public Config(int batchSize) {
      this.batchSize = batchSize;
    }

    /**
     * Retrieve the configured batch size. The default value is defined by DEFAULT_BATCH_SIZE.
     *
     * @return the configured batch size.
     */
    public int getBatchSize() {
      return batchSize;
    }

    /**
     * Configure the batch size. This is the number of operations which will be allowed to
     * accumulate before flushing the transaction to disk. However it does not force the commit of
     * the transaction.
     *
     * @param requestedBatchSize
     */
    public void setBatchSize(int requestedBatchSize) {
      this.batchSize = requestedBatchSize;
    }
  }
}
