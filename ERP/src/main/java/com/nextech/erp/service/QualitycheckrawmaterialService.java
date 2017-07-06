package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Qualitycheckrawmaterial;

public interface QualitycheckrawmaterialService extends
		CRUDService<Qualitycheckrawmaterial> {
	public Qualitycheckrawmaterial getQualitycheckrawmaterialByInvoiceIdAndRMId(long invoiceId,long rmId) throws Exception;

	public List<Qualitycheckrawmaterial> getQualitycheckrawmaterialByInvoiceId(long invoiceId) throws Exception;

}
