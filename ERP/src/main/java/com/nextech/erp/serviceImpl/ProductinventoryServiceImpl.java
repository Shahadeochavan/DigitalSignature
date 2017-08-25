package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.ProductinventoryDao;
import com.nextech.erp.dto.ProductInventoryDTO;
import com.nextech.erp.factory.ProductInventoryRequestResponseFactory;
import com.nextech.erp.model.Productinventory;
import com.nextech.erp.service.ProductinventoryService;
@Service
public class ProductinventoryServiceImpl extends CRUDServiceImpl<Productinventory> implements ProductinventoryService {

	@Autowired
	 ProductinventoryDao productinventoryDao;
	@Override
	public Productinventory getProductinventoryByProductId(long productId)
			throws Exception {
		return productinventoryDao.getProductinventoryByProductId(productId);
	}
	@Override
	public List<ProductInventoryDTO> getProductinventoryListByProductId(
			long productId) throws Exception {
		List<ProductInventoryDTO> productInventoryDTOs = new ArrayList<ProductInventoryDTO>();
		List<Productinventory> productinventories = productinventoryDao.getProductinventoryListByProductId(productId);
		for (Productinventory productinventory : productinventories) {
			ProductInventoryDTO productInventoryDTO = ProductInventoryRequestResponseFactory.setProductDTO(productinventory);
			productInventoryDTOs.add(productInventoryDTO);
			
		}
		return productInventoryDTOs;
	}
	@Override
	public List<ProductInventoryDTO> getproductInventoryDTO() throws Exception {
		List<ProductInventoryDTO> productInventoryDTOs = new ArrayList<ProductInventoryDTO>();
		List<Productinventory> productinventories = productinventoryDao.getList(Productinventory.class);
		for (Productinventory productinventory : productinventories) {
			ProductInventoryDTO productInventoryDTO = ProductInventoryRequestResponseFactory.setProductDTO(productinventory);
			productInventoryDTOs.add(productInventoryDTO);
			
		}
		return productInventoryDTOs;
	}
	@Override
	public ProductInventoryDTO getProductInventory(long id) throws Exception {
		Productinventory productinventory = productinventoryDao.getById(Productinventory.class, id);
		ProductInventoryDTO productInventoryDTO = ProductInventoryRequestResponseFactory.setProductDTO(productinventory);
		return productInventoryDTO;
	}
	@Override
	public void deleteProductInventory(long id) throws Exception {
		Productinventory productinventory = productinventoryDao.getById(Productinventory.class, id);
		productinventory.setIsactive(false);
		productinventoryDao.update(productinventory);
		
	}

}
