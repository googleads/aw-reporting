// Copyright 2011 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.appengine.objectify;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.Report;
import com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister;
import com.google.api.ads.adwords.jaxws.extensions.report.model.util.DateUtil;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.googlecode.objectify.cmd.Query;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * MongoDB implementation of NoSqlStorage
 *
 * @author jtoledo@google.com (Julian Toledo)
 */
@Component
@Qualifier("objectifyEntityPersister")
public class ObjectifyEntityPersister implements EntityPersister {

  protected ObjectifyEntityPersister() {
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister
   *      #get(java.lang.Class, java.util.Map, int, int)
   */
  @Override
  public <T, V> List<T> get(Class<T> classT, Map<String, V> keyValueList, int numToSkip, int limit) {
    Query<T> query = ofy().load().type(classT);
    if (keyValueList != null) {
      
      for (String key : keyValueList.keySet()) {
        query.filter(key, keyValueList.get(key));
      }
    }    
    if (limit > 0) {
      query.limit(limit);
    }
    if (numToSkip > 0) {
      query.offset(numToSkip);
    }
    return query.list();
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister
   *      #get(java.lang.Class, java.util.Map)
   */
  @Override
  public <T, V> List<T> get(Class<T> t, Map<String, V> keyValueList) {
    return get(t, keyValueList, 0, 0);
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister
   *      #get(java.lang.Class, java.lang.String, java.lang.Object, int, int)
   */
  @Override
  public <T, V> List<T> get(Class<T> t, String key, V value, int numToSkip, int limit) {
    Map<String, V> keyValueList = new HashMap<String, V>();
    if (key != null && value != null) {
      keyValueList.put(key, value);
    }
    return get(t, keyValueList, numToSkip, limit);
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister
   *      #get(java.lang.Class, java.lang.String, java.lang.Object)
   */
  @Override
  public <T, V> List<T> get(Class<T> t, String key, V value) {
    return get(t, key, value, 0, 0);
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister
   *      #get(java.lang.Class)
   */
  @Override
  public <T> List<T> get(Class<T> t) {
    return get(t, null, 0, 0);
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister
   *      #get(java.lang.Class, int, int)
   */
  @Override
  public <T> List<T> get(Class<T> t, int numToSkip, int limit) {
    return get(t, null, numToSkip, limit);
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister
   *      #get(java.lang.Class, java.lang.String, java.lang.Object, java.lang.String,
   *      java.util.Date, java.util.Date)
   */
  @Override
  public <T> List<T> get(Class<T> t,
      String key,
      Object value,
      String dateKey,
      Date dateStart,
      Date dateEnd) {
    return get(t, key, value, dateKey, dateStart, dateEnd, 0, 0);
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister
   *      #get(java.lang.Class, java.lang.String, java.lang.Object, java.lang.String,
   *      java.util.Date, java.util.Date, int, int)
   */
  @Override
  public <T> List<T> get(Class<T> classT,
      String key,
      Object value,
      String dateKey,
      Date dateStart,
      Date dateEnd,
      int numToSkip,
      int limit) {    

    Query<T> query = ofy().load().type(classT);
    
    if (key != null) {
      query.filter(key, value);
    } 
    if (dateKey != null && dateStart != null) {

      if (dateEnd == null) { // One day only
        query.filter(dateKey, DateUtil.formatYearMonthDayNoDash(dateStart));
      } else { // All within the date range
        query.filter(dateKey + " >=", DateUtil.formatYearMonthDayNoDash(dateStart));
        query.filter(dateKey + " <=", DateUtil.formatYearMonthDayNoDash(dateEnd));
      }
    }
    
    if (limit > 0) {
      query.offset(limit);
    }
    if (numToSkip > 0) {
      query.offset(numToSkip);
    }
    return query.list();
  }

  @Override
  public <T> List<T> get(Class<T> classT,
      String key,
      Object value,
      String dateKey,
      String dateStart,
      String dateEnd) {

    Query<T> query = ofy().load().type(classT);
    
    if (key != null) {
      query.filter(key, value);
    } 
    if (dateKey != null && dateStart != null) {

      if (dateEnd == null) { // One day only
        query.filter(dateKey, dateStart);
      } else { // All within the date range
        query.filter(dateKey + " >=", dateStart);
        query.filter(dateKey + " <=", dateEnd);
      }
    }
    return query.list();
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister
   *      #save(java.lang.Object)
   */
  @Override
  public <T> T save(T t) {
    T newT = null;
    if (t != null) {
      ofy().save().entity(t);
    }
    return newT;
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister
   *      #save(java.util.List)
   */
  @Override
  public <T> void save(List<T> listT) {
    if (listT != null && listT.size() > 0) {
      ofy().save().entities(listT);
    }
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister
   *      #remove(java.lang.Object)
   */
  @Override
  public <T> void remove(T t) {
    if (t != null) {
      ofy().save().entity(t);
    }
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister
   *      #remove(java.util.Collection)
   */
  @Override
  public <T> void remove(Collection<T> listT) {
    if (listT != null && listT.size() > 0) {
      for (T t : listT) {
        //TODO: add batches
        ofy().delete().entity(t);
      }
    }
  }

  /**
   * Adds a field as a DB index
   *
   * @param classT the entity T class
   */
  @Override
  public <T> void createIndex(Class<T> classT, String key) {
    //TODO: check indexes
  }

  /**
   * Adds a list of fields as DB indexes
   *
   * @param classT the entity T class
   */
  @Override
  public <T> void createIndex(Class<T> classT, List<String> keys) {
    //TODO: check indexes
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister#
   * persistReportEntities(java.util.List)
   */
  @Override
  public void persistReportEntities(List<? extends Report> reportEntities) {

    this.save(reportEntities);
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister#
   * listReports(java.lang.Class)
   */
  @Override
  public <T extends Report> List<T> listReports(Class<T> clazz) {

    return this.get(clazz);
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister#
   * removeReportEntities(java.util.Collection)
   */
  @Override
  public void removeReportEntities(Collection<? extends Report> reportEntities) {

    this.remove(reportEntities);
  }

  /**
   *
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.
   *      EntityPersister#listMonthReports(Class, long, DateTime, DateTime)
   */
  @Override
  public List<? extends Report> listMonthReports(
      Class<? extends Report> clazz, long accountId, DateTime startDate, DateTime endDate) {
    return null;
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.
   *      EntityPersister#listMonthReports(Class, long, DateTime, DateTime, int, int)
   */
  @Override
  public List<? extends Report> listMonthReports(Class<? extends Report> clazz,
      long accountId,
      DateTime startDate,
      DateTime endDate,
      int page,
      int amount) {
    return null;
  }
}
