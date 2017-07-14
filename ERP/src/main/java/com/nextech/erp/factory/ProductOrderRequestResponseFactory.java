package com.nextech.erp.factory;

import com.nextech.erp.dto.ProductOrderDTO;
import com.nextech.erp.model.Productorder;
import com.nextech.erp.model.Productorderassociation;
import com.nextech.erp.newDTO.ProductOrderAssociationDTO;

public class ProductOrderRequestResponseFactory {
	
	public static Productorder setProductOrder(ProductOrderDTO productOrderDTO){
		Productorder productorder = new Productorder();
		productorder.setClient(productOrderDTO.getClientId());
		productorder.setCreateDate(productOrderDTO.getCreateDate());
		productorder.setDescription(productOrderDTO.getDescription());
		productorder.setExpecteddeliveryDate(productOrderDTO.getExpectedDeliveryDate());
		productorder.setInvoiceNo(productOrderDTO.getInvoiceNo());
		productorder.setId(productOrderDTO.getId());
		productorder.setQuantity(productOrderDTO.getProductOrderAssociationDTOs().size());
		productorder.setIsactive(true);
		return productorder;
	}
	public static Productorderassociation setProductOrderAsso(ProductOrderDTO productOrderDTO,ProductOrderAssociationDTO productOrderAssociationDTO){
		Productorderassociation productorderassociation = new Productorderassociation();
		Productorder productorder = new Productorder();
		productorder.setId(productOrderDTO.getId());
		productorderassociation.setQuantity(productOrderAssociationDTO.getQuantity());
		productorderassociation.setRemainingQuantity(productOrderAssociationDTO.getQuantity());
		productorderassociation.setProduct(productOrderAssociationDTO.getProductId());
		productorderassociation.setProductorder(productorder);
		productorderassociation.setIsactive(true);
		return productorderassociation;
	}

}
