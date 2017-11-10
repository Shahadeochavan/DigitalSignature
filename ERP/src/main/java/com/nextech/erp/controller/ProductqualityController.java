package com.nextech.erp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nextech.erp.dto.ProductQualityDTO;
import com.nextech.erp.factory.ProductQualityRequestResponseFactory;
import com.nextech.erp.newDTO.ProductOrderAssociationDTO;
import com.nextech.erp.newDTO.ProductionPlanningDTO;
import com.nextech.erp.service.DailyproductionService;
import com.nextech.erp.service.ProductService;
import com.nextech.erp.service.ProductinventoryService;
import com.nextech.erp.service.ProductinventoryhistoryService;
import com.nextech.erp.service.ProductionplanningService;
import com.nextech.erp.service.ProductorderService;
import com.nextech.erp.service.ProductorderassociationService;
import com.nextech.erp.service.ProductqualityService;
import com.nextech.erp.service.StatusService;
import com.nextech.erp.status.Response;
import com.nextech.erp.status.UserStatus;
import com.nextech.erp.util.DateUtil;


@Controller
@Transactional
@RequestMapping("/productquality")
public class ProductqualityController {
	@Autowired
	ProductqualityService productqualityService;

	@Autowired
	ProductinventoryService productinventoryService;

	@Autowired
	ProductinventoryhistoryService productinventoryhistoryService;

	@Autowired
	ProductService productService;
	@Autowired
	ProductionplanningService productionplanningService;

	@Autowired
	StatusService statusService;

	@Autowired
	ProductorderassociationService productorderassociationService;

	@Autowired
	ProductorderService productorderService;

	@Autowired
	DailyproductionService dailyproductionService;
	
	static Logger logger = Logger.getLogger(ProductqualityController.class);

	@RequestMapping(value = "getQualityPendingListByDate/{date}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getProductionPlanDate1(@PathVariable("date") String date,HttpServletRequest request,HttpServletResponse response) {
		List<ProductionPlanningDTO> productionplanningsList = new ArrayList<ProductionPlanningDTO>();
		try {
			List<ProductionPlanningDTO> productionplannings = productionplanningService.getProductionplanByDate(DateUtil.convertToDate(date));
			for (ProductionPlanningDTO productionplanning : productionplannings) {
				boolean isProductRemaining = false;
				List<ProductOrderAssociationDTO> productorderassociations = productorderassociationService.getIncompleteProductOrderAssoByProductId(productionplanning.getProductId().getId());
				if(productorderassociations !=null && !productorderassociations.isEmpty()){
					for (ProductOrderAssociationDTO productorderassociation : productorderassociations) {
						if(productorderassociation.getRemainingQuantity() > 0){
							isProductRemaining = true;
							break;
						}
					}
				}
				if(isProductRemaining && productionplanning.getQualityPendingQuantity() > 0)
					productionplanningsList.add(productionplanning);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			return new Response(1, e.getMessage());
		}
		String message = productionplanningsList.size() == 0 ? "There are no Products for Quality Check." : "Success";
		return new Response(0, message, productionplanningsList);
	}


	@RequestMapping(value = "/productQualityCheck", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addProductquality(@Valid @RequestBody ProductQualityDTO productQualityDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
			}
			//TODO product quality check save call
			long userId = Long.parseLong(request.getAttribute("current_user").toString());
			productqualityService.addProductQuality(productQualityDTO, userId);
			
			return new UserStatus(1, "Productquality added Successfully !");
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

	@RequestMapping(value = "/productQualityCheckStore", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addProductqualityStore(@Valid @RequestBody ProductQualityDTO productQualityDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
			}
			//TODO Product quality check store
			long userId = Long.parseLong(request.getAttribute("current_user").toString());
			productqualityService.qualityCheckStore(productQualityDTO, userId);
			
			return new UserStatus(1, "Productquality Store added Successfully !");
		} catch (ConstraintViolationException cve) {
			cve.printStackTrace();
			return new UserStatus(0, cve.getCause().getMessage());
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			return new UserStatus(0, pe.getCause().getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.getCause().getMessage());
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getProductquality(@PathVariable("id") long id) {
		ProductQualityDTO productquality = null;
		try {

			productquality = productqualityService.getProductQualityById(id);
			if(productquality==null){
				logger.error("There is no any product quality");
				return new Response(1,"There is no any product quality");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,productquality);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateProductquality(@RequestBody ProductQualityDTO productQualityDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			productqualityService.updateEntity(ProductQualityRequestResponseFactory.setproductQuality(productQualityDTO, request));
			return new UserStatus(1, "Productquality update Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getProductquality() {
		List<ProductQualityDTO> productqualityList = null;
		try {
			productqualityList = productqualityService.getProductQualityList();
			if(productqualityList==null){
				logger.error("There is no any product quality list");
				return  new Response(1,"There is no any product quality list");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,productqualityList);
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteProductquality(@PathVariable("id") long id) {
		try {
			ProductQualityDTO productQualityDTO =	productqualityService.deleteproductQuality(id);
			if(productQualityDTO==null){
				logger.error("There is no any product quality list");
				return new Response(1,"There is no any product quality");
			}
			return new Response(1, "Productquality deleted Successfully !");
		} catch (Exception e) {
			return new Response(0, e.toString());
		}
	}
}
