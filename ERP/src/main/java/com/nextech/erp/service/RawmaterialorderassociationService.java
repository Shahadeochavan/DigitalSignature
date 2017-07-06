package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Rawmaterialorderassociation;

public interface RawmaterialorderassociationService extends
		CRUDService<Rawmaterialorderassociation> {
	public List<Rawmaterialorderassociation> getRMOrderRMAssociationByRMOrderId(
			long id) throws Exception;
	public Rawmaterialorderassociation getRMOrderRMAssociationByRMOrderIdandRMId(
			long id,long rmId) throws Exception;

}
