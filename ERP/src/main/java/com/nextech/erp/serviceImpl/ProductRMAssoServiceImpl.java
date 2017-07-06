package com.nextech.erp.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.ProductRMAssoDao;
import com.nextech.erp.model.Productrawmaterialassociation;
import com.nextech.erp.service.ProductRMAssoService;
@Service
public class ProductRMAssoServiceImpl extends CRUDServiceImpl<Productrawmaterialassociation> implements ProductRMAssoService {

	@Autowired
	ProductRMAssoDao productrmassDao;

	@Override
	public Productrawmaterialassociation getPRMAssociationByPidRmid(long pid,long rid) throws Exception {
		return productrmassDao.getPRMAssociationByPidRmid(pid,rid);
	}

	@Override
	public List<Productrawmaterialassociation> getProductRMAssoListByProductId(
			long productID) throws Exception {
		// TODO Auto-generated method stub
		return productrmassDao.getProductRMAssoListByProductId(productID);
	}

	@Override
	public List<Long> getProductList()
			throws Exception {
		
		return productrmassDao.getProductList();
	}
}