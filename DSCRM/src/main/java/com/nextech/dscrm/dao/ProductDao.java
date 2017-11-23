package com.nextech.dscrm.dao;

import java.util.List;

import com.nextech.dscrm.model.Product;

public interface ProductDao extends SuperDao<Product>{
	
    public Product getProductByName(String name) throws Exception;
	
	public Product getProductByPartNumber(String partnumber) throws Exception;

	public List<Product> getProductList(List<Long> productIdList);
	
	public Product getProductByProductId(long productId) throws Exception;
}

