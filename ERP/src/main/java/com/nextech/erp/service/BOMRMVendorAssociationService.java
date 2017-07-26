package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Bomrmvendorassociation;
import com.nextech.erp.newDTO.BomRMVendorAssociationsDTO;


public interface BOMRMVendorAssociationService extends CRUDService<Bomrmvendorassociation>{
	
	public List<BomRMVendorAssociationsDTO> getBomRMVendorByBomId(long bomId) throws Exception;

}
