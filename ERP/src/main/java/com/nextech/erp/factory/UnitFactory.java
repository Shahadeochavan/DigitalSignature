package com.nextech.erp.factory;



import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.model.Unit;
import com.nextech.erp.newDTO.UnitDTO;

public class UnitFactory {
	
	public static Unit setUnit(UnitDTO unitDTO,HttpServletRequest request){
		Unit unit = new Unit();
		unit.setId(unitDTO.getId());
		unit.setDescription(unitDTO.getDescription());
		unit.setName(unitDTO.getName());
		unit.setIsactive(true);
		unit.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return unit;
	}
	
	public static Unit setUnitUpdate(UnitDTO unitDTO,HttpServletRequest request){
		Unit unit = new Unit();
		unit.setId(unitDTO.getId());
		unit.setDescription(unitDTO.getDescription());
		unit.setName(unitDTO.getName());
		unit.setIsactive(true);
		unit.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return unit;
	}
	
	public static UnitDTO setUnitDTO(Unit unit){
		UnitDTO unitDTO =  new UnitDTO();
		unitDTO.setActive(true);
		unitDTO.setCreatedBy(unit.getCreatedBy());
		unitDTO.setCreatedDate(unit.getCreatedDate());
		unitDTO.setDescription(unit.getDescription());
		unitDTO.setId(unit.getId());
		unitDTO.setName(unit.getName());
		unitDTO.setUpdatedBy(unit.getUpdatedBy());
		unitDTO.setUpdatedDate(unit.getUpdatedDate());
		return unitDTO;
	}

}
