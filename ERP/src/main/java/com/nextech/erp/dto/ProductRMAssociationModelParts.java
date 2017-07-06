package com.nextech.erp.dto;

import com.nextech.erp.model.Rawmaterial;

public class ProductRMAssociationModelParts {
	private Rawmaterial rawmaterial;
	private long quantity;

	public Rawmaterial getRawmaterial() {
		return rawmaterial;
	}
	public void setRawmaterial(Rawmaterial rawmaterial) {
		this.rawmaterial = rawmaterial;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
}
