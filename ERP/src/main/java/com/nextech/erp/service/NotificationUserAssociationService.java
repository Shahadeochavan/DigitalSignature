package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Notificationuserassociation;
import com.nextech.erp.newDTO.NotificationUserAssociatinsDTO;

public interface NotificationUserAssociationService extends CRUDService<Notificationuserassociation>{

	List<Notificationuserassociation> getNotificationuserassociationByUserId(long userId) throws Exception;
	
	List<Notificationuserassociation> getNotificationuserassociationBynotificationId(long notificationId) throws Exception;
	
	List<NotificationUserAssociatinsDTO> getNotificationUserAssociatinsDTOs(long notificationId)throws Exception;
	
	List<NotificationUserAssociatinsDTO> getNotificationUserAssoList() throws Exception;
	
	public NotificationUserAssociatinsDTO getNotificationUserById(long id) throws Exception;
	
	public NotificationUserAssociatinsDTO deleteNotificationUserAsso(long id) throws Exception;

}
