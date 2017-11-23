package com.nextech.dscrm.dao;

import java.util.Date;
import java.util.List;

import com.nextech.dscrm.model.Productionplanning;
import com.nextech.dscrm.model.Productorderassociation;

public interface ProductorderassociationDao extends SuperDao<Productorderassociation>{
	
	public Productorderassociation getProductorderassociationByProdcutOrderIdandProdcutId(long pOrderId,long pId) throws Exception;

	public List<Productorderassociation> getProductorderassociationByProductId(long pId) throws Exception;
	
	public List<Productorderassociation> getProductorderassociationByOrderId(long oderID) throws Exception;

	public List<Productorderassociation> getIncompleteProductOrderAssoByProductId(long productId) throws Exception;

	public Productionplanning getProductionPlanningforCurrentMonthByProductIdAndDate(long pId,Date date)throws Exception;

	public List<Productorderassociation> getProductOrderAssoByOrderId(long orderId) throws Exception;
	
	public Productorderassociation getProdcutAssoByProductId(long prodcutId) throws Exception;
	
	public Productorderassociation getProdcutAssoByOrder(long orderId) throws Exception;

	public List<Productorderassociation> getIncompleteProductOrderAssociations() throws Exception ;
}
