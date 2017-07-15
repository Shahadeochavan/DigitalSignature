package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.model.Productionplanning;
import com.nextech.erp.newDTO.ProductionPlanningDTO;

public class ProductionPlanningRequestResponseFactory {
	
	public static Productionplanning setProductionPlanning(ProductionPlanningDTO productionPlanningDTO,HttpServletRequest request){
		Productionplanning productionplanning = new Productionplanning();
		productionplanning.setCompletedQuantity(productionPlanningDTO.getCompletedQuantity());
		productionplanning.setDate(productionPlanningDTO.getDate());
		productionplanning.setDispatchQuantity(productionPlanningDTO.getDispatchQuantity());
		productionplanning.setExcessQuantity(productionPlanningDTO.getExcessQuantity());
		productionplanning.setFailQuantity(productionPlanningDTO.getFailQuantity());
		productionplanning.setId(productionPlanningDTO.getId());
		productionplanning.setIsactive(true);
		productionplanning.setLagQuantity(productionPlanningDTO.getLagQuantity());
		productionplanning.setProduct(productionPlanningDTO.getProductId());
		productionplanning.setQualityCheckedQuantity(productionPlanningDTO.getQualityCheckedQuantity());
		productionplanning.setQualityPendingQuantity(productionPlanningDTO.getQualityPendingQuantity());
		productionplanning.setRemark(productionPlanningDTO.getRemark());
		productionplanning.setRepaired_quantity(productionPlanningDTO.getRepairedQuantity());
		productionplanning.setStoreOut_quantity(productionPlanningDTO.getStoreOutQuantity());
		productionplanning.setTargetQuantity(productionPlanningDTO.getTargetQuantity());
		productionplanning.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return productionplanning;
	}

}