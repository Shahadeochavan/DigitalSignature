package com.nextech.erp.dto;

public class DispatchProductDTO {
	
	private long quantityDispatched;
	private String productName;
	private String clientPartNumber;
	private String invoiceNumber;
	private String description;
	private float totalCost;
	public long getQuantityDispatched() {
		return quantityDispatched;
	}
	public void setQuantityDispatched(long quantityDispatched) {
		this.quantityDispatched = quantityDispatched;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getClientPartNumber() {
		return clientPartNumber;
	}
	public void setClientPartNumber(String clientPartNumber) {
		this.clientPartNumber = clientPartNumber;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}
	

}
