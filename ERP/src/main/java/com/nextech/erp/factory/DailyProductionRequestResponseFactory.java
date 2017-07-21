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

}
