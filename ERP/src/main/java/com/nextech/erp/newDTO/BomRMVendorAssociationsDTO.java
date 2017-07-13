package com.nextech.erp.newDTO;



import com.nextech.erp.dto.AbstractDTO;
import com.nextech.erp.model.Bom;
import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.model.Vendor;

public class BomRMVendorAssociationsDTO extends AbstractDTO{

	private float cost;
	private float pricePerUnit;
	private long quantity;
	private Rawmaterial rawmaterialId;
	private Bom bomId;
	private Vendor vendorId;
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
	public Rawmaterial getRawmaterialId() {
		return rawmaterialId;
	}
	public void setRawmaterialId(Rawmaterial rawmaterialId) {
		this.rawmaterialId = rawmaterialId;
	}
	public Bom getBomId() {
		return bomId;
	}
	public void setBomId(Bom bomId) {
		this.bomId = bomId;
	}
	public Vendor getVendorId() {
		return vendorId;
	}
	public void setVendorId(Vendor vendorId) {
		this.vendorId = vendorId;
	}
	
	
}
