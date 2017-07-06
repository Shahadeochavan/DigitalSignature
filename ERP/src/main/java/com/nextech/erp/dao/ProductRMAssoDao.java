package com.nextech.erp.dao;

import java.util.List;

import com.nextech.erp.model.Productrawmaterialassociation;

public interface ProductRMAssoDao extends SuperDao<Productrawmaterialassociation>{

	public Productrawmaterialassociation getPRMAssociationByPidRmid(long pid, long rmid) throws Exception;

	public List<Productrawmaterialassociation> getProductRMAssoListByProductId(long productID) throws Exception;
	
	public List<Long> getProductList() throws Exception;
}
