package com.nextech.erp.controller;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nextech.erp.model.Status;
import com.nextech.erp.service.StatusService;
import com.nextech.erp.status.UserStatus;

@RestController
@RequestMapping("/status")
public class StatusController {

	@Autowired
	StatusService statusService;

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addStatustransition(
			@Valid @RequestBody Status status, BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			status.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			status.setIsactive(true);
			statusService.addEntity(status);
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

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Status getStatus(@PathVariable("id") long id) {
		Status status = null;
		try {
			status = statusService.getEntityById(Status.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateStatus(@RequestBody Status status,HttpServletRequest request,HttpServletResponse response) {
		try {
			status.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			status.setIsactive(true);
			statusService.updateEntity(status);
			return new UserStatus(1, "Status update Successfully !");
		} catch (Exception e) {
			// e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Status> getStatus() {

		List<Status> statusList = null;
		try {
			statusList = statusService.getEntityList(Status.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return statusList;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteStatus(@PathVariable("id") long id) {

		try {
			Status status = statusService.getEntityById(Status.class,id);
			status.setIsactive(false);
			statusService.updateEntity(status);
			return new UserStatus(1, "Status deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}

	}
}
