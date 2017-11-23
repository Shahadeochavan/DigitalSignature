package com.nextech.dscrm.dao;

import java.util.List;

import com.nextech.dscrm.model.Status;

public interface StatusDao extends SuperDao<Status>{
	
	public List<Status> getStatusByType(String type) throws Exception;
	
}

