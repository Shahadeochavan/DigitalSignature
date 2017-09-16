package com.nextech.erp.dao;

import com.nextech.erp.model.Unit;

public interface UnitDao extends SuperDao<Unit>{
	
	public Unit getUnitByName(String name)throws Exception;
}

