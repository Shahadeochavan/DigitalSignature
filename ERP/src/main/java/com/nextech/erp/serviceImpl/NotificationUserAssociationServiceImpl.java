package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.NotificationUserassociationDao;
import com.nextech.erp.factory.NotificationUserAssRequestResponseFactory;
import com.nextech.erp.model.Notificationuserassociation;
import com.nextech.erp.newDTO.NotificationUserAssociatinsDTO;
import com.nextech.erp.service.NotificationUserAssociationService;
@Service
public class NotificationUserAssociationServiceImpl extends CRUDServiceImpl<Notificationuserassociation> implements NotificationUserAssociationService {

	@Autowired
	NotificationUserassociationDao notificationUserassociationDao;


	@Override
	public List<Notificationuserassociation> getNotificationUserAssociationByUserId(
			long userId) throws Exception {
		
		return notificationUserassociationDao.getNotificationuserassociationByUserId(userId);
	}

	@Override
	public List<Notificationuserassociation> getNotificationuserassociationBynotificationId(
			long notificationId) throws Exception {
		
		return notificationUserassociationDao.getNotificationuserassociationBynotificationId(notificationId);
	}

	@Override
	public List<NotificationUserAssociatinsDTO> getNotificationUserAssociatinsDTOs(
			long notificationId) throws Exception {
		
	List<NotificationUserAssociatinsDTO>	notificationUserAssociatinsDTOs = new ArrayList<NotificationUserAssociatinsDTO>();
		List<Notificationuserassociation> notificationuserassociations = notificationUserassociationDao.getNotificationuserassociationBynotificationId(notificationId);
		for (Notificationuserassociation notificationuserassociation : notificationuserassociations) {
			NotificationUserAssociatinsDTO notificationUserAssociatinsDTO = NotificationUserAssRequestResponseFactory.setNotifiactionDTO(notificationuserassociation);
			notificationUserAssociatinsDTOs.add(notificationUserAssociatinsDTO);
		}
		return notificationUserAssociatinsDTOs;
	}

	@Override
	public List<NotificationUserAssociatinsDTO> getNotificationUserAssoList()
			throws Exception {
		
		List<NotificationUserAssociatinsDTO> notificationUserAssociatinsDTOs = new ArrayList<NotificationUserAssociatinsDTO>();
		List<Notificationuserassociation> notificationuserassociations = notificationUserassociationDao.getList(Notificationuserassociation.class);
		if (notificationuserassociations.isEmpty()) {
			return null;
		}
		for (Notificationuserassociation notificationuserassociation : notificationuserassociations) {
			NotificationUserAssociatinsDTO notificationUserAssociatinsDTO = NotificationUserAssRequestResponseFactory.setNotifiactionDTO(notificationuserassociation);
			notificationUserAssociatinsDTOs.add(notificationUserAssociatinsDTO);
		}
		return notificationUserAssociatinsDTOs;
	}

	@Override
	public NotificationUserAssociatinsDTO getNotificationUserById(long id)
			throws Exception {
		
		Notificationuserassociation notificationuserassociation = notificationUserassociationDao.getById(Notificationuserassociation.class, id);
		if (notificationuserassociation==null) {
			return null;
		}
		NotificationUserAssociatinsDTO notificationUserAssociatinsDTO = NotificationUserAssRequestResponseFactory.setNotifiactionDTO(notificationuserassociation);
		return notificationUserAssociatinsDTO;
	}

	@Override
	public NotificationUserAssociatinsDTO deleteNotificationUserAsso(long id) throws Exception {
		
		Notificationuserassociation notificationuserassociation = notificationUserassociationDao.getById(Notificationuserassociation.class, id);
		if (notificationuserassociation==null) {
			return null;
		}
		notificationuserassociation.setIsactive(false);
		notificationUserassociationDao.update(notificationuserassociation);
		NotificationUserAssociatinsDTO notificationUserAssociatinsDTO = NotificationUserAssRequestResponseFactory.setNotifiactionDTO(notificationuserassociation);
		return notificationUserAssociatinsDTO;
		
	}

}
