package com.nextech.erp.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nextech.erp.dto.QualityCheckRMDTO;
import com.nextech.erp.dto.RawMaterialInvoiceDTO;
import com.nextech.erp.model.Qualitycheckrawmaterial;

public interface QualitycheckrawmaterialService extends
		CRUDService<Qualitycheckrawmaterial> {
	public Qualitycheckrawmaterial getQualitycheckrawmaterialByInvoiceIdAndRMId(long invoiceId,long rmId) throws Exception;

	public List<Qualitycheckrawmaterial> getQualitycheckrawmaterialByInvoiceId(long invoiceId) throws Exception;
	
	public RawMaterialInvoiceDTO addQualityCheck(RawMaterialInvoiceDTO rawMaterialInvoiceDTO,HttpServletRequest request,HttpServletResponse response)throws Exception;
	
	public void addRawmaterialorderinvoiceReadyStore(RawMaterialInvoiceDTO rawMaterialInvoiceDTO,HttpServletRequest request,HttpServletResponse response)throws Exception;

}
