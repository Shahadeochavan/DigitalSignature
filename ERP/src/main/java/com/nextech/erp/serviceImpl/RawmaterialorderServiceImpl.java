package com.nextech.erp.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.RawmaterialorderDao;
import com.nextech.erp.model.Rawmaterialorder;
import com.nextech.erp.service.RawmaterialorderService;
@Service
public class RawmaterialorderServiceImpl extends CRUDServiceImpl<Rawmaterialorder> implements RawmaterialorderService{

	@Autowired
	RawmaterialorderDao rawmaterialorderDao;

	@Override
	public Rawmaterialorder getRawmaterialorderByIdName(long id,String rmname) throws Exception {
		return rawmaterialorderDao.getRawmaterialorderByIdName(id,rmname);
	}

	@Override
	public List<Rawmaterialorder> getRawmaterialorderByStatusId(long statusId,long statusId1,long statusId2)
			throws Exception {
		// TODO Auto-generated method stub
		return rawmaterialorderDao.getRawmaterialorderByStatusId(statusId,statusId1,statusId2);
	}

	@Override
	public List<Rawmaterialorder> getRawmaterialorderByQualityCheckStatusId(
			long statusId) throws Exception {
		// TODO Auto-generated method stub
		return rawmaterialorderDao.getRawmaterialorderByQualityCheckStatusId(statusId);
	}

	@Override
	public List<Rawmaterialorder> getRawmaterialorderByVendor(long vendorId)
			throws Exception {
		// TODO Auto-generated method stub
		return rawmaterialorderDao.getRawmaterialorderByVendor(vendorId);
	}

	@Override
	public List<Rawmaterialorder> getRawmaterialByName(String name)
			throws Exception {
		// TODO Auto-generated method stub
		return rawmaterialorderDao.getRawmaterialByName(name);
	}

	@Override
	public List<Rawmaterialorder> getRawmaterialorderByVendorId(long vendorId,
			long statusId1, long statusId2) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

