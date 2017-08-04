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
import com.nextech.erp.dto.CreatePDFProductOrder;
import com.nextech.erp.dto.Mail;
import com.nextech.erp.dto.RMOrderModelData;
import com.nextech.erp.dto.RawmaterialOrderDTO;
import com.nextech.erp.factory.RMOrderRequestResponseFactory;
import com.nextech.erp.newDTO.NotificationDTO;
import com.nextech.erp.newDTO.NotificationUserAssociatinsDTO;
import com.nextech.erp.newDTO.RMOrderAssociationDTO;
import com.nextech.erp.newDTO.RMVendorAssociationDTO;
import com.nextech.erp.newDTO.RawMaterialDTO;
import com.nextech.erp.newDTO.StatusDTO;
import com.nextech.erp.newDTO.UserDTO;
import com.nextech.erp.newDTO.VendorDTO;
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

@Controller
@Transactional @RequestMapping("/rawmaterialorder")
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

	@Transactional @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addRawmaterialorder(
			@Valid @RequestBody RawmaterialOrderDTO rawmaterialOrderDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			rawmaterialOrderDTO.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			
			rawmaterialorderService.addEntity(RMOrderRequestResponseFactory.setRMOrder(rawmaterialOrderDTO));
			//TODO WHy this update
			rawmaterialorderService.updateEntity(RMOrderRequestResponseFactory.setRMOrder(rawmaterialOrderDTO));
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

	@Transactional @RequestMapping(value = "/createmultiple", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addMultipleRawmaterialorder(
			@Valid @RequestBody RawmaterialOrderDTO rawmaterialOrderDTO, BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
			}
			//TODO save call raw material order
			RawmaterialOrderDTO rawmaterialOrderDTO2	= rawmaterialorderService.saveRMOrder(rawmaterialOrderDTO, request, response);
			rawmaterialOrderDTO.setId(rawmaterialOrderDTO2.getId());
			rawmaterialOrderDTO.setStatusId(rawmaterialOrderDTO2.getStatusId());
			addRMOrderAsso(rawmaterialOrderDTO, request, response);
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

	@Transactional @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody RawmaterialOrderDTO getRawmaterialorder(
			@PathVariable("id") long id) {
		RawmaterialOrderDTO rawmaterialorder = null;
		try {
			rawmaterialorder = rawmaterialorderService.getRMOrderById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rawmaterialorder;
	}

	@Transactional @RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateRawmaterialorder(
			@RequestBody RawmaterialOrderDTO rawmaterialOrderDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			rawmaterialorderService.updateRMOrder(rawmaterialOrderDTO, request, response);
			return new UserStatus(1, "Rawmaterial Order update Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@Transactional @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<RawmaterialOrderDTO> getRawmaterialorder() {
		List<RawmaterialOrderDTO> rawmaterialorderList = null;
		try {
			rawmaterialorderList = rawmaterialorderService.getRMOrderList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rawmaterialorderList;
	}
	
	@Transactional @RequestMapping(value = "/list/securityCheck", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<RawmaterialOrderDTO> getRMOrderForSecurityCheck() {
		List<RawmaterialOrderDTO> rawmaterialorderList = null;
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

	@Transactional @RequestMapping(value = "/list/qualityCheck", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<RawmaterialOrderDTO> getRMOrderForQualityCheck() {
		List<RawmaterialOrderDTO> rawmaterialorderList = null;
		try {
			rawmaterialorderList = rawmaterialorderService
					.getRawmaterialorderByQualityCheckStatusId(Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_QUALITY_CHECK, null, null)));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rawmaterialorderList;
	}

	@Transactional @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteRawmaterialorder(
			@PathVariable("id") long id) {
		try {
			rawmaterialorderService.deleteRMOrder(id);
			return new UserStatus(1, "Rawmaterial Order deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}
	}
	
	@Transactional @RequestMapping(value = "getVendorOrder/{VENDOR-ID}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<RawmaterialOrderDTO> getRawmaterialorderVendor(@PathVariable("VENDOR-ID") long vendorId) {
		List<RawmaterialOrderDTO> rawmaterialorderList = null;
		try {
			rawmaterialorderList = rawmaterialorderService.getRawmaterialorderByVendor(vendorId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rawmaterialorderList;
	}

	private void addRMOrderAsso(RawmaterialOrderDTO rawmaterialOrderDTO,HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<RMOrderAssociationDTO> rmOrderAssociationDTOs = rawmaterialOrderDTO.getRmOrderAssociationDTOs();
		List<RMOrderModelData> rmOrderModelDatas = new ArrayList<RMOrderModelData>();
		VendorDTO vendor = vendorService.getVendorById(rawmaterialOrderDTO.getVendorId().getId());
		if(rmOrderAssociationDTOs !=null && !rmOrderAssociationDTOs.isEmpty()){
			for (RMOrderAssociationDTO rmOrderAssociationDTO : rmOrderAssociationDTOs) {
				rmOrderAssociationDTO.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
				rawmaterialorderassociationService.addEntity(RMOrderRequestResponseFactory.setRMOrderAssociation(rawmaterialOrderDTO, rmOrderAssociationDTO));
			}
		}
		for (RMOrderAssociationDTO rmOrderAssociationDTO : rmOrderAssociationDTOs){
			RMOrderModelData rmOrderModelData = new RMOrderModelData();
			RawMaterialDTO rawmaterial = rawmaterialService.getRMDTO(rmOrderAssociationDTO.getRawmaterialId().getId());
			RMVendorAssociationDTO rawmaterialvendorassociation = rmvAssoService.getRMVAssoByRMId(rawmaterial.getId());
			rmOrderModelData.setRmName(rawmaterial.getPartNumber());
			rmOrderModelData.setQuantity(rmOrderAssociationDTO.getQuantity());
			rmOrderModelData.setPricePerUnit(rawmaterialvendorassociation.getPricePerUnit());
			rmOrderModelData.setAmount(rawmaterialvendorassociation.getPricePerUnit()*rmOrderAssociationDTO.getQuantity());
			rmOrderModelData.setTax(rawmaterialOrderDTO.getTax());
			rmOrderModelData.setDescription(rawmaterial.getDescription());
			rmOrderModelDatas.add(rmOrderModelData);
		}
		downloadPDF(request, response, rawmaterialOrderDTO,rmOrderModelDatas,vendor);
	}
	
	public void downloadPDF(HttpServletRequest request, HttpServletResponse response,RawmaterialOrderDTO rawmaterialOrderDTO,List<RMOrderModelData> rmOrderModelDatas,VendorDTO vendor) throws IOException {
		final ServletContext servletContext = request.getSession().getServletContext();
	    final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
	    final String temperotyFilePath = tempDirectory.getAbsolutePath();
	    String fileName = "RMOrder.pdf";
	    response.setContentType("application/pdf");
	    response.setHeader("Content-disposition", "attachment; filename="+ fileName);
	    try {
	    	CreatePDFProductOrder createPDF = new CreatePDFProductOrder();
	    	createPDF.createPDF(temperotyFilePath+"\\"+fileName,rawmaterialOrderDTO,rmOrderModelDatas,vendor);
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        baos = convertPDFToByteArrayOutputStream(temperotyFilePath+"\\"+fileName,rawmaterialOrderDTO,rmOrderModelDatas);
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
	
	private ByteArrayOutputStream convertPDFToByteArrayOutputStream(String fileName,RawmaterialOrderDTO rawmaterialOrderDTO,List<RMOrderModelData> rmOrderModelDatas) throws Exception {
		StatusDTO status = statusService.getStatusById(rawmaterialOrderDTO.getStatusId().getId());
		NotificationDTO notification = notificationService.getNotifiactionByStatus(status.getId());
		VendorDTO vendor = vendorService.getVendorById(rawmaterialOrderDTO.getVendorId().getId());
		//TODO mail sending
        mailSending(notification, rawmaterialOrderDTO, vendor,fileName,rmOrderModelDatas);
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

	private void mailSending(NotificationDTO notification,RawmaterialOrderDTO rawmaterialOrderDTO,VendorDTO vendor,String fileName,List<RMOrderModelData> rmOrderModelDatas) throws Exception{
		List<NotificationUserAssociatinsDTO> notificationuserassociations = notificationUserAssociationService.getNotificationUserAssociatinsDTOs(notification.getId());
		Mail mail = new Mail();
		for (NotificationUserAssociatinsDTO notificationuserassociation : notificationuserassociations) {
			UserDTO user = userService.getUserDTO(notificationuserassociation.getUserId().getId());
			if(notificationuserassociation.getTo()==true){
				mail.setMailTo(vendor.getEmail());
			}else if(notificationuserassociation.getBcc()==true){
				mail.setMailBcc(user.getEmailId());
			}else if(notificationuserassociation.getCc()==true){
				mail.setMailCc(user.getEmailId());
			}
		}
		mail.setMailSubject(notification.getSubject());
		mail.setAttachment(fileName);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("firstName", vendor.getFirstName());
		model.put("lastName", vendor.getLastName());
		model.put("location", "Pune");
		model.put("rmOrderModelDatas", rmOrderModelDatas);
		model.put("address", vendor.getAddress());
		model.put("companyName", vendor.getCompanyName());
		model.put("tax", rawmaterialOrderDTO.getTax());
		model.put("mailFrom", notification.getName());
		model.put("signature", "www.NextechServices.in");
		mail.setModel(model);
		mailService.sendEmail(mail,notification);
	}
}