package com.nextech.erp.serviceImpl;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.nextech.erp.constants.ERPConstants;
import com.nextech.erp.dao.BOMRMVendorAssociationDao;
import com.nextech.erp.dao.BomDao;
import com.nextech.erp.dao.DispatchDao;
import com.nextech.erp.dao.ProductDao;
import com.nextech.erp.dao.ProductinventoryDao;
import com.nextech.erp.dao.ProductorderDao;
import com.nextech.erp.dao.ProductorderassociationDao;
import com.nextech.erp.dao.StatusDao;
import com.nextech.erp.dto.DispatchDTO;
import com.nextech.erp.dto.DispatchPartDTO;
import com.nextech.erp.dto.DispatchProductDTO;
import com.nextech.erp.factory.DispatchRequestResponseFactory;
import com.nextech.erp.model.Bom;
import com.nextech.erp.model.Bomrmvendorassociation;
import com.nextech.erp.model.Dispatch;
import com.nextech.erp.model.Product;
import com.nextech.erp.model.Productinventory;
import com.nextech.erp.model.Productorder;
import com.nextech.erp.model.Productorderassociation;
import com.nextech.erp.model.Status;
import com.nextech.erp.service.DispatchService;
import com.nextech.erp.status.Response;
@Service
public class DispatchServiceImpl extends CRUDServiceImpl<Dispatch> implements DispatchService {

	@Autowired
	DispatchDao dispatchDao;
	
	@Autowired
	ProductDao productDao;
	
	@Autowired
	ProductinventoryDao productinventoryDao;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	ProductorderassociationDao productorderassociationDao;
	
	@Autowired
	ProductorderDao productorderDao;
	
	@Autowired
	BomDao bomDao;
	
	@Autowired
	BOMRMVendorAssociationDao bOMRMVendorAssociationDao;
	
	@Autowired
	StatusDao statusDao;
	
	private static final int STATUS_PRODUCT_ORDER_INCOMPLETE = 32;
	private static final int STATUS_PRODUCT_ORDER_COMPLETE = 31;


	@Override
	public Dispatch getDispatchByProductOrderIdAndProductId(long orderID,
			long productID) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Dispatch> getDispatchByProductOrderId(long productOrderId)
			throws Exception {
		// TODO Auto-generated method stub
		return dispatchDao.getDispatchByProductOrderId(productOrderId);
	}

	@Override
	public Response addDispatchProduct(DispatchDTO dispatchDTO,HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		List<DispatchProductDTO> dispatchProductDTOs = new ArrayList<DispatchProductDTO>();
		for (DispatchPartDTO dispatchPartDTO : dispatchDTO.getDispatchPartDTOs()) {
			Dispatch dispatch = setDispatchPart(dispatchPartDTO);
			Productinventory productinventory = productinventoryDao.getProductinventoryByProductId(dispatch.getProduct()
							.getId());
			System.out.println("dispatchDTO" + dispatchDTO.getOrderId());
			List<Productorderassociation> productorderassociationList = productorderassociationDao.getProductOrderAssoByOrderId(dispatchDTO.getOrderId());
			for (Productorderassociation productorderassociation : productorderassociationList) {
				// check product id is equal
				if (dispatch.getProduct().getId() == productorderassociation.getProduct().getId()) {
					// update data related to dispatch
					if (productinventory.getQuantityavailable() < dispatch.getQuantity()|| productorderassociation.getQuantity() < dispatch.getQuantity()) {
						//return new UserStatus(2,messageSource.getMessage(ERPConstants.TO_CHECK_QUANTITY_IN_PRODUCTINVENTORY,null, null));
					} else {
						dispatch.setDescription(dispatchDTO.getDescription());
						dispatch.setProductorder(productorderDao.getById(Productorder.class,dispatchDTO.getOrderId()));
						dispatch.setInvoiceNo(dispatchDTO.getInvoiceNo());
						dispatch.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
						dispatch.setStatus(statusDao.getById(Status.class,Long.parseLong(messageSource.getMessage(ERPConstants.ORDER_DISPATCHED,null, null))));
						Product product = productDao.getById(Product.class,productorderassociation.getProduct().getId());
						Bom bom = bomDao.getBomByProductId(product.getId());
						if(bom==null){
							return new Response(1,"Please create bom for this product");
						}else{
						List<Bomrmvendorassociation> bomrmvendorassociations = bOMRMVendorAssociationDao.getBomRMVendorByBomId(bom.getId());
						float totalCost = 0;
						DispatchProductDTO  dispatchProductDTO = new DispatchProductDTO();
						dispatchProductDTO.setClientPartNumber(product.getClientpartnumber());
						dispatchProductDTO.setProductName(product.getPartNumber());
						dispatchProductDTO.setQuantityDispatched(dispatch.getQuantity());
						dispatchProductDTO.setDescription(dispatchDTO.getDescription());
						for (Bomrmvendorassociation bomrmvendorassociation : bomrmvendorassociations) {
						totalCost = totalCost+bomrmvendorassociation.getCost();
						dispatchProductDTO.setTotalCost(totalCost);
						}
						dispatchProductDTOs.add(dispatchProductDTO);
						}
						dispatchDao.add(dispatch);
					}
				}
			}
			Productorder productorder = productorderDao.getById(Productorder.class, dispatch.getProductorder().getId());
			Product product = productDao.getById(Product.class,dispatch.getProduct().getId());

			// TODO update product order association
			updateProductOrderAssoRemainingQuantity(productorder, dispatch,
					request);

			// TODO add product Inventroy history
			addProductInventoryHistory(dispatch.getQuantity(), product,
					dispatch, request);

			// TODO update product Inventory
			updateProductInventory(dispatch, product, request);

			// TODO update product order
			updateProductOrder(productorder, request);
		}
		return new Response(dispatchProductDTOs);
	
	}
	private void updateProductOrderAssoRemainingQuantity(Productorder productorder, Dispatch dispatch,HttpServletRequest request)
			throws Exception {
		Productorderassociation productorderassociation = productorderassociationDao.getProductorderassociationByProdcutOrderIdandProdcutId(
						productorder.getId(), dispatch.getProduct().getId());
		productorderassociation.setRemainingQuantity(productorderassociation.getRemainingQuantity() - dispatch.getQuantity());
		productorderassociation.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		productorderassociationDao.update(productorderassociation);
	}
	
	private void addProductInventoryHistory(long goodQuantity, Product product,Dispatch dispatch, HttpServletRequest request) throws Exception {
		Productinventory productinventory = productinventoryDao.getProductinventoryByProductId(product.getId());
		if (productinventory == null) {
			productinventory = new Productinventory();
			productinventory.setProduct(product);
			productinventory.setQuantityavailable(0);
			productinventory.setCreatedBy(Long.parseLong(request.getAttribute(
					"current_user").toString()));
			productinventory.setIsactive(true);
			productinventoryDao.add(productinventory);
		}
	}
	private Productinventory updateProductInventory(Dispatch dispatch,
			Product product, HttpServletRequest request) throws Exception {
		Productinventory productinventory = productinventoryDao.getProductinventoryByProductId(dispatch.getProduct().getId());
		if (productinventory == null) {
			productinventory = new Productinventory();
			productinventory.setProduct(product);
			productinventory.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			productinventory.setQuantityavailable(productinventory.getQuantityavailable() - dispatch.getQuantity());
			productinventory.setIsactive(true);
			productinventoryDao.add(productinventory);

		} else {
			productinventory.setQuantityavailable(productinventory.getQuantityavailable() - dispatch.getQuantity());
			productinventory.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
			productinventory.setIsactive(true);
			productinventoryDao.update(productinventory);

		}
		return productinventory;
	}
	private int getProductOrderStatus(Productorder productorder)
			throws Exception {
		boolean isOrderComplete = false;
		List<Productorderassociation> productorderassociationsList = productorderassociationDao.getProductorderassociationByOrderId(productorder.getId());
		for (Iterator<Productorderassociation> iterator = productorderassociationsList.iterator(); iterator.hasNext();) {
			Productorderassociation productorderassociation = (Productorderassociation) iterator.next();
			if (productorderassociation.getRemainingQuantity() == 0) {
				isOrderComplete = true;
			} else {
				isOrderComplete = false;
				break;
			}

		}
		return isOrderComplete ? STATUS_PRODUCT_ORDER_COMPLETE
				: STATUS_PRODUCT_ORDER_INCOMPLETE;
	}

	private void updateProductOrder(Productorder productorder,
			HttpServletRequest request)
			throws Exception {
		productorder.setStatus(statusDao.getById(Status.class,getProductOrderStatus(productorder)));
		productorder.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		productorder.setCreatedDate(new Timestamp(new Date().getTime()));
		productorderDao.update(productorder);
	}
		private Dispatch setDispatchPart(DispatchPartDTO dispatchPartDTO) throws Exception {
			Dispatch dispatch = new Dispatch();
			dispatch.setProduct(productDao.getById(Product.class,dispatchPartDTO.getProductId()));
			dispatch.setQuantity(dispatchPartDTO.getQuantity());
			dispatch.setIsactive(true);
			return dispatch;
		}

		@Override
		public List<DispatchDTO> getDispatchList() throws Exception {
			// TODO Auto-generated method stub
			List<DispatchDTO> dispatchDTOs =  new ArrayList<DispatchDTO>();
			List<Dispatch> dispatchs =  dispatchDao.getList(Dispatch.class);
			if(dispatchs.isEmpty()){
				return null;
			}
			for (Dispatch dispatch : dispatchs) {
				DispatchDTO dispatchDTO = DispatchRequestResponseFactory.setDispatchDTO(dispatch);
				dispatchDTOs.add(dispatchDTO);
			}
			return dispatchDTOs;
		}

		@Override
		public DispatchDTO getDispatchById(long id) throws Exception {
			// TODO Auto-generated method stub
			Dispatch dispatch = dispatchDao.getById(Dispatch.class, id);
			if(dispatch==null){
				return null;
			}
			DispatchDTO dispatchDTO = DispatchRequestResponseFactory.setDispatchDTO(dispatch);
			return dispatchDTO;
		}

		@Override
		public DispatchDTO deleteDispatchById(long id) throws Exception {
			// TODO Auto-generated method stub
			Dispatch dispatch = dispatchDao.getById(Dispatch.class, id);
			if(dispatch==null){
				return null;
			}
			dispatch.setIsactive(false);
			dispatchDao.update(dispatch);
			DispatchDTO dispatchDTO = DispatchRequestResponseFactory.setDispatchDTO(dispatch);
			return dispatchDTO;
		}
		

}
