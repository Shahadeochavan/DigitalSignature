package com.nextech.dscrm.service;

import java.util.List;

import com.nextech.dscrm.dto.ProductInventoryDTO;
import com.nextech.dscrm.model.Productinventory;

public interface ProductinventoryService extends CRUDService<Productinventory>{
	
	public Productinventory getProductinventoryByProductId(long productId) throws Exception;
	
	public List<ProductInventoryDTO> getProductinventoryListByProductId(long productId) throws Exception;
	
	public List<ProductInventoryDTO> getproductInventoryDTO() throws Exception;
	
	public ProductInventoryDTO  getProductInventory(long id) throws Exception;
	
	public ProductInventoryDTO deleteProductInventory(long id)throws Exception;
	
}