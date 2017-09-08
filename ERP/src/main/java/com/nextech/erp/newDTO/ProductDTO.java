package com.nextech.erp.newDTO;



import com.nextech.erp.dto.AbstractDTO;



public class ProductDTO  extends AbstractDTO{

	private String clientPartNumber;
	private String design;
	private String name;
	private String partNumber;
	private long ratePerUnit;
	private TaxStructureDTO taxStructureDTO;
	
	public ProductDTO(){
		
	}
	public ProductDTO(int id){
		this.setId(id);
	}
	public String getClientPartNumber() {
		return clientPartNumber;
	}
	public void setClientPartNumber(String clientPartNumber) {
		this.clientPartNumber = clientPartNumber;
	}
	public String getDesign() {
		return design;
	}
	public void setDesign(String design) {
		this.design = design;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public long getRatePerUnit() {
		return ratePerUnit;
	}
	public void setRatePerUnit(long ratePerUnit) {
		this.ratePerUnit = ratePerUnit;
	}
	public TaxStructureDTO getTaxStructureDTO() {
		return taxStructureDTO;
	}
	public void setTaxStructureDTO(TaxStructureDTO taxStructureDTO) {
		this.taxStructureDTO = taxStructureDTO;
	}
	
}
