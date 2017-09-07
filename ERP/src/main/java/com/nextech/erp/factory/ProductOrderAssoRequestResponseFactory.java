package com.nextech.erp.factory;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.model.Product;
import com.nextech.erp.model.Productorder;
import com.nextech.erp.model.Productorderassociation;
import com.nextech.erp.newDTO.ProductDTO;
import com.nextech.erp.newDTO.ProductOrderAssociationDTO;

public class ProductOrderAssoRequestResponseFactory {
	
	public static Productorderassociation setProductPrderAsso(ProductOrderAssociationDTO productOrderAssociationDTO,HttpServletRequest request){
		Productorderassociation productorderassociation  =  new Productorderassociation();
		productorderassociation.setId(productOrderAssociationDTO.getId());
		productorderassociation.setIsactive(true);
		Product product =  new Product();
		product.setId(productOrderAssociationDTO.getProductId().getId());
		productorderassociation.setProduct(product);
		Productorder productorder = new Productorder();
		productorder.setId(productOrderAssociationDTO.getProductOrderId().getId());
		productorderassociation.setProductorder(productorder);
		productorderassociation.setQuantity(productOrderAssociationDTO.getQuantity());
		productorderassociation.setRemainingQuantity(productOrderAssociationDTO.getQuantity());
		productorderassociation.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return productorderassociation;
	}
	
	public static Productorderassociation setProductPrderAssoUpdate(ProductOrderAssociationDTO productOrderAssociationDTO,HttpServletRequest request){
		Productorderassociation productorderassociation  =  new Productorderassociation();
		productorderassociation.setId(productOrderAssociationDTO.getId());
		productorderassociation.setIsactive(true);
		Product product =  new Product();
		product.setId(productOrderAssociationDTO.getProductId().getId());
		productorderassociation.setProduct(product);
		Productorder productorder = new Productorder();
		productorder.setId(productOrderAssociationDTO.getProductOrderId().getId());
		productorderassociation.setProductorder(productorder);
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
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(productorderassociation.getProduct().getId());
		productDTO.setPartNumber(productorderassociation.getProduct().getPartNumber());
		productOrderAssociationDTO.setProductId(productDTO);
		productOrderAssociationDTO.setQuantity(productorderassociation.getQuantity());
		productOrderAssociationDTO.setRemainingQuantity(productorderassociation.getRemainingQuantity());
		productOrderAssociationDTO.setUpdatedBy(productorderassociation.getUpdatedBy());
		productOrderAssociationDTO.setUpdatedDate(productorderassociation.getUpdatedDate());
		return productOrderAssociationDTO;
}
	public static List<ProductOrderAssociationDTO> getProductOrderAssociationDTOs(List<Productorderassociation> productorderassociations){
		List<ProductOrderAssociationDTO> productOrderAssociationDTOs =  new ArrayList<ProductOrderAssociationDTO>();
		for (Productorderassociation productorderassociation : productorderassociations) {
			ProductOrderAssociationDTO productOrderAssociationDTO = ProductOrderAssoRequestResponseFactory.setProductOrderAssoDto(productorderassociation);
			productOrderAssociationDTOs.add(productOrderAssociationDTO);
		}
		return productOrderAssociationDTOs;
	}

}
