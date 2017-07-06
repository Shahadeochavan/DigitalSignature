package com.nextech.erp.dto;

import java.util.List;

public class ProductQualityDTO {
	private long productionPlanId;
	private List<ProductQualityPart> productQualityParts;
	public long getProductionPlanId() {
		return productionPlanId;
	}
	public void setProductionPlanId(long productionPlanId) {
		this.productionPlanId = productionPlanId;
	}
	public List<ProductQualityPart> getProductQualityParts() {
		return productQualityParts;
	}
	public void setProductQualityParts(List<ProductQualityPart> productQualityParts) {
		this.productQualityParts = productQualityParts;
	}
	
}
