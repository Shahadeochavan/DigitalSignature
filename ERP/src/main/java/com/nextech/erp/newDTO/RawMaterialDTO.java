package com.nextech.erp.newDTO;


import com.nextech.erp.dto.AbstractDTO;
import com.nextech.erp.model.Rmtype;
import com.nextech.erp.model.Unit;

public class RawMaterialDTO extends AbstractDTO{

	private String rmName;
	private String partNumber;
	private float pricePerUnit;
	private Unit unitId;
	private Rmtype rmTypeId;
	public String getRmName() {
		return rmName;
	}
	public void setRmName(String rmName) {
		this.rmName = rmName;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public float getPricePerUnit() {
		return pricePerUnit;
	}
	public void setPricePerUnit(float pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	public Unit getUnitId() {
		return unitId;
	}
	public void setUnitId(Unit unitId) {
		this.unitId = unitId;
	}
	public Rmtype getRmTypeId() {
		return rmTypeId;
	}
	public void setRmTypeId(Rmtype rmTypeId) {
		this.rmTypeId = rmTypeId;
	}
	
	
}
