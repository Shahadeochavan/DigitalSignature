package com.nextech.erp.service;

import java.util.Date;
import java.util.List;

import com.nextech.erp.model.Productionplanning;
import com.nextech.erp.model.Productorderassociation;
import com.nextech.erp.newDTO.ProductOrderAssociationDTO;

public interface ProductorderassociationService extends CRUDService<Productorderassociation>{

	public Productorderassociation getProductorderassociationByProdcutOrderIdandProdcutId(
			long pOrderId,long pId) throws Exception;

	public List<Productorderassociation> getProductorderassociationByProdcutId(long pId) throws Exception;
	public List<ProductOrderAssociationDTO> getProductorderassociationByOrderId(long orderId) throws Exception;

	public List<ProductOrderAssociationDTO> getIncompleteProductOrderAssoByProdutId(long productId) throws Exception;

	public Productionplanning getProductionPlanningforCurrentMonthByProductIdAndDate(long pId,Date date)throws Exception;

	public List<Productorderassociation> getProductOrderAssoByOrderId(long orderId) throws Exception;
	
	public Productorderassociation getProdcutAssoByProdcutId(long prodcutId) throws Exception;
	
	public Productorderassociation getProdcutAssoByOrder(long orderId) throws Exception;
	
	public List<ProductOrderAssociationDTO> getProductOrderAssoList() throws Exception;
	
	public ProductOrderAssociationDTO getProductOrderAsoById(long id)throws Exception;
	
	public void deleteProductOrderAsso(long id) throws Exception;
	
	
}
