package com.nextech.erp.dto;

import java.util.List;

import com.nextech.erp.model.Rawmaterial;

public class ProductRMAssociationDTO extends AbstractDTO {
	private long product;
	private String name;
	private long quantity;
	private Rawmaterial rawmaterialId;

	private List<ProductRMAssociationModelParts> productRMAssociationModelParts;


	public long getProduct() {
		return product;
	}
	public void setProduct(long product) {
		this.product = product;
	}
	public List<ProductRMAssociationModelParts> getProductRMAssociationModelParts() {
		return productRMAssociationModelParts;
	}
	public void setProductRMAssociationModelParts(
			List<ProductRMAssociationModelParts> productRMAssociationModelParts) {
		this.productRMAssociationModelParts = productRMAssociationModelParts;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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

}
