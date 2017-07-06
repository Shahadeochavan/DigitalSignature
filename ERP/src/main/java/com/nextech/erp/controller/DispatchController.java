package com.nextech.erp.controller;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dto.CreatePDF;
import com.nextech.erp.dto.CreatePdfForDispatchProduct;
import com.nextech.erp.dto.DispatchDTO;
import com.nextech.erp.dto.DispatchProductDTO;
import com.nextech.erp.dto.Mail;
import com.nextech.erp.dto.Part;
import com.nextech.erp.dto.ProductOrderData;
import com.nextech.erp.model.Bom;
import com.nextech.erp.model.Bomrmvendorassociation;
import com.nextech.erp.model.Client;
import com.nextech.erp.model.Dispatch;
import com.nextech.erp.model.Notification;
import com.nextech.erp.model.Notificationuserassociation;
import com.nextech.erp.model.Product;
import com.nextech.erp.model.Productinventory;
import com.nextech.erp.model.Productinventoryhistory;
import com.nextech.erp.model.Productorder;
import com.nextech.erp.model.Productorderassociation;
import com.nextech.erp.model.Status;
import com.nextech.erp.model.User;
import com.nextech.erp.service.BOMRMVendorAssociationService;
import com.nextech.erp.service.BomService;
import com.nextech.erp.service.ClientService;
import com.nextech.erp.service.DispatchService;
import com.nextech.erp.service.MailService;
import com.nextech.erp.service.NotificationService;
import com.nextech.erp.service.NotificationUserAssociationService;
import com.nextech.erp.service.ProductService;
import com.nextech.erp.service.ProductinventoryService;
import com.nextech.erp.service.ProductinventoryhistoryService;
import com.nextech.erp.service.ProductorderService;
import com.nextech.erp.service.ProductorderassociationService;
import com.nextech.erp.service.StatusService;
import com.nextech.erp.service.UserService;
import com.nextech.erp.status.UserStatus;

@Controller
@RequestMapping("/dispatch")
public class DispatchController {

	@Autowired
	DispatchService dispatchservice;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ProductorderService productorderService;

	@Autowired
	ProductService productService;
	
	@Autowired
	BomService bomService;
	
	@Autowired
	BOMRMVendorAssociationService bomRMVendorAssociationService;

	@Autowired
	ProductorderassociationService productorderassociationService;

	@Autowired
	StatusService statusService;

	@Autowired
	ProductinventoryService productinventoryService;

	@Autowired
	ProductinventoryhistoryService productinventoryhistoryService;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	NotificationUserAssociationService notificationUserAssService;

	@Autowired
	NotificationService notificationService;

	@Autowired
	ClientService clientService;
	
	@Autowired
	UserService userService;

	@Autowired
	MailService mailService;

	private static final int STATUS_PRODUCT_ORDER_INCOMPLETE = 32;
	private static final int STATUS_PRODUCT_ORDER_COMPLETE = 31;
	

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addDispatch(
			@Valid @RequestBody Dispatch dispatch, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			dispatch.setIsactive(true);
			dispatchservice.addEntity(dispatch);
			return new UserStatus(1, "Dispatch added Successfully !");
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

	@RequestMapping(value = "/dispatchProducts", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus listDispatch(
			@RequestBody DispatchDTO dispatchDTO, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			List<DispatchProductDTO> dispatchProductDTOs = new ArrayList<DispatchProductDTO>();
			for (Part part : dispatchDTO.getParts()) {
				Dispatch dispatch = setPart(part);
				Productinventory productinventory = productinventoryService
						.getProductinventoryByProductId(dispatch.getProduct()
								.getId());
				System.out.println("dispatchDTO" + dispatchDTO.getOrderId());
				List<Productorderassociation> productorderassociationList = productorderassociationService
						.getProductOrderAssoByOrderId(dispatchDTO.getOrderId());
				for (Productorderassociation productorderassociation : productorderassociationList) {
					// check product id is equal
					if (dispatch.getProduct().getId() == productorderassociation.getProduct().getId()) {
						// update data related to dispatch
						if (productinventory.getQuantityavailable() < dispatch.getQuantity()|| productorderassociation.getQuantity() < dispatch.getQuantity()) {
							return new UserStatus(2,messageSource.getMessage(ERPConstants.TO_CHECK_QUANTITY_IN_PRODUCTINVENTORY,null, null));
						} else {
							dispatch.setDescription(dispatchDTO
									.getDescription());
							dispatch.setProductorder(productorderService
									.getEntityById(Productorder.class,
											dispatchDTO.getOrderId()));
							dispatch.setInvoiceNo(dispatchDTO.getInvoiceNo());
							dispatch.setCreatedBy(Long.parseLong(request
									.getAttribute("current_user").toString()));
							dispatch.setStatus(statusService.getEntityById(
									Status.class,
									Long.parseLong(messageSource.getMessage(
											ERPConstants.ORDER_DISPATCHED,
											null, null))));
							Product product = productService.getEntityById(Product.class,productorderassociation.getProduct().getId());
							Bom bom = bomService.getBomByProductId(product.getId());
							if(bom !=null){
							List<Bomrmvendorassociation> bomrmvendorassociations = bomRMVendorAssociationService.getBomRMVendorByBomId(bom.getId());
							float totalCost = 0;
							DispatchProductDTO  dispatchProductDTO = new DispatchProductDTO();
							dispatchProductDTO.setClientPartNumber(product.getClientpartnumber());
							dispatchProductDTO.setProductName(product.getPartNumber());
							dispatchProductDTO.setQuantityDispatched(dispatch.getQuantity());
							dispatchProductDTO.setDescription(dispatchDTO.getDescription());
							for (Bomrmvendorassociation bomrmvendorassociation : bomrmvendorassociations) {
							totalCost = totalCost+bomrmvendorassociation.getCost();
							dispatchProductDTO.setTotalCost(totalCost);
							}
							dispatchProductDTOs.add(dispatchProductDTO);
							dispatchservice.addEntity(dispatch);
							}else{
								return new UserStatus(1, "If you want  Pdf for Dispatch Please Make BOM");
							}
						}
					}
				}
				Productorder productorder = productorderService.getEntityById(
						Productorder.class, dispatch.getProductorder().getId());
				Product product = productService.getEntityById(Product.class,
						dispatch.getProduct().getId());

				// TODO update product order association
				updateProductOrderAssoRemainingQuantity(productorder, dispatch,
						request, response);

				// TODO add product Inventroy history
				addProductInventoryHistory(dispatch.getQuantity(), product,
						dispatch, request, response);

				// TODO update product Inventory
				updateProductInventory(dispatch, product, request, response);

				// TODO update product order
				updateProductOrder(productorder, request, response);
			}
			Productorder productorder = productorderService.getEntityById(Productorder.class, dispatchDTO.getOrderId());
			Client client = clientService.getEntityById(Client.class,productorder.getClient().getId());
			Status status = statusService.getEntityById(Status.class,productorder.getStatus().getId());
		//	mailSending(productorder, request, response, client, status);
			downloadPDF(request, response, productorder, dispatchProductDTOs, client,dispatchDTO);
			return new UserStatus(1, "Dispatch added Successfully !");
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
	public @ResponseBody Dispatch getDispatch(@PathVariable("id") long id) {
		Dispatch dispatch = null;
		try {
			dispatch = dispatchservice.getEntityById(Dispatch.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dispatch;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateDispatch(
			@RequestBody Dispatch dispatch, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			dispatch.setIsactive(true);
			dispatch.setUpdatedBy(Long.parseLong(request.getAttribute(
					"current_user").toString()));
			dispatchservice.updateEntity(dispatch);
			return new UserStatus(1, "Dispatch update Successfully !");
		} catch (Exception e) {
			// e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Dispatch> getDispatch() {

		List<Dispatch> dispatchList = null;
		try {
			dispatchList = dispatchservice.getEntityList(Dispatch.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dispatchList;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteDispatch(@PathVariable("id") long id) {

		try {
			Dispatch dispatch = dispatchservice.getEntityById(Dispatch.class,
					id);
			dispatch.setIsactive(false);
			dispatchservice.updateEntity(dispatch);
			return new UserStatus(1, "Dispatch deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}

	}

	private void updateProductOrderAssoRemainingQuantity(
			Productorder productorder, Dispatch dispatch,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Productorderassociation productorderassociation = productorderassociationService
				.getProductorderassociationByProdcutOrderIdandProdcutId(
						productorder.getId(), dispatch.getProduct().getId());
		productorderassociation.setRemainingQuantity(productorderassociation
				.getRemainingQuantity() - dispatch.getQuantity());
		productorderassociation.setCreatedBy(Long.parseLong(request
				.getAttribute("current_user").toString()));
		productorderassociationService.updateEntity(productorderassociation);
	}

	private int getProductOrderStatus(Productorder productorder)
			throws Exception {
		boolean isOrderComplete = false;
		List<Productorderassociation> productorderassociationsList = productorderassociationService
				.getProductorderassociationByOrderId(productorder.getId());
		for (Iterator<Productorderassociation> iterator = productorderassociationsList
				.iterator(); iterator.hasNext();) {
			Productorderassociation productorderassociation = (Productorderassociation) iterator
					.next();
			if (productorderassociation.getRemainingQuantity() == 0) {
				isOrderComplete = true;
			} else {
				isOrderComplete = false;
				break;
			}

		}
		return isOrderComplete ? STATUS_PRODUCT_ORDER_COMPLETE
				: STATUS_PRODUCT_ORDER_INCOMPLETE;
	}

	private void updateProductOrder(Productorder productorder,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		productorder.setStatus(statusService.getEntityById(Status.class,
				getProductOrderStatus(productorder)));
		productorder.setUpdatedBy(Long.parseLong(request.getAttribute(
				"current_user").toString()));
		productorder.setCreatedDate(new Timestamp(new Date().getTime()));
		productorderService.updateEntity(productorder);
	}
	private Productinventory updateProductInventory(Dispatch dispatch,
			Product product, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Productinventory productinventory = productinventoryService
				.getProductinventoryByProductId(dispatch.getProduct().getId());
		if (productinventory == null) {
			productinventory = new Productinventory();
			productinventory.setProduct(product);
			productinventory.setCreatedBy(Long.parseLong(request.getAttribute(
					"current_user").toString()));
			productinventory.setQuantityavailable(productinventory
					.getQuantityavailable() - dispatch.getQuantity());
			productinventory.setIsactive(true);
			productinventoryService.addEntity(productinventory);

		} else {
			productinventory.setQuantityavailable(productinventory
					.getQuantityavailable() - dispatch.getQuantity());
			productinventory.setUpdatedBy(Long.parseLong(request.getAttribute(
					"current_user").toString()));
			productinventory.setIsactive(true);
			productinventoryService.updateEntity(productinventory);

		}
		return productinventory;
	}

	private void addProductInventoryHistory(long goodQuantity, Product product,
			Dispatch dispatch, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Productinventory productinventory = productinventoryService
				.getProductinventoryByProductId(product.getId());
		if (productinventory == null) {
			productinventory = new Productinventory();
			productinventory.setProduct(product);
			productinventory.setQuantityavailable(0);
			productinventory.setCreatedBy(Long.parseLong(request.getAttribute(
					"current_user").toString()));
			productinventory.setIsactive(true);
			productinventoryService.addEntity(productinventory);
		}
		Productinventoryhistory productinventoryhistory = new Productinventoryhistory();
		productinventoryhistory.setProductinventory(productinventory);
		productinventoryhistory.setIsactive(true);
		productinventoryhistory.setCreatedBy(Long.parseLong(request
				.getAttribute("current_user").toString()));
		productinventoryhistory.setBeforequantity(productinventory
				.getQuantityavailable() - dispatch.getQuantity());
		productinventoryhistory.setAfterquantity((goodQuantity
				+ productinventory.getQuantityavailable() - dispatch
				.getQuantity()));
		productinventoryhistory
				.setStatus(statusService.getEntityById(Status.class, Long
						.parseLong(messageSource.getMessage(
								ERPConstants.STATUS_PRODUCT__INVENTORY_ADD,
								null, null))));
		productinventoryhistoryService.addEntity(productinventoryhistory);
	}
	
	private Dispatch setPart(Part part) throws Exception {
		Dispatch dispatch = new Dispatch();
		dispatch.setProduct(productService.getEntityById(Product.class,
				part.getProductId()));
		dispatch.setQuantity(part.getQuantity());
		dispatch.setIsactive(true);
		return dispatch;
	}
//TODO
	private void mailSending(Productorder productorder,HttpServletRequest request, HttpServletResponse response,Client client, Status status,String fileName,List<DispatchProductDTO> dispatchProductDTOs,DispatchDTO dispatchDTO) throws NumberFormatException,Exception {
		Mail mail = new Mail();

		Notification notification = notificationService.getEntityById(Notification.class,Long.parseLong(messageSource.getMessage(ERPConstants.DISPATCHED_SUCCESSFULLY, null, null)));
		  List<Notificationuserassociation> notificationuserassociations = notificationUserAssService.getNotificationuserassociationBynotificationId(notification.getId());
		  for (Notificationuserassociation notificationuserassociation : notificationuserassociations) {
			  User user = userService.getEntityById(User.class, notificationuserassociation.getUser().getId());
			  if(notificationuserassociation.getTo()==true){
				  mail.setMailTo(client.getEmailid()); 
			  }else if(notificationuserassociation.getBcc()==true){
				  mail.setMailBcc(user.getEmail());
			  }else if(notificationuserassociation.getCc()==true){
				  mail.setMailCc(user.getEmail());
			  }
			
		}
	   mail.setAttachment(fileName);
		mail.setMailSubject(notification.getSubject());
		mail.setMailTo(client.getEmailid());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("firstName", client.getCompanyname());
		model.put("mailfrom", "Nextech");
		model.put("address", client.getAddress());
		model.put("dispatchProductDTOs", dispatchProductDTOs);
		model.put("invoiceNo", dispatchDTO.getInvoiceNo());
		model.put("discription", dispatchDTO.getDescription());
		model.put("location", "Pune");
		model.put("signature", "www.NextechServices.in");
		mail.setModel(model);
		mailService.sendEmail(mail, notification);
	}
	
	public void downloadPDF(HttpServletRequest request, HttpServletResponse response,Productorder productorder,List<DispatchProductDTO> dispatchProductDTOs,Client client,DispatchDTO dispatchDTO) throws IOException {

		final ServletContext servletContext = request.getSession().getServletContext();
	    final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
	    final String temperotyFilePath = tempDirectory.getAbsolutePath();

	    String fileName = "Dispatch.pdf";
	    response.setContentType("application/pdf");
	    response.setHeader("Content-disposition", "attachment; filename="+ fileName);

	    try {

	   CreatePdfForDispatchProduct createPdfForDispatchProduct = new CreatePdfForDispatchProduct();
	   createPdfForDispatchProduct.createPDF(temperotyFilePath+"\\"+fileName,productorder,dispatchProductDTOs,client,dispatchDTO);
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        baos = convertPDFToByteArrayOutputStream(temperotyFilePath+"\\"+fileName,request, response, productorder,dispatchProductDTOs,dispatchDTO);
	        OutputStream os = response.getOutputStream();
	        baos.writeTo(os);
	        os.flush();

	    } catch (Exception e1) {
	        e1.printStackTrace();
	    }

	}

	private ByteArrayOutputStream convertPDFToByteArrayOutputStream(String fileName,HttpServletRequest request, HttpServletResponse response,Productorder productorder,List<DispatchProductDTO> dispatchProductDTOs,DispatchDTO dispatchDTO) throws Exception {


		Status status = statusService.getEntityById(Status.class, productorder.getStatus().getId());
		Client client = clientService.getEntityById(Client.class,productorder.getClient().getId());

		//TODO mail sending
        mailSending(productorder, request, response, client, status, fileName,dispatchProductDTOs,dispatchDTO);

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
}
