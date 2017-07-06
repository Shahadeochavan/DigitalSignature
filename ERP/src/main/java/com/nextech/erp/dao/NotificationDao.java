package com.nextech.erp.dao;

import com.nextech.erp.model.Notification;

public interface NotificationDao extends SuperDao<Notification>{

	public Notification getNotifiactionByStatus(long statusId) throws Exception;

}
