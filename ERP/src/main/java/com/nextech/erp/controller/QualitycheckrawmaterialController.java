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
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nextech.erp.dto.Mail;
import com.nextech.erp.dto.QualityCheckRMDTO;
import com.nextech.erp.dto.RawMaterialInvoiceDTO;
import com.nextech.erp.dto.StoreInDTO;
import com.nextech.erp.factory.QualityRequestResponseFactory;
import com.nextech.erp.factory.StoreRequestResponseFactory;
import com.nextech.erp.model.Qualitycheckrawmaterial;
import com.nextech.erp.model.Rawmaterialorder;
import com.nextech.erp.model.Rawmaterialorderinvoice;
import com.nextech.erp.model.Rmorderinvoiceintakquantity;
import com.nextech.erp.model.Status;
import com.nextech.erp.model.Vendor;
import com.nextech.erp.newDTO.NotificationDTO;
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
import com.nextech.erp.status.Response;
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

	@RequestMapping(value = "/qualitycheck", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	@Transactional
	public @ResponseBody UserStatus addRawmaterialorderinvoice(
			@Valid @RequestBody RawMaterialInvoiceDTO rawMaterialInvoiceDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
			}
			
			//TODO save call quality check
			qualitycheckrawmaterialService.addQualityCheck(rawMaterialInvoiceDTO, request, response);
		    Rawmaterialorderinvoice rawmaterialorderinvoiceNew = rawmaterialorderinvoiceService.getEntityById(Rawmaterialorderinvoice.class,rawMaterialInvoiceDTO.getId());
			Rawmaterialorder rawmaterialorder = rawmaterialorderService.getEntityById(Rawmaterialorder.class, rawmaterialorderinvoiceNew.getPo_No());
			List<QualityCheckRMDTO> qualityCheckRMDTOs = rawMaterialInvoiceDTO.getQualityCheckRMDTOs();
			Status status = statusService.getEntityById(Status.class, rawmaterialorderinvoiceNew.getStatus().getId());
			Vendor vendor = vendorService.getEntityById(Vendor.class, Long.parseLong(rawmaterialorderinvoiceNew.getVendorname()));
			NotificationDTO notificationDTO = notificationService.getNotifiactionByStatus(status.getId());
			mailSending(notificationDTO, vendor,qualityCheckRMDTOs,rawmaterialorder);
			return new UserStatus(1,"Quality Check RM Succesfully");
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.getCause().getMessage());
		}
	}

	@RequestMapping(value = "/qualityCheckInReadyStore", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	@Transactional
	public @ResponseBody UserStatus addRawmaterialorderinvoiceReadyStore(
			@Valid @RequestBody RawMaterialInvoiceDTO rawMaterialInvoiceDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
			}
			//TODO RM Ready Store In
			qualitycheckrawmaterialService.addRawmaterialOrderInvoiceReadyStore(rawMaterialInvoiceDTO, request, response);
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
	
	@RequestMapping(value = "listrm/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getRmorderinvoiceintakquantityByRMOInvoiceId(
			@PathVariable("id") long id) {
		List<QualityCheckRMDTO> qualityCheckRMDTOs = new ArrayList<QualityCheckRMDTO>();
		try {
			List<Rmorderinvoiceintakquantity> rmorderinvoiceintakquantities = rmorderinvoiceintakquantityService.getRmorderinvoiceintakquantityByRMOInvoiceId(id);
			if(rmorderinvoiceintakquantities.isEmpty()){
				return  new Response(1,"There is no any qc rm");
			}
			for (Rmorderinvoiceintakquantity rmorderinvoiceintakquantity : rmorderinvoiceintakquantities) {
				QualityCheckRMDTO qualityCheckRMDTO = QualityRequestResponseFactory.createQualityResonse(rmorderinvoiceintakquantity);
				qualityCheckRMDTO.setId(rmorderinvoiceintakquantity.getRawmaterial().getId());
				qualityCheckRMDTOs.add(qualityCheckRMDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,qualityCheckRMDTOs);
	}
	
	@RequestMapping(value = "listrmGoodQuantity/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getRmorderinvoiceintakquantity(
			@PathVariable("id") long id) {
		List<StoreInDTO> storeInDTOs = new ArrayList<StoreInDTO>();
		try {
			List<Qualitycheckrawmaterial> qualitycheckrawmaterials = qualitycheckrawmaterialService.getQualitycheckrawmaterialByInvoiceId(id);
			if(qualitycheckrawmaterials.isEmpty()){
				return new Response(1,"there is no any qc rm list");
			}
			for (Qualitycheckrawmaterial qualitycheckrawmaterial : qualitycheckrawmaterials) {
				storeInDTOs.add(StoreRequestResponseFactory.createStoreInResonse(qualitycheckrawmaterial));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,storeInDTOs);
	}
	
	private void mailSending(NotificationDTO notification,Vendor vendor,List<QualityCheckRMDTO> qualityCheckRMDTOs,Rawmaterialorder rawmaterialorder) throws Exception{
		  Mail mail = userService.emailNotification(notification);
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