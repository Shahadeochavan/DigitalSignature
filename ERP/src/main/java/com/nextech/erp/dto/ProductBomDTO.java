package com.nextech.erp.dto;

import java.util.Date;

public class ProductBomDTO {
	private String productPartNumber;
	private String clinetPartNumber;
	private Date createdDate;
	public String getProductPartNumber() {
		return productPartNumber;
	}
	public void setProductPartNumber(String productPartNumber) {
		this.productPartNumber = productPartNumber;
	}
	public String getClinetPartNumber() {
		return clinetPartNumber;
	}
	public void setClinetPartNumber(String clinetPartNumber) {
		this.clinetPartNumber = clinetPartNumber;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


}
