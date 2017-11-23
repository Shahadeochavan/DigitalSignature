package com.nextech.dscrm.service;

import com.nextech.dscrm.model.Qualitycheckguideline;

public interface QualityCheckGuidelineService extends CRUDService<Qualitycheckguideline>{
	
	public Qualitycheckguideline getQCGuidlineByRMId(long rmId) throws Exception;
	
	public Qualitycheckguideline getQCGuidelineByProductId(long productId) throws Exception;

}
