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

package com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.mongodb;

import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.AuthMcc;
import com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.AuthTokenPersister;
import com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.EntityPersister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The MongoDb implementation of the token persister.
 *
 * @author jtoledo@google.com (Julian Toledo)
 * @author gustavomoreira@google.com (Gustavo Moreira)
 */
@Component
public class MongoDbAuthTokenPersister implements AuthTokenPersister {

  private EntityPersister mongoEntityPersister;

  /**
   * MongoDbReportEntitiesPersister constructor
   *
   * @param mongoEntityPersister the NoSqlStorage to communicate with the DB
   */
  @Autowired
  public MongoDbAuthTokenPersister(
      @Qualifier("mongoEntityPersister") EntityPersister mongoEntityPersister) {
    this.mongoEntityPersister = mongoEntityPersister;
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.AuthTokenPersister#
   *      persistAuthToken(
   *      com.google.api.ads.adwords.jaxws.extensions.report.model.entities.AuthMcc)
   */
  @Override
  public void persistAuthToken(AuthMcc authToken) {
    mongoEntityPersister.save(authToken);
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.AuthTokenPersister#
   *      getAuthToken(java.lang.String)
   */
  @Override
  public AuthMcc getAuthToken(String mccAccountId) {
    List<AuthMcc> list = mongoEntityPersister.get(AuthMcc.class, "topAccountId", mccAccountId);
    if (list != null && list.size() > 0) {
      return list.get(0);
    } else {
      return null;
    }
  }
}
