package com.nextech.erp.controller;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nextech.erp.dto.DailyProductionPlanDTO;
import com.nextech.erp.dto.TodaysProductionPlanDTO;
import com.nextech.erp.factory.DailyProductionRequestResponseFactory;
import com.nextech.erp.service.DailyproductionService;
import com.nextech.erp.service.ProductService;
import com.nextech.erp.service.ProductionplanningService;
import com.nextech.erp.service.StatusService;
import com.nextech.erp.status.Response;
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
	ProductionplanningService productionplanningService;
	
	static Logger logger = Logger.getLogger(ClientController.class);

	@RequestMapping(value = "/dailyproductionSave", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addDailyProduction(@Valid @RequestBody TodaysProductionPlanDTO todaysProductionPlanDTO,HttpServletRequest request,HttpServletResponse response,
			BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
			}
			dailyproductionservice.addDailyProduction(todaysProductionPlanDTO, request);
			return new UserStatus(1, "Dailyproduction added Successfully !");
		} catch (ConstraintViolationException cve) {
			logger.error(cve);
			cve.printStackTrace();
			return new UserStatus(0, cve.getCause().getMessage());
		} catch (PersistenceException pe) {
			logger.error(pe);
			pe.printStackTrace();
			return new UserStatus(0, pe.getCause().getMessage());
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return new UserStatus(0, e.getCause().getMessage());
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getDailyproduction(@PathVariable("id") long id) {
		DailyProductionPlanDTO dailyproduction = null;
		try {
			dailyproduction = dailyproductionservice.getDailyProductionById(id);
			if(dailyproduction== null){
				logger.error("There is no any daily production");
				return  new Response(1,"There is no any daily production");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,dailyproduction);
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
	public @ResponseBody Response getDailyproduction() {

		List<DailyProductionPlanDTO> dailyproductionList = null;
		try {
			dailyproductionList = dailyproductionservice.getDailyProductionList();
			if(dailyproductionList==null){
				logger.error("There is no any daily production list");
				return  new Response(1,"There is no any dailyproduction list");
			}

		} catch (Exception e) {
			logger.error("exception daily production list");
			e.printStackTrace();
		}
		return new Response(1,dailyproductionList);
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteDailyproduction(@PathVariable("id") long id) {

		try {
			DailyProductionPlanDTO dailyProductionPlanDTO =dailyproductionservice.deleteDailyProduction(id);
			if(dailyProductionPlanDTO==null){
				logger.error("There is no any daily production plan");
				return  new Response(1,"There is no any daily production plan");
			}
			return new Response(1, "Dailyproduction deleted Successfully !");
		} catch (Exception e) {
			return new Response(0, e.toString());
		}
	}
	
}