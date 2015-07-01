// Copyright 2015 Google Inc. All Rights Reserved.
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

package com.google.api.ads.adwords.awreporting.alerting;

import com.google.api.ads.adwords.awreporting.alerting.AwReportingAlerting;
import com.google.api.client.util.Sets;

import junit.framework.Assert;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Set;

/**
 * Test case for the {@link AwReportingAlerging} class.
 */
public class AwReportingAlertingTest {

  /**
   * Test the file reading feature, and that the account IDs are properly added to the given set
   */
  @Test
  public void testAddAccountFromFile() throws FileNotFoundException {

    Set<Long> accountIdsSet = Sets.newHashSet();

    AwReportingAlerting.addAccountsFromFile(accountIdsSet, "src/test/resources/util/accounts-for-test.txt");

    Assert.assertEquals(5, accountIdsSet.size());

    Assert.assertTrue(accountIdsSet.contains(1235431234L));
    Assert.assertTrue(accountIdsSet.contains(3492871722L));
    Assert.assertTrue(accountIdsSet.contains(5731985421L));
    Assert.assertTrue(accountIdsSet.contains(3821071791L));
    Assert.assertTrue(accountIdsSet.contains(5471928097L));
  }
}
