package com.nextech.dscrm.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.nextech.dscrm.constants.ERPConstants;
import com.nextech.dscrm.dao.ClientDao;
import com.nextech.dscrm.dao.ClientproductassoDao;
import com.nextech.dscrm.dao.ProductDao;
import com.nextech.dscrm.dao.ProductinventoryDao;
import com.nextech.dscrm.dao.ProductorderDao;
import com.nextech.dscrm.dao.StatusDao;
import com.nextech.dscrm.dto.ProductOrderDTO;
import com.nextech.dscrm.dto.ProductOrderData;
import com.nextech.dscrm.factory.ProductOrderRequestResponseFactory;
import com.nextech.dscrm.model.Client;
import com.nextech.dscrm.model.Clientproductasso;
import com.nextech.dscrm.model.Product;
import com.nextech.dscrm.model.Productinventory;
import com.nextech.dscrm.model.Productorder;
import com.nextech.dscrm.model.Status;
import com.nextech.dscrm.newDTO.ProductOrderAssociationDTO;
import com.nextech.dscrm.service.ClientService;
import com.nextech.dscrm.service.ProductService;
import com.nextech.dscrm.service.ProductorderService;
import com.nextech.dscrm.service.ProductorderassociationService;

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
	
	@Autowired
	ClientproductassoDao clientproductassoDao;
	
	@Autowired
	ProductDao productDao;
	
	@Autowired
	ProductinventoryDao productinventoryDao;

	
	@Override
	public List<ProductOrderDTO> getPendingProductOrders(long statusId,long statusId1) {
		List<ProductOrderDTO> productOrderDTOs =  new ArrayList<ProductOrderDTO>();
		List<Productorder> productorders = productorderDao.getPendingProductOrders(statusId,statusId1);
		if(productorders==null){
			return null;
		}
		for (Productorder productorder : productorders) {
			ProductOrderDTO productOrderDTO = ProductOrderRequestResponseFactory.setProductOrderDTO(productorder);
			productOrderDTOs.add(productOrderDTO);
		}
		return productOrderDTOs;
	}
	@Override
	public List<ProductOrderDTO> getInCompleteProductOrder(long clientId,long statusId,long statusId1) {
		
		List<ProductOrderDTO> productOrderDTOs =  new ArrayList<ProductOrderDTO>();
		List<Productorder> productorders = productorderDao.getInCompleteProductOrder(clientId,statusId,statusId1);
		if(productorders==null){
			return null;
		}
		for (Productorder productorder : productorders) {
			ProductOrderDTO productOrderDTO = ProductOrderRequestResponseFactory.setProductOrderDTO(productorder);
			productOrderDTOs.add(productOrderDTO);
		}
		return productOrderDTOs;
	}
	
	@Override
	public List<ProductOrderDTO> getInCompleteProductOrders(long statusId) {
		
		List<ProductOrderDTO> productOrderDTOs =  new ArrayList<ProductOrderDTO>();
		List<Productorder> productorders = productorderDao.getInCompleteProductOrders(statusId);
		System.out.println(productorders);
		if(productorders==null){
			return null;
		}
		for (Productorder productorder : productorders) {
			ProductOrderDTO productOrderDTO = ProductOrderRequestResponseFactory.setProductOrderDTO(productorder);
			productOrderDTOs.add(productOrderDTO);
		}
		return productOrderDTOs;
	}
	@Override
	public Productorder addMultipleProductOrder(ProductOrderDTO productOrderDTO,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Productorder productorder = ProductOrderRequestResponseFactory.setProductOrder(productOrderDTO);
		Product product =  productDao.getById(Product.class, productOrderDTO.getProduct().getId());
		productorder.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		productorder.setClient(clientDao.getById(Client.class,productOrderDTO.getClientId().getId()));
		productorder.setProduct(product);

		if(productorder.getTotalAmount()==productorder.getReceivedAmount()){
			productorder.setStatus(statusDao.getById(Status.class,Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_PAYMENT_COMPLETE, null, null))));
			}else if(productorder.getTotalAmount()<productorder.getReceivedAmount()){
				productorder.setStatus(statusDao.getById(Status.class,Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_PAYMNET_INCOMPLETE, null, null))));	
			}else{
				productorder.setStatus(statusDao.getById(Status.class,Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_NO_PAYMENT, null, null))));
			}
	long id =	productorderDao.add(productorder);
		System.out.println(productorder);
		String innoiceNo = generateProductOrderInvoiceNumber() + productorder.getId();
		productorder.setInvoiceNo(innoiceNo);
		productorderDao.update(productorder);
		return productorder;
	}
	@Override
	public List<ProductOrderDTO> getProductOrderList() throws Exception {
		
		List<ProductOrderDTO> productOrderDTOs =  new ArrayList<ProductOrderDTO>();
		List<Productorder> productorders = productorderDao.getList(Productorder.class);
		if(productorders.isEmpty()){
			return null;
		}
		for (Productorder productorder : productorders) {
			ProductOrderDTO productOrderDTO  =  ProductOrderRequestResponseFactory.setProductOrderDTO(productorder);
			productOrderDTOs.add(productOrderDTO);
		}
		return productOrderDTOs;
	}
	@Override
	public ProductOrderDTO getProductById(long id) throws Exception {
		
		Productorder productorder = productorderDao.getById(Productorder.class, id);
		if(productorder==null){
			return null;
		}
		ProductOrderDTO productOrderDTO = ProductOrderRequestResponseFactory.setProductOrderDTO(productorder);
		return productOrderDTO;
	}
	@Override
	public ProductOrderDTO deleteProductOrder(long id) throws Exception {
		
		Productorder productorder = productorderDao.getById(Productorder.class, id);
		if(productorder==null){
			return null;
		}
		productorder.setIsactive(false);
		productorderDao.update(productorder);
		ProductOrderDTO productOrderDTO = ProductOrderRequestResponseFactory.setProductOrderDTO(productorder);
		return productOrderDTO;
		
	}
	@Override
	public List<ProductOrderData> createProductorderAsso(ProductOrderDTO productOrderDTO, HttpServletRequest request)
			throws Exception {
		
		
		List<ProductOrderAssociationDTO> productOrderAssociationDTOs = productOrderDTO.getProductOrderAssociationDTOs();
		if (productOrderAssociationDTOs != null&& !productOrderAssociationDTOs.isEmpty()) {
			for (ProductOrderAssociationDTO productOrderAssociationDTO : productOrderAssociationDTOs) {
				productorderassociationService.addEntity(ProductOrderRequestResponseFactory.setProductOrderAsso(productOrderDTO, productOrderAssociationDTO,request));
				Productinventory productinventory = productinventoryDao.getById(Productinventory.class, productOrderAssociationDTO.getProductId().getId());
				productinventory.setQuantityavailable(productinventory.getQuantityavailable()-productOrderAssociationDTO.getRemainingQuantity());
				productinventoryDao.update(productinventory);
			}
		}
		List<ProductOrderData> productOrderDatas = new ArrayList<ProductOrderData>();
		for (ProductOrderAssociationDTO productOrderAssociationDTO : productOrderAssociationDTOs) {
			ProductOrderData productOrderData =  new ProductOrderData();
			Clientproductasso clientproductasso = clientproductassoDao.getById(Clientproductasso.class, productOrderDTO.getClientId().getId());
			productOrderData.setAmount(productOrderAssociationDTO.getQuantity()*(long)((clientproductasso.getPricePerUnit())));
			productOrderDatas.add(productOrderData);
			
		}
		return productOrderDatas;
	}
	
	@Override
	public ProductOrderDTO updateMultiple(ProductOrderDTO productOrderDTO,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Productorder productorder = ProductOrderRequestResponseFactory.setProductOrder(productOrderDTO);
		if(productorder==null){
			return null;
		}
		productorder.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		if(productorder.getTotalAmount()==productorder.getReceivedAmount()){
		productorder.setStatus(statusDao.getById(Status.class,Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_PAYMENT_COMPLETE, null, null))));
		}else{
			productorder.setStatus(statusDao.getById(Status.class,Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_PAYMNET_INCOMPLETE, null, null))));	
		}
		productorderDao.update(productorder);
		return productOrderDTO;
	}
	private String generateProductOrderInvoiceNumber() {
		String year="";
		Date currentDate = new Date();
		if(currentDate.getMonth()+1 > 3){
			int str = currentDate.getYear()+1900;
			int stri = str + 1;
			String strDate = stri+"";
			year = str+"/"+strDate.substring(2);
		}else{
			int str = currentDate.getYear()+1899;
			int stri = str + 1;
			String strDate = stri+"";
			year = str+"/"+strDate.substring(2);
		}
		year = "NEX/"+year+"/";
		return year;
	}
	@Override
	public List<ProductOrderDTO> getNewAndInCompleteProductOrders(long newStatus,long inCompleteStatus)
			throws Exception {
		// TODO Auto-generated method stub
		List<ProductOrderDTO> productOrderDTOs =  new ArrayList<ProductOrderDTO>();
		List<Productorder> productorders = productorderDao.getNewAndInCompleteProductOrders(newStatus,inCompleteStatus);
		if(productorders.isEmpty()){
			return null;
		}
		for (Productorder productorder : productorders) {
			ProductOrderDTO productOrderDTO  =  ProductOrderRequestResponseFactory.setProductOrderDTO(productorder);
			productOrderDTOs.add(productOrderDTO);
		}
		return productOrderDTOs;
	}
	@Override
	public List<Productorder> getProductOrderListByClientId(long clientId)
			throws Exception {
		// TODO Auto-generated method stub
		return productorderDao.getProductOrderListByClientId(clientId);
	}
}
