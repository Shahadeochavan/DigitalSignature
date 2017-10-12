package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dao.BOMRMVendorAssociationDao;
import com.nextech.erp.dao.ClientDao;
import com.nextech.erp.dao.ProductorderDao;
import com.nextech.erp.dao.StatusDao;
import com.nextech.erp.dto.ProductOrderDTO;
import com.nextech.erp.dto.ProductOrderData;
import com.nextech.erp.factory.ProductOrderRequestResponseFactory;
import com.nextech.erp.model.Bom;
import com.nextech.erp.model.Bomrmvendorassociation;
import com.nextech.erp.model.Client;
import com.nextech.erp.model.Productorder;
import com.nextech.erp.model.Status;
import com.nextech.erp.newDTO.ClientDTO;
import com.nextech.erp.newDTO.ProductDTO;
import com.nextech.erp.newDTO.ProductOrderAssociationDTO;
import com.nextech.erp.service.BomService;
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
	
	@Autowired 
	BomService bomService;
	
	@Autowired
	BOMRMVendorAssociationDao bomrmVendorAssociationDao;
	
	@Override
	public Productorder getProductorderByProductOrderId(long productOrderId)throws Exception {
		return productorderDao.getProductorderByProductOrderId(productOrderId);
	}
	
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
	public ProductOrderDTO addMultipleProductOrder(ProductOrderDTO productOrderDTO,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Productorder productorder = ProductOrderRequestResponseFactory.setProductOrder(productOrderDTO);
		productorder.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		productorder.setClient(clientDao.getById(Client.class,productOrderDTO.getClientId().getId()));
		productorder.setStatus(statusDao.getById(Status.class,Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_NEW_PRODUCT_ORDER, null, null))));
	long id =	productorderDao.add(productorder);
		System.out.println(productorder);
		String innoiceNo = generateProductOrderInvoiceNumber() + productorder.getId();
		productorder.setInvoiceNo(innoiceNo);
		productorderDao.update(productorder);
	Productorder  productorder2 = productorderDao.getById(Productorder.class, productorder.getId());
		ProductOrderDTO productOrderDTO2 =  new ProductOrderDTO();
		productOrderDTO2.setId(productorder2.getId());
		productOrderDTO2.setCreatedDate(productorder2.getCreatedDate());
		productOrderDTO2.setInvoiceNo(productorder2.getInvoiceNo());
		productOrderDTO2.setStatusId(productorder2.getStatus());
		productOrderDTO2.setPoNO(productorder2.getPoNO());
		productOrderDTO2.setCreateDate(productorder2.getCreateDate());
		return productOrderDTO2;
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
		ClientDTO client = clientService.getClientDTOById(productOrderDTO.getClientId().getId());
		if (productOrderAssociationDTOs != null&& !productOrderAssociationDTOs.isEmpty()) {
			for (ProductOrderAssociationDTO productOrderAssociationDTO : productOrderAssociationDTOs) {
				productorderassociationService.addEntity(ProductOrderRequestResponseFactory.setProductOrderAsso(productOrderDTO, productOrderAssociationDTO,request));
			}
		}
		List<ProductOrderData> productOrderDatas = new ArrayList<ProductOrderData>();
		for (ProductOrderAssociationDTO productOrderAssociationDTO : productOrderAssociationDTOs) {
			float totalRate=0;
			ProductDTO product = productService.getProductDTO(productOrderAssociationDTO.getProductId().getId());
			Bom bom = bomService.getBomByProductId(productOrderAssociationDTO.getProductId().getId());
			if(bom !=null){
				ProductOrderData productOrderData = new ProductOrderData();
				List<Bomrmvendorassociation> bomrmvendorassociations = bomrmVendorAssociationDao.getBomRMVendorByBomId(bom.getId());
				for (Bomrmvendorassociation bomrmvendorassociation : bomrmvendorassociations) {
					totalRate = totalRate+bomrmvendorassociation.getCost();
					productOrderData.setProductName(product.getName());
					productOrderData.setQuantity(productOrderAssociationDTO.getQuantity());
					productOrderData.setRate((long)(totalRate));
					productOrderData.setAmount((long)totalRate*productOrderAssociationDTO.getQuantity());
					productOrderData.setCgst(product.getTaxStructureDTO().getCgst());
					productOrderData.setSgst(product.getTaxStructureDTO().getSgst());
					productOrderData.setIgst(product.getTaxStructureDTO().getIgst());
				}
				productOrderDatas.add(productOrderData);
			} else {
				System.out.println("It looks like BOM is not created for this product : " + product.getId() );
			}
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
		productorder.setStatus(statusDao.getById(Status.class,Long.parseLong(messageSource.getMessage(ERPConstants.STATUS_NEW_PRODUCT_ORDER, null, null))));
		productorderDao.update(productorder);
		return productOrderDTO;
	}
	private String generateProductOrderInvoiceNumber() {
		String invoiceNo = "";
		invoiceNo = "000";
		return invoiceNo;
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
}
