package com.nextech.erp.newDTO;

import com.nextech.erp.dto.AbstractDTO;



public class StatusDTO extends AbstractDTO{
	
	private String statusName;
	private String statusType;
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getStatusType() {
		return statusType;
	}
	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}
	
}
