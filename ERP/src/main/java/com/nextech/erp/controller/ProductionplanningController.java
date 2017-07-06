package com.nextech.erp.controller;

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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dto.ProductinPlanPRMAssoData;
import com.nextech.erp.dto.ProductionPlan;
import com.nextech.erp.model.Product;
import com.nextech.erp.model.Productionplanning;
import com.nextech.erp.model.Productorderassociation;
import com.nextech.erp.model.Productrawmaterialassociation;
import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.model.Rawmaterialinventory;
import com.nextech.erp.service.ProductRMAssoService;
import com.nextech.erp.service.ProductService;
import com.nextech.erp.service.ProductinventoryService;
import com.nextech.erp.service.ProductinventoryhistoryService;
import com.nextech.erp.service.ProductionplanningService;
import com.nextech.erp.service.ProductorderassociationService;
import com.nextech.erp.service.RawmaterialService;
import com.nextech.erp.service.RawmaterialinventoryService;
import com.nextech.erp.service.StatusService;
import com.nextech.erp.status.Response;
import com.nextech.erp.status.UserStatus;
import com.nextech.erp.util.DateUtil;
@Controller
@RequestMapping("/productionplanning")
public class ProductionplanningController {


	@Autowired
	ProductionplanningService productionplanningService;

	@Autowired
	ProductService productService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ProductinventoryService productinventoryService;

	@Autowired
	ProductinventoryhistoryService productinventoryhistoryService;

	@Autowired
	ProductorderassociationService productorderassociationService;

	@Autowired
	ProductRMAssoService productRMAssoService;

	@Autowired
	RawmaterialService rawmaterialService;
	
	@Autowired
	StatusService statusService;

	@Autowired
	RawmaterialinventoryService rawmaterialinventoryService;
	
	private static final int PRODUCTION_PLAN_READY_TO_START = 46;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addProductionplanning(@Valid @RequestBody Productionplanning productionplanning,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}

			productionplanningService.addEntity(productionplanning);
			return new UserStatus(1, "Productionplanning added Successfully !");
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
	@RequestMapping(value = "/createProductionForCurrentMonth", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus createProductionPlanningForCurrentMonth(@Valid @RequestBody Productionplanning productionplanning,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			if (productionplanningService.getProductionPlanningforCurrentMonthByProductIdAndDate(
					productionplanning.getProduct().getId(),
					productionplanning.getDate())== null){
				productionplanningService.addEntity(productionplanning);
			}
			else{
				return new UserStatus(1,
						messageSource.getMessage(ERPConstants.PRODUCT_DATE_EXIT, null, null));
			}
			return new UserStatus(1,
					"Productrawmaterialassociation added Successfully !");
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
	public @ResponseBody Productionplanning getProductionplanning(@PathVariable("id") long id) {
		Productionplanning productionplanning = null;
		try {
			productionplanning = productionplanningService.getEntityById(Productionplanning.class,id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productionplanning;
	}
	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateProductionplanning(@RequestBody Productionplanning productionplanning,HttpServletRequest request,HttpServletResponse response) {
		try {
			productionplanning.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			productionplanning.setIsactive(true);
			productionplanningService.updateEntity(productionplanning);
			return new UserStatus(1, "Productionplanning update Successfully !");
		} catch (Exception e) {
			 e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/updateProductionPlan", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateProductionplanningForCurrentMonth(@RequestBody List<ProductionPlan> productionplanningList,HttpServletRequest request,HttpServletResponse response) {
		
		try {
	
			productionplanningService.updateProductionplanningForCurrentMonth(productionplanningList, request, response);
			return new UserStatus(1, "Productionplanning update Successfully !");
		} catch (Exception e) {
			 e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Productionplanning> getProductionplanning() {

		List<Productionplanning> productionplanningList = null;
		try {
			productionplanningList = productionplanningService.getEntityList(Productionplanning.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return productionplanningList;
	}

	@RequestMapping(value = "getProductionplanningByMonth/{month}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Productionplanning> getProductionplanningByMonth(@PathVariable("month") Date month) {

		List<Productionplanning> productionplanningList = null;
		try {
			productionplanningList = productionplanningService.getProductionplanningByMonth(month);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return productionplanningList;
	}

	@RequestMapping(value = "getProductionPlanForCurrentMonth", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<ProductionPlan> getProductionPlanMonthYear() {

		List<ProductionPlan> productionplanningList = null;
		try {
			productionplanningList = productionplanningService.getProductionPlanForCurrentMonth();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return productionplanningList;
	}
	@RequestMapping(value = "updateProductionPlanMonthYear/{MONTH-YEAR}", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody List<Productionplanning> updateProductionPlanMonthYear(@PathVariable("MONTH-YEAR") String month_year) {

		List<Productionplanning> productionplanningList = null;
		try {

			productionplanningList = productionplanningService.updateProductionPlanByMonthYear(month_year);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return productionplanningList;
	}

	@RequestMapping(value = "createProductionPlanMonthYear/{MONTH-YEAR}", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<Productionplanning> createProductionPlanMonthYear(@PathVariable("MONTH-YEAR") String month_year,HttpServletRequest request,HttpServletResponse response) {
		List<Productionplanning> productionplanningList = null;
		List<Product> productList = null;
		try {
			productList = productService.getEntityList(Product.class);
								productionplanningList = productionplanningService.createProductionPlanMonthYear( productList, month_year, request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return productionplanningList;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteProductionplanning(@PathVariable("id") long id) {

		try {
			Productionplanning productionplanning = productionplanningService.getEntityById(Productionplanning.class,id);
			productionplanning.setIsactive(false);
			productionplanningService.updateEntity(productionplanning);
			return new UserStatus(1, "Productionplanning deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}

	}

	@RequestMapping(value = "getProductionPlanByDateAndPId/{date}/{pID}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Productionplanning getProductionPlanDateAndProductId(@PathVariable("date") String date,@PathVariable("pID")long pId) {

		Productionplanning productionplanning = null;
		try {
			productionplanning = productionplanningService.getProductionplanByDateAndProductId(DateUtil.convertToDate(date),pId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return productionplanning;
	}

	@RequestMapping(value = "getProductionPlanReadyListByDate/{date}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getProductionPlanReadyDate(@PathVariable("date") String date) {

		List<Productionplanning> productionplanningFinalList = new ArrayList<Productionplanning>();
		try {
			List<Productionplanning> productionplannings = productionplanningService.getProductionplanByDate(DateUtil.convertToDate(date));
			for (Productionplanning productionplanning : productionplannings) {
				boolean isProductRemaining = false;
				//TODO Can we add this if condition below to Query along with condition target quantity?
				if(productionplanning.getStatus().getId() == Long.parseLong(messageSource.getMessage(ERPConstants.PRODUCTION_PLAN_READY_TO_START, null, null))||
						productionplanning.getStatus().getId()==Long.parseLong(messageSource.getMessage(ERPConstants.PROD_PLAN_COMPLETE, null, null))){
					if(productionplanning.getTargetQuantity() > 0){
						List<Productorderassociation> productorderassociations = productorderassociationService.getIncompleteProductOrderAssoByProdutId(productionplanning.getProduct().getId());
						if(productorderassociations !=null && !productorderassociations.isEmpty()){
							for (Productorderassociation productorderassociation : productorderassociations) {
								if(productorderassociation.getRemainingQuantity() > 0){
									isProductRemaining = true;
									break;
								}
							}
						}
					}
				}/*else{
					return new Response(101,"Please get RM from RM Store Out. So that Today's Production Plan will be generated.",productionplanningFinalList);
				}*/
				if(isProductRemaining)
					productionplanningFinalList.add(productionplanning);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Response(1,"Success",productionplanningFinalList);
	}
	
	
	@RequestMapping(value = "getProductionPlanListByDate/{date}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Productionplanning> getProductionPlanDate1(@PathVariable("date") String date) {

		List<Productionplanning> productionplanningFinalList = new ArrayList<Productionplanning>();
		try {
			List<Productionplanning> productionplannings = productionplanningService.getProductionplanByDate(DateUtil.convertToDate(date));
			for (Productionplanning productionplanning : productionplannings) {
				boolean isProductRemaining = false;
//				if(productionplanning.getStatus().getId()==Long.parseLong(messageSource.getMessage(ERPConstants.PROD_PLAN_NEW, null, null))){
					if(productionplanning.getTargetQuantity() > 0){
						List<Productorderassociation> productorderassociations = productorderassociationService.getIncompleteProductOrderAssoByProdutId(productionplanning.getProduct().getId());
						if(productorderassociations !=null && !productorderassociations.isEmpty()){
							for (Productorderassociation productorderassociation : productorderassociations) {
								if(productorderassociation.getRemainingQuantity() > 0){
									isProductRemaining = true;
									break;
								}
							}
						}
					}
//				}
				if(isProductRemaining)
					productionplanningFinalList.add(productionplanning);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return productionplanningFinalList;
	}
	

	@RequestMapping(value = "getProductionPlanByDate/{date}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Productionplanning> getProductionPlanDate(@PathVariable("date") String date) {

		List<Productionplanning> productionplanningFinalList = new ArrayList<Productionplanning>();
		try {

			List<Productionplanning> productionplanningList = productionplanningService.getProductionplanByDate(DateUtil.convertToDate(date));
			for (Productionplanning productionplanning : productionplanningList) {
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
				if(isProductRemaining)
					productionplanningFinalList.add(productionplanning);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return productionplanningFinalList;
	}


	@RequestMapping(value = "getProductionPlanListForStoreOutByDateAndPId/{date}/{productID}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getProductionPlanListByDate(@PathVariable("date") String date,@PathVariable("productID") long productID) {

		List<Productionplanning> productionplanningList = null;
		List<ProductinPlanPRMAssoData> productinPlanPRMAssoDataList = new ArrayList<ProductinPlanPRMAssoData>();
		try {
			productionplanningList = productionplanningService.getProductionplanByProdutId(DateUtil.convertToDate(date),productID);
			for(Productionplanning productionplanning : productionplanningList){
				List<Productrawmaterialassociation> productrawmaterialassociations = productRMAssoService.getProductRMAssoListByProductId(productionplanning.getProduct().getId());
				if(productrawmaterialassociations !=null && !productrawmaterialassociations.isEmpty()){
					for(Productrawmaterialassociation productrawmaterialassociation : productrawmaterialassociations){
						ProductinPlanPRMAssoData productinPlanPRMAssoData = new ProductinPlanPRMAssoData();
						Rawmaterial rawmaterial = rawmaterialService.getEntityById(Rawmaterial.class, productrawmaterialassociation.getRawmaterial().getId());
						Rawmaterialinventory rawmaterialinventory = rawmaterialinventoryService.getByRMId(productrawmaterialassociation.getRawmaterial().getId());
					    if(rawmaterialinventory==null){
					    	return new Response(0,"Please Add RM In RM Inventory",null);
					    }else{
						productinPlanPRMAssoData.setName(rawmaterial.getPartNumber());
						productinPlanPRMAssoData.setRawmaterial(productrawmaterialassociation.getRawmaterial().getId());
						productinPlanPRMAssoData.setInventoryQuantity(rawmaterialinventory.getQuantityAvailable());
						productinPlanPRMAssoData.setQuantityRequired(productrawmaterialassociation.getQuantity());
						productinPlanPRMAssoDataList.add(productinPlanPRMAssoData);
					}
	
					}
				}else{
					return new Response(0,"Please Add Product Raw Material Association for doing RM Store Out",null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Response(1,"Success",productinPlanPRMAssoDataList);
	}



}

