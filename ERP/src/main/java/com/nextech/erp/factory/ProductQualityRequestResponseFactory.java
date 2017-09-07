package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.dto.ProductQualityDTO;
import com.nextech.erp.model.Product;
import com.nextech.erp.model.Productionplanning;
import com.nextech.erp.model.Productquality;
import com.nextech.erp.newDTO.ProductDTO;

public class ProductQualityRequestResponseFactory {
	
	public static Productquality setproductQuality(ProductQualityDTO productQualityDTO,HttpServletRequest request){
		Productquality productquality = new Productquality();
		productquality.setCheckQuantity(productQualityDTO.getCheckQuantity());
		Product product = new Product();
		product.setId(productQualityDTO.getProductId().getId());
		productquality.setProduct(product);
		productquality.setGoodQuantity(productQualityDTO.getCheckQuantity());
		productquality.setId(productQualityDTO.getId());
		productquality.setIsactive(true);
		Productionplanning productionplanning = new Productionplanning();
		productionplanning.setId(productQualityDTO.getId());
		productquality.setProductionplanning(productionplanning);
		productquality.setRejectedQuantity(productQualityDTO.getRejectedQuantity());
		productquality.setRemark(productQualityDTO.getRemark());
		productquality.setUpdatedBy(productQualityDTO.getUpdatedBy());
		productquality.setUpdatedDate(productQualityDTO.getUpdatedDate());
		return productquality;
	}
	
	public static ProductQualityDTO setProductQualityList(Productquality productquality){
		ProductQualityDTO productQualityDTO =  new ProductQualityDTO();
		productQualityDTO.setCheckQuantity(productquality.getCheckQuantity());
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(productquality.getProduct().getId());
		productDTO.setPartNumber(productquality.getProduct().getPartNumber());
		productQualityDTO.setProductId(productDTO);
		productQualityDTO.setGoodQuantity(productquality.getCheckQuantity());
		productQualityDTO.setId(productquality.getId());
		productQualityDTO.setActive(true);
		Productionplanning productionplanning = new Productionplanning();
		productionplanning.setId(productquality.getId());
		productQualityDTO.setProductionPlanId(productionplanning.getId());
		productQualityDTO.setRejectedQuantity(productquality.getRejectedQuantity());
		productQualityDTO.setRemark(productquality.getRemark());
		productQualityDTO.setUpdatedBy(productquality.getUpdatedBy());
		productQualityDTO.setUpdatedDate(productquality.getUpdatedDate());
		return productQualityDTO;
	}

}
