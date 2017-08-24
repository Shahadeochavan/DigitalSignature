package com.nextech.erp.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.nextech.erp.dao.NotificationDao;
import com.nextech.erp.model.Notification;

@Repository

public class NotificationDaoImpl extends SuperDaoImpl<Notification> implements NotificationDao{

	@Override
	public Notification getNotifiactionByStatus(long statusId) throws Exception {
		session = sessionFactory.getCurrentSession();
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Notification> criteria = builder.createQuery(Notification.class);
		Root<Notification> userRoot = (Root<Notification>) criteria.from(Notification.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("status1"), statusId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Notification> query = session.createQuery(criteria);
		  List<Notification> list = query.getResultList();
		  if (list.isEmpty()) {
		        return null;
		    }
		    return list.get(0);
	}

	@Override
	public Notification getNotificationByCode(String code) throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.getCurrentSession();
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Notification> criteria = builder.createQuery(Notification.class);
		Root<Notification> userRoot = (Root<Notification>) criteria.from(Notification.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("code"), code),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Notification> query = session.createQuery(criteria);
		  List<Notification> list = query.getResultList();
		  if (list.isEmpty()) {
		        return null;
		    }
		    return list.get(0);
	}

}
