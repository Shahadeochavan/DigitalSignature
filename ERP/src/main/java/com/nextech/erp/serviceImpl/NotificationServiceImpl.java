package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.NotificationDao;
import com.nextech.erp.factory.NotificationRequestResponseFactory;
import com.nextech.erp.model.Notification;
import com.nextech.erp.newDTO.NotificationDTO;
import com.nextech.erp.service.NotificationService;
@Service
public class NotificationServiceImpl extends CRUDServiceImpl<Notification> implements NotificationService{


	@Autowired
	NotificationDao notificationDao;

	@Override
	public NotificationDTO getNotifiactionByStatus(long statusId) throws Exception {
		// TODO Auto-generated method stub
		Notification notification = notificationDao.getNotifiactionByStatus(statusId);
		NotificationDTO notificationDTO = NotificationRequestResponseFactory.setNotificationDTO(notification);
		return notificationDTO;
	}

	@Override
	public List<NotificationDTO> getNofificationList(
			List<NotificationDTO> notificationDTOs) throws Exception {
		// TODO Auto-generated method stub
		notificationDTOs =  new ArrayList<NotificationDTO>();
		List<Notification> notifications = notificationDao.getList(Notification.class);
		for (Notification notification : notifications) {
			NotificationDTO notificationDTO = NotificationRequestResponseFactory.setNotificationDTO(notification);
			notificationDTOs.add(notificationDTO);
		}
		return notificationDTOs;
	}

	@Override
	public NotificationDTO getNotificationDTOById(long id) throws Exception {
		// TODO Auto-generated method stub
		Notification notification = notificationDao.getById(Notification.class, id);
		NotificationDTO notificationDTO = NotificationRequestResponseFactory.setNotificationDTO(notification);
		return notificationDTO;
	}

	@Override
	public void deleteNofificationById(long id) throws Exception {
		// TODO Auto-generated method stub
		Notification notification = notificationDao.getById(Notification.class, id);
		notification.setIsactive(false);
		notificationDao.update(notification);
	}

}
