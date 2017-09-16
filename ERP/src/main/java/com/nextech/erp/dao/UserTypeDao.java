package com.nextech.erp.dao;

import com.nextech.erp.model.Usertype;

public interface UserTypeDao extends SuperDao<Usertype>{
	
	public Usertype getUserTypeByUserTypeName(String userTypeName) throws Exception;
	
}
