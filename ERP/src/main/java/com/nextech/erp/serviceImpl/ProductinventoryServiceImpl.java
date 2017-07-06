package com.nextech.erp.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.ProductinventoryDao;
import com.nextech.erp.model.Productinventory;
import com.nextech.erp.service.ProductinventoryService;
@Service
public class ProductinventoryServiceImpl extends CRUDServiceImpl<Productinventory> implements ProductinventoryService {

	@Autowired
	 ProductinventoryDao productinventoryDao;
	@Override
	public Productinventory getProductinventoryByProductId(long productId)
			throws Exception {
		// TODO Auto-generated method stub
		return productinventoryDao.getProductinventoryByProductId(productId);
	}
	@Override
	public List<Productinventory> getProductinventoryListByProductId(
			long productId) throws Exception {
		// TODO Auto-generated method stub
		return productinventoryDao.getProductinventoryListByProductId(productId);
	}

}
