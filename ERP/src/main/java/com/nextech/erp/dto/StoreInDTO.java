package com.nextech.erp.dto;

public class StoreInDTO {
	private String partNumber;
	private long goodQuantity;
	private long receivedQuantity;
	private String remark;
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public long getGoodQuantity() {
		return goodQuantity;
	}
	public void setGoodQuantity(long goodQuantity) {
		this.goodQuantity = goodQuantity;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public long getReceivedQuantity() {
		return receivedQuantity;
	}
	public void setReceivedQuantity(long receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}
}