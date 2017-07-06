package com.nextech.erp.dao;

import java.util.List;

import com.nextech.erp.model.Dispatch;

public interface DispatchDao extends SuperDao<Dispatch>{

	public Dispatch getDispatchByProductOrderIdAndProductId(long orderID,long productID) throws Exception;

	public List<Dispatch> getDispatchByProductOrderId(long productOrderId) throws Exception;

}
