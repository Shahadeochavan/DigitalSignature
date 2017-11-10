package com.nextech.erp.controller;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nextech.erp.dto.StoreOutDTO;
import com.nextech.erp.factory.StoreoutRequestResponseFactory;
import com.nextech.erp.service.ProductService;
import com.nextech.erp.service.ProductionplanningService;
import com.nextech.erp.service.RawmaterialService;
import com.nextech.erp.service.RawmaterialinventoryService;
import com.nextech.erp.service.StatusService;
import com.nextech.erp.service.StoreoutService;
import com.nextech.erp.service.StoreoutrmService;
import com.nextech.erp.service.StoreoutrmassociationService;
import com.nextech.erp.status.Response;
import com.nextech.erp.status.UserStatus;

@Controller
@Transactional
@RequestMapping("/storeout")
public class StoreoutController {

	@Autowired
	StoreoutService storeoutService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ProductionplanningService productionplanningService;

	@Autowired
	ProductService productService;

	@Autowired
	StatusService statusService;

	@Autowired
	RawmaterialService rawmaterialService;

	@Autowired
	StoreoutrmService storeoutrmService;

	@Autowired
	StoreoutrmassociationService storeoutrmassociationService;

	@Autowired
	RawmaterialinventoryService rawmaterialinventoryService;
	
	static Logger logger = Logger.getLogger(StoreoutController.class);

	@RequestMapping(value = "/createStoreOut", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addStoreOutRM(@Valid @RequestBody StoreOutDTO storeOutDTO,
			BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
			}
			//TODO save call store out rm
			storeoutService.addStoreOutRM(storeOutDTO, request);
			
			return new UserStatus(1, "Storeout added Successfully !");
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
	public @ResponseBody Response getStoreout(@PathVariable("id") long id) {
		StoreOutDTO Storeout = null;
		try {
			Storeout = storeoutService.getStoreOutById(id);
			if(Storeout==null){
				 logger.error("There is no any store out rm");
				return  new Response(1,"There is no any store out rm");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return new Response(1,Storeout);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateStoreout(@RequestBody StoreOutDTO storeOutDTO, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			storeoutService.updateEntity(StoreoutRequestResponseFactory.setStoreOut(storeOutDTO, request));
			return new UserStatus(1, "Storeout update Successfully !");
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getStoreout() {
		List<StoreOutDTO> storeouts = null;
		
		try {
			 storeouts =  storeoutService.getStoreOutlist();
			 if(storeouts==null){
				 logger.error("There is no any store out list");
				 return new Response(1,"There is no any store out list");
			 }

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

		return new Response(1,storeouts);
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteStoreout(@PathVariable("id") long id) {

		try {
			StoreOutDTO storeOutDTO  = storeoutService.deleteStoreOutById(id);
			if(storeOutDTO==null){
				 logger.error("There is no any store out rm");
				return  new Response(1,"There is no any store out rm");
			}
			return new Response(1, "Storeout deleted Successfully !");
		} catch (Exception e) {
			logger.error(e);
			return new Response(0, e.toString());
		}

	}
}
