package com.nextech.dscrm.controller;

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

import com.nextech.dscrm.constants.ERPConstants;
import com.nextech.dscrm.dto.Mail;
import com.nextech.dscrm.dto.ProductOrderDTO;
import com.nextech.dscrm.model.Client;
import com.nextech.dscrm.model.Productinventory;
import com.nextech.dscrm.model.Productorder;
import com.nextech.dscrm.newDTO.NotificationDTO;
import com.nextech.dscrm.newDTO.ProductOrderAssociationDTO;
import com.nextech.dscrm.service.ClientService;
import com.nextech.dscrm.service.ClientproductassoService;
import com.nextech.dscrm.service.MailService;
import com.nextech.dscrm.service.NotificationService;
import com.nextech.dscrm.service.NotificationUserAssociationService;
import com.nextech.dscrm.service.ProductService;
import com.nextech.dscrm.service.ProductinventoryService;
import com.nextech.dscrm.service.ProductorderService;
import com.nextech.dscrm.service.ProductorderassociationService;
import com.nextech.dscrm.service.StatusService;
import com.nextech.dscrm.service.UserService;
import com.nextech.dscrm.status.Response;
import com.nextech.dscrm.status.UserStatus;

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
	ClientproductassoService clientproductassoService;
	
	@Autowired
	ProductinventoryService productinventoryService;
	

	static Logger logger = Logger.getLogger(ProductorderController.class);

	@Transactional @RequestMapping(value = "/createMultiple", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addMultipleProductOrder(
			@Valid @RequestBody ProductOrderDTO productOrderDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
			try {
				if (bindingResult.hasErrors()) {
					return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
				}
				Productinventory productinventory = productinventoryService.getEntityById(Productinventory.class, productOrderDTO.getProduct().getId());
				if(productinventory.getQuantityavailable()>=productOrderDTO.getQuantity()){
					productinventory.setQuantityavailable(productinventory.getQuantityavailable()-productOrderDTO.getQuantity());
					productinventoryService.updateEntity(productinventory);
				}else{
					return  new UserStatus(0,"Please purchase product and add into product inventory");
				}
				// TODO save call product orde
				productorderService.addMultipleProductOrder(productOrderDTO, request, response);
				
				
				
				//TODO Check Inventory for Products
			return new UserStatus(1,"Multiple Product Order added Successfully !");
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
	public @ResponseBody Response getProductorder(@PathVariable("id") long id) {
		ProductOrderDTO productorder = null;
		try {
			productorder = productorderService.getProductById(id);
			if(productorder==null){
				return new Response(1,"There is no product order");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,productorder);
	}

	@Transactional @RequestMapping(value = "productorderId/{orderId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getProductOrder(@PathVariable("orderId") long id) {
		List<ProductOrderAssociationDTO> productorderassociations = null;
		try {
			productorderassociations = productorderassociationService.getProductorderassociationByOrderId(id);
			if(productorderassociations.isEmpty()){
				
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return new Response(1,productorderassociations);
	}

	@Transactional @RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateProductorder(
			@RequestBody ProductOrderDTO productOrderDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			productorderService.updateMultiple(productOrderDTO, request, response);
			return new UserStatus(1, "Product Order update Successfully !");
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@Transactional @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getProductorder() {
		List<ProductOrderDTO> productorderList = null;
		try {
			productorderList = productorderService.getProductOrderList();
			if(productorderList==null){
				logger.error("There is no product order list");
				return new Response(1,"There is no product order list");
			}

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return new Response(1,productorderList);
	}

	@Transactional @RequestMapping(value = "/pendingList", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getPendingsProductorders() {
		List<ProductOrderDTO> productorderList = null;
		try {
			productorderList = productorderService.getPendingProductOrders(Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_NO_PAYMENT, null, null)),
					Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_PRODUCT_ORDER_INCOMPLETE, null, null)));
			if(productorderList==null){
				logger.error("There is no any pending list");
				return new Response(1,"There is no any pending list");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return new Response(1,productorderList);
	}

	@Transactional @RequestMapping(value = "incompleteProductOrder/{CLIENT-ID}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getInCompleteProductOrder(@PathVariable("CLIENT-ID") long clientId) {
		List<ProductOrderDTO> productorderList = null;
		try {
			productorderList = productorderService.getInCompleteProductOrder(clientId,Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_PRODUCT_ORDER_INCOMPLETE, null, null)),
			Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_PRODUCT_ORDER_COMPLETE, null, null)));
			if(productorderList==null){
				logger.error("There is no product order list");
				return new Response(1,"There is no product order list");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return new Response(1,productorderList);
	}

	@Transactional @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteProductorder(
			@PathVariable("id") long id) {
		try {
		ProductOrderDTO  productOrderDTO =	productorderService.deleteProductOrder(id);
		if(productOrderDTO==null){
			logger.error("There is no product order");
			return  new Response(1,"There is no product order");
		}
			return new Response(1, "Product Order deleted Successfully !");
		} catch (Exception e) {
			logger.error(e);
			return new Response(0, e.toString());
		}
	}
/*
	public void createPdfProductOrder(HttpServletRequest request, HttpServletResponse response,ProductOrderDTO productOrderDTO,List<ProductOrderData> productOrderDatas,ClientDTO client) throws IOException {
		final ServletContext servletContext = request.getSession().getServletContext();
	    final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
	    final String temperotyFilePath = tempDirectory.getAbsolutePath();
	    String fileName = "ProductOrder.pdf";
	    response.setContentType("application/pdf");
	    response.setHeader("Content-disposition", "attachment; filename="+ fileName);
	    try {
	    	ProductOrderPdf createPDFProductOrder = new ProductOrderPdf();
	    	createPDFProductOrder.createPDF(temperotyFilePath+"\\"+fileName,productOrderDTO,productOrderDatas,client);
	 
	       String productOrderPdfFile =    PDFToByteArrayOutputStreamUtil.convertPDFToByteArrayOutputStream(temperotyFilePath+"\\"+fileName);
	 	   StatusDTO status = statusService.getStatusById(productOrderDTO.getStatusId().getId());
		   NotificationDTO notificationDTO = notificationService.getNotifiactionByStatus(status.getId());
		   emailNotificationProductOrder(notificationDTO, productOrderDatas, client, productOrderPdfFile, productOrderDTO);
	    } catch (Exception e1) {
	    	logger.error(e1);
	        e1.printStackTrace();
	    }
	}
	
	private void emailNotificationProductOrder(NotificationDTO notification,List<ProductOrderData> productOrderDatas,ClientDTO client,String fileName,ProductOrderDTO productOrderDTO) throws Exception{
		 Mail mail = mailService.setMailCCBCCAndTO(notification);
	    String userEmailCC = mail.getMailCc()+","+client.getEmailId();
	    mail.setMailCc(userEmailCC);
	    mail.setAttachment(fileName);
        mail.setModel(MailResponseRequestFactory.setMailDetailsProductOrder(notification, productOrderDatas, client, productOrderDTO));
        mailService.sendEmail(mail,notification);
	}*/
	
//	@Scheduled(initialDelay=600000, fixedRate=600000)
	public void executeSchedular() throws Exception{
		logger.info("Client payment email notification");
		
		List<Client> clients = clientService.getEntityList(Client.class);
		try {
			for (Client client : clients) {
				long quantityCount = 0;
				List<Productorder> productorders = productorderService.getProductOrderListByClientId(client.getId());
				if(productorders !=null&&!productorders.isEmpty()){
				for (Productorder productorder : productorders) {
					long id =3;
					if(productorder.getStatus().getId()!=id){
						quantityCount = quantityCount+productorder.getQuantity();
					 }
				   }
				}
				if(quantityCount>=5){
					clientEmailNotifiactionPayment(client);
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}
	
	public void clientEmailNotifiactionPayment(Client client) throws Exception{
		   NotificationDTO  notificationDTO = notificationService.getNotificationByCode((messageSource.getMessage(ERPConstants.CLIENT_PAYMENT_NOTIFICATION, null, null)));
		   Mail mail = mailService.setMailCCBCCAndTO(notificationDTO);
		   String clientTO = mail.getMailTo()+","+client.getEmailid();
		    mail.setMailTo(clientTO);
	        mail.setMailSubject(notificationDTO.getSubject());
	        Map < String, Object > model = new HashMap < String, Object > ();
	        model.put("companyname", client.getCompanyname());
	        model.put("location", "Pune");
	        model.put("signature", "www.NextechServices.in");
	        mail.setModel(model);
		   mailService.sendEmailWithoutPdF(mail, notificationDTO);
	}
}

