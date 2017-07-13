package com.nextech.erp.factory;

import com.nextech.erp.dto.BomDTO;
import com.nextech.erp.model.Bom;

public class BOMFactory {
	
	public static Bom  setBom(BomDTO bomDTO){
		Bom bom = new Bom();
		AbstractRequestResponse.setAbstractDataToEntity(bomDTO, bom);
		bom.setProduct(bomDTO.getProduct());
		bom.setBomId(bomDTO.getBomId());
		bom.setId(bomDTO.getId());
		bom.setIsactive(true);
		return bom;
	}

}
