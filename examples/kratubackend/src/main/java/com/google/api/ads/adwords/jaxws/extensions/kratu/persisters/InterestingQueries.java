package com.google.api.ads.adwords.jaxws.extensions.kratu.persisters;

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
