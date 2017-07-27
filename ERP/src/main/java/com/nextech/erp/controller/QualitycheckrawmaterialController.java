package com.nextech.erp.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dto.Mail;
import com.nextech.erp.dto.QualityCheckRMDTO;
import com.nextech.erp.dto.RawMaterialInvoiceDTO;
import com.nextech.erp.dto.StoreInDTO;
import com.nextech.erp.exceptions.InvalidRMQuantityInQC;
import com.nextech.erp.factory.QualityRequestResponseFactory;
import com.nextech.erp.factory.StoreRequestResponseFactory;
import com.nextech.erp.model.Notification;
import com.nextech.erp.model.Notificationuserassociation;
import com.nextech.erp.model.Qualitycheckrawmaterial;
import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.model.Rawmaterialinventory;
import com.nextech.erp.model.Rawmaterialinventoryhistory;
import com.nextech.erp.model.Rawmaterialorder;
import com.nextech.erp.model.Rawmaterialorderassociation;
import com.nextech.erp.model.Rawmaterialorderhistory;
import com.nextech.erp.model.Rawmaterialorderinvoice;
import com.nextech.erp.model.Rmorderinvoiceintakquantity;
import com.nextech.erp.model.Status;
import com.nextech.erp.model.User;
import com.nextech.erp.model.Vendor;
import com.nextech.erp.newDTO.NotificationDTO;
import com.nextech.erp.newDTO.NotificationUserAssociatinsDTO;
import com.nextech.erp.newDTO.RMOrderAssociationDTO;
import com.nextech.erp.newDTO.UserDTO;
import com.nextech.erp.service.MailService;
import com.nextech.erp.service.NotificationService;
import com.nextech.erp.service.NotificationUserAssociationService;
import com.nextech.erp.service.QualitycheckrawmaterialService;
import com.nextech.erp.service.RawmaterialService;
import com.nextech.erp.service.RawmaterialinventoryService;
import com.nextech.erp.service.RawmaterialinventoryhistoryService;
import com.nextech.erp.service.RawmaterialorderService;
import com.nextech.erp.service.RawmaterialorderassociationService;
import com.nextech.erp.service.RawmaterialorderhistoryService;
import com.nextech.erp.service.RawmaterialorderinvoiceService;
import com.nextech.erp.service.RawmaterialorderinvoiceassociationService;
import com.nextech.erp.service.RmorderinvoiceintakquantityService;
import com.nextech.erp.service.StatusService;
import com.nextech.erp.service.UserService;
import com.nextech.erp.service.VendorService;
import com.nextech.erp.status.UserStatus;

@Controller
@Transactional
@RequestMapping("/qualitycheckrawmaterial")
public class QualitycheckrawmaterialController {
	@Autowired
	QualitycheckrawmaterialService qualitycheckrawmaterialService;

	@Autowired
	RmorderinvoiceintakquantityService rmorderinvoiceintakquantityService;

	@Autowired
	RawmaterialService rawmaterialService;

	@Autowired
	RawmaterialorderinvoiceService rawmaterialorderinvoiceService;

	@Autowired
	RawmaterialorderhistoryService rawmaterialorderhistoryService;

	@Autowired
	RawmaterialinventoryhistoryService rawmaterialinventoryhistoryService;

	@Autowired
	RawmaterialorderService rawmaterialorderService;

	@Autowired
	RawmaterialinventoryService rawmaterialinventoryService;

	@Autowired
	StatusService statusService;

	@Autowired
	NotificationService notificationService;

	@Autowired
	VendorService vendorService;

	@Autowired
	MailService mailService;
	
	@Autowired
	UserService userService;
	
	@Autowired 
	NotificationUserAssociationService notificationUserAssociationService;

	@Autowired
	RawmaterialorderinvoiceassociationService rawmaterialorderinvoiceassociationService;

	@Autowired
	RawmaterialorderassociationService rawmaterialorderassociationService;

	@Autowired
	private MessageSource messageSource;

	private HashMap<Long,Long> rmIdQuantityMap;

	private static final int STATUS_RAW_MATERIAL_ORDER_INCOMPLETE=2;
	private static final int STATUS_RAW_MATERIAL_ORDER_COMPLETE=3;

	@RequestMapping(value = "/qualitycheck", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	@Transactional
	public @ResponseBody UserStatus addRawmaterialorderinvoice(
			@Valid @RequestBody RawMaterialInvoiceDTO rawMaterialInvoiceDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
			}
			String message = "";
			Rawmaterialorderinvoice rawmaterialorderinvoiceNew = rawmaterialorderinvoiceService.getEntityById(Rawmaterialorderinvoice.class,rawMaterialInvoiceDTO.getId());
			Rawmaterialorder rawmaterialorder = rawmaterialorderService.getEntityById(Rawmaterialorder.class, rawmaterialorderinvoiceNew.getPo_No());
			List<QualityCheckRMDTO> qualityCheckRMDTOs = rawMaterialInvoiceDTO.getQualityCheckRMDTOs();
			if (qualityCheckRMDTOs != null && !qualityCheckRMDTOs.isEmpty()) {
				for (QualityCheckRMDTO qualityCheckRMDTO : qualityCheckRMDTOs) {
					Rawmaterial rawmaterial = rawmaterialService.getEntityById(Rawmaterial.class, qualityCheckRMDTO.getId());
					saveQualityCheckRawMaterial(new Qualitycheckrawmaterial(), qualityCheckRMDTO,rawmaterialorderinvoiceNew, rawmaterial, request, response);
					updateRawMaterialInvoice(ERPConstants.STATUS_READY_STORE_IN,rawmaterialorderinvoiceNew, request, response);
					updateRMOrderRemainingQuantity(qualityCheckRMDTO, rawmaterialorder, request, response);
					updateRMIdQuantityMap(qualityCheckRMDTO.getId(), qualityCheckRMDTO.getIntakeQuantity() - qualityCheckRMDTO.getGoodQuantity());
					message = messageSource.getMessage(ERPConstants.RM_QUALITY_CHECK, null, null);
				}
			}
			addOrderHistory(rawmaterialorderinvoiceNew, rawmaterialorder, request, response);
			updateRawMaterialOrderStatus(rawmaterialorder);
			Status status = statusService.getEntityById(Status.class, rawmaterialorderinvoiceNew.getStatus().getId());
			Vendor vendor = vendorService.getEntityById(Vendor.class, Long.parseLong(rawmaterialorderinvoiceNew.getVendorname()));
			NotificationDTO notificationDTO = notificationService.getNotificationDTOById(status.getId());
			mailSending(notificationDTO, vendor,qualityCheckRMDTOs,rawmaterialorder);
			return new UserStatus(1,message);
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.getCause().getMessage());
		}
	}

	@RequestMapping(value = "/qualityCheckInReadyStore", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	@Transactional
	public @ResponseBody UserStatus addRawmaterialorderinvoiceReadyStore(
			@Valid @RequestBody RawMaterialInvoiceDTO materialInvoiceDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
			}
			Rawmaterialorderinvoice rawmaterialorderinvoiceNew = rawmaterialorderinvoiceService.getEntityById(Rawmaterialorderinvoice.class,materialInvoiceDTO.getId());
			List<Qualitycheckrawmaterial> qualitycheckrawmaterials = qualitycheckrawmaterialService.getQualitycheckrawmaterialByInvoiceId(rawmaterialorderinvoiceNew.getId());
			if (qualitycheckrawmaterials != null&& !qualitycheckrawmaterials.isEmpty()) {
				for (Qualitycheckrawmaterial qualitycheckrawmaterial : qualitycheckrawmaterials) {
					Rawmaterial rawmaterial = rawmaterialService.getRMByRMId(qualitycheckrawmaterial.getRawmaterial().getId());
					Rawmaterialinventory rawmaterialinventory = updateInventory(qualitycheckrawmaterial, rawmaterial, request, response);
					addRMInventoryHistory(qualitycheckrawmaterial, rawmaterialinventory, request, response);
					updateRawMaterialInvoice(ERPConstants.STATUS_RAW_MATERIAL_INVENTORY_ADD,rawmaterialorderinvoiceNew, request, response);
				}
			}
			return new UserStatus(1,"Store Quality Check information save succesfully");
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


	private void updateRawMaterialOrderStatus(Rawmaterialorder rawmaterialorder) throws Exception{

		rawmaterialorder.setStatus(statusService.getEntityById(Status.class, getOrderStatus(rawmaterialorder)));
		rawmaterialorderService.updateEntity(rawmaterialorder);
	}

	private int getOrderStatus(Rawmaterialorder rawmaterialorder) throws Exception{
		boolean isOrderComplete = false;
		List<RMOrderAssociationDTO> rawmaterialorderassociationList  =rawmaterialorderassociationService.getRMOrderRMAssociationByRMOrderId(rawmaterialorder.getId());
		for (Iterator<RMOrderAssociationDTO> iterator = rawmaterialorderassociationList.iterator(); iterator
				.hasNext();) {
			RMOrderAssociationDTO rawmaterialorderassociation = (RMOrderAssociationDTO) iterator
					.next();
			if(rawmaterialorderassociation.getRemainingQuantity() == 0 ){
				isOrderComplete = true;
			}else{
				isOrderComplete = false;
				break;
			}

		}
		return isOrderComplete ? STATUS_RAW_MATERIAL_ORDER_COMPLETE : STATUS_RAW_MATERIAL_ORDER_INCOMPLETE;
	}

	private void updateRawMaterialInvoice(String invoiceStatus,Rawmaterialorderinvoice rawmaterialorderinvoice,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Status statusInventoryAdd = statusService.getEntityById(Status.class,Long.parseLong(messageSource.getMessage(invoiceStatus, null, null)));
		rawmaterialorderinvoice.setStatus(statusInventoryAdd);
		rawmaterialorderinvoice.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		rawmaterialorderinvoiceService.updateEntity(rawmaterialorderinvoice);
	}

	private long saveQualityCheckRawMaterial(Qualitycheckrawmaterial qualitycheckrawmaterial,QualityCheckRMDTO qualityCheckRMDTO,Rawmaterialorderinvoice rawmaterialorderinvoiceNew,Rawmaterial rawmaterial,HttpServletRequest request,HttpServletResponse response) throws Exception{
		qualitycheckrawmaterial.setRawmaterialorderinvoice(rawmaterialorderinvoiceNew);
		qualitycheckrawmaterial.setRawmaterial(rawmaterial);
		qualitycheckrawmaterial.setGoodQuantity(qualityCheckRMDTO.getGoodQuantity());
		qualitycheckrawmaterial.setIntakeQuantity(qualityCheckRMDTO.getIntakeQuantity());
		qualitycheckrawmaterial.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		qualitycheckrawmaterial.setIsactive(true);
		qualitycheckrawmaterialService.addEntity(qualitycheckrawmaterial);
		return qualitycheckrawmaterial.getId();
	}

	private Rawmaterialinventory updateInventory(Qualitycheckrawmaterial qualitycheckrawmaterial,Rawmaterial rawmaterial,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Rawmaterialinventory rawmaterialinventory =  rawmaterialinventoryService.getByRMId(qualitycheckrawmaterial.getRawmaterial().getId());
		if(rawmaterialinventory == null){
			rawmaterialinventory = new Rawmaterialinventory();
			rawmaterialinventory.setRawmaterial(rawmaterial);
			rawmaterialinventory.setIsactive(true);
			rawmaterialinventory.setQuantityAvailable(qualitycheckrawmaterial.getGoodQuantity());
			rawmaterialinventory.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			rawmaterialinventoryService.addEntity(rawmaterialinventory);
		}else{
			rawmaterialinventory.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			rawmaterialinventory.setIsactive(true);
			rawmaterialinventory.setQuantityAvailable(rawmaterialinventory.getQuantityAvailable()+qualitycheckrawmaterial.getGoodQuantity());
			rawmaterialinventory.setUpdatedDate(new Timestamp(new Date().getTime()));
			 rawmaterialinventoryService.updateEntity(rawmaterialinventory);
		}
		return rawmaterialinventory;
	}
	private void  updateRMOrderRemainingQuantity(QualityCheckRMDTO qualityCheckRMDTO ,Rawmaterialorder rawmaterialorder,HttpServletRequest request,HttpServletResponse response) throws InvalidRMQuantityInQC,Exception{
		Rawmaterialorderassociation rawmaterialorderassociation = rawmaterialorderassociationService.getRMOrderRMAssociationByRMOrderIdandRMId(rawmaterialorder.getId(),qualityCheckRMDTO.getId());
		if(rawmaterialorderassociation.getRemainingQuantity()-qualityCheckRMDTO.getGoodQuantity() >= 0){
			rawmaterialorderassociation.setRemainingQuantity(rawmaterialorderassociation.getRemainingQuantity()-qualityCheckRMDTO.getGoodQuantity());
		}else{
			throw new InvalidRMQuantityInQC("Good quantity exceeded than Remaining Quantity");
		}
		rawmaterialorderassociation.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		rawmaterialorderassociationService.updateEntity(rawmaterialorderassociation);
	}

	private void addRMInventoryHistory(Qualitycheckrawmaterial qualitycheckrawmaterial,Rawmaterialinventory rawmaterialinventory,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Rawmaterialinventoryhistory rawmaterialinventoryhistory = new Rawmaterialinventoryhistory();
		rawmaterialinventoryhistory.setQualitycheckrawmaterial(qualitycheckrawmaterial);
		rawmaterialinventoryhistory.setRawmaterialinventory(rawmaterialinventory);
		rawmaterialinventoryhistory.setIsactive(true);
		rawmaterialinventoryhistory.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		rawmaterialinventoryhistory.setCreatedDate(new Timestamp(new Date().getTime()));
		rawmaterialinventoryhistory.setStatus(statusService.getEntityById(Status.class,Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_RAW_MATERIAL_INVENTORY_ADD, null, null))));
		rawmaterialinventoryhistoryService.addEntity(rawmaterialinventoryhistory);
	}

	private void addOrderHistory(Rawmaterialorderinvoice rawmaterialorderinvoice,Rawmaterialorder rawmaterialorder,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Rawmaterialorderhistory rawmaterialorderhistory = new Rawmaterialorderhistory();
		rawmaterialorderhistory.setComment(rawmaterialorderinvoice.getDescription());
		rawmaterialorderhistory.setRawmaterialorder(rawmaterialorder);
		rawmaterialorderhistory.setRawmaterialorderinvoice(rawmaterialorderinvoice);
		rawmaterialorderhistory.setCreatedDate(new Timestamp(new Date().getTime()));
		rawmaterialorderhistory.setIsactive(true);
		rawmaterialorderhistory.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		rawmaterialorderhistory.setQualitycheckrawmaterial(null);
		rawmaterialorderhistory.setStatus1(statusService.getEntityById(Status.class,rawmaterialorder.getStatus().getId()));
		rawmaterialorderhistory.setStatus2(statusService.getEntityById(Status.class, getOrderStatus(rawmaterialorder)));
		rawmaterialorderhistory.setCreatedBy(3);
		rawmaterialorderhistoryService.addEntity(rawmaterialorderhistory);
	}
	private void updateRMIdQuantityMap(long rmId,long quantity){
		if(rmIdQuantityMap == null){
			rmIdQuantityMap = new HashMap<Long, Long>();
		}
		rmIdQuantityMap.put(rmId, quantity);

	}
	@RequestMapping(value = "listrm/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<QualityCheckRMDTO> getRmorderinvoiceintakquantityByRMOInvoiceId(
			@PathVariable("id") long id) {
		List<QualityCheckRMDTO> qualityCheckRMDTOs = new ArrayList<QualityCheckRMDTO>();
		try {
			List<Rmorderinvoiceintakquantity> rmorderinvoiceintakquantities = rmorderinvoiceintakquantityService.getRmorderinvoiceintakquantityByRMOInvoiceId(id);
			for (Rmorderinvoiceintakquantity rmorderinvoiceintakquantity : rmorderinvoiceintakquantities) {
				QualityCheckRMDTO qualityCheckRMDTO = QualityRequestResponseFactory.createQualityResonse(rmorderinvoiceintakquantity);
				qualityCheckRMDTO.setId(rmorderinvoiceintakquantity.getRawmaterial().getId());
				qualityCheckRMDTOs.add(qualityCheckRMDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qualityCheckRMDTOs;
	}
	
	@RequestMapping(value = "listrmGoodQuantity/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<StoreInDTO> getRmorderinvoiceintakquantity(
			@PathVariable("id") long id) {
		List<StoreInDTO> storeInDTOs = new ArrayList<StoreInDTO>();
		try {
			List<Qualitycheckrawmaterial> qualitycheckrawmaterials = qualitycheckrawmaterialService.getQualitycheckrawmaterialByInvoiceId(id);
			for (Qualitycheckrawmaterial qualitycheckrawmaterial : qualitycheckrawmaterials) {
				storeInDTOs.add(StoreRequestResponseFactory.createStoreInResonse(qualitycheckrawmaterial));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return storeInDTOs;
	}
	private void mailSending(NotificationDTO notification,Vendor vendor,List<QualityCheckRMDTO> qualityCheckRMDTOs,Rawmaterialorder rawmaterialorder) throws Exception{
		  Mail mail = new Mail();
		  List<NotificationUserAssociatinsDTO> notificationUserAssociatinsDTOs = notificationUserAssociationService.getNotificationUserAssociatinsDTOs(notification.getId());
		  for (NotificationUserAssociatinsDTO notificationuserassociation : notificationUserAssociatinsDTOs) {
			  UserDTO user = userService.getUserDTO( notificationuserassociation.getUserId().getId());
			  if(notificationuserassociation.getTo()==true){
				  mail.setMailTo(vendor.getEmail());
			  }else if(notificationuserassociation.getBcc()==true){
				  mail.setMailBcc(user.getEmailId());
			  }else if(notificationuserassociation.getCc()==true){
				  mail.setMailCc(user.getEmailId());
			  }
		}
	        mail.setMailSubject(notification.getSubject());
	        Map < String, Object > model = new HashMap < String, Object > ();
	        model.put("firstName", vendor.getFirstName());
	        model.put("qualityCheckRMDTOs", qualityCheckRMDTOs);
	        model.put("address", vendor.getAddress());
	        model.put("companyName", vendor.getCompanyName());
	        model.put("lastName", vendor.getLastName());
	        model.put("location", "Pune");
	        model.put("signature", "www.NextechServices.in");
	        mail.setModel(model);

		mailService.sendEmailWithoutPdF(mail,notification);
	}

}
