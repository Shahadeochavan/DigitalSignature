package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.dto.ProductRMAssociationDTO;
import com.nextech.erp.model.Product;
import com.nextech.erp.model.Productrawmaterialassociation;

public class ProductRMAssoRequestResponseFactory {

	public static Productrawmaterialassociation setProductRMAsso(ProductRMAssociationDTO productRMAssociationDTO,HttpServletRequest request){
		Productrawmaterialassociation productrawmaterialassociation = new Productrawmaterialassociation();
		productrawmaterialassociation.setId(productRMAssociationDTO.getId());
		Product product = new Product();
		product.setId(productRMAssociationDTO.getProduct());
		productrawmaterialassociation.setProduct(product);
		productrawmaterialassociation.setQuantity(productRMAssociationDTO.getQuantity());
		productrawmaterialassociation.setRawmaterial(productRMAssociationDTO.getRawmaterialId());
		productrawmaterialassociation.setIsactive(true);
		productrawmaterialassociation.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return productrawmaterialassociation;
	}
	
	public static Productrawmaterialassociation setProductRMAssoUpdate(ProductRMAssociationDTO productRMAssociationDTO,HttpServletRequest request){
		Productrawmaterialassociation productrawmaterialassociation = new Productrawmaterialassociation();
		productrawmaterialassociation.setId(productRMAssociationDTO.getId());
		Product product = new Product();
		product.setId(productRMAssociationDTO.getProduct());
		productrawmaterialassociation.setProduct(product);
		productrawmaterialassociation.setQuantity(productRMAssociationDTO.getQuantity());
		productrawmaterialassociation.setRawmaterial(productRMAssociationDTO.getRawmaterialId());
		productrawmaterialassociation.setIsactive(true);
		productrawmaterialassociation.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return productrawmaterialassociation;
	}
	
	public static ProductRMAssociationDTO  setProductRMAssoList(Productrawmaterialassociation productrawmaterialassociation){
		ProductRMAssociationDTO productRMAssociationDTO = new ProductRMAssociationDTO();
		productRMAssociationDTO.setQuantity(productrawmaterialassociation.getQuantity());
		productRMAssociationDTO.setRawmaterialId(productrawmaterialassociation.getRawmaterial());
		productRMAssociationDTO.setProduct(productrawmaterialassociation.getProduct().getId());
		productRMAssociationDTO.setId(productrawmaterialassociation.getId());
		productRMAssociationDTO.setCreatedBy(productrawmaterialassociation.getCreatedBy());
		productRMAssociationDTO.setCreatedDate(productrawmaterialassociation.getCreatedDate());
		productRMAssociationDTO.setUpdatedBy(productrawmaterialassociation.getUpdatedBy());
		productRMAssociationDTO.setUpdatedDate(productrawmaterialassociation.getUpdatedDate());
		productRMAssociationDTO.setActive(true);
		return productRMAssociationDTO;
	}
}
