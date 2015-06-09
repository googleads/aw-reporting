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

package com.google.api.ads.adwords.awreporting.authentication;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Provides the scope used by AwReports for all OAuth2 authenticated services.
 *  
 * @author joeltoby@google.com (joeltoby)
 *
 */
public class OAuthScope {
  
  private static String SCOPE_ADWORDS = "https://adwords.google.com/api/adwords";

  private static String SCOPE_DRIVE = "https://www.googleapis.com/auth/drive.file";
  
  public enum SCOPE_TYPE { ADWORDS, DRIVE }
  
  
  /**
   * Convenience method to provide a comma separated scope list 
   * consisting of the services provided in the {@link SCOPE_TYPE}s.
   * @param scopeTypes
   * @return scopes in csv format
   */
  public static String getScopeCsv(SCOPE_TYPE... scopeTypes) {
    List<String> scope = getScopeList(scopeTypes);
    return StringUtils.join(scope, ',');
  }
  
  /**
   * Provides a scope {@link List} consisting of the services provided 
   * in the {@link SCOPE_TYPE}s.
   * consisting of the services provided in the {@link SCOPE_TYPE}s.
   * @param scopeTypes
   * @return list of scopes
   */
  public static List<String> getScopeList(SCOPE_TYPE... scopeTypes) {
    List<String> scope = new ArrayList<String>();
    
    for(SCOPE_TYPE type : scopeTypes) {
      if(type == SCOPE_TYPE.ADWORDS) {
        scope.add(SCOPE_ADWORDS);
      } else if(type == SCOPE_TYPE.DRIVE){
        scope.add(SCOPE_DRIVE);
      }
    }
    
    return scope;
  }
}
