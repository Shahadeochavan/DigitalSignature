package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Product;
import com.nextech.erp.newDTO.ProductDTO;
public interface ProductService extends CRUDService<Product>{

	public Product getProductByName(String name) throws Exception;
	
	public Product getProductByPartNumber(String partnumber) throws Exception;
	
	public boolean isOrderPartiallyDispatched(long orderId) throws Exception;

	public Product getProductListByProductId(long id);
	
	public List<ProductDTO> getProductList(List<Long> productIdList);
	
	public List<ProductDTO> getProductList() throws Exception;
	
	public ProductDTO getProductDTO(long id) throws Exception;
	
	public ProductDTO deleteProduct(long id) throws Exception;
	
	public Product getProductByProductId(long productId) throws Exception;
}
