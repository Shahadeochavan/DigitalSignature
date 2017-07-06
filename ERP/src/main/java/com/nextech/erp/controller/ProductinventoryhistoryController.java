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

import com.nextech.erp.model.Productinventoryhistory;
import com.nextech.erp.service.ProductinventoryhistoryService;
import com.nextech.erp.status.UserStatus;

@Controller
@RequestMapping("/productinventoryhistory")
public class ProductinventoryhistoryController {


	@Autowired
	ProductinventoryhistoryService productinventoryhistoryService;

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
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
	public @ResponseBody Productinventoryhistory getProductinventoryhistory(@PathVariable("id") long id) {
		Productinventoryhistory productinventoryhistory = null;
		try {
			productinventoryhistory = productinventoryhistoryService.getEntityById(Productinventoryhistory.class,id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productinventoryhistory;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateProductinventoryhistory(@RequestBody Productinventoryhistory productinventoryhistory,HttpServletRequest request,HttpServletResponse response) {
		try {
			productinventoryhistory.setIsactive(true);
			productinventoryhistory.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			productinventoryhistoryService.updateEntity(productinventoryhistory);
			return new UserStatus(1, "Productinventoryhistory update Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Productinventoryhistory> getProductinventoryhistory() {

		List<Productinventoryhistory> productinventoryhistoryList = null;
		try {
			productinventoryhistoryList = productinventoryhistoryService.getEntityList(Productinventoryhistory.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return productinventoryhistoryList;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteProductinventoryhistory(@PathVariable("id") long id) {

		try {
			Productinventoryhistory productinventoryhistory = productinventoryhistoryService.getEntityById(Productinventoryhistory.class,id);
			productinventoryhistory.setIsactive(false);
			productinventoryhistoryService.updateEntity(productinventoryhistory);
			return new UserStatus(1, "Productinventoryhistory deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}

	}
}
