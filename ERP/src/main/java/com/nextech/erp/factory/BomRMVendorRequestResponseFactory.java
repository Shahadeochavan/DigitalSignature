package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.dto.BomModelPart;
import com.nextech.erp.model.Bom;
import com.nextech.erp.model.Bomrmvendorassociation;
import com.nextech.erp.newDTO.BomRMVendorAssociationsDTO;

public class BomRMVendorRequestResponseFactory {
	
	public static Bomrmvendorassociation  setBomVendorAsso(BomModelPart bomModelPart,HttpServletRequest request){
		Bomrmvendorassociation bomrmvendorassociation = new Bomrmvendorassociation();
		Bom bom =  new Bom();
		bom.setId(bomModelPart.getId());
		bomrmvendorassociation.setBom(bom);
		bomrmvendorassociation.setCost(bomModelPart.getCost());
		bomrmvendorassociation.setId(bomModelPart.getId());
		bomrmvendorassociation.setIsactive(true);
		bomrmvendorassociation.setPricePerUnit(bomModelPart.getPricePerUnit());
		bomrmvendorassociation.setQuantity(bomModelPart.getQuantity());
		bomrmvendorassociation.setRawmaterial(bomModelPart.getRawmaterial());
		bomrmvendorassociation.setVendor(bomModelPart.getVendor());
		bomrmvendorassociation.setCreatedBy(request.getAttribute("current_user").toString());
		return bomrmvendorassociation;
	}
	
	public static BomRMVendorAssociationsDTO setBOMRMVendorAssoDTO(Bomrmvendorassociation bomrmvendorassociation){
		BomRMVendorAssociationsDTO bomRMVendorAssociationsDTO = new BomRMVendorAssociationsDTO();
		bomRMVendorAssociationsDTO.setActive(true);
		bomRMVendorAssociationsDTO.setBomId(bomrmvendorassociation.getBom());
		bomRMVendorAssociationsDTO.setId(bomrmvendorassociation.getId());
		bomRMVendorAssociationsDTO.setPricePerUnit(bomrmvendorassociation.getPricePerUnit());
		bomRMVendorAssociationsDTO.setQuantity(bomrmvendorassociation.getQuantity());
		bomRMVendorAssociationsDTO.setCost(bomrmvendorassociation.getCost());
		bomRMVendorAssociationsDTO.setRawmaterialId(bomrmvendorassociation.getRawmaterial());
		bomRMVendorAssociationsDTO.setVendorId(bomrmvendorassociation.getVendor());
		return bomRMVendorAssociationsDTO;
	}

}
