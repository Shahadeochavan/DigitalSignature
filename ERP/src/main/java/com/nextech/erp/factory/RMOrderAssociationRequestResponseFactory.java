package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.model.Rawmaterialorderassociation;
import com.nextech.erp.newDTO.RMOrderAssociationDTO;

public class RMOrderAssociationRequestResponseFactory {

	public static Rawmaterialorderassociation setRMOrderAssocition(RMOrderAssociationDTO rmOrderAssociationDTO,HttpServletRequest request){
		Rawmaterialorderassociation rawmaterialorderassociation = new Rawmaterialorderassociation();
		rawmaterialorderassociation.setCreatedDate(rmOrderAssociationDTO.getCreatedDate());
		rawmaterialorderassociation.setId(rmOrderAssociationDTO.getId());
		rawmaterialorderassociation.setIsactive(true);
		rawmaterialorderassociation.setQuantity(rmOrderAssociationDTO.getQuantity());
		rawmaterialorderassociation.setRawmaterial(rmOrderAssociationDTO.getRawmaterialId());
		rawmaterialorderassociation.setRawmaterialorder(rmOrderAssociationDTO.getRawmaterialOrderId());
		rawmaterialorderassociation.setRemainingQuantity(rmOrderAssociationDTO.getQuantity());
		rawmaterialorderassociation.setUpdatedDate(rmOrderAssociationDTO.getUpdatedDate());
		rawmaterialorderassociation.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return rawmaterialorderassociation;
	}
	public static Rawmaterialorderassociation setRMOrderAssocitionUpdate(RMOrderAssociationDTO rmOrderAssociationDTO,HttpServletRequest request){
		Rawmaterialorderassociation rawmaterialorderassociation = new Rawmaterialorderassociation();
		rawmaterialorderassociation.setCreatedDate(rmOrderAssociationDTO.getCreatedDate());
		rawmaterialorderassociation.setId(rmOrderAssociationDTO.getId());
		rawmaterialorderassociation.setIsactive(true);
		rawmaterialorderassociation.setQuantity(rmOrderAssociationDTO.getQuantity());
		rawmaterialorderassociation.setRawmaterial(rmOrderAssociationDTO.getRawmaterialId());
		rawmaterialorderassociation.setRawmaterialorder(rmOrderAssociationDTO.getRawmaterialOrderId());
		rawmaterialorderassociation.setRemainingQuantity(rmOrderAssociationDTO.getQuantity());
		rawmaterialorderassociation.setUpdatedDate(rmOrderAssociationDTO.getUpdatedDate());
		rawmaterialorderassociation.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return rawmaterialorderassociation;
	}
	
	public static RMOrderAssociationDTO setRMOrderDTO(Rawmaterialorderassociation rawmaterialorderassociation){
		RMOrderAssociationDTO  rmOrderAssociationDTO = new RMOrderAssociationDTO();
		rmOrderAssociationDTO.setActive(true);
		rmOrderAssociationDTO.setCreatedBy(rawmaterialorderassociation.getCreatedBy());
		rmOrderAssociationDTO.setCreatedDate(rawmaterialorderassociation.getCreatedDate());
		rmOrderAssociationDTO.setId(rawmaterialorderassociation.getId());
		rmOrderAssociationDTO.setQuantity(rawmaterialorderassociation.getQuantity());
		rmOrderAssociationDTO.setRawmaterialId(rawmaterialorderassociation.getRawmaterial());
		rmOrderAssociationDTO.setRawmaterialOrderId(rawmaterialorderassociation.getRawmaterialorder());
		rmOrderAssociationDTO.setRemainingQuantity(rawmaterialorderassociation.getRemainingQuantity());
		rmOrderAssociationDTO.setUpdatedDate(rawmaterialorderassociation.getUpdatedDate());
		rmOrderAssociationDTO.setUpdatedBy(rawmaterialorderassociation.getUpdatedBy());
		return rmOrderAssociationDTO;
	}
	
	
}
