package com.nextech.erp.controller;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.factory.RMRequestResponseFactory;
import com.nextech.erp.newDTO.RMVendorAssociationDTO;
import com.nextech.erp.newDTO.RawMaterialDTO;
import com.nextech.erp.service.RawmaterialService;
import com.nextech.erp.service.RawmaterialinventoryService;
import com.nextech.erp.status.UserStatus;
@Controller
@Transactional @RequestMapping("/rawmaterial")
public class RawmaterialController {

	@Autowired
	RawmaterialService rawmaterialService;
	
	@Autowired
	RawmaterialinventoryService rawmaterialinventoryService;

	@Autowired
	private MessageSource messageSource;

	@SuppressWarnings("null")
	@ExceptionHandler(Exception.class)
	@Transactional @RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody UserStatus addRawmaterial(
			@Valid @RequestBody RawMaterialDTO rawMaterialDTO, BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
		long id=	rawmaterialService.addEntity(RMRequestResponseFactory.setRawMaterial(rawMaterialDTO, request));
		rawMaterialDTO.setId(id);
			addRMInventory(rawMaterialDTO, Long.parseLong(request.getAttribute("current_user").toString()));
			return new UserStatus(1, messageSource.getMessage(ERPConstants.RAW_MATERAIL_ADD, null, null));
		} catch (ConstraintViolationException cve) {
			cve.printStackTrace();
			return new UserStatus(0, cve.getCause().getMessage());
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			return new UserStatus(0, pe.getCause().getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.getCause().getMessage());
		}
	}

	@Transactional @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody RawMaterialDTO getRawmaterial(@PathVariable("id") long id) {
		RawMaterialDTO rawmaterial = null;
		try {
			rawmaterial = rawmaterialService.getRMDTO(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rawmaterial;
	}

	@Transactional @RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateRawmaterial(@RequestBody RawMaterialDTO rawMaterialDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			rawmaterialService.updateEntity(RMRequestResponseFactory.setRawMaterialUpdate(rawMaterialDTO, request));
			return new UserStatus(1,messageSource.getMessage(ERPConstants.RAW_MATERAIL_UPDATE, null, null));
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@Transactional @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<RawMaterialDTO> getRawmaterial() {

		List<RawMaterialDTO> rawmaterialList = null;
		try {
			rawmaterialList = rawmaterialService.getRMList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rawmaterialList;
	}

	@Transactional @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteRawmaterial(@PathVariable("id") long id) {

		try {

			rawmaterialService.deleteRM(id);
			return new UserStatus(1, messageSource.getMessage(ERPConstants.RAW_MATERAIL_DELETE, null, null));
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}

	}

	@Transactional @RequestMapping(value = "/getRMaterial/{VendorId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<RMVendorAssociationDTO> getRawmaterialForVendor(@PathVariable("VendorId") long id) {

		List<RMVendorAssociationDTO> rawmaterialvendorassociationList = null;
		try {
			rawmaterialvendorassociationList = rawmaterialService.getRawmaterialByVenodrId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rawmaterialvendorassociationList;
	}

	@Transactional @RequestMapping(value = "/getRMForRMOrder/{RMOrderId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<RawMaterialDTO> getRawmaterialForRMOrder(@PathVariable("RMOrderId") long id) {

		List<RawMaterialDTO> rawmaterialList = null;
		try {
			rawmaterialList = rawmaterialService.getRawMaterialByRMOrderId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rawmaterialList;
	}
	private void addRMInventory(RawMaterialDTO rawMaterialDTO,long userId) throws Exception{
		rawmaterialinventoryService.addEntity(RMRequestResponseFactory.setRMIn(rawMaterialDTO));
	}
	
	@Transactional @RequestMapping(value = "/getRMaterialList/{RMTypeId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<RawMaterialDTO> getRawmaterialForRMType(@PathVariable("RMTypeId") long id) {

		List<RawMaterialDTO> rawmaterialList = null;
		try {
			rawmaterialList = rawmaterialService.getRMByRMTypeId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rawmaterialList;
	}
}