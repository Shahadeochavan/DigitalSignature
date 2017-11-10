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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.factory.StatusTransitionRequestResponseFactory;
import com.nextech.erp.newDTO.StatusTransitionDTO;
import com.nextech.erp.service.StatustransitionService;
import com.nextech.erp.status.Response;
import com.nextech.erp.status.UserStatus;

@RestController
@Transactional @RequestMapping("/statustransition")
public class StatustransitionController {

	@Autowired
	StatustransitionService statustransitionService;
	
	@Autowired
	private MessageSource messageSource;
	
	static Logger logger = Logger.getLogger(StatustransitionController.class);

	@Transactional @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addStatustransition(
			@Valid @RequestBody StatusTransitionDTO statusTransitionDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			if (statustransitionService.getStatustransitionByEmail(statusTransitionDTO.getIsNotificationEmail()) != null){
				return new UserStatus(1, messageSource.getMessage(ERPConstants.EMAIL_SHOULD_BE_UNIQUE, null, null));
			}
			statustransitionService.addEntity(StatusTransitionRequestResponseFactory.setStatusTransitin(statusTransitionDTO, request));
			return new UserStatus(1, "Statustransition added Successfully !");
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

	@Transactional @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getStatustransition(
			@PathVariable("id") long id) {
		StatusTransitionDTO statustransition = null;
		try {
			statustransition = statustransitionService.getStatusTranstionbyId(id);
			if(statustransition==null){
			logger.error("There is no any stattus transtion");
			 return  new Response(1,"There is no any status transtion");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return new Response(1,statustransition);
	}

	@Transactional @RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateStatustransition(
			@RequestBody StatusTransitionDTO statusTransitionDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			statustransitionService.updateEntity(StatusTransitionRequestResponseFactory.setStatusTransitin(statusTransitionDTO, request));
			return new UserStatus(1, "Statustransition update Successfully !");
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@Transactional @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getStatustransition() {

		List<StatusTransitionDTO> statustransitionList = null;
		try {
			statustransitionList = statustransitionService.getStatusTranstionList();
			if(statustransitionList==null){
				logger.error("There is no any stattus transtion list");
				return new Response(1,"There is no any statsus transition list");
			}

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

		return new Response(1,statustransitionList);
	}

	@Transactional @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteStatustransition(
			@PathVariable("id") long id) {

		try {
			StatusTransitionDTO statusTransitionDTO =	statustransitionService.deleteStatusTranstion(id);
			if(statusTransitionDTO==null){
				logger.error("There is no any stattus transtion");
				return  new Response(1,"There is no any stattus transtion");
			}
			return new Response(1, "Statustransition deleted Successfully !");
		} catch (Exception e) {
			logger.error(e);
			return new Response(0, e.toString());
		}

	}
}
