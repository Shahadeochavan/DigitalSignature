package com.nextech.erp.service;

import com.nextech.erp.dto.Mail;
import com.nextech.erp.model.Notification;

public interface MailService extends CRUDService<Notification>{
	 public void sendEmail( Mail mail,Notification notification);

	 public void sendEmailWithoutPdF( Mail mail,Notification notification);

	 public void sendEmailVendor(Mail mail);
}
