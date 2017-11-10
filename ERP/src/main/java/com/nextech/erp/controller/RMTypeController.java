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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nextech.erp.factory.RMTypeRequestResponseFactory;
import com.nextech.erp.newDTO.RMTypeDTO;
import com.nextech.erp.service.RMTypeService;
import com.nextech.erp.status.Response;
import com.nextech.erp.status.UserStatus;

@RestController
@RequestMapping("/rmtype")
public class RMTypeController {

	@Autowired
	RMTypeService rmTypeService;
	
	static Logger logger = Logger.getLogger(RMTypeController.class);
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addRMType(@Valid @RequestBody RMTypeDTO rmTypeDTO,HttpServletRequest request,HttpServletResponse response,
			BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			if(rmTypeService.getRMTypeByRMTypeName(rmTypeDTO.getRmTypeName())!=null){
				return new UserStatus(2,"RM Type Name already exists");
			}
			rmTypeService.addEntity(RMTypeRequestResponseFactory.setRMType(rmTypeDTO, request));
			return new UserStatus(1, "RM Type added Successfully !");
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
	public @ResponseBody Response getRMType(@PathVariable("id") long id) {
		RMTypeDTO rmTypeDTO = null;
		try {
			rmTypeDTO = rmTypeService.getRMTypeById(id);
			if(rmTypeDTO==null){
				logger.error("There is no any rm type ");
				return new Response(1,"There is no any rm type ");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return new Response(1,rmTypeDTO);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateRMType(@RequestBody RMTypeDTO rmTypeDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			rmTypeService.updateEntity(RMTypeRequestResponseFactory.setRMTypeUpdate(rmTypeDTO, request));
			return new UserStatus(1, "RM Type update Successfully !");
		} catch (Exception e) {
			logger.error(e);
			 e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getRMType() {

		List<RMTypeDTO> rmTypeDTOs = null;
		try {
			rmTypeDTOs = rmTypeService.getRMTypeList();
			if(rmTypeDTOs==null){
				logger.error("There is no any rm type list ");
				return  new Response(1,"There is no any rm type list");
			}

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

		return new Response(1,rmTypeDTOs);
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteRMType(@PathVariable("id") long id) {

		try {
			RMTypeDTO rmTypeDTO =rmTypeService.deleteRMType(id);
			if(rmTypeDTO==null){
				logger.error("There is no any rm type ");
				return  new Response(1,"There is no rm type");
			}
			return new Response(1, "RM Type deleted Successfully !");
		} catch (Exception e) {
			logger.error(e);
			return new Response(0, e.toString());
		}

	}
}

