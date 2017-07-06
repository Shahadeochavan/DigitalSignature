package com.nextech.erp.dao;

import java.util.List;

import com.nextech.erp.model.Bomrmvendorassociation;

public interface BOMRMVendorAssociationDao extends SuperDao<Bomrmvendorassociation> {
	
	public List<Bomrmvendorassociation> getBomRMVendorByBomId(long bomId) throws Exception;

}
