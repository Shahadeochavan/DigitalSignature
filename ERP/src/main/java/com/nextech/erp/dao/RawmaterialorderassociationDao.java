package com.nextech.erp.dao;

import java.util.List;

import com.nextech.erp.model.Rawmaterialorderassociation;

public interface RawmaterialorderassociationDao extends
		SuperDao<Rawmaterialorderassociation> {
	public List<Rawmaterialorderassociation> getRMOrderRMAssociationByRMOrderId(
			long id) throws Exception;
	public Rawmaterialorderassociation getRMOrderRMAssociationByRMOrderIdandRMId(
			long id, long rmId)throws Exception;

}
