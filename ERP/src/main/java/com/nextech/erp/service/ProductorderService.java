package com.nextech.erp.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nextech.erp.dto.ProductOrderDTO;
import com.nextech.erp.dto.ProductOrderData;
import com.nextech.erp.model.Productorder;

public interface ProductorderService extends CRUDService<Productorder>{

	public Productorder getProductorderByProductOrderId(long productOrderId) throws Exception;

	public List<ProductOrderDTO> getPendingProductOrders(long statusId,long statusId1);

	public List<ProductOrderDTO> getInCompleteProductOrder(long clientId,long statusId,long statusId1);
	
	public ProductOrderDTO addMultipleProductOrder(ProductOrderDTO productOrderDTO,HttpServletRequest request,HttpServletResponse response)throws Exception;
	
	public ProductOrderDTO updateMultiple(ProductOrderDTO productOrderDTO,HttpServletRequest request,HttpServletResponse response)throws Exception;
	
	public List<ProductOrderDTO> getProductOrderList() throws Exception;
	
	public ProductOrderDTO getProductById(long id) throws Exception;
	
	public ProductOrderDTO deleteProductOrder(long id) throws Exception;
	
	public List<ProductOrderData> createProductorderAsso(ProductOrderDTO productOrderDTO,HttpServletRequest request) throws Exception;

	List<ProductOrderDTO> getInCompleteProductOrders(long statusId);
	
	List<ProductOrderDTO> getNewAndInCompleteProductOrders(long newStatus,long inCompleteStatus) throws Exception;
}
