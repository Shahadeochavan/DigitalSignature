package com.nextech.dscrm.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.dscrm.dto.DispatchDTO;
import com.nextech.dscrm.model.Dispatch;
import com.nextech.dscrm.model.Product;

public class DispatchRequestResponseFactory {
	
	public static Dispatch setdispatch(DispatchDTO dispatchDTO,HttpServletRequest request){
		Dispatch dispatch =  new Dispatch();
		dispatch.setDescription(dispatchDTO.getDescription());
		dispatch.setId(dispatchDTO.getId());
		dispatch.setInvoiceNo(dispatchDTO.getInvoiceNo());
		dispatch.setIsactive(true);
		Product product =  new Product();
		product.setId(dispatchDTO.getProductId().getId());
		dispatch.setProduct(product);
		return dispatch;
	}
	
	public static DispatchDTO setDispatchDTO(Dispatch dispatch){
		DispatchDTO  dispatchDTO =  new DispatchDTO();
		dispatchDTO.setActive(true);
		dispatchDTO.setCreatedBy(dispatch.getCreatedBy());
		dispatchDTO.setCreatedDate(dispatchDTO.getCreatedDate());
		dispatchDTO.setId(dispatch.getId());
		dispatchDTO.setInvoiceNo(dispatch.getInvoiceNo());
		dispatchDTO.setOrderId(dispatch.getProductorder().getId());
		dispatchDTO.setDescription(dispatch.getDescription());
		dispatchDTO.setUpdatedBy(dispatch.getUpdatedBy());
		dispatchDTO.setQuantity(dispatch.getQuantity());
		return dispatchDTO;
	}

}
