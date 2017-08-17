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
import com.nextech.erp.dto.ProductOrderRMData;
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
	
	StringBuilder stringBuilderCC = new StringBuilder();
	StringBuilder stringBuilderTO = new StringBuilder();
	StringBuilder stringBuilderBCC = new StringBuilder();
	
	String prefixCC="";
	String prefixTO="";
	String prefixBCC="";
	
	String multipleCC="";
	String multipleBCC="";
	String multipleTO="";

	@Transactional @RequestMapping(value = "/createMultiple", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addMultipleProductOrder(
			@Valid @RequestBody ProductOrderDTO productOrderDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
			try {
				if (bindingResult.hasErrors()) {
					return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
				}
				// TODO save call product order
				ProductOrderDTO productOrderDTO2	=	productorderService.addMultipleProductOrder(productOrderDTO, request, response);

				// TODO add product order association
		        productOrderDTO.setId(productOrderDTO2.getId());
		        productOrderDTO.setInvoiceNo(productOrderDTO2.getInvoiceNo());
		        productOrderDTO.setCreatedDate(productOrderDTO2.getCreatedDate());
		        productOrderDTO.setStatusId(productOrderDTO2.getStatusId());
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
		List<ProductOrderData> productOrderDatas=productorderService.createProductorderAsso(productOrderDTO, request);
		ClientDTO client = clientService.getClientDTOById(productOrderDTO.getClientId().getId());
		downloadPDF(request, response, productOrderDTO,productOrderDatas,client);
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
	        baos = convertPDFToByteArrayOutputStream(temperotyFilePath+"\\"+fileName,productOrderDTO,productOrderDatas);
	        OutputStream os = response.getOutputStream();
	        baos.writeTo(os);
	        os.flush();
	    } catch (Exception e1) {
	        e1.printStackTrace();
	    }
	}

	private ByteArrayOutputStream convertPDFToByteArrayOutputStream(String fileName,ProductOrderDTO productOrderDTO,List<ProductOrderData> productOrderDatas) throws Exception {
		StatusDTO status = statusService.getStatusById(productOrderDTO.getStatusId().getId());
		NotificationDTO notificationDTO = notificationService.getNotifiactionByStatus(status.getId());
		ClientDTO client = clientService.getClientDTOById(productOrderDTO.getClientId().getId());
		//TODO mail sending
        mailSending(notificationDTO, productOrderDatas, client,fileName,productOrderDTO);
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

	private void mailSending(NotificationDTO notification,List<ProductOrderData> productOrderDatas,ClientDTO client,String fileName,ProductOrderDTO productOrderDTO) throws Exception{
		List<NotificationUserAssociatinsDTO> notificationUserAssociatinsDTOs  = notificationUserAssociationService.getNotificationUserAssociatinsDTOs(notification.getId());
		Mail mail = new Mail();
		for (NotificationUserAssociatinsDTO notificationuserassociation : notificationUserAssociatinsDTOs) {
			//  User user = userService.getEntityById(User.class, notificationuserassociation.getUser().getId());
			UserDTO userDTO = userService.getUserDTO(notificationuserassociation.getUserId().getId());
			if(notificationuserassociation.getTo()){
				  stringBuilderTO.append(prefixTO);
					prefixTO=",";
					stringBuilderTO.append(client.getEmailId());
					multipleTO = stringBuilderTO.toString();
					mail.setMailTo(multipleTO);
			  }else if(notificationuserassociation.getBcc()){
					stringBuilderBCC.append(prefixBCC);
					prefixBCC=",";
					stringBuilderBCC.append(userDTO.getEmailId());
					multipleBCC = stringBuilderBCC.toString();
					mail.setMailBcc(multipleBCC);
			  }else if(notificationuserassociation.getCc()){
					stringBuilderCC.append(prefixCC);
					prefixCC=",";
					stringBuilderCC.append(userDTO.getEmailId());
					multipleCC = stringBuilderCC.toString();
					mail.setMailCc(multipleCC);
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
        
        mailSendingToRMUser(productOrderDTO);
	}
	
	public void mailSendingToRMUser(ProductOrderDTO productOrderDTO)throws Exception{
		
		List<ProductOrderAssociationDTO>  productorderassociations= productorderassociationService.getProductorderassociationByOrderId(productOrderDTO.getId());
		List<ProductOrderRMData> productOrderRMDatas = new ArrayList<ProductOrderRMData>();
		for (ProductOrderAssociationDTO productorderassociation : productorderassociations) {
			ProductDTO product = productService.getProductDTO(productorderassociation.getProductId().getId());
			ProductOrderRMData productOrderRMData = new ProductOrderRMData();	
			//TODO to check rmaining quantity
			long totalRMQuantity = 0;
			if(productorderassociation.getRemainingQuantity()>0){
			List<ProductRMAssociationDTO> productRMAssociationDTOs = productRMAssoService.getProductRMAssoList(product.getId());
			for (ProductRMAssociationDTO productRMAssociationDTO : productRMAssociationDTOs) {	
			//	RawMaterialDTO  rawMaterialDTO = rawmaterialService.getRMDTO(productRMAssociationDTO.getRawmaterialId().getId());
				productOrderRMData.setProductPartNumber(productorderassociation.getProductId().getPartNumber());
				productOrderRMData.setRmPartNumber(productRMAssociationDTO.getRawmaterialId().getPartNumber());
				productOrderRMData.setProductQuantity(productorderassociation.getQuantity());
				totalRMQuantity = totalRMQuantity+productRMAssociationDTO.getQuantity();
				productOrderRMData.setRmQuantity(totalRMQuantity);
			   }
			productOrderRMDatas.add(productOrderRMData);
			}
		}
		  Mail mail = new Mail();
		  NotificationDTO  notificationDTO = notificationService.getNotifiactionByStatus(Long.parseLong(messageSource.getMessage(ERPConstants.RM_NOTIFICATION, null, null)));
		  List<NotificationUserAssociatinsDTO> notificationUserAssociatinsDTOs = notificationUserAssociationService.getNotificationUserAssociatinsDTOs(notificationDTO.getId());
		  for (NotificationUserAssociatinsDTO notificationuserassociation : notificationUserAssociatinsDTOs) {
			  UserDTO userDTO = userService.getUserDTO(notificationuserassociation.getUserId().getId());
			  if(notificationuserassociation.getTo()){
				  stringBuilderTO.append(prefixTO);
					prefixTO=",";
					stringBuilderTO.append(userDTO.getEmailId());
					multipleTO = stringBuilderTO.toString();
					mail.setMailTo(multipleTO);
			  }else if(notificationuserassociation.getBcc()){
					stringBuilderBCC.append(prefixBCC);
					prefixBCC=",";
					stringBuilderBCC.append(userDTO.getEmailId());
					multipleBCC = stringBuilderBCC.toString();
					mail.setMailBcc(multipleBCC);
			  }else if(notificationuserassociation.getCc()){
					stringBuilderCC.append(prefixCC);
					prefixCC=",";
					stringBuilderCC.append(userDTO.getEmailId());
					multipleCC = stringBuilderCC.toString();
					mail.setMailCc(multipleCC);
			  }
			
		}
	        mail.setMailSubject(notificationDTO.getSubject());
	        Map < String, Object > model = new HashMap < String, Object > ();
	        model.put("productOrderRMDatas", productOrderRMDatas);
	        model.put("location", "Pune");
	        model.put("signature", "www.NextechServices.in");
	        mail.setModel(model);
		mailService.sendEmailWithoutPdF(mail, notificationDTO);
	}
	
	
}

