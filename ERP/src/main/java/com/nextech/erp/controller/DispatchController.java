package com.nextech.erp.controller;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dto.CreatePdfForDispatchProduct;
import com.nextech.erp.dto.DispatchDTO;
import com.nextech.erp.dto.DispatchProductDTO;
import com.nextech.erp.dto.Mail;
import com.nextech.erp.dto.ProductOrderDTO;
import com.nextech.erp.factory.DispatchRequestResponseFactory;
import com.nextech.erp.newDTO.ClientDTO;
import com.nextech.erp.newDTO.NotificationDTO;
import com.nextech.erp.newDTO.NotificationUserAssociatinsDTO;
import com.nextech.erp.newDTO.StatusDTO;
import com.nextech.erp.newDTO.UserDTO;
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
	
	StringBuilder stringBuilderCC = new StringBuilder();
	StringBuilder stringBuilderTO = new StringBuilder();
	StringBuilder stringBuilderBCC = new StringBuilder();
	
	String prefixCC="";
	String prefixTO="";
	String prefixBCC="";
	
	String multipleCC="";
	String multipleBCC="";
	String multipleTO="";

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
		List<DispatchProductDTO> dispatchProductDTOs=	dispatchservice.addDispatchProduct(dispatchDTO, request);
			ProductOrderDTO productorder = productorderService.getProductById(dispatchDTO.getOrderId());
			ClientDTO client = clientService.getClientDTOById(productorder.getClientId().getId());
			StatusDTO status = statusService.getStatusById(productorder.getStatusId().getId());
			//mailSending(productorder, request, response, client, status);
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
	public @ResponseBody DispatchDTO getDispatch(@PathVariable("id") long id) {
		DispatchDTO dispatch = null;
		try {
			dispatch = dispatchservice.getDispatchById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dispatch;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateDispatch(
			@RequestBody DispatchDTO dispatchDTO, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			dispatchservice.updateEntity(DispatchRequestResponseFactory.setdispatch(dispatchDTO, request));
			return new UserStatus(1, "Dispatch update Successfully !");
		} catch (Exception e) {
			// e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<DispatchDTO> getDispatch() {

		List<DispatchDTO> dispatchList = null;
		try {
			dispatchList = dispatchservice.getDispatchList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dispatchList;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteDispatch(@PathVariable("id") long id) {

		try {
			dispatchservice.deleteDispatchById(id);
			return new UserStatus(1, "Dispatch deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}

	}

	private void mailSending(ProductOrderDTO productorder,HttpServletRequest request, HttpServletResponse response,ClientDTO client, StatusDTO status,String fileName,List<DispatchProductDTO> dispatchProductDTOs,DispatchDTO dispatchDTO) throws NumberFormatException,Exception {
		Mail mail = new Mail();

		  NotificationDTO  notificationDTO = notificationService.getNotificationDTOById(Long.parseLong(messageSource.getMessage(ERPConstants.DISPATCHED_SUCCESSFULLY, null, null)));
		  List<NotificationUserAssociatinsDTO> notificationUserAssociatinsDTOs = notificationUserAssService.getNotificationUserAssociatinsDTOs(notificationDTO.getId());
		  for (NotificationUserAssociatinsDTO notificationuserassociation : notificationUserAssociatinsDTOs) {
			  UserDTO userDTO = userService.getUserDTO(notificationuserassociation.getUserId().getId());
			  if(notificationuserassociation.getTo()==true){
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
			
		}
	   mail.setAttachment(fileName);
		mail.setMailSubject(notificationDTO.getSubject());
		mail.setMailTo(client.getEmailId());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("firstName", client.getCompanyName());
		model.put("mailfrom", "Nextech");
		model.put("address", client.getAddress());
		model.put("dispatchProductDTOs", dispatchProductDTOs);
		model.put("invoiceNo", dispatchDTO.getInvoiceNo());
		model.put("discription", dispatchDTO.getDescription());
		model.put("location", "Pune");
		model.put("signature", "www.NextechServices.in");
		mail.setModel(model);
		mailService.sendEmail(mail, notificationDTO);
	}
	
	public void downloadPDF(HttpServletRequest request, HttpServletResponse response,ProductOrderDTO productorder,List<DispatchProductDTO> dispatchProductDTOs,ClientDTO client,DispatchDTO dispatchDTO) throws IOException {

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

	private ByteArrayOutputStream convertPDFToByteArrayOutputStream(String fileName,HttpServletRequest request, HttpServletResponse response,ProductOrderDTO productorder,List<DispatchProductDTO> dispatchProductDTOs,DispatchDTO dispatchDTO) throws Exception {


		StatusDTO status = statusService.getStatusById(productorder.getStatusId().getId());
		ClientDTO client = clientService.getClientDTOById(productorder.getClientId().getId());

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
