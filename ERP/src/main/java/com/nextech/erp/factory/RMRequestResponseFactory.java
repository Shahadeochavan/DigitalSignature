package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;
import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.model.Rawmaterialinventory;
import com.nextech.erp.newDTO.RawMaterialDTO;

public class RMRequestResponseFactory {
	
	public static Rawmaterial setRawMaterial(RawMaterialDTO rawMaterialDTO,HttpServletRequest request){
		Rawmaterial rawmaterial = new Rawmaterial();
		rawmaterial.setId(rawMaterialDTO.getId());
		rawmaterial.setDescription(rawMaterialDTO.getDescription());
		rawmaterial.setName(rawMaterialDTO.getRmName());
		rawmaterial.setPartNumber(rawMaterialDTO.getPartNumber());
		rawmaterial.setPricePerUnit(rawMaterialDTO.getPricePerUnit());
		rawmaterial.setUnit(rawMaterialDTO.getUnitId());
		rawmaterial.setRmtype(rawMaterialDTO.getRmTypeId());
		rawmaterial.setDesign(rawMaterialDTO.getDesign());
		rawmaterial.setIsactive(true);
		rawmaterial.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return rawmaterial;
	}
	
	public static Rawmaterial setRawMaterialUpdate(RawMaterialDTO rawMaterialDTO,HttpServletRequest request){
		Rawmaterial rawmaterial = new Rawmaterial();
		rawmaterial.setId(rawMaterialDTO.getId());
		rawmaterial.setDescription(rawMaterialDTO.getDescription());
		rawmaterial.setName(rawMaterialDTO.getRmName());
		rawmaterial.setPartNumber(rawMaterialDTO.getPartNumber());
		rawmaterial.setPricePerUnit(rawMaterialDTO.getPricePerUnit());
		rawmaterial.setUnit(rawMaterialDTO.getUnitId());
		rawmaterial.setRmtype(rawMaterialDTO.getRmTypeId());
		rawmaterial.setDesign(rawMaterialDTO.getDesign());
		rawmaterial.setIsactive(true);
		rawmaterial.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return rawmaterial;
	}
	
	public static Rawmaterialinventory setRMIn(RawMaterialDTO rawMaterialDTO,long userId){
		Rawmaterialinventory rawmaterialinventory = new Rawmaterialinventory();
		Rawmaterial rawmaterial = new Rawmaterial();
		rawmaterial.setId(rawMaterialDTO.getId());
		rawmaterialinventory.setRawmaterial(rawmaterial);
		rawmaterialinventory.setQuantityAvailable(0);
		rawmaterialinventory.setIsactive(true);
		rawmaterialinventory.setCreatedBy(userId);
		return rawmaterialinventory;
	}
	
	public static RawMaterialDTO setRawMaterialDTO(Rawmaterial rawmaterial){
		RawMaterialDTO rawMaterialDTO = new RawMaterialDTO();
		rawMaterialDTO.setDescription(rawmaterial.getDescription());
		rawMaterialDTO.setPartNumber(rawmaterial.getPartNumber());
		rawMaterialDTO.setPricePerUnit(rawmaterial.getPricePerUnit());
		rawMaterialDTO.setRmName(rawmaterial.getName());
		rawMaterialDTO.setUnitId(rawmaterial.getUnit());
		rawMaterialDTO.setRmTypeId(rawmaterial.getRmtype());
		rawMaterialDTO.setActive(true);
		rawMaterialDTO.setId(rawmaterial.getId());
		rawMaterialDTO.setCreatedBy(rawmaterial.getCreatedBy());
		rawMaterialDTO.setCreatedDate(rawmaterial.getCreatedDate());
		rawMaterialDTO.setUpdatedBy(rawmaterial.getUpdatedBy());
		rawMaterialDTO.setUpdatedDate(rawmaterial.getUpdatedDate());
		rawMaterialDTO.setDesign(rawmaterial.getDesign());
		return rawMaterialDTO;
	}

}
