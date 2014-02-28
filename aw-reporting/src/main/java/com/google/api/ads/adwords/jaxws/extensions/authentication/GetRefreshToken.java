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

package com.google.api.ads.adwords.jaxws.extensions.authentication;

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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import org.apache.commons.configuration.MapConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class will create an OAuth2 refresh token that can be used with the OfflineCredentials
 * utility. Please copy the refresh token into your ads.properites file after running.
 *
 * @author Adam Rogal
 * @author jtoledo@google.com (Julian Toledo)
 */
public class GetRefreshToken {

  private static final String SCOPE = "https://adwords.google.com/api/adwords/";

  // This callback URL will allow you to copy the token from the success screen.
  private static final String CALLBACK_URL = "urn:ietf:wg:oauth:2.0:oob";

  private static Credential getOAuth2Credential(GoogleClientSecrets clientSecrets)
      throws IOException {
    GoogleAuthorizationCodeFlow authorizationFlow = new GoogleAuthorizationCodeFlow.Builder(
        new NetHttpTransport(), new JacksonFactory(), clientSecrets, Lists.newArrayList(SCOPE))
    // Set the access type to offline so that the token can be refreshed.
    // By default, the library will automatically refresh tokens when it
    // can, but this can be turned off by setting
    // api.dfp.refreshOAuth2Token=false in your ads.properties file.
        .setAccessType("offline").build();

    String authorizeUrl =
        authorizationFlow.newAuthorizationUrl().setRedirectUri(CALLBACK_URL).build();

    System.out.println("\n**ACTION REQUIRED** Paste this url in your browser"
        + " and authenticate using your **AdWords Admin Email**: \n\n" + authorizeUrl + '\n');

    // Wait for the authorization code.
    System.out.println("Type the code you received on the web page here: ");
    String authorizationCode = new BufferedReader(new InputStreamReader(System.in)).readLine();

    // Authorize the OAuth2 token.
    GoogleAuthorizationCodeTokenRequest tokenRequest =
        authorizationFlow.newTokenRequest(authorizationCode);
    tokenRequest.setRedirectUri(CALLBACK_URL);
    GoogleTokenResponse tokenResponse = tokenRequest.execute();

    // Create the OAuth2 credential.
    GoogleCredential credential = new GoogleCredential.Builder().setTransport(
        new NetHttpTransport())
        .setJsonFactory(new JacksonFactory()).setClientSecrets(clientSecrets).build();

    // Set authorized credentials.
    credential.setFromTokenResponse(tokenResponse);

    return credential;
  }

  /**
   * Retrieves the refresh token from the API
   *
   * @param clientId the client ID
   * @param clientSecret the client secret
   * @return the refresh token
   * @throws IOException error connecting to the auth server
   */
  protected static String get(String clientId, String clientSecret) throws IOException {

    GoogleClientSecrets clientSecrets = null;
    try {

      MapConfiguration config = new MapConfiguration(new ImmutableMap.Builder<String, String>().put(
          "api.adwords.clientId", clientId).put("api.adwords.clientSecret", clientSecret).build());

      clientSecrets = new GoogleClientSecretsBuilder().forApi(Api.ADWORDS).from(config).build();
    } catch (ValidationException e) {
      System.err.println(
          "Please input your OAuth2 client ID and secret into your aw-report.properties file."
          + " If you do not have a client ID or secret, please create one in "
          + "the API console: https://code.google.com/apis/console#access");
      System.exit(1);
    }

    // Get the OAuth2 credential.
    Credential credential = getOAuth2Credential(clientSecrets);
    return credential.getRefreshToken();
  }
}
