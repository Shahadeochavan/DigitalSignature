package com.nextech.erp.newDTO;



import com.nextech.erp.dto.AbstractDTO;

public class RMVendorAssociationDTO extends AbstractDTO {
	
	private float pricePerUnit;
	private RawMaterialDTO rawmaterialId;
	private VendorDTO vendorId;
	private TaxStructureDTO taxStructureDTO;
	public RMVendorAssociationDTO(){
		
	}
	public	RMVendorAssociationDTO(int id){
		this.setId(id);
	}
	public float getPricePerUnit() {
		return pricePerUnit;
	}
	public void setPricePerUnit(float pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	public RawMaterialDTO getRawmaterialId() {
		return rawmaterialId;
	}
	public void setRawmaterialId(RawMaterialDTO rawmaterialId) {
		this.rawmaterialId = rawmaterialId;
	}
	public VendorDTO getVendorId() {
		return vendorId;
	}
	public void setVendorId(VendorDTO vendorId) {
		this.vendorId = vendorId;
	}
	public TaxStructureDTO getTaxStructureDTO() {
		return taxStructureDTO;
	}
	public void setTaxStructureDTO(TaxStructureDTO taxStructureDTO) {
		this.taxStructureDTO = taxStructureDTO;
	}
	
}
