package com.nextech.dscrm.dao;

import java.util.List;

import com.nextech.dscrm.model.Productquality;
public interface ProductqualityDao extends SuperDao<Productquality>{

	public List<Productquality> getProductqualityListByProductId(long productId) throws Exception;
}
