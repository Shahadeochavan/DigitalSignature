package com.nextech.erp.controller;

import java.util.List;

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
import com.nextech.erp.factory.UserTypePageAssoFactory;
import com.nextech.erp.newDTO.UserTypePageAssoDTO;
import com.nextech.erp.newDTO.UserTypePageAssoPart;
import com.nextech.erp.service.PageService;
import com.nextech.erp.service.UserTypeService;
import com.nextech.erp.service.UsertypepageassociationService;
import com.nextech.erp.status.Response;
import com.nextech.erp.status.UserStatus;

@Controller
@RequestMapping("/usertypepageassociation")
public class UsertypepageassociationController {

	@Autowired
	UsertypepageassociationService usertypepageassociationService;
	
	@Autowired
	PageService pageService;
	
	@Autowired
	UserTypeService userTypeService;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/createMultiple", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addMultipleUserTypePageAsso(
			@Valid @RequestBody UserTypePageAssoDTO userTypePageAssoDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			List<UserTypePageAssoPart> userTypePageAssoParts =	userTypePageAssoDTO.getUserTypePageAssoParts();
			for (UserTypePageAssoPart userTypePageAssoPart : userTypePageAssoParts) {	
			if (usertypepageassociationService.getUserTypePageAssoByPageIduserTypeId((userTypePageAssoPart.getPageId().getId()),userTypePageAssoDTO.getUsertypeId().getId()) == null){
				usertypepageassociationService.addMultipleUserTypePageAsso(userTypePageAssoDTO, request.getAttribute("current_user").toString());
			}else{
				return new UserStatus(2, messageSource.getMessage(ERPConstants.USERTYPE_PAGE_ASSOCITION_EXIT, null, null));
			}
			
			}
			return new UserStatus(1,
					"Usertypepageassociation added Successfully !");
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
	public @ResponseBody Response getUserTypePageAsso(
			@PathVariable("id") long id) {
		UserTypePageAssoDTO userTypePageAssoDTO = null;
		try {
			userTypePageAssoDTO = usertypepageassociationService.getUserTypeDto(id);
			if(userTypePageAssoDTO==null){
				return new Response(1,"There is no any user type page association");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,userTypePageAssoDTO);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateUserTypePageAsso(
			@RequestBody UserTypePageAssoDTO userTypePageAssoDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			usertypepageassociationService.addEntity(UserTypePageAssoFactory.setUserTypePageAssUpdate(userTypePageAssoDTO, request));
			return new UserStatus(1,"Usertypepageassociation update Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getUserTypePageAsso() {

		List<UserTypePageAssoDTO> userTypePageAssoDTOs = null;
		try {
			userTypePageAssoDTOs = usertypepageassociationService.getUserTypePageAssList();
			if(userTypePageAssoDTOs.isEmpty()){
				return new Response(1,"There is no any user type page association list");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Response(1,userTypePageAssoDTOs);
	}
	
	@RequestMapping(value = "/UserTypePageAsso/{UserTypeId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getPageAssByUserTypeId(@PathVariable("UserTypeId") long id) {

		List<UserTypePageAssoDTO> UserTypePageAssoDTO = null;
		try {
			UserTypePageAssoDTO = usertypepageassociationService.getPagesByUsertype(id);
			if(UserTypePageAssoDTO==null){
				return new Response(1,"There is no user type page association");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Response(1,UserTypePageAssoDTO);
	}
	
	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteUserTypePageAsso(@PathVariable("id") long id) {

		try {
			UserTypePageAssoDTO userTypePageAssoDTO =	usertypepageassociationService.deleteUserTypePage(id);
			if(userTypePageAssoDTO==null){
				return new Response(1,"There is no any user type page assocition");
			}
			return new Response(1,
					"Usertypepageassociation deleted Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(0, e.toString());
		}

	}
}
