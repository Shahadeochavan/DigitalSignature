package com.nextech.erp.dto;

import java.util.List;

public class StoreOutDTO {
	private long productionPlanId;
	private long productId;
	private long statusId;
	private long quantityRequired;
	private boolean isSelectedStoreOut;
	private List<StoreOutPart> storeOutParts;
	private String description;

	public long getProductionPlanId() {
		return productionPlanId;
	}

	public void setProductionPlanId(long productionPlanId) {
		this.productionPlanId = productionPlanId;
	}

	public long getStatusId() {
		return statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	public List<StoreOutPart> getStoreOutParts() {
		return storeOutParts;
	}

	public void setStoreOutParts(List<StoreOutPart> storeOutParts) {
		this.storeOutParts = storeOutParts;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getQuantityRequired() {
		return quantityRequired;
	}

	public void setQuantityRequired(long quantityRequired) {
		this.quantityRequired = quantityRequired;
	}

	public boolean isSelectedStoreOut() {
		return isSelectedStoreOut;
	}

	public void setSelectedStoreOut(boolean isSelectedStoreOut) {
		this.isSelectedStoreOut = isSelectedStoreOut;
	}

}
