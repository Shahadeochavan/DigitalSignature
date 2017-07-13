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
		rawmaterialorder.setQuantity(rawmaterialOrderDTO.getQuantity());
		rawmaterialorder.setTax(rawmaterialOrderDTO.getTax());
		rawmaterialorder.setTotalprice(rawmaterialOrderDTO.getTotalprice());
		return rawmaterialorder;
	}
	
	public static Rawmaterialorderassociation setRMOrderAssociation(RawmaterialOrderDTO rawmaterialOrderDTO,RMOrderAssociationDTO rawAssociationDTO){
		Rawmaterialorderassociation rawmaterialorderassociation = new Rawmaterialorderassociation();
		rawmaterialorderassociation.setQuantity(rawAssociationDTO.getQuantity());
		rawmaterialorderassociation.setRawmaterial(rawAssociationDTO.getRawmaterialId());
		rawmaterialorderassociation.setRawmaterialorder(rawAssociationDTO.getRawmaterialorderId());
		rawmaterialorderassociation.setIsactive(true);
		return rawmaterialorderassociation;
	}

}
