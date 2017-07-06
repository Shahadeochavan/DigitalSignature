package com.nextech.erp.dto;

public class ProductQualityPart {
	private long productionPlanId;
	private long productId;
	private int productQuantity;
	private int passQuantity;
	private long failQuantity;
	private String remark;
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public int getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}
	public int getPassQuantity() {
		return passQuantity;
	}
	public void setPassQuantity(int passQuantity) {
		this.passQuantity = passQuantity;
	}
	public long getFailQuantity() {
		return failQuantity;
	}
	public void setFailQuantity(long failQuantity) {
		this.failQuantity = failQuantity;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public long getProductionPlanId() {
		return productionPlanId;
	}
	public void setProductionPlanId(long productionPlanId) {
		this.productionPlanId = productionPlanId;
	}
	
}
