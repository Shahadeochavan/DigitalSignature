package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.ProductorderassociationDao;
import com.nextech.erp.factory.ProductOrderAssoRequestResponseFactory;
import com.nextech.erp.factory.ProductOrderRequestResponseFactory;
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
	public List<Productorderassociation> getProductorderassociationByProdcutId(
			long pId) throws Exception {
		return productorderassociationDao.getProductorderassociationByProdcutId( pId);
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
	public List<ProductOrderAssociationDTO> getIncompleteProductOrderAssoByProdutId(long productId)
			throws Exception {
		// TODO Auto-generated method stub
		List<ProductOrderAssociationDTO> productOrderAssociationDTOs =  new ArrayList<ProductOrderAssociationDTO>();
		List<Productorderassociation> productorderassociations = productorderassociationDao.getIncompleteProductOrderAssoByProdutId(productId);
		for (Productorderassociation productorderassociation : productorderassociations) {
			ProductOrderAssociationDTO productOrderAssociationDTO = ProductOrderAssoRequestResponseFactory.setProductOrderAssoDto(productorderassociation);
			productOrderAssociationDTOs.add(productOrderAssociationDTO);
		}
		return productOrderAssociationDTOs;
	}

	@Override
	public Productionplanning getProductionPlanningforCurrentMonthByProductIdAndDate(
			long pId, Date date) throws Exception {
		// TODO Auto-generated method stub
		return productorderassociationDao.getProductionPlanningforCurrentMonthByProductIdAndDate(pId, date);
	}

	@Override
	public List<Productorderassociation> getProductOrderAssoByOrderId(
			long orderId) throws Exception {
		// TODO Auto-generated method stub
		return productorderassociationDao.getProductOrderAssoByOrderId(orderId);
	}

	@Override
	public Productorderassociation getProdcutAssoByProdcutId(long prodcutId)
			throws Exception {
		// TODO Auto-generated method stub
		return productorderassociationDao.getProdcutAssoByProdcutId(prodcutId);
	}

	@Override
	public Productorderassociation getProdcutAssoByOrder(long orderId)
			throws Exception {
		// TODO Auto-generated method stub
		return productorderassociationDao.getProdcutAssoByOrder(orderId);
	}

	@Override
	public List<ProductOrderAssociationDTO> getProductOrderAssoList()
			throws Exception {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		Productorderassociation productorderassociation = productorderassociationDao.getById(Productorderassociation.class, id);
		ProductOrderAssociationDTO productOrderAssociationDTO = ProductOrderAssoRequestResponseFactory.setProductOrderAssoDto(productorderassociation);
		return productOrderAssociationDTO;
	}

	@Override
	public void deleteProductOrderAsso(long id) throws Exception {
		// TODO Auto-generated method stub
		Productorderassociation productorderassociation = productorderassociationDao.getById(Productorderassociation.class, id);
		productorderassociation.setIsactive(false);
		productorderassociationDao.update(productorderassociation);
		
		
	}


}
