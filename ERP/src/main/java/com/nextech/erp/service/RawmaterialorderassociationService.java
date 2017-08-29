package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Rawmaterialorderassociation;
import com.nextech.erp.newDTO.RMOrderAssociationDTO;

public interface RawmaterialorderassociationService extends
		CRUDService<Rawmaterialorderassociation> {
	public List<RMOrderAssociationDTO> getRMOrderRMAssociationByRMOrderId(long id) throws Exception;
	
	public Rawmaterialorderassociation getRMOrderRMAssociationByRMOrderIdandRMId(long id,long rmId) throws Exception;
	
	public RMOrderAssociationDTO getRMOrderAssoById(long id) throws Exception;
	
	public List<RMOrderAssociationDTO> getRMOrderAssoList() throws Exception;
	
	public RMOrderAssociationDTO deleteRMOrderAsso(long id)throws Exception;

}
