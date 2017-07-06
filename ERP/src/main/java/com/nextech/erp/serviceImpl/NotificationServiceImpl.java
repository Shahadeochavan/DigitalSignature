package com.nextech.erp.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.NotificationDao;
import com.nextech.erp.model.Notification;
import com.nextech.erp.service.NotificationService;
@Service
public class NotificationServiceImpl extends CRUDServiceImpl<Notification> implements NotificationService{


	@Autowired
	NotificationDao notificationDao;

	@Override
	public Notification getNotifiactionByStatus(long statusId) throws Exception {
		// TODO Auto-generated method stub
		return notificationDao.getNotifiactionByStatus(statusId);
	}

}
