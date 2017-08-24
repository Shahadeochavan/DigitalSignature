package com.nextech.erp.dto;



import com.nextech.erp.model.Rawmaterial;

public class RMInventoryDTO extends AbstractDTO{
	
	private String rmPartNumber;
	private long quantityAvailable;
	private long minimumQuantity;
	private long maximumQuantity;
	private Rawmaterial rawmaterialId;
	
	public String getRmPartNumber() {
		return rmPartNumber;
	}
	public void setRmPartNumber(String rmPartNumber) {
		this.rmPartNumber = rmPartNumber;
	}
	public long getQuantityAvailable() {
		return quantityAvailable;
	}
	public void setQuantityAvailable(long quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}
	public long getMinimumQuantity() {
		return minimumQuantity;
	}
	public void setMinimumQuantity(long minimumQuantity) {
		this.minimumQuantity = minimumQuantity;
	}
	public long getMaximumQuantity() {
		return maximumQuantity;
	}
	public void setMaximumQuantity(long maximumQuantity) {
		this.maximumQuantity = maximumQuantity;
	}
	public Rawmaterial getRawmaterialId() {
		return rawmaterialId;
	}
	public void setRawmaterialId(Rawmaterial rawmaterialId) {
		this.rawmaterialId = rawmaterialId;
	}

}
