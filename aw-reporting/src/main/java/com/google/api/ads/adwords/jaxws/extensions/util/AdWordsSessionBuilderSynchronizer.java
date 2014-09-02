package com.google.api.ads.adwords.jaxws.extensions.util;

import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.common.lib.exception.ValidationException;

/**
 * Class to synchronize all the access to the {@code AdWordsSession}.
 *
 * It is just a wrapper around the session builder, to make that there just one request per time.
 *
 * @author gustavomoreira@google.com (Gustavo Moreira)
 */
public class AdWordsSessionBuilderSynchronizer {

  private final AdWordsSession.Builder builder;

  /**
   * Constructor.
   *
   * @param builder the session builder for the API.
   */
  public AdWordsSessionBuilderSynchronizer(AdWordsSession.Builder builder) {
    this.builder = builder;
  }

  /**
   * Builds a new COPY {@code AdWordsSession} for the given cid.
   *
   * @param cid the cid
   * @return the session.
   * @throws ValidationException error validating the CID.
   */
  public synchronized AdWordsSession getAdWordsSessionCopy(Long cid) throws ValidationException {

    AdWordsSession adWordsSession = AdWordsSessionUtil.copy(this.builder.build());
    adWordsSession.setClientCustomerId(String.valueOf(cid));
    return adWordsSession;
  }
}
