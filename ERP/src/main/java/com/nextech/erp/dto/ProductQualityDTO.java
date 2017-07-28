package com.nextech.erp.dto;

import java.util.List;

import com.nextech.erp.model.Product;


public class ProductQualityDTO extends AbstractDTO{
	private long productionPlanId;
	private long checkQuantity;
	private long goodQuantity;
	private long rejectedQuantity;
	private Product productId;
	private String remark;

	private List<ProductQualityPart> productQualityParts;
	public long getProductionPlanId() {
		return productionPlanId;
	}
	public void setProductionPlanId(long productionPlanId) {
		this.productionPlanId = productionPlanId;
	}
	public List<ProductQualityPart> getProductQualityParts() {
		return productQualityParts;
	}
	public void setProductQualityParts(List<ProductQualityPart> productQualityParts) {
		this.productQualityParts = productQualityParts;
	}
	public long getCheckQuantity() {
		return checkQuantity;
	}
	public void setCheckQuantity(long checkQuantity) {
		this.checkQuantity = checkQuantity;
	}
	public long getGoodQuantity() {
		return goodQuantity;
	}
	public void setGoodQuantity(long goodQuantity) {
		this.goodQuantity = goodQuantity;
	}
	public long getRejectedQuantity() {
		return rejectedQuantity;
	}
	public void setRejectedQuantity(long rejectedQuantity) {
		this.rejectedQuantity = rejectedQuantity;
	}
	public Product getProductId() {
		return productId;
	}
	public void setProductId(Product productId) {
		this.productId = productId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
