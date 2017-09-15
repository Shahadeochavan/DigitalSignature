package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;
import com.nextech.erp.dto.BomDTO;
import com.nextech.erp.dto.BomModelPart;
import com.nextech.erp.model.Bom;
import com.nextech.erp.model.Bomrmvendorassociation;
import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.model.Vendor;
import com.nextech.erp.newDTO.BomRMVendorAssociationsDTO;
import com.nextech.erp.newDTO.RawMaterialDTO;
import com.nextech.erp.newDTO.VendorDTO;

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
		Rawmaterial rawmaterial =  new Rawmaterial();
		rawmaterial.setId(bomModelPart.getRawmaterial().getId());
		bomrmvendorassociation.setRawmaterial(rawmaterial);
		Vendor vendor =  new Vendor();
		vendor.setId(bomModelPart.getVendor().getId());
		bomrmvendorassociation.setVendor(vendor);
		bomrmvendorassociation.setCost(bomModelPart.getQuantity()*bomModelPart.getPricePerUnit());
		bomrmvendorassociation.setCreatedBy(request.getAttribute("current_user").toString());
		return bomrmvendorassociation;
	}
	
	public static BomRMVendorAssociationsDTO setBOMRMVendorAssoDTO(Bomrmvendorassociation bomrmvendorassociation){
		BomRMVendorAssociationsDTO bomRMVendorAssociationsDTO = new BomRMVendorAssociationsDTO();
		bomRMVendorAssociationsDTO.setActive(true);
		BomDTO bomDTO =  new BomDTO();
		bomDTO.setBomId(bomrmvendorassociation.getBom().getBomId());
		bomRMVendorAssociationsDTO.setBomId(bomDTO);
		bomRMVendorAssociationsDTO.setId(bomrmvendorassociation.getId());
		bomRMVendorAssociationsDTO.setPricePerUnit(bomrmvendorassociation.getPricePerUnit());
		bomRMVendorAssociationsDTO.setQuantity(bomrmvendorassociation.getQuantity());
		bomRMVendorAssociationsDTO.setCost(bomrmvendorassociation.getCost());
		RawMaterialDTO  rawMaterialDTO = new RawMaterialDTO();
		rawMaterialDTO.setId(bomrmvendorassociation.getRawmaterial().getId());
		rawMaterialDTO.setPartNumber(bomrmvendorassociation.getRawmaterial().getPartNumber());
		bomRMVendorAssociationsDTO.setRawmaterialId(rawMaterialDTO);
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setId(bomrmvendorassociation.getVendor().getId());
		vendorDTO.setCompanyName(bomrmvendorassociation.getVendor().getCompanyName());
		bomRMVendorAssociationsDTO.setVendorId(vendorDTO);
		return bomRMVendorAssociationsDTO;
	}

}
