package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.dto.RawmaterialOrderDTO;
import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.model.Rawmaterialorder;
import com.nextech.erp.model.Rawmaterialorderassociation;
import com.nextech.erp.newDTO.RMOrderAssociationDTO;
import com.nextech.erp.newDTO.RawMaterialDTO;

public class RMOrderAssociationRequestResponseFactory {

	public static Rawmaterialorderassociation setRMOrderAssocition(RMOrderAssociationDTO rmOrderAssociationDTO,HttpServletRequest request){
		Rawmaterialorderassociation rawmaterialorderassociation = new Rawmaterialorderassociation();
		rawmaterialorderassociation.setCreatedDate(rmOrderAssociationDTO.getCreatedDate());
		rawmaterialorderassociation.setId(rmOrderAssociationDTO.getId());
		rawmaterialorderassociation.setIsactive(true);
		rawmaterialorderassociation.setQuantity(rmOrderAssociationDTO.getQuantity());
		Rawmaterial rawmaterial =  new Rawmaterial();
		rawmaterial.setId(rmOrderAssociationDTO.getRawmaterialId().getId());
		rawmaterialorderassociation.setRawmaterial(rawmaterial);
		Rawmaterialorder  rawmaterialorder =  new Rawmaterialorder();
		rawmaterialorder.setId(rmOrderAssociationDTO.getRawmaterialOrderId().getId());
		rawmaterialorderassociation.setRawmaterialorder(rawmaterialorder);
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
		Rawmaterial rawmaterial =  new Rawmaterial();
		rawmaterial.setId(rmOrderAssociationDTO.getRawmaterialId().getId());
		rawmaterialorderassociation.setRawmaterial(rawmaterial);
		Rawmaterialorder  rawmaterialorder =  new Rawmaterialorder();
		rawmaterialorder.setId(rmOrderAssociationDTO.getRawmaterialOrderId().getId());
		rawmaterialorderassociation.setRawmaterialorder(rawmaterialorder);
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
		RawMaterialDTO  rawMaterialDTO =  new RawMaterialDTO();
		rawMaterialDTO.setId(rawmaterialorderassociation.getRawmaterial().getId());
		rmOrderAssociationDTO.setRawmaterialId(rawMaterialDTO);
		RawmaterialOrderDTO rawmaterialOrderDTO = new RawmaterialOrderDTO();
		rawmaterialOrderDTO.setId(rawmaterialorderassociation.getRawmaterialorder().getId());
		rawmaterialOrderDTO.setName(rawmaterialorderassociation.getRawmaterialorder().getName());
		rmOrderAssociationDTO.setRawmaterialOrderId(rawmaterialOrderDTO);
		rmOrderAssociationDTO.setRemainingQuantity(rawmaterialorderassociation.getRemainingQuantity());
		rmOrderAssociationDTO.setUpdatedDate(rawmaterialorderassociation.getUpdatedDate());
		rmOrderAssociationDTO.setUpdatedBy(rawmaterialorderassociation.getUpdatedBy());
		return rmOrderAssociationDTO;
	}
	
	
}
