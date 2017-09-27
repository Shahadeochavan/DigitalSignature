package com.nextech.erp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dto.ProductRMAssociationDTO;
import com.nextech.erp.dto.ProductRMAssociationModelParts;
import com.nextech.erp.dto.RMVendorData;
import com.nextech.erp.factory.ProductRMAssoRequestResponseFactory;
import com.nextech.erp.newDTO.ProductDTO;
import com.nextech.erp.newDTO.RMVendorAssociationDTO;
import com.nextech.erp.newDTO.RawMaterialDTO;
import com.nextech.erp.newDTO.VendorDTO;
import com.nextech.erp.service.ProductRMAssoService;
import com.nextech.erp.service.ProductService;
import com.nextech.erp.service.ProductionplanningService;
import com.nextech.erp.service.RMVAssoService;
import com.nextech.erp.service.RawmaterialService;
import com.nextech.erp.service.VendorService;
import com.nextech.erp.status.Response;
import com.nextech.erp.status.UserStatus;

@Controller
@Transactional @RequestMapping("/productRMAsso")
public class ProductRMAssoController {

	@Autowired
	ProductRMAssoService productRMAssoService;

	@Autowired 
	private MessageSource messageSource;

	@Autowired
	RawmaterialService rawmaterialService;

	@Autowired
	ProductService productService;
	
	@Autowired
	RMVAssoService RMVAssoService;
	
	@Autowired
	VendorService vendorService;
	

	@Autowired
	ProductionplanningService productionplanningService;

	@Transactional @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addProductrawmaterialassociation(
			@Valid @RequestBody ProductRMAssociationDTO productRMAssociationDTO,
			BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
			}
			if (productRMAssoService.getPRMAssociationByPidRmid(
					productRMAssociationDTO.getProduct(),
					productRMAssociationDTO.getRawmaterialId().getId()) == null){
				productRMAssoService.addEntity(ProductRMAssoRequestResponseFactory.setProductRMAsso(productRMAssociationDTO, request));
			}
			else
				return new UserStatus(1,messageSource.getMessage(ERPConstants.PRODUCT_RM_ASSO_EXIT, null, null));
			return new UserStatus(1,"Productrawmaterialassociation added Successfully !");
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

	@Transactional @RequestMapping(value = "/createmultiple", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addMultipleRawmaterialorder(
			@Valid @RequestBody ProductRMAssociationDTO productRMAssociationDTO, BindingResult bindingResult,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError().getDefaultMessage());
			}
			if(productRMAssociationDTO.getProductRMAssociationModelParts().isEmpty()){
				return new UserStatus(0,"In Product RM Assocition data is Empty !Please dont send Empty data");
			}
			productRMAssoService.addMultipleRawmaterialorder(productRMAssociationDTO, request.getAttribute("current_user").toString());
			return new UserStatus(1, "Multiple Rawmaterialorder added Successfully !");
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

	@Transactional @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody ProductRMAssociationDTO getProductrawmaterialassociation(
			@PathVariable("id") long id) {
		ProductRMAssociationDTO productrawmaterialassociation = null;
		try {
			productrawmaterialassociation = productRMAssoService.getProductRMAsooById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productrawmaterialassociation;
	}

	@Transactional @RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateProductrawmaterialassociation(
			@RequestBody ProductRMAssociationDTO productRMAssociationDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			productRMAssoService.updateEntity(ProductRMAssoRequestResponseFactory.setProductRMAssoUpdate(productRMAssociationDTO, request));
			return new UserStatus(1,"Productrawmaterialassociation update Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@Transactional @RequestMapping(value = "/update/multipleProductRMAssociation", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateProductRMAssociation(
			@RequestBody ProductRMAssociationDTO productRMAssociationDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			List<ProductRMAssociationDTO> productrawmaterialassociations = productRMAssoService.getProductRMAssoList(productRMAssociationDTO.getProduct());
			for(ProductRMAssociationDTO productrawmaterialassociation : productrawmaterialassociations){
				deleteProductrawmaterialassociation(productrawmaterialassociation.getId());
			}
			productRMAssoService.addMultipleRawmaterialorder(productRMAssociationDTO, request.getAttribute("current_user").toString());
			return new UserStatus(1,"Productrawmaterialassociation update Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@Transactional @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<ProductRMAssociationDTO> getProductrawmaterialassociation() {
		List<ProductRMAssociationDTO> productrawmaterialassociationList = null;
		try {
			productrawmaterialassociationList = productRMAssoService.getProductRMAssoList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productrawmaterialassociationList;
	}
	
	@Transactional @RequestMapping(value = "getRMVendorData/{productId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getRMVendorList(@PathVariable("productId") long productId) {
		List<ProductRMAssociationDTO> productrawmaterialassociations = null;
		List<RMVendorData> rmVendorDatas = new ArrayList<RMVendorData>();
		try {
			productrawmaterialassociations = productRMAssoService.getProductRMAssoList(productId);
			for(ProductRMAssociationDTO productRMAssociationDTO : productrawmaterialassociations){
				List<RMVendorAssociationDTO> rmVendorAssociationDTOs = RMVAssoService.getRawmaterialvendorassociationListByRMId(productRMAssociationDTO.getRawmaterialId().getId());
				for(RMVendorAssociationDTO rawmaterialvendorassociation : rmVendorAssociationDTOs){
					RMVendorData rawRmVendorData = new RMVendorData();
					VendorDTO vendor = vendorService.getVendorById(rawmaterialvendorassociation.getVendorId().getId());
					rawRmVendorData.setVendor(vendor.getId());
					rmVendorDatas.add(rawRmVendorData);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,"RMList and VendorList",rmVendorDatas);
	}

	@Transactional @RequestMapping(value = "/list/multiple", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getMultipleProductrawmaterialassociation() {
		List<ProductRMAssociationDTO> productRMAssociationModels = new ArrayList<ProductRMAssociationDTO>();
		try {
			List<ProductRMAssociationDTO> productrawmaterialassociationList = null;
			productrawmaterialassociationList = productRMAssoService.getProductRMAssoList();
			HashMap<Long, List<ProductRMAssociationModelParts>> multplePRMAsso = new HashMap<Long, List<ProductRMAssociationModelParts>>();
			for(ProductRMAssociationDTO productrawmaterialassociation : productrawmaterialassociationList){
				List<ProductRMAssociationModelParts> productRMAssociationModelParts = null;
				if(multplePRMAsso.get(productrawmaterialassociation.getProduct()) == null){
					productRMAssociationModelParts = new ArrayList<ProductRMAssociationModelParts>();
				}else{
					productRMAssociationModelParts = multplePRMAsso.get(productrawmaterialassociation.getProduct());
				}
				ProductRMAssociationModelParts productRMAssociationModelPart = new ProductRMAssociationModelParts();
				productRMAssociationModelPart.setQuantity(productrawmaterialassociation.getQuantity());
				RawMaterialDTO rawMaterialDTO =  new RawMaterialDTO();
				rawMaterialDTO.setPartNumber(productrawmaterialassociation.getRawmaterialId().getPartNumber());
				productRMAssociationModelPart.setRawmaterial(rawMaterialDTO);
				productRMAssociationModelParts.add(productRMAssociationModelPart);
				multplePRMAsso.put(productrawmaterialassociation.getProduct(), productRMAssociationModelParts);
			}
			Set<Entry<Long, List<ProductRMAssociationModelParts>>> multplePRMAssoEntries =  multplePRMAsso.entrySet();
			for(Entry<Long, List<ProductRMAssociationModelParts>> multplePRMAssoEntry : multplePRMAssoEntries){
				ProductRMAssociationDTO productRMAssociationModel = new ProductRMAssociationDTO();
				ProductDTO product = productService.getProductDTO( multplePRMAssoEntry.getKey());
				productRMAssociationModel.setName(product.getPartNumber());
				productRMAssociationModel.setProduct(multplePRMAssoEntry.getKey());
				productRMAssociationModel.setProductRMAssociationModelParts(multplePRMAssoEntry.getValue());
				productRMAssociationModels.add(productRMAssociationModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,productRMAssociationModels);
	}

	@Transactional @RequestMapping(value = "productRMAssoList/{productId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getProductRMAssoList(@PathVariable("productId") long productId) {

		List<ProductRMAssociationDTO> productrawmaterialassociationList = null;
		try {
			productrawmaterialassociationList = productRMAssoService.getProductRMAssoList(productId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,"Productionplanning List and Productrawmaterialassociation List",productrawmaterialassociationList);
	}
	
	@Transactional @RequestMapping(value = "getProductList", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response getProductList() {
		List<Long> productIdList = new ArrayList<Long>();
		List<ProductDTO> products = null;
		try {
			productIdList = productRMAssoService.getProductList();
			products = productService.getProductList(productIdList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Response response = null; 
		if(products == null){
			response = new Response(0,"Please add Product RM Association to Create BOM", products);
		}else{
			response = new Response(1,"Success", products);
		}
		return response;
	}

	@Transactional @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteProductrawmaterialassociation(
			@PathVariable("id") long id) {
		try {
			ProductRMAssociationDTO productRMAssociationDTO =	productRMAssoService.deleteProductRMAssoById(id);
			if(productRMAssociationDTO==null){
				return new Response(1,"There is no any product rm association");
			}
			return new Response(1,"Productrawmaterialassociation deleted Successfully !");
		} catch (Exception e) {
			return new Response(0, e.toString());
		}
	}
	
	@Transactional @RequestMapping(value = "delete/multiple/{productId}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteProductrawmaterialassociationByProductId(
			@PathVariable("productId") long productId) {
		try {
			List<ProductRMAssociationDTO> productrawmaterialassociations = productRMAssoService.getProductRMAssoList(productId);
			if(productrawmaterialassociations==null){
				return new Response(1,"There is no any product rm association");
			}
			for(ProductRMAssociationDTO productrawmaterialassociation : productrawmaterialassociations){
				//Remove all Product RM Associations
				//productRMAssoService.deleteEntity(Productrawmaterialassociation.class, productrawmaterialassociation.getId());
				deleteProductrawmaterialassociation(productrawmaterialassociation.getId());
			}
			return new Response(1,"Product Raw Material Associations deleted Successfully !");
		} catch (Exception e) {
			return new Response(0, e.toString());
		}
	}
}