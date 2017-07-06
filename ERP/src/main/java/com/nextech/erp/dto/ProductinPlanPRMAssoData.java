package com.nextech.erp.dto;

public class ProductinPlanPRMAssoData {

	private String name;
	private long rawmaterial;
	private long quantityRequired;
	private long inventoryQuantity;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getQuantityRequired() {
		return quantityRequired;
	}
	public void setQuantityRequired(long quantityRequired) {
		this.quantityRequired = quantityRequired;
	}
	public long getRawmaterial() {
		return rawmaterial;
	}
	public void setRawmaterial(long rawmaterial) {
		this.rawmaterial = rawmaterial;
	}
	public long getInventoryQuantity() {
		return inventoryQuantity;
	}
	public void setInventoryQuantity(long inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}
}