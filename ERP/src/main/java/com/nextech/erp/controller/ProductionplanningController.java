package com.nextech.erp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dto.ProductRMAssociationDTO;
import com.nextech.erp.dto.ProductinPlanPRMAssoData;
import com.nextech.erp.dto.ProductionPlan;
import com.nextech.erp.dto.RMInventoryDTO;
import com.nextech.erp.factory.ProductionPlanningRequestResponseFactory;
import com.nextech.erp.newDTO.ProductDTO;
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

	@Transactional @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody ProductionPlanningDTO getProductionplanning(@PathVariable("id") long id) {
		ProductionPlanningDTO productionplanning = null;
		try {
			productionplanning = productionplanningService.getProductionPlanById(id);
		} catch (Exception e) {
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
			e.printStackTrace();
		}
		return productionplanningList;
	}
	@Transactional @RequestMapping(value = "updateProductionPlanMonthYear/{MONTH-YEAR}", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody List<ProductionPlanningDTO> updateProductionPlanMonthYear(@PathVariable("MONTH-YEAR") String month_year) {
		List<ProductionPlanningDTO> productionplanningList = null;
		try {
			productionplanningList = productionplanningService.updateProductionPlanByMonthYear(month_year);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productionplanningList;
	}

	@Transactional @RequestMapping(value = "createProductionPlanMonthYear/{MONTH-YEAR}", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<ProductionPlanningDTO> createProductionPlanMonthYear(@PathVariable("MONTH-YEAR") String month_year,HttpServletRequest request,HttpServletResponse response) {
		List<ProductionPlanningDTO> productionplanningList = null;
		List<ProductDTO> productList = null;
		try {
			productList = productService.getProductList();
			productionplanningList = productionplanningService.createProductionPlanMonthYear( productList, month_year, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productionplanningList;
	}

	@Transactional @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteProductionplanning(@PathVariable("id") long id) {
		try {
			productionplanningService.deleteProduction(id);
			return new UserStatus(1, "Productionplanning deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}
	}

	@Transactional @RequestMapping(value = "getProductionPlanByDateAndPId/{date}/{pID}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody ProductionPlanningDTO getProductionPlanDateAndProductId(@PathVariable("date") String date,@PathVariable("pID")long pId) {
		ProductionPlanningDTO productionplanning = null;
		try {
			productionplanning = productionplanningService.getProductionplanByDateAndProductId(DateUtil.convertToDate(date),pId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productionplanning;
	}

	@Transactional @RequestMapping(value = "getProductionPlanReadyListByDate/{date}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getProductionPlanReadyDate(@PathVariable("date") String date) {
		List<ProductionPlanningDTO> productionplanningFinalList = new ArrayList<ProductionPlanningDTO>();
		try {
			List<ProductionPlanningDTO> productionplannings = productionplanningService.getProductionplanByDate(DateUtil.convertToDate(date));
			for (ProductionPlanningDTO productionplanning : productionplannings) {
				boolean isProductRemaining = false;
				//TODO Can we add this if condition below to Query along with condition target quantity?
				if(productionplanning.getStatusId().getId() == Long.parseLong(messageSource.getMessage(ERPConstants.PRODUCTION_PLAN_READY_TO_START, null, null))||
						productionplanning.getStatusId().getId()==Long.parseLong(messageSource.getMessage(ERPConstants.PROD_PLAN_COMPLETE, null, null))){
					if(productionplanning.getTargetQuantity() > 0){
						List<ProductOrderAssociationDTO> productorderassociations = productorderassociationService.getIncompleteProductOrderAssoByProdutId(productionplanning.getProductId().getId());
						if(productorderassociations !=null && !productorderassociations.isEmpty()){
							for (ProductOrderAssociationDTO productorderassociation : productorderassociations) {
								if(productorderassociation.getRemainingQuantity() > 0){
									isProductRemaining = true;
									break;
								}
							}
						}
						return new Response(101,"Please get RM from RM Store Out. So that Today's Production Plan will be generated.",productionplanningFinalList);
					}
				}else{
					return new Response(101,"There is no production plan for today.",productionplanningFinalList);
				}
				if(isProductRemaining)
					productionplanningFinalList.add(productionplanning);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,"Success",productionplanningFinalList);
	}
	
	
	@Transactional @RequestMapping(value = "getProductionPlanListByDate/{date}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<ProductionPlanningDTO> getProductionPlanDate1(@PathVariable("date") String date) {

		List<ProductionPlanningDTO> productionplanningFinalList = new ArrayList<ProductionPlanningDTO>();
		try {
			List<ProductionPlanningDTO> productionplannings = productionplanningService.getProductionplanByDate(DateUtil.convertToDate(date));
			for (ProductionPlanningDTO productionplanning : productionplannings) {
				boolean isProductRemaining = false;
				if(productionplanning.getTargetQuantity() > 0){
					List<ProductOrderAssociationDTO> productOrderAssociationDTOs = productorderassociationService.getIncompleteProductOrderAssoByProdutId(productionplanning.getProductId().getId());
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productionplanningFinalList;
	}
	

	@Transactional @RequestMapping(value = "getProductionPlanByDate/{date}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<ProductionPlanningDTO> getProductionPlanDate(@PathVariable("date") String date) {

		List<ProductionPlanningDTO> productionplanningFinalList = new ArrayList<ProductionPlanningDTO>();
		try {

			List<ProductionPlanningDTO> productionplanningList = productionplanningService.getProductionplanByDate(DateUtil.convertToDate(date));
			for (ProductionPlanningDTO productionplanning : productionplanningList) {
				boolean isProductRemaining = false;
				List<ProductOrderAssociationDTO> productorderassociations = productorderassociationService.getIncompleteProductOrderAssoByProdutId(productionplanning.getProductId().getId());
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

		} catch (Exception e) {
			e.printStackTrace();
		}

		return productionplanningFinalList;
	}


	@Transactional @RequestMapping(value = "getProductionPlanListForStoreOutByDateAndPId/{date}/{productID}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getProductionPlanListByDate(@PathVariable("date") String date,@PathVariable("productID") long productID) {

		List<ProductionPlanningDTO> productionplanningList = null;
		List<ProductinPlanPRMAssoData> productinPlanPRMAssoDataList = new ArrayList<ProductinPlanPRMAssoData>();
		try {
			productionplanningList = productionplanningService.getProductionplanByProdutId(DateUtil.convertToDate(date),productID);
			for(ProductionPlanningDTO productionplanning : productionplanningList){
				List<ProductRMAssociationDTO> productrawmaterialassociations = productRMAssoService.getProductRMAssoListByProductId(productionplanning.getProductId().getId());
				if(productrawmaterialassociations !=null && !productrawmaterialassociations.isEmpty()){
					for(ProductRMAssociationDTO productrawmaterialassociation : productrawmaterialassociations){
						ProductinPlanPRMAssoData productinPlanPRMAssoData = new ProductinPlanPRMAssoData();
						RawMaterialDTO rawmaterial = rawmaterialService.getRMDTO( productrawmaterialassociation.getRawmaterialId().getId());
						RMInventoryDTO rawmaterialinventory = rawmaterialinventoryService.getRMInventoryById(productrawmaterialassociation.getRawmaterialId().getId());
					    if(rawmaterialinventory==null)
					    	return new Response(0,"Please Add RM In RM Inventory",null);
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
					return new Response(0,"Please Add Product Raw Material Association for doing RM Store Out",null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,"Success",productinPlanPRMAssoDataList);
	}
}