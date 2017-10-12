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

import com.nextech.erp.factory.UnitFactory;
import com.nextech.erp.newDTO.UnitDTO;
import com.nextech.erp.service.UnitService;
import com.nextech.erp.status.Response;
import com.nextech.erp.status.UserStatus;

@RestController
@RequestMapping("/unit")
public class UnitController {

	@Autowired
	UnitService unitservice;
	
	static Logger logger = Logger.getLogger(UnitController.class);

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addUnit(@Valid @RequestBody UnitDTO unitDTO,HttpServletRequest request,HttpServletResponse response,
			BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			if(unitservice.getUnitByName(unitDTO.getName())==null){
				unitservice.addEntity(UnitFactory.setUnit(unitDTO, request));
			}else{
				return  new UserStatus(2,"Unit name already exist");
			}
		 	
			return new UserStatus(1, "Unit added Successfully !");
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

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getUnit(@PathVariable("id") long id) {
		UnitDTO unit = null;
		try {
			unit = unitservice.getUnitByID(id);
			if(unit==null){
				logger.error("There is no any unit");
				return new Response(1,"There is no any unit");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,unit);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateUnit(@RequestBody UnitDTO unitDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			unitservice.updateEntity(UnitFactory.setUnitUpdate(unitDTO, request));
			return new UserStatus(1, "Unit update Successfully !");
		} catch (Exception e) {
			 e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getUnit() {
		List<UnitDTO> unitList = null;
		try {
			unitList = unitservice.getUnitList();
			if(unitList==null){
				logger.error("There is no any unit list");
				return new Response(1,"There is no any unit list");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,unitList);
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteUnit(@PathVariable("id") long id) {

		try {
			UnitDTO unitDTO =unitservice.deleteUnit(id);
			if(unitDTO==null){
				logger.error("There is no any unit");
				return new Response(1,"There is no any unit");
			}
			return new Response(1, "Unit deleted Successfully !");
		} catch (Exception e) {
			return new Response(0, e.toString());
		}

	}
}
