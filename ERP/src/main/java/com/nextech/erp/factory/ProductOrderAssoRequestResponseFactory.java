package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.model.Productorderassociation;
import com.nextech.erp.newDTO.ProductOrderAssociationDTO;

public class ProductOrderAssoRequestResponseFactory {
	
	public static Productorderassociation setProductPrderAsso(ProductOrderAssociationDTO productOrderAssociationDTO,HttpServletRequest request){
		Productorderassociation productorderassociation  =  new Productorderassociation();
		productorderassociation.setId(productOrderAssociationDTO.getId());
		productorderassociation.setIsactive(true);
		productorderassociation.setProduct(productOrderAssociationDTO.getProductId());
		productorderassociation.setProductorder(productOrderAssociationDTO.getProductOrderId());
		productorderassociation.setQuantity(productOrderAssociationDTO.getQuantity());
		productorderassociation.setRemainingQuantity(productOrderAssociationDTO.getQuantity());
		productorderassociation.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return productorderassociation;
	}
	
	public static Productorderassociation setProductPrderAssoUpdate(ProductOrderAssociationDTO productOrderAssociationDTO,HttpServletRequest request){
		Productorderassociation productorderassociation  =  new Productorderassociation();
		productorderassociation.setId(productOrderAssociationDTO.getId());
		productorderassociation.setIsactive(true);
		productorderassociation.setProduct(productOrderAssociationDTO.getProductId());
		productorderassociation.setProductorder(productOrderAssociationDTO.getProductOrderId());
		productorderassociation.setQuantity(productOrderAssociationDTO.getQuantity());
		productorderassociation.setRemainingQuantity(productOrderAssociationDTO.getQuantity());
		productorderassociation.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return productorderassociation;
	}
	
	public static ProductOrderAssociationDTO setProductOrderAssoDto(Productorderassociation productorderassociation){
		ProductOrderAssociationDTO productOrderAssociationDTO =  new ProductOrderAssociationDTO();
		productOrderAssociationDTO.setActive(true);
		productOrderAssociationDTO.setCreatedBy(productorderassociation.getCreatedBy());
		productOrderAssociationDTO.setCreatedDate(productOrderAssociationDTO.getUpdatedDate());
		productOrderAssociationDTO.setId(productorderassociation.getId());
		productOrderAssociationDTO.setProductId(productorderassociation.getProduct());
		productOrderAssociationDTO.setQuantity(productorderassociation.getQuantity());
		productOrderAssociationDTO.setRemainingQuantity(productorderassociation.getRemainingQuantity());
		productOrderAssociationDTO.setUpdatedBy(productorderassociation.getUpdatedBy());
		productOrderAssociationDTO.setUpdatedDate(productorderassociation.getUpdatedDate());
		return productOrderAssociationDTO;
}

}
