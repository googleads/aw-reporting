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

package com.google.api.ads.adwords.jaxws.extensions.proxy;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Test case for the {@code JaxWsProxySelector} class.
 *
 * @author jtoledo@google.com (Julian Toledo)
 */
public class JaxWsProxySelectorTest {

  private static final String HTTP_PROXY_HOST = "http.proxyHost";
  private static final String HTTP_PROXY_PORT = "http.proxyPort";

  private static final String HTTPS_PROXY_HOST = "https.proxyHost";
  private static final String HTTPS_PROXY_PORT = "https.proxyPort";

  private static final String FTP_PROXY_HOST = "ftp.proxyHost";
  private static final String FTP_PROXY_PORT = "ftp.proxyPort";

  private static final String SOCKS_PROXY_HOST = "socksProxyHost";
  private static final String SOCKS_PROXY_PORT = "socksProxyPort";

  private URI testUri;

  @Before
  public void setUp() throws URISyntaxException {
    testUri = new URI("http://www.test.com");
  }

  /**
   * Tests select(URI) NO_PROXY
   */
  @Test
  public void testSelect_Direct() {
    JaxWsProxySelector ps = new JaxWsProxySelector(ProxySelector.getDefault());
    ProxySelector.setDefault(ps);

    List<Proxy> list = ps.select(testUri);
    assertEquals(list.get(0), Proxy.NO_PROXY);
  }

  /**
   * Tests select(URI) HTTP
   */
  @Test
  public void testSelect_HTTP() throws UnknownHostException {
    System.getProperties().put(HTTP_PROXY_HOST, "127.0.0.1");
    System.getProperties().put(HTTP_PROXY_PORT, "8888");

    JaxWsProxySelector ps = new JaxWsProxySelector(ProxySelector.getDefault());
    ProxySelector.setDefault(ps);

    List<Proxy> list = ps.select(testUri);

    assertEquals(list.get(0),
        new Proxy(Type.HTTP, new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 8888)));

    System.getProperties().remove(HTTP_PROXY_HOST);
    System.getProperties().remove(HTTP_PROXY_PORT);
  }

  /**
   * Tests select(URI) HTTS
   */
  @Test
  public void testSelect_HTTPS() throws UnknownHostException {
    System.getProperties().put(HTTPS_PROXY_HOST, "127.0.0.1");
    System.getProperties().put(HTTPS_PROXY_PORT, "8888");

    JaxWsProxySelector ps = new JaxWsProxySelector(ProxySelector.getDefault());
    ProxySelector.setDefault(ps);

    List<Proxy> list = ps.select(testUri);
    assertEquals(list.get(0),
        new Proxy(Type.HTTP, new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 8888)));

    System.getProperties().remove(HTTPS_PROXY_HOST);
    System.getProperties().remove(HTTPS_PROXY_PORT);
  }

  /**
   * Tests select(URI) FTP
   */
  @Test
  public void testSelect_FTP() throws UnknownHostException {
    System.getProperties().put(FTP_PROXY_HOST, "127.0.0.1");
    System.getProperties().put(FTP_PROXY_PORT, "8888");

    JaxWsProxySelector ps = new JaxWsProxySelector(ProxySelector.getDefault());
    ProxySelector.setDefault(ps);

    List<Proxy> list = ps.select(testUri);
    assertEquals(list.get(0),
        new Proxy(Type.HTTP, new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 8888)));

    System.getProperties().remove(FTP_PROXY_HOST);
    System.getProperties().remove(FTP_PROXY_PORT);
  }

  /**
   * Tests select(URI) SOCKS
   */
  @Test
  public void testSelect_Socks() throws UnknownHostException {
    System.getProperties().put(SOCKS_PROXY_HOST, "127.0.0.1");
    System.getProperties().put(SOCKS_PROXY_PORT, "8888");

    JaxWsProxySelector ps = new JaxWsProxySelector(ProxySelector.getDefault());
    ProxySelector.setDefault(ps);

    List<Proxy> list = ps.select(testUri);
    assertEquals(list.get(0),
        new Proxy(Type.SOCKS, new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 8888)));

    System.getProperties().remove(SOCKS_PROXY_HOST);
    System.getProperties().remove(SOCKS_PROXY_PORT);
  }

  /**
   * Tests select(URI) IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSelect_IllegalArgumentException() {
    JaxWsProxySelector ps = new JaxWsProxySelector(ProxySelector.getDefault());
    ps.select(null);
  }

  /**
   * Tests the connectFailed(URI, SocketAddress, IOException)
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConnectFailed() {
    JaxWsProxySelector ps = new JaxWsProxySelector(ProxySelector.getDefault());
    ps.connectFailed(null, null, null);
  }
}
