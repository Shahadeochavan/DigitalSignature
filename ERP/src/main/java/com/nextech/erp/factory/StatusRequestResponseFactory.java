package com.nextech.erp.factory;

import com.nextech.erp.model.Status;
import com.nextech.erp.newDTO.StatusDTO;

public class StatusRequestResponseFactory {
	
	public static Status setStatsu(StatusDTO statusDTO){
		Status status =  new Status();
		status.setId(statusDTO.getId());
		status.setName(statusDTO.getStatusName());
		status.setType(statusDTO.getStatusType());
		status.setDescription(statusDTO.getDescription());
		status.setIsactive(true);
		return status;
	}
	public static Status deleteStatus(StatusDTO statusDTO){
		Status status =  new Status();
		status.setId(statusDTO.getId());
		status.setName(statusDTO.getStatusName());
		status.setType(statusDTO.getStatusType());
		status.setDescription(statusDTO.getDescription());
		status.setIsactive(false);
		return status;
	}

}
