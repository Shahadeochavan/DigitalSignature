package com.nextech.erp.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.StatustransitionDao;
import com.nextech.erp.model.Statustransition;

@Repository
@Transactional
public class StatustransitionDaoImpl extends SuperDaoImpl<Statustransition> implements StatustransitionDao {
	@Autowired
	SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;

	@Override
	public Statustransition getStatustransitionByEmail(String email) throws Exception{
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Statustransition.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("isNotificationEmail", email));
		Statustransition statustransition= criteria.list().size() > 0 ? (Statustransition) criteria.list().get(0) : null;
				return statustransition;
	}
}
