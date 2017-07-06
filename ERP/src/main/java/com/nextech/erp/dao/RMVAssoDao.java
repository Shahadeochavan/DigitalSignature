package com.nextech.erp.dao;

import java.util.List;

import com.nextech.erp.model.Rawmaterialvendorassociation;

public interface RMVAssoDao extends SuperDao<Rawmaterialvendorassociation>{
	
	public Rawmaterialvendorassociation getRMVAssoByVendorIdRMId(long  vendorId, long rmId) throws Exception;

	public List<Rawmaterialvendorassociation> getRawmaterialvendorassociationListByRMId(long id);
	
	public Rawmaterialvendorassociation getRMVAssoByRMId(long rmId)	throws Exception;
	
}
