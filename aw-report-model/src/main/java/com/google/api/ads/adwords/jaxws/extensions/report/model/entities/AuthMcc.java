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

package com.google.api.ads.adwords.jaxws.extensions.report.model.entities;

import com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.mongodb.MongoEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class for MCC Authentication.
 *
 * @author jtoledo@google.com (Julian Toledo)
 * @author gustavomoreira@google.com (Gustavo Moreira)
 */
@Entity
@com.googlecode.objectify.annotation.Entity
@Table(name = "AW_AuthMcc")
public class AuthMcc implements MongoEntity {

  public static final String TOP_ACCOUNT_ID = "topAccountId";

  @Id
  @com.googlecode.objectify.annotation.Id
  @Column(name = "TOP_ACCOUNT_ID")
  private String topAccountId;

  @Column(name = "TOP_ACCOUNT_NAME")
  private String topAccountName;
  
  @Column(name = "AUTH_TOKEN", length = 1024)
  private String authToken;
  
  @Column(name = "SCOPE", length = 1024)
  private String scope;

  /**
   * Constructor to satisfy Hibernate.
   */
  public AuthMcc() {}

  /**
   * @param topAccountId the top level MCC account ID.
   * @param authToken the authentication Token.
   */
  public AuthMcc(String topAccountId, String topAccountName, String authToken, String scope) {
    this.topAccountId = topAccountId;
    this.topAccountName = topAccountName;
    this.authToken = authToken;
    this.scope = scope;
  }

  public String getId() {
    return topAccountId;
  }

  public String getTopAccountId() {
    return topAccountId;
  }

  public void setTopAccountId(String topAccountId) {
    this.topAccountId = topAccountId;
  }

  public String getTopAccountName() {
    return topAccountName;
  }

  public void setTopAccountName(String topAccountName) {
    this.topAccountName = topAccountName;
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken = authToken;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }
}

