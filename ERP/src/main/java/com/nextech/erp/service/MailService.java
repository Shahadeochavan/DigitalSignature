package com.nextech.erp.service;

import com.nextech.erp.dto.Mail;
import com.nextech.erp.model.Notification;
import com.nextech.erp.newDTO.NotificationDTO;

public interface MailService extends CRUDService<Notification>{
	 public void sendEmail( Mail mail,NotificationDTO notification);

	 public void sendEmailWithoutPdF( Mail mail,NotificationDTO notification);

}
