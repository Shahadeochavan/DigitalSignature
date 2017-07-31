package com.nextech.erp.service;

import com.nextech.erp.model.Qualitycheckguideline;

public interface QualityCheckGuidelineService extends CRUDService<Qualitycheckguideline>{
	
	public Qualitycheckguideline getQCGuidlineByRMId(long rmId) throws Exception;
	
	public Qualitycheckguideline getQCGuidelineByProductId(long productId) throws Exception;

}
