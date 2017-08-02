package com.nextech.erp.controller;

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
import com.nextech.erp.model.Qualitycheckguideline;
import com.nextech.erp.newDTO.QualitycheckguidelineDTO;
import com.nextech.erp.service.QualityCheckGuidelineService;
import com.nextech.erp.status.UserStatus;


@RestController
@RequestMapping("/qcGuideline")
public class QualityCheckGuidelineController {

	@Autowired
	QualityCheckGuidelineService qualityCheckGuidelineService;
	
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public @ResponseBody UserStatus addUnit(@Valid @RequestBody QualitycheckguidelineDTO qualitycheckguidelineDTO,HttpServletRequest request,HttpServletResponse response,
			BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				return new UserStatus(0, bindingResult.getFieldError()
						.getDefaultMessage());
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
	public @ResponseBody Qualitycheckguideline getUnit(@PathVariable("id") long id) {
		Qualitycheckguideline qualitycheckguideline = null;
		try {
			qualitycheckguideline = qualityCheckGuidelineService.getEntityById(Qualitycheckguideline.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qualitycheckguideline;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody UserStatus updateUnit(@RequestBody QualitycheckguidelineDTO  qualitycheckguidelineDTO,HttpServletRequest request,HttpServletResponse response) {
		try {
			qualityCheckGuidelineService.updateEntity(QCGuidelineRequestResponseFactory.setQualityCheckGuidlines(qualitycheckguidelineDTO, request));
			return new UserStatus(1, "Qualitycheckguideline update Successfully !");
		} catch (Exception e) {
			 e.printStackTrace();
			return new UserStatus(0, e.toString());
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Qualitycheckguideline> getUnit() {

		List<Qualitycheckguideline> qualitycheckguidelines = null;
		try {
			qualitycheckguidelines = qualityCheckGuidelineService.getEntityList(Qualitycheckguideline.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qualitycheckguidelines;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public @ResponseBody UserStatus deleteUnit(@PathVariable("id") long id) {
		Qualitycheckguideline qualitycheckguideline = null;
		try {
			qualitycheckguideline = qualityCheckGuidelineService.getEntityById(Qualitycheckguideline.class, id);
			qualitycheckguideline.setIsactive(false);
			qualityCheckGuidelineService.updateEntity(qualitycheckguideline);
			
			return new UserStatus(1, "Qualitycheckguideline deleted Successfully !");
		} catch (Exception e) {
			return new UserStatus(0, e.toString());
		}

	}
	
	@RequestMapping(value = "QCGUIDLINES/{RMID}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Qualitycheckguideline qcGuidlines(@PathVariable("RMID") long rmId) {
		Qualitycheckguideline qualitycheckguideline = null;
		try {
			qualitycheckguideline = qualityCheckGuidelineService.getQCGuidlineByRMId(rmId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qualitycheckguideline;
	}
}