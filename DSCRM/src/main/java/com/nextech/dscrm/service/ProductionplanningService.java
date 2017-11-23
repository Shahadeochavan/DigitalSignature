package com.nextech.dscrm.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nextech.dscrm.dto.ProductionPlan;
import com.nextech.dscrm.model.Product;
import com.nextech.dscrm.model.Productionplanning;
import com.nextech.dscrm.newDTO.ProductionPlanningDTO;

public interface ProductionplanningService extends CRUDService<Productionplanning>{

	public Productionplanning getProductionPlanningforCurrentMonthByProductIdAndDate(long pId,Date date)throws Exception;

	public List<ProductionPlanningDTO> getProductionplanningByMonth(Date month) throws Exception;

	public List<ProductionPlan> getProductionPlanForCurrentMonth() throws Exception;

	public List<ProductionPlanningDTO> updateProductionPlanByMonthYear(String month_year) throws Exception;

	public List<Productionplanning> createProductionPlanMonthYear(List<Product> productList,String month_year,HttpServletRequest request,HttpServletResponse response) throws Exception;

	public void updateProductionplanningForCurrentMonth(List<ProductionPlan> productionplanningList,HttpServletRequest request,HttpServletResponse response) throws Exception;

	public ProductionPlanningDTO getProductionplanByDateAndProductId(Date date,long pId)throws Exception;

	public List<ProductionPlanningDTO> getProductionplanByDate(Date date)throws Exception;

	public List<ProductionPlanningDTO> getProductionplanByProdutId(Date date,long productID)throws Exception;
	
	public List<ProductionPlanningDTO> getProductionPlanList() throws Exception;
	
	public ProductionPlanningDTO getProductionPlanById(long id)throws Exception;
	
	public ProductionPlanningDTO deleteProduction(long id)throws Exception;

}
