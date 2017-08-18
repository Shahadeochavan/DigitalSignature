package com.nextech.erp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nextech.erp.dto.Mail;
import com.nextech.erp.newDTO.NotificationUserAssociatinsDTO;
import com.nextech.erp.newDTO.UserDTO;
import com.nextech.erp.serviceImpl.UserServiceImpl;



public class EmailNotification {
	
	static StringBuilder stringBuilderCC = new StringBuilder();
	static StringBuilder stringBuilderTO = new StringBuilder();
	static StringBuilder stringBuilderBCC = new StringBuilder();
	
	static String prefixCC="";
	static String prefixTO="";
	static String prefixBCC="";
	
	static String multipleCC="";
	static String multipleBCC="";
	static String multipleTO="";
	public static Mail emailNotification(UserDTO userDTO,HttpServletRequest request,HttpServletResponse response,  List<NotificationUserAssociatinsDTO> notificationUserAssociatinsDTOs) throws Exception{
		Mail mail =  new Mail();
		  for (NotificationUserAssociatinsDTO notificationuserassociation : notificationUserAssociatinsDTOs) {
			  UserServiceImpl userServiceImpl =  new UserServiceImpl();
			  UserDTO user1 = userServiceImpl.getUserDTO(notificationuserassociation.getUserId().getId());
			  if(notificationuserassociation.getTo()){
					stringBuilderTO.append(prefixTO);
					prefixTO=","; 
					stringBuilderTO.append(userDTO.getEmailId());
					multipleTO = stringBuilderTO.toString();
					mail.setMailTo(multipleTO);
			  }else if(notificationuserassociation.getBcc()){
				  stringBuilderBCC.append(prefixBCC);
					prefixBCC=",";
					stringBuilderBCC.append(user1.getEmailId());
					multipleBCC = stringBuilderBCC.toString();
					mail.setMailBcc(multipleBCC);
			  }else if(notificationuserassociation.getCc()){
					stringBuilderCC.append(prefixCC);
					prefixCC=",";
					stringBuilderCC.append(user1.getEmailId());
					multipleCC = stringBuilderCC.toString();
					mail.setMailCc(multipleCC);
			  }
			
		}
		  return mail;
	}
}
