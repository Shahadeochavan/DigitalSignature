package com.nextech.erp.newDTO;



import com.nextech.erp.dto.AbstractDTO;
import com.nextech.erp.dto.BomDTO;

public class BomRMVendorAssociationsDTO extends AbstractDTO{

	private float cost;
	private float pricePerUnit;
	private long quantity;
	private RawMaterialDTO rawmaterialId;
	private BomDTO bomId;
	private VendorDTO vendorId;
	
	public BomRMVendorAssociationsDTO(){
		
	}
	public BomRMVendorAssociationsDTO(int id){
		this.setId(id);
	}
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
	public float getPricePerUnit() {
		return pricePerUnit;
	}
	public void setPricePerUnit(float pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public RawMaterialDTO getRawmaterialId() {
		return rawmaterialId;
	}
	public void setRawmaterialId(RawMaterialDTO rawmaterialId) {
		this.rawmaterialId = rawmaterialId;
	}
	public BomDTO getBomId() {
		return bomId;
	}
	public void setBomId(BomDTO bomId) {
		this.bomId = bomId;
	}
	public VendorDTO getVendorId() {
		return vendorId;
	}
	public void setVendorId(VendorDTO vendorId) {
		this.vendorId = vendorId;
	}
	
	
}
