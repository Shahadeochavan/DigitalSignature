package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.model.Product;
import com.nextech.erp.model.Productionplanning;
import com.nextech.erp.model.Status;
import com.nextech.erp.newDTO.ProductDTO;
import com.nextech.erp.newDTO.ProductionPlanningDTO;
import com.nextech.erp.newDTO.StatusDTO;

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
		Product product =  new Product();
		product.setId(productionPlanningDTO.getProductId().getId());
		productionplanning.setProduct(product);
		productionplanning.setQualityCheckedQuantity(productionPlanningDTO.getQualityCheckedQuantity());
		productionplanning.setQualityPendingQuantity(productionPlanningDTO.getQualityPendingQuantity());
		productionplanning.setRemark(productionPlanningDTO.getRemark());
		productionplanning.setRepaired_quantity(productionPlanningDTO.getRepairedQuantity());
		productionplanning.setStoreOut_quantity(productionPlanningDTO.getStoreOutQuantity());
		productionplanning.setTargetQuantity(productionPlanningDTO.getTargetQuantity());
		productionplanning.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		Status status = new Status();
		status.setId(productionPlanningDTO.getStatusId().getId());
		productionplanning.setStatus(status);
		return productionplanning;
	}
	
	public static ProductionPlanningDTO setProductionPlanningDTO(Productionplanning productionplanning){
		ProductionPlanningDTO productionPlanningDTO = new ProductionPlanningDTO();
		productionPlanningDTO.setCompletedQuantity(productionplanning.getCompletedQuantity());
		productionPlanningDTO.setDate(productionplanning.getDate());
		productionPlanningDTO.setDispatchQuantity(productionplanning.getDispatchQuantity());
		productionPlanningDTO.setExcessQuantity(productionplanning.getExcessQuantity());
		productionPlanningDTO.setFailQuantity(productionplanning.getFailQuantity());
		productionPlanningDTO.setId(productionplanning.getId());
		productionPlanningDTO.setActive(true);
		productionPlanningDTO.setLagQuantity(productionplanning.getLagQuantity());
		ProductDTO productDTO =  new ProductDTO();
		productDTO.setId(productionplanning.getProduct().getId());
		productDTO.setPartNumber(productionplanning.getProduct().getPartNumber());
		productionPlanningDTO.setProductId(productDTO);
		productionPlanningDTO.setQualityCheckedQuantity(productionplanning.getQualityCheckedQuantity());
		productionPlanningDTO.setQualityPendingQuantity(productionplanning.getQualityPendingQuantity());
		productionPlanningDTO.setRemark(productionplanning.getRemark());
		productionPlanningDTO.setRepairedQuantity(productionplanning.getRepaired_quantity());
		productionPlanningDTO.setStoreOutQuantity(productionplanning.getStoreOut_quantity());
		productionPlanningDTO.setTargetQuantity(productionplanning.getTargetQuantity());
		StatusDTO statusDTO = new StatusDTO();
		statusDTO.setId(productionplanning.getStatus().getId());
		statusDTO.setStatusName(productionplanning.getStatus().getName());
		productionPlanningDTO.setStatusId(statusDTO);
		return productionPlanningDTO;
	}

}
