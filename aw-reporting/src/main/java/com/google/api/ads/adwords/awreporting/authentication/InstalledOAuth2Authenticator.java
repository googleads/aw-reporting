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

package com.google.api.ads.adwords.awreporting.authentication;

import com.google.api.ads.adwords.awreporting.model.entities.AuthToken;
import com.google.api.ads.adwords.awreporting.model.persistence.AuthTokenPersister;
import com.google.api.ads.adwords.awreporting.util.AdWordsServicesUtil;
import com.google.api.ads.adwords.jaxws.v201806.mcm.ApiException;
import com.google.api.ads.adwords.jaxws.v201806.mcm.Customer;
import com.google.api.ads.adwords.jaxws.v201806.mcm.CustomerServiceInterface;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Handles OAuth2 authentication for Installed application instances.
 */
@Component
public class InstalledOAuth2Authenticator implements Authenticator {

  private static final Logger logger = LoggerFactory.getLogger(InstalledOAuth2Authenticator.class);

  private static final String CALLBACK_URL = "urn:ietf:wg:oauth:2.0:oob";

  private final String userAgent;
  private final String clientId;
  private final String clientSecret;
  private final String developerToken;
  private final String managerAccountId;
  private final String scope;

  private AuthTokenPersister authTokenPersister;

  /**
   * Constructor with the OAuth2 parameters autowired by Spring.
   *
   * @param userAgent the user agent string
   * @param developerToken the developer token
   * @param clientId the OAuth2 authentication clientId
   * @param clientSecret the OAuth2 authentication clientSecret
   */
  @Autowired
  public InstalledOAuth2Authenticator(
      @Value(value = "${userAgent}") String userAgent,
      @Value(value = "${developerToken}") String developerToken,
      @Value(value = "${clientId}") String clientId,
      @Value(value = "${clientSecret}") String clientSecret,
      @Value(value = "${managerAccountId}") String managerAccountId) {
    this.userAgent = userAgent;
    this.developerToken = developerToken;
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.managerAccountId = managerAccountId;
    this.scope = OAuthScope.getScopeCsv(OAuthScope.ADWORDS);
  }

  private AdWordsSession.Builder getSessionBuilderWithoutCredential() {
    return new AdWordsSession.Builder()
        .withUserAgent(userAgent)
        .withClientCustomerId(managerAccountId)
        .withDeveloperToken(developerToken);
  }

  private AdWordsSession.Builder authenticate(Credential credential) {
    return getSessionBuilderWithoutCredential().withOAuth2Credential(credential);
  }

  /**
   * Get an adwords session builder with the authentication info.
   */
  @Override
  public AdWordsSession.Builder authenticate() throws OAuthException {
    return getSessionBuilderWithoutCredential().withOAuth2Credential(getOAuth2Credential());
  }

  /**
   * Builds the OAuth 2.0 credential for the user with a known authToken
   *
   * @param authToken the last authentication token
   * @return the new OAuth2 {@link Credential}
   */
  private Credential buildOAuth2Credential(String authToken) {
    return new GoogleCredential.Builder()
            .setClientSecrets(clientId, clientSecret)
            .setJsonFactory(new JacksonFactory())
            .setTransport(new NetHttpTransport())
            .build()
            .setRefreshToken(authToken);
  }

  /**
   * Obtains an OAuth {@link Credential} configured for AwReporting by obtaining a refresh token.
   * This method should be invoked for any users for which a refresh token is not known or is
   * invalid.
   *
   * @return
   *    The OAuth2 credential. The scope of the token generated depends on the properties file
   *    configuration. For example, if writing PDF reports to Google Drive, the scope of the
   *    token will include both AdWords and Drive.
   *
   * @throws OAuthException error in obtaining a token
   */
  private Credential getOAuth2Credential() throws OAuthException {
    Credential credential = null;

    logger.debug("Retrieving Auth Token from DB.");
    AuthToken authMcc = getAuthTokenFromStorage(managerAccountId);

    // Generate a new Auth Token if necessary
    if (authMcc == null || authMcc.getScope() == null || !authMcc.getScope().equals(scope)) {
      try {
        logger.debug("Auth Token FORCED. Getting a new one.");
        credential = getNewOAuth2Credential();
      } catch (OAuthException e) {
        if (e.getMessage().contains("Connection reset")) {
          logger.info("Connection reset when getting Auth Token, retrying...");
          credential = getNewOAuth2Credential();
        } else {
          logger.error("Error Authenticating",  e);
          throw e;
        }
      } finally {
        if (credential != null) {
          // Try to get the manager account's DescriptiveName.
          String name = "";
          try {
            AdWordsSession adWordsSession = authenticate(credential).buildImmutable();
            CustomerServiceInterface customerService =
                AdWordsServicesUtil.getService(adWordsSession, CustomerServiceInterface.class);

            // The AdWords session has clientCustomerId specified, so only details of that customer
            // will be returned by CustomerService.getCustomers().
            List<Customer> customers = customerService.getCustomers();
            if (customers != null && !customers.isEmpty()) {
              name = customers.get(0).getDescriptiveName();
            }
          } catch (ValidationException | ApiException e) {
            logger.error("Error trying to get manager account name", e);
          }

          logger.info("Saving Refresh Token to DB...");
          saveAuthTokenToStorage(managerAccountId, name, credential.getRefreshToken(), scope);
        }
      }
    } else {
      credential = buildOAuth2Credential(authMcc.getAuthToken());
    }

    return credential;
  }

  /*
   * Get new OAuth2 Credential from the user from the command line OAuth2 dance.
   */
  private Credential getNewOAuth2Credential() throws OAuthException {
    GoogleAuthorizationCodeFlow authorizationFlow = getAuthorizationFlow();

    String authorizeUrl =
        authorizationFlow.newAuthorizationUrl().setRedirectUri(CALLBACK_URL).build();

    System.out.println("\n**ACTION REQUIRED** Paste this url in your browser"
        + " and authenticate using your **AdWords Admin Account**: \n\n" + authorizeUrl + '\n');

    // Wait for the authorization code.
    System.out.println("Type the code you received on the web page here: ");
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
      String authorizationCode = reader.readLine();

      // Authorize the OAuth2 token.
      GoogleAuthorizationCodeTokenRequest tokenRequest =
          authorizationFlow.newTokenRequest(authorizationCode);
      tokenRequest.setRedirectUri(CALLBACK_URL);
      GoogleTokenResponse tokenResponse = tokenRequest.execute();

      //  Create the credential.
      Credential credential =
          new GoogleCredential.Builder()
              .setClientSecrets(clientId, clientSecret)
              .setJsonFactory(new JacksonFactory())
              .setTransport(new NetHttpTransport())
              .build()
              .setFromTokenResponse(tokenResponse);
      return credential;
    } catch (IOException e) {
      throw new OAuthException("An error occured obtaining the OAuth2Credential",  e);
    }
  }

  private GoogleAuthorizationCodeFlow getAuthorizationFlow() {
    return new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(),
                new JacksonFactory(),
                clientId,
                clientSecret,
                Arrays.asList(scope.split("\\s*,\\s*")))
            .setAccessType("offline")
            .setApprovalPrompt("force")
            .build();
  }

  /**
   * The implementation must persist the token to be retrieved later.
   *
   * @param managerAccountId the MCC account ID.
   * @param authToken the authentication token.
   * @param scope the OAuth2 scope.
   */
  private void saveAuthTokenToStorage(String managerAccountId, String managerAccountName,
      String authToken, String scope) {
    logger.debug("Persisting refresh token...");
    AuthToken authMcc = new AuthToken(managerAccountId, managerAccountName, authToken, scope);
    authTokenPersister.persistAuthToken(authMcc);
    logger.debug("... success.");
  }

  /**
   * The implementation should retrieve the authentication token previously persisted.
   *
   * @param managerAccountId Manager account ID.
   * @return the authentication token.
   */
  private AuthToken getAuthTokenFromStorage(String managerAccountId) {
    AuthToken authToken = authTokenPersister.getAuthToken(managerAccountId);
    if (authToken != null) {
      return authToken;
    }
    return null;
  }

  @Autowired
  public void setAuthTokenPersister(AuthTokenPersister authTokenPersister) {
    this.authTokenPersister = authTokenPersister;
  }
}
