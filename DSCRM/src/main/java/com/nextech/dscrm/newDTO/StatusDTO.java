package com.nextech.dscrm.newDTO;

import com.nextech.dscrm.dto.AbstractDTO;



public class StatusDTO extends AbstractDTO{
	
	private String statusName;
	private String statusType;
	
	public StatusDTO(){
		
	}
	public StatusDTO(int id){
		this.setId(id);
	}
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
