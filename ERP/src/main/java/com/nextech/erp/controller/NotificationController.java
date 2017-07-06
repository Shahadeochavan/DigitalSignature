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
import com.nextech.erp.model.Notification;
import com.nextech.erp.model.Status;
import com.nextech.erp.service.NotificationService;
import com.nextech.erp.service.StatusService;
import com.nextech.erp.status.UserStatus;


@Controller
@RequestMapping("/notification")
public class NotificationController {

	@Autowired
	NotificationService notificationservice;
	
	@Autowired
	private MessageSource messageSource;
	@Autowired
	StatusService statusService;

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addNotification(@Valid @RequestBody Notification notification,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			Status status = statusService.getEntityById(Status.class, 1);
			notification.setStatus2(status);
			notification.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			notification.setIsactive(true);
			notificationservice.addEntity(notification);
			return new UserStatus(1, "Notification added Successfully !");
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
	public @ResponseBody Notification getNotification(@PathVariable("id") long id) {
		Notification notification = null;
		try {
			notification = notificationservice.getEntityById(Notification.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notification;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateNotification(@RequestBody Notification notification,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			notification.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			notification.setIsactive(true);
			notificationservice.updateEntity(notification);
			return new UserStatus(1, "Notification update Successfully !");
		} catch (Exception e) {
			// e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Notification> getNotification() {

		List<Notification> notificationList = null;
		try {
			notificationList = notificationservice.getEntityList(Notification.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return notificationList;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteNotification(@PathVariable("id") long id) {

		try {
			Notification notification = notificationservice.getEntityById(Notification.class,id);
			notification.setIsactive(false);
			notificationservice.updateEntity(notification);
			return new UserStatus(1, "Notification deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}

	}
}

