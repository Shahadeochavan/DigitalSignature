package com.nextech.erp.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.ProductorderassociationDao;
import com.nextech.erp.model.Productionplanning;
import com.nextech.erp.model.Productorderassociation;
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
	public List<Productorderassociation> getProductorderassociationByOrderId(
			long orderId) throws Exception {
		return productorderassociationDao.getProductorderassociationByOrderId(orderId);
	}

	@Override
	public List<Productorderassociation> getIncompleteProductOrderAssoByProdutId(long productId)
			throws Exception {
		// TODO Auto-generated method stub
		return productorderassociationDao.getIncompleteProductOrderAssoByProdutId(productId);
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


}
