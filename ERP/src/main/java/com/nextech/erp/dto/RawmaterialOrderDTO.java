package com.nextech.erp.dto;

import java.util.Date;
import java.util.List;
import com.nextech.erp.model.Status;
import com.nextech.erp.model.Vendor;
import com.nextech.erp.newDTO.RMOrderAssociationDTO;

public class RawmaterialOrderDTO extends AbstractDTO{
	
	private Date expectedDeliveryDate;	
	private Date createDate;
	private Vendor vendorId;
	private Status statusId;
	private String name;
	private float otherCharges;
	private float tax;
	private float totalPrice;
	private float actualPrice;
	private int quantity;
	List<RMOrderAssociationDTO> rmOrderAssociationDTOs;

   public RawmaterialOrderDTO(){
	   
   }
   
   public RawmaterialOrderDTO(int id){
	   this.setId(id);
   }
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Vendor getVendorId() {
		return vendorId;
	}
	public void setVendorId(Vendor vendorId) {
		this.vendorId = vendorId;
	}
	public Status getStatusId() {
		return statusId;
	}
	public void setStatusId(Status statusId) {
		this.statusId = statusId;
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
	
	
	public Date getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}
	public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}
	public List<RMOrderAssociationDTO> getRmOrderAssociationDTOs() {
		return rmOrderAssociationDTOs;
	}
	public void setRmOrderAssociationDTOs(
			List<RMOrderAssociationDTO> rmOrderAssociationDTOs) {
		this.rmOrderAssociationDTOs = rmOrderAssociationDTOs;
	}
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

}
