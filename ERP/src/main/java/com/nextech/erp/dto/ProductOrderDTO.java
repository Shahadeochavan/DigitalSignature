package com.nextech.erp.dto;

import java.util.Date;
import java.util.List;

import com.nextech.erp.model.Client;
import com.nextech.erp.model.Status;
import com.nextech.erp.newDTO.ProductOrderAssociationDTO;

public class ProductOrderDTO extends AbstractDTO{
	
	private Client clientId;
	private Status statusId;
	private Date expectedDeliveryDate;	
	private Date createDate;
	private String invoiceNo;
	private long quantity; 
	private String poNO;
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
	
	public Status getStatusId() {
		return statusId;
	}
	public void setStatusId(Status statusId) {
		this.statusId = statusId;
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
	public String getPoNO() {
		return poNO;
	}
	public void setPoNO(String poNO) {
		this.poNO = poNO;
	}	
}
