package com.nextech.erp.newDTO;


import com.nextech.erp.dto.AbstractDTO;

public class RawMaterialDTO extends AbstractDTO{

	private String rmName;
	private String partNumber;
	private float pricePerUnit;
	private UnitDTO unitId;
	private RMTypeDTO rmTypeId;
	private String design;
	
	public RawMaterialDTO(){
		
	}
	public RawMaterialDTO(int id){
		this.setId(id);
	}
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
	public String getDesign() {
		return design;
	}
	public void setDesign(String design) {
		this.design = design;
	}
	public UnitDTO getUnitId() {
		return unitId;
	}
	public void setUnitId(UnitDTO unitId) {
		this.unitId = unitId;
	}
	public RMTypeDTO getRmTypeId() {
		return rmTypeId;
	}
	public void setRmTypeId(RMTypeDTO rmTypeId) {
		this.rmTypeId = rmTypeId;
	}
	
	
}
