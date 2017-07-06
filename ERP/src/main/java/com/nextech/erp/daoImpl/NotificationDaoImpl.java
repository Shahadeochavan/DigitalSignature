package com.nextech.erp.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.NotificationDao;
import com.nextech.erp.model.Notification;

@Repository
@Transactional
public class NotificationDaoImpl extends SuperDaoImpl<Notification> implements NotificationDao{

	@Override
	public Notification getNotifiactionByStatus(long statusId) throws Exception {
		// TODO Auto-generated method stub
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Notification.class);
		criteria.add(Restrictions.eq("status1.id",statusId));
		criteria.add(Restrictions.eq("isactive", true));
		Notification notification = criteria.list().size() > 0 ? (Notification) criteria.list().get(0) : null;
		return notification;
	}

}
