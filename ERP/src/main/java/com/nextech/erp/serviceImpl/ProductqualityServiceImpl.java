package com.nextech.erp.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.ProductqualityDao;
import com.nextech.erp.model.Productquality;
import com.nextech.erp.service.ProductqualityService;
@Service
public class ProductqualityServiceImpl extends CRUDServiceImpl<Productquality> implements ProductqualityService{
	
	@Autowired
	ProductqualityDao productqualityDao;

	@Override
	public List<Productquality> getProductqualityListByProductId(long productId)
			throws Exception {
		// TODO Auto-generated method stub
		return productqualityDao.getProductqualityListByProductId(productId);
	}

}
