package com.nextech.erp.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.BomDao;
import com.nextech.erp.model.Bom;
import com.nextech.erp.service.BomService;
@Service
public class BomServiceImpl extends CRUDServiceImpl<Bom> implements BomService{

	@Autowired
	BomDao bomDao;
	@Override
	public List<Bom> getBomListByProductId(long productID) throws Exception {
		// TODO Auto-generated method stub
		return bomDao.getBomListByProductId(productID);
	}
	@Override
	public List<Bom> getBomListByProductIdAndBomId(long productId, long bomId)
			throws Exception {
		// TODO Auto-generated method stub
		return bomDao.getBomListByProductIdAndBomId(productId, bomId);
	}
	@Override
	public List<Long> getProductList() {
		// TODO Auto-generated method stub
		return bomDao.getProductList();
	}
	@Override
	public Bom getBomByProductId(long productID) throws Exception {
		// TODO Auto-generated method stub
		return bomDao.getBomByProductId(productID);
	}

}
