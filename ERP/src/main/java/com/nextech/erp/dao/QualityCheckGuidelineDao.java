package com.nextech.erp.dao;

import com.nextech.erp.model.Qualitycheckguideline;

public interface QualityCheckGuidelineDao extends SuperDao<Qualitycheckguideline> {
	
	public Qualitycheckguideline getQCGuidlineByRMId(long rmId) throws Exception;
	
	public Qualitycheckguideline getQCGuidelineByProductId(long productId) throws Exception;

}
