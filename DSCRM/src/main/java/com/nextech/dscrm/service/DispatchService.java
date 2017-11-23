package com.nextech.dscrm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nextech.dscrm.dto.DispatchDTO;
import com.nextech.dscrm.model.Dispatch;
import com.nextech.dscrm.status.Response;


public interface DispatchService  extends CRUDService<Dispatch>{

	public Dispatch getDispatchByProductOrderIdAndProductId(long orderId,long productId) throws Exception;

	public List<Dispatch> getDispatchByProductOrderId(long productOrderId) throws Exception;
	
	public Response addDispatchProduct(DispatchDTO dispatchDTO,HttpServletRequest request) throws Exception;
	
	public List<DispatchDTO> getDispatchList() throws Exception;
	
	public DispatchDTO getDispatchById(long id) throws Exception;
	
	public DispatchDTO deleteDispatchById(long id) throws Exception;
	
}
