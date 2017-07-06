package com.nextech.erp.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.RawmaterialinventoryDao;
import com.nextech.erp.model.Rawmaterialinventory;
import com.nextech.erp.service.RawmaterialinventoryService;
@Service
public class RawmaterialinventoryServiceImpl extends CRUDServiceImpl<Rawmaterialinventory> implements
		RawmaterialinventoryService {

	@Autowired
	RawmaterialinventoryDao rawmaterialinventoryDao; 
	
	@Override
	public Rawmaterialinventory getByRMId(long id) throws Exception {
		return rawmaterialinventoryDao.getByRMId(id);
	}

}
