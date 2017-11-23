package com.nextech.dscrm.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.dscrm.dao.QualityCheckGuidelineDao;
import com.nextech.dscrm.model.Qualitycheckguideline;
import com.nextech.dscrm.service.QualityCheckGuidelineService;

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
