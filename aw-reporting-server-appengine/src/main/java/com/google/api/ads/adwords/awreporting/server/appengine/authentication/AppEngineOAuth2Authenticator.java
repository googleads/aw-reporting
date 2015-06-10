//Copyright 2014 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.awreporting.server.appengine.authentication;

import com.google.api.ads.adwords.awreporting.authentication.Authenticator;
import com.google.api.ads.adwords.awreporting.authentication.OAuthScope;
import com.google.api.ads.adwords.awreporting.authentication.OAuthScope.SCOPE_TYPE;
import com.google.api.ads.adwords.awreporting.server.appengine.RestServer;
import com.google.api.ads.adwords.awreporting.server.appengine.model.UserToken;
import com.google.api.ads.adwords.awreporting.server.appengine.processors.RefreshAccountsTask;
import com.google.api.ads.adwords.awreporting.util.CustomerDelegate;
import com.google.api.ads.adwords.jaxws.v201502.mcm.ApiException;
import com.google.api.ads.adwords.jaxws.v201502.mcm.Customer;
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
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.common.collect.Maps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This class will create an OAuth2 credentials for AppEngine
 *
 * @author jtoledo@google.com (Julian Toledo)
 * @author joeltoby@google.com  (Joel Toby)
 */
@Component
public class AppEngineOAuth2Authenticator implements Authenticator {

  private static final Logger LOGGER = Logger.getLogger(AppEngineOAuth2Authenticator.class.getName());

  private static final String USER_AGENT = "AwReporting-AppEngine";

  private String clientId = null;  
  private String clientSecret = null;
  private String developerToken = null;
  private String callBackUrl = null;

  /**
   * Constructor.
   * 
   * @param developerToken
   *            the developer token
   * @param clientId
   *            the OAuth2 authentication clientId
   * @param clientSecret
   *            the OAuth2 authentication clientSecret
   */
  @Autowired
  public AppEngineOAuth2Authenticator(
      @Value("${developerToken}") String developerToken,
      @Value(value = "${clientId}") String clientId,
      @Value(value = "${clientSecret}") String clientSecret,
      @Value(value = "${callBackUrl}") String callBackUrl) {
    this.developerToken = developerToken;
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.callBackUrl = callBackUrl;
  }

  private GoogleAuthorizationCodeFlow getAuthorizationFlow() throws ValidationException {

    GoogleAuthorizationCodeFlow authorizationFlow = new GoogleAuthorizationCodeFlow.Builder(
        new NetHttpTransport(), new JacksonFactory(), clientId, clientSecret, 
        OAuthScope.getScopeList(SCOPE_TYPE.ADWORDS, SCOPE_TYPE.DRIVE))
    .setAccessType("offline").setApprovalPrompt("force").build();

    return authorizationFlow;
  }

  public String getOAuth2Url(Long topAccountId) throws Exception {
    String authorizeUrl = getAuthorizationFlow().newAuthorizationUrl().setRedirectUri(callBackUrl)
        .setState(String.valueOf(topAccountId)).build();
    return authorizeUrl;
  }

  public void processOAuth2Credential(String code, String topAccountId) throws ValidationException, IOException, ApiException {

    // Create the OAuth2 credential request using the authorization code.
    GoogleAuthorizationCodeTokenRequest tokenRequest = getAuthorizationFlow().newTokenRequest(code);
    tokenRequest.setRedirectUri(callBackUrl);
    GoogleTokenResponse tokenResponse = tokenRequest.execute();

    String refreshToken = tokenResponse.getRefreshToken();

    UserService userService = UserServiceFactory.getUserService();
    String userId = userService.getCurrentUser().getUserId();

    UserToken userToken = new UserToken(userId,  Long.parseLong(topAccountId),
        null, userService.getCurrentUser().getEmail(), refreshToken);


    // Use the new credentials to get infor about the topAccountId and update the new UserToken
    try {
      AdWordsSession adWordsSession = authenticate(userToken).build();
      CustomerDelegate customerDelegate = new CustomerDelegate(adWordsSession);
      Customer customer = customerDelegate.getCustomer();
      String name = customer.getCompanyName() + " (" + customer.getDescriptiveName() + ")";
      userToken.setName(name);
      
      RestServer.getPersister().save(userToken);
      
      // Get Accounts List for the MCC and store it.
      RefreshAccountsTask.createRefreshAccountsTask(topAccountId);
      
    } catch(ApiException exception) {
      throw new ApiException("AdWords API error, your account may not have access to this MCC", exception.getFaultInfo(), exception);
    }
  }

  private AdWordsSession.Builder authenticate(UserToken userToken) {
    Credential credential = new GoogleCredential.Builder().setClientSecrets(clientId, clientSecret)
        .setJsonFactory(new JacksonFactory()).setTransport(new NetHttpTransport()).build()
        .setRefreshToken(userToken.getOauthToken());

    AdWordsSession.Builder builder = new AdWordsSession.Builder().withOAuth2Credential(credential)
        .withUserAgent(USER_AGENT).withDeveloperToken(developerToken).withClientCustomerId(String.valueOf(userToken.getTopAccountId()));

    return builder;
  }

  public Credential getOAuth2Credential(String mccAccountId, boolean force) throws OAuthException {

    String userId = RestServer.getWebAuthenticator().getCurrentUser();
    LOGGER.info("getOAuth2Credential for " + userId);

    Map<String, Object> query = Maps.newHashMap();
    query.put(UserToken.TOP_ACCOUNT_ID, Long.parseLong(mccAccountId));
    query.put(UserToken.USER_ID, userId);

    List<UserToken> userTokenList = RestServer.getPersister().get(UserToken.class, query); 

    if (userTokenList != null && userTokenList.size() > 0) {

      Credential credential = new GoogleCredential.Builder().setClientSecrets(clientId, clientSecret)
          .setJsonFactory(new JacksonFactory()).setTransport(new NetHttpTransport()).build()
          .setRefreshToken(userTokenList.get(0).getOauthToken()); 

      // TODO: Avoid refreshing token by implementing the force
      try {
        credential.refreshToken();
      } catch (IOException e) {
        LOGGER.severe("AuthenticationError calling AppEngineAuthenticator authenticate IOException");
        throw new OAuthException("AuthenticationError, IOException");
      }

      return credential;
    } else {
      LOGGER.severe("AuthenticationError calling AppEngineAuthenticator authenticate");
      throw new OAuthException("AuthenticationError, List<UserToken> null or 0");
    }
  }

  public AdWordsSession.Builder authenticate(String mccAccountId, boolean force) throws OAuthException {
    // Construct a AdWordsSession.
    AdWordsSession.Builder builder = new AdWordsSession.Builder().withOAuth2Credential(getOAuth2Credential(mccAccountId, force))
        .withUserAgent(USER_AGENT).withDeveloperToken(developerToken).withClientCustomerId(mccAccountId);
    return builder;
  }
}
