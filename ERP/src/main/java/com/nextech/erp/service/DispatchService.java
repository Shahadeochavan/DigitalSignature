package com.nextech.erp.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.dto.DispatchDTO;
import com.nextech.erp.dto.DispatchProductDTO;
import com.nextech.erp.model.Dispatch;
import com.nextech.erp.status.Response;
import com.nextech.erp.status.UserStatus;


public interface DispatchService  extends CRUDService<Dispatch>{

	public Dispatch getDispatchByProductOrderIdAndProductId(long orderID,long productID) throws Exception;

	public List<Dispatch> getDispatchByProductOrderId(long productOrderId) throws Exception;
	
	public Response addDispatchProduct(DispatchDTO dispatchDTO,HttpServletRequest request) throws Exception;
	
	public List<DispatchDTO> getDispatchList() throws Exception;
	
	public DispatchDTO getDispatchById(long id) throws Exception;
	
	public DispatchDTO deleteDispatchById(long id) throws Exception;
	
}
