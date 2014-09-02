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

package com.google.api.ads.adwords.jaxws.extensions.kratu.data;

import com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.mongodb.MongoEntity;
import com.google.api.ads.adwords.jaxws.v201406.mcm.Customer;
import com.google.api.ads.adwords.jaxws.v201406.mcm.ManagedCustomer;
import com.google.common.collect.Lists;

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
@Table(name = "AW_Account")
public class Account implements MongoEntity {

  public static final String __id = "_id";
  public static final String ID = "id";
  public static final String TOP_ACCOUNT_ID = "topAccountId";  

  @Id
  @Column(name = "ID")
  private String id;

  @Column(name = "EXTERNAL_CUSTOMER_ID")
  private Long externalCustomerId;

  @Column(name = "TOP_ACCOUNT_ID")
  protected Long topAccountId;

  @Column(name = "NAME")
  private String name;

  @Column(name = "CURRENCY_CODE")
  private String currencyCode;

  @Column(name = "DATE_TIME_ZONE")
  private String dateTimeZone;

  @Column(name = "IS_CAN_MANAGE_CLIENTS")
  private Boolean isCanManageClients;
  
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
    externalCustomerId = managedCustomer.getCustomerId();
    name = managedCustomer.getName()  + " (" + managedCustomer.getCompanyName() + ")";
    currencyCode = managedCustomer.getCurrencyCode();
    dateTimeZone = managedCustomer.getDateTimeZone();
    isCanManageClients = managedCustomer.isCanManageClients();
    this.topAccountId = topAccountId;
  }
  
  /**
   * Creates a new Account from the API's Customer
   * @param customer
   * @param topAccountId
   */
  Account(Customer customer, Long topAccountId) {
    id = String.valueOf(customer.getCustomerId());
    externalCustomerId = customer.getCustomerId();
    name = customer.getDescriptiveName() + " (" + customer.getCompanyName() + ")";
    currencyCode = customer.getCurrencyCode();
    dateTimeZone = customer.getDateTimeZone();
    isCanManageClients = customer.isCanManageClients();
    this.topAccountId = topAccountId;
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

  public void setIid(String id) {
    this.id = id;
  }

  public Long getTopAccountId() {
    return topAccountId;
  }

  public void setTopAccountId(Long topAccountId) {
    this.topAccountId = topAccountId;
  }
  
  public Long getExternalCustomerId() {
    return externalCustomerId;
  }

  public void setExternalCustomerId(Long externalCustomerId) {
    this.externalCustomerId = externalCustomerId;
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

  public Boolean getIsCanManageClients() {
    return isCanManageClients;
  }

  public void setIsCanManageClients(Boolean isCanManageClients) {
    this.isCanManageClients = isCanManageClients;
  }
}