package com.nextech.erp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.AuthenticationException;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.apache.log4j.Logger;
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
import com.nextech.erp.dto.Mail;
import com.nextech.erp.factory.UserFactory;
import com.nextech.erp.filter.TokenFactory;
import com.nextech.erp.model.Authorization;
import com.nextech.erp.model.Page;
import com.nextech.erp.model.Report;
import com.nextech.erp.model.Reportusertypeassociation;
import com.nextech.erp.model.User;
import com.nextech.erp.model.Usertype;
import com.nextech.erp.newDTO.NotificationDTO;
import com.nextech.erp.newDTO.UserDTO;
import com.nextech.erp.newDTO.UserTypePageAssoDTO;
import com.nextech.erp.service.MailService;
import com.nextech.erp.service.NotificationService;
import com.nextech.erp.service.NotificationUserAssociationService;
import com.nextech.erp.service.ReportusertypeassociationService;
import com.nextech.erp.service.UserService;
import com.nextech.erp.service.UserTypeService;
import com.nextech.erp.service.UsertypepageassociationService;
import com.nextech.erp.status.Response;
import com.nextech.erp.status.UserStatus;
import com.nextech.erp.util.EncryptDecrypt;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userservice;

	@Autowired
	com.nextech.erp.filter.TokenFactory tokenFactory;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	UserTypeService userTypeService;

	@Autowired
	UsertypepageassociationService usertypepageassociationService;
	
	@Autowired
	NotificationUserAssociationService notificationUserAssService;

	@Autowired
	NotificationService notificationService;
	
	@Autowired
	ReportusertypeassociationService reportusertypeassociationService;

	@Autowired
	MailService mailService;
	
	static Logger logger = Logger.getLogger(UserController.class);
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addUser(@Valid @RequestBody UserDTO userDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			if ((Boolean) request.getAttribute("auth_token")) {
				if (userservice.getUserByUserId(userDTO.getUserId()) == null) {

				} else {
					return new UserStatus(2, messageSource.getMessage(
							ERPConstants.USER_ID, null, null));
				}
				if (userservice.getUserByEmail(userDTO.getEmailId()) == null) {
				} else {
					return new UserStatus(2, messageSource.getMessage(
							ERPConstants.EMAIL_ALREADY_EXIT, null, null));
				}
				if (userservice.getUserByMobile(userDTO.getMobileNo()) == null) {
				} else {
					return new UserStatus(2, messageSource.getMessage(
							ERPConstants.CONTACT_NUMBER_EXISTS, null, null));
				}
				User user = UserFactory.setUser(userDTO, request);
				user.setPassword(new EncryptDecrypt().encrypt(userDTO.getPassword()));
				user.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			   userservice.addEntity(user);
	            NotificationDTO  notificationDTO = notificationService.getNotificationByCode((messageSource.getMessage(ERPConstants.USER_ADD_NOTIFICATION, null, null)));
		       mailSending(userDTO, request, response,notificationDTO);
			return new UserStatus(1, "User added Successfully !");
			} else {
				new UserStatus(0, "User is not authenticated.");
			}
		} catch (ConstraintViolationException cve) {
			System.out.println("Inside ConstraintViolationException");
			cve.printStackTrace();
			return new UserStatus(0, cve.getCause().getMessage());
		} catch (PersistenceException pe) {
			System.out.println("Inside PersistenceException");
			pe.printStackTrace();
			return new UserStatus(0, pe.getCause().getMessage());
		} catch (AuthenticationException authException) {

			return new UserStatus(0, authException.getCause().getMessage());
		} catch (Exception e) {
			System.out.println("Inside Exception");
			e.printStackTrace();
			return new UserStatus(0, e.getCause().getMessage());
		}
		return new UserStatus(0, "User is not authenticated.");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	public UserStatus login(@RequestBody User user, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user2 = userservice.getUserByUserId(user.getUserid());
		
		try {

			System.out.println(messageSource.getMessage(ERPConstants.COUNT,
					null, null));
			logger.info("this is a information log message");
			logger.warn("this is a warning log message");
			logger.error("this is a error log message");
			if (user2 != null && authenticate(user, user2)) {
				Authorization authorization = new Authorization();
				authorization.setUserid(user.getUserid());
				authorization.setPassword(user.getPassword());
				authorization.setUpdatedDate(new Date());
				String token = TokenFactory.createAccessJwtToken(user2);
				authorization.setToken(token);
				response.addHeader("auth_token", token);
				Usertype usertype = userTypeService.getEntityById(Usertype.class, user2.getUsertype().getId());
				List<UserTypePageAssoDTO> usertypepageassociations = usertypepageassociationService
						.getPagesByUsertype(usertype.getId());
				List<Reportusertypeassociation> reportusertypeassociations = reportusertypeassociationService.getReportByUsertype(usertype.getId());
				HashMap<String, Object> result = new HashMap<String, Object>();
				List<Page> pages = new ArrayList<Page>();
				List<Report> reports = new ArrayList<Report>();
				if(!reportusertypeassociations.isEmpty()){
					for (Reportusertypeassociation reportusertypeassociation : reportusertypeassociations) {
						reports.add(reportusertypeassociation.getReport());
					}
					result.put("reports", reports);
				}
				if(!usertypepageassociations.isEmpty()){
				for (UserTypePageAssoDTO usertypepageassociation : usertypepageassociations) {
					Page pageDTO =  new Page();
					pageDTO.setId(usertypepageassociation.getPage().getId());
					pageDTO.setMenu(usertypepageassociation.getPage().getMenu());
					pageDTO.setPageName(usertypepageassociation.getPage().getPageName());
					pageDTO.setSubmenu(usertypepageassociation.getPage().getSubmenu());
					pageDTO.setUrl(usertypepageassociation.getPage().getUrl());
					pages.add(pageDTO);
				}
				result.put("pages", pages);
				}
				String success = user.getUserid()+" logged in Successfully";
				return new UserStatus(1, success, result,user2);
			}
		} catch (AuthenticationException authException) {
			return new UserStatus(0, authException.getCause().getMessage());
		} catch (Exception e) {
			System.out.println("Inside Exception");
			e.printStackTrace();
			return new UserStatus(0, e.getCause().getMessage());
		}
		return new UserStatus(0, "Please enter correct credentials");

	}

	private boolean authenticate(User formUser, User dbUser) {
		EncryptDecrypt encDec = new EncryptDecrypt();
		//TODO NIKHIL- Do not trim of make checking case insensitive. 
		String pass = encDec.decrypt(dbUser.getPassword());
		boolean password =formUser.getPassword().equals(pass);
		if(password==true){
			return true;
		} 	
		else {
			return false;
		}
	}
	

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getUser(@PathVariable("id") long id) {
		UserDTO userDTO = null;
		try {
			userDTO = userservice.getUserDTO(id);
			if(userDTO==null){
				return  new Response(1,"There is no any user");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,userDTO);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateUser(@RequestBody UserDTO userDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			User oldUserInfo = userservice.getEntityById(User.class, userDTO.getId());
			if(userDTO.getUserId().equals(oldUserInfo.getUserid())){  
			} else { 
				if (userservice.getUserByUserId(userDTO.getUserId()) == null) {
			    }else{  
				return new UserStatus(2, messageSource.getMessage(ERPConstants.USER_ID, null, null));
				}
			 }
            if(userDTO.getEmailId().equals(oldUserInfo.getEmail())){  
			} else { 
				if (userservice.getUserByEmail(userDTO.getEmailId()) == null) {
			    }else{  
				return new UserStatus(2, messageSource.getMessage(ERPConstants.EMAIL_ALREADY_EXIT, null, null));
				}
			 }           
			if (userDTO.getMobileNo().equals(oldUserInfo.getMobile())) {
			} else {
				if (userservice.getUserByMobile(userDTO.getMobileNo()) == null) {
				} else {
					return new UserStatus(2, messageSource.getMessage(
							ERPConstants.CONTACT_NUMBER_EXISTS, null, null));
				}
			}
			User user = UserFactory.setUserUpdate(userDTO, request);
			if(userDTO.getPassword().equals(oldUserInfo.getPassword())){
				user.setPassword(userDTO.getPassword());
				 EncryptDecrypt encDec = new EncryptDecrypt();
					String pass = encDec.decrypt(userDTO.getPassword());
					userDTO.setPassword(pass); 
				
			}else{
			    user.setPassword(new com.nextech.erp.util.EncryptDecrypt().encrypt(userDTO.getPassword()));
			    EncryptDecrypt encDec = new EncryptDecrypt();
				String pass = encDec.decrypt(user.getPassword());
				userDTO.setPassword(pass); 
			}
	     	userservice.updateEntity(user);
            NotificationDTO  notificationDTO = notificationService.getNotificationByCode((messageSource.getMessage(ERPConstants.USER_UPDATE_NOTIFICATION, null, null)));
           
		    mailSending(userDTO, request, response, notificationDTO);
		return new UserStatus(1, "User update Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getUser() {

		List<UserDTO> userList = null;
		try {
			userList = userservice.getUserList(userList);
			logger.debug("this is a debudg log message");
			if(userList==null){
				return new Response(1,"There is no any user list");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Response(1,userList);
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteEmployee(@PathVariable("id") long id) {

		try {
			 UserDTO user =userservice.deleteUser(id);
			 if (user==null) {
				 return new Response(1,"There is no any user for delete");
			}
			return new Response(1, "User deleted Successfully !");
		} catch (Exception e) {
			return new Response(0, e.toString());
		}

	}
	private void mailSending(UserDTO userDTO,HttpServletRequest request,HttpServletResponse response,NotificationDTO  notificationDTO) throws NumberFormatException, Exception{
         Mail mail = userservice.emailNotification(notificationDTO);
         String userEmailTo = mail.getMailTo()+","+userDTO.getEmailId();
                mail.setMailTo(userEmailTo);      
		        mail.setMailSubject(notificationDTO.getSubject());
		        Map < String, Object > model = new HashMap < String, Object > ();
		        model.put("firstName", userDTO.getFirstName());
		        model.put("lastName", userDTO.getLastName());
		        model.put("userId", userDTO.getUserId());
		        model.put("password", userDTO.getPassword());
		        model.put("email", userDTO.getEmailId());
		        model.put("location", "Pune");
		        model.put("signature", "www.NextechServices.in");
		        mail.setModel(model);
		        mailService.sendEmailWithoutPdF(mail, notificationDTO);
	}

}
