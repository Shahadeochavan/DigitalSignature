package com.nextech.dscrm.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.dscrm.model.Product;
import com.nextech.dscrm.newDTO.ProductDTO;

public class ProductRequestResponseFactory {
	
	public static Product setProduct(ProductDTO productDTO,HttpServletRequest request){
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setDescription(productDTO.getDescription());
		product.setName(productDTO.getName());
		product.setPricePerUnit(productDTO.getPricePerUnit());
		product.setProductType(productDTO.getProductType());
		product.setProductValidity(productDTO.getProductValidity());
		product.setIsactive(true);
		product.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return product;
	}

	public static Product setProductUpdate(ProductDTO productDTO,HttpServletRequest request){
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setDescription(productDTO.getDescription());
		product.setName(productDTO.getName());
		product.setPricePerUnit(productDTO.getPricePerUnit());
		product.setProductType(productDTO.getProductType());
		product.setProductValidity(productDTO.getProductValidity());
		product.setIsactive(true);
		product.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return product;
		
	}

	public static ProductDTO setProductDto(Product product){
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setDescription(product.getDescription());
		productDTO.setName(product.getName());
		productDTO.setPricePerUnit(product.getPricePerUnit());
		productDTO.setProductType(product.getProductType());
		productDTO.setProductValidity(product.getProductValidity());
		productDTO.setActive(true);
		productDTO.setCreatedBy(product.getCreatedBy());
		productDTO.setCreatedDate(product.getCreatedDate());
		productDTO.setUpdatedBy(product.getUpdatedBy());
		productDTO.setUpdatedDate(product.getUpdatedDate());
		return productDTO;
	}

}
