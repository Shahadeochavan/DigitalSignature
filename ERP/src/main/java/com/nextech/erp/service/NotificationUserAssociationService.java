package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Notificationuserassociation;

public interface NotificationUserAssociationService extends CRUDService<Notificationuserassociation>{

	public Notificationuserassociation getNotifiactionByUserId(long userId) throws Exception;
	
	List<Notificationuserassociation> getNotificationuserassociationByUserId(long userId) throws Exception;
	
	List<Notificationuserassociation> getNotificationuserassociationBynotificationId(long notificationId) throws Exception;

}
