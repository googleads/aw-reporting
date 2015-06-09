// Copyright 2014 Google Inc. All Rights Reserved.
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

import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.client.auth.oauth2.Credential;

import java.io.IOException;

/**
 * Authenticator interface for OAuth.
 *
 * @author jtoledo@google.com (Julian Toledo)
 * @author joeltoby@google.com (Joel Toby)
 */
public interface Authenticator {
  
  /**
   * Authenticates the user against the API(s) depending on the OAuth scope and then returns an
   * {@link AdWordsSession}.
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
      throws OAuthException;

  /**
   * Obtains an OAuth {@link Credential} configured for AwReporting by doing the OAuth dance.
   * This method should be invoked for any users for which a refresh token is not known or is
   * invalid.
   * 
   * @return
   *    The OAuth2 credentials. The scope of the token generated depends on the properties file
   *    configuration.  For example<br>
   *    If writing PDF reports to Google Drive, the scope of the token will include both
   *    AdWords and Drive.
   *    
   * @throws OAuthException
   *    If an error is encountered when trying to obtain a token.
   */
  public Credential getOAuth2Credential(String mccAccountId, boolean force)
      throws OAuthException;

}
