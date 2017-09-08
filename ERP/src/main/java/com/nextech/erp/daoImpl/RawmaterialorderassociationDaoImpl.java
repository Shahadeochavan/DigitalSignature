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

import com.nextech.erp.dao.RawmaterialorderassociationDao;
import com.nextech.erp.model.Rawmaterialorderassociation;

@Repository

public class RawmaterialorderassociationDaoImpl extends SuperDaoImpl<Rawmaterialorderassociation> implements
		RawmaterialorderassociationDao {
	@Autowired
	SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;

	@Override
	public List<Rawmaterialorderassociation> getRMOrderRMAssociationByRMOrderId(long id) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Rawmaterialorderassociation> criteria = builder.createQuery(Rawmaterialorderassociation.class);
		Root<Rawmaterialorderassociation> userRoot  = (Root<Rawmaterialorderassociation>) criteria.from(Rawmaterialorderassociation.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("rawmaterialorder"), id),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Rawmaterialorderassociation> query = session.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public Rawmaterialorderassociation getRMOrderRMAssociationByRMOrderIdandRMId(long id, long rmId) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Rawmaterialorderassociation> criteria = builder.createQuery(Rawmaterialorderassociation.class);
		Root<Rawmaterialorderassociation> userRoot = criteria.from(Rawmaterialorderassociation.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("rawmaterial"), rmId),builder.equal(userRoot.get("rawmaterialorder"), id),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Rawmaterialorderassociation> query = session.createQuery(criteria);
		List<Rawmaterialorderassociation> results = query.getResultList();
		  if (results.isEmpty()) {
		        return null;
		    }
		    return results.get(0);
	}
}
