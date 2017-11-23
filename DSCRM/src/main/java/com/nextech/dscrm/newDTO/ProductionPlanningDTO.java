package com.nextech.dscrm.newDTO;

import java.util.Date;

import com.nextech.dscrm.dto.AbstractDTO;

public class ProductionPlanningDTO extends AbstractDTO{

	private long completedQuantity;
	private Date date;
	private long dispatchQuantity;
	private int excessQuantity;
	private long lagQuantity;
	private long qualityPendingQuantity;
	private long qualityCheckedQuantity;
	private long failQuantity;
	private long storeOutQuantity;
	private long repairedQuantity;
	private String remark;
	private long targetQuantity;
	private ProductDTO productId;
	private StatusDTO statusId;
	
	public ProductionPlanningDTO(){
	}
	public ProductionPlanningDTO(int id){
	this.setId(id);
	}
	public long getCompletedQuantity() {
		return completedQuantity;
	}
	public void setCompletedQuantity(long completedQuantity) {
		this.completedQuantity = completedQuantity;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public long getDispatchQuantity() {
		return dispatchQuantity;
	}
	public void setDispatchQuantity(long dispatchQuantity) {
		this.dispatchQuantity = dispatchQuantity;
	}
	public int getExcessQuantity() {
		return excessQuantity;
	}
	public void setExcessQuantity(int excessQuantity) {
		this.excessQuantity = excessQuantity;
	}
	public long getLagQuantity() {
		return lagQuantity;
	}
	public void setLagQuantity(long lagQuantity) {
		this.lagQuantity = lagQuantity;
	}
	public long getQualityPendingQuantity() {
		return qualityPendingQuantity;
	}
	public void setQualityPendingQuantity(long qualityPendingQuantity) {
		this.qualityPendingQuantity = qualityPendingQuantity;
	}
	public long getQualityCheckedQuantity() {
		return qualityCheckedQuantity;
	}
	public void setQualityCheckedQuantity(long qualityCheckedQuantity) {
		this.qualityCheckedQuantity = qualityCheckedQuantity;
	}
	public long getFailQuantity() {
		return failQuantity;
	}
	public void setFailQuantity(long failQuantity) {
		this.failQuantity = failQuantity;
	}
	public long getStoreOutQuantity() {
		return storeOutQuantity;
	}
	public void setStoreOutQuantity(long storeOutQuantity) {
		this.storeOutQuantity = storeOutQuantity;
	}
	public long getRepairedQuantity() {
		return repairedQuantity;
	}
	public void setRepairedQuantity(long repairedQuantity) {
		this.repairedQuantity = repairedQuantity;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public long getTargetQuantity() {
		return targetQuantity;
	}
	public void setTargetQuantity(long targetQuantity) {
		this.targetQuantity = targetQuantity;
	}
	public ProductDTO getProductId() {
		return productId;
	}
	public void setProductId(ProductDTO productId) {
		this.productId = productId;
	}
	public StatusDTO getStatusId() {
		return statusId;
	}
	public void setStatusId(StatusDTO statusId) {
		this.statusId = statusId;
	}
	
}
