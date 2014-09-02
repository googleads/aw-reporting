package com.google.api.ads.adwords.jaxws.extensions;

import com.google.api.client.util.Sets;

import junit.framework.Assert;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Set;

/**
 * Test case for the {@link AwReporting} class.
 */
public class AwReportingTest {

  /**
   * Test the file reading feature, and that the account IDs are properly added to the given set
   */
  @Test
  public void testAddAccountFromFile() throws FileNotFoundException {

    Set<Long> accountIdsSet = Sets.newHashSet();

    AwReporting.addAccountsFromFile(accountIdsSet, "src/test/resources/util/account-for-test.txt");

    Assert.assertEquals(5, accountIdsSet.size());

    Assert.assertTrue(accountIdsSet.contains(1235431234L));
    Assert.assertTrue(accountIdsSet.contains(3492871722L));
    Assert.assertTrue(accountIdsSet.contains(5731985421L));
    Assert.assertTrue(accountIdsSet.contains(3821071791L));
    Assert.assertTrue(accountIdsSet.contains(5471928097L));

  }

}
