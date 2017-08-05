package com.nextech.erp.newDTO;


import com.nextech.erp.dto.AbstractDTO;

public class QualitycheckguidelineDTO extends AbstractDTO{
	
	private String guidelines;
	private long productId;
	private long rawMaterialId;
	private String productPartNumber;
	private String rmPartNumber;
	public String getGuidelines() {
		return guidelines;
	}
	public void setGuidelines(String guidelines) {
		this.guidelines = guidelines;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public long getRawMaterialId() {
		return rawMaterialId;
	}
	public void setRawMaterialId(long rawMaterialId) {
		this.rawMaterialId = rawMaterialId;
	}
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
	

}
