package com.nextech.dscrm.newDTO;


import java.util.Date;

import com.nextech.dscrm.dto.AbstractDTO;
import com.nextech.dscrm.dto.RawmaterialOrderDTO;

public class RMOrderAssociationDTO extends AbstractDTO {

	private int quantity;
	private long remainingQuantity;
	private RawMaterialDTO rawmaterialId;
	private RawmaterialOrderDTO rawmaterialOrderId;
	private Date expectedDeliveryDate;
	
	public RMOrderAssociationDTO(){
		
	}
	public RMOrderAssociationDTO(int id){
		this.setId(id);
	}
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
	public RawMaterialDTO getRawmaterialId() {
		return rawmaterialId;
	}
	public void setRawmaterialId(RawMaterialDTO rawmaterialId) {
		this.rawmaterialId = rawmaterialId;
	}
	public RawmaterialOrderDTO getRawmaterialOrderId() {
		return rawmaterialOrderId;
	}
	public void setRawmaterialOrderId(RawmaterialOrderDTO rawmaterialOrderId) {
		this.rawmaterialOrderId = rawmaterialOrderId;
	}
	public Date getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}
	public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}
	
}
