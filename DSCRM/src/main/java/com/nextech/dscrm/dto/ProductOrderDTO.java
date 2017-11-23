package com.nextech.dscrm.dto;

import java.util.Date;
import java.util.List;

import com.nextech.dscrm.model.Client;
import com.nextech.dscrm.model.Product;
import com.nextech.dscrm.model.Status;
import com.nextech.dscrm.newDTO.ProductOrderAssociationDTO;

public class ProductOrderDTO extends AbstractDTO{
	
	private Client clientId;
	private Status statusId;
	private Date expectedDeliveryDate;	
	private Date createDate;
	private String invoiceNo;
	private long quantity; 
	private String poNO;
	private float totalAmount;
	private double receivedAmount;
	private Product product;
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
	public float getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}
	public double getReceivedAmount() {
		return receivedAmount;
	}
	public void setReceivedAmount(double receivedAmount) {
		this.receivedAmount = receivedAmount;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}	
	
}
