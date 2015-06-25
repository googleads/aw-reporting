//Copyright 2015 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.awreporting.alerting.action;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

import com.google.gson.JsonObject;

/**
* Test case for the {@link AlertAction} classes.
* 
 * @author zhuoc@google.com (Zhuo Chen)
*/
public class AlertActionsTest {
  
  final static String alertActionInterfaceName = "com.google.api.ads.adwords.awreporting.alerting.action.AlertAction";
  
  final static String[] alertActionClassNames = {
      "com.google.api.ads.adwords.awreporting.alerting.action.DummyAlertAction",
      "com.google.api.ads.adwords.awreporting.alerting.action.SimpleConsoleWriter",
      "com.google.api.ads.adwords.awreporting.alerting.action.SimpleLogFileWriter",
      "com.google.api.ads.adwords.awreporting.alerting.action.PerAccountManagerEmailSender",
      "com.google.api.ads.adwords.awreporting.alerting.action.SQLDBPersister"
  };
  
  /**
  * Test each alert action implementation adheres to the interface definition
  */
  @Test
  public void testAlertActionImplementations()  {
    try {
      Class<?> alertRuleInterface = Class.forName(alertActionInterfaceName);
      
      for (String alertRuleClassName : alertActionClassNames) {
        // Check that the alert rule class implements AlertRule interface
        Class<?> alertRuleClass = Class.forName(alertRuleClassName);
        Class<?>[] interfaces = alertRuleClass.getInterfaces();
        assertTrue(Arrays.asList(interfaces).contains(alertRuleInterface));
        
        // Check that the alert rule class has a construction with JsonObject argument
        alertRuleClass.getConstructor(new Class[] {JsonObject.class});
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception thrown");
    }
  }
}
