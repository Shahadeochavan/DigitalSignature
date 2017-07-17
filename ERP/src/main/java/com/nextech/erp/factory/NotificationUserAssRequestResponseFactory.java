package com.nextech.erp.factory;

import com.nextech.erp.model.Notificationuserassociation;
import com.nextech.erp.newDTO.NotificationUserAssociatinsDTO;

public class NotificationUserAssRequestResponseFactory {
	
	public static Notificationuserassociation  setNotificationUserAss(NotificationUserAssociatinsDTO notificationUserAssociatinsDTO){
		Notificationuserassociation notificationuserassociation = new Notificationuserassociation();
		notificationuserassociation.setBcc(notificationUserAssociatinsDTO.getBcc());
		notificationuserassociation.setCc(notificationUserAssociatinsDTO.getCc());
		notificationuserassociation.setTo(notificationUserAssociatinsDTO.getTo());
		notificationuserassociation.setId(notificationUserAssociatinsDTO.getId());
		notificationuserassociation.setUser(notificationUserAssociatinsDTO.getUserId());
		notificationuserassociation.setNotification(notificationUserAssociatinsDTO.getNotificationId());
		notificationuserassociation.setIsactive(true);
		return notificationuserassociation;
	}
	public static NotificationUserAssociatinsDTO setNotifiactionDTO(Notificationuserassociation notificationuserassociation){
		NotificationUserAssociatinsDTO notificationUserAssociatinsDTO = new NotificationUserAssociatinsDTO();
		notificationUserAssociatinsDTO.setBcc(notificationuserassociation.getBcc());
		notificationUserAssociatinsDTO.setCc(notificationuserassociation.getCc());
		notificationUserAssociatinsDTO.setTo(notificationuserassociation.getTo());
		notificationUserAssociatinsDTO.setId(notificationuserassociation.getId());
		notificationUserAssociatinsDTO.setUserId(notificationuserassociation.getUser());
		notificationUserAssociatinsDTO.setNotificationId(notificationuserassociation.getNotification());
		notificationUserAssociatinsDTO.setActive(true);
		return notificationUserAssociatinsDTO;
	}

}
