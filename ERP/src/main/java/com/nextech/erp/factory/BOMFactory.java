package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.dto.BomDTO;
import com.nextech.erp.model.Bom;
import com.nextech.erp.model.Product;
import com.nextech.erp.newDTO.ProductDTO;

public class BOMFactory {
	
	public static Bom  setBom(BomDTO bomDTO,HttpServletRequest request){
		Bom bom = new Bom();
		//AbstractRequestResponse.setAbstractDataToEntity(bomDTO, bom);
		Product  product =  new Product();
		product.setId(bomDTO.getProduct().getId());
		bom.setProduct(product);
		bom.setBomId(bomDTO.getBomId());
		bom.setId(bomDTO.getId());
		bom.setIsactive(true);
		bom.setCreatedBy(request.getAttribute("current_user").toString());
		return bom;
	}
	public static BomDTO setBomDTO(Bom bom){
		BomDTO bomDTO =  new BomDTO();
		bomDTO.setActive(true);
		bomDTO.setBomId(bom.getBomId());
		bomDTO.setId(bom.getId());
		ProductDTO productDTO =  new ProductDTO();
		productDTO.setId(bom.getProduct().getId());
		productDTO.setPartNumber(bom.getProduct().getPartNumber());
		bomDTO.setProduct(productDTO);
		bomDTO.setUpdatedDate(bom.getUpdatedDate());
		bomDTO.setCreatedDate(bom.getCreatedDate());
		return bomDTO;
	}

}
