package com.nextech.erp.dao;

import java.util.List;

import com.nextech.erp.model.Notificationuserassociation;

public interface NotificationUserassociationDao extends SuperDao<Notificationuserassociation> {

	public Notificationuserassociation getNotifiactionByUserId(long userId) throws Exception;
	
	List<Notificationuserassociation> getNotificationuserassociationByUserId(long userId) throws Exception;
	
	List<Notificationuserassociation> getNotificationuserassociationBynotificationId(long notificationId) throws Exception;

}
