package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.model.Statustransition;
import com.nextech.erp.newDTO.StatusTransitionDTO;

public class StatusTransitionRequestResponseFactory {
	
	public static Statustransition setStatusTransitin(StatusTransitionDTO statusTransitionDTO ,HttpServletRequest request){
		Statustransition statustransition = new Statustransition();
		statustransition.setDescription(statusTransitionDTO.getDescription());
		statustransition.setFromStatus(statusTransitionDTO.getFromStatus());
		statustransition.setId(statusTransitionDTO.getId());
		statustransition.setIsNotificationEmail(statusTransitionDTO.getIsNotificationEmail());
		statustransition.setIsNotificationSMS(statusTransitionDTO.getIsNotificationSMS());
		statustransition.setName(statusTransitionDTO.getName());
		statustransition.setToStatus(statusTransitionDTO.getToStatus());
		statustransition.setIsactive(true);
		statustransition.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return statustransition;
	}
	public static Statustransition setStatusTransitinUpdate(StatusTransitionDTO statusTransitionDTO ,HttpServletRequest request){
		Statustransition statustransition = new Statustransition();
		statustransition.setDescription(statusTransitionDTO.getDescription());
		statustransition.setFromStatus(statusTransitionDTO.getFromStatus());
		statustransition.setId(statusTransitionDTO.getId());
		statustransition.setIsNotificationEmail(statusTransitionDTO.getIsNotificationEmail());
		statustransition.setIsNotificationSMS(statusTransitionDTO.getIsNotificationSMS());
		statustransition.setName(statusTransitionDTO.getName());
		statustransition.setToStatus(statusTransitionDTO.getToStatus());
		statustransition.setIsactive(true);
		statustransition.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return statustransition;
	}
	
	public static StatusTransitionDTO setStatusTransitinDTO(Statustransition statustransition){
		StatusTransitionDTO statusTransitionDTO = new StatusTransitionDTO();
		statusTransitionDTO.setDescription(statustransition.getDescription());
		statusTransitionDTO.setFromStatus(statustransition.getFromStatus());
		statusTransitionDTO.setId(statustransition.getId());
		statusTransitionDTO.setIsNotificationEmail(statustransition.getIsNotificationEmail());
		statusTransitionDTO.setIsNotificationSMS(statustransition.getIsNotificationSMS());
		statusTransitionDTO.setName(statustransition.getName());
		statusTransitionDTO.setToStatus(statustransition.getToStatus());
		statusTransitionDTO.setActive(true);
		return statusTransitionDTO;
	}

}
