package com.nextech.erp.dao;

import java.util.List;

import com.nextech.erp.model.Productquality;

public interface ProductqualityDao extends SuperDao<Productquality>{

	public List<Productquality> getProductqualityListByProductId(long productId) throws Exception;
}
