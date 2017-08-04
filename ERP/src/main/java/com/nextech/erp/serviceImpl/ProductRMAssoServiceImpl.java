package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.ProductDao;
import com.nextech.erp.dao.ProductRMAssoDao;
import com.nextech.erp.dao.RawmaterialDao;
import com.nextech.erp.dto.ProductRMAssociationDTO;
import com.nextech.erp.dto.ProductRMAssociationModelParts;
import com.nextech.erp.factory.ProductRMAssoRequestResponseFactory;
import com.nextech.erp.model.Product;
import com.nextech.erp.model.Productrawmaterialassociation;
import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.newDTO.RawMaterialDTO;
import com.nextech.erp.service.ProductRMAssoService;
@Service
public class ProductRMAssoServiceImpl extends CRUDServiceImpl<Productrawmaterialassociation> implements ProductRMAssoService {

	@Autowired
	ProductRMAssoDao productrmassDao;
	
	@Autowired
	RawmaterialDao rawmaterialDao;
	
	@Autowired
	ProductDao productDao;

	@Override
	public Productrawmaterialassociation getPRMAssociationByPidRmid(long pid,long rid) throws Exception {
		return productrmassDao.getPRMAssociationByPidRmid(pid,rid);
	}

	@Override
	public List<ProductRMAssociationDTO> getProductRMAssoListByProductId(
			long productID) throws Exception {
		// TODO Auto-generated method stub
		List<ProductRMAssociationDTO> productRMAssociationDTOs = new ArrayList<ProductRMAssociationDTO>();
		List<Productrawmaterialassociation> productrawmaterialassociations = productrmassDao.getProductRMAssoListByProductId(productID);
		if (productrawmaterialassociations != null && !productrawmaterialassociations.isEmpty()) {
		for (Productrawmaterialassociation productrawmaterialassociation : productrawmaterialassociations) {
			ProductRMAssociationDTO productRMAssociationDTO = ProductRMAssoRequestResponseFactory.setProductRMAssoList(productrawmaterialassociation);
			productRMAssociationDTOs.add(productRMAssociationDTO);
		}
		}
		return productRMAssociationDTOs;
	}

	@Override
	public List<Long> getProductList()
			throws Exception {
		
		return productrmassDao.getProductList();
	}

	@Override
	public List<ProductRMAssociationDTO> getProductRMAssoList(long productId)
			throws Exception {
		// TODO Auto-generated method stub
		List<ProductRMAssociationDTO> productRMAssociationDTOs = new ArrayList<ProductRMAssociationDTO>();
		List<Productrawmaterialassociation> productrawmaterialassociations = productrmassDao.getProductRMAssoListByProductId(productId);
		for (Productrawmaterialassociation productrawmaterialassociation : productrawmaterialassociations) {
			ProductRMAssociationDTO productRMAssociationDTO = ProductRMAssoRequestResponseFactory.setProductRMAssoList(productrawmaterialassociation);
			productRMAssociationDTOs.add(productRMAssociationDTO);
		}
		return productRMAssociationDTOs;
	}

	@Override
	public ProductRMAssociationDTO createmultiple(ProductRMAssociationDTO productRMAssociationDTO,
			String currentUser) throws Exception {
		// TODO Auto-generated method stub
		for(ProductRMAssociationModelParts productRMAssociationModelParts : productRMAssociationDTO.getProductRMAssociationModelParts()){
			Productrawmaterialassociation productrawmaterialassociation =  setMultipleRM(productRMAssociationModelParts);
			productrawmaterialassociation.setProduct(productDao.getById(Product.class, productRMAssociationDTO.getProduct()));
			productrawmaterialassociation.setCreatedBy(Long.parseLong(currentUser));
			productrmassDao.add(productrawmaterialassociation);
		}
		return productRMAssociationDTO;
	}
	private Productrawmaterialassociation setMultipleRM(ProductRMAssociationModelParts productRMAssociationModelParts) throws Exception {
		Productrawmaterialassociation productrawmaterialassociation = new Productrawmaterialassociation();
		productrawmaterialassociation.setQuantity(productRMAssociationModelParts.getQuantity());
		productrawmaterialassociation.setRawmaterial(rawmaterialDao.getById(Rawmaterial.class, productRMAssociationModelParts.getRawmaterial().getId()));
		productrawmaterialassociation.setIsactive(true);
		return productrawmaterialassociation;
	}

	@Override
	public List<ProductRMAssociationDTO> getProductRMAssoList()
			throws Exception {
		// TODO Auto-generated method stub
		List<ProductRMAssociationDTO> productRMAssociationDTOs = new ArrayList<ProductRMAssociationDTO>();
		List<Productrawmaterialassociation> productrawmaterialassociations = productrmassDao.getList(Productrawmaterialassociation.class);
		for (Productrawmaterialassociation productrawmaterialassociation : productrawmaterialassociations) {
			ProductRMAssociationDTO productRMAssociationDTO = ProductRMAssoRequestResponseFactory.setProductRMAssoList(productrawmaterialassociation);
			productRMAssociationDTOs.add(productRMAssociationDTO);
		}
		return productRMAssociationDTOs;
	}

	@Override
	public ProductRMAssociationDTO getProductRMAsooById(long Id)
			throws Exception {
		// TODO Auto-generated method stub
		Productrawmaterialassociation productrawmaterialassociation = productrmassDao.getById(Productrawmaterialassociation.class, Id);
		ProductRMAssociationDTO productRMAssociationDTO = ProductRMAssoRequestResponseFactory.setProductRMAssoList(productrawmaterialassociation);
		return productRMAssociationDTO;
	}

	@Override
	public void deleteProductRMAssoById(long id) throws Exception {
		// TODO Auto-generated method stub
		Productrawmaterialassociation productrawmaterialassociation = productrmassDao.getById(Productrawmaterialassociation.class, id);
		productrawmaterialassociation.setIsactive(false);
		productrmassDao.update(productrawmaterialassociation);
	}

	@Override
	public List<Productrawmaterialassociation> getProductRMAssoListByProductID(
			long productID) throws Exception {
		// TODO Auto-generated method stub
		return productrmassDao.getProductRMAssoListByProductId(productID);
	}

	@Override
	public List<ProductRMAssociationDTO> getProductRMListByProductId(long rmId)
			throws Exception {
		// TODO Auto-generated method stub
		List<ProductRMAssociationDTO> productRMAssociationDTOs =  new ArrayList<ProductRMAssociationDTO>();
		List<Productrawmaterialassociation> productrawmaterialassociations = productrmassDao.getProductRMListByProductId(rmId);
		for (Productrawmaterialassociation productrawmaterialassociation : productrawmaterialassociations) {
			ProductRMAssociationDTO productRMAssociationDTO = ProductRMAssoRequestResponseFactory.setProductRMAssoList(productrawmaterialassociation);
			productRMAssociationDTOs.add(productRMAssociationDTO);
		}
		return productRMAssociationDTOs;
	}

	@Override
	public ProductRMAssociationDTO getProductRMListByProduct(long productId)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
		

}