package com.nextech.erp.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.NotificationUserassociationDao;
import com.nextech.erp.model.Notificationuserassociation;
import com.nextech.erp.service.NotificationUserAssociationService;
@Service
public class NotificationUserAssociationServiceImpl extends CRUDServiceImpl<Notificationuserassociation> implements NotificationUserAssociationService {

	@Autowired
	NotificationUserassociationDao notificationUserassociationDao;

	@Override
	public Notificationuserassociation getNotifiactionByUserId(long userId)
			throws Exception {
		// TODO Auto-generated method stub
		return notificationUserassociationDao.getNotifiactionByUserId(userId);
	}

	@Override
	public List<Notificationuserassociation> getNotificationuserassociationByUserId(
			long userId) throws Exception {
		// TODO Auto-generated method stub
		return notificationUserassociationDao.getNotificationuserassociationByUserId(userId);
	}

	@Override
	public List<Notificationuserassociation> getNotificationuserassociationBynotificationId(
			long notificationId) throws Exception {
		// TODO Auto-generated method stub
		return notificationUserassociationDao.getNotificationuserassociationBynotificationId(notificationId);
	}

}
