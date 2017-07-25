package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.dto.BomDTO;
import com.nextech.erp.model.Bom;

public class BOMFactory {
	
	public static Bom  setBom(BomDTO bomDTO,HttpServletRequest request){
		Bom bom = new Bom();
		//AbstractRequestResponse.setAbstractDataToEntity(bomDTO, bom);
		bom.setProduct(bomDTO.getProduct());
		bom.setBomId(bomDTO.getBomId());
		bom.setId(bomDTO.getId());
		bom.setIsactive(true);
		bom.setCreatedBy(request.getAttribute("current_user").toString());
		return bom;
	}

}
