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

package com.google.api.ads.adwords.awreporting.model.persistence.sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.google.api.ads.adwords.awreporting.model.entities.AuthToken;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Test case for the {@link SqlAuthTokenPersister} class.
 */
@RunWith(JUnit4.class)
public class SqlAuthTokenPersisterTest {

  private SqlAuthTokenPersister sqlAuthTokenPersister;
  
  @Mock private Session mockedSession;
  
  @Mock private SessionFactory mockedSessionFactory;
  
  @Mock private Transaction mockedTransaction;
 
  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    
    Mockito.when(mockedSessionFactory.getCurrentSession()).thenReturn(mockedSession);
    Mockito.when(mockedSession.beginTransaction()).thenReturn(mockedTransaction);
    
    sqlAuthTokenPersister = new SqlAuthTokenPersister(mockedSessionFactory); 
  }

  /**
   * Tests the persistence and retrieval of the token.
   */
  @Test
  public void testTokenPersistence() {
    AuthToken authToken = new AuthToken("1234", "Name", "4321", "scope");
    Mockito.when(sqlAuthTokenPersister.getAuthToken("1234")).thenReturn(authToken);
    sqlAuthTokenPersister.persistAuthToken(authToken);

    // Retrieve from key.
    authToken = sqlAuthTokenPersister.getAuthToken("1234");
    assertNotNull("AuthToken should not be null", authToken);
    assertEquals("AuthToken must match", "4321", authToken.getAuthToken());

    // Retrieve unknown key.
    authToken = sqlAuthTokenPersister.getAuthToken("12345");
    assertNull("AuthToken should be null", authToken);

    // Mutate auth token.
    authToken = new AuthToken("1234", "Name", "54321", "scope");
    sqlAuthTokenPersister.persistAuthToken(authToken);

    Mockito.when(sqlAuthTokenPersister.getAuthToken("1234")).thenReturn(authToken);
    authToken = sqlAuthTokenPersister.getAuthToken("1234");
    assertNotNull("AuthToken should not be null", authToken);
    assertEquals("AuthToken must match", "54321", authToken.getAuthToken());
  }
}
