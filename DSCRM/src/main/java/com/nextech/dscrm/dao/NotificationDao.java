package com.nextech.dscrm.dao;

import com.nextech.dscrm.model.Notification;

public interface NotificationDao extends SuperDao<Notification>{

	public Notification getNotifiactionByStatus(long statusId) throws Exception;
	
	public Notification getNotificationByCode(String code) throws Exception;

}
