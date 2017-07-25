package com.nextech.erp.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.BomDao;
import com.nextech.erp.dao.ProductDao;
import com.nextech.erp.dto.BomDTO;
import com.nextech.erp.factory.BOMFactory;
import com.nextech.erp.model.Bom;
import com.nextech.erp.model.Product;
import com.nextech.erp.service.BomService;
@Service
public class BomServiceImpl extends CRUDServiceImpl<Bom> implements BomService{

	@Autowired
	BomDao bomDao;
	
	@Autowired
	ProductDao productDao;
	
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
	@Override
	public BomDTO saveBOM(BomDTO bomDTO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Bom bom = BOMFactory.setBom(bomDTO, request);
		bom.setProduct(productDao.getById(Product.class,bomDTO.getProduct().getId()));
	   long id	=bomDao.add(bom);
	   String bomId = generateBomId()+bom.getId();
	    bom.setBomId(bomId);
	   bomDao.update(bom);
	   BomDTO bomDTO2 =  new BomDTO();
  	  bomDTO2.setId(id);
	  bomDTO2.setProduct(bom.getProduct());
	  return bomDTO2;
	}
	@Override
	public void updateBOMId(BomDTO bomDTO, String bomId) throws Exception {
		// TODO Auto-generated method stub
		Bom bom = new Bom();
		bom.setId(bomDTO.getId());
		bom.setBomId(bomId);
		bom.setIsactive(true);
		bomDao.update(bom);
	}
	private String generateBomId(){
		String bom="";
		bom = "BOM000";
		return bom;
	}
	

}
