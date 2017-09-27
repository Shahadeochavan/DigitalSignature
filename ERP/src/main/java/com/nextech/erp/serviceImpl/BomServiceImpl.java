package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
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
import com.nextech.erp.newDTO.ProductDTO;
import com.nextech.erp.service.BomService;

@Service
public class BomServiceImpl extends CRUDServiceImpl<Bom> implements BomService {

	@Autowired
	BomDao bomDao;

	@Autowired
	ProductDao productDao;

	@Override
	public List<BomDTO> getBomListByProductId(long productID) throws Exception {
		List<BomDTO> bomDTOs = new ArrayList<BomDTO>();
		List<Bom> boms = bomDao.getBomListByProductId(productID);
		if(boms.isEmpty()){
			return null;
		}
		for (Bom bom : boms) {
			BomDTO  bomDTO =  BOMFactory.setBomDTO(bom);
			bomDTOs.add(bomDTO);
		}
		return bomDTOs;
	}

	@Override
	public List<BomDTO> getBomListByProductIdAndBomId(long productId, long bomId)
			throws Exception {
		List<BomDTO> bomDTOs = new ArrayList<BomDTO>();
		List<Bom> boms = bomDao.getBomListByProductIdAndBomId(productId, bomId);
		if(boms.isEmpty()){
			return null;
		}
		for (Bom bom : boms) {
			BomDTO bomDTO = BOMFactory.setBomDTO(bom);
			bomDTOs.add(bomDTO);
		}
		return bomDTOs;
	}

	@Override
	public List<Long> getProductList() {
		
		return bomDao.getProductList();
	}

	@Override
	public Bom getBomByProductId(long productID) throws Exception {
		
		return bomDao.getBomByProductId(productID);
	}

	@Override
	public BomDTO addMultipleBom(BomDTO bomDTO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Bom bom = BOMFactory.setBom(bomDTO, request);
		bom.setProduct(productDao.getById(Product.class, bomDTO.getProduct()
				.getId()));
		long id = bomDao.add(bom);
		String bomId = generateBomId() + bom.getId();
		bom.setBomId(bomId);
		bomDao.update(bom);
		BomDTO bomDTO2 = new BomDTO();
		bomDTO2.setId(id);
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(bom.getProduct().getId());
		bomDTO2.setProduct(productDTO);
		return bomDTO2;
	}

	private String generateBomId() {
		String bom = "";
		bom = "BOM000";
		return bom;
	}

	@Override
	public List<BomDTO> getBomList() throws Exception {
		List<BomDTO> bomDTOs =  new ArrayList<BomDTO>();
		List<Bom> boms = bomDao.getList(Bom.class);
		if(boms.isEmpty()){
			return null;
		}
		for (Bom bom : boms) {
			BomDTO bomDTO = BOMFactory.setBomDTO(bom);
			bomDTOs.add(bomDTO);
		}
		return bomDTOs;
	}

	@Override
	public BomDTO getBomById(long id) throws Exception {
		Bom bom = bomDao.getById(Bom.class, id);
		if(bom==null){
			return null;
		}
		BomDTO bomDTO = BOMFactory.setBomDTO(bom);
		return bomDTO;
	}

	@Override
	public BomDTO deleteBom(long id) throws Exception {
		Bom bom = bomDao.getById(Bom.class, id);
		if(bom==null){
			return null;
		}
		bom.setIsactive(false);
		bomDao.update(bom);
		BomDTO bomDTO = BOMFactory.setBomDTO(bom);
		return bomDTO;
	}
}
