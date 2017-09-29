package com.nextech.erp.dao;

import com.nextech.erp.model.Rmtype;

public interface RMTypeDao extends SuperDao<Rmtype> {
	
	public Rmtype getRMTypeByRMTypeName(String rmTypeName) throws Exception; 

}
