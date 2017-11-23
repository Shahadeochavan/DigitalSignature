package com.nextech.dscrm.dao;

import java.util.List;

import com.nextech.dscrm.model.Clientproductasso;

public interface ClientproductassoDao extends SuperDao<Clientproductasso>{
	
	public List<Clientproductasso> getClientProductAssoListByClientId(long clientId) throws Exception;

}
