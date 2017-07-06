package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Dispatch;


public interface DispatchService  extends CRUDService<Dispatch>{

	public Dispatch getDispatchByProductOrderIdAndProductId(long orderID,long productID) throws Exception;

	public List<Dispatch> getDispatchByProductOrderId(long productOrderId) throws Exception;

}
