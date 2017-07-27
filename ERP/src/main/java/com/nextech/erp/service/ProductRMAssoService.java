
package com.nextech.erp.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.dto.ProductRMAssociationDTO;
import com.nextech.erp.model.Productrawmaterialassociation;

public interface ProductRMAssoService extends CRUDService<Productrawmaterialassociation>{

	public Productrawmaterialassociation getPRMAssociationByPidRmid(long pid, long rid) throws Exception;

	public List<Productrawmaterialassociation> getProductRMAssoListByProductId(long productID) throws Exception;
	
	public List<Long> getProductList() throws Exception;
	
	public List<ProductRMAssociationDTO> getProductRMAssoList(long productId) throws Exception;
	
	public ProductRMAssociationDTO  saveProductRMAsso(ProductRMAssociationDTO productRMAssociationDTO ,String currentUser)throws Exception;
	
	public List<ProductRMAssociationDTO> getProductRMAssoList() throws Exception;
	
	public ProductRMAssociationDTO getProductRMAsooById(long Id) throws Exception;
	
	public void deleteProductRMAssoById(long id)throws Exception;
}
