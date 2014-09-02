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

package com.google.api.ads.adwords.jaxws.extensions.kratu.restserver.kratu;

import com.google.api.ads.adwords.jaxws.extensions.kratu.data.KratuProcessor;
import com.google.api.ads.adwords.jaxws.extensions.kratu.data.RunnableKratu;
import com.google.api.ads.adwords.jaxws.extensions.kratu.restserver.AbstractServerResource;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.service.TaskService;

/**
 * GenerateKratusRest
 * 
 * @author jtoledo@google.com (Julian Toledo)
 */
public class GenerateKratusRest extends AbstractServerResource {
  
  static TaskService taskService = new TaskService();
  
  public Representation getHandler() {
    String result = null;
    try {
      getParameters();

      //Generate Kratus at Mcc level
      if (topAccountId != null && dateStart != null && dateEnd != null) {
        // Launching a new Service(Thread) to make the request async.
        KratuProcessor kratuProcessor = getApplicationContext().getBean(KratuProcessor.class);

        RunnableKratu runnableKratu = kratuProcessor.createRunnableKratu(
            topAccountId, null, getStorageHelper(), dateStart, dateEnd);

        taskService.submit(runnableKratu);

        result = "OK - Task created, this usually takes 1-2mins for each 1000 accounts/month";
      } else {
        taskService.getContext();
      }
    } catch (Exception exception) {
      return handleException(exception);
    }
    addReadOnlyHeaders();
    return createJsonResult(result);
  }

  public void deleteHandler() {
    this.setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
  }

  public Representation postPutHandler(String json) {
    this.setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
    return createJsonResult(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED.getDescription());
  }
}