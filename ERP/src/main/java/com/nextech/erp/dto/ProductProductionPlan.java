package com.nextech.erp.dto;

import java.util.Date;



public class ProductProductionPlan {

	private Date productionDate;
	private long target_quantity;
	private long achived_quantity;
	private long dispatch_quantity;

	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public long getTarget_quantity() {
		return target_quantity;
	}

	public void setTarget_quantity(long target_quantity) {
		this.target_quantity = target_quantity;
	}

	public long getAchived_quantity() {
		return achived_quantity;
	}

	public void setAchived_quantity(long achived_quantity) {
		this.achived_quantity = achived_quantity;
	}

	public long getDispatch_quantity() {
		return dispatch_quantity;
	}

	public void setDispatch_quantity(long dispatch_quantity) {
		this.dispatch_quantity = dispatch_quantity;
	}

}
