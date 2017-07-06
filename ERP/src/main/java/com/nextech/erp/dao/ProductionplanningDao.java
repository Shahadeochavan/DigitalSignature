package com.nextech.erp.dao;

import java.util.Date;
import java.util.List;

import com.nextech.erp.model.Productionplanning;

public interface ProductionplanningDao extends SuperDao<Productionplanning> {
	public Productionplanning getProductionPlanningforCurrentMonthByProductIdAndDate(
			long pId, Date date) throws Exception;

	public List<Productionplanning> getProductionplanningByCurrentMonth(Date month) throws Exception;

	public List<Productionplanning> getProductionPlanByMonthYear(Date startDate,Date endDate) throws Exception;

	public List<Productionplanning> updateProductionPlanByMonthYear(String month_year) throws Exception;

	public Productionplanning getProductionPlanningByDateAndProductId(Date productionDateStart,Date productionDateEnd,
			long product_id);

	public Productionplanning getProductionplanByDateAndProductId(Date date,long pId)throws Exception;

	public List<Productionplanning> getProductionplanByDate(Date date)throws Exception;

	public List<Productionplanning> getProductionplanByProdutId(Date date,long productID)throws Exception;

}
