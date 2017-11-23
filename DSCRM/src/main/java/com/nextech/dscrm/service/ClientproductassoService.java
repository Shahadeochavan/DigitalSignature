package com.nextech.dscrm.service;

import java.util.List;

import com.nextech.dscrm.model.Clientproductasso;

public interface ClientproductassoService extends CRUDService<Clientproductasso>{
	
	public List<Clientproductasso> getClientProductAssoListByClientId(long clientId) throws Exception;

}
