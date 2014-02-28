package com.google.api.ads.adwords.jaxws.extensions.authentication;

import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.AuthMcc;
import com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.AuthTokenPersister;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.common.lib.auth.OfflineCredentials;
import com.google.api.ads.common.lib.auth.OfflineCredentials.Api;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.api.client.auth.oauth2.Credential;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class InstalledOAuth2Authenticator implements Authenticator {

  private static final Logger LOGGER = Logger.getLogger(InstalledOAuth2Authenticator.class);

  private static final String USER_AGENT = "AwReporting";

  private AuthTokenPersister authTokenPersister;

  // AdWords Authentication Properties
  private String clientId = null;
  private String clientSecret = null;
  private String developerToken = null;

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
  public InstalledOAuth2Authenticator(
      @Value("${developerToken}") String developerToken,
      @Value(value = "${clientId}") String clientId,
      @Value(value = "${clientSecret}") String clientSecret) {
    this.developerToken = developerToken;
    this.clientId = clientId;
    this.clientSecret = clientSecret;
  }

  /**
   * Authenticates the user against the API.
   * 
   * @param force
   *            true if the authentication token must be renewed.
   * @return the session builder after the authentication.
   * @throws IOException
   *             error connecting to authentication server
   * @throws OAuthException
   *             error on the OAuth process
   */
  public AdWordsSession.Builder authenticate(String mccAccountId, boolean force)
      throws OAuthException, IOException {

    String authToken = this.retrieveAuthToken(mccAccountId, force);

    Credential oAuth2Credential = null;
    try {
      oAuth2Credential = this.buildOAuth2Credentials(authToken);

    } catch (OAuthException e) {
      if (e.getMessage().contains("Credential could not be refreshed")) {
        System.out
        .println("**ERROR** Credential could not be refreshed,"
            + " we need a new OAuth2 Token");

        authToken = GetRefreshToken.get(this.clientId,
            this.clientSecret);
        oAuth2Credential = this.buildOAuth2Credentials(authToken);

        System.out.println("Saving Refresh Token to DB...\n");
        this.saveAuthTokenToStorage(mccAccountId, authToken);

      } else {
        e.printStackTrace();
        throw e;
      }
    }

    return new AdWordsSession.Builder()
    .withOAuth2Credential(oAuth2Credential)
    .withUserAgent(USER_AGENT)
    .withClientCustomerId(mccAccountId)
    .withDeveloperToken(this.developerToken);
  }

  /**
   * Builds the OAuth 2.0 credential for the user
   * 
   * @param authToken
   *            the last authentication token
   * @return the new {@link Credential}
   * @throws OAuthException
   *             error creating the credentials
   */
  private Credential buildOAuth2Credentials(String authToken)
      throws OAuthException {

    try {
      return new OfflineCredentials.Builder().forApi(Api.ADWORDS)
          .withRefreshToken(authToken)
          .withClientSecrets(this.clientId, this.clientSecret)
          .build().generateCredential();
    } catch (ValidationException e) {

      e.printStackTrace();
      throw new IllegalStateException("Builder not set properly. "
          + "This might mean a bug with the authentication, "
          + "or the credential values are incorrect.", e);
    }
  }

  /**
   * Retrieves the authentication token by refreshing it if necessary, and
   * persisting the updated token into the DB.
   * 
   * @param force
   *            if the actual token should be refreshed
   * @return the valid token for the moment
   * @throws IOException
   *             error connecting to authentication server
   * @throws OAuthException
   *             error on the OAuth process
   */
  private String retrieveAuthToken(String mccAccountId, boolean force) throws IOException,
  OAuthException {

    LOGGER.debug("Retrieving auth token from DB.");
    String authToken = this.getAuthTokenFromStorage(mccAccountId);

    // Generate a new Auth token if necessary
    if ((authToken == null || force)) {
      try {
        if (force) {
          LOGGER.debug("Token refresh FORCED. Getting a new one.");
        } else {
          LOGGER.debug("Token not found. Getting one.");
        }
        authToken = GetRefreshToken.get(this.clientId, this.clientSecret);

      } catch (IOException e) {
        if (e.getMessage().contains("Connection reset")) {
          LOGGER.info("Connection reset when getting authToken, retrying...");
          this.authenticate(mccAccountId, true);
        } else {
          LOGGER.error("Error authenticating: " + e.getMessage());
          e.printStackTrace();
          throw e;
        }
      }
      LOGGER.info("Saving Refresh Token to DB...");
      this.saveAuthTokenToStorage(mccAccountId, authToken);
    }
    return authToken;
  }

  /**
   * The implementation must persist the token to be retrieved later.
   * 
   * @param mccAccountId
   *            the MCC account ID.
   * @param authToken
   *            the authentication token.
   */
  private void saveAuthTokenToStorage(String mccAccountId, String authToken) {
    LOGGER.debug("Persisting refresh token...");
    AuthMcc authMcc = new AuthMcc(mccAccountId, authToken);
    this.authTokenPersister.persistAuthToken(authMcc);
    LOGGER.debug("... success.");
  }

  /**
   * The implementation should retrieve the authentication token previously
   * persisted.
   * 
   * @param mccAccountId
   *            the MCC account ID.
   * @return the authentication token.
   */
  private String getAuthTokenFromStorage(String mccAccountId) {

    AuthMcc authToken = this.authTokenPersister.getAuthToken(mccAccountId);
    if (authToken != null) {
      return authToken.getAuthToken();
    }
    return null;
  }

  /**
   * @param authTokenPersister
   *            the authTokenPersister to set
   */
  @Autowired
  public void setAuthTokenPersister(AuthTokenPersister authTokenPersister) {
    this.authTokenPersister = authTokenPersister;
  }
}
