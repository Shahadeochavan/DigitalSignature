package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.dto.RMInventoryDTO;
import com.nextech.erp.model.Rawmaterialinventory;

public class RMInventoryRequestResponseFactory {
	
	public static Rawmaterialinventory setRMInventory(RMInventoryDTO rmInventoryDTO,HttpServletRequest request){
		Rawmaterialinventory rawmaterialinventory = new Rawmaterialinventory();
		rawmaterialinventory.setMaximum_quantity(rmInventoryDTO.getMaximumQuantity());
		rawmaterialinventory.setMinimum_quantity(rmInventoryDTO.getMinimumQuantity());
		rawmaterialinventory.setQuantityAvailable(rmInventoryDTO.getQuantityAvailable());
		rawmaterialinventory.setRawmaterial(rmInventoryDTO.getRawmaterialId());
		rawmaterialinventory.setId(rmInventoryDTO.getId());
		rawmaterialinventory.setIsactive(true);
		rawmaterialinventory.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return rawmaterialinventory;
	}
	
	public static Rawmaterialinventory setRMInventoryUpdate(RMInventoryDTO rmInventoryDTO,HttpServletRequest request){
		Rawmaterialinventory rawmaterialinventory = new Rawmaterialinventory();
		rawmaterialinventory.setMaximum_quantity(rmInventoryDTO.getMaximumQuantity());
		rawmaterialinventory.setMinimum_quantity(rmInventoryDTO.getMinimumQuantity());
		rawmaterialinventory.setQuantityAvailable(rmInventoryDTO.getQuantityAvailable());
		rawmaterialinventory.setRawmaterial(rmInventoryDTO.getRawmaterialId());
		rawmaterialinventory.setId(rmInventoryDTO.getId());
		rawmaterialinventory.setIsactive(true);
		rawmaterialinventory.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return rawmaterialinventory;
	
	}

}
