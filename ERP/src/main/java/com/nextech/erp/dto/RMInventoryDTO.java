package com.nextech.erp.dto;

public class RMInventoryDTO {
	
	private String rmPartNumber;
	private long quantityAvailable;
	private long minimumQuantity;
	public String getRmPartNumber() {
		return rmPartNumber;
	}
	public void setRmPartNumber(String rmPartNumber) {
		this.rmPartNumber = rmPartNumber;
	}
	public long getQuantityAvailable() {
		return quantityAvailable;
	}
	public void setQuantityAvailable(long quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}
	public long getMinimumQuantity() {
		return minimumQuantity;
	}
	public void setMinimumQuantity(long minimumQuantity) {
		this.minimumQuantity = minimumQuantity;
	}

}
