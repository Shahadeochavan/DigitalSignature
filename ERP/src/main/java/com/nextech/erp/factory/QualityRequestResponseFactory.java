package com.nextech.erp.factory;

import com.nextech.erp.dto.QualityCheckRMDTO;
import com.nextech.erp.model.Rmorderinvoiceintakquantity;

public class QualityRequestResponseFactory {
	public static QualityCheckRMDTO createQualityResonse(Rmorderinvoiceintakquantity rmorderinvoiceintakquantity){
		QualityCheckRMDTO qualityCheckRMDTO = new QualityCheckRMDTO();
		AbstractRequestResponse.setAbstractData(qualityCheckRMDTO,rmorderinvoiceintakquantity);
		qualityCheckRMDTO.setQuantity(rmorderinvoiceintakquantity.getQuantity());
		qualityCheckRMDTO.setRmPartNo(rmorderinvoiceintakquantity.getRawmaterial().getPartNumber());
		return qualityCheckRMDTO;
	}
}
