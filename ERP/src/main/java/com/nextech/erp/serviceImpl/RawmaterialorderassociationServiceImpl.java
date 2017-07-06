package com.nextech.erp.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.RawmaterialorderassociationDao;
import com.nextech.erp.model.Rawmaterialorderassociation;
import com.nextech.erp.service.RawmaterialorderassociationService;
@Service
public class RawmaterialorderassociationServiceImpl extends CRUDServiceImpl<Rawmaterialorderassociation> implements RawmaterialorderassociationService {

	@Autowired
	RawmaterialorderassociationDao rawmaterialorderassociationDao;

	@Override
	public List<Rawmaterialorderassociation> getRMOrderRMAssociationByRMOrderId(long id) throws Exception {
		
		return rawmaterialorderassociationDao.getRMOrderRMAssociationByRMOrderId(id);
	}

	@Override
	public Rawmaterialorderassociation getRMOrderRMAssociationByRMOrderIdandRMId(long id, long rmId) throws Exception {
		// TODO Auto-generated method stub
		return rawmaterialorderassociationDao.getRMOrderRMAssociationByRMOrderIdandRMId(id, rmId);
	}

}
