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
import com.nextech.erp.dto.CreatePDFProductOrder;
import com.nextech.erp.dto.Mail;
import com.nextech.erp.dto.RMOrderModelData;
import com.nextech.erp.dto.RawmaterialOrderAssociationModel;
import com.nextech.erp.model.Notification;
import com.nextech.erp.model.Notificationuserassociation;
import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.model.Rawmaterialorder;
import com.nextech.erp.model.Rawmaterialorderassociation;
import com.nextech.erp.model.Rawmaterialvendorassociation;
import com.nextech.erp.model.Status;
import com.nextech.erp.model.User;
import com.nextech.erp.model.Vendor;
import com.nextech.erp.service.MailService;
import com.nextech.erp.service.NotificationService;
import com.nextech.erp.service.NotificationUserAssociationService;
import com.nextech.erp.service.RMVAssoService;
import com.nextech.erp.service.RawmaterialService;
import com.nextech.erp.service.RawmaterialorderService;
import com.nextech.erp.service.RawmaterialorderassociationService;
import com.nextech.erp.service.StatusService;
import com.nextech.erp.service.UserService;
import com.nextech.erp.service.VendorService;
import com.nextech.erp.status.UserStatus;
import com.sun.corba.se.spi.servicecontext.UEInfoServiceContext;

@Controller
@RequestMapping("/rawmaterialorder")
public class RawmaterialorderController {

	@Autowired
	RawmaterialorderService rawmaterialorderService;

	@Autowired
	RawmaterialorderassociationService rawmaterialorderassociationService;

	@Autowired
	StatusService statusService;

	@Autowired
	VendorService vendorService;

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
	RawmaterialService rawmaterialService;
	
	@Autowired 
	RMVAssoService rmvAssoService;

	private String generateInvoiceId(){
		String year="";
		Date currentDate = new Date();
		if(currentDate.getMonth()+1 > 3){
			int str = currentDate.getYear()+1900;
			int stri = str + 1;
			String strDate = stri+"";
			year = str+"/"+strDate.substring(2);
		}else{
			int str = currentDate.getYear()+1899;
			int stri = str + 1;
			String strDate = stri+"";
			year = str+"/"+strDate.substring(2);
		}
		year = "EK/PUN/"+year+"/";
		return year;
	}
	String s;
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addRawmaterialorder(
			@Valid @RequestBody Rawmaterialorder rawmaterialorder,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			rawmaterialorder.setIsactive(true);
			rawmaterialorder.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			
			Long id = rawmaterialorderService.addEntity(rawmaterialorder);
			String invoiceId = generateInvoiceId()+id;
			rawmaterialorder.setName(invoiceId);
			rawmaterialorderService.updateEntity(rawmaterialorder);
			return new UserStatus(1, "Rawmaterialorder added Successfully !");
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

	@RequestMapping(value = "/createmultiple", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addMultipleRawmaterialorder(
			@Valid @RequestBody RawmaterialOrderAssociationModel rawmaterialOrderAssociationModel, BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
			}
			//TODO save call raw material order
			Rawmaterialorder rawmaterialorder = saveRMOrder(rawmaterialOrderAssociationModel, request, response);
			String invoiceId = generateInvoiceId()+rawmaterialorder.getId();
			rawmaterialorder.setName(invoiceId);
			rawmaterialorderService.updateEntity(rawmaterialorder);
			//TODO add raw material association
			addRMOrderAsso(rawmaterialorder,rawmaterialOrderAssociationModel, request, response);

			return new UserStatus(1, "Multiple Rawmaterial Order added Successfully !");
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
	public @ResponseBody Rawmaterialorder getRawmaterialorder(
			@PathVariable("id") long id) {
		Rawmaterialorder rawmaterialorder = null;
		try {
			rawmaterialorder = rawmaterialorderService
					.getEntityById(Rawmaterialorder.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rawmaterialorder;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateRawmaterialorder(
			@RequestBody Rawmaterialorder rawmaterialorder,HttpServletRequest request,HttpServletResponse response) {
		try {
			rawmaterialorder.setStatus(statusService.getEntityById(Status.class,Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_NEW_RM_ORDER, null, null))));
			rawmaterialorder.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			rawmaterialorder.setIsactive(true);
			rawmaterialorderService.updateEntity(rawmaterialorder);
			return new UserStatus(1, "Rawmaterial Order update Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Rawmaterialorder> getRawmaterialorder() {

		List<Rawmaterialorder> rawmaterialorderList = null;
		try {
			rawmaterialorderList = rawmaterialorderService
					.getEntityList(Rawmaterialorder.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rawmaterialorderList;
	}
	@RequestMapping(value = "/list/securityCheck", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Rawmaterialorder> getRMOrderForSecurityCheck() {

		List<Rawmaterialorder> rawmaterialorderList = null;
		try {
			rawmaterialorderList = rawmaterialorderService
					.getRawmaterialorderByStatusId(Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_INVOICE_IN, null, null)),
							Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_RAW_MATERIAL_INPROCESS, null, null))
							,Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_RAW_MATERIAL_INCOMPLETE, null, null)));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rawmaterialorderList;
	}

	@RequestMapping(value = "/list/qualityCheck", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Rawmaterialorder> getRMOrderForQualityCheck() {

		List<Rawmaterialorder> rawmaterialorderList = null;
		try {
			rawmaterialorderList = rawmaterialorderService
					.getRawmaterialorderByQualityCheckStatusId(Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_QUALITY_CHECK, null, null)));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rawmaterialorderList;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteRawmaterialorder(
			@PathVariable("id") long id) {

		try {
			Rawmaterialorder rawmaterialorder = rawmaterialorderService
					.getEntityById(Rawmaterialorder.class,id);
			rawmaterialorder.setIsactive(false);
			rawmaterialorderService.updateEntity(rawmaterialorder);
			return new UserStatus(1, "Rawmaterial Order deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}

	}
	@RequestMapping(value = "getVendorOrder/{VENDOR-ID}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Rawmaterialorder> getRawmaterialorderVendor(@PathVariable("VENDOR-ID") long vendorId) {

		List<Rawmaterialorder> rawmaterialorderList = null;
		try {
			rawmaterialorderList = rawmaterialorderService.getRawmaterialorderByVendor(vendorId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rawmaterialorderList;
	}

	private Rawmaterialorder  saveRMOrder(RawmaterialOrderAssociationModel rawmaterialOrderAssociationModel,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Rawmaterialorder rawmaterialorder= new Rawmaterialorder();
		rawmaterialorder.setCreateDate(new Date());
		rawmaterialorder.setDescription(rawmaterialOrderAssociationModel.getDescription());
    	rawmaterialorder.setExpectedDeliveryDate(rawmaterialOrderAssociationModel.getExpecteddeliveryDate());
		rawmaterialorder.setQuantity(rawmaterialOrderAssociationModel.getRawmaterialorderassociations().size());
		rawmaterialorder.setStatus(statusService.getEntityById(Status.class,Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_NEW_RM_ORDER, null, null))));
		rawmaterialorder.setVendor(vendorService.getEntityById(Vendor.class,rawmaterialOrderAssociationModel.getVendor()));
		rawmaterialorder.setName(rawmaterialOrderAssociationModel.getName());
		rawmaterialorder.setActualPrice(rawmaterialOrderAssociationModel.getActualPrice());
		rawmaterialorder.setOtherCharges(rawmaterialOrderAssociationModel.getOtherCharges());
		rawmaterialorder.setTax(rawmaterialOrderAssociationModel.getTax());
		rawmaterialorder.setTotalprice(rawmaterialOrderAssociationModel.getTotalprice());
		//rawmaterialorder.setRemainingQuantity(rawmaterialOrderAssociationModel.getRawmaterialorderassociations());
		rawmaterialorder.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		rawmaterialorder.setIsactive(true);
		long id=rawmaterialorderService.addEntity(rawmaterialorder);
		System.out.println("id is"+id);
		//TODO Create PDF file
		//downloadPDF(request, response, rawmaterialorder);

		return rawmaterialorder;
	}

	private void addRMOrderAsso(Rawmaterialorder rawmaterialorder,RawmaterialOrderAssociationModel rawmaterialOrderAssociationModel,HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<Rawmaterialorderassociation> rawmaterialorderassociations = rawmaterialOrderAssociationModel.getRawmaterialorderassociations();
		List<RMOrderModelData> rmOrderModelDatas = new ArrayList<RMOrderModelData>();
		
		Vendor vendor = vendorService.getEntityById(Vendor.class, rawmaterialOrderAssociationModel.getVendor());
		
		if(rawmaterialorderassociations !=null && !rawmaterialorderassociations.isEmpty()){
			for (Rawmaterialorderassociation rawmaterialorderassociation : rawmaterialorderassociations) {
				rawmaterialorderassociation.setRawmaterialorder(rawmaterialorder);
				rawmaterialorderassociation.setIsactive(true);
				rawmaterialorderassociation.setRemainingQuantity(rawmaterialorderassociation.getQuantity());
				rawmaterialorderassociation.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
				rawmaterialorderassociationService.addEntity(rawmaterialorderassociation);
			}
		}
		
		for (Rawmaterialorderassociation rawmaterialorderassociation : rawmaterialorderassociations){
			RMOrderModelData rmOrderModelData = new RMOrderModelData();
			Rawmaterial rawmaterial = rawmaterialService.getEntityById(Rawmaterial.class, rawmaterialorderassociation.getRawmaterial().getId());
			Rawmaterialvendorassociation rawmaterialvendorassociation = rmvAssoService.getRMVAssoByRMId(rawmaterial.getId());
			rmOrderModelData.setRmName(rawmaterial.getPartNumber());
			rmOrderModelData.setQuantity(rawmaterialorderassociation.getQuantity());
			rmOrderModelData.setPricePerUnit(rawmaterialvendorassociation.getPricePerUnit());
			rmOrderModelData.setAmount(rawmaterialvendorassociation.getPricePerUnit()*rawmaterialorderassociation.getQuantity());
			rmOrderModelData.setTax(rawmaterialorder.getTax());
			rmOrderModelData.setDescription(rawmaterialorder.getName());
			rmOrderModelDatas.add(rmOrderModelData);
		}
		downloadPDF(request, response, rawmaterialorder,rmOrderModelDatas,vendor);
	}
	public void downloadPDF(HttpServletRequest request, HttpServletResponse response,Rawmaterialorder rawmaterialorder,List<RMOrderModelData> rmOrderModelDatas,Vendor vendor) throws IOException {

		final ServletContext servletContext = request.getSession().getServletContext();
	    final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
	    final String temperotyFilePath = tempDirectory.getAbsolutePath();

	    String fileName = "RMOrder.pdf";
	    response.setContentType("application/pdf");
	    response.setHeader("Content-disposition", "attachment; filename="+ fileName);

	    try {

	   CreatePDFProductOrder createPDF = new CreatePDFProductOrder();
	   createPDF.createPDF(temperotyFilePath+"\\"+fileName,rawmaterialorder,rmOrderModelDatas,vendor);
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        baos = convertPDFToByteArrayOutputStream(temperotyFilePath+"\\"+fileName,rawmaterialorder,rmOrderModelDatas);
	        OutputStream os = response.getOutputStream();
	        baos.writeTo(os);
	        os.flush();

	    /*	Status status = statusService.getEntityById(Status.class, rawmaterialorder.getStatus().getId());
			Notification notification = notificationService.getEntityById(Notification.class, status.getNotifications1().size());
			Vendor vendor = vendorService.getEntityById(Vendor.class,rawmaterialorder.getVendor().getId());
			//TODO mail sending
	        mailSending(notification, rawmaterialorder, vendor,temperotyFilePath);*/
	    } catch (Exception e1) {
	        e1.printStackTrace();
	    }

	}
	
	private ByteArrayOutputStream convertPDFToByteArrayOutputStream(String fileName,Rawmaterialorder rawmaterialorder,List<RMOrderModelData> rmOrderModelDatas) throws Exception {

		Status status = statusService.getEntityById(Status.class, rawmaterialorder.getStatus().getId());
		Notification notification = notificationService.getNotifiactionByStatus(status.getId());
		Vendor vendor = vendorService.getEntityById(Vendor.class,rawmaterialorder.getVendor().getId());
		//TODO mail sending
        mailSending(notification, rawmaterialorder, vendor,fileName,rmOrderModelDatas);

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

	private void mailSending(Notification notification,Rawmaterialorder rawmaterialorder,Vendor vendor,String fileName,List<RMOrderModelData> rmOrderModelDatas) throws Exception{
		List<Notificationuserassociation> notificationuserassociations = notificationUserAssociationService.getNotificationuserassociationBynotificationId(notification.getId());
		  Mail mail = new Mail();
		  for (Notificationuserassociation notificationuserassociation : notificationuserassociations) {
			  User user = userService.getEmailUserById(notificationuserassociation.getUser().getId());
			  if(notificationuserassociation.getTo()==true){
				   mail.setMailTo(vendor.getEmail());
			  }else if(notificationuserassociation.getBcc()==true){
				  mail.setMailBcc(user.getEmail());
			  }else if(notificationuserassociation.getCc()==true){
				  mail.setMailCc(user.getEmail());
			  }
			  
		}
	        mail.setMailSubject(notification.getSubject());
	        mail.setAttachment(fileName);
	        Map < String, Object > model = new HashMap < String, Object > ();
	        model.put("firstName", vendor.getFirstName());
	        model.put("lastName", vendor.getLastName());
	        model.put("location", "Pune");
	        model.put("rmOrderModelDatas",rmOrderModelDatas);
	        model.put("address", vendor.getAddress());
	        model.put("companyName", vendor.getCompanyName());
	        model.put("tax", rawmaterialorder.getTax());
	        model.put("mailFrom", notification.getName());
	        model.put("signature", "www.NextechServices.in");
	        mail.setModel(model);

		mailService.sendEmail(mail,notification);
	}
}
