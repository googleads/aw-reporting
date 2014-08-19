// Copyright 2014 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.jaxws.extensions.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.google.api.ads.adwords.jaxws.extensions.util.AdWordsSessionBuilderSynchronizer;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

/**
 * Tests for {@link AdWordsSessionBuilderSynchronizer}.
 * 
 * @author jtoledo@google.com (Julian Toledo)
 * 
 */
@RunWith(JUnit4.class)
public class AdWordsSessionBuilderSynchronizerTest {

  @Test
  public void testCopy() throws ValidationException {

    AdWordsSession.Builder builder = new AdWordsSession.Builder()
        .withEndpoint("http://")
        .withDeveloperToken("DeveloperToken")
        .withUserAgent("UserAgent")
        .withOAuth2Credential( new GoogleCredential.Builder().build());

    AdWordsSession adWordsSession = builder.build();

    AdWordsSessionBuilderSynchronizer adWordsSessionBuilderSynchronizer =
        new AdWordsSessionBuilderSynchronizer(builder);

    AdWordsSession copiedSession = adWordsSessionBuilderSynchronizer.getAdWordsSessionCopy(777L);

    assertEquals(copiedSession.getClientCustomerId(), "777");
    assertEquals(copiedSession.isReportMoneyInMicros(), null);

    assertEquals(copiedSession.getDeveloperToken(), adWordsSession.getDeveloperToken());
    assertEquals(copiedSession.getEndpoint(), adWordsSession.getEndpoint());
    assertEquals(copiedSession.getOAuth2Credential(), adWordsSession.getOAuth2Credential());
    assertEquals(copiedSession.getUserAgent(), adWordsSession.getUserAgent());
    assertEquals(copiedSession.isPartialFailure(), adWordsSession.isPartialFailure());
    assertEquals(copiedSession.isValidateOnly(), adWordsSession.isValidateOnly());
  }
}
