package com.nextech.dscrm.dto;

import java.util.List;

import com.nextech.dscrm.model.Product;

public class StoreOutDTO  extends AbstractDTO{
	private long productionPlanId;
	private Product productId;
	private long statusId;
	private long quantityRequired;
	private boolean isSelectedStoreOut;
	private List<StoreOutPart> storeOutParts;



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
	
	public long getProductionPlanId() {
		return productionPlanId;
	}

	public void setProductionPlanId(long productionPlanId) {
		this.productionPlanId = productionPlanId;
	}

	public Product getProductId() {
		return productId;
	}

	public void setProductId(Product productId) {
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
