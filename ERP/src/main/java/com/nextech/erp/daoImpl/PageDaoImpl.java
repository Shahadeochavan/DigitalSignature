package com.nextech.erp.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.PageDao;
import com.nextech.erp.model.Page;

@Repository
@Transactional
public class PageDaoImpl extends SuperDaoImpl<Page> implements PageDao{
	
	@Autowired
	SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;

	@Override
	public Page getPageByUrl(String url) throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Page.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("url", url));
		Page page = criteria.list().size() > 0 ? (Page) criteria.list()
				.get(0) : null;
		 // //session.close();
		return page;
	}

}

