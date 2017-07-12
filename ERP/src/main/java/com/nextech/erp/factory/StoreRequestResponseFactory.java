package com.nextech.erp.factory;

import com.nextech.erp.dto.StoreInDTO;
import com.nextech.erp.model.Qualitycheckrawmaterial;

public class StoreRequestResponseFactory {
	public static StoreInDTO createStoreInResonse(Qualitycheckrawmaterial qualitycheckrawmaterial){
		StoreInDTO storeInDTO = new StoreInDTO();
		storeInDTO.setPartNumber(qualitycheckrawmaterial.getRawmaterial().getPartNumber());
		storeInDTO.setGoodQuantity(qualitycheckrawmaterial.getGoodQuantity());
		return storeInDTO;
	}
}