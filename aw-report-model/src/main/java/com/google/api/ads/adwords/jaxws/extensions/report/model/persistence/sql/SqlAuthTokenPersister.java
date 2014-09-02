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

package com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.sql;

import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.AuthMcc;
import com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.AuthTokenPersister;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * The SQL implementation of the token persister.
 *
 * @author gustavomoreira@google.com (Gustavo Moreira)
 */
@Component
public class SqlAuthTokenPersister implements AuthTokenPersister {

  private SessionFactory sessionFactory;

  /**
   * C'tor.
   *
   * @param sessionFactory the hibernate session factory
   */
  @Autowired
  public SqlAuthTokenPersister(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.AuthTokenPersister#
   *      persistAuthToken(
   *      com.google.api.ads.adwords.jaxws.extensions.report.model.entities.AuthMcc)
   */
  @Override
  @Transactional
  public void persistAuthToken(AuthMcc authToken) {

    Session session = this.sessionFactory.getCurrentSession();
    session.saveOrUpdate(authToken);
  }

  /**
   * @see com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.AuthTokenPersister#
   *      getAuthToken(java.lang.String)
   */
  @Override
  @Transactional
  public AuthMcc getAuthToken(String mccAccountId) {

    Session session = this.sessionFactory.getCurrentSession();
    return (AuthMcc) session.get(AuthMcc.class, mccAccountId);
  }
}
