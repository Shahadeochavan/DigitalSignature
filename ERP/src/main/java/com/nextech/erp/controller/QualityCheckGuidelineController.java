package com.nextech.erp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.factory.QCGuidelineRequestResponseFactory;
import com.nextech.erp.model.Product;
import com.nextech.erp.model.Qualitycheckguideline;
import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.newDTO.QualitycheckguidelineDTO;
import com.nextech.erp.service.ProductService;
import com.nextech.erp.service.QualityCheckGuidelineService;
import com.nextech.erp.service.RawmaterialService;
import com.nextech.erp.status.Response;
import com.nextech.erp.status.UserStatus;


@RestController
@RequestMapping("/qcGuideline")
public class QualityCheckGuidelineController {

	@Autowired
	QualityCheckGuidelineService qualityCheckGuidelineService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	RawmaterialService rawmaterialService;
	
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addQualityCheckGuidline(@Valid @RequestBody QualitycheckguidelineDTO qualitycheckguidelineDTO,HttpServletRequest request,HttpServletResponse response,
			BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
			}
			
			if(qualitycheckguidelineDTO.getProductId()== qualitycheckguidelineDTO.getRawMaterialId()){
				return new UserStatus(0,"Please don't enter same product Id and rm Id");
			}
			if (qualityCheckGuidelineService.getQCGuidlineByRMId(qualitycheckguidelineDTO.getRawMaterialId()) == null) {

			} else {
				return new UserStatus(0, messageSource.getMessage(ERPConstants.QC_GUIDLINES_FOR_RM, null, null));
			}
			if (qualityCheckGuidelineService.getQCGuidelineByProductId(qualitycheckguidelineDTO.getRawMaterialId()) == null) {

			} else {
				return new UserStatus(0, messageSource.getMessage(ERPConstants.QC_GUIDLINES_FOR_PRODUCT, null, null));
			}
			qualityCheckGuidelineService.addEntity(QCGuidelineRequestResponseFactory.setQualityCheckGuidlines(qualitycheckguidelineDTO, request));
			return new UserStatus(1, "QC Guidline added Successfully !");
		} catch (ConstraintViolationException cve) {
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
	public @ResponseBody UserStatus getQualityCheckGuidline(@PathVariable("id") long id) {
		Qualitycheckguideline qualitycheckguideline = null;
		try {
			qualitycheckguideline = qualityCheckGuidelineService.getEntityById(Qualitycheckguideline.class, id);
			if(qualitycheckguideline == null){
				return new UserStatus(1,"There is no qc guidlines for this id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new UserStatus(1,qualitycheckguideline);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateQualityCheckGuidline(@RequestBody QualitycheckguidelineDTO  qualitycheckguidelineDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			qualityCheckGuidelineService.updateEntity(QCGuidelineRequestResponseFactory.setQualityCheckGuidlines(qualitycheckguidelineDTO, request));
			return new UserStatus(1, "Qualitycheckguideline update Successfully !");
		} catch (Exception e) {
			 e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody UserStatus getQualityCheckGuidline() {

		List<Qualitycheckguideline> qualitycheckguidelines = null;
		List<QualitycheckguidelineDTO> qualitycheckguidelineDTOs =  new ArrayList<QualitycheckguidelineDTO>();
		try {
			qualitycheckguidelines = qualityCheckGuidelineService.getEntityList(Qualitycheckguideline.class);
			if(qualitycheckguidelines != null && !qualitycheckguidelines.isEmpty()){
			for (Qualitycheckguideline qualitycheckguideline : qualitycheckguidelines) {
				QualitycheckguidelineDTO qualitycheckguidelineDTO = new QualitycheckguidelineDTO();
				qualitycheckguidelineDTO.setGuidelines(qualitycheckguideline.getGuidelines());
				qualitycheckguidelineDTO.setActive(true);
				if(qualitycheckguideline.getProductId()>0){
					Product product = productService.getEntityById(Product.class, qualitycheckguideline.getProductId());
					qualitycheckguidelineDTO.setProductPartNumber(product.getPartNumber());
					qualitycheckguidelineDTO.setId(qualitycheckguideline.getId());
					qualitycheckguidelineDTO.setProductId(qualitycheckguideline.getProductId());
					qualitycheckguidelineDTOs.add(qualitycheckguidelineDTO);
				}else if(qualitycheckguideline.getRawMaterialId()>0){
					Rawmaterial rawmaterial =  rawmaterialService.getEntityById(Rawmaterial.class, qualitycheckguideline.getRawMaterialId());
					qualitycheckguidelineDTO.setRmPartNumber(rawmaterial.getPartNumber());
					qualitycheckguidelineDTO.setId(qualitycheckguideline.getId());
					qualitycheckguidelineDTO.setRawMaterialId(qualitycheckguideline.getRawMaterialId());
					qualitycheckguidelineDTOs.add(qualitycheckguidelineDTO);
				}
			}
			}else{
				return new UserStatus(1,"There is no quality check guidlines list");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new UserStatus(1,qualitycheckguidelineDTOs);
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteQualityCheckGuidline(@PathVariable("id") long id) {
		Qualitycheckguideline qualitycheckguideline = null;
		try {
			qualitycheckguideline = qualityCheckGuidelineService.getEntityById(Qualitycheckguideline.class, id);
			if(qualitycheckguideline==null){
				return new UserStatus(1,"There is no qc guidlines for delete");
			}
			qualitycheckguideline.setIsactive(false);
			qualityCheckGuidelineService.updateEntity(qualitycheckguideline);
			
			return new UserStatus(1, "Qualitycheckguideline deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}

	}
	
	@RequestMapping(value = "rm/{RMID}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response qcGuidlines(@PathVariable("RMID") long rmId) {
		Qualitycheckguideline qualitycheckguideline = null;
		try {
			qualitycheckguideline = qualityCheckGuidelineService.getQCGuidlineByRMId(rmId);
			if(qualitycheckguideline==null){
				return new Response(0,"There is no quality check guidlines for this Raw Material");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,"qc guidlines List",qualitycheckguideline);
	}
	
	@RequestMapping(value = "product/{PRODUCTID}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response qcGuidlinesProduct(@PathVariable("PRODUCTID") long productId) {
		Qualitycheckguideline qualitycheckguideline = null;
		try {
			qualitycheckguideline = qualityCheckGuidelineService.getQCGuidelineByProductId(productId);
			if(qualitycheckguideline==null){
				return new Response(0,"There is no quality check guidlines for this product");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,"qc guidlines List",qualitycheckguideline);
	}
}
