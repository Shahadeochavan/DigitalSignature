package com.nextech.dscrm.factory;


import com.nextech.dscrm.model.Notification;
import com.nextech.dscrm.model.Status;
import com.nextech.dscrm.newDTO.NotificationDTO;
import com.nextech.dscrm.newDTO.StatusDTO;

public class NotificationRequestResponseFactory {
	
	public static Notification setNotification(NotificationDTO notificationDTO){
		Notification notification = new Notification();
		notification.setBeanClass(notificationDTO.getBeanClass());
		notification.setDescription(notificationDTO.getDescription());
		notification.setId(notificationDTO.getId());
		notification.setIsactive(true);
		notification.setName(notificationDTO.getName());
		notification.setSubject(notificationDTO.getSubject());
		notification.setTemplate(notificationDTO.getTemplate());
		notification.setType(notificationDTO.getType());
		Status  status =  new Status();
		status.setId(notificationDTO.getStatus1().getId());
		notification.setStatus1(status);
		notification.setStatus2(status);
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
		StatusDTO statusDTO =  new StatusDTO();
		statusDTO.setId(notification.getStatus1().getId());
		statusDTO.setStatusName(notification.getStatus1().getName());
		notificationDTO.setStatus1(statusDTO);
		notificationDTO.setStatus2(statusDTO);
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
