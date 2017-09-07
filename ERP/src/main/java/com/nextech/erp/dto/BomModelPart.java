package com.nextech.erp.dto;

import com.nextech.erp.newDTO.RawMaterialDTO;
import com.nextech.erp.newDTO.VendorDTO;

public class BomModelPart extends AbstractDTO{

	private RawMaterialDTO rawmaterial;
	private VendorDTO vendor;
	private long quantity;
	private long cost;
	private long pricePerUnit;

	public BomModelPart(){
		
	}
    public BomModelPart(int id){
		this.setId(id);
	}
	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public RawMaterialDTO getRawmaterial() {
		return rawmaterial;
	}

	public void setRawmaterial(RawMaterialDTO rawmaterial) {
		this.rawmaterial = rawmaterial;
	}

	public VendorDTO getVendor() {
		return vendor;
	}

	public void setVendor(VendorDTO vendor) {
		this.vendor = vendor;
	}

	public long getCost() {
		return cost;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	public long getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(long pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

}
