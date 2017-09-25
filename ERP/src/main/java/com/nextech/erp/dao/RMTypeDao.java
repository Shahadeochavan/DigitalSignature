package com.nextech.erp.dao;

import com.nextech.erp.model.Rmtype;
import com.nextech.erp.newDTO.RMTypeDTO;

public interface RMTypeDao extends SuperDao<Rmtype> {
	
	public Rmtype getRMTypeByRMTypeName(String rmTypeName) throws Exception; 

}
