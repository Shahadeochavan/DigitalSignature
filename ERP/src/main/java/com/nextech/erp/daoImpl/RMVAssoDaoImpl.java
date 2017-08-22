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

import com.nextech.erp.dao.RMVAssoDao;
import com.nextech.erp.model.Rawmaterialvendorassociation;

@Repository

public class RMVAssoDaoImpl extends SuperDaoImpl<Rawmaterialvendorassociation>
		implements RMVAssoDao {

	@Autowired
	SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;

	@Override
	public Rawmaterialvendorassociation getRMVAssoByVendorIdRMId(long vendorId,
			long rmId) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Rawmaterialvendorassociation> criteria = builder.createQuery(Rawmaterialvendorassociation.class);
		Root<Rawmaterialvendorassociation> userRoot = criteria.from(Rawmaterialvendorassociation.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("vendor"), vendorId),builder.equal(userRoot.get("rawmaterial"), rmId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Rawmaterialvendorassociation> query = session.createQuery(criteria);
		List<Rawmaterialvendorassociation> results = query.getResultList();
		  if (results.isEmpty()) {
		        return null;
		    }
		    return results.get(0);
}
	
	public List<Rawmaterialvendorassociation> getRawmaterialvendorassociationListByRMId(long rmId) {		
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Rawmaterialvendorassociation> criteria = builder.createQuery(Rawmaterialvendorassociation.class);
		Root<Rawmaterialvendorassociation> userRoot  = (Root<Rawmaterialvendorassociation>) criteria.from(Rawmaterialvendorassociation.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("rawmaterial"), rmId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Rawmaterialvendorassociation> query = session.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public Rawmaterialvendorassociation getRMVAssoByRMId(long rmId)
			throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Rawmaterialvendorassociation> criteria = builder.createQuery(Rawmaterialvendorassociation.class);
		Root<Rawmaterialvendorassociation> userRoot = criteria.from(Rawmaterialvendorassociation.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("rawmaterial"), rmId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Rawmaterialvendorassociation> query = session.createQuery(criteria);
		List<Rawmaterialvendorassociation> results = query.getResultList();
		  if (results.isEmpty()) {
		        return null;
		    }
		    return results.get(0);
	}

}
