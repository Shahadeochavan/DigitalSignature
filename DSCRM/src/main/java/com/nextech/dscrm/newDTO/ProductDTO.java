package com.nextech.dscrm.newDTO;



import com.nextech.dscrm.dto.AbstractDTO;



public class ProductDTO  extends AbstractDTO{

	private String productType;
	private String productValidity;
	private String name;
	private float pricePerUnit;
	
	public ProductDTO(){
		
	}
	public ProductDTO(int id){
		this.setId(id);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductValidity() {
		return productValidity;
	}
	public void setProductValidity(String productValidity) {
		this.productValidity = productValidity;
	}
	public float getPricePerUnit() {
		return pricePerUnit;
	}
	public void setPricePerUnit(float pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	
}
