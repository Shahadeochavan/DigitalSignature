package com.nextech.erp.factory;


import com.nextech.erp.dto.RawmaterialOrderDTO;
import com.nextech.erp.model.Rawmaterialorder;
import com.nextech.erp.model.Rawmaterialorderassociation;
import com.nextech.erp.newDTO.RMOrderAssociationDTO;

public class RMOrderRequestResponseFactory {
	
	public static Rawmaterialorder setRMOrder(RawmaterialOrderDTO rawmaterialOrderDTO){
		Rawmaterialorder rawmaterialorder = new Rawmaterialorder();
		rawmaterialorder.setActualPrice(rawmaterialOrderDTO.getActualPrice());
		rawmaterialorder.setDescription(rawmaterialOrderDTO.getDescription());
		rawmaterialorder.setId(rawmaterialOrderDTO.getId());
		rawmaterialorder.setIsactive(true);
		rawmaterialorder.setName(rawmaterialOrderDTO.getName());
		rawmaterialorder.setOtherCharges(rawmaterialOrderDTO.getOtherCharges());
		rawmaterialorder.setQuantity(rawmaterialOrderDTO.getRmOrderAssociationDTOs().size());
		rawmaterialorder.setTax(rawmaterialOrderDTO.getTax());
		rawmaterialorder.setVendor(rawmaterialOrderDTO.getVendorId());
		rawmaterialorder.setExpectedDeliveryDate(rawmaterialOrderDTO.getExpectedDeliveryDate());
		rawmaterialorder.setCreateDate(rawmaterialOrderDTO.getCreateDate());
		rawmaterialorder.setTotalprice(rawmaterialOrderDTO.getTotalPrice());
		rawmaterialorder.setStatus(rawmaterialOrderDTO.getStatusId());
		return rawmaterialorder;
	}
	
	public static Rawmaterialorderassociation setRMOrderAssociation(RawmaterialOrderDTO rawmaterialOrderDTO,RMOrderAssociationDTO rawAssociationDTO){
		Rawmaterialorderassociation rawmaterialorderassociation = new Rawmaterialorderassociation();
		Rawmaterialorder rawmaterialorder = new Rawmaterialorder();
		rawmaterialorder.setId(rawmaterialOrderDTO.getId());
		rawmaterialorderassociation.setQuantity(rawAssociationDTO.getQuantity());
		rawmaterialorderassociation.setRawmaterial(rawAssociationDTO.getRawmaterialId());
		rawmaterialorderassociation.setRawmaterialorder(rawmaterialorder);
		rawmaterialorderassociation.setRemainingQuantity(rawAssociationDTO.getQuantity());
		rawmaterialorderassociation.setIsactive(true);
		return rawmaterialorderassociation;
	}
	
	public static RawmaterialOrderDTO setRMOrderDTO(Rawmaterialorder rawmaterialorder){
		RawmaterialOrderDTO rawmaterialOrderDTO = new RawmaterialOrderDTO();
		rawmaterialOrderDTO.setActive(true);
		rawmaterialOrderDTO.setActualPrice(rawmaterialorder.getActualPrice());
		rawmaterialOrderDTO.setDescription(rawmaterialorder.getDescription());
		rawmaterialOrderDTO.setId(rawmaterialorder.getId());
		rawmaterialOrderDTO.setName(rawmaterialorder.getName());
		rawmaterialOrderDTO.setOtherCharges(rawmaterialorder.getOtherCharges());
		rawmaterialOrderDTO.setQuantity(rawmaterialorder.getQuantity());
		rawmaterialOrderDTO.setTax(rawmaterialorder.getTax());
		rawmaterialOrderDTO.setTotalPrice(rawmaterialorder.getTotalprice());
		rawmaterialOrderDTO.setVendorId(rawmaterialorder.getVendor());
		rawmaterialOrderDTO.setStatusId(rawmaterialorder.getStatus());
		rawmaterialOrderDTO.setCreateDate(rawmaterialorder.getCreateDate());
		rawmaterialOrderDTO.setExpectedDeliveryDate(rawmaterialorder.getExpectedDeliveryDate());
		return rawmaterialOrderDTO;
	}

}
