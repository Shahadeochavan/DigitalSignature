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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.model.Statustransition;
import com.nextech.erp.service.StatustransitionService;
import com.nextech.erp.status.UserStatus;

@RestController
@RequestMapping("/statustransition")
public class StatustransitionController {

	@Autowired
	StatustransitionService statustransitionService;
	
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addStatustransition(
			@Valid @RequestBody Statustransition statustransition,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			if (statustransitionService
					.getStatustransitionByEmail(statustransition
							.getIsNotificationEmail()) == null){
				statustransition.setIsactive(true);
				statustransition.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
				statustransitionService.addEntity(statustransition);
			}
			else
				return new UserStatus(1, messageSource.getMessage(ERPConstants.EMAIL_ALREADY_EXIT, null, null));
			return new UserStatus(1, "Statustransition added Successfully !");
		} catch (ConstraintViolationException cve) {
			System.out.println("Inside ConstraintViolationException");
			cve.printStackTrace();
			return new UserStatus(0, cve.getCause().getMessage());
		} catch (PersistenceException pe) {
			System.out.println("Inside PersistenceException");
			pe.printStackTrace();
			return new UserStatus(0, pe.getCause().getMessage());
		} catch (Exception e) {
			System.out.println("Inside Exception");
			e.printStackTrace();
			return new UserStatus(0, e.getCause().getMessage());
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Statustransition getStatustransition(
			@PathVariable("id") long id) {
		Statustransition statustransition = null;
		try {
			statustransition = statustransitionService
					.getEntityById(Statustransition.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statustransition;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateStatustransition(
			@RequestBody Statustransition statustransition,HttpServletRequest request,HttpServletResponse response) {
		try {
			statustransition.setIsactive(true);
			statustransition.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			statustransitionService.updateEntity(statustransition);
			return new UserStatus(1, "Statustransition update Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Statustransition> getStatustransition() {

		List<Statustransition> statustransitionList = null;
		try {
			statustransitionList = statustransitionService
					.getEntityList(Statustransition.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return statustransitionList;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteStatustransition(
			@PathVariable("id") long id) {

		try {
			Statustransition statustransition = statustransitionService
					.getEntityById(Statustransition.class, id);
			statustransition.setIsactive(false);
			statustransitionService.updateEntity(statustransition);
			return new UserStatus(1, "Statustransition deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}

	}
}
