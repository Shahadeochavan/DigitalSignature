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
		notification.setStatus1(notificationDTO.getStatus1());
		notification.setStatus2(notificationDTO.getStatus1());
		notification.setCode(notificationDTO.getCode());
		return notification;
	}
	
	public static NotificationDTO setNotificationDTO(Notification notification){
		NotificationDTO notificationDTO = new NotificationDTO();
		notificationDTO.setBeanClass(notification.getBeanClass());
		notificationDTO.setDescription(notification.getDescription());
		notificationDTO.setId(notification.getId());
		notificationDTO.setActive(true);
		notificationDTO.setName(notification.getName());
		notificationDTO.setStatus1(notification.getStatus1());
		notificationDTO.setSubject(notification.getSubject());
		notificationDTO.setTemplate(notification.getTemplate());
		notificationDTO.setType(notification.getType());
		notificationDTO.setCreatedBy(notification.getCreatedBy());
		notificationDTO.setCreatedDate(notification.getCreatedDate());
		notificationDTO.setUpdatedBy(notification.getUpdatedBy());
		notificationDTO.setUpdatedDate(notification.getUpdatedDate());
		notificationDTO.setCode(notification.getCode());
		return notificationDTO;
	}

}
