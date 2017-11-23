package com.nextech.dscrm.dao;

import com.nextech.dscrm.model.Qualitycheckguideline;

public interface QualityCheckGuidelineDao extends SuperDao<Qualitycheckguideline> {
	
	public Qualitycheckguideline getQCGuidlineByRMId(long rmId) throws Exception;
	
	public Qualitycheckguideline getQCGuidelineByProductId(long productId) throws Exception;

}
