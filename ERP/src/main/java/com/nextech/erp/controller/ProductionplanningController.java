package com.nextech.erp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nextech.erp.dto.ProductRMAssociationDTO;
import com.nextech.erp.dto.ProductinPlanPRMAssoData;
import com.nextech.erp.dto.ProductionPlan;
import com.nextech.erp.dto.RMInventoryDTO;
import com.nextech.erp.factory.ProductionPlanningRequestResponseFactory;
import com.nextech.erp.model.Product;
import com.nextech.erp.model.Productionplanning;
import com.nextech.erp.newDTO.ProductOrderAssociationDTO;
import com.nextech.erp.newDTO.ProductionPlanningDTO;
import com.nextech.erp.newDTO.RawMaterialDTO;
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
@Transactional @RequestMapping("/productionplanning")
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
	
	static Logger logger = Logger.getLogger(ProductionplanningController.class);

	@Transactional @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody ProductionPlanningDTO getProductionplanning(@PathVariable("id") long id) {
		ProductionPlanningDTO productionplanning = null;
		try {
			productionplanning = productionplanningService.getProductionPlanById(id);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return productionplanning;
	}
	
	@Transactional @RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateProductionplanning(@RequestBody ProductionPlanningDTO productionPlanningDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			productionplanningService.updateEntity(ProductionPlanningRequestResponseFactory.setProductionPlanning(productionPlanningDTO, request));
			return new UserStatus(1, "Productionplanning update Successfully !");
		} catch (Exception e) {
			logger.error(e);
			 e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@Transactional @RequestMapping(value = "/updateProductionPlan", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateProductionplanningForCurrentMonth(@RequestBody List<ProductionPlan> productionplanningList,HttpServletRequest request,HttpServletResponse response) {
		try {
			productionplanningService.updateProductionplanningForCurrentMonth(productionplanningList, request, response);
			return new UserStatus(1, "Productionplanning update Successfully !");
		} catch (Exception e) {
			logger.error(e);
			 e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@Transactional @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<ProductionPlanningDTO> getProductionplanning() {
		List<ProductionPlanningDTO> productionplanningList = null;
		try {
			productionplanningList = productionplanningService.getProductionPlanList();
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return productionplanningList;
	}

	@Transactional @RequestMapping(value = "getProductionplanningByMonth/{month}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<ProductionPlanningDTO> getProductionplanningByMonth(@PathVariable("month") Date month) {
		List<ProductionPlanningDTO> productionplanningList = null;
		try {
			productionplanningList = productionplanningService.getProductionplanningByMonth(month);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return productionplanningList;
	}

	@Transactional @RequestMapping(value = "getProductionPlanForCurrentMonth", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<ProductionPlan> getProductionPlanMonthYear() {

		List<ProductionPlan> productionplanningList = null;
		try {
			productionplanningList = productionplanningService.getProductionPlanForCurrentMonth();
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return productionplanningList;
	}
	@Transactional @RequestMapping(value = "updateProductionPlanMonthYear/{MONTH-YEAR}", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody Response updateProductionPlanMonthYear(@PathVariable("MONTH-YEAR") String month_year) {
		List<ProductionPlanningDTO> productionplanningList = null;
		try {
			productionplanningList = productionplanningService.updateProductionPlanByMonthYear(month_year);
			if(productionplanningList==null){
				logger.error("There is no any production planning list");
				return  new Response(1,"There is no any production planning list");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return new Response(1,productionplanningList);
	}
	@RequestMapping(value = "createProductionPlanMonthYear/{MONTH-YEAR}", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Response createProductionPlanMonthYear(@PathVariable("MONTH-YEAR") String month_year,HttpServletRequest request,HttpServletResponse response) {
		List<Productionplanning> productionplanningList = null;
		List<Product> productList = null;
		try {
			productList = productService.getEntityList(Product.class);
		   productionplanningList = productionplanningService.createProductionPlanMonthYear( productList, month_year, request, response);
			if(productionplanningList==null){
			logger.error("There is no any production planning list");	
			return  new Response(1,"There is no any production planning list");
		}
		
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

		return new Response(1,productionplanningList);
	}

	@Transactional @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteProductionplanning(@PathVariable("id") long id) {
		try {
		ProductionPlanningDTO productionPlanningDTO = 	productionplanningService.deleteProduction(id);
		if (productionPlanningDTO==null) {
			logger.error("There is no any production planning ");	
			return  new Response(1,"There is no any production planning");
			
		}
			return new Response(1, "Productionplanning deleted Successfully !");
		} catch (Exception e) {
			logger.error(e);
			return new Response(0, e.toString());
		}
	}

	@Transactional @RequestMapping(value = "getProductionPlanByDateAndPId/{date}/{pID}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody ProductionPlanningDTO getProductionPlanDateAndProductId(@PathVariable("date") String date,@PathVariable("pID")long pId) {
		ProductionPlanningDTO productionplanning = null;
		try {
			productionplanning = productionplanningService.getProductionplanByDateAndProductId(DateUtil.convertToDate(date),pId);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return productionplanning;
	}

	@Transactional @RequestMapping(value = "getProductionPlanReadyListByDate/{date}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getProductionPlanReadyDate(@PathVariable("date") String date) {
		List<ProductionPlanningDTO> productionplanningFinalList = new ArrayList<ProductionPlanningDTO>();
		try {
			List<ProductionPlanningDTO> productionplannings = productionplanningService.getProductionplanByDate(DateUtil.convertToDate(date));
			if(productionplannings!=null){
			for (ProductionPlanningDTO productionplanning : productionplannings) {
				boolean isProductionRemaining = false;
				//TODO Can we add this if condition below to Query along with condition target quantity
					if(productionplanning.getTargetQuantity() > 0){
						List<ProductOrderAssociationDTO> productorderassociations = 
								productorderassociationService.getIncompleteProductOrderAssoByProductId(productionplanning.getProductId().getId());
						if(productorderassociations !=null && !productorderassociations.isEmpty()){
							for (ProductOrderAssociationDTO productorderassociation : productorderassociations) {
								if(productorderassociation.getRemainingQuantity() > 0){
									isProductionRemaining = true;
									break;
								}
							}
						}
						//return new Response(101,"Please get RM from RM Store Out. So that Today's Production Plan will be generated.",productionplanningFinalList);
					}
				if(isProductionRemaining)
					productionplanningFinalList.add(productionplanning);
			}
			}else{
				return  new Response(1,"There is no any production planning lisy");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return new Response(1,"Success",productionplanningFinalList);
	}
	
	@Transactional @RequestMapping(value = "getProductionPlanListByDate/{date}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getProductionPlanDate1(@PathVariable("date") String date) {

		List<ProductionPlanningDTO> productionplanningFinalList = new ArrayList<ProductionPlanningDTO>();
		try {
			List<ProductionPlanningDTO> productionplannings = productionplanningService.getProductionplanByDate(DateUtil.convertToDate(date));
			if(productionplannings!=null){
			for (ProductionPlanningDTO productionplanning : productionplannings) {
				boolean isProductRemaining = false;
				if(productionplanning.getTargetQuantity() > 0){
					List<ProductOrderAssociationDTO> productOrderAssociationDTOs = productorderassociationService.getIncompleteProductOrderAssoByProductId(productionplanning.getProductId().getId());
					if(productOrderAssociationDTOs !=null && !productOrderAssociationDTOs.isEmpty()){
						for (ProductOrderAssociationDTO productOrderAssociationDTO : productOrderAssociationDTOs) {
							if(productOrderAssociationDTO.getRemainingQuantity() > 0){
								isProductRemaining = true;
								break;
							}
						}
					}
				}
				if(isProductRemaining)
					productionplanningFinalList.add(productionplanning);
			}
			}else{
				logger.error("There is no any production planning list ! you can update production plan for current date");
				return  new Response(1,"There is no any production planning list ! you can update production plan for current date");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return new Response(1,productionplanningFinalList);
	}
	
	@Transactional @RequestMapping(value = "getProductionPlanByDate/{date}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getProductionPlanDate(@PathVariable("date") String date) {

		List<ProductionPlanningDTO> productionplanningFinalList = new ArrayList<ProductionPlanningDTO>();
		try {

			List<ProductionPlanningDTO> productionplanningList = productionplanningService.getProductionplanByDate(DateUtil.convertToDate(date));
			if(productionplanningList!=null){
			for (ProductionPlanningDTO productionplanning : productionplanningList) {
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
				if(isProductRemaining)
					productionplanningFinalList.add(productionplanning);
			}
			}else{
				return new Response(1,"There is no any production planning list");
			}

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

		return new Response(1,productionplanningFinalList);
	}

	@Transactional @RequestMapping(value = "getProductionPlanListForStoreOutByDateAndPId/{date}/{productID}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getProductionPlanListByDate(@PathVariable("date") String date,@PathVariable("productID") long productID) {

		List<ProductionPlanningDTO> productionplanningList = null;
		List<ProductinPlanPRMAssoData> productinPlanPRMAssoDataList = new ArrayList<ProductinPlanPRMAssoData>();
		try {
			productionplanningList = productionplanningService.getProductionplanByProdutId(DateUtil.convertToDate(date),productID);
			if(productionplanningList!=null){
			for(ProductionPlanningDTO productionplanning : productionplanningList){
				List<ProductRMAssociationDTO> productrawmaterialassociations = productRMAssoService.getProductRMAssoListByProductId(productionplanning.getProductId().getId());
				if(productrawmaterialassociations !=null && !productrawmaterialassociations.isEmpty()){
					for(ProductRMAssociationDTO productrawmaterialassociation : productrawmaterialassociations){
						ProductinPlanPRMAssoData productinPlanPRMAssoData = new ProductinPlanPRMAssoData();
						RawMaterialDTO rawmaterial = rawmaterialService.getRMDTO( productrawmaterialassociation.getRawmaterialId().getId());
						RMInventoryDTO rawmaterialinventory = rawmaterialinventoryService.getByRMId(productrawmaterialassociation.getRawmaterialId().getId());
					    if(rawmaterialinventory==null)
					    	return new Response(0,"Please Add Raw Material " + rawmaterial.getPartNumber() + " to Raw Material Inventory",null);
					    else
					    {
							productinPlanPRMAssoData.setName(rawmaterial.getPartNumber());
							productinPlanPRMAssoData.setRawmaterial(productrawmaterialassociation.getRawmaterialId().getId());
							productinPlanPRMAssoData.setInventoryQuantity(rawmaterialinventory.getQuantityAvailable());
							productinPlanPRMAssoData.setQuantityRequired(productrawmaterialassociation.getQuantity());
							productinPlanPRMAssoDataList.add(productinPlanPRMAssoData);
						}
					}
				}else{
					return new Response(0, "Please Add Product Raw Material Association for Product " + productionplanning.getProductId().getPartNumber(), null);
				}
			}
			}else{
				return  new Response(1,"There is no any productionplanning list");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,"Success",productinPlanPRMAssoDataList);
	}
}
