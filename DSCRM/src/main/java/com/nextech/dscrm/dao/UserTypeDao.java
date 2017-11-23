package com.nextech.dscrm.dao;

import com.nextech.dscrm.model.Usertype;

public interface UserTypeDao extends SuperDao<Usertype>{
	
	public Usertype getUserTypeByUserTypeName(String userTypeName) throws Exception;
	
}
