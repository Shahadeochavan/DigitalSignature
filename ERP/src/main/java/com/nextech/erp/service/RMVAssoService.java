package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Rawmaterialvendorassociation;
import com.nextech.erp.newDTO.RMVendorAssociationDTO;

public interface RMVAssoService extends CRUDService<Rawmaterialvendorassociation>{
	
	public Rawmaterialvendorassociation getRMVAssoByVendorIdRMId(long vendorId,long rmId) throws Exception;

	public List<RMVendorAssociationDTO> getRawmaterialvendorassociationListByRMId(long id) throws Exception;
	
	public Rawmaterialvendorassociation getRMVAssoByRMId(long rmId) throws Exception;
	
	public List<RMVendorAssociationDTO> getRMVendorList() throws Exception;
	
	public RMVendorAssociationDTO  getRMVendor(long id) throws Exception;
	
	public void deleteRMVendor(long id)throws Exception;
	
}