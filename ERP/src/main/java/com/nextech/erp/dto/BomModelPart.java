package com.nextech.erp.dto;

import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.model.Vendor;

public class BomModelPart {

	private Rawmaterial rawmaterial;
	private Vendor vendor;
	private long quantity;
	private long cost;
	private long pricePerUnit;

	public Rawmaterial getRawmaterial() {
		return rawmaterial;
	}

	public void setRawmaterial(Rawmaterial rawmaterial) {
		this.rawmaterial = rawmaterial;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public long getCost() {
		return cost;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	public long getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(long pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

}
