package com.nextech.erp.dto;

public class StoreOutPart {

	private long quantityDispatched;
	private long quantityRequired;
	private long rawmaterial;

	public long getRawmaterial() {
		return rawmaterial;
	}

	public void setRawmaterial(long rawmaterial) {
		this.rawmaterial = rawmaterial;
	}

	public long getQuantityDispatched() {
		return quantityDispatched;
	}

	public void setQuantityDispatched(long quantityDispatched) {
		this.quantityDispatched = quantityDispatched;
	}

	public long getQuantityRequired() {
		return quantityRequired;
	}

	public void setQuantityRequired(long quantityRequired) {
		this.quantityRequired = quantityRequired;
	}

}
