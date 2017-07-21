package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.dto.DispatchDTO;
import com.nextech.erp.model.Dispatch;

public class DispatchRequestResponseFactory {
	
	public static Dispatch setdispatch(DispatchDTO dispatchDTO,HttpServletRequest request){
		Dispatch dispatch =  new Dispatch();
		dispatch.setDescription(dispatchDTO.getDescription());
		return dispatch;
	}

}
