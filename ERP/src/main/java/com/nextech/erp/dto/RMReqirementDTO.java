package com.nextech.erp.dto;

public class RMReqirementDTO extends AbstractDTO{

	public String rmPartNumber;
	public Long rmId;
	public Long requiredQuantity;
	public Long inventoryQuantity;
	public Long minimumQuantity;
	
	
	
	public String getRmPartNumber() {
		return rmPartNumber;
	}
	public void setRmPartNumber(String rmPartNumber) {
		this.rmPartNumber = rmPartNumber;
	}
	public Long getRmId() {
		return rmId;
	}
	public void setRmId(Long rmId) {
		this.rmId = rmId;
	}
	public Long getRequiredQuantity() {
		return requiredQuantity;
	}
	public void setRequiredQuantity(Long requiredQuantity) {
		this.requiredQuantity = requiredQuantity;
	}
	public Long getInventoryQuantity() {
		return inventoryQuantity;
	}
	public void setInventoryQuantity(Long inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}
	public Long getMinimumQuantity() {
		return minimumQuantity;
	}
	public void setMinimumQuantity(Long minimumQuantity) {
		this.minimumQuantity = minimumQuantity;
	}
	
}
