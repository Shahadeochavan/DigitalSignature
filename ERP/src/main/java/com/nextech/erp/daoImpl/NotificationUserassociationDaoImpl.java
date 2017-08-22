package com.nextech.erp.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import com.nextech.erp.dao.NotificationUserassociationDao;
import com.nextech.erp.model.Notificationuserassociation;

@Repository

public class NotificationUserassociationDaoImpl extends
		SuperDaoImpl<Notificationuserassociation> implements
		NotificationUserassociationDao {

	@Override
	public Notificationuserassociation getNotifiactionByUserId(long userId)
			throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.getCurrentSession();
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Notificationuserassociation> criteria = builder.createQuery(Notificationuserassociation.class);
		Root<Notificationuserassociation> userRoot = (Root<Notificationuserassociation>) criteria.from(Notificationuserassociation.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("user"), userId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Notificationuserassociation> query = session.createQuery(criteria);
		  List<Notificationuserassociation> list = query.getResultList();
		  if (list.isEmpty()) {
		        return null;
		    }
		    return list.get(0);
	}

	@Override
	public List<Notificationuserassociation> getNotificationuserassociationByUserId(
			long userId) throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Notificationuserassociation> criteria = builder.createQuery(Notificationuserassociation.class);
		Root<Notificationuserassociation> userRoot  = (Root<Notificationuserassociation>) criteria.from(Notificationuserassociation.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("user"), userId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Notificationuserassociation> query = session.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public List<Notificationuserassociation> getNotificationuserassociationBynotificationId(
			long notificationId) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Notificationuserassociation> criteria = builder.createQuery(Notificationuserassociation.class);
		Root<Notificationuserassociation> userRoot  = (Root<Notificationuserassociation>) criteria.from(Notificationuserassociation.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("notification"), notificationId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Notificationuserassociation> query = session.createQuery(criteria);
		return query.getResultList();
	}

}
