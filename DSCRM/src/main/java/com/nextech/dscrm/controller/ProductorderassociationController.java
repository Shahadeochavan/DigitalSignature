package com.nextech.dscrm.controller;

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

import com.nextech.dscrm.dto.ProductInventoryDTO;
import com.nextech.dscrm.dto.ProductOrderInventoryData;
import com.nextech.dscrm.factory.ProductOrderAssoRequestResponseFactory;
import com.nextech.dscrm.newDTO.ProductDTO;
import com.nextech.dscrm.newDTO.ProductOrderAssociationDTO;
import com.nextech.dscrm.service.ProductService;
import com.nextech.dscrm.service.ProductinventoryService;
import com.nextech.dscrm.service.ProductorderassociationService;
import com.nextech.dscrm.status.Response;
import com.nextech.dscrm.status.UserStatus;

@Controller
@Transactional @RequestMapping("/productorderassociation")
public class ProductorderassociationController {

	@Autowired
	ProductorderassociationService productorderassociationService;

	@Autowired
	ProductinventoryService productinventoryService;

	@Autowired
	ProductService productService;
	
	static Logger logger = Logger.getLogger(ProductorderassociationController.class);

	@Transactional @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addProductOrderAssociation(
			@Valid @RequestBody ProductOrderAssociationDTO productOrderAssociationDTO, BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
			}
			productorderassociationService.addEntity(ProductOrderAssoRequestResponseFactory.setProductPrderAsso(productOrderAssociationDTO, request));
			return new UserStatus(1, "Productorderassociation added Successfully !");
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
	public @ResponseBody Response getProductOrderAssociation(@PathVariable("id") long id) {
		ProductOrderAssociationDTO productorderassociation = null;
		try {
			productorderassociation = productorderassociationService.getProductOrderAsoById(id);
			if (productorderassociation==null) {
				logger.error("There is no any product order association");
				return new Response(1,"There is no any product order association");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,productorderassociation);
	}

	@Transactional @RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateProductOrderAssociation(@RequestBody ProductOrderAssociationDTO productOrderAssociationDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			productorderassociationService.updateEntity(ProductOrderAssoRequestResponseFactory.setProductPrderAssoUpdate(productOrderAssociationDTO, request));
			return new UserStatus(1, "Productorderassociation update Successfully !");
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@Transactional @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getProductOrderAssociation() {
		List<ProductOrderAssociationDTO> productorderassociationList = null;
		try {
			productorderassociationList = productorderassociationService.getProductOrderAssoList();
			if(productorderassociationList==null){
				logger.error("there is no any product order assocition");
				return new Response(1,"There is no any product order assocition list");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return new Response(1,productorderassociationList);
	}
	
	@Transactional @RequestMapping(value = "getProductOrderInventoryData/{orderId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getProductorderassociationList(@PathVariable("orderId") long orderId) {
		List<ProductOrderAssociationDTO> productorderassociationList = null;
		List<ProductOrderInventoryData> productOrderInventoryList = new ArrayList<ProductOrderInventoryData>();
		try {
			productorderassociationList = productorderassociationService.getProductorderassociationByOrderId(orderId);
			if(productorderassociationList !=null){
			for(ProductOrderAssociationDTO productorderassociation : productorderassociationList){
				List<ProductInventoryDTO> productinventories = productinventoryService.getProductinventoryListByProductId(productorderassociation.getProductId().getId());
				if(productinventories !=null){
				for(ProductInventoryDTO productinventory : productinventories){
					ProductOrderInventoryData productOrderInventoryData = new ProductOrderInventoryData();
					ProductDTO product = productService.getProductDTO(productorderassociation.getProductId().getId());
					productOrderInventoryData.setProductId(productinventory.getProductId().getId());
					productOrderInventoryData.setAvailableQuantity(productinventory.getQuantityAvailable());
					productOrderInventoryData.setRemainingQuantity(productorderassociation.getRemainingQuantity());
					productOrderInventoryList.add(productOrderInventoryData);
				}
				}else{
					logger.error("there is no any product inventories");
					return new Response(1,"There is no any product inventories");
				}
			}
			}else{
				return new Response(1,"There is no any product rm assocition");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return new Response(1,"ProductorderList and ProductInventoryList",productOrderInventoryList);
	}

	@Transactional @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteProductOrderAssociation(@PathVariable("id") long id) {
		try {
			ProductOrderAssociationDTO productOrderAssociationDTO =	productorderassociationService.deleteProductOrderAsso(id);
			if(productOrderAssociationDTO==null){
			return  new Response(1,"There is no any product order association");
			}
			return new Response(1, "Productorderassociation deleted Successfully !");
		} catch (Exception e) {
			logger.error(e);
			return new Response(0, e.toString());
		}
	}
}