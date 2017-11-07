package com.nextech.erp.controller;

import java.util.List;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.log4j.Logger;
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
import com.nextech.erp.factory.MailResponseRequestFactory;
import com.nextech.erp.newDTO.ClientDTO;
import com.nextech.erp.newDTO.NotificationDTO;
import com.nextech.erp.service.ClientService;
import com.nextech.erp.service.MailService;
import com.nextech.erp.service.NotificationService;
import com.nextech.erp.service.NotificationUserAssociationService;
import com.nextech.erp.service.UserService;
import com.nextech.erp.status.Response;
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

	static Logger logger = Logger.getLogger(ClientController.class);

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addClient(
			@Valid @RequestBody ClientDTO clientDTO, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			if (clientService.getClientByCompanyName(clientDTO.getCompanyName()) != null) {
				return new UserStatus(2, messageSource.getMessage(ERPConstants.COMPANY_NAME_SHOULD_BE_UNIQUE, null, null));
			} 
			if (clientService.getClientByEmail(clientDTO.getEmailId()) != null) {
				return new UserStatus(2, messageSource.getMessage(ERPConstants.EMAIL_SHOULD_BE_UNIQUE, null, null));
			}
		     clientService.addEntity(ClientFactory.setClient(clientDTO, request));
	           NotificationDTO  notificationDTO = notificationService.getNotificationByCode((messageSource.getMessage(ERPConstants.CLIENT_ADDED_SUCCESSFULLY, null, null)));
		       mailSending(clientDTO,notificationDTO);
			     return new UserStatus(1, messageSource.getMessage(ERPConstants.CLIENT_ADDED, null, null));
		} catch (ConstraintViolationException cve) {
			cve.printStackTrace();
			return new UserStatus(0, cve.getCause().getMessage());
		} catch (PersistenceException pe) {
			logger.error("Inside PersistenceException");
			pe.printStackTrace();
			return new UserStatus(0, pe.getCause().getMessage());
		} catch (Exception e) {
			logger.error("Inside Exception");
			e.printStackTrace();
			return new UserStatus(0, e.getCause().getMessage());
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getClient(@PathVariable("id") long id) {
		ClientDTO clientDTO = null;
		try {
			clientDTO = clientService.getClientDTOById(id);
			if(clientDTO==null){
				logger.error("There is no client");
				return new Response(1,"Thare is no client");
			}
		} catch (Exception e) {
			logger.error("Inside exception");
			e.printStackTrace();
		}
		return new Response(1,clientDTO);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateClient(@RequestBody ClientDTO clientDTO,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			ClientDTO oldClientInfo = clientService.getClientDTOById(clientDTO.getId());
			if(!clientDTO.getCompanyName().equals(oldClientInfo.getCompanyName())){  	
				if (clientService.getClientByCompanyName(clientDTO.getCompanyName()) != null) {
				return new UserStatus(2, messageSource.getMessage(ERPConstants.COMPANY_NAME_SHOULD_BE_UNIQUE, null, null));
				}
			 }
            if(!clientDTO.getEmailId().equals(oldClientInfo.getEmailId())){  			
				if (clientService.getClientByEmail(clientDTO.getEmailId()) != null) {
				return new UserStatus(2, messageSource.getMessage(ERPConstants.EMAIL_SHOULD_BE_UNIQUE, null, null));
				}
			 }
	    	clientService.updateEntity(ClientFactory.setClientUpdate(clientDTO, request));
            NotificationDTO  notificationDTO = notificationService.getNotificationByCode((messageSource.getMessage(ERPConstants.CLIENT_UPDATE_SUCCESSFULLY, null, null)));
	        mailSending(clientDTO,notificationDTO);
			return new UserStatus(1, messageSource.getMessage(ERPConstants.CLIENT_UPDATE, null, null));
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getClientList() {
		List<ClientDTO> clientList = null;
		try {
			clientList = clientService.getClientList(clientList);
			if(clientList==null){
				logger.error("There is no client list");
				return new Response(1,"There is no client list");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,clientList);
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteClient(@PathVariable("id") long id) {

		try {
			ClientDTO clientDTO =clientService.deleteClient(id);
			if(clientDTO==null){
				logger.error("There is no client list");
				return new Response(1,"There is no client for delete");
			}
			return new Response(1, messageSource.getMessage(ERPConstants.CLIENT_DELETE, null, null));
		} catch (Exception e) {
			return new Response(0, e.toString());
		}
	}
	
	private void mailSending(ClientDTO client,NotificationDTO  notificationDTO) throws Exception{
		Mail mail = mailService.setMailCCBCCAndTO(notificationDTO);
	    String clientTO = mail.getMailTo()+","+client.getEmailId();
	    mail.setMailTo(clientTO);
	    mail.setModel(MailResponseRequestFactory.setMailDetailsClient(client));
		mailService.sendEmailWithoutPdF(mail, notificationDTO);
	}
}
