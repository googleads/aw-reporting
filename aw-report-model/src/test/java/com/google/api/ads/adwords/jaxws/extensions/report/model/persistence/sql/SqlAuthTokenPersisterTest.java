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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.Assert;

/**
 * Test class for the authorization token persistence layer.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:aw-report-model-test-beans.xml")
public class SqlAuthTokenPersisterTest {

  @Autowired
  private AuthTokenPersister authTokenPersister;

  /**
   * Tests the persistence and retrieval of the token.
   */
  @Test
  public void testTokenPersistence() {

    AuthMcc authMcc = new AuthMcc("1234", "Name", "4321", "scope");
    this.authTokenPersister.persistAuthToken(authMcc);

    AuthMcc authToken = this.authTokenPersister.getAuthToken("1234");
    Assert.assertNotNull(authToken);
    Assert.assertEquals("4321", authToken.getAuthToken());

    authToken = this.authTokenPersister.getAuthToken("12345");
    Assert.assertNull(authToken);

    authMcc = new AuthMcc("1234", "Name", "54321", "scope");
    this.authTokenPersister.persistAuthToken(authMcc);

    authToken = this.authTokenPersister.getAuthToken("1234");
    Assert.assertNotNull(authToken);
    Assert.assertEquals("54321", authToken.getAuthToken());
  }
}