package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.dto.DailyProductionPlanDTO;
import com.nextech.erp.model.Dailyproduction;
import com.nextech.erp.model.Productionplanning;

public class DailyProductionRequestResponseFactory {
	
	public static Dailyproduction setDailyProduction(DailyProductionPlanDTO dailyProductionPlanDTO,HttpServletRequest request){
		Dailyproduction dailyproduction = new Dailyproduction();
		dailyproduction.setId(dailyProductionPlanDTO.getId());
		dailyproduction.setAchivedQuantity(dailyProductionPlanDTO.getAchivedQuantity());
		Productionplanning productionplanning =  new Productionplanning();
		productionplanning.setId(dailyProductionPlanDTO.getId());
		dailyproduction.setProductionplanning(productionplanning);
		dailyproduction.setRemark(dailyProductionPlanDTO.getRemark());
		dailyproduction.setRepaired_quantity(dailyProductionPlanDTO.getRepairedQuantity());
		dailyproduction.setTargetQuantity(dailyProductionPlanDTO.getTargetQuantity());
		dailyproduction.setIsactive(true);
		dailyproduction.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return dailyproduction;
	}
	
	public static Dailyproduction setDailyProductionUpdate(DailyProductionPlanDTO dailyProductionPlanDTO,HttpServletRequest request){
		Dailyproduction dailyproduction = new Dailyproduction();
		dailyproduction.setId(dailyProductionPlanDTO.getId());
		dailyproduction.setAchivedQuantity(dailyProductionPlanDTO.getAchivedQuantity());
		Productionplanning productionplanning =  new Productionplanning();
		productionplanning.setId(dailyProductionPlanDTO.getId());
		dailyproduction.setProductionplanning(productionplanning);
		dailyproduction.setRemark(dailyProductionPlanDTO.getRemark());
		dailyproduction.setRepaired_quantity(dailyProductionPlanDTO.getRepairedQuantity());
		dailyproduction.setTargetQuantity(dailyProductionPlanDTO.getTargetQuantity());
		dailyproduction.setIsactive(true);
		dailyproduction.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return dailyproduction;
	}
	
	public static DailyProductionPlanDTO setDailyProductionDTO(Dailyproduction dailyproduction){
		DailyProductionPlanDTO  dailyProductionPlanDTO = new DailyProductionPlanDTO();
		dailyProductionPlanDTO.setAchivedQuantity(dailyproduction.getAchivedQuantity());
		dailyProductionPlanDTO.setActive(true);
		dailyProductionPlanDTO.setCreatedBy(dailyproduction.getCreatedBy());
		dailyProductionPlanDTO.setId(dailyproduction.getId());
		dailyProductionPlanDTO.setProductionPlanId(dailyproduction.getProductionplanning());
		dailyProductionPlanDTO.setRemark(dailyproduction.getRemark());
		dailyProductionPlanDTO.setRepairedQuantity((int)dailyproduction.getRepaired_quantity());
		dailyProductionPlanDTO.setTargetQuantity(dailyproduction.getTargetQuantity());
		dailyProductionPlanDTO.setUpdatedBy(dailyproduction.getUpdatedBy());
		dailyProductionPlanDTO.setUpdatedDate(dailyproduction.getUpdatedDate());
		return dailyProductionPlanDTO;
	}

}
