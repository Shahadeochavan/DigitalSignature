package com.nextech.erp.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nextech.erp.dao.UsertypepageassociationDao;
import com.nextech.erp.model.Rawmaterialvendorassociation;
import com.nextech.erp.model.Usertypepageassociation;

@Repository

public class UsertypepageassociationDaoImpl extends SuperDaoImpl<Usertypepageassociation> implements UsertypepageassociationDao {

	@Autowired
	SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Usertypepageassociation> getPagesByUsertype(long usertypeId) {
		session = sessionFactory.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Usertypepageassociation.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("usertype.id", usertypeId));
		List<Usertypepageassociation> usertypepageassociationList = criteria.list().size() > 0 ?  criteria.list(): null;
		return usertypepageassociationList;
	}

	@Override
	public boolean checkPageAccess(long usertypeId, long pageId) {
		session = sessionFactory.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Usertypepageassociation.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("usertype.id", usertypeId));
		criteria.add(Restrictions.eq("page.id", pageId));
		boolean hasAccess = (criteria.list().size() > 0 ?  true: false);
		return hasAccess;
	}

	@Override
	public Usertypepageassociation getUserTypePageAssoByPageIduserTypeId(
			long pageId, long userTypeId) throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Usertypepageassociation.class);
		criteria.add(Restrictions.eq("page.id", pageId));
		criteria.add(Restrictions.eq("usertype.id", userTypeId));
		Usertypepageassociation rawmaterialorderassociation = (Usertypepageassociation) (criteria.list().size() > 0 ? criteria.list().get(0) : null);
		return rawmaterialorderassociation;
	}
	
}