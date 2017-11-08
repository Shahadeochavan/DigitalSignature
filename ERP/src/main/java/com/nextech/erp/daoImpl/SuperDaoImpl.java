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
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.SuperDao;

@Service("superDao")
public class SuperDaoImpl<T> implements SuperDao<T>{

	@Autowired
	SessionFactory sessionFactory;
	Session session = null;

	@Override
	public Long add(T bean) throws Exception {
		session = sessionFactory.openSession();
		Long id = (Long) session.save(bean);
		return id;
	}

	@Override
	public T getById(Class<T> z,long id) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(z);
		Root<T> userRoot = (Root<T>) criteria.from(z);
		criteria.select(userRoot).where(builder.equal(userRoot.get("id"), id),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<T> query = session.createQuery(criteria);
		List<T> results = query.getResultList();
		  if (results.isEmpty()) {
		        return null;
		    }
		    return results.get(0);
	}
	
	@Override
	public List<T> getList(Class<T> z) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(z);
		Root<T> userRoot = (Root<T>) criteria.from(z);
		criteria.select(userRoot).where(builder.equal(userRoot.get("isactive"), true));
		TypedQuery<T> query = session.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public boolean delete(Class<T> z,long id) throws Exception {
		session = sessionFactory.openSession();
		Object o = session.load(z, id);
		session.beginTransaction();
		session.delete(o);
		return true;
	}

	@Override
	public T update(T bean) throws Exception {
		session = sessionFactory.openSession();
		Transaction tx =session.beginTransaction();
		@SuppressWarnings("unchecked")
		T mergedBean = (T) session.merge(bean);
		tx.commit();
		return mergedBean;
	}
}