package com.nextech.erp.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nextech.erp.dto.BomDTO;
import com.nextech.erp.model.Bom;

public interface BomService extends CRUDService<Bom> {
	
	public List<BomDTO> getBomListByProductId(long productID) throws Exception;
	
	public List<BomDTO> getBomListByProductIdAndBomId(long productId,long bomId) throws Exception;

	public List<Long> getProductList();
	
	public Bom  getBomByProductId(long productID) throws Exception;
	
	public BomDTO saveBOM(BomDTO bomDTO, HttpServletRequest request,HttpServletResponse response) throws Exception;
	
	public List<BomDTO> getBomList() throws Exception;
	
	public BomDTO getBomById(long id) throws Exception;
	
	public void deleteBom(long id) throws Exception;
	
}
