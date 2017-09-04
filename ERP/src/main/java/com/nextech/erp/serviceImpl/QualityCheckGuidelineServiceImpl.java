package com.nextech.erp.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.QualityCheckGuidelineDao;
import com.nextech.erp.model.Qualitycheckguideline;
import com.nextech.erp.service.QualityCheckGuidelineService;

@Service
public class QualityCheckGuidelineServiceImpl extends CRUDServiceImpl<Qualitycheckguideline> implements  QualityCheckGuidelineService {

	@Autowired
	QualityCheckGuidelineDao qualityCheckGuidelineDao;
	@Override
	public Qualitycheckguideline getQCGuidlineByRMId(long rmId)
			throws Exception {
		
		return qualityCheckGuidelineDao.getQCGuidlineByRMId(rmId);
	}

	@Override
	public Qualitycheckguideline getQCGuidelineByProductId(long productId)
			throws Exception {
		
		return qualityCheckGuidelineDao.getQCGuidelineByProductId(productId);
	}

}
