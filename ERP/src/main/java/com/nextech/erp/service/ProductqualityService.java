package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.dto.ProductQualityDTO;
import com.nextech.erp.model.Productquality;

public interface ProductqualityService  extends CRUDService<Productquality>{
	
	public List<Productquality> getProductqualityListByProductId(long productId) throws Exception;
	
	public ProductQualityDTO saveProductQuality(ProductQualityDTO productQualityDTO,long userId)throws Exception;
	
	public void qualityCheckStore(ProductQualityDTO productQualityDTO,long userId) throws Exception;
	
	public List<ProductQualityDTO> getProductQualityList() throws Exception;
	
	public ProductQualityDTO getProductQualityById(long id) throws Exception;
	
	public void deleteproductQuality(long id) throws Exception;

}
