package com.nextech.erp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
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
import com.nextech.erp.factory.RMInventoryRequestResponseFactory;
import com.nextech.erp.newDTO.NotificationDTO;
import com.nextech.erp.newDTO.RawMaterialDTO;
import com.nextech.erp.service.MailService;
import com.nextech.erp.service.NotificationService;
import com.nextech.erp.service.NotificationUserAssociationService;
import com.nextech.erp.service.RawmaterialService;
import com.nextech.erp.service.RawmaterialinventoryService;
import com.nextech.erp.service.UserService;
import com.nextech.erp.status.Response;
import com.nextech.erp.status.UserStatus;

@RestController
@Transactional
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
	
	static Logger logger = Logger.getLogger(RawmaterialinventoryController.class);
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addRawMaterialInventory(
			@Valid @RequestBody RMInventoryDTO rmInventoryDTO, BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
			}
			if(rawmaterialinventoryService.getByRMId(rmInventoryDTO.getRawmaterialId().getId())==null){
				rawmaterialinventoryService.addEntity(RMInventoryRequestResponseFactory.setRMInventory(rmInventoryDTO, request));
			}
			else
				return new UserStatus(0, messageSource.getMessage(ERPConstants.RAW_MATERIAL_INVENTORY, null, null));
			return new UserStatus(1, "Rawmaterialinventory added Successfully !");
		} catch (ConstraintViolationException cve) {
			logger.error("Inside ConstraintViolationException");
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
	public @ResponseBody Response getRawMaterialInventory(@PathVariable("id") long id) {
		RMInventoryDTO rawmaterialinventory = null;
		try {
			rawmaterialinventory = rawmaterialinventoryService.getRMInventoryById(id);
			if(rawmaterialinventory==null){
				logger.error("There is no rm inventory");
				return new Response(1,"There is no rm inventory");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,rawmaterialinventory);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateRawMaterialInventory(@RequestBody RMInventoryDTO rmInventoryDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			rawmaterialinventoryService.updateEntity(RMInventoryRequestResponseFactory.setRMInventoryUpdate(rmInventoryDTO, request));
			return new UserStatus(1, "Rawmaterialinventory update Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getRawMaterialInventory() {

		List<RMInventoryDTO> rawmaterialinventoryList = null;
		try {
			rawmaterialinventoryList = rawmaterialinventoryService.getRMInventoryList();
			if(rawmaterialinventoryList==null){
				logger.error("There is no rm inventory list");
				return new Response(1,"There is no rm inventory");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,rawmaterialinventoryList);
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteRawMaterialInventory(@PathVariable("id") long id) {
		try {
			RMInventoryDTO rmInventoryDTO =	rawmaterialinventoryService.deleteRMInventory(id);
			if(rmInventoryDTO==null){
				return new Response(1,"There is no rm inventroy");
			}
			return new Response(1, "Rawmaterialinventory deleted Successfully !");
		} catch (Exception e) {
			return new Response(0, e.toString());
		}
	}

	//@Scheduled(initialDelay=60000, fixedRate=60000)
	public void executeSchedular() throws Exception{
		List<RMInventoryDTO> rawmaterialinventoryList = null;
		logger.info("RM Inventory Check");
		List<RMInventoryDTO> rmInventoryDTOs = new ArrayList<RMInventoryDTO>();
	
		try { 
			rawmaterialinventoryList = rawmaterialinventoryService.getRMInventoryList();
			if(rawmaterialinventoryList !=null){
			for (RMInventoryDTO rawmaterialinventory : rawmaterialinventoryList) {
				RMInventoryDTO  rmInventoryDTO = new RMInventoryDTO();
				RawMaterialDTO rawmaterial = rawmaterialService.getRMDTO(rawmaterialinventory.getRawmaterialId().getId());
				if(rawmaterialinventory.getQuantityAvailable()>=rawmaterialinventory.getMinimumQuantity()){
				}else{
					rmInventoryDTO.setRmPartNumber(rawmaterial.getPartNumber());
					rmInventoryDTO.setQuantityAvailable(rawmaterialinventory.getQuantityAvailable());
					rmInventoryDTO.setMinimumQuantity(rawmaterialinventory.getMinimumQuantity());
					rmInventoryDTOs.add(rmInventoryDTO);
				}
			}
			}else{
				logger.info("There is no any rm inventory list");
				//return new Response(1,"There is no any rm inventory list");
			}
			if(rmInventoryDTOs != null&& ! rmInventoryDTOs.isEmpty()){
				emailNotificationRMInventory(rmInventoryDTOs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return null;
	}
	
	private void emailNotificationRMInventory(List<RMInventoryDTO> rmInventoryDTOs) throws Exception {
		   NotificationDTO  notificationDTO = notificationService.getNotificationByCode((messageSource.getMessage(ERPConstants.RM_INVENTORY_NOTIFICATION, null, null)));
		Mail mail = userService.emailNotification(notificationDTO);
		mail.setMailSubject(notificationDTO.getSubject());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("rmInventoryDTOs", rmInventoryDTOs);
		model.put("location", "Pune");
		model.put("signature", "www.NextechServices.in");
		mail.setModel(model);
		mailService.sendEmailWithoutPdF(mail, notificationDTO);
	}
}