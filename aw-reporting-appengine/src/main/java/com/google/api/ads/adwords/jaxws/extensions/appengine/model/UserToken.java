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

package com.google.api.ads.adwords.jaxws.extensions.appengine.model;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class UserToken {

  @Id
  private String userId;

  private Long topAccountId;

  private String oauthToken;

  private String email;

  protected Date timestamp;

  public UserToken() {
    timestamp = new Date();
  }

  public UserToken(String userId, Long topAccountId, String email, String oauthToken) {
    this.userId = userId;
    this.topAccountId = topAccountId;
    this.oauthToken = oauthToken;
    this.setEmail(email);
    this.timestamp = new Date();
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Long getTopAccountId() {
    return topAccountId;
  }

  public void setTopAccountId(Long topAccountId) {
    this.topAccountId = topAccountId;
  }

  public String getOauthToken() {
    return oauthToken;
  }

  public void setOauthToken(String oauthToken) {
    this.oauthToken = oauthToken;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
