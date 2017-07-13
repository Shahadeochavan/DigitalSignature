package com.nextech.erp.newDTO;


import com.nextech.erp.dto.AbstractDTO;
import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.model.Rawmaterialorder;

public class RMOrderAssociationDTO extends AbstractDTO {

	private int quantity;
	private long remainingQuantity;
	private Rawmaterial rawmaterialId;
	private Rawmaterialorder rawmaterialorderId;
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public long getRemainingQuantity() {
		return remainingQuantity;
	}
	public void setRemainingQuantity(long remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}
	public Rawmaterial getRawmaterialId() {
		return rawmaterialId;
	}
	public void setRawmaterialId(Rawmaterial rawmaterialId) {
		this.rawmaterialId = rawmaterialId;
	}
	public Rawmaterialorder getRawmaterialorderId() {
		return rawmaterialorderId;
	}
	public void setRawmaterialorderId(Rawmaterialorder rawmaterialorderId) {
		this.rawmaterialorderId = rawmaterialorderId;
	}
	
}
