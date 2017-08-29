package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.dto.ProductInventoryDTO;
import com.nextech.erp.model.Productinventory;

public interface ProductinventoryService extends CRUDService<Productinventory>{
	
	public Productinventory getProductinventoryByProductId(long productId) throws Exception;
	
	public List<ProductInventoryDTO> getProductinventoryListByProductId(long productId) throws Exception;
	
	public List<ProductInventoryDTO> getproductInventoryDTO() throws Exception;
	
	public ProductInventoryDTO  getProductInventory(long id) throws Exception;
	
	public ProductInventoryDTO deleteProductInventory(long id)throws Exception;
	
}