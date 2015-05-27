package com.google.api.ads.adwords.awreporting.authentication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.api.ads.adwords.awreporting.authentication.OAuthScope.SCOPE_TYPE;
import com.google.api.ads.adwords.awreporting.exporter.reportwriter.ReportWriterType;
import com.google.api.ads.adwords.awreporting.model.entities.AuthMcc;
import com.google.api.ads.adwords.awreporting.model.persistence.AuthTokenPersister;
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

/**
 * Handles OAuth2 authentication for Installed application instances
 * 
 * @author jtoledo@google.com (jtoledo@google.com)
 * @author joeltoby@google.com (joeltoby@google.com)
 *
 */
@Component
public class InstalledOAuth2Authenticator implements Authenticator {

  private static final Logger LOGGER = Logger.getLogger(InstalledOAuth2Authenticator.class);

  private static final String USER_AGENT = "AwReporting";

  private static final String CALLBACK_URL = "urn:ietf:wg:oauth:2.0:oob";

  private String clientId = null;
  private String clientSecret = null;
  private String developerToken = null;
  private String scope = null;

  private AuthTokenPersister authTokenPersister;

  /**
   * Constructor with the OAuth2 parameters autowired by Spring.
   * 
   * @param developerToken the developer token
   * @param clientId the OAuth2 authentication clientId
   * @param clientSecret the OAuth2 authentication clientSecret
   */
  @Autowired
  public InstalledOAuth2Authenticator(
      @Value("${developerToken}") String developerToken,
      @Value(value = "${clientId}") String clientId,
      @Value(value = "${clientSecret}") String clientSecret,
      @Value(value = "${aw.report.exporter.reportwritertype:}") ReportWriterType reportWriterType) {
    this.developerToken = developerToken;
    this.clientId = clientId;
    this.clientSecret = clientSecret;

    /*
     * Google Drive API will be included in the scope if it is used for 
     * writing PDF files.
     */
    if (reportWriterType != null 
        && reportWriterType == ReportWriterType.GoogleDriveWriter) {
      scope = OAuthScope.getScopeCsv(SCOPE_TYPE.ADWORDS, SCOPE_TYPE.DRIVE);
    } else {
      scope = OAuthScope.getScopeCsv(SCOPE_TYPE.ADWORDS);
    }
  }

  /* (non-Javadoc)
   * @see com.google.api.ads.adwords.awreporting.authentication.Authenticator#authenticate(java.lang.String, boolean)
   */
  public AdWordsSession.Builder authenticate(String userId, String mccAccountId, boolean force)
      throws OAuthException {

    return new AdWordsSession.Builder()
      .withOAuth2Credential(getOAuth2Credential(null, mccAccountId, force))
      .withUserAgent(USER_AGENT)
      .withClientCustomerId(mccAccountId)
      .withDeveloperToken(this.developerToken);
  }

  public AdWordsSession.Builder authenticate(String userId, String mccAccountId, Credential credential)
      throws OAuthException {

    return new AdWordsSession.Builder()
      .withOAuth2Credential(credential)
      .withUserAgent(USER_AGENT)
      .withClientCustomerId(mccAccountId)
      .withDeveloperToken(this.developerToken);
  }

  /**
   * Builds the OAuth 2.0 credential for the user with a known authToken
   * 
   * @param authToken
   *            the last authentication token
   * @return the new {@link Credential}
   * @throws OAuthException
   *             error creating the credentials
   */
  private Credential buildOAuth2Credentials(String authToken) {

    Credential credential = new GoogleCredential.Builder().setClientSecrets(clientId, clientSecret)
        .setJsonFactory(new JacksonFactory()).setTransport(new NetHttpTransport()).build()
        .setRefreshToken(authToken);

    return credential;
  }

  /**
   * Obtains an OAuth {@link Credential} configured for AwReporting doing the OAuth dance.
   * This method should be invoked for any users for which a refresh token is not known or is
   * invalid.
   * 
   * @param mccAccountId the MCC account ID.
   * @param force if the actual token should be refreshed
   * @return The OAuth2 credentials. The scope of the token generated depends on the properties file
   *         configuration.
   * @throws OAuthException If an error is encountered when trying to obtain a token.
   */
  public Credential getOAuth2Credential(String userId, String mccAccountId, boolean force)
      throws OAuthException {

    Credential credential = null;

    LOGGER.debug("Retrieving Auth Token from DB.");
    AuthMcc authMcc = this.getAuthTokenFromStorage(mccAccountId);
    String authToken = null;

    // Generate a new Auth Token if necessary
    if (authMcc == null || authMcc.getScope() == null
        || !authMcc.getScope().equals(scope) || force) {
      try {

        LOGGER.debug("Auth Token FORCED. Getting a new one.");
        credential = getNewOAuth2Credential();

      } catch (OAuthException e) {
        if (e.getMessage().contains("Connection reset")) {

          LOGGER.info("Connection reset when getting Auth Token, retrying...");
          credential = getNewOAuth2Credential();

        } else {
          LOGGER.error("Error Authenticating: " + e.getMessage());
          e.printStackTrace();
          throw e;
        }
      } finally {
        if (credential != null) {
          
          // Try to get the MCC Company Name and DescriptiveName
          String name = "";
          try {
            AdWordsSession adWordsSession = authenticate(null, mccAccountId, credential).build();
            CustomerDelegate customerDelegate = new CustomerDelegate(adWordsSession);          
            Customer customer = customerDelegate.getCustomer();
            name = customer.getCompanyName() + " (" + customer.getDescriptiveName() + ")";
          } catch (ValidationException e) {
            LOGGER.error("Error trying to get MCC Name " + e.getMessage());
          } catch (ApiException e) {
            LOGGER.error("Error trying to get MCC Name " + e.getMessage());
          }

          LOGGER.info("Saving Refresh Token to DB...");
          this.saveAuthTokenToStorage(mccAccountId, name, credential.getRefreshToken(), scope);
        }
      }
    } else {
      authToken = authMcc.getAuthToken();
      credential = buildOAuth2Credentials(authToken);
    }

    return credential;
  }

  /*
   * Get New Credentials from the user from the command line OAuth2 dance.
   */
  public Credential getNewOAuth2Credential()
      throws OAuthException {
    
    GoogleAuthorizationCodeFlow authorizationFlow = getAuthorizationFlow();

    String authorizeUrl =
        authorizationFlow.newAuthorizationUrl().setRedirectUri(CALLBACK_URL).build();

    System.out.println("\n**ACTION REQUIRED** Paste this url in your browser"
        + " and authenticate using your **AdWords Admin Email**: \n\n" + authorizeUrl + '\n');

    // Wait for the authorization code.
    System.out.println("Type the code you received on the web page here: ");
    try {
      String authorizationCode = new BufferedReader(new InputStreamReader(System.in)).readLine();

      // Authorize the OAuth2 token.
      GoogleAuthorizationCodeTokenRequest tokenRequest =
          authorizationFlow.newTokenRequest(authorizationCode);
      tokenRequest.setRedirectUri(CALLBACK_URL);
      GoogleTokenResponse tokenResponse = tokenRequest.execute();

      //  Create the credential.
      Credential credential = new GoogleCredential.Builder().setClientSecrets(clientId, clientSecret)
          .setJsonFactory(new JacksonFactory()).setTransport(new NetHttpTransport()).build().setFromTokenResponse(tokenResponse); 

      // Set authorized credentials.
      credential.setFromTokenResponse(tokenResponse);

      return credential;
    } catch(IOException e) {
      throw new OAuthException("An error occured obtaining the OAuth2Credential",  e.getCause());
    }
  }

  private GoogleAuthorizationCodeFlow getAuthorizationFlow() {
    GoogleAuthorizationCodeFlow authorizationFlow = new GoogleAuthorizationCodeFlow.Builder(
        new NetHttpTransport(), new JacksonFactory(), clientId, clientSecret,
        Arrays.asList(scope.split("\\s*,\\s*")))
    .setAccessType("offline").setApprovalPrompt("force").build();

    return authorizationFlow;
  }

  /**
   * The implementation must persist the token to be retrieved later.
   * 
   * @param mccAccountId the MCC account ID.
   * @param authToken the authentication token.
   * @param scope the OAuth2 scope.
   */
  private void saveAuthTokenToStorage(String mccAccountId, String topAccountName, String authToken, String scope) {
    LOGGER.debug("Persisting refresh token...");
    AuthMcc authMcc = new AuthMcc(mccAccountId, topAccountName, authToken, scope);
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
  private AuthMcc getAuthTokenFromStorage(String mccAccountId) {

    AuthMcc authToken = this.authTokenPersister.getAuthToken(mccAccountId);
    if (authToken != null) {
      return authToken;
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
