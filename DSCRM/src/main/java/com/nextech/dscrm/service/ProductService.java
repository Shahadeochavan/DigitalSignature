package com.nextech.dscrm.service;

import java.util.List;

import com.nextech.dscrm.model.Product;
import com.nextech.dscrm.newDTO.ProductDTO;
public interface ProductService extends CRUDService<Product>{

	public Product getProductByName(String name) throws Exception;
	
	public Product getProductByPartNumber(String partnNumber) throws Exception;
	
	public boolean isOrderPartiallyDispatched(long orderId) throws Exception;
	
	public List<ProductDTO> getProductList(List<Long> productIdList);
	
	public List<ProductDTO> getProductList() throws Exception;
	
	public ProductDTO getProductDTO(long id) throws Exception;
	
	public ProductDTO deleteProduct(long id) throws Exception;
	
	public Product getProductByProductId(long productId) throws Exception;
}
