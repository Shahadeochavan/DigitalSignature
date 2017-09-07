package com.nextech.erp.dto;

import com.nextech.erp.newDTO.RawMaterialDTO;

public class QualityCheckRMDTO extends AbstractDTO{
	
	private String rmPartNo;
	private long quantity;
	private long goodQuantity;
	private long remainingQuantity;
	private long intakeQuantity;
	private boolean isReturnInvoiceInitated;
	private String remark;
	private RawMaterialDTO  rawMaterailId;
	private float priceperunit;
	
	public QualityCheckRMDTO(){
		
	}
	public QualityCheckRMDTO(int id){
		this.setId(id);
	}
	
	public long getGoodQuantity() {
		return goodQuantity;
	}
	public void setGoodQuantity(long goodQuantity) {
		this.goodQuantity = goodQuantity;
	}
	public long getIntakeQuantity() {
		return intakeQuantity;
	}
	public void setIntakeQuantity(long intakeQuantity) {
		this.intakeQuantity = intakeQuantity;
	}
	public boolean isReturnInvoiceInitated() {
		return isReturnInvoiceInitated;
	}
	public void setReturnInvoiceInitated(boolean isReturnInvoiceInitated) {
		this.isReturnInvoiceInitated = isReturnInvoiceInitated;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public String getRmPartNo() {
		return rmPartNo;
	}
	public void setRmPartNo(String rmPartNo) {
		this.rmPartNo = rmPartNo;
	}
	public long getRemainingQuantity() {
		return remainingQuantity;
	}
	public void setRemainingQuantity(long remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}
	
	public RawMaterialDTO getRawMaterailId() {
		return rawMaterailId;
	}
	public void setRawMaterailId(RawMaterialDTO rawMaterailId) {
		this.rawMaterailId = rawMaterailId;
	}
	public float getPriceperunit() {
		return priceperunit;
	}
	public void setPriceperunit(float priceperunit) {
		this.priceperunit = priceperunit;
	}
}
