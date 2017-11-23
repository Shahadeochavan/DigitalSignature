package com.nextech.dscrm.newDTO;


import com.nextech.dscrm.dto.AbstractDTO;
import com.nextech.dscrm.dto.ProductOrderDTO;
public class ProductOrderAssociationDTO extends AbstractDTO {

	private long quantity;
	private long remainingQuantity;
	private ProductDTO productId;
	private ProductOrderDTO productOrderId;
	
	public ProductOrderAssociationDTO(){
		
	}
    public ProductOrderAssociationDTO(int id){
	this.setId(id);	
	}
	
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
	public ProductDTO getProductId() {
		return productId;
	}
	public void setProductId(ProductDTO productId) {
		this.productId = productId;
	}
	public ProductOrderDTO getProductOrderId() {
		return productOrderId;
	}
	public void setProductOrderId(ProductOrderDTO productOrderId) {
		this.productOrderId = productOrderId;
	}

}
