package com.nextech.erp.newDTO;


import com.nextech.erp.dto.AbstractDTO;
import com.nextech.erp.model.Product;
import com.nextech.erp.model.Productorder;

public class ProductOrderAssociationDTO extends AbstractDTO {

	private long quantity;
	private long remainingQuantity;
	private Product productId;
	private Productorder productOrderId;
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public long getRemainingQuantity() {
		return remainingQuantity;
	}
	public void setRemainingQuantity(long remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}
	public Product getProductId() {
		return productId;
	}
	public void setProductId(Product productId) {
		this.productId = productId;
	}
	public Productorder getProductOrderId() {
		return productOrderId;
	}
	public void setProductOrderId(Productorder productOrderId) {
		this.productOrderId = productOrderId;
	}
	
	
}
