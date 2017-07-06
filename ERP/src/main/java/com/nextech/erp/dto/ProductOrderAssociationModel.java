package com.nextech.erp.dto;

import java.sql.Date;
import java.util.List;

import com.nextech.erp.model.Productorderassociation;

public class ProductOrderAssociationModel {
	
	private String description;
	private long client;
	private long status;
	private Date expecteddeliveryDate;	
	private Date createDate;
	private String invoiceNo;
	private List<Productorderassociation> orderproductassociations;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getClient() {
		return client;
	}
	public void setClient(long client) {
		this.client = client;
	}
	
	public Date getExpecteddeliveryDate() {
		return expecteddeliveryDate;
	}
	public void setExpecteddeliveryDate(Date expecteddeliveryDate) {
		this.expecteddeliveryDate = expecteddeliveryDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public List<Productorderassociation> getOrderproductassociations() {
		return orderproductassociations;
	}
	public void setOrderproductassociations(
			List<Productorderassociation> orderproductassociations) {
		this.orderproductassociations = orderproductassociations;
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
	
}
