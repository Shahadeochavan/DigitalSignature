package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Bomrmvendorassociation;


public interface BOMRMVendorAssociationService extends CRUDService<Bomrmvendorassociation>{
	
	public List<Bomrmvendorassociation> getBomRMVendorByBomId(long bomId) throws Exception;

}
