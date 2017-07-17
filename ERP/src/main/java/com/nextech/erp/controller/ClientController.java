package com.nextech.erp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dto.Mail;
import com.nextech.erp.factory.ClientFactory;
import com.nextech.erp.newDTO.ClientDTO;
import com.nextech.erp.newDTO.NotificationDTO;
import com.nextech.erp.newDTO.NotificationUserAssociatinsDTO;
import com.nextech.erp.newDTO.UserDTO;
import com.nextech.erp.service.ClientService;
import com.nextech.erp.service.MailService;
import com.nextech.erp.service.NotificationService;
import com.nextech.erp.service.NotificationUserAssociationService;
import com.nextech.erp.service.UserService;
import com.nextech.erp.status.UserStatus;

@Controller
@RequestMapping("/client")
public class ClientController {

	@Autowired
	ClientService clientService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	NotificationService notificationService;


	@Autowired
	NotificationUserAssociationService notificationUserAssService;
	
	@Autowired
	UserService userService;

	@Autowired
	MailService mailService;

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addClient(
			@Valid @RequestBody ClientDTO clientDTO, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			if (clientService.getClientByCompanyName(clientDTO.getCompanyName()) == null) {

			} else {
				return new UserStatus(2, messageSource.getMessage(
						ERPConstants.COMPANY_NAME_EXIT, null, null));

			}
			if (clientService.getClientByEmail(clientDTO.getEmailId()) == null) {
			} else {
				return new UserStatus(2, messageSource.getMessage(
						ERPConstants.EMAIL_ALREADY_EXIT, null, null));
			}
       
		    	clientService.addEntity(ClientFactory.setClient(clientDTO, request));
		        mailSending(clientDTO, request, response);
			return new UserStatus(1, messageSource.getMessage(
					ERPConstants.CLIENT_ADDED, null, null));
		} catch (ConstraintViolationException cve) {
			cve.printStackTrace();
			return new UserStatus(0, cve.getCause().getMessage());
		} catch (PersistenceException pe) {
			System.out.println("Inside PersistenceException");
			pe.printStackTrace();
			return new UserStatus(0, pe.getCause().getMessage());
		} catch (Exception e) {
			System.out.println("Inside Exception");
			e.printStackTrace();
			return new UserStatus(0, e.getCause().getMessage());
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody ClientDTO getClient(@PathVariable("id") long id) {
		ClientDTO clientDTO = null;
		try {
			clientDTO = clientService.getClientDTOById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clientDTO;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateClient(@RequestBody ClientDTO clientDTO,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			
			ClientDTO oldClientInfo = clientService.getClientDTOById(clientDTO.getId());
			System.out.println(oldClientInfo);
			if(clientDTO.getCompanyName().equals(oldClientInfo.getCompanyName())){  	
			} else { 
				if (clientService.getClientByCompanyName(clientDTO.getCompanyName()) == null) {
			    }else{  
				return new UserStatus(2, messageSource.getMessage(ERPConstants.COMPANY_NAME_EXIT, null, null));
				}
			 }
            if(clientDTO.getEmailId().equals(oldClientInfo.getEmailId())){  			
			} else { 
				if (clientService.getClientByEmail(clientDTO.getEmailId()) == null) {
			    }else{  
				return new UserStatus(2, messageSource.getMessage(ERPConstants.EMAIL_ALREADY_EXIT, null, null));
				}
			 }
	    	clientService.updateEntity(ClientFactory.setClientUpdate(clientDTO, request));
	        mailSendingUpdate(clientDTO, request, response);
			return new UserStatus(1, messageSource.getMessage(
					ERPConstants.CLIENT_UPDATE, null, null));
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<ClientDTO> getClient() {

		List<ClientDTO> clientList = null;
		try {
			clientList = clientService.getClientList(clientList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return clientList;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteClient(@PathVariable("id") long id) {

		try {
		    clientService.deleteClient(id);
			return new UserStatus(1, messageSource.getMessage(
					ERPConstants.CLIENT_DELETE, null, null));
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}

	}
	private void mailSending(ClientDTO client,HttpServletRequest request, HttpServletResponse response) throws Exception{
		  Mail mail = new Mail();

		  NotificationDTO  notificationDTO = notificationService.getNotificationDTOById(Long.parseLong(messageSource.getMessage(ERPConstants.CLIENT_ADDED_SUCCESSFULLY, null, null)));
		  List<NotificationUserAssociatinsDTO> notificationUserAssociatinsDTOs = notificationUserAssService.getNotificationUserAssociatinsDTOs(notificationDTO.getId());
		  for (NotificationUserAssociatinsDTO notificationuserassociation : notificationUserAssociatinsDTOs) {
			  UserDTO userDTO = userService.getUserDTO(notificationuserassociation.getId());
			  if(notificationuserassociation.getTo()==true){
				   mail.setMailTo(client.getEmailId()); 
			  }else if(notificationuserassociation.getBcc()==true){
				  mail.setMailBcc(userDTO.getEmailId());
			  }else if(notificationuserassociation.getCc()==true){
				  mail.setMailCc(userDTO.getEmailId());
			  }
			
		}
	     
	        mail.setMailSubject(notificationDTO.getSubject());

	        Map < String, Object > model = new HashMap < String, Object > ();
	        model.put("firstName", client.getCompanyName());
	        model.put("email", client.getEmailId());
	        model.put("contactNumber", client.getContactNumber());
	        model.put("location", "Pune");
	        model.put("signature", "www.NextechServices.in");
	        mail.setModel(model);

		mailService.sendEmailWithoutPdF(mail, notificationDTO);
	}
	
	private void mailSendingUpdate(ClientDTO client,HttpServletRequest request, HttpServletResponse response) throws Exception{
		  Mail mail = new Mail();

		  NotificationDTO  notificationDTO = notificationService.getNotificationDTOById(Long.parseLong(messageSource.getMessage(ERPConstants.CLIENT_ADDED_SUCCESSFULLY, null, null)));
		  List<NotificationUserAssociatinsDTO> notificationUserAssociatinsDTOs = notificationUserAssService.getNotificationUserAssociatinsDTOs(notificationDTO.getId());
		  for (NotificationUserAssociatinsDTO notificationuserassociation : notificationUserAssociatinsDTOs) {
			  UserDTO userDTO = userService.getUserDTO(notificationuserassociation.getId());
			  if(notificationuserassociation.getTo()==true){
				  mail.setMailTo(client.getEmailId());
			  }else if(notificationuserassociation.getBcc()==true){
				  mail.setMailBcc(userDTO.getEmailId());
			  }else if(notificationuserassociation.getCc()==true){
				  mail.setMailCc(userDTO.getEmailId());
			  }
			
		}
	     
	        mail.setMailSubject(notificationDTO.getSubject());

	        Map < String, Object > model = new HashMap < String, Object > ();
	        model.put("firstName", client.getCompanyName());
	        model.put("email", client.getEmailId());
	        model.put("contactNumber", client.getContactNumber());
	        model.put("location", "Pune");
	        model.put("signature", "www.NextechServices.in");
	        mail.setModel(model);

		mailService.sendEmailWithoutPdF(mail, notificationDTO);
	}
}
