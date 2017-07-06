package com.nextech.erp.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.ProductorderDao;
import com.nextech.erp.model.Productorder;
import com.nextech.erp.service.ProductorderService;
@Service
public class ProductorderServiceImpl extends CRUDServiceImpl<Productorder> implements ProductorderService {

	@Autowired
	ProductorderDao productorderDao;
	@Override
	public Productorder getProductorderByProductOrderId(long pOrderId)
			throws Exception {
		// TODO Auto-generated method stub
		return productorderDao.getProductorderByProductOrderId(pOrderId);
	}
	@Override
	public List<Productorder> getPendingProductOrders(long statusId,long statusId1) {
		return productorderDao.getPendingProductOrders(statusId,statusId1);
	}
	@Override
	public List<Productorder> getInCompleteProductOrder(long clientId,long statusId,long statusId1) {
		// TODO Auto-generated method stub
		return productorderDao.getInCompleteProductOrder(clientId,statusId,statusId1);
	}
}
