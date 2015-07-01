// Copyright 2015 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.awreporting.alerting.action;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import com.google.api.ads.adwords.awreporting.alerting.report.ReportEntry;
import com.google.gson.JsonObject;

/**
 * An alert action implementation that persists alert messages into database.
 * Note that it must provide a constructor that takes a JsonObject parameter, and must not modify report entries.
 * 
 * For simplicity, it just uses JDBC instead of Hibernate 
 * 
 * 
 * The JSON config should look like:
 * {
 *   "RuleClass": "SQLDBPersister",
 *   "DB_driver": "...",
 *   "DB_url": "...",
 *   "DB_username": "...",
 *   "DB_password": "..."
 * }
 * 
 * @author zhuoc@google.com (Zhuo Chen)
 */
public class SQLDBPersister implements AlertAction {
  // config keys for database connection
  private static String DB_DRIVER_TAG = "DB_driver";      // optional
  private static String DB_URL_TAG    = "DB_url";
  private static String DB_USERNAME_TAG = "DB_username";  // optional
  private static String DB_PASSWORD_TAG = "DB_password";  // optional
  
  // default values
  private static String DEFAULT_DB_DRIVER = "com.mysql.jdbc.Driver";
  
  private static int BATCH_INSERTION_SIZE = 100;  // number of insertions in a batch
  
  private static String dbSchemaName = "AWReportingAlerts";
  private static String dbTableName  = "AW_ReportAlerts";
  private static String createTableSql = "CREATE TABLE " + dbTableName +
                                                " (TIMESTAMP datetime,"                   +
                                                " ACCOUNT_ID bigint,"                     +
                                                " ACCOUNT_DESCRIPTIVE_NAME varchar(255)," +
                                                " ACCOUNT_MANAGER_NAME varchar(255),"     +
                                                " ACCOUNT_MANAGER_EMAIL varchar(255),"    +
                                                " ALERT_MESSAGE text not null,"           +
                                                " INDEX ACCOUNT_ID_INDEX (ACCOUNT_ID))";
  private static String insertAlertSql = "INSERT INTO " + dbTableName +
                                         " (TIMESTAMP, ACCOUNT_ID, ACCOUNT_DESCRIPTIVE_NAME, ACCOUNT_MANAGER_NAME, ACCOUNT_MANAGER_EMAIL, ALERT_MESSAGE)" +
                                         " VALUES (?, ?, ?, ?, ?, ?)";
  
  Connection dbConnection;
  PreparedStatement preparedStatement;
  int batchedInsertions;
  
  /**
   * Constructor
   * @param config the JsonObject for the alert action configuration.
   * @throws ClassNotFoundException 
   * @throws SQLException 
   */
  public SQLDBPersister(JsonObject config) throws ClassNotFoundException, SQLException {    
    String driver = DEFAULT_DB_DRIVER;
    if (config.has(DB_DRIVER_TAG)) {
      driver = config.get(DB_DRIVER_TAG).getAsString();
    }
    
    assert (config.has(DB_URL_TAG));
    String url = config.get(DB_URL_TAG).getAsString();
    
    String username = null;
    if (config.has(DB_USERNAME_TAG)) {
      username = config.get(DB_USERNAME_TAG).getAsString();
    }
    
    String password = null;
    if (config.has(DB_PASSWORD_TAG)) {
      password = config.get(DB_PASSWORD_TAG).getAsString();
    }
    
    // Register driver
    Class.forName(driver);
    
    // Open a DB connection
    if (null == username && null == password) {
      dbConnection = DriverManager.getConnection(url);
    }
    else {
      dbConnection = DriverManager.getConnection(url, username, password);
    }
  }
  
  private static Timestamp getCurrentTimestamp() {
    Date now = new Date();
    return new Timestamp(now.getTime());
  }

  /**
   * Initialization action: prepare database table and statement
   */
  @Override
  public void initializeAction() {
    batchedInsertions = 0;
    
    try {
      // Check if table already exists, if not create it
      DatabaseMetaData metaData = dbConnection.getMetaData();
      ResultSet result = metaData.getTables(null, dbSchemaName, dbTableName, null);
      if (!result.next()) {
        Statement statement = dbConnection.createStatement();
        System.out.println(createTableSql);
        statement.executeUpdate(createTableSql);
        statement.close();
      }
      
      // Create prepared statement
      preparedStatement = dbConnection.prepareStatement(insertAlertSql);
      
      // Enable batch insertion
      dbConnection.setAutoCommit(false);
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Process a report entry, and insert information into database
   * 
   * @param entry the report entry to process.
   */
  @Override
  public void processReportEntry(ReportEntry entry) {
    try {
      preparedStatement.setTimestamp(1, getCurrentTimestamp());

      String accountIdStr = entry.getFieldValue("ExternalCustomerId");
      if (null != accountIdStr) {
        long accountId = Long.parseLong(accountIdStr.replaceAll("-", ""));
        preparedStatement.setLong(2, accountId);
      }
      
      String accountName = entry.getFieldValue("AccountDescriptiveName");
      if (null != accountName) {
        preparedStatement.setString(3, accountName);
      }
      
      String accountManagerName = entry.getFieldValue("AccountManagerName");
      if (null != accountManagerName) {
        preparedStatement.setString(4, accountManagerName);
      }
      
      String accountManagerEmail = entry.getFieldValue("AccountManagerEmail");
      if (null != accountManagerEmail) {
        preparedStatement.setString(5, accountManagerEmail);
      }
      
      String alertMessage = entry.getFieldValue("AlertMessage");
      preparedStatement.setString(6, alertMessage);
      
      preparedStatement.addBatch();
      if (batchedInsertions++ >= BATCH_INSERTION_SIZE) {
        preparedStatement.executeBatch();
        dbConnection.commit();
        batchedInsertions = 0;
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Finalization action: execute reminder batch insertions, then close statement and database connection.
   */
  @Override
  public void finalizeAction() {
    try {
      if (batchedInsertions > 0) {
        preparedStatement.executeBatch();
        dbConnection.commit();
        batchedInsertions = 0;
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    finally {
      // Close statement and database connection
      try {
        preparedStatement.close();
      }
      catch (SQLException e) {
        e.printStackTrace();
      }
      
      try {
        dbConnection.close();
      }
      catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
