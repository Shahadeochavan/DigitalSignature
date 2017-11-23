package com.nextech.dscrm.dto;

public class ProductOrderRMData {

	private String productPartNumber;
	private String rmPartNumber;
	private long rmQuantity;
	private long productQuantity;
	public String getProductPartNumber() {
		return productPartNumber;
	}
	public void setProductPartNumber(String productPartNumber) {
		this.productPartNumber = productPartNumber;
	}
	public String getRmPartNumber() {
		return rmPartNumber;
	}
	public void setRmPartNumber(String rmPartNumber) {
		this.rmPartNumber = rmPartNumber;
	}
	public long getRmQuantity() {
		return rmQuantity;
	}
	public void setRmQuantity(long rmQuantity) {
		this.rmQuantity = rmQuantity;
	}
	public long getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(long productQuantity) {
		this.productQuantity = productQuantity;
	}
	
	
	
}
