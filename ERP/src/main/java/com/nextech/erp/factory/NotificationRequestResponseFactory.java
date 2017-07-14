package com.nextech.erp.factory;

import com.nextech.erp.model.Notification;
import com.nextech.erp.newDTO.NotificationDTO;

public class NotificationRequestResponseFactory {
	
	public static Notification setNotification(NotificationDTO notificationDTO){
		Notification notification = new Notification();
		notification.setBeanClass(notificationDTO.getBeanClass());
		notification.setDescription(notificationDTO.getDescription());
		notification.setId(notificationDTO.getId());
		notification.setIsactive(true);
		notification.setName(notificationDTO.getName());
		notification.setStatus1(notificationDTO.getStatus1());
		notification.setSubject(notificationDTO.getSubject());
		notification.setTemplate(notificationDTO.getTemplate());
		notification.setType(notificationDTO.getType());
		return notification;
	}

}
