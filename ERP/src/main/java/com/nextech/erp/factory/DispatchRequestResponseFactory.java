package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.dto.DispatchDTO;
import com.nextech.erp.model.Dispatch;

public class DispatchRequestResponseFactory {
	
	public static Dispatch setdispatch(DispatchDTO dispatchDTO,HttpServletRequest request){
		Dispatch dispatch =  new Dispatch();
		dispatch.setDescription(dispatchDTO.getDescription());
		dispatch.setId(dispatchDTO.getId());
		dispatch.setInvoiceNo(dispatchDTO.getInvoiceNo());
		dispatch.setIsactive(true);
		dispatch.setProduct(dispatchDTO.getProductId());
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
