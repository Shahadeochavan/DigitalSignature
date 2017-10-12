package com.nextech.erp.service;

import com.nextech.erp.dto.Mail;
import com.nextech.erp.newDTO.NotificationDTO;

public interface MailService{
	
     public void sendEmail( Mail mail,NotificationDTO notification);

	  public void sendEmailWithoutPdF( Mail mail,NotificationDTO notification);
	
	  public Mail setMailDetails(String firstName,String lastName,String email,NotificationDTO  notificationDTO) throws Exception;
	
}
