package com.nextech.dscrm.dto;

import com.nextech.dscrm.newDTO.RawMaterialDTO;

public class ProductRMAssociationModelParts {
	private RawMaterialDTO rawmaterial;
	private long quantity;

	
	public RawMaterialDTO getRawmaterial() {
		return rawmaterial;
	}
	public void setRawmaterial(RawMaterialDTO rawmaterial) {
		this.rawmaterial = rawmaterial;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
}
