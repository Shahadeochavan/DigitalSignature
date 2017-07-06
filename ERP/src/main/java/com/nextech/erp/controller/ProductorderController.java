package com.nextech.erp.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dto.CreatePDF;
import com.nextech.erp.dto.Mail;
import com.nextech.erp.dto.ProductOrderAssociationModel;
import com.nextech.erp.dto.ProductOrderData;
import com.nextech.erp.dto.ProductRMAssociationModel;
import com.nextech.erp.model.Client;
import com.nextech.erp.model.Notification;
import com.nextech.erp.model.Notificationuserassociation;
import com.nextech.erp.model.Product;
import com.nextech.erp.model.Productorder;
import com.nextech.erp.model.Productorderassociation;
import com.nextech.erp.model.Productrawmaterialassociation;
import com.nextech.erp.model.Rawmaterialinventory;
import com.nextech.erp.model.Status;
import com.nextech.erp.model.User;
import com.nextech.erp.service.ClientService;
import com.nextech.erp.service.MailService;
import com.nextech.erp.service.NotificationService;
import com.nextech.erp.service.NotificationUserAssociationService;
import com.nextech.erp.service.ProductRMAssoService;
import com.nextech.erp.service.ProductService;
import com.nextech.erp.service.ProductorderService;
import com.nextech.erp.service.ProductorderassociationService;
import com.nextech.erp.service.RawmaterialinventoryService;
import com.nextech.erp.service.StatusService;
import com.nextech.erp.service.UserService;
import com.nextech.erp.status.UserStatus;

@Controller
@RequestMapping("/productorder")
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
	RawmaterialinventoryService rawMaterialInventoryService;

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addProductorder(
			@Valid @RequestBody Productorder productorder,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			// TODO afterwards you need to change it from properties.
			productorder.setStatus(statusService.getEntityById(Status.class, 14));
			productorder.setIsactive(true);
			productorder.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			productorderService.addEntity(productorder);
			return new UserStatus(1, "Product Order added Successfully !");
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

	@RequestMapping(value = "/createmultiple", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addMultipleProductorder(
			@Valid @RequestBody ProductOrderAssociationModel productOrderAssociationModel,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}

			// TODO save call product order
			Productorder productorder = saveProductOrder(productOrderAssociationModel, request, response);

			// TODO add product order association
			addProductOrderAsso(productOrderAssociationModel, productorder, request, response);
			
			//TODO Check Inventory for Products
			
			checkInventoryStatus(productOrderAssociationModel);

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
	
	public void checkInventoryStatus(ProductOrderAssociationModel productOrderAssociationModel) throws Exception{
		HashMap<Long,Long> rawMaterialQtyMap = new HashMap<Long, Long>();
		List<Long> rmIds = new ArrayList<Long>();
		List<Productorderassociation> productorderassociations = productOrderAssociationModel.getOrderproductassociations();
		
		for(Productorderassociation productorderassociation : productorderassociations){
			List<Productrawmaterialassociation> productrawmaterialassociations = productRMAssoService.getProductRMAssoListByProductId(productorderassociation.getProduct().getId());
			for(Productrawmaterialassociation productrawmaterialassociation : productrawmaterialassociations){
				long requiredQuantity = productrawmaterialassociation.getQuantity() * productorderassociation.getQuantity();
					if(rawMaterialQtyMap.containsKey(productrawmaterialassociation.getRawmaterial())){
						long existingQuantity = rawMaterialQtyMap.get(productrawmaterialassociation.getRawmaterial());
						rawMaterialQtyMap.put(productrawmaterialassociation.getRawmaterial().getId(), existingQuantity + requiredQuantity);
					}else{
						rawMaterialQtyMap.put(productrawmaterialassociation.getRawmaterial().getId(), requiredQuantity);
					}
					if(!rmIds.contains(productrawmaterialassociation.getRawmaterial().getId())){
						rmIds.add(productrawmaterialassociation.getRawmaterial().getId());
					}
			}
		}
		
		for(Long rmId : rmIds){
			Rawmaterialinventory rmInventory = rawMaterialInventoryService.getByRMId(rmId);
			if(rmInventory != null){
				long inventoryQuantity = rmInventory.getQuantityAvailable();
				System.out.println(rmId + " Quantity Required : " + rawMaterialQtyMap.get(rmId) + " Inventory Quantity : " + inventoryQuantity);
			}else{
				System.out.println("RM Inventory is not added for RM Id : " +  rmId);
			}
			
		}
		
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Productorder getProductorder(
			@PathVariable("id") long id) {
		Productorder productorder = null;
		try {
			productorder = productorderService.getEntityById(
					Productorder.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productorder;
	}

	@RequestMapping(value = "productorderId/{orderId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Productorderassociation> getProductOrder(
			@PathVariable("orderId") long id) {
		List<Productorderassociation> productorderassociations = null;
		try {
			productorderassociations = productorderassociationService.getProductorderassociationByOrderId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productorderassociations;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateProductorder(
			@RequestBody Productorder productorder,HttpServletRequest request,HttpServletResponse response) {
		try {
			productorder.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			productorder.setIsactive(true);
			productorder.setStatus(statusService.getEntityById(Status.class,
					Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_NEW_PRODUCT_ORDER, null, null))));
			productorderService.updateEntity(productorder);
			return new UserStatus(1, "Product Order update Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Productorder> getProductorder() {

		List<Productorder> productorderList = null;
		try {
			productorderList = productorderService
					.getEntityList(Productorder.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return productorderList;
	}

	@RequestMapping(value = "/pendingList", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Productorder> getPendingsProductorders() {

		List<Productorder> productorderList = null;
		try {
			// TODO afterwards you need to change it from properties.
			productorderList = productorderService.getPendingProductOrders(Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_NEW_PRODUCT_ORDER, null, null)),
					Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_PRODUCT_ORDER_INCOMPLETE, null, null)));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return productorderList;
	}


	@RequestMapping(value = "incompleteProductOrder/{CLIENT-ID}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Productorder> getInCompleteProductOrder(@PathVariable("CLIENT-ID") long clientId) {

		List<Productorder> productorderList = null;
		try {
			// TODO afterwards you need to change it from properties.
			productorderList = productorderService.getInCompleteProductOrder(clientId,Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_PRODUCT_ORDER_INCOMPLETE, null, null)),
					Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_PRODUCT_ORDER_COMPLETE, null, null)));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return productorderList;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteProductorder(
			@PathVariable("id") long id) {

		try {
			Productorder productorder = productorderService.getEntityById(
					Productorder.class, id);
			productorder.setIsactive(false);
			productorderService.updateEntity(productorder);
			return new UserStatus(1, "Product Order deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}

	}
	private Productorder saveProductOrder(ProductOrderAssociationModel productOrderAssociationModel,HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		Productorder productorder = new Productorder();
		productorder.setClient(clientService.getEntityById(Client.class,productOrderAssociationModel.getClient()));
		productorder.setCreateDate(new Date());
		productorder.setDescription(productOrderAssociationModel.getDescription());
		productorder.setInvoiceNo(productOrderAssociationModel.getInvoiceNo());
		productorder.setExpecteddeliveryDate(productOrderAssociationModel.getExpecteddeliveryDate());
		productorder.setQuantity(productOrderAssociationModel.getOrderproductassociations().size());
		productorder.setStatus(statusService.getEntityById(Status.class,Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_NEW_PRODUCT_ORDER, null, null))));
		productorder.setIsactive(true);
		productorder.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		productorderService.addEntity(productorder);

		//TODO create  PDF file
	//	downloadPDF(request, response, productorder);
		return productorder;
	}

	private void addProductOrderAsso(ProductOrderAssociationModel productOrderAssociationModel,Productorder productorder,HttpServletRequest request,HttpServletResponse response) throws Exception {
		List<Productorderassociation> productorderassociations = productOrderAssociationModel.getOrderproductassociations();
		Client client = clientService.getEntityById(Client.class, productOrderAssociationModel.getClient());
		if (productorderassociations != null
				&& !productorderassociations.isEmpty()) {
			for (Productorderassociation productorderassociation : productorderassociations) {
				productorderassociation.setProductorder(productorder);
				productorderassociation.setRemainingQuantity(productorderassociation.getQuantity());
				productorderassociation.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
				productorderassociation.setIsactive(true);
				productorderassociationService
						.addEntity(productorderassociation);
			}
		}
		List<ProductOrderData> productOrderDatas = new ArrayList<ProductOrderData>();
		for (Productorderassociation productorderassociation : productorderassociations) {
			Product product = productService.getEntityById(Product.class, productorderassociation.getProduct().getId());
			ProductOrderData productOrderData = new ProductOrderData();
			productOrderData.setProductName(product.getName());
			productOrderData.setQuantity(productorderassociation.getQuantity());
			productOrderData.setRate(product.getRatePerUnit());
			productOrderData.setAmount(product.getRatePerUnit()*productorderassociation.getQuantity());
			productOrderDatas.add(productOrderData);
		}
		//downloadPDF(request, response, productorder,productOrderDatas,client);
	}
	public void downloadPDF(HttpServletRequest request, HttpServletResponse response,Productorder productorder,List<ProductOrderData> productOrderDatas,Client client) throws IOException {

		final ServletContext servletContext = request.getSession().getServletContext();
	    final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
	    final String temperotyFilePath = tempDirectory.getAbsolutePath();

	    String fileName = "Productorder.pdf";
	    response.setContentType("application/pdf");
	    response.setHeader("Content-disposition", "attachment; filename="+ fileName);

	    try {

	   CreatePDF ceCreatePDFProductOrder = new CreatePDF();
	   ceCreatePDFProductOrder.createPDF(temperotyFilePath+"\\"+fileName,productorder,productOrderDatas,client);
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        baos = convertPDFToByteArrayOutputStream(temperotyFilePath+"\\"+fileName,productorder);
	        OutputStream os = response.getOutputStream();
	        baos.writeTo(os);
	        os.flush();

	    } catch (Exception e1) {
	        e1.printStackTrace();
	    }

	}

	private ByteArrayOutputStream convertPDFToByteArrayOutputStream(String fileName,Productorder productorder) throws Exception {


		Status status = statusService.getEntityById(Status.class, productorder.getStatus().getId());
		Notification notification = notificationService.getNotifiactionByStatus(status.getId());
		Client client = clientService.getEntityById(Client.class,productorder.getClient().getId());

		//TODO mail sending
        mailSending(notification, productorder, client,fileName);

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

	@SuppressWarnings("unchecked")
	private void mailSending(Notification notification,Productorder productorder,Client client,String fileName) throws Exception{
		List<Productorderassociation>  productorderassociations= productorderassociationService.getProductorderassociationByOrderId(productorder.getId());
		List<ProductOrderData> productOrderDatas = new ArrayList<ProductOrderData>();
		List<Notificationuserassociation> notificationuserassociations  = notificationUserAssociationService.getNotificationuserassociationBynotificationId(notification.getId());
		for (Productorderassociation productorderassociation : productorderassociations) {
			Product product = productService.getEntityById(Product.class, productorderassociation.getProduct().getId());
			ProductOrderData productOrderData = new ProductOrderData();
			productOrderData.setProductName(product.getName());
			productOrderData.setQuantity(productorderassociation.getQuantity());
			productOrderData.setRate(product.getRatePerUnit());
			productOrderDatas.add(productOrderData);
		}
		  Mail mail = new Mail();
		  for (Notificationuserassociation notificationuserassociation : notificationuserassociations) {
		 //  User user = userService.getEntityById(User.class, notificationuserassociation.getUser().getId());
		   User user = userService.getEmailUserById(notificationuserassociation.getUser().getId());
		 if(notificationuserassociation.getTo()==true){
			 mail.setMailTo(client.getEmailid()); 
		 }else if(notificationuserassociation.getBcc()==true){
			 mail.setMailBcc(user.getEmail());
		 }else if(notificationuserassociation.getCc()==true){
			 mail.setMailCc(user.getEmail());
		 }
	  
	        mail.setMailSubject(notification.getSubject());
	        mail.setAttachment(fileName);
		  }     
	        Map < String, Object > model = new HashMap < String, Object >();
	            model.put("companyName", client.getCompanyname());
	            model.put("mailfrom", notification.getName());
	   	        model.put("location", "Pune");
	   	        model.put("productOrderDatas",productOrderDatas);
	   	        model.put("invoiceNumber",productorder.getInvoiceNo());
	   	         model.put("date",productorder.getCreatedDate());
	   	        model.put("address", client.getAddress());
	   	        model.put("signature", "www.NextechServices.in");
	   	        mail.setModel(model);
	  
		mailService.sendEmail(mail,notification);
	}
}
