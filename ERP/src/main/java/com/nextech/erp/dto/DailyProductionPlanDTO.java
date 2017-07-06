package com.nextech.erp.dto;

public class DailyProductionPlanDTO {
	private long productionPlanId;
	private long productId;
	private int targetQuantity;
	private int achivedQuantity;
	private int repairedQuantity;
	private String remark;
	public long getProductionPlanId() {
		return productionPlanId;
	}
	public void setProductionPlanId(long productionPlanId) {
		this.productionPlanId = productionPlanId;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public int getTargetQuantity() {
		return targetQuantity;
	}
	public void setTargetQuantity(int targetQuantity) {
		this.targetQuantity = targetQuantity;
	}
	public int getAchivedQuantity() {
		return achivedQuantity;
	}
	public void setAchivedQuantity(int achivedQuantity) {
		this.achivedQuantity = achivedQuantity;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getRepairedQuantity() {
		return repairedQuantity;
	}
	public void setRepairedQuantity(int repairedQuantity) {
		this.repairedQuantity = repairedQuantity;
	}

}
