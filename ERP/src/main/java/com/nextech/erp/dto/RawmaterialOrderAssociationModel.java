package com.nextech.erp.dto;

import java.sql.Date;
import java.util.List;

import com.nextech.erp.model.Rawmaterialorderassociation;

public class RawmaterialOrderAssociationModel {
	
	private String description;
	private Date expecteddeliveryDate;	
	private Date createDate;
	private long Vendor;
	private long status;
	private String name;
	private float otherCharges;
	private float tax;
	private float totalprice;
	private float actualPrice;
	private List<Rawmaterialorderassociation> rawmaterialorderassociations;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public List<Rawmaterialorderassociation> getRawmaterialorderassociations() {
		return rawmaterialorderassociations;
	}
	public void setRawmaterialorderassociations(
			List<Rawmaterialorderassociation> rawmaterialorderassociations) {
		this.rawmaterialorderassociations = rawmaterialorderassociations;
	}
	public long getVendor() {
		return Vendor;
	}
	public void setVendor(long vendor) {
		Vendor = vendor;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getOtherCharges() {
		return otherCharges;
	}
	public void setOtherCharges(float otherCharges) {
		this.otherCharges = otherCharges;
	}
	public float getTax() {
		return tax;
	}
	public void setTax(float tax) {
		this.tax = tax;
	}
	public float getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(float totalprice) {
		this.totalprice = totalprice;
	}
	public float getActualPrice() {
		return actualPrice;
	}
	public void setActualPrice(float actualPrice) {
		this.actualPrice = actualPrice;
	}

}
