//Copyright 2012 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.api.ads.adwords.awreporting.server.entities;

import com.google.api.ads.adwords.awreporting.model.persistence.mongodb.MongoEntity;
import com.google.api.ads.adwords.jaxws.v201406.mcm.Customer;
import com.google.api.ads.adwords.jaxws.v201406.mcm.ManagedCustomer;
import com.google.common.collect.Lists;

import com.googlecode.objectify.annotation.Index;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Account
 * 
 * @author jtoledo@google.com (Julian Toledo)
 */
@Entity
@com.googlecode.objectify.annotation.Entity
@Table(name = "AW_Account")
public class Account implements MongoEntity {

  public static final String ID = "id";
  public static final String TOP_ACCOUNT_ID = "topAccountId";

  @Id
  @com.googlecode.objectify.annotation.Id
  @Column(name = "ID")
  private String id;

  @Index
  @Column(name = "TOP_ACCOUNT_ID")
  private Long topAccountId;

  @Column(name = "LOGIN")
  private String login;

  @Column(name = "COMPANY_NAME")
  private String companyName;

  @Column(name = "NAME")
  private String name;

  @Column(name = "CURRENCY_CODE")
  private String currencyCode;

  @Column(name = "DATE_TIME_ZONE")
  private String dateTimeZone;

  @Column(name = "CAN_MANAGE_CLIENTS")
  private Boolean canManageClients;

  @Column(name = "TIMESTAMP")
  private Date timestamp;

  /**
   * C'tor to satisfy Hibernate.
   */
  public Account() {
  }

  /** 
   * Creates a new Account from the API's ManagedCustomer
   * @param managedCustomer
   * @param topAccountId
   */
  Account(ManagedCustomer managedCustomer, Long topAccountId) {
    id = String.valueOf(managedCustomer.getCustomerId());
    this.topAccountId = topAccountId;
    login = managedCustomer.getLogin();
    companyName = managedCustomer.getCompanyName();
    name = managedCustomer.getName();
    currencyCode = managedCustomer.getCurrencyCode();
    dateTimeZone = managedCustomer.getDateTimeZone();
    canManageClients = managedCustomer.isCanManageClients();
    this.timestamp = new Date();
  }

  /**
   * Creates a new Account from the API's Customer
   * @param customer
   * @param topAccountId
   */
  Account(Customer customer, Long topAccountId) {
    id = String.valueOf(customer.getCustomerId());
    this.topAccountId = topAccountId;
    login = "";
    companyName = customer.getCompanyName();
    name = customer.getDescriptiveName();
    currencyCode = customer.getCurrencyCode();
    dateTimeZone = customer.getDateTimeZone();
    canManageClients = customer.isCanManageClients();
    this.timestamp = new Date();
  }

  /**
   * Resturs a list of Accounts from the API's ManagedCustomer
   * @param list
   * @param topAccountId
   * @return a list of Accounts
   */
  public static List<Account> fromManagedCustomerList(List<ManagedCustomer> list, Long topAccountId) {
    List<Account> returnList = Lists.newArrayList();
    for (ManagedCustomer managedCustomer : list) {
      returnList.add(new Account(managedCustomer, topAccountId));
    }
    return returnList;
  }

  /**
   * Resturs a list of Accounts from the API's Customer
   * @param list
   * @param topAccountId
   * @return a list of Accounts
   */
  public static List<Account> fromCustomerList(List<Customer> list, Long topAccountId) {
    List<Account> returnList = Lists.newArrayList();
    for (Customer customer : list) {
      returnList.add(new Account(customer, topAccountId));
    }
    return returnList;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Long getExternalCustomerId() {
    return Long.valueOf(id.replaceAll("-", ""));
  }

  public Long getTopAccountId() {
    return topAccountId;
  }

  public void setTopAccountId(Long topAccountId) {
    this.topAccountId = topAccountId;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public String getDateTimeZone() {
    return dateTimeZone;
  }

  public void setDateTimeZone(String dateTimeZone) {
    this.dateTimeZone = dateTimeZone;
  }

  public Boolean getCanManageClients() {
    return canManageClients;
  }

  public void setCanManageClients(Boolean canManageClients) {
    this.canManageClients = canManageClients;
  }
}
