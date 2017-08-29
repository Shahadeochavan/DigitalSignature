package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Notification;
import com.nextech.erp.newDTO.NotificationDTO;

public interface NotificationService extends CRUDService<Notification>{

	public NotificationDTO getNotifiactionByStatus(long statusId) throws Exception;
	
	public List<NotificationDTO> getNofificationList(List<NotificationDTO> notificationDTOs) throws Exception;
	
	public NotificationDTO getNotificationDTOById(long id) throws Exception;
	
	public NotificationDTO deleteNofificationById(long id) throws Exception;
	
	public NotificationDTO getNotificationByCode(String code)throws Exception;

}
