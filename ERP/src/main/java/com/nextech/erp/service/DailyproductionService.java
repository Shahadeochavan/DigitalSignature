package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Dailyproduction;

public interface DailyproductionService extends CRUDService<Dailyproduction> {
	public List<Dailyproduction> getDailyProdPendingForQualityCheckByPlanningId(long planningId);
}
