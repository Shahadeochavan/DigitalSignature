package com.nextech.erp.controller;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
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

import com.nextech.erp.factory.NotificationRequestResponseFactory;
import com.nextech.erp.newDTO.NotificationDTO;
import com.nextech.erp.service.NotificationService;
import com.nextech.erp.status.Response;
import com.nextech.erp.status.UserStatus;

@Controller
@Transactional @RequestMapping("/notification")
public class NotificationController {

	@Autowired
	NotificationService notificationservice;
	
	static Logger logger = Logger.getLogger(NotificationController.class);
	
	@Transactional @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addNotification(@Valid @RequestBody NotificationDTO notificationDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			notificationDTO.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			notificationservice.addEntity(NotificationRequestResponseFactory.setNotification(notificationDTO));
			return new UserStatus(1, "Notification added Successfully !");
			
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
	public @ResponseBody Response getNotification(@PathVariable("id") long id) {
		NotificationDTO notification = null;
		try {
			notification = notificationservice.getNotificationDTOById(id);
			if(notification==null){
				logger.error("There is no any notification");
				return  new Response(1,"There is no any notification");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,notification);
	}

	@Transactional @RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateNotification(@RequestBody NotificationDTO notificationDTO,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			notificationDTO.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			notificationservice.updateEntity(NotificationRequestResponseFactory.setNotification(notificationDTO));
			return new UserStatus(1, "Notification update Successfully !");
		} catch (Exception e) {
			// e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@Transactional @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getNotification() {

		List<NotificationDTO> notificationList = null;
		try {
			notificationList = notificationservice.getNofificationList(notificationList);
			if(notificationList==null){
				logger.error("There is no any notification list");
				return new Response(1,"There is no any list");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Response(1,notificationList);
	}

	@Transactional @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteNotification(@PathVariable("id") long id) {

		try {
			NotificationDTO notificationDTO =notificationservice.deleteNofificationById(id);
			if(notificationDTO==null){
				logger.error("There is no any notification for delete");
				return  new Response(1,"There is no any notification for delete");
			}
			return new Response(1, "Notification deleted Successfully !");
		} catch (Exception e) {
			return new Response(0, e.toString());
		}

	}
}

