package com.nextech.erp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
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

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dto.Mail;
import com.nextech.erp.dto.ProductInventoryDTO;
import com.nextech.erp.factory.ProductInventoryRequestResponseFactory;
import com.nextech.erp.newDTO.NotificationDTO;
import com.nextech.erp.newDTO.ProductDTO;
import com.nextech.erp.service.MailService;
import com.nextech.erp.service.NotificationService;
import com.nextech.erp.service.NotificationUserAssociationService;
import com.nextech.erp.service.ProductService;
import com.nextech.erp.service.ProductinventoryService;
import com.nextech.erp.service.UserService;
import com.nextech.erp.status.Response;
import com.nextech.erp.status.UserStatus;
@Controller
@Transactional
@RequestMapping("/productinventory")
public class ProductinventoryController {


	@Autowired
	ProductinventoryService productinventoryService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	NotificationService notificationService;
	
	@Autowired
	NotificationUserAssociationService notificationUserAssociationService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	ProductService productService;
	
	static Logger logger = Logger.getLogger(ProductinventoryController.class);

	@Transactional @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addProductinventory(
			@Valid @RequestBody ProductInventoryDTO productInventoryDTO, BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			if (productinventoryService.getProductinventoryByProductId(productInventoryDTO.getProductId().getId()) != null){
				return new UserStatus(0, messageSource.getMessage(ERPConstants.PRODUCT_INVENTORY_ASSO_EXIT, null, null));
			}
			productinventoryService.addEntity(ProductInventoryRequestResponseFactory.setProductInventory(productInventoryDTO, request));
			return new UserStatus(1, "Productinventory added Successfully !");
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
	public @ResponseBody Response getProductinventory(@PathVariable("id") long id) {
		ProductInventoryDTO productinventory = null;
		try {
			productinventory = productinventoryService.getProductInventory(id);
			if(productinventory==null){
				logger.error("There is no any product inventory");
				return new Response(1,"There is no any product inventory");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return new Response(1,productinventory);
	}

	@Transactional @RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateProductinventory(@RequestBody ProductInventoryDTO productInventoryDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			productinventoryService.updateEntity(ProductInventoryRequestResponseFactory.setProductInventoryUpdate(productInventoryDTO, request));
			return new UserStatus(1, "Productinventory update Successfully !");
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@Transactional @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getProductinventory() {

		List<ProductInventoryDTO> productinventoryList = null;
		try {
			productinventoryList = productinventoryService.getproductInventoryDTO();
			if (productinventoryList==null) {
				logger.error("Please add product inventory");
				return new Response(1, "Product Inventory is empty");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return new Response(1, productinventoryList);
	}

	@Transactional @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteProductinventory(@PathVariable("id") long id) {

		try {
			ProductInventoryDTO productInventoryDTO = productinventoryService.deleteProductInventory(id);
			if (productInventoryDTO==null) {
				logger.error("There is no any product Inventory");
				return new Response(1,"There is no any product Inventory");
			}
			return new Response(1, "Productinventory deleted Successfully !");
		} catch (Exception e) {
			logger.error(e);
			return new Response(0, e.toString());
		}

	}

	//@Scheduled(initialDelay=600000, fixedRate=600000)
	public void executeSchedular() throws Exception{
		logger.info("Product Inventory Check");
		List<ProductInventoryDTO> productinventoryList = null;
		List<ProductInventoryDTO> productInventoryDTOs = new ArrayList<ProductInventoryDTO>();
		ProductInventoryDTO productInventoryDTO = new ProductInventoryDTO();
		try {
			productinventoryList = productinventoryService.getproductInventoryDTO();
			for (ProductInventoryDTO productInventoryDTO1 : productinventoryList) {
				ProductDTO  product = productService.getProductDTO(productInventoryDTO1.getProductId().getId());
				if(productInventoryDTO1.getQuantityAvailable()>=productInventoryDTO1.getMinimumQuantity()){
				}else{
					productInventoryDTO.setInventoryQuantity(productInventoryDTO1.getQuantityAvailable());
					productInventoryDTO.setProductPartNumber(product.getPartNumber());
					productInventoryDTO.setMinimumQuantity(productInventoryDTO1.getMinimumQuantity());
					productInventoryDTOs.add(productInventoryDTO);
				}
			}
			if(productInventoryDTOs != null&& ! productInventoryDTOs.isEmpty()){
				emailNotifiactionProductInventory(productInventoryDTOs);	
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}
	
	public void emailNotifiactionProductInventory(List<ProductInventoryDTO> productInventoryDTOs) throws Exception{
		   NotificationDTO  notificationDTO = notificationService.getNotificationByCode((messageSource.getMessage(ERPConstants.PRODUCT_INVENTORY_NOTIFICATION, null, null)));
		   Mail mail = mailService.setMailCCBCCAndTO(notificationDTO);
	        mail.setMailSubject(notificationDTO.getSubject());
	        Map < String, Object > model = new HashMap < String, Object > ();
	        model.put("productInventoryDTOs", productInventoryDTOs);
	        model.put("location", "Pune");
	        model.put("signature", "www.NextechServices.in");
	        mail.setModel(model);
		   mailService.sendEmailWithoutPdF(mail, notificationDTO);
	}
}