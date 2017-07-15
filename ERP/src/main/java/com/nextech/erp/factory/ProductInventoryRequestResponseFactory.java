package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.dto.ProductInventoryDTO;
import com.nextech.erp.model.Productinventory;

public class ProductInventoryRequestResponseFactory {
	
	public static Productinventory setProductInventory(ProductInventoryDTO productInventoryDTO,HttpServletRequest request){
		Productinventory productinventory = new Productinventory();
		productinventory.setId(productInventoryDTO.getId());
		productinventory.setDescription(productInventoryDTO.getDescription());
		productinventory.setMaximum_quantity(productInventoryDTO.getMaximumQuantity());
		productinventory.setMinimum_quantity(productInventoryDTO.getMinimumQuantity());
		productinventory.setName(productInventoryDTO.getName());
		productinventory.setProduct(productInventoryDTO.getProductId());
		productinventory.setQuantityavailable(productInventoryDTO.getQuantityAvailable());
		productinventory.setRacknumber(productInventoryDTO.getRackNumber());
		productinventory.setIsactive(true);
		productinventory.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return productinventory;
	}
	
	public static Productinventory setProductInventoryUpdate(ProductInventoryDTO productInventoryDTO,HttpServletRequest request){
		Productinventory productinventory = new Productinventory();
		productinventory.setId(productInventoryDTO.getId());
		productinventory.setDescription(productInventoryDTO.getDescription());
		productinventory.setMaximum_quantity(productInventoryDTO.getMaximumQuantity());
		productinventory.setMinimum_quantity(productInventoryDTO.getMinimumQuantity());
		productinventory.setName(productInventoryDTO.getName());
		productinventory.setProduct(productInventoryDTO.getProductId());
		productinventory.setQuantityavailable(productInventoryDTO.getQuantityAvailable());
		productinventory.setRacknumber(productInventoryDTO.getRackNumber());
		productinventory.setIsactive(true);
		productinventory.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return productinventory;
	}

}
