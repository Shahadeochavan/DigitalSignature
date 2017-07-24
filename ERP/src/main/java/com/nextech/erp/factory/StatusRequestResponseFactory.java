package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.model.Status;
import com.nextech.erp.newDTO.StatusDTO;

public class StatusRequestResponseFactory {
	
	public static Status setStatus(StatusDTO statusDTO,HttpServletRequest request){
		Status status =  new Status();
		status.setId(statusDTO.getId());
		status.setName(statusDTO.getStatusName());
		status.setType(statusDTO.getStatusType());
		status.setDescription(statusDTO.getDescription());
		status.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		status.setIsactive(true);
		return status;
	}
	public static Status setStatusUpdate(StatusDTO statusDTO,HttpServletRequest request){
		Status status =  new Status();
		status.setId(statusDTO.getId());
		status.setName(statusDTO.getStatusName());
		status.setType(statusDTO.getStatusType());
		status.setDescription(statusDTO.getDescription());
		status.setIsactive(true);
		status.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return status;
	}
	
	public static StatusDTO setStatusDTO(Status  status){
		StatusDTO statusDTO =  new StatusDTO();
		statusDTO.setActive(true);
		statusDTO.setCreatedBy(status.getCreatedBy());
		statusDTO.setCreatedDate(status.getCreatedDate());
		statusDTO.setDescription(status.getDescription());
		statusDTO.setId(status.getId());
		statusDTO.setStatusName(status.getName());
		statusDTO.setStatusType(status.getType());
		statusDTO.setUpdatedBy(status.getUpdatedBy());
		statusDTO.setUpdatedDate(status.getUpdatedDate());
		return statusDTO;
	}

}
