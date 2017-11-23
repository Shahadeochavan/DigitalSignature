package com.nextech.dscrm.controller;

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

import com.nextech.dscrm.model.Productinventoryhistory;
import com.nextech.dscrm.service.ProductinventoryhistoryService;
import com.nextech.dscrm.status.Response;
import com.nextech.dscrm.status.UserStatus;

@Controller
@Transactional @RequestMapping("/productinventoryhistory")
public class ProductinventoryhistoryController {


	@Autowired
	ProductinventoryhistoryService productinventoryhistoryService;
	
	static Logger logger = Logger.getLogger(ProductinventoryhistoryController.class);

	@Transactional @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addProductinventoryhistory(
			@Valid @RequestBody Productinventoryhistory productinventoryhistory, BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			productinventoryhistory.setIsactive(true);
			productinventoryhistory.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			productinventoryhistoryService.addEntity(productinventoryhistory);

			return new UserStatus(1, "Productinventoryhistory added Successfully !");
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

	@Transactional @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getProductinventoryhistory(@PathVariable("id") long id) {
		Productinventoryhistory productinventoryhistory = null;
		try {
			productinventoryhistory = productinventoryhistoryService.getEntityById(Productinventoryhistory.class,id);
			if(productinventoryhistory==null){
				logger.error("There is no any product history");
				return new Response(1,"There is no any product history");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return new Response(1,productinventoryhistory);
	}

	@Transactional @RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateProductinventoryhistory(@RequestBody Productinventoryhistory productinventoryhistory,HttpServletRequest request,HttpServletResponse response) {
		try {
			productinventoryhistory.setIsactive(true);
			productinventoryhistory.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			productinventoryhistoryService.updateEntity(productinventoryhistory);
			return new UserStatus(1, "Productinventoryhistory update Successfully !");
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@Transactional @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getProductinventoryhistory() {

		List<Productinventoryhistory> productinventoryhistoryList = null;
		try {
			productinventoryhistoryList = productinventoryhistoryService.getEntityList(Productinventoryhistory.class);
			if(productinventoryhistoryList.isEmpty()){
				return new Response(1,"There is no any product invetory list");
			}

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

		return new Response(1,productinventoryhistoryList);
	}

	@Transactional @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteProductinventoryhistory(@PathVariable("id") long id) {

		try {
			Productinventoryhistory productinventoryhistory = productinventoryhistoryService.getEntityById(Productinventoryhistory.class,id);
			if(productinventoryhistory==null){
				return  new UserStatus(1,"there is no any product inventory history");
			}
			productinventoryhistory.setIsactive(false);
			productinventoryhistoryService.updateEntity(productinventoryhistory);
			return new UserStatus(1, "Productinventoryhistory deleted Successfully !");
		} catch (Exception e) {
			logger.error(e);
			return new UserStatus(0, e.toString());
		}

	}
}
