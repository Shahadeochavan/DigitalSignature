package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nextech.erp.dto.QualityCheckRMDTO;
import com.nextech.erp.model.Qualitycheckrawmaterial;
import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.model.Rawmaterialorderinvoice;
import com.nextech.erp.model.Rmorderinvoiceintakquantity;

public class QualityRequestResponseFactory {
	public static QualityCheckRMDTO createQualityResonse(Rmorderinvoiceintakquantity rmorderinvoiceintakquantity){
		QualityCheckRMDTO qualityCheckRMDTO = new QualityCheckRMDTO();
		AbstractRequestResponse.setAbstractData(qualityCheckRMDTO,rmorderinvoiceintakquantity);
		qualityCheckRMDTO.setQuantity(rmorderinvoiceintakquantity.getQuantity());
		qualityCheckRMDTO.setRmPartNo(rmorderinvoiceintakquantity.getRawmaterial().getPartNumber());
		return qualityCheckRMDTO;
	}
	
	public static Qualitycheckrawmaterial setQualityCheckRM(Qualitycheckrawmaterial qualitycheckrawmaterial,QualityCheckRMDTO qualityCheckRMDTO,Rawmaterialorderinvoice rawmaterialorderinvoiceNew,Rawmaterial rawmaterial,HttpServletRequest request,HttpServletResponse response){
		qualitycheckrawmaterial.setRawmaterialorderinvoice(rawmaterialorderinvoiceNew);
		qualitycheckrawmaterial.setRawmaterial(rawmaterial);
		qualitycheckrawmaterial.setGoodQuantity(qualityCheckRMDTO.getGoodQuantity());
		qualitycheckrawmaterial.setIntakeQuantity(qualityCheckRMDTO.getIntakeQuantity());
		qualitycheckrawmaterial.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		qualitycheckrawmaterial.setIsactive(true);
		qualitycheckrawmaterial.setRemark(qualityCheckRMDTO.getRemark());
		qualitycheckrawmaterial.setPriceperunit(qualityCheckRMDTO.getPriceperunit());
		return qualitycheckrawmaterial;
	}
}
