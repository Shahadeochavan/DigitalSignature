package com.nextech.erp.dto;

import java.sql.Date;
import java.util.List;

import com.nextech.erp.model.Rawmaterialorderassociation;
import com.nextech.erp.newDTO.RMOrderAssociationDTO;

public class RawmaterialOrderDTO extends AbstractDTO{
	
	private Date expecteddeliveryDate;	
	private Date createDate;
	private long Vendor;
	private long status;
	private String name;
	private float otherCharges;
	private float tax;
	private float totalprice;
	private float actualPrice;
	private int quantity;
	List<RMOrderAssociationDTO> rmOrderAssociationDTOs;

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
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public List<RMOrderAssociationDTO> getRmOrderAssociationDTOs() {
		return rmOrderAssociationDTOs;
	}
	public void setRmOrderAssociationDTOs(
			List<RMOrderAssociationDTO> rmOrderAssociationDTOs) {
		this.rmOrderAssociationDTOs = rmOrderAssociationDTOs;
	}

}
