
package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.dto.ProductRMAssociationDTO;
import com.nextech.erp.model.Productrawmaterialassociation;

public interface ProductRMAssoService extends CRUDService<Productrawmaterialassociation>{

	public Productrawmaterialassociation getPRMAssociationByPidRmid(long pid, long rid) throws Exception;

	public List<Productrawmaterialassociation> getProductRMAssoListByProductId(long productID) throws Exception;
	
	public List<Long> getProductList() throws Exception;
	
	public List<ProductRMAssociationDTO> getProductRMAssoList(long productId) throws Exception;
}
