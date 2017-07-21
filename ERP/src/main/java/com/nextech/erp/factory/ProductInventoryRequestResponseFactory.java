package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.dto.ProductInventoryDTO;
import com.nextech.erp.model.Product;
import com.nextech.erp.model.Productinventory;
import com.nextech.erp.newDTO.ProductDTO;

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
	public static Productinventory setProductIn(ProductDTO productDTO){
		Productinventory productinventory = new Productinventory();
		Product product = new Product();
		product.setId(productDTO.getId());
		productinventory.setProduct(product);
		productinventory.setQuantityavailable(0);
		productinventory.setIsactive(true);
		return productinventory;
	}
	
	public static ProductInventoryDTO  setProductDTO(Productinventory productinventory){
		ProductInventoryDTO productInventoryDTO = new ProductInventoryDTO();
		productInventoryDTO.setId(productinventory.getId());
		productInventoryDTO.setDescription(productinventory.getDescription());
		productInventoryDTO.setMaximumQuantity(productinventory.getMaximum_quantity());
		productInventoryDTO.setMinimumQuantity(productinventory.getMinimum_quantity());
		productInventoryDTO.setName(productinventory.getName());
		productInventoryDTO.setProductId(productinventory.getProduct());
		productInventoryDTO.setQuantityAvailable(productinventory.getQuantityavailable());
		productInventoryDTO.setRackNumber(productinventory.getRacknumber());
		productInventoryDTO.setActive(true);
		productInventoryDTO.setCreatedBy(productinventory.getCreatedBy());
		productInventoryDTO.setCreatedDate(productinventory.getCreatedDate());
		productInventoryDTO.setUpdatedBy(productinventory.getUpdatedBy());
		productInventoryDTO.setUpdatedDate(productinventory.getUpdatedDate());
		return productInventoryDTO;
	}

}
