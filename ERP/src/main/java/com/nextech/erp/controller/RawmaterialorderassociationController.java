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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nextech.erp.model.Rawmaterialorderassociation;
import com.nextech.erp.service.RawmaterialService;
import com.nextech.erp.service.RawmaterialorderassociationService;
import com.nextech.erp.status.Response;
import com.nextech.erp.status.UserStatus;

@Controller
@RequestMapping("/rawmaterialorderassociation")
public class RawmaterialorderassociationController {

	@Autowired
	RawmaterialorderassociationService rawmaterialorderassociationService;

	@Autowired
	RawmaterialService rawmaterialService;

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addRawmaterialorderassociation(
			@Valid @RequestBody Rawmaterialorderassociation rawmaterialorderassociation,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			rawmaterialorderassociation.setIsactive(true);
			rawmaterialorderassociation.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			rawmaterialorderassociationService.addEntity(rawmaterialorderassociation);
			return new UserStatus(1,"Rawmaterialorderassociation added Successfully !");
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

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Rawmaterialorderassociation getRawmaterialorderassociation(
			@PathVariable("id") long id) {
		Rawmaterialorderassociation rawmaterialorderassociation = null;
		try {
			rawmaterialorderassociation = rawmaterialorderassociationService
					.getEntityById(Rawmaterialorderassociation.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rawmaterialorderassociation;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateRawmaterialorderassociation(
			@RequestBody Rawmaterialorderassociation rawmaterialorderassociation,HttpServletRequest request,HttpServletResponse response) {
		try {
			rawmaterialorderassociation.setIsactive(true);
			rawmaterialorderassociation.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			rawmaterialorderassociationService
					.updateEntity(rawmaterialorderassociation);
			return new UserStatus(1,"Rawmaterialorderassociation update Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Rawmaterialorderassociation> getRawmaterialorderassociation() {

		List<Rawmaterialorderassociation> rawmaterialorderassociationList = null;
		try {
			rawmaterialorderassociationList = rawmaterialorderassociationService
					.getEntityList(Rawmaterialorderassociation.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rawmaterialorderassociationList;
	}

	@RequestMapping(value = "getRMForRMOrder/{RMOrderId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getRawmaterialorderassociationByRMOId(
			@PathVariable("RMOrderId") long id) throws Exception {
		List<Rawmaterialorderassociation> rawmaterialorderassociations = null;
		String message = "Success";
		int code = 1;
		rawmaterialorderassociations = rawmaterialorderassociationService.getRMOrderRMAssociationByRMOrderId(id);
		if(rawmaterialorderassociations == null || rawmaterialorderassociations.size() == 0){
			 message = "Invalid RM Order Data";
			 code = 0;
			 System.out.println("There are no raw materials for the RM Order. Hence this RM Order is invalid");
		}
		Response response = new Response(code, message, rawmaterialorderassociations);
		return response;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteRawmaterialorderassociation(
			@PathVariable("id") long id) {

		try {
			Rawmaterialorderassociation rawmaterialorderassociation = rawmaterialorderassociationService
					.getEntityById(Rawmaterialorderassociation.class, id);
			rawmaterialorderassociation.setIsactive(false);
			rawmaterialorderassociationService
					.updateEntity(rawmaterialorderassociation);
			return new UserStatus(1,
					"Rawmaterialorderassociation deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}

	}
}
