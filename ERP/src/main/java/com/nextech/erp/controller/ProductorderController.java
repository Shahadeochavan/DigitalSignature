package com.nextech.erp.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
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
import com.nextech.erp.dto.CreatePDF;
import com.nextech.erp.dto.Mail;
import com.nextech.erp.dto.ProductOrderDTO;
import com.nextech.erp.dto.ProductOrderData;
import com.nextech.erp.dto.ProductRMAssociationDTO;
import com.nextech.erp.dto.RMInventoryDTO;
import com.nextech.erp.factory.ProductOrderRequestResponseFactory;
import com.nextech.erp.model.Productrawmaterialassociation;
import com.nextech.erp.newDTO.ClientDTO;
import com.nextech.erp.newDTO.NotificationDTO;
import com.nextech.erp.newDTO.NotificationUserAssociatinsDTO;
import com.nextech.erp.newDTO.ProductDTO;
import com.nextech.erp.newDTO.ProductOrderAssociationDTO;
import com.nextech.erp.newDTO.RawMaterialDTO;
import com.nextech.erp.newDTO.StatusDTO;
import com.nextech.erp.newDTO.UserDTO;
import com.nextech.erp.service.ClientService;
import com.nextech.erp.service.MailService;
import com.nextech.erp.service.NotificationService;
import com.nextech.erp.service.NotificationUserAssociationService;
import com.nextech.erp.service.ProductRMAssoService;
import com.nextech.erp.service.ProductService;
import com.nextech.erp.service.ProductorderService;
import com.nextech.erp.service.ProductorderassociationService;
import com.nextech.erp.service.RawmaterialService;
import com.nextech.erp.service.RawmaterialinventoryService;
import com.nextech.erp.service.StatusService;
import com.nextech.erp.service.UserService;
import com.nextech.erp.status.UserStatus;

@Controller
@Transactional @RequestMapping("/productorder")
public class ProductorderController {

	@Autowired
	ProductorderService productorderService;

	@Autowired
	ProductorderassociationService productorderassociationService;

	@Autowired
	ClientService clientService;

	@Autowired
	StatusService statusService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	NotificationService notificationService;
	
	@Autowired
	NotificationUserAssociationService notificationUserAssociationService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	UserService userService;

	@Autowired
	MailService mailService;
	
	@Autowired
	ProductRMAssoService productRMAssoService;
	
	@Autowired
	RawmaterialService rawmaterialService;
	
	@Autowired
	RawmaterialinventoryService rawMaterialInventoryService;

	@Transactional @RequestMapping(value = "/createmultiple", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus createMultiple(
			@Valid @RequestBody ProductOrderDTO productOrderDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
			try {
				if (bindingResult.hasErrors()) {
					return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
				}
				// TODO save call product order
				ProductOrderDTO productOrderDTO2	=	productorderService.createMultiple(productOrderDTO, request, response);

				// TODO add product order association
		        productOrderDTO.setId(productOrderDTO2.getId());
		        productOrderDTO.setInvoiceNo(productOrderDTO2.getInvoiceNo());
		        productOrderDTO.setCreatedDate(productOrderDTO2.getCreatedDate());
				addProductOrderAsso(productOrderDTO,request, response);
				
				//TODO Check Inventory for Products
				checkInventoryStatus(productOrderDTO);
			return new UserStatus(1,
					"Multiple Product Order added Successfully !");
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
	
	public void checkInventoryStatus(ProductOrderDTO productOrderDTO) throws Exception{
		HashMap<Long,Long> rawMaterialQtyMap = new HashMap<Long, Long>();
		List<Long> rmIds = new ArrayList<Long>();
		List<ProductOrderAssociationDTO> productOrderAssociationDTOs = productOrderDTO.getProductOrderAssociationDTOs();
		for(ProductOrderAssociationDTO productOrderAssociationDTO : productOrderAssociationDTOs){
			List<ProductRMAssociationDTO> productrawmaterialassociations = productRMAssoService.getProductRMAssoListByProductId(productOrderAssociationDTO.getProductId().getId());
			for(ProductRMAssociationDTO productrawmaterialassociation : productrawmaterialassociations){
				long requiredQuantity = productrawmaterialassociation.getQuantity() * productOrderAssociationDTO.getQuantity();
				if(rawMaterialQtyMap.containsKey(productrawmaterialassociation.getRawmaterialId())){
					long existingQuantity = rawMaterialQtyMap.get(productrawmaterialassociation.getRawmaterialId());
					rawMaterialQtyMap.put(productrawmaterialassociation.getRawmaterialId().getId(), existingQuantity + requiredQuantity);
				}else{
					rawMaterialQtyMap.put(productrawmaterialassociation.getRawmaterialId().getId(), requiredQuantity);
				}
				if(!rmIds.contains(productrawmaterialassociation.getRawmaterialId().getId())){
					rmIds.add(productrawmaterialassociation.getRawmaterialId().getId());
				}
			}
		}
		for(Long rmId : rmIds){
			RMInventoryDTO rmInventory = rawMaterialInventoryService.getByRMId(rmId);
			if(rmInventory != null){
				long inventoryQuantity = rmInventory.getQuantityAvailable();
				System.out.println(rmId + " Quantity Required : " + rawMaterialQtyMap.get(rmId) + " Inventory Quantity : " + inventoryQuantity);
			}else{
				System.out.println("RM Inventory is not added for RM Id : " +  rmId);
			}
		}
	}

	@Transactional @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody ProductOrderDTO getProductorder(@PathVariable("id") long id) {
		ProductOrderDTO productorder = null;
		try {
			productorder = productorderService.getProductById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productorder;
	}

	@Transactional @RequestMapping(value = "productorderId/{orderId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<ProductOrderAssociationDTO> getProductOrder(
			@PathVariable("orderId") long id) {
		List<ProductOrderAssociationDTO> productorderassociations = null;
		try {
			productorderassociations = productorderassociationService.getProductorderassociationByOrderId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productorderassociations;
	}

	@Transactional @RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateProductorder(
			@RequestBody ProductOrderDTO productOrderDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			productorderService.updateMultiple(productOrderDTO, request, response);
			//productorderService.updateEntity(ProductOrderRequestResponseFactory.setProductOrder(productOrderDTO));
			return new UserStatus(1, "Product Order update Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@Transactional @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<ProductOrderDTO> getProductorder() {
		List<ProductOrderDTO> productorderList = null;
		try {
			productorderList = productorderService.getProductOrderList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return productorderList;
	}

	@Transactional @RequestMapping(value = "/pendingList", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<ProductOrderDTO> getPendingsProductorders() {
		List<ProductOrderDTO> productorderList = null;
		try {
			// TODO afterwards you need to change it from properties.
			productorderList = productorderService.getPendingProductOrders(Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_NEW_PRODUCT_ORDER, null, null)),
					Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_PRODUCT_ORDER_INCOMPLETE, null, null)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productorderList;
	}

	@Transactional @RequestMapping(value = "incompleteProductOrder/{CLIENT-ID}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<ProductOrderDTO> getInCompleteProductOrder(@PathVariable("CLIENT-ID") long clientId) {
		List<ProductOrderDTO> productorderList = null;
		try {
			// TODO afterwards you need to change it from properties.
			productorderList = productorderService.getInCompleteProductOrder(clientId,Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_PRODUCT_ORDER_INCOMPLETE, null, null)),
			Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_PRODUCT_ORDER_COMPLETE, null, null)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productorderList;
	}

	@Transactional @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteProductorder(
			@PathVariable("id") long id) {
		try {
			productorderService.deleteProductOrder(id);
			return new UserStatus(1, "Product Order deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}
	}

	private void addProductOrderAsso(ProductOrderDTO productOrderDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		productorderService.createProductorderAsso(productOrderDTO, request);
		//downloadPDF(request, response, productOrderDTO,productOrderDatas,client);
	}
	
	public void downloadPDF(HttpServletRequest request, HttpServletResponse response,ProductOrderDTO productOrderDTO,List<ProductOrderData> productOrderDatas,ClientDTO client) throws IOException {
		final ServletContext servletContext = request.getSession().getServletContext();
	    final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
	    final String temperotyFilePath = tempDirectory.getAbsolutePath();
	    String fileName = "ProductOrder.pdf";
	    response.setContentType("application/pdf");
	    response.setHeader("Content-disposition", "attachment; filename="+ fileName);
	    try {
	    	CreatePDF ceCreatePDFProductOrder = new CreatePDF();
	    	ceCreatePDFProductOrder.createPDF(temperotyFilePath+"\\"+fileName,productOrderDTO,productOrderDatas,client);
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        baos = convertPDFToByteArrayOutputStream(temperotyFilePath+"\\"+fileName,productOrderDTO);
	        OutputStream os = response.getOutputStream();
	        baos.writeTo(os);
	        os.flush();
	    } catch (Exception e1) {
	        e1.printStackTrace();
	    }
	}

	private ByteArrayOutputStream convertPDFToByteArrayOutputStream(String fileName,ProductOrderDTO productOrderDTO) throws Exception {
		StatusDTO status = statusService.getStatusById(productOrderDTO.getStatusId().getId());
		NotificationDTO notificationDTO = notificationService.getNotificationDTOById(status.getId());
		ClientDTO client = clientService.getClientDTOById(productOrderDTO.getClientId().getId());
		//TODO mail sending
        mailSending(notificationDTO, productOrderDTO, client,fileName);
		InputStream inputStream = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			inputStream = new FileInputStream(fileName);
			byte[] buffer = new byte[1024];
			baos = new ByteArrayOutputStream();
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return baos;
	}

	private void mailSending(NotificationDTO notification,ProductOrderDTO productOrderDTO,ClientDTO client,String fileName) throws Exception{
		long totalRMQuantity = 0;
		List<ProductOrderAssociationDTO>  productorderassociations= productorderassociationService.getProductorderassociationByOrderId(productOrderDTO.getId());
		List<ProductOrderData> productOrderDatas = new ArrayList<ProductOrderData>();
		List<NotificationUserAssociatinsDTO> notificationUserAssociatinsDTOs  = notificationUserAssociationService.getNotificationUserAssociatinsDTOs(notification.getId());
		for (ProductOrderAssociationDTO productorderassociation : productorderassociations) {
			ProductDTO product = productService.getProductDTO(productorderassociation.getProductId().getId());
			ProductOrderData productOrderData = new ProductOrderData();
			//TODO to check rmaining quantity
			if(productorderassociation.getRemainingQuantity()>0){
			List<ProductRMAssociationDTO> productRMAssociationDTOs = productRMAssoService.getProductRMAssoList(product.getId());
			for (ProductRMAssociationDTO productRMAssociationDTO : productRMAssociationDTOs) {	
				RawMaterialDTO  rawMaterialDTO = rawmaterialService.getRMDTO(productRMAssociationDTO.getRawmaterialId().getId());
			         productOrderData.setPartNumber(rawMaterialDTO.getPartNumber());
		        	  productOrderData.setRmQuantity(productRMAssociationDTO.getQuantity());
			          productOrderData.setProductName(product.getName());
			          productOrderData.setQuantity(productorderassociation.getQuantity());
			          productOrderData.setRate(product.getRatePerUnit());
			          productOrderDatas.add(productOrderData);
				  }
			}
		}
		Mail mail = new Mail();
		for (NotificationUserAssociatinsDTO notificationuserassociation : notificationUserAssociatinsDTOs) {
			//  User user = userService.getEntityById(User.class, notificationuserassociation.getUser().getId());
			UserDTO userDTO = userService.getUserDTO(notificationuserassociation.getId());
			if(notificationuserassociation.getTo()==true){
				mail.setMailTo(client.getEmailId()); 
			}else if(notificationuserassociation.getBcc()==true){
				mail.setMailBcc(userDTO.getEmailId());
			}else if(notificationuserassociation.getCc()==true){
				mail.setMailCc(userDTO.getEmailId());
			}
			mail.setMailSubject(notification.getSubject());
			mail.setAttachment(fileName);
		}     
        Map < String, Object > model = new HashMap < String, Object >();
        model.put("companyName", client.getCompanyName());
        model.put("mailfrom", notification.getName());
        model.put("location", "Pune");
        model.put("productOrderDatas",productOrderDatas);
        model.put("invoiceNumber",productOrderDTO.getInvoiceNo());
        model.put("date",productOrderDTO.getCreatedDate());
        model.put("address", client.getAddress());
        model.put("signature", "www.NextechServices.in");
        mail.setModel(model);
        mailService.sendEmail(mail,notification);
	}
	
	public void mailSendingToRMUser(List<ProductOrderData> productOrderDatas)throws Exception{
		  Mail mail = new Mail();
		  NotificationDTO  notificationDTO = notificationService.getNotificationDTOById(Long.parseLong(messageSource.getMessage(ERPConstants.VENDOR_UPDATE_SUCCESSFULLY, null, null)));
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
	        model.put("productOrderDatas", productOrderDatas);
	        model.put("location", "Pune");
	        model.put("signature", "www.NextechServices.in");
	        mail.setModel(model);
		mailService.sendEmailWithoutPdF(mail, notificationDTO);
	}
	
	
}

