package com.nextech.dscrm.dao;

import java.util.List;

import com.nextech.dscrm.model.Dispatch;

public interface DispatchDao extends SuperDao<Dispatch>{

	public Dispatch getDispatchByProductOrderIdAndProductId(long orderID,long productID) throws Exception;

	public List<Dispatch> getDispatchByProductOrderId(long productOrderId) throws Exception;

}
