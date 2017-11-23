package com.nextech.dscrm.factory;

import com.nextech.dscrm.model.Notification;
import com.nextech.dscrm.model.Notificationuserassociation;
import com.nextech.dscrm.model.User;
import com.nextech.dscrm.newDTO.NotificationDTO;
import com.nextech.dscrm.newDTO.NotificationUserAssociatinsDTO;
import com.nextech.dscrm.newDTO.UserDTO;

public class NotificationUserAssRequestResponseFactory {
	
	public static Notificationuserassociation  setNotificationUserAss(NotificationUserAssociatinsDTO notificationUserAssociatinsDTO){
		Notificationuserassociation notificationuserassociation = new Notificationuserassociation();
		notificationuserassociation.setBcc(notificationUserAssociatinsDTO.getBcc());
		notificationuserassociation.setCc(notificationUserAssociatinsDTO.getCc());
		notificationuserassociation.setTo(notificationUserAssociatinsDTO.getTo());
		notificationuserassociation.setId(notificationUserAssociatinsDTO.getId());
		User user  = new User();
		user.setId(notificationUserAssociatinsDTO.getUserId().getId());
		notificationuserassociation.setUser(user);
		Notification notification =  new Notification();
		notification.setId(notificationUserAssociatinsDTO.getNotificationId().getId());
		notificationuserassociation.setNotification(notification);
		notificationuserassociation.setIsactive(true);
		return notificationuserassociation;
	}
	public static NotificationUserAssociatinsDTO setNotifiactionDTO(Notificationuserassociation notificationuserassociation){
		NotificationUserAssociatinsDTO notificationUserAssociatinsDTO = new NotificationUserAssociatinsDTO();
		notificationUserAssociatinsDTO.setBcc(notificationuserassociation.getBcc());
		notificationUserAssociatinsDTO.setCc(notificationuserassociation.getCc());
		notificationUserAssociatinsDTO.setTo(notificationuserassociation.getTo());
		notificationUserAssociatinsDTO.setId(notificationuserassociation.getId());
		UserDTO userDTO = new UserDTO();
		userDTO.setId(notificationuserassociation.getUser().getId());
		userDTO.setUserId(notificationuserassociation.getUser().getUserid());
		userDTO.setFirstName(notificationuserassociation.getUser().getFirstName());
		notificationUserAssociatinsDTO.setUserId(userDTO);
		NotificationDTO notificationDTO =  new NotificationDTO();
		notificationDTO.setId(notificationuserassociation.getNotification().getId());
		notificationDTO.setSubject(notificationuserassociation.getNotification().getSubject());
		notificationDTO.setName(notificationuserassociation.getNotification().getName());
		notificationUserAssociatinsDTO.setNotificationId(notificationDTO);
		notificationUserAssociatinsDTO.setActive(true);
		return notificationUserAssociatinsDTO;
	}

}
