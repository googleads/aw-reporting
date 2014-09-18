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

package com.google.api.ads.adwords.awreporting.kratubackend.restserver;

import com.google.api.ads.adwords.awreporting.kratubackend.util.InterestingQueries;
import com.google.api.ads.adwords.awreporting.model.entities.AuthMcc;
import com.google.api.ads.adwords.awreporting.model.entities.ReportAccount;
import com.google.api.ads.adwords.awreporting.server.rest.AbstractServerResource;
import com.google.api.ads.adwords.awreporting.server.rest.RestServer;

import org.restlet.representation.Representation;

import java.util.ArrayList;
import java.util.List;

/**
 * MccRest
 * 
 * @author jtoledo@google.com (Julian Toledo)
 */
public class MccRest extends AbstractServerResource {
  class AuthMccPlus {
    public AuthMcc mccInfo;
    public String minDate;
    public String maxDate;
  }

  public Representation getHandler() {
    String result = null;
    try {
      getParameters();
      InterestingQueries interestingQueries =
          RestServer.getApplicationContext().getBean(InterestingQueries.class);

      ArrayList<AuthMccPlus> fixed = new ArrayList<AuthMccPlus>();

      List<AuthMcc> listAuthMcc = KratuRestServer.getKratuStorageHelper().getEntityPersister()
          .get(AuthMcc.class);
      for (AuthMcc authMcc : listAuthMcc) {
        authMcc.setScope(null);
        authMcc.setAuthToken(null);

        List<?> o = interestingQueries.getMin(ReportAccount.class,
            authMcc.getTopAccountId(), "dateStart", "dateEnd");
        Object obj = o.get(0);
        System.out.println(((Object[]) obj)[0]);
        System.out.println(((Object[]) obj)[1]);
        AuthMccPlus amp = new AuthMccPlus();
        amp.mccInfo = authMcc;
        amp.minDate = (String) ((Object[]) obj)[0];
        amp.maxDate = (String) ((Object[]) obj)[1];

        fixed.add(amp);

      }
      result = gson.toJson(fixed);

    } catch (Exception exception) {
      System.out.println(exception.getMessage());
      return handleException(exception);
    }
    addReadOnlyHeaders();
    return createJsonResult(result);
  }
}
