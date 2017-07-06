package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Productquality;

public interface ProductqualityService  extends CRUDService<Productquality>{
	
	public List<Productquality> getProductqualityListByProductId(long productId) throws Exception;

}
