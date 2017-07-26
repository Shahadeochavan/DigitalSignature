package com.nextech.erp.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dto.DailyProductionPlanDTO;
import com.nextech.erp.dto.TodaysProductionPlanDTO;
import com.nextech.erp.factory.DailyProductionRequestResponseFactory;
import com.nextech.erp.model.Dailyproduction;
import com.nextech.erp.model.Productionplanning;
import com.nextech.erp.model.Status;
import com.nextech.erp.service.DailyproductionService;
import com.nextech.erp.service.ProductService;
import com.nextech.erp.service.ProductionplanningService;
import com.nextech.erp.service.StatusService;
import com.nextech.erp.status.UserStatus;

@RestController
@Transactional @RequestMapping("/dailyproduction")
public class DailyproductionController {
	
	@Autowired
	DailyproductionService dailyproductionservice;
	
	@Autowired 
	ProductService productService;
	
	@Autowired
	StatusService statusService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	ProductionplanningService productionplanningService;

	@RequestMapping(value = "/dailyproductionSave", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addDailyproduction(@Valid @RequestBody TodaysProductionPlanDTO todaysProductionPlanDTO,HttpServletRequest request,HttpServletResponse response,
			BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
			}
			for(DailyProductionPlanDTO dailyProductionPlanDTO : todaysProductionPlanDTO.getDailyProductionPlanDTOs()){
				Dailyproduction dailyproduction = DailyProductionRequestResponseFactory.setDailyProduction(dailyProductionPlanDTO, request);
				dailyproduction.setStatus(statusService.getEntityById(Status.class, Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_QUALITY_CHECK_PENDING, null, null))));
		       	dailyproductionservice.addEntity(dailyproduction);
		       	//TODO update production plan daily
		       	//need not to change status every time. once it is changed don't execute below method.
		       	//we will be marking production plan complete from store's call.
		      	updateProductionPlan(dailyproduction, dailyProductionPlanDTO.getRepairedQuantity(), request, response);
			}
			return new UserStatus(1, "Dailyproduction added Successfully !");
		} catch (ConstraintViolationException cve) {
			System.out.println("Inside ConstraintViolationException");
			cve.printStackTrace();
			return new UserStatus(0, cve.getCause().getMessage());
		} catch (PersistenceException pe) {
			System.out.println("Inside PersistenceException");
			pe.printStackTrace();
			return new UserStatus(0, pe.getCause().getMessage());
		} catch (Exception e) {
			System.out.println("Inside Exception");
			e.printStackTrace();
			return new UserStatus(0, e.getCause().getMessage());
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody DailyProductionPlanDTO getDailyproduction(@PathVariable("id") long id) {
		DailyProductionPlanDTO Dailyproduction = null;
		try {
			Dailyproduction = dailyproductionservice.getDailyProductionById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Dailyproduction;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateDailyproduction(@RequestBody DailyProductionPlanDTO dailyProductionPlanDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			dailyproductionservice.updateEntity(DailyProductionRequestResponseFactory.setDailyProductionUpdate(dailyProductionPlanDTO, request));
			return new UserStatus(1, "Dailyproduction update Successfully !");
		} catch (Exception e) {
			 e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<DailyProductionPlanDTO> getDailyproduction() {

		List<DailyProductionPlanDTO> DailyproductionList = null;
		try {
			DailyproductionList = dailyproductionservice.getDailyProductionList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return DailyproductionList;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteDailyproduction(@PathVariable("id") long id) {

		try {
			dailyproductionservice.deleteDailyProduction(id);
			return new UserStatus(1, "Dailyproduction deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}
	}
	
	private void updateProductionPlan(Dailyproduction dailyproduction,int repairedQuantity,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Productionplanning productionplanning = productionplanningService.getEntityById(Productionplanning.class, dailyproduction.getProductionplanning().getId());
		productionplanning.setQualityPendingQuantity(productionplanning.getQualityPendingQuantity()+dailyproduction.getAchivedQuantity() + repairedQuantity);
		productionplanning.setRepaired_quantity(productionplanning.getRepaired_quantity() + repairedQuantity);
		productionplanning.setFailQuantity(productionplanning.getFailQuantity() >= repairedQuantity ? productionplanning.getFailQuantity() - repairedQuantity : productionplanning.getFailQuantity());
		productionplanning.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		productionplanning.setUpdatedDate(new Timestamp(new Date().getTime()));
		productionplanningService.updateEntity(productionplanning);
	}
}