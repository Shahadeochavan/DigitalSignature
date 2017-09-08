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

import com.nextech.erp.dao.RawmaterialDao;
import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.model.Rawmaterialvendorassociation;

@Repository

public class RawmaterialDaoImpl extends SuperDaoImpl<Rawmaterial> implements RawmaterialDao{
	
	@Autowired
	SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;
	
	@Override
	public List<Rawmaterialvendorassociation> getRawmaterialByVenodrId(long id)
			throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Rawmaterialvendorassociation> criteria = builder.createQuery(Rawmaterialvendorassociation.class);
		Root<Rawmaterialvendorassociation> userRoot  = (Root<Rawmaterialvendorassociation>) criteria.from(Rawmaterialvendorassociation.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("vendor"), id),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Rawmaterialvendorassociation> query = session.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public Rawmaterial getRMByRMId(long id) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Rawmaterial> criteria = builder.createQuery(Rawmaterial.class);
		Root<Rawmaterial> userRoot = criteria.from(Rawmaterial.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("id"), id),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Rawmaterial> query = session.createQuery(criteria);
		List<Rawmaterial> results = query.getResultList();
		  if (results.isEmpty()) {
		        return null;
		    }
		    return results.get(0);
	}

	@Override
	public List<Rawmaterial> getRMByRMTypeId(long id) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Rawmaterial> criteria = builder.createQuery(Rawmaterial.class);
		Root<Rawmaterial> userRoot  = (Root<Rawmaterial>) criteria.from(Rawmaterial.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("rmtype"), id),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Rawmaterial> query = session.createQuery(criteria);
		return query.getResultList();
	}
}

