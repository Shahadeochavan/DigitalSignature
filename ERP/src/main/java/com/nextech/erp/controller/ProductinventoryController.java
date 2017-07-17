package com.nextech.erp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import com.nextech.erp.model.Notification;
import com.nextech.erp.model.Notificationuserassociation;
import com.nextech.erp.model.Product;
import com.nextech.erp.model.Productinventory;
import com.nextech.erp.model.User;
import com.nextech.erp.newDTO.NotificationDTO;
import com.nextech.erp.newDTO.NotificationUserAssociatinsDTO;
import com.nextech.erp.newDTO.UserDTO;
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
	

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addProductinventory(
			@Valid @RequestBody ProductInventoryDTO productInventoryDTO, BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			if (productinventoryService.getProductinventoryByProductId(
					productInventoryDTO.getProductId().getId()) == null){
				productinventoryService.addEntity(ProductInventoryRequestResponseFactory.setProductInventory(productInventoryDTO, request));
			}	
			else
				return new UserStatus(0, messageSource.getMessage(
						ERPConstants.PRODUCT_INVENTORY_ASSO_EXIT, null, null));
			return new UserStatus(1, "Productinventory added Successfully !");
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
	public @ResponseBody Productinventory getProductinventory(@PathVariable("id") long id) {
		Productinventory productinventory = null;
		try {
			productinventory = productinventoryService.getEntityById(Productinventory.class,id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productinventory;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateProductinventory(@RequestBody ProductInventoryDTO productInventoryDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			productinventoryService.updateEntity(ProductInventoryRequestResponseFactory.setProductInventoryUpdate(productInventoryDTO, request));
			return new UserStatus(1, "Productinventory update Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getProductinventory() {

		List<Productinventory> productinventoryList = null;
		try {
			productinventoryList = productinventoryService.getEntityList(Productinventory.class);
			if (productinventoryList.isEmpty()) {
				System.out.println("Please add product inventory");
				return new Response(1, "Product Inventory is empty",
						productinventoryList);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Response(1, productinventoryList);
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteProductinventory(@PathVariable("id") long id) {

		try {
			Productinventory productinventory = productinventoryService.getEntityById(Productinventory.class,id);
			productinventory.setIsactive(false);
			productinventoryService.updateEntity(productinventory);
			return new UserStatus(1, "Productinventory deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}

	}

	//@Scheduled(initialDelay=600000, fixedRate=600000)
	public void executeSchedular() throws Exception{
		System.out.println("Product Inventory Check");
		List<Productinventory> productinventoryList = null;
		List<ProductInventoryDTO> productInventoryDTOs = new ArrayList<ProductInventoryDTO>();
		try {
			productinventoryList = productinventoryService.getEntityList(Productinventory.class);
			for (Productinventory productinventory : productinventoryList) {
				Product product = productService.getEntityById(Product.class, productinventory.getProduct().getId());
				ProductInventoryDTO productInventoryDTO = new ProductInventoryDTO();
				if(productinventory.getQuantityavailable()>=productinventory.getMinimum_quantity()){
				}else{
					productInventoryDTO.setInventoryQuantity(productinventory.getQuantityavailable());
					productInventoryDTO.setProductPartNumber(product.getPartNumber());
					productInventoryDTO.setMinimumQuantity(productinventory.getMinimum_quantity());
					productInventoryDTOs.add(productInventoryDTO);
				}
				
			}
			if(productInventoryDTOs != null&& ! productInventoryDTOs.isEmpty()){
				System.out.println("value  of product  inventroy"+productInventoryDTOs);
				System.out.println();
				mailSendingProductInventroy(productInventoryDTOs);	
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void mailSendingProductInventroy(List<ProductInventoryDTO> productInventoryDTOs) throws Exception{
		  Mail mail = new Mail();
		  NotificationDTO  notificationDTO = notificationService.getNotificationDTOById(Long.parseLong(messageSource.getMessage(ERPConstants.PRODUCT_INVENTORY_NOTIFICATION, null, null)));
		  List<NotificationUserAssociatinsDTO> notificationUserAssociatinsDTOs = notificationUserAssociationService.getNotificationUserAssociatinsDTOs(notificationDTO.getId());
		  for (NotificationUserAssociatinsDTO notificationuserassociation : notificationUserAssociatinsDTOs) {
			  UserDTO userDTO = userService.getUserDTO(notificationuserassociation.getId());
			  if(notificationuserassociation.getTo()==true){
				  mail.setMailTo(userDTO.getEmailId()); 
			  }else if(notificationuserassociation.getBcc()==true){
				  mail.setMailBcc(userDTO.getEmailId());
			  }else if(notificationuserassociation.getCc()==true){
				  mail.setMailCc(userDTO.getEmailId());
			  }
			
		}
	        mail.setMailSubject(notificationDTO.getSubject());
	        Map < String, Object > model = new HashMap < String, Object > ();
	        model.put("firstName", "Prashant");
	        model.put("lastName", "Raskar");
	        model.put("productInventoryDTOs", productInventoryDTOs);
	        model.put("location", "Pune");
	        model.put("signature", "www.NextechServices.in");
	        mail.setModel(model);
		mailService.sendEmailWithoutPdF(mail, notificationDTO);
	}
}