package com.nextech.erp.controller;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nextech.erp.factory.StatusRequestResponseFactory;
import com.nextech.erp.newDTO.StatusDTO;
import com.nextech.erp.service.StatusService;
import com.nextech.erp.status.UserStatus;

@RestController
@Transactional @RequestMapping("/status")
public class StatusController {

	@Autowired
	StatusService statusService;

	@Transactional @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addStatustransition(
			@Valid @RequestBody StatusDTO statusDTO, BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			statusService.addEntity(StatusRequestResponseFactory.setStatus(statusDTO, request));
			return new UserStatus(1, "Status added Successfully !");
		} catch (ConstraintViolationException cve) {
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
	public @ResponseBody StatusDTO getStatus(@PathVariable("id") long id) {
		StatusDTO status = null;
		try {
			status = statusService.getStatusById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	@Transactional @RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateStatus(@RequestBody StatusDTO statusDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			statusService.updateEntity(StatusRequestResponseFactory.setStatusUpdate(statusDTO, request));
			return new UserStatus(1, "Status update Successfully !");
		} catch (Exception e) {
			// e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@Transactional @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<StatusDTO> getStatus() {

		List<StatusDTO> statusList = null;
		try {
			statusList = statusService.getStatusList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return statusList;
	}

	@Transactional @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteStatus(@PathVariable("id") long id) {

		try {
			statusService.deleteStatus(id);
			return new UserStatus(1, "Status deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}

	}
}
