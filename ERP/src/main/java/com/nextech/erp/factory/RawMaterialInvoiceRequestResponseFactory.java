package com.nextech.erp.factory;

import com.nextech.erp.dto.QualityCheckRMDTO;
import com.nextech.erp.dto.RawMaterialInvoiceDTO;
import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.model.Rawmaterialorder;
import com.nextech.erp.model.Rawmaterialorderassociation;
import com.nextech.erp.model.Rawmaterialorderinvoice;
import com.nextech.erp.model.Rawmaterialorderinvoiceassociation;
import com.nextech.erp.model.Rmorderinvoiceintakquantity;

public class RawMaterialInvoiceRequestResponseFactory {
	public static Rawmaterialorderinvoice setRMInvoice(RawMaterialInvoiceDTO rawMaterialInvoiceDTO){
		Rawmaterialorderinvoice rawmaterialorderinvoice = new Rawmaterialorderinvoice();
		rawmaterialorderinvoice.setInvoice_No(rawMaterialInvoiceDTO.getInvoiceNo());
		rawmaterialorderinvoice.setVendorname(rawMaterialInvoiceDTO.getVendorName());
		rawmaterialorderinvoice.setVehicleNo(rawMaterialInvoiceDTO.getVehicleNo());
		rawmaterialorderinvoice.setFirstName(rawMaterialInvoiceDTO.getDriverFirstName());
		rawmaterialorderinvoice.setLastName(rawMaterialInvoiceDTO.getDriverLastName());
		rawmaterialorderinvoice.setIntime(rawMaterialInvoiceDTO.getInTime());
		rawmaterialorderinvoice.setOuttime(rawMaterialInvoiceDTO.getOutTime());
		rawmaterialorderinvoice.setPo_No(rawMaterialInvoiceDTO.getPoNo());
		rawmaterialorderinvoice.setCreateDate(rawMaterialInvoiceDTO.getCreateDate());
		rawmaterialorderinvoice.setIsactive(true);
		return rawmaterialorderinvoice;
	}
	
	
	public static Rawmaterialorderinvoiceassociation setRMOrderInvoiceAsso(RawMaterialInvoiceDTO rawMaterialInvoiceDTO,Rawmaterialorderinvoice rawmaterialorderinvoice){
		Rawmaterialorderinvoiceassociation rawmaterialorderinvoiceassociation = new Rawmaterialorderinvoiceassociation();
		rawmaterialorderinvoiceassociation.setRawmaterialorderinvoice(rawmaterialorderinvoice);
		Rawmaterialorder rawmaterial = new Rawmaterialorder();
		rawmaterial.setId(rawMaterialInvoiceDTO.getPoNo());
		rawmaterialorderinvoiceassociation.setRawmaterialorder(rawmaterial);
		return rawmaterialorderinvoiceassociation;
	}
	
	public static QualityCheckRMDTO setRMOrderAsso(QualityCheckRMDTO qualityCheckRMDTO,Rawmaterialorderassociation rawmaterialorderassociation){
		AbstractRequestResponse.setAbstractData(qualityCheckRMDTO, rawmaterialorderassociation);
		qualityCheckRMDTO.setQuantity(rawmaterialorderassociation.getQuantity());
		qualityCheckRMDTO.setRemainingQuantity(rawmaterialorderassociation.getRemainingQuantity());
		qualityCheckRMDTO.setRmPartNo(rawmaterialorderassociation.getRawmaterial().getPartNumber());
		qualityCheckRMDTO.setDescription(rawmaterialorderassociation.getRawmaterial().getDescription());
		qualityCheckRMDTO.setId(rawmaterialorderassociation.getRawmaterial().getId());
		return qualityCheckRMDTO;
	}
	
	public static Rmorderinvoiceintakquantity setRMOInvoice(QualityCheckRMDTO qualityCheckRMDTO,Rmorderinvoiceintakquantity rmorderinvoiceintakquantity){
		AbstractRequestResponse.setAbstractDataToEntity(qualityCheckRMDTO,rmorderinvoiceintakquantity);
		Rawmaterial rawmaterial = new Rawmaterial();
		rawmaterial.setId(qualityCheckRMDTO.getId());
		rmorderinvoiceintakquantity.setQuantity((int) qualityCheckRMDTO.getQuantity());
		rmorderinvoiceintakquantity.setRawmaterial(rawmaterial);
		return rmorderinvoiceintakquantity;
	}
}