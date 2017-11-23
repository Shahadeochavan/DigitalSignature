package com.nextech.dscrm.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.dscrm.dto.ProductOrderDTO;
import com.nextech.dscrm.model.Product;
import com.nextech.dscrm.model.Productorder;
import com.nextech.dscrm.model.Productorderassociation;
import com.nextech.dscrm.newDTO.ProductOrderAssociationDTO;

public class ProductOrderRequestResponseFactory {
	
	public static Productorder setProductOrder(ProductOrderDTO productOrderDTO){
		Productorder productorder = new Productorder();
		productorder.setClient(productOrderDTO.getClientId());
		productorder.setCreateDate(productOrderDTO.getCreateDate());
		productorder.setDescription(productOrderDTO.getDescription());
		productorder.setProduct(productOrderDTO.getProduct());
	//	productorder.setExpecteddeliveryDate(productOrderDTO.getExpectedDeliveryDate());
		//productorder.setPoNO(productOrderDTO.getPoNO());
		productorder.setId(productOrderDTO.getId());
		productorder.setQuantity(productOrderDTO.getQuantity());
		productorder.setTotalAmount(productOrderDTO.getTotalAmount());
		productorder.setReceivedAmount(productOrderDTO.getReceivedAmount());
		productorder.setIsactive(true);
		return productorder;
	}
	public static Productorderassociation setProductOrderAsso(ProductOrderDTO productOrderDTO,ProductOrderAssociationDTO productOrderAssociationDTO,HttpServletRequest request){
		Productorderassociation productorderassociation = new Productorderassociation();
		Productorder productorder = new Productorder();
		productorder.setId(productOrderDTO.getId());
		productorderassociation.setQuantity(productOrderAssociationDTO.getQuantity());
		productorderassociation.setRemainingQuantity(productOrderAssociationDTO.getQuantity());
		Product product =  new Product();
		product.setId(productOrderAssociationDTO.getProductId().getId());
		productorderassociation.setProduct(product);
		productorderassociation.setProductorder(productorder);
		productorderassociation.setIsactive(true);
		productorderassociation.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return productorderassociation;
	}
	public static ProductOrderDTO setProductOrderDTO(Productorder productorder){
		ProductOrderDTO productOrderDTO =  new ProductOrderDTO();
		productOrderDTO.setActive(true);
		productOrderDTO.setId(productorder.getId());
		productOrderDTO.setClientId(productorder.getClient());
		productOrderDTO.setProduct(productorder.getProduct());
		productOrderDTO.setCreatedDate(productorder.getCreatedDate());
		productOrderDTO.setDescription(productorder.getDescription());
		productOrderDTO.setExpectedDeliveryDate(productorder.getExpecteddeliveryDate());
		productOrderDTO.setInvoiceNo(productorder.getInvoiceNo());
		productOrderDTO.setPoNO(productorder.getPoNO());
		productOrderDTO.setQuantity(productorder.getQuantity());
		productOrderDTO.setStatusId(productorder.getStatus());
		productOrderDTO.setUpdatedBy(productorder.getUpdatedBy());
		productOrderDTO.setUpdatedDate(productorder.getUpdatedDate());
		productOrderDTO.setCreateDate(productorder.getCreateDate());
		productOrderDTO.setTotalAmount(productorder.getTotalAmount());
		productOrderDTO.setReceivedAmount(productorder.getReceivedAmount());
		return productOrderDTO;
	}
}
