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
import com.nextech.erp.status.UserStatus;

@RestController
@Transactional @RequestMapping("/statustransition")
public class StatustransitionController {

	@Autowired
	StatustransitionService statustransitionService;
	
	@Autowired
	private MessageSource messageSource;

	@Transactional @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addStatustransition(
			@Valid @RequestBody StatusTransitionDTO statusTransitionDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			if (statustransitionService
					.getStatustransitionByEmail(statusTransitionDTO
							.getIsNotificationEmail()) == null){
				statustransitionService.addEntity(StatusTransitionRequestResponseFactory.setStatusTransitin(statusTransitionDTO, request));
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

	@Transactional @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody StatusTransitionDTO getStatustransition(
			@PathVariable("id") long id) {
		StatusTransitionDTO statustransition = null;
		try {
			statustransition = statustransitionService.getStatusTranstionbyId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statustransition;
	}

	@Transactional @RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateStatustransition(
			@RequestBody StatusTransitionDTO statusTransitionDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			statustransitionService.updateEntity(StatusTransitionRequestResponseFactory.setStatusTransitin(statusTransitionDTO, request));
			return new UserStatus(1, "Statustransition update Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@Transactional @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<StatusTransitionDTO> getStatustransition() {

		List<StatusTransitionDTO> statustransitionList = null;
		try {
			statustransitionList = statustransitionService.getStatusTranstionList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return statustransitionList;
	}

	@Transactional @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteStatustransition(
			@PathVariable("id") long id) {

		try {
			statustransitionService.deleteStatusTranstion(id);
			return new UserStatus(1, "Statustransition deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}

	}
}
