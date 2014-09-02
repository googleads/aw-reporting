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

import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.Report;
import com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * This is the basic implementation of the persistence layer to communicate with a SQL data base.
 *
 * The communication is done using a generic {@link SessionFactory}, which allows this
 * implementation to talk to other data bases easily, but the intention is to specialize this class
 * to communicate in the best way possible to a SQL data base, so don't count in the use of the
 * {@code SessionFactory} when implementing your client class.
 *
 * @author gustavomoreira@google.com (Gustavo Moreira)
 */
@Component
@Qualifier("sqlEntitiesPersister")
public class SqlReportEntitiesPersister implements EntityPersister {
  
  private static final int BATCH_SIZE = 50;

  private SessionFactory sessionFactory;

  /**
   * C'tor
   *
   * @param sessionFactory the session factory to communicate with the DB
   */
  @Autowired
  public SqlReportEntitiesPersister(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  /**
   * Persists all the given entities into the DB configured in the {@code SessionFactory}
   */
  @Override
  @Transactional
  public void persistReportEntities(List<? extends Report> reportEntities) {

    int batchFlush = 0;

    Session session = this.sessionFactory.getCurrentSession();

    for (Report report : reportEntities) {
      report.setId();
      session.saveOrUpdate(report);
      batchFlush++;

      if (batchFlush == BATCH_SIZE) {
        session.flush();
        session.clear();
      }
    }
  }

  /**
   * Lists all the report entities persisted so far.
   *
   * Note that there is no pagination, so be careful when invoking this method.
   */
  @Override
  @Transactional
  @SuppressWarnings("unchecked")
  public <T extends Report> List<T> listReports(Class<T> classT) {

    Criteria criteria = createCriteria(classT);

    return criteria.list();
  }

  /**
   * Creates a new criteria for the current session
   *
   * @param classT the class of the entity
   * @return the criteria for the current session
   */
  private <T> Criteria createCriteria(Class<T> classT) {

    Session session = this.sessionFactory.getCurrentSession();
    return session.createCriteria(classT);
  }

  /**
   * Remove all the entities given in the {@code Collection}, that will be found in the DB.
   */
  @Override
  @Transactional
  public void removeReportEntities(Collection<? extends Report> reportEntities) {

    Session session = this.sessionFactory.getCurrentSession();

    for (Report report : reportEntities) {
      session.delete(report);
    }
  }

  /**
   * Create a paginated query
   *
   * @param classT the entity class
   * @param numToSkip the first result
   * @param limit the max number of results
   * @return the list of results
   */
  private <T> Criteria createPaginatedCriteria(Class<T> classT, int numToSkip, int limit) {

    Criteria criteria = this.createCriteria(classT);
    criteria.setFirstResult(numToSkip);
    criteria.setMaxResults(limit);
    return criteria;
  }

  /**
   * Gets the entity list for the given report class.
   */
  @Override
  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public <T> List<T> get(Class<T> classT) {

    Criteria criteria = this.createCriteria(classT);
    return criteria.list();
  }

  /**
   * Gets the entity list for the given report class.
   *
   * @param classT the report class
   * @param numToSkip the number to begin pagination
   * @param limit the number to limit the pagination
   */
  @Override
  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public <T> List<T> get(Class<T> classT, int numToSkip, int limit) {

    Criteria criteria = this.createPaginatedCriteria(classT, numToSkip, limit);
    return criteria.list();
  }

  /**
   * Gets the entity list for the given report class and with the given value.
   */
  @Override
  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public <T, V> List<T> get(Class<T> classT, String key, V value) {

    Criteria criteria = this.createCriteria(classT);
    criteria.add(Restrictions.eq(key, value));
    return criteria.list();
  }

  /**
   * Gets the entity list for the given report class, with a key in those values
   *
   * @param classT the report class
   * @param key the name of the property
   * @param values the values that meet key
   */
  @Override
  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public <T, V> List<T> get(Class<T> classT, String key, List<V> values) {

    Criteria criteria = this.createCriteria(classT);
    if (key != null) {
      criteria.add(Restrictions.in(key, values));
    }
    return criteria.list();
  }

  /**
   * Gets the entity list for the given report class and with the given value.
   *
   * @param classT the report class
   * @param key the name of the property
   * @param value the value for the property
   * @param numToSkip the number to begin pagination
   * @param limit the number to be paginated
   */
  @Override
  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public <T, V> List<T> get(Class<T> classT, String key, V value, int numToSkip, int limit) {

    Criteria criteria = this.createPaginatedCriteria(classT, numToSkip, limit);
    criteria.add(Restrictions.eq(key, value));
    return criteria.list();
  }

  /**
   * Gets the entity list for the given report class and with the given date range.
   */
  @Override
  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public <T> List<T> get(Class<T> classT,
      String key,
      Object value,
      String dateKey,
      Date dateStart,
      Date dateEnd) {

    Criteria criteria = this.createCriteria(classT);
    if (key != null) {
      criteria.add(Restrictions.eq(key, value));
    }
    criteria.add(Restrictions.ge(dateKey, dateStart));
    criteria.add(Restrictions.le(dateKey, dateEnd));
    return criteria.list();
  }

  /**
   * Gets the entity list for the given report class and key the given range.
   */
  @Override
  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public <T> List<T> get(Class<T> classT,
      String key,
      Object value,
      String dateKey,
      String dateStart,
      String dateEnd) {

    Criteria criteria = this.createCriteria(classT);
    if (key != null) {
      criteria.add(Restrictions.eq(key, value));
    }
    criteria.add(Restrictions.ge(dateKey, dateStart));
    criteria.add(Restrictions.le(dateKey, dateEnd));
    return criteria.list();
  }

  /**
   * Gets the entity list for the given report class and with the given date range.
   */
  @Override
  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public <T> List<T> get(Class<T> classT,
      String key,
      Object value,
      String dateKey,
      Date dateStart,
      Date dateEnd,
      int numToSkip,
      int limit) {

    Criteria criteria = this.createPaginatedCriteria(classT, numToSkip, limit);
    criteria.add(Restrictions.eq(key, value));
    criteria.add(Restrictions.ge(dateKey, dateStart));
    criteria.add(Restrictions.le(dateKey, dateEnd));
    return criteria.list();
  }

  /**
   * Gets the entity list for the given report class and with the given map of values.
   */
  @Override
  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public <T, V> List<T> get(Class<T> classT, Map<String, V> keyValueList) {

    Criteria criteria = this.createCriteria(classT);
    for (Entry<String, V> entry : keyValueList.entrySet()) {
      criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
    }
    return criteria.list();
  }

  /**
   * Gets the entity list for the given report class and with the given map of values.
   */
  @Override
  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public <T, V> List<T> get(Class<T> classT, Map<String, V> keyValueList, int numToSkip, int limit) {

    Criteria criteria = this.createPaginatedCriteria(classT, numToSkip, limit);
    for (Entry<String, V> entry : keyValueList.entrySet()) {
      criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
    }
    return criteria.list();
  }

  /**
   * Removes the report entity.
   */
  @Override
  @Transactional
  public <T> void remove(T t) {

    Session session = this.sessionFactory.getCurrentSession();
    session.delete(t);
  }

  /**
   * Removes the report entity list.
   */
  @Override
  @Transactional
  public <T> void remove(Collection<T> listT) {

    Session session = this.sessionFactory.getCurrentSession();
    for (T t : listT) {
      session.delete(t);
    }
  }

  private <T> Field getField(Class<T> classT, String fieldName)
      throws NoSuchFieldException {
    try {
      return classT.getDeclaredField(fieldName);
    } catch (NoSuchFieldException e) {
      Class<? super T> superClass = classT.getSuperclass();
      if (superClass == null) {
        throw e;
      } else {
        return getField(superClass, fieldName);
      }
    }
  }

  /**
   * Removes the collection of entities by key,value
   *
   * @param classT the entity T class
   * @param key the property name
   * @param value the property value
   */
  @Override
  @Transactional
  public <T, V> void remove(Class<T> classT, String key, V value) {
    remove(get(classT, key, value));
  }

  /**
   * Removes the collection of entities by key,values
   *
   * @param classT the entity T class
   * @param key the property name
   * @param a list of values
   */
  @Override
  @Transactional
  public <T, V> void remove(Class<T> classT, String key, List<V> values) {
    remove(get(classT, key, values));
  }

  /**
   * Create a new Index on the "key" column
   */
  @Override
  @Transactional
  public <T> void createIndex(Class<T> t, String key) {
    
    try {
      Table table = t.getAnnotation(Table.class);
      String tableName = table.name();

      Field property = getField(t, key);
      Column column = property.getAnnotation(Column.class);
      String columnName = column.name();

      String checkIndex = "SELECT COUNT(1) IndexIsThere FROM INFORMATION_SCHEMA.STATISTICS WHERE " +
          "Table_name='" + tableName + "' AND index_name='AW_INDEX_" + columnName + "'";

      String newIndex = "ALTER TABLE  " + tableName + " ADD INDEX " + "AW_INDEX_" + columnName + " ( " + columnName + " )" ;

      Session session = this.sessionFactory.getCurrentSession();
      
      List<?> list = session.createSQLQuery(checkIndex).list();
      if (String.valueOf(list.get(0)).equals("0")) {
        System.out.println( "Creating Index AW_INDEX_" + columnName +" ON " + tableName );
        SQLQuery sqlQuery = session.createSQLQuery(newIndex);
        sqlQuery.executeUpdate();  
      }

    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    }
  }

  /**
   * Create a new composed Index with the the "keys" columns
   */
  @Override
  @Transactional
  public <T> void createIndex(Class<T> t, List<String> keys) {
    try {
      Table table = t.getAnnotation(Table.class);
      String tableName = table.name();

      String columnNames = "";
      String columnIndexName = "";
      int position = 0;
      for (String key : keys) {
        Field property = getField(t, key);
        Column column = property.getAnnotation(Column.class);
        
        if (position++ == 0) {
          columnNames += column.name();
          columnIndexName += column.name();
        } else {
          columnNames += "," + column.name();
          columnIndexName += "_" + column.name();
        }
      }

      String checkIndex = "SELECT COUNT(1) IndexIsThere FROM INFORMATION_SCHEMA.STATISTICS WHERE " +
          "Table_name='" + tableName + "' AND index_name='AW_INDEX_" + columnIndexName + "'";

      String newIndex = "ALTER TABLE  " + tableName + " ADD INDEX " + "AW_INDEX_" + columnIndexName + " ( " + columnNames + " )" ;

      Session session = this.sessionFactory.getCurrentSession();
      
      List<?> list = session.createSQLQuery(checkIndex).list();
      if (String.valueOf(list.get(0)).equals("0")) {
        System.out.println( "Creating Index AW_INDEX_" + columnIndexName +" ON " + tableName );
        SQLQuery sqlQuery = session.createSQLQuery(newIndex);
        sqlQuery.executeUpdate();  
      }

    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    }
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister
   *      #save(com.google.api.ads.adwords.jaxws.extensions.report.model.entities.Report)
   */
  @Override
  @Transactional
  public <T> T save(T t) {

    Session session = this.sessionFactory.getCurrentSession();
    session.saveOrUpdate(t);
    return t;
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister
   *      #save(java.util.List)
   */
  @Override
  @Transactional
  public <T> void save(List<T> reports) {

    Session session = this.sessionFactory.getCurrentSession();
    for (T report : reports) {
      session.saveOrUpdate(report);
    }
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister
   *      #listMonthReports(Class, long, DateTime, DateTime)
   */
  @Override
  @Transactional
  public <T extends Report> List<T> listMonthReports(
      Class<T> classT, long accountId, DateTime startDate, DateTime endDate) {

    Criteria criteria = this.createCriteria(classT);
    return this.listMonthReportsForCriteria(accountId, startDate, endDate, criteria);
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.
   *      EntityPersister#listMonthReports(Class, long, DateTime, DateTime, int, int)
   */
  @Override
  @Transactional
  public <T extends Report> List<T> listMonthReports(Class<T> classT,
      long accountId,
      DateTime startDate,
      DateTime endDate,
      int page,
      int amount) {

    Criteria criteria = this.createPaginatedCriteria(classT, page, amount);
    return this.listMonthReportsForCriteria(accountId, startDate, endDate, criteria);
  }

  /**
   * @param startDate the start date
   * @param endDate the end date
   * @param criteria the criteria
   * @return the list of reports grouped by month
   */
  @SuppressWarnings("unchecked")
  private <T extends Report> List<T> listMonthReportsForCriteria(
      long accountId, DateTime startDate, DateTime endDate, Criteria criteria) {
    criteria.add(Restrictions.isNull("day"));
    criteria.add(Restrictions.isNotNull("month"));
    criteria.add(Restrictions.eq("accountId", accountId));
    criteria.addOrder(Order.asc("month"));
    if (startDate != null) {
      criteria.add(Restrictions.ge("month", startDate.toDate()));
    }
    if (endDate != null) {
      criteria.add(Restrictions.le("month", endDate.toDate()));
    }
    return criteria.list();
  }
}
