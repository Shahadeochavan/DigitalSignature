package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Unit;
import com.nextech.erp.newDTO.UnitDTO;

public interface UnitService extends CRUDService<Unit>{
	
	public List<UnitDTO> getUnit(long Unit) throws Exception; 
	
}
