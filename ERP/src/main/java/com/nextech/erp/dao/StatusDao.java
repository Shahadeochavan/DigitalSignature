package com.nextech.erp.dao;

import java.util.List;

import com.nextech.erp.model.Status;

public interface StatusDao extends SuperDao<Status>{
	
	public List<Status> getStatusByType(String type) throws Exception;
	
}

