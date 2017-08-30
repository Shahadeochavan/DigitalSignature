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
	public @ResponseBody Response getRawMaterialInventory(@PathVariable("id") long id) {
		RMInventoryDTO rawmaterialinventory = null;
		try {
			rawmaterialinventory = rawmaterialinventoryService.getRMInventoryById(id);
			if(rawmaterialinventory==null){
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
			if(rawmaterialinventoryList.isEmpty()){
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
	public Response executeSchedular() throws Exception{
		List<RMInventoryDTO> rawmaterialinventoryList = null;
		System.out.println("RM Inventory Check");
		List<RMInventoryDTO> rmInventoryDTOs = new ArrayList<RMInventoryDTO>();
		RMInventoryDTO  rmInventoryDTO = new RMInventoryDTO();
		try { 
			rawmaterialinventoryList = rawmaterialinventoryService.getRMInventoryList();
			if(rawmaterialinventoryList !=null){
			for (RMInventoryDTO rawmaterialinventory : rawmaterialinventoryList) {
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
				return new Response(1,"There is no any rm inventory list");
			}
			if(rmInventoryDTOs != null&& ! rmInventoryDTOs.isEmpty()){
				mailSendingRMInventroy(rmInventoryDTOs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void mailSendingRMInventroy(List<RMInventoryDTO> rmInventoryDTOs) throws Exception {
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