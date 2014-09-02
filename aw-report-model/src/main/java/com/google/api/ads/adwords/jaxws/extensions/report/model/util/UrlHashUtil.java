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

package com.google.api.ads.adwords.jaxws.extensions.report.model.util;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Util class for creating a Sha1 for URLs
 *
 * @author jtoledo@google.com (Julian Toledo)
 */
public class UrlHashUtil {

  /**
   * Creates a SHA-1 Hash of the url
   * 
   * @param url the url that needs to be hashed
   * @return a Stri g with a SHA-1 hash of the URL
   */
  public static String createUrlHash(String url) {
    String hash = null;
    MessageDigest messageDigest;
    try {
      messageDigest = MessageDigest.getInstance("SHA-1");
      messageDigest.reset();
      messageDigest.update(url.getBytes("UTF-8"));
      final byte[] resultByte = messageDigest.digest();
      hash = new String(Hex.encodeHex(resultByte));
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return hash;
  }
}
