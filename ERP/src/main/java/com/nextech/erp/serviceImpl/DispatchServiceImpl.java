package com.nextech.erp.serviceImpl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.DispatchDao;
import com.nextech.erp.model.Dispatch;
import com.nextech.erp.service.DispatchService;
@Service
public class DispatchServiceImpl extends CRUDServiceImpl<Dispatch> implements DispatchService {

	@Autowired
	DispatchDao dispatchDao;


	@Override
	public Dispatch getDispatchByProductOrderIdAndProductId(long orderID,
			long productID) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Dispatch> getDispatchByProductOrderId(long productOrderId)
			throws Exception {
		// TODO Auto-generated method stub
		return dispatchDao.getDispatchByProductOrderId(productOrderId);
	}

}
