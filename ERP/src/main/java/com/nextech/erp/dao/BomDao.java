package com.nextech.erp.dao;

import java.util.List;

import com.nextech.erp.model.Bom;

public interface BomDao extends SuperDao<Bom>{
	
	public List<Bom> getBomListByProductId(long productID) throws Exception;
	
	public List<Bom> getBomListByProductIdAndBomId(long productId,long bomId) throws Exception;

	public List<Long> getProductList();
	
	public Bom  getBomByProductId(long productID) throws Exception;

}
