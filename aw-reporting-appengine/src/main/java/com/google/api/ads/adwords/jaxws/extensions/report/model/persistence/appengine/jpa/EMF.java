package com.google.api.ads.adwords.jaxws.extensions.report.model.persistence.appengine.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class EMF {
  private static final EntityManagerFactory emfInstance =
      Persistence.createEntityManagerFactory("transactions-optional");

  private EMF() {}

  public static EntityManagerFactory get() {
    return emfInstance;
  }

  public static EntityManager getPersistenceManager() {
    return EMF.get().createEntityManager();
  }
}
