package com.nextech.erp.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.dto.DailyProductionPlanDTO;
import com.nextech.erp.model.Dailyproduction;

public interface DailyproductionService extends CRUDService<Dailyproduction> {
	public List<Dailyproduction> getDailyProdPendingForQualityCheckByPlanningId(long planningId);
	
	public List<DailyProductionPlanDTO> getDailyProductionList() throws Exception;
	
	public DailyProductionPlanDTO getDailyProductionById(long id) throws Exception;
	
	public void deleteDailyProduction(long id) throws Exception;
	
	public DailyProductionPlanDTO saveDailyProduction(DailyProductionPlanDTO dailyProductionPlanDTO,HttpServletRequest request)throws Exception;
	
}
