package com.nextech.dscrm.daoImpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nextech.dscrm.dao.UsertypepageassociationDao;
import com.nextech.dscrm.model.Usertypepageassociation;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
@Repository

public class UsertypepageassociationDaoImpl extends SuperDaoImpl<Usertypepageassociation> implements UsertypepageassociationDao {

	@Autowired
	SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;
	
	@Override
	public List<Usertypepageassociation> getPagesByUsertype(long usertypeId) {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Usertypepageassociation> criteria = builder.createQuery(Usertypepageassociation.class);
		Root<Usertypepageassociation> userRoot  = (Root<Usertypepageassociation>) criteria.from(Usertypepageassociation.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("usertype"), usertypeId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Usertypepageassociation> query = session.createQuery(criteria);
		return query.getResultList();
		
	}

	@Override
	public boolean checkPageAccess(long usertypeId, long pageId) {
		
/*		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Usertypepageassociation.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("usertype.id", usertypeId));
		criteria.add(Restrictions.eq("page.id", pageId));
		boolean hasAccess = (criteria.list().size() > 0 ?  true: false);
		return hasAccess;*/
		
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Usertypepageassociation> criteria = builder.createQuery(Usertypepageassociation.class);
		Root<Usertypepageassociation> userRoot = criteria.from(Usertypepageassociation.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("page"), pageId),builder.equal(userRoot.get("usertype"), usertypeId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Usertypepageassociation> query = session.createQuery(criteria);
		List<Usertypepageassociation> results = query.getResultList();
		  if (results.isEmpty()) {
		        return false;
		    }
		    return true;
	}

	@Override
	public Usertypepageassociation getUserTypePageAssoByPageIduserTypeId(
			long pageId, long userTypeId) throws Exception {
		
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Usertypepageassociation> criteria = builder.createQuery(Usertypepageassociation.class);
		Root<Usertypepageassociation> userRoot = criteria.from(Usertypepageassociation.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("page"), pageId),builder.equal(userRoot.get("usertype"), userTypeId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Usertypepageassociation> query = session.createQuery(criteria);
		List<Usertypepageassociation> results = query.getResultList();
		  if (results.isEmpty()) {
		        return null;
		    }
		    return results.get(0);
	}
	
}