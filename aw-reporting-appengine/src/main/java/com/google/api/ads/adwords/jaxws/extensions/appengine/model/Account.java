package com.google.api.ads.adwords.jaxws.extensions.appengine.model;

import com.google.api.ads.adwords.jaxws.v201309.mcm.ManagedCustomer;
import com.google.api.ads.common.lib.exception.ValidationException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Account {

  @Id
  protected Long accountId;

  protected String login;

  protected String companyName;
  
  protected String name;

  protected Boolean canManageClients;

  protected String currencyCode;

  protected String dateTimeZone;

  protected Long topAccountId;

  protected Date timestamp;
  
  public Account() {
    
  }

  public Account(Long topAccountId, ManagedCustomer account) {
    this.accountId = account.getCustomerId();
    this.login = account.getLogin();
    this.companyName = account.getCompanyName();
    this.canManageClients = account.isCanManageClients();
    this.currencyCode = account.getCurrencyCode();
    this.dateTimeZone = account.getDateTimeZone();
    this.name = account.getName();

    this.topAccountId = topAccountId;
    this.timestamp = new Date();
  }

  public static List<Account> fromApiAccounts(
      List<ManagedCustomer> apiAccounts, Long topAccountId)
          throws ValidationException {
    List<Account> accounts = new ArrayList<Account>(apiAccounts.size());
    for (ManagedCustomer apiAccount : apiAccounts) {
      accounts.add(new Account(topAccountId, apiAccount));
    }
    return accounts;
  }

  public Long getTopAccountId() {
    return topAccountId;
  }

  public void setTopAccountId(Long topAccountId) {
    this.topAccountId = topAccountId;
  }

  public Long getAccountId() {
    return accountId;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
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

  public Boolean getCanManageClients() {
    return canManageClients;
  }

  public void setCanManageClients(Boolean canManageClients) {
    this.canManageClients = canManageClients;
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

  public Date getTimestamp() {
    return timestamp;
  }
}
