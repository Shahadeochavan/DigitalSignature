package com.nextech.erp.newDTO;


import com.nextech.erp.dto.AbstractDTO;

public class QualitycheckguidelineDTO extends AbstractDTO{
	
	private String guidelines;
	private long productId;
	private long rawMaterialId;
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
	

}
