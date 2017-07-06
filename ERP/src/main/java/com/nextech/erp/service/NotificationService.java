package com.nextech.erp.service;

import com.nextech.erp.model.Notification;

public interface NotificationService extends CRUDService<Notification>{

	public Notification getNotifiactionByStatus(long statusId) throws Exception;

}
