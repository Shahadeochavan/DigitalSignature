package com.nextech.erp.controller;

import java.util.ArrayList;
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

import com.nextech.erp.dto.QualityCheckRMDTO;
import com.nextech.erp.factory.RMOrderAssociationRequestResponseFactory;
import com.nextech.erp.factory.RawMaterialInvoiceRequestResponseFactory;
import com.nextech.erp.newDTO.RMOrderAssociationDTO;
import com.nextech.erp.service.RawmaterialService;
import com.nextech.erp.service.RawmaterialorderassociationService;
import com.nextech.erp.status.Response;
import com.nextech.erp.status.UserStatus;

@Controller
@Transactional @RequestMapping("/rawmaterialorderassociation")
public class RawmaterialorderassociationController {

	@Autowired
	RawmaterialorderassociationService rawmaterialorderassociationService;

	@Autowired
	RawmaterialService rawmaterialService;
	
	static Logger logger = Logger.getLogger(RawmaterialorderassociationController.class);
	
	@Transactional @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addRawmaterialorderassociation(
			@Valid @RequestBody RMOrderAssociationDTO rmOrderAssociationDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
			}
			rawmaterialorderassociationService.addEntity(RMOrderAssociationRequestResponseFactory.setRMOrderAssocition(rmOrderAssociationDTO, request));
			return new UserStatus(1,"Rawmaterialorderassociation added Successfully !");
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
	public @ResponseBody UserStatus getRawmaterialorderassociation(
			@PathVariable("id") long id) {
		RMOrderAssociationDTO rawmaterialorderassociation = null;
		try {
			rawmaterialorderassociation = rawmaterialorderassociationService.getRMOrderAssoById(id);
			if (rawmaterialorderassociation==null) {
				logger.error("There is no rm association");
				return new UserStatus(1,"There is no rm association");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new UserStatus(1,rawmaterialorderassociation);
	}

	@Transactional @RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateRawmaterialorderassociation(
			@RequestBody RMOrderAssociationDTO rmOrderAssociationDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			rawmaterialorderassociationService.updateEntity(RMOrderAssociationRequestResponseFactory.setRMOrderAssocitionUpdate(rmOrderAssociationDTO, request));
			return new UserStatus(1,"Rawmaterialorderassociation update Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}
	
	@Transactional @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response  getRawmaterialorderassociation() {
		List<RMOrderAssociationDTO> rawmaterialorderassociationList = null;
		try {
			rawmaterialorderassociationList = rawmaterialorderassociationService.getRMOrderAssoList();
			if(rawmaterialorderassociationList==null){
				logger.error("There is no rm association list");
				return new Response(1,"There is no any rm assocition list");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,rawmaterialorderassociationList);
	}

	@Transactional @RequestMapping(value = "getRMForRMOrder/{RMOrderId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getRawmaterialorderassociationByRMOId(@PathVariable("RMOrderId") long id) throws Exception {
		List<RMOrderAssociationDTO> rawmaterialorderassociations = null;
		String message = "Success";
		int code = 1;
		List<QualityCheckRMDTO> qualityCheckRMDTO = new ArrayList<QualityCheckRMDTO>();
		rawmaterialorderassociations = rawmaterialorderassociationService.getRMOrderRMAssociationByRMOrderId(id);
		for (RMOrderAssociationDTO rawmaterialorderassociation2 : rawmaterialorderassociations) {
			qualityCheckRMDTO.add(RawMaterialInvoiceRequestResponseFactory.setRMOrderAsso(new QualityCheckRMDTO(), rawmaterialorderassociation2));
		}
		if(rawmaterialorderassociations == null || rawmaterialorderassociations.size() == 0){
			 message = "Invalid RM Order Data";
			 code = 0;
			 logger.info("There are no raw materials for the RM Order. Hence this RM Order is invalid");
		}
		Response response = new Response(code, message, qualityCheckRMDTO);
		return response;
	}

	@Transactional @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteRawmaterialorderassociation(
			@PathVariable("id") long id) {
		try {
			RMOrderAssociationDTO rawAssociationDTO = 	rawmaterialorderassociationService.deleteRMOrderAsso(id);
			if(rawAssociationDTO==null){
				logger.error("There is no rm association");
				return new Response(1,"There is no rm association");
			}
			return new Response(1,
					"Rawmaterialorderassociation deleted Successfully !");
		} catch (Exception e) {
			return new Response(0, e.toString());
		}
	}
}