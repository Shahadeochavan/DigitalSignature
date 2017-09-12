package com.nextech.erp.dto;

public class ProductOrderData {
	private String productName;
	private long quantity;
	private long rate;
	private long amount;
	private String partNumber;
	private long rmQuantity;
	private double cgst;
	private double igst;
	private double sgst;


	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public long getRate() {
		return rate;
	}

	public void setRate(long rate) {
		this.rate = rate;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}


	public long getRmQuantity() {
		return rmQuantity;
	}

	public void setRmQuantity(long rmQuantity) {
		this.rmQuantity = rmQuantity;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public double getCgst() {
		return cgst;
	}

	public void setCgst(double cgst) {
		this.cgst = cgst;
	}

	public double getIgst() {
		return igst;
	}

	public void setIgst(double igst) {
		this.igst = igst;
	}

	public double getSgst() {
		return sgst;
	}

	public void setSgst(double sgst) {
		this.sgst = sgst;
	}
}
