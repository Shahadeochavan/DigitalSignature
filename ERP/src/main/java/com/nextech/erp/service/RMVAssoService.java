package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Rawmaterialvendorassociation;

public interface RMVAssoService extends CRUDService<Rawmaterialvendorassociation>{
	
	public Rawmaterialvendorassociation getRMVAssoByVendorIdRMId(long vendorId,long rmId) throws Exception;

	public List<Rawmaterialvendorassociation> getRawmaterialvendorassociationListByRMId(long id) throws Exception;
	
	public Rawmaterialvendorassociation getRMVAssoByRMId(long rmId) throws Exception;
	
}