
package com.nextech.erp.service;

import java.util.List;


import com.nextech.erp.dto.ProductRMAssociationDTO;
import com.nextech.erp.model.Productrawmaterialassociation;

public interface ProductRMAssoService extends CRUDService<Productrawmaterialassociation>{

	public Productrawmaterialassociation getPRMAssociationByPidRmid(long pid, long rid) throws Exception;

	public List<Productrawmaterialassociation> getProductRMAssoListByProductID(long productID) throws Exception;
	
	public List<ProductRMAssociationDTO> getProductRMAssoListByProductId(long productID) throws Exception;
	
	public List<Long> getProductList() throws Exception;
	
	public List<ProductRMAssociationDTO> getProductRMAssoList(long productId) throws Exception;
	
	public ProductRMAssociationDTO  addMultipleRawmaterialorder(ProductRMAssociationDTO productRMAssociationDTO ,String currentUser)throws Exception;
	
	public List<ProductRMAssociationDTO> getProductRMAssoList() throws Exception;
	
	public ProductRMAssociationDTO getProductRMAsooById(long Id) throws Exception;
	
	public void deleteProductRMAssoById(long id)throws Exception;
	
	public List<ProductRMAssociationDTO> getProductRMListByProductId(long rmId)throws Exception;
	
	public ProductRMAssociationDTO getProductRMListByProduct(long productId) throws Exception;
}
