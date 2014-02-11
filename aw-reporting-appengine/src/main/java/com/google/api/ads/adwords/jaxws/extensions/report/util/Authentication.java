//Copyright 2012 Google Inc. All Rights Reserved.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.

package com.google.api.ads.adwords.jaxws.extensions.report.util;

import com.google.api.ads.adwords.jaxws.extensions.appengine.RestServer;
import com.google.api.ads.adwords.jaxws.extensions.appengine.model.UserToken;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.common.lib.auth.GoogleClientSecretsBuilder;
import com.google.api.ads.common.lib.auth.GoogleClientSecretsBuilder.Api;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.commons.configuration.MapConfiguration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class Authentication {

  private static final String userAgent = "AppEngineAdWords";

  private static final String developerToken = "LAORHWYoU9uJFPggIvnGWg";

  private static final String SCOPE = "https://adwords.google.com/api/adwords";

  // This callback URL will allow you to copy the token from the success screen.

  //private static final String CALLBACK_URL = "https://contactssample.appspot.com/oauth2callback";
  //private static final String CLIENT_ID = "695297850063.apps.googleusercontent.com";
  //private static final String CLIENT_SECRET = "QWRKtFqk6jIN_9e9vMMzYEH-";
  
  //private static final String CALLBACK_URL = "https://pspreporting.appspot.com/oauth2callback";
  //private static final String CLIENT_ID = "695297850063-246c95ild9etf6jjaeace2ir4mfjj507.apps.googleusercontent.com";
  //private static final String CLIENT_SECRET = "EyxOQk9FMIeSgjuT9UaRiYJe";

  //private static final String CALLBACK_URL = "https://simpleadwords.googleplex.com/oauth2callback";
  //private static final String CLIENT_ID = "695297850063-vmlap0bc4okv7meb9qfq7s006njgujcc.apps.googleusercontent.com";
  //private static final String CLIENT_SECRET = "NHwmMrzwOlwbLNaqcJKjugtA";
  
  // LOCAL for Testing
  private static final String CALLBACK_URL = "http://localhost:8888/oauth2callback";
  private static final String CLIENT_ID = "695297850063-r3c5n4gnq19diicfo4dgvjl5q1dirjea.apps.googleusercontent.com";  
  private static final String CLIENT_SECRET = "6bKUVUiUQEASZ9DqxmLYXGdX";

  public static GoogleAuthorizationCodeFlow getAuthorizationFlow() throws ValidationException {

    MapConfiguration config = new MapConfiguration(new ImmutableMap.Builder<String, String>().put(
          "api.adwords.clientId", CLIENT_ID).put("api.adwords.clientSecret", CLIENT_SECRET).build());

    GoogleClientSecrets clientSecrets = new GoogleClientSecretsBuilder().forApi(Api.ADWORDS).from(config).build();

    GoogleAuthorizationCodeFlow authorizationFlow = new GoogleAuthorizationCodeFlow.Builder(
        new NetHttpTransport(), new JacksonFactory(), clientSecrets, Lists.newArrayList(SCOPE)).build();
    
    /*
    GoogleAuthorizationCodeFlow authorizationFlow = new GoogleAuthorizationCodeFlow.Builder(
        new NetHttpTransport(), new JacksonFactory(), CLIENT_ID, CLIENT_SECRET,
        Lists.newArrayList(SCOPE)).setApprovalPrompt("force").setAccessType("offline").build();
    */
    return authorizationFlow;
  }

  public static AdWordsSession.Builder getAdWordsSessionBuilder(String mccAccountId, String accountId,
      String userId) throws ValidationException, IOException {
    GoogleCredential credential;
    if (userId != null) {
      
      RestServer.persister.get(UserToken.class, "userId", userId);
      Map<String,Object> map = Maps.newHashMap();
      map.put("userId", userId);
      map.put("topAccountId", Long.parseLong(mccAccountId));
      List<UserToken> userTokenList = RestServer.persister.get(UserToken.class, map);
      
      if (userTokenList != null && userTokenList.size() > 0) {
        credential = new GoogleCredential().setAccessToken(userTokenList.get(0).getOauthToken());
        credential.refreshToken();
      } else {
        throw new ValidationException("AuthenticationError, List<UserToken> null or 0", "List<UserToken>");
      }
    } else {
      // TEMP ?
      List<UserToken> userTokenList = RestServer.persister.get(UserToken.class, "mccAccountId", Long.parseLong(mccAccountId)); 
      credential = new GoogleCredential().setAccessToken(userTokenList.get(0).getOauthToken());
      credential.refreshToken();
    }

    if (accountId == null)
      accountId = mccAccountId;

    // Construct a AdWordsSession.
    AdWordsSession.Builder builder = new AdWordsSession.Builder().withOAuth2Credential(credential)
        .withUserAgent(userAgent).withClientCustomerId(accountId)
        .withDeveloperToken(developerToken);

    return builder;
  }

  public static String getOAuth2Credential(Long topAccountId) throws Exception {
    GoogleAuthorizationCodeFlow authorizationFlow = Authentication.getAuthorizationFlow();
    String authorizeUrl = authorizationFlow.newAuthorizationUrl().setRedirectUri(CALLBACK_URL)
        .setState(String.valueOf(topAccountId)).build();
    return authorizeUrl;
  }
  
  public static String getOAuth2Credential(Long topAccountId, URL returnUrl) throws Exception {
    GoogleAuthorizationCodeFlow authorizationFlow = Authentication.getAuthorizationFlow();
    String authorizeUrl = authorizationFlow.newAuthorizationUrl().setRedirectUri(CALLBACK_URL)
        .setState(String.valueOf(topAccountId) + returnUrl).build();
    return authorizeUrl;
  }

  public static Credential processOAuth2Credential(String code, String topAccountId)
      throws Exception {
    GoogleAuthorizationCodeFlow authorizationFlow = Authentication.getAuthorizationFlow();
    
    UserService userService = UserServiceFactory.getUserService();

    // Create the OAuth2 credential request using the authorization code.
    GoogleAuthorizationCodeTokenRequest tokenRequest = authorizationFlow.newTokenRequest(code);
    tokenRequest.setRedirectUri(CALLBACK_URL);

    GoogleTokenResponse tokenResponse = tokenRequest.execute();

    // Create the credential.
    Credential credential = authorizationFlow.createAndStoreCredential(tokenResponse, null);

    // Though not necessary when first created, you can refresh the token,
    // which is necessary after 60 minutes. In an offline application, you
    // should keep a reference to your credential to refresh it in this
    // time period.
    credential.refreshToken();
    
    // Test Token with one operation.
    //Reporting reporting = new Reporting(String.valueOf(topAccountId), userService.getCurrentUser().getUserId());
    //reporting.getInfo();

    UserToken userToken = new UserToken(userService.getCurrentUser().getUserId(),
        Long.parseLong(topAccountId), userService.getCurrentUser().getEmail(), credential.getAccessToken());

    RestServer.persister.save(userToken);

    return credential;
  }
}
