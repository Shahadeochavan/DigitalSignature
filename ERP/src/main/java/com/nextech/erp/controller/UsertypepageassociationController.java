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

import com.nextech.erp.factory.UserTypePageAssoFactory;
import com.nextech.erp.newDTO.PageDTO;
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
	
	static Logger logger = Logger.getLogger(UsertypepageassociationController.class);
	

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
			if(!userTypePageAssoParts.isEmpty()){
			for (UserTypePageAssoPart userTypePageAssoPart : userTypePageAssoParts) {	
			if (usertypepageassociationService.getUserTypePageAssoByPageIduserTypeId((userTypePageAssoPart.getPageId().getId()),userTypePageAssoDTO.getUsertypeId().getId()) == null){
				usertypepageassociationService.addMultipleUserTypePageAsso(userTypePageAssoDTO, request.getAttribute("current_user").toString());
			}else{
				PageDTO pageDTO = pageService.getPageDTOById(userTypePageAssoPart.getPageId().getId());
				String pageName = pageDTO.getPageName()+" already exists";
				return new UserStatus(2, pageName);
			}
			}
			}else{
				return new UserStatus(2,"Please select page and click on add button");
			}
			return new UserStatus(1,"User Type Page Association added uccessfully !");
		} catch (ConstraintViolationException cve) {
			logger.error(cve);
			cve.printStackTrace();
			return new UserStatus(0, cve.getCause().getMessage());
		} catch (PersistenceException pe) {
			logger.error(pe);
			pe.printStackTrace();
			return new UserStatus(0, pe.getCause().getMessage());
		} catch (Exception e) {
			logger.error(e);
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
				logger.info("There is no any user type page association");
				return new Response(1,"There is no any user type page association");
			}
		} catch (Exception e) {
			logger.error(e);
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
			logger.error(e);
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
				logger.info("There is no any user type page association list");
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
				logger.info("There is no any user type page association");
				return new Response(1,"There is no user type page association");
			}

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

		return new Response(1,UserTypePageAssoDTO);
	}
	
	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteUserTypePageAsso(@PathVariable("id") long id) {

		try {
			UserTypePageAssoDTO userTypePageAssoDTO =	usertypepageassociationService.deleteUserTypePage(id);
			if(userTypePageAssoDTO==null){
				logger.info("There is no any user type page association");
				return new Response(1,"There is no any user type page assocition");
			}
			PageDTO pageDTO = pageService.getPageDTOById(userTypePageAssoDTO.getPage().getId());
			String message = "From User Type Pagee Association "+pageDTO.getPageName()+" page deleted successfully ";
			return new Response(1,message);
		} catch (Exception e) {
			logger.error(e);
			return new Response(0, e.toString());
		}

	}
}
