package com.nextech.dscrm.service;

import java.util.Date;
import java.util.List;

import com.nextech.dscrm.model.Productionplanning;
import com.nextech.dscrm.model.Productorderassociation;
import com.nextech.dscrm.newDTO.ProductOrderAssociationDTO;

public interface ProductorderassociationService extends CRUDService<Productorderassociation>{

	public Productorderassociation getProductorderassociationByProdcutOrderIdandProdcutId(
			long pOrderId,long pId) throws Exception;

	public List<Productorderassociation> getProductorderassociationByProductId(long pId) throws Exception;
	
	public List<ProductOrderAssociationDTO> getProductorderassociationByOrderId(long orderId) throws Exception;

	public List<ProductOrderAssociationDTO> getIncompleteProductOrderAssoByProductId(long productId) throws Exception;

	public Productionplanning getProductionPlanningforCurrentMonthByProductIdAndDate(long pId,Date date)throws Exception;

	public List<Productorderassociation> getProductOrderAssoByOrderId(long orderId) throws Exception;
	
	public Productorderassociation getProdcutAssoByProductId(long prodcutId) throws Exception;
	
	public Productorderassociation getProductAssoByOrder(long orderId) throws Exception;
	
	public List<ProductOrderAssociationDTO> getProductOrderAssoList() throws Exception;
	
	public ProductOrderAssociationDTO getProductOrderAsoById(long id)throws Exception;
	
	public ProductOrderAssociationDTO deleteProductOrderAsso(long id) throws Exception;
	
	public List<ProductOrderAssociationDTO> getIncompleteProductOrderAssociations() throws Exception;
	
	
}
