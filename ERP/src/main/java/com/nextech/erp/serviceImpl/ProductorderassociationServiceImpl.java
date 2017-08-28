package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.ProductorderassociationDao;
import com.nextech.erp.factory.ProductOrderAssoRequestResponseFactory;
import com.nextech.erp.model.Productionplanning;
import com.nextech.erp.model.Productorderassociation;
import com.nextech.erp.newDTO.ProductOrderAssociationDTO;
import com.nextech.erp.service.ProductorderassociationService;
@Service
public class ProductorderassociationServiceImpl extends CRUDServiceImpl<Productorderassociation> implements
		ProductorderassociationService {
@Autowired
ProductorderassociationDao productorderassociationDao;

	@Override
	public Productorderassociation getProductorderassociationByProdcutOrderIdandProdcutId(
			long pOrderId, long pId) throws Exception {
		return productorderassociationDao.getProductorderassociationByProdcutOrderIdandProdcutId(pOrderId, pId);
	}

	@Override
	public List<Productorderassociation> getProductorderassociationByProductId(
			long pId) throws Exception {
		return productorderassociationDao.getProductorderassociationByProductId( pId);
	}

	@Override
	public List<ProductOrderAssociationDTO> getProductorderassociationByOrderId(
			long orderId) throws Exception {
		List<ProductOrderAssociationDTO> productOrderAssociationDTOs =  new ArrayList<ProductOrderAssociationDTO>();
		List<Productorderassociation> productorderassociations = productorderassociationDao.getProductorderassociationByOrderId(orderId);
		for (Productorderassociation productorderassociation : productorderassociations) {
			ProductOrderAssociationDTO  productOrderAssociationDTO = ProductOrderAssoRequestResponseFactory.setProductOrderAssoDto(productorderassociation);
			productOrderAssociationDTOs.add(productOrderAssociationDTO);
		}
		return productOrderAssociationDTOs;
	}
	
	@Override
	public List<ProductOrderAssociationDTO> getIncompleteProductOrderAssociations() throws Exception{
		List<Productorderassociation> productorderassociations = productorderassociationDao.getIncompleteProductOrderAssociations();
		HashMap<Long, Long> productQuantityMap = new HashMap<Long, Long>();
		for (Iterator<Productorderassociation> iterator = productorderassociations.iterator(); iterator
				.hasNext();) {
			Productorderassociation productorderassociation = (Productorderassociation) iterator
					.next();
			Long val = productQuantityMap.containsKey(productorderassociation.getProduct().getId()) 
					? productQuantityMap.put(productorderassociation.getProduct().getId(), productQuantityMap.get(productorderassociation.getProduct().getId()) + productorderassociation.getRemainingQuantity()) 
					: productQuantityMap.put(productorderassociation.getProduct().getId(), productorderassociation.getRemainingQuantity());
			
		}
		return ProductOrderAssoRequestResponseFactory.getProductOrderAssociationDTOs(productorderassociations);
	}
	
	@Override
	public List<ProductOrderAssociationDTO> getIncompleteProductOrderAssoByProductId(long productId)
			throws Exception {
		List<ProductOrderAssociationDTO> productOrderAssociationDTOs =  new ArrayList<ProductOrderAssociationDTO>();
		List<Productorderassociation> productorderassociations = productorderassociationDao.getIncompleteProductOrderAssoByProductId(productId);
		for (Productorderassociation productorderassociation : productorderassociations) {
			ProductOrderAssociationDTO productOrderAssociationDTO = ProductOrderAssoRequestResponseFactory.setProductOrderAssoDto(productorderassociation);
			productOrderAssociationDTOs.add(productOrderAssociationDTO);
		}
		return productOrderAssociationDTOs;
	}

	@Override
	public Productionplanning getProductionPlanningforCurrentMonthByProductIdAndDate(
			long pId, Date date) throws Exception {
		return productorderassociationDao.getProductionPlanningforCurrentMonthByProductIdAndDate(pId, date);
	}

	@Override
	public List<Productorderassociation> getProductOrderAssoByOrderId(
			long orderId) throws Exception {
		return productorderassociationDao.getProductOrderAssoByOrderId(orderId);
	}

	@Override
	public Productorderassociation getProdcutAssoByProductId(long prodcutId)
			throws Exception {
		return productorderassociationDao.getProdcutAssoByProductId(prodcutId);
	}

	@Override
	public Productorderassociation getProdcutAssoByOrder(long orderId)
			throws Exception {
		return productorderassociationDao.getProdcutAssoByOrder(orderId);
	}

	@Override
	public List<ProductOrderAssociationDTO> getProductOrderAssoList()
			throws Exception {
		List<ProductOrderAssociationDTO> productOrderAssociationDTOs = new ArrayList<ProductOrderAssociationDTO>();
		List<Productorderassociation> productorderassociations = productorderassociationDao.getList(Productorderassociation.class);
		for (Productorderassociation productorderassociation : productorderassociations) {
			ProductOrderAssociationDTO productOrderAssociationDTO = ProductOrderAssoRequestResponseFactory.setProductOrderAssoDto(productorderassociation);
			productOrderAssociationDTOs.add(productOrderAssociationDTO);
		}
		return productOrderAssociationDTOs;
	}

	@Override
	public ProductOrderAssociationDTO getProductOrderAsoById(long id)
			throws Exception {
		Productorderassociation productorderassociation = productorderassociationDao.getById(Productorderassociation.class, id);
		ProductOrderAssociationDTO productOrderAssociationDTO = ProductOrderAssoRequestResponseFactory.setProductOrderAssoDto(productorderassociation);
		return productOrderAssociationDTO;
	}

	@Override
	public void deleteProductOrderAsso(long id) throws Exception {
		Productorderassociation productorderassociation = productorderassociationDao.getById(Productorderassociation.class, id);
		productorderassociation.setIsactive(false);
		productorderassociationDao.update(productorderassociation);
		
		
	}


}
