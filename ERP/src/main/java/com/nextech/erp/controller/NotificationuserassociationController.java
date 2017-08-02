package com.nextech.erp.controller;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nextech.erp.factory.NotificationUserAssRequestResponseFactory;
import com.nextech.erp.newDTO.NotificationUserAssociatinsDTO;
import com.nextech.erp.service.NotificationUserAssociationService;
import com.nextech.erp.status.UserStatus;

@Controller
@Transactional @RequestMapping("/notificationuserassociation")
public class NotificationuserassociationController {

	@Autowired
	NotificationUserAssociationService notificationservice;
	
	@Transactional @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addNotification(@Valid @RequestBody NotificationUserAssociatinsDTO notificationUserAssociatinsDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			notificationUserAssociatinsDTO.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			notificationservice.addEntity(NotificationUserAssRequestResponseFactory.setNotificationUserAss(notificationUserAssociatinsDTO));
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

	@Transactional @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody NotificationUserAssociatinsDTO getNotification(@PathVariable("id") long id) {
		NotificationUserAssociatinsDTO notification = null;
		try {
			notification = notificationservice.getNotificationUserById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notification;
	}

	@Transactional @RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateNotification(@RequestBody NotificationUserAssociatinsDTO notificationUserAssociatinsDTO,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			notificationUserAssociatinsDTO.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			notificationservice.updateEntity(NotificationUserAssRequestResponseFactory.setNotificationUserAss(notificationUserAssociatinsDTO));
			return new UserStatus(1, "Notification update Successfully !");
		} catch (Exception e) {
			// e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@Transactional @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<NotificationUserAssociatinsDTO> getNotification() {

		List<NotificationUserAssociatinsDTO> notificationList = null;
		try {
			notificationList = notificationservice.getNotificationUserAssoList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return notificationList;
	}

	@Transactional @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteNotification(@PathVariable("id") long id) {

		try {
			notificationservice.deleteNotificationUserAsso(id);
			return new UserStatus(1, "Notification deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}

	}
}

