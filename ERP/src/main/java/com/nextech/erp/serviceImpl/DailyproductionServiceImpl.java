package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dao.DailyproductionDao;
import com.nextech.erp.dao.StatusDao;
import com.nextech.erp.dto.DailyProductionPlanDTO;
import com.nextech.erp.factory.DailyProductionRequestResponseFactory;
import com.nextech.erp.model.Dailyproduction;
import com.nextech.erp.model.Status;
import com.nextech.erp.service.DailyproductionService;
@Service
public class DailyproductionServiceImpl extends CRUDServiceImpl<Dailyproduction> implements DailyproductionService {

	@Autowired
	DailyproductionDao dailyproductionDao; 
	
	@Autowired
	StatusDao statusDao;
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	public List<Dailyproduction> getDailyProdPendingForQualityCheckByPlanningId(long planningId) {
		return dailyproductionDao.getDailyProdPendingForQualityCheckByPlanningId(planningId);
	}

	@Override
	public List<DailyProductionPlanDTO> getDailyProductionList()
			throws Exception {
		// TODO Auto-generated method stub
		List<DailyProductionPlanDTO> dailyProductionPlanDTOs = new ArrayList<DailyProductionPlanDTO>();
		List<Dailyproduction> dailyproductions =  dailyproductionDao.getList(Dailyproduction.class);
		for (Dailyproduction dailyproduction : dailyproductions) {
		DailyProductionPlanDTO dailyProductionPlanDTO =  DailyProductionRequestResponseFactory.setDailyProductionDTO(dailyproduction);
		dailyProductionPlanDTOs.add(dailyProductionPlanDTO);
		}
		return dailyProductionPlanDTOs;
	}

	@Override
	public DailyProductionPlanDTO getDailyProductionById(long id)
			throws Exception {
		// TODO Auto-generated method stub
		Dailyproduction  dailyproduction = dailyproductionDao.getById(Dailyproduction.class, id);
		DailyProductionPlanDTO dailyProductionPlanDTO =  DailyProductionRequestResponseFactory.setDailyProductionDTO(dailyproduction);
		return dailyProductionPlanDTO;
	}

	@Override
	public void deleteDailyProduction(long id) throws Exception {
		// TODO Auto-generated method stub
		Dailyproduction  dailyproduction = dailyproductionDao.getById(Dailyproduction.class, id);
		dailyproduction.setIsactive(false);
		dailyproductionDao.update(dailyproduction);
	}

	@Override
	public DailyProductionPlanDTO saveDailyProduction(DailyProductionPlanDTO dailyProductionPlanDTO,
			HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		Dailyproduction dailyproduction = DailyProductionRequestResponseFactory.setDailyProduction(dailyProductionPlanDTO, request);
		dailyproduction.setStatus(statusDao.getById(Status.class, Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_QUALITY_CHECK_PENDING, null, null))));
		long id= dailyproductionDao.add(dailyproduction);
		dailyProductionPlanDTO.setId(id);
		return dailyProductionPlanDTO;
	}

}
