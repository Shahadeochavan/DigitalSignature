package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dao.ClientDao;
import com.nextech.erp.dao.ProductorderDao;
import com.nextech.erp.dao.StatusDao;
import com.nextech.erp.dto.ProductOrderDTO;
import com.nextech.erp.dto.ProductOrderData;
import com.nextech.erp.factory.ProductOrderRequestResponseFactory;
import com.nextech.erp.model.Client;
import com.nextech.erp.model.Productorder;
import com.nextech.erp.model.Status;
import com.nextech.erp.newDTO.ClientDTO;
import com.nextech.erp.newDTO.ProductDTO;
import com.nextech.erp.newDTO.ProductOrderAssociationDTO;
import com.nextech.erp.service.ClientService;
import com.nextech.erp.service.ProductService;
import com.nextech.erp.service.ProductorderService;
import com.nextech.erp.service.ProductorderassociationService;
@Service
public class ProductorderServiceImpl extends CRUDServiceImpl<Productorder> implements ProductorderService {

	@Autowired
	ProductorderDao productorderDao;
	
	@Autowired
	ClientDao clientDao;
	
	@Autowired
	StatusDao statusDao;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	ProductorderassociationService productorderassociationService;
	
	@Override
	public Productorder getProductorderByProductOrderId(long pOrderId)
			throws Exception {
		// TODO Auto-generated method stub
		return productorderDao.getProductorderByProductOrderId(pOrderId);
	}
	@Override
	public List<ProductOrderDTO> getPendingProductOrders(long statusId,long statusId1) {
		List<ProductOrderDTO> productOrderDTOs =  new ArrayList<ProductOrderDTO>();
		List<Productorder> productorders = productorderDao.getPendingProductOrders(statusId,statusId1);
		for (Productorder productorder : productorders) {
			ProductOrderDTO productOrderDTO = ProductOrderRequestResponseFactory.setProductOrderDTO(productorder);
			productOrderDTOs.add(productOrderDTO);
		}
		return productOrderDTOs;
	}
	@Override
	public List<ProductOrderDTO> getInCompleteProductOrder(long clientId,long statusId,long statusId1) {
		// TODO Auto-generated method stub
		List<ProductOrderDTO> productOrderDTOs =  new ArrayList<ProductOrderDTO>();
		List<Productorder> productorders = productorderDao.getInCompleteProductOrder(clientId,statusId,statusId1);
		for (Productorder productorder : productorders) {
			ProductOrderDTO productOrderDTO = ProductOrderRequestResponseFactory.setProductOrderDTO(productorder);
			productOrderDTOs.add(productOrderDTO);
		}
		return productOrderDTOs;
	}
	@Override
	public ProductOrderDTO createMultiple(ProductOrderDTO productOrderDTO,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		Productorder productorder = ProductOrderRequestResponseFactory.setProductOrder(productOrderDTO);
		productorder.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		productorder.setClient(clientDao.getById(Client.class,productOrderDTO.getClientId().getId()));
		productorder.setStatus(statusDao.getById(Status.class,Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_NEW_PRODUCT_ORDER, null, null))));
		productorderDao.add(productorder);
		System.out.println(productorder);
	Productorder  productorder2 = productorderDao.getById(Productorder.class, productorder.getId());
		ProductOrderDTO productOrderDTO2 =  new ProductOrderDTO();
		productOrderDTO2.setId(productorder2.getId());
		productOrderDTO2.setCreatedDate(productorder2.getCreatedDate());
		productOrderDTO2.setInvoiceNo(productorder2.getInvoiceNo());
		return productOrderDTO2;
	}
	@Override
	public List<ProductOrderDTO> getProductOrderList() throws Exception {
		// TODO Auto-generated method stub
		List<ProductOrderDTO> productOrderDTOs =  new ArrayList<ProductOrderDTO>();
		List<Productorder> productorders = productorderDao.getList(Productorder.class);
		for (Productorder productorder : productorders) {
			ProductOrderDTO productOrderDTO  =  ProductOrderRequestResponseFactory.setProductOrderDTO(productorder);
			productOrderDTOs.add(productOrderDTO);
		}
		return productOrderDTOs;
	}
	@Override
	public ProductOrderDTO getProductById(long id) throws Exception {
		// TODO Auto-generated method stub
		Productorder productorder = productorderDao.getById(Productorder.class, id);
		ProductOrderDTO productOrderDTO = ProductOrderRequestResponseFactory.setProductOrderDTO(productorder);
		return productOrderDTO;
	}
	@Override
	public void deleteProductOrder(long id) throws Exception {
		// TODO Auto-generated method stub
		Productorder productorder = productorderDao.getById(Productorder.class, id);
		productorder.setIsactive(false);
		productorderDao.update(productorder);
		
	}
	@Override
	public List<ProductOrderData> createProductorderAsso(ProductOrderDTO productOrderDTO, HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		List<ProductOrderAssociationDTO> productOrderAssociationDTOs = productOrderDTO.getProductOrderAssociationDTOs();
		ClientDTO client = clientService.getClientDTOById(productOrderDTO.getClientId().getId());
		if (productOrderAssociationDTOs != null&& !productOrderAssociationDTOs.isEmpty()) {
			for (ProductOrderAssociationDTO productOrderAssociationDTO : productOrderAssociationDTOs) {
				productorderassociationService.addEntity(ProductOrderRequestResponseFactory.setProductOrderAsso(productOrderDTO, productOrderAssociationDTO));
			}
		}
		List<ProductOrderData> productOrderDatas = new ArrayList<ProductOrderData>();
		for (ProductOrderAssociationDTO productOrderAssociationDTO : productOrderAssociationDTOs) {
			ProductDTO product = productService.getProductDTO(productOrderAssociationDTO.getProductId().getId());
			ProductOrderData productOrderData = new ProductOrderData();
			productOrderData.setProductName(product.getName());
			productOrderData.setQuantity(productOrderAssociationDTO.getQuantity());
			productOrderData.setRate(product.getRatePerUnit());
			productOrderData.setAmount(product.getRatePerUnit()*productOrderAssociationDTO.getQuantity());
			productOrderDatas.add(productOrderData);
		}
		return productOrderDatas;
	}
}
