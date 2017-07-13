package com.nextech.erp.newDTO;


import com.nextech.erp.dto.AbstractDTO;
import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.model.Vendor;

public class RMVendorAssociationDTO extends AbstractDTO {
	
	private float pricePerUnit;
	private Rawmaterial rawmaterialId;
	private Vendor vendorId;
	public float getPricePerUnit() {
		return pricePerUnit;
	}
	public void setPricePerUnit(float pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	public Rawmaterial getRawmaterialId() {
		return rawmaterialId;
	}
	public void setRawmaterialId(Rawmaterial rawmaterialId) {
		this.rawmaterialId = rawmaterialId;
	}
	public Vendor getVendorId() {
		return vendorId;
	}
	public void setVendorId(Vendor vendorId) {
		this.vendorId = vendorId;
	}
	
	
}
