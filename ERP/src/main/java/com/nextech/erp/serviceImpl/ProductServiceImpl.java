package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.ProductDao;
import com.nextech.erp.dao.ProductorderDao;
import com.nextech.erp.factory.ProductRequestResponseFactory;
import com.nextech.erp.model.Product;
import com.nextech.erp.model.Productorder;
import com.nextech.erp.model.Productorderassociation;
import com.nextech.erp.newDTO.ProductDTO;
import com.nextech.erp.service.ProductService;
@Service
public class ProductServiceImpl extends CRUDServiceImpl<Product> implements ProductService {

	@Autowired
	ProductDao productDao;

	@Autowired
	ProductorderDao productorderDao; 
	
	@Override
	public Product getProductByName(String name) throws Exception {
		return productDao.getProductByName(name);
	}

	@Override
	public Product getProductByPartNumber(String partnumber) throws Exception {
		return productDao.getProductByPartNumber(partnumber);
	}

	@Override
	public boolean isOrderPartiallyDispatched(long orderId) throws Exception {
		boolean isDispatched = false;
		Productorder productorder = productorderDao.getProductorderByProductOrderId(orderId);
		for (Productorderassociation productorderassociation : productorder.getOrderproductassociations()) {
			if(productorderassociation.getRemainingQuantity() >= 0){
				isDispatched = true;
				break;
			}
		}
		return isDispatched;
	}

	@Override
	public Product getProductListByProductId(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductDTO> getProductList(List<Long> productIdList) {
		// TODO Auto-generated method stub
		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		List<Product> products = productDao.getProductList(productIdList);
		if(products.isEmpty()){
			return null;
		}
		for (Product product : products) {
			ProductDTO productDTO = ProductRequestResponseFactory.setProductDto(product);
			productDTOs.add(productDTO);
		}
		return productDTOs;
	}

	@Override
	public List<ProductDTO> getProductList() throws Exception {
		// TODO Auto-generated method stub
		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		List<Product> products = productDao.getList(Product.class);
		for (Product product : products) {
			ProductDTO productDTO = ProductRequestResponseFactory.setProductDto(product);
			productDTOs.add(productDTO);
		}
		return productDTOs;
	}

	@Override
	public ProductDTO getProductDTO(long id) throws Exception {
		// TODO Auto-generated method stub
		Product product = productDao.getById(Product.class, id);
		if(product ==null){
			return null;
		}
		ProductDTO productDTO = ProductRequestResponseFactory.setProductDto(product);
		return productDTO;
	}

	@Override
	public ProductDTO deleteProduct(long id) throws Exception {
		// TODO Auto-generated method stub
		Product product = productDao.getById(Product.class, id);
		if(product ==null){
			return null;
		}
		ProductDTO productDTO = ProductRequestResponseFactory.setProductDto(product);
		return productDTO;
		
	}

	@Override
	public Product getProductByProductId(long productId) throws Exception {
		// TODO Auto-generated method stub
		return productDao.getProductByProductId(productId);
	}
}
