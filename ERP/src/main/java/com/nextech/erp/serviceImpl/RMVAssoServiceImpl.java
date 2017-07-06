package com.nextech.erp.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.RMVAssoDao;
import com.nextech.erp.model.Rawmaterialvendorassociation;
import com.nextech.erp.service.RMVAssoService;
@Service
public class RMVAssoServiceImpl extends CRUDServiceImpl<Rawmaterialvendorassociation> implements RMVAssoService{

	@Autowired
	RMVAssoDao rMVAssoDao;
	
	@Override
	public Rawmaterialvendorassociation getRMVAssoByVendorIdRMId(
			long vendorId, long rmId) throws Exception {
		// TODO Auto-generated method stub
		return rMVAssoDao.getRMVAssoByVendorIdRMId(vendorId, rmId);
	}

	@Override
	public List<Rawmaterialvendorassociation> getRawmaterialvendorassociationListByRMId(long id) throws Exception {
		// TODO Auto-generated method stub
		return rMVAssoDao.getRawmaterialvendorassociationListByRMId(id);
	}

	@Override
	public Rawmaterialvendorassociation getRMVAssoByRMId(long rmId)
			throws Exception {
		// TODO Auto-generated method stub
		return rMVAssoDao.getRMVAssoByRMId(rmId);
	}
	
}

