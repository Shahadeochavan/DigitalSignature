package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.model.Rawmaterialinventory;
import com.nextech.erp.model.Rmtype;
import com.nextech.erp.model.Unit;
import com.nextech.erp.newDTO.RMTypeDTO;
import com.nextech.erp.newDTO.RawMaterialDTO;
import com.nextech.erp.newDTO.UnitDTO;

public class RMRequestResponseFactory {
	
	public static Rawmaterial setRawMaterial(RawMaterialDTO rawMaterialDTO,HttpServletRequest request){
		Rawmaterial rawmaterial = new Rawmaterial();
		rawmaterial.setId(rawMaterialDTO.getId());
		rawmaterial.setDescription(rawMaterialDTO.getDescription());
		rawmaterial.setName(rawMaterialDTO.getRmName());
		rawmaterial.setPartNumber(rawMaterialDTO.getPartNumber());
		rawmaterial.setPricePerUnit(rawMaterialDTO.getPricePerUnit());
		Unit unit =  new Unit();
		unit.setId(rawMaterialDTO.getUnitId().getId());
		rawmaterial.setUnit(unit);
		Rmtype rmtype =  new Rmtype();
		rmtype.setId(rawMaterialDTO.getRmTypeId().getId());
		rawmaterial.setRmtype(rmtype);
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
		Unit unit =  new Unit();
		unit.setId(rawMaterialDTO.getUnitId().getId());
		rawmaterial.setUnit(unit);
		Rmtype rmtype =  new Rmtype();
		rmtype.setId(rawMaterialDTO.getRmTypeId().getId());
		rawmaterial.setRmtype(rmtype);
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
		UnitDTO unitDTO = new UnitDTO();
		unitDTO.setId(rawmaterial.getUnit().getId());
		unitDTO.setName(rawmaterial.getUnit().getName());
		rawMaterialDTO.setUnitId(unitDTO);
		RMTypeDTO rmTypeDTO =  new RMTypeDTO();
		rmTypeDTO.setId(rawmaterial.getRmtype().getId());
		rmTypeDTO.setRmTypeName(rawmaterial.getRmtype().getName());
		rawMaterialDTO.setRmTypeId(rmTypeDTO);
		rawMaterialDTO.setActive(true);
		rawMaterialDTO.setId(rawmaterial.getId());
		rawMaterialDTO.setDesign(rawmaterial.getDesign());
		return rawMaterialDTO;
	}

}
