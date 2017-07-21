package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.model.Rawmaterialvendorassociation;
import com.nextech.erp.newDTO.RMVendorAssociationDTO;

public class RMVendorAssoRequestResponseFactory {
	
	public static Rawmaterialvendorassociation setRMVendorAsso(RMVendorAssociationDTO rmVendorAssociationDTO,HttpServletRequest request){
		Rawmaterialvendorassociation rawmaterialvendorassociation = new Rawmaterialvendorassociation();
		rawmaterialvendorassociation.setPricePerUnit(rmVendorAssociationDTO.getPricePerUnit());
		rawmaterialvendorassociation.setRawmaterial(rmVendorAssociationDTO.getRawmaterialId());
		rawmaterialvendorassociation.setVendor(rmVendorAssociationDTO.getVendorId());
		rawmaterialvendorassociation.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		rawmaterialvendorassociation.setId(rmVendorAssociationDTO.getId());
		rawmaterialvendorassociation.setIsactive(true);
		return rawmaterialvendorassociation;
	}
	
	public static Rawmaterialvendorassociation setRMVendorAssoUpdate(RMVendorAssociationDTO rmVendorAssociationDTO,HttpServletRequest request){
		Rawmaterialvendorassociation rawmaterialvendorassociation = new Rawmaterialvendorassociation();
		rawmaterialvendorassociation.setPricePerUnit(rmVendorAssociationDTO.getPricePerUnit());
		rawmaterialvendorassociation.setRawmaterial(rmVendorAssociationDTO.getRawmaterialId());
		rawmaterialvendorassociation.setVendor(rmVendorAssociationDTO.getVendorId());
		rawmaterialvendorassociation.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		rawmaterialvendorassociation.setId(rmVendorAssociationDTO.getId());
		rawmaterialvendorassociation.setIsactive(true);
		return rawmaterialvendorassociation;
	}
	
	public static RMVendorAssociationDTO setRMVendorList(Rawmaterialvendorassociation rawmaterialvendorassociation){
		RMVendorAssociationDTO rawAssociationDTO = new RMVendorAssociationDTO();
		rawAssociationDTO.setActive(true);
		rawAssociationDTO.setCreatedBy(rawmaterialvendorassociation.getCreatedBy());
		rawAssociationDTO.setCreatedDate(rawmaterialvendorassociation.getCreatedDate());
		rawAssociationDTO.setPricePerUnit(rawmaterialvendorassociation.getPricePerUnit());
		rawAssociationDTO.setRawmaterialId(rawmaterialvendorassociation.getRawmaterial());
		rawAssociationDTO.setUpdatedBy(rawmaterialvendorassociation.getUpdatedBy());
		rawAssociationDTO.setUpdatedDate(rawmaterialvendorassociation.getUpdatedDate());
		rawAssociationDTO.setUpdatedBy(rawmaterialvendorassociation.getUpdatedBy());
		rawAssociationDTO.setVendorId(rawmaterialvendorassociation.getVendor());
		return rawAssociationDTO;
	}

}
