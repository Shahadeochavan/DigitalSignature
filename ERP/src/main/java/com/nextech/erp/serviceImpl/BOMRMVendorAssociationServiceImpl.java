package com.nextech.erp.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.BOMRMVendorAssociationDao;
import com.nextech.erp.model.Bomrmvendorassociation;
import com.nextech.erp.service.BOMRMVendorAssociationService;
@Service
public class BOMRMVendorAssociationServiceImpl extends CRUDServiceImpl<Bomrmvendorassociation> implements BOMRMVendorAssociationService {

	@Autowired
	BOMRMVendorAssociationDao bOMRMVendorAssociationDao;
	@Override
	public List<Bomrmvendorassociation> getBomRMVendorByBomId(long bomId)
			throws Exception {
		// TODO Auto-generated method stub
		return bOMRMVendorAssociationDao.getBomRMVendorByBomId(bomId);
	}
	


}
