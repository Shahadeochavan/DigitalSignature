package com.nextech.erp.service;

import com.nextech.erp.dto.Mail;
import com.nextech.erp.newDTO.NotificationDTO;
import com.nextech.erp.newDTO.UserDTO;

public interface MailService{
	
     public void sendEmail( Mail mail,NotificationDTO notification);

	  public void sendEmailWithoutPdF( Mail mail,NotificationDTO notification);
	
	  public void emailNotification(UserDTO userDTO,NotificationDTO  notificationDTO)throws Exception;
	
}
