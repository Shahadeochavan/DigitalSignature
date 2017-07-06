package com.nextech.erp.controller;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.model.Rawmaterialvendorassociation;
import com.nextech.erp.service.RMVAssoService;
import com.nextech.erp.status.UserStatus;

@Controller
@RequestMapping("/rmvendorasso")
public class RMVAssoController {
	@Autowired
	RMVAssoService rmvAssoService;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addRawmaterialvendorassociation(
			@Valid @RequestBody Rawmaterialvendorassociation rawmaterialvendorassociation,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			if (rmvAssoService.getRMVAssoByVendorIdRMId(
					rawmaterialvendorassociation.getVendor().getId(),
					rawmaterialvendorassociation.getRawmaterial().getId()) == null){
				rawmaterialvendorassociation.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
				rawmaterialvendorassociation.setIsactive(true);
				rmvAssoService.addEntity(rawmaterialvendorassociation);
			}	
			else
				return new UserStatus(2, messageSource.getMessage(
						ERPConstants.VENDOR_RM_ASSO_EXIT, null, null));
			return new UserStatus(1,
					"Rawmaterialvendorassociation added Successfully !");
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

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Rawmaterialvendorassociation getRawmaterialvendorassociation(
			@PathVariable("id") long id) {
		Rawmaterialvendorassociation rawmaterialvendorassociation = null;
		try {
			rawmaterialvendorassociation = rmvAssoService.getEntityById(
					Rawmaterialvendorassociation.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rawmaterialvendorassociation;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateRawmaterialvendorassociation(
			@RequestBody Rawmaterialvendorassociation rawmaterialvendorassociation,HttpServletRequest request,HttpServletResponse response) {
		try {
			rawmaterialvendorassociation.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			rawmaterialvendorassociation.setIsactive(true);
			rmvAssoService.updateEntity(rawmaterialvendorassociation);
			return new UserStatus(1,
					"Rawmaterialvendorassociation update Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Rawmaterialvendorassociation> getRawmaterialvendorassociation() {

		List<Rawmaterialvendorassociation> rawmaterialvendorassociationList = null;
		try {
			rawmaterialvendorassociationList = rmvAssoService
					.getEntityList(Rawmaterialvendorassociation.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rawmaterialvendorassociationList;
	}
	@RequestMapping(value = "rmVendorList/{rmId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Rawmaterialvendorassociation> getRMVendorList(@PathVariable("rmId") long rmId) {
		List<Rawmaterialvendorassociation> rmRawmaterialvendorassociations = null;
		try {
			rmRawmaterialvendorassociations	 = rmvAssoService.getRawmaterialvendorassociationListByRMId(rmId);
		} catch (Exception e) {
			
		}
    return rmRawmaterialvendorassociations;
	}
	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteRawmaterialvendorassociation(
			@PathVariable("id") long id) {

		try {
			Rawmaterialvendorassociation rawmaterialvendorassociation = rmvAssoService
					.getEntityById(Rawmaterialvendorassociation.class, id);
			rawmaterialvendorassociation.setIsactive(false);
			rmvAssoService.updateEntity(rawmaterialvendorassociation);
			return new UserStatus(1,
					"Rawmaterialvendorassociation deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}

	}
}
