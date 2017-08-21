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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dto.Mail;
import com.nextech.erp.factory.VendorFactory;
import com.nextech.erp.newDTO.NotificationDTO;
import com.nextech.erp.newDTO.VendorDTO;
import com.nextech.erp.service.MailService;
import com.nextech.erp.service.NotificationService;
import com.nextech.erp.service.NotificationUserAssociationService;
import com.nextech.erp.service.UserService;
import com.nextech.erp.service.VendorService;
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
	
	
	
	String multipleTO="";

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addVendor(Model model,
			@Valid @RequestBody VendorDTO  vendorDTO, BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				model.addAttribute("vendor", vendorDTO);
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}

			if (vendorService.getVendorByCompanyName(vendorDTO.getCompanyName()) == null) {
   
			} else {
				return new UserStatus(2, messageSource.getMessage(ERPConstants.COMPANY_NAME_EXIT, null, null));
			}
			if (vendorService.getVendorByEmail(vendorDTO.getEmail()) == null) {
			} else {
				return new UserStatus(2,messageSource.getMessage(ERPConstants.EMAIL_ALREADY_EXIT, null, null));
			}
              
              vendorService.addEntity(VendorFactory.setVendor(vendorDTO, request));
    		  NotificationDTO  notificationDTO = notificationService.getNotificationDTOById(Long.parseLong(messageSource.getMessage(ERPConstants.VENDOR_ADDED_SUCCESSFULLY, null, null)));
		      mailSending(vendorDTO, request, response, notificationDTO);
			return new UserStatus(1, "vendor added Successfully !");
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
	public @ResponseBody VendorDTO getVendor(@PathVariable("id") long id) {
		VendorDTO vendorDTO = null;
		try {
			vendorDTO = vendorService.getVendorById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vendorDTO;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateVendor(@RequestBody VendorDTO vendorDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			VendorDTO oldVendorInfo = vendorService.getVendorById(vendorDTO.getId());
			if(vendorDTO.getCompanyName().equals(oldVendorInfo.getCompanyName())){  	
			} else { 
				if (vendorService.getVendorByCompanyName(vendorDTO.getCompanyName()) == null) {
			    }else{  
				return new UserStatus(2, messageSource.getMessage(ERPConstants.COMPANY_NAME_EXIT, null, null));
				}
			 }
            if(vendorDTO.getEmail().equals(oldVendorInfo.getEmail())){  	
			} else { 
				if (vendorService.getVendorByEmail(vendorDTO.getEmail()) == null) {
			    }else{  
				return new UserStatus(2, messageSource.getMessage(ERPConstants.EMAIL_ALREADY_EXIT, null, null));
				}
			 }
            vendorService.updateEntity( VendorFactory.setVendor(vendorDTO, request));
  		  NotificationDTO  notificationDTO = notificationService.getNotificationDTOById(Long.parseLong(messageSource.getMessage(ERPConstants.VENDOR_UPDATE_SUCCESSFULLY, null, null)));
		   mailSending(vendorDTO, request, response, notificationDTO);
			return new UserStatus(1, "Vendor update Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<VendorDTO> getVendor() {

		List<VendorDTO> vendorDTOs = null;
		try {
			vendorDTOs = vendorService.getVendorList(vendorDTOs);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return vendorDTOs;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteVendor(@PathVariable("id") long id) {

		try {
			vendorService.deleteVendor(id);
			return new UserStatus(1, "Vendor deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}

	}
	private void mailSending(VendorDTO vendorDTO,HttpServletRequest request,HttpServletResponse response,NotificationDTO  notificationDTO) throws Exception{
		 Mail mail = userService.emailNotification(notificationDTO);
		 String vendorEmail = mail.getMailTo()+","+vendorDTO.getEmail();
		   mail.setMailTo(vendorEmail);
	        mail.setMailSubject(notificationDTO.getSubject());
	        Map < String, Object > model = new HashMap < String, Object > ();
	        model.put("firstName", vendorDTO.getFirstName());
	        model.put("lastName", vendorDTO.getLastName());
	        model.put("email", vendorDTO.getEmail());
	        model.put("contactNumber", vendorDTO.getContactNumberMobile());
	        model.put("location", "Pune");
	        model.put("signature", "www.NextechServices.in");
	        mail.setModel(model);

		mailService.sendEmailWithoutPdF(mail, notificationDTO);
	}


}
