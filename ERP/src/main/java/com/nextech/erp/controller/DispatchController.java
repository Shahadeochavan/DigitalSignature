package com.nextech.erp.controller;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nextech.erp.dto.CreatePdfForDispatchProduct;
import com.nextech.erp.dto.DispatchDTO;
import com.nextech.erp.dto.DispatchProductDTO;
import com.nextech.erp.dto.Mail;
import com.nextech.erp.dto.ProductOrderDTO;
import com.nextech.erp.factory.DispatchRequestResponseFactory;
import com.nextech.erp.factory.MailResponseRequestFactory;
import com.nextech.erp.newDTO.ClientDTO;
import com.nextech.erp.newDTO.NotificationDTO;
import com.nextech.erp.newDTO.StatusDTO;
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
import com.nextech.erp.status.Response;
import com.nextech.erp.status.UserStatus;
import com.nextech.erp.util.PDFToByteArrayOutputStreamUtil;

@Controller
@Transactional
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
	
	static Logger logger = Logger.getLogger(DispatchController.class);


	@Transactional
	@RequestMapping(value = "/dispatchProducts", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addDispatchProduct(
			@RequestBody DispatchDTO dispatchDTO, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
		     Response responseList = dispatchservice.addDispatchProduct(dispatchDTO, request);
			ProductOrderDTO productorder = productorderService.getProductById(dispatchDTO.getOrderId());
			ClientDTO client = clientService.getClientDTOById(productorder.getClientId().getId());
	    	List<DispatchProductDTO> 	dispatchProductDTOs = responseList.getDispatchProductDTOs();
		     if(dispatchProductDTOs==null){
			logger.error("Please create bom for dispatch product");
			return new UserStatus(1,"Please create bom for dispatch product");
		    }
		     
		     createPDFDispatch(request, response, productorder, dispatchProductDTOs, client,dispatchDTO);
			
			return new UserStatus(1, "Dispatch added Successfully !");
		} catch (ConstraintViolationException cve) {
			logger.error("Inside ConstraintViolationException");
			cve.printStackTrace();
			return new UserStatus(0, cve.getCause().getMessage());
		} catch (PersistenceException pe) {
			logger.error("Inside PersistenceException");
			pe.printStackTrace();
			return new UserStatus(0, pe.getCause().getMessage());
		} catch (Exception e) {
			logger.error("Inside Exception");
			e.printStackTrace();
			return new UserStatus(0, e.getCause().getMessage());
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getDispatch(@PathVariable("id") long id) {
		DispatchDTO dispatch = null;
		try {
			dispatch = dispatchservice.getDispatchById(id);
			if(dispatch==null){
				logger.error("There is no any dispatch product");
				return new Response(1,"There is no any dispatch product");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,dispatch);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateDispatch(
			@RequestBody DispatchDTO dispatchDTO, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			dispatchservice.updateEntity(DispatchRequestResponseFactory.setdispatch(dispatchDTO, request));
			return new UserStatus(1, "Dispatch update Successfully !");
		} catch (Exception e) {
			 e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getDispatchList() {

		List<DispatchDTO> dispatchList = null;
		try {
			dispatchList = dispatchservice.getDispatchList();
			if(dispatchList==null){
				logger.error("There is no any dispatch product");
				return new Response(1,"There is no any dispatch product list");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,dispatchList);
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteDispatch(@PathVariable("id") long id) {

		try {
			DispatchDTO dispatchDTO = dispatchservice.deleteDispatchById(id);
			if(dispatchDTO==null){
				logger.error("There is no any dispatch product for delete");
				return new Response(1,"There is no any dispatch product");
			}
			return new Response(1, "Dispatch deleted Successfully !");
		} catch (Exception e) {
			return new Response(0, e.toString());
		}

	}

	private void mailSending(ProductOrderDTO productorder,ClientDTO clientDTO, StatusDTO status,String fileName,List<DispatchProductDTO> dispatchProductDTOs,DispatchDTO dispatchDTO) throws NumberFormatException,Exception {
		  NotificationDTO  notificationDTO = notificationService.getNotifiactionByStatus(status.getId());
		  Mail mail = mailService.setMailCCBCCAndTO(notificationDTO);
	      mail.setAttachment(fileName);
	      mail.setMailSubject(notificationDTO.getSubject());
		  mail.setMailTo(clientDTO.getEmailId());
		  mail.setModel(MailResponseRequestFactory.setMailDetailsDispatch(clientDTO, dispatchProductDTOs, dispatchDTO));
		 mailService.sendEmail(mail, notificationDTO);
	}
	
	public void createPDFDispatch(HttpServletRequest request, HttpServletResponse response,ProductOrderDTO productorder,List<DispatchProductDTO> dispatchProductDTOs,ClientDTO client,DispatchDTO dispatchDTO) throws IOException {

		final ServletContext servletContext = request.getSession().getServletContext();
	    final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
	    final String temperotyFilePath = tempDirectory.getAbsolutePath();
	    String fileName = "Dispatch.pdf";
	    response.setContentType("application/pdf");
	    response.setHeader("Content-disposition", "attachment; filename="+ fileName);
	    try {
	    	
	   CreatePdfForDispatchProduct createPdfForDispatchProduct = new CreatePdfForDispatchProduct();
	   createPdfForDispatchProduct.createPDF(temperotyFilePath+"\\"+fileName,productorder,dispatchProductDTOs,client,dispatchDTO);
	   String dispatchPdfFile =    PDFToByteArrayOutputStreamUtil.convertPDFToByteArrayOutputStream(temperotyFilePath+"\\"+fileName);
	   StatusDTO status = statusService.getStatusById(productorder.getStatusId().getId());
	   mailSending(productorder, client, status, dispatchPdfFile, dispatchProductDTOs, dispatchDTO);
	    } catch (Exception e1) {
	        e1.printStackTrace();
	    }

	}
}
