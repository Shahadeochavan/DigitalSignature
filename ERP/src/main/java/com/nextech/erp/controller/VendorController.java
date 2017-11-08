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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dto.Mail;
import com.nextech.erp.exception.DuplicateEnteryException;
import com.nextech.erp.factory.MailResponseRequestFactory;
import com.nextech.erp.factory.VendorFactory;
import com.nextech.erp.newDTO.NotificationDTO;
import com.nextech.erp.newDTO.VendorDTO;
import com.nextech.erp.service.MailService;
import com.nextech.erp.service.NotificationService;
import com.nextech.erp.service.NotificationUserAssociationService;
import com.nextech.erp.service.UserService;
import com.nextech.erp.service.VendorService;
import com.nextech.erp.status.Response;
import com.nextech.erp.status.UserStatus;

@Controller
@RequestMapping("/vendor")
public class VendorController {

	@Autowired
	VendorService vendorService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	NotificationService notificationService;
	
	@Autowired
	UserService userService;

	@Autowired
	NotificationUserAssociationService notificationUserAssService;

	@Autowired
	MailService mailService;
	
	static Logger logger = Logger.getLogger(VendorController.class);
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addVendor(Model model,
			@Valid @RequestBody VendorDTO  vendorDTO, BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) throws DuplicateEnteryException {
		try {
			if (bindingResult.hasErrors()) {
				model.addAttribute("vendor", vendorDTO);
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}

			if (vendorService.getVendorByCompanyName(vendorDTO.getCompanyName()) != null) {
				return new UserStatus(2, messageSource.getMessage(ERPConstants.COMPANY_NAME_SHOULD_BE_UNIQUE, null, null));
			}
			if (vendorService.getVendorByEmail(vendorDTO.getEmail()) != null) {
				return new UserStatus(2,messageSource.getMessage(ERPConstants.EMAIL_SHOULD_BE_UNIQUE, null, null));
			} 
              vendorService.addEntity(VendorFactory.setVendor(vendorDTO, request));
              NotificationDTO  notificationDTO = notificationService.getNotificationByCode((messageSource.getMessage(ERPConstants.VENDOR_ADDED_SUCCESSFULLY, null, null)));
              emailNotificationVendor(vendorDTO, notificationDTO);
			return new UserStatus(1, "vendor added Successfully !");
		}
		catch (ConstraintViolationException cve) {
			logger.info("Inside ConstraintViolationException ");
			throw new DuplicateEnteryException("ConstraintViolationException");
			//return new UserStatus(0, cve.getCause().getMessage());
		} 
		catch (PersistenceException pe) {
			logger.info("Inside PersistenceException ");
			pe.printStackTrace();
			return new UserStatus(0, pe.getCause().getMessage());
		} catch (Exception e) {
			logger.info("Inside Exception ");
			e.printStackTrace();
			return new UserStatus(0, e.getCause().getMessage());
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getVendor(@PathVariable("id") long id) {
		VendorDTO vendorDTO = null;
		try {
			vendorDTO = vendorService.getVendorById(id);
			if(vendorDTO==null){
				return new Response(1,"There is no vendor");
			}
		} catch (Exception e) {
			logger.error("Exception in vendor ");
			e.printStackTrace();
		}
		return new Response(1,vendorDTO);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateVendor(@RequestBody VendorDTO vendorDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			VendorDTO oldVendorInfo = vendorService.getVendorById(vendorDTO.getId());
			if(!vendorDTO.getCompanyName().equals(oldVendorInfo.getCompanyName())){ 
				if (vendorService.getVendorByCompanyName(vendorDTO.getCompanyName()) != null) {
				return new UserStatus(2, messageSource.getMessage(ERPConstants.COMPANY_NAME_SHOULD_BE_UNIQUE, null, null));
				}
			 }
            if(!vendorDTO.getEmail().equals(oldVendorInfo.getEmail())){  	
				if (vendorService.getVendorByEmail(vendorDTO.getEmail()) != null) {
				return new UserStatus(2, messageSource.getMessage(ERPConstants.EMAIL_SHOULD_BE_UNIQUE, null, null));
				}
			 }
            vendorService.updateEntity( VendorFactory.setVendor(vendorDTO, request));
            NotificationDTO  notificationDTO = notificationService.getNotificationByCode((messageSource.getMessage(ERPConstants.VENDOR_UPDATE_SUCCESSFULLY, null, null)));
            emailNotificationVendor(vendorDTO, notificationDTO);
			return new UserStatus(1, "Vendor update Successfully !");
		} catch (Exception e) {
			logger.error("Exception in vendor update");
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getVendor() {
		List<VendorDTO> vendorDTOs = null;
		try {
			vendorDTOs = vendorService.getVendorList(vendorDTOs);
			if(vendorDTOs==null){
				logger.error("There is no any vendor list");
				return new Response(1,"There is no vendor list");
			}
		} catch (Exception e) {
			logger.error("Exception in vendor delete");
			e.printStackTrace();
		}
		return new Response(1,vendorDTOs);
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteVendor(@PathVariable("id") long id) {

		try {
			VendorDTO vendorDTO = vendorService.deleteVendor(id);
			if(vendorDTO==null){
				logger.info("There is no any data in vendor");
				return new Response(1,"There is no any vendor for delete");
			}
			return new Response(1, "Vendor deleted Successfully !");
		} catch (Exception e) {
			logger.error("Exception in vendor delete");
			return new Response(0, e.toString());
		}

	}
	
	private void emailNotificationVendor(VendorDTO vendorDTO,NotificationDTO  notificationDTO) throws Exception{
		Mail mail = mailService.setMailCCBCCAndTO(notificationDTO);
		 String vendorEmail = mail.getMailTo()+","+vendorDTO.getEmail();
		   mail.setMailTo(vendorEmail);
		   mail.setModel(MailResponseRequestFactory.setMailDetailsVendor(vendorDTO));
		mailService.sendEmailWithoutPdF(mail, notificationDTO);
	}
}
