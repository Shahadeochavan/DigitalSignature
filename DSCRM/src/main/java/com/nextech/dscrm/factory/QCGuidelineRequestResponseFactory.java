package com.nextech.dscrm.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.dscrm.model.Qualitycheckguideline;
import com.nextech.dscrm.newDTO.QualitycheckguidelineDTO;

public class QCGuidelineRequestResponseFactory {
	
	public static Qualitycheckguideline setQualityCheckGuidlines(QualitycheckguidelineDTO qualitycheckguidelineDTO,HttpServletRequest request){
		Qualitycheckguideline qualitycheckguideline =  new Qualitycheckguideline();
		qualitycheckguideline.setId(qualitycheckguidelineDTO.getId());
		qualitycheckguideline.setProductId(qualitycheckguidelineDTO.getProductId());
		qualitycheckguideline.setRawMaterialId(qualitycheckguidelineDTO.getRawMaterialId());
		qualitycheckguideline.setGuidelines(qualitycheckguidelineDTO.getGuidelines());
		qualitycheckguideline.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		qualitycheckguideline.setIsactive(true);
		return qualitycheckguideline;
	}
	
	public static Qualitycheckguideline setQualityCheckGuidlinesUpdet(QualitycheckguidelineDTO qualitycheckguidelineDTO,HttpServletRequest request){
		Qualitycheckguideline qualitycheckguideline =  new Qualitycheckguideline();
		qualitycheckguideline.setId(qualitycheckguidelineDTO.getId());
		qualitycheckguideline.setProductId(qualitycheckguidelineDTO.getProductId());
		qualitycheckguideline.setRawMaterialId(qualitycheckguidelineDTO.getRawMaterialId());
		qualitycheckguideline.setGuidelines(qualitycheckguidelineDTO.getGuidelines());
		qualitycheckguideline.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		qualitycheckguideline.setIsactive(true);
		return qualitycheckguideline;
	}

}
