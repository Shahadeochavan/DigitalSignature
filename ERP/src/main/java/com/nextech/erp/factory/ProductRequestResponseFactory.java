package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.model.Product;
import com.nextech.erp.newDTO.ProductDTO;

public class ProductRequestResponseFactory {
	
	public static Product setProduct(ProductDTO productDTO,HttpServletRequest request){
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setClientpartnumber(productDTO.getClientPartNumber());
		product.setDescription(productDTO.getDescription());
		product.setDesign(productDTO.getDesign());
		product.setPartNumber(productDTO.getPartNumber());
		product.setName(productDTO.getName());
		product.setIsactive(true);
		product.setRatePerUnit(productDTO.getRatePerUnit());
		product.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return product;
	}

	public static Product setProductUpdate(ProductDTO productDTO,HttpServletRequest request){
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setClientpartnumber(productDTO.getClientPartNumber());
		product.setDescription(productDTO.getDescription());
		product.setDesign(productDTO.getDesign());
		product.setPartNumber(productDTO.getPartNumber());
		product.setName(productDTO.getName());
		product.setDesign(productDTO.getDesign());
		product.setIsactive(true);
		product.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return product;
	}

	public static ProductDTO setProductDto(Product product){
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setRatePerUnit(product.getRatePerUnit());
		productDTO.setClientPartNumber(product.getClientpartnumber());
		productDTO.setDescription(product.getDescription());
		productDTO.setDesign(product.getDesign());
		productDTO.setPartNumber(product.getPartNumber());
		productDTO.setName(product.getName());
		productDTO.setActive(true);
		productDTO.setCreatedBy(product.getCreatedBy());
		productDTO.setCreatedDate(product.getCreatedDate());
		productDTO.setUpdatedBy(product.getUpdatedBy());
		productDTO.setUpdatedDate(product.getUpdatedDate());
		return productDTO;
	}

}
