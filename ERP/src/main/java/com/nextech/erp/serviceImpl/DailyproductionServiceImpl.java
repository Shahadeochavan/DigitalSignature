package com.nextech.erp.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dao.DailyproductionDao;
import com.nextech.erp.dao.ProductionplanningDao;
import com.nextech.erp.dao.StatusDao;
import com.nextech.erp.dto.DailyProductionPlanDTO;
import com.nextech.erp.dto.TodaysProductionPlanDTO;
import com.nextech.erp.factory.DailyProductionRequestResponseFactory;
import com.nextech.erp.model.Dailyproduction;
import com.nextech.erp.model.Productionplanning;
import com.nextech.erp.model.Status;
import com.nextech.erp.service.DailyproductionService;
@Service
public class DailyproductionServiceImpl extends CRUDServiceImpl<Dailyproduction> implements DailyproductionService {

	@Autowired
	DailyproductionDao dailyproductionDao; 
	
	@Autowired
	StatusDao statusDao;
	
	@Autowired 
	ProductionplanningDao productionplanningDao;
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	public List<Dailyproduction> getDailyProdPendingForQualityCheckByPlanningId(long planningId) {
		return dailyproductionDao.getDailyProdPendingForQualityCheckByPlanningId(planningId);
	}

	@Override
	public List<DailyProductionPlanDTO> getDailyProductionList()
			throws Exception {
		List<DailyProductionPlanDTO> dailyProductionPlanDTOs = new ArrayList<DailyProductionPlanDTO>();
		List<Dailyproduction> dailyproductions =  dailyproductionDao.getList(Dailyproduction.class);
		if(dailyproductions.isEmpty()){
			return null;
		}
		for (Dailyproduction dailyproduction : dailyproductions) {
		DailyProductionPlanDTO dailyProductionPlanDTO =  DailyProductionRequestResponseFactory.setDailyProductionDTO(dailyproduction);
		dailyProductionPlanDTOs.add(dailyProductionPlanDTO);
		}
		return dailyProductionPlanDTOs;
	}

	@Override
	public DailyProductionPlanDTO getDailyProductionById(long id)
			throws Exception {
		Dailyproduction  dailyproduction = dailyproductionDao.getById(Dailyproduction.class, id);
		if(dailyproduction==null){
			return null;
		}
		DailyProductionPlanDTO dailyProductionPlanDTO =  DailyProductionRequestResponseFactory.setDailyProductionDTO(dailyproduction);
		return dailyProductionPlanDTO;
	}

	@Override
	public DailyProductionPlanDTO deleteDailyProduction(long id) throws Exception {
		Dailyproduction  dailyproduction = dailyproductionDao.getById(Dailyproduction.class, id);
		if(dailyproduction==null){
			return null;
		}
		dailyproduction.setIsactive(false);
		dailyproductionDao.update(dailyproduction);
		DailyProductionPlanDTO dailyProductionPlanDTO =  DailyProductionRequestResponseFactory.setDailyProductionDTO(dailyproduction);
		return dailyProductionPlanDTO;
	}

	@Override
	public void addDailyProduction(TodaysProductionPlanDTO todaysProductionPlanDTO,
			HttpServletRequest request) throws Exception {
		
		for(DailyProductionPlanDTO dailyProductionPlanDTO : todaysProductionPlanDTO.getDailyProductionPlanDTOs()){
			Dailyproduction dailyproduction = DailyProductionRequestResponseFactory.setDailyProduction(dailyProductionPlanDTO, request);
			dailyproduction.setStatus(statusDao.getById(Status.class, Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_QUALITY_CHECK_PENDING, null, null))));
			dailyproductionDao.add(dailyproduction);
	       	//TODO update production plan daily
	       	//need not to change status every time. once it is changed don't execute below method.
	       	//we will be marking production plan complete from store's call.
	      	updateProductionPlan(dailyproduction, dailyProductionPlanDTO.getRepairedQuantity(), request);
		}
	}
	private void updateProductionPlan(Dailyproduction dailyproduction,int repairedQuantity,HttpServletRequest request) throws Exception{
		Productionplanning productionplanning = productionplanningDao.getById(Productionplanning.class, dailyproduction.getProductionplanning().getId());
		productionplanning.setQualityPendingQuantity(productionplanning.getQualityPendingQuantity()+dailyproduction.getAchivedQuantity() + repairedQuantity);
		productionplanning.setRepaired_quantity(productionplanning.getRepaired_quantity() + repairedQuantity);
		productionplanning.setFailQuantity(productionplanning.getFailQuantity() >= repairedQuantity ? productionplanning.getFailQuantity() - repairedQuantity : productionplanning.getFailQuantity());
		productionplanning.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		productionplanning.setUpdatedDate(new Timestamp(new Date().getTime()));
		productionplanningDao.update(productionplanning);
	}

}
