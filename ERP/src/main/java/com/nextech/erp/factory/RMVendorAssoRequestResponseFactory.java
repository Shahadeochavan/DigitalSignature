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

}
