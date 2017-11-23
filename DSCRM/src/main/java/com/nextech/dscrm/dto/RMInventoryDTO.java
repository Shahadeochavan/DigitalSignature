package com.nextech.dscrm.dto;



import com.nextech.dscrm.newDTO.RawMaterialDTO;

public class RMInventoryDTO extends AbstractDTO{
	
	private String rmPartNumber;
	private long quantityAvailable;
	private long minimumQuantity;
	private long maximumQuantity;
	private RawMaterialDTO rawmaterialId;
	
	public RMInventoryDTO(){
		
	}
	public RMInventoryDTO(int id){
		this.setId(id);
	}
	
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
	public RawMaterialDTO getRawmaterialId() {
		return rawmaterialId;
	}
	public void setRawmaterialId(RawMaterialDTO rawmaterialId) {
		this.rawmaterialId = rawmaterialId;
	}

}
