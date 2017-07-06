package com.nextech.erp.dao;

import java.util.Date;
import java.util.List;

import com.nextech.erp.model.Productionplanning;
import com.nextech.erp.model.Productorderassociation;

public interface ProductorderassociationDao extends SuperDao<Productorderassociation>{
	public Productorderassociation getProductorderassociationByProdcutOrderIdandProdcutId(
			long pOrderId,long pId) throws Exception;

	public List<Productorderassociation> getProductorderassociationByProdcutId(long pId) throws Exception;
	public List<Productorderassociation> getProductorderassociationByOrderId(long oderID) throws Exception;

	public List<Productorderassociation> getIncompleteProductOrderAssoByProdutId(long productId) throws Exception;

	public Productionplanning getProductionPlanningforCurrentMonthByProductIdAndDate(long pId,Date date)throws Exception;

	public List<Productorderassociation> getProductOrderAssoByOrderId(long orderId) throws Exception;
	
	public Productorderassociation getProdcutAssoByProdcutId(long prodcutId) throws Exception;
	
	public Productorderassociation getProdcutAssoByOrder(long orderId) throws Exception;
}
