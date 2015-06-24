//Copyright 2014 Google Inc. All Rights Reserved.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.

package com.google.api.ads.adwords.awreporting.alerting.processor;

import static org.junit.Assert.assertEquals;

import com.google.api.ads.adwords.awreporting.alerting.util.ConfigTags;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.junit.Before;
import org.junit.Test;

/**
* Test case for the {@link AlertActionsProcessor} class.
*/
public class AlertActionsProcessorTest {
  
  private AlertActionsProcessor alertActionsProcessor;
  
  @Before
  public void setUp() {
    JsonArray configs = new JsonArray();
    JsonObject alertActionConfig = new JsonObject();
    alertActionConfig.addProperty(ConfigTags.Action.ACTION_CLASS, "DummyAlertAction");
    configs.add(alertActionConfig);
    configs.add(alertActionConfig);
    alertActionsProcessor = new AlertActionsProcessor(configs);
  }
  
  @Test
  public void testConstruction() {
    assertEquals(alertActionsProcessor.getActionsSize(), 2);
  }
}
