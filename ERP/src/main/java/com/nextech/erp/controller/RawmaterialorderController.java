package com.nextech.erp.controller;


import java.io.File;
import java.io.IOException;
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

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dto.MultipleRMOrderDTO;
import com.nextech.erp.dto.RMOrderPdf;
import com.nextech.erp.dto.Mail;
import com.nextech.erp.dto.ProductOrderDTO;
import com.nextech.erp.dto.ProductRMAssociationDTO;
import com.nextech.erp.dto.RMInventoryDTO;
import com.nextech.erp.dto.RMOrderModelData;
import com.nextech.erp.dto.RMReqirementDTO;
import com.nextech.erp.dto.RawmaterialOrderDTO;
import com.nextech.erp.factory.MailResponseRequestFactory;
import com.nextech.erp.factory.RMOrderRequestResponseFactory;
import com.nextech.erp.model.Productinventory;
import com.nextech.erp.model.Vendor;
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
import com.nextech.erp.util.PDFToByteArrayOutputStreamUtil;

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

	static Logger logger = Logger.getLogger(RawmaterialorderController.class);
	
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
			rawmaterialOrderDTO.setName(rawmaterialOrderDTO2.getName());
			addRMOrderAsso(rawmaterialOrderDTO, request, response);
			return new UserStatus(1, "Multiple Rawmaterial Order added Successfully !");
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
	public @ResponseBody Response getRawmaterialorder(
			@PathVariable("id") long id) {
		RawmaterialOrderDTO rawmaterialorder = null;
		try {
			rawmaterialorder = rawmaterialorderService.getRMOrderById(id);
			if(rawmaterialorder==null){
				logger.error("There is no rm order list");
				return new Response(1,"There is no rm order");
			}
		} catch (Exception e) {
			logger.error(e);
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
			logger.error(e);
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
				logger.error("There is no rm order list");
				return new Response(1,"There is no rm order list");
			}
		} catch (Exception e) {
			logger.error(e);
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
				logger.error("There is no rm order list");
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
				logger.error("There is no rm order");
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
				logger.error("There is no rm order list");
				return new Response(1,"There is no rm order list");
			}
		} catch (Exception e) {
			logger.error(e);
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
		createRMOrderPdf(request, response, rawmaterialOrderDTO,rmOrderModelDatas,vendor);
	}
	
	public void createRMOrderPdf(HttpServletRequest request, HttpServletResponse response,RawmaterialOrderDTO rawmaterialOrderDTO,List<RMOrderModelData> rmOrderModelDatas,VendorDTO vendor) throws IOException {
		final ServletContext servletContext = request.getSession().getServletContext();
	    final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
	    final String temperotyFilePath = tempDirectory.getAbsolutePath();
	    String fileName = "RMOrder.pdf";
	    response.setContentType("application/pdf");
	    response.setHeader("Content-disposition", "attachment; filename="+ fileName);
	    try {
	    	RMOrderPdf createPDF = new RMOrderPdf();
	    	createPDF.createPDF(temperotyFilePath+"\\"+fileName,rawmaterialOrderDTO,rmOrderModelDatas,vendor);
	    	
	       String rmOrderPdffile =    PDFToByteArrayOutputStreamUtil.convertPDFToByteArrayOutputStream(temperotyFilePath+"\\"+fileName);
			StatusDTO status = statusService.getStatusById(rawmaterialOrderDTO.getStatusId().getId());
			NotificationDTO notification = notificationService.getNotifiactionByStatus(status.getId());
			emailNotificationRMOrder(notification, rawmaterialOrderDTO, vendor, rmOrderPdffile, rmOrderModelDatas);
	    } catch (Exception e1) {
	        e1.printStackTrace();
	    }
	}
	
	private void emailNotificationRMOrder(NotificationDTO notification,RawmaterialOrderDTO rawmaterialOrderDTO,VendorDTO vendor,String fileName,List<RMOrderModelData> rmOrderModelDatas) throws Exception{
		Mail mail = mailService.setMailCCBCCAndTO(notification);
	 String userEmailCC = mail.getMailCc()+","+vendor.getEmail();
	    mail.setMailCc(userEmailCC);
		mail.setMailSubject(notification.getSubject());
		mail.setAttachment(fileName);
		mail.setModel(MailResponseRequestFactory.setMailDetailsRMOrder(notification, rawmaterialOrderDTO, vendor, rmOrderModelDatas));
		mailService.sendEmail(mail,notification);
	}
	
	@Transactional @RequestMapping(value = "/getRequiredRMList", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getRequiredRMList() {
		try{
		String message = messageSource.getMessage(ERPConstants.STATUS_PRODUCT_ORDER_COMPLETE, null, null);	
		String messageNewOrder = messageSource.getMessage(ERPConstants.STATUS_NEW_PRODUCT_ORDER, null,null);
		Long completeOrderStatus = Long.parseLong(message);	
		Long newOrderStatus =Long.parseLong(messageNewOrder);
		List<RMReqirementDTO> rmReqirementDTOs = new ArrayList<RMReqirementDTO>();
		List<Long> rmIds = new ArrayList<Long>();
		List<ProductOrderDTO> newAndIncompleteOrders = productorderService.getNewAndInCompleteProductOrders(completeOrderStatus,newOrderStatus);
		if(newAndIncompleteOrders!=null){
		Map<Long, Long> productQuantityMap = new HashMap<Long, Long>();
		for (Iterator<ProductOrderDTO> iterator = newAndIncompleteOrders.iterator(); iterator
				.hasNext();) {
			ProductOrderDTO productOrderDTO = (ProductOrderDTO) iterator.next();
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
		Set<Entry<Long, Long>> rmQuantityEntries = rmQuantityMap.entrySet();
		if(rmQuantityEntries !=null){
	
		for (Iterator<Entry<Long, Long>> iterator = rmQuantityEntries.iterator(); iterator
				.hasNext();) {
			Entry<Long, Long> rmQtyentry = (Entry<Long, Long>) iterator.next();
			List<VendorDTO> vendorDTOs = new ArrayList<VendorDTO>();
			RMInventoryDTO rmInventoryDTO = rawmaterialinventoryService.getByRMId(rmQtyentry.getKey());
			RMReqirementDTO rmReqirementDTO = new RMReqirementDTO();
			rmReqirementDTO.setRmId(rmQtyentry.getKey());
			rmReqirementDTO.setRmPartNumber(rmInventoryDTO.getRmPartNumber());
			long maintainAvailablelQuantity =0;
			if(rmInventoryDTO.getQuantityAvailable()<rmInventoryDTO.getMinimumQuantity()){
				List<RMOrderAssociationDTO> rmOrderAssociationDTOs = rawmaterialorderassociationService.getRMListByRMId(rmQtyentry.getKey());
				if(rmOrderAssociationDTOs !=null){
				long totalRMRemainingQuantity=0;
				for (RMOrderAssociationDTO rmOrderAssociationDTO : rmOrderAssociationDTOs) {
					totalRMRemainingQuantity = totalRMRemainingQuantity+rmOrderAssociationDTO.getRemainingQuantity();
				}
				 maintainAvailablelQuantity = rmInventoryDTO.getMinimumQuantity()-rmInventoryDTO.getQuantityAvailable();
			  rmReqirementDTO.setRequiredQuantity(rmQtyentry.getValue()-totalRMRemainingQuantity+maintainAvailablelQuantity);
				}else{
					maintainAvailablelQuantity = rmInventoryDTO.getMinimumQuantity()-rmInventoryDTO.getQuantityAvailable();
					rmReqirementDTO.setRequiredQuantity(rmQtyentry.getValue()-rmInventoryDTO.getQuantityAvailable()+maintainAvailablelQuantity);
				}
			rmReqirementDTO.setInventoryQuantity(rmInventoryDTO.getQuantityAvailable());
			rmReqirementDTO.setMinimumQuantity(rmInventoryDTO.getMinimumQuantity());
			List<RMVendorAssociationDTO> rmVendorAssociationDTOs = rmvAssoService.getRawmaterialvendorassociationListByRMId(rmQtyentry.getKey());
			
			for (RMVendorAssociationDTO rmVendorAssociationDTO : rmVendorAssociationDTOs) {
			VendorDTO vendorDTO =  vendorService.getVendorById(rmVendorAssociationDTO.getVendorId().getId());
			vendorDTOs.add(vendorDTO);
			rmReqirementDTO.setVendorDTOs(vendorDTOs);
			}
			
			logger.info("rmid : " + rmQtyentry.getKey() + " reqQty : " + rmQtyentry.getValue() + " invQty : " + rmReqirementDTO.getInventoryQuantity());
			if(rmQtyentry.getValue() > rmInventoryDTO.getQuantityAvailable()){
				rmReqirementDTOs.add(rmReqirementDTO);
				rmIds.add(rmReqirementDTO.getRmId());
			}
			}
		}
		}else{
			return new Response(1,"There is no any requirmemt");
		}
		}else{
			//RM Inventory  quantity display when product order are not created
			Response rmReqirementDTOs2 = getRMRequirmentDto();
			if(rmReqirementDTOs2.getRmReqirementDTOs() !=null){
			for (RMReqirementDTO rmReqirementDTO : rmReqirementDTOs2.getRmReqirementDTOs()) {
				rmReqirementDTOs.add(rmReqirementDTO);
			}
			}else{
				String errorMessage = rmReqirementDTOs2.getMessage();
				return new Response(1,errorMessage);
			}
			return new Response(0, "Success", rmReqirementDTOs);
		}
		//RM Inventory quantity and required quantity display when product order created
		Response rmReqirementDTOs2 = getRMRequirmentDto();
		if(rmReqirementDTOs2.getRmReqirementDTOs() !=null){
		for (RMReqirementDTO rmReqirementDTO : rmReqirementDTOs2.getRmReqirementDTOs()) {
			if(!rmIds.contains(rmReqirementDTO.getRmId())){
				rmReqirementDTOs.add(rmReqirementDTO);
			}
		}}else{
			String errorMessage = rmReqirementDTOs2.getMessage();
			return new Response(1,errorMessage);
		}
		return new Response(0, "Success", rmReqirementDTOs);
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			return new Response(1, "Error", null);
		}
	}
	@Transactional @RequestMapping(value = "/createMultipleRMOrder", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addMultipleRMOrder(
			@Valid  @RequestBody MultipleRMOrderDTO multipleRMOrderDTO, BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
			}
			//TODO save call raw material order
			List<RawmaterialOrderDTO> rawmaterialOrderDTOs =  new ArrayList<RawmaterialOrderDTO>();
			HashMap<Long, List<RMOrderAssociationDTO>> multipleOrder = new HashMap<Long, List<RMOrderAssociationDTO>>();
			RawmaterialOrderDTO newrawmaterialOrderDTO = new RawmaterialOrderDTO();
			for(RawmaterialOrderDTO rawmaterialOrderDTO : multipleRMOrderDTO.getRawmaterialOrderDTOs()){
				newrawmaterialOrderDTO.setExpectedDeliveryDate(rawmaterialOrderDTO.getExpectedDeliveryDate());
				newrawmaterialOrderDTO.setCreateDate(rawmaterialOrderDTO.getCreateDate());
				newrawmaterialOrderDTO.setDescription(rawmaterialOrderDTO.getDescription());
				List<RMOrderAssociationDTO> rmOrderAssociationDTOs = null;
				if(multipleOrder.get(rawmaterialOrderDTO.getVendorId().getId()) == null){
					rmOrderAssociationDTOs = new ArrayList<RMOrderAssociationDTO>();
				}else{
					rmOrderAssociationDTOs = multipleOrder.get(rawmaterialOrderDTO.getVendorId().getId());
				}
				RMOrderAssociationDTO rmOrderAssociationDTO = new RMOrderAssociationDTO();
				for (RMOrderAssociationDTO rmOrderAssociationDTO1 : rawmaterialOrderDTO.getRmOrderAssociationDTOs()) {
					rmOrderAssociationDTO.setQuantity(rmOrderAssociationDTO1.getQuantity());
					rmOrderAssociationDTO.setRawmaterialId(rmOrderAssociationDTO1.getRawmaterialId());
					rmOrderAssociationDTO.setExpectedDeliveryDate(rawmaterialOrderDTO.getExpectedDeliveryDate());
					rmOrderAssociationDTOs.add(rmOrderAssociationDTO);
					multipleOrder.put(rawmaterialOrderDTO.getVendorId().getId(), rmOrderAssociationDTOs);
				}
				
			}
			Set<Entry<Long, List<RMOrderAssociationDTO>>> multpleRMAssoEntries =  multipleOrder.entrySet();
			for(Entry<Long, List<RMOrderAssociationDTO>> multpleRMAssoEntry : multpleRMAssoEntries){
				RawmaterialOrderDTO rawmaterialOrderDTO = new RawmaterialOrderDTO();
				rawmaterialOrderDTO.setRmOrderAssociationDTOs(multpleRMAssoEntry.getValue());
				Vendor vendor =  new Vendor();
				vendor.setId(multpleRMAssoEntry.getKey());
				rawmaterialOrderDTO.setVendorId(vendor);
				for(RMOrderAssociationDTO rmOrderAssociationDTO:rawmaterialOrderDTO.getRmOrderAssociationDTOs()){
					rawmaterialOrderDTO.setExpectedDeliveryDate(rmOrderAssociationDTO.getExpectedDeliveryDate());	
				}
				rawmaterialOrderDTOs.add(rawmaterialOrderDTO);
			}
			for (RawmaterialOrderDTO rawmaterialOrderDTO : rawmaterialOrderDTOs) {
				RawmaterialOrderDTO rawmaterialOrderDTO2	= rawmaterialorderService.addMultipleRawMaterialOrder(rawmaterialOrderDTO, request, response);
				rawmaterialOrderDTO.setId(rawmaterialOrderDTO2.getId());
				rawmaterialOrderDTO.setStatusId(rawmaterialOrderDTO2.getStatusId());
				rawmaterialOrderDTO.setName(rawmaterialOrderDTO2.getName());
				addRMOrderAsso(rawmaterialOrderDTO, request, response);
			}
			return new UserStatus(1, "Multiple Rawmaterial Order added Successfully !");
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
	public Response getRMRequirmentDto() throws Exception{
		List<RMReqirementDTO> rmReqirementDTOs = new ArrayList<RMReqirementDTO>();
		List<RMInventoryDTO> rmInventoryDTOs = rawmaterialinventoryService.getRMInventoryList();
		for (RMInventoryDTO rmInventoryDTO : rmInventoryDTOs) {
			RMReqirementDTO rmReqirementDTO = new RMReqirementDTO();
			List<VendorDTO> vendorDTOs = new ArrayList<VendorDTO>();
			rmReqirementDTO.setRmId(rmInventoryDTO.getRawmaterialId().getId());
			rmReqirementDTO.setRmPartNumber(rmInventoryDTO.getRmPartNumber());
			long maintainAvailablelQuantity =0;
			if(rmInventoryDTO.getQuantityAvailable()<rmInventoryDTO.getMinimumQuantity()){
				List<RMOrderAssociationDTO> rmOrderAssociationDTOs = rawmaterialorderassociationService.getRMListByRMId(rmInventoryDTO.getRawmaterialId().getId());
				if(rmOrderAssociationDTOs !=null){
				long totalRMRemainingQuantity=0;
				for (RMOrderAssociationDTO rmOrderAssociationDTO : rmOrderAssociationDTOs) {
					totalRMRemainingQuantity = totalRMRemainingQuantity+rmOrderAssociationDTO.getRemainingQuantity();
				}
				 maintainAvailablelQuantity = rmInventoryDTO.getMinimumQuantity()-rmInventoryDTO.getQuantityAvailable();
			     rmReqirementDTO.setRequiredQuantity(maintainAvailablelQuantity-totalRMRemainingQuantity);
				}else{
					maintainAvailablelQuantity = rmInventoryDTO.getMinimumQuantity()-rmInventoryDTO.getQuantityAvailable();
					rmReqirementDTO.setRequiredQuantity(rmInventoryDTO.getQuantityAvailable()+maintainAvailablelQuantity);
				}
			rmReqirementDTO.setInventoryQuantity(rmInventoryDTO.getQuantityAvailable());
			rmReqirementDTO.setMinimumQuantity(rmInventoryDTO.getMinimumQuantity());
			List<RMVendorAssociationDTO> rmVendorAssociationDTOs = rmvAssoService.getRawmaterialvendorassociationListByRMId(rmInventoryDTO.getRawmaterialId().getId());
			if(rmVendorAssociationDTOs !=null){
			for (RMVendorAssociationDTO rmVendorAssociationDTO : rmVendorAssociationDTOs) {
			VendorDTO vendorDTO =  vendorService.getVendorById(rmVendorAssociationDTO.getVendorId().getId());
			vendorDTOs.add(vendorDTO);
			rmReqirementDTO.setVendorDTOs(vendorDTOs);
			}
			}else{
				String message ="Please create rm vendor association for "+rmInventoryDTO.getRmPartNumber();
				return new Response(1,message);
			}
			rmReqirementDTOs.add(rmReqirementDTO);
			}
		}
		return new Response(2,rmReqirementDTOs);
	}
}
