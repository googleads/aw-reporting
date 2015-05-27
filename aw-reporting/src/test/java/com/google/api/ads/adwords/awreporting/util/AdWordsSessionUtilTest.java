// Copyright 2013 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.awreporting.util;

import static org.junit.Assert.assertEquals;

import com.google.api.ads.adwords.awreporting.util.AdWordsSessionUtil;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for {@link AdWordsSessionUtil}.
 * 
 * @author jtoledo@google.com (Julian Toledo)
 * 
 */
@RunWith(JUnit4.class)
public class AdWordsSessionUtilTest {

  @Test
  public void testCopy() throws ValidationException {

    AdWordsSession adWordsSession = new AdWordsSession.Builder()
        .withEndpoint("http://")
        .withDeveloperToken("DeveloperToken")
        .withClientCustomerId("123")
        .withUserAgent("UserAgent")
        .withOAuth2Credential( new GoogleCredential.Builder().build()).build();

    AdWordsSession copiedSession = AdWordsSessionUtil.copy(adWordsSession);
    assertEquals(copiedSession.getClientCustomerId(), adWordsSession.getClientCustomerId());
    assertEquals(copiedSession.getDeveloperToken(), adWordsSession.getDeveloperToken());
    assertEquals(copiedSession.getEndpoint(), adWordsSession.getEndpoint());
    assertEquals(copiedSession.getOAuth2Credential(), adWordsSession.getOAuth2Credential());
    assertEquals(copiedSession.getUserAgent(), adWordsSession.getUserAgent());
    assertEquals(copiedSession.isPartialFailure(), adWordsSession.isPartialFailure());
    assertEquals(copiedSession.isValidateOnly(), adWordsSession.isValidateOnly());
  }
}
