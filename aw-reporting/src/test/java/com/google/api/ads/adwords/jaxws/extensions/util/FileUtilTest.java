package com.google.api.ads.adwords.jaxws.extensions.util;

import junit.framework.Assert;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Test case for the {@link FileUtil} class.
 */
public class FileUtilTest {

  /**
   * Tests the file reading feature
   */
  @Test
  public void testLineListing() throws FileNotFoundException {

    List<String> linesAsStrings =
        FileUtil.readFileLinesAsStrings(new File("src/test/resources/util/account-for-test.txt"));

    Assert.assertEquals(10, linesAsStrings.size());

    Assert.assertTrue(linesAsStrings.contains("123-543-1234"));
    Assert.assertTrue(linesAsStrings.contains("349-287-1722"));
    Assert.assertTrue(linesAsStrings.contains("573-198-5421"));
    Assert.assertTrue(linesAsStrings.contains("382-107-1791"));
    Assert.assertTrue(linesAsStrings.contains("547-192-8097"));

  }
}
