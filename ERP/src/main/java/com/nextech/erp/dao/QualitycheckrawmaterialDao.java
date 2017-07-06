package com.nextech.erp.dao;

import java.util.List;

import com.nextech.erp.model.Qualitycheckrawmaterial;

public interface QualitycheckrawmaterialDao extends
		SuperDao<Qualitycheckrawmaterial> {
	
	public Qualitycheckrawmaterial getQualitycheckrawmaterialByInvoiceIdAndRMId(long invoiceId,long rmId) throws Exception;
	
	public List<Qualitycheckrawmaterial> getQualitycheckrawmaterialByInvoiceId(long invoiceId) throws Exception;

}
