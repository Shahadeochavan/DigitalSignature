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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
import com.nextech.erp.dto.RMOrderPdf;
import com.nextech.erp.dto.Mail;
import com.nextech.erp.dto.ProductOrderDTO;
import com.nextech.erp.dto.ProductRMAssociationDTO;
import com.nextech.erp.dto.RMInventoryDTO;
import com.nextech.erp.dto.RMOrderModelData;
import com.nextech.erp.dto.RMReqirementDTO;
import com.nextech.erp.dto.RawmaterialOrderDTO;
import com.nextech.erp.factory.RMOrderRequestResponseFactory;
import com.nextech.erp.model.Productinventory;
import com.nextech.erp.newDTO.NotificationDTO;
import com.nextech.erp.newDTO.ProductOrderAssociationDTO;
import com.nextech.erp.newDTO.RMOrderAssociationDTO;
import com.nextech.erp.newDTO.RMVendorAssociationDTO;
import com.nextech.erp.newDTO.RawMaterialDTO;
import com.nextech.erp.newDTO.StatusDTO;
import com.nextech.erp.newDTO.VendorDTO;
import com.nextech.erp.service.MailService;
import com.nextech.erp.service.NotificationService;
import com.nextech.erp.service.NotificationUserAssociationService;
import com.nextech.erp.service.ProductRMAssoService;
import com.nextech.erp.service.ProductinventoryService;
import com.nextech.erp.service.ProductorderService;
import com.nextech.erp.service.ProductorderassociationService;
import com.nextech.erp.service.RMVAssoService;
import com.nextech.erp.service.RawmaterialService;
import com.nextech.erp.service.RawmaterialinventoryService;
import com.nextech.erp.service.RawmaterialorderService;
import com.nextech.erp.service.RawmaterialorderassociationService;
import com.nextech.erp.service.StatusService;
import com.nextech.erp.service.UserService;
import com.nextech.erp.service.VendorService;
import com.nextech.erp.status.Response;
import com.nextech.erp.status.UserStatus;

@Controller
@Transactional @RequestMapping("/rawmaterialorder")
public class RawmaterialorderController {

	@Autowired
	RawmaterialorderService rawmaterialorderService;

	@Autowired
	RawmaterialorderassociationService rawmaterialorderassociationService;
	
	@Autowired
	RawmaterialinventoryService rawmaterialinventoryService;
	
	@Autowired
	ProductorderassociationService productorderassociationService;
	
	@Autowired
	ProductorderService productorderService;
	
	@Autowired
	ProductinventoryService productinventoryService;
	
	@Autowired
	ProductRMAssoService productRMAssoService;

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


	@Transactional @RequestMapping(value = "/createMultiple", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addMultipleRawMaterialOrder(
			@Valid @RequestBody RawmaterialOrderDTO rawmaterialOrderDTO, BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
			}
			//TODO save call raw material order
			if(rawmaterialOrderDTO.getRmOrderAssociationDTOs().isEmpty()){
				return  new UserStatus(0,"In RM Order data is Empty !Please dont send Empty data");
			}
			RawmaterialOrderDTO rawmaterialOrderDTO2	= rawmaterialorderService.addMultipleRawMaterialOrder(rawmaterialOrderDTO, request, response);
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
	public @ResponseBody Response getRawmaterialorder(
			@PathVariable("id") long id) {
		RawmaterialOrderDTO rawmaterialorder = null;
		try {
			rawmaterialorder = rawmaterialorderService.getRMOrderById(id);
			if(rawmaterialorder==null){
				return new Response(1,"There is no rm order");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,rawmaterialorder);
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
	public @ResponseBody Response getRawmaterialorder() {
		List<RawmaterialOrderDTO> rawmaterialorderList = null;
		try {
			rawmaterialorderList = rawmaterialorderService.getRMOrderList();
			if(rawmaterialorderList==null){
				return new Response(1,"There is no rm order list");
			}
			
			//getRequiredRMList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,rawmaterialorderList);
	}
	
	@Transactional @RequestMapping(value = "/list/securityCheck", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getRMOrderForSecurityCheck() {
		List<RawmaterialOrderDTO> rawmaterialorderList = null;
		try {
			rawmaterialorderList = rawmaterialorderService
					.getRawmaterialorderByStatusId(Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_INVOICE_IN, null, null)),
							Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_RAW_MATERIAL_INPROCESS, null, null))
							,Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_RAW_MATERIAL_INCOMPLETE, null, null)));
			if(rawmaterialorderList==null){
				return new Response(1,"There is no rm order list");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,rawmaterialorderList);
	}

	@Transactional @RequestMapping(value = "/list/qualityCheck", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getRMOrderForQualityCheck() {
		List<RawmaterialOrderDTO> rawmaterialorderList = null;
		try {
			rawmaterialorderList = rawmaterialorderService
					.getRawmaterialorderByQualityCheckStatusId(Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_QUALITY_CHECK, null, null)));
			if(rawmaterialorderList==null){
				return new Response(1,"There is no rm order list");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,rawmaterialorderList);
	}

	@Transactional @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteRawmaterialorder(
			@PathVariable("id") long id) {
		try {
			RawmaterialOrderDTO rawmaterialOrderDTO =	rawmaterialorderService.deleteRMOrder(id);
			if(rawmaterialOrderDTO==null){
				return new Response(1,"There is no rm order");
			}
			return new Response(1, "Rawmaterial Order deleted Successfully !");
		} catch (Exception e) {
			return new Response(0, e.toString());
		}
	}
	
	@Transactional @RequestMapping(value = "getVendorOrder/{VENDOR-ID}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getRawmaterialorderVendor(@PathVariable("VENDOR-ID") long vendorId) {
		List<RawmaterialOrderDTO> rawmaterialorderList = null;
		try {
			rawmaterialorderList = rawmaterialorderService.getRawmaterialorderByVendor(vendorId);
			if(rawmaterialorderList==null){
				return new Response(1,"There is no rm order list");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,rawmaterialorderList);
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
			rmOrderModelData.setCgst(rawmaterialvendorassociation.getTaxStructureDTO().getCgst());
			rmOrderModelData.setSgst(rawmaterialvendorassociation.getTaxStructureDTO().getSgst());
			rmOrderModelData.setIgst(rawmaterialvendorassociation.getTaxStructureDTO().getIgst());
			rmOrderModelData.setDescription(rawmaterial.getDescription());
			rmOrderModelDatas.add(rmOrderModelData);
		}
		//downloadPDF(request, response, rawmaterialOrderDTO,rmOrderModelDatas,vendor);
	}
	
	public void downloadPDF(HttpServletRequest request, HttpServletResponse response,RawmaterialOrderDTO rawmaterialOrderDTO,List<RMOrderModelData> rmOrderModelDatas,VendorDTO vendor) throws IOException {
		final ServletContext servletContext = request.getSession().getServletContext();
	    final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
	    final String temperotyFilePath = tempDirectory.getAbsolutePath();
	    String fileName = "RMOrder.pdf";
	    response.setContentType("application/pdf");
	    response.setHeader("Content-disposition", "attachment; filename="+ fileName);
	    try {
	    	RMOrderPdf createPDF = new RMOrderPdf();
	    	createPDF.createPDF(temperotyFilePath+"\\"+fileName,rawmaterialOrderDTO,rmOrderModelDatas,vendor);
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        baos = convertPDFToByteArrayOutputStream(temperotyFilePath+"\\"+fileName,rawmaterialOrderDTO,rmOrderModelDatas);
	        OutputStream os = response.getOutputStream();
	        baos.writeTo(os);
	        os.flush();
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
	Mail mail =  userService.emailNotification(notification);
	 String userEmailCC = mail.getMailCc()+","+vendor.getEmail();
	    mail.setMailCc(userEmailCC);
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
	
	@Transactional @RequestMapping(value = "/getRequiredRMList", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getRequiredRMList() {
		try{
		String message = messageSource.getMessage(ERPConstants.STATUS_PRODUCT_ORDER_COMPLETE, null, null);	
		String messageNewOrder = messageSource.getMessage(ERPConstants.STATUS_NEW_PRODUCT_ORDER, null,null);
		Long completeOrderStatus = Long.parseLong(message);	
		Long newOrderStatus =Long.parseLong(messageNewOrder);
		List<ProductOrderDTO> incompleteOrders = productorderService.getNewAndInCompleteProductOrders(completeOrderStatus,newOrderStatus);
		Map<Long, Long> productQuantityMap = new HashMap<Long, Long>();
		for (Iterator<ProductOrderDTO> iterator = incompleteOrders.iterator(); iterator
				.hasNext();) {
			ProductOrderDTO productOrderDTO = (ProductOrderDTO) iterator.next();
			//Productorderassociation productorderassociation = productorderassociationService.getProductAssoByOrder(productOrderDTO.getId());
			List<ProductOrderAssociationDTO> productOrderAssociationDTOs = productorderassociationService.getProductorderassociationByOrderId(productOrderDTO.getId());
			for (ProductOrderAssociationDTO productOrderAssociationDTO : productOrderAssociationDTOs) {
			if(productOrderAssociationDTO !=null){
			Long productId = productOrderAssociationDTO.getProductId().getId();
			if(productQuantityMap.containsKey(productId)){
				productQuantityMap.put(productId, (productQuantityMap.get(productId) + productOrderAssociationDTO.getRemainingQuantity()));
			}else {
				productQuantityMap.put(productId, productOrderAssociationDTO.getRemainingQuantity());
			}
		}else{
			return  new Response(1,"There is no product order assocition");
		}
		}
		}
		
		Set<Entry<Long, Long>> entries = productQuantityMap.entrySet();
		Map<Long, Long> rmQuantityMap = new HashMap<Long, Long>();
		for (Iterator<Entry<Long, Long>> iterator = entries.iterator(); iterator.hasNext();) {
			Entry<Long, Long> entry = (Entry<Long, Long>) iterator.next();
			Long productId = entry.getKey();
			Productinventory productinventory = productinventoryService.getProductinventoryByProductId(productId);
			Long productInventoryQty = productinventory == null ? 0 : productinventory.getQuantityavailable();
			Long actualRequiredQuantity = entry.getValue() - productInventoryQty;
			List<ProductRMAssociationDTO> productRMList = productRMAssoService.getProductRMAssoList(productId);
			if(productRMList !=null){
			for (Iterator<ProductRMAssociationDTO> iterator2 = productRMList.iterator(); iterator2
					.hasNext();) {
				ProductRMAssociationDTO productRMAssociationDTO = (ProductRMAssociationDTO) iterator2
						.next();
				Long rmId = productRMAssociationDTO.getRawmaterialId().getId();
				if(rmQuantityMap.containsKey(rmId)){
					rmQuantityMap.put(rmId, (rmQuantityMap.get(rmId) + (productRMAssociationDTO.getQuantity()* actualRequiredQuantity)));
				}else {
					rmQuantityMap.put(rmId, (productRMAssociationDTO.getQuantity()* actualRequiredQuantity));
				}
			}
			}else{
				return  new Response(1,"There is no any product RM List");
			}
		}
		List<RMReqirementDTO> rmReqirementDTOs = new ArrayList<RMReqirementDTO>();
		Set<Entry<Long, Long>> rmQuantityEntries = rmQuantityMap.entrySet();
		if(rmQuantityEntries !=null){
	
		for (Iterator<Entry<Long, Long>> iterator = rmQuantityEntries.iterator(); iterator
				.hasNext();) {
			Entry<Long, Long> rmQtyentry = (Entry<Long, Long>) iterator.next();
			RMInventoryDTO rmInventoryDTO = rawmaterialinventoryService.getByRMId(rmQtyentry.getKey());
			RMVendorAssociationDTO rmVendorAssociationDTO = rmvAssoService.getRMVAssoByRMId(rmQtyentry.getKey());
			if(rmVendorAssociationDTO !=null){
			VendorDTO vendorDTO =  vendorService.getVendorById(rmVendorAssociationDTO.getVendorId().getId());
			RMReqirementDTO rmReqirementDTO = new RMReqirementDTO();
			rmReqirementDTO.setRmId(rmQtyentry.getKey());
			rmReqirementDTO.setRmPartNumber(rmInventoryDTO.getRmPartNumber());
			rmReqirementDTO.setRequiredQuantity(rmQtyentry.getValue()-rmInventoryDTO.getQuantityAvailable()+rmInventoryDTO.getMinimumQuantity());
			rmReqirementDTO.setInventoryQuantity(rmInventoryDTO.getQuantityAvailable());
			rmReqirementDTO.setMinimumQuantity(rmInventoryDTO.getMinimumQuantity());
			rmReqirementDTO.setVendorId(vendorDTO.getId());
			rmReqirementDTO.setCompanyName(vendorDTO.getCompanyName());
	
			System.out.println("rmid : " + rmQtyentry.getKey() + " reqQty : " + rmQtyentry.getValue() + " invQty : " + rmReqirementDTO.getInventoryQuantity());
			if(rmQtyentry.getValue() > rmInventoryDTO.getQuantityAvailable()){
				rmReqirementDTOs.add(rmReqirementDTO);
			}}
			else{
				return new Response(1,"Please create rm ventor assocition");
			}
		}
		}else{
			return new Response(1,"There is no any requirmemt");
		}
		return new Response(0, "Success", rmReqirementDTOs);
		}catch(Exception ex){
			ex.printStackTrace();
			return new Response(1, "Error", null);
		}
	}
	
}
