package com.nextech.dscrm.controller;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nextech.dscrm.dto.SupportDTO;
import com.nextech.dscrm.factory.SupportRequestResponseFactory;
import com.nextech.dscrm.model.Support;
import com.nextech.dscrm.service.SupportService;
import com.nextech.dscrm.status.Response;
import com.nextech.dscrm.status.UserStatus;


@Controller
@RequestMapping("/support")
public class SupportController {

	@Autowired
	SupportService supportService;
	
    static Logger logger = Logger.getLogger(SupportController.class);
	
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addSupportDetail(@Valid @RequestBody SupportDTO SupportDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
			}
			
			supportService.addEntity(SupportRequestResponseFactory.setSupport(SupportDTO, request));
			return new UserStatus(1, "support added Successfully !");
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
	public @ResponseBody Response getSupport(@PathVariable("id") long id) {
		Support support = null;
		try {
			support = supportService.getEntityById(Support.class, id);
			if(support==null){
				return new Response(1,"There is no any support");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return new Response(1,support);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateSupport(@RequestBody SupportDTO SupportDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			
			supportService.updateEntity(SupportRequestResponseFactory.setSupportUpdate(SupportDTO, request));
			return new UserStatus(1, "support update Successfully !");
		} catch (Exception e) {
			logger.error(e);
			 e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getSupport() {

		List<Support> supports = null;
		try {
			supports = supportService.getEntityList(Support.class);
			if(supports==null){
				 logger.info("there is no any Support list");
				return new Response(1,"There is no any Support list");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Response(1,supports);
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteSupport(@PathVariable("id") long id) {

		try {
			Support support =supportService.getEntityById(Support.class, id);
			if (support==null) {
				 logger.info("there is no any support");
				return new Response(1,"there is no any support");
			}
			support.setIsactive(false);
			supportService.updateEntity(support);
			return new Response(1, "support deleted Successfully !");
		} catch (Exception e) {
			return new Response(0, e.toString());
		}

	}
}

