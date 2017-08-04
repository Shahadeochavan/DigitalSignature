package com.nextech.erp.serviceImpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dao.QualitycheckrawmaterialDao;
import com.nextech.erp.dto.QualityCheckRMDTO;
import com.nextech.erp.dto.RMInventoryDTO;
import com.nextech.erp.dto.RawMaterialInvoiceDTO;
import com.nextech.erp.exceptions.InvalidRMQuantityInQC;
import com.nextech.erp.factory.QualityRequestResponseFactory;
import com.nextech.erp.factory.RMOrderHistoryRequestResponseFactory;
import com.nextech.erp.model.Qualitycheckrawmaterial;
import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.model.Rawmaterialinventory;
import com.nextech.erp.model.Rawmaterialinventoryhistory;
import com.nextech.erp.model.Rawmaterialorder;
import com.nextech.erp.model.Rawmaterialorderassociation;
import com.nextech.erp.model.Rawmaterialorderhistory;
import com.nextech.erp.model.Rawmaterialorderinvoice;
import com.nextech.erp.model.Status;
import com.nextech.erp.newDTO.RMOrderAssociationDTO;
import com.nextech.erp.service.MailService;
import com.nextech.erp.service.NotificationService;
import com.nextech.erp.service.QualitycheckrawmaterialService;
import com.nextech.erp.service.RawmaterialService;
import com.nextech.erp.service.RawmaterialinventoryService;
import com.nextech.erp.service.RawmaterialinventoryhistoryService;
import com.nextech.erp.service.RawmaterialorderService;
import com.nextech.erp.service.RawmaterialorderassociationService;
import com.nextech.erp.service.RawmaterialorderhistoryService;
import com.nextech.erp.service.RawmaterialorderinvoiceService;
import com.nextech.erp.service.RawmaterialorderinvoiceassociationService;
import com.nextech.erp.service.RmorderinvoiceintakquantityService;
import com.nextech.erp.service.StatusService;
import com.nextech.erp.service.UserService;
import com.nextech.erp.service.VendorService;
@Service
public class QualitycheckrawmaterialServiceImpl extends CRUDServiceImpl<Qualitycheckrawmaterial> implements QualitycheckrawmaterialService {
	
	@Autowired
	QualitycheckrawmaterialDao qualitycheckrawmaterialDao;
	

	@Autowired
	RmorderinvoiceintakquantityService rmorderinvoiceintakquantityService;

	@Autowired
	RawmaterialService rawmaterialService;

	@Autowired
	RawmaterialorderinvoiceService rawmaterialorderinvoiceService;

	@Autowired
	RawmaterialorderhistoryService rawmaterialorderhistoryService;

	@Autowired
	RawmaterialinventoryhistoryService rawmaterialinventoryhistoryService;

	@Autowired
	RawmaterialorderService rawmaterialorderService;

	@Autowired
	RawmaterialinventoryService rawmaterialinventoryService;

	@Autowired
	StatusService statusService;

	@Autowired
	NotificationService notificationService;

	@Autowired
	VendorService vendorService;

	@Autowired
	MailService mailService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RawmaterialorderinvoiceassociationService rawmaterialorderinvoiceassociationService;

	@Autowired
	RawmaterialorderassociationService rawmaterialorderassociationService;

	@Autowired
	private MessageSource messageSource;

	private HashMap<Long,Long> rmIdQuantityMap;
	
	private static final int STATUS_RAW_MATERIAL_ORDER_INCOMPLETE=2;
	private static final int STATUS_RAW_MATERIAL_ORDER_COMPLETE=3;

	@Override
	public Qualitycheckrawmaterial getQualitycheckrawmaterialByInvoiceIdAndRMId(long invoiceId, long rmID) throws Exception {
		
		return qualitycheckrawmaterialDao.getQualitycheckrawmaterialByInvoiceIdAndRMId(invoiceId, rmID);
	}

	@Override
	public List<Qualitycheckrawmaterial> getQualitycheckrawmaterialByInvoiceId(
			long invoiceId) throws Exception {
		// TODO Auto-generated method stub
		return qualitycheckrawmaterialDao.getQualitycheckrawmaterialByInvoiceId(invoiceId);
	}

	@Override
	public RawMaterialInvoiceDTO addQualityCheck(RawMaterialInvoiceDTO rawMaterialInvoiceDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String message = "";
		Rawmaterialorderinvoice rawmaterialorderinvoiceNew = rawmaterialorderinvoiceService.getEntityById(Rawmaterialorderinvoice.class,rawMaterialInvoiceDTO.getId());
		Rawmaterialorder rawmaterialorder = rawmaterialorderService.getEntityById(Rawmaterialorder.class, rawmaterialorderinvoiceNew.getPo_No());
		List<QualityCheckRMDTO> qualityCheckRMDTOs = rawMaterialInvoiceDTO.getQualityCheckRMDTOs();
		if (qualityCheckRMDTOs != null && !qualityCheckRMDTOs.isEmpty()) {
			for (QualityCheckRMDTO qualityCheckRMDTO : qualityCheckRMDTOs) {
				Rawmaterial rawmaterial = rawmaterialService.getEntityById(Rawmaterial.class, qualityCheckRMDTO.getId());
				saveQualityCheckRawMaterial(new Qualitycheckrawmaterial(), qualityCheckRMDTO,rawmaterialorderinvoiceNew, rawmaterial, request, response);
				updateRawMaterialInvoice(ERPConstants.STATUS_READY_STORE_IN,rawmaterialorderinvoiceNew, request, response);
				updateRMOrderRemainingQuantity(qualityCheckRMDTO, rawmaterialorder, request, response);
				updateRMIdQuantityMap(qualityCheckRMDTO.getId(), qualityCheckRMDTO.getIntakeQuantity() - qualityCheckRMDTO.getGoodQuantity());
				message = messageSource.getMessage(ERPConstants.RM_QUALITY_CHECK, null, null);
			}
		}
		addOrderHistory(rawmaterialorderinvoiceNew, rawmaterialorder, request, response);
		updateRawMaterialOrderStatus(rawmaterialorder);
		return rawMaterialInvoiceDTO;
	}
	private long saveQualityCheckRawMaterial(Qualitycheckrawmaterial qualitycheckrawmaterial,QualityCheckRMDTO qualityCheckRMDTO,Rawmaterialorderinvoice rawmaterialorderinvoiceNew,Rawmaterial rawmaterial,HttpServletRequest request,HttpServletResponse response) throws Exception{
	Qualitycheckrawmaterial qualitycheckrawmaterial1 =	QualityRequestResponseFactory.setQualityCheckRM(qualitycheckrawmaterial, qualityCheckRMDTO, rawmaterialorderinvoiceNew, rawmaterial, request, response);
	qualitycheckrawmaterialDao.add(qualitycheckrawmaterial1);
		return qualitycheckrawmaterial1.getId();
	}
	private void updateRawMaterialInvoice(String invoiceStatus,Rawmaterialorderinvoice rawmaterialorderinvoice,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Status statusInventoryAdd = statusService.getEntityById(Status.class,Long.parseLong(messageSource.getMessage(invoiceStatus, null, null)));
		rawmaterialorderinvoice.setStatus(statusInventoryAdd);
		rawmaterialorderinvoice.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		rawmaterialorderinvoiceService.updateEntity(rawmaterialorderinvoice);
	}
	private void  updateRMOrderRemainingQuantity(QualityCheckRMDTO qualityCheckRMDTO ,Rawmaterialorder rawmaterialorder,HttpServletRequest request,HttpServletResponse response) throws InvalidRMQuantityInQC,Exception{
		Rawmaterialorderassociation rawmaterialorderassociation = rawmaterialorderassociationService.getRMOrderRMAssociationByRMOrderIdandRMId(rawmaterialorder.getId(),qualityCheckRMDTO.getId());
		if(rawmaterialorderassociation.getRemainingQuantity()-qualityCheckRMDTO.getGoodQuantity() >= 0){
			rawmaterialorderassociation.setRemainingQuantity(rawmaterialorderassociation.getRemainingQuantity()-qualityCheckRMDTO.getGoodQuantity());
		}else{
			throw new InvalidRMQuantityInQC("Good quantity exceeded than Remaining Quantity");
		}
		rawmaterialorderassociation.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		rawmaterialorderassociationService.updateEntity(rawmaterialorderassociation);
	}
	private void updateRMIdQuantityMap(long rmId,long quantity){
		if(rmIdQuantityMap == null){
			rmIdQuantityMap = new HashMap<Long, Long>();
		}
		rmIdQuantityMap.put(rmId, quantity);

	}
	private void addOrderHistory(Rawmaterialorderinvoice rawmaterialorderinvoice,Rawmaterialorder rawmaterialorder,HttpServletRequest request,HttpServletResponse response) throws Exception{
	Rawmaterialorderhistory rawmaterialorderhistory =	RMOrderHistoryRequestResponseFactory.setRMOrderHostory(rawmaterialorderinvoice, rawmaterialorder, request, response);
		rawmaterialorderhistory.setStatus1(statusService.getEntityById(Status.class,rawmaterialorder.getStatus().getId()));
		rawmaterialorderhistory.setStatus2(statusService.getEntityById(Status.class, getOrderStatus(rawmaterialorder)));
		rawmaterialorderhistoryService.addEntity(rawmaterialorderhistory);
	}
	private void updateRawMaterialOrderStatus(Rawmaterialorder rawmaterialorder) throws Exception{
		rawmaterialorder.setStatus(statusService.getEntityById(Status.class, getOrderStatus(rawmaterialorder)));
		rawmaterialorderService.updateEntity(rawmaterialorder);
	}

	private int getOrderStatus(Rawmaterialorder rawmaterialorder) throws Exception{
		boolean isOrderComplete = false;
		List<RMOrderAssociationDTO> rawmaterialorderassociationList  =rawmaterialorderassociationService.getRMOrderRMAssociationByRMOrderId(rawmaterialorder.getId());
		for (Iterator<RMOrderAssociationDTO> iterator = rawmaterialorderassociationList.iterator(); iterator
				.hasNext();) {
			RMOrderAssociationDTO rawmaterialorderassociation = (RMOrderAssociationDTO) iterator
					.next();
			if(rawmaterialorderassociation.getRemainingQuantity() == 0 ){
				isOrderComplete = true;
			}else{
				isOrderComplete = false;
				break;
			}
		}
		return isOrderComplete ? STATUS_RAW_MATERIAL_ORDER_COMPLETE : STATUS_RAW_MATERIAL_ORDER_INCOMPLETE;
	}

	@Override
	public void addRawmaterialorderinvoiceReadyStore(
			RawMaterialInvoiceDTO rawMaterialInvoiceDTO,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		Rawmaterialorderinvoice rawmaterialorderinvoiceNew = rawmaterialorderinvoiceService.getEntityById(Rawmaterialorderinvoice.class,rawMaterialInvoiceDTO.getId());
		List<Qualitycheckrawmaterial> qualitycheckrawmaterials = qualitycheckrawmaterialDao.getQualitycheckrawmaterialByInvoiceId(rawmaterialorderinvoiceNew.getId());
		if (qualitycheckrawmaterials != null&& !qualitycheckrawmaterials.isEmpty()) {
			for (Qualitycheckrawmaterial qualitycheckrawmaterial : qualitycheckrawmaterials) {
				Rawmaterial rawmaterial = rawmaterialService.getRMByRMId(qualitycheckrawmaterial.getRawmaterial().getId());
				Rawmaterialinventory rawmaterialinventory = updateInventory(qualitycheckrawmaterial, rawmaterial, request, response);
				addRMInventoryHistory(qualitycheckrawmaterial, rawmaterialinventory, request, response);
				updateRawMaterialInvoice(ERPConstants.STATUS_RAW_MATERIAL_INVENTORY_ADD,rawmaterialorderinvoiceNew, request, response);
			}
		}
		
	}
	
	@SuppressWarnings("unused")
	private Rawmaterialinventory updateInventory(Qualitycheckrawmaterial qualitycheckrawmaterial,Rawmaterial rawmaterial,HttpServletRequest request,HttpServletResponse response) throws Exception{
		RMInventoryDTO rmInventoryDTO =  rawmaterialinventoryService.getByRMId(qualitycheckrawmaterial.getRawmaterial().getId());
		Rawmaterialinventory	rawmaterialinventory = new Rawmaterialinventory();
		rawmaterialinventory.setId(rmInventoryDTO.getId());
		if(rmInventoryDTO == null){
		
			rawmaterialinventory.setRawmaterial(rawmaterial);
			rawmaterialinventory.setIsactive(true);
			rawmaterialinventory.setQuantityAvailable(qualitycheckrawmaterial.getGoodQuantity());
			rawmaterialinventory.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			rawmaterialinventoryService.addEntity(rawmaterialinventory);
		}else{
			rawmaterialinventory.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			rawmaterialinventory.setIsactive(true);
			rawmaterialinventory.setRawmaterial(rmInventoryDTO.getRawmaterialId());
			rawmaterialinventory.setQuantityAvailable(rmInventoryDTO.getQuantityAvailable()+qualitycheckrawmaterial.getGoodQuantity());
			rawmaterialinventory.setUpdatedDate(new Timestamp(new Date().getTime()));
			 rawmaterialinventoryService.updateEntity(rawmaterialinventory);
		}
		return rawmaterialinventory;
	}
	
	private void addRMInventoryHistory(Qualitycheckrawmaterial qualitycheckrawmaterial,Rawmaterialinventory rawmaterialinventory,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Rawmaterialinventoryhistory rawmaterialinventoryhistory = new Rawmaterialinventoryhistory();
		rawmaterialinventoryhistory.setQualitycheckrawmaterial(qualitycheckrawmaterial);
		rawmaterialinventoryhistory.setRawmaterialinventory(rawmaterialinventory);
		rawmaterialinventoryhistory.setIsactive(true);
		rawmaterialinventoryhistory.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		rawmaterialinventoryhistory.setCreatedDate(new Timestamp(new Date().getTime()));
		rawmaterialinventoryhistory.setStatus(statusService.getEntityById(Status.class,Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_RAW_MATERIAL_INVENTORY_ADD, null, null))));
		rawmaterialinventoryhistoryService.addEntity(rawmaterialinventoryhistory);
	}
}
