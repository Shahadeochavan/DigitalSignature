package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.dto.StoreOutDTO;
import com.nextech.erp.model.Storeout;

public class StoreoutRequestResponseFactory {
	
	public static Storeout setStoreOut(StoreOutDTO storeOutDTO,HttpServletRequest request){
		Storeout storeout = new Storeout();
		storeout.setDescription(storeOutDTO.getDescription());
		storeout.setId(storeOutDTO.getId());
		storeout.setProduct(storeOutDTO.getProductId());
		storeout.setProductionplanning(storeOutDTO.getProductionPlanId());
		storeout.setQuantityRequired(storeOutDTO.getQuantityRequired());
		storeout.setSelectedStoreOut(storeOutDTO.isSelectedStoreOut());
		storeout.setIsactive(true);
		storeout.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));		
		return storeout;
	}
	
	public static Storeout setStoreOutUpdate(StoreOutDTO storeOutDTO,HttpServletRequest request){
		Storeout storeout = new Storeout();
		storeout.setDescription(storeOutDTO.getDescription());
		storeout.setId(storeOutDTO.getId());
		storeout.setProduct(storeOutDTO.getProductId());
		storeout.setProductionplanning(storeOutDTO.getProductionPlanId());
		storeout.setQuantityRequired(storeOutDTO.getQuantityRequired());
		storeout.setSelectedStoreOut(storeOutDTO.isSelectedStoreOut());
		storeout.setIsactive(true);
		storeout.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));		
		return storeout;
	}


}
