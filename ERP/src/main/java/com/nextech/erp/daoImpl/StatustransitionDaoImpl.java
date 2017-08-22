package com.nextech.erp.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nextech.erp.dao.StatustransitionDao;
import com.nextech.erp.model.Statustransition;

@Repository

public class StatustransitionDaoImpl extends SuperDaoImpl<Statustransition> implements StatustransitionDao {
	@Autowired
	SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;

	@Override
	public Statustransition getStatustransitionByEmail(String email) throws Exception{
				session = sessionFactory.openSession();
				CriteriaBuilder builder = session.getCriteriaBuilder();
				CriteriaQuery<Statustransition> criteria = builder.createQuery(Statustransition.class);
				Root<Statustransition> userRoot = criteria.from(Statustransition.class);
				criteria.select(userRoot).where(builder.equal(userRoot.get("isNotificationEmail"), email),builder.equal(userRoot.get("isactive"), true));
				TypedQuery<Statustransition> query = session.createQuery(criteria);
				List<Statustransition> results = query.getResultList();
				  if (results.isEmpty()) {
				        return null;
				    }
				    return results.get(0);
	}
}
