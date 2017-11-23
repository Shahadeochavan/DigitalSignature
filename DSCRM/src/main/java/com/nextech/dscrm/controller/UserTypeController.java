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

import com.nextech.dscrm.factory.UserTypeFactory;
import com.nextech.dscrm.model.Usertype;
import com.nextech.dscrm.newDTO.UserTypeDTO;
import com.nextech.dscrm.service.UserTypeService;
import com.nextech.dscrm.status.Response;
import com.nextech.dscrm.status.UserStatus;

@Controller
@RequestMapping("/usertype")
public class UserTypeController {
	@Autowired
	UserTypeService userTypeService;
	
	static Logger logger = Logger.getLogger(UserTypeController.class);
	
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addUserType(@Valid @RequestBody UserTypeDTO userTypeDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
			}
			
			if(userTypeService.getUserTypeByUserTypeName(userTypeDTO.getUsertypeName())!=null){
				return new UserStatus(2,"User Type name already exist");
			}
			
			userTypeService.addEntity(UserTypeFactory.setUserType(userTypeDTO, request));
			return new UserStatus(1, "Usertype added Successfully !");
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
	public @ResponseBody Response getUserType(@PathVariable("id") long id) {
		UserTypeDTO userTypeDTO = null;
		try {
			userTypeDTO = userTypeService.getUserTypeDto(id);
			if(userTypeDTO==null){
				logger.info("there is no any user type ");
				return new Response(1,"There is no any user type");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return new Response(1,userTypeDTO);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateUserType(@RequestBody UserTypeDTO userTypeDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			
			Usertype oldUserInfo = userTypeService.getEntityById(Usertype.class, userTypeDTO.getId());
			if(!userTypeDTO.getUsertypeName().equals(oldUserInfo.getUsertypeName())){  
				if (userTypeService.getUserTypeByUserTypeName(userTypeDTO.getUsertypeName()) != null) {
				return new UserStatus(2, "User Type name already exit");
				}
			 }
			
		     userTypeService.updateEntity(UserTypeFactory.setUserTypeUpdate(userTypeDTO, request));
			return new UserStatus(1, "UserType update Successfully !");
		} catch (Exception e) {
			logger.error(e);
			 e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getUserType() {

		List<UserTypeDTO> userList = null;
		try {
			userList = userTypeService.getUserTypeDTO();
			if(userList==null){
				 logger.info("there is no any user type list");
				return new Response(1,"There is no any user type list");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Response(1,userList);
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteUserType(@PathVariable("id") long id) {

		try {
			UserTypeDTO userTypeDTO =userTypeService.deleteUserType(id);
			if (userTypeDTO==null) {
				 logger.info("there is no any user type");
				return new Response(1,"there is no any user type");
			}
			return new Response(1, "UserType deleted Successfully !");
		} catch (Exception e) {
			return new Response(0, e.toString());
		}

	}
}
