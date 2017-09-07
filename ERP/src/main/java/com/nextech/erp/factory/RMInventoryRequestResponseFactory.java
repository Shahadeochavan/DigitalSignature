package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.dto.RMInventoryDTO;
import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.model.Rawmaterialinventory;
import com.nextech.erp.newDTO.RawMaterialDTO;

public class RMInventoryRequestResponseFactory {
	
	public static Rawmaterialinventory setRMInventory(RMInventoryDTO rmInventoryDTO,HttpServletRequest request){
		Rawmaterialinventory rawmaterialinventory = new Rawmaterialinventory();
		rawmaterialinventory.setMaximum_quantity(rmInventoryDTO.getMaximumQuantity());
		rawmaterialinventory.setMinimum_quantity(rmInventoryDTO.getMinimumQuantity());
		rawmaterialinventory.setQuantityAvailable(rmInventoryDTO.getQuantityAvailable());
		Rawmaterial  rawmaterial =  new Rawmaterial();
		rawmaterial.setId(rmInventoryDTO.getRawmaterialId().getId());
		rawmaterialinventory.setRawmaterial(rawmaterial);
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
		Rawmaterial  rawmaterial =  new Rawmaterial();
		rawmaterial.setId(rmInventoryDTO.getRawmaterialId().getId());
		rawmaterialinventory.setId(rmInventoryDTO.getId());
		rawmaterialinventory.setIsactive(true);
		rawmaterialinventory.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return rawmaterialinventory;
	}
	
	public static RMInventoryDTO setRMInvetoryDTO(Rawmaterialinventory rawmaterialinventory){
		RMInventoryDTO rmInventoryDTO =  new RMInventoryDTO();
		rmInventoryDTO.setActive(true);
		rmInventoryDTO.setCreatedBy(rawmaterialinventory.getCreatedBy());
		rmInventoryDTO.setCreatedDate(rawmaterialinventory.getCreatedDate());
		rmInventoryDTO.setId(rawmaterialinventory.getId());
		rmInventoryDTO.setRmPartNumber(rawmaterialinventory.getRawmaterial().getPartNumber());
		rmInventoryDTO.setMaximumQuantity(rawmaterialinventory.getMaximum_quantity());
		rmInventoryDTO.setMinimumQuantity(rawmaterialinventory.getMinimum_quantity());
		rmInventoryDTO.setQuantityAvailable(rawmaterialinventory.getQuantityAvailable());
		RawMaterialDTO  rawMaterialDTO =  new RawMaterialDTO();
		rawMaterialDTO.setId(rawmaterialinventory.getRawmaterial().getId());
		rawMaterialDTO.setPartNumber(rawmaterialinventory.getRawmaterial().getPartNumber());
		rmInventoryDTO.setRawmaterialId(rawMaterialDTO);
		rmInventoryDTO.setUpdatedBy(rawmaterialinventory.getUpdatedBy());
		rmInventoryDTO.setUpdatedDate(rawmaterialinventory.getUpdatedDate());
		return rmInventoryDTO;
	}

}
