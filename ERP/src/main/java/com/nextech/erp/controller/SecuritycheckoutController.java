package com.nextech.erp.controller;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.apache.log4j.Logger;
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

import com.nextech.erp.dto.SecurityCheckOutDTO;
import com.nextech.erp.factory.SecurityCheckOutRequestResponseFactory;
import com.nextech.erp.service.DispatchService;
import com.nextech.erp.service.ProductorderService;
import com.nextech.erp.service.ProductorderassociationService;
import com.nextech.erp.service.SecuritycheckoutService;
import com.nextech.erp.service.StatusService;
import com.nextech.erp.status.Response;
import com.nextech.erp.status.UserStatus;


@Controller
@Transactional @RequestMapping("/securitycheckout")
public class SecuritycheckoutController {

	@Autowired
	SecuritycheckoutService securitycheckoutService;

	@Autowired
	ProductorderService productorderService;


	@Autowired
	ProductorderassociationService productorderassociationService;

	@Autowired
	StatusService statusService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	DispatchService dispatchService;

	static Logger logger = Logger.getLogger(SecuritycheckoutController.class);

	@Transactional @RequestMapping(value = "/productOrderCheckOut", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addSecurityCheckOut(@Valid @RequestBody SecurityCheckOutDTO securityCheckOutDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
                
			//TODO Save call security check out
			securitycheckoutService.addSecurityCheckOut(securityCheckOutDTO, request);

			return new UserStatus(1, "Securitycheckout added Successfully !");
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

	@Transactional @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getSecuritycheckout(@PathVariable("id") long id) {
		SecurityCheckOutDTO securitycheckout = null;
		try {
			securitycheckout = securitycheckoutService.getSecurityCheckOutById(id);
			if(securitycheckout==null){
				logger.error("There is no any security check out");
				return  new Response(1,"There is no any security check out");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,securitycheckout);
	}

	@Transactional @RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateSecuritycheckout(
			@RequestBody SecurityCheckOutDTO securityCheckOutDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			securitycheckoutService.updateEntity(SecurityCheckOutRequestResponseFactory.setSecurityCheckOutUpdate(securityCheckOutDTO, request));
			return new UserStatus(1, "Securitycheckout update Successfully !");
		} catch (Exception e) {
			 e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@Transactional @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getSecuritycheckout() {

		List<SecurityCheckOutDTO> securitycheckoutList = null;
		try {
			securitycheckoutList = securitycheckoutService.getSecurityCheckOutList();
			if(securitycheckoutList==null){
				logger.error("There is no any security check out list");
				return  new Response(1,"There is no any secruty check out list");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Response(1,securitycheckoutList);
	}

	@Transactional @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteSecuritycheckout(@PathVariable("id") long id) {

		try {
		SecurityCheckOutDTO  securityCheckOutDTO =	securitycheckoutService.deleteSecurityCheckOut(id);
		if(securityCheckOutDTO==null){
			logger.error("There is no any security check out");
			return  new Response(1,"There is no any security check out");
		}
			return new Response(1, "Securitycheckout deleted Successfully !");
		} catch (Exception e) {
			return new Response(0, e.toString());
		}

	}            

}

