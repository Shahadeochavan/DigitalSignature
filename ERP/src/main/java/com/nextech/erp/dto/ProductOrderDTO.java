package com.nextech.erp.dto;

import java.sql.Date;
import java.util.List;

import com.nextech.erp.model.Client;
import com.nextech.erp.newDTO.ProductOrderAssociationDTO;

public class ProductOrderDTO extends AbstractDTO{
	
	private Client clientId;
	private long status;
	private Date expectedDeliveryDate;	
	private Date createDate;
	private String invoiceNo;
	private long quantity; 
	private List<ProductOrderAssociationDTO> productOrderAssociationDTOs;


	
	public Client getClientId() {
		return clientId;
	}
	public void setClientId(Client clientId) {
		this.clientId = clientId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public List<ProductOrderAssociationDTO> getProductOrderAssociationDTOs() {
		return productOrderAssociationDTOs;
	}
	public void setProductOrderAssociationDTOs(
			List<ProductOrderAssociationDTO> productOrderAssociationDTOs) {
		this.productOrderAssociationDTOs = productOrderAssociationDTOs;
	}
	public Date getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}
	public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}	
	
}
