package com.nextech.dscrm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
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

import com.nextech.dscrm.constants.ERPConstants;
import com.nextech.dscrm.factory.QCGuidelineRequestResponseFactory;
import com.nextech.dscrm.model.Qualitycheckguideline;
import com.nextech.dscrm.newDTO.QualitycheckguidelineDTO;
import com.nextech.dscrm.service.ProductService;
import com.nextech.dscrm.service.QualityCheckGuidelineService;
import com.nextech.dscrm.status.Response;
import com.nextech.dscrm.status.UserStatus;


@RestController
@RequestMapping("/qcGuideline")
public class QualityCheckGuidelineController {

	@Autowired
	QualityCheckGuidelineService qualityCheckGuidelineService;
	
	@Autowired
	ProductService productService;
	
	
	@Autowired
	private MessageSource messageSource;
	
	static Logger logger = Logger.getLogger(QualityCheckGuidelineController.class);

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
			if(qualitycheckguidelineDTO.getProductId()==0){
			if (qualityCheckGuidelineService.getQCGuidlineByRMId(qualitycheckguidelineDTO.getRawMaterialId()) != null) {
				return new UserStatus(0, messageSource.getMessage(ERPConstants.QC_GUIDLINES_FOR_RM, null, null));
			  }
			}
			if (qualityCheckGuidelineService.getQCGuidelineByProductId(qualitycheckguidelineDTO.getProductId()) != null) {
				return new UserStatus(0, messageSource.getMessage(ERPConstants.QC_GUIDLINES_FOR_PRODUCT, null, null));
			}
			qualityCheckGuidelineService.addEntity(QCGuidelineRequestResponseFactory.setQualityCheckGuidlines(qualitycheckguidelineDTO, request));
			return new UserStatus(1, "QC Guidline added Successfully !");
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
	public @ResponseBody Response getQualityCheckGuidline(@PathVariable("id") long id) {
		Qualitycheckguideline qualitycheckguideline = null;
		try {
			qualitycheckguideline = qualityCheckGuidelineService.getEntityById(Qualitycheckguideline.class, id);
			if(qualitycheckguideline == null){
				logger.error("There is no qc guidlines for this id");
				return new Response(1,"There is no qc guidlines");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,qualitycheckguideline);
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

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody Response deleteQualityCheckGuidline(@PathVariable("id") long id) {
		Qualitycheckguideline qualitycheckguideline = null;
		try {
			qualitycheckguideline = qualityCheckGuidelineService.getEntityById(Qualitycheckguideline.class, id);
			if(qualitycheckguideline==null){
				logger.error("There is no qc guidlines for delete");
				return new Response(1,"There is no qc guidlines for delete");
			}
			qualitycheckguideline.setIsactive(false);
			qualityCheckGuidelineService.updateEntity(qualitycheckguideline);
			
			return new Response(1, "Qualitycheckguideline deleted Successfully !");
		} catch (Exception e) {
			return new Response(0, e.toString());
		}

	}
	
	@RequestMapping(value = "rm/{RMID}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Response qcGuidlines(@PathVariable("RMID") long rmId) {
		Qualitycheckguideline qualitycheckguideline = null;
		try {
			qualitycheckguideline = qualityCheckGuidelineService.getQCGuidlineByRMId(rmId);
			if(qualitycheckguideline==null){
				logger.error("There is no quality check guidlines for this Raw Material");
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
				logger.error("There is no quality check guidlines for this product");
				return new Response(0,"There is no quality check guidlines for this product");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(1,"qc guidlines List",qualitycheckguideline);
	}
}
