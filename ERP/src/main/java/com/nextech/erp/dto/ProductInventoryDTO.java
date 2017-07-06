package com.nextech.erp.dto;

public class ProductInventoryDTO {
	private String productPartNumber;
	private long inventoryQuantity;
	private long minimum_quantity;
	public String getProductPartNumber() {
		return productPartNumber;
	}
	public void setProductPartNumber(String productPartNumber) {
		this.productPartNumber = productPartNumber;
	}
	public long getInventoryQuantity() {
		return inventoryQuantity;
	}
	public void setInventoryQuantity(long inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}
	public long getMinimum_quantity() {
		return minimum_quantity;
	}
	public void setMinimum_quantity(long minimum_quantity) {
		this.minimum_quantity = minimum_quantity;
	}

}
