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

package com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.mongodb;

import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.Report;
import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.ReportBase;
import com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister;
import com.google.api.ads.adwords.jaxws.extensions.report.model.util.DateUtil;
import com.google.api.ads.adwords.jaxws.extensions.report.model.util.GsonUtil;
import com.google.gson.Gson;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

import org.joda.time.DateTime;

import java.net.UnknownHostException;
import java.util.ArrayList;
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
public class MongoEntityPersister implements EntityPersister {

  private MongoClient mongoClient;
  private DB db;
  private Gson gson = GsonUtil.getGsonBuilder().create();

  protected MongoEntityPersister(String mongoConnectionUrl, String mongoDataBaseName)
      throws UnknownHostException, MongoException {
    mongoClient = new MongoClient(new MongoClientURI(mongoConnectionUrl));
    db = mongoClient.getDB(mongoDataBaseName);
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister
   *      #get(java.lang.Class, java.util.Map, int, int)
   */
  @Override
  public <T, V> List<T> get(Class<T> classT, Map<String, V> keyValueList, int numToSkip, int limit) {
    BasicDBObject query = new BasicDBObject();
    if (keyValueList != null) {
      for (String key : keyValueList.keySet()) {
        query.put(key, keyValueList.get(key));
      }
    }
    DBCursor cur = getCollection(classT).find(query);

    if (limit > 0) {
      cur.limit(limit);
    }
    if (numToSkip > 0) {
      cur.skip(numToSkip);
    }

    List<T> list = new ArrayList<T>();
    while (cur.hasNext()) {
      DBObject dbObject = cur.next();
      list.add(gson.fromJson(com.mongodb.util.JSON.serialize(dbObject), classT));
    }
    return list;
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister
   *      #get(java.lang.Class, java.util.Map)
   */
  @Override
  public <T, V> List<T> get(Class<T> classT, Map<String, V> keyValueList) {
    return get(classT, keyValueList, 0, 0);
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister
   *      #get(java.lang.Class, java.lang.String, java.lang.Object, int, int)
   */
  @Override
  public <T, V> List<T> get(Class<T> classT, String key, V value, int numToSkip, int limit) {
    Map<String, V> keyValueList = new HashMap<String, V>();
    if (key != null && value != null) {
      keyValueList.put(key, value);
    }
    return get(classT, keyValueList, numToSkip, limit);
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
   * Gets the entity list for the given report class, with a key in those values
   *
   * @param classT the report class
   * @param key the name of the property
   * @param values the values that meet key
   */
  @Override
  public <T, V> List<T> get(Class<T> classT, String key, List<V> values) {

    BasicDBList valuesIds = new BasicDBList();
    valuesIds.addAll(values);
    DBObject inClause = new BasicDBObject("$in", valuesIds);
    DBObject query = new BasicDBObject(key, inClause);
    DBCursor cur = getCollection(classT).find(query);

    List<T> list = new ArrayList<T>();
    while (cur.hasNext()) {
      DBObject dbObject = cur.next();
      list.add(gson.fromJson(com.mongodb.util.JSON.serialize(dbObject), classT));
    }
    return list;
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
  public <T> List<T> get(Class<T> t,
      String key,
      Object value,
      String dateKey,
      Date dateStart,
      Date dateEnd,
      int numToSkip,
      int limit) {
    Map<String, Object> keyValueList = new HashMap<String, Object>();
    if (key != null) {
      keyValueList.put(key, value);
    }
    if (dateKey != null && dateStart != null) {

      if (dateEnd == null) { // One day only
        keyValueList.put(dateKey, DateUtil.formatYearMonthDayNoDash(dateStart));
      } else { // All within the date range
        keyValueList.put(dateKey, BasicDBObjectBuilder.start(
            "$gte", DateUtil.formatYearMonthDayNoDash(dateStart))
            .add("$lte", DateUtil.formatYearMonthDayNoDash(dateEnd)).get());
      }
    }
    return get(t, keyValueList, numToSkip, limit);
  }


  @Override
  public <T> List<T> get(Class<T> t,
      String key,
      Object value,
      String dateKey,
      String dateStart,
      String dateEnd) {
    Map<String, Object> keyValueList = new HashMap<String, Object>();
    if (key != null) {
      keyValueList.put(key, value);
    }
    keyValueList.put(
        dateKey, BasicDBObjectBuilder.start("$gte", dateStart).add("$lte", dateEnd).get());
    return get(t, keyValueList);
  }

  /**
   * Gets the DB Collection for the Object type T
   * 
   * @returns the DBCollection for the Object type T
   */
  private <T> DBCollection getCollection(Class<T> classT) {
    if (db.collectionExists(classT.getCanonicalName())) {
      return  db.getCollection(classT.getCanonicalName());
    } else {
      return db.createCollection(classT.getCanonicalName(), new BasicDBObject());
    }
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister
   *      #save(java.lang.Object)
   */
  @Override
  public <T> T save(T t) {
    T newT = null;
    if (t != null) {
      String jsonObject;
      jsonObject = gson.toJson(t);
      DBObject dbObject = (DBObject) com.mongodb.util.JSON.parse(jsonObject.toString());
      dbObject.put("safe", "true");

      // Set the proper _id from the MongoEntity ID
      if (t instanceof MongoEntity) {
        dbObject.put("_id", ((MongoEntity) t).getId()); 
      }

      getCollection(t.getClass()).save(dbObject, WriteConcern.SAFE);
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
      for (T t : listT) {
        String jsonObject = gson.toJson(t);
        DBObject dbObject = (DBObject) com.mongodb.util.JSON.parse(jsonObject.toString());

        // Set the proper _id from the MongoEntity ID
        if (t instanceof MongoEntity) {
          dbObject.put("_id", ((MongoEntity) t).getId()); 
        }

        getCollection(t.getClass()).save(dbObject);
      }
    }
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister
   *      #remove(java.lang.Object)
   */
  @Override
  public <T> void remove(T t) {
    if (t != null) {
      String jsonObject = gson.toJson(t);
      DBObject dbObject = (DBObject) com.mongodb.util.JSON.parse(jsonObject.toString());
      getCollection(t.getClass()).remove(dbObject);
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
        String jsonObject = gson.toJson(t);
        DBObject dbObject = (DBObject) com.mongodb.util.JSON.parse(jsonObject.toString());
        getCollection(t.getClass()).remove(dbObject);
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
  public <T, V> void remove(Class<T> classT, String key, List<V> values) {
    remove(get(classT, key, values));
  }

  /**
   * Adds a field as a DB index
   *
   * @param classT the entity T class
   */
  @Override
  public <T> void createIndex(Class<T> classT, String key) {
    getCollection(classT).ensureIndex(new BasicDBObject(key, 1));
  }

  /**
   * Adds a list of fields as DB indexes
   *
   * @param classT the entity T class
   */
  @Override
  public <T> void createIndex(Class<T> classT, List<String> keys) {
    BasicDBObject dbObject = new BasicDBObject();
    if (keys != null) {
      for (String key : keys) {
        dbObject.put(key, 1);
      }
    }
    DBCollection dbcollection =  getCollection(classT);
    dbcollection.ensureIndex(dbObject);
    List<DBObject> list = dbcollection.getIndexInfo();
    for (DBObject o : list) {
      System.out.println("index: " + o);
    }
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
  public <T extends Report> List<T> listMonthReports(
      Class<T> classT, long accountId, DateTime dateStart, DateTime dateEnd) {

    Map<String, Object> keyValueList = new HashMap<String, Object>();
    keyValueList.put(Report.ACCOUNT_ID, accountId);
    keyValueList.put(
        ReportBase.MONTH, BasicDBObjectBuilder.start("$gte", dateStart).add("$lte", dateEnd).get());
    return get(classT, keyValueList);
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.
   *      EntityPersister#listMonthReports(Class, long, DateTime, DateTime, int, int)
   */
  @Override
  public <T extends Report> List<T> listMonthReports(Class<T> classT,
      long accountId,
      DateTime dateStart,
      DateTime dateEnd,
      int page,
      int amount) {
    
    Map<String, Object> keyValueList = new HashMap<String, Object>();
    keyValueList.put(Report.ACCOUNT_ID, accountId);
    keyValueList.put(
        ReportBase.MONTH, BasicDBObjectBuilder.start("$gte", dateStart).add("$lte", dateEnd).get());
    return get(classT, keyValueList, page, amount);
  }
}
