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

import com.nextech.erp.dao.PageDao;
import com.nextech.erp.model.Page;

@Repository

public class PageDaoImpl extends SuperDaoImpl<Page> implements PageDao{
	
	@Autowired
	SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;

	@Override
	public Page getPageByUrl(String url) throws Exception {
		
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Page> criteria = builder.createQuery(Page.class);
		Root<Page> userRoot = (Root<Page>) criteria.from(Page.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("url"), url),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Page> query = session.createQuery(criteria);
		  List<Page> list = query.getResultList();
		  if (list.isEmpty()) {
		        return null;
		    }
		    return list.get(0);
	}

}

