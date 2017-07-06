package com.nextech.erp.dao;

import java.util.List;

import com.nextech.erp.model.Dailyproduction;

public interface DailyproductionDao extends SuperDao<Dailyproduction>{
	public List<Dailyproduction> getDailyProdPendingForQualityCheckByPlanningId(long planningId);
}
