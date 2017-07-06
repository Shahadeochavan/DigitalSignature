package com.nextech.erp.controller;

import java.util.ArrayList;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dto.Mail;
import com.nextech.erp.dto.RMInventoryDTO;
import com.nextech.erp.model.Notification;
import com.nextech.erp.model.Notificationuserassociation;
import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.model.Rawmaterialinventory;
import com.nextech.erp.model.User;
import com.nextech.erp.service.MailService;
import com.nextech.erp.service.NotificationService;
import com.nextech.erp.service.NotificationUserAssociationService;
import com.nextech.erp.service.RawmaterialService;
import com.nextech.erp.service.RawmaterialinventoryService;
import com.nextech.erp.service.UserService;
import com.nextech.erp.status.UserStatus;

@RestController
@RequestMapping("/rawmaterialinventory")
public class RawmaterialinventoryController {

	@Autowired
	RawmaterialinventoryService rawmaterialinventoryService;

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	NotificationService notificationService;
	
	@Autowired
	NotificationUserAssociationService notificationUserAssociationService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	RawmaterialService rawmaterialService;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addRawmaterialinventory(
			@Valid @RequestBody Rawmaterialinventory rawmaterialinventory, BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			if(rawmaterialinventoryService.getByRMId(rawmaterialinventory.getRawmaterial().getId())==null){
				rawmaterialinventory.setIsactive(true);
				rawmaterialinventory.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
				rawmaterialinventoryService.addEntity(rawmaterialinventory);
			}
			else
				return new UserStatus(0, messageSource.getMessage(
						ERPConstants.RAW_MATERIAL_INVENTORY, null, null));
			return new UserStatus(1, "Rawmaterialinventory added Successfully !");
		} catch (ConstraintViolationException cve) {
			cve.printStackTrace();
			return new UserStatus(0, cve.getCause().getMessage());
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			return new UserStatus(0, pe.getCause().getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.getCause().getMessage());
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Rawmaterialinventory getRawmaterialinventory(@PathVariable("id") long id) {
		Rawmaterialinventory rawmaterialinventory = null;
		try {
			rawmaterialinventory = rawmaterialinventoryService.getEntityById(Rawmaterialinventory.class,id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rawmaterialinventory;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateRawmaterialinventory(@RequestBody Rawmaterialinventory rawmaterialinventory,HttpServletRequest request,HttpServletResponse response) {
		try {
			rawmaterialinventory.setIsactive(true);
			rawmaterialinventory.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			rawmaterialinventoryService.updateEntity(rawmaterialinventory);
			return new UserStatus(1, "Rawmaterialinventory update Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Rawmaterialinventory> getRawmaterialinventory() {

		List<Rawmaterialinventory> rawmaterialinventoryList = null;
		try {
			rawmaterialinventoryList = rawmaterialinventoryService.getEntityList(Rawmaterialinventory.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rawmaterialinventoryList;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteRawmaterialinventory(@PathVariable("id") long id) {

		try {
			Rawmaterialinventory rawmaterialinventory = rawmaterialinventoryService.getEntityById(Rawmaterialinventory.class,id);
			rawmaterialinventory.setIsactive(false);
			rawmaterialinventoryService.updateEntity(rawmaterialinventory);
			return new UserStatus(1, "Rawmaterialinventory deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}

	}
	@Scheduled(initialDelay=600000, fixedRate=600000)
	private void executeSchedular() throws Exception{
		List<Rawmaterialinventory> rawmaterialinventoryList = null;
		System.out.println("RM Inventory Check");
		List<RMInventoryDTO> rmInventoryDTOs = new ArrayList<RMInventoryDTO>();
		try { 
			rawmaterialinventoryList = rawmaterialinventoryService.getEntityList(Rawmaterialinventory.class);
			for (Rawmaterialinventory rawmaterialinventory : rawmaterialinventoryList) {
				Rawmaterial rawmaterial = rawmaterialService.getEntityById(Rawmaterial.class, rawmaterialinventory.getRawmaterial().getId());
				if(rawmaterialinventory.getQuantityAvailable()>=rawmaterialinventory.getMinimum_quantity()){

				}else{
					RMInventoryDTO  rmInventoryDTO = new RMInventoryDTO();
					rmInventoryDTO.setRmPartNumber(rawmaterial.getPartNumber());
					rmInventoryDTO.setQuantityAvailable(rawmaterialinventory.getQuantityAvailable());
					rmInventoryDTO.setMinimumQuantity(rawmaterialinventory.getMinimum_quantity());
					rmInventoryDTOs.add(rmInventoryDTO);
				}
			}
			if(rmInventoryDTOs != null&& ! rmInventoryDTOs.isEmpty()){
				mailSendingRMInventroy(rmInventoryDTOs);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void mailSendingRMInventroy(List<RMInventoryDTO> rmInventoryDTOs) throws Exception{
		  Mail mail = new Mail();
		  Notification notification = notificationService.getEntityById(Notification.class,18);
		  List<Notificationuserassociation> notificationuserassociations = notificationUserAssociationService.getNotificationuserassociationBynotificationId(notification.getId());
		  for (Notificationuserassociation notificationuserassociation : notificationuserassociations) {
			  User   user = userService.getEmailUserById(notificationuserassociation.getUser().getId());
			  if(notificationuserassociation.getTo()==true){
				  mail.setMailTo(user.getEmail()); 
			  }else if(notificationuserassociation.getBcc()==true){
				  mail.setMailBcc(user.getEmail());
			  }else if(notificationuserassociation.getCc()==true){
				  mail.setMailCc(user.getEmail());
			  }
			
		}
	        mail.setMailSubject(notification.getSubject());
	        Map < String, Object > model = new HashMap < String, Object > ();
	        model.put("firstName", "Prashant");
	        model.put("rmInventoryDTOs", rmInventoryDTOs);
	        model.put("location", "Pune");
	        model.put("signature", "www.NextechServices.in");
	        mail.setModel(model);
		mailService.sendEmailWithoutPdF(mail, notification);
	}
}
