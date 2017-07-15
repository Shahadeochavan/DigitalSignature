package com.nextech.erp.newDTO;

import java.util.Date;

import com.nextech.erp.dto.AbstractDTO;
import com.nextech.erp.model.Product;
import com.nextech.erp.model.Status;

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
	private Product productId;
	private Status statusId;
	
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
	public Product getProductId() {
		return productId;
	}
	public void setProductId(Product productId) {
		this.productId = productId;
	}
	public Status getStatusId() {
		return statusId;
	}
	public void setStatusId(Status statusId) {
		this.statusId = statusId;
	}
	
	


}
