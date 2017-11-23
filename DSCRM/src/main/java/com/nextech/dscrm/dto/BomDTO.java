package com.nextech.dscrm.dto;

import java.util.List;

import com.nextech.dscrm.newDTO.ProductDTO;

public class BomDTO extends AbstractDTO{
	private ProductDTO product;
	private String bomId;
	private List<BomModelPart> bomModelParts;

    public BomDTO(){
    	
    }
	public BomDTO(int id){
		this.setId(id);
	}
	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}

	public String getBomId() {
		return bomId;
	}

	public void setBomId(String bomId) {
		this.bomId = bomId;
	}

	public List<BomModelPart> getBomModelParts() {
		return bomModelParts;
	}

	public void setBomModelParts(List<BomModelPart> bomModelParts) {
		this.bomModelParts = bomModelParts;
	}

}
