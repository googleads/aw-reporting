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

package com.google.api.ads.adwords.awreporting.model.persistence.mongodb;

import com.google.api.ads.adwords.awreporting.model.entities.Report;
import com.google.api.ads.adwords.awreporting.model.persistence.EntityPersister;
import com.google.api.ads.adwords.awreporting.model.util.GsonUtil;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MongoDB implementation of NoSqlStorage.
 */
public class MongoEntityPersister implements EntityPersister {

  private MongoClient mongoClient;
  private DB db;
  private Gson gson = GsonUtil.createGson();

  /**
   * Constructor that opens MongoDB client from URL, and retrives specified database.
   *
   * @param mongoConnectionUrl the Mongo connection url
   * @param mongoDataBaseName the Mongo database name
   */
  public MongoEntityPersister(String mongoConnectionUrl, String mongoDataBaseName)
      throws UnknownHostException, MongoException {
    mongoClient = new MongoClient(new MongoClientURI(mongoConnectionUrl));
    db = mongoClient.getDB(mongoDataBaseName);
  }
  
  @Override
  public void persistReportEntities(List<? extends Report> reportEntities) {
    if (reportEntities != null && reportEntities.size() > 0) {
      for (Report report : reportEntities) {
        String jsonObject = gson.toJson(report);
        DBObject dbObject = (DBObject) com.mongodb.util.JSON.parse(jsonObject.toString());

        // Set the proper _id from the MongoEntity RowID
        dbObject.put("_id", ((MongoEntity) report).getRowId());

        getCollection(report.getClass()).save(dbObject);
      }
    }
  }

  @Override
  public <T> T save(T t) {
    T newT = null;
    if (t != null) {
      String jsonObject = gson.toJson(t);
      DBObject dbObject = (DBObject) com.mongodb.util.JSON.parse(jsonObject.toString());
      dbObject.put("safe", "true");

      // Set the proper _id from the MongoEntity RowID
      if (t instanceof MongoEntity) {
        dbObject.put("_id", ((MongoEntity) t).getRowId());
      }

      getCollection(t.getClass()).save(dbObject, WriteConcern.SAFE);
    }
    return newT;
  }
  
  @Override
  public <T, V> List<T> get(Class<T> classT, String key, V value) {
    BasicDBObject query = new BasicDBObject();
    if (key != null && value != null) {
      query.put(key, value);
    }
    
    DBCursor cur = getCollection(classT).find(query);
    List<T> list = new ArrayList<T>();
    while (cur.hasNext()) {
      DBObject dbObject = cur.next();
      list.add(gson.fromJson(com.mongodb.util.JSON.serialize(dbObject), classT));
    }
    
    return list;
  }
  
  private <T> DBCollection getCollection(Class<T> classT) {
    String[] fragments = classT.getCanonicalName().split("\\.");
    String collectionName = fragments[fragments.length -1];

    if (db.collectionExists(collectionName)) {
      return  db.getCollection(collectionName);
    } else {
      return db.createCollection(collectionName, new BasicDBObject());
    }
  }
}
