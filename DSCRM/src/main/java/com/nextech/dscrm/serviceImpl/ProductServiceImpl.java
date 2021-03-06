package com.nextech.dscrm.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.dscrm.dao.ProductDao;
import com.nextech.dscrm.dao.ProductorderDao;
import com.nextech.dscrm.factory.ProductRequestResponseFactory;
import com.nextech.dscrm.model.Product;
import com.nextech.dscrm.model.Productorder;
import com.nextech.dscrm.model.Productorderassociation;
import com.nextech.dscrm.newDTO.ProductDTO;
import com.nextech.dscrm.service.ProductService;

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
	public Product getProductByPartNumber(String partNumber) throws Exception {
		return productDao.getProductByPartNumber(partNumber);
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
	public List<ProductDTO> getProductList(List<Long> productIdList) {
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
		Product product = productDao.getById(Product.class, id);
		if(product ==null){
			return null;
		}
		ProductDTO productDTO = ProductRequestResponseFactory.setProductDto(product);
		return productDTO;
	}

	@Override
	public ProductDTO deleteProduct(long id) throws Exception {
		Product product = productDao.getById(Product.class, id);
		if(product ==null){
			return null;
		}
		product.setIsactive(false);
		productDao.update(product);
		ProductDTO productDTO = ProductRequestResponseFactory.setProductDto(product);
		return productDTO;
	}

	@Override
	public Product getProductByProductId(long productId) throws Exception {
		return productDao.getProductByProductId(productId);
	}
}
