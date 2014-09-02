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

package com.google.api.ads.adwords.jaxws.extensions.report.model.persistence;

import com.google.api.ads.adwords.jaxws.extensions.report.model.entities.AuthMcc;

/**
 * The persister interface for the authorization token.
 *
 *  It is responsibility of the implementation to decide where the authentication will be persisted,
 * and how it will be retrieved.
 *
 * @author gustavomoreira@google.com (Gustavo Moreira)
 */
public interface AuthTokenPersister {

  /**
   * Persists the token.
   *
   * @param authToken the authentication token.
   */
  void persistAuthToken(AuthMcc authToken);

  /**
   * Retrieves the authentication token.
   *
   * @param mccAccountId the top level MCC account ID.
   * @return the authorization token, or null if not found for the account ID.
   */
  AuthMcc getAuthToken(String mccAccountId);

}
