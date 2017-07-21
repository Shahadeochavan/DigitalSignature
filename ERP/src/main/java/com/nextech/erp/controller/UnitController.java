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
import com.nextech.erp.factory.UnitFactory;
import com.nextech.erp.newDTO.UnitDTO;
import com.nextech.erp.service.UnitService;
import com.nextech.erp.status.UserStatus;

@RestController
@RequestMapping("/unit")
public class UnitController {

	@Autowired
	UnitService unitservice;

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addUnit(@Valid @RequestBody UnitDTO unitDTO,HttpServletRequest request,HttpServletResponse response,
			BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
		 	unitservice.addEntity(UnitFactory.setUnit(unitDTO, request));
			return new UserStatus(1, "Unit added Successfully !");
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
	public @ResponseBody UnitDTO getUnit(@PathVariable("id") long id) {
		UnitDTO unit = null;
		try {
			unit = unitservice.getUnitByID(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return unit;
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
	public @ResponseBody List<UnitDTO> getUnit() {

		List<UnitDTO> unitList = null;
		try {
			unitList = unitservice.getUnitList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return unitList;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteUnit(@PathVariable("id") long id) {

		try {
			unitservice.deleteUnit(id);
			return new UserStatus(1, "Unit deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}

	}
}
