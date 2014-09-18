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

package com.google.api.ads.adwords.awreporting.kratubackend.util;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Qualifier("kratuEntitiesService")
public class InterestingQueries {

  @Autowired
  private SessionFactory sessionFactory;

  @Transactional
  public List<?> getMin(Class<?> clazz, String mccid, String propertyName1, String propertyName2) {

    return this.createCriteria(clazz)
        .add(Restrictions.eq("topAccountId", Long.parseLong(mccid, 10)))
        .setProjection(Projections.projectionList()
            .add(Projections.min(propertyName1))
            .add(Projections.max(propertyName2))).list();
  }

  /**
   * Creates a new criteria for the current session
   * 
   * @param clazz
   *            the class of the entity
   * @return the criteria for the current session
   */
  private <T> Criteria createCriteria(Class<T> clazz) {

    Session session = this.sessionFactory.getCurrentSession();
    return session.createCriteria(clazz);
  }

}
