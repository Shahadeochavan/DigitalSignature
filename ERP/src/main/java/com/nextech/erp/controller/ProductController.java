package com.nextech.erp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dto.Mail;
import com.nextech.erp.dto.ProductNewAssoicatedList;
import com.nextech.erp.factory.ProductInventoryRequestResponseFactory;
import com.nextech.erp.factory.ProductRequestResponseFactory;
import com.nextech.erp.model.Product;
import com.nextech.erp.model.Productrawmaterialassociation;
import com.nextech.erp.newDTO.NotificationDTO;
import com.nextech.erp.newDTO.NotificationUserAssociatinsDTO;
import com.nextech.erp.newDTO.ProductDTO;
import com.nextech.erp.newDTO.UserDTO;
import com.nextech.erp.service.MailService;
import com.nextech.erp.service.NotificationService;
import com.nextech.erp.service.NotificationUserAssociationService;
import com.nextech.erp.service.ProductRMAssoService;
import com.nextech.erp.service.ProductService;
import com.nextech.erp.service.ProductinventoryService;
import com.nextech.erp.service.UserService;
import com.nextech.erp.service.VendorService;
import com.nextech.erp.status.Response;
import com.nextech.erp.status.UserStatus;
import com.nextech.erp.util.ImageUploadUtil;

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
	ProductRMAssoService productRMAssoService;
	
	
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
	NotificationUserAssociationService notificationUserAssociationService;
	
	
	@Transactional @RequestMapping(value = "/create",headers = "Content-Type=*/*", method = RequestMethod.POST)
	public @ResponseBody UserStatus addProduct(
			HttpServletRequest request,@RequestParam("file") MultipartFile inputFile,
			@RequestParam("clientPartNumber") String clientPartNumber,
			@RequestParam("name") String name,@RequestParam("description")String description,
			@RequestParam("partNumber") String partNumber) {
		try {
			if (productService.getProductByName(name) == null) {

			} else {
				return new UserStatus(0, messageSource.getMessage(ERPConstants.PRODUCT_NAME, null, null));
			}
			if (productService.getProductByPartNumber(partNumber) == null) {
			} else {
				return new UserStatus(0, messageSource.getMessage(ERPConstants.PART_NUMBER, null, null));
			}
			String destinationFilePath = ImageUploadUtil.imgaeUpload(inputFile);
			  ProductDTO productDTO =  new ProductDTO();
			   productDTO.setClientPartNumber(clientPartNumber);
			   productDTO.setName(name);
			   productDTO.setPartNumber(partNumber);
			   productDTO.setDescription(description);
			Product product = ProductRequestResponseFactory.setProduct(productDTO, request);
			 product.setDesign(destinationFilePath);
		    long id =	productService.addEntity(product);
	    	productDTO.setId(id); 
			addProductInventory(productDTO,Long.parseLong(request.getAttribute("current_user").toString()));
			return new UserStatus(1, "product added Successfully !");
		} catch (ConstraintViolationException cve) {
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

	@Transactional @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody UserStatus getProduct(@PathVariable("id") long id) {
		ProductDTO product = null;
		try {
			product = productService.getProductDTO(id);
			if(product==null){
				return new UserStatus(1,"There is no any product");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new UserStatus(1,product);
	}
	
	@Transactional @RequestMapping(value = "/update",headers = "Content-Type=*/*", method = RequestMethod.POST)
	public @ResponseBody UserStatus updateProduct(
			HttpServletRequest request,@RequestParam("file") MultipartFile inputFile,
			@RequestParam("clientPartNumber") String clientPartNumber,	@RequestParam("id") long id,
			@RequestParam("name") String name,@RequestParam("description")String description,
			@RequestParam("partNumber") String partNumber) {
		try {
			ProductDTO oldProductInfo = productService.getProductDTO(id);
			if(name.equals(oldProductInfo.getName())){ 	
				} else { 
					if (productService.getProductByName(name) == null) {
				    }else{  
				    	return new UserStatus(0, messageSource.getMessage(ERPConstants.PRODUCT_NAME, null, null));
					}
				 }
	            if(partNumber.equals(oldProductInfo.getPartNumber())){  			
				} else { if (productService.getProductByPartNumber(partNumber) == null) {
				    }else{  
				    	return new UserStatus(0, messageSource.getMessage(ERPConstants.PART_NUMBER, null, null));
					}
				 }
			String destinationFilePath = ImageUploadUtil.imgaeUpload(inputFile);
			  ProductDTO productDTO =  new ProductDTO();
			   productDTO.setClientPartNumber(clientPartNumber);
			   productDTO.setName(name);
			   productDTO.setId(id);
			   productDTO.setPartNumber(partNumber);
			   productDTO.setDescription(description);
			   productDTO.setDesign(destinationFilePath);
				productService.updateEntity(ProductRequestResponseFactory.setProductUpdate(productDTO, request));
				mailSendingUpdate();
			return new UserStatus(1, "product added Successfully !");
		} catch (ConstraintViolationException cve) {
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
	
	@Transactional @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getProduct() {

		List<ProductDTO> productList = null;
		try {
			productList = productService.getProductList();
			if(productList==null){
				return new Response(1,"There is no product list");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Response(1,productList);
	}
	@Transactional @RequestMapping(value = "/list/newProductRMAssociation", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getNewProductRMAsso() {

		List<Product> productList = null;
		List<ProductNewAssoicatedList> productNewAssoicatedLists = new ArrayList<ProductNewAssoicatedList>();
		try {
			productList = productService.getEntityList(Product.class);
			for(Product product : productList){
				List<Productrawmaterialassociation> productrawmaterialassociations = productRMAssoService.getProductRMAssoListByProductID(product.getId());
				if(productrawmaterialassociations.isEmpty()){
					ProductNewAssoicatedList productNewAssoicatedList = new ProductNewAssoicatedList();
					productNewAssoicatedList.setId(product.getId());
					productNewAssoicatedList.setPartNumber(product.getPartNumber());
					productNewAssoicatedLists.add(productNewAssoicatedList);
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Response(1,"New Productrawmaterialassociation List",productNewAssoicatedLists);
	}

	@Transactional @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteProduct(@PathVariable("id") long id) {

		try {
			ProductDTO productDTO =productService.deleteProduct(id);
			if(productDTO==null){
				return new Response(1,"There is no product id");
			}
			return new Response(1, "Product deleted Successfully !");
		} catch (Exception e) {
			return new Response(0, e.toString());
		}

	}
	
	@Transactional @RequestMapping(value = "/image/{PRODUCT-ID}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<byte[]> getProductByproductId(@PathVariable("PRODUCT-ID") long productId,HttpServletRequest request) {
	
		try {
			Product product = productService.getProductByProductId(productId);
			String FILE_PATH = product.getDesign();
			
			InputStream in = request.getServletContext().getResourceAsStream(FILE_PATH);
			in = new FileInputStream(new File(FILE_PATH));
		    final HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.IMAGE_PNG);
		    return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void addProductInventory(ProductDTO productDTO,long userId) throws Exception{
		productinventoryService.addEntity(ProductInventoryRequestResponseFactory.setProductIn(productDTO, userId));
	}
	
	private void mailSendingUpdate() throws Exception{
		  Mail mail = new Mail();
		  NotificationDTO  notificationDTO = notificationService.getNotificationDTOById(Long.parseLong(messageSource.getMessage(ERPConstants.PRODUCT_MODIFICATION, null, null)));
		  List<NotificationUserAssociatinsDTO> notificationUserAssociatinsDTOs = notificationUserAssociationService.getNotificationUserAssociatinsDTOs(notificationDTO.getId());
		  for (NotificationUserAssociatinsDTO notificationuserassociation : notificationUserAssociatinsDTOs) {
			  UserDTO userDTO = userService.getUserDTO(notificationuserassociation.getUserId().getId());
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
	        model.put("companyName","EK ELECTRONICS");
	        model.put("location", "Pune");
	        model.put("signature", "www.NextechServices.in");
	        mail.setModel(model);
		mailService.sendEmailWithoutPdF(mail, notificationDTO);
	}
	
}
