package com.nextech.dscrm.controller;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
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

import com.nextech.dscrm.factory.ProductInventoryRequestResponseFactory;
import com.nextech.dscrm.factory.ProductRequestResponseFactory;
import com.nextech.dscrm.newDTO.ProductDTO;
import com.nextech.dscrm.service.MailService;
import com.nextech.dscrm.service.NotificationService;
import com.nextech.dscrm.service.NotificationUserAssociationService;
import com.nextech.dscrm.service.ProductService;
import com.nextech.dscrm.service.ProductinventoryService;
import com.nextech.dscrm.service.TaxstructureService;
import com.nextech.dscrm.service.UserService;
import com.nextech.dscrm.service.VendorService;
import com.nextech.dscrm.status.Response;
import com.nextech.dscrm.status.UserStatus;

@Controller
@Transactional @RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ProductinventoryService productinventoryService;

	
	@Autowired
	NotificationService notificationService;

	@Autowired
	VendorService vendorService;

	@Autowired
	MailService mailService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ServletContext context;
	
	@Autowired
	TaxstructureService taxstructureService;
	
	@Autowired 
	NotificationUserAssociationService notificationUserAssociationService;
	
	static Logger logger = Logger.getLogger(PageController.class);
	
	
	@Transactional @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addPage(@Valid @RequestBody ProductDTO productDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
		long id =	productService.addEntity(ProductRequestResponseFactory.setProduct(productDTO, request));
			productDTO.setId(id);
			addProductInventory(productDTO, Long.parseLong(request.getAttribute("current_user").toString()));
			return new UserStatus(1, "Product added Successfully !");
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
	public @ResponseBody Response getPage(@PathVariable("id") long id) {
		ProductDTO productDTO = null;
		try {
			productDTO = productService.getProductDTO(id);
			if(productDTO==null){
				logger.error("There is no any product");
				return new Response(1,"There is no any page");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return new Response(1,productDTO);
	}

	@Transactional @RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updatePage(@RequestBody ProductDTO productDTO,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			productService.updateEntity(ProductRequestResponseFactory.setProductUpdate(productDTO, request));
			return new UserStatus(1, "Product update Successfully !");
		} catch (Exception e) {
			logger.error(e);
			return new UserStatus(0, e.toString());
		}
	}
	
	@Transactional @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getProductList() {

		List<ProductDTO> productList = null;
		try {
			productList = productService.getProductList();
			if(productList==null){
				logger.error("There is no product list");
				return new Response(1,"There is no product list");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return new Response(1,productList);
	}

	@Transactional @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteProduct(@PathVariable("id") long id) {

		try {
			ProductDTO productDTO =productService.deleteProduct(id);
			if(productDTO==null){
				logger.error("There is no product for delete");
				return new Response(1,"There is no product id");
			}
			return new Response(1, "Product deleted Successfully !");
		} catch (Exception e) {
			logger.error(e);
			return new Response(0, e.toString());
		}

	}

	private void addProductInventory(ProductDTO productDTO,long userId) throws Exception{
		productinventoryService.addEntity(ProductInventoryRequestResponseFactory.setProductIn(productDTO, userId));
	}
}
