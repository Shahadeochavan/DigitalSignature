package com.nextech.erp.dto;


public class QualityCheckRMDTO {
	
	private String description;
	private  int intakeQuantity;
	private int goodQuantity;
	public int getIntakeQuantity() {
		return intakeQuantity;
	}
	public void setIntakeQuantity(int intakeQuantity) {
		this.intakeQuantity = intakeQuantity;
	}
	public int getGoodQuantity() {
		return goodQuantity;
	}
	public void setGoodQuantity(int goodQuantity) {
		this.goodQuantity = goodQuantity;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

}
