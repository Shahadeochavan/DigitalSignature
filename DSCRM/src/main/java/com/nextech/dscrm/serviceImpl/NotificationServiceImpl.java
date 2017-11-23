package com.nextech.dscrm.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.dscrm.dao.NotificationDao;
import com.nextech.dscrm.factory.NotificationRequestResponseFactory;
import com.nextech.dscrm.model.Notification;
import com.nextech.dscrm.newDTO.NotificationDTO;
import com.nextech.dscrm.service.NotificationService;
@Service
public class NotificationServiceImpl extends CRUDServiceImpl<Notification> implements NotificationService{


	@Autowired
	NotificationDao notificationDao;

	@Override
	public NotificationDTO getNotifiactionByStatus(long statusId) throws Exception {
		
		Notification notification = notificationDao.getNotifiactionByStatus(statusId);
		if(notification==null){
			return null;
		}
		NotificationDTO notificationDTO = NotificationRequestResponseFactory.setNotificationDTO(notification);
		return notificationDTO;
	}

	@Override
	public List<NotificationDTO> getNofificationList(
			List<NotificationDTO> notificationDTOs) throws Exception {
		
		notificationDTOs =  new ArrayList<NotificationDTO>();
		List<Notification> notifications = notificationDao.getList(Notification.class);
		if(notifications.isEmpty()){
			return null;
		}
		for (Notification notification : notifications) {
			NotificationDTO notificationDTO = NotificationRequestResponseFactory.setNotificationDTO(notification);
			notificationDTOs.add(notificationDTO);
		}
		return notificationDTOs;
	}

	@Override
	public NotificationDTO getNotificationDTOById(long id) throws Exception {
		
		Notification notification = notificationDao.getById(Notification.class, id);
		if(notification==null){
			return null;
		}
		NotificationDTO notificationDTO = NotificationRequestResponseFactory.setNotificationDTO(notification);
		return notificationDTO;
	}

	@Override
	public NotificationDTO deleteNofificationById(long id) throws Exception {
		
		Notification notification = notificationDao.getById(Notification.class, id);
		if(notification==null){
			return null;
		}
		notification.setIsactive(false);
		notificationDao.update(notification);
		NotificationDTO notificationDTO = NotificationRequestResponseFactory.setNotificationDTO(notification);
		return notificationDTO;
	}

	@Override
	public NotificationDTO getNotificationByCode(String code) throws Exception {
		
		Notification notification = notificationDao.getNotificationByCode(code);
		NotificationDTO notificationDTO = NotificationRequestResponseFactory.setNotificationDTO(notification);
		return notificationDTO;
	}

}
