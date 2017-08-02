package com.nextech.erp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.fileupload.disk.DiskFileItem;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.constants.UploadImageConstants;
import com.nextech.erp.dto.FileInfo;
import com.nextech.erp.dto.Mail;
import com.nextech.erp.dto.ProductNewAssoicatedList;
import com.nextech.erp.dto.ProductRMAssociationDTO;
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
import com.nextech.erp.util.MockMultipartFile;

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

	@Transactional @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult,HttpServletRequest request) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			if (productService.getProductByName(productDTO.getName()) == null) {

			} else {
				return new UserStatus(0, messageSource.getMessage(ERPConstants.PRODUCT_NAME, null, null));
			}
			if (productService.getProductByPartNumber(productDTO.getPartNumber()) == null) {
			} else {
				return new UserStatus(0, messageSource.getMessage(ERPConstants.PART_NUMBER, null, null));
			}
			File file = new File(productDTO.getDesign());
			
			DiskFileItem fileItem = new DiskFileItem(productDTO.getDesign(), "image/png", false, file.getName(), (int) file.length(), file.getParentFile());
			
            fileItem.getOutputStream();
            
            FileInputStream input = new FileInputStream(productDTO.getDesign());
            
            MockMultipartFile multipartFile = new MockMultipartFile("fileItem", fileItem.getName(), "image/png", IOUtils.toByteArray(input));
            
            String destinationPath =   upload(multipartFile);
			Product product = ProductRequestResponseFactory.setProduct(productDTO, request);
			product.setDesign(destinationPath);
		    long id =	productService.addEntity(product);
	    	productDTO.setId(id);
			addProductInventory(productDTO, Long.parseLong(request.getAttribute("current_user").toString()));
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
	public @ResponseBody ProductDTO getProduct(@PathVariable("id") long id) {
		ProductDTO product = null;
		try {
			product = productService.getProductDTO(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return product;
	}

	@Transactional @RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateProduct(@RequestBody ProductDTO productDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			ProductDTO oldProductInfo = productService.getProductDTO(productDTO.getId());
			if(productDTO.getName().equals(oldProductInfo.getName())){ 	
				} else { 
					if (productService.getProductByName(productDTO.getName()) == null) {
				    }else{  
				    	return new UserStatus(0, messageSource.getMessage(ERPConstants.PRODUCT_NAME, null, null));
					}
				 }
	            if(productDTO.getPartNumber().equals(oldProductInfo.getPartNumber())){  			
				} else { if (productService.getProductByPartNumber(productDTO.getPartNumber()) == null) {
				    }else{  
				    	return new UserStatus(0, messageSource.getMessage(ERPConstants.PART_NUMBER, null, null));
					}
				 }
			productService.updateEntity(ProductRequestResponseFactory.setProductUpdate(productDTO, request));
			mailSendingUpdate();
			return new UserStatus(1, "Product update Successfully !");
		} catch (Exception e) {
			 e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@Transactional @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<ProductDTO> getProduct() {

		List<ProductDTO> ProductList = null;
		try {
			ProductList = productService.getProductList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ProductList;
	}
	@Transactional @RequestMapping(value = "/list/newProductRMAssociation", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getNewProductRMAsso() {

		List<Product> productList = null;
		List<ProductNewAssoicatedList> productNewAssoicatedLists = new ArrayList<ProductNewAssoicatedList>();
		try {
			productList = productService.getEntityList(Product.class);
			for(Product product : productList){
				List<Productrawmaterialassociation> productrawmaterialassociations = productRMAssoService.getProductRMAssoListByProductID(product.getId());
				if(productrawmaterialassociations==null){
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
	public @ResponseBody UserStatus deleteProduct(@PathVariable("id") long id) {

		try {
			productService.deleteProduct(id);
			return new UserStatus(1, "Product deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}

	}
	
	@Transactional @RequestMapping(value = "DOWNLOAD-PRODUCT-IMAGE/{PRODUCT-ID}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody UserStatus getProductByproductId(@PathVariable("PRODUCT-ID") long productId,HttpServletRequest request) {
		  InputStream inputStream = null;  
		  OutputStream outputStream = null;  
		try {
			Product product = productService.getProductByProductId(productId);
			 
			         System.out.println("File downloaded at client successfully");

			return new UserStatus(1, "Product Image download Successfully !",outputStream);
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}
	}

	private void addProductInventory(ProductDTO productDTO,long userId) throws Exception{
		productinventoryService.addEntity(ProductInventoryRequestResponseFactory.setProductIn(productDTO));
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
	public String upload(MultipartFile inputFile) {
				FileInfo fileInfo = new FileInfo();
				String destinationPath ="";
				HttpHeaders headers = new HttpHeaders();
					try {
						String originalFilename = inputFile.getOriginalFilename();
						String fileName = UploadImageConstants.UPLOAD_IMAGE_PATH;
						File destinationFile = new File((fileName)+ File.separator + originalFilename);
						inputFile.transferTo(destinationFile);
						fileInfo.setFileName(destinationFile.getPath());
						fileInfo.setFileSize(inputFile.getSize());
						headers.add("File Uploaded Successfully - ", originalFilename);
						destinationPath = String.valueOf(destinationFile);
					} catch (Exception e) {
						e.printStackTrace();
					}
	         return destinationPath;
	}
}
