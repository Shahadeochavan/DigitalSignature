package com.nextech.erp.dao;

import java.util.List;

import com.nextech.erp.model.Product;

public interface ProductDao extends SuperDao<Product>{
	
    public Product getProductByName(String name) throws Exception;
	
	public Product getProductByPartNumber(String partnumber) throws Exception;

	public List<Product> getProductList(List<Long> productIdList);
}

