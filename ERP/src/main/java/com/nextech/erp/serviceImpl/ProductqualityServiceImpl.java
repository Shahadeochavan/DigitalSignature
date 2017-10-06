package com.nextech.erp.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dao.DailyproductionDao;
import com.nextech.erp.dao.ProductDao;
import com.nextech.erp.dao.ProductinventoryDao;
import com.nextech.erp.dao.ProductinventoryhistoryDao;
import com.nextech.erp.dao.ProductionplanningDao;
import com.nextech.erp.dao.ProductqualityDao;
import com.nextech.erp.dao.StatusDao;
import com.nextech.erp.dto.ProductQualityDTO;
import com.nextech.erp.dto.ProductQualityPart;
import com.nextech.erp.factory.ProductQualityRequestResponseFactory;
import com.nextech.erp.model.Dailyproduction;
import com.nextech.erp.model.Product;
import com.nextech.erp.model.Productinventory;
import com.nextech.erp.model.Productinventoryhistory;
import com.nextech.erp.model.Productionplanning;
import com.nextech.erp.model.Productquality;
import com.nextech.erp.model.Status;
import com.nextech.erp.service.ProductqualityService;
@Service
public class ProductqualityServiceImpl extends CRUDServiceImpl<Productquality> implements ProductqualityService{
	
	@Autowired
	ProductqualityDao productqualityDao;
	
	@Autowired
	ProductDao productDao;
	
	@Autowired
	ProductionplanningDao productionplanningDao;
	
	@Autowired
	DailyproductionDao dailyproductionDao;
	
	@Autowired
	StatusDao statusDao;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	ProductinventoryDao productinventoryDao;
	
	@Autowired
	ProductinventoryhistoryDao ProductinventoryhistoryDao;

	@Override
	public List<Productquality> getProductqualityListByProductId(long productId)
			throws Exception {
		
		return productqualityDao.getProductqualityListByProductId(productId);
	}

	@Override
	public ProductQualityDTO addProductQuality(ProductQualityDTO productQualityDTO,long userId) throws Exception {
		
		for(ProductQualityPart productQualityPart : productQualityDTO.getProductQualityParts()){
			Productquality productquality = setProductquality(productQualityPart,userId);
			productqualityDao.add(productquality);
			updateProductionPlanningForQualityCheck(productquality,userId);
			updateDailyProduction(productQualityPart,userId);
		}
		return productQualityDTO;
	}
	private Productquality setProductquality(ProductQualityPart productQualityPart,long userId) throws Exception {
		Productquality productquality = new Productquality();
		productquality.setProduct(productDao.getById(Product.class, productQualityPart.getProductId()));
		productquality.setCheckQuantity(productQualityPart.getProductQuantity());
		productquality.setProductionplanning(productionplanningDao.getById(Productionplanning.class, productQualityPart.getProductionPlanId()));
		productquality.setGoodQuantity(productQualityPart.getPassQuantity());
		productquality.setRejectedQuantity(productQualityPart.getFailQuantity());
		productquality.setRemark(productQualityPart.getRemark());
		productquality.setCreatedBy(userId);
		productquality.setIsactive(true);
		return productquality;
	}
	private void updateProductionPlanningForQualityCheck(Productquality productquality,long userId) throws Exception {
		Productionplanning productionplanning = productionplanningDao.getById(Productionplanning.class, productquality.getProductionplanning().getId());
		productionplanning.setQualityPendingQuantity(productionplanning.getQualityPendingQuantity()-(productquality.getGoodQuantity() + productquality.getRejectedQuantity()));
		productionplanning.setQualityCheckedQuantity(productionplanning.getQualityCheckedQuantity()+productquality.getGoodQuantity());
		productionplanning.setFailQuantity(productionplanning.getFailQuantity() + productquality.getRejectedQuantity());
		productionplanning.setUpdatedBy(userId);
		productionplanningDao.update(productionplanning);
	}
	private void updateDailyProduction(ProductQualityPart productQualityPart,long userId) throws NumberFormatException, NoSuchMessageException, Exception {
		List<Dailyproduction> dailyproductions = dailyproductionDao.getDailyProdPendingForQualityCheckByPlanningId(productQualityPart.getProductionPlanId());
		for (Dailyproduction dailyproduction : dailyproductions) {
			dailyproduction.setStatus(statusDao.getById(Status.class, Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_QUALITY_CHECK_COMPLETE, null, null))));
			dailyproduction.setUpdatedBy(userId);
			dailyproduction.setUpdatedDate(new Timestamp(new Date().getTime()));
		}
	}

	@Override
	public void qualityCheckStore(ProductQualityDTO productQualityDTO,long userId) throws Exception {
		
		for(ProductQualityPart productQualityPart : productQualityDTO.getProductQualityParts()){
			Productquality productquality = setProductquality(productQualityPart,userId);
			Product product =  productDao.getById(Product.class, productquality.getProduct().getId());
			addProductInventoryHistory(productquality.getGoodQuantity(), product, userId);
			updateProductInventory(productquality, product, userId);
			updateProductionPlanningForStore(productquality,userId);
		}
		
	}
	private void addProductInventoryHistory(long goodQuantity,Product product,long userId) throws Exception{
		Productinventory productinventory =  productinventoryDao.getProductinventoryByProductId(product.getId());
		if(productinventory != null){
			Productinventoryhistory productinventoryhistory = new Productinventoryhistory();
			productinventoryhistory.setProductinventory(productinventory);
			productinventoryhistory.setCreatedBy(userId);
			productinventoryhistory.setIsactive(true);
			productinventoryhistory.setBeforequantity((int)productinventory.getQuantityavailable());
			productinventoryhistory.setAfterquantity((int)(goodQuantity+productinventory.getQuantityavailable()));
			productinventoryhistory.setStatus(statusDao.getById(Status.class, Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_RAW_MATERIAL_INVENTORY_ADD, null, null))));
			ProductinventoryhistoryDao.add(productinventoryhistory);
		}
	}
	private Productinventory updateProductInventory(Productquality productquality,Product product,long userId) throws Exception{
		Productinventory productinventory =  productinventoryDao.getProductinventoryByProductId(productquality.getProduct().getId());
		if(productinventory != null){
			productinventory.setUpdatedBy(userId);
			productinventory.setQuantityavailable(productinventory.getQuantityavailable() + productquality.getGoodQuantity());
			productinventoryDao.update(productinventory);
		}
		return productinventory;
	}
	private void updateProductionPlanningForStore(Productquality productquality,long userId) throws Exception {
		Productionplanning productionplanning = productionplanningDao.getById(Productionplanning.class, productquality.getProductionplanning().getId());
		productionplanning.setQualityCheckedQuantity(productionplanning.getQualityCheckedQuantity()-productquality.getGoodQuantity());
		productionplanning.setCompletedQuantity(productionplanning.getCompletedQuantity()+productquality.getGoodQuantity());
		productionplanning.setUpdatedBy(userId);
		if(productionplanning.getTargetQuantity() >= productionplanning.getCompletedQuantity()){
			productionplanning.setStatus(statusDao.getById(Status.class,  Long.parseLong(messageSource.getMessage(ERPConstants.PROD_PLAN_COMPLETE, null, null))));
		}
		productionplanningDao.update(productionplanning);
	}

	@Override
	public List<ProductQualityDTO> getProductQualityList() throws Exception {
		
		List<ProductQualityDTO> productQualityDTOs  = new ArrayList<ProductQualityDTO>();
		List<Productquality> productqualities = productqualityDao.getList(Productquality.class);
		if(productqualities==null){
			return null;
		}
		for (Productquality productquality : productqualities) {
			ProductQualityDTO productQualityDTO = ProductQualityRequestResponseFactory.setProductQualityList(productquality);
			productQualityDTOs.add(productQualityDTO);
		}
		return productQualityDTOs;
	}

	@Override
	public ProductQualityDTO getProductQualityById(long id) throws Exception {
		
		Productquality productquality = productqualityDao.getById(Productquality.class, id);
		if(productquality==null){
			return null;
		}
		ProductQualityDTO productQualityDTO = ProductQualityRequestResponseFactory.setProductQualityList(productquality);
		return productQualityDTO;
	}

	@Override
	public ProductQualityDTO deleteproductQuality(long id) throws Exception {
		
		Productquality productquality = productqualityDao.getById(Productquality.class, id);
		if(productquality==null){
			return null;
		}
		productquality.setIsactive(false);
		productqualityDao.update(productquality);
		ProductQualityDTO productQualityDTO = ProductQualityRequestResponseFactory.setProductQualityList(productquality);
		return productQualityDTO;
		
	}

}
