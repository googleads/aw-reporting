//Copyright 2015 Google Inc. All Rights Reserved.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.

package com.google.api.ads.adwords.awreporting.alerting.util;

import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
* Generate test entities for other test cases
*/
public class TestEntitiesGenerator {

  public static JsonObject getTestReportQueryConfig() {
    String jsonConfigStr = "{" +
                           "  \"ReportType\": \"KEYWORDS_PERFORMANCE_REPORT\", " +
                           "  \"Fields\": \"ExternalCustomerId,AccountDescriptiveName,Id,KeywordText,Impressions,Ctr\", " +
                           "  \"Conditions\": \"Impressions > 100 AND Ctr < 0.05\", " +
                           "  \"TimeRange\": \"YESTERDAY\" " +
                           "}";
    return new JsonParser().parse(jsonConfigStr).getAsJsonObject();
  }
  
  public static AdWordsSession.Builder getTestAdWordsSessionBuilder() {
    return new AdWordsSession.Builder().withEndpoint("http://www.google.com")
               .withDeveloperToken("DeveloperToken")
               .withClientCustomerId("123")
               .withUserAgent("UserAgent")
               .withOAuth2Credential(new GoogleCredential.Builder().build());
  }
  
  public static AdWordsSession getTestAdWordsSession() throws ValidationException {
    return getTestAdWordsSessionBuilder().build();
  }
}
