package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.model.Rawmaterialvendorassociation;
import com.nextech.erp.model.Taxstructure;
import com.nextech.erp.model.Vendor;
import com.nextech.erp.newDTO.RMVendorAssociationDTO;
import com.nextech.erp.newDTO.RawMaterialDTO;
import com.nextech.erp.newDTO.VendorDTO;

public class RMVendorAssoRequestResponseFactory {
	
	public static Rawmaterialvendorassociation setRMVendorAsso(RMVendorAssociationDTO rmVendorAssociationDTO,HttpServletRequest request){
		Rawmaterialvendorassociation rawmaterialvendorassociation = new Rawmaterialvendorassociation();
		rawmaterialvendorassociation.setPricePerUnit(rmVendorAssociationDTO.getPricePerUnit());
		Rawmaterial  rawmaterial = new Rawmaterial();
		rawmaterial.setId(rmVendorAssociationDTO.getRawmaterialId().getId());
		rawmaterialvendorassociation.setRawmaterial(rawmaterial);
		Vendor vendor = new Vendor();
		vendor.setId(rmVendorAssociationDTO.getVendorId().getId());
		rawmaterialvendorassociation.setVendor(vendor);
		rawmaterialvendorassociation.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		rawmaterialvendorassociation.setId(rmVendorAssociationDTO.getId());
		Taxstructure taxstructure =  new Taxstructure();
		taxstructure.setId(rmVendorAssociationDTO.getTaxStructureDTO().getId());
		rawmaterialvendorassociation.setTaxstructure(taxstructure);
		rawmaterialvendorassociation.setIsactive(true);
		return rawmaterialvendorassociation;
	}
	
	public static Rawmaterialvendorassociation setRMVendorAssoUpdate(RMVendorAssociationDTO rmVendorAssociationDTO,HttpServletRequest request){
		Rawmaterialvendorassociation rawmaterialvendorassociation = new Rawmaterialvendorassociation();
		rawmaterialvendorassociation.setPricePerUnit(rmVendorAssociationDTO.getPricePerUnit());
		Rawmaterial  rawmaterial = new Rawmaterial();
		rawmaterial.setId(rmVendorAssociationDTO.getRawmaterialId().getId());
		rawmaterialvendorassociation.setRawmaterial(rawmaterial);
		Vendor vendor = new Vendor();
		vendor.setId(rmVendorAssociationDTO.getVendorId().getId());
		rawmaterialvendorassociation.setVendor(vendor);
		rawmaterialvendorassociation.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		rawmaterialvendorassociation.setId(rmVendorAssociationDTO.getId());
		rawmaterialvendorassociation.setIsactive(true);
		return rawmaterialvendorassociation;
	}
	
	public static RMVendorAssociationDTO setRMVendorList(Rawmaterialvendorassociation rawmaterialvendorassociation){
		RMVendorAssociationDTO rawAssociationDTO = new RMVendorAssociationDTO();
		rawAssociationDTO.setActive(true);
		rawAssociationDTO.setId(rawmaterialvendorassociation.getId());
		rawAssociationDTO.setCreatedBy(rawmaterialvendorassociation.getCreatedBy());
		rawAssociationDTO.setCreatedDate(rawmaterialvendorassociation.getCreatedDate());
		rawAssociationDTO.setPricePerUnit(rawmaterialvendorassociation.getPricePerUnit());
		RawMaterialDTO  rawMaterialDTO = new RawMaterialDTO();
		rawMaterialDTO.setId(rawmaterialvendorassociation.getRawmaterial().getId());
		rawMaterialDTO.setPartNumber(rawmaterialvendorassociation.getRawmaterial().getPartNumber());
		rawMaterialDTO.setPricePerUnit(rawmaterialvendorassociation.getRawmaterial().getPricePerUnit());
		rawMaterialDTO.setDescription(rawmaterialvendorassociation.getRawmaterial().getDescription());
		rawAssociationDTO.setRawmaterialId(rawMaterialDTO);
		rawAssociationDTO.setUpdatedBy(rawmaterialvendorassociation.getUpdatedBy());
		rawAssociationDTO.setUpdatedDate(rawmaterialvendorassociation.getUpdatedDate());
		rawAssociationDTO.setUpdatedBy(rawmaterialvendorassociation.getUpdatedBy());
		VendorDTO  vendorDTO = new VendorDTO();
		vendorDTO.setId(rawmaterialvendorassociation.getVendor().getId());
		vendorDTO.setCompanyName(rawmaterialvendorassociation.getVendor().getCompanyName());
		rawAssociationDTO.setVendorId(vendorDTO);
		rawAssociationDTO.setCreatedBy(rawmaterialvendorassociation.getCreatedBy());
		return rawAssociationDTO;
	}

}
