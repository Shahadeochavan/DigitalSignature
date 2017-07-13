package com.nextech.erp.dto;

import java.util.List;

import com.nextech.erp.model.Product;

public class BomDTO extends AbstractDTO{
	private long id;
	private Product product;
	private String bomId;
	private List<BomModelPart> bomModelParts;



	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
