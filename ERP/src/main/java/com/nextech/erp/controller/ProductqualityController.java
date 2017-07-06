package com.nextech.erp.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dto.ProductQualityDTO;
import com.nextech.erp.dto.ProductQualityPart;
import com.nextech.erp.model.Dailyproduction;
import com.nextech.erp.model.Product;
import com.nextech.erp.model.Productinventory;
import com.nextech.erp.model.Productinventoryhistory;
import com.nextech.erp.model.Productionplanning;
import com.nextech.erp.model.Productorderassociation;
import com.nextech.erp.model.Productquality;
import com.nextech.erp.model.Status;
import com.nextech.erp.service.DailyproductionService;
import com.nextech.erp.service.ProductService;
import com.nextech.erp.service.ProductinventoryService;
import com.nextech.erp.service.ProductinventoryhistoryService;
import com.nextech.erp.service.ProductionplanningService;
import com.nextech.erp.service.ProductorderService;
import com.nextech.erp.service.ProductorderassociationService;
import com.nextech.erp.service.ProductqualityService;
import com.nextech.erp.service.StatusService;
import com.nextech.erp.status.UserStatus;
import com.nextech.erp.util.DateUtil;


@Controller
@RequestMapping("/productquality")
public class ProductqualityController {
	@Autowired
	ProductqualityService productqualityService;

	@Autowired
	private MessageSource messageSource;

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

	@RequestMapping(value = "getQualityPendingListByDate/{date}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Productionplanning> getProductionPlanDate1(@PathVariable("date") String date,HttpServletRequest request,HttpServletResponse response) {
		List<Productionplanning> productionplanningsList = new ArrayList<Productionplanning>();
		try {
			List<Productionplanning> productionplannings = productionplanningService.getProductionplanByDate(DateUtil.convertToDate(date));
			for (Productionplanning productionplanning : productionplannings) {
				boolean isProductRemaining = false;
				List<Productorderassociation> productorderassociations = productorderassociationService.getIncompleteProductOrderAssoByProdutId(productionplanning.getProduct().getId());
				if(productorderassociations !=null && !productorderassociations.isEmpty()){
					for (Productorderassociation productorderassociation : productorderassociations) {
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
		}
		return productionplanningsList;
	}


	@RequestMapping(value = "/productQualityCheck", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addProductquality(@Valid @RequestBody ProductQualityDTO productQualityDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
			}
			long userId = Long.parseLong(request.getAttribute("current_user").toString());
			for(ProductQualityPart productQualityPart : productQualityDTO.getProductQualityParts()){
				Productquality productquality = setProductquality(productQualityPart,userId);
				productqualityService.addEntity(productquality);
				updateProductionPlanningForQualityCheck(productquality,userId);
				updateDailyProduction(productQualityPart,userId);
			}

			return new UserStatus(1, "Productquality added Successfully !");
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

	@RequestMapping(value = "/productQualityCheckStore", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addProductqualityStore(@Valid @RequestBody ProductQualityDTO productQualityDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
			}
			long userId = Long.parseLong(request.getAttribute("current_user").toString());
			for(ProductQualityPart productQualityPart : productQualityDTO.getProductQualityParts()){
				Productquality productquality = setProductquality(productQualityPart,userId);
				Product product =  productService.getEntityById(Product.class, productquality.getProduct().getId());
				addProductInventoryHistory(productquality.getGoodQuantity(), product, request, response);
				updateProductInventory(productquality, product, request, response);
				updateProductionPlanningForStore(productquality,userId);
			}
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
	public @ResponseBody Productquality getProductquality(@PathVariable("id") long id) {
		Productquality productquality = null;
		try {

			productquality = productqualityService.getEntityById(Productquality.class,id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productquality;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateProductquality(@RequestBody Productquality productquality,HttpServletRequest request,HttpServletResponse response) {
		try {
			productquality.setIsactive(true);
			productquality.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			productqualityService.updateEntity(productquality);
			return new UserStatus(1, "Productquality update Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Productquality> getProductquality() {

		List<Productquality> productqualityList = null;
		try {
			productqualityList = productqualityService.getEntityList(Productquality.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return productqualityList;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteProductquality(@PathVariable("id") long id) {

		try {
			Productquality productquality = productqualityService.getEntityById(Productquality.class,id);
			productquality.setIsactive(false);
			productqualityService.updateEntity(productquality);
			return new UserStatus(1, "Productquality deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}

	}

	private Productinventory updateProductInventory(Productquality productquality,Product product,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Productinventory productinventory =  productinventoryService.getProductinventoryByProductId(productquality.getProduct().getId());
		if(productinventory != null){
			productinventory.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			productinventory.setQuantityavailable(productinventory.getQuantityavailable() + productquality.getGoodQuantity());
			productinventoryService.updateEntity(productinventory);
		}
		return productinventory;
	}

	private void addProductInventoryHistory(long goodQuantity,Product product,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Productinventory productinventory =  productinventoryService.getProductinventoryByProductId(product.getId());
		if(productinventory != null){
			Productinventoryhistory productinventoryhistory = new Productinventoryhistory();
			productinventoryhistory.setProductinventory(productinventory);
			productinventoryhistory.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			productinventoryhistory.setIsactive(true);
			productinventoryhistory.setBeforequantity((int)productinventory.getQuantityavailable());
			productinventoryhistory.setAfterquantity((int)(goodQuantity+productinventory.getQuantityavailable()));
			productinventoryhistory.setStatus(statusService.getEntityById(Status.class, Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_RAW_MATERIAL_INVENTORY_ADD, null, null))));
			productinventoryhistoryService.addEntity(productinventoryhistory);
		}
	}

	private void updateProductionPlanningForQualityCheck(Productquality productquality,long userId) throws Exception {
		Productionplanning productionplanning = productionplanningService.getEntityById(Productionplanning.class, productquality.getProductionplanning().getId());
		productionplanning.setQualityPendingQuantity(productionplanning.getQualityPendingQuantity()-(productquality.getGoodQuantity() + productquality.getRejectedQuantity()));
		productionplanning.setQualityCheckedQuantity(productionplanning.getQualityCheckedQuantity()+productquality.getGoodQuantity());
		productionplanning.setFailQuantity(productionplanning.getFailQuantity() + productquality.getRejectedQuantity());
		productionplanning.setUpdatedBy(userId);
		productionplanningService.updateEntity(productionplanning);
	}
	

	private void updateProductionPlanningForStore(Productquality productquality,long userId) throws Exception {
		Productionplanning productionplanning = productionplanningService.getEntityById(Productionplanning.class, productquality.getProductionplanning().getId());
		productionplanning.setQualityCheckedQuantity(productionplanning.getQualityCheckedQuantity()-productquality.getGoodQuantity());
		productionplanning.setCompletedQuantity(productionplanning.getCompletedQuantity()+productquality.getGoodQuantity());
		productionplanning.setUpdatedBy(userId);
		if(productionplanning.getTargetQuantity() >= productionplanning.getCompletedQuantity()){
			productionplanning.setStatus(statusService.getEntityById(Status.class,  Long.parseLong(messageSource.getMessage(ERPConstants.PROD_PLAN_COMPLETE, null, null))));
		}
		productionplanningService.updateEntity(productionplanning);
	}

	private Productquality setProductquality(ProductQualityPart productQualityPart,long userId) throws Exception {
		Productquality productquality = new Productquality();
		productquality.setProduct(productService.getEntityById(Product.class, productQualityPart.getProductId()));
		productquality.setCheckQuantity(productQualityPart.getProductQuantity());
		productquality.setProductionplanning(productionplanningService.getEntityById(Productionplanning.class, productQualityPart.getProductionPlanId()));
		productquality.setGoodQuantity(productQualityPart.getPassQuantity());
		productquality.setRejectedQuantity(productQualityPart.getFailQuantity());
		productquality.setRemark(productQualityPart.getRemark());
		productquality.setCreatedBy(userId);
		productquality.setIsactive(true);
		return productquality;
	}

	private void updateDailyProduction(ProductQualityPart productQualityPart,long userId) throws NumberFormatException, NoSuchMessageException, Exception {
		List<Dailyproduction> dailyproductions = dailyproductionService.getDailyProdPendingForQualityCheckByPlanningId(productQualityPart.getProductionPlanId());
		for (Dailyproduction dailyproduction : dailyproductions) {
			dailyproduction.setStatus(statusService.getEntityById(Status.class, Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_QUALITY_CHECK_COMPLETE, null, null))));
			dailyproduction.setUpdatedBy(userId);
			dailyproduction.setUpdatedDate(new Timestamp(new Date().getTime()));
		}
	}
}