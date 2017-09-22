package com.nextech.erp.dao;

import java.util.List;

import com.nextech.erp.dto.ProductOrderDTO;
import com.nextech.erp.model.Productorder;

public interface ProductorderDao extends SuperDao<Productorder> {
	public Productorder getProductorderByProductOrderId(
			long pOrderId) throws Exception;

	public List<Productorder> getPendingProductOrders(long statusId,long statusId1);

	public List<Productorder> getInCompleteProductOrder(long clientId,long statusId,long statusId1);

	public List<Productorder> getInCompleteProductOrders(long statusId);
	
	public List<Productorder> getNewAndInCompleteProductOrders(long newStatus,long inCompleteStatus) throws Exception;

}
