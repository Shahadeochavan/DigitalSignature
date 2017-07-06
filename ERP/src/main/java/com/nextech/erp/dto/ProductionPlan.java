package com.nextech.erp.dto;

import java.util.List;

public class ProductionPlan {
	
	private String month_year;
	private long productId;
	private String productName;
	private long productBalanceQty;
	private long productTargetQty;
	private List<ProductProductionPlan> productProductionPlan;

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public long getProductBalanceQty() {
		return productBalanceQty;
	}

	public void setProductBalanceQty(long productBalanceQty) {
		this.productBalanceQty = productBalanceQty;
	}

	public long getProductTargetQty() {
		return productTargetQty;
	}

	public void setProductTargetQty(long productTargetQty) {
		this.productTargetQty = productTargetQty;
	}

	public String getMonth_year() {
		return month_year;
	}

	public void setMonth_year(String month_year) {
		this.month_year = month_year;
	}

	public List<ProductProductionPlan> getProductProductionPlan() {
		return productProductionPlan;
	}

	public void setProductProductionPlan(
			List<ProductProductionPlan> productProductionPlan) {
		this.productProductionPlan = productProductionPlan;
	}
}