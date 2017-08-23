package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.dto.StoreOutDTO;
import com.nextech.erp.model.Productionplanning;
import com.nextech.erp.model.Storeout;

public class StoreoutRequestResponseFactory {
	
	public static Storeout setStoreOut(StoreOutDTO storeOutDTO,HttpServletRequest request){
		Storeout storeout = new Storeout();
		storeout.setDescription(storeOutDTO.getDescription());
		storeout.setId(storeOutDTO.getId());
		storeout.setProduct(storeOutDTO.getProductId());
		Productionplanning productionplanning = new Productionplanning();
		productionplanning.setId(storeOutDTO.getProductionPlanId());
		storeout.setProductionplanning(productionplanning);
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
		Productionplanning productionplanning = new Productionplanning();
		productionplanning.setId(storeOutDTO.getProductionPlanId());
		storeout.setProductionplanning(productionplanning);
		storeout.setQuantityRequired(storeOutDTO.getQuantityRequired());
		storeout.setSelectedStoreOut(storeOutDTO.isSelectedStoreOut());
		storeout.setIsactive(true);
		storeout.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));		
		return storeout;
	}
	public static StoreOutDTO setStoreOutDTO(Storeout storeout){
		StoreOutDTO storeOutDTO =  new StoreOutDTO();
		storeOutDTO.setActive(true);
		storeout.setCreatedBy(storeout.getCreatedBy());
		storeout.setDescription(storeout.getDescription());
		storeOutDTO.setCreatedDate(storeout.getCreatedDate());
		storeOutDTO.setId(storeout.getId());
		storeOutDTO.setProductId(storeout.getProduct());
		storeOutDTO.setProductionPlanId(storeout.getProductionplanning().getId());
		storeOutDTO.setStatusId(storeout.getStatus().getId());
		storeOutDTO.setUpdatedBy(storeout.getUpdatedBy());
		storeOutDTO.setQuantityRequired(storeout.getQuantityRequired());
		storeOutDTO.setUpdatedDate(storeout.getUpdatedDate());
		return storeOutDTO;
	}


}
